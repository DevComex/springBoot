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

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.wechatvideo.WeChatVideoUpLoadLogBean;
import cn.gyyx.action.beans.wechatvideo.WeChatVideoVoteLogBean;
import cn.gyyx.action.dao.MyBatisBaseDAO;

/**
 * <p>
 * 视频投票数据访问层
 * </p>
 * 
 * @author tanjunkai
 * @since 0.0.1
 */
public class WeChatVideoVoteLogDao extends MyBatisBaseDAO {

    /**
     * <p>
     * 获取某用户某视频当天的点赞记录
     * </p>
     *
     * @action tanjunkai
     *
     * @param userId
     *            点赞用户ID
     * @param videoId
     *            点赞视频ID
     * @return WeChatVideoVoteLogBean
     */
    public WeChatVideoVoteLogBean selectTodayVoteLog(Integer userId,
            Integer videoId) {
        try (SqlSession session = getSession(true)) {
            WeChatVideoVoteLogBeanMapper mapper = session
                    .getMapper(WeChatVideoVoteLogBeanMapper.class);
            return mapper.selectTodayVoteLogByVideoId(userId, videoId);
        }
    }

    /**
     * <p>
     * 插入点赞日志,同时更新视频点赞总次数
     * </p>
     *
     * @action tanjunkai
     *
     * @param record
     * @return int
     */
    public int insertSelective(WeChatVideoVoteLogBean record) {
        SqlSession session = getSession(false);
        int result = 0;
        try {
            WeChatVideoVoteLogBeanMapper mapper = session
                    .getMapper(WeChatVideoVoteLogBeanMapper.class);
            result = mapper.insertSelective(record);
            if (result > 0) {
                WeChatVideoUpLoadLogBeanMapper uploadLogMapper = session
                        .getMapper(WeChatVideoUpLoadLogBeanMapper.class);
                result = uploadLogMapper.addVoteTimes(record.getVideoId());
                session.commit();
            }
        } catch (Exception e) {
            logger.warn(e.toString());
            session.rollback();
        } finally {
            if (session != null)
                session.close();
        }
        return result;
    }
}
