/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：xuanwuba
 * @作者：yangyusheng
 * @联系方式：yangyusheng@gyyx.cn
 * @创建时间： 2015年7月8日 上午10:36:26
 * @版本号：
 * @本类主要用途描述：玩家信息连接数据库
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.xwbcreditsluckydraw;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.xwbcreditsluckydraw.PlayerInfoBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class PlayerinfoDAO {

	private static final Logger logger = GYYXLoggerFactory
			.getLogger(PlayerinfoDAO.class);
	/**
	 * 获取玩家信息（用户名）
	 * @param account
	 * @return
	 */
	public PlayerInfoBean selectInfoByAccount(String account){
		PlayerInfoBean playerInfo = null;
		SqlSession session = getSession();
		try {
			IPlayerinfoMapper playerinfoMapper = session
					.getMapper(IPlayerinfoMapper.class);
			playerInfo = playerinfoMapper.selectInfoByAccount(account);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return playerInfo;
	}

	/**
	 * 
	 * @日期：2015年7月8日
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
	 * 
	 * @日期：2015年7月8日
	 * @Title: getPlayers
	 * @Description: TODO 获取所有玩家信息
	 * @return List<PlayerInfoBean>
	 */
	public List<PlayerInfoBean> getPlayers() {
		List<PlayerInfoBean> playerList = null;
		SqlSession session = getSession();
		try {
			IPlayerinfoMapper playerinfoMapper = session
					.getMapper(IPlayerinfoMapper.class);
			playerList = playerinfoMapper.getPlayers();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return playerList;
	}

	/**
	 * 
	 * @日期：2015年7月8日
	 * @Title: addPlayer
	 * @Description: TODO 添加玩家信息
	 * @param player
	 * @return int
	 */
	public int addPlayer(PlayerInfoBean player) {
		int result = 0;
		SqlSession session = getSession();
		try {
			IPlayerinfoMapper playerinfoMapper = session
					.getMapper(IPlayerinfoMapper.class);
			result = playerinfoMapper.addPlayer(player);
			session.commit();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return result;
	}

	/**
	 * 
	 * @日期：2015年7月9日
	 * @Title: getTotal
	 * @Description: TODO 查询玩家信息总数
	 * @return int
	 */
	public int getTotal() {
		int result = 0;
		SqlSession session = getSession();
		try {
			IPlayerinfoMapper playerinfoMapper = session
					.getMapper(IPlayerinfoMapper.class);
			result = playerinfoMapper.getTotal();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return result;
	}

	/**
	 * 
	 * @日期：2015年7月9日
	 * @Title: getPlayerByPage
	 * @Description: TODO 根据页数查询玩家信息
	 * @param page
	 * @return List<PlayerInfoBean>
	 */
	public List<PlayerInfoBean> getPlayerByPage(int page) {
		List<PlayerInfoBean> playerList = null;
		SqlSession session = getSession();
		try {
			IPlayerinfoMapper playerinfoMapper = session
					.getMapper(IPlayerinfoMapper.class);
			playerList = playerinfoMapper.getPlayerByPage(page);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return playerList;
	}

	/**
	 * 
	 * @日期：2015年7月9日
	 * @Title: getTotalByServer
	 * @Description: TODO 根据区服查询所有玩家数量
	 * @param player
	 * @return int
	 */
	public int getTotalByServer(PlayerInfoBean player) {
		int result = 0;
		SqlSession session = getSession();
		try {
			IPlayerinfoMapper playerinfoMapper = session
					.getMapper(IPlayerinfoMapper.class);
			result = playerinfoMapper.getTotalByServer(player);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return result;
	}

	/**
	 * 
	 * @日期：2015年7月9日
	 * @Title: getPlayerByPageAndNetType
	 * @Description: TODO 根据区服和页数查询玩家信息
	 * @param player
	 * @return List<PlayerInfoBean>
	 */
	public List<PlayerInfoBean> getPlayerByPageAndNetType(PlayerInfoBean player) {
		List<PlayerInfoBean> playerList = null;
		SqlSession session = getSession();
		try {
			IPlayerinfoMapper playerinfoMapper = session
					.getMapper(IPlayerinfoMapper.class);
			playerList = playerinfoMapper.getPlayerByPageAndNetType(player);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return playerList;
	}
	public String getSexByAccount(String account){
		String sex = "";
		SqlSession session = getSession();
		try {
			IPlayerinfoMapper playerinfoMapper = session
					.getMapper(IPlayerinfoMapper.class);
			sex = playerinfoMapper.getSexByAccount(account);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return sex;
	}
	public Integer updateplayerinfo(PlayerInfoBean playerInfoBean){
		int result = 0;
		SqlSession session = getSession();
		try {
			IPlayerinfoMapper playerinfoMapper = session
					.getMapper(IPlayerinfoMapper.class);
			result = playerinfoMapper.updateplayerinfo(playerInfoBean);
			session.commit();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return result;
	}
}
