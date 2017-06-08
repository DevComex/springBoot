/**------------------------------------------------------------------------- 
* 版权所有：北京光宇在线科技有限责任公司 
-------------------------------------------------------------------------*/ 

package cn.gyyx.action.dao.xlsgwxsign;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.beans.xlsgwxsign.XlsgSignLog;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

/** 
 * 作        者：成龙 
 * 联系方式：chenglong@gyyx.cn 
 * 创建时间： 2016年5月13日 下午8:08:14
 */
public class XlsgSignLogDAO {
	
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	
	public int insertSignLog(XlsgSignLog signLog) {
		int result = 0;
		SqlSession session = getSession();
		try {
			result = insertSignLog(signLog,session);
			session.commit();
		} finally {
			session.close();
		}
		return result;
	}
	
	public int insertSignLog(XlsgSignLog signLog,SqlSession session) {
		int result = 0;
		IXlsgSignLogMapper mapper = session.getMapper(IXlsgSignLogMapper.class);
		result = mapper.insertSignLog(signLog);
		return result;
	}

}
