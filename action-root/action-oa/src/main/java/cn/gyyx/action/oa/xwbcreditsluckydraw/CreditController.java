/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：xuanwuba
 * @作者：yangyusheng
 * @联系方式：yangyusheng@gyyx.cn
 * @创建时间： 2015年7月10日 下午2:34:58
 * @版本号：
 * @本类主要用途描述：积分记录控制器
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.oa.xwbcreditsluckydraw;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.xwbcreditsluckydraw.CreditsBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.PageBean;
import cn.gyyx.action.bll.xwbcreditsluckydraw.CreditBLL;
import cn.gyyx.action.bll.xwbcreditsluckydraw.MaterialAuditBll;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

@Controller
@RequestMapping("/xwbJiFen")
public class CreditController {

	private static final Logger logger = GYYXLoggerFactory
			.getLogger(PlayerinfoController.class);
	private CreditBLL creditBll = new CreditBLL();

	@RequestMapping("/credits")
	public String credits(Model model, Integer pageNum, CreditsBean credit,
			String acc, String time, String ty)
			throws UnsupportedEncodingException {
		logger.debug("第几页：" + pageNum);
		List<CreditsBean> creditList = null;
		int creditsNum = 0;
		// 直接进入默认第一页
		if (pageNum == null) {
			pageNum = 1;
		}
		credit.setPage(pageNum);
		if (acc != null || time != null || ty != null) {
			if (ty != null) {
				ty = new String(ty.getBytes("ISO-8859-1"), "UTF-8");
			}
			CreditsBean bean = new CreditsBean(acc, time, ty, pageNum);
			creditList = creditBll.getCredits(bean);
			creditsNum = creditBll.getCreditsNum(bean);
			model.addAttribute("acc", acc);
			model.addAttribute("time", time);
			model.addAttribute("ty", ty);
		} else {
			creditList = creditBll.getCredits(credit);
			creditsNum = creditBll.getCreditsNum(credit);
			model.addAttribute("acc", credit.getAccount());
			model.addAttribute("time", credit.getDate());
			model.addAttribute("ty", credit.getType());
		}
		PageBean page = new PageBean(pageNum, creditsNum);
		model.addAttribute("page", page);
		model.addAttribute("creditList", creditList);
		return "xwbcreditsluckydraw/credits";
	}

	/**
	 * 
	 * @日期：2015年7月10日
	 * @Title: getSumCredit
	 * @Description: TODO 根据用户查询总积分
	 * @param account
	 * @param model
	 * @return String
	 */
	@RequestMapping("/getSumCredit")
	public String getSumCredit(String account, Model model) {
		logger.debug("account", account);
		int sumNum = creditBll.getSumCredit(account);
		model.addAttribute("sumNum", sumNum);
		model.addAttribute("sumAcc", account);
		return "xwbcreditsluckydraw/credits";
	}
	
	/**
	 * 管理员直接发放积分
	 * @param creditsBean
	 * @param model
	 * @return
	 */
	@RequestMapping("/giveCredit")
	@ResponseBody
	public String giveCredit(CreditsBean creditsBean, Model model) {
		creditsBean.setEnterTime(new Date());
		creditBll.judge(creditsBean);
		return "1";
	}
	
}
