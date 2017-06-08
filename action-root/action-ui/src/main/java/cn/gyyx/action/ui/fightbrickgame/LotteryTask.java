package cn.gyyx.action.ui.fightbrickgame;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.gyyx.action.beans.config.po.ActionConfigPO;
import cn.gyyx.action.beans.fightbrickgame.Constants;
import cn.gyyx.action.beans.fightbrickgame.FightBrickGameBean;
import cn.gyyx.action.beans.lottery.UserInfoBean;
import cn.gyyx.action.bll.config.impl.DefaultHdConfigBLL;
import cn.gyyx.action.bll.fightbrickgame.FightBrickGameBLL;
import cn.gyyx.action.bll.lottery.UserLotteryBll;
import cn.gyyx.action.bll.newLottery.NewUserLotteryBll;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.action.service.fightbrickgame.FightBrickGameService;
import cn.gyyx.distribute.lock.DistributedLock;

/**
 * 
 * <p>
 * 微信h5小游戏抽奖定时任务
 * </p>
 * 
 * @author Administrator
 * @since 0.0.1
 */
public class LotteryTask {
    private Logger logger = LoggerFactory.getLogger(LotteryTask.class);

    private FightBrickGameBLL fightBrickGameBll = new FightBrickGameBLL();

    private NewUserLotteryBll userLotteryBll = new NewUserLotteryBll();

    private UserLotteryBll lotteryBll = new UserLotteryBll();

    private FightBrickGameService fightBrickGameService = new FightBrickGameService();
    
    /**
     * 每天凌晨00:01定时抽奖
     */
    public void start() throws Exception {
        DefaultHdConfigBLL hdConfigBll = new DefaultHdConfigBLL();
        ActionConfigPO configInfo = hdConfigBll
                .getConfigEntity(Constants.ACTION_CODE);

        Date hdEnd = configInfo.getHdEnd();
        Date now = new Date();
        
        if ((hdEnd.getTime()+5*60*1000) <= now.getTime()) {
            logger.info("活动已经结束了,不用进行定时抽奖的任务了");
            return;
        }        
        
        logger.info("查询昨天得分前200名");
        List<FightBrickGameBean> list = fightBrickGameBll.getYesterdayRank();
        if (list.isEmpty()) {
            logger.info("查询昨天排行200名的数据失败");
            return;
        }

        Object obj=hdConfigBll.getConfigByKey(Constants.ACTION_CODE, "PrizeName");                
        String prizeName= obj==null?Constants.PRIZE_NAME:obj.toString();
        logger.info("奖品名称{}",prizeName);
        
        try (DistributedLock lock = new DistributedLock(
                Constants.DISTRIBUTED_LOCK_LOTTERY_TASK)) {
            lock.weakLock(100, 0);
            // 查询当天是否有抽奖日志
            List<UserInfoBean> winUserList = lotteryBll
                    .getUserLottery(Constants.ACTION_CODE);
            if (winUserList != null && !winUserList.isEmpty()) {
                UserInfoBean winUser = winUserList.get(0);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date yesterday = fightBrickGameService.addDate(
                    sdf.parse(sdf.format(new Date())), -1);
                if (winUser.getTime().getTime() >= yesterday.getTime()) {
                    // 已经开始抽奖了,直接返回
                    logger.info("定时抽奖的任务已经执行,直接返回");
                    return;
                }
            }

            // 抽奖
            lottery(winUserList, list, prizeName);

        } catch (Exception ex) {
            logger.error("定时抽奖任务分布式锁异常", ex);
            return;
        }
    }

    /**
     * 
     * <p>
     * 用户不能重复中奖,删除已经中奖的用户
     * </p>
     *
     * @action yangteng 2017年3月25日 下午3:14:07 描述
     *
     * @param winUserList
     * @param list
     *            void
     */
    private List<FightBrickGameBean> getNotLotteryUserList(List<UserInfoBean> winUserList,
            List<FightBrickGameBean> list) {
        
        if (winUserList == null || winUserList.isEmpty()) {
            return list;
        }

        List<FightBrickGameBean> newList=new ArrayList<FightBrickGameBean>();       
        for (FightBrickGameBean item : list) {
            boolean isExist=false;
            for (UserInfoBean winUser : winUserList) {
                if (item.getOpenid().equals(winUser.getAccount())) {
                    isExist=true;
                    break;
                }
            }
            if(!isExist) 
                newList.add(item);
        }
        return newList;
    }

    /**
     * 
     * <p>
     * 具有临时抽奖资格的人选中10个人
     * </p>
     *
     * @action yangteng 2017年3月25日 上午11:33:52 描述
     *
     * @param list
     * @return List<String>
     */
    public List<String> getLotteryUserList(List<UserInfoBean> winUserList,
            List<FightBrickGameBean> list) {
        List<String> lotteryList = new ArrayList<String>();

        // 用户不能重复中奖,删除已经中奖的用户
        List<FightBrickGameBean> userList=getNotLotteryUserList(winUserList, list);

        int score = 0;
        for (FightBrickGameBean item : userList) {
            score += item.getHighScore();
        }

        Date date = new Date();
        Random rand = new Random(date.getTime());

        //处理用户少于10个人时场景
        if(userList.size()<=10){
            for(FightBrickGameBean item : userList){
                lotteryList.add(item.getOpenid());
            }
            return lotteryList;
        }      
        int j = 0;
        while (j < 10) {
            int temp = 0;
            int number = rand.nextInt(score);
            for (int i = 0; i < userList.size(); i++) {
                FightBrickGameBean model = userList.get(i);
                if (number <= model.getHighScore() + temp && number > temp) {
                    if (lotteryList.contains(model.getOpenid())) {
                        break;
                    }
                    j++;
                    lotteryList.add(model.getOpenid());
                    break;
                }
                temp += model.getHighScore();
            }
        }
        return lotteryList;
    }

    /**
     * 
     * <p>
     * 抽奖
     * </p>
     *
     * @action yangteng 2017年3月25日 下午12:31:08 描述
     *
     * @param userList
     *            等待抽奖的用户
     * @param prizeList
     *            奖品列表
     * @return List<String>
     */
    public void lottery(List<UserInfoBean> winUserList,
            List<FightBrickGameBean> list, String prizeName) {
        SqlSession session = getSession(false);
        // 具有临时抽奖资格的集合中抽取10个人
        List<String> userList = getLotteryUserList(winUserList, list);

        logger.info("抽奖开始");

        int j = 0;
        while (j < userList.size()) {
            String openid = userList.get(j);

            logger.info(String.format("微信用户%s抽中奖品%s", openid,
                prizeName));
            try {
                UserInfoBean userInfoBean = initUserBean(openid, prizeName);
                // 记录中奖日志
                userLotteryBll.addUserLotteryInfo(userInfoBean, session);
                // 提交事务
                session.commit();
                logger.info(String.format("微信用户%s抽中奖品%s,记录抽奖日志成功", openid,
                    prizeName));
                // 计数器+1
                j++;
            } catch (Exception ex) {
                session.rollback();
                logger.error("记录抽奖日志OR更新奖品数量异常,错误消息", ex);
                logger.info(String.format("微信用户%s抽中奖品%s,记录抽奖日志OR更新奖品数量异常",
                    openid, prizeName));
            }
        }
        logger.info("抽奖结束");
    }

    /**
     * 
     * <p>
     * 初始化中奖日志bean
     * </p>
     *
     * @action yangteng 2017年3月25日 下午1:29:01 描述
     *
     * @param openid
     * @param prize
     * @return UserInfoBean
     */
    private UserInfoBean initUserBean(String openid, String prizeName)
            throws Exception {
        UserInfoBean userInfoBean = new UserInfoBean();
        userInfoBean.setActionCode(Constants.ACTION_CODE);
        userInfoBean.setSource("h5小游戏");// 领奖时记录发奖的订单号
        userInfoBean.setSourceCode(447);
        userInfoBean.setAccount(openid);
        userInfoBean.setGameCode(Constants.GAME_ID);
        userInfoBean.setServerCode(0);
        userInfoBean.setServerName("");
        userInfoBean.setPresentType(Constants.VIRTUAL_PRESENT_TYPE);
        userInfoBean.setPresentName(prizeName);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        userInfoBean.setTime(fightBrickGameService.addDate(
            sdf.parse(sdf.format(new Date())), -1));
        userInfoBean.setIp("0.0.0.0");// 领奖时更新用户的ip地址
        userInfoBean.setAvailable(0);
        return userInfoBean;
    }

    /**
     * 获得SqlSession
     * 
     * @autoCommit 自动提交
     * @return
     */
    private SqlSession getSession(boolean autoCommit) {
        SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
                .getSqlActionDBV2SessionFactory();
        return sqlSessionFactory.openSession(autoCommit);
    }
}
