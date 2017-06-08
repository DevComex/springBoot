/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：lottery
 * @作者：yangyusheng
 * @联系方式：yangyusheng@gyyx.cn
 * @创建时间： 2015年3月19日 上午11:11:15
 * @版本号：
 * @本类主要用途描述：用户资格逻辑类
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.bll.wdninestory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;

import cn.gyyx.action.beans.common.ActionConfigBean;
import cn.gyyx.action.beans.lottery.QualificationBean;
import cn.gyyx.action.dao.wdninestory.QualificationDAO;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class QualificationBLL {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(QualificationBLL.class);
	private QualificationDAO qualificationDao = new QualificationDAO();

	/**
	 * 
	 * @日期：2015年3月19日
	 * @Title: getQualification
	 * @Description: TODO 根据用户编号获取用户抽奖资格信息
	 * @param userCode
	 * @return QualificationBean
	 */
	public QualificationBean getQualification(int userCode) {
		return qualificationDao.getQualification(userCode);
	}

	/**
	 * 
	 * @日期：2015年3月19日
	 * @Title: setLotteryTime
	 * @Description: TODO 根据用户code更新用户抽奖次数
	 * @param qualification
	 *            void
	 */
	public void setLotteryTime(QualificationBean qualification) {
		logger.debug("QualificationBean", qualification);

		qualification.setLotteryTime(qualification.getLotteryTime() - 1);
		logger.debug("QualificationBean", qualification);
		qualificationDao.setLotteryTime(qualification);
	}

	public void addLotteryTime(QualificationBean qualification) {
		logger.debug("QualificationBean", qualification);

		qualification.setLotteryTime(qualification.getLotteryTime() + 1);
		logger.debug("QualificationBean", qualification);
		qualificationDao.setLotteryTime(qualification);
	}

	/**
	 * 
	 * @日期：2015年8月21日
	 * @Title: setLotteryTime
	 * @Description: TODO 根据用户code更新用户抽奖次数
	 * @param qualification
	 *            void
	 */
	public void setTimeLottery(QualificationBean qualification) {
		logger.debug("QualificationBean", qualification);
		qualificationDao.setLotteryTime(qualification);
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
		qualificationDao.setLotteryTimeSqlsession(qualification, session);
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
	public QualificationBean selectByUserAndAction(String userCode,
			String actionCode) {
		Map<String, String> map = new HashMap<>();
		map.put("userCode", userCode);
		map.put("actionCode", actionCode);
		return qualificationDao.selectByUserAndAction(map);
	}

	/**
	 * 增加用户资格 ——问道康师傅V1.211活动需要
	 * 
	 * @param qualification
	 */
	public void addQualification(QualificationBean qualification) {
		qualificationDao.insertQualification(qualification);
	}

	public void reduceTime(int userCode, int actionCode) {
		qualificationDao.reduceTime(userCode, actionCode);
	}

	public List<QualificationBean> selectByAction(int actionCode) {
		return qualificationDao.selectByAction(actionCode);

	}

	public List<QualificationBean> selectByActionAndPage(int actionCode,
			int pageSize, int pageNo) {
		return qualificationDao.selectByActionAndPage(actionCode, pageSize,
				pageNo);
	}

	public void removeByActionCode(int actionCode) {
		qualificationDao.removeByActionCode(actionCode);

	}

	/***
	 * 更改次数
	 * 
	 * @param userCode
	 * @param actionCode
	 * @param time
	 * @param sqlSession
	 */
	public void setTimes(int userCode, int actionCode, int time,
			SqlSession sqlSession) {
		QualificationBean qualificationBean = qualificationDao
				.selectLotteryInfoByUserAndActionSqlsession(userCode,
						actionCode, sqlSession);
		if (qualificationBean != null) {
			qualificationDao.setTimes(userCode, actionCode, time, sqlSession);
		} else if (time > 0) {
			QualificationBean qualificationBeanTemp = new QualificationBean();
			qualificationBeanTemp.setLotteryTime(time);
			qualificationBeanTemp.setUserCode(userCode);
			qualificationBeanTemp.setActionCode(actionCode);
			qualificationDao.insertQualificationWithoutSession(
					qualificationBeanTemp, sqlSession);
		}

	}
	/**
	 * 获取活动信息
	 * @param actionCode
	 * @return
	 */
	public ActionConfigBean selectActionConfigByCode(int actionCode){
		return qualificationDao.selectActionConfig(actionCode);
	}
}
