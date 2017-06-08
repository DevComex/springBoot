/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年3月31日上午9:32:21
 * 版本号：v1.0
 * 本类主要用途叙述：评论的业务层
 */

package cn.gyyx.action.bll.wdtenyearfans;

import java.util.List;

import cn.gyyx.action.beans.wdtenyearfans.WdCommentsBean;
import cn.gyyx.action.dao.wdtenyearfans.WdCommentsDAO;

public class WdCommentsBll {
	private WdCommentsDAO wdCommentsDAO = new WdCommentsDAO();

	/***
	 * 增加评论
	 * 
	 * @param wdCommentsBean
	 */
	public void addtWdCommentsBean(WdCommentsBean wdCommentsBean) {
		wdCommentsDAO.insertWdCommentsBean(wdCommentsBean);
	}
	
	/**
	 * 
	* @Title: selectWdCommentsBean
	* @Description: TODO 查询已通过的评论
	* @param @return    
	* @return WdCommentsBean    
	* @throws
	 */
	public List<WdCommentsBean> getWdCommentsBean(int nominationCode) {
		return wdCommentsDAO.selectWdCommentsBean(nominationCode);
	}
}
