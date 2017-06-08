/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：lottery
 * @作者：yangyusheng
 * @联系方式：yangyusheng@gyyx.cn
 * @创建时间： 2015年3月19日 上午10:31:41
 * @版本号：
 * @本类主要用途描述：用户资格表连接数据库
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.wdninestory;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.common.ActionConfigBean;
import cn.gyyx.action.beans.lottery.QualificationBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class QualificationDAO {

	private static final Logger logger = GYYXLoggerFactory
			.getLogger(QualificationDAO.class);

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
	 * 
	 * @日期：2015年3月19日
	 * @Title: getQualification
	 * @Description: TODO 根据用户编号获取用户抽奖资格信息
	 * @param userCode
	 * @return QualificationBean
	 */
	public QualificationBean getQualification(int userCode) {
		QualificationBean qualification = null;
		SqlSession session = getSession();
		try {
			IQualificationMapper qualificationMapper = session
					.getMapper(IQualificationMapper.class);
			qualification = qualificationMapper.selectQualification(userCode);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return qualification;
	}

	/**
	 * 
	 * @日期：2015年3月19日
	 * @Title: getQualification
	 * @Description: TODO 根据用户code更新用户抽奖次数
	 * @param qualification
	 */
	public void setLotteryTime(QualificationBean qualification) {
		SqlSession session = getSession();
		try {
			IQualificationMapper qualificationMapper = session
					.getMapper(IQualificationMapper.class);
			qualificationMapper.updateLotteryTime(qualification);
			session.commit();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
	}

	/**
	 * 
	 * @日期：2015年3月19日
	 * @Title: getQualification
	 * @Description: TODO 根据用户code更新用户抽奖次数
	 * @param qualification
	 */
	public void setLotteryTimeSqlsession(QualificationBean qualification,
			SqlSession session) {

		IQualificationMapper qualificationMapper = session
				.getMapper(IQualificationMapper.class);
		qualificationMapper.updateLotteryTime(qualification);
	}

	/**
	 * 根据用户编号和活动编号获取抽奖资格信息
	 * 
	 * @日期：2015年4月9日
	 * @Title: selectByUserAndAction
	 * @param map
	 *            条件集合，包括用户Code和活动Code
	 * @return QualificationBean 用户抽奖资格信息
	 */
	public QualificationBean selectByUserAndAction(Map<String, String> map) {
		QualificationBean qualification = null;
		SqlSession session = getSession();
		try {
			IQualificationMapper qualificationMapper = session
					.getMapper(IQualificationMapper.class);
			qualification = qualificationMapper.selectByUserAndAction(map);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return qualification;
	}

	/**
	 * 查询抽奖资格靠用户Code和活动Code
	 * 
	 * @param userCode
	 * @param actionCode
	 * @return
	 */
	public QualificationBean selectLotteryInfoByUserAndAction(int userCode,
			int actionCode) {
		QualificationBean qualification = null;
		SqlSession session = getSession();
		try {
			IQualificationMapper qualificationMapper = session
					.getMapper(IQualificationMapper.class);
			qualification = qualificationMapper
					.selectLotteryInfoByUserAndAction(userCode, actionCode);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return qualification;
	}

	public QualificationBean selectLotteryInfoByUserAndActionSqlsession(
			int userCode, int actionCode, SqlSession sqlSession) {

		IQualificationMapper qualificationMapper = sqlSession
				.getMapper(IQualificationMapper.class);
		return qualificationMapper.selectLotteryInfoByUserAndAction(userCode,
				actionCode);
	}
	
	
	/**
	 * 插入用户资格 ——问道康师傅V1.211活动需要
	 * 
	 * @param qualification
	 */
	public void insertQualification(QualificationBean qualification) {
		SqlSession session = getSession();
		try {
			IQualificationMapper qualificationMapper = session
					.getMapper(IQualificationMapper.class);
			qualificationMapper.insertQualification(qualification);
			session.commit();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
	}

	/**
	 * 插入用户抽奖资格（事物）__问道十周年活动————一生换十年 V1.253需要
	 * 
	 * @param qualification
	 */
	public void insertQualificationWithoutSession(
			QualificationBean qualification, SqlSession session) {
		IQualificationMapper qualificationMapper = session
				.getMapper(IQualificationMapper.class);
		qualificationMapper.insertQualification(qualification);
	}

	/**
	 * 增加两次抽奖机会问道IDO活动需要
	 * 
	 * @param userCode
	 * @param actionCode
	 * @param session
	 */
	public void addTwoLotteryTime(int userCode, int actionCode,
			SqlSession session) {
		IQualificationMapper qualificationMapper = session
				.getMapper(IQualificationMapper.class);
		qualificationMapper.addTwoLotteryTime(userCode, actionCode);
	}
	
	/**
	 * 增加一次抽奖机会（事物）__问道十周年活动————一生换十年 V1.253需要
	 * 
	 * @param userCode
	 * @param actionCode
	 * @param session
	 */
	public void addOneLotteryTime(int userCode, int actionCode,
			SqlSession session) {
		IQualificationMapper qualificationMapper = session
				.getMapper(IQualificationMapper.class);
		qualificationMapper.addOneLotteryTime(userCode, actionCode);
	}

	/**
	 * 增加一次抽奖机会数据（事物）__问道1.61活动————问道1.61官网活动需要
	 * 抽奖次数为0
	 * @param userCode
	 * @param actionCode
	 * @param session
	 */
	public int addOneLotteryData(int userCode, int actionCode,
			SqlSession session) {
		IQualificationMapper qualificationMapper = session
				.getMapper(IQualificationMapper.class);
		return qualificationMapper.addOneLotteryData(userCode, actionCode);
	}
	/**
	 * 增加一次抽奖机会数据（事物）__问道1.61活动————问道1.61官网活动需要
	 * 抽奖次数加1
	 * @param actionCode
	 * @return
	 */
	public int addLotteryTime(int userCode, int actionCode,
			SqlSession session) {
		IQualificationMapper qualificationMapper = session
				.getMapper(IQualificationMapper.class);
		return qualificationMapper.addLotteryTime(userCode, actionCode);
	}

	public List<QualificationBean> selectByAction(int actionCode) {
		List<QualificationBean> qualification = null;
		SqlSession session = getSession();
		try {
			IQualificationMapper qualificationMapper = session
					.getMapper(IQualificationMapper.class);
			qualification = qualificationMapper.selectByAction(actionCode);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return qualification;
	}

	public void reduceTime(int userCode, int actionCode) {
		SqlSession session = getSession();
		try {
			IQualificationMapper qualificationMapper = session
					.getMapper(IQualificationMapper.class);
			qualificationMapper.reduceTime(userCode, actionCode);
			session.commit();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
	}

	public List<QualificationBean> selectByActionAndPage(int actionCode,
			int pageSize, int pageNo) {
		try (SqlSession sqlSession = getSession()) {
			IQualificationMapper qualificationMapper = sqlSession
					.getMapper(IQualificationMapper.class);
			return qualificationMapper.selectByActionAndPage(actionCode,
					pageSize, pageNo);
		}
	}

	public void removeByActionCode(int actionCode) {
		try (SqlSession sqlSession = getSession()) {
			IQualificationMapper qualificationMapper = sqlSession
					.getMapper(IQualificationMapper.class);
			qualificationMapper.removeByActionCode(actionCode);
			sqlSession.commit();
		}

	}
	
	/**
	 * 查询活动的信息
	 * @param actionCode 活动编码
	 * @return
	 */
	public ActionConfigBean selectActionConfig(int actionCode) {
		try(SqlSession session = getSession()) {
			IQualificationMapper mapper = session.getMapper(IQualificationMapper.class);
			return mapper.selectActionConfig(actionCode);
		}
	}

	public void setTimes(int userCode, int actionCode, int time,
			SqlSession sqlSession) {
		IQualificationMapper qualificationMapper = sqlSession
				.getMapper(IQualificationMapper.class);
		qualificationMapper.setTimes(userCode, actionCode, time);
	}
}
