/**------------------------------------------------------------------------- 
* 版权所有：北京光宇在线科技有限责任公司 
-------------------------------------------------------------------------*/ 

package cn.gyyx.action.dao.wdqingchengshan;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.jswswxsign.Sign;
import cn.gyyx.action.beans.jswswxsign.SignLog;
import cn.gyyx.action.beans.wdnationalday.WdNationaldaySignLogBean;
import cn.gyyx.action.beans.wdqingchengshan.SignLogBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.action.dao.wdnationaldayshare.IWdNationaldaySignLogMapper;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/** 
 * 作        者：成龙 
 * 联系方式：chenglong@gyyx.cn 
 * 创建时间： 2016年5月13日 下午8:08:14
 */
public class SignLogDAO {
	
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(SignLogDAO.class);
	
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	
	public SignLogBean getTodaySignLog(String account,String today) {
		try (SqlSession sqlSession = getSession()) {
			ISignLogMapper mapper = sqlSession
					.getMapper(ISignLogMapper.class);
			return mapper.getTodaySignLog(account,today);
		}
	}
	
	public int insertSignLog(SignLogBean bean,SqlSession session) {
		int res = 0;
		ISignLogMapper mapper = session
				.getMapper(ISignLogMapper.class);
		res = mapper.insertSignLog(bean);
		return res;
	}

	public List<SignLogBean> select(SignLogBean params) {
		try(SqlSession session = getSession()) {
			return session.getMapper(ISignLogMapper.class).select(params);
		}
		catch(Exception e) {
			logger.error(e.getMessage());
		}
		
		return null;
	}
}
