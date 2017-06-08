/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年3月19日下午1:18:18
 * 版本号：v1.0
 * 本类主要用途叙述：奖品的bean
 */



package cn.gyyx.action.beans.lottery;

public class PrizeBean {
	//奖品主键
	private int code;
	//中文名
	private String chinese;
	//奖品英文名
	private String english;
	//奖品的code
	private int actionCode;
	//是否是虚拟
	private String isReal;
	//与前台互动的代号
	private int num;
	private String pictureUrl;
	private int number;
	private double probability;
	private int isAvailable;
	private int prizeCode;
	
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public double getProbability() {
		return probability;
	}
	public void setProbability(double probability) {
		this.probability = probability;
	}
	public String getPictureUrl() {
		return pictureUrl;
	}
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getIsReal() {
		return isReal;
	}
	public void setIsReal(String isReal) {
		this.isReal = isReal;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getChinese() {
		return chinese;
	}
	public void setChinese(String chinese) {
		this.chinese = chinese;
	}
	public String getEnglish() {
		return english;
	}
	public void setEnglish(String english) {
		this.english = english;
	}
	public int getActionCode() {
		return actionCode;
	}
	public void setActionCode(int actionCode) {
		this.actionCode = actionCode;
	}
	public int getIsAvailable() {
		return isAvailable;
	}
	public void setIsAvailable(int isAvailable) {
		this.isAvailable = isAvailable;
	}
	public int getPrizeCode() {
		return prizeCode;
	}
	public void setPrizeCode(int prizeCode) {
		this.prizeCode = prizeCode;
	}
	public PrizeBean(int code, String chinese, String english, int actionCode,
			String isReal, int num) {
		this.code = code;
		this.chinese = chinese;
		this.english = english;
		this.actionCode = actionCode;
		this.isReal = isReal;
		this.num = num;
	}
	public PrizeBean() {
	}
	
	
	

}
