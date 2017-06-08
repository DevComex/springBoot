/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：问道十周年粉丝榜
 * @作者：chenpeilan
 * @联系方式：chenpeilan@gyyx.cn
 * @创建时间： 2016年3月29日
 * @版本号：
 * @本类主要用途描述：问道十周年粉丝榜活动评论相关接口
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.wdtenyearfans;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.wdtenyearfans.PageBean;
import cn.gyyx.action.beans.wdtenyearfans.WdCommentsBean;

public interface IWdCommentsBean {
	public void insertWdCommentsBean(WdCommentsBean bean);
	public WdCommentsBean selectWdCommentsBeanByCode(@Param("code")int code);
	public void updateWdCommentsBean(WdCommentsBean bean);
	public List<WdCommentsBean> selectWdCommentsBeanList(PageBean pageBean);
	public List<WdCommentsBean> selectDeleteWdCommentsBeanList(PageBean pageBean);
	public void updateFlag(WdCommentsBean bean);
	public int selectWdCommentsCount(PageBean pageBean);
	public int selectDeleteWdCommentsCount(PageBean pageBean);
	
	public List<WdCommentsBean> selectWdCommentsBean(@Param("nominationCode") int nominationCode);
}