package cn.gyyx.action.dao.xwbcreditsluckydraw;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.xwbcreditsluckydraw.MissionBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.PictureUrlBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class PictureUrlDAO {

	private static final Logger logger = GYYXLoggerFactory
			.getLogger(PictureUrlDAO.class);

	/**
	 * 获取session
	 */
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	public PictureUrlBean getPictureUrlByPrizeCode(Integer prizeCode){
		PictureUrlBean pictureUrlBean = null;
		SqlSession session = getSession();
		try {
			IPictureUrlMapper iPictureUrlMapper = session
					.getMapper(IPictureUrlMapper.class);
			pictureUrlBean = iPictureUrlMapper.getPictureUrlByPrizeCode(prizeCode);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return pictureUrlBean;
		
	}
	public PictureUrlBean getPictureUrlByPrizeFlag(Integer prizeFlag){
		PictureUrlBean pictureUrlBean = null;
		SqlSession session = getSession();
		try {
			IPictureUrlMapper iPictureUrlMapper = session
					.getMapper(IPictureUrlMapper.class);
			pictureUrlBean = iPictureUrlMapper.getPictureUrlByPrizeFlag(prizeFlag);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return pictureUrlBean;
	}
	public List<PictureUrlBean> getPrizeByFlag(Integer prizeFlag){
		List<PictureUrlBean> pictureUrlBean = null;
		SqlSession session = getSession();
		try {
			IPictureUrlMapper iPictureUrlMapper = session
					.getMapper(IPictureUrlMapper.class);
			pictureUrlBean = iPictureUrlMapper.getPrizeByFlag(prizeFlag);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return pictureUrlBean;
	}
	public Integer updatePictureUrlBean(PictureUrlBean pictureUrlBean){
		Integer result = null;
		SqlSession session = getSession();
		try {
			IPictureUrlMapper iPictureUrlMapper = session
					.getMapper(IPictureUrlMapper.class);
			result = iPictureUrlMapper.updatePictureUrlBean(pictureUrlBean);
			session.commit();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return result;
	}
	public PictureUrlBean getPrizeByPrizeCode(Integer prizeCode){
		PictureUrlBean pictureUrlBean = null;
		SqlSession session = getSession();
		try {
			IPictureUrlMapper iPictureUrlMapper = session
					.getMapper(IPictureUrlMapper.class);
			pictureUrlBean = iPictureUrlMapper.getPrizeByPrizeCode(prizeCode);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return pictureUrlBean;
	}
}
