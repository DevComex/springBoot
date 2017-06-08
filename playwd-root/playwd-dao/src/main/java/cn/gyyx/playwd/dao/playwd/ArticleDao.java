package cn.gyyx.playwd.dao.playwd;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.playwd.beans.playwd.ArticleBean;

public interface ArticleDao {
	
    int insertSelective(ArticleBean record);

    ArticleBean selectByPrimaryKey(Integer code);

    int updateByPrimaryKeySelective(ArticleBean record);

    int updateByPrimaryKeyWithBLOBs(ArticleBean record);

    int updateByPrimaryKey(ArticleBean record);

    //更新文章状态
    int updateArticleStatusById(@Param("code")int code, @Param("status") String status,
			@Param("recommendDetail") String recommendDetail,@Param("prizeId") int prizeId);

    int selectByTitle(@Param("title")String title);
    
    List<ArticleBean> getList(Map<String, Object> map);
    
    int getCount(Map<String, Object>  map);

    List<ArticleBean> getArticleByUserId(Map<String, Object> map);

    /**
     * 获取自然月内被推荐的文章
     * @param userId
     * @return
     */
    List<ArticleBean> selectListByMonthUserId(int userId);

    /**
     * 获取推荐管理列表
     * @param slotId
     * @param prizeId
     * @param authorType
     * @param startCode
     * @param pageSize
     * @return
     */
    List<ArticleBean>selectRecommendManagementList(@Param("slotId")int slotId,@Param("prizeId")int prizeId
    		,@Param("authorType")String authorType,@Param("startCode")int startCode,@Param("pageSize")int pageSize);
    
    /**
     * 获取推荐管理列表数量
     * @param slotId
     * @param prizeId
     * @param authorType
     * @return
     */
    int selectRecommendManagementCount(@Param("slotId")int slotId,@Param("prizeId")int prizeId
    		,@Param("authorType")String authorType);
    
    int getCountByUserId(Map<String, Object> map);

    List<ArticleBean> findAuthorInfoByUserId(Integer userId);
    
    /**
     * 获取推荐记录
     * @param prizeId
     * @param startDate
     * @param endDate
     * @param startCode
     * @param pageSize
     * @return
     */
    List<ArticleBean>selectRecommendRecordList(@Param("prizeId")int prizeId,
    		@Param("startDate")Date startDate,@Param("endDate")Date endDate,
    		@Param("startCode")int startCode,@Param("pageSize")int pageSize);
    
    /**
     * 获取推荐记录数量
     * @param prizeId
     * @param startDate
     * @param endDate
     * @return
     */
    int selectRecommendRecordCount(@Param("prizeId")int prizeId,
    		@Param("startDate")Date startDate,@Param("endDate")Date endDate);
    
    /**
     * 修改文章备注
     * @param contentId
     * @param remark
     * @return
     */
    int updateRemark(@Param("contentId") int contentId,@Param("remark") String remark);
    
    List<ArticleBean> getAtricleList(Map<String, Object> map);

    int getAtricleCount(Map<String, Object> map);

    /**
     * 获取用户审核通过的文章数量
     * @param code
     * @return
     */
	int getPassedArtitleCountByUserId(@Param("userId") int userId);

	/**
	 * 获取图文列表-根据用户ID和状态取 topN
	 * @param userId
	 * @param statusList
	 * @param topN
	 * @param orderBy
	 * @return
	 */
	List<ArticleBean> getUserArticleList(@Param("userId")Integer userId,
			@Param("statusList")List<String> statusList,@Param("topN") int topN,
		@Param("orderBy")String orderBy);
	
	/**
	 * 获取用户服务器信息和作者名
	 * @param userId
	 * @return
	 */
	ArticleBean selectServerNameAndAuth(@Param("userId") int userId);

	/**
     * 增加浏览量
     * @param code
     */
	int increaseViewCount(int code);

	/**
     * 增加点赞量
     * @param code
     */
	int increaseLikeCount(int code);

	/**
     * 增加收藏量
     * @param code
     */
	int increaseCollectCount(int code);
}