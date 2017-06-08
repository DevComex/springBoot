/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年3月14日下午4:34:01
 * 版本号：v1.0
 * 本类主要用途叙述：问道十年分数的
 */

package cn.gyyx.action.oa.wdtenyear;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.predicable.ResultBeanWithPage;
import cn.gyyx.action.beans.tenyearpicture.ScoreOaBean;
import cn.gyyx.action.bll.wdtenyearspicture.ScoreBll;
import cn.gyyx.action.bll.wdtenyearspicture.ScoreLogBll;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.action.oa.christmasLight.OaChristmasLight;
import cn.gyyx.action.service.WdTenYearService;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

@Controller
@RequestMapping(value = "/oawtenyearscore")
public class WDTenYearsScoreController {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(OaChristmasLight.class);
	private ScoreBll scoreBll = new ScoreBll();
	private ScoreLogBll scoreLogBll = new ScoreLogBll();
	private WdTenYearService wedService = new WdTenYearService();

	/**
	 * 
	 * @日期：2015年3月19日
	 * @Title: getSession 获取session
	 * @return SqlSession
	 */
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}

	@RequestMapping("/index")
	public String checkDisInfo(Model model) {
		SqlSession session = getSession();
		try {
			// 参与活动拥有积分的人数
			int countAccount = scoreBll.getCountByAccount(session);
			model.addAttribute("countAccount", countAccount);
			// 各类活动名称
			List<String> listAction = scoreLogBll.getScoreLogHdName(session);
			model.addAttribute("listAction", listAction);
		} catch (Exception e) {
			logger.warn("insertLimitBean" + e);
			session.rollback();
		} finally {
			session.close();
		}
		return "wdtenyear/scoreoa";
	}

	/***
	 * 获取列表具体信息
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getInfo")
	public String getInfo(Model model,
			@RequestParam(value = "account") String account,
			@RequestParam(value = "pageNo") int pageNo,
			@RequestParam(value = "pageSize") int pageSize) throws Exception {
		SqlSession session = getSession();
		try {
			account = new String(account.getBytes("iso8859-1"), "UTF-8");
			ResultBeanWithPage<List<ScoreOaBean>> result = wedService
					.getScoreOaBeans(account, pageNo, pageSize, session);
			model.addAttribute("result", result);
			model.addAttribute("pageNo", pageNo);
		} catch (Exception e) {
			logger.warn("getInfo" + e);
			session.rollback();
		} finally {
			session.close();
		}
		return "wdtenyear/scoreListoa";
	}

	/**
	 * 导出查询数据
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/exportScore")
	@ResponseBody
	public ResultBean<String> exportSearchSum(
			@RequestParam(value = "account") String account,
			HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		ResultBean<String> resultBean = new ResultBean<String>();
		SqlSession sqlSession = getSession();
		try {
			account = (new String(account.getBytes("iso8859-1"), "UTF-8"));
			wedService.getScoreAccountXLS(response, account, sqlSession);
			sqlSession.commit();
			resultBean.setProperties(true, "导出成功", "导出成功");
		} catch (Exception e) {
			logger.warn("exportSearchSum" + e);
			resultBean.setProperties(false, "导出失败", "导出失败");
			sqlSession.rollback();
		} finally {
			sqlSession.close();
		}
		return resultBean;
	}

}
