/**
 * --------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：马文斌
 * 联系方式：mawenbin@gyyx.cn 
 * 创建时间：2015年3月11日下午5:52
 * 版本号：v1.0
 * 本类主要用途叙述：祝福信息实体类
 */
package cn.gyyx.action.beans.wd9year;

import java.util.Date;

/**
 * 祝福信息实体类
 * */
public class WishBean{
	//code标识
	private int code;
	//用户ID
	private int userId;
	//祝福语
	private String content;
	//图片URL
	private String pictureURL;
	//小图片路劲
	private String smallPictureURL;
	//审核状态（默认为“未审核”）
	private String checkStatus;
	//发送祝福时间
	private Date wishTime;
	//字符串形式祝福时间
	private String strWishTime;
	

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

	public String getPictureURL() {
		return pictureURL;
	}

	public void setPictureURL(String pictureURL) {
		this.pictureURL = pictureURL;
	}

	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getWishTime() {
		return wishTime;
	}

	public void setWishTime(Date wishTime) {
		this.wishTime = wishTime;
	}

	public String getStrWishTime() {
		return strWishTime;
	}

	public void setStrWishTime(String strWishTime) {
		this.strWishTime = strWishTime;
	}
	
	public String getSmallPictureURL() {
		return smallPictureURL;
	}

	public void setSmallPictureURL(String smallPictureURL) {
		this.smallPictureURL = smallPictureURL;
	}

	/**
	 * 重写equals方法
	 * */
	public boolean equals(Object obj){
		if(obj == null){
			return false;
		}
		if(this == obj){
			return true;
		} 
		if (obj instanceof WishBean) {
		      WishBean wish = (WishBean) obj;
		      return  wish.getCode() == code;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return ((Integer)code).hashCode();
	}
	
}
