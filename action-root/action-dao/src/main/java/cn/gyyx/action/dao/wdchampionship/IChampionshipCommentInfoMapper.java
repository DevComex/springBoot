/*************************************************
       Copyright ©, 2015, GY Game
       Author: 柳佳琦
       Created: 2016年3月24日
       Note：名人争霸赛评论数据访问
************************************************/
package cn.gyyx.action.dao.wdchampionship;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.wdchampionship.ChampionshipCommenttInfoBean;

/**
 * @ClassName: IChampionshipCommentInfoMapper
 * @Description: TODO 名人争霸赛评论数据访问接口.
 * @author 柳佳琦 liujiaqi@gyyx.cn
 * @date 2016年3月24日 上午10:48:44
 */
public interface IChampionshipCommentInfoMapper {
	
	/**
	* @Title: addComment
	* @Description: TODO 添加一条评论.
	* @date 2016年3月24日 上午11:00:42
	* @param @param comment 评论实体
	* @param @return    
	* @return int
	 */
	public int addComment(ChampionshipCommenttInfoBean comment);
	
	
	/**
	* @Title: selectComment
	* @Description: TODO 分页查询留言.
	* @date 2016年3月24日 下午3:54:55
	* @param @param isDel 是否被删除
	* @param @param typeOfYear 届数
	* @param @param startIndex 查询开始索引
	* @param @param endStartIndex 查询结束索引
	* @param @return    
	* @return List<ChampionshipCommenttInfoBean>
	 */
	public List<ChampionshipCommenttInfoBean> selectComment(@Param("isDel")boolean isDel,
															@Param("typeOfYear")int typeOfYear,
															@Param("startIndex")int startIndex,
															@Param("endIndex")int endIndex);

	/**
	* @Title: selectCommentCount
	* @Description: TODO 查询留言总数
	* @date 2016年3月24日 下午3:56:07
	* @param @param isDel
	* @param @param typeOfYear
	* @param @return    
	* @return int
	 */
	public int selectCommentCount(@Param("isDel")boolean isDel,
								  @Param("typeOfYear")int typeOfYear);
	
	
	/**
	* @Title: selectTopComment
	* @Description: TODO 查询最新审核通过的99条留言
	* @date 2016年3月24日 下午3:56:29
	* @param @param typeOdYear
	* @param @return    
	* @return List<ChampionshipCommenttInfoBean>
	 */
	public List<ChampionshipCommenttInfoBean> selectTopComment(int typeOfYear);
}
