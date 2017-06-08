package cn.gyyx.action.dao.xwbcreditsluckydraw;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.xwbcreditsluckydraw.SumCreditBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：xuanwuba
 * @作者：yangyusheng
 * @联系方式：yangyusheng@gyyx.cn
 * @创建时间： 2015年7月10日 上午10:56:38
 * @版本号：
 * @本类主要用途描述：总积分表数据库连接类
 *-------------------------------------------------------------------------
 */
public class SumCreditDAO {

	private static final Logger logger = GYYXLoggerFactory
			.getLogger(SumCreditDAO.class);

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
	 * @Title: addSumCredit
	 * @Description: TODO 添加总积分表新用户
	 * @param credit
	 * @return int
	 */
	public int addSumCredit(SumCreditBean credit) {
		int result = 0;
		SqlSession session = getSession();
		try {
			ISumCreditMapper sumCreditMapper = session
					.getMapper(ISumCreditMapper.class);
			result = sumCreditMapper.addSumCredit(credit);
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
	 * @Title: getSumCredit
	 * @Description: TODO 根据用户获取总积分数量
	 * @param account
	 * @return int
	 */
	public int getSumCredit(String account) {
		int result = -1;
		SqlSession session = getSession();
		try {
			ISumCreditMapper sumCreditMapper = session
					.getMapper(ISumCreditMapper.class);
			result = sumCreditMapper.getSumCredit(account);
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
	 * @Title: setSumCredit
	 * @Description: TODO 根据用户修改积分数量
	 * @param credit
	 * @return int
	 */
	public int setSumCredit(SumCreditBean credit) {
		int result = 0;
		SqlSession session = getSession();
		try {
			ISumCreditMapper sumCreditMapper = session
					.getMapper(ISumCreditMapper.class);
			result = sumCreditMapper.setSumCredit(credit);
			session.commit();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return result;
	}
	public SumCreditBean getCreditByAccount(String account){
		SumCreditBean result = null;
		SqlSession session = getSession();
		try {
			ISumCreditMapper sumCreditMapper = session
					.getMapper(ISumCreditMapper.class);
			result = sumCreditMapper.getCreditByAccount(account);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return result;
	}
		
	
}
