package cn.gyyx.action.dao.xwbcreditsluckydraw;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.xwbcreditsluckydraw.GoodsGetBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class GoodsGetDAO {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(GoodsGetDAO.class);

	/**
	 * 
	 * @日期：2015年7月8日
	 * @Title: getSession
	 * @Description: TODO 获取session
	 * @return SqlSession
	 */
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	/**
	 * @日期：20157月8日
	 * @Title:addGoodsGet
	 * @Description:新增中奖或兑换记录
	 * @return void
	 * */
	public void addGoodsGet(GoodsGetBean goodsGetBean){
		SqlSession session = getSession();
		try{
			IGoodsGetMapper mapper = session.getMapper(IGoodsGetMapper.class);
			mapper.addGoodsGet(goodsGetBean);
			session.commit();
		}catch(Exception e){
			logger.warn("addGoodsGet"+e.toString());
		}finally{
			session.close();
		}
	}
	/**
	 * @日期：20157月8日
	 * @Title:getGoodsGetByPage
	 * @Description:查询中奖或奖品兑换记录
	 * @return void
	 * */
	public List<GoodsGetBean> getGoodsGetByPage(Map<String,Object> paramMap){
		SqlSession session = getSession();
		try{
			IGoodsGetMapper mapper = session.getMapper(IGoodsGetMapper.class);
			return mapper.getGoodsGetByPage(paramMap);
		}catch(Exception e){
			logger.warn("getGoodsGetByPage"+e.toString());
		}finally{
			session.close();
		}
		return null;
	}
	/**
	 * @日期：20157月8日
	 * @Title:getGoodsGetCount
	 * @Description:查询中奖或奖品兑换记录的总数量
	 * @return void
	 * */
	public int getGoodsGetCount(GoodsGetBean goodsGetBean){
		SqlSession session = getSession();
		try{
			IGoodsGetMapper mapper = session.getMapper(IGoodsGetMapper.class);
			return mapper.getGoodsGetCount(goodsGetBean);
		}catch(Exception e){
			logger.warn("getGoodsGetCount"+e.toString());
		}finally{
			session.close();
		}
		return -1;
	}
	public List<GoodsGetBean> getGoodsGetByUser(String account){
		SqlSession session = getSession();
		try{
			IGoodsGetMapper mapper = session.getMapper(IGoodsGetMapper.class);
			return mapper.getGoodsGetByUser(account);
		}catch(Exception e){
			logger.warn("getGoodsGetByUser"+e.toString());
		}finally{
			session.close();
		}
		return null;
	}
	public List<GoodsGetBean> getGoodsRecord(GoodsGetBean goodsGetBean){
		SqlSession session = getSession();
		try{
			IGoodsGetMapper mapper = session.getMapper(IGoodsGetMapper.class);
			return mapper.getGoodsRecord(goodsGetBean);
		}catch(Exception e){
			logger.warn("getGoodsRecord"+e.toString());
		}finally{
			session.close();
		}
		return null;
		
	}
	public List<GoodsGetBean> getGoodsUserRecord(String account){
		SqlSession session = getSession();
		try{
			IGoodsGetMapper mapper = session.getMapper(IGoodsGetMapper.class);
			return mapper.getGoodsUserRecord(account);
		}catch(Exception e){
			logger.warn("getGoodsRecord"+e.toString());
		}finally{
			session.close();
		}
		return null;
		
	}
	public List<GoodsGetBean> getGoodsRecordTop(){
		SqlSession session = getSession();
		try{
			IGoodsGetMapper mapper = session.getMapper(IGoodsGetMapper.class);
			return mapper.getGoodsRecordTop();
		}catch(Exception e){
			logger.warn("getGoodsRecord"+e.toString());
		}finally{
			session.close();
		}
		return null;
		
	}
}
