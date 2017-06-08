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

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.wechatvideo.WeChatVideoVoteLogBean;

/**
 * <p>
 * 视频点赞相关接口
 * </p>
 * 
 * @author tanjunkai
 * @since 0.0.1
 */
public interface WeChatVideoVoteLogBeanMapper {

    /**
     * <p>
     * 新增点赞记录
     * </p>
     *
     * @action tanjunkai 2017年3月15日 下午6:00:55 描述
     *
     * @param 视频点赞实体
     * @return int 影响的行数
     */
    int insert(WeChatVideoVoteLogBean record);

    /**
     * <p>
     * 插入点赞日志
     * </p>
     *
     * @action tanjunkai 2017年3月15日 下午5:59:58 描述
     *
     * @param record
     *            视频点赞实体
     * @return int 影响的行数
     */
    int insertSelective(WeChatVideoVoteLogBean record);

    /**
     * <p>
     * 通过主键查询视频点赞记录
     * </p>
     *
     * @action tanjunkai 2017年3月15日 下午6:01:33 描述
     *
     * @param code
     *            主键
     * @return WeChatVideoVoteLogBean 视频点赞记录实体
     */
    WeChatVideoVoteLogBean selectByPrimaryKey(Integer code);

    /**
     * <p>
     * 更新视频点赞记录
     * </p>
     *
     * @action tanjunkai 2017年3月15日 下午6:02:22 描述
     *
     * @param record
     *            视频点赞记录实体
     * @return int 影响的行数
     */
    int updateByPrimaryKeySelective(WeChatVideoVoteLogBean record);

    /**
     * <p>
     * 更新视频点赞记录
     * </p>
     *
     * @action tanjunkai 2017年3月15日 下午6:02:22 描述
     *
     * @param record
     *            视频点赞记录实体
     * @return int 影响的行数
     */
    int updateByPrimaryKey(WeChatVideoVoteLogBean record);

    /**
     * <p>
     * 获取某用户某视频当天的点赞记录
     * </p>
     *
     * @action tanjunkai 2017年3月15日 下午5:58:43 描述
     *
     * @param userId
     *            点赞用户ID
     * @param videoId
     *            点赞视频ID
     * @return WeChatVideoVoteLogBean
     */
    WeChatVideoVoteLogBean selectTodayVoteLogByVideoId(
            @Param("userId") Integer userId, @Param("videoId") Integer videoId);
}