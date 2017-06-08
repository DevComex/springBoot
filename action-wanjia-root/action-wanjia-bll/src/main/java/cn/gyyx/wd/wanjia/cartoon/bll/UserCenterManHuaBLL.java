/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：才帅
 * 联系方式：caishuai@gyyx.cn 
 * 创建时间：2016-1-3
 * 版本号：v1.0
 * 本类主要用途叙述：玩家天地，漫画专区，用户中心，漫画部分内容bll
 */
package cn.gyyx.wd.wanjia.cartoon.bll;

import java.util.List;
import java.util.Map;

import cn.gyyx.wd.wanjia.cartoon.beans.WanwdUser;

public interface UserCenterManHuaBLL {
	/**
	 * @description 查询用户上传的漫画章节信息，分页
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> selectUserUpload(Map<String, Object> map);

	/**
	 * @description 查询用户上传的漫画章节总条数
	 * @param map
	 * @return
	 */
	public Integer selectUserUploadTotalSize(Map<String, Object> map);

	/**
	 * @description 查询用户漫画编辑回复通知消息，分页
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> selectManagerReply(Map<String, Object> map);

	/**
	 * @description 查询用户漫画编辑回复通知消息总条数
	 * @param map
	 * @return
	 */
	public Integer selectManagerReplyTotalSize(Map<String, Object> map);

	/**
	 * @description 查询作者被推荐的漫画
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> selectRecommendManHua(Map<String, Object> map);

	/**
	 * @description 查询作者被推荐的漫画总数
	 * @param map
	 * @return
	 */
	public int selectRecommendManHuaTotalSize(Map<String, Object> map);

	/**
	 * @description 查询用户收藏的ManHua
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> selectUserCollection(Map<String, Object> map);

	/**
	 * @description 查询用户收藏的ManHua总数量
	 * @param map
	 * @return
	 */
	public int selectUserCollectionTotalSize(Map<String, Object> map);

	/**
	 * @description 查询漫画的消息通知
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> selectMessageAboutManHua(Map<String, Object> map);

	/**
	 * @description 查询漫画的消息通知总条数
	 * @param map
	 * @return
	 */
	int selectMessageAboutManHuaTotalSize(Map<String, Object> map);
	/**
	 * 查询登陆用户角色信息
	 * 
	 * @param userId
	 * @return
	 */
	public List<WanwdUser> getRoleInfo(int userId);
}
