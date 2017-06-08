/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：xuanwuba
 * @作者：yangyusheng
 * @联系方式：yangyusheng@gyyx.cn
 * @创建时间： 2015年7月8日 上午10:01:46
 * @版本号：
 * @本类主要用途描述：玩家信息接口
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.xwbcreditsluckydraw;

import java.util.List;

import cn.gyyx.action.beans.xwbcreditsluckydraw.PlayerInfoBean;

public interface IPlayerinfoMapper {
	/**
	 * 获取玩家信息（用户名）
	 * @param account
	 * @return
	 */
	public PlayerInfoBean selectInfoByAccount(String account);

	/**
	 * 
	 * @日期：2015年7月8日
	 * @Title: getPlayers
	 * @Description: TODO 获取所有玩家信息
	 * @return List<PlayerInfoBean>
	 */
	public List<PlayerInfoBean> getPlayers();

	/**
	 * 
	 * @日期：2015年7月8日
	 * @Title: addPlayer
	 * @Description: TODO 添加玩家信息
	 * @param player
	 * @return int
	 */
	public int addPlayer(PlayerInfoBean player);

	/**
	 * 
	 * @日期：2015年7月9日
	 * @Title: getTotal
	 * @Description: TODO 获取玩家信息总数
	 * @return int
	 */
	public int getTotal();

	/**
	 * 
	 * @日期：2015年7月9日
	 * @Title: getPlayerByPage
	 * @Description: TODO 根据页数进行查询
	 * @param page
	 * @return List<PlayerInfoBean>
	 */
	public List<PlayerInfoBean> getPlayerByPage(int page);

	/**
	 * 
	 * @日期：2015年7月9日
	 * @Title: getTotalByServer
	 * @Description: TODO 根据区服查询所有玩家数量
	 * @param player
	 * @return int
	 */
	public int getTotalByServer(PlayerInfoBean player);

	/**
	 * 
	 * @日期：2015年7月9日
	 * @Title: getPlayerByPageAndNetType 根据页数和区服查询
	 * @Description: TODO
	 * @param player
	 * @return List<PlayerInfoBean>
	 */
	public List<PlayerInfoBean> getPlayerByPageAndNetType(PlayerInfoBean player);
	/**
	 * 根据账号查询性别
	 * @param account
	 * @return
	 */
	public String getSexByAccount(String account);
	
	public Integer updateplayerinfo(PlayerInfoBean playerInfoBean);
}
