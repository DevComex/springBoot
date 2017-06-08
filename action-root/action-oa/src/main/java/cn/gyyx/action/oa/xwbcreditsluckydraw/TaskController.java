package cn.gyyx.action.oa.xwbcreditsluckydraw;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.lottery.ChancePrizeBean;
import cn.gyyx.action.beans.lottery.PrizeBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.GoodsInfoBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.MissionBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.MissionReceiveBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.NewPageBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.PictureUrlBean;
import cn.gyyx.action.bll.lottery.UserLotteryBll;
import cn.gyyx.action.bll.novicecard.ActivityConfigBll;
import cn.gyyx.action.bll.xwbcreditsluckydraw.GoodsBLL;
import cn.gyyx.action.bll.xwbcreditsluckydraw.GoodsGetBll;
import cn.gyyx.action.bll.xwbcreditsluckydraw.MissionBLL;
import cn.gyyx.action.bll.xwbcreditsluckydraw.MissionReceiveBLL;
import cn.gyyx.action.bll.xwbcreditsluckydraw.PictureUrlBLL;
import cn.gyyx.core.DataFormat;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

@Controller
@RequestMapping("/xwbJiFen")
public class TaskController {
	private Integer actionCode = 288;
	private MissionBLL missionBLL = new MissionBLL();
	private MissionReceiveBLL missionReceiveBLL = new MissionReceiveBLL();
	private GoodsBLL goodsBLL = new GoodsBLL();
	private PictureUrlBLL pictureUrlBLL = new PictureUrlBLL();
	private UserLotteryBll userLotteryBll = new UserLotteryBll();
	private GoodsGetBll goodsGetBll = new GoodsGetBll();
	private ActivityConfigBll activityConfigBll = new ActivityConfigBll();
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(PlayerinfoController.class);
	/**
	 * 任务列表
	 * @param model
	 * @return
	 */
    @RequestMapping("commentsList")
    public String commentsList(Model model,NewPageBean newPageBean) {
    	try {
			newPageBean.setMissionType(new String(newPageBean.getMissionType().getBytes("ISO-8859-1"),"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
    	List<MissionBean> list = missionBLL.getMissionBy(newPageBean);
    	Integer count = missionBLL.getMissionCount(newPageBean);
    	newPageBean.setTotalRecords(count);
    	
		model.addAttribute("list", list);
		model.addAttribute("count", count);
		model.addAttribute("pageBean", newPageBean);
        return "xwbcreditsluckydraw/missionInfo";
    }
    
    /**
     * 检查任务是否有人领取
     * @param model
     * @return
     */
    @RequestMapping(value="getCountReceiveByCode", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Integer getCountReceiveByCode(Integer code,Model model) {
    	Integer count = missionReceiveBLL.getCountReceiveByCode(code);
    	return count;
    }
    
    @RequestMapping("closeTask")
    public String closeTask(Model model,NewPageBean newPageBean) {
    	MissionBean missionBean = new MissionBean();
    	Date date = new Date();
    	missionBean.setMissionCloseTime(date);
    	missionBean.setMissionCloseStatus(1);
    	missionBean.setCode(newPageBean.getCode());
    	missionBLL.closeMission(missionBean);
        return "redirect:commentsList?pageIndex=" + newPageBean.getPageIndex()+"&recommendTags="+newPageBean.getRecommendTags()+"&missionType="+newPageBean.getMissionType()+"&missionCloseStatus="+newPageBean.getMissionCloseStatus();
    }
    @RequestMapping("openTask")
    public String openTask(Model model,NewPageBean newPageBean) {
    	
    	MissionBean missionBean = new MissionBean();
    	Date date = new Date();
    	missionBean.setMissionCloseTime(date);
    	missionBean.setMissionCloseStatus(0);
    	missionBean.setCode(newPageBean.getCode());
    	missionBLL.closeMission(missionBean);
    	return "redirect:commentsList?pageIndex=" + newPageBean.getPageIndex()+"&recommendTags="+newPageBean.getRecommendTags()+"&missionType="+newPageBean.getMissionType()+"&missionCloseStatus="+newPageBean.getMissionCloseStatus();
    }
    
    @RequestMapping("addTask")
    public String addTask(Model model,MissionBean missionBean) {

    	Date date = new Date();
    	missionBean.setMissionPublishTime(date);
    	missionBean.setMissionCloseStatus(0);
    	missionBLL.addMission(missionBean);
    	return "redirect:commentsList";
    }
    
    @RequestMapping(value="getPubMissionInfo", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getPubMissionInfo(Model model) {
    	MissionBean missionBean = missionBLL.getPubMissionInfo();
    	return DataFormat.objToJson(missionBean);
    }
    
    @RequestMapping("updateMission")
    public String updateMission(Model model,MissionBean missionBean) {
    
    	missionBLL.updateMission(missionBean);
    	return "redirect:commentsList";
    }
 
    @RequestMapping(value = "/getMission",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	 public String getMission(Model model,String code) {
    	
    	MissionBean missionBean = missionBLL.getMissionInfoByCode(Integer.parseInt(code));
		return DataFormat.objToJson(missionBean);
	}
    /**
	 * 
	 * @日期：2015年10月14日
	 * @Title: "getMissionReceiveLog"
	 * @Description: TODO 查询完成记录 
	 * @param 
	 * @return 
	 */
    @RequestMapping("getMissionReceiveLog")
	public String getMissionReceiveLog(MissionReceiveBean missionReceiveBean,Model model){
    	if(missionReceiveBean.getMissionType() != null){
    		try {
				missionReceiveBean.setMissionType(new String(missionReceiveBean.getMissionType().getBytes("iso-8859-1"),"utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
    	}
    	List<MissionReceiveBean> list = missionReceiveBLL.getMissionReceiveLog(missionReceiveBean);
    	model.addAttribute("acount", missionReceiveBean.getAcount());
    	model.addAttribute("missionType", missionReceiveBean.getMissionType());
    	model.addAttribute("list", list);
    	model.addAttribute("listJSON", DataFormat.objToJson(list));
		model.addAttribute("listCount",
				list.size() % 10 == 0 ? list.size() / 10 : list.size() / 10 + 1);
    	return "xwbcreditsluckydraw/missionLog";
		
	}
    @RequestMapping("/exchange")
	public String exchange(Model model) {
		List<GoodsInfoBean> goodsInfoBean = goodsBLL.getGoodsAll();
		if (!activityConfigBll.getConfigMessage("炫舞吧抽奖").getIsSuccess()) {
			model.addAttribute("message", "no");
		}else{
			model.addAttribute("message", "yes");
		}
		model.addAttribute("goodsInfoBean", goodsInfoBean);
		return "xwbcreditsluckydraw/exchange";
	}
    @RequestMapping("/addGoodsInfoBean")
  	public String addGoodsInfoBean(Model model,GoodsInfoBean goodsInfoBean) {
  		goodsBLL.addGood(goodsInfoBean);
  		return "redirect:exchange";
  	}
    @RequestMapping(value = "/getGoodsInfoBean",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
  	public String getGoodsInfoBean(Model model,String code) {
    	GoodsInfoBean goodsInfoBean = goodsBLL.getGoodsByCode(Integer.parseInt(code));
  		return DataFormat.objToJson(goodsInfoBean);
  	}
    @RequestMapping("/updateGood")
  	public String updateGood(Model model,GoodsInfoBean goodsInfoBean) {
  		goodsBLL.updateGood(goodsInfoBean);
  		return "redirect:exchange";
  	}
    @RequestMapping("/deleteGood")
  	public String deleteGood(Model model,String code) {
    	GoodsInfoBean goodsInfoBean = new GoodsInfoBean();
    	goodsInfoBean.setCode(Integer.parseInt(code));
    	goodsInfoBean.setDeleteFlag(1);
  		goodsBLL.deleteGood(goodsInfoBean);
  		return "redirect:exchange";
  	}
    @RequestMapping("/prizeShow")
  	public String prizeShow(Model model) {
    	List<PictureUrlBean> pictureUrlList = pictureUrlBLL.getPrizeByFlag(0);
    	List<PictureUrlBean> pictureUrl = pictureUrlBLL.getPrizeByFlag(1);
    	model.addAttribute("pictureUrlList", pictureUrlList);
    	model.addAttribute("pictureUrl", pictureUrl);
  		return "xwbcreditsluckydraw/prizeShow";
  	}
    @RequestMapping(value = "/prizeUpdate")
  	public String prizeUpdate(Model model,PictureUrlBean pictureUrlBean) {
    	 PrizeBean prizeBean = new PrizeBean();
    	 ChancePrizeBean chancePrizeBean = new ChancePrizeBean();
    	 prizeBean.setChinese(pictureUrlBean.getChinese());
    	 prizeBean.setEnglish(pictureUrlBean.getEnglish());
    	 prizeBean.setCode(pictureUrlBean.getPrizeCode());
    	 chancePrizeBean.setPrizeCode(pictureUrlBean.getPrizeCode());
    	 chancePrizeBean.setNumber(pictureUrlBean.getNumber());
    	 chancePrizeBean.setProbability(pictureUrlBean.getProbability());
    	 /*
    	  * update action_prize_tb set chinese=#{chinese},english=#{english} where
    	  * code=#{code}
    	  */
    	 userLotteryBll.updatePrizeBean(prizeBean);
    	 /*
    	  * update action_lottery_chance_tb set
    	  * probability=#{probability},number=#{number} where
    	  * prize_code=#{prizeCode}
    	  * 
    	  */
    	 userLotteryBll.updateChancePrizeBean(chancePrizeBean);
    	 /*
    	  * update xuanwuba_picture_tb set picture_url=#{pictureUrl},prize_yard=#{prizeYard} where prize_code=#{prizeCode}
    	  * 
    	  */
    	 pictureUrlBLL.updatePictureUrlBean(pictureUrlBean);
    	 return "redirect:prizeShow";
  	}
    @RequestMapping(value="/prizeOneShow", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
  	public String prizeOneShow(Model model,String code) {
    	PictureUrlBean pictureUrl = pictureUrlBLL.getPrizeByPrizeCode(Integer.parseInt(code));
    	return DataFormat.objToJson(pictureUrl);
  	}

 	@RequestMapping("/openActivity")
   	public String openActivity(Model model) {
   		activityConfigBll.openActivityConfig();
   		return "redirect:exchange";
   	}
 	@RequestMapping("/closeActivity")
   	public String closeActivity(Model model) {
   		activityConfigBll.closeActivityConfig();
   		return "redirect:exchange";
   	}
}
