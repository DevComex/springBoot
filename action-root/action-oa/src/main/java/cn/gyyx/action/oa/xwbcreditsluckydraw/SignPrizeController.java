/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：xuanwuba
 * @作者：fanyongliang
 * @联系方式：fanyongliang@gyyx.cn
 * @创建时间： 2015年9月7日
 * @版本号：1.214
 * @本类主要用途描述：签到奖励信息控制器
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.oa.xwbcreditsluckydraw;


import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.CalendarInfoBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.SignPrizeBean;
import cn.gyyx.action.bll.xwbcreditsluckydraw.SignPrizeBll;
import cn.gyyx.action.service.xuanwuba.XWBService;
import cn.gyyx.core.DataFormat;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.core.auth.SignedUser;
import cn.gyyx.core.auth.UserInfo;

@Controller
@RequestMapping("/xwbJiFen")
public class SignPrizeController {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(GoodsGetController.class);
	private SignPrizeBll signPrizeBll = new SignPrizeBll();
	private XWBService xWBService = new XWBService();
	/**
	 * 主页
	 * 
	 */
	@RequestMapping("/signPrizeIndex")
	public String searchSignIn(Model model) {
		List<SignPrizeBean> signPrizeList = signPrizeBll.getAllSignPrize();
		model.addAttribute("signPrizeList", signPrizeList);
		return "xwbcreditsluckydraw/signprizeindex";
	}
	/**
	 * 增加
	 * 
	 */
	@RequestMapping("/addSignPrice")
	public String addSignPrice(SignPrizeBean signPrizeBean,Model model) {
		signPrizeBll.addSignPrize(signPrizeBean);
		return "redirect:signPrizeIndex";
	}
	/**
	 * 修改
	 * 
	 */
	@RequestMapping("/modifySignPrice")
	public String modifySignPrice(SignPrizeBean signPrizeBean,Model model) {
		signPrizeBll.setSignPrize(signPrizeBean);
		return "redirect:signPrizeIndex";
	}
	/**
	 * 删除
	 * 
	 */
	@RequestMapping("/deleteSignPrice")
	public String deleteSignPrice(Integer code,Model model) {
		signPrizeBll.deleteSignPrize(code);
		return "redirect:signPrizeIndex";
	}
	/**
	 * 获取信息
	 * @param code
	 * @return
	 */
	@RequestMapping(value="/getSignPrizeByCode", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getSignPrizeByCode(Integer code) {
		SignPrizeBean signPrizeBean = signPrizeBll.getSignPrizeByCode(code);
		return DataFormat.objToJson(signPrizeBean);
	}
	
	/**
	 * 获取数量
	 * @return
	 */
	@RequestMapping(value="/getPrizeCountByType", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Integer getPrizeCountByType(String prizeType,String sex) {
		if(sex !=null){
			try {
				sex = new String(sex.getBytes("iso-8859-1"),"utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Integer count = signPrizeBll.getPrizeCountByType(prizeType,sex);
		return count;
	}
}
