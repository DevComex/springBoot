package cn.gyyx.action.oa.wechat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.lottery.ChancePrizeBean;
import cn.gyyx.action.beans.lottery.PrizeBean;
import cn.gyyx.action.beans.lottery.RechargeBean;
import cn.gyyx.action.beans.wechat.ConfigBean;
import cn.gyyx.action.beans.wechat.LotteryChanceSupplyBean;
import cn.gyyx.action.beans.wechat.WechatPresentLogBean;
import cn.gyyx.action.bll.lottery.RechargeBll;
import cn.gyyx.action.bll.lottery.UserLotteryBll;
import cn.gyyx.action.bll.wechat.ConfigBLL;
import cn.gyyx.action.bll.wechat.LotteryChanceSupplyBLL;
import cn.gyyx.action.bll.wechat.WechatSendPresentLogBLL;
import cn.gyyx.core.DataFormat;

@Controller
@RequestMapping("/wechatUserBackstage")
public class WeChatGameUserBackstageController {
	private static final Logger logger = LoggerFactory
			.getLogger(WeChatGameUserBackstageController.class);
	private ConfigBLL weChatConfigBLL = new ConfigBLL();
	private UserLotteryBll userLotteryBll = new UserLotteryBll();
	private WechatSendPresentLogBLL wechatSendPresentLogBLL = new WechatSendPresentLogBLL();
	private LotteryChanceSupplyBLL lotteryChanceSupplyBLL = new LotteryChanceSupplyBLL();
	private RechargeBll rechargeBll = new RechargeBll();
	
	@RequestMapping("/index")
	public String index(Model model) {
		List<ConfigBean> list = weChatConfigBLL.getWechatConfig();
		model.addAttribute("configListJSON", DataFormat.objToJson(list));
		model.addAttribute("configList", list);
		model.addAttribute("count", list.size());
		model.addAttribute("listCount", list.size()%10==0?list.size()/10:list.size()/10	+1);
		return "wechat/wechatUserIndex";
	}
	
	/**
	 * 奖品补充
	 * @param actionCode
	 * @param model
	 * @return
	 */
	@RequestMapping("/chanceSupply")
	public String chanceSupply(Integer actionCode,Model model) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		List<ChancePrizeBean> listInformation = userLotteryBll
				.getChancePrize(actionCode);
		for (ChancePrizeBean chancePrizeBean : listInformation) {
			PrizeBean prize = userLotteryBll.getPrizeByCode(chancePrizeBean.getPrizeCode());
			chancePrizeBean.setPrizeName(prize.getChinese());
			WechatPresentLogBean wechatPresentLogBean = new WechatPresentLogBean();
			wechatPresentLogBean.setActivityId(actionCode);
			wechatPresentLogBean.setPresentName(prize.getChinese());
			int use = wechatSendPresentLogBLL.getLotteryPrizeCount(wechatPresentLogBean);
			chancePrizeBean.setProvideNum(use);
			int sup = lotteryChanceSupplyBLL.getSupplyCount(chancePrizeBean.getPrizeCode());
			chancePrizeBean.setSupplyNum(sup);
			if(sup > 0){
				LotteryChanceSupplyBean lotteryChanceSupplyBean = lotteryChanceSupplyBLL.getTopSupply(chancePrizeBean.getPrizeCode());
				chancePrizeBean.setSupplyTime(sdf.format(lotteryChanceSupplyBean.getSupplyDate()));
			}
			chancePrizeBean.setOverNum(chancePrizeBean.getNumber()-use);
			chancePrizeBean.setOldNumber(chancePrizeBean.getNumber()-sup);
			if(prize.getIsReal().equals("card")){
				RechargeBean rechargeBean = new RechargeBean();
				rechargeBean.setActionCode(actionCode);
				rechargeBean.setType(prize.getEnglish());
				chancePrizeBean.setCardNoUse(rechargeBll.getRechargeCountNoUse(rechargeBean));
				chancePrizeBean.setCardTotal(rechargeBll.getRechargeCount(rechargeBean));
			}
		}
		model.addAttribute("actionCode", actionCode);
		model.addAttribute("listInformation", listInformation);
		return "wechat/chanceNumber";
	}
	/**
	 * 查询奖品信息
	 * @param actionCode
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/getPrizeInfo", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getPrizeInfo(int actionCode,Model model) {
		List<ChancePrizeBean> listInformation = userLotteryBll
				.getChancePrize(actionCode);
		for (ChancePrizeBean chancePrizeBean : listInformation) {
			PrizeBean prize = userLotteryBll.getPrizeByCode(chancePrizeBean.getPrizeCode());
			chancePrizeBean.setPrizeName(prize.getChinese());
		}
		return DataFormat.objToJson(listInformation);
	}
	
	/**
	 * 增加补充
	 * @param lotteryChanceSupplyBean
	 * @param model
	 */
	@RequestMapping(value="/addPrizeSupply", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public int addPrizeSupply(LotteryChanceSupplyBean lotteryChanceSupplyBean,Model model) {
		int num = 0;
		if(lotteryChanceSupplyBean != null){
			lotteryChanceSupplyBean.setSupplyDate(new Date());
			lotteryChanceSupplyBLL.addPrizeSupply(lotteryChanceSupplyBean);
			ChancePrizeBean chance = userLotteryBll.getChancePrizeByprizeCode(lotteryChanceSupplyBean.getChanceCode());
			chance.setNumber(chance.getNumber() + lotteryChanceSupplyBean.getSupplyNum());
			num = userLotteryBll.updateChancePrizeBean(chance);
		}
		return num;
	}
	
	
	/**
	 * 查询虚拟信息
	 * @param actionCode
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/getNoRealInfo", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getNoRealInfo(int actionCode,Model model) {
		List<ChancePrizeBean> listInformation = userLotteryBll
				.getChancePrize(actionCode);
		List<ChancePrizeBean> listInfo = new ArrayList<ChancePrizeBean>();
		for (ChancePrizeBean chancePrizeBean : listInformation) {
			PrizeBean prize = userLotteryBll.getPrizeByCode(chancePrizeBean.getPrizeCode());
			chancePrizeBean.setPrizeName(prize.getChinese());
			chancePrizeBean.setPrizeEnglish(prize.getEnglish());
			if(prize.getIsReal().equals("card")){
				listInfo.add(chancePrizeBean);
			}
		}
		return DataFormat.objToJson(listInfo);
	}
	
	/**
	 * 增加卡
	 * @param lotteryChanceSupplyBean
	 * @param model
	 */
	@RequestMapping(value="/addRechargeInfo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public void addRechargeInfo(RechargeBean rechargeBean,Model model) {
		if(rechargeBean != null){
			rechargeBean.setUserCode(0);
			rechargeBll.addRechargeInfo(rechargeBean);
		}
	}
}
