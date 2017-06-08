/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：炫舞吧积分活动
 * @作者：王雷
 * @联系方式：wanglei@gyyx.cn
 * @创建时间： 2015年9月6日
 * @版本号：V1.214
 * @本类主要用途描述：签到业务类
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.bll.xwbcreditsluckydraw;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import cn.gyyx.action.beans.xwbcreditsluckydraw.PageBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.SignInBean;
import cn.gyyx.action.dao.xwbcreditsluckydraw.SignInDAO;
import cn.gyyx.core.Text;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class SignInBll {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(SignInBll.class);
	private SignInDAO signInDao = new SignInDAO();
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	private TimeBLL timeBLL = new TimeBLL();

	/**
	 * 添加中奖或积分兑换记录
	 * */
	public void addSignIn(SignInBean signInBean) {
		try {
			signInBean
					.setSignInDate(format.parse(signInBean.getStrSignInDate()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		signInDao.addSignIn(signInBean);
	}

	/**
	 * 查询签到记录
	 * */
	public List<SignInBean> getSignInByPage(SignInBean signInBean,
			PageBean pageBean) {
		try {
			if (!Text.isNullOrEmpty(signInBean.getStrSignInDate())) {
				signInBean.setSignInDate(format.parse(signInBean
						.getStrSignInDate()));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("signIn", signInBean);
		map.put("page", pageBean);
		List<SignInBean> list = signInDao.getSignInByPage(map);
		List<SignInBean> newList = new ArrayList<SignInBean>();
		for (SignInBean temp : list) {
			temp.setStrSignInDate(format.format(temp.getSignInDate()));
			newList.add(temp);
		}
		return newList;
	}

	/**
	 * 查询签到记录条数
	 * */
	public Integer getSignInCount(SignInBean signInBean) {
		try {
			if (!Text.isNullOrEmpty(signInBean.getStrSignInDate())) {
				signInBean.setSignInDate(format.parse(signInBean
						.getStrSignInDate()));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return signInDao.getSignInCount(signInBean);
	}

	/**
	 * 查找本人本周的签到天数
	 * 
	 * @param signInBean
	 * @return
	 */
	public Integer getWeekSignCount(String account) {
		logger.info("炫舞吧积分活动___查找本人本周的签到天数,参数account：" + account);
		// 获取本周开始和结束时间
		String startTime = getCurrentMonday();
		String endTime = getPreviousSunday();
		logger.info("炫舞吧积分活动___查找本人本周的签到天数,当前天为：" + new Date() + ",周一的日期:"
				+ startTime + ",周日  的日期:" + endTime);
		Integer weekSignCount = signInDao.getSignInCount(new SignInBean(
				startTime, endTime, account));
		logger.info("炫舞吧积分活动___查找本人本周的签到天数,结果为：" + weekSignCount);
		return weekSignCount;
	}

	/**
	 * 查找本人本月的签到天数
	 * @param signInBean
	 * @return
	 */
	public Integer geMonthSignCount(String account){
		logger.info("炫舞吧积分活动___查找本人本周的签到天数,参数account："+account);
		//获取本月开始和结束时间
		String startTime = timeBLL.getMinMonthDate();
		String endTime = timeBLL.getMaxMonthDate();
		logger.info("炫舞吧积分活动___查找本人本周的签到天数,当前天为："+new Date()+",月初的日期:"+startTime+",月末  的日期:"+endTime);
		Integer weekSignCount = signInDao.getSignInCount(new SignInBean(startTime,endTime,account));
		logger.info("炫舞吧积分活动___查找本人本周的签到天数,结果为："+weekSignCount);
		return weekSignCount;
	}
	/**
	 * 是否满勤
	 * @param account
	 * @return true:满勤   false:没有满勤
	 */
	public Boolean isAllMonthSign(String account){
		//本人本月的签到天数
		int signDays = geMonthSignCount(account);
		//本月的天数
		int monthDays = timeBLL.getMonthDays();
		if(signDays == monthDays){
			return true;
		}else{
			return false;
		}
		
	}
	/**
	 * 获得当前日期与本周一相差的天数
	 * 
	 * @return
	 */
	private int getMondayPlus() {
		Calendar cd = Calendar.getInstance();
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
		if (dayOfWeek == 1) {
			return -6;
		} else {
			return 2 - dayOfWeek;
		}
	}

	/**
	 * 获得当前周- 周一的日期
	 * 
	 * @return
	 */
	private String getCurrentMonday() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus);
		Date monday = currentDate.getTime();
		String preMonday = dateFormat.format(monday) + " 00:00:00";
		return preMonday;
	}

	/**
	 * 获得当前周- 周日 的日期
	 * 
	 * @return
	 */
	private String getPreviousSunday() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
		Date monday = currentDate.getTime();
		String preMonday = dateFormat.format(monday) + " 23:59:59";
		return preMonday;
	}

	/**
	 * 查询用户某月签到信息
	 * @author fanyongliang 
	 * @param account
	 * @param firstDay
	 * @param lastDay
	 * @return
	 */
	public List<SignInBean> getSignInInfoBetweenFLDay(String account,
			String firstDay, String lastDay) {
		List<SignInBean> list = signInDao.selectSignInInfoBetweenFLDay(account,
				firstDay, lastDay);
		return list;
	}
}
