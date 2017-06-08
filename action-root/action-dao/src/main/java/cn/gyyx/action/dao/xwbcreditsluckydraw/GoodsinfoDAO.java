/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：xuanwuba
 * @作者：yangyusheng
 * @联系方式：yangyusheng@gyyx.cn
 * @创建时间： 2015年7月13日 上午10:48:57
 * @版本号：
 * @本类主要用途描述：物品连接数据库
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.xwbcreditsluckydraw;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.xwbcreditsluckydraw.GoodsInfoBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class GoodsinfoDAO {

	private static final Logger logger = GYYXLoggerFactory
			.getLogger(GoodsinfoDAO.class);

	/**
	 * 
	 * @日期：2015年7月13日
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
	 * 
	 * @日期：2015年7月14日
	 * @Title: getGoods
	 * @Description: TODO 获得所有物品
	 * @param page
	 * @return List<GoodsInfoBean>
	 */
	public List<GoodsInfoBean> getGoods(int page) {
		List<GoodsInfoBean> goodsList = null;
		SqlSession session = getSession();
		try {
			IGoodsinfoMapper goodsinfoMapper = session
					.getMapper(IGoodsinfoMapper.class);
			goodsList = goodsinfoMapper.getGoods(page);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return goodsList;
	}

	/**
	 * 
	 * @日期：2015年7月14日
	 * @Title: getGoodsTotal
	 * @Description: TODO 查询所有物品数量
	 * @return int
	 */
	public int getGoodsTotal() {
		int result = 0;
		SqlSession session = getSession();
		try {
			IGoodsinfoMapper goodsinfoMapper = session
					.getMapper(IGoodsinfoMapper.class);
			result = goodsinfoMapper.getGoodsTotal();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return result;
	}

	/**
	 * 
	 * @日期：2015年7月13日
	 * @Title: addGoods
	 * @Description: TODO
	 * @param good
	 * @return int
	 */
	public int addGoods(GoodsInfoBean good) {
		int result = 0;
		SqlSession session = getSession();
		try {
			IGoodsinfoMapper goodsinfoMapper = session
					.getMapper(IGoodsinfoMapper.class);
			result = goodsinfoMapper.addGoods(good);
			session.commit();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return result;
	}
	public List<GoodsInfoBean> getGoodsAll() {
		List<GoodsInfoBean> result = null;
		SqlSession session = getSession();
		try {
			IGoodsinfoMapper goodsinfoMapper = session
					.getMapper(IGoodsinfoMapper.class);
			result = goodsinfoMapper.getGoodsAll();
		
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return result;
	}
	public int updateGood(GoodsInfoBean good) {
		int result = 0;
		SqlSession session = getSession();
		try {
			IGoodsinfoMapper goodsinfoMapper = session
					.getMapper(IGoodsinfoMapper.class);
			result = goodsinfoMapper.updateGood(good);
			session.commit();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return result;
	}
	public int deleteGood(GoodsInfoBean good) {
		int result = 0;
		SqlSession session = getSession();
		try {
			IGoodsinfoMapper goodsinfoMapper = session
					.getMapper(IGoodsinfoMapper.class);
			result = goodsinfoMapper.deleteGood(good);
			session.commit();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return result;
	}
	public GoodsInfoBean getGoodsByCode(int code){
		GoodsInfoBean result = null;
		SqlSession session = getSession();
		try {
			IGoodsinfoMapper goodsinfoMapper = session
					.getMapper(IGoodsinfoMapper.class);
			result = goodsinfoMapper.getGoodsByCode(code);
			
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return result;
	}
	
	/**
	 * 修改道具数量
	 * @param code
	 */
	public void updatePriceNum(int code){
		SqlSession session = getSession();
		try {
			IGoodsinfoMapper goodsinfoMapper = session
					.getMapper(IGoodsinfoMapper.class);
			goodsinfoMapper.updatePriceNum(code);
			session.commit();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
	}
}
