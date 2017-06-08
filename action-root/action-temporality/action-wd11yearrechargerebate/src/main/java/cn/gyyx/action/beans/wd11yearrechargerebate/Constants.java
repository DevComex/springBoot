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
package cn.gyyx.action.beans.wd11yearrechargerebate;


/**
 * <p>
 * 活动中用到的一些常量
 * </p>
 * 
 * @author chenglong
 * @since 0.0.1
 */
public class Constants {

    public static final int HD_CODE = 452;
    public static final String HD_NAME = "问道11年充值兑换返现";
    public static final String HD_NAME_ENGLISH = "wd11yearrechargerebate";
   
    // 分布式锁的前缀
    //新服充值金额是否大于等于10
    public static final String RECHARGEISGT10_PRIFIX = HD_CODE + "_rechargeisgt10_";
    //新服等级是否大于等于60
    public static final String LEVELISGT60_PRIFIX = HD_CODE + "_levelisgt60_";
    //账号请求接口次数:x分钟x账号
    public static final String ACCOUNT_REQUEST_PRIFIX = HD_CODE + "_num_acount_%s_%s";
    //IP请求接口次数：xipx分钟
    public static final String IP_REQUEST_PRIFIX = HD_CODE + "_num_ip_%s_%s";


    // 游戏主键
    public static final int GAMEID = 2;
    // 友好提示
    public static final String FRIENDLY_PROMPT = "参与活动的玩家太多啦，服务器暂时忙不过来，请稍后再试~";
   
    private Constants() {
        
    }

}
