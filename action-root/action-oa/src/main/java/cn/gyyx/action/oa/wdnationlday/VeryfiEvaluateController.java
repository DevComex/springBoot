package cn.gyyx.action.oa.wdnationlday;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.oa.stage.httpRequest.OAUserRequest;
import cn.gyyx.oa.stage.model.OAUserInfoModel;

@Controller
@RequestMapping("/lottery")
public class VeryfiEvaluateController {

	@RequestMapping("/nationalLottery")
	public String index(HttpServletRequest request, HttpServletResponse response) {

		return "/WdNationalDay/WdNationalDay";
	}

	/**
	 * 修改评论状态
	 * 
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/verify/evaluate/update/{status}/{code}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Map<String, String>> updateEvaluateStatus(@PathVariable int status, @PathVariable int code,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> map = new HashMap<>();
		map.put("message", "活动已结束！");
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	/**
	 * 展示审核评论
	 * 
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/verify/evaluate/show", method = RequestMethod.POST)
	public String showVerifyEvaluate(int status, String type, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		try {
			OAUserInfoModel userInfoModel = OAUserRequest
					.getUserInfoByRequest(request);
			if (userInfoModel == null) {
				model.addAttribute("loginFlag", "no");
				return "/WdNationalDay/WdNationalDay";
			}
		} catch (Exception e) {
			model.addAttribute("loginFlag", "no");
			return "/WdNationalDay/WdNationalDay";
		}
		model.addAttribute("values", new ArrayList<Object>());
		model.addAttribute("typePara", "evaluate");
		if (status == 0) {
			model.addAttribute("button", "have");
		} else {
			model.addAttribute("button", "no");
		}
		return "/WdNationalDay/WdNationalDay";
	}

	/**
	 * 展示审核production
	 * 
	 * @param status
	 * @param type
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/verify/production/show", method = RequestMethod.POST)
	public String showVerifyProduction(int status, int type, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		try {
			OAUserInfoModel userInfoModel = OAUserRequest
					.getUserInfoByRequest(request);
			if (userInfoModel == null) {
				model.addAttribute("loginFlag", "no");
				return "/WdNationalDay/WdNationalDay";
			}
		} catch (Exception e) {
			model.addAttribute("loginFlag", "no");
			return "/WdNationalDay/WdNationalDay";
		}
		List<Object> list = new ArrayList<Object>();
		model.addAttribute("values", list);
		model.addAttribute("typePara", "production");
		if (status == 0) {
			model.addAttribute("button", "have");
		} else {
			model.addAttribute("button", "no");
		}
		return "/WdNationalDay/WdNationalDay";
	}

	/**
	 * 修改审核production状态
	 * 
	 * @param status
	 * @param type
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/verify/production/update/{status}/{code}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Map<String, String>> updateVerifyProductionStatus(@PathVariable int status,
			@PathVariable int code, HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> map = new HashMap<>();
		try {
			OAUserInfoModel userInfoModel = OAUserRequest
					.getUserInfoByRequest(request);
			if (userInfoModel == null) {
				map.put("message", "您没登录,请先登录");
				return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			map.put("message", "您没登录,请先登录");
			return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
		}
		map.put("message", "活动已结束！");
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

}
