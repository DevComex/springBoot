/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年4月9日上午11:00:36
 * 版本号：v1.0
 * 本类主要用途叙述：奖品相关配置信息的实体
 */

package cn.gyyx.action.beans.exchangescore;

import java.util.Date;

public class PrizeConfigBean {
	// 主键
	private int code;
	// 奖品主键
	private int prizeCode;
	// 增加数量
	private int addNum;
	// 总数量
	private int totalBum;
	// 更新时间
	private Date updateTime;
	// 冷却天数
	private int time;
	// 开始发放时间
	private Date beginTime;
	// 发放总数量
	private int allNum;
	// 每日发放个数
	private int dayBum;
	// 消耗分数
	private int score;
	private String type;
	private String info;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getPrizeCode() {
		return prizeCode;
	}

	public void setPrizeCode(int prizeCode) {
		this.prizeCode = prizeCode;
	}

	public int getAddNum() {
		return addNum;
	}

	public void setAddNum(int addNum) {
		this.addNum = addNum;
	}

	public int getTotalBum() {
		return totalBum;
	}

	public void setTotalBum(int totalBum) {
		this.totalBum = totalBum;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public int getAllNum() {
		return allNum;
	}

	public void setAllNum(int allNum) {
		this.allNum = allNum;
	}

	public int getDayBum() {
		return dayBum;
	}

	public void setDayBum(int dayBum) {
		this.dayBum = dayBum;
	}

	@Override
	public String toString() {
		return "PrizeConfigBean [code=" + code + ", prizeCode=" + prizeCode
				+ ", addNum=" + addNum + ", totalBum=" + totalBum
				+ ", updateTime=" + updateTime + ", time=" + time
				+ ", beginTime=" + beginTime + ", allNum=" + allNum
				+ ", dayBum=" + dayBum + "]";
	}

}
