/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：炫舞吧积分活动
 * @作者：王雷
 * @联系方式：wanglei@gyyx.cn
 * @创建时间： 2015年9月2日
 * @版本号：V1.214
 * @本类主要用途描述：点赞记录实体类
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.xwbcreditsluckydraw;

import java.util.Date;

public class PraiseBean {
	//主键
	int code;
	//用户名
	String account;
	//点赞的素材Code
	Integer materialInfo;
	//点赞日期
	Date praiseDate;
	//是否取消
	Boolean isDelete;
	
	//查询数据条件
	//开始时间
	Date startTime;
	//结束时间
	Date endTime;
	
	
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
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
	public Integer getMaterialInfo() {
		return materialInfo;
	}
	public void setMaterialInfo(Integer materialInfo) {
		this.materialInfo = materialInfo;
	}
	public Date getPraiseDate() {
		return praiseDate;
	}
	public void setPraiseDate(Date praiseDate) {
		this.praiseDate = praiseDate;
	}
	public Boolean getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}
	
	
	
	public PraiseBean(String account, Integer materialInfo) {
		super();
		this.account = account;
		this.materialInfo = materialInfo;
	}
	public PraiseBean(String account, Integer materialInfo, Date praiseDate,
			Boolean isDelete) {
		super();
		this.account = account;
		this.materialInfo = materialInfo;
		this.praiseDate = praiseDate;
		this.isDelete = isDelete;
	}
	public PraiseBean() {
		super();
	}
	
}
