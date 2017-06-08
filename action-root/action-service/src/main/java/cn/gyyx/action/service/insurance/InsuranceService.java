/**
 * --------------------------------------------------- 
F *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年7月14日下午3:34:59
 * 版本号：v1.0
 * 本类主要用途叙述：问道保险的服务层
 */

package cn.gyyx.action.service.insurance;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.insurance.BlackListBean;
import cn.gyyx.action.beans.insurance.OrderBean;
import cn.gyyx.action.beans.insurance.OrderParameterBean;
import cn.gyyx.action.beans.insurance.ReceiveReparationResultBean;
import cn.gyyx.action.beans.insurance.ReparationBean;
import cn.gyyx.action.bll.insurance.BlackListBLL;
import cn.gyyx.action.bll.insurance.OrderBLL;
import cn.gyyx.action.bll.insurance.OrderParameterBLL;
import cn.gyyx.action.bll.insurance.ReparationBLL;
import cn.gyyx.action.service.agent.CallWebServiceInsuranceAgent;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;

public class InsuranceService {
	// 乾坤锁绑定天数限制
	private int dayB = 7;
	private int phoineDay = 30;
	private BlackListBLL blackListBLL = new BlackListBLL();
	private OrderParameterBLL orderParameterBLL = new OrderParameterBLL();
	private OrderBLL orderBLL = new OrderBLL();
	private ReparationBLL reparationBLL = new ReparationBLL();
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(InsuranceService.class);
	
	/**
	 * 检查此订单是否存在或已失效 
	 * @param orderNum
	 * @return
	 */
	public ReceiveReparationResultBean checkExistOrNoefforder(String orderNum){
		ReceiveReparationResultBean result = new ReceiveReparationResultBean(false,false,"检查此订单是否存在或已失效 未知错误");
		ResultBean<OrderBean> exist = orderBLL.selectByOrderNum(orderNum);  
		logger.info("理赔接口验证订单号为"+orderNum+"的订单是否存在或失效");
		//如果在订单表中查数据失败，则直接返回
		if(!exist.getIsSuccess()){
			result.setAll(false, false, exist.getMessage());
			return result;
		}
		////如果在订单表中数据为null ，则说明没有此条数据
		if(exist.getData()==null){
			result.setAll(true, false,"此订单号不存在");
			logger.info("理赔接口验证订单号为"+orderNum+"的订单不存在");
			return result;
		}
		//如果订单状态为已失效，则直接返回
		if("noefforder ".equals(exist.getData().getStatus())){
			result.setAll(true, false,"此保单已失效");
			logger.info("理赔接口验证订单号为"+orderNum+"的订单失效");
			return result;
		}
		result.setAll(true, true, "");
		logger.info("理赔接口验证订单号为"+orderNum+"的订单存在并且有效失效");
		return result;
	}
	/**
	 * 当接口的状态为处理中的状态时，应该进行的逻辑
	 * @param orderNum
	 * @return
	 */
	public ReceiveReparationResultBean whenReparation(String orderNum ,ReparationBean reparationBean){
		ReceiveReparationResultBean result = new ReceiveReparationResultBean(false,false,"处理处理中状态未知错误");
		ReparationBean reparation = reparationBLL.selectReparationBeanByOrderNum(orderNum);
		//根据订单编号获取理赔表中信息，如果有信息，则说明已经理赔过
		if(reparation != null){
			logger.info("理赔接口处理理赔处理中——订单号为"+orderNum+"的订单已经进行中或进行过理赔");
			result.setAll(true, false, "此订单已经进行中或进行过理赔");
			return result;
		}
		//没有信息的话，则要将订单表的此条信息的状态改为处理中，并向理赔表中插入数据
		boolean flagS = orderBLL.setOrderStatus(orderNum, "reparation");
		logger.info("理赔接口处理理赔处理中——更新"+orderNum+"订单状态为reparation");
		if(!flagS){
			result.setAll(true, false, "内部错误");
			logger.debug("理赔接口处理理赔处理中——更新"+orderNum+"订单状态出错");
			return result;
		}
		logger.info("理赔接口处理理赔处理中——向理赔表中插入数据"+reparationBean.toString());
		boolean flag = reparationBLL.addReparation(reparationBean);
		if(!flag){
			result.setAll(true, false, "内部错误");
			logger.debug("理赔接口处理理赔处理中——增加"+orderNum+"订单号理赔数据出错");
			return result;
		}
		
		result.setAll(true, true, "处理理赔处理中状态成功");
		return result;
	}
	/**
	 * 当接口的状态为理赔处理结束的状态时，应该进行的逻辑
	 * @param orderNum
	 * @return
	 */
	public ReceiveReparationResultBean whenEndreparation(String orderNum ,ReparationBean reparationBean, String resultStatus,Float reparationNum){
		ReceiveReparationResultBean result = new ReceiveReparationResultBean(false,false,"处理理赔成功或失败状态未知错误");
		ReparationBean reparation = reparationBLL.selectReparationBeanByOrderNum(orderNum);
		//根据订单编号获取理赔表中信息，如果没有信息，则说明还没进行理赔处理。
		if(reparation == null){
			logger.info("理赔接口处理其他状态--订单号为"+orderNum+"的订单在reparation表中没有数据，还未进行理赔");
			result.setAll(true, false, "此订单还未进行理赔");;
			return result;
		}
		//获取此订单号的状态
		String status = orderBLL.selectByOrderNum(orderNum).getData().getStatus();
		logger.info("理赔接口处理其他状态--订单号为"+orderNum+"的订单在order表中的状态为"+status);
		if(!"reparation".equals(status)){
			logger.info("理赔接口处理其他状态--订单号为"+orderNum+"的订单状态不是进行中，不能出现理赔结果");
			result.setAll(true, false, "此订单状态不是进行中，不能出现理赔结果");
			return result;
		}
		
		//订单状态改为理赔成功，更改订单状态,增加理赔金额
		if("failreparation_scam".equals(resultStatus)){
			resultStatus = "failreparation";
		}
		logger.info("理赔接口处理其他状态--订单号为"+orderNum+"的订单状态在order表中修改为"+resultStatus);
		boolean flag = orderBLL.setOrderStatus(orderNum, resultStatus);
		logger.info("理赔接口处理其他状态--订单号为"+orderNum+"的订单在reparation表中的理赔金额修改为"+reparationNum);
		reparationBLL.setReparation(orderNum, reparationNum);
		if(!flag){
			result.setAll(true, false, "内部错误");
			logger.debug("理赔接口处理其他状态--跟新"+orderNum+"订单状态出错");
			return result;
		}
		//如果状态为理赔失败状态还要进行更新理赔说明的操作
		if("failreparation".equals(resultStatus)){
			logger.info("理赔接口处理其他状态--订单号为"+orderNum+"的订单在reparation表中的理赔说明修改为"+reparationBean.getReparationResult());
			boolean f =reparationBLL.setReparationOrderNum(orderNum, reparationBean.getReparationResult());
			if(!f){
				result.setAll(true, false, "内部错误");
				logger.debug("理赔接口处理其他状态--更新"+orderNum+"更新理赔说明失败");
				return result;
			}
		}
		result.setAll(true, true,"");
		return result;
	}

	/**
	 * 判断黑名单中是否存在该用户
	 * 
	 * @param account
	 * @return
	 */
	public boolean isBlackList(String phone,String account) {
		logger.debug(" isBlackList input phone", phone);
		boolean is = false;
		// 获取该认证手机的黑名单信息
		List<BlackListBean>  bean = blackListBLL.getBlackListBeanByAccountOrPhone(phone,account);
		// 该手机黑名单信息不存在
		if (bean == null) {
			is = true;
		}
		if(bean!=null){
			if(bean.size()==0){
				is = true;
			}
		}
		logger.debug(" isBlackList output", is);
		return is;
	}

	/**
	 * 判断乾坤锁是否
	 * 
	 * @param userCode
	 * @return
	 */
	/*public boolean isEkeyDays(int userCode) {
		logger.debug("isEkeyDays input userCode", userCode);
		boolean is = false;
		// 获取当前用户乾坤锁绑定的时间
		Date dateEkeyDate = CallWebServiceInsuranceAgent
				.getEkeyInformationBean(userCode);
		Date now = new Date();
		int day = daysBetween(dateEkeyDate, now);
		// 乾坤锁绑定大于要求天数
		if (day >= dayB) {
			is = true;
		}
		logger.debug(" isEkeyDays output", is);
		return is;
	}*/

	/**
	 * 判断手机是否认证
	 * 
	 * @param userCode
	 * @return
	 */
	public boolean isPhoneAuth(int userCode) {
		boolean is = false;
	
		String status = CallWebServiceInsuranceAgent
				.getUserPhoneStatus(userCode);
			//时间为空 则为未认证
			if(status.equals("3")){
				is= true;
			}
		return is;
	}
	/**
	 * 判断手机是否超过30天
	 * @param userCode
	 * @return
	 */
	public boolean isPhoneDays(int userCode) {
		boolean is = false;
		// 获取当前用户乾手机认证生效的时间
		Date datePhoineDate = CallWebServiceInsuranceAgent
				.getUserPhoneValidateTime(userCode);
		Date now = new Date();
			 
		int day = daysBetween(datePhoineDate, now);
		// 手机锁绑定大于要求天数
		if (day >= phoineDay) {
				is = true;
		}
		 
		return is;
	}

	/**
	 * 判断两个日期相差的天数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public int daysBetween(Date date1, Date date2) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date1);
		long time1 = cal.getTimeInMillis();
		cal.setTime(date2);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 投保的判断
	 * 
	 * @param userCode
	 * @param phone
	 * @return
	 */
	public ResultBean<String> isCondition1(int userCode, String phone,String account) {
		ResultBean<String> resultBean = new ResultBean<String>();
		if (!isBlackList(phone,account)) {
			resultBean.setProperties(false, "该账户有骗保记录，无法投保", "");
			return resultBean;
		} /*else if (!isPhoneDays(userCode) && !isEkeyDays(userCode)) {
			resultBean.setProperties(false, "您的手机认证未达30天，乾坤锁未达7天，无法投保", "");
			return resultBean;
		} */else if (!isPhoneAuth(userCode)) {
			resultBean.setProperties(false, "您的手机未认证", "");
			return resultBean;
		}else if (!isPhoneDays(userCode)) {
			resultBean.setProperties(false, "您的手机认证未达30天", "");
			return resultBean;
		}else{
			resultBean.setProperties(true, "输入手机验证码，进行验证", "");
			return resultBean;
		}
		/*else if (isEkeyDays(userCode)) {
			resultBean.setProperties(true, "输入手机验证码，乾坤锁动态码，进行验证", "");
			return resultBean;
		} else {
			resultBean.setProperties(false, "乾坤锁未达7天，无法投保", "");
			return resultBean;
		}*/
	}

	/**
	 * 理赔条件的判断
	 * 
	 * @param userCode
	 * @param phone
	 * @return
	 */
	public ResultBean<String> isCondition2(int userCode, String phone,String account) {
		ResultBean<String> resultBean = new ResultBean<String>();
		if (!isBlackList(phone,account)) {
			resultBean.setProperties(false, "该账户有骗保记录，无法理赔", "");
			return resultBean;
		}else if(isPhoneDays(userCode)){
			resultBean.setProperties(true, "理赔审核通过", "");
			resultBean.setMessage("手机验证成功");
			return resultBean;
		}
		/*else if (!isPhoneDays(userCode) && !isEkeyDays(userCode)) {
			resultBean.setProperties(false, "您的手机认证未达30天，乾坤锁未达7天，无法理赔", "");
			return resultBean;
		} else if (isPhoneDays(userCode) || isEkeyDays(userCode)) {
			resultBean.setProperties(true, "理赔审核通过", "");
			//验证手机
			if(isPhoneDays(userCode)){
				resultBean.setMessage("手机验证成功");
			}
			//验证乾坤锁
			if(isEkeyDays(userCode)){
				resultBean.setMessage("乾坤锁验证成功");
			}
			//乾坤锁与手机同时成功
			if(isPhoneDays(userCode) && isEkeyDays(userCode)){
				resultBean.setMessage("乾坤锁验证成功,手机验证成功");	
			}
			return resultBean;
		} */else {
			resultBean.setProperties(false, "抱歉，您的手机认证<30天", "");
			return resultBean;
		}
	}
      /**
       * 获取保险的参数信息
       * @return
       */
	public ResultBean<List<OrderParameterBean>> getAllOrderOrderParameterBean() {
		try {
			return new ResultBean<List<OrderParameterBean>>(true, "获取成功",
					orderParameterBLL.getAllOrderParameterBeans());
		} catch (Exception e) {
			// TODO: handle exception
			return new ResultBean<List<OrderParameterBean>>(false, "获取失败", null);
		}
	}
	/**
	 * 生成订单号
	 * @param userCode
	 * @return
	 */
	public String creatOrderNum(int userCode){
		 String a = new SimpleDateFormat ("yyyyMMddHHmmss").format(new Date()); 
		 Random random=new Random();
		 String bString="";
		 for(int i=0;i<5;i++){
			 bString=bString+random.nextInt(10);
			 
		 }
		 String endString=a+""+userCode+""+bString;
		 return endString;
	}
	/***
	 * 增加一个表示cookies
	 * @param request
	 * @param response
	 * @param name
	 */
	public void setStepCookies(HttpServletRequest request,
			HttpServletResponse response,String name) {
		try {
			Cookie cooki;
			cooki = new Cookie(name, "yes");// 用户ID
			cooki.setMaxAge(60 * 60 * 24 );// cookie时间
			cooki.setPath("/"); // 根据个人的不用，在不同功能的路径下创建
			response.addCookie(cooki);
		} catch (Exception e) {
			// TODO: handle exception
			logger.warn("setLoadCookies error", e.getMessage());
		}
	}
	/**
	 * 获取标志cookies
	 * @param request
	 * @param response
	 * @param name
	 * @return
	 */
	public boolean getStepCookies(HttpServletRequest request,
			HttpServletResponse response,String name ) {
		String valueString = "0";
		try {
			Cookie[] cookies = request.getCookies();// 这样便可以获取一个cookie数组
			// 寻找cookies值
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName()
						.equals(name)) {
					valueString = cookies[i].getValue();
					break;
				}
			}
			// 如果不存在cookies新增
			if (valueString.equals("0")) {
				return false;

			} else if (valueString.equals("yes")) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.warn("getLoadCookies error", e.getMessage());
		return false;
		}
		
	}
	/**
	 * IP地址的转换
	 * @param longipString
	 * @return
	 */
	public String turnIp(String longipString){
		long longip=Long.parseLong(longipString);; 
		StringBuffer sb=new StringBuffer(""); 
		sb.append(String.valueOf(longip>>>24));//直接右移24位 
		sb.append("."); 
		sb.append(String.valueOf((longip&0x00ffffff)>>>16));//将高8位置0，然后右移16位 
		sb.append("."); 
		sb.append(String.valueOf((longip&0x0000ffff)>>>8)); 
		sb.append("."); 
		sb.append(String.valueOf(longip&0x000000ff)); 
		return sb.toString();
	}
	
}
