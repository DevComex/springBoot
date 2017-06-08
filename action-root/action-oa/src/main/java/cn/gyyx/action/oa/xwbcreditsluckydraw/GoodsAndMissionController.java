/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：xuanwuba
 * @作者：yangyusheng
 * @联系方式：yangyusheng@gyyx.cn
 * @创建时间： 2015年7月13日 上午11:50:24
 * @版本号：
 * @本类主要用途描述：任务和物品控制器
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.oa.xwbcreditsluckydraw;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.gyyx.action.beans.xwbcreditsluckydraw.GoodsInfoBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.MissionBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.PageBean;
import cn.gyyx.action.bll.xwbcreditsluckydraw.GoodsBLL;
import cn.gyyx.action.bll.xwbcreditsluckydraw.MissionBLL;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

@Controller
@RequestMapping("/xwbJiFen")
public class GoodsAndMissionController {

	private static final Logger logger = GYYXLoggerFactory
			.getLogger(GoodsAndMissionController.class);
	private MissionBLL missionBll = new MissionBLL();
	private GoodsBLL goodsBll = new GoodsBLL();

	/**
	 * 
	 * @日期：2015年7月13日
	 * @Title: goods
	 * @Description: TODO 获取物品信息首页
	 * @param model
	 * @return String
	 */
	@RequestMapping("/goods")
	public String goods(Model model, Integer pageNum) {
		logger.debug("页数", pageNum);
		// 直接进入默认第一页
		if (pageNum == null) {
			pageNum = 1;
		}
		List<GoodsInfoBean> goodsList = goodsBll.getGoods(pageNum);
		int goodsNum = goodsBll.getGoodsTotal();
		PageBean page = new PageBean(pageNum, goodsNum);
		model.addAttribute("page", page);
		model.addAttribute("goodsList", goodsList);
		return "xwbcreditsluckydraw/goodsinfo";
	}

	/**
	 * 
	 * @日期：2015年7月13日
	 * @Title: mission
	 * @Description: TODO 获取任务信息首页
	 * @param model
	 * @return String
	 */
	@RequestMapping("/mission")
	public String mission(Model model, Integer pageNum) {
		logger.debug("页数", pageNum);
		// 直接进入默认第一页
		if (pageNum == null) {
			pageNum = 1;
		}
		List<MissionBean> missionList = missionBll.getMission(pageNum);
		int missionNum = missionBll.getMissionTotal();
		PageBean page = new PageBean(pageNum, missionNum);
		model.addAttribute("missionList", missionList);
		model.addAttribute("page", page);
		return "xwbcreditsluckydraw/mission";
	}
}
