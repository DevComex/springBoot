package cn.gyyx.action.dao.activityCode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.wd9yeardatavideo.PresentLogBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.action.dao.wd9yearnovicebag.ServerConfigDAO;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class SendPresentLogDAO implements ISendPresentLogMapper {
	private static final Logger LOG = GYYXLoggerFactory
			.getLogger(ServerConfigDAO.class);
	/**
	 * 获取session，
	 * 
	 * @throws IOException
	 */
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	/**
	 * 查找所有该活动相同IP的获奖个数
	 */
	@Override
	public int SameIPNum(String ip,int activityId) {
		SqlSession session = getSession();
		int result = 0;
		try{
			ISendPresentLogMapper entity = session.getMapper(ISendPresentLogMapper.class);
			result = entity.SameIPNum(ip,activityId);
		}catch(Exception e){
			LOG.error("查找相同IP数据个数失败",e);
		}finally{
			session.close();
		}
		return result;
	}
	/**
	 * 判断是否重复
	 */
	@Override
	public List<PresentLogBean> isExist(int activityId, String searchPara) {
		SqlSession session = getSession();
		List<PresentLogBean> result = new ArrayList<PresentLogBean>();
		try{
			ISendPresentLogMapper entity = session.getMapper(ISendPresentLogMapper.class);
			result = entity.isExist(activityId,searchPara);
		}catch(Exception e){
			LOG.error("判断是否重复失败",e);
		}finally{
			session.close();
		}
		return result;
	}
	/**
	 * 插入中奖信息
	 */
	@Override
	public boolean insertWinningMes(PresentLogBean para) {
		SqlSession session = getSession();
		boolean result = false;
		try{
			ISendPresentLogMapper entity = session.getMapper(ISendPresentLogMapper.class);
			result = entity.insertWinningMes(para);
		}catch(Exception e){
			LOG.error("插入中奖信息失败",e);
		}finally{
			session.commit();
			session.close();
		}
		return result;
	}
	
}
