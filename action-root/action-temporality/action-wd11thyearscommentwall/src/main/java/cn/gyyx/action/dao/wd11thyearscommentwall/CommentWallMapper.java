/**
  * -------------------------------------------------------------------------
  * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
  * @版权所有：北京光宇在线科技有限责任公司
  * @项目名称：action-wd11thyearscommentwall
  * @作者：caishuai
  * @联系方式：caishuai@gyyx.cn
  * @创建时间：2017年3月9日 下午3:11:39
  * @版本号：0.0.1
  *-------------------------------------------------------------------------
  */
package cn.gyyx.action.dao.wd11thyearscommentwall;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.wd11thyearscommentwall.CommentWallBean;

/**
 * <p>
 * CommentWallMapper留言数据操作接口
 * </p>
 * 
 * @author caishuai
 * @since 0.0.1
 */
public interface CommentWallMapper {
    /**
     * 
     * <p>
     * 插入一条新留言
     * </p>
     *
     * @action caishuai 2017年3月10日 下午2:01:09 描述
     *
     * @param record
     *            留言实体
     * @return int
     */
    int insertSelective(CommentWallBean record);

    /**
     * 
     * <p>
     * 查询前x条数据by createTime desc，
     * </p>
     *
     * @action caishuai 2017年3月9日 下午3:19:23 描述
     *
     * @param size
     *            请求数据大小
     * @return List<CommentWallBean>
     */
    List<CommentWallBean> selectTopComments(int size);

    /**
     * 
     * <p>
     * 分页查询数据by createTime desc，create_time在endTime之前
     * </p>
     *
     * @action caishuai 2017年3月9日 下午5:24:35 描述
     * @action caishuai 2017年3月15日 11:42:24 
     *          添加actionCode字段,增加获取某个活动单独的留言功能
     *
     * @param start开始位置
     * @param end结束位置
     * @param endTime数据截止时间
     * @param actionCode
     *            活动code，为null时默认获取全部
     * @return List<CommentWallBean>
     */
    List<CommentWallBean> selectTopCommentsLimitTimeBefore(
            @Param("start") int start, @Param("end") int end,
            @Param("endTime") Date endTime,
            @Param("actionCode") Integer actionCode);

    /**
     * 
     * <p>
     * 分页查询数据总条数
     * </p>
     *
     * @action caishuai 2017年3月10日 下午6:23:57 描述
     * @action caishuai 2017年3月15日 11:42:24 
     *          添加actionCode字段,增加获取某个活动单独的留言功能
     *
     * @param endTime数据截止时间
     * @param actionCode
     *            活动code，为null时默认获取全部
     * @return int
     */
    int selectCountLimitTimeBefore(@Param("endTime") Date endTime,
            @Param("actionCode") Integer actionCode);
}