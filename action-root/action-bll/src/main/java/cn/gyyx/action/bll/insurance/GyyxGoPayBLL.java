package cn.gyyx.action.bll.insurance;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;

import cn.gyyx.action.beans.insurance.InsuranceConfigBean;
import cn.gyyx.action.beans.insurance.TranferInsurerParaBean;
import cn.gyyx.action.dao.insurance.GyyxGoPayDAO;
import cn.gyyx.log.sdk.GYYXLoggerFactory;


public class GyyxGoPayBLL {
	private static final Logger LOG = GYYXLoggerFactory
			.getLogger(GyyxGoPayBLL.class);
	private GyyxGoPayDAO goPayDAO = new GyyxGoPayDAO();
	//增加光宇GO购流水号
	public void paySuccess(String outTradeNo,String tradeNo) {
		goPayDAO.paySuccess(tradeNo,outTradeNo);
	}
	//查找订单状态
	public String isSuccessPay(String outTradeNo) {
		return goPayDAO.isSuccessPay(outTradeNo);
	}
	//添加PICC投保订单号
	public void addPICCOrder(String piccnum,String outTradeNo){
		goPayDAO.addPICCOrder(piccnum, outTradeNo);
	}
	//修改支付时间，过期时间，订单状态
	public String alterPaySuccOrderInfo(String outTradeNo,String offset){
		Date payTime = new Date();
		LOG.info("输出支付成功时间 ："+getFormatTime(payTime)+"***订单号为："+outTradeNo);
		String overTime = excuOverTime(payTime,offset);
		LOG.info("输出有效时间 ："+overTime+"***订单号为："+outTradeNo);
		goPayDAO.alterPaySuccOrderInfo(outTradeNo, getFormatTimeM(payTime), overTime);
		LOG.info("成功调用修改时间与状态方法...");
		return getFormatTime(payTime);
	}
	//修改订单状态
	public void alterOrder(String outTradeNo,String status){
		goPayDAO.alterOrder(outTradeNo, status);
	}
	public TranferInsurerParaBean selectInsurePara(String outTradeNo){
		return goPayDAO.selectInsurePara(outTradeNo);
	}
	public int getCircle(String outTradeNo) {
		return goPayDAO.getCircle(outTradeNo);
	}
	public List<InsuranceConfigBean> getAllConfig() {
		List<InsuranceConfigBean> result = new ArrayList<InsuranceConfigBean>();
		result = goPayDAO.getAllConfig();
		return result;
	}
	//查询GO购秘钥
	public String getInsureKey(String para) {
		String result = "";
		result = goPayDAO.getInsureKey(para);
		return result;
	}
	//格式化时间
	public String getFormatTime(Date date){
		String time = "";
		try{
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd+kk:mm:ss");
			time = dateFormat.format(date);
		}catch(Exception e){
			LOG.error("格式化时间出错....");
		}
		return time;
	}
	public String getFormatTimeM(Date date){
		String time = "";
		try{
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
			time = dateFormat.format(date);
		}catch(Exception e){
			LOG.error("格式化时间出错....");
		}
		return time;
	}
	//计算过期时间
	public String excuOverTime(Date date,String offsetStr ){
		int offset = 0 ;
		try{
			offset = Integer.parseInt(offsetStr);
			LOG.info("投保周期："+offset);
		}catch(Exception e){
			LOG.error("传递参数过期月数有问题...不是纯数字格式...");
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, offset);
		Date overTime = calendar.getTime();
		return getFormatTimeM(overTime);
	}
}
