package cn.gyyx.action.dao.wechat;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.gyyx.action.beans.wechat.WeChatCountBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

public class WeChatCountDao {

	private static final Logger logger = LoggerFactory
			.getLogger(WeChatCountDao.class);

	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}

	public void addAccessCount(WeChatCountBean weChatCountBean) {
		SqlSession session = getSession();
		try {
			IWeChatCountMapper mapper = session
					.getMapper(IWeChatCountMapper.class);
			mapper.addAccessCount(weChatCountBean);
			session.commit();
		} catch (Exception e) {
			logger.warn("normalAccess" + e.toString());
		} finally {
			session.close();
		}
	}

	public Integer selectCountByType(String countType) {
		SqlSession session = getSession();
		try {
			IWeChatCountMapper mapper = session
					.getMapper(IWeChatCountMapper.class);
			return mapper.selectCountByType(countType);
		} catch (Exception e) {
			logger.warn("normalAccess" + e.toString());
		} finally {
			session.close();
		}
		return 0;
	}

	public List<WeChatCountBean> selectCountWithPM() {
		SqlSession session = getSession();
		try {
			IWeChatCountMapper mapper = session
					.getMapper(IWeChatCountMapper.class);
			return mapper.selectCountWithPM();
		} catch (Exception e) {
			logger.warn("normalAccess" + e.toString());
		} finally {
			session.close();
		}
		return null;
	}
	
	public Integer selectDayCountByType(String countType,String nickName,String dateStr) {
		SqlSession session = getSession();
		try {
			IWeChatCountMapper mapper = session
					.getMapper(IWeChatCountMapper.class);
			return mapper.selectDayCountByType(countType,nickName,dateStr);
		} catch (Exception e) {
			logger.warn("selectDayCountByType" + e.toString());
		} finally {
			session.close();
		}
		return 0;
	}
	
}
