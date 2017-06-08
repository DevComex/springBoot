/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年1月25日下午3:50:18
 * 版本号：v1.0
 * 本类主要用途叙述：绝世武神分数的实体
 */

package cn.gyyx.action.beans.jswsexchange;

import java.util.Date;

public class JSWSScoreBean {
	// 主键
	private int code;
	// 标识
	private String openId;
	// 分数
	private int score;
	// 建立时间
	private Date creatTime;
	// 更新时间
	private Date updateTime;
	// 类型
	private String type;
	// 昵称
	private String nickName;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Date getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	@Override
	public String toString() {
		return "JSWSScoreBean [code=" + code + ", openId=" + openId
				+ ", score=" + score + ", creatTime=" + creatTime
				+ ", updateTime=" + updateTime + ", type=" + type
				+ ", nickName=" + nickName + "]";
	}

	public JSWSScoreBean(int code, String openId, int score, Date creatTime,
			Date updateTime, String type, String nickName) {
		this.code = code;
		this.openId = openId;
		this.score = score;
		this.creatTime = creatTime;
		this.updateTime = updateTime;
		this.type = type;
		this.nickName = nickName;
	}

	public JSWSScoreBean() {
	}

}
