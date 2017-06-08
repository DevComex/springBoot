/*------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：王雷
 * 联系方式：wanglei@gyyx.cn 
 * 创建时间：2015年07月14日 
 * 版本号：v1.0 
 * 本类主要用途描述： 虚拟保险项目——登陆日志数据处理类
 * 
-------------------------------------------------------------------------*/

package cn.gyyx.action.dao.insurance;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;

import cn.gyyx.action.beans.insurance.LoginLogBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.action.dao.wd9yearnovicebag.ServerConfigDAO;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class LoginLogDAO {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(ServerConfigDAO.class);
	ILoginLogMapper iLoginLogMapper;
	/**
	 * 插入登陆日志
	 * @param loginLogBean
	 */
	public void insertLoginLog(LoginLogBean loginLogBean){
		SqlSession session = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory().openSession();
		try {
			iLoginLogMapper = session.getMapper(ILoginLogMapper.class);
			//
			iLoginLogMapper.insertLoginLog(loginLogBean);
			session.commit();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
	}
	/**
	 * 获取上次的登陆日志
	 * @param countId
	 * @return
	 */
	public LoginLogBean selectLastLogin(Integer countId){
		SqlSession session = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory().openSession();
		LoginLogBean loginLogBean = null;
		try {
			iLoginLogMapper = session.getMapper(ILoginLogMapper.class);
			//
			loginLogBean = iLoginLogMapper.selectLastLogin(countId);
			session.commit();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return loginLogBean;
	}
}
