/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：才帅
 * 联系方式：caishuai@gyyx.cn 
 * 创建时间：2016-12-12
 * 版本号：v1.0
 * 本类主要用途叙述：玩家天地，漫画专区，漫画浏览的Service接口
 */
package cn.gyyx.wd.wanjia.cartoon.service;

import java.util.Map;

import cn.gyyx.core.auth.UserInfo;

public interface ManHuaBrowseService {
	/**
	 * 
	 * 得到漫画目录页漫画信息和点赞收藏阅读状态by manhua code,和登录信息
	 * 
	 * @param code
	 * @param userInfo
	 * @return
	 */
	public Map<String,Object> getManHuaInfo(Integer code, UserInfo userInfo);

	/**
	 * 添加漫画点赞信息
	 * 
	 * @param userInfo
	 * @param manhuaCode
	 * @return
	 */
	public boolean addGoodStatus(UserInfo userInfo, Integer manhuaCode);

	/**
	 * 浏览漫画，具体信息调取
	 * 
	 * @param userInfo
	 * @param manhuaCode
	 * @param bookNum
	 * @param pageNum
	 * @return
	 */
	public Map<String,Object> manhuaBrowse(UserInfo userInfo, Integer manhuaCode, Integer bookNum, Integer pageNum);

	/**
	 * 浏览漫画最新章节
	 * 
	 * @param userInfo
	 * @param manhuaCode
	 * @return
	 */
	public Map<String,Object> getNewestBook(UserInfo userInfo, Integer manhuaCode);

	/**
	 * 漫画阅读量 +1
	 * 
	 * @param manhuaCode
	 * @return
	 */
	public boolean readCountPlus(Integer manhuaCode);

}
