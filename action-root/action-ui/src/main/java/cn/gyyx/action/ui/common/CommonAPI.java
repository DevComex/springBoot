package cn.gyyx.action.ui.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.novicecard.ResultBean;
import cn.gyyx.action.beans.novicecard.ServerInfoBean.Value;
import cn.gyyx.action.beans.wdninestory.ServerBean;
import cn.gyyx.action.service.SimpleCaptcha;
import cn.gyyx.action.service.agent.CallWebApiAgent;
import freemarker.template.TemplateException;

@Controller
@RequestMapping(value = "/commonAPI")
public class CommonAPI {
	private static final CallWebApiAgent callWebApiAgent = new CallWebApiAgent();
	/**
	 * 获得问道服务器列表 by netType 
	 */
	@RequestMapping(value = "/getWDServers")
	@ResponseBody
	public ResultBean<List<ServerBean>> getServers(
			@RequestParam(value = "netType") String typeCode) {
		ResultBean<List<ServerBean>> result = new ResultBean<List<ServerBean>>(
				true, "查询服务器失败", null);
		result.setProperties(true, "查询服务器失败", null);
		List<Value> serversList = callWebApiAgent
				.getAllServerByNetTypeCode(typeCode);
		List<ServerBean> serverList = new ArrayList<ServerBean>();
		for (Value value : serversList) {
			ServerBean server = new ServerBean(value.getCode(),
					value.getServerName());
			serverList.add(server);
		}
		result.setProperties(true, "查询服务器成功", serverList);
		return result;
	}
	@RequestMapping("/simpleCaptcha")
	public void simpleCaptcha(HttpServletResponse response,@RequestParam("r")double r) throws IOException, TemplateException {
		response.setContentType("image/png");
		SimpleCaptcha.write(response);
		response.getOutputStream().close();
	}
}
