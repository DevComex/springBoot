package cn.gyyx.action.dao.sdsg.advice;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.sdsg.advice.SdsgAdviceBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class SdsgAdviceMapperImpl  implements SdsgAdviceMapper{

	private static final Logger logger = GYYXLoggerFactory.getLogger(SdsgAdviceMapperImpl.class);
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	@Override
	public int insertAdviceLog(SdsgAdviceBean sdsgBean) {
		int result = 0;
		SqlSession session = getSession();
		try {
			logger.debug("-----插入建议{}",sdsgBean.toString());
			SdsgAdviceMapper sdsgAdviceMapper = session.getMapper(SdsgAdviceMapper.class);
			result = sdsgAdviceMapper.insertAdviceLog(sdsgBean);
			logger.debug("-----插入建议响应条数{}",result);
			session.commit();
		} catch (Exception e) {
			logger.warn("插入建议失败-{},{},{}",new Object[]{e.getCause(),e.getMessage(),e.getStackTrace()});
			e.printStackTrace();
		} finally {
			session.close();
		}
		return result;
	}

}
