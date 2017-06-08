package cn.gyyx.action.oa.wechat;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.gyyx.action.beans.wechat.WeChatCountBean;
import cn.gyyx.action.bll.wechat.WeChatCountBLL;
import cn.gyyx.core.DataFormat;

@Controller
@RequestMapping("/WeChatGame")
public class WeChatCountController {

	private static final Logger logger = LoggerFactory
			.getLogger(WeChatCountController.class);
	private WeChatCountBLL weChatCountBLL = new WeChatCountBLL();

	@RequestMapping("/index")
	public String index(Model model) {
		model.addAttribute("normalAccess",
				weChatCountBLL.countByType("normalAccess"));
		model.addAttribute("examAccess",
				weChatCountBLL.countByType("examAccess"));
		model.addAttribute("certAccess",
				weChatCountBLL.countByType("certAccess"));
		model.addAttribute("shareAccess",
				weChatCountBLL.countByType("shareAccess"));
		model.addAttribute("leadAccess",
				weChatCountBLL.countByType("leadAccess"));
		return "wechat/index";
	}

	@RequestMapping("/online")
	public String online(Model model) {
		List<WeChatCountBean> list = weChatCountBLL.selectCountWithPM();
		for (WeChatCountBean weChatCountBean : list) {
			if (weChatCountBean.getPartakeTime() != null) {
				// 分
				weChatCountBean
						.setPartakeTime(weChatCountBean.getPartakeTime() % 60 == 0 ? weChatCountBean
								.getPartakeTime() / 60 : weChatCountBean
								.getPartakeTime() / 60 + 1);
			}
		}
		for (int i = 1; i <= list.size(); i++) {
			list.get(i - 1).setCode(i);
		}
		model.addAttribute("list", list);
		model.addAttribute("listJSON", DataFormat.objToJson(list));
		model.addAttribute("listCount",
				list.size() % 10 == 0 ? list.size() / 10 : list.size() / 10 + 1);
		return "wechat/online";
	}
}
