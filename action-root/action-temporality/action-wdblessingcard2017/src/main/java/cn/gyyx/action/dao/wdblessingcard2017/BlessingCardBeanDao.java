/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action-wdblessingcard2017
 * @作者：laixiancai
 * @联系方式：laixiancai@gyyx.cn
 * @创建时间：2017年3月10日 上午11:49:07
 * @版本号：0.0.1
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.wdblessingcard2017;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.wdblessingcard2017.BlessingCardBean;
import cn.gyyx.action.beans.wdblessingcard2017.Constants;
import cn.gyyx.action.beans.wdblessingcard2017.RoleBindBean;
import cn.gyyx.action.beans.wdblessingcard2017.UpvoteLogBean;
import cn.gyyx.action.dao.MyBatisBaseDAO;
import cn.gyyx.core.Text;

/**
 * <p>
 * 祝福卡Dao
 * </p>
 * 
 * @author laixiancai
 * @since 0.0.1
 */
public class BlessingCardBeanDao extends MyBatisBaseDAO {

    /**
     * 
     * <p>
     * 根据账号获取当天的点赞数
     * </p>
     *
     * @action laixiancai 2017年3月16日 下午8:40:13 新增
     *
     * @param account
     * @param sqlSession
     * @return int
     */
    public int getVoteCountByAccount(String account, SqlSession sqlSession) {
        BlessingCardBeanMapper mapper = sqlSession
                .getMapper(BlessingCardBeanMapper.class);
        return mapper.getVoteCountByAccount(account);
    }

    /**
     * 
     * <p>
     * OA后台-获取系统发出的总抽奖次数
     * </p>
     *
     * @action laixiancai 2017年3月17日 下午8:33:30 新增
     *
     * @return Integer
     */
    public Integer getSystemLotteryTimes() {
        try (SqlSession sqlSession = this.getSession(true)) {
            BlessingCardBeanMapper mapper = sqlSession
                    .getMapper(BlessingCardBeanMapper.class);
            return mapper.getSystemLotteryTimes();
        }
    }

    /**
     * 
     * <p>
     * OA后台-获取玩家的总抽奖次数
     * </p>
     *
     * @action laixiancai 2017年3月17日 下午8:33:30 新增
     *
     * @return Integer
     */
    public Integer getUsersLotteryTimes() {
        try (SqlSession sqlSession = this.getSession(true)) {
            BlessingCardBeanMapper mapper = sqlSession
                    .getMapper(BlessingCardBeanMapper.class);
            return mapper.getUsersLotteryTimes();
        }
    }

    /**
     * 使用编号获取祝福卡
     * <p>
     * 方法说明
     * </p>
     *
     * @action niushuai 2017年3月11日 下午5:48:15 描述
     *
     * @param code
     * @return BlessingCardBean
     */
    public BlessingCardBean getBlessingCardByCode(Integer code) {
        try (SqlSession sqlSession = this.getSession(true)) {
            BlessingCardBeanMapper mapper = sqlSession
                    .getMapper(BlessingCardBeanMapper.class);
            return mapper.selectByPrimaryKey(code);
        }
    }

    /**
     * 
     * <p>
     * 根据编号获取祝福卡
     * </p>
     *
     * @action laixiancai 2017年3月13日 下午12:19:18 新增
     *
     * @param code
     * @param sqlSession
     * @return BlessingCardBean
     */
    public BlessingCardBean getBlessingCardByCodeOa(Integer code,
            SqlSession sqlSession) {
        BlessingCardBeanMapper mapper = sqlSession
                .getMapper(BlessingCardBeanMapper.class);
        return mapper.selectByPrimaryKeyOa(code);
    }

    /**
     * 
     * <p>
     * 审核祝福卡
     * </p>
     *
     * @action laixiancai 2017年3月12日 下午5:37:15 新增
     *
     * @param code
     * @param verifyStatus
     * @param verifyAdmin
     * @param verifyTime
     * @param sqlSession
     * @return int
     */
    public int verifyBlessingCard(int code, int verifyStatus,
            String verifyAdmin, String verifyTime, SqlSession sqlSession) {
        BlessingCardBeanMapper blessingCardBeanMapper = sqlSession
                .getMapper(BlessingCardBeanMapper.class);
        int count = blessingCardBeanMapper.verifyBlessingCard(code,
            verifyStatus, verifyAdmin, verifyTime);
        return count;
    }

    /**
     * 
     * <p>
     * OA后台获取祝福卡列表
     * </p>
     *
     * @action laixiancai 2017年3月10日 上午11:59:12 新增方法
     *
     * @param beginTime
     * @param endTime
     * @param account
     * @param verifyStatus
     * @param pageSize
     * @param pageNo
     * @return List<BlessingCardBean>
     */
    public List<BlessingCardBean> getBlessingCardList(String beginTime,
            String endTime, String account, String verifyStatus, int pageSize,
            int pageNo) {
        try (SqlSession session = this.getSession(true)) {
            BlessingCardBeanMapper blessingCardBeanMapper = session
                    .getMapper(BlessingCardBeanMapper.class);
            return blessingCardBeanMapper.getBlessingCardList(beginTime,
                endTime, account, verifyStatus, pageSize, pageNo);
        }
    }

    /**
     * 
     * <p>
     * OA后台获取祝福卡数量
     * </p>
     *
     * @action laixiancai 2017年3月10日 下午1:46:39 新增
     *
     * @param beginTime
     * @param endTime
     * @param account
     * @param verifyStatus
     * @return int
     */
    public int getBlessingCardCount(String beginTime, String endTime,
            String account, String verifyStatus) {
        try (SqlSession session = this.getSession(true)) {
            BlessingCardBeanMapper blessingCardBeanMapper = session
                    .getMapper(BlessingCardBeanMapper.class);
            return blessingCardBeanMapper.getBlessingCardCount(beginTime,
                endTime, account, verifyStatus);
        }
    }

    /**
     * <p>
     * 统计祝福卡数量
     * </p>
     *
     * @action niushuai 2017年3月10日 下午4:55:16 描述
     *
     * @param registerYear
     * @param verifyStatus
     * @return int
     */
    public int getVerifyedBlessingCardCount(String title, int verifyStatus,
            Integer registerYearFrom, Integer registarYearEnd, String roleName,
            String orderBy, Integer registerYear) {
        try (SqlSession session = this.getSession(true)) {
            BlessingCardBeanMapper blessingCardBeanMapper = session
                    .getMapper(BlessingCardBeanMapper.class);
            return blessingCardBeanMapper.getVerifyedBlessingCardCount(title,
                verifyStatus, registerYearFrom, registarYearEnd, roleName,
                orderBy, registerYear);
        }
    }

    /**
     * <p>
     * 根据称号获取祝福卡
     * </p>
     *
     * @action niushuai 2017年3月11日 下午6:16:24 描述
     *
     * @param title
     * @param verifyStatus
     * @param page
     * @param size
     * @return List<BlessingCardBean>
     */
    public List<BlessingCardBean> getVerifyedBlessingCardPaging(String title,
            int verifyStatus, int page, int size, Integer registerYearFrom,
            Integer registerYearEnd, String roleName, String orderBy,
            Integer registerYear) {
        try (SqlSession session = this.getSession(true)) {
            BlessingCardBeanMapper blessingCardbeanMapper = session
                    .getMapper(BlessingCardBeanMapper.class);
            return blessingCardbeanMapper.getVerifyedBlessingCardPaging(title,
                verifyStatus, page, size, registerYearFrom, registerYearEnd,
                roleName, orderBy, registerYear);
        }
    }

    /**
     * <p>
     * 通过用户账号查询祝福卡
     * </p>
     *
     * @action niushuai 2017年3月11日 上午11:57:32 描述
     *
     * @param account
     * @return BlessingCardBean
     */
    public BlessingCardBean getBlessingCardByAccount(String account) {
        try (SqlSession session = this.getSession(true)) {
            BlessingCardBeanMapper blessingCardbeanMapper = session
                    .getMapper(BlessingCardBeanMapper.class);
            return blessingCardbeanMapper.getBlessingCardByAccount(account);
        }
    }

    /**
     * <p>
     * 通过用户账号查询祝福卡
     * </p>
     *
     * @action laixiancai 2017年3月11日 上午11:57:32 新增
     *
     * @param account
     * @param sqlSession
     * @return BlessingCardBean
     */
    public BlessingCardBean getBlessingCardByAccount(String account,
            SqlSession sqlSession) {
        BlessingCardBeanMapper blessingCardbeanMapper = sqlSession
                .getMapper(BlessingCardBeanMapper.class);
        return blessingCardbeanMapper.getBlessingCardByAccount(account);
    }

    /**
     * 
     * <p>
     * 添加祝福卡
     * </p>
     *
     * @action niushuai 2017年3月11日 下午1:02:37 描述
     *
     * @param blessingCard
     * @return int
     */
    public int insert(BlessingCardBean blessingCard) {
        int count = 0;
        try (SqlSession session = this.getSession()) {
            BlessingCardBeanMapper blessingCardbeanMapper = session
                    .getMapper(BlessingCardBeanMapper.class);
            count = blessingCardbeanMapper.insertSelective(blessingCard);
            session.commit();
        }
        return count;
    }

    /**
     * <p>
     * 给祝福卡点赞
     * </p>
     *
     * @action niushuai 2017年3月12日 下午8:23:17 描述
     *
     * @param account
     *            点赞的用户
     * @param code
     *            祝福卡编号
     */
    public void upvoteBlessing(RoleBindBean roleBind, Integer code, String ip,
            SqlSession sqlSession) {
        RoleBindBeanMapper roleBindBeanMapper = sqlSession
                .getMapper(RoleBindBeanMapper.class);

        // 用户的可点赞数减1
        roleBind.setUpvoteTimes(roleBind.getUpvoteTimes() - 1);
        roleBindBeanMapper.updateUpvoteTimes(roleBind);
        // 祝福卡的点赞数加1
        BlessingCardBeanMapper blessingCardbeanMapper = sqlSession
                .getMapper(BlessingCardBeanMapper.class);
        BlessingCardBean blessingCard = blessingCardbeanMapper
                .selectByPrimaryKey(code);
        blessingCard.setUpvoteNum(blessingCard.getUpvoteNum() + 1);
        blessingCardbeanMapper.updateUpvoteNum(blessingCard);
        // 插入点赞记录
        UpvoteLogBean upvoteLogBean = new UpvoteLogBean();
        upvoteLogBean.setAccount(roleBind.getAccount());
        upvoteLogBean.setBlessingCardCode(code.toString());
        upvoteLogBean.setVoteSource(Constants.VOTEPC);
        upvoteLogBean.setIp(ip);
        blessingCardbeanMapper.insertUpvoteLog(upvoteLogBean);
    }

    /**
     * 
     * <p>
     * 微信端点赞
     * </p>
     *
     * @action laixiancai 2017年3月16日 下午8:16:27 新增
     *
     * @param openId
     * @param code
     * @param ip
     *            void
     */
    public void wxVote(String openId, Integer code, String ip,
            SqlSession sqlSession) {
        // 祝福卡的点赞数加1
        BlessingCardBeanMapper blessingCardbeanMapper = sqlSession
                .getMapper(BlessingCardBeanMapper.class);
        BlessingCardBean blessingCard = blessingCardbeanMapper
                .selectByPrimaryKey(code);
        blessingCard.setUpvoteNum(blessingCard.getUpvoteNum() + 1);
        blessingCardbeanMapper.updateUpvoteNum(blessingCard);
        // 插入点赞记录
        UpvoteLogBean upvoteLogBean = new UpvoteLogBean();
        upvoteLogBean.setAccount(openId);
        upvoteLogBean.setBlessingCardCode(code.toString());
        upvoteLogBean.setVoteSource(Constants.VOTEWEIXIN);
        upvoteLogBean.setIp(ip);
        blessingCardbeanMapper.insertUpvoteLog(upvoteLogBean);
    }

    /**
     * <p>
     * 用户更新祝福卡
     * </p>
     *
     * @action niushuai 2017年3月13日 下午3:26:49 描述
     *
     * @param blessingCard
     *            void
     */
    public void updateBlessingCard(BlessingCardBean blessingCard) {
        try (SqlSession session = this.getSession()) {
            BlessingCardBeanMapper blessingCardBeanMapper = session
                    .getMapper(BlessingCardBeanMapper.class);
            blessingCardBeanMapper.updateBlessingCard(blessingCard);
            session.commit();
        }
    }

}
