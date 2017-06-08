/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：xuanwuba
 * @作者：yangyusheng
 * @联系方式：yangyusheng@gyyx.cn
 * @创建时间： 2015年7月9日 下午1:55:04
 * @版本号：
 * @本类主要用途描述：玩家信息控制器
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.oa.xwbcreditsluckydraw;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.gyyx.action.beans.xwbcreditsluckydraw.GoodsGetBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.PlayerInfoBean;
import cn.gyyx.action.bll.xwbcreditsluckydraw.GoodsGetBll;
import cn.gyyx.core.DataFormat;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

@Controller
@RequestMapping("/xwbJiFen")
public class PlayerinfoController {

	private static final Logger logger = GYYXLoggerFactory
			.getLogger(PlayerinfoController.class);
	private GoodsGetBll goodsGetBll = new GoodsGetBll();

	/**
	 * 
	 * @日期：2015年7月9日
	 * @Title: playerinfo
	 * @Description: TODO 玩家信息首页
	 * @param model
	 * @return String
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/playerinfo")
	public String playerinfo(Model model,
			GoodsGetBean goods){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		List<GoodsGetBean> playerList = goodsGetBll.getGoodsRecord(goods);
		for (GoodsGetBean goodsGetBean : playerList) {
			if(goodsGetBean.getExchangeDate()!=null){
				goodsGetBean.setStrExchangeDate(format.format(goodsGetBean.getExchangeDate()));
			}
		}
		for (int i = 1; i <= playerList.size(); i++) {
			playerList.get(i-1).setCode(i);
		}
		model.addAttribute("account", goods.getAccount());
		model.addAttribute("strExchangeDate", goods.getStrExchangeDate());
		model.addAttribute("playerList", playerList);
		model.addAttribute("playerListJSON", DataFormat.objToJson(playerList));
		model.addAttribute("count", playerList.size());
		model.addAttribute("listCount",
				playerList.size() % 10 == 0 ? playerList.size() / 10 : playerList.size() / 10 + 1);
		
		return "xwbcreditsluckydraw/playerinfo";
	}

}
