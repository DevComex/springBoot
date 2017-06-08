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
package cn.gyyx.action.service.wdblessingcard2017;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.PageBean;
import cn.gyyx.action.beans.predicable.ResultBeanWithPage;
import cn.gyyx.action.beans.wdblessingcard2017.BlessingCardBean;
import cn.gyyx.action.beans.wdblessingcard2017.RoleBindBean;
import cn.gyyx.action.ui.wdblessingcard2017.QueryBean;
import cn.gyyx.action.beans.ResultBean;

/**
 * <p>
 * 祝福卡Service接口
 * </p>
 * 
 * @author laixiancai
 * @since 0.0.1
 */
public interface BlessingCardBeanService {

    BlessingCardBean getBlessingCardByCode(Integer code);

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
     * 往游戏内发虚拟物品
     * </p>
     *
     * @action laixiancai 2017年3月16日 上午11:22:11 新增
     *
     * @param account
     * @param giftPackage
     * @param serverCode
     * @return boolean
     */
    boolean reciveGift(String account, String giftPackage, int serverCode);

    /**
     * 
     * <p>
     * 获得玩家当前可用的抽奖次数
     * </p>
     *
     * @action laixiancai 2017年3月16日 下午3:10:50 新增
     *
     * @param roleBindBean
     * @param sqlSession
     * @return int
     */
    int getUserAvailableLotteryTimes(RoleBindBean roleBindBean,
            SqlSession sqlSession);

    /**
     * 
     * <p>
     * 玩家通过玩拼图游戏获取抽奖次数
     * </p>
     *
     * @action laixiancai 2017年3月15日 下午10:35:36 新增
     *
     * @param account
     * @return ResultBean<String>
     */
    ResultBean<String> playPuzzleGameToGetLotteryTime(String account);

    /**
     * 
     * <p>
     * 玩家领取游戏称号
     * </p>
     *
     * @action laixiancai 2017年3月16日 上午1:36:56 新增
     *
     * @param account
     * @return ResultBean<String>
     */
    ResultBean<String> getGameTitle(String account);

    /**
     * <p>
     * 获取活动当前的状态
     * </p>
     *
     * @action bozhencheng add 2017年3月4日 下午6:16:34 描述
     *
     * @param activityId
     * @return ResultBean<Object>
     */
    ResultBean<String> getActivityStatus(int activityId);

    /**
     * 
     * <p>
     * 根据注册年份得到相应的抽奖次数
     * </p>
     *
     * @action laixiancai 2017年3月15日 下午8:45:58 新增
     *
     * @param userRegisterYear
     * @return int
     */
    int getLotteryTimesByRegisterYear(int userRegisterYear);

    /**
     * 
     * <p>
     * 批量审核祝福卡
     * </p>
     *
     * @action laixiancai 2017年3月12日 下午9:44:07 新增
     *
     * @param codesStr
     * @param verifyStatus
     * @param verifyAdmin
     * @return ResultBean<String>
     */
    ResultBean<String> batchVerifyBlessingCard(String codesStr,
            int verifyStatus, String verifyAdmin);

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
     * @return ResultBeanWithPage<List<BlessingCardBean>>
     */
    ResultBeanWithPage<List<BlessingCardBean>> getBlessingCardList(
            String beginTime, String endTime, String account,
            String checkStatus, int pageSize, int pageNo);

    /**
     * 前台展示祝福卡列表
     * <p>
     * 方法说明
     * </p>
     *
     * @action niushuai 2017年3月10日 下午4:43:45 描述
     *
     * @param queryBean
     *            void
     */
    PageBean<BlessingCardBean> getVerifyedBlessingCardPaging(QueryBean queryBean);

    /**
     * <p>
     * 通过用户账号查询祝福卡
     * </p>
     *
     * @action niushuai 2017年3月11日 上午11:51:24 描述
     *
     * @param account
     * @return BlessingCardBean
     */
    BlessingCardBean getBlessingCardByAccount(String account);

    /**
     * <p>
     * 通过用户账号查询祝福卡
     * </p>
     *
     * @action laixiancai 2017年3月11日 上午11:51:24 新增
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
     * @action niushuai 2017年3月11日 下午12:55:00 描述
     *
     * @param blessingCard
     *            void
     */
    int insert(BlessingCardBean blessingCard);

    /**
     * <p>
     * 用户给祝福卡点赞
     * </p>
     *
     * @action niushuai 2017年3月12日 下午8:21:32 描述
     *
     * @param account
     * @param code
     * @param ip
     *            void
     */
    ResultBean<String> upvoteBlessing(String account, Integer code, String ip);

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
     *            ResultBean<String>
     */
    ResultBean<String> wxVote(String openId, Integer code, String ip);

    /**
     * <p>
     * 用户更新祝福卡
     * </p>
     *
     * @action niushuai 2017年3月13日 下午3:25:36 描述
     *
     * @param blessingCard
     *            void
     */
    void updateBlessingCard(BlessingCardBean blessingCard);

}
