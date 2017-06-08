/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年7月14日下午5:58:22
 * 版本号：v1.0
 * 本类主要用途叙述：处理用户相关请求，包括登陆和注册
 */

package cn.gyyx.action.beans.challenger;

import java.util.Date;

public class ChallengeRelationshipBean {

	private int code;

	private int userId;

	private int dareId;

	private Date createTime;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getDareId() {
		return dareId;
	}

	public void setDareId(int dareId) {
		this.dareId = dareId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "ChallengeRelationshipBean [code=" + code + ", userId=" + userId
				+ ", dareId=" + dareId + ", createTime=" + createTime + "]";
	}

}
