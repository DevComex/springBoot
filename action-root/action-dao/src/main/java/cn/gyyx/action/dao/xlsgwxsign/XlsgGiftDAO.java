/**------------------------------------------------------------------------- 
* 版权所有：北京光宇在线科技有限责任公司 
-------------------------------------------------------------------------*/ 

package cn.gyyx.action.dao.xlsgwxsign;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.beans.xlsgwxsign.XlsgGift;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

/** 
 * 作        者：成龙 
 * 联系方式：chenglong@gyyx.cn 
 * 创建时间： 2016年5月13日 下午8:08:14
 */
public class XlsgGiftDAO {
	
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}

	public List<XlsgGift> getGiftList(XlsgGift gift) {
		List<XlsgGift> result = null;
		SqlSession session = getSession();
		try {
			IXlsgGiftMapper mapper = session.getMapper(IXlsgGiftMapper.class);
			result = mapper.getGiftList(gift);
		}finally{
			session.close();
		}
		return result;
	}

	public XlsgGift getGift(XlsgGift gift) {
		XlsgGift result = null;
		SqlSession session = getSession();
		try {
			IXlsgGiftMapper mapper = session.getMapper(IXlsgGiftMapper.class);
			result = mapper.getGift(gift);
		}finally{
			session.close();
		}
		return result;
	}

}
