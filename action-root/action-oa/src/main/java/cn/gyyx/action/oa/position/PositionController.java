/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年2月18日上午9:59:38
 * 版本号：v1.0
 * 本类主要用途叙述：职位申请后台的控制层
 */

package cn.gyyx.action.oa.position;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.positionApply.ApplyInfoBean;
import cn.gyyx.action.bll.positionApply.ApplyInfoBll;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

@Controller
@RequestMapping(value = "/oaposition")
public class PositionController {
	private ApplyInfoBll applyInfoBll = new ApplyInfoBll();
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(PositionController.class);

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

	/**
	 * 删除申請接口
	 * 
	 * @param model
	 * @param request
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/removeApplyInfo", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> removeApplyInfo(Model model,
			HttpServletRequest request,
			@RequestParam(value = "code") String code) {
		ResultBean<String> resultBean = new ResultBean<String>();
		SqlSession sqlSession = getSession();
		try {
			try {
				// 刪除
				applyInfoBll.removeApplyInfoByCode(Integer.parseInt(code),
						sqlSession);
				resultBean.setProperties(true, "删除成功", "");
				sqlSession.commit(true);
			} catch (Exception e) {
				// TODO: handle exception
				logger.warn("removeApplyInfo" + e.toString());
				sqlSession.rollback();
				resultBean.setProperties(false, "删除失败", "");
			}

		} catch (Exception e) {
			logger.warn("insertLimitBean" + e.toString());
		} finally {
			sqlSession.close();
		}
		return resultBean;
	}

	/***
	 * 后台查看首页
	 * 
	 * @param model
	 * @param httpResponse
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Model model, HttpServletResponse httpResponse) {
		SqlSession sqlSession = getSession();
		try {
			// 获取拥有的职位信息
			List<String> positionsList = applyInfoBll
					.getApplyInfoPostion(sqlSession);
			model.addAttribute("positionsList", positionsList);
			// 所有申请信息
			List<ApplyInfoBean> listApplyInfoBeans = applyInfoBll
					.getAllApplyInfoBeans(sqlSession);
			model.addAttribute("listApplyInfoBeans", listApplyInfoBeans);
		} catch (Exception e) {
			logger.warn("insertLimitBean" + e.toString());
		} finally {
			sqlSession.close();
		}
		return "position/index";
	}

	/***
	 * 跳转到具体信息页面
	 * 
	 * @param model
	 * @param httpResponse
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public String info(Model model, HttpServletResponse httpResponse,
			@RequestParam(value = "code") String code) {
		logger.info("info" + code);
		SqlSession sqlSession = getSession();
		try {
			try {
				ApplyInfoBean applyInfoBean = applyInfoBll.getApplyInfoByCode(
						Integer.parseInt(code), sqlSession);
				model.addAttribute("applyInfoBean", applyInfoBean);

			} catch (Exception e) {
				// TODO: handle exception
				logger.warn("info" + e.toString());
			}

		} catch (Exception e) {
			logger.warn("insertLimitBean" + e.toString());
		} finally {
			sqlSession.close();
		}
		return "position/info";
	}

}
