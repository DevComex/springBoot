/**
  * -------------------------------------------------------------------------
  * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
  * @版权所有：北京光宇在线科技有限责任公司
  * @项目名称：action-wechatroulette
  * @作者：caishuai
  * @联系方式：caishuai@gyyx.cn
  * @创建时间：2017年3月10日 下午3:04:53
  * @版本号：0.0.1
  *-------------------------------------------------------------------------
  */
package cn.gyyx.action.beans.wechatroulette;

/**
 * <p>
 * 项目常量定义
 * </p>
 * 
 * @author caishuai
 * @since 0.0.1
 */
public class Constant {
    /**
     * 活动Code
     */
    public static final int ACTION_CODE = 449;
    /**
     * 每日允许最大次数
     */
    public static final int MAX_TIMES = 3;
    /**
     * 公众号类型
     */
    public static final String WeChat_Type = "Wd";
    /**
     * 积分转化抽奖次数比率
     */
    public static final int Rate = 5;
    /**
     * 游戏code
     */
    public static final int Game_Id = 2;

    /**
     * 发放的物品
     */
    public static final String Send_Gift = "铜钱铭牌";
    /**
     * 游戏礼包
     */
    public static final String Send_Gift_Package = "特殊铭牌奖励活动奖励铜钱铭牌(140902)";
    /**
     * 谢谢参与的prizeCode
     */
    public static final int Thanks_Code = 1661;

    /**
     * 获取服务器信息缓存过期时间 3分钟
     */
    public static final int Three_Mins = 3 * 60;

    /**
     * 获取奖品列表缓存时间1分钟
     */
    public static final int One_Min = 60;
    /**
     * 绑定角色缓存时间15天，单位为天
     */
    public static final int Fifteen_Days = 15; 
}
