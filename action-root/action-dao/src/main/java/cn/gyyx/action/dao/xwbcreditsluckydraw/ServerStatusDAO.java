/*************************************************
       Copyright ©, 2015, GY Game
       Author: 范永良
       Created: 2016年-02月-18日
       Note:服务器关闭状态数据访问
************************************************/
package cn.gyyx.action.dao.xwbcreditsluckydraw;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.xwbcreditsluckydraw.ServerStatusBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class ServerStatusDAO {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(ServerStatusDAO.class);

	/**
	 * 
	 * @日期：2016年2月18日
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
	 * 增加服务器关闭信息
	 * @param serverStatusBean
	 */
	public void addServerStatusInfo(ServerStatusBean serverStatusBean){
		SqlSession session = getSession();
		try{
			IServerStatusMapper mapper = session.getMapper(IServerStatusMapper.class);
			mapper.addServerStatusInfo(serverStatusBean);
			session.commit();
		}catch(Exception e){
			logger.warn("addServerStatusInfo"+e.toString());
		}finally{
			session.close();
		}
	}
	
	/**
	 * 获取所有的停服信息
	 * @return
	 */
	public List<ServerStatusBean> getAllServerStatusInfoByPage(int page){
		SqlSession session = getSession();
		List<ServerStatusBean> list = new ArrayList<ServerStatusBean>();
		try{
			IServerStatusMapper mapper = session.getMapper(IServerStatusMapper.class);
			list = mapper.getAllServerStatusInfoByPage(page);
		}catch(Exception e){
			logger.warn("getAllServerStatusInfo"+e.toString());
		}finally{
			session.close();
		}
		return list;
	}
	
	public List<ServerStatusBean> getAllServerStatusInfo(){
		SqlSession session = getSession();
		List<ServerStatusBean> list = new ArrayList<ServerStatusBean>();
		try{
			IServerStatusMapper mapper = session.getMapper(IServerStatusMapper.class);
			list = mapper.getAllServerStatusInfo();
		}catch(Exception e){
			logger.warn("getAllServerStatusInfo"+e.toString());
		}finally{
			session.close();
		}
		return list;
	}
	
	/**
	 * 获取所有开启的停服信息
	 * @return
	 */
	public List<ServerStatusBean> getAllOpenServerStatusInfo(){
		SqlSession session = getSession();
		List<ServerStatusBean> list = new ArrayList<ServerStatusBean>();
		try{
			IServerStatusMapper mapper = session.getMapper(IServerStatusMapper.class);
			list = mapper.getAllOpenServerStatusInfo();
		}catch(Exception e){
			logger.warn("getAllOpenServerStatusInfo:"+e.toString());
		}finally{
			session.close();
		}
		return list;
	}
	
	/**
	 * 根据code获取停服信息
	 * @param code
	 * @return
	 */
	public ServerStatusBean getServerStatusInfoByCode(int code){
		SqlSession session = getSession();
		ServerStatusBean bean = new ServerStatusBean();
		try{
			IServerStatusMapper mapper = session.getMapper(IServerStatusMapper.class);
			bean = mapper.getServerStatusInfoByCode(code);
		}catch(Exception e){
			logger.warn("getServerStatusInfoByCode"+e.toString());
		}finally{
			session.close();
		}
		return bean;
	}
	
	/**
	 * 修改服务器关闭信息
	 * @param serverStatusBean
	 */
	public void modifyServerStatusInfo(ServerStatusBean serverStatusBean){
		SqlSession session = getSession();
		try{
			IServerStatusMapper mapper = session.getMapper(IServerStatusMapper.class);
			mapper.modifyServerStatusInfo(serverStatusBean);
			session.commit();
		}catch(Exception e){
			logger.warn("modifyServerStatusInfo"+e.toString());
		}finally{
			session.close();
		}
	}
}
