/*------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：王雷
 * 联系方式：wanglei@gyyx.cn 
 * 创建时间：2015年07月14日 
 * 版本号：v1.0 
 * 本类主要用途描述： 虚拟保险项目——黑名单数据处理类
 * 
-------------------------------------------------------------------------*/

package cn.gyyx.action.dao.insurance;

import java.io.IOException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.insurance.BlackListBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.action.dao.wd9yearnovicebag.ServerConfigDAO;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class BlackListDAO implements  IBlackListMapper {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(ServerConfigDAO.class);
	IBlackListMapper iBlackListMapper ;
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
	 * 根据获取总条数
	 * @param account
	 * @return
	 */
	public Integer selectPageCount( ){
		SqlSession session = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory().openSession();
		Integer count = null;
//		session.selectOne("namespace.id", parameter);
		try {
			iBlackListMapper = session.getMapper(IBlackListMapper.class);
			//
			count = iBlackListMapper.selectPageCount();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return count;
	}
	/**
	 * 根据获取黑名单信息——分页
	 * @param account
	 * @return
	 */
	public List<BlackListBean> getBlackLisForPage( Integer num, Integer pageNum){
		SqlSession session = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory().openSession();
		List<BlackListBean> blackList = null;
		try {
			iBlackListMapper = session.getMapper(IBlackListMapper.class);
			//
			blackList = iBlackListMapper.getBlackLisForPage(num, pageNum);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return blackList;
	}
	/**
	 * 向黑名单中插入数据
	 * @param blackBean
	 */
	public void insertBlackBean(BlackListBean blackBean){
		SqlSession session = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory().openSession();
		try {
			iBlackListMapper = session.getMapper(IBlackListMapper.class);
			//
			iBlackListMapper.insertBlackBean(blackBean);
			session.commit();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
	}
    /**
     * 根据认证手机获取黑名单信息
     */
	@Override
	public List<BlackListBean>  getBlackListBeanByAccountOrPhone(String phone,String account) {
		// TODO Auto-generated method stub
		try (SqlSession sqlSession = getSession()) {
			IBlackListMapper iBlackListMapper = sqlSession
					.getMapper(IBlackListMapper.class);
			return iBlackListMapper.getBlackListBeanByAccountOrPhone(phone,account);
	
		}
	}
	

}
