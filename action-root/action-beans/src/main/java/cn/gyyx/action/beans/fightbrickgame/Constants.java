package cn.gyyx.action.beans.fightbrickgame;

/**
 * h5小游戏使用的静态常量
  * <p>
  *   Constants描述
  * </p>
  *  
  * @author Administrator
  * @since 0.0.1
 */
public class Constants {

	/**
	 * 微信加密key
	 */
	public static final String WX_SIGN_KEY = "Dfad124FAC518DF3";
	
	/**
	 * 活动编号
	 */
	public static final Integer ACTION_CODE=447;
	
	/**
	 * 设置cookie的过期时间5分钟
	 */
	public static final Integer COOKIE_EXPIRE_MINUTE=5;
	
	/**
	 * 用于加密用户得分的唯一标识缓存5分钟
	 */
	public static final Integer IDENTIFY_CACHE_MINUTE=5;
	
	/**
	 * 游戏服务器列表缓存key
	 */
	public static final String SERVER_LIST_CACHE_TEMPLATE="hd_fightbrickgame_serverlist_%s";
	
	/**
	 * 游戏服务器列表缓存60分钟
	 */
	public static final Integer SERVER_LIST_CACHE_MINUTE=60;
	
	/**
	 * 微信昵称缓存5分钟
	 */
	public static final Integer WEIXIN_NICKNAME_CACHE_MINUTE=5;
	
	/**
	 * 微信昵称缓存的模板
	 */
	public static final String WEIXIN_NICKNAME_CACHE_TEMPLATE="wd_nickname_%s";
	
	/**
	 * 问道ID
	 */
	public static final Integer GAME_ID=2;
	
	/**
	 * DistributedLock的key
	 */
	public static final String DISTRIBUTED_LOCK_TEMPLATE="hd_fightbrickgame_lock_%s";
	
	/**
	 * 锁定时间30秒
	 */
	public static final Integer DISTRIBUTED_LOCK_TIME=30;
	
	/**
	 * 等待锁的时间10秒
	 */
	public static final Integer DISTRIBUTED_LOCK_WAIT_TIME=10;
	
	/**
	 * 定时抽奖任务DistributedLock的key
	 */
	public static final String DISTRIBUTED_LOCK_LOTTERY_TASK="hd_fightbrickgame_lottery_task";
	
	/**
	 * 定时计算排名任务DistributedLock的key
	 */
	public static final String DISTRIBUTED_LOCK_RANK_TASK="hd_fightbrickgame_rank_task";
	
	/**
	 * 虚拟奖品类型
	 */
	public static final String VIRTUAL_PRESENT_TYPE="VirtualPresent";
	
	/**
	 * 实物奖品类型
	 */
	public static final String REAL_PRESENT_TYPE="RealPresent";
	
	/**
	 * 奖品名称,1W银元宝
	 */
	public static final String PRIZE_NAME="1W银元宝";
	
	/**
         * 更新分数时分布式锁的key
         */
        public static final String DISTRIBUTED_UPDATE_SCORE_LOCK_TEMPLATE="hd_fightbrickgame_updatescore_lock_%s";
}
