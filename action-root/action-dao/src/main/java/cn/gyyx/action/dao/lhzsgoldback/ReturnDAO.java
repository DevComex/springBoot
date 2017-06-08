/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：LhzsGoldBack
 * @作者：范佳琪
 * @联系方式：fanjiaqig@gyyx.cn
 * @创建时间： 2016年04月13日
 * @版本号：
 * @本类主要用途描述：玩家金币返还记录数据访问层
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.lhzsgoldback;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.lhzsgoldback.ReturnBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class ReturnDAO {

	private static final Logger logger = GYYXLoggerFactory
			.getLogger(ReturnDAO.class);
	
	/**
	 * 获取session
	 */
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	
	/**
	 * @Title addReturnBean
	 * @Description 添加用户返还记录
	 * @author fanjiaqi
	 * @param returnBean
	 */
	public void addReturnBean(ReturnBean returnBean){
		SqlSession session = getSession();
		try {
			IReturnMapper mapper = session.getMapper(IReturnMapper.class);
			mapper.addReturnBean(returnBean);
			session.commit();
		} catch (Exception e) {
			logger.warn("灵魂战神  金币返还活动-ReturnDAO-addReturnBean,Exception:"
					+ e.toString());
		} finally {
			session.close();
		}
		
	}
	
	/**
	 * @Title findByAccount
	 * @Description 根据用户账号查询用户返还记录
	 * @author fanjiaqi
	 * @param account
	 * @return
	 */
	public Integer findByAccount(String account){
		SqlSession session = getSession();
		Integer count = 0;
		try {
			IReturnMapper mapper = session.getMapper(IReturnMapper.class);
			count = mapper.findByAccount(account);
		} catch (Exception e) {
			logger.warn("灵魂战神  金币返还活动-ReturnDAO-findByAccount,Exception:"
					+ e.toString());
		} finally {
			session.close();
		}
		return count;
	}
}
