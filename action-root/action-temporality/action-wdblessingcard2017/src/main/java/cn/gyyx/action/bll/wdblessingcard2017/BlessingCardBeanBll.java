/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action-wdblessingcard2017
 * @作者：laixiancai
 * @联系方式：laixiancai@gyyx.cn
 * @创建时间：2017年3月10日 下午12:10:04
 * @版本号：0.0.1
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.bll.wdblessingcard2017;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.wdblessingcard2017.BlessingCardBean;
import cn.gyyx.action.beans.wdblessingcard2017.RoleBindBean;

/**
 * <p>
 * 祝福卡Bll接口
 * </p>
 * 
 * @author laixiancai
 * @since 0.0.1
 */
public interface BlessingCardBeanBll {

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
    Integer getSystemLotteryTimes();

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
    Integer getUsersLotteryTimes();

    /**
     * <p>
     * 用编号获取祝福卡
     * </p>
     *
     * @action niushuai 2017年3月11日 下午6:09:31 描述
     *
     * @param code
     * @return BlessingCardBean
     */
    BlessingCardBean getBlessingCardByCode(Integer code);

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
    BlessingCardBean getBlessingCardByCodeOa(Integer code, SqlSession sqlSession);

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
    int verifyBlessingCard(int code, int verifyStatus, String verifyAdmin,
            String verifyTime, SqlSession sqlSession);

    /**
     * 
     * <p>
     * OA后台获取祝福卡列表
     * </p>
     *
     * @action laixiancai 2017年3月10日 上午11:59:12 新增
     *
     * @param beginTime
     * @param endTime
     * @param account
     * @param checkStatus
     * @param pageSize
     * @param pageNo
     * @return List<BlessingCardBean>
     */
    List<BlessingCardBean> getBlessingCardList(String beginTime,
            String endTime, String account, String checkStatus, int pageSize,
            int pageNo);

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
    int getBlessingCardCount(String beginTime, String endTime, String account,
            String verifyStatus);

    /**
     * <p>
     * 统计祝福卡数量
     * </p>
     *
     * @action niushuai 2017年3月10日 下午4:55:01 描述
     *
     * @param registerYear
     * @param verifyStatus
     * @return int
     */
    int getVerifyedBlessingCardCount(String title, int verifyStatus,
            Integer registerYearFrom, Integer registerYearEnd, String roleName,
            String createBy, Integer registerYear);

    /**
     * <p>
     * 分页查询祝福卡
     * </p>
     *
     * @action niushuai 2017年3月10日 下午5:24:12 描述
     *
     * @param registerYear
     * @param verifyStatus
     *            void
     */
    List<BlessingCardBean> getVerifyedBlessingCardPaging(String title,
            int verifyStatus, int page, int size, Integer registerYearFrom,
            Integer registarYearEnd, String roleName, String createBy,
            Integer registerYear);

    /**
     * <p>
     * 按用户账号查询祝福卡
     * </p>
     *
     * @action niushuai 2017年3月11日 上午11:52:15 描述
     *
     * @param account
     * @return BlessingCardBean
     */
    BlessingCardBean getBlessingCardByAccount(String account);

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
    int getVoteCountByAccount(String account, SqlSession sqlSession);

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
    BlessingCardBean getBlessingCardByAccount(String account,
            SqlSession sqlSession);

    /**
     * <p>
     * 添加祝福卡
     * </p>
     *
     * @action niushuai 2017年3月11日 下午1:00:13 描述
     *
     * @param blessingCard
     * @return int
     */
    int insert(BlessingCardBean blessingCard);

    /**
     * <p>
     * 给祝福卡点赞
     * </p>
     *
     * @action niushuai 2017年3月12日 下午8:23:17 描述
     *
     * @param roleBind
     *            点赞的用户绑定信息
     * @param code
     *            祝福卡编号
     * @param ip
     */
    void upvoteBlessing(RoleBindBean roleBind, Integer code, String ip,
            SqlSession sqlSession);

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
    void wxVote(String openId, Integer code, String ip, SqlSession sqlSession);

    /**
     * <p>
     * 用户更新祝福卡
     * </p>
     *
     * @action niushuai 2017年3月13日 下午3:26:12 描述
     *
     * @param blessingCard
     *            void
     */
    void updateBlessingCard(BlessingCardBean blessingCard);

}
