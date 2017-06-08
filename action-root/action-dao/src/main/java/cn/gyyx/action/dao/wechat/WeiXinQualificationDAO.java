/*************************************************
Copyright ©, 2015, GY Game
Author: 王雷
Created: 2015年-12月-21日
Note: 微信端用户资格表连接数据库
************************************************/
package cn.gyyx.action.dao.wechat;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.wechat.WeiXinQualificationBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class WeiXinQualificationDAO {

	private static final Logger logger = GYYXLoggerFactory
			.getLogger(WeiXinQualificationDAO.class);

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
	 * @return WeiXinQualificationBean
	 */
	public WeiXinQualificationBean getQualification(String openId) {
		WeiXinQualificationBean qualification = null;
		SqlSession session = getSession();
		try {
			IWeiXinQualificationMapper qualificationMapper = session
					.getMapper(IWeiXinQualificationMapper.class);
			qualification = qualificationMapper.selectQualification(openId);
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
	public void setLotteryTime(WeiXinQualificationBean qualification) {
		SqlSession session = getSession();
		try {
			IWeiXinQualificationMapper qualificationMapper = session
					.getMapper(IWeiXinQualificationMapper.class);
			qualificationMapper.updateLotteryTime(qualification);
			session.commit();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
	}
	
	/**
	 * 根据用户编号和活动编号获取抽奖资格信息
	 * @日期：2015年4月9日
	 * @Title: selectByUserAndAction 
	 * @param map 条件集合，包括用户Code和活动Code
	 * @return 
	 * WeiXinQualificationBean 用户抽奖资格信息
	 */
	public WeiXinQualificationBean selectByUserAndAction(Map<String, String> map){
		WeiXinQualificationBean qualification = null;
		SqlSession session = getSession();
		try {
			IWeiXinQualificationMapper qualificationMapper = session
					.getMapper(IWeiXinQualificationMapper.class);
			qualification = qualificationMapper.selectByUserAndAction(map);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return qualification;
	}
	/**
	 * 插入用户资格 ——问道康师傅V1.211活动需要
	 * @param qualification
	 */
	public void insertQualification(WeiXinQualificationBean qualification){
		SqlSession session = getSession();
		try {
			IWeiXinQualificationMapper qualificationMapper = session
					.getMapper(IWeiXinQualificationMapper.class);
			qualificationMapper.insertQualification(qualification);
			session.commit();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
	}
	public List<WeiXinQualificationBean> selectByAction(int actionCode){
		List<WeiXinQualificationBean>  qualification = null;
		SqlSession session = getSession();
		try {
			IWeiXinQualificationMapper qualificationMapper = session
					.getMapper(IWeiXinQualificationMapper.class);
			qualification = qualificationMapper.selectByAction(actionCode);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return qualification;
	}
	public void reduceTime(String openId, int actionCode) {
		SqlSession session = getSession();
		try {
			IWeiXinQualificationMapper qualificationMapper = session
					.getMapper(IWeiXinQualificationMapper.class);
			qualificationMapper.reduceTime(openId, actionCode);
			session.commit();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
	}
	
}
