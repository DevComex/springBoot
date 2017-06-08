/**
  * -------------------------------------------------------------------------
  * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
  * @版权所有：北京光宇在线科技有限责任公司
  * @项目名称：action-wd11yearrechargerebate
  * @作者：chenglong
  * @联系方式：chenglong@gyyx.cn
  * @创建时间：2017年3月29日 上午10:59:56
  * @版本号：0.0.1
  *-------------------------------------------------------------------------
  */
package cn.gyyx.action.beans.wdshenluowangxiang;


/**
 * <p>
 * 活动中用到的一些常量
 * </p>
 * 
 * @author chenglong
 * @since 0.0.1
 */
public class Constants {

    public static final int GAME_ID = 2;
    public static final int HD_CODE = 462;
    public static final String HD_NAME = "问道森罗万象活动";
    public static final String HD_NAME_ENGLISH = "wdshenluowangxiang";
    public static final String SERVER_LIST = "serverlist";
    // 每天的获奖次数
    public static final int DAILY_TIMES = 3;
    // 分布式锁的前缀
    public static final String LOTTERY_PRIFIX = HD_CODE + "_lottery_";

    // 邀请函 地址
    public static final String INVITE_ADDRESS = "invite";
    // 发奖 地址
    public static final String LOTTERY_ADDRESS = "lottery";
    // 游戏主键
    public static final int GAMEID = 2;
    // 友好提示
    public static final String FRIENDLY_PROMPT = "服务器繁忙,请稍后再试~";
    public static final String NOLOGIN_PROMPT = "您没有登录！";
   
    private Constants() {
        
    }

}
