package cn.gyyx.action.oa.wdappsign;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.wdsigned.WdAppSignedConstantBean;
import cn.gyyx.action.bll.challenger.SameDataBll;
import cn.gyyx.action.bll.lottery.MemcacheUtil;
import cn.gyyx.action.bll.wdsigned.WdAppSignOaBll;
import cn.gyyx.action.service.challenger.ChallengerConstant;
import cn.gyyx.core.memcache.XMemcachedClientAdapter;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

@Controller
@RequestMapping("/wdappsignoa")
public class WdAppSignOaController {
	public static final String MEM_KEY_PREFIX = "wd_app_sign_";
	private MemcacheUtil memcacheUtil = new MemcacheUtil();
	private XMemcachedClientAdapter memcachedClientAdapter = memcacheUtil.getMemcache();
	private static final Logger logger = GYYXLoggerFactory.getLogger(WdAppSignOaController.class);

	private SameDataBll sameDataBll = new SameDataBll();

	@RequestMapping("/index")
	public String index(Model model) {

		return "/wdappsignoa/index";
	}

	@RequestMapping(value = "/modifytime", method = { RequestMethod.POST })
	@ResponseBody
	public ResultBean<String> modifytime(HttpServletRequest request, String batch) {
		ResultBean<String> res = new ResultBean<String>();

		/*
		 * //验证登陆情况a
		 * UserInfo userInfo = SignedUser.getUserInfo(request);
		 * if(userInfo == null) {
		 * res.setIsSuccess(false);
		 * res.setMessage("请先登录");
		 * return res;
		 * } else {
		 * //验证权限
		 * if(!userInfo==) {
		 * res.setIsSuccess(false);
		 * res.setMessage("您没有操作权限!");
		 * return res;
		 * }
		 */
		// wdAppSignOaBll.insert(bean);
		res.setIsSuccess(true);
		res.setMessage("修改问道APP签到活动时间成功");
		if (StringUtils.isEmpty(batch) || "null".equals(batch)) {
			res.setProperties(false, "开始时间不能为空", null);
			return res;
		}
		// String batch=String.format("%s-%s-%s",
		// bean.getYear(),bean.getMonth(),bean.getDay());
		memcachedClientAdapter.set(WdAppSignedConstantBean.MEM_KEY_PREFIX, 3600 * 30 * 24, batch);
		sameDataBll.resetSameDate(WdAppSignedConstantBean.MEM_KEY_PREFIX, batch, WdAppSignedConstantBean.ACTIVITY_CODE);
		String time = memcachedClientAdapter.get(MEM_KEY_PREFIX, String.class);
		res.setProperties(true, "获取成功", time);
		return res;
	}

	/***
	 * 从缓存中获取时间配置信息
	 * 
	 * @param sqlSession
	 * @return
	 */

	// public WdAppSignOaBean getWdAppSignOaBeanMem(String batch){
	// WdAppSignOaBean bean = new WdAppSignOaBean();
	// String memkey= batch + "_wd_app_sign_";
	// try {
	// String time = memcachedClientAdapter.get(
	// memkey, String.class);
	// } catch (Exception e) {
	// logger.warn("getWdAppSignOaBeanMem" + e);
	//// WdAppSignOaBean bean=new WdAppSignOaBean();
	// memcachedClientAdapter.set(memkey, 3600 * 5, bean);
	// return bean ;
	// }
	// return WdAppSignOaBean;
	// }
}
