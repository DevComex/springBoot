/*
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：马涛 
 * 联系方式：matao@gyyx.cn 
 * 创建时间：2015年7月14日下午6:10:22
 * 版本号：v1.0
 * 本类主要用途叙述：用于连接光宇GO支付
 */

package cn.gyyx.action.beans.insurance;

public class GoPayParamBean {
	
	public static final String BODY="body";
	public static final String BUYERACCOUNT="buyer_account";
	public static final String COMMONPARAM="common_param";
	public static final String NOTIFYURL="notify_url";
	public static final String ORDERTIMEOUT="order_timeout";
	public static final String OUTTRADENO="out_trade_no";
	public static final String PARTNER="partner";
	public static final String RETURNURL="return_url";
	public static final String SUBJECT="subject";
	public static final String SELLERACCOUNT="seller_account";
	public static final String TOTALFEE="total_fee";
	public static final String SHOWURL="show_url";
	private String gyyxGoService;    
	private String bodyValue;    //订单描述
	private String buyerAccountValue;    //用户账号
	private String commonParamValue;    //一般传empty
	private String notifyUrlValue;    //回调接口地址
	private String orderTimeoutValue;    //超时时间
	private String outTradeNoValue;    //订单号
	private String partnerValue;    //商户号
	private String returnUrlValue;    //成功页
	private String subjectValue;    //描述
	private String sellerAccountValue;    //卖家信息
	private String totalFeeValue;    //投保价格
	private String showUrlValue;    //详情页 http://www.gyyx.cn
	private String gPayService;
	public String getgPayService() {
		return gPayService;
	}
	public void setgPayService(String gPayService) {
		this.gPayService = gPayService;
	}
	public String getGyyxGoService() {
		return gyyxGoService;
	}
	public void setGyyxGoService(String gyyxGoService) {
		this.gyyxGoService = gyyxGoService;
	}
	public String getBodyValue() {
		return bodyValue;
	}
	public void setBodyValue(String bodyValue) {
		this.bodyValue = bodyValue;
	}
	public String getBuyerAccountValue() {
		return buyerAccountValue;
	}
	public void setBuyerAccountValue(String buyerAccountValue) {
		this.buyerAccountValue = buyerAccountValue;
	}
	public String getCommonParamValue() {
		return commonParamValue;
	}
	public void setCommonParamValue(String commonParamValue) {
		this.commonParamValue = commonParamValue;
	}
	public String getNotifyUrlValue() {
		return notifyUrlValue;
	}
	public void setNotifyUrlValue(String notifyUrlValue) {
		this.notifyUrlValue = notifyUrlValue;
	}
	public String getOrderTimeoutValue() {
		return orderTimeoutValue;
	}
	public void setOrderTimeoutValue(String orderTimeoutValue) {
		this.orderTimeoutValue = orderTimeoutValue;
	}
	public String getOutTradeNoValue() {
		return outTradeNoValue;
	}
	public void setOutTradeNoValue(String outTradeNoValue) {
		this.outTradeNoValue = outTradeNoValue;
	}
	public String getPartnerValue() {
		return partnerValue;
	}
	public void setPartnerValue(String partnerValue) {
		this.partnerValue = partnerValue;
	}
	public String getReturnUrlValue() {
		return returnUrlValue;
	}
	public void setReturnUrlValue(String returnUrlValue) {
		this.returnUrlValue = returnUrlValue;
	}
	public String getSubjectValue() {
		return subjectValue;
	}
	public void setSubjectValue(String subjectValue) {
		this.subjectValue = subjectValue;
	}
	public String getSellerAccountValue() {
		return sellerAccountValue;
	}
	public void setSellerAccountValue(String sellerAccountValue) {
		this.sellerAccountValue = sellerAccountValue;
	}
	public String getTotalFeeValue() {
		return totalFeeValue;
	}
	public void setTotalFeeValue(String totalFeeValue) {
		this.totalFeeValue = totalFeeValue;
	}
	public String getShowUrlValue() {
		return showUrlValue;
	}
	public void setShowUrlValue(String showUrlValue) {
		this.showUrlValue = showUrlValue;
	}
	
	
	
}
