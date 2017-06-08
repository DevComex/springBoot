/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：问道十周年粉丝榜
 * @作者：chenpeilan
 * @联系方式：chenpeilan@gyyx.cn
 * @创建时间： 2016年3月29日
 * @版本号：
 * @本类主要用途描述：问道十周年粉丝榜活动账号相关DAO
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.wdtenyearfans;

import cn.gyyx.action.beans.wdtenyearfans.WdAccountInfoBean;
import org.apache.ibatis.session.SqlSessionFactory;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import org.apache.ibatis.session.SqlSession;

public class WdAccountInfoDAO {
	SqlSessionFactory factory = MyBatisConnectionFactory.getSqlActionDBV2SessionFactory(); 
	/**
	 * 
	* @Title: insertWdAccountInfoBean
	* @Description: TODO 绑定角色
	* @param @param wdAccountInfoBean    
	* @return void    
	* @throws
	 */
	public void insertWdAccountInfoBean(WdAccountInfoBean wdAccountInfoBean) {
		try(SqlSession session = factory.openSession()) {
			IWdAccountInfoBean mapper = session.getMapper(IWdAccountInfoBean.class);
			mapper.insertWdAccountInfoBean(wdAccountInfoBean);
			session.commit();
		}
	}
	public WdAccountInfoBean selectWdAccountInfoBeanByCode(int code) {
		try(SqlSession session = factory.openSession()) {
			IWdAccountInfoBean mapper = session.getMapper(IWdAccountInfoBean.class);
			return mapper.selectWdAccountInfoBeanByCode(code);
		}
	}
	/**
	 * 
	* @Title: selectWdAccountInfoBeanByCode
	* @Description: TODO 查询是否绑定过角色
	* @param @param code
	* @param @return    
	* @return WdAccountInfoBean    
	* @throws
	 */
	public WdAccountInfoBean selectWdAccountInfoBeanByAccountName(String accountName) {
		try(SqlSession session = factory.openSession()) {
			IWdAccountInfoBean mapper = session.getMapper(IWdAccountInfoBean.class);
			return mapper.selectWdAccountInfoBeanByAccountName(accountName);
		}
	}
	public void updateWdAccountInfoBean(WdAccountInfoBean wdAccountInfoBean) {
		try(SqlSession session = factory.openSession()) {
			IWdAccountInfoBean mapper = session.getMapper(IWdAccountInfoBean.class);
			mapper.updateWdAccountInfoBean(wdAccountInfoBean);
			session.commit();
		}
	}
	
	/**
	 * 根据账号查询区服和绑定角色
	 * @param accountName
	 * @return
	 */
	public WdAccountInfoBean selectAccountInfoByAccountName(String accountName) {
		try(SqlSession session = factory.openSession()) {
			IWdAccountInfoBean mapper = session.getMapper(IWdAccountInfoBean.class);
			return mapper.selectAccountInfoByAccountName(accountName);
		}
	}
}