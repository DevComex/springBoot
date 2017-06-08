/**------------------------------------------------------------------------- 
* 版权所有：北京光宇在线科技有限责任公司 
-------------------------------------------------------------------------*/ 

package cn.gyyx.action.dao.jswswxsign;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.jswswxsign.GiftExchangeCode;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/** 
 * 作        者：成龙 
 * 联系方式：chenglong@gyyx.cn 
 * 创建时间： 2016年5月13日 下午8:08:14
 */
public class GiftExchangeCodeDAO {
	
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(GiftExchangeCodeDAO.class);
	
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	
	public GiftExchangeCode getGiftExchangeCodeOne(
			GiftExchangeCode giftExchangeCode,SqlSession session) {
		GiftExchangeCode result = null;
		IGiftExchangeCodeMapper mapper = session.getMapper(IGiftExchangeCodeMapper.class);
		result = mapper.getGiftExchangeCodeOne(giftExchangeCode);
		return result;
	}

	public GiftExchangeCode getGiftExchangeCodeOne(
			GiftExchangeCode giftExchangeCode) {
		GiftExchangeCode result = null;
		SqlSession session = getSession();
		try {
			result = getGiftExchangeCodeOne(giftExchangeCode,session);
		} catch (Exception e) {
			logger.warn(e.toString());
		}finally{
			session.close();
		}
		return result;
	}

	public int updateGiftExchangeCode(GiftExchangeCode giftExchangeCode) {
		int result = 0;
		SqlSession session = getSession();
		try {
			result = updateGiftExchangeCode(giftExchangeCode,session);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.commit();
			session.close();
		}
		return result;
	}

	public int updateGiftExchangeCode(GiftExchangeCode giftExchangeCode,
			SqlSession session) {
		int result = 0;
		IGiftExchangeCodeMapper mapper = session.getMapper(IGiftExchangeCodeMapper.class);
		result = mapper.updateGiftExchangeCode(giftExchangeCode);
		return result;
	}

}
