/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年5月17日上午10:26:37
 * 版本号：v1.0
 * 本类主要用途叙述：礼物
 */

package cn.gyyx.action.beans.giftinterface;

public class GiftBagBean {
	// 区组
	private String serverName;
	// 时间
	private String dateString;
	// 礼包名称
	private String giftName;
	// 数量
	private int number;
	// 礼包类型
	private String giftType;
	// 小时数
	private int hours;

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	public String getGiftName() {
		return giftName;
	}

	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getGiftType() {
		return giftType;
	}

	public void setGiftType(String giftType) {
		this.giftType = giftType;
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public GiftBagBean(String serverName, String dateString, String giftName,
			int number, String giftType, int hours) {
		this.serverName = serverName;
		this.dateString = dateString;
		this.giftName = giftName;
		this.number = number;
		this.giftType = giftType;
		this.hours = hours;
	}

	public GiftBagBean() {
		super();
	}

	@Override
	public String toString() {
		return "GiftBagBean [serverName=" + serverName + ", dateString="
				+ dateString + ", giftName=" + giftName + ", number=" + number
				+ ", giftType=" + giftType + ", hours=" + hours + "]";
	}

}
