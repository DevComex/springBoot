/*------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：Lizepu
 * 联系方式：lizepu@gyyx.cn 
 * 创建时间： 2014年12月15日17:38:44
 * 版本号：v1.0 
 * 本类主要用途描述： 
 * 用于各类活动的图片实体
-------------------------------------------------------------------------*/
package cn.gyyx.action.beans.wdno1pet;

public class ImageBean {
	int imgCode;
	int userCode;
	String imgStatus = "uncheck";
	String imgType;
	String tempUrl;
	String realUrl;
	int imgWidth;
	int imgHeight;
	String imgFeature;
	public int getImgCode() {
		return imgCode;
	}
	public void setImgCode(int imgCode) {
		this.imgCode = imgCode;
	}
	public int getUserCode() {
		return userCode;
	}
	public void setUserCode(int userCode) {
		this.userCode = userCode;
	}
	public String getImgStatus() {
		return imgStatus;
	}
	public void setImgStatus(String imgStatus) {
		this.imgStatus = imgStatus;
	}
	public String getImgType() {
		return imgType;
	}
	public void setImgType(String imgType) {
		this.imgType = imgType;
	}
	public String getTempUrl() {
		return tempUrl;
	}
	public void setTempUrl(String tempUrl) {
		this.tempUrl = tempUrl;
	}
	public String getRealUrl() {
		return realUrl;
	}
	public void setRealUrl(String realUrl) {
		this.realUrl = realUrl;
	}
	public int getImgWidth() {
		return imgWidth;
	}
	public void setImgWidth(int imgWidth) {
		this.imgWidth = imgWidth;
	}
	public int getImgHeight() {
		return imgHeight;
	}
	public void setImgHeight(int imgHeight) {
		this.imgHeight = imgHeight;
	}
	public String getImgFeature() {
		return imgFeature;
	}
	public void setImgFeature(String imgFeature) {
		this.imgFeature = imgFeature;
	}
}
