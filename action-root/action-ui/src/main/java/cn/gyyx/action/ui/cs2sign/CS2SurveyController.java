/*************************************************
       Copyright ©, 2016, GY Game
       Author: chenpeilan
       Created: 2016年5月3日
       Note：创世2调查问卷活动控制器
************************************************/
package cn.gyyx.action.ui.cs2sign;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;

/**
 * @ClassName: CS2SurveyController
 * @Description: TODO 创世2调查问卷活动控制器
 * @author chenpeilan chenpeilan@gyyx.cn
 * @date 2016年5月3日 下午3:41:18
 *
 */
@Controller
@RequestMapping(value = "/cs2survey")
public class CS2SurveyController {

	
	/**
	 * 
	* @Title: toIndex
	* @Description: TODO 活动首页
	* @param @param model
	* @param @param request
	* @param @return    
	* @return String    
	* @throws
	 */
	@RequestMapping("/index")
	public String toIndex(Model model, HttpServletRequest request) {
		return "cs2Survey/index";
	}
	
	/**
	 * 
	* @Title: addSurveyInfoBean
	* @Description: TODO 提交调查问卷信息
	* @param @param surveyInfoBean
	* @param @return    
	* @return ResultBean<Boolean>    
	* @throws
	 */
	@RequestMapping(value = "/addSurvey", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResultBean<Boolean> addSurveyInfoBean() {
		return new ResultBean<Boolean>(false, "谢谢参与，活动已结束", null);

	}

}
