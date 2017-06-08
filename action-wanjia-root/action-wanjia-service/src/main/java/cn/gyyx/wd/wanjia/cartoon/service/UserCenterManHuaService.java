/**
 * --------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：才帅
 * 联系方式：caishuai@gyyx.cn 
 * 创建时间：2016-1-3
 * 版本号：v1.0
 * 本类主要用途叙述：玩家天地，漫画专区，用户中心，漫画部分内容Service接口
 */
package cn.gyyx.wd.wanjia.cartoon.service;

import java.util.List;
import java.util.Map;

import cn.gyyx.wd.wanjia.cartoon.beans.Constans;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdUser;

public interface UserCenterManHuaService {
	/**
	 * @description 用户中心漫画上传，获取用户上传的漫画章节信息
	 * @param status
	 *            DEFAULT {@link Constans}REVIEW three status and RECOMMEND
	 * @param resourceType
	 * @param userId
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> getUpload(String status, Integer resourceType, Integer userId, Integer pageIndex,
			Integer pageSize);

	/**
	 * @description 用户中心，漫画信息通知，包括审核和推荐信息
	 * @param userId
	 * @param resourceType
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> getMessage(Integer userId, Integer resourceType, Integer pageIndex, Integer pageSize);


	/**
	 * @description 用户中心，漫画信息编辑回复通知
	 * @param userId
	 * @param resourceType
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> getManagerReply(Integer userId, Integer resourceType, Integer pageIndex,
			Integer pageSize);

	/**
	 * @description 用户中心，用户收藏好的漫画
	 * @param userId
	 * @param resourceType
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> getCollection(Integer userId, Integer resourceType, Integer pageIndex, Integer pageSize);
	public List<WanwdUser> getRoleInfo(int userId);
}
