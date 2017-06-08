package cn.gyyx.action.service.fightbrickgame;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.gyyx.action.beans.config.po.ActionConfigPO;
import cn.gyyx.action.beans.fightbrickgame.Constants;
import cn.gyyx.action.beans.fightbrickgame.FightBrickGameBean;
import cn.gyyx.action.beans.fightbrickgame.FightBrickGameHistoryBean;
import cn.gyyx.action.beans.lottery.UserInfoBean;
import cn.gyyx.action.beans.lottery.vo.LotteryPrizesVO;
import cn.gyyx.action.beans.novicecard.ServerInfoBean;
import cn.gyyx.action.beans.novicecard.ServerInfoBean.Value;
import cn.gyyx.action.beans.weixin.dictionary.AttentionDictionary;
import cn.gyyx.action.bll.config.impl.DefaultHdConfigBLL;
import cn.gyyx.action.bll.fightbrickgame.FightBrickGameBLL;
import cn.gyyx.action.bll.fightbrickgame.FightBrickGameHistoryBLL;
import cn.gyyx.action.bll.lottery.IActionPrizesAddressBLL;
import cn.gyyx.action.bll.lottery.UserLotteryBll;
import cn.gyyx.action.bll.lottery.impl.ActionPrizesAddressBLLImpl;
import cn.gyyx.action.bll.newLottery.NewUserLotteryBll;
import cn.gyyx.action.common.beans.ResultBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.action.service.agent.CallWebApiAgent;
import cn.gyyx.action.service.agent.CallWebServiceInsuranceAgent;
import cn.gyyx.action.service.weixin.WeChatAttention;
import cn.gyyx.core.memcache.XMemcachedClientAdapter;
import cn.gyyx.distribute.lock.DistributedLock;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * 微信H5小游戏服务层
 * <p>
 * FightBrickGameService描述
 * </p>
 * 
 * @author Administrator
 * @since 0.0.1
 */
public class FightBrickGameService {

    private static Logger logger = GYYXLoggerFactory
            .getLogger(FightBrickGameService.class);

    private XMemcachedClientAdapter memcachedClientAdapter = new XMemcachedClientAdapter("Action-ListData");

    private FightBrickGameBLL fightBrickGameBll = new FightBrickGameBLL();

    private WeChatAttention att = new WeChatAttention();

    private FightBrickGameHistoryBLL fightbrickGameHistoryBll = new FightBrickGameHistoryBLL();

    /**
     * 生成唯一标识放在memcache
     * 
     * @param openid
     * @return
     */
    public List<String> getIdentify() {

        // 生成key,value
        String key = UUID.randomUUID().toString();
        String value = UUID.randomUUID().toString();

        // 替换-
        key = key.replace("-", "");
        value = value.replace("-", "");

        List<String> list = new ArrayList<>();
        list.add(key);
        list.add(value);

        memcachedClientAdapter.add(key,
            getUntilDayEndSeconds(Constants.IDENTIFY_CACHE_MINUTE), value);
        return list;
    }

    /**
     * 记录得分
     * 
     * @param openid
     *            微信用户
     * @param encrypt
     *            加密之后分数
     * @param score
     *            用户提交的分数
     */
    public ResultBean<String> addScore(String openid, String encrypt,
            Integer score, String key) {

        ResultBean<String> result = new ResultBean<>();
        result.setData("expired");

        if (key == null) {
            logger.info("cookie的key已经过期或者被修改,参数:openid={},encrypt={},score={},key={}",
                new Object[] { openid, encrypt, score, key });
            result.setMessage("记录得分失败");
            return result;
        }

        String lockKey = String.format(Constants.DISTRIBUTED_LOCK_TEMPLATE, openid);
        String value = null;
        try (DistributedLock lock = new DistributedLock(lockKey)) {

            lock.weakLock(30, 10); // 开启分布式锁
            // 缓存读取唯一标识
            value = memcachedClientAdapter.get(key, String.class);
            if (value == null) {
                logger.info(
                    "缓存的value已经过期,参数:openid={},encrypt={},score={},key={}",
                    new Object[] { openid, encrypt, score, key });
                result.setMessage("记录得分失败");
                return result;
            }
            // 缓存删除value
            memcachedClientAdapter.delete(key);

        } catch (Exception ex) {
            logger.error("分布式事物锁异常,错误消息", ex);
            result.setMessage("记录分数失败,请重试");
            return result;
        }

        // 校验用户提交的数据
        String str = encrypt.substring(0, 32);
        if (!str.equals(value)) {
            logger.info(
                "用户修改参数encrypt,参数openid={},encrypt={},score={},key={},缓存value={},返回结果:分数不合法",
                new Object[] { openid, encrypt, score, key, value });
            result.setMessage("分数不合法");
            return result;
        }

        Integer realScore = Integer.parseInt(encrypt.substring(32), 16);
        if (realScore.intValue() != score.intValue()) {
            logger.info(
                "用户修改参数score,参数openid={},encrypt={},score={},key={},缓存value={},realScore={},返回消息:分数不合法",
                new Object[] { openid, encrypt, score, key, value, realScore });
            result.setMessage("分数不合法");
            return result;
        }

        // 缓存读取用户昵称
        ResultBean<String> nickNameResult = getNickNameFromCache(openid);
        if (!nickNameResult.getIsSuccess()) {
            result.setMessage(nickNameResult.getMessage());
            return result;
        }

        String nickName = nickNameResult.getData();
        
        // 向历史表记录得分
        FightBrickGameHistoryBean fightBrickGameHistory = new FightBrickGameHistoryBean(openid, nickName, realScore);
        Integer row = fightbrickGameHistoryBll.addScoreHistory(fightBrickGameHistory);
        if (row <= 0) {
            result.setMessage("记录得分失败");
            return result;
        }

        String updateScoreLockKey = String.format(Constants.DISTRIBUTED_UPDATE_SCORE_LOCK_TEMPLATE, openid);
        try (DistributedLock lock = new DistributedLock(updateScoreLockKey)) {
            lock.weakLock(30, 10); // 开启分布式锁
            Integer updateRow = 0;
            // 查询用户当天得分信息
            FightBrickGameBean fightBrickGame = fightBrickGameBll.getScoreInfo(openid);
            if (fightBrickGame == null) {
                // 添加用户得分信息
                fightBrickGame = new FightBrickGameBean(openid, nickName, realScore, realScore);
                updateRow = fightBrickGameBll.addScore(fightBrickGame);
            } else {
                // 更新用户的信息
                fightBrickGame.setNickName(nickName);
                fightBrickGame.setLatestScore(realScore);
                Integer highScore = fightBrickGame.getHighScore();
                if (realScore > highScore) {
                    fightBrickGame.setHighScore(realScore);
                    fightBrickGame.setUpdate(true);
                }
                updateRow = fightBrickGameBll.update(fightBrickGame);
            }
    
            if (updateRow <= 0) {
                result.setMessage("记录得分失败");
                return result;
            }
        
        }catch(Exception ex){
            logger.error("更新分数时,分布式事物锁异常",ex);
            result.setMessage("记录得分失败");
            return result;
        }
        result.setMessage("记录得分成功");
        result.setIsSuccess(true);
        return result;
    }

    /**
     * 查询最新得分，当天最高得分，历史最高得分，当天排行，是否有领奖资格，是否有奖励未领取
     * 
     * @return
     */
    public ResultBean<Map<String, Object>> getStatInfo(String openid,
            Integer activityId) throws Exception {
        ResultBean<Map<String, Object>> result = new ResultBean<>();

        // 查询最新得分,当天最高得分，当天排行
        FightBrickGameBean model = fightBrickGameBll.getRank(openid);
        if (model == null) {
            result.setMessage("暂无数据");
            return result;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("latestScore", model.getLatestScore());
        map.put("highScore", model.getHighScore());
        map.put("rank", model.getRank());

        // 查询历史最高得分
        Integer historyScore = fightBrickGameBll.getHighScore(openid);
        map.put("historyScore", historyScore);

        //查询活动配置信息
        ResultBean<Map<String,Object>> hdResult = getHdConfigInfo(activityId);
        if(!hdResult.getIsSuccess()){
            result.setMessage(hdResult.getMessage());
            return result;
        }
        
        Map<String,Object> hdMap=hdResult.getData();
        boolean isOver7Days=(boolean) hdMap.get("isOver7Days");        
        Date hdEnd=(Date) hdMap.get("hdEnd");
        
        // 查询未领取的奖励
        UserLotteryBll userLotteryBll = new UserLotteryBll();
        List<UserInfoBean> list = userLotteryBll.wishGetUserLotteryByUserCode(activityId, openid);
        if (list != null && !list.isEmpty()) {
            //1活动结束超过7天,没有未领取奖励的提示
            //2活动结束没有超过7天,但是已经所有领取奖励,也没有未领取奖励的提示
            if (isOver7Days) {
                map.put("receive", true);
            } else {
                boolean isShow = true;
                for (UserInfoBean item : list) {
                    if (item.getAvailable() == 0) {
                        isShow = false;
                        break;
                    }
                }
                map.put("receive", isShow);
            }
        }
        Date now=new Date();
        if (hdEnd.getTime() > now.getTime()) {
            // 查询是否有临时抽奖资格
            Integer tempScore = fightBrickGameBll.getTempQualification();
            if (tempScore <= model.getHighScore()) {
                // 有获奖资格
                map.put("qualification", true);
            } else {
                map.put("qualification", false);
                map.put("differScore", tempScore - model.getHighScore());
            }
        }

        result.setIsSuccess(true);
        result.setMessage("查询成功");
        result.setData(map);
        return result;
    }

    /**
     * 查询个人奖励
     * 
     * @param openid
     * @param actioncode
     * @return
     */
    public ResultBean<Map<String, Object>> getAward(String openid,
            Integer activityId) throws Exception {
        ResultBean<Map<String, Object>> result = new ResultBean<>();

        //查询活动配置信息
        ResultBean<Map<String,Object>> hdResult = getHdConfigInfo(activityId);
        if(!hdResult.getIsSuccess()){
            result.setMessage(hdResult.getMessage());
            return result;
        }
        
        Map<String,Object> hdMap=hdResult.getData();
        boolean isOver7Days=(boolean) hdMap.get("isOver7Days");            
        Date hdEnd=(Date) hdMap.get("hdEnd"); 
        
        Map<String, Object> map = new HashMap<>();

        // 查询未领取的奖励
        UserLotteryBll userLotteryBll = new UserLotteryBll();
        List<UserInfoBean> list = userLotteryBll.wishGetUserLotteryByUserCode(activityId, openid);

        StringBuilder sBuilder=new StringBuilder();
        
        boolean isReceve=false;
        map.put("hdEnd", isOver7Days?true:false);
        if (list == null || list.isEmpty()) {
            // 未中奖
            map.put("isAward", false);
        } else {
            map.put("isAward", false);           
            // 中奖     
            for(int i=0;i<list.size();i++){
                UserInfoBean userInfo=list.get(i);
                //1活动时间内没有抽中奖,活动结束后抽中大礼包
                //2活动时间内抽中每日排行奖励,活动结束后抽中大礼包
                //3活动时间内抽中每日排行奖励,活动结束后没有抽中大礼包
                if(userInfo.getTime().getTime() < hdEnd.getTime()){
                    map.put("presentName", userInfo.getPresentName());
                    if(userInfo.getAvailable()==1){
                        map.put("receive", true);
                    }else{
                        map.put("receive", isOver7Days?true:false);
                    }      
                    map.put("isAward", true);           
                    continue;
                }
                sBuilder.append(userInfo.getPresentName()+",");
                if(userInfo.getAvailable()==1){
                    isReceve=true;
                }
            }                
            
            if (sBuilder.length() > 0) {
                map.put("giftName", sBuilder.substring(0, sBuilder.length() - 1));
                if(isOver7Days){
                    map.put("btnGift", false);
                }else{
                    map.put("btnGift", isReceve?false:true);// 隐藏大礼包按钮
                }
            }else{
                map.put("btnGift", false);
                map.put("giftName", null);
            }
        }        

        result.setIsSuccess(true);
        result.setMessage("查询成功");
        result.setData(map);
        return result;
    }

    /**
     * 查询游戏服务器列表
     * 
     * @param netType
     * @param activityId
     * @return
     */
    public ResultBean<List<Map<String, Object>>> getServerList(String netType,
            Integer activityId) throws Exception {
        ResultBean<List<Map<String, Object>>> result = new ResultBean<>();

        //查询活动配置信息
        ResultBean<Map<String,Object>> hdResult = getHdConfigInfo(activityId);
        if(!hdResult.getIsSuccess()){
            result.setMessage(hdResult.getMessage());
            return result;
        }
        
        Map<String,Object> hdMap=hdResult.getData();
        boolean isOver7Days=(boolean) hdMap.get("isOver7Days");    
              
        if (isOver7Days) {
            result.setMessage("活动已经结束,敬请期待下次活动");
            return result;
        }

        // 从缓存查询区服列表
        List<Value> list = getServerListFromCache(netType);
        if (list.isEmpty()) {
            result.setMessage("获取失败");
            return result;
        }

        List<Map<String, Object>> serverList = new ArrayList<>();
        Iterator<Value> iterator = list.iterator();
        while (iterator.hasNext()) {
            Value serverInfo = iterator.next();

            Map<String, Object> map = new HashMap<>();
            map.put("code", serverInfo.getCode());
            map.put("serverName", serverInfo.getServerName());
            serverList.add(map);
        }

        result.setIsSuccess(true);
        result.setData(serverList);
        result.setMessage("查询成功");
        return result;
    }

    /**
     * 领取虚拟奖励
     * 
     * @param account
     *            社区账号
     * @param openid
     *            微信用户昵称
     * @param netType
     *            大区
     * @param serverId
     *            服务器ID
     * @param ipAddr
     *            玩家的IP地址
     * @return
     */
    public ResultBean<String> receiveVirtualPresent(Integer activityId,
            String account, String openid, String netType, Integer serverId,
            String ip) throws Exception {
        ResultBean<String> result = new ResultBean<>();

        ResultBean<String> serverResult = checkAndUpdateSendPresentLog(
            activityId, openid, account, netType, serverId, ip, Constants.VIRTUAL_PRESENT_TYPE);
        if (!serverResult.getIsSuccess()) {
            result.setMessage(serverResult.getMessage());
            return result;
        }

        result.setMessage("领取奖励成功");
        result.setIsSuccess(true);
        return result;
    }

    /**
     * 领取实物奖品
     * 
     * @param activityId
     *            活动编号
     * @param account
     *            账号
     * @param openid
     *            微信用户
     * @param netType
     *            大区
     * @param serverId
     *            游戏服务器ID
     * @param ip
     *            ip地址
     * @return
     */
    public ResultBean<String> receiveRealPresent(Integer activityId,
            String account, String openid, String netType, Integer serverId,
            String ip, LotteryPrizesVO vo) throws Exception {
        ResultBean<String> result = new ResultBean<>();

        ResultBean<String> serverResult = checkAndUpdateSendPresentLog(
            activityId, openid, account, netType, serverId, ip, Constants.REAL_PRESENT_TYPE);
        if (!serverResult.getIsSuccess()) {
            result.setMessage(serverResult.getMessage());
            return result;
        }

        SqlSession session = getSession();
        // 保存用户的领取信息
        IActionPrizesAddressBLL addressBll = new ActionPrizesAddressBLLImpl();
        boolean flag = addressBll.putAddress(vo, session);
        if (!flag) {
            session.rollback();
            result.setMessage("保存领奖信息失败");
            return result;
        }

        session.commit();

        result.setMessage("保存领奖信息成功");
        result.setIsSuccess(true);
        return result;
    }

    /**
     * 查询玩家的昵称，历史最高得分，历史最高排名
     * 
     * @param openid
     * @param actioncode
     * @return
     */
    public ResultBean<Map<String, Object>> getShare(String openid) {
        ResultBean<Map<String, Object>> result = new ResultBean<>();

        Map<String, Object> map = new HashMap<>();

        // 查询微信昵称
        ResultBean<String> nickNameResult = getNickNameFromCache(openid);
        if (!nickNameResult.getIsSuccess()) {
            result.setMessage(nickNameResult.getMessage());
            return result;
        }

        map.put("nickName", nickNameResult.getData());

        // 查询历史最高得分
        Integer historyScore = fightBrickGameBll.getHighScore(openid);
        map.put("historyScore", historyScore);

        // 查询今天最高排名
        FightBrickGameBean model = fightBrickGameBll.getRank(openid);

        // 查询历史最高排名
        Integer historyRank = fightBrickGameBll.getHignRank(openid);
        historyRank = historyRank == null ? 0 : historyRank;

        if (model == null) {
            map.put("historyRank", historyRank);
        } else {
            if (model.getRank() >= historyRank) {
                map.put("historyRank", historyRank == 0 ? model.getRank() : historyRank);
            } else {
                map.put("historyRank", model.getRank());
            }
        }

        result.setIsSuccess(true);
        result.setMessage("查询成功");
        result.setData(map);
        return result;
    }
    
    /**
     * 
      * <p>
      *    记录分享日志
      * </p>
      *
      * @action
      *    yangteng 2017年4月7日 下午1:18:53 描述
      *
      * @param activityId
      * @param openid
      * @param ip
      * @return Object
     */
    public ResultBean<String> addShareLog(Integer activityId, String openid, String ip) {
        ResultBean<String> result=new ResultBean<String>();
        
        Integer number=fightBrickGameBll.addShareLog(activityId, openid, ip);
        if(number<=0){
            result.setMessage("记录分享日志失败");
            return result;
        }
        
        result.setIsSuccess(true);
        result.setMessage("记录分享日志成功");
        return result;
    }    
    
    /**
     * 领取奖品之前操作
     * 
     * @param activityId
     *            活动编号
     * @param openid
     *            微信用户
     * @param account
     *            账号
     * @param netType
     *            大区
     * @param serverId
     *            游戏服务器ID
     * @param ip
     *            IP地址
     * @return
     */
    private ResultBean<String> checkAndUpdateSendPresentLog(
            Integer activityId, String openid, String account, String netType,
            Integer serverId, String ip, String presentType) throws Exception {
        ResultBean<String> result = new ResultBean<>();

        //查询用户是否有角色
        ResultBean<ServerInfoBean> roleResult=isExistRole(netType, serverId, account);
        if(!roleResult.getIsSuccess()){
            result.setMessage(roleResult.getMessage());
            return result;
        }              

        //查询活动配置信息
        ResultBean<Map<String,Object>> hdResult = getHdConfigInfo(activityId);
        if(!hdResult.getIsSuccess()){
            result.setMessage(hdResult.getMessage());
            return result;
        }
        
        Map<String,Object> hdMap=hdResult.getData();
        boolean isOver7Days=(boolean) hdMap.get("isOver7Days");          
        Date hdEnd=(Date) hdMap.get("hdEnd");
        
        ServerInfoBean serverInfo=roleResult.getData();       

        SqlSession session = null;
        String lockKey = String.format(Constants.DISTRIBUTED_LOCK_TEMPLATE,
            openid);
        try (DistributedLock lock = new DistributedLock(lockKey)) {

            lock.weakLock(Constants.DISTRIBUTED_LOCK_TIME,
                Constants.DISTRIBUTED_LOCK_WAIT_TIME); // 开启分布式锁

            // 查询未领取的奖励
            UserLotteryBll userLotteryBll = new UserLotteryBll();
            List<UserInfoBean> list = userLotteryBll
                    .wishGetUserLotteryByUserCode(activityId, openid);
            boolean isAward = true;
            if (list == null || list.isEmpty()) {
                isAward = false;
            }

            // 活动结束7天后
            if (isOver7Days) {
                result.setMessage(isAward ? "活动领奖时间已结束,敬请期待下次活动"
                        : "活动已结束,敬请期待下次活动");
                return result;
            }
            
            //活动时间内没有中奖
            if(!isAward){
                result.setMessage("很遗憾,您还未中奖");
                return result;
            }
            
            List<UserInfoBean> notReceivePrizeList=new ArrayList<>();
            
            if(Constants.VIRTUAL_PRESENT_TYPE.equals(presentType)){
                
                UserInfoBean userInfo=null;
                for(UserInfoBean item : list){
                    if(item.getTime().getTime()<hdEnd.getTime()){
                        userInfo=item;
                        break;
                    }
                }
                
                if(userInfo==null){
                    //活动结束中大礼包,活动时间内未中奖
                    result.setMessage("很遗憾,您未抽中该奖励,不能领奖");
                    return result;
                }

                if(userInfo.getAvailable()==1){
                    result.setMessage("您已经领取该奖励");
                    return result;
                }
                notReceivePrizeList.add(userInfo);
            }
            
            if(Constants.REAL_PRESENT_TYPE.equals(presentType)){
                
                boolean isReceive=false;
                for(UserInfoBean item : list){
                    if(item.getTime().getTime()<hdEnd.getTime()){
                        continue;
                    }
                    notReceivePrizeList.add(item);
                    if(item.getAvailable()==1)
                        isReceive=true;
                }
                
                if(isReceive){
                    result.setMessage("您已经领取该奖励");
                    return result; 
                }
                
                if(notReceivePrizeList.isEmpty()){
                    //活动时间内获得奖品,活动结束后没有中大礼包
                    result.setMessage("很遗憾,您未抽中该奖励,不能领奖");
                    return result;
                }                  
            }                               
            
            session = getSession();
            NewUserLotteryBll lotteryBll = new NewUserLotteryBll();
            
            for(int i=0;i<notReceivePrizeList.size();i++){
                UserInfoBean userInfo = notReceivePrizeList.get(i);
                // 更新用户的发奖日志信息
                initUserInfo(serverId, serverInfo.getValue().getServerName(), ip, account, userInfo);
                Integer row=lotteryBll.updateSendPresentLog(userInfo, session);
                if(row<=0){
                    session.rollback();
                    result.setMessage("更新用户的发奖日志信息失败");
                    return result;
                }
            }
            session.commit();

        } catch (Exception ex) {
            if(session!=null)
                session.rollback();
            logger.error("beforeReceivePresent调用分布式事物锁异常,错误消息{}", ex);
            result.setMessage("服务器异常");
            return result;
        }

        result.setIsSuccess(true);
        return result;
    }

    /**
     * 
      * <p>
      *    查询活动配置信息
      * </p>
      *
      * @action
      *    Administrator 2017年3月27日 下午11:18:07 描述
      *
      * @param activityId
      * @return
      * @throws Exception boolean
     */
    public ResultBean<Map<String,Object>> getHdConfigInfo(Integer activityId) throws Exception{
        ResultBean<Map<String,Object>> result= new ResultBean<>();
        
        DefaultHdConfigBLL hdConfigBll = new DefaultHdConfigBLL();
        ActionConfigPO configInfo = hdConfigBll.getConfigEntity(activityId);
        if(configInfo==null){
            result.setMessage("查询活动配置信息失败");
            return result;
        }
        
        Map<String,Object> map= new HashMap<>();
        
        Date hdEnd = configInfo.getHdEnd();
        Date now = new Date();
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // 活动结束7天后
        if (addDate(dateFormat.parse(dateFormat.format(hdEnd)), 8)
                .getTime() <= now.getTime()) {
            map.put("isOver7Days", true);
        }else{
            map.put("isOver7Days", false);
       }
        
        map.put("hdEnd", configInfo.getHdEnd());        
        result.setData(map);
        result.setIsSuccess(true);
        return result;        
    }
    
    /**
     * 
      * <p>
      *    查询用户是否有角色
      * </p>
      *
      * @action
      *    Administrator 2017年3月27日 下午8:01:04 描述
      *
      * @param netType
      * @param serverId
      * @param account
      * @return ResultBean<Map<String,Object>>
     */
    private ResultBean<ServerInfoBean> isExistRole(String netType, Integer serverId, String account) throws Exception{
        ResultBean<ServerInfoBean> result=new ResultBean<>();
        
        // 查询游戏服务器列表
        List<Value> serverList = getServerListFromCache(netType);
        if (serverList.isEmpty()) {
            result.setMessage("查询服务器列表失败");
            return result;
        }

        ServerInfoBean serverInfo = new ServerInfoBean();
        // 验证游戏服务器是否存在
        boolean isExist = false;

        Iterator<Value> iterator = serverList.iterator();
        while (iterator.hasNext()) {
            Value item = iterator.next();
            if (item.getCode().intValue() == serverId.intValue()) {
                isExist = true;
                serverInfo.setValue(item);
                break;
            }
        }

        if (!isExist) {
            result.setMessage("游戏服务器不存在");
            return result;
        }

        // 查询用户是否有角色
        String roleStr = CallWebServiceInsuranceAgent.getRoleInfo(account,
            serverId);
        JSONObject jsonObj = JSONObject.fromObject(roleStr);
        if (jsonObj.getString("IsSuccess").equalsIgnoreCase("false")) {
            result.setMessage("没有查询到角色信息,请重新选择区服");
            return result;
        }
        
        result.setIsSuccess(true);
        result.setData(serverInfo);
        return result;
    }
    
    /**
     * 
      * <p>
      *    初始化中奖日志信息的实体类
      * </p>
      *
      * @action
      *    Administrator 2017年3月27日 下午7:52:39 描述
      *
      * @param serverId
      * @param serverName
      * @param ip
      * @param account
      * @return UserInfoBean
     */
    private void initUserInfo(Integer serverId, String serverName, String ip, String account, UserInfoBean userInfo){
        // 更新用户的发奖日志信息
        userInfo.setGameCode(Constants.GAME_ID);
        userInfo.setServerCode(serverId);
        userInfo.setServerName(serverName);
        userInfo.setIp(ip);
        userInfo.setRemark(account);
    }
    
    /**
     * 获得SqlSession
     * 
     * @return
     */
    private SqlSession getSession() {
        SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
                .getSqlActionDBV2SessionFactory();
        return sqlSessionFactory.openSession();
    }

    /**
     * 从缓存读取服务器列表
     * 
     * @param netType
     * @return
     */
    private List<Value> getServerListFromCache(String netType) throws Exception {

        String key = String.format(Constants.SERVER_LIST_CACHE_TEMPLATE,
            netType);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
            false);

        String str = memcachedClientAdapter.get(key, String.class);
        if (str != null) {
            List<Value> list = mapper.readValue(str, mapper.getTypeFactory()
                    .constructParametricType(ArrayList.class, Value.class));
            return list;
        } else {
            CallWebApiAgent agent = new CallWebApiAgent();
            List<Value> list = agent.getAllServerByNetTypeCode(netType);
            if (list == null || list.isEmpty()) {
                // 获取游戏服务器列表为空
                return new ArrayList<Value>();
            }

            String value = mapper.writeValueAsString(list);
            memcachedClientAdapter.set(key,
                getUntilDayEndSeconds(Constants.SERVER_LIST_CACHE_MINUTE),
                value);

            return list;
        }
    }

    /**
     * 指定日期添加天数
     * 
     * @param date
     * @param day
     * @return
     */
    public Date addDate(Date date, long day) {
        long time = date.getTime(); // 得到指定日期的毫秒数
        day = day * 24 * 60 * 60 * 1000; // 要加上的天数转换成毫秒数
        time += day; // 相加得到新的毫秒数
        return new Date(time); // 将毫秒数转换成日期
    }

    /**
     * 从缓存读取微信用户昵称
     * 
     * @param openid
     * @return
     */
    private ResultBean<String> getNickNameFromCache(String openid) {

        ResultBean<String> result = new ResultBean<String>();

        String key = String.format(Constants.WEIXIN_NICKNAME_CACHE_TEMPLATE,
            openid);
        // 读取缓存昵称
        String nickName = memcachedClientAdapter.get(key, String.class);
        if (nickName == null) {
            // 检测是否关注过微信号
            JSONObject jsonWXUserInfo = att.getWeXinUserInfo("Wd", openid);
            if (!jsonWXUserInfo
                    .containsKey(AttentionDictionary.ATTENTION_SUBSCRIBE)) {
                result.setMessage("无效的openId:" + openid);
                return result;
            } else if (!"1".equals(jsonWXUserInfo
                    .getString(AttentionDictionary.ATTENTION_SUBSCRIBE))) {
                result.setMessage("请先关注问道微信");
                return result;
            } else if (!jsonWXUserInfo
                    .containsKey(AttentionDictionary.ATTENTION_NICK_NAME)) {
                result.setMessage("获取昵称失败");
                return result;
            }

            // 设置缓存昵称,5分钟
            memcachedClientAdapter.add(key,
                Constants.WEIXIN_NICKNAME_CACHE_MINUTE, jsonWXUserInfo
                        .getString(AttentionDictionary.ATTENTION_NICK_NAME));

            result.setIsSuccess(true);
            result.setData(jsonWXUserInfo
                    .getString(AttentionDictionary.ATTENTION_NICK_NAME));
            return result;
        }

        result.setIsSuccess(true);
        result.setData(nickName);
        return result;
    }

    /**
     * 缓存多少分钟
     *
     * @action yangteng 2017年3月16日17:45:02 描述
     * @minute 分钟
     * @return Integer
     */
    private Integer getUntilDayEndSeconds(Integer minute) {

        Date nowDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowDate);
        calendar.add(Calendar.MINUTE, minute);
        Date tempDate;
        tempDate = calendar.getTime();
        return (int) ((tempDate.getTime() - nowDate.getTime()) / 1000);

    }
}
