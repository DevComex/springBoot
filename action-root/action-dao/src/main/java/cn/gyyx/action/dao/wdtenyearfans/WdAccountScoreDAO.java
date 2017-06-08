/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：问道十周年粉丝榜
 * @作者：chenpeilan
 * @联系方式：chenpeilan@gyyx.cn
 * @创建时间： 2016年3月29日
 * @版本号：
 * @本类主要用途描述：问道十周年粉丝榜活动积分相关DAO
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.wdtenyearfans;

import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.action.beans.wdtenyearfans.WdAccountScoreBean;

import org.apache.ibatis.session.SqlSession;

public class WdAccountScoreDAO {
	SqlSessionFactory factory = MyBatisConnectionFactory.getSqlActionDBV2SessionFactory(); 
	public void insertWdAccountScoreBean(WdAccountScoreBean wdAccountScoreBean) {
		try(SqlSession session = factory.openSession()) {
			IWdAccountScoreBean mapper = session.getMapper(IWdAccountScoreBean.class);
			mapper.insertWdAccountScoreBean(wdAccountScoreBean);
			session.commit();
		}
	}
	public WdAccountScoreBean selectWdAccountScoreBeanByCode(int code) {
		try(SqlSession session = factory.openSession()) {
			IWdAccountScoreBean mapper = session.getMapper(IWdAccountScoreBean.class);
			return mapper.selectWdAccountScoreBeanByCode(code);
		}
	}
	public void updateWdAccountScoreBean(WdAccountScoreBean wdAccountScoreBean) {
		try(SqlSession session = factory.openSession()) {
			IWdAccountScoreBean mapper = session.getMapper(IWdAccountScoreBean.class);
			mapper.updateWdAccountScoreBean(wdAccountScoreBean);
			session.commit();
		}
	}

	public WdAccountScoreBean selectWdAccountScoreBeanByAccount(String accountName){
		try(SqlSession session = factory.openSession()) {
			IWdAccountScoreBean mapper = session.getMapper(IWdAccountScoreBean.class);
			return mapper.selectWdAccountScoreBeanByAccount(accountName);
		}
		}
	public WdAccountScoreBean selectWdAccountScoreBeanByAccountName(String accountName) {
		try(SqlSession session = factory.openSession()) {
			IWdAccountScoreBean mapper = session.getMapper(IWdAccountScoreBean.class);
			return mapper.selectWdAccountScoreBeanByAccountName(accountName);
		}
	}
}