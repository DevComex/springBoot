/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：xuanwuba
 * @作者：yangyusheng
 * @联系方式：yangyusheng@gyyx.cn
 * @创建时间： 2015年7月10日 上午11:27:08
 * @版本号：
 * @本类主要用途描述：积分记录连接数据库
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.xwbcreditsluckydraw;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.xwbcreditsluckydraw.CreditsBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class CreditsDAO {

	private static final Logger logger = GYYXLoggerFactory
			.getLogger(CreditsDAO.class);

	/**
	 * 
	 * @日期：2015年7月10日
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
	 * @日期：2015年7月10日
	 * @Title: addCredits
	 * @Description: TODO 添加积分记录
	 * @param bean
	 * @return int
	 */
	public int addCredits(CreditsBean bean) {
		int result = 0;
		SqlSession session = getSession();
		try {
			ICreditsMapper creditsMapper = session
					.getMapper(ICreditsMapper.class);
			result = creditsMapper.addCredits(bean);
			session.commit();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return result;
	}

	/**
	 * 
	 * @日期：2015年7月10日
	 * @Title: getCredits
	 * @Description: TODO 获取所有积分记录
	 * @param page
	 * @return List<CreditsBean>
	 */
	public List<CreditsBean> getCredits(CreditsBean bean) {
		List<CreditsBean> creditsList = null;
		SqlSession session = getSession();
		try {
			ICreditsMapper creditsMapper = session
					.getMapper(ICreditsMapper.class);
			creditsList = creditsMapper.getCredits(bean);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return creditsList;
	}

	/**
	 * 
	 * @日期：2015年7月10日
	 * @Title: getCredits
	 * @Description: TODO 获取所有记录数
	 * @return int
	 */
	public int getCreditsNum(CreditsBean bean) {
		int result = 0;
		SqlSession session = getSession();
		try {
			ICreditsMapper creditsMapper = session
					.getMapper(ICreditsMapper.class);
			result = creditsMapper.getCreditsNum(bean);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return result;
	}
}
