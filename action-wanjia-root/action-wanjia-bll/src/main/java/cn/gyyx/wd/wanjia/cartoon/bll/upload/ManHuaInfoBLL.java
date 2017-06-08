/**
 * --------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：才帅
 * 联系方式：caishuai@gyyx.cn 
 * 创建时间：2016-12-6
 * 版本号：v1.0
 * 本类主要用途叙述：玩家天地，漫画上传漫画信息的BLL
 */
package cn.gyyx.wd.wanjia.cartoon.bll.upload;

import java.util.List;

import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.wd.wanjia.cartoon.beans.UploadFormBean;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdManhua;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdManhuaBook;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdManhuaPage;

public interface ManHuaInfoBLL {
	/**
	 * 查询当前作者的所有未完结漫画的名字
	 * 
	 * @param user
	 * @return
	 */
	public List<WanwdManhua> findAllUnfinishedManhuaNameByAuthor(UserInfo user);

	/**
	 * 插入一个新的漫画
	 * 
	 * @param manhua
	 * @return
	 */
	public Integer addManhua(WanwdManhua manhua);

	/**
	 * 更新漫画的状态
	 * 
	 * @param manhua
	 * @return
	 */
	public Integer updateManhua(WanwdManhua manhua);

	/**
	 * 查询漫画名为title的漫画
	 * 
	 * @param title
	 * @return
	 */
	public WanwdManhua findManhuaByTitle(String title);

	/**
	 * 查询漫画最新章节，并+1返回下一章节
	 * 
	 * @param manhua
	 * @return
	 */
	public Integer findManhuaNextBookNum(WanwdManhua manhua);

	/**
	 * 存储上传的漫画图片集合
	 * 
	 * @param pages
	 * @return
	 */
	public void insertPages(WanwdManhuaPage pages);

	/**
	 * 存储上传的章节信息
	 * 
	 * @param book
	 * @return
	 */
	public Integer insertBook(WanwdManhuaBook book);

	WanwdManhua selectByPrimaryKey(Integer code);

}
