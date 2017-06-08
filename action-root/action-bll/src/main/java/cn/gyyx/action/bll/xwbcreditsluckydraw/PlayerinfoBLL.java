/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：xuanwuba
 * @作者：yangyusheng
 * @联系方式：yangyusheng@gyyx.cn
 * @创建时间： 2015年7月8日 上午10:38:08
 * @版本号：
 * @本类主要用途描述：玩家信息逻辑层
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.bll.xwbcreditsluckydraw;

import java.util.List;

import cn.gyyx.action.beans.xwbcreditsluckydraw.PlayerInfoBean;
import cn.gyyx.action.dao.xwbcreditsluckydraw.PlayerinfoDAO;

public class PlayerinfoBLL {

	private PlayerinfoDAO playerDao = new PlayerinfoDAO();
	/**
	 * 获取玩家信息（用户名）
	 * @param account
	 * @return
	 */
	public PlayerInfoBean getInfoByAccount(String account){
		return playerDao.selectInfoByAccount(account);
	}

	/**
	 * 
	 * @日期：2015年7月8日
	 * @Title: getPlayers
	 * @Description: TODO 获取所有玩家信息
	 * @return List<PlayerInfoBean>
	 */
	public List<PlayerInfoBean> getPlayers() {
		return playerDao.getPlayers();
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
		return playerDao.addPlayer(player);
	}

	/**
	 * 
	 * @日期：2015年7月9日
	 * @Title: getTotal
	 * @Description: TODO 获取玩家信息数量总数
	 * @return int
	 */
	public int getTotal() {
		return playerDao.getTotal();
	}

	/**
	 * 
	 * @日期：2015年7月9日
	 * @Title: getPlayerByPage
	 * @Description: TODO 根据页数获取玩家信息
	 * @param page
	 * @return List<PlayerInfoBean>
	 */
	public List<PlayerInfoBean> getPlayerByPage(int page) {
		return playerDao.getPlayerByPage(page);
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
		return playerDao.getTotalByServer(player);
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
		return playerDao.getPlayerByPageAndNetType(player);
	}
	
	public String getSexByAccount(String account){
		return playerDao.getSexByAccount(account);
	}
	public Integer updateplayerinfo(PlayerInfoBean playerInfoBean){
		return playerDao.updateplayerinfo(playerInfoBean);
		
	}
}
