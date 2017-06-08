package cn.gyyx.playwd.oa.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.playwd.beans.common.ResultBean;
import cn.gyyx.playwd.beans.playwd.RecommendContentBean;
import cn.gyyx.playwd.beans.playwd.RecommendSlotBean;
import cn.gyyx.playwd.bll.RecommendContentBll;
import cn.gyyx.playwd.oa.common.web.BaseController;
import cn.gyyx.playwd.oa.viewmodel.RecommendContentEditViewModel;
import cn.gyyx.playwd.service.RecommendContentService;
import cn.gyyx.playwd.service.RecommendSlotService;
import cn.gyyx.playwd.utils.DateTools;

/**
 * 推荐区域信息管理
 * 
 * @author yangteng
 *
 */
@Controller
@RequestMapping(value = "/recommend/content")
public class RecommendContentController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	public RecommendContentBll recommendContentBll;

	@Autowired
	public RecommendContentService recommendContentService;
	
	@Autowired
	public RecommendSlotService recommendSlotService;
	
	/**
	 * 图文推荐区域信息
	 */
	@RequestMapping(value = "/article", method = { RequestMethod.GET })
	public String Index(Integer locid, Model model, HttpServletRequest request) {

		// 加载分类列表
		List<RecommendSlotBean> list = recommendSlotService.getRecommendSlotList("article");
		model.addAttribute("list", list);
		if (list.size() == 0) {
			return "recommend/content/article";
		}

		if (locid == null) {
			locid = list.get(0).getCode();
		}
		List<RecommendContentBean> contentList = recommendContentBll
				.getList(locid);
		model.addAttribute("recommendList", contentList);

		Integer maxDispalyOrder = recommendContentBll.getDisplayOrder(locid);
		model.addAttribute("maxDisplayOrder", maxDispalyOrder);

		Integer minDisplayOrder = recommendContentBll.getMinDisplayOrder(locid);
		model.addAttribute("minDisplayOrder", minDisplayOrder);

		model.addAttribute("total", contentList.size());
		model.addAttribute("code", locid);

		return "recommend/content/article";
	}
	
	/**
	 * 查询列表
	 * 
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	@ResponseBody
	public ResultBean<List<Map<String, Object>>> getList(Integer code,
			HttpServletRequest request) {

		ResultBean<List<Map<String, Object>>> result = new ResultBean<List<Map<String, Object>>>();

		try {

			if (code == null) {
				result.setMessage("缺少参数code");
				return result;
			}

			List<RecommendContentBean> contentlist = recommendContentBll
					.getList(code);
			if (contentlist.size() == 0) {
				result.setMessage("暂无数据");
				return result;
			}

			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			for (RecommendContentBean item : contentlist) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("code", item.getCode());
				map.put("title", item.getTitle());
				map.put("cover", item.getCover());
				map.put("url", item.getUrl());
				map.put("isDisplay", item.getIsDisplay());
				map.put("updateTime", DateTools.formatDate(
						item.getUpdateTime(), "yyyy-MM-dd HH:mm:ss"));
				map.put("displayOrder", item.getDisplayOrder());
				list.add(map);
			}

			result.setProperties(true, "查询成功", list);

		} catch (Exception ex) {
			logger.error("查询推荐区域信息列表异常", ex);
			result.setMessage("查询推荐区域信息列表异常");
		}
		return result;
	}

	/**
	 * 编辑推荐区域信息
	 * 
	 * @return
	 */
	@RequestMapping(value="/update",method={RequestMethod.POST})
	@ResponseBody
	public ResultBean<String> update(
			@Valid RecommendContentEditViewModel viewModel,
			BindingResult bindingResult, HttpServletRequest request) {

		try {

			if (bindingResult.hasErrors()) {
				String messageString = validationData(bindingResult);
				String[] messageStrings = messageString.split("\\|");
				return new ResultBean<String>(false, messageStrings[1], null);
			}

			ModelMapper modelMapper = new ModelMapper();
			RecommendContentBean model = modelMapper.map(viewModel,
					RecommendContentBean.class);

			return recommendContentService.update(model);

		} catch (Exception ex) {
			logger.error("编辑推荐区域信息异常", ex);
			return new ResultBean<String>(false, "编辑推荐区域信息异常", null);
		}
	}

	/**
	 * 隐藏,显示功能
	 * 
	 * @param code
	 * @param isDisplay
	 *            显示 true,隐藏 false
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/show", method = { RequestMethod.POST })
	@ResponseBody
	public ResultBean<String> show(Integer code, Boolean isDisplay,
			HttpServletRequest request) {

		ResultBean<String> result = new ResultBean<String>();

		try {

			if (code == null || code <= 0) {
				result.setProperties(false, "缺少参数code", null);
				return result;
			}

			if (isDisplay == null) {
				result.setProperties(false, "缺少参数isDisplay", null);
			}

			RecommendContentBean model = new RecommendContentBean();
			model.setCode(code);
			model.setIsDisplay(isDisplay);

			return recommendContentService.updateDisplay(model);

		} catch (Exception ex) {
			logger.error("更新推荐区域信息的显示状态时异常", ex);
			result.setProperties(false, "更新推荐区域信息的显示状态时异常", null);
			return result;
		}
	}

	/**
	 * 向上移动,向下移动
	 * @param code
	 * @param type 向上移动up,向下移动down
	 * @param request
	 */
	@RequestMapping(value = "/move", method = { RequestMethod.POST })
	@ResponseBody
	public ResultBean<String> move(Integer code, String type,HttpServletRequest request) {
		try {
			if (code == null || code <= 0) {
				return new ResultBean<String>(false, "缺少参数code", null);
			}

			if (!type.equals("moveUp") && !type.equals("moveDown")
					&& !type.equals("moveTop") && !type.equals("moveBottom")) {
				return new ResultBean<String>(false, "参数type不正确", null);
			}

			return recommendContentService.move(code, type);
			
		} catch (Exception ex) {
			logger.error("推荐区域信息向上移动异常", ex);
			return new ResultBean<String>(false, "推荐区域信息向上移动异常", null);
		}
	}
	
	/**
	 * 获取错误信息
	 * 
	 * @param vresult
	 * @return
	 */
	private String validationData(BindingResult bindingResult) {
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		for(FieldError error : fieldErrors){
			System.out.println(error.getDefaultMessage());
		}
		FieldError fieldError = fieldErrors.get(0);
		return fieldError.getDefaultMessage();
	}
}