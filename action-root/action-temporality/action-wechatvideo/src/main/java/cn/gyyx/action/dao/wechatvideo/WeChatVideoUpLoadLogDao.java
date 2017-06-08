/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 *
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：
 * @作者：tanjunkai
 * @联系方式：tanjunkai@gyyx.cn
 * @创建时间：2017/3/14 11:26
 */
package cn.gyyx.action.dao.wechatvideo;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.wechatvideo.Constants;
import cn.gyyx.action.beans.wechatvideo.WeChatVideoRoleBindBean;
import cn.gyyx.action.beans.wechatvideo.WeChatVideoUpLoadLogBean;
import cn.gyyx.action.dao.MyBatisBaseDAO;

/**
 * <p>
 * 视频上传数据访问层
 * </p>
 * 
 * @author tanjunkai
 * @since 0.0.1
 */
public class WeChatVideoUpLoadLogDao extends MyBatisBaseDAO {

    /**
     * <p>
     * 搜索审核通过的视频
     * </p>
     *
     * @action tanjunkai
     *
     * @param searchPar
     * @return List<WeChatVideoUpLoadLogBean>
     */
    public List<WeChatVideoUpLoadLogBean> videoSearch(String searchPar) {
        try (SqlSession session = getSession(true)) {
            WeChatVideoUpLoadLogBeanMapper mapper = session
                    .getMapper(WeChatVideoUpLoadLogBeanMapper.class);
            return mapper.selectByParam(searchPar);
        }
    }

    /**
     * <p>
     * 首页根据页码获取视频列表
     * </p>
     *
     * @action tanjunkai 2017年3月15日 下午4:43:40 描述
     *
     * @param index
     *            当前页码
     * @param pageCount
     *            每页显示条数
     * @return List<WeChatVideoUpLoadLogBean>
     */
    public List<WeChatVideoUpLoadLogBean> getVideoByIndex(int index,
            int pageCount) {
        int skipCount = (index - 1) * pageCount;
        try (SqlSession session = getSession(true)) {
            WeChatVideoUpLoadLogBeanMapper mapper = session
                    .getMapper(WeChatVideoUpLoadLogBeanMapper.class);
            return mapper.getVideoByIndex(skipCount, pageCount);
        }
    }

    /**
     * <p>
     * OA后台审核获取所有上传视频列表
     * </p>
     *
     * @action tanjunkai 2017年3月16日 下午3:15:53 描述
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
    public List<WeChatVideoUpLoadLogBean> getVideoList(String beginTime,
            String endTime, String keyWord, String verifyStatus, int pageSize,
            int index) {
        int skipCount = (index - 1) * pageSize;
        try (SqlSession session = getSession(true)) {
            WeChatVideoUpLoadLogBeanMapper mapper = session
                    .getMapper(WeChatVideoUpLoadLogBeanMapper.class);
            return mapper.getVideoList(beginTime, endTime, keyWord,
                verifyStatus, skipCount, pageSize);
        }
    }

    /**
     * <p>
     * 获取上传视频总数
     * </p>
     *
     * @action tanjunkai 2017年3月16日 下午6:08:04 描述
     *
     * @return int
     */
    public int getVideoCount(String beginTime, String endTime, String keyWord,
            String verifyStatus) {
        try (SqlSession session = getSession(true)) {
            WeChatVideoUpLoadLogBeanMapper mapper = session
                    .getMapper(WeChatVideoUpLoadLogBeanMapper.class);
            return mapper.getVideoCount(beginTime, endTime, keyWord,
                verifyStatus);
        }
    }

    /**
     * <p>
     * 根据视频状态获取上传过滤用户后视频总数
     * </p>
     *
     * @action tanjunkai 2017年3月25日 下午14:56:37 描述
     *
     * @return int
     */
    public int getDistinctUserIdCount(String verifyStatus) {
        try (SqlSession session = getSession(true)) {
            WeChatVideoUpLoadLogBeanMapper mapper = session
                    .getMapper(WeChatVideoUpLoadLogBeanMapper.class);
            return mapper.getDistinctUserIdCount(verifyStatus);
        }
    }

    /**
     * <p>
     * 通过主键获取视频信息
     * </p>
     *
     * @action tanjunkai
     *
     * @param code
     * @return WeChatVideoUpLoadLogBean
     */
    public WeChatVideoUpLoadLogBean selectByPrimaryKey(Integer code) {
        try (SqlSession session = getSession(true)) {
            WeChatVideoUpLoadLogBeanMapper mapper = session
                    .getMapper(WeChatVideoUpLoadLogBeanMapper.class);
            return mapper.selectByPrimaryKey(code);
        }
    }

    /**
     * <p>
     * 根据userId和视频状态获取上传视频列表
     * </p>
     *
     * @action tanjunkai 2017年3月17日 下午2:31:26 描述
     *
     * @param userId
     * @param status
     * @return List<WeChatVideoUpLoadLogBean>
     */
    public List<WeChatVideoUpLoadLogBean> getUploadTimesByUserId(Integer userId,
            Integer status) {
        try (SqlSession session = getSession(true)) {
            WeChatVideoUpLoadLogBeanMapper mapper = session
                    .getMapper(WeChatVideoUpLoadLogBeanMapper.class);
            return mapper.getUploadTimesByUserId(userId, status);
        }
    }

    /**
     * <p>
     * 新增上传视频
     * </p>
     *
     * @action tanjunkai 2017年3月15日 下午2:46:56 描述
     *
     * @param record
     *            视频上传实体
     * @return int
     */
    public int insertSelective(WeChatVideoUpLoadLogBean record) {
        SqlSession session = getSession(false);
        int result = 0;
        try {
            WeChatVideoUpLoadLogBeanMapper mapper = session
                    .getMapper(WeChatVideoUpLoadLogBeanMapper.class);
            result = mapper.insertSelective(record);
            session.commit();
        } catch (Exception e) {
            logger.warn(e.toString());
            session.rollback();
            throw e;
        } finally {
            if (session != null)
                session.close();
        }
        return result;
    }

    /**
     * <p>
     * 更新视频信息
     * </p>
     *
     * @action tanjunkai
     *
     * @param record
     * @return int
     */
    public int updateByPrimaryKeySelective(WeChatVideoUpLoadLogBean record) {
        SqlSession session = getSession(false);
        int result = 0;
        try {
            WeChatVideoUpLoadLogBeanMapper mapper = session
                    .getMapper(WeChatVideoUpLoadLogBeanMapper.class);
            result = mapper.updateByPrimaryKeySelective(record);
            // 审核通过增加抽奖次数和总次数
            if (result > 0 && record
                    .getVideoStatus() == Constants.WECHATVIDEO_VERIFYED) {
                WeChatVideoRoleBindBeanMapper roleBindMapper = session
                        .getMapper(WeChatVideoRoleBindBeanMapper.class);
                // 给用户的剩余抽奖机会和总的抽奖机会都加1
                if (roleBindMapper.addLotteryTimes(record.getUserId()) <= 0)
                    session.rollback();
            }
            session.commit();

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
