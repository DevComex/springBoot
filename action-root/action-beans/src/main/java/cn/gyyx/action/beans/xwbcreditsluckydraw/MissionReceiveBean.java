/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：xuanwuba-collect
 * @作者：范永良
 * @联系方式：fanyongliang@gyyx.cn
 * @创建时间： 2015年10月14日
 * @版本号：v1.228
 * @本类主要用途描述：任务领取记录表
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.xwbcreditsluckydraw;

import java.util.Date;

public class MissionReceiveBean {
	// 主键
	private int code;
	// 所属账号
	private String acount;
	// 是否完成
	private String completeStatus;
	// 任务编号
	private int missionCode;
	// 领取时间
	private Date receiveTime;
	// 完成时间
	private Date finishTime;
	private String finishTimeStr;
	private int missionCredits;
	private String missionType;
	private int uploadNum;
	private int SignNum;
	private int count;
	private String missionName;
	
	
	

	public String getFinishTimeStr() {
		return finishTimeStr;
	}

	public void setFinishTimeStr(String finishTimeStr) {
		this.finishTimeStr = finishTimeStr;
	}

	public String getAcount() {
		return acount;
	}

	public void setAcount(String acount) {
		this.acount = acount;
	}

	public String getMissionName() {
		return missionName;
	}

	public void setMissionName(String missionName) {
		this.missionName = missionName;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getMissionCredits() {
		return missionCredits;
	}

	public void setMissionCredits(int missionCredits) {
		this.missionCredits = missionCredits;
	}

	public String getMissionType() {
		return missionType;
	}

	public void setMissionType(String missionType) {
		this.missionType = missionType;
	}

	public int getUploadNum() {
		return uploadNum;
	}

	public void setUploadNum(int uploadNum) {
		this.uploadNum = uploadNum;
	}

	public int getSignNum() {
		return SignNum;
	}

	public void setSignNum(int signNum) {
		SignNum = signNum;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	
	public String getCompleteStatus() {
		return completeStatus;
	}

	public void setCompleteStatus(String completeStatus) {
		this.completeStatus = completeStatus;
	}

	public int getMissionCode() {
		return missionCode;
	}

	public void setMissionCode(int missionCode) {
		this.missionCode = missionCode;
	}

	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

}
