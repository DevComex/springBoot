/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action
 * @作者：wanglei
 * @联系方式：wanglei@gyyx.cn
 * @创建时间： 2015年8月11日 
 * @版本号：v1.211
 * @本类主要用途描述：问道康师傅抽奖活动项目 控制器 后身为
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.ui;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.wdmaster.WDMasterRegisterInfoBean;
import cn.gyyx.core.DataFormat;

@Controller
@RequestMapping(value = "/wdcard")
public class WDMasterController {

	
	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/lottery")
	public String index(Model model) {
		return "wdmaster/index";
	}

	/**
	 * 用户注册
	 * @param request
	 * @param wdMasterRegisterInfoBean
	 * @return
	 */
	@RequestMapping(value = "/register" , produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String register(HttpServletRequest request, WDMasterRegisterInfoBean wdMasterRegisterInfoBean) {
		ResultBean<String> result = new ResultBean<String>(false,
				"活动已经结束", "");

			return DataFormat.objToJson(result);
	}

}
