package cn.gyyx.action.ui.common;

import java.io.IOException;
import java.io.Writer;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.common.beans.CommonUpload;
import cn.gyyx.action.common.beans.InnerTransmissionData;
import cn.gyyx.action.common.beans.ResultBean;
import cn.gyyx.action.common.dictionary.DBColum;
import cn.gyyx.action.common.dictionary.Dictionary;
import cn.gyyx.action.common.dictionary.DictionaryContainer;
import cn.gyyx.action.common.manager.ActionManager;
import cn.gyyx.action.common.manager.ManagerContainer;
import cn.gyyx.core.Ip;
import cn.gyyx.core.auth.SignedUser;
import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.core.prop.PropertiesParser;
import cn.gyyx.core.prop.SimpleProperties;
import freemarker.template.TemplateException;

@Controller
@RequestMapping(value = "/commonSupporter")
public class CommonSupporterController {
	private static String showAnno = "no";
	static {
		PropertiesParser parser = SimpleProperties.getInstance();
		String res = parser.getStringProperty("showAnno");
		if(res != null) {
			showAnno = res;
		}
	}
	@RequestMapping("/actionUtil")
	public void actionUtil(HttpServletResponse response,@RequestParam("actionCode")int actionCode) throws IOException, TemplateException {
		response.setContentType("application/x-javascript;charset=utf-8");
		Writer writer = response.getWriter();
		Dictionary dic = DictionaryContainer.getDictionary(actionCode);
		if(dic != null) {
			dic.writeScript(writer,showAnno.equals("yes"));
		}
		writer.flush();
		writer.close();
	}
	
	@RequestMapping("/ajaxOperation")
	public @ResponseBody ResultBean<Object> ajaxOperation(HttpServletRequest request,HttpServletResponse response,CommonUpload data) {
		//response.addHeader("Access-Control-Allow-Origin","*");
		InnerTransmissionData transData = data.getTransmissionData();
		if(transData != null) {
			ActionManager manager = ManagerContainer.getActionManager(transData.getActionCode());
			if(manager != null) {
				insertUploadActionData(transData,request);
				ResultBean<Object> result = manager.doChain(request, transData);
				return result;
			} else {
				ResultBean<Object> res = new ResultBean<Object>();
				res.setIsSuccess(false);
				res.setMessage("活动未配置(code:"+transData.getActionCode()+")");
				return res;
			}
		} else {
			ResultBean<Object> res = new ResultBean<Object>();
			res.setIsSuccess(false);
			res.setMessage("接口不匹配或参数异常");
			return res;
		}
	}

	private void insertUploadActionData(InnerTransmissionData data,HttpServletRequest request) {
		if(request.getCookies() != null) {
			UserInfo info = SignedUser.getUserInfo(request);
			if(info != null) {
				data.getDataMap().put(DBColum.ACCOUNT, info.getAccount());
				data.getDataMap().put(DBColum.ACCOUNT_CODE, info.getUserId());
			}
		}
		data.getDataMap().put(DBColum.UPLOAD_DATE, new Date());
		data.getDataMap().put(DBColum.UPLOAD_SQLDATE, new java.sql.Date(System.currentTimeMillis()));
		data.getDataMap().put(DBColum.IP_ADDR, Ip.getCurrent(request).asString());
	}
	
//	@RequestMapping("/commonTestPage")
//	public String commonTestPage() {
//		return "commonSupporter/commonTestPage";
//	}
	
}
