package cn.gyyx.action.service.wdtombthreeteam;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.PrizeBean;
import cn.gyyx.action.beans.lottery.UserLotteryBean;
import cn.gyyx.action.beans.mobile.website.InterfaceReturnBean;
import cn.gyyx.action.beans.wdtombthreeteam.TombLoginInfoBean;
import cn.gyyx.action.beans.wdtombthreeteam.TombReserveInfoBean;
import cn.gyyx.action.beans.wdtombthreeteam.TombServeyInfoBean;
import cn.gyyx.action.beans.wdtombthreeteam.TombVoteInfoBean;
import cn.gyyx.action.bll.jswswxsign.DateTools;
import cn.gyyx.action.bll.wdtombthreeteam.WdTombThreeTeamBLL;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.action.service.agent.CallWebApiAgent;
import cn.gyyx.action.service.mobile.website.CallWebAgentMobile;
import cn.gyyx.action.service.newLottery.LotteryException;
import cn.gyyx.action.service.newLottery.NewlotteryService;
import cn.gyyx.distribute.lock.DLockException;
import cn.gyyx.distribute.lock.DistributedLock;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import net.sf.json.JSONObject;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import com.google.common.base.Throwables;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpSession;

/**
 * Created by DerCg on 2016/8/30.
 */
public class WdTombThreeTeamService {
    private static final Logger logger = GYYXLoggerFactory
            .getLogger(WdTombThreeTeamService.class);
    //活动编号
    private static final int ActionCode = 398;
    private WdTombThreeTeamBLL wdThreeTeamyBLL = new WdTombThreeTeamBLL();
    private CallWebApiAgent callWebApiAgent = new CallWebApiAgent();
    private NewlotteryService lotteryService = new NewlotteryService();

    /**
     * 叙述:在事务机制成熟前暂且这样<br />
     *
     * @return SqlSession
     */
    private SqlSession getSession() {
        SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
                .getSqlActionDBV2SessionFactory();
        return sqlSessionFactory.openSession();
    }

    /**
     * 添加预约信息
     *
     * @param info
     * @param veritySmsCode
     * @return
     */
    public ResultBean<String> addReserveInfo(TombReserveInfoBean info, String veritySmsCode) {
        ResultBean<String> result = new ResultBean<>();

        boolean verifyResult = callWebApiAgent.validateMessageCaptcha(veritySmsCode, info.getPhoneNum(), "WdWildCitySms");
        if (!verifyResult) {
            result.setIsSuccess(false);
            result.setMessage("验证码输入错误");
            return result;
        }
        try (DistributedLock lock = new DistributedLock(ActionCode + info.getPhoneNum() + "addReserveInfo")) {
            //分布式锁
            lock.weakLock(30, 0);
            //检查此人是否预约
            TombReserveInfoBean reserveInfoBean = wdThreeTeamyBLL.selectByPhoneAndActionCode(info.getPhoneNum(), info.getActionCode());
            if (reserveInfoBean != null) {
                result.setIsSuccess(false);
                result.setMessage("该手机号已预约");
                result.setData("HasReserve");
                return result;
            }

            Boolean addResult = wdThreeTeamyBLL.addReserveInfoBean(info);

            if (addResult) {
                result.setIsSuccess(true);
                result.setMessage("预约成功");
                //TODO 发送预约成功短信通知
                sendNoticeMessage(info.getPhoneNum(),"WdWildCityReserve","");

                
                return result;
            } else {
                result.setIsSuccess(false);
                result.setMessage("预约失败");
                return result;
            }
        } catch (DLockException ex) {
            result.setIsSuccess(false);
            result.setMessage("网络异常");
            logger.error("DLockException in WlidCity addReserveInfo:" + ex);
            return result;
        }
    }

    /**
     * 发送预约短信
     *
     * @param phoneNum
     * @param channelType
     * @return
     */
    public ResultBean<String> sendReserveSms(String phoneNum, String channelType,int actionCode) {
        ResultBean<String> resultBean = new ResultBean();
//        ResultBean<String> sendResult= new ResultBean();
        TombReserveInfoBean reserveInfoBean = wdThreeTeamyBLL.selectByPhoneAndActionCode(phoneNum, actionCode);
        TombServeyInfoBean serveyInfoBean = reserveInfoBean ==null ? null:wdThreeTeamyBLL.selectByReserveCode(reserveInfoBean.getCode());
        
        
        if (reserveInfoBean != null && serveyInfoBean == null && !reserveInfoBean.getIsPrize()) { // !null ,null, false
            resultBean.setIsSuccess(false);
//            resultBean.setMessage("该手机号已经预约了哦! 请耐心等待官方微信公布预约结果");
            resultBean.setMessage("该手机号已预约");
            resultBean.setIsSuccess(false);
            return resultBean;
        }else if (reserveInfoBean != null && serveyInfoBean != null && !reserveInfoBean.getIsPrize()){ //!null ,!null ,false
        	resultBean.setMessage("已提交过调查问卷");
            resultBean.setIsSuccess(false);
            return resultBean;
        }else if (reserveInfoBean != null && serveyInfoBean != null  && reserveInfoBean.getIsPrize()){ //!null ,!null ,true
        	resultBean.setMessage("您已参与过抽奖活动");
            resultBean.setIsSuccess(false);
            return resultBean;
        }else{

        	resultBean = sendVerityCodeByPhone(phoneNum);
        }
        return resultBean;
    }

    /**
     * 抽奖
     * @param phoneNum
     * @param channelType
     * @return
     */
    public ResultBean<UserLotteryBean> lottery(String phoneNum, String channelType,int actionCode) {
        ResultBean<UserLotteryBean> lotteryResult = new ResultBean<>();
        try (DistributedLock lock = new DistributedLock(ActionCode + phoneNum + "lottery")) {
            lock.weakLock(30, 0);
            //1、检查用户是否预约
            TombReserveInfoBean reserveInfoBean = wdThreeTeamyBLL.selectByPhoneAndActionCode(phoneNum, actionCode);
            if (reserveInfoBean == null) {
                lotteryResult.setIsSuccess(false);
                lotteryResult.setMessage("请进行活动预约");
                return lotteryResult;
            }
            //2、检查用户是否已抽奖
            if(reserveInfoBean.getIsPrize()){
                lotteryResult.setIsSuccess(false);
                lotteryResult.setMessage("您已参与过抽奖活动");
                return lotteryResult;
            }

            //3、检查用户是否已填写调查问卷
            TombServeyInfoBean serveyInfoBean = wdThreeTeamyBLL.selectByReserveCode(reserveInfoBean.getCode());
            if (serveyInfoBean == null) {
                lotteryResult.setIsSuccess(false);
                lotteryResult.setMessage("请先填写调查问卷");
                return lotteryResult;
            }

            try (SqlSession session = getSession()) {
                lotteryResult = lotteryService.lotteryByDataBase(ActionCode, reserveInfoBean.getCode(), "byChance", phoneNum, "-", 0, "-", getNoPrizeBean(ActionCode), session);
                session.commit(true);
                if(lotteryResult.getIsSuccess()){
                    //更新中奖状态
                	wdThreeTeamyBLL.updateIsPrizeStatus(reserveInfoBean.getCode());
                    //发送中奖短信通知
                    sendNoticeMessage(phoneNum,"WdWildCityLottery",String.format("%s,%s"
                            ,lotteryResult.getData().getPrizeChinese()
                            ,lotteryResult.getData().getCardCode()));
                }
                return lotteryResult;
            } catch (LotteryException e) {
                logger.warn("LotteryException when WlidCity lottery processing phoneNum:" + phoneNum + ",e:" + e);
            	ResultBean<UserLotteryBean> errorResult = new ResultBean<UserLotteryBean>();
                errorResult.setIsSuccess(false);
                errorResult.setMessage("内部错误");
                return errorResult;
            }
        } catch (DLockException ex) {
            lotteryResult.setIsSuccess(false);
            lotteryResult.setMessage("网络异常");
            logger.error("DLockException in WlidCity lottery:" + ex);
            return lotteryResult;
        }
    }

    /***
     * 纪念奖
     *
     * @param actionCode
     * @return
     */
    private PrizeBean getNoPrizeBean(int actionCode) {
        PrizeBean prizeBean = new PrizeBean();
        prizeBean.setActionCode(actionCode);
        prizeBean.setChinese("谢谢参与");
        prizeBean.setEnglish("thanks");
        prizeBean.setIsReal("thanks");
        prizeBean.setNum(1);
        return prizeBean;
    }

    /**
     * 添加问卷调查信息
     *
     * @param info
     * @return
     */
    public ResultBean<String> addServeyInfo(TombServeyInfoBean info) {
        ResultBean<String> resultBean = new ResultBean();
        try (DistributedLock lock = new DistributedLock(ActionCode + info.getPhoneNum() + "addServeyInfo")) {
            //分布式锁
            lock.weakLock(30, 0);
            //1、检查用户是否预约
            TombReserveInfoBean reserveInfoBean = wdThreeTeamyBLL.selectByPhoneAndActionCode(info.getPhoneNum(), info.getActionCode());
            if (reserveInfoBean == null) {
                resultBean.setIsSuccess(false);
                resultBean.setMessage("请进行活动预约");
                resultBean.setData("NoReserve");
                return resultBean;
            }
            //2、检查用户是否已填写调查问卷
            TombServeyInfoBean serveyInfoBean = wdThreeTeamyBLL.selectByReserveCode(reserveInfoBean.getCode());
            if (serveyInfoBean != null) {
                resultBean.setIsSuccess(false);
                resultBean.setMessage("已提交过调查问卷");
                return resultBean;
            }

            info.setReserveCode(reserveInfoBean.getCode());

            //3、添加调查问卷信息
            Boolean addResult = wdThreeTeamyBLL.addServeyInfoBean(info);
            resultBean.setIsSuccess(addResult);
            resultBean.setMessage(addResult?"调查问卷提交成功":"调查问卷提交失败，请重试");

           return resultBean;
        } catch (DLockException ex) {
            resultBean.setIsSuccess(false);
            resultBean.setMessage("网络异常");
            logger.error("DLockException in TombThreeTeam addServeyInfo:" + ex);
            return resultBean;
        }
    }


    /**
     * 发送短信验证码
     * @param phoneNum
     * @return
     */
    private ResultBean<String> sendVerityCodeByPhone(String phoneNum) {
        logger.debug("start CAPTCHA :");
        ResultBean<String> resultBean = new ResultBean<>();
        CallWebAgentMobile callWeb = new CallWebAgentMobile();
        String domain = "http://interface.message.gyyx.cn";
        String projectName = "/v1/Send/VerifyCode/Phone/";
        TreeMap<String, String> map = new TreeMap<String, String>();
        map.put("phone", phoneNum);
        map.put("sourceType", "WdWildCitySms");
        map.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        ResultBean<InterfaceReturnBean> result = callWeb.post(callWeb.getSign(domain, projectName, "123456", map), map);
        JSONObject json = JSONObject.fromObject(result.getData().getMessage());

        resultBean.setProperties(result.getIsSuccess(), json.getString("Message"), json.getJSONObject("Data").getString("CountDownTime"));

        logger.debug("CAPTCHA message:" + result.getData().getMessage());
        logger.debug("finished CAPTCHA ! " + resultBean.getIsSuccess() + "--" + resultBean.getMessage() + "--" + resultBean.getData());
        return resultBean;
    }

    /**
     * 叙述:发送短信通知<br />
     *
     * @param phoneNumber 手机号
     * @param type        类型
     * @return ResultBean<String> 代表短信发送结果
     * @Param messageParam 短信参数 一般是带星号的账号名
     */
    public ResultBean<String> sendNoticeMessage(String phoneNumber, String type, String messageParam) {
        logger.debug("start sendNoticeMessage :");
        ResultBean<String> resultBean = new ResultBean<>();
        CallWebAgentMobile callWeb = new CallWebAgentMobile();
        String domain = "http://interface.message.gyyx.cn";
        String projectName = "/v1/Send/Phone";
        TreeMap<String, String> map = new TreeMap<String, String>();
        map.put("phone", phoneNumber);
        map.put("sourceType", type);
        map.put("messageParam", messageParam);
        map.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        ResultBean<InterfaceReturnBean> result = callWeb.post(callWeb.getSign(domain, projectName, "123456", map), map);
        JSONObject json = JSONObject.fromObject(result.getData().getMessage());

        resultBean.setProperties(result.getIsSuccess(), json.getString("Message"), json.getJSONObject("Data").getString("CountDownTime"));

        logger.debug("sendNoticeMessage message:" + result.getData().getMessage());
        logger.debug("finished sendNoticeMessage ! " + resultBean.getIsSuccess() + "--" + resultBean.getMessage() + "--" + resultBean.getData());
        return resultBean;
    }
    
    public ResultBean page(String phoneNum, String channelType,int actionCode) {
        ResultBean result = new ResultBean();
        result.setIsSuccess(false);
    	result.setMessage("网络异常");
        try (DistributedLock lock = new DistributedLock(ActionCode + phoneNum + "page")) {
            lock.weakLock(30, 0);
            //1、检查用户是否预约
            TombReserveInfoBean reserveInfoBean = wdThreeTeamyBLL.selectByPhoneAndActionCode(phoneNum, actionCode);
            if (reserveInfoBean == null) {
            	result.setIsSuccess(false);
            	result.setMessage("请进行活动预约");
                return result;
            }

            //2、检查用户是否已填写调查问卷
            TombServeyInfoBean serveyInfoBean = wdThreeTeamyBLL.selectByReserveCode(reserveInfoBean.getCode());
            if (serveyInfoBean == null) {
            	result.setIsSuccess(false);
            	result.setMessage("请先填写调查问卷");
                return result;
            }
            //3、检查用户是否已抽奖
            if(reserveInfoBean.getIsPrize()){
            	result.setIsSuccess(false);
            	result.setMessage("您已参与过抽奖活动");
            	return result;
            }else if(!reserveInfoBean.getIsPrize()) {
            	result.setIsSuccess(false);
            	result.setMessage("可以抽奖活动");
            	return result;
            }
            

            
            
        } catch (DLockException ex) {
        	result.setIsSuccess(false);
        	result.setMessage("网络异常");
            logger.error("DLockException in WlidCity lottery:" + ex);
            return result;
        }
        return result;
    }

    
    /**
     * 添加投票信息  已登录&&当日未投票，才可以投票
     * @param votePhone
     * @param actioncode
     * @param voteWho
     */
	public ResultBean<String> addVoteInfo(String votterPhone, int actioncode, String voteWho) {
		
		ResultBean<String> result = new ResultBean<String>();
		/*//检查此人是否预约  
		TombReserveInfoBean reserveInfoBean = wdThreeTeamyBLL.selectByPhoneAndActionCode(votterPhone, actioncode);
		if(reserveInfoBean==null){
			result.setIsSuccess(false);
			result.setMessage("无投票资格");
			return result;
		}*/
		
		//检查当天是否投过票   
		String date = DateTools.formatDate(new Date());
		/*Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH)+1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		logger.warn("calendar", calendar);*/
		TombVoteInfoBean tombVoteInfoBean = wdThreeTeamyBLL.selectVoteInfoByDate(votterPhone, actioncode, date);
        if(tombVoteInfoBean !=null){
        	result.setIsSuccess(false);
			result.setMessage("今日二次投票");
			return result;
        }
		
		//当天没有投过票,执行投票操作（添加投票信息）   
		if(tombVoteInfoBean ==null ){
			TombVoteInfoBean voteInfoBean = new TombVoteInfoBean();
			voteInfoBean.setActionCode(ActionCode);
			voteInfoBean.setCreateTime(new Date());
			voteInfoBean.setScore(10);
			voteInfoBean.setVoteWho(voteWho);
			voteInfoBean.setVotterPhone(votterPhone);
			wdThreeTeamyBLL.addVoteInfoBean(voteInfoBean);
			result.setIsSuccess(true);
			result.setMessage("投票成功");
		}else{
			result.setIsSuccess(false);
			result.setMessage("投票失败");
		}
		
		
		return result;
	}

	
	/**
	 * 获取个人所有投票记录信息
	 * @param votterPhone
	 * @param actioncode2
	 * @return 
	 */
	public List<TombVoteInfoBean> searchPesonalVoteLog(String votterPhone, int actioncode) {
		
		List<TombVoteInfoBean>  voteInfoBean =  wdThreeTeamyBLL.selectPesonalVoteLog(votterPhone, actioncode);
		
		
		
		return voteInfoBean;
	}
    
   /**
    * 获取全部投票记录信息
    * 
    */
	public List<Map<String,Integer>> searchAllVoteLog(int actioncode) {
			
		List<Map<String,Integer>>  voteInfoBean =  wdThreeTeamyBLL.selectAllVoteLog(actioncode);
			
			
			
			return voteInfoBean;
	}
	
	
    /**
     * 发送投票短信验证码
     * @param phoneNum
     * @return
     */
    public ResultBean<String> sendVoteSms(String phoneNum) {
        logger.debug("start CAPTCHA :");
        ResultBean<String> resultBean = new ResultBean<>();
        CallWebAgentMobile callWeb = new CallWebAgentMobile();
        String domain = "http://interface.message.gyyx.cn";
        String projectName = "/v1/Send/VerifyCode/Phone/";
        TreeMap<String, String> map = new TreeMap<String, String>();
        map.put("phone", phoneNum);
        map.put("sourceType", "WdTombThreeTeamVoteSms");
        map.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        ResultBean<InterfaceReturnBean> result = callWeb.post(callWeb.getSign(domain, projectName, "123456", map), map);
        JSONObject json = JSONObject.fromObject(result.getData().getMessage());

        resultBean.setProperties(result.getIsSuccess(), json.getString("Message"), json.getJSONObject("Data").getString("CountDownTime"));

        logger.debug("CAPTCHA message:" + result.getData().getMessage());
        logger.debug("finished CAPTCHA ! " + resultBean.getIsSuccess() + "--" + resultBean.getMessage() + "--" + resultBean.getData());
        return resultBean;
    }

    /**
     * 用于投票时登录   
     * @param votterPhone
     * @param veritySmsCode
     */
	public ResultBean<String> login4Vote(String votterPhone, String veritySmsCode) {
		 //验证手机短信验证码
		 ResultBean<String> result = new ResultBean<>();
		 try{
			 boolean verifyResult = callWebApiAgent.validateMessageCaptcha(veritySmsCode, votterPhone, "WdTombThreeTeamVoteSms");
			 if (!verifyResult) {
	            result.setIsSuccess(false);
	            result.setMessage("验证码输入错误");
	            return result;
			 }else{
			 //往表wd_tombthreeteam_loginlog_tb存手机号数据
			   TombLoginInfoBean tombLoginInfoBean = wdThreeTeamyBLL.selectLoginInfo(votterPhone);
			   if(tombLoginInfoBean==null){
				   //执行添加操作
				   TombLoginInfoBean loginInfoBean = new TombLoginInfoBean();
				   loginInfoBean.setActionCode(ActionCode);
				   loginInfoBean.setCreateTime(new Date());
				   loginInfoBean.setVotterPhone(votterPhone);
					wdThreeTeamyBLL.addLoginInfoBean(loginInfoBean);
			   }
	           result.setIsSuccess(true);
	           result.setMessage("登录成功");
	           	
	        	
	        }
		 }catch(Exception e){
			 result.setIsSuccess(false);
			 result.setMessage("登录失败");
			 logger.error("盗墓三番队概念站 WdTombThreeTeamService【login4Vote出现异常】",e);
		 }
		
		return result;
		
	}
    
    
    
    
    
}
