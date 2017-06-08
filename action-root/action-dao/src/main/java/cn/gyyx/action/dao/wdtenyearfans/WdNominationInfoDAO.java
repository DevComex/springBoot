/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：问道十周年粉丝榜
 * @作者：chenpeilan
 * @联系方式：chenpeilan@gyyx.cn
 * @创建时间： 2016年3月29日
 * @版本号：
 * @本类主要用途描述：问道十周年粉丝榜活动提名相关DAO
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.wdtenyearfans;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.beans.wdtenyearfans.PageBean;
import cn.gyyx.action.beans.wdtenyearfans.WdNominationInfoBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

public class WdNominationInfoDAO {
	SqlSessionFactory factory = MyBatisConnectionFactory
			.getSqlActionDBV2SessionFactory();

	/**
	 * 
	 * @Title: insertWdNominationInfoBean
	 * @Description: TODO 提名操作
	 * @param @param wdNominationInfoBean
	 * @return void
	 * @throws
	 */
	public void insertWdNominationInfoBean(
			WdNominationInfoBean wdNominationInfoBean) {
		try (SqlSession session = factory.openSession()) {
			IWdNominationInfoBean mapper = session
					.getMapper(IWdNominationInfoBean.class);
			mapper.insertWdNominationInfoBean(wdNominationInfoBean);
			session.commit();
		}
	}

	public WdNominationInfoBean selectWdNominationInfoBeanByCode(int code) {
		try (SqlSession session = factory.openSession()) {
			IWdNominationInfoBean mapper = session
					.getMapper(IWdNominationInfoBean.class);
			return mapper.selectWdNominationInfoBeanByCode(code);
		}
	}

	public int selectWdNominationCount(PageBean pageBean) {
		try (SqlSession session = factory.openSession()) {
			IWdNominationInfoBean mapper = session
					.getMapper(IWdNominationInfoBean.class);
			return mapper.selectWdNominationCount(pageBean);
		}
	}
	
	public int selectDeleteWdNominationCount(PageBean pageBean) {
		try (SqlSession session = factory.openSession()) {
			IWdNominationInfoBean mapper = session
					.getMapper(IWdNominationInfoBean.class);
			return mapper.selectDeleteWdNominationCount(pageBean);
		}
	}

	public void updateWdNominationInfoBean(
			WdNominationInfoBean wdNominationInfoBean) {
		try (SqlSession session = factory.openSession()) {
			IWdNominationInfoBean mapper = session
					.getMapper(IWdNominationInfoBean.class);
			mapper.updateWdNominationInfoBean(wdNominationInfoBean);
			session.commit();
		}
	}

	public void updateAuditStatus(WdNominationInfoBean bean) {
		try (SqlSession session = factory.openSession()) {
			IWdNominationInfoBean mapper = session
					.getMapper(IWdNominationInfoBean.class);
			mapper.updateAuditStatus(bean);
			session.commit();
		}
	}

	public List<WdNominationInfoBean> selectWdNominationInfoList(
			PageBean pageBean) {
		try (SqlSession session = factory.openSession()) {
			IWdNominationInfoBean mapper = session
					.getMapper(IWdNominationInfoBean.class);
			return mapper.selectWdNominationInfoList(pageBean);
		}
	}
	
	public List<WdNominationInfoBean> selectDeleteWdNominationInfoList(
			PageBean pageBean) {
		try (SqlSession session = factory.openSession()) {
			IWdNominationInfoBean mapper = session
					.getMapper(IWdNominationInfoBean.class);
			return mapper.selectDeleteWdNominationInfoList(pageBean);
		}
	}

	/**
	 * 
	 * @Title: selectWdNominationInfoBeanByAccountName
	 * @Description: TODO 判断是否提名
	 * @param @param accountName
	 * @param @return
	 * @return WdNominationInfoBean
	 * @throws
	 */
	public WdNominationInfoBean selectWdNominationInfoBeanByAccountName(
			String accountName) {
		try (SqlSession session = factory.openSession()) {
			IWdNominationInfoBean mapper = session
					.getMapper(IWdNominationInfoBean.class);
			return mapper.selectWdNominationInfoBeanByAccountName(accountName);
		}
	}

	/***
	 * 根据角色筛选提名作品
	 * 
	 * @param roleName
	 * @return
	 */
	public List<WdNominationInfoBean> getWdNominationInfoBeansByRoleName(
			String roleName, int pageSize, int pageNo, int status) {
		try (SqlSession session = factory.openSession()) {
			IWdNominationInfoBean mapper = session
					.getMapper(IWdNominationInfoBean.class);
			return mapper.getWdNominationInfoBeansByRoleName(roleName,
					pageSize, pageNo, status);
		}
	}

	/**
	 * 根据主键获取白粉的排名
	 * 
	 * @param code
	 * @return
	 */
	public Integer getWhiteNumCount(int code) {
		try (SqlSession session = factory.openSession()) {
			IWdNominationInfoBean mapper = session
					.getMapper(IWdNominationInfoBean.class);
			if (mapper.getWhiteNumCount(code) == null) {
				return 0;
			}
			return mapper.getWhiteNumCount(code);
		}
	}

	/**
	 * 根据主键获取白粉的排名
	 * 
	 * @param code
	 * @return
	 */
	public Integer getBlackNumCount(int code) {
		try (SqlSession session = factory.openSession()) {
			IWdNominationInfoBean mapper = session
					.getMapper(IWdNominationInfoBean.class);
			if (mapper.getBlackNumCount(code) == null) {
				return 0;
			}
			return mapper.getBlackNumCount(code);
		}

	}

	/***
	 * 根据角色状态筛选提名作品数量
	 * 
	 * @param roleName
	 * @param status
	 * @return
	 */
	public Integer getCountWdNominationInfoBeansByRoleName(String roleName,
			int status) {
		try (SqlSession session = factory.openSession()) {
			IWdNominationInfoBean mapper = session
					.getMapper(IWdNominationInfoBean.class);
			if (mapper
					.getCountWdNominationInfoBeansByRoleName(roleName, status) == null) {
				return 0;
			}
			return mapper.getCountWdNominationInfoBeansByRoleName(roleName,
					status);
		}
	}

	/***
	 * 根据时间顺序获取提名的信息
	 * 
	 * @return
	 */
	public List<WdNominationInfoBean> getWdNominationInfoBeansByOrder(
			int pageSize, int pageNo, int status) {
		try (SqlSession session = factory.openSession()) {
			IWdNominationInfoBean mapper = session
					.getMapper(IWdNominationInfoBean.class);
			return mapper.getWdNominationInfoBeansByOrder(pageSize, pageNo,
					status);
		}
	}

	/***
	 * 根据白粉顺序获取提名的信息
	 * 
	 * @return
	 */
	public List<WdNominationInfoBean> getWdNominationInfoBeansByWhite(
			int pageSize, int pageNo, int status) {
		try (SqlSession session = factory.openSession()) {
			IWdNominationInfoBean mapper = session
					.getMapper(IWdNominationInfoBean.class);
			return mapper.getWdNominationInfoBeansByWhite(pageSize, pageNo,
					status);
		}
	}

	/***
	 * 根据黑粉顺序获取提名的信息
	 * 
	 * @return
	 */
	public List<WdNominationInfoBean> getWdNominationInfoBeansByBlack(
			int pageSize, int pageNo, int status) {
		try (SqlSession session = factory.openSession()) {
			IWdNominationInfoBean mapper = session
					.getMapper(IWdNominationInfoBean.class);
			return mapper.getWdNominationInfoBeansByBlack(pageSize, pageNo,
					status);
		}
	}

	/**
	 * 获取所有提名的数量
	 * 
	 * @param code
	 * @return
	 */
	public Integer getCountWdNominationInfoBeans(int status) {
		try (SqlSession session = factory.openSession()) {
			IWdNominationInfoBean mapper = session
					.getMapper(IWdNominationInfoBean.class);
			if (mapper.getCountWdNominationInfoBeans(status) == null) {
				return 0;
			}
			return mapper.getCountWdNominationInfoBeans(status);
		}
	}

	/***
	 * 增加投票数
	 * 
	 * @param type
	 * @param code
	 */
	public void setVoteNum(int type, int code) {
		try (SqlSession session = factory.openSession()) {
			IWdNominationInfoBean mapper = session
					.getMapper(IWdNominationInfoBean.class);
			mapper.setVoteNum(type, code,new Date());
			session.commit();
		}
	}

	/**
	 * 
	 * @Title: getPageNum
	 * @Description: TODO 获取通过作品总数
	 * @param @return
	 * @return Integer
	 * @throws
	 */
	public Integer getPageNum() {
		try (SqlSession session = factory.openSession()) {
			IWdNominationInfoBean mapper = session
					.getMapper(IWdNominationInfoBean.class);
			return mapper.getPageNum();
		}
	}

	/**
	 * 
	 * @Title: selectWdNominationInfoBeanByAccountName
	 * @Description: TODO 查询提名简介
	 * @param @param accountName
	 * @param @return
	 * @return WdNominationInfoBean
	 * @throws
	 */
	public WdNominationInfoBean selectNominatedContentByCode(int code) {
		try (SqlSession session = factory.openSession()) {
			IWdNominationInfoBean mapper = session
					.getMapper(IWdNominationInfoBean.class);
			return mapper.selectNominatedContentByCode(code);
		}
	}

	/***
	 * 得到相同名次下白名单的最终名次
	 * 
	 * @param code
	 * @param whiteNum
	 * @return
	 */
	public Integer getWhiteOrderWhenSameOrder(Date code, int whiteNum) {
		try (SqlSession session = factory.openSession()) {
			IWdNominationInfoBean mapper = session
					.getMapper(IWdNominationInfoBean.class);
			return mapper.getWhiteOrderWhenSameOrder(code, whiteNum);
		}
	}

	/***
	 * 得到相同名次下黑名单的最终名次
	 * 
	 * @param code
	 * @param whiteNum
	 * @return
	 */
	public Integer getBlackOrderWhenSameOrder(Date code, int blackNum) {
		try (SqlSession session = factory.openSession()) {
			IWdNominationInfoBean mapper = session
					.getMapper(IWdNominationInfoBean.class);
			return mapper.getBlackOrderWhenSameOrder(code, blackNum);
		}
	}
	
	/**
	 * 查询所有提名账号--分页
	 * @return
	 */
	public List<String> selectNominationAccount(int pageSize,int pageNo,String accountName) {
		try (SqlSession session = factory.openSession()) {
			IWdNominationInfoBean mapper = session
					.getMapper(IWdNominationInfoBean.class);
			return mapper.selectNominationAccount(pageSize, pageNo, accountName);
		}
	}
	
	/**
	 * 查询所有提名账号
	 * @return
	 */
	public List<String> selectAllNominationAccount() {
		try (SqlSession session = factory.openSession()) {
			IWdNominationInfoBean mapper = session
					.getMapper(IWdNominationInfoBean.class);
			return mapper.selectAllNominationAccount();
		}
	}
	
	/**
	 * 查询通过提名账号数量
	 * @param accountName
	 * @return
	 */
	public Integer selectNominationAccountNum(String accountName) {
		try (SqlSession session = factory.openSession()) {
			IWdNominationInfoBean mapper = session
					.getMapper(IWdNominationInfoBean.class);
			return mapper.selectNominationAccountNum(accountName);
		}
	}
	/**
	 * 查询提名账号数量
	 * @return
	 */
	public Integer selectAllNominationAccountNum() {
		try (SqlSession session = factory.openSession()) {
			IWdNominationInfoBean mapper = session
					.getMapper(IWdNominationInfoBean.class);
			return mapper.selectAllNominationAccountNum();
		}
	}
	/**
	 * 查询提名IP数量
	 * @return
	 */
	public Integer selectNominationIpNum() {
		try (SqlSession session = factory.openSession()) {
			IWdNominationInfoBean mapper = session
					.getMapper(IWdNominationInfoBean.class);
			return mapper.selectNominationIpNum();
		}
	}
	
	/**
	 * 根据账号查询提名相关信息
	 * @param accountName
	 * @return
	 */
	public WdNominationInfoBean selectNominationInfo(String accountName) {
		try (SqlSession session = factory.openSession()) {
			IWdNominationInfoBean mapper = session
					.getMapper(IWdNominationInfoBean.class);
			return mapper.selectNominationInfo(accountName);
		}
	}
	
	/**
	 * 每日为自己投票数
	 * @param pageBean
	 * @return
	 */
	public int getNomanitionCountForMe(PageBean pageBean) {
		try (SqlSession session = factory.openSession()) {
			IWdNominationInfoBean mapper = session
					.getMapper(IWdNominationInfoBean.class);
			return mapper.getNomanitionCountForMe(pageBean);
		}
	}
	
	/**
	 * 每日为他人投票数
	 * @param pageBean
	 * @return
	 */
	public int getNomanitionCountForHe(PageBean pageBean) {
		try (SqlSession session = factory.openSession()) {
			IWdNominationInfoBean mapper = session
					.getMapper(IWdNominationInfoBean.class);
			return mapper.getNomanitionCountForHe(pageBean);
		}
	}

}