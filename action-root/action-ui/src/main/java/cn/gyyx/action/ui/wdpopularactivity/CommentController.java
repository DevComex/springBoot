/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：WdPopularActivity
 * @作者：范佳琪
 * @联系方式：fanjiaqig@gyyx.cn
 * @创建时间： 2016年03月14日
 * @版本号：
 * @本类主要用途描述：评论信息控制器
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.ui.wdpopularactivity;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

@Controller
@RequestMapping(value = "/popularactivty")
public class CommentController {

	private static final Logger logger = GYYXLoggerFactory
			.getLogger(CommentController.class);
	
	/**
	 * @Title addCommentBean
	 * @Description 添加投票信息
	 * @author fanjiaqi
	 * @param request response  nickName  comment  code
	 * @return
	 */
	@RequestMapping(value = "/addCommentBean")
	@ResponseBody
	public ResultBean<String> addCommentBean(){
		return  new ResultBean<>(false, "谢谢参与，活动已结束", null);
	}
	
	
	/**
	 * @Title addCommentBean
	 * @Description 评论信息展示
	 * @author fanjiaqi
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/show")
	@ResponseBody
	public ResultBean<String> show(){
		return new ResultBean<>(false, "谢谢参与，活动已结束", null);
	}
}
