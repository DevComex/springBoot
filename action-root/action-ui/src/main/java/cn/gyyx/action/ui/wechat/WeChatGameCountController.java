package cn.gyyx.action.ui.wechat;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.gyyx.action.beans.wechat.MappingBean;
import cn.gyyx.action.beans.wechat.WeChatCountBean;
import cn.gyyx.action.beans.wechat.WeiXinQualificationBean;
import cn.gyyx.action.bll.wechat.MappingBLL;
import cn.gyyx.action.bll.wechat.WeChatCountBLL;
import cn.gyyx.action.bll.wechat.WeiXinQualificationBLL;
import cn.gyyx.distribute.lock.DLockException;
import cn.gyyx.distribute.lock.DistributedLock;

@Controller
@RequestMapping("/wechatCount")
public class WeChatGameCountController {
	private static final Logger logger = LoggerFactory
			.getLogger(WeChatGameCountController.class);
	private WeChatCountBLL weChatCountBLL = new WeChatCountBLL();
	private MappingBLL mappingBLL = new MappingBLL();
	private WeiXinQualificationBLL weiXinQualificationBLL = new WeiXinQualificationBLL();
	
	@RequestMapping(value = "/ajaxBackstageCount")
	public String ajaxWeChatCount(
			@ModelAttribute WeChatCountBean weChatCountBean,
			HttpServletRequest request) {
		logger.info("WeChatGameCountController微信小游戏前台统计-接收参数{}:"+weChatCountBean);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<MappingBean> paraList = new ArrayList<MappingBean>();
		@SuppressWarnings("unchecked")
		Enumeration<String> paraNames = request.getParameterNames();
		int flag = 0;
		for (Enumeration<String> e = paraNames; e.hasMoreElements();) {
			String thisName = e.nextElement().toString();
			String thisValue = request.getParameter(thisName);
			if(!thisName.equals("countType") && !thisName.equals("nickName") && !thisName.equals("actionCode") && !thisName.equals("partakeTime")){
				flag++;
				paraList.add(new MappingBean("S"+flag, thisName, thisValue));
			}
		}
		logger.info("WeChatGameCountController微信小游戏前台统计-自定义参数数量{}:"+paraList.size());
		if(paraList.size() > 6){
			logger.info("WeChatGameCountController微信小游戏前台统计-自定义参数数量:{}参数超过上限！");
			return "wechat/blank";
		}
		if (weChatCountBean.getNickName() != null) {
			try {
				weChatCountBean.setNickName(new String(weChatCountBean
						.getNickName().getBytes("iso-8859-1"), "utf-8"));
			} catch (UnsupportedEncodingException e) {
				logger.info(e.toString());
			}
			logger.info("WeChatGameCountController微信小游戏前台统计-编码后的nickName{}:"+weChatCountBean.getNickName());
		}
		for (MappingBean mappingBean : paraList) {
			mappingBLL.addMappingInfo(mappingBean);
		}
		for (int i = 0; i < paraList.size(); i++) {
			switch (i) {
			case 0:
				weChatCountBean.setS1(paraList.get(0).getParaValue());
				break;
			case 1:
				weChatCountBean.setS2(paraList.get(1).getParaValue());
				break;
			case 2:
				weChatCountBean.setS3(paraList.get(2).getParaValue());
				break;
			case 3:
				weChatCountBean.setS4(paraList.get(3).getParaValue());
				break;
			case 4:
				weChatCountBean.setS5(paraList.get(4).getParaValue());
				break;
			case 5:
				weChatCountBean.setS6(paraList.get(5).getParaValue());
				break;
			default:
				break;
			}
		}
		weChatCountBean.setCountTime(new Date());
		weChatCountBean.setActionCode(1001);
		try (DistributedLock lock = new DistributedLock("count294")){
			lock.weakLock(30, 2);
			
			int lotteryTime = 0;
			if("zjjsFinish".equals(weChatCountBean.getCountType())){
				lotteryTime = 1;
			}else if("zjjsShare".equals(weChatCountBean.getCountType())){
				lotteryTime = 3;
			}else{
				lotteryTime = 0;
			}
			int count = weChatCountBLL.getDayCountByType(weChatCountBean.getCountType(),weChatCountBean.getNickName(), sdf.format(new Date()));
			WeiXinQualificationBean qua = weiXinQualificationBLL.getQualification(weChatCountBean.getNickName());				
			if(count == 0){
				if(qua == null){
					logger.info("WeChatGameCountController微信小游戏前台统计-抽奖资格表没有记录，新增抽奖记录:{}" + lotteryTime);
					weiXinQualificationBLL.addQualification(new WeiXinQualificationBean(weChatCountBean.getNickName(), lotteryTime, 294));
				}else{
					logger.info("WeChatGameCountController微信小游戏前台统计-抽奖资格表有记录，增加抽奖记录:{}" + lotteryTime);
					weiXinQualificationBLL.setTimeLottery(new WeiXinQualificationBean(weChatCountBean.getNickName(), qua.getLotteryTime()+lotteryTime, 294));
				}
			}
			logger.info("WeChatGameCountController微信小游戏前台统计-记录参数:{}" + weChatCountBean.toString());
			weChatCountBLL.accessCount(weChatCountBean);
			
		} catch (DLockException e) {
			logger.error("DLockException type:" + e.getType() + ",msg:" + e.getMessage());
		}
		return "wechat/blank";
	}
}
