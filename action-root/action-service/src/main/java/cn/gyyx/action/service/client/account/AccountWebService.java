/*************************************************
       Copyright ©, 2016, GY Game
       Author: 王雷
       Created: 2016年-3月-31日
       Note: webService包装方法
************************************************/
package cn.gyyx.action.service.client.account;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;

import org.slf4j.Logger;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.core.prop.SimpleProperties;

public class AccountWebService {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(AccountWebService.class);
	/**
	 * 获取用户是否存在
	 * @param serverIp
	 * @param serverPort
	 * @param accountName :账号
	 * @return resultBean true：用户存在
	 * @return resultBean false：可能用户不存在，可能参数错误，或者其他原因（Message中有说明）
	 */
	public ResultBean<String> checkoutAccountExist(String serverIp, String serverPort, String accountName){
		long start = System.currentTimeMillis();
		logger.info("调取webService获取用户是否存在结果："+start);
		ResultBean<String> resultBean = new ResultBean<String>(false, "未知错误", "");
		//验证参数
		if(serverIp==null || "".equals(serverIp.trim())){
			resultBean.setMessage("serverIp无效！！");
			return resultBean;
		}
		if(serverPort==null || "".equals(serverPort.trim())){
			resultBean.setMessage("serverPort无效！！");
			return resultBean;
		}
		if(accountName==null || "".equals(accountName.trim())){
			resultBean.setMessage("accountName无效！！");
			return resultBean;
		}
		logger.info("调取webService获取用户是否存在参数："+serverIp+" "+serverPort+"   "+accountName);
		//更改本地wsdl文件中的serverIP、serverPort
		String fileURL = SimpleProperties.getInstance().getStringProperty("wsdl.path").replace("file:\\", "").replace("file:", "");
		logger.info(fileURL);
		List<String> sList = new ArrayList<String>();
		try(BufferedReader in = new BufferedReader(new FileReader(fileURL))){
			String s = null;
			while((s = in.readLine()) != null){
				if(s.trim().startsWith("<soap:address location=")){
					s = "<soap:address location=\"http://"+serverIp+":"+serverPort+"/AAAWebService/AccountService\" xmlns:wsdl=\"http://schemas.xmlsoap.org/wsdl/\" xmlns:soap12=\"http://schemas.xmlsoap.org/wsdl/soap12/\"/>";
				}
				sList.add(s);
			}
		}catch(IOException e){
			logger.info("IOException in checkoutAccountExist read:" + e);
		}
		try(BufferedWriter out = new BufferedWriter(new FileWriter(fileURL))) {
			for(String ss :sList){
				out.write(ss);
				out.newLine();
			}
			out.flush();
		} catch (IOException e) {
			logger.info("IOException in checkoutAccountExist write:" + e);
		}
		long start1 = System.currentTimeMillis();
		logger.info("调取webService获取用户是否存在结果："+start1);
		try{
			AccountService test = new AccountService();
			test.setHandlerResolver(new HandlerResolver(){
			       public List<Handler> getHandlerChain(PortInfo portInfo) {
			           List<Handler> handlerList = new ArrayList<Handler>();
			
			           handlerList.add(new SOAPEnvelopeHandler());
			           return handlerList;
			       }
			       
			});
			Account account = test.getAccountPort("http://"+serverIp+":"+serverPort+"/AAAWebService/AccountService");
			QueryResult result = account.queryRemainingCoins(accountName);
			if(result.getErrorCode()==201){
				resultBean.setProperties(false,result.getDescription(),"");
			}else if(result.getErrorCode()==0){
				resultBean.setProperties(true, "", "");
			}else{
				resultBean.setProperties(false, result.getErrorCode()+" "+result.getDescription(), "");
			}
			logger.info("调取webService获取用户是否存在结果："+result.getErrorCode()+" "+result.getDescription()+"   "+result.getGoldCoins());
		}catch(Exception e){
			resultBean.setProperties(false, e.toString(), "");
		}
		logger.info("调取webService获取用户是否存在结果："+System.currentTimeMillis());
		logger.info("调取webService获取用户是否存在结果最终用时："+(System.currentTimeMillis()-start));
		return resultBean;
	}
}
