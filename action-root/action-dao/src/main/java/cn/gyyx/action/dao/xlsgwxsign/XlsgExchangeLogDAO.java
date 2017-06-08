/**------------------------------------------------------------------------- 
* 版权所有：北京光宇在线科技有限责任公司 
-------------------------------------------------------------------------*/ 

package cn.gyyx.action.dao.xlsgwxsign;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.beans.xlsgwxsign.XlsgExchangeLog;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

/** 
 * 作        者：成龙 
 * 联系方式：chenglong@gyyx.cn 
 * 创建时间： 2016年5月13日 下午8:08:14
 */
public class XlsgExchangeLogDAO implements IXlsgExchangeLogMapper{
	
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	
	public List<XlsgExchangeLog> getExchangeLogList(
			XlsgExchangeLog exchangeLog) {
		List<XlsgExchangeLog> result = null;
		SqlSession session = getSession();
		try {
			IXlsgExchangeLogMapper mapper = session.getMapper(IXlsgExchangeLogMapper.class);
			result = mapper.getExchangeLogList(exchangeLog);
		}finally{
			session.close();
		}
		return result;
	}

	public int insertExchangeLog(XlsgExchangeLog exchangeLog) {
		int result = 0;
		SqlSession session = getSession();
		try {
			result = insertExchangeLog(exchangeLog,session);
			session.commit();
		} finally {
			session.close();
		}
		return result;
	}
	
	public int insertExchangeLog(XlsgExchangeLog exchangeLog,SqlSession session) {
		int result = 0;
		IXlsgExchangeLogMapper mapper = session.getMapper(IXlsgExchangeLogMapper.class);
		result = mapper.insertExchangeLog(exchangeLog);
		return result;
	}

}
