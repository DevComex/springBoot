/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：xuanwuba-collect
 * @作者：范永良
 * @联系方式：fanyongliang@gyyx.cn
 * @创建时间： 2015年10月14日
 * @版本号：v1.228
 * @本类主要用途描述：任务信息表
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.xwbcreditsluckydraw;

import java.util.Date;

public class MissionBean {

	// 主键
	private int code;
	// 任务名称
	private String missionName;
	// 任务说明
	private String missionState;
	// 对应积分
	private int missionCredits;
	// 任务类型
	private String missionType;
	// 发布时间
	private Date missionPublishTime;
	//String类型
	private String missionPublishTimeStr;
	// 关闭时间
	private Date missionCloseTime;
	// 关闭状态
	private Integer missionCloseStatus;
	// 是否为推荐任务
	private Integer recommendTags;
	private int pageSize;
	private int pageIndex;
	private int rowNum;
	private int uploadNum;
	private int SignNum;
	private String missionUrl;
	private String status;

	public String getMissionPublishTimeStr() {
		return missionPublishTimeStr;
	}

	public void setMissionPublishTimeStr(String missionPublishTimeStr) {
		this.missionPublishTimeStr = missionPublishTimeStr;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getMissionUrl() {
		return missionUrl;
	}

	public void setMissionUrl(String missionUrl) {
		this.missionUrl = missionUrl;
	}

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getRecommendTags() {
		return recommendTags;
	}

	public void setRecommendTags(Integer recommendTags) {
		this.recommendTags = recommendTags;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMissionName() {
		return missionName;
	}

	public void setMissionName(String missionName) {
		this.missionName = missionName;
	}

	public String getMissionState() {
		return missionState;
	}

	public void setMissionState(String missionState) {
		this.missionState = missionState;
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

	@Override
	public String toString() {
		return "MissionBean [code=" + code + ", missionName=" + missionName
				+ ", missionState=" + missionState + ", missionCredits="
				+ missionCredits + ", missionType=" + missionType
				+ ", missionPublishTime=" + missionPublishTime
				+ ", missionCloseTime=" + missionCloseTime
				+ ", missionCloseStatus=" + missionCloseStatus
				+ ", recommendTags=" + recommendTags + ", pageSize=" + pageSize
				+ ", pageIndex=" + pageIndex + ", rowNum=" + rowNum
				+ ", uploadNum=" + uploadNum + ", SignNum=" + SignNum
				+ ", missionUrl=" + missionUrl + "]";
	}

	public void setMissionType(String missionType) {
		this.missionType = missionType;
	}

	public Date getMissionPublishTime() {
		return missionPublishTime;
	}

	public void setMissionPublishTime(Date missionPublishTime) {
		this.missionPublishTime = missionPublishTime;
	}

	public Date getMissionCloseTime() {
		return missionCloseTime;
	}

	public void setMissionCloseTime(Date missionCloseTime) {
		this.missionCloseTime = missionCloseTime;
	}

	public Integer getMissionCloseStatus() {
		return missionCloseStatus;
	}

	public void setMissionCloseStatus(Integer missionCloseStatus) {
		this.missionCloseStatus = missionCloseStatus;
	}

}
