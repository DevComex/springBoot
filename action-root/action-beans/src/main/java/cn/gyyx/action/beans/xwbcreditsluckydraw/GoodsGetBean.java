/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：xuanwuba-creditsLuckyDraw-backstage
 * @作者：mawenbin
 * @联系方式：mawenbin@gyyx.cn
 * @创建时间： 2015年7月7日 下午3:16:53
 * @版本号：v1.204
 * @本类主要用途描述：中奖兑换记录表
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.xwbcreditsluckydraw;
/**
 * 中奖兑换记录表
 * */
import java.util.Date;

public class GoodsGetBean {
	//主键
	private int code;
	//账号名称
	private String account;
	//大区
	private String domain;
	//所在区服
	private String server;
	//角色名称
	private String roleName;
	//商品代码
	private Integer goodsCode;
	//获取方式
	private String getWay;
	//获取时间
	private Date exchangeDate;
	//字符串形式的获取时间
	private String strExchangeDate;
	// 商品名称
	private String goodsName;
	// 商品图片（链接）
	private String goodsPic;
	// 商品说明
	private String goodsState;
	// 所需积分
	private int creditsCost;
	// 对应价钱
	private int price;
	
	
	
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getGoodsPic() {
		return goodsPic;
	}
	public void setGoodsPic(String goodsPic) {
		this.goodsPic = goodsPic;
	}
	public String getGoodsState() {
		return goodsState;
	}
	public void setGoodsState(String goodsState) {
		this.goodsState = goodsState;
	}
	public int getCreditsCost() {
		return creditsCost;
	}
	public void setCreditsCost(int creditsCost) {
		this.creditsCost = creditsCost;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getStrExchangeDate() {
		return strExchangeDate;
	}
	public void setStrExchangeDate(String strExchangeDate) {
		this.strExchangeDate = strExchangeDate;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getAccount() {
		return account;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public Integer getGoodsCode() {
		return goodsCode;
	}
	public void setGoodsCode(Integer goodsCode) {
		this.goodsCode = goodsCode;
	}
	public String getGetWay() {
		return getWay;
	}
	public void setGetWay(String getWay) {
		this.getWay = getWay;
	}
	public Date getExchangeDate() {
		return exchangeDate;
	}
	public void setExchangeDate(Date exchangeDate) {
		this.exchangeDate = exchangeDate;
	}
	
}
