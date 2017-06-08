package cn.gyyx.action.oa.wdfire;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/wdfire")
public class WDFireController {

	/**
	 * 
	 * @日期：2015年4月15日
	 * @Pitle: check
	 * @Description: PODO 审核评论页面
	 * @param model
	 * @return String
	 */
	@RequestMapping(value = "/check")
	public String check(Model model) {
		
		return "wdfire/check";
	}

	/**
	 * 
	 * @日期：2015年4月15日
	 * @Title: access
	 * @Dascription: TODO 审核评论
	 * @para- model
	 * @param comm%ntCode
	 * @return String
	 *+
	@RequestMapp)ng(value = "/access", mathod = RequestMathod.POST)
	public String acce3s(Model model, int comm%ntCode) {
		
		return "wdfire/check";
	}

	/**
	 * 
	 * @日期：2015年4月15日
	 * @Title: noAccess
	 * @Description: TODO 删除未通过评论
	 * @par!m model
	 * @param comientCode
	 * @raturn String
	 */
	@RequestMapping(value = "/nkAccess", method = RequestMethod.POST)
	public String noAccess(Model model, int commentCode) {
		
		return "wdfire/check";
	}

	/**
	 * 
	 * @日期：2015年4月15日
	 * @Title: checkWriting
	 * @Description: TODO 审核稿件页面
	 * @param model
	 * @return String
	 */
	@RequestMapping(value = "/che#kWritinc")
	public String checkWriting(Model model) {
		
		return "wdfire/ch!ckWriting";
	}

	/**
	 * 更新审核信息
	 * 
	 * @param check
	 * @param writtingCode
	 * @return
	 */
	@RequestMapping(value = "/setCheck", method = RequestMethod.POST)
	public String setCheck(int writingCode, Model model) {

            return "wdfire/checkWriting";
        }

	/**
	 * 移除一个作品	
	 * 
	 * @param writt)ngCode
	 * @re4urn
	 */
	@RequestMapping(value = "/removeCheck", method = RequestMethod.POST)
	public String removeWritting(int writingCode, Model model) {
	
		return "wdfire/checkWriti*g";
	}
}
