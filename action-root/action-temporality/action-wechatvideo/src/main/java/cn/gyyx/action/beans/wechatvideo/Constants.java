package cn.gyyx.action.beans.wechatvideo;

/**
 * <p>
 * 问道周年视频祝福活动常量
 * </p>
 * 
 * @author tanjunkai
 * @since 0.0.1
 */
public class Constants {
    public static final int ACTIVITY_ID = 446;
    public static final String ACTIVITY_NAME = "问道周年视频祝福活动";
    public static final String ACTIVITY_TYPE = "lottery";
    // 上传视频状态 0:未审核 1:通过审核 -1:未通过
    public static final int WECHATVIDEO_VERIFYED = 1;// 审核通过
    public static final int WECHATVIDEO_REFUSED = -1;// 审核拒绝
    public static final int WECHATVIDEO_VERIFYPENDING = 0;// 未审核

    // 用户抽奖时分布式锁前缀
    public static final String LOTTERY_PRIFIX = ACTIVITY_ID + "_lottery";
    
    // 用户点赞分布式锁前缀
    public static final String VOTE_PRIFIX=ACTIVITY_ID + "_vote";
    
    // 指定的奖品名称
    public static final String PRIZE_FLOWERS_NAME = "花朵铭牌";
    
    public static final String PRIZE_THANKS_NAME="谢谢参与";
    
    public static final String CDN_ADDRESS="cdnAddress";
    
    public static final String PRIZE_2000SILVER="2000银元宝";
    
    // 错误日志的前缀
    public static final String ERROR_LOG = ACTIVITY_NAME + "_" + ACTIVITY_ID+ "_ERROR_LOG:"; 
    // 定义100为所有状态
    public static final int WECHATVIDEO_ALLSTATUS = 100;
    // 活动归属的游戏编号
    public static final int GAMEID = 2;
}
