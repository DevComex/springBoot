package cn.gyyx.action.module;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.xwb.turnintegra.IntegraParaBean;
import cn.gyyx.action.xwb.turnintegra.XWBTurnIntegralService;

@Controller
@RequestMapping("xwb")
public class XWBTurnIntegralController {
	
	@RequestMapping("integral")
	@ResponseBody
	public ResultBean<String>  turnToIntegral(HttpServletRequest request,IntegraParaBean para){
		return new XWBTurnIntegralService().turnIntegral(request, para);
	}
}
