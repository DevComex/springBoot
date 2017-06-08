/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action-wdblessingcard2017
 * @作者：laixiancai
 * @联系方式：laixiancai@gyyx.cn
 * @创建时间：2017年3月9日 下午10:08:15
 * @版本号：0.0.1
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.wdblessingcard2017;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.wdblessingcard2017.BlessingCardBean;
import cn.gyyx.action.beans.wdblessingcard2017.UpvoteLogBean;

/**
 * <p>
 * 祝福卡Mapper
 * </p>
 * 
 * @author laixiancai
 * @since 0.0.1
 */
public interface BlessingCardBeanMapper {

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
     * 
     * <p>
     * 根据账号获取当天的点赞数
     * </p>
     *
     * @action laixiancai 2017年3月16日 下午8:40:13 新增
     *
     * @param account
     * @return int
     */
    int getVoteCountByAccount(@Param("account") String account);

    /**
     * 
     * <p>
     * 插入点赞记录
     * </p>
     *
     * @action laixiancai 2017年3月16日 下午4:22:54 新增
     *
     * @param upvoteLogBean
     *            void
     */
    void insertUpvoteLog(UpvoteLogBean upvoteLogBean);

    /**
     * <p>
     * 添加祝福卡
     * </p>
     *
     * @action niushuai 2017年3月11日 下午5:48:59 描述
     *
     * @param record
     * @return int
     */
    int insertSelective(BlessingCardBean record);

    //
    /**
     * <p>
     * 使用编号获取祝福卡
     * </p>
     *
     * @action niushuai 2017年3月11日 下午5:49:07 描述
     *
     * @param code
     * @return BlessingCardBean
     */
    BlessingCardBean selectByPrimaryKey(Integer code);

    /**
     * 
     * <p>
     * OA后台根据祝福卡编号查询祝福卡
     * </p>
     *
     * @action laixiancai 2017年3月13日 下午1:53:00 新增
     *
     * @param code
     * @return BlessingCardBean
     */
    BlessingCardBean selectByPrimaryKeyOa(Integer code);

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
    int verifyBlessingCard(@Param("code") int code,
            @Param("verifyStatus") int verifyStatus,
            @Param("verifyAdmin") String verifyAdmin,
            @Param("verifyTime") String verifyTime);

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
     * @param verifyStatus
     * @param pageSize
     * @param pageNo
     * @return List<BlessingCardBean>
     */
    List<BlessingCardBean> getBlessingCardList(
            @Param("beginTime") String beginTime,
            @Param("endTime") String endTime, @Param("account") String account,
            @Param("verifyStatus") String verifyStatus,
            @Param("pageSize") int pageSize, @Param("pageNo") int pageNo);

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
    int getBlessingCardCount(@Param("beginTime") String beginTime,
            @Param("endTime") String endTime, @Param("account") String account,
            @Param("verifyStatus") String verifyStatus);

    /**
     * <p>
     * 统计已审核的祝福卡列表数量
     * </p>
     *
     * @action niushuai 2017年3月10日 下午4:55:47 描述
     *
     * @param registerYear
     * @param verifyStatus
     * @return int
     */
    int getVerifyedBlessingCardCount(@Param("title") String title,
            @Param("verifyStatus") int verifyStatus,
            @Param("registerYearFrom") Integer registerYearFrom,
            @Param("registerYearEnd") Integer registerYearEnd,
            @Param("roleName") String roleName,
            @Param("orderBy") String orderBy,
            @Param("registerYear") Integer registerYear);

    /**
     * 
     * <p>
     * 查询已审核的祝福卡列表
     * </p>
     *
     * @action niushuai 2017年3月10日 下午6:07:00 描述
     *
     * @param title
     * @param verifyStatus
     * @param page
     * @param size
     * @return List<BlessingCardBean>
     */
    List<BlessingCardBean> getVerifyedBlessingCardPaging(
            @Param("title") String title,
            @Param("verifyStatus") int verifyStatus, @Param("page") int page,
            @Param("size") int size,
            @Param("registerYearFrom") Integer registerYearFrom,
            @Param("registerYearEnd") Integer registerYearEnd,
            @Param("roleName") String roleName,
            @Param("orderBy") String orderBy,
            @Param("registerYear") Integer registerYear);

    /**
     * <p>
     * 通过用户账号查询祝福卡
     * </p>
     *
     * @action niushuai 2017年3月11日 上午11:56:52 描述
     *
     * @param account
     * @return BlessingCardBean
     */
    BlessingCardBean getBlessingCardByAccount(String account);

    /**
     * <p>
     * 更新祝福卡的赞数(专用)
     * </p>
     *
     * @action niushuai 2017年3月12日 下午9:05:27 描述
     *
     * @param blessingCard
     *            void
     */
    void updateUpvoteNum(BlessingCardBean blessingCard);

    /**
     * <p>
     * 用户更新祝福卡内容
     * </p>
     *
     * @action niushuai 2017年3月13日 下午3:32:47 描述
     *
     * @param blessingCard
     *            void
     */
    void updateBlessingCard(BlessingCardBean blessingCard);

}
