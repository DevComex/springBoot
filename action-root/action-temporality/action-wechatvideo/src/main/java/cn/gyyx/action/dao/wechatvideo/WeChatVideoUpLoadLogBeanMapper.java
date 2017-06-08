/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 *
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：
 * @作者：tanjunkai        
 * @联系方式：tanjunkai@gyyx.cn
 * @创建时间：2017/3/14 14:48
 * @版本号：0.0.1 -------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.wechatvideo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.wechatvideo.WeChatVideoUpLoadLogBean;

/**
 * <p>
 * 视频上传相关接口
 * </p>
 * 
 * @author tanjunkai
 * @since 0.0.1
 */
public interface WeChatVideoUpLoadLogBeanMapper {

    /**
     * <p>
     * 新增上传视频
     * </p>
     *
     * @action tanjunkai 2017年3月15日 下午5:54:45 描述
     *
     * @param record
     *            上传视频信息实体
     * @return int 影响的行数
     */
    int insert(WeChatVideoUpLoadLogBean record);

    /**
     * <p>
     * 新增上传视频
     * </p>
     *
     * @action tanjunkai 2017年3月15日 下午5:54:45 描述
     *
     * @param record
     *            上传视频信息实体
     * @return int 影响的行数
     */
    int insertSelective(WeChatVideoUpLoadLogBean record);

    /**
     * <p>
     * 通过主键获取视频信息
     * </p>
     *
     * @action tanjunkai 2017年3月15日 下午5:53:43 描述
     *
     * @param code
     * @return WeChatVideoUpLoadLogBean
     */
    WeChatVideoUpLoadLogBean selectByPrimaryKey(Integer code);

    /**
     * <p>
     * 根据userId获取上传视频次数
     * </p>
     *
     * @action tanjunkai 2017年3月15日 下午5:54:15 描述
     *
     * @param userId
     * @return List<WeChatVideoUpLoadLogBean>
     */
    List<WeChatVideoUpLoadLogBean> getUploadTimesByUserId(
            @Param("userId") Integer userId,@Param("status") Integer status);

    /**
     * <p>
     * 搜索审核通过的视频
     * </p>
     *
     * @action tanjunkai 2017年3月15日 下午5:52:47 描述
     *
     * @param searchPar
     *            账号、角色名或微信名
     * @return List<WeChatVideoUpLoadLogBean>
     */
    List<WeChatVideoUpLoadLogBean> selectByParam(
            @Param("searchPar") String searchPar);

    /**
     * <p>
     * 首页根据页码获取视频列表
     * </p>
     *
     * @action tanjunkai 2017年3月15日 下午4:58:56 描述
     *
     * @param skipCount
     *            从多少条开始取
     * @param pageCount
     *            每页显示的条数
     * @return List<WeChatVideoUpLoadLogBean>
     */
    List<WeChatVideoUpLoadLogBean> getVideoByIndex(
            @Param("skipCount") int skipCount,
            @Param("pageCount") int pageCount);

    int updateByPrimaryKeySelective(WeChatVideoUpLoadLogBean record);

    /**
     * <p>
     * OA后台审核获取所有上传视频列表
     * </p>
     *
     * @action tanjunkai 2017年3月16日 下午3:17:18 描述
     *
     * @param beginTime
     *            开始时间
     * @param endTime
     *            结束时间
     * @param keyWord
     *            关键字
     * @param verifyStatus
     *            视频状态
     * @param pageCount
     *            每页显示条数
     * @param index
     *            当前页码
     * @return List<WeChatVideoUpLoadLogBean>
     */
    List<WeChatVideoUpLoadLogBean> getVideoList(
            @Param("beginTime") String beginTime,
            @Param("endTime") String endTime, @Param("keyWord") String keyWord,
            @Param("verifyStatus") String verifyStatus,
            @Param("skipCount") int skipCount,
            @Param("pageCount") int pageCount);

    /**
     * <p>
     * 获取上传视频总数
     * </p>
     *
     * @action tanjunkai 2017年3月16日 下午6:07:37 描述
     *
     * @return int
     */
    int getVideoCount(@Param("beginTime") String beginTime,
            @Param("endTime") String endTime, @Param("keyWord") String keyWord,
            @Param("verifyStatus") String verifyStatus);

    /**
     * <p>
     * 根据视频状态获取过滤用户后视频总数
     * </p>
     *
     * @action tanjunkai 2017年3月25日 下午14:56:37 描述
     *
     * @return int
     */
    int getDistinctUserIdCount(@Param("verifyStatus") String verifyStatus);
    
    /**
     * <p>
     * 更新视频信息
     * </p>
     *
     * @action tanjunkai 2017年3月15日 下午5:55:34 描述
     *
     * @param record
     *            视频实体信息
     * @return int 影响的行数
     */
    int updateByPrimaryKey(WeChatVideoUpLoadLogBean record);
    
    
    /**
      * <p>
      *    OA后台更新视频状态
      * </p>
      *
      * @action
      *    tanjunkai 2017年3月29日 下午8:47:11 描述
      *
      * @return int
      */
    int updateVideoStatusByPrimaryKey(@Param("videoId") int videoId,@Param("videoStatus") String videoStatus);
    
    /**
     * <p>
     *    增加视频点赞
     * </p>
     *
     * @action
     *    tanjunkai 2017年3月29日 下午6:32:59 描述
     *
     * @param code
     */
   int addVoteTimes(@Param("videoId")int videoId);
    
}