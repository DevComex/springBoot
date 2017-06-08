/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：xuanwuba-creditsLuckyDraw-backstage
 * @作者：mawenbin
 * @联系方式：mawenbin@gyyx.cn
 * @创建时间： 2015年7月7日 下午3:16:53
 * @版本号：v1.204
 * @本类主要用途描述：商品信息表
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.xwbcreditsluckydraw;

/**
 * 商品信息
 * */
public class GoodsInfoBean {
	// 主键
	private int code;
	// 商品代码
	private String goodsCode;
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
	private int deleteFlag;
	//邮件内容
	private String mailContent;
	

	public String getMailContent() {
		return mailContent;
	}

	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}

	public int getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}

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

}
