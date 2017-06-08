/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年2月17日上午9:49:13
 * 版本号：v1.0
 * 本类主要用途叙述：职位申请的控制层
 */

package cn.gyyx.action.ui;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.positionApply.ApplyInfoBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.action.service.positionApply.ApplyInfoService;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

@Controller
@RequestMapping(value = "/position")
public class PositionApplyController {
	private ApplyInfoService applyInfoService = new ApplyInfoService();
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(PositionApplyController.class);

	/**
	 * 
	 * @日期：2015年3月19日
	 * @Title: getSession
	 * @Description: TODO 获取session
	 * @return SqlSession
	 */
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}

	/***
	 * 申请职位类别
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/introduce")
	public String tointroduce(Model model) {

		return "positionApply/introduce";
	}

	/***
	 * 申请职位列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/positionlist")
	public String toPositionlist(Model model) {
		return "positionApply/positionlist";
	}

	/***
	 * 联系我页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/contactme")
	public String toContactme(Model model) {
		return "positionApply/contactme";
	}

	/***
	 * 职位详细信息页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("positiontype/{type}")
	public String gamedeveloper(Model model, @PathVariable("type") String type,
			HttpServletRequest request, HttpServletResponse response) {
		// 是否是微信浏览器
		String ua = request.getHeader("user-agent").toLowerCase();
		if (ua.indexOf("micromessenger") == -1 || ua.indexOf("windows") > -1) {// 是微信浏览器
			try {
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().write(
						String.valueOf("<h1>此功能只能在微信浏览器中使用！</h1>"));
				response.getWriter().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			return "positionApply/position/" + type;
		} catch (Exception e) {
			// TODO: handle exception
			return "positionApply/positionlist";
		}

	}

	/***
	 * 申请职位主页
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String toIndex(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		// 是否是微信浏览器
		String ua = request.getHeader("user-agent").toLowerCase();
		if (ua.indexOf("micromessenger") == -1 || ua.indexOf("windows") > -1) {// 是微信浏览器
			try {
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().write(
						String.valueOf("<h1>此功能只能在微信浏览器中使用！</h1>"));
				response.getWriter().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "positionApply/index";
	}

	/***
	 * 申请职位表单
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/apply")
	public String toIndex(Model model,
			@RequestParam(value = "position") String position,
			HttpServletRequest request, HttpServletResponse response) {
		// 是否是微信浏览器

		String ua = request.getHeader("user-agent").toLowerCase();
		if (ua.indexOf("micromessenger") == -1 || ua.indexOf("windows") > -1) {// 是微信浏览器
			try {
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().write(
						String.valueOf("<h1>此功能只能在微信浏览器中使用！</h1>"));
				response.getWriter().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// 转码
		try {
			ApplyInfoBean applyInfoBean = new ApplyInfoBean();
			position = new String(position.getBytes("iso8859-1"), "UTF-8");
			applyInfoBean.setPosition(position);
			position = applyInfoBean.positionGet(applyInfoBean).getPosition();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			position = "";
			return "positionApply/index";
		}
		model.addAttribute("position", position);
		return "positionApply/apply";
	}

	/***
	 * 分享
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/share")
	public String share(Model model,
			@RequestParam(value = "num") String position,
			HttpServletRequest request, HttpServletResponse response) {
		// 是否是微信浏览器
		String ua = request.getHeader("user-agent").toLowerCase();
		if (ua.indexOf("micromessenger") == -1 || ua.indexOf("windows") > -1) {// 是微信浏览器
			try {
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().write(
						String.valueOf("<h1>此功能只能在微信浏览器中使用！</h1>"));
				response.getWriter().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		String num = "";
		// 转码
		try {
			ApplyInfoBean applyInfoBean = new ApplyInfoBean();
			position = new String(position.getBytes("iso8859-1"), "UTF-8");
			num = position;
			applyInfoBean.setPosition(position);
			position = applyInfoBean.positionGet(applyInfoBean).getPosition();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			position = "";
			return "positionApply/index";
		}
		model.addAttribute("num", num);
		return "positionApply/share";
	}

	/***
	 * 增加信息
	 * 
	 * @param model
	 * @param applyInfoBean
	 * @return
	 */
	@RequestMapping(value = "/addInfo", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> addInfo(Model model, ApplyInfoBean applyInfoBean) {
		ResultBean<String> resultBean = new ResultBean<String>();
		logger.info("addInfo" + applyInfoBean.toString());
		try (SqlSession sqlSession = getSession()) {
			try {
				resultBean = applyInfoService.addApplyInfo(applyInfoBean,
						sqlSession);
				sqlSession.commit(true);
				return resultBean;
			} catch (Exception e) {
				// TODO: handle exception
				logger.warn(e.toString());
				sqlSession.rollback();
				resultBean.setProperties(false, "申请失败，请重试", "");
				return resultBean;
			}
		}
	}
}
