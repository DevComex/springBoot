/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：online-change
 * @作者：fanyongliang
 * @联系方式：fanyongliang@gyyx.cn
 * @创建时间： 2015年9月28日
 * @版本号：
 * @本类主要用途描述：玩家兑换光宇币日志记录数据访问层
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.online;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.online.OnlineChangeLogBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;


public class OnlineChangeLogDAO {

	private static final Logger logger = GYYXLoggerFactory
			.getLogger(OnlineChangeLogDAO.class);

	/**
	 * 获取session
	 */
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	/**
	 * 增加用户申请记录
	 * @param onlineChangeLogBean
	 * @return
	 */
	public int addOnlineChangeLog(OnlineChangeLogBean onlineChangeLogBean) {
		SqlSession session = getSession();
		int num = 0;
		try {
			IOnlineChangeLogMapper mapper = session.getMapper(IOnlineChangeLogMapper.class);
			num = mapper.addOnlineChangeLog(onlineChangeLogBean);
			session.commit();
		} catch (Exception e) {
			logger.warn("online增加用户申请记录" + e.toString());
		} finally {
			session.close();
		}
		return num;
	}
	/**
	 * 按账号查找用户申请记录
	 * @param account
	 * @return
	 */
	public OnlineChangeLogBean selectonlineChangeLog(String account){
		SqlSession session = getSession();
		OnlineChangeLogBean onlineChangeLogBean = null;
		try {
			IOnlineChangeLogMapper mapper = session.getMapper(IOnlineChangeLogMapper.class);
			onlineChangeLogBean = mapper.selectonlineChangeLog(account);
			session.commit();
		} catch (Exception e) {
			logger.warn("online增加用户申请记录" + e.toString());
		} finally {
			session.close();
		}
		return onlineChangeLogBean;
	}


}
