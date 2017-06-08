/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：xuanwuba-creditsLuckyDraw-backstage
 * @作者：mawenbin
 * @联系方式：mawenbin@gyyx.cn
 * @创建时间： 2015年7月7日 下午3:16:53
 * @版本号：v1.204
 * @本类主要用途描述：积分表
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.xwbcreditsluckydraw;

import java.util.Date;

/**
 * 积分表
 * */
public class CreditsBean {
	// 主键
	private int code;
	// 账号名称
	private String account;
	// 常规积分
	private int credits;
	// 类型
	private String type;
	// 获取时间
	private Date enterTime;
	// 查询时间所用字符串
	private String date;
	// 获取时间字符串
	private String enterTimeStr;
	// 分页用的页数
	private int page;
	
	//查询需要
	//开始时间
	String startTime;
	//结束时间
	String endTime;

	public CreditsBean(String account,String type, String startTime, String endTime) {
		super();
		this.account = account;
		this.startTime = startTime;
		this.endTime = endTime;
		this.type = type;
	}

	public CreditsBean() {

	}

	public CreditsBean(String account, int credits, String type, Date enterTime) {
		super();
		this.account = account;
		this.credits = credits;
		this.type = type;
		this.enterTime = enterTime;
	}

	public CreditsBean(String account, String date, String type, int page) {
		this.account = account;
		this.date = date;
		this.type = type;
		this.page = page;
	}

	
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public int getCredits() {
		return credits;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getEnterTime() {
		return enterTime;
	}

	public void setEnterTime(Date enterTime) {
		this.enterTime = enterTime;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getEnterTimeStr() {
		return enterTimeStr;
	}

	public void setEnterTimeStr(String enterTimeStr) {
		this.enterTimeStr = enterTimeStr;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

}
