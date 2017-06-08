package cn.gyyx.action.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.UserInfoBean;
import cn.gyyx.action.beans.lottery.UserLotteryBean;
import cn.gyyx.action.beans.wdtombthreeteam.TombReserveInfoBean;
import cn.gyyx.action.beans.wdtombthreeteam.TombServeyInfoBean;
import cn.gyyx.action.beans.wdtombthreeteam.TombVoteInfoBean;
import cn.gyyx.action.bll.wdtombthreeteam.WdTombThreeTeamBLL;
import cn.gyyx.action.service.wdtombthreeteam.WdTombThreeTeamService;
import cn.gyyx.core.auth.SignedUser;
import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.core.captcha.Captcha;
import cn.gyyx.core.security.Aes;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * 盗墓三番队预约调查问卷活动
 * Created by DerCg on 2016/8/30.
 */
@Controller
@RequestMapping("/tombThreeTeam")
public class WdTombThreeTeamController {
	private static final Logger logger = GYYXLoggerFactory.getLogger(WdTombThreeTeamController.class);
    private WdTombThreeTeamService wdTombThreeTeamService = new WdTombThreeTeamService();
    public static final int ACTIONCODE = 398;
    private static final String SKEY = "asdfghjklzxcvbnm";
    private static final String SIV = "qwertyuiasdfghjk";
    private static final String COOKIE_PHONE = "GYYX_ACTIVITY_V2_398_ID";
//    private static final String redirect_url = "http://static.cn114.cn/hello.html";
    private static final String redirect_url = "http://dm3fd.gyyx.cn";

    @RequestMapping("/index")
    public String index(HttpServletRequest request, HttpServletResponse response,Model model) {
    	String referer = request.getHeader("referer");
        if (referer == null || referer.length() <= 0) {
        	return "redirect:"+redirect_url;
        }
        model.addAttribute("returnIndex", redirect_url);
        return "wdThreeTeam/order";
    }
    
	public boolean isLegalUrl(String referer) {
		if (referer == null || referer.length() <= 0) {
        	return false;
        }
		/*if(referer.equals(redirect_url)){
        	return true;
        }
        if (referer.indexOf("actionv2.gyyx.cn") <= 0 || referer.indexOf("tombThreeTeam") <=0) {
        	return false;
        }*/
        
        return true;
	}

    /**
     * 移动端调查问卷首页
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/mobile/index")
    public String mobileIndex(HttpServletRequest request, HttpServletResponse response,Model model) {
    	String referer = request.getHeader("referer");
        if (referer == null || referer.length() <= 0) {
//        	return "redirect:"+redirect_url;
        	return "redirect:"+"http://dm3fd.gyyx.cn/phone/html/mobileIndex.html";
        }
        model.addAttribute("returnIndex", redirect_url);
    	return "wdThreeTeam/orderWap";
    }

    /**
     * 移动端调查问卷页
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/mobile/server")
    public String serverPage(HttpServletRequest request, HttpServletResponse response) {
        String referer = request.getHeader("referer");
        if (referer == null || referer.length() <= 0) {
            return "wdThreeTeam/mobileIndex";
        }
        return "wdThreeTeam/serverPage";
    }

    /**
     * 发送短信验证码
     *
     * @param request
     * @param phoneNum
     * @param verityCode
     * @param response
     * @return
     */
    @RequestMapping(value = "/sendSms", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> sendSms(HttpServletRequest request
            , @RequestParam("phoneNum") String phoneNum
            , @RequestParam("verityCode") String verityCode
            , @RequestParam("channelType") String channelType
            , HttpServletResponse response) {
        ResultBean<String> result = new ResultBean<>();
        if (phoneNum == null || phoneNum.trim().length() <= 0
                || verityCode == null || verityCode.trim().length() <= 0
                || channelType == null || channelType.trim().length() <= 0) {
            result.setIsSuccess(false);
            result.setMessage("参数错误");
            return result;
        }
        if (!phoneNum.matches("^1[0-9]{10}")) {
            result.setIsSuccess(false);
            result.setMessage("手机号格式错误");
            return result;
        }

        if (!new Captcha(request, response).equals(verityCode)) {
            result.setIsSuccess(false);
            result.setMessage("验证码错误");
            return result;
        }
        
        Cookie cookie = new Cookie(COOKIE_PHONE, Aes.encrypt(phoneNum, SKEY, SIV));
		cookie.setMaxAge(60*60*24);
		response.addCookie(cookie);

        result = wdTombThreeTeamService.sendReserveSms(phoneNum, channelType,ACTIONCODE);

        return result;
    }
    
    
    /**
     * 预约
     *
     * @param request
     * @param reserveInfoBean
     * @param veritySmsCode   短信验证码
     * @param response
     * @return
     */
    @RequestMapping(value = "/reserve", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> reserve(HttpServletRequest request
            , @Valid TombReserveInfoBean reserveInfoBean, BindingResult bindingResult, String veritySmsCode, HttpServletResponse response) {
        ResultBean<String> result = new ResultBean<>();
        //获取参数验证信息
        if (bindingResult.hasErrors()) {
            result.setIsSuccess(false);
            result.setMessage(bindingResult.getFieldError().getDefaultMessage());
            return result;
        }
        //cookie查找手机号
        String phoneValue = fromCookieGetPhoneNum(request);
        if(StringUtils.isBlank(phoneValue)){
    		result.setIsSuccess(false);
            result.setMessage("您已经错过预约时间,请关闭浏览器重新操作!");
            return result;
        }
    	reserveInfoBean.setPhoneNum(phoneValue);

        if (veritySmsCode == null || veritySmsCode.trim().length() <= 0 || !veritySmsCode.matches("^(\\w|[0-9]){5}$")) {
            result.setIsSuccess(false);
            result.setMessage("验证码错误");
            return result;
        }
        reserveInfoBean.setActionCode(ACTIONCODE);
        result = wdTombThreeTeamService.addReserveInfo(reserveInfoBean, veritySmsCode);
        if(result.getMessage().equals("预约成功")){
        	String channelType=request.getParameter("channelType");
        	String phoneNum=request.getParameter("phoneNum");
        	request.getSession().setAttribute("phoneNum",phoneNum);
        	request.getSession().setAttribute("channelType",channelType);
        }
        return result;
    }

	/**
	 * @param request
	 * @param result
	 * @return
	 */
	public String fromCookieGetPhoneNum(HttpServletRequest request) {
		String phoneValue = "";
        Cookie [] cookies = request.getCookies();
    	if(cookies!=null&&cookies.length>0){
    		for(Cookie co:cookies){
    			if(co.getName().equals(COOKIE_PHONE)){
    				phoneValue = co.getValue();
    				break;
    			}
    		}
    	}
    	if(phoneValue == null ){
    		return "";
    	}
    	phoneValue = Aes.decrypt(phoneValue, SKEY, SIV);
		return phoneValue;
	}
    //wj v2.0修改跳转手机页面链接
    @RequestMapping("/wj")
    public String wj (HttpServletRequest request, HttpServletResponse response,Model model) {
    	if(!isLegalUrl(request.getHeader("referer"))){
    		return "redirect:"+redirect_url;
    	}
    	
//    	String phoneNum=request.getParameter("phone");
    	String phoneNum=fromCookieGetPhoneNum(request);//cookie获取
    	String channelType=request.getParameter("source");	
       
    	ResultBean result =wdTombThreeTeamService.page(phoneNum,channelType,ACTIONCODE);
    	model.addAttribute("returnIndex", redirect_url);
    	if(result.getMessage().equals("请进行活动预约")){
    		if(channelType.equals("pc")){
    			return "wdThreeTeam/order";
    		}
    		return "wdThreeTeam/orderWap";
    		
    	}else if(result.getMessage().equals("您已参与过抽奖活动")){
    		
    		if(channelType.equals("pc")){
    			return "wdThreeTeam/order";
    		}
    		return "wdThreeTeam/orderWap";
    		
    	}else if (result.getMessage().equals("请先填写调查问卷")){
    		
    		if(channelType.equals("pc")){
    			return "wdThreeTeam/wj";
    		}
    		return "wdThreeTeam/questionWap";
    		
    	}else if (result.getMessage().equals("可以抽奖活动")){
    		
    		if(channelType.equals("pc")){
    			return "wdThreeTeam/lottery";
    		}
    		return "wdThreeTeam/prizeWap";
    		
    	}
    	else {
    			return "wdThreeTeam/order";
    	}
       
    }
    
    //跳转抽奖页
    @RequestMapping("/skipLotteryPage")
    public String skipLotteryPage (HttpServletRequest request, HttpServletResponse response,Model model) {
    	if(!isLegalUrl(request.getHeader("referer"))){
    		return "redirect:"+redirect_url;
    	}
//    	String phoneNum=(String) request.getSession().getAttribute("phoneNum");
    	//cookie查找手机号
        String phoneNum = fromCookieGetPhoneNum(request);
    	String channelType = request.getParameter("source");
    	if (StringUtils.isBlank(channelType)) {
    		channelType = "pc";
		} 
    	// v2.0修改
		/*if (!channelType.equals("pc") && !channelType.equals("mobile")) {
			channelType = "pc";
		}*/
		
    	ResultBean result =wdTombThreeTeamService.page(phoneNum,channelType,ACTIONCODE);
    	model.addAttribute("returnIndex", redirect_url);
    	if(result.getMessage().equals("请进行活动预约")){
    		if(channelType.equals("pc")){
    			return "wdThreeTeam/order";
    		}
    		return "wdThreeTeam/orderWap";
    	}else if(result.getMessage().equals("您已参与过抽奖活动")){
    		if(channelType.equals("pc")){
    			return "wdThreeTeam/order";
    		}
    		return "wdThreeTeam/orderWap";
    	}else if (result.getMessage().equals("请先填写调查问卷")){
    		if(channelType.equals("pc")){
    			return "wdThreeTeam/wj";
    		}
    		return "wdThreeTeam/questionWap";
    	}else if (result.getMessage().equals("可以抽奖活动")){
    		if(channelType.equals("pc")){
    			return "wdThreeTeam/lottery";
    		}
    		return "wdThreeTeam/prizeWap";
    	}
    	else {
    		return "wdThreeTeam/order";
    	}
    }
    
    /**
     * 提交调查问卷内容
     *
     * @return
     */
    @RequestMapping(value = "/servey", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> serveyTable(HttpServletRequest request, @Valid TombServeyInfoBean serveyInfoBean, BindingResult bindingResult, HttpServletResponse response) {
        ResultBean<String> result = new ResultBean<>();
        //获取参数验证信息
        if (bindingResult.hasErrors()) {
            result.setIsSuccess(false);
            result.setMessage(bindingResult.getFieldError().getDefaultMessage());
            return result;
        }
        
        String phoneNum = fromCookieGetPhoneNum(request);
        if(StringUtils.isBlank(phoneNum)){
    		result.setIsSuccess(false);
            result.setMessage("您已经错过填写时间,请关闭浏览器重新操作!");
            return result;
        }
        
    	String channelType = request.getParameter("channelType");
    	if (StringUtils.isBlank(channelType)) {
			result.setMessage("渠道类型不能为空！");
			return result;
		}
		if (!channelType.equals("pc") && !channelType.equals("mobile")) {
			result.setMessage("渠道类型不正确！");
			return result;
		}
    	
        serveyInfoBean.setActionCode(ACTIONCODE);
        serveyInfoBean.setChannelType(channelType);
        serveyInfoBean.setPhoneNum(phoneNum);
        
        
        result = wdTombThreeTeamService.addServeyInfo(serveyInfoBean);
        
        return result;
    }

    /**
     * 抽奖
     *
     * @return
     */
    @RequestMapping(value = "/lottery", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<UserLotteryBean> lottery(HttpServletRequest request
            , @RequestParam("channelType") String channelType
            , HttpServletResponse response) {
    	 String phoneNum = fromCookieGetPhoneNum(request);
    	 
    	 ResultBean<UserLotteryBean> lotteryResult = new ResultBean<>();
                
         if(StringUtils.isBlank(phoneNum)){
        	 lotteryResult.setIsSuccess(false);
        	 lotteryResult.setMessage("您已经错过抽奖时间,请关闭浏览器重新操作!");
             return lotteryResult;
         }
 		if (!channelType.equals("pc") && !channelType.equals("mobile")) {
 			lotteryResult.setMessage("渠道类型不正确！");
 			return lotteryResult;
 		}
     	
        return wdTombThreeTeamService.lottery(phoneNum, channelType,ACTIONCODE);
    }
    
    
    //跳转投票页面PC端
    @RequestMapping("/votePage")
    public String skipVote(HttpServletRequest request, HttpServletResponse response) {

        return "wdThreeTeam/vote";
    }
  //跳转投票页面手机端
    @RequestMapping("/mobilevotePage")
    public String skipMobileVote(HttpServletRequest request, HttpServletResponse response) {

        return "wdThreeTeam/mobilevote";
    }
    
    
    
    /**
     * 
     * 登录接口     需要前台初始化页面的时候，调用一次   判断是否要带登录信息
     * 用于投票功能 
     * 
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    
    public ResultBean<String> login(HttpServletRequest request,@RequestParam("phoneNum") String phoneNum,
              String veritySmsCode, HttpServletResponse response) {
        ResultBean<String> result = new ResultBean<>();
      //获取参数验证信息
       /* if (bindingResult.hasErrors()) {
            result.setIsSuccess(false);
            result.setMessage(bindingResult.getFieldError().getDefaultMessage());
            return result;
        }*/
        
        
      
//      String votterPhone=fromCookieGetPhoneNum(request);
//      //有可能返回值：   1.无手机cookie信息   2.登录成功  3.验证码输入错误 
//      if(StringUtils.isBlank(votterPhone)){
//    	  result.setSuccess(false);
//    	  result.setMessage("不带登录状态");
//    	  return result;
//      }else{
    	   //  这里要判断手机短信验证码是否正确   已经登录       返回 登录成功   积分信息  手机号信息    sum(score)
    	  result=wdTombThreeTeamService.login4Vote(phoneNum,veritySmsCode);   //登录成功
    	  if(result.getMessage().equals("登录成功")){
    		  Cookie cookie = new Cookie(COOKIE_PHONE, Aes.encrypt(phoneNum, SKEY, SIV));
    		  cookie.setMaxAge(60*60*24);
    		  response.addCookie(cookie);
    	  }
//      }
    	 
    	 return  result;
     }
    
    /**
     * checkLoginStatus  页面是否带登录信息  带登录状态，返回投票信息
     */
    @RequestMapping(value = "/checkLoginStatus",method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<Map<String,String>> checkLoginStatus(HttpServletRequest request, HttpServletResponse response) {
//    	ResultBean<List<TombVoteInfoBean>> resultBean = new ResultBean<>();
    	ResultBean<Map<String,String>> resultBean = new ResultBean<>();
          String votterPhone=fromCookieGetPhoneNum(request);
          int sumScore=0;
	      if(StringUtils.isBlank(votterPhone)){
	    	  resultBean.setProperties(false, "不带登录状态", null);
	    	 
	      }else{
	    	  List<TombVoteInfoBean> list = wdTombThreeTeamService.searchPesonalVoteLog(votterPhone,ACTIONCODE);
//	    	  resultBean.setProperties(true, "带登录状态", list);
	    	  Map<String,String> map = new HashMap<String,String>();
	    	  votterPhone= votterPhone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
	    	  for(TombVoteInfoBean lt : list ){
	    		  sumScore+=lt.getScore();
	    	  }
	    	  map.put("sumScore", sumScore+"");
	    	  map.put("votterPhone", votterPhone);
	    	  resultBean.setProperties(true, "带登录状态", map);
	      } 
	    	
	    	  return resultBean;
	      
	     }
	    
    
    /**
     * 注销接口   1.setMaxAge(0)  2.覆盖之前的 3.删除所有   
     */
    @RequestMapping(value="/logout",method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> logout(HttpServletRequest request,HttpServletResponse response){
    	 ResultBean<String> result = new  ResultBean<String>();
    	Cookie[] cookies=request.getCookies();  
    	try{   
	    	if(cookies!=null&&cookies.length>0){
	    		for(Cookie co:cookies){
	    			if(co.getName().equals(COOKIE_PHONE)){
	    				co=new Cookie(COOKIE_PHONE,null);  
	    				co.setMaxAge(0);
	    				response.addCookie(co);
	    				result.setIsSuccess(true);
	    	             result.setMessage("注销成功");
	    				break;
	    			}
	    		}
	    	}
    	}catch(Exception e){ 
    		logger.error("盗墓三番队概念站 WdTombThreeTeamController【注销出现异常】",e);
        	result.setIsSuccess(false);
            result.setMessage("亲! 注销发生异常,请刷新页面");
               
        } 
    	
    	return result;
    }
    
    
    
    
    /**
     * 投票接口 已登录&&当日未投票，才可以投票,
     *  (不满足任何一个条件，直接返回对应信息，满足后，执行投票操作，执行成功/失败后，给予提示信息)
     * 
     */
    @RequestMapping(value = "/vote", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String>  vote(HttpServletRequest request , @RequestParam("voteWho") String voteWho,
    		HttpServletResponse response) {
    	 ResultBean<String>  result = new ResultBean<String>();
    	 String votterPhone=fromCookieGetPhoneNum(request);
         if(StringUtils.isBlank(votterPhone)){
       	  result.setSuccess(false);
       	  result.setMessage("亲！查不到手机登录信息,请先登录");
       	  return result;
         }else if(StringUtils.isBlank(voteWho)){    // 
        	result.setSuccess(false);
      	 	result.setMessage("请求参数有误");
      	 	return result;
         }else{
        	 result= wdTombThreeTeamService.addVoteInfo(votterPhone,ACTIONCODE,voteWho);
       }
        
     	return result;
    }
    
    
    
    /**
     * 查询个人投票记录
     */
    
    @RequestMapping(value = "/searchPesonalVoteLog" ,method = RequestMethod.POST)
    @ResponseBody
      public ResultBean<List<TombVoteInfoBean>> searchPesonalVoteLog (HttpServletRequest request ,HttpServletResponse response){
    	ResultBean<List<TombVoteInfoBean>> resultBean = new ResultBean<>();
    	
    	//从cookie获得用户手机登录信息  应该是一定已经登录的
    	String votterPhone=fromCookieGetPhoneNum(request);
        List<TombVoteInfoBean> list = wdTombThreeTeamService.searchPesonalVoteLog(votterPhone,ACTIONCODE);
		try {
			if (list == null || list.isEmpty()) {
				resultBean.setProperties(false, "亲！查不到您的投票信息", list);
			}else{
				
				resultBean.setProperties(true, "成功", list);
			}
		} catch (Exception e) {
			logger.warn("取不到账户信息");
			logger.warn(e.getMessage());
			resultBean.setProperties(false, "取不到账户信息", null);
		}
		logger.info(" searchPesonalVoteLog resultBean", resultBean);
		return resultBean;
        
        
        
      }
    
    
    
    
    /**
     * 查询所有投票记录   如果投票成功或者失败，需要刷新页面数据
     */
    
    @RequestMapping(value = "/searchAllVoteLog")
    @ResponseBody
      public ResultBean<List<Map<String,Integer>>> searchAllVoteLog (HttpServletRequest request ,HttpServletResponse response){
//    	ResultBean<List<TombVoteInfoBean>> resultBean = new ResultBean<>();
    	ResultBean<List<Map<String,Integer>>> resultBean = new ResultBean<List<Map<String,Integer>>>();
    	try {
    		List<Map<String,Integer>> list = wdTombThreeTeamService.searchAllVoteLog(ACTIONCODE);
//			if (list == null || list.isEmpty()) {
//				resultBean.setIsSuccess(false);
//			}
//			
//			Map<String,String> voteWhoMap = new HashMap<String,String>();
//			//初始化所有声优的投票数    页面声优的代码以0~9表示 voteWhoMap 中key是声优代号，value是获得的投票数
//			for(int i=0;i<=9;i++){
//				voteWhoMap.put(i+"", "0");
//			}
			
			
			//汇总所有声优投票数据
			
			
			
			
			
			resultBean.setProperties(true, "成功", list);
		} catch (Exception e) {
			
			logger.error("盗墓三番队概念站 WdTombThreeTeamController【查询所有投票记录出现异常】",e);
			resultBean.setProperties(false, "获取全部投票信息失败", null);
		}
		logger.info(" searchAllVoteLog resultBean", resultBean);
		return resultBean;
      }
    
    
    /**
     * 投票登录页面发送短信验证码
     * 
     */
    
    @RequestMapping(value = "/sendSmsForVote", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> sendSmsForVote(HttpServletRequest request
            , @RequestParam("phoneNum") String phoneNum
            , @RequestParam("verityCode") String verityCode
            , HttpServletResponse response) {
        ResultBean<String> result = new ResultBean<>();
        if (phoneNum == null || phoneNum.trim().length() <= 0
                || verityCode == null || verityCode.trim().length() <= 0
                ) {
            result.setIsSuccess(false);
            result.setMessage("参数错误");
            return result;
        }
        if (!phoneNum.matches("^1[0-9]{10}")) {
            result.setIsSuccess(false);
            result.setMessage("手机号格式错误");
            return result;
        }

        if (!new Captcha(request, response).equals(verityCode)) {
            result.setIsSuccess(false);
            result.setMessage("验证码错误");
            return result;
        }
        
       
        
        try{
        	result = wdTombThreeTeamService.sendVoteSms(phoneNum);
        }catch(Exception e){
        	logger.error("盗墓三番队概念站 WdTombThreeTeamController【投票发送短信验证码出现异常】",e);
        	result.setProperties(false, "发送失败", null);
        }

        return result;
    }
    
}




























