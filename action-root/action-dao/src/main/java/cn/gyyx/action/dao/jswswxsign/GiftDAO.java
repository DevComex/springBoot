/**------------------------------------------------------------------------- 
* 版权所有：北京光宇在线科技有限责任公司 
-------------------------------------------------------------------------*/ 

package cn.gyyx.action.dao.jswswxsign;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.jswswxsign.Gift;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/** 
 * 作        者：成龙 
 * 联系方式：chenglong@gyyx.cn 
 * 创建时间： 2016年5月13日 下午8:08:14
 */
public class GiftDAO {
	
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(GiftDAO.class);
	
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}

	public List<Gift> getGiftList(Gift gift) {
		List<Gift> result = null;
		SqlSession session = getSession();
		try {
			IGiftMapper mapper = session.getMapper(IGiftMapper.class);
			result = mapper.getGiftList(gift);
		} catch (Exception e) {
			logger.warn(e.toString());
		}finally{
			session.close();
		}
		return result;
	}

	public Gift getGift(Gift gift) {
		Gift result = null;
		SqlSession session = getSession();
		try {
			IGiftMapper mapper = session.getMapper(IGiftMapper.class);
			result = mapper.getGift(gift);
		} catch (Exception e) {
			logger.warn(e.toString());
		}finally{
			session.close();
		}
		return result;
	}

}
