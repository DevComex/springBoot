package cn.gyyx.action.ui.allpk;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.wdallpk.AllPKInfo;
import cn.gyyx.action.bll.wdpk.WDPKBll;
import cn.gyyx.action.service.CaptchaValidate;
import cn.gyyx.action.service.agent.CallWebApiAgent;
import cn.gyyx.action.service.cs2Sign.Timing;
import cn.gyyx.core.auth.SignedUser;
import cn.gyyx.core.auth.UserInfo;


@Controller
@RequestMapping(value = "/wdallpk")
public class WDAllPkController {
	private CallWebApiAgent agent = new CallWebApiAgent();
	private Pattern phonePattern = Pattern.compile("^1[3|4|5|8|7][0-9]\\d{8}$");
	private WDPKBll wdpkBll = new WDPKBll();
	
	
	@RequestMapping(value = "/index")
	public String index() {
		return "wdallpk/index";
	}
	
	@RequestMapping(value = "/checkoutTime")
	public @ResponseBody ResultBean<String> checkoutTime(HttpServletRequest request) {
		ResultBean<String> res = new ResultBean<String>();
		//检查活动时间
		if(checkTime()){
			res.setIsSuccess(false);
			res.setMessage("亲~请关注正确的填写时间");
			return res;
		} else {
			res.setIsSuccess(true);
			res.setMessage(null);
		}
		return res;
		}
	
	@RequestMapping(value = "/sendMessage")
	public @ResponseBody ResultBean<String> sendMessage(HttpServletRequest request,@RequestParam("phoneNumber")String phoneNumber) {
		ResultBean<String> res = new ResultBean<String>();
		//检测电话号码
		if(phoneNumber == null||!phonePattern.matcher(phoneNumber).matches()) {
			res.setIsSuccess(false);
			res.setMessage("电话号码格式错误");
			return res;
		}
		//验证登陆状态
		UserInfo info = SignedUser.getUserInfo(request);
		if(info == null) {
			res.setIsSuccess(false);
			res.setMessage("请先登录");
			return res;
		}
		
			//调用短信接口
			String sendResult = agent.sendMessage(phoneNumber,"WDAllPk",starilize(info.getAccount()));
			if("SUCCESS".equals(sendResult)) {
				res.setIsSuccess(true);
				res.setMessage("信息已发送");
			} else {
				res.setIsSuccess(false);
				res.setMessage(sendResult);
			}
		
		return res;
	}
	
	@RequestMapping(value = "/uploadInfo",method = { RequestMethod.POST })
	public @ResponseBody ResultBean<String> uploadInfo(HttpServletRequest request,AllPKInfo info) {
		ResultBean<String> res = new ResultBean<String>();
		//验证活动时间
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date end = format.parse("2016-11-12 23:59:59");
			Date start = format.parse("2016-11-08 00:00:00");
			Date crt = new Date();
			if(crt.after(end) || crt.before(start)) {
				res.setIsSuccess(false);
				res.setMessage("信息提交时间为2016-11-08 至 2016-11-12");
				return res;
			}
		} catch (ParseException e) {
			res.setIsSuccess(false);
			res.setMessage("内部时间异常");
			return res;
		}
		//验证登陆情况
		UserInfo userInfo = SignedUser.getUserInfo(request);
		if(userInfo == null) {
			res.setIsSuccess(false);
			res.setMessage("请先登录");
			return res;
		} else {
			//验证参赛资格
			if(!wdpkBll.isAccountAvailable(userInfo.getAccount())) {
				res.setIsSuccess(false);
				res.setMessage("您没有参赛资格!");
				return res;
			}
		}
		info.setAccount(userInfo.getAccount());
		String validate = info.validate();//验证参数
		if(!"SUCCESS".equals(validate)) {
			res.setIsSuccess(false);
			res.setMessage(validate);
		} else {
			//验证提交验证码
			CaptchaValidate captchaVd = new CaptchaValidate();
			if(captchaVd.checkCaptcha(info.getCaptcha(), request) != 0) {
			//if(false){
				res.setIsSuccess(false);
				res.setMessage("验证码错误");
			} else {
				//调用接口，验证短信验证码正确性
				if(agent.validateMessageCaptcha(info.getMessageCaptcha(),info.getPhoneNumber(),"WDAllPk")){
					//if(true){
					if(!wdpkBll.isAccountExists(userInfo.getAccount())) {
						//插入数据库
						wdpkBll.addWDPKInfoBean(info);
						res.setIsSuccess(true);
						res.setMessage("你的信息成功提交，若进入8强，则请等待官方人员联系认证。官方不会以任何形式向玩家索取账号密码、手机验证码、密保等信息，谨防诈骗！");
					} else {
						res.setIsSuccess(false);
						res.setMessage("您已经提交过信息了");
					}
				} else {
					res.setIsSuccess(false);
					res.setMessage("短信验证码错误,请重新获取");
				}
			}
		}
		return res;
	}
	@RequestMapping(value = "/isAccountAvailable")
	public @ResponseBody ResultBean<Boolean> isAccountAvailable(HttpServletRequest request) {
		ResultBean<Boolean> res = new ResultBean<Boolean>();
		UserInfo userInfo = SignedUser.getUserInfo(request);
		if(userInfo == null) {
			res.setIsSuccess(false);
			res.setMessage("请先登录");
			return res;
		} else {
			//验证参赛资格
			if(!wdpkBll.isAccountAvailable(userInfo.getAccount())) {
				res.setIsSuccess(false);
				res.setMessage("您没有参赛资格!");
			    return res;
			}else {
				res.setIsSuccess(true);
				res.setMessage("查询成功");
				res.setData(wdpkBll.isAccountAvailable(userInfo.getAccount()));
			 return res;
			}
	     }
	}
	
	
	/**
	 * 检测 活动时间 是否结束
	 * @return
	 */
	private boolean checkTime(){
		Timing time = new Timing("2016-11-08 00:00:00","2016-11-12 23:59:59");
        if(!time.currentAvailable()) {
             
            return true;
        }
		return false;
	}
	/** 
	* 叙述:账号加*<br />
	* @param account 要加星的账号
	* @return String 带星账号
	*/
	private String starilize(String account) {
		char[] src = account.toCharArray();
		char[] c = new char[src.length];
		for(int i = 0;i < c.length;i++) {
			if(i >= 2 && i < c.length - 2) {
				c[i] = '*';
			} else {
				c[i] = src[i];
			}
		}
		return new String(c);
	}
}
