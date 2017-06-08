/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action-wdshenluowanxiang
 * @作者：chenglong
 * @联系方式：chenglong@gyyx.cn
 * @创建时间：2017年3月12日 下午4:23:05
 * @版本号：0.0.1
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.service.wdshenluowangxiang;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.PrizeBean;
import cn.gyyx.action.beans.lottery.UserLotteryBean;
import cn.gyyx.action.beans.lottery.po.ActionLotteryLogPO;
import cn.gyyx.action.beans.lottery.po.ActionPrizesAddressPO;
import cn.gyyx.action.beans.wdshenluowangxiang.Constants;
import cn.gyyx.action.beans.wdshenluowangxiang.ShenLuoWangXiangBean;
import cn.gyyx.action.bll.lottery.IActionLotteryLogBLL;
import cn.gyyx.action.bll.lottery.IActionPrizesAddressBLL;
import cn.gyyx.action.bll.lottery.UserLotteryBll;
import cn.gyyx.action.bll.lottery.impl.ActionLotteryLogBLLImpl;
import cn.gyyx.action.bll.lottery.impl.ActionPrizesAddressBLLImpl;
import cn.gyyx.action.bll.wdshenluowangxiang.ShenLuoWangXiangBll;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.action.service.newLottery.LotteryException;
import cn.gyyx.action.service.newLottery.NewlotteryService;
import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.distribute.lock.DistributedLock;
import cn.gyyx.log.sdk.GYYXLogger;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

import com.google.common.base.Throwables;

/**
 * <p>
 *   森罗万象抽奖service
 * </p>
 * 
 * @author chenglong
 * @since 0.0.1
 */
public class ShenLuoWangXiangLotteryService {
    private static final GYYXLogger LOG = GYYXLoggerFactory
            .getLogger(ShenLuoWangXiangLotteryService.class);
    
    // 公用抽奖实现
    private NewlotteryService newlotteryService = new NewlotteryService();
    // 抽奖bll
    private UserLotteryBll userLotteryBll = new UserLotteryBll();
    // 抽奖日志bll
    private IActionLotteryLogBLL actionLotteryLogBLL = new ActionLotteryLogBLLImpl();
    // 用户表bll
    private static final ShenLuoWangXiangBll shenLuoWangXiangBll = new ShenLuoWangXiangBll();
    // 抽奖地址bll
    private IActionPrizesAddressBLL actionPrizesAddressBLL = new ActionPrizesAddressBLLImpl();
    
    private static final SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
            .getSqlActionDBV2SessionFactory();

    /**
     * 
      * <p>
      *    抽奖service 代码
      * </p>
      *
      * @action
      *    chenglong 2017年4月9日 下午3:02:02 抽奖service
      *
      * @param actionCode
      * @param userInfo
      * @param ip
      * @return ResultBean<UserLotteryBean>
     * @throws Exception 
     */
    public ResultBean<UserLotteryBean> doLottery(UserInfo userInfo, String ip) throws Exception {
        // 是否符合绑定条件
        ShenLuoWangXiangBean roleBean = shenLuoWangXiangBll.getUserInfoByUserId(userInfo.getUserId());
        if(roleBean == null){
            return new ResultBean<>(false,"请先绑定区组！",null);
        }
        if(roleBean.getMaxLevel() == null || roleBean.getMaxLevel() < 20 ){
            return new ResultBean<>(false,"绑定区组下未查询到20级以上的角色,请稍后再试！",null);
        }
        
        ResultBean<UserLotteryBean> result = null;
        // 开分布式锁 规则：活动ID前缀_用户ID
        try (DistributedLock lock = new DistributedLock(Constants.LOTTERY_PRIFIX
            + userInfo.getUserId());){
            
            lock.weakLock(65, 10);
            //获取抽奖信息
            result = getLotteryResult(userInfo, ip, roleBean);
        } catch (Exception e) {
            LOG.error(Constants.HD_NAME + "_用户抽奖时失败,错误堆栈：{}",
                Throwables.getStackTraceAsString(e));
            return new ResultBean<>(false,"人太多了，请稍后再试！",null);
        } 
        return result;
    }

    /**
      * <p>
      *    抽奖
      * </p>
      *
      * @action
      *    chenglong 2017年4月10日 下午12:33:42 抽奖
      *
      * @param userInfo
      * @param ip
      * @param roleBean
      * @return
      * @throws LotteryException
      * @throws Exception ResultBean<UserLotteryBean>
      */
    public ResultBean<UserLotteryBean> getLotteryResult(UserInfo userInfo,
            String ip, ShenLuoWangXiangBean roleBean) throws 
            Exception {
        ResultBean<UserLotteryBean> result;
        try (SqlSession session = sqlSessionFactory.openSession()) {
            // 是否有抽奖次数
            if(roleBean.getLastNum() <= 0 ){
                return new ResultBean<>(false,"您目前的抽奖机会为0！",null);
            }
            
            List<PrizeBean> prizes = userLotteryBll
                    .getPrize(Constants.HD_CODE);
            // 设置纪念奖
            PrizeBean mPPrize = new PrizeBean();
            for (PrizeBean prize : prizes) {
                if ("谢谢参与".equals(prize.getChinese())) {
                    mPPrize = prize;
                }
            }
            // 按概率抽奖，并获取result
            result = newlotteryService.lotteryByDataBase(
                Constants.HD_CODE, userInfo.getUserId(), "byChance",
                userInfo.getAccount(), roleBean.getServerName(),
                roleBean.getServerId(), ip, mPPrize, session);
            
            LOG.info(String.format("活动名称：[%s]，活动编号：[%s]，用户[%s]，奖品信息:[%s], resultBeanInfo:[%s]",
                Constants.HD_NAME, Constants.HD_CODE, userInfo.getAccount(), result.getData()
                .getPrizeChinese(), result));
            
            // 减少抽奖次数
            shenLuoWangXiangBll.updateLuckNum(userInfo.getUserId(),session);
            // 记录抽奖结果
            ActionLotteryLogPO log = new ActionLotteryLogPO();
            log.setActivityId(Constants.HD_CODE);
            log.setUserId(userInfo.getAccount());
            log.setGameId(Integer.toString(Constants.GAMEID));
            log.setServerId(roleBean.getServerId()+"");
            log.setPrizeType(result.getData().getIsReal());
            log.setPrizeCode(result.getData().getPrizeCode());
            log.setPrizeName(result.getData().getPrizeChinese());
            log.setPrizeNum(1); 
            log.setCardCode(result.getData().getCardCode());
            log.setWinningAt(new Date());
            log.setWinningIp(ip);
            log.setIsAvailable(result.getData().getIsAvailable());
            log.setActivityType("lottery");
            actionLotteryLogBLL.putData(log, session);
            
            if (result.getIsSuccess() ) {
                session.commit();
            } else {
                session.rollback();
            }
        }
        return result;
    }

    /**
     * 
      * <p>
      *    获取抽奖信息
      * </p>
      *
      * @action
      *    chenglong 2017年4月9日 下午5:17:03 获取抽奖信息
      *
      * @param userInfo
      * @return List<ActionLotteryLogPO>
     */
    public List<ActionLotteryLogPO> getMyPrize(UserInfo userInfo) {
        ActionLotteryLogPO params = new ActionLotteryLogPO();
        params.setUserId(userInfo.getAccount());
        params.setActivityId(Constants.HD_CODE);
        return actionLotteryLogBLL.getDataExceptThankYou(params);
    }

    /**
     * 
      * <p>
      *    添加中奖实物地址
      * </p>
      *
      * @action
      *    chenglong 2017年4月9日 下午5:39:38 添加中奖实物地址
      *
      * @param params
      * @param userInfo void
     */
    public void putAddress(ActionPrizesAddressPO params,
            UserInfo userInfo) throws Exception{
        try (DistributedLock lock = new DistributedLock(Constants.HD_CODE + "_address_" + userInfo.getUserId() );){
                lock.weakLock(30, 0);  // 开启分布式锁
                params.setActivityId(Constants.HD_CODE);
                params.setUserCode(userInfo.getUserId());
                params.setUserId(userInfo.getAccount());
                params.setActivityType("lottery");
                try(SqlSession session = sqlSessionFactory.openSession(true);){
                    actionPrizesAddressBLL.post(params, session);
                }
        }
    }

    /**
     * 
      * <p>
      *    获取抽奖地址
      * </p>
      *
      * @action
      *    chenglong 2017年4月9日 下午6:04:13 获取抽奖地址
      *
      * @param userId
      * @return ShenLuoWangXiangLotteryAddressVO
     */
    public ActionPrizesAddressPO getAddress(String account) {
        return actionPrizesAddressBLL.get(Constants.HD_CODE, account, "lottery");
    }
}