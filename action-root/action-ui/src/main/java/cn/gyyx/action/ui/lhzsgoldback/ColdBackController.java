/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：LhzsGoldBack
 * @作者：范佳琪
 * @联系方式：fanjiaqig@gyyx.cn
 * @创建时间： 2016年04月13日
 * @版本号：
 * @本类主要用途描述：金币返还数据控制器
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.ui.lhzsgoldback;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.gyyx.action.beans.ResultBean;

@Controller
@RequestMapping(value = "/lhzsGoldBack")
public class ColdBackController {
	@RequestMapping("/index")
	public String index(Model model){
		return "LHZSGoldBack/index";
	}
	
	/**
	 * @Title findByAccount
	 * @Description 判断用户是否符合返还条件
	 * @author fanjiaqi
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/findByAccount")
	@ResponseBody
	public ResultBean findByAccount(HttpServletRequest request){
		
		ResultBean resultBean = new ResultBean(false, "谢谢参与，活动已结束", null);
		return resultBean;
	}
	
	/**
	 * @Title addReturnBean
	 * @Description 申请返还金币
	 * @author fanjiaqi
	 * @param account
	 * @return
	 */
	@RequestMapping(value = "/addReturnBean")
	@ResponseBody
	public ResultBean<String> addReturnBean(HttpServletRequest request){
		ResultBean<String> resultBean = new ResultBean<String>(false, "谢谢参与，活动已结束", null);
		return resultBean;
	}
}
