/*------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：马涛
 * 联系方式：matao@gyyx.cn
 * 创建时间：2015年4月7日 下午6:03:24 
 * 版本号：v1.189
 * 本类主要用途描述： 
 * 中奖用户表
-------------------------------------------------------------------------*/
package cn.gyyx.action.beans.wd9yeardatavideo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class VictorInfoBean {
	private int code;
	private int userCode;    //用户编号（用户表的主键）
	private String userAccount;    //用户账号
	private int userRegion;    //用户区组编号
	private String userRegionVar;    //用户区组中文名
	private int userServer;    //用户所在服务器编号
	private String userServerVar;    //用户所在服务器中文名字
	private Date userSubmitDate;    //用户提交答案时间
	private String userSubmitDateStr;    
	private Date userEndingDate;    //该用户称号结束时间
	private String userEndingDateStr;
	private int fractionAndAppellation;    //得分与称号映射表主键
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public int getUserCode() {
		return userCode;
	}
	public void setUserCode(int userCode) {
		this.userCode = userCode;
	}
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public int getUserRegion() {
		return userRegion;
	}
	public void setUserRegion(int userRegion) {
		this.userRegion = userRegion;
	}
	public String getUserRegionVar() {
		return userRegionVar;
	}
	public void setUserRegionVar(String userRegionVar) {
		this.userRegionVar = userRegionVar;
	}
	public int getUserServer() {
		return userServer;
	}
	public void setUserServer(int userServer) {
		this.userServer = userServer;
	}
	public String getUserServerVar() {
		return userServerVar;
	}
	public void setUserServerVar(String userServerVar) {
		this.userServerVar = userServerVar;
	}
	public Date getUserSubmitDate() {
		return userSubmitDate;
	}
	public void setUserSubmitDate(Date userSubmitDate) {
		this.userSubmitDate = userSubmitDate;
	}
	public String getUserSubmitDateStr() {
		return userSubmitDateStr;
	}
	public void setUserSubmitDateStr(String userSubmitDateStr) {
		this.userSubmitDateStr = userSubmitDateStr;
	}
	public Date getUserEndingDate() {
		return userEndingDate;
	}
	public void setUserEndingDate(Date userEndingDate) {
		this.userEndingDate = userEndingDate;
	}
	public String getUserEndingDateStr() {
		return userEndingDateStr;
	}
	public void setUserEndingDateStr(String userEndingDateStr) {
		this.userEndingDateStr = userEndingDateStr;
	}
	public int getFractionAndAppellation() {
		return fractionAndAppellation;
	}
	public void setFractionAndAppellation(int fractionAndAppellation) {
		this.fractionAndAppellation = fractionAndAppellation;
	}
	/**
	 * 格式化时间
	 * @param date 待格式化的时间
	 * @return 格式化好的时间
	 */
	public String formatDate(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
		return format.format(date);
	}
	/**
	 * 获取当天时间的10点
	 * @return 返回Date类型
	 */
	public Date getTodayTen() {
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		cal.setTime(new Date());
		cal.set(Calendar.HOUR_OF_DAY,10);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}
	/**
	 * 获取当天时间的10点
	 * @return 返回String类型
	 */
	public String getTodayTenStr() {
		return formatDate(getTodayTen());
	}
	
}
