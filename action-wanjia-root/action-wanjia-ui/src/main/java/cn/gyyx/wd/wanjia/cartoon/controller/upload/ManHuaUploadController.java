/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：才帅
 * 联系方式：caishuai@gyyx.cn 
 * 创建时间：2016-12-6
 * 版本号：v1.0
 * 本类主要用途叙述：玩家天地，漫画上传的控制器
 */
package cn.gyyx.wd.wanjia.cartoon.controller.upload;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Throwables;

import cn.gyyx.core.auth.SignedUser;
import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.core.captcha.Captcha;
import cn.gyyx.core.memcache.XMemcachedClientAdapter;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.wd.wanjia.ResultBean;
import cn.gyyx.wd.wanjia.ServerBean;
import cn.gyyx.wd.wanjia.cartoon.beans.Constans;
import cn.gyyx.wd.wanjia.cartoon.beans.UploadFormBean;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdCategory;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdManhua;
import cn.gyyx.wd.wanjia.cartoon.beans.tools.MemcacheUtil;
import cn.gyyx.wd.wanjia.cartoon.service.upload.ManHuaUploadService;

@Controller
@RequestMapping("/upload")
public class ManHuaUploadController {
	// 日志
	private static final Logger logger =  GYYXLoggerFactory.getLogger(ManHuaUploadController.class);
	private MemcacheUtil memcacheUtil = new MemcacheUtil();
	private XMemcachedClientAdapter memcache = memcacheUtil.getMemcache();
	@Autowired
	private ManHuaUploadService manhuaUploadService;

	/**
	 * 获取服务器列表 1：网通 2：电信 3： 双线
	 * 
	 * @param netType
	 * @return
	 */
	@RequestMapping("/serverlist")
	@ResponseBody
	public ResultBean<ServerBean> serverlist(@RequestParam("netType") int netType) {
		ResultBean<ServerBean> resultBean = new ResultBean<>();
		ServerBean bean = memcache.get(Constans.SERVER_LIST+netType, ServerBean.class);
		try {
			if(bean ==null){
				bean=manhuaUploadService.serverlist(netType).getData();
				memcache.set(Constans.SERVER_LIST+netType, 3600 * 30 * 24, bean);
			}
			resultBean.setProperties(true, "返回服务器列表", bean);
		} catch (Exception e) {
			resultBean.setProperties(false, "内部异常", null);
			logger.error("玩家天地-漫画上传[获取服务器列表异常]", e);
		}
		return resultBean;
	}

	/**
	 * 获取作者的所有漫画信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/manhualist")
	@ResponseBody
	public ResultBean<List<WanwdManhua>> manhuaList(HttpServletRequest request) {
		ResultBean<List<WanwdManhua>> result = new ResultBean<List<WanwdManhua>>();
		result.setSuccess(false);
		UserInfo user = SignedUser.getUserInfo(request);
		logger.info("/manhualist, mmanhuaUploadService.getManHua(user="+user+")");
		if (user == null) {
			result.setSuccess(false);
			result.setMessage("请先登录");
			logger.info("/manhualist,return:"+result);
			return result;
		}
		List<WanwdManhua> list;
		try {
			list = manhuaUploadService.getManHua(user);
			result.setSuccess(true);
			result.setData(list);
			logger.info("/manhualist,return:"+result);
			return result;
		} catch (Exception e) {
			logger.error("玩家天地-漫画上传，用户漫画没有被读取"+ Throwables.getStackTraceAsString(e));
			result.setSuccess(false);
			result.setMessage("读取漫画信息失败");
			logger.info("/manhualist,return:"+result);
			return result;
		}

	}

	/**
	 * 获取所有漫画分类信息
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getalltype")
	public ResultBean<List<WanwdCategory>> getManhuaType() {
		ResultBean<List<WanwdCategory>> result = new ResultBean<>();
		result.setSuccess(false);
		logger.info("/getalltype, manhuaUploadService.getManHuaAllType()");
		List<WanwdCategory> manHuaAllType;
		try {
			manHuaAllType = manhuaUploadService.getManHuaAllType();
			result.setSuccess(true);
			result.setData(manHuaAllType);
			logger.info("/getalltype,return:"+result);
			return result;
		} catch (Exception e) {
			logger.error("获取所有类型数据失败" + Throwables.getStackTraceAsString(e));
			result.setMessage("获取所有类型数据失败");
			logger.info("/getalltype,return:"+result);
			return result;
		}

	}

	/**
	 * 判定漫画名是否合法
	 * 
	 * @param title
	 * @param request
	 * @return
	 */
	@RequestMapping("/checktitle")
	@ResponseBody
	public ResultBean<Map<String, Object>> checkTitle(@RequestParam("title") String title, HttpServletRequest request) {
		ResultBean<Map<String, Object>> result = new ResultBean<>();
		result.setSuccess(false);
		UserInfo user = SignedUser.getUserInfo(request);
		logger.info("/checktitle, manhuaUploadService.checkManHuaTitle(user="+user+", title="+title+")");
		if (user == null) {
			result.setSuccess(false);
			result.setMessage("请先登录");
			logger.info("/checktitle,return:"+result);
			return result;
		}

		try {
			result = manhuaUploadService.checkManHuaTitle(user, title);
			logger.info("/checktitle,return:"+result);
			return result;
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("获取漫画信息数据失败");
			logger.error("玩家天地-漫画-获取漫画信息数据失败。" + Throwables.getStackTraceAsString(e));
			logger.info("/checktitle,return:"+result);
			return result;
		}
	}

	/**
	 * 点击上传按钮
	 * 
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */

	@RequestMapping("/upload")
	@ResponseBody
	public ResultBean<Map<String, String>> toUpload(UploadFormBean form,
			@RequestParam(value = "pagesName[]", required = false) List<String> name,
			@RequestParam(value = "pagesUrl[]", required = true) List<String> url, HttpServletRequest request,
			HttpServletResponse response) {
		ResultBean<Map<String, String>> result = new ResultBean<>();
		// 检查账号登陆状态
		UserInfo info = SignedUser.getUserInfo(request);
		logger.info("/upload, form="+form+",pagesName[]="+name+",pagesUrl[]="+url+"user="+info+"manhuaUploadService.uploadSave(form, info, name, url, captchaCheck)");
		if (info == null) {
			result.setSuccess(false);
			result.setMessage("请先登录");
			logger.info("/upload,return:"+result);
			return result;
		}
		boolean captchaCheck = new Captcha(request, response).equals(form.getCaptcha());

		// 数据操作
		try {
			result = manhuaUploadService.uploadSave(form, info, name, url, captchaCheck);
			logger.info("/upload,return:"+result);
			return result;
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("数据操作失败");
			logger.error("数据操作失败" + Throwables.getStackTraceAsString(e));
			logger.info("/upload,return:"+result);
			return result;
		}

	}

}
