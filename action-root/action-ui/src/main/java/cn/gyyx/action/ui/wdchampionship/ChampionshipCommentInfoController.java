/*************************************************
       Copyright ©, 2015, GY Game
       Author: 柳佳琦
       Created: 2016年3月25日
       Note：问道名人争霸赛评论控制层
************************************************/
package cn.gyyx.action.ui.wdchampionship;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.PageBean;
import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.wdchampionship.ChampionshipCommenttInfoBean;
import cn.gyyx.action.service.wdchampionship.ChampionshipCommentInfoService;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.core.captcha.Captcha;

/**
 * @ClassName: ChampionshipCommentInfoController
 * @Description: TODO 问道名人争霸赛评论控制层.
 * @author 柳佳琦 liujiaqi@gyyx.cn
 * @date 2016年3月25日 上午9:30:49
 */
@Controller
@RequestMapping("/championship")
public class ChampionshipCommentInfoController {
	private ChampionshipCommentInfoService service = new ChampionshipCommentInfoService();
	private static final Logger LOG = GYYXLoggerFactory
			.getLogger(ChampionshipCommentInfoController.class);
	
	@RequestMapping("/{page}")
	public String index(@PathVariable("page")String page){
		return "wdchampionship/" + page;
	}

	/**
	* @Title: addComment
	* @Description: TODO 提交评论
	* @date 2016年3月25日 上午9:42:03
	* @param comment    
	* @return ResultBean<String>
	 */
	@RequestMapping(value="/submitComment",method=RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> addComment(ChampionshipCommenttInfoBean comment
		 ,String captchacode,HttpServletRequest request,HttpServletResponse response){
		LOG.info("commentBean :" + comment + " , validateCode:" + captchacode);
		ResultBean<String> result =  new ResultBean<String>(false, null, null);
		if (!new Captcha(request, response).equals(captchacode)) { //验证码不正确
			result.setProperties(false, "很抱歉，您的验证码填写不正确", "");
		}else{
			result = service.addComment(comment);
		}
		LOG.info("resultBean: " + result);
		return result;
	}
	
	/**
	* @Title: getComments
	* @Description: TODO 分页查询评论
	* @date 2016年3月25日 上午9:42:28
	* @param typeOfYear
	* @param currentPage   
	* @return ResultBean<PageBean<ChampionshipCommenttInfoBean>>
	 */
	@RequestMapping(value="/getComment",method=RequestMethod.GET)
	@ResponseBody															  
	public ResultBean<PageBean<ChampionshipCommenttInfoBean>> getComments(int typeOfYear,int currentPage){
		LOG.info("typeOfYear :" + typeOfYear + ",currentPage: " + currentPage);
		return service.selectComment(false, typeOfYear, currentPage);
	}
	
	/**
	* @Title: getTopComments
	* @Description: TODO 查询最新的99条审核通过的评论
	* @date 2016年3月25日 上午9:42:41
	* @param @param typeOfYear
	* @param @return    
	* @return ResultBean<List<ChampionshipCommenttInfoBean>>
	 */
	@RequestMapping(value="/getTopComment",method=RequestMethod.GET)
	@ResponseBody
	public ResultBean<List<ChampionshipCommenttInfoBean>> getTopComments(int typeOfYear){
		LOG.info("typeOfYear :" + typeOfYear );
		return service.selectTopComment(typeOfYear);
	}
}
