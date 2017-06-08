/**
  * -------------------------------------------------------------------------
  * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
  * @版权所有：北京光宇在线科技有限责任公司
  * @项目名称：action-wdrankinglist2017
  * @作者：niushuai
  * @联系方式：niushuai@gyyx.cn
  * @创建时间：2017年4月5日 下午2:32:58
  * @版本号：0.0.1
  *-------------------------------------------------------------------------
  */
package cn.gyyx.action.beans.wdrankinglist2017;

/**
 * <p>
 * 2017问道微信新服冲榜活动(wdrankinglist2017)常量
 * </p>
 * 
 * @author niushuai
 * @since 0.0.1
 */
public class Constants {

    public static final int HD_CODE = 461;
    public static final String HD_NAME = "2017新服冲榜活动";
    public static final String HD_NAME_English = "wdrankinglist2017";
    public static final String HD_TYPE = "lottery";

    // 错误日志的前缀
    public static final String ERROR_LOG = HD_CODE + "_" + HD_NAME
            + "_ERROR_LOG:";
    // 分布式锁的前缀
    // 用户抽奖时分布式锁前缀
    public static final String LOTTERY_PRIFIX = HD_CODE + "_lottery";
    // 游戏主键
    public static final int GAMEID = 2;

    /**
     * 宣言审核状态-待审核
     */
    public static final int DECLARATION_STATUS_PENDING = 0;
    /**
     * 宣言审核状态-审核通过
     */
    public static final int DECLARATION_STATUS_PASS = 1;
    /**
     * 宣言审核状态-审核拒绝
     */
    public static final int DECLARATION_STATUS_REFUSED = -1;
    public static final int DECLARATION_STATUS_UNWRITE = 5; // 没填写，用于前台

    // 缓存key的前缀
    public static final String CACHE_ROLEBIND_PREFIX = "cache_" + HD_CODE
            + "_rolebind_";
    public static final String CACHE_DECLARATION_PREFIX = "cache_" + HD_CODE
            + "_declaration_";
    public static final String CACHE_PRIZES_SHOW = "cache_" + HD_CODE + "prizes_show";
    
    // 常用提示
    public static final String MSG_NOT_YET_ROLEBIND = "您还没有完成报名！";

    // 指定的奖品名称
    public static final String SPECIFIED_PRIZE_NAME_THANKS = "谢谢参与";
    public static final String SPECIFIED_PRIZE_NAME_TONGQIANMINGPAI = "铜钱铭牌";
    public static final String SPECIFIED_PRIZE_NAME_CHONGBANGMA= "新服大型冲榜比赛(170331)";

}
