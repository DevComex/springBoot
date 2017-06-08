/*************************************************
       Copyright ©, 2015, GY Game
       Author: 柳佳琦
       Created: 2016年3月24日
       Note：名人争霸赛留言业务层
************************************************/
package cn.gyyx.action.bll.wdchampionship;

import java.util.List;


import cn.gyyx.action.beans.wdchampionship.ChampionshipCommenttInfoBean;
import cn.gyyx.action.dao.wdchampionship.ChampionshipCommentInfoDAO;
import cn.gyyx.action.dao.wdchampionship.IChampionshipCommentInfoMapper;

/**
 * @ClassName: ChampionshipCommentInfoBLL
 * @Description: TODO 名人争霸赛留言业务层
 * @author 柳佳琦 liujiaqi@gyyx.cn
 * @date 2016年3月24日 下午4:04:53
 */
public class ChampionshipCommentInfoBLL {
	private IChampionshipCommentInfoMapper dao = new ChampionshipCommentInfoDAO();
	
	/**
	* @Title: addComment
	* @Description: TODO 添加一条评论
	* @date 2016年3月24日 下午4:07:47
	* @param comment
	* @return boolean
	 */
	public boolean addComment(ChampionshipCommenttInfoBean comment) {
		String content = comment.getCommentContent();
		// 替换掉评论中的HTML 标签
		content = content.replace("<", "&lt;");
		content = content.replace(">", "&gt;");
		comment.setCommentContent(content);
		return dao.addComment(comment) > 0;
	}

	/**
	* @Title: selectComment
	* @Description: TODO 分页查询评论
	* @date 2016年3月24日 下午4:14:15
	* @param isDel 是否被删除
	* @param typeOfYear 届数
	* @param currentPage 当前页
	* @param pageSize 每页记录数   
	* @return List<ChampionshipCommenttInfoBean>
	 */
	public List<ChampionshipCommenttInfoBean> selectComment(boolean isDel,
			int typeOfYear,int currentPage,int pageSize) {
		currentPage = Math.max(1, currentPage);
		int startIndex = (currentPage - 1) * pageSize + 1 ;
		int endIndex = currentPage * pageSize;
		
		return dao.selectComment(isDel, typeOfYear, startIndex, endIndex);
	}

	/**
	* @Title: selectCommentCount
	* @Description: TODO 查询评论总数
	* @date 2016年3月24日 下午4:08:37
	* @param @param isDel
	* @param @param typeOfYear
	* @param @return    
	* @return int
	 */
	public int selectCommentCount(boolean isDel, int typeOfYear) {
		return dao.selectCommentCount(isDel, typeOfYear);
	}

	/**
	* @Title: selectTopComment
	* @Description: TODO 查询最新的前99条审核通过的评论
	* @date 2016年3月24日 下午4:09:19
	* @param @param typeOfYear
	* @param @return    
	* @return List<ChampionshipCommenttInfoBean>
	 */
	public List<ChampionshipCommenttInfoBean> selectTopComment(int typeOfYear) {
		return dao.selectTopComment(typeOfYear);
	}

}
