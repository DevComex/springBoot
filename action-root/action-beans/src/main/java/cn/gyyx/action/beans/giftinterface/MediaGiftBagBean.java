/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年5月17日下午4:06:17
 * 版本号：v1.0
 * 本类主要用途叙述：获取媒体礼包的实体
 */



package cn.gyyx.action.beans.giftinterface;

public class MediaGiftBagBean {
	//数量
	private int number;
    //服务器主键
	private int serverId;
	//礼包名称
	private String pageName;
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getServerId() {
		return serverId;
	}
	public void setServerId(int serverId) {
		this.serverId = serverId;
	}
	public String getPageName() {
		return pageName;
	}
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}
	public MediaGiftBagBean(int number, int serverId, String pageName) {
		this.number = number;
		this.serverId = serverId;
		this.pageName = pageName;
	}
	public MediaGiftBagBean() {
		super();
	}
	
    
}
