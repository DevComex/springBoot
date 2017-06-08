package cn.gyyx.action.oa.lottery;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.PageBean;
import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.po.ActionPrizesAddressPO;
import cn.gyyx.action.bll.lottery.impl.ActionPrizesAddressBLLImpl;
import cn.gyyx.action.service.wdqingchengshan.WdQingchengshanConstant;
import cn.gyyx.log.sdk.GYYXLoggerFactory;


@Controller
@RequestMapping(value = "/wdqingchengshan")
public class CheckAdressController {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(CheckAdressController.class); 
	private ActionPrizesAddressBLLImpl actionPrizesAddressBLL = new ActionPrizesAddressBLLImpl();
	private WdQingchengshanConstant wdQingchengshanConstant = new WdQingchengshanConstant(); 
    
	
	@RequestMapping("/index")
	public String index(Model model) {
		return "wdqingchengshan/checkwriteinfo";
	}
	
    @RequestMapping(value = "/getAddressList",method=RequestMethod.GET)
	@ResponseBody
	public ResultBean<PageBean<ActionPrizesAddressPO>> getAddressList(
			@RequestParam("currentPage") int currentPage){
		logger.info("问道升仙大会-CheckAdressController-getAddressList"
				+ "，参数currentPage:" + currentPage);
		return wdQingchengshanConstant.getAddressList(currentPage);
	}
	/**
	 * 导出
	 * @param response
	 */
	@RequestMapping(value="/extendExcel",method=RequestMethod.GET)
	public void extendExcel(HttpServletResponse response) {
		List<ActionPrizesAddressPO> addressPO = actionPrizesAddressBLL.getUserAddress();
		try {
			wdQingchengshanConstant.extendExcel(response, addressPO);
		} catch (Exception e) {
			logger.error("问道升仙大会兑换信息展示-CheckAdressController-extendExcel,Exception:"
					+ e.toString(),e);
		}
	}

	
}
