package cn.gyyx.action.service.insurance;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;

import cn.gyyx.action.beans.insurance.InsureBackInfoBean;
import cn.gyyx.action.beans.insurance.TranferInsurerParaBean;
import cn.gyyx.action.bll.insurance.GyyxGoPayBLL;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.core.security.MD5;

public class FroGYYXGO {
	private static final Logger LOG = GYYXLoggerFactory
			.getLogger(FroGYYXGO.class);
	GyyxGoPayBLL goPayBLL = new GyyxGoPayBLL();
	//检查签名
	public boolean inspectSign (String sign,HttpServletRequest request,String scert){  //秘钥
		//获取全部URL地址
		String [] array = new String [10];
		String url = request.getScheme()+"://"; //请求协议 http 或 https    
		url+=request.getHeader("host");  // 请求服务器    
		url+=request.getRequestURI();     // 工程名      
		LOG.info("请求的所有参数"+request.getRequestURL().toString()+"?"+request.getQueryString());
		LOG.info("请求的所有参数："+request.getQueryString());
		LOG.info("common_param："+request.getParameter("common_param"));
		if(request.getQueryString()!=null) {    //判断请求参数是否为空
		Enumeration en = request.getParameterNames();
		int i=0;
        while (en.hasMoreElements()) {
        	String paramName = (String) en.nextElement();
        	if(paramName.equals("sign")||paramName.equals("sign_type")){
        		continue;
        	}else{
        		array[i] = paramName+"="+request.getParameter(paramName);
        		i++;
        	}
		}
		    GYYXGopayIntService goPay = new GYYXGopayIntService();
		    goPay.quickSort(array,0,array.length-1);
		    String paramsAll = "";
		    for(int j=0;j<array.length;j++){
		    	if(array[j]!=null){
		    		paramsAll += array[j];
					if(array[j+1]!=null){
						paramsAll += "&";
					}
				}
		    }
		    url = paramsAll;
		}
		LOG.info("接受到请求...URL="+url);
		//TODO 回调接口地址
		//TODO 内网测试
		String froAddress =url+"&"+scert;    //加秘钥（正式）  9op0-[=]
		String nowSign = MD5.encode(froAddress);    //MD5加密获取sign
		if(nowSign.equals(sign)){
			return true;
		}else{
			return false;
		}
	}
	public String froGOInterface(HttpServletRequest request) {
		try{
		TranferInsurerParaBean para = new TranferInsurerParaBean();
//		TranferInsurerParaBean para = null;
		LOG.info("开始调用光宇GO异步回调接口...时间："+new Date());
		PayInsurance payInsurance = new PayInsurance();
		int backStatus = 0 ;    //PICC接口状态
		String sign = (String) request.getParameter("sign");//验签
		//TODO 内网测试秘钥...
		String scert = goPayBLL.getInsureKey("insure_key");
		LOG.info("请求GO购物获得秘钥为："+scert);
		if(inspectSign(sign,request,scert)){    //验签通过
			LOG.info("光宇GO异步回调接口签名验证成功...时间："+new Date());
			String tradeStatus = (String) request.getParameter("trade_status");    //交易状态
			String totalPrice = (String) request.getParameter("total_price");    //支付金额
			String tradeNo = (String) request.getParameter("trade_no");    //GO购订单流水
			String outTradeNo = (String) request.getParameter("out_trade_no");    //订单号
		LOG.info("光宇GO购接口异步返回...订单号："+outTradeNo+"....交易状态："+tradeStatus+"....GO购订单流水"+tradeNo+"....支付金额"+totalPrice);	
			goPayBLL.paySuccess(outTradeNo, tradeNo);    //更新GO购订单流水
			if(tradeStatus.toUpperCase().equals("TRADE_SUCCESS")||tradeStatus.toUpperCase().equals("TRADE_FINISHED")){    //GO购付费成功
					LOG.info("订单支付成...订单号："+outTradeNo);
				int circle = goPayBLL.getCircle(outTradeNo);    //获取投保周期
				String payTime =goPayBLL.alterPaySuccOrderInfo(outTradeNo,circle+"");    //修改支付时间，过期时间，订单状态
				para = goPayBLL.selectInsurePara(outTradeNo);    //查询PICC所有参数
				LOG.info("传递PICC保险公司参数"+"*"+para.getCtime()
						+"*"+para.getGamename()+"*"+para.getGameserver()
						+"*"+para.getInsermoney()+"*"+para.getInsureterm()
						+"*"+para.getMobile()+"*"+para.getOrderid()
						+"*"+para.getPreclaims()+"*"+para.getRealname()
						+"*"+para.getRolename());
					InsureBackInfoBean insureBackInfo = payInsurance.requestInsurance(para,payTime);    //PICC投保接口调用
					try{
						backStatus = Integer.parseInt(insureBackInfo.getrInt());    //获取投保状态
					}catch(Exception e){
						LOG.error("PICC返回投保状态信息有误...时间："+new Date());
						LOG.info("PICC返回投保状态信息为："+insureBackInfo.getrInt());
					}
					if(backStatus==1||backStatus==(-201)){    //该订单已经成功投保
						if(backStatus==1){
							goPayBLL.addPICCOrder(insureBackInfo.getData(), outTradeNo);    //添加PICC保单订单号
							LOG.info("PICC投保订单号："+insureBackInfo.getData());
							goPayBLL.alterOrder(outTradeNo, "efforder");
							LOG.info("跟新订单状态为有效保单....订单号："+outTradeNo);
						}else{
							goPayBLL.alterOrder(outTradeNo, "efforder");
							LOG.info("PICC订单已存在...订单号："+outTradeNo);
						}
					}else{
						LOG.info("PICC投保订单未被处理..订单号："+outTradeNo+".未处理原因："+insureBackInfo.getrMsg());
						return "fail";
					}
			}else{
				//TODO 订单失败
				goPayBLL.alterOrder(outTradeNo, "failpay");
				LOG.info("光宇GO购支付超时....");
			}
			LOG.info("调用GO购成功...");
			return "success";
		}
		LOG.info("传过来的签名sign值："+sign);
		LOG.info("签名验证失败...时间："+new Date());
		return "fail";
		}catch(Exception e){
			LOG.error(e.toString());
			return "fail";
		}
	}
	//检查签名
	/**
	 * 
	 * @param sign
	 * @param request
	 * @param scert 密钥
	 * @param domain 域名
	 * @return
	 */
	public boolean inspectSignDomain  (String sign,HttpServletRequest request,String scert,String domain){
		//获取全部URL地址
				String [] array = new String [10];
				String url = request.getScheme()+"://"; //请求协议 http 或 https    
				url+=request.getHeader("host");  // 请求服务器    
				url+=request.getRequestURI();     // 工程名      
				String tempStr = "";
				if(request.getQueryString()!=null) {    //判断请求参数是否为空
					Enumeration en = request.getParameterNames();
					int i=0;
					while (en.hasMoreElements()) {
						String paramName = (String) en.nextElement();
						if(paramName.equals("sign")||paramName.equals("sign_type")){
							continue;
						}else{
							try {
								tempStr =  new String(request.getParameter(paramName).getBytes("iso8859-1"),"utf-8");
							} catch (UnsupportedEncodingException e) {
								LOG.error("理赔接口调用..转换成UTF-8出错");
							}
//							array[i] = paramName+"="+request.getParameter(paramName);
							array[i] = paramName+"="+tempStr;
							i++;
						}
					}
				    GYYXGopayIntService goPay = new GYYXGopayIntService();
				    goPay.quickSort(array,0,array.length-1);
				    String paramsAll = "";
				    for(int j=0;j<array.length;j++){
				    	if(array[j]!=null){
				    		paramsAll += array[j];
							if(array[j+1]!=null){
								paramsAll += "&";
							}
						}
				    }
				    url += "?"+paramsAll;
				}
				LOG.info("接受到请求...URL="+url);
				//TODO 回调接口地址
				String froAddress = "";
				String []tempUrl = url.split(domain);    //去域名
//				String temp = tempUrl[1].replaceAll(":8080", "");
				String []froMd5Address = tempUrl[1].split("&sign_type=");    //去签名，留时间戳
//				String []froMd5Address = temp.split("&sign_type=");    //去签名，留时间戳
				//TODO 内网测试
				froAddress = froMd5Address[0]+scert;    //加秘钥（正式）  9op0-[=]
				String nowSign = MD5.encode(froAddress);    //MD5加密获取sign
				if(nowSign.equals(sign)){
					return true;
				}else{
					return false;
				}
	} 
}
