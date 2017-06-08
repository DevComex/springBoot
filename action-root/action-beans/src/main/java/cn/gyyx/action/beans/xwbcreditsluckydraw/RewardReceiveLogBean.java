/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：炫舞吧积分活动
 * @作者：王雷
 * @联系方式：wanglei@gyyx.cn
 * @创建时间： 2015年9月2日
 * @版本号：V1.214
 * @本类主要用途描述：签到奖励领取日志实体类
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.xwbcreditsluckydraw;

import java.util.Date;

public class RewardReceiveLogBean {
	//主键
	int code;
	//奖励类型
	String rewardType;
	//用户名
	String account;
	//服务器Id
	Integer serverId;
	//性别
	String sex;
	//领取时间
	Date receiveDate;
	
	//查询条件
	//开始时间
	String startTime;
	//结束时间
	String endTime;
	
	
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
	public String getRewardType() {
		return rewardType;
	}
	public void setRewardType(String rewardType) {
		this.rewardType = rewardType;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Integer getServerId() {
		return serverId;
	}
	public void setServerId(Integer serverId) {
		this.serverId = serverId;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Date getReceiveDate() {
		return receiveDate;
	}
	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}
	
	public RewardReceiveLogBean(String rewardType, String account,
			Integer serverId, String sex, Date receiveDate) {
		super();
		this.rewardType = rewardType;
		this.account = account;
		this.serverId = serverId;
		this.sex = sex;
		this.receiveDate = receiveDate;
	}
	public RewardReceiveLogBean() {
		super();
	}
	public RewardReceiveLogBean(String account, String startTime, String endTime) {
		super();
		this.account = account;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
}
