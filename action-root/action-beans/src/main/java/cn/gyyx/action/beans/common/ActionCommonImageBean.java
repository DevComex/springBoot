package cn.gyyx.action.beans.common;

import cn.gyyx.action.beans.wdpetdating.WdDatingPet;


public class ActionCommonImageBean {
	/** 
	* 主键
	*/
	private int code;
	/**
	 * 公共表单关联
	 */
	private int formCode;
	
	/** 
	* 活动代码
	*/
	private int actionCode;
	/** 
	* 临时地址
	*/
	private String tempUrl;
	/** 
	* 正式地址
	*/
	private String realUrl;
	private String imgType;
	/** 
	* 图片宽
	*/
	private int imgWidth;
	/** 
	* 图片高
	*/
	private int imgHeight;
	/** 
	* 图片特征值
	*/
	private String imgFeature;
	/** 
	* 图片状态
	*/
	private String status;
	
	private WdDatingPet wdDatingPet;
	
	
	public WdDatingPet getWdDatingPet() {
		return wdDatingPet;
	}

	public void setWdDatingPet(WdDatingPet wdDatingPet) {
		this.wdDatingPet = wdDatingPet;
	}
	public String getImgType() {
		return imgType;
	}
	public void setImgType(String imgType) {
		this.imgType = imgType;
	}
	public int getActionCode() {
		return actionCode;
	}
	public void setActionCode(int actionCode) {
		this.actionCode = actionCode;
	}
	public void setCode(int code){
 		this.code = code;
	}
	public int getCode(){
		return this.code;
	}
	public int getFormCode() {
		return formCode;
	}
	public void setFormCode(int formCode) {
		this.formCode = formCode;
	}
	public void setTempUrl(String tempUrl){
 		this.tempUrl = tempUrl;
	}
	public String getTempUrl(){
		return this.tempUrl;
	}
	public void setRealUrl(String realUrl){
 		this.realUrl = realUrl;
	}
	public String getRealUrl(){
		return this.realUrl;
	}
	public void setImgWidth(int imgWidth){
 		this.imgWidth = imgWidth;
	}
	public int getImgWidth(){
		return this.imgWidth;
	}
	public void setImgHeight(int imgHeight){
 		this.imgHeight = imgHeight;
	}
	public int getImgHeight(){
		return this.imgHeight;
	}
	public void setImgFeature(String imgFeature){
 		this.imgFeature = imgFeature;
	}
	public String getImgFeature(){
		return this.imgFeature;
	}
	public void setStatus(String status){
 		this.status = status;
	}
	public String getStatus(){
		return this.status == null?"未审核":status;
	}

	@Override
	public String toString() {
		return "ActionCommonImageBean [code=" + code + ", formCode=" + formCode
				+ ", actionCode=" + actionCode + ", tempUrl=" + tempUrl
				+ ", realUrl=" + realUrl + ", imgType=" + imgType
				+ ", imgWidth=" + imgWidth + ", imgHeight=" + imgHeight
				+ ", imgFeature=" + imgFeature + ", status=" + status
				+ ", wdDatingPet=" + wdDatingPet + "]";
	}
	
}