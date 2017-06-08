package cn.gyyx.action.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.wd9year.WishBean;
import cn.gyyx.action.beans.wdno1pet.ResultBean;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.service.wd9year.WishService;

@Controller
@RequestMapping("/wd9year")
public class WD9YearWishController {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(WD9YearWishController.class);
	private WishService wishService = new WishService();

	/**
	 * 实现上传祝福的功能
	 * 
	 * @param model
	 *            request wishBean 祝福实体类
	 * */
	@RequestMapping(value = "saveWish", method = RequestMethod.POST)
	public @ResponseBody ResultBean<Integer> wish(Model model,
			HttpServletRequest request, WishBean wishBean) {
		logger.info("wishBean", wishBean);
		ResultBean<Integer> resultBean = null;
		try {
			wishBean.setCheckStatus("uncheck");
			wishBean.setWishTime(new Date());
			resultBean = wishService.uploadWish(request, wishBean);
		} catch (Exception e) {
			logger.warn(e.toString());
			resultBean = new ResultBean<Integer>();
			resultBean.setData(Integer.MIN_VALUE);
			resultBean.setIsSuccess(false);
			resultBean.setMessage(e.toString());
		}
		logger.info("wish resultBean ", resultBean);
		return resultBean;
	}

	/**
	 * 随机和最新显示祝福
	 * 
	 * @param q
	 *            查询标志
	 * */
	@RequestMapping(value = "wish", method = RequestMethod.GET)
	public String toWish(String q, Model model) {
		try {
			model.addAttribute("wishList", wishService.searchNewomWish());
		} catch (Exception e) {
			logger.warn("Server Error:" + e.toString());
		}
		model.addAttribute("staticSource",
				"http://static.cn114.cn/action/wd9year");
		return "wd9year/index";
	}

	@RequestMapping(value = "searchWish", method = RequestMethod.POST)
	public @ResponseBody List<WishBean> searchWish(String q, Model model) {
		List<WishBean> wishList = null;
		try {
			if (q.equals("new")) {
				wishList = wishService.searchNewomWish();
			} else if (q.equals("random")) {
				wishList = wishService.searchRandomWish();
			}
			model.addAttribute("staticSource",
					"http://static.cn114.cn/action/wd9year");
		} catch (Exception e) {
			logger.error("Server Error:" + e.toString());
			wishList = new ArrayList<WishBean>();
		}
		logger.info("wishList", wishList);
		return wishList;
	}
}
