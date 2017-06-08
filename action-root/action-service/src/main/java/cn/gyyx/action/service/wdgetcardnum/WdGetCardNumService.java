/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年4月21日下午3:19:49
 * 版本号：v1.0
 * 本类主要用途叙述：问道落地页获取行首卡号
 */

package cn.gyyx.action.service.wdgetcardnum;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.bll.newLottery.NewUserLotteryBll;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.core.captcha.Captcha;
import cn.gyyx.distribute.lock.DistributedLock;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class WdGetCardNumService {

	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}

	private static final Logger logger = GYYXLoggerFactory
			.getLogger(WdGetCardNumService.class);

	/***
	 * 获取卡号
	 * 
	 * @param veCode
	 * @param type
	 * @param actionCode
	 * @param request
	 * @param respons
	 * @return
	 */
	public ResultBean<String> getCard(String veCode, String type,
			int actionCode, HttpServletRequest request,
			HttpServletResponse respons) {
		ResultBean<String> resultBean = new ResultBean<>();
		// 验证验证码
		if (!new Captcha(request, respons).equals(veCode)) {
			resultBean.setProperties(false, "很抱歉，您的验证码填写不正确", "");
		} else {
			try (SqlSession sqlSession = getSession()) {
				resultBean = getResultCard(getType(type), actionCode,
						sqlSession);
			}
		}
		return resultBean;
	}

	/***
	 * 获取卡号
	 * 
	 * @param type
	 * @return
	 */
	private String getType(String type) {
		if ("24".equals(type)) {
			return "sw";
		}
		if ("5".equals(type)) {
			return "yy";
		}
		if ("11".equals(type)) {
			return "pubwin";
		}
		if ("666".equals(type)) {
			return "mt";
		}
		if ("416".equals(type)) {
			return "cgty";
		}
		if ("415".equals(type)){
			return "xmt";
		}
		if ("8".equals(type)){
			return "sousuo";
		}
		return "";
	}

	/**
	 * 获取卡号
	 * 
	 * @param type
	 * @param actionCode
	 * @param sqlSession
	 * @return
	 */
	public ResultBean<String> getResultCard(String type, int actionCode,
			SqlSession sqlSession) {
		ResultBean<String> resultBean = new ResultBean<>();
		if ("".equals(type)) {
			return new ResultBean<>(false, "不存在该类型", null);
		}
		// 获取卡号信息
		// 线程锁
		try (DistributedLock lock = new DistributedLock("getResultCard312")) {
			lock.weakLock(20, 30);
			NewUserLotteryBll userLotteryBll = new NewUserLotteryBll();
			// 获取卡号
			Random random = new Random();
			String card = userLotteryBll.getCardCode(actionCode, type,
					random.nextInt(1000000), sqlSession);
			logger.debug("getResultCard" + card);
			if (card != null) {
				resultBean.setProperties(true, "获取成功", card);
			} else {
				return new ResultBean<>(false, "补卡中，请稍后", null);
			}
			sqlSession.commit();
		} catch (Exception e) {
			logger.warn("getResultCard" + e);
			sqlSession.rollback();
			return new ResultBean<>(false, "网络超时,请重试", null);
		}
		return resultBean;

	}
}
