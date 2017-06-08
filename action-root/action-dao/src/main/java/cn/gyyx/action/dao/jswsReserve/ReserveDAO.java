/**
 * --------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：范佳琪 
 * 联系方式：fanjiaqi@gyyx.cn 
 * 创建时间：2016年1月20日下午13:46:02
 * 版本号：
 * 本类主要用途叙述：绝世武神预约分享的数据库DAO
 */
package cn.gyyx.action.dao.jswsReserve;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.jswsReserve.ReserveBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class ReserveDAO implements IReserveMapper{
	
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(ReserveDAO.class);
	
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}

	/**
	 * 
	 * @Title: insertReserveInfo
	 * @Description: TODO 添加预约信息
	 * @param @param reserveBean
	 * @param @return    
	 * @return int    
	 * @throws
	 */
	public int insertReserveInfo(ReserveBean reserveBean) {
		int result = 0;
		SqlSession session = getSession();
		try {
			IReserveMapper reserveMapper = session.getMapper(IReserveMapper.class);
			result = reserveMapper.insertReserveInfo(reserveBean);
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
 	 * @Title: getReserveByPhoneNum
	 * @Description: TODO 查询该手机号码是否已经预约过
	 * @param @param phoneNum
	 * @param @return    
	 * @return ReserveBean    
	 * @throws
	 */
	public ReserveBean getReserveByPhoneNum(String phoneNum) {
		ReserveBean reserveBean = null;
		SqlSession session = getSession();
		try {
			IReserveMapper reserveMapper = session.getMapper(IReserveMapper.class);
			reserveBean = reserveMapper.getReserveByPhoneNum(phoneNum);
		} catch (Exception e) {
			logger.warn(e.toString());
		}finally{
			session.close();
		}
		return reserveBean;
	}

}
