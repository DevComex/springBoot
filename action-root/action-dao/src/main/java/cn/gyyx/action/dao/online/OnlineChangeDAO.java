/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：online-change
 * @作者：fanyongliang
 * @联系方式：fanyongliang@gyyx.cn
 * @创建时间： 2015年9月28日
 * @版本号：
 * @本类主要用途描述：玩家元宝信息数据访问层
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.online;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.online.OnlineChangeBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;


public class OnlineChangeDAO {

	private static final Logger logger = GYYXLoggerFactory
			.getLogger(OnlineChangeDAO.class);
	
	/**
	 * 获取session
	 */
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	/**
	 * 查询用户的资格
	 * @param account
	 * @return
	 */
	public OnlineChangeBean selectAccount(String account) {
		SqlSession session = getSession();
		OnlineChangeBean bean = new OnlineChangeBean();
		try {
			IOnlineChangeMapper mapper = session.getMapper(IOnlineChangeMapper.class);
			bean = mapper.selectAccount(account);
		} catch (Exception e) {
			logger.warn("online查询用户的资格:" + e.toString());
		} finally {
			session.close();
		}
		return bean;
	}
}
