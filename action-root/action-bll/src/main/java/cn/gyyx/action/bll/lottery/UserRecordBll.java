/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年4月16日下午4:24:52
 * 版本号：v1.0
 * 本类主要用途叙述：抽奖活动的bll
 */



package cn.gyyx.action.bll.lottery;

import java.text.ParseException;
import java.util.Date;

import com.ibm.icu.text.SimpleDateFormat;

import cn.gyyx.action.beans.lottery.UserRecordBean;
import cn.gyyx.action.dao.lottery.IUserRecord;
import cn.gyyx.action.dao.lottery.UserRecordDao;

public class UserRecordBll {
	private IUserRecord iDneUserLottery=new UserRecordDao();
	/**
	 * 增加一条用户抽奖记录
	 * @param userbean
	 * @throws Exception 
	 */
	public void add(UserRecordBean userbean) throws Exception{
		userbean.setTime(getDateTurn(userbean.getTime()));
		iDneUserLottery.addUserRecordBean(userbean);
	}
	/**
	 * 根据时间用户查询抽奖记录
	 * @param userCode
	 * @param lotteryTime
	 * @return
	 */
	public UserRecordBean getUserRecordBeanByTime(int userCode,Date lotteryTime,int actionCode,String type){
		return iDneUserLottery.getUserRecordBeanByTime(userCode, lotteryTime, actionCode, type);
	}
	/**\
	 * 时间格式转化
	 * @param date
	 * @return
	 * @throws ParseException 
	 */
	public Date  getDateTurn(Date date) throws Exception{
		  String dateString = new SimpleDateFormat("yyyy-MM-dd").format(date);
		  return  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateString+" 01:00:00");
		
	}
	/**
	 * 根据ip查询记录
	 * @param ip
	 * @param actionCode
	 * @param type
	 * @return
	 */
	public UserRecordBean getUserRecordBeanByIp(String ip,int actionCode,String type){
		return iDneUserLottery.getUserRecordBeanByIp(ip, actionCode, type);
	}

}
