/*------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：马涛
 * 联系方式：
 * 创建时间： 2014年12月15日 
 * 版本号：v1.0 
 * 本类主要用途描述： 
 * 
-------------------------------------------------------------------------*/
package cn.gyyx.action.oa;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 通过该controller不执行任何逻辑，仅仅是跳转功能
 * @author MT
 *
 */
@Controller
public class CheckDefaultController {
	
	/**
	 * 默认跳转到index.vn页
	 * 功能：跳转到index，在index页中再选择是进行图片审核还是评论审核
	 * @return
	 */
	@RequestMapping("/")
	public String defaultPage() {
		return "index";
	}
	
	/**
	 * 跳转到评论审核页
	 * @return
	 */
	@RequestMapping("/goCheckDiscuss")
	public String goCheckDiscuss(Model model) {
		model.addAttribute("nowPage", "1");
		return "checkDiscuss";
	}
	
	/**
	 * 跳转到图片审核页
	 * @return
	 */
	@RequestMapping("/goCheckImg")
	public String goCheckImg (Model model) {
		model.addAttribute("nowPage", "1");
		return "checkImg";
	}
	
}
