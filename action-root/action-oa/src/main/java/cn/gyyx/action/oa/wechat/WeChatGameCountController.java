package cn.gyyx.action.oa.wechat;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.wechat.ConfigBean;
import cn.gyyx.action.beans.wechat.OperateBean;
import cn.gyyx.action.beans.wechat.WeChatCountBean;
import cn.gyyx.action.beans.wechat.WeiXinAddressBean;
import cn.gyyx.action.bll.wechat.ConfigBLL;
import cn.gyyx.action.bll.wechat.CustomBLL;
import cn.gyyx.action.bll.wechat.OperateBLL;
import cn.gyyx.action.bll.wechat.WeChatCountBLL;
import cn.gyyx.action.bll.wechat.WeiXinAddressBLL;
import cn.gyyx.core.DataFormat;

@Controller
@RequestMapping("/wechatCount")
public class WeChatGameCountController {

	private static final Logger logger = LoggerFactory
			.getLogger(WeChatGameCountController.class);
	private WeChatCountBLL weChatCountBLL = new WeChatCountBLL();
	private ConfigBLL configBLL = new ConfigBLL();
	private OperateBLL operateBLL = new OperateBLL();
	private CustomBLL customBLL = new CustomBLL();
	private WeiXinAddressBLL weiXinAddressBLL = new WeiXinAddressBLL();

	@RequestMapping("/choice")
	public String choice(Model model) {
		return "wechat/choiceIndex";
	}

	@RequestMapping("/index")
	public String index(Integer configCode,Model model) {
		if(configCode != null){
			ConfigBean configBean = configBLL.getWechatConfigInfoByCode(configCode);
			model.addAttribute("configName", configBean.getConfigName());
			List<OperateBean> operateList = operateBLL.getOperateInfoByConfigCodeNoCount(configCode);
			for (OperateBean operateBean : operateList) {
				Integer count = weChatCountBLL.countByType(operateBean.getOperateType());
				operateBean.setTotalCount(count);
			}
			model.addAttribute("operateList", operateList);
		}	
		return "wechat/wechatIndex";
	}
	
	@RequestMapping("/address")
	public String address(WeiXinAddressBean weiXinAddressBean,Model model) {
		if(weiXinAddressBean == null){
			weiXinAddressBean = new WeiXinAddressBean();
			weiXinAddressBean.setActionCode(294);
		}else{
			weiXinAddressBean.setActionCode(294);
		}
		List<WeiXinAddressBean> addressList =  weiXinAddressBLL.getAllAddressInfoByActionCode(weiXinAddressBean);
		for (int i = 1; i <= addressList.size(); i++) {
			addressList.get(i-1).setCode(i);
		}
		model.addAttribute("openId", weiXinAddressBean.getOpenId());
		model.addAttribute("addressList", addressList);
		model.addAttribute("addressListJSON", DataFormat.objToJson(addressList));
		model.addAttribute("count", addressList.size());
		model.addAttribute("listCount",
				addressList.size() % 10 == 0 ? addressList.size() / 10 : addressList.size() / 10 + 1);
		return "wechat/addressInfo";
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
