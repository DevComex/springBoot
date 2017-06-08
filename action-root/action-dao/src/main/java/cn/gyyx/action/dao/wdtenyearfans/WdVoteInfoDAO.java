/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：问道十周年粉丝榜
 * @作者：chenpeilan
 * @联系方式：chenpeilan@gyyx.cn
 * @创建时间： 2016年3月29日
 * @版本号：
 * @本类主要用途描述：问道十周年粉丝榜活动投票相关DAO
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.wdtenyearfans;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.beans.wdtenyearfans.PageBean;
import cn.gyyx.action.beans.wdtenyearfans.WdVoteInfoBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

public class WdVoteInfoDAO {
	SqlSessionFactory factory = MyBatisConnectionFactory
			.getSqlActionDBV2SessionFactory();

	public void insertWdVoteInfoBean(WdVoteInfoBean wdVoteInfoBean) {
		try (SqlSession session = factory.openSession()) {
			IWdVoteInfoBean mapper = session.getMapper(IWdVoteInfoBean.class);
			mapper.insertWdVoteInfoBean(wdVoteInfoBean);
			session.commit();
		}
	}

	public WdVoteInfoBean selectWdVoteInfoBeanByCode(int code) {
		try (SqlSession session = factory.openSession()) {
			IWdVoteInfoBean mapper = session.getMapper(IWdVoteInfoBean.class);
			return mapper.selectWdVoteInfoBeanByCode(code);
		}
	}

	public void updateWdVoteInfoBean(WdVoteInfoBean wdVoteInfoBean) {
		try (SqlSession session = factory.openSession()) {
			IWdVoteInfoBean mapper = session.getMapper(IWdVoteInfoBean.class);
			mapper.updateWdVoteInfoBean(wdVoteInfoBean);
			session.commit();
		}
	}

	/***
	 * 查询账户投票的数量
	 * 
	 * @param account
	 * @return
	 */
	public Integer getCountWdVoteInfoBeanByAccount(String account) {
		try (SqlSession session = factory.openSession()) {
			IWdVoteInfoBean mapper = session.getMapper(IWdVoteInfoBean.class);
			if (mapper.getCountWdVoteInfoBeanByAccount(account) == null) {
				return 0;
			}
			return mapper.getCountWdVoteInfoBeanByAccount(account);
		}
	}

	/***
	 * 查询某账户对某提名的投票情况
	 */
	public WdVoteInfoBean getWdVoteInfoBeanByAccountAndNominationCode(
			String account, int nominationCode) {
		try (SqlSession session = factory.openSession()) {
			IWdVoteInfoBean mapper = session.getMapper(IWdVoteInfoBean.class);
			return mapper.getWdVoteInfoBeanByAccountAndNominationCode(account,
					nominationCode);
		}
	}
	/***
	 * 查询某账户对某提名的投票情况
	 */
	public WdVoteInfoBean selectVoteTypeByAccount(WdVoteInfoBean wdVoteInfoBean) {
		try (SqlSession session = factory.openSession()) {
			IWdVoteInfoBean mapper = session.getMapper(IWdVoteInfoBean.class);
			return mapper.selectVoteTypeByAccount(wdVoteInfoBean);
		}
	}

	/***
	 * 获取账号投票的信息
	 * 
	 * @param account
	 * @return
	 */
	public List<WdVoteInfoBean> getWdVoteInfoBeanByAccount(String account) {
		try (SqlSession session = factory.openSession()) {
			IWdVoteInfoBean mapper = session.getMapper(IWdVoteInfoBean.class);
			return mapper.getWdVoteInfoBeanByAccount(account);
		}
	}
	
	/**
	 * 查询投票账号数量
	 * @return
	 */
	public Integer getVoteAccountNum(String accountName) {
		try (SqlSession session = factory.openSession()) {
			IWdVoteInfoBean mapper = session.getMapper(IWdVoteInfoBean.class);
			return mapper.getVoteAccountNum(accountName);
		}
	}
	/**
	 * 查询投票IP数量
	 * @return
	 */
	public Integer getVoteIpNum() {
		try (SqlSession session = factory.openSession()) {
			IWdVoteInfoBean mapper = session.getMapper(IWdVoteInfoBean.class);
			return mapper.getVoteIpNum();
		}
	}
	/**
	 * 根据账号查询投票次数
	 * @param accountName
	 * @return
	 */
	public Integer getVoteNumByAccount(String accountName) {
		try (SqlSession session = factory.openSession()) {
			IWdVoteInfoBean mapper = session.getMapper(IWdVoteInfoBean.class);
			return mapper.getVoteNumByAccount(accountName);
		}
	}
	/**
	 * 查询所有投票账号
	 * @return
	 */
	public List<String> getVoteAccount(int pageSize,int pageNo,String accountName) {
		try (SqlSession session = factory.openSession()) {
			IWdVoteInfoBean mapper = session.getMapper(IWdVoteInfoBean.class);
			return mapper.getVoteAccount(pageSize, pageNo,accountName);
		}
	}
	
	/**
	 * 查询所有投票账号
	 * @return
	 */
	public List<String> getAllVoteAccount() {
		try (SqlSession session = factory.openSession()) {
			IWdVoteInfoBean mapper = session.getMapper(IWdVoteInfoBean.class);
			return mapper.getAllVoteAccount();
		}
	}
	
	/**
	 * 每日参与关注账号数
	 * @param pageBean
	 * @return
	 */
	public Integer getAccountNumByWhite(PageBean pageBean) {
		try (SqlSession session = factory.openSession()) {
			IWdVoteInfoBean mapper = session.getMapper(IWdVoteInfoBean.class);
			return mapper.getAccountNumByWhite(pageBean);
		}
	}
	/**
	 * 每日参与拉黑账号数
	 * @param pageBean
	 * @return
	 */
	public Integer getAccountNumByBlack(PageBean pageBean) {
		try (SqlSession session = factory.openSession()) {
			IWdVoteInfoBean mapper = session.getMapper(IWdVoteInfoBean.class);
			return mapper.getAccountNumByBlack(pageBean);
		}
	}
	/**
	 * 每日参与投票账号数
	 * @param pageBean
	 * @return
	 */
	public Integer getAccountNumByVote(PageBean pageBean) {
		try (SqlSession session = factory.openSession()) {
			IWdVoteInfoBean mapper = session.getMapper(IWdVoteInfoBean.class);
			return mapper.getAccountNumByVote(pageBean);
		}
	}
	/**
	 * 每日账号关注的数量
	 * @param pageBean
	 * @return
	 */
	public Integer getWhiteNumByAccountDate(PageBean pageBean) {
		try (SqlSession session = factory.openSession()) {
			IWdVoteInfoBean mapper = session.getMapper(IWdVoteInfoBean.class);
			return mapper.getWhiteNumByAccountDate(pageBean);
		}
	}
	/**
	 * 每日账号拉黑的数量
	 * @param pageBean
	 * @return
	 */
	public Integer getBlackNumByAccountDate(PageBean pageBean) {
		try (SqlSession session = factory.openSession()) {
			IWdVoteInfoBean mapper = session.getMapper(IWdVoteInfoBean.class);
			return mapper.getBlackNumByAccountDate(pageBean);
		}
	}
	/**
	 * 每日投票账号列表
	 * @param pageBean
	 * @return
	 */
	public List<String> selectAllVoteAccount(PageBean pageBean) {
		try (SqlSession session = factory.openSession()) {
			IWdVoteInfoBean mapper = session.getMapper(IWdVoteInfoBean.class);
			return mapper.selectAllVoteAccount(pageBean);
		}
	}
}