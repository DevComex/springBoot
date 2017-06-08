/**
  * -------------------------------------------------------------------------
  * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
  * @版权所有：北京光宇在线科技有限责任公司
  * @项目名称：action-wd11thyearscommentwall-oa
  * @作者：guoyonggang
  * @联系方式：guoyonggang@gyyx.cn
  * @创建时间：2017年3月13日 下午4:15:45
  * @版本号：0.0.1
  *-------------------------------------------------------------------------
  */
package cn.gyyx.action.oa.wd11thyearscommentwall.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Throwables;

import cn.gyyx.action.dao.MyBatisBaseDAO;
import cn.gyyx.action.oa.wd11thyearscommentwall.beans.WdElevenCommentWallBean;

/**
 * <p>
 * 问道11周年留言墙DAO操作
 * </p>
 * 
 * @author guoyonggang
 * @since 0.0.1
 */
public class WdElevenCommentWallDAO extends MyBatisBaseDAO {
    private static final Logger logger = LoggerFactory
            .getLogger(WdElevenCommentWallDAO.class);

    /**
     * <p>
     * 更新问道留言墙审核信息
     * </p>
     *
     * @action guoyonggang 2017年3月13日 下午5:17:34 描述
     *
     * @param record
     * @return boolean
     */
    public boolean update(WdElevenCommentWallBean record) {
        SqlSession session = getSession(false);
        logger.info("WdElevenCommentWallDAO update begin:params:{}", record);
        try {
            IWdElevenCommentWallBeanMapper mapper = session
                    .getMapper(IWdElevenCommentWallBeanMapper.class);
            boolean isSuccess = mapper.updateByPrimaryKeySelective(record) > 0;
            if (!isSuccess) {
                session.rollback();
                return isSuccess;
            }
            session.commit();
            return isSuccess;
        } catch (Exception e) {
            logger.warn(
                "WdElevenCommentWallDAO update eleven years comment fail,reason:{}",
                Throwables.getStackTraceAsString(e));
            session.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    /**
     * <p>
     * 分页查询评论数据
     * </p>
     *
     * @action guoyonggang 2017年3月13日 下午6:14:27 描述
     *
     * @param pageIndex
     *            页码
     * @param pageSize
     *            页面尺寸
     * @param forwardPage
     *            当前页前面的总条数
     * @param strWhere
     *            查询条件
     * @return List<WdElevenCommentWallBean> 查询结果
     * @throws ParseException
     */
    public List<WdElevenCommentWallBean> queryElevenYearsCommentList(
            Integer pageIndex, Integer pageSize, Integer forwardPage,
            String auditStatus, String beginTime, String endTime,
            String keyWord) throws ParseException {
        // 拼接where语句
        logger.info("buildSqlWhere begin");
        String strWhere = buildSqlWhere(auditStatus, beginTime, endTime,
            keyWord);
        logger.info("buildSqlWhere end ; result:{}", strWhere);
        SqlSession session = getSession(true);
        logger.info(
            "WdElevenCommentWallDAO queryElevenYearsCommentList begin;params:pageIndex:{};pageSize:{};forwardPage:{};strWhere:{};",
            new Object[] { pageIndex, pageSize, forwardPage, strWhere });
        try {
            IWdElevenCommentWallBeanMapper mapper = session
                    .getMapper(IWdElevenCommentWallBeanMapper.class);
            return mapper.queryElevenYearsCommentList(pageIndex, pageSize,
                forwardPage, strWhere);
        } catch (Exception e) {
            logger.warn(
                "WdElevenCommentWallDAO queryElevenYearsCommentList fail,reason:{}",
                Throwables.getStackTraceAsString(e));
            return null;
        } finally {
            session.close();
        }
    }

    /**
     * 
     * <p>
     * 查询评论总数
     * </p>
     *
     * @action guoyonggang 2017年3月13日 下午4:37:12 描述
     *
     * @param strWhere
     *            查询条件
     * @return int 总条数
     * @throws ParseException
     */
    public int queryElevenYearsCommentCount(String auditStatus,
            String beginTime, String endTime, String keyWord)
            throws ParseException {
        // 拼接where语句
        logger.info("buildSqlWhere begin");
        String strWhere = buildSqlWhere(auditStatus, beginTime, endTime,
            keyWord);
        logger.info("buildSqlWhere end ; result:{}", strWhere);
        SqlSession session = getSession(true);
        logger.info(
            "WdElevenCommentWallDAO queryElevenYearsCommentCount begin;strWhere:{}",
            strWhere);
        try {
            IWdElevenCommentWallBeanMapper mapper = session
                    .getMapper(IWdElevenCommentWallBeanMapper.class);
            return mapper.queryElevenYearsCommentCount(strWhere);
        } catch (Exception e) {
            logger.warn(
                "WdElevenCommentWallDAO queryElevenYearsCommentCount fail,reason:{}",
                Throwables.getStackTraceAsString(e));
            return 0;
        } finally {
            session.close();
        }
    }

    /**
     * <p>
     * 获取评论墙统计数据
     * </p>
     *
     * @action guoyonggang 2017年3月13日 下午5:08:54 描述
     *
     * @param strWhere
     *            查询条件
     * @return List<Map<String,Object>> 查询结果
     * @throws ParseException
     */
    public List<Map<String, Object>> queryElevenYearsCommentStatistics(
            String auditStatus, String beginTime, String endTime,
            String keyWord) throws ParseException {
        // 拼接where语句
        logger.info("buildSqlWhere begin");
        String strWhere = buildSqlWhere(auditStatus, beginTime, endTime,
            keyWord);
        logger.info("buildSqlWhere end ; result:{}", strWhere);
        SqlSession session = getSession(true);
        logger.info(
            "WdElevenCommentWallDAO queryElevenYearsCommentStatistics begin;strWhere:{}",
            strWhere);
        try {
            IWdElevenCommentWallBeanMapper mapper = session
                    .getMapper(IWdElevenCommentWallBeanMapper.class);
            return mapper.queryElevenYearsCommentStatistics(strWhere);
        } catch (Exception e) {
            logger.warn(
                "WdElevenCommentWallDAO queryElevenYearsCommentStatistics fail,reason:{}",
                Throwables.getStackTraceAsString(e));
            return null;
        } finally {
            session.close();
        }
    }

    /**
     * <p>
     * 生成Where查询条件
     * </p>
     *
     * @action guoyonggang 2017年3月13日 下午8:19:25 描述
     *
     * @param auditStatus
     *            审核状态
     * @param beginTime
     *            开始时间
     * @param endTime
     *            结束时间
     * @param keyWord
     *            关键字
     * @return where查询语句
     * @throws ParseException
     *             String 转换异常
     */
    private String buildSqlWhere(String auditStatus, String beginTime,
            String endTime, String keyWord) throws ParseException {
        String strWhere = "1=1";
        if (!auditStatus.equals("All")) {
            strWhere = strWhere.concat(" and wall.is_audit=")
                    .concat(auditStatus);
        }
        if (!StringUtils.isEmpty(beginTime)) {
            strWhere = strWhere.concat(" and wall.create_time>='")
                    .concat(beginTime).concat("'");
        }
        SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
        if (!StringUtils.isEmpty(endTime)) {
            Date nextDay = addDate(formate.parse(endTime), 1);
            strWhere = strWhere.concat(" and wall.create_time<='")
                    .concat(formate.format(nextDay)).concat("'");
        }
        if (!StringUtils.isEmpty(keyWord)) {
            strWhere = strWhere.concat(" and (hd.hd_name like '%")
                    .concat(keyWord).concat("%'");
            strWhere = strWhere.concat(" or wall.nick_name like '%")
                    .concat(keyWord).concat("%'");
            strWhere = strWhere.concat(" or wall.message like '%")
                    .concat(keyWord).concat("%')");
        }
        return strWhere;
    }

    /**
     * <p>
     * 给指定日期添加指定天数
     * </p>
     *
     * @action guoyonggang 2017年3月14日 上午10:55:28 描述
     *
     * @param date
     *            日期
     * @param day
     *            天数
     * @return
     * @throws ParseException
     *             Date
     */
    private Date addDate(Date date, long day) throws ParseException {
        long time = date.getTime(); // 得到指定日期的毫秒数
        day = day * 24 * 60 * 60 * 1000; // 要加上的天数转换成毫秒数
        time += day; // 相加得到新的毫秒数
        return new Date(time); // 将毫秒数转换成日期
    }
}
