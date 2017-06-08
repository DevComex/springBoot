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

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.google.common.base.Throwables;

import cn.gyyx.action.beans.wd11thyearscommentwall.CommentWallBean;
import cn.gyyx.action.dao.MyBatisBaseDAO;

/**
 * <p>
 * CommentWallMapper数据操作实现类
 * </p>
 * 
 * @author caishuai
 * @since 0.0.1
 */
public class CommentWallMapperImpl extends MyBatisBaseDAO {
    /**
     * 
     * <p>
     * 插入comment留言
     * </p>
     *
     * @action caishuai 2017年3月9日 下午3:34:03 描述
     *
     * @param record留言实体
     * @return int
     */
    public boolean insertSelective(CommentWallBean record) {
        SqlSession session = getSession(false);
        try {
            CommentWallMapper mapper = session
                    .getMapper(CommentWallMapper.class);
            boolean isSuccess = mapper.insertSelective(record) > 0;
            if (!isSuccess) {
                session.rollback();
                return isSuccess;
            }
            session.commit();
            return isSuccess;
        } catch (Exception e) {
            logger.warn("InsertCommentError:{}",
                Throwables.getStackTraceAsString(e));
            session.rollback();
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    /**
     * 
     * <p>
     * 查询前x条审核过的数据by createTime desc
     * </p>
     *
     * @action caishuai 2017年3月9日 下午3:28:38 描述
     *
     * @param size数据大小
     * @return List<CommentBean>
     * 
     */
    public List<CommentWallBean> getTopComments(int size) {
        SqlSession session = getSession(true);
        try {
            session.getConnection().setReadOnly(true);
            CommentWallMapper mapper = session
                    .getMapper(CommentWallMapper.class);
            return mapper.selectTopComments(size);
        } catch (SQLException e) {
            logger.warn("set session readOnly error{}",
                Throwables.getStackTraceAsString(e));
            throw new RuntimeException(e);
        } catch (Exception e) {
            logger.warn("getTopKCommentsError:{}",
                Throwables.getStackTraceAsString(e));
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    /**
     * 
     * <p>
     * 查询前x条审核过的数据by createTime desc，create_time在endTime之前
     * </p>
     *
     * @action caishuai 2017年3月9日 下午5:24:35 描述
     * @action caishuai 2017年3月15日 11:42:24 
     *          添加actionCode字段,增加获取某个活动单独的留言功能
     *
     * @param start开始位置
     * @param end结束位置
     * @param endTime数据截止时间、
     * @param actionCode
     *            活动code，为null时默认获取全部
     * @return List<CommentBean>
     */
    public List<CommentWallBean> selectTopCommentsLimitTimeBefore(int start,
            int end, Date endTime,Integer actionCode) {
        SqlSession session = getSession(true);
        try {
            session.getConnection().setReadOnly(true);
            CommentWallMapper mapper = session
                    .getMapper(CommentWallMapper.class);
            return mapper.selectTopCommentsLimitTimeBefore(start, end, endTime,actionCode);
        } catch (SQLException e) {
            logger.warn("set session readOnly error{}",
                Throwables.getStackTraceAsString(e));
            throw new RuntimeException(e);
        } catch (Exception e) {
            logger.warn("getTopKCommentsLimitTimeBeforeError:{}",
                Throwables.getStackTraceAsString(e));
            throw new RuntimeException(e);
        } finally {
            session.close();
        }

    }

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
    public int selectCountLimitTimeBefore(Date endTime,Integer actionCode) {
        SqlSession session = getSession(true);
        try {
            session.getConnection().setReadOnly(true);
        } catch (SQLException e) {
            logger.warn("set session readOnly error{}",
                Throwables.getStackTraceAsString(e));
        }
        try {
            CommentWallMapper mapper = session
                    .getMapper(CommentWallMapper.class);
            return mapper.selectCountLimitTimeBefore(endTime,actionCode);
        } catch (Exception e) {
            logger.warn("getTopKCommentsLimitTimeBeforeError:{}",
                Throwables.getStackTraceAsString(e));
            return 0;
        } finally {
            session.close();
        }
    }
}
