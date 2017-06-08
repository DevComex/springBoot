package cn.gyyx.action.service.insurance;

import java.util.Date;

import net.sf.json.JSONObject;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.client.RestClient;
import org.slf4j.Logger;

import cn.gyyx.action.beans.insurance.InsureBackInfoBean;
import cn.gyyx.action.beans.insurance.TranferInsurerParaBean;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.core.security.MD5;

public class PayInsurance {
	private static final Logger LOG = GYYXLoggerFactory
			.getLogger(PayInsurance.class);
	/**
	 * 生成请求保险公司URL
	 * @param para
	 * @return
	 */
	public String gumURL(TranferInsurerParaBean para,String payTime){
		String token = "S5C4A0AA9EA2820B232B85A1341";
		String sign = "";
		String ctime = "";
		try{
			ctime = para.getCtime().split(" ")[0];
		}catch(Exception e){
			LOG.error("数据库中插入的时间格式不对....应为'yyyy-MM-dd kk:mm:ss'");
		}
		long ct ;
		String url = "http://www.xnccbx.com/Interface/Handler.ashx?action=publish&myid=1"
				+"&mobile="+para.getMobile()
				+"&realname="+para.getRealname()
				+"&gamename="+para.getGamename()
				+"&gameserver="+para.getGameserver()
				+"&rolename="+para.getRolename()
				+"&insermoney="+para.getInsermoney()
				+"&preclaims="+para.getPreclaims()
				+"&insureterm="+para.getInsureterm()
				+"&paytime="+payTime
				+"&orderid="+para.getOrderid()
				+"&ctime="+ctime;
		Date date = new Date();
		ct = date.getTime()/1000;    //ct时间戳参数
		sign = MD5.encode("publish1"+ct+token);
		LOG.info("生成的PICC投保接口签名sign："+sign);
//		url += "&ct="+ct+"&token="+token+"&sign="+sign;
		url += "&ct="+ct+"&sign="+sign;
		LOG.info("请求PICC投保接口地址："+url);
		return url;
	}
	/**
	 * 按照URL调用保险公司接口
	 * @param para
	 * @return
	 */
	public InsureBackInfoBean requestInsurance(TranferInsurerParaBean para,String payTime){
		InsureBackInfoBean insureBackInfo = new InsureBackInfoBean();
		String url = gumURL(para,payTime);
		String result = "";
		try{
			RestClient client = new RestClient();
			LOG.info("开始请求PICC投保接口...时间："+new Date());
			org.apache.wink.client.Resource resource = client.resource(url);
			ClientResponse response = (resource).get();
			LOG.info("请求PICC投保接口成功返回响应信息...时间："+new Date());
			// 接收返回响应体
			result = response.getEntity(String.class);
			LOG.info("PICC返回JSON结果："+result);
		}catch(Exception e){
			LOG.error("连接PICC投保接口出错...时间："+new Date()+"...错误信息："+e.toString());
			LOG.info(e.toString());
		}
		try{
			JSONObject jsonobject = JSONObject.fromObject(result);
			insureBackInfo.setrInt(jsonobject.getString("rInt"));
			insureBackInfo.setData(jsonobject.getString("Data"));
			insureBackInfo.setrMsg(jsonobject.getString("rMsg"));
		}catch(Exception e){
			LOG.error("PICC投保接口返回非JSON格式...");
			LOG.info("PICC投保接口返回数据:"+result);
			
		}
		return insureBackInfo;
	}
}
