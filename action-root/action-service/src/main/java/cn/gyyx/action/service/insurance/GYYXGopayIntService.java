/*
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：马涛 
 * 联系方式：matao@gyyx.cn 
 * 创建时间：2015年7月14日下午4:10:22
 * 版本号：v1.0
 * 本类主要用途叙述：用于连接光宇GO支付
 */
package cn.gyyx.action.service.insurance;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;

import cn.gyyx.action.beans.insurance.GoPayParamBean;
import cn.gyyx.action.beans.insurance.InsuranceConfigBean;
import cn.gyyx.action.bll.insurance.GyyxGoPayBLL;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.core.security.MD5;

public class GYYXGopayIntService {
	private GyyxGoPayBLL goPayBLL = new GyyxGoPayBLL();
	private static final Logger LOG = GYYXLoggerFactory
			.getLogger(GYYXGopayIntService.class);
	/**
	 * 连接URL
	 * @param paramBean
	 * @return
	 */
	public String gumURL(GoPayParamBean paramBean){
		String url = "";
		StringBuffer realURL = new StringBuffer("http://api.gpay.gyyx.cn/GateWay?");    //paramBean.getGyyxGoService();
		List<InsuranceConfigBean> configLi = goPayBLL.getAllConfig();
		String configKey = "";
		String configValue = "";
		String insuranceKey = "";
		for(int i=0;i<configLi.size();i++){
			configKey = configLi.get(i).getKey();
			configValue = configLi.get(i).getValue();
			switch(configKey)
			{
			case "seller_account":
				paramBean.setSellerAccountValue(configValue);
				break;
			case "partner":
				paramBean.setPartnerValue(configValue);
				break;
			case "show_url":
				paramBean.setShowUrlValue(configValue);
				break;
			case "insure_key":
				insuranceKey = configValue;
				break;
			case "service":
				paramBean.setgPayService(configValue);
				break;
			}
		}
		LOG.info("输出所有配置信息"+paramBean.getSellerAccountValue()+"*"+paramBean.getPartnerValue()+"*"+paramBean.getShowUrlValue()
				+"*"+insuranceKey+"*"+paramBean.getgPayService());
		realURL.append("body="+paramBean.getBodyValue());
		realURL.append("&buyer_account="+paramBean.getBuyerAccountValue());
		realURL.append("&common_param=Empty");
		realURL.append("&notify_url=http://actionv2.module.gyyx.cn/insurance/froGoPay");
		realURL.append("&order_timeout=45");
		realURL.append("&out_trade_no="+paramBean.getOutTradeNoValue());
		realURL.append("&partner="+paramBean.getPartnerValue());
		realURL.append("&return_url=http://actionv2.gyyx.cn/insurance/goPaySuccess");
		realURL.append("&subject="+paramBean.getBodyValue());    //subject描述与body采用一样的值...
		realURL.append("&seller_account="+paramBean.getSellerAccountValue());
		realURL.append("&total_fee="+paramBean.getTotalFeeValue());
		realURL.append("&show_url="+paramBean.getShowUrlValue());
		realURL.append("&service="+paramBean.getgPayService());
		long timestampPara = new Date().getTime()/1000;
		realURL.append("&timestamp=" + timestampPara);
		url = sortURL(realURL.toString());
		LOG.info("请求光宇GO购排序后url:"+url);
		String tempURL = url + insuranceKey; // 内网
		String MD5URL = MD5.encode(tempURL);
		LOG.info("请求光宇GO购sign:"+MD5URL);
		String[] tempUrlArray = url.split("&timestamp="+timestampPara);
		String tempUrlArray2 = tempUrlArray[0]+tempUrlArray[1];
		String realURLMD5 = "http://api.gpay.gyyx.cn";
		realURLMD5 =  realURLMD5+tempUrlArray2+"&timestamp="+ timestampPara + "&sign_type=md5" + "&sign=" + MD5URL;
		LOG.info("请求光宇GO购url+签名:"+realURLMD5);
		return realURLMD5;
	}
	/**
	 * 拼写URL
	 * @param url
	 * @return
	 */
	public String sortURL(String url){
		StringBuffer realURL = new StringBuffer("/GateWay?");
		String []paramNames = new String [26];    //最多26个参数
		String []paramValues = new String [26];
		String []temp = new String[3];
		String []tempURL = url.split("api.gpay.gyyx.cn/GateWay");    
		String []tempParam = tempURL[1].split("\\?");
		String [] tempValue = tempParam[1].split("&");
		quickSort(tempValue,0,tempValue.length-1);
		for(int i=0;i<tempValue.length;i++){
			temp = tempValue[i].split("=");
			paramNames[i]=temp[0];
			paramValues[i]=temp[1];
		}
		for(int j=0;j<paramNames.length;j++){
			if(paramNames[j]!=null&&paramValues[j]!=null){
				realURL.append(paramNames[j]);
				realURL.append("=");
				realURL.append(paramValues[j]);
				if(paramNames[j+1]!=null){
					realURL.append("&");
				}
			}
		}
		return realURL.toString();
	}
	/**
	 * 快排
	 * @param array
	 * @param first
	 * @param last
	 * @return
	 */
	public int executeCom(String []array,int first,int last){
		int i=first,j=last;
		String standard = array[i];
		while(i<j){
			if(!(array[j]==null)){
				while(i<j&&array[j].compareTo(standard)>=0){
					j--;
				}
				if(i<j){
					array[i]=array[j];
				}
				while(i<j&&array[i].compareTo(standard)<=0){
					i++;
				}
				if(i<j){
					array[j]=array[i];
				}	
			}else{
				j--;
			}
		}
		array[i]=standard;
		return i;
	}
	public void quickSort(String []array,int first,int last) {
		if(first<last){
			int i = executeCom(array,first,last);
			quickSort(array,first,i-1);
			quickSort(array,i+1,last);
		}
	}
}
