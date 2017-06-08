package cn.gyyx.action.oa.schoolopen;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.gyyx.action.beans.xwbcreditsluckydraw.NewPageBean;

@Controller
@RequestMapping(value = "/looklottery")
public class LookLotteryController {

	@RequestMapping(value = "/showList", method = RequestMethod.GET)
	public String showList(Model model, HttpServletResponse httpResponse) {

		return "schoolopen/show";
	}

	@RequestMapping(value = "/getProbability", method = RequestMethod.GET)
	public String getProbability(Model model, HttpServletResponse httpResponse) {

		return "schoolopen/getProbability";
	}

	@RequestMapping(value = "/adminHome", method = RequestMethod.GET)
	public String adminHome(Model model, HttpServletResponse httpResponse) {

		return "schoolopen/adminHome";
	}

	@RequestMapping(value = "/prizeActionCode", method = RequestMethod.GET)
	public String prizeActionCode(Model model, HttpServletResponse httpResponse) {

		return "schoolopen/prizeActionCode";
	}

	@RequestMapping(value = "/getExportTaskList", method = RequestMethod.GET)
	public String getExportTaskList(Model model,
			HttpServletResponse httpResponse) {

		return "schoolopen/export";
	}

	@RequestMapping(value = "/getProbabilityList", method = RequestMethod.GET)
	public String getProbabilityList(Model model,
			HttpServletResponse httpResponse, String actionCode) {

		return "schoolopen/prizeList";
	}

	@RequestMapping(value = "/showImgCheck")
	public String showImgCheck(Model model, NewPageBean newPageBean) {

		return "schoolopen/imgCheck";
	}

	@RequestMapping(value = "/updateSum")
	public String updateSum(Model model, NewPageBean newPageBean) {

		return "schoolopen/updateSum";
	}

	@RequestMapping(value = "/updateAccount")
	public String updateAccount(Model model, NewPageBean newPageBean) {

		return "redirect:updateSum?pageIndex=" + newPageBean.getPageIndex();
	}

	@RequestMapping(value = "/passImg")
	public String passImg(Model model, NewPageBean newPageBean) {

		return "redirect:showImgCheck";
	}

	@RequestMapping(value = "/unpassImg")
	public String unpassImg(Model model, NewPageBean newPageBean) {

		return "redirect:showImgCheck";
	}

	@RequestMapping("/winningRecord")
	public String winningRecord(Model model, NewPageBean newPageBean) {

		return "schoolopen/prizeShow";
	}

}
