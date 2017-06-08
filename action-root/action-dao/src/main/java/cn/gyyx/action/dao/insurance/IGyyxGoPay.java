package cn.gyyx.action.dao.insurance;

import java.util.List;

import cn.gyyx.action.beans.insurance.InsuranceConfigBean;
import cn.gyyx.action.beans.insurance.TranferInsurerParaBean;


public interface IGyyxGoPay {
	//异步支付成功修改状态
	public void paySuccess(String tradeNo,String outTradeNo);
	//判断订单支付状态
	public String isSuccessPay(String outTraeNo);
	//添加PICC投保订单号
	public void addPICCOrder(String piccnum,String outTradeNo);
	//修改支付时间，过期时间，订单状态
	public void alterPaySuccOrderInfo(String outTradeNo,String payTime,String overTime);
	//添加PICC
	//修改订单状态
	public void alterOrder(String outTradeNo,String status);
	//通过订单号查询投保接口所要参数
	public TranferInsurerParaBean selectInsurePara(String outTradeNo);
	//修改订单状态
	public int getCircle(String outTradeNo);
	//获取所有配置信息
	public List<InsuranceConfigBean> getAllConfig();
	//获取GO购秘钥值
	public String getInsureKey(String para);
}
