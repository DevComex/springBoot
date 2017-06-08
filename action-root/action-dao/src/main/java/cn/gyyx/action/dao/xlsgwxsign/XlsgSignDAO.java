/**------------------------------------------------------------------------- 
* 版权所有：北京光宇在线科技有限责任公司 
-------------------------------------------------------------------------*/ 

package cn.gyyx.action.dao.xlsgwxsign;


import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.beans.xlsgwxsign.XlsgSign;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

/** 
 * 作        者：成龙 
 * 联系方式：chenglong@gyyx.cn 
 * 创建时间： 2016年5月13日 下午8:08:14
 */
public class XlsgSignDAO{
	
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}

	public int insertSign(XlsgSign sign) {
		int result = 0;
		SqlSession session = getSession();
		try {
			result = insertSign(sign,session);
			session.commit();
		} finally {
			session.close();
		}
		return result;
	}
	
	public int insertSign(XlsgSign sign,SqlSession session) {
		int result = 0;
		IXlsgSignMapper mapper = session.getMapper(IXlsgSignMapper.class);
		result = mapper.insertSign(sign);
		return result;
	}

	public XlsgSign getSign(XlsgSign sign) {
		XlsgSign result = null;
		SqlSession session = getSession();
		try {
			result = getSign(sign,session);
		}finally{
			session.close();
		}
		return result;
	}
	
	public XlsgSign getSign(XlsgSign sign,SqlSession session) {
		XlsgSign result = null;
		IXlsgSignMapper mapper = session.getMapper(IXlsgSignMapper.class);
		result = mapper.getSign(sign);
		return result;
	}
	
	public int updateSign(XlsgSign sign) {
		int result = 0;
		SqlSession session = getSession();
		try {
			result = updateSign(sign,session);
			session.commit();
		} finally {
			session.close();
		}
		return result;
	}
	
	public int updateSign(XlsgSign sign,SqlSession session) {
		int result = 0;
		IXlsgSignMapper mapper = session.getMapper(IXlsgSignMapper.class);
		result = mapper.updateSign(sign);
		return result;
	}
	
	public XlsgSign getRecentSign(String openId) {
		XlsgSign result = null;
		SqlSession session = getSession();
		try {
			result = getRecentSign(openId,session);
		}finally{
			session.close();
		}
		return result;
	}

	public XlsgSign getRecentSign(String openId, SqlSession session) {
		XlsgSign result = null;
		IXlsgSignMapper mapper = session.getMapper(IXlsgSignMapper.class);
		XlsgSign param = new XlsgSign();
		param.setOpenId(openId);
		result = mapper.getRecentSign(param);
		return result;
	}
	
	public void updateSignZero(SqlSession session) {
		IXlsgSignMapper mapper = session.getMapper(IXlsgSignMapper.class);
		mapper.updateSignZero();
	}
}
