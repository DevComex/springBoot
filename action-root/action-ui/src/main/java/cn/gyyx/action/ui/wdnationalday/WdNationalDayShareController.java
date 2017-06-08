package cn.gyyx.action.ui.wdnationalday;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.ui.WeChatBaseController;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

@Controller
@RequestMapping("/wdnationalshare")
public class WdNationalDayShareController extends WeChatBaseController {
	private static final Logger logger = GYYXLoggerFactory.getLogger(WdNationalDayShareController.class);
	private static int actionCode = 395;
	private static String actionName = "2016国庆活动";
	private String developType = "release";

	protected void setDevelopType(String type) {
		this.developType = StringUtils.isEmpty(type) ? "release" : type;
	}

	@RequestMapping("/uppage")
	public String index(Model model) {

		return "nationalDay/upPicture";
	}

	@RequestMapping("/uptext")
	public String uptext(Model model) {

		return "nationalDay/up_txt";
	}

	@RequestMapping("/sharepage")
	public String sharepage(Model model) {

		return "nationalDay/share";
	}


	/*
	 * 上传图片
	 */
	@RequestMapping("/shareimages")
	@ResponseBody
	public ResultBean<Integer> addImages(HttpServletRequest request,
			HttpServletResponse response, @Param("OpenId") String OpenId) {
		ResultBean<Integer> result = new ResultBean<Integer>(false, "活动已结束！", null);
		return result;
	}

	/*
	 * 上传文章
	 */
	@RequestMapping(value = "/sharediary", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<Integer> addDiary(HttpServletRequest request,
			HttpServletResponse response,  @Param("OpenId") String OpenId) {
		ResultBean<Integer> result = new ResultBean<Integer>(false, "活动已结束！", null);
		return result;
	}

	/*
	 * 检查作品状态
	 */
	@RequestMapping("/checkstatus")
	@ResponseBody
	public ResultBean<Integer> selectStatus(HttpServletRequest request, 
			HttpServletResponse response,  @Param("OpenId") String OpenId) {
		ResultBean<Integer> result = new ResultBean<Integer>(false, "活动已结束！", null);
		return result;
	}

	/*
	 * 查询图片审核通过次数
	 */
	@RequestMapping("/checkimgtimes")
	@ResponseBody
	public ResultBean<Integer> checkImgTimes(HttpServletRequest request, 
			HttpServletResponse response,  @Param("OpenId") String OpenId) {
		ResultBean<Integer> result = new ResultBean<Integer>(false, "活动已结束！", null);
		return result;

	}

	/*
	 * 查询日记审核通过次数
	 */
	@RequestMapping("/checkdiarytimes")
	@ResponseBody
	public ResultBean<Integer> checkDirayTimes(HttpServletRequest request, 
			HttpServletResponse response,  @Param("OpenId") String OpenId) {

		ResultBean<Integer> result = new ResultBean<Integer>(false, "活动已结束！", null);
		return result;

	}

}
