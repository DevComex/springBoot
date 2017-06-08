/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：炫舞吧积分活动
 * @作者：王雷
 * @联系方式：wanglei@gyyx.cn
 * @创建时间： 2015年9月2日
 * @版本号：V1.214
 * @本类主要用途描述：点赞记录数据处理接口
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.xwbcreditsluckydraw;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.xwbcreditsluckydraw.PraiseBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class PraiseDAO {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(GoodsinfoDAO.class);
	IPraiseMapper iPraiseMapper;

	/**
	 * 
	 * @日期：2015年9月2日
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
	 * 增加点赞记录
	 * @param praiseBean
	 */
	public void insertPraise(PraiseBean praiseBean){
		SqlSession session = getSession();
		try {
			iPraiseMapper = session
					.getMapper(IPraiseMapper.class);
			iPraiseMapper.insertPraise(praiseBean);
			session.commit();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
	}
	/**
	 * 根据素材Code更新点赞记录的是否取消is_delete
	 * @param materialInfo
	 */
	public void updatePraiseDelete(PraiseBean praiseBean){
		SqlSession session = getSession();
		try {
			iPraiseMapper = session
					.getMapper(IPraiseMapper.class);
			iPraiseMapper.updatePraiseDelete(praiseBean);
			session.commit();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
	}
	/**
	 * 条件查询点赞信息
	 * @param praiseBean
	 * @return
	 */
	public PraiseBean selectPraise(PraiseBean praiseBean){
		PraiseBean praise = null;
		SqlSession session = getSession();
		try {
			iPraiseMapper = session
					.getMapper(IPraiseMapper.class);
			praise = iPraiseMapper.selectPraise(praiseBean);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return praise;
	}
	/**
	 * 根据素材Code查询有多少个赞
	 * @param materialInfo
	 * @return
	 */
	public Integer selectCountPraise(Integer materialInfo){
		Integer count = null;
		SqlSession session = getSession();
		try {
			iPraiseMapper = session
					.getMapper(IPraiseMapper.class);
			count = iPraiseMapper.selectCountPraise(materialInfo);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return count;
	}
	/**
	 * 修改状态
	 * @param praiseBean
	 */
	public void updatePraiseStatus(PraiseBean praiseBean){
		SqlSession session = getSession();
		try {
			iPraiseMapper = session
					.getMapper(IPraiseMapper.class);
			iPraiseMapper.updatePraiseStatus(praiseBean);
			session.commit();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
	}
	public Integer getPraiseCountByUser(String account,Integer materialCode){
		SqlSession session = getSession();
		try {
			iPraiseMapper = session
					.getMapper(IPraiseMapper.class);
			return iPraiseMapper.getPraiseCountByUser(account,materialCode);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return 0;
	}
}
