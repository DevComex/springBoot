package cn.gyyx.action.dao.xwbcreditsluckydraw;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.xwbcreditsluckydraw.SignInBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class SignInDAO {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(SignInDAO.class);

	/**
	 * 
	 * @日期：2015年7月8日
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
	 * @日期：2015年7月10日
	 * @Title: addSignIn
	 * @Description: TODO 新增签到记录
	 * @return void
	 * */
	public void addSignIn(SignInBean signInBean) {
		SqlSession session = getSession();
		try {
			ISignInMapper mapper = session.getMapper(ISignInMapper.class);
			mapper.addSignIn(signInBean);
			session.commit();
		} catch (Exception e) {
			logger.warn("addSignIn" + e.toString());
		} finally {
			session.close();
		}
	}

	/**
	 * @日期：2015年7月10日
	 * @Title: getSignInByPage
	 * @Description: TODO 查询签到记录
	 * @return List<SignInBean>
	 * */
	public List<SignInBean> getSignInByPage(Map<String, Object> paramMap) {
		SqlSession session = getSession();
		try {
			ISignInMapper mapper = session.getMapper(ISignInMapper.class);
			return mapper.getSignInByPage(paramMap);
		} catch (Exception e) {
			logger.warn("getSignInByPage" + e.toString());
		} finally {
			session.close();
		}
		return null;
	}

	/**
	 * @日期：2015年7月10日
	 * @Title: getSignInCount
	 * @Description: TODO 查询签到记录数量
	 * @return List<SignInBean>
	 * */
	public Integer getSignInCount(SignInBean signInBean) {
		SqlSession session = getSession();
		try {
			ISignInMapper mapper = session.getMapper(ISignInMapper.class);
			return mapper.getSignInCount(signInBean);
		} catch (Exception e) {
			logger.warn("getSignInCount" + e.toString());
		} finally {
			session.close();
		}
		return -1;
	}

	/**
	 * 查询用户某月签到信息
	 * @author fanyongliang
	 * @param account
	 * @param firstDay
	 * @param lastDay
	 * @return
	 */
	public List<SignInBean> selectSignInInfoBetweenFLDay(String account,
			String firstDay, String lastDay) {
		SqlSession session = getSession();
		List<SignInBean> list = new ArrayList<SignInBean>();
		try {
			ISignInMapper mapper = session.getMapper(ISignInMapper.class);
			list = mapper.selectSignInInfoBetweenFLDay(account, firstDay,
					lastDay);
		} catch (Exception e) {
			logger.warn("selectSignInInfoBetweenFLDay" + e.toString());
		} finally {
			session.close();
		}
		return list;
	}
}
