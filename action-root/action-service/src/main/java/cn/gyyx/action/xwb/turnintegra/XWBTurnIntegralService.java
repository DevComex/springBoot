package cn.gyyx.action.xwb.turnintegra;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.xwb.turnintegra.IntegraParaBean;
import cn.gyyx.action.bll.xwb.integral.TurnIntegralBLL;
import cn.gyyx.core.security.MD5;

public class XWBTurnIntegralService {
	private static final Logger LOG = LoggerFactory
			.getLogger(XWBTurnIntegralService.class);
	/**
	 * 调用前请检测amount属性是否是float
	 * @param request
	 * @param para
	 * @return
	 */
	public ResultBean<String> turnIntegral (HttpServletRequest request,IntegraParaBean para){
		ResultBean<String> result = new ResultBean<String>(false,"","");
		if(verigyPara(para)){
			if(verifyAutograph(request)){
				para.setIntegral((int)para.getAmount());
				result = new TurnIntegralBLL().computeTurn(para.getOrderNumber(), para.getAccount(), para.getAmount(), para.getIntegral(),para.getServiceId(),para.getDatetime());
			}else{
				result.setProperties(false, "签名失败！", "wrongSign");
				LOG.debug(para.getOrderNumber()+"--"+para.getAccount()+"--"+"签名失败！");
			}
		}else{
			result.setProperties(false, "参数错误！", "wrongPara");
			LOG.debug(para.getOrderNumber()+"--"+para.getAccount()+"--"+"参数错误！");
		}
		return result;
	}
	
	
	private boolean verifyAutograph(HttpServletRequest request){
		String [] result = new String [3];
		@SuppressWarnings("unchecked")
		Enumeration<String> paraNames = request.getParameterNames();
		while(paraNames.hasMoreElements()){
			String paraName = paraNames.nextElement();
			if("timestamp".equals(paraName)){
				result[0]=request.getParameterValues(paraName)[0];
			}else if("sign".equals(paraName)){
				result[1]=request.getParameterValues(paraName)[0];
			}
		}
		String sign = MD5.encode("/xwb/integral?timestamp="+result[0]+"123456");
		if(sign.equals(result[1])){
			return true;
		}
		return false;
	}
	
	private boolean verigyPara(IntegraParaBean para){
		if(para.getAccount()==null||"".equals(para.getAccount())){
			return false;
		}
		if(para.getAmount()==0){
			return false;
		}
		if(para.getOrderNumber()==null||"".equals(para.getOrderNumber())){
			return false;
		}
		if(para.getServiceId()==null||"".equals(para.getServiceId())){
			return false;
		}
		if(para.getDatetime()==null||"".equals(para.getDatetime())){
			return false;
		}
		return true;
	}
}
