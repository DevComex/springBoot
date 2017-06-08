/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年9月22日上午10:07:50
 * 版本号：v1.0
 * 本类主要用途叙述：我是预测帝的oa后台审核界面
 */

package cn.gyyx.action.oa.predicable;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping(value = "/oapredicable")
public class OaPredicableController {
	@RequestMapping(value = "/account")
	public String toAccount(){
		
		return "predicable/account";
	}
	
	/**
	 * 作品审核界面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/writingInfo")
	public String toWritingInfo(Model model){
		
		return "predicable/writingCheck";
	}
	/**
	 * 通过评论类型查出10评论
	 * @author MT
	 * @param commentStatus
	 * @param model
	 * @return
	 */
	@RequestMapping("/checkDisInfo")
	public String checkDisInfo (Model model) {

		return "predicable/checkDiscuss";
	}
	/**
	 * 作品审核界面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/checkDisInfo1/{lip}")
	public String commentCheck(Model model,@PathVariable("lip") String nowpageNew){

		return "predicable/pageDis";
	}
	/**
	 * 返回作品列表
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/writingInfoList")
	public String toWritingInfoList(Model model,@RequestParam(value = "pageNum") int pageNo){

		return "predicable/listWriting";
	}
	
	/**
	 * 审核评论通过
	 * 
	 * @param commentCode
	 * @return
	 */
	@RequestMapping("/checkCommentSuccess/{commentCode}")
	public String checkCommentSuccess() {
			
		return "predicable/pageDis";
	}

	/**
	 * 审核评论不通过
	 * 
	 * @param commentCode
	 * @return
	 */
	@RequestMapping("/checkCommentFail/{commentCode}")
	public String checkCommentFail() {

		return "predicable/pageDis";

	}


	/**
	 * 根据服务器和账户筛选
	 * 
	 * @param request
	 * @param suit
	 * @param occupation
	 * @return
	 */
	@RequestMapping(value = "/getWritingByFilter")
	public String getWritingByFilter() {

		return "predicable/listWriting";
	}

	/**
	 * 筛选后翻页
	 * 
	 * @param request
	 * @param pageNo
	 * @return
	 */
	@RequestMapping(value = "/getWritingByFilterForPage")
	public String getWritingByFilterForPage() {
		
		return "predicable/listWriting";
	}

}
