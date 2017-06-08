/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action-wdblessingcard2017
 * @作者：niushuai
 * @联系方式：niushuai@gyyx.cn
 * @创建时间：2017年3月9日 上午10:50:04
 * @版本号：0.0.1
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.wdblessingcard2017;

/**
 * <p>
 * 活动中用到的一些常量
 * </p>
 * 
 * @author niushuai
 * @since 0.0.1
 */
public class Constants {

    public static final int HD_CODE = 443;
    public static final String HD_NAME = "问道11周年祝福卡活动";
    public static final String HD_NAME_English = "wdblessingcard2017";
    public static final String HD_TYPE = "lottery";
    public static final int INITIAL_UPVOTE_TIMES = 100; // 初始点赞次数

    // 奖品类型
    public static final String PRIZE_TYPE_REALPRIZE = "realPrize"; //实物奖品
    public static final String PRIZE_TYPE_NOREALPRIZE = "noRealPrize"; //非实物奖品
    
    // 祝福卡审核状态 0:未审核 1:通过审核 -1:未通过
    public static final int BLESSINGCARD_VERIFYED = 1;// 通过审核
    public static final int BLESSINGCARD_REFUSED = -1;// 通过审核
    public static final int BLESSINGCARD_VERIFYPENDING = 0;// 未审核

    // 分布式锁的前缀
    // 用户抽奖时分布式锁前缀
    public static final String LOTTERY_PRIFIX = HD_CODE + "_lottery";

    // 游戏主键
    public static final int GAMEID = 2;
    // 友好提示
    public static final String FRIENDLY_PROMPT = "参与活动的玩家太多啦，服务器暂时忙不过来，请稍后再试~";

    // 指定的奖品名称
    public static final String SPECIFIED_PRIZE_NAME = "花朵铭牌";
    public static final String SPECIFIED_PRIZE_NAME_WULEILING = "五雷令";

    // 错误日志的前缀
    public static final String ERROR_LOG = HD_NAME + "_" + HD_CODE
            + "_ERROR_LOG:";

    public static final int VOTEPC = 0;// 来自PC的点赞
    public static final int VOTEWEIXIN = 1;// 来自微信的点赞
}
