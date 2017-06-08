/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年5月18日上午10:11:49
 * 版本号：v1.0
 * 本类主要用途叙述：官方新手卡的实体
 */

package cn.gyyx.action.beans.giftinterface;

public class OfficialGiftBagBean {
	// 数量
	private int number;
	// 服务器主键
	private String serverName;
	// 礼包名称
	private String pageName;

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public OfficialGiftBagBean(int number, String serverName, String pageName) {
		this.number = number;
		this.serverName = serverName;
		this.pageName = pageName;
	}

	public OfficialGiftBagBean() {
		super();
	}

}
