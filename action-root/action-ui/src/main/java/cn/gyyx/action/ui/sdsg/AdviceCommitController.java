/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：java版账户
 * @作者：luozhenyu
 * @联系方式：luozhenyu@gyyx.cn
 * @创建时间：2016年11月15日
 * @版本号：1.0
 * @本类主要用途描述：    神道三国——你提我改
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.ui.sdsg;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.sdsg.advice.SdsgAdviceBean;
import cn.gyyx.action.beans.sdsg.advice.SdsgAdviceForm;
import cn.gyyx.action.service.sdsg.advice.ISdsgAdviceService;
import cn.gyyx.action.service.sdsg.advice.Impl.SdsgAdviceServiceImpl;
import cn.gyyx.action.ui.WeChatBaseController;
import cn.gyyx.core.captcha.Captcha;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * 神道三国——你提我改
 * 
 * @ClassName: AdviceCommitController
 * @description AdviceCommitController
 * @author luozhenyu
 * @date 2016年11月15日
 */
@Controller
@RequestMapping("/sdsgbugcommit")
public class AdviceCommitController extends WeChatBaseController {

	private static final Logger LOG = GYYXLoggerFactory.getLogger(AdviceCommitController.class);

	private ISdsgAdviceService iSdsgAdviceService = null;

	@RequestMapping("/sdsgInfo")
	public String showTextDetail(Model model, HttpServletRequest request, HttpServletResponse response) {

		return "sdsg/sdsg_tigai";
	}

	/**
	 * 插入投稿
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/contributions", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map<String, String>> adviceInsert(@Valid SdsgAdviceForm form, BindingResult bindingResult,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> result = new HashMap();
		LOG.info("提交建议开始-:title:{},account:{},content{}",new Object[]{form.getTitle(),form.getAccount(),form.getContent()});
		FieldError error = bindingResult.getFieldError();
		if (error != null) {
			String errormessage = error.getDefaultMessage();
			result.put("message", errormessage);
			result.put("isSuccess", "false");
			result.put("data", null);
			LOG.info("建议提交结束:表单填写错误,{}",errormessage);
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		if ("请选择".equals(form.getServer())) {
			result.put("message", "区服不能为空");
			result.put("isSuccess", "false");
			result.put("data", null);
			LOG.info("建议提交结束:区服没有选择,{}");
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		String CACHE=form.getCAPTCHA();
		if (!new Captcha(request, response).equals(CACHE)) {
			result.put("message", "验证码错误");
			result.put("isSuccess", "false");
			result.put("data", null);
			LOG.info("建议提交结束:验证码错误");
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		SdsgAdviceBean sdsgAdviceBean = new SdsgAdviceBean();
		BeanUtils.copyProperties(form, sdsgAdviceBean);
		sdsgAdviceBean.setCreateTime(new Date());
		iSdsgAdviceService = new SdsgAdviceServiceImpl();
		if (iSdsgAdviceService.insertSdsgAdvice(sdsgAdviceBean)) {
			result.put("isSuccess", "true");
			result.put("message", "投稿插入成功");
			result.put("data", null);
			LOG.info("建议提交结束:表单填写成功");
		} else {
			result.put("isSuccess", "false");
			result.put("message", "投稿插入失败");
			result.put("data", null);
			LOG.info("建议提交结束:插入数据错误");
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	

	/**
	 * 获取神道三国区服列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value="/getRegion", method = RequestMethod.POST)
	private ResultBean<String> getRegion(HttpServletRequest request) {
		LOG.info("获取区服");
		String result = "";
		ResultBean<String> resultBean = new ResultBean<String>();
		try {
			result = new SdsgAdviceServiceImpl().getRegion();
			resultBean.setProperties(true, "success", result);
		} catch (Exception e) {
			resultBean.setProperties(false, "网络不给力！！", "网络不给力！！");
		}
		if (result.equals("{}")) {
			resultBean.setProperties(false, "网络不给力！！", "网络不给力！！");
		}
		if (result.equals("")) {
			resultBean.setProperties(false,"网络不给力！！", "网络不给力！！");
		}
		LOG.info("获取区服结束");
		return resultBean;
	}
}