/**
  * -------------------------------------------------------------------------
  * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
  * @版权所有：北京光宇在线科技有限责任公司
  * @项目名称：action-wd11thyearscommentwall
  * @作者：caishuai
  * @联系方式：caishuai@gyyx.cn
  * @创建时间：2017年3月10日 下午3:04:53
  * @版本号：0.0.1
  *-------------------------------------------------------------------------
  */
package cn.gyyx.action.beans.wd11thyearscommentwall;

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
    public static final int ACTION_CODE = 444;
    /**
     * 缓存时间变量名
     */
    public static final String CACHE_TIME = "cacheTime";
    /**
     * 缓存时间默认值
     */
    public static final int CACHE_TIME_DEFAULT = 100;
    /**
     * 留言每日限制次数变量名
     */
    public static final String LIMIT = "limit";
    /**
     * 留言每日限制次数默认值
     */
    public static final int LIMIT_DEFAULT = 20;
    /**
     * 每次加载的弹幕数量变量名
     */
    public static final String BARRAGE = "barrage";
    /**
     * 每次加载的弹幕数量默认值
     */
    public static final int BARRAGE_DEFAULT = 150;
    /**
     * 允许的活动code值变量名
     */
    public static final String ALLOW_ACTION = "allowAction";

    /**
     * 允许的活动code值默认值
     */
    public static final String ALLOW_ACTION_DEFAULT = "444,445";
}
