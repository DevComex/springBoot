/**------------------------------------------------------------------------- 
* 版权所有：北京光宇在线科技有限责任公司 
-------------------------------------------------------------------------*/ 

package cn.gyyx.action.dao.jswswxsign;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.jswswxsign.ExchangeLog;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/** 
 * 作        者：成龙 
 * 联系方式：chenglong@gyyx.cn 
 * 创建时间： 2016年5月13日 下午8:08:14
 */
public class ExchangeLogDAO implements IExchangeLogMapper{
	
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(ExchangeLogDAO.class);
	
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	
	public List<ExchangeLog> getExchangeLogList(
			ExchangeLog exchangeLog) {
		List<ExchangeLog> result = null;
		SqlSession session = getSession();
		try {
			IExchangeLogMapper mapper = session.getMapper(IExchangeLogMapper.class);
			result = mapper.getExchangeLogList(exchangeLog);
		}finally{
			session.close();
		}
		return result;
	}

	public int insertExchangeLog(ExchangeLog exchangeLog) {
		int result = 0;
		SqlSession session = getSession();
		try {
			result = insertExchangeLog(exchangeLog,session);
		} finally {
			session.commit();
			session.close();
		}
		return result;
	}
	
	public int insertExchangeLog(ExchangeLog exchangeLog,SqlSession session) {
		int result = 0;
		IExchangeLogMapper mapper = session.getMapper(IExchangeLogMapper.class);
		result = mapper.insertExchangeLog(exchangeLog);
		return result;
	}

}
