/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action-wdblessingcard2017
 * @作者：laixiancai
 * @联系方式：laixiancai@gyyx.cn
 * @创建时间：2017年3月10日 下午12:06:38
 * @版本号：0.0.1
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.bll.wdblessingcard2017.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.wdblessingcard2017.BlessingCardBean;
import cn.gyyx.action.beans.wdblessingcard2017.RoleBindBean;
import cn.gyyx.action.bll.wdblessingcard2017.BlessingCardBeanBll;
import cn.gyyx.action.dao.wdblessingcard2017.BlessingCardBeanDao;

/**
 * <p>
 * 祝福卡Bll
 * </p>
 * 
 * @author laixiancai
 * @since 0.0.1
 */
public class BlessingCardBeanBllImpl implements BlessingCardBeanBll {
    private BlessingCardBeanDao blessingCardBeanDao = new BlessingCardBeanDao();

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
        return blessingCardBeanDao.getSystemLotteryTimes();
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
        return blessingCardBeanDao.getUsersLotteryTimes();
    }

    public BlessingCardBean getBlessingCardByCode(Integer code) {
        return blessingCardBeanDao.getBlessingCardByCode(code);
    }

    /**
     * 
     * <p>
     * 根据编号获取祝福卡
     * </p>
     *
     * @action laixiancai 2017年3月13日 下午12:20:15 新增
     *
     * @param code
     * @param sqlSession
     * @return BlessingCardBean
     */
    public BlessingCardBean getBlessingCardByCodeOa(Integer code,
            SqlSession sqlSession) {
        return blessingCardBeanDao.getBlessingCardByCodeOa(code, sqlSession);
    }

    /**
     * 
     * <p>
     * 审核祝福卡
     * </p>
     *
     * @action laixiancai 2017年3月11日 下午1:17:55 新增
     *
     * @param code
     * @param verifyStatus
     * @param verifyAdmin
     * @param verifyTime
     * @return int
     */
    public int verifyBlessingCard(int code, int verifyStatus,
            String verifyAdmin, String verifyTime, SqlSession sqlSession) {
        return blessingCardBeanDao.verifyBlessingCard(code, verifyStatus,
            verifyAdmin, verifyTime, sqlSession);
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
        return blessingCardBeanDao.getBlessingCardList(beginTime, endTime,
            account, verifyStatus, pageSize, pageNo);
    }

    /**
     * 
     * <p>
     * OA后台获取祝福卡数量
     * </p>
     *
     * @action laixiancai 2017年3月10日 下午1:44:46 新增
     *
     * @param beginTime
     * @param endTime
     * @param account
     * @param verifyStatus
     * @return int
     */
    public int getBlessingCardCount(String beginTime, String endTime,
            String account, String verifyStatus) {
        return blessingCardBeanDao.getBlessingCardCount(beginTime, endTime,
            account, verifyStatus);
    }

    @Override
    public int getVerifyedBlessingCardCount(String title, int verifyStatus,
            Integer registerYearFrom, Integer registarYearEnd, String roleName,
            String orderBy, Integer registerYear) {
        return blessingCardBeanDao.getVerifyedBlessingCardCount(title,
            verifyStatus, registerYearFrom, registarYearEnd, roleName, orderBy,
            registerYear);
    }

    @Override
    public List<BlessingCardBean> getVerifyedBlessingCardPaging(String title,
            int verifyStatus, int page, int size, Integer registerYearFrom,
            Integer registarYearEnd, String roleName, String orderBy,
            Integer registerYear) {
        return blessingCardBeanDao.getVerifyedBlessingCardPaging(title,
            verifyStatus, page, size, registerYearFrom, registarYearEnd,
            roleName, orderBy, registerYear);
    }

    @Override
    public BlessingCardBean getBlessingCardByAccount(String account) {
        return blessingCardBeanDao.getBlessingCardByAccount(account);
    }

    /**
     * <p>
     * 按用户账号查询祝福卡
     * </p>
     *
     * @action laixiancai 2017年3月11日 上午11:52:15 新增
     *
     * @param account
     * @param sqlSession
     * @return BlessingCardBean
     */
    public BlessingCardBean getBlessingCardByAccount(String account,
            SqlSession sqlSession) {
        return blessingCardBeanDao
                .getBlessingCardByAccount(account, sqlSession);
    }

    @Override
    public int insert(BlessingCardBean blessingCard) {
        return blessingCardBeanDao.insert(blessingCard);
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
     * @param ip
     */
    public void upvoteBlessing(RoleBindBean roleBind, Integer code, String ip,
            SqlSession sqlSession) {
        blessingCardBeanDao.upvoteBlessing(roleBind, code, ip, sqlSession);
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
        blessingCardBeanDao.wxVote(openId, code, ip, sqlSession);
    }

    @Override
    public void updateBlessingCard(BlessingCardBean blessingCard) {
        blessingCardBeanDao.updateBlessingCard(blessingCard);
    }

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
        return blessingCardBeanDao.getVoteCountByAccount(account, sqlSession);
    }

}
