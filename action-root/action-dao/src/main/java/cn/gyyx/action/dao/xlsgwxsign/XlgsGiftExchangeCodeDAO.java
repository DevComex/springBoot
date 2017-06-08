/**------------------------------------------------------------------------- 
* 版权所有：北京光宇在线科技有限责任公司 
-------------------------------------------------------------------------*/ 

package cn.gyyx.action.dao.xlsgwxsign;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.beans.xlsgwxsign.XlsgGiftExchangeCode;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

/** 
 * 作        者：成龙 
 * 联系方式：chenglong@gyyx.cn 
 * 创建时间： 2016年5月13日 下午8:08:14
 */
public class XlgsGiftExchangeCodeDAO {
	
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	
	public XlsgGiftExchangeCode getGiftExchangeCodeOne(
			XlsgGiftExchangeCode giftExchangeCode,SqlSession session) {
		XlsgGiftExchangeCode result = null;
		IXlsgGiftExchangeCodeMapper mapper = session.getMapper(IXlsgGiftExchangeCodeMapper.class);
		result = mapper.getGiftExchangeCodeOne(giftExchangeCode);
		return result;
	}

	public XlsgGiftExchangeCode getGiftExchangeCodeOne(
			XlsgGiftExchangeCode giftExchangeCode) {
		XlsgGiftExchangeCode result = null;
		SqlSession session = getSession();
		try {
			result = getGiftExchangeCodeOne(giftExchangeCode,session);
		}finally{
			session.close();
		}
		return result;
	}

	public int updateGiftExchangeCode(XlsgGiftExchangeCode giftExchangeCode) {
		int result = 0;
		SqlSession session = getSession();
		try {
			result = updateGiftExchangeCode(giftExchangeCode,session);
		} finally {
			session.commit();
			session.close();
		}
		return result;
	}

	public int updateGiftExchangeCode(XlsgGiftExchangeCode giftExchangeCode,
			SqlSession session) {
		int result = 0;
		IXlsgGiftExchangeCodeMapper mapper = session.getMapper(IXlsgGiftExchangeCodeMapper.class);
		result = mapper.updateGiftExchangeCode(giftExchangeCode);
		return result;
	}

}
