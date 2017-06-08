package cn.gyyx.action.service.challenger;

/**
 * 版        权：光宇游戏
 * 作        者：ChengLong
 * 创建时间：2016年7月12日 下午7:49:24
 * 描        述：活动常量
 */
public class ChallengerConstant {
	/**
	 * 用户审核状态
	 */
	public enum userState{
		oncheck,
		pass,
		unpass
	}
	
	/**
	 * 队伍创建审核状态
	 */
	public enum teamState{
		oncheck,
		pass,
		unpass
	}
	
	/**
	 * 队伍申请审核状态
	 */
	public enum teamApplyState{
		oncheck,
		pass,
		unpass
	}
	
	/**
	 * 成员申请申队伍审核状态
	 */
	public enum memberApplyTeamState{
		oncheck,
		pass,
		unpass
	}
	
	/**
	 * 视频列表是否置顶
	 */
	public enum liveRadioState{
		Y,
		N,
	}
	
	/**
	 * simpleData 数据类型
	 */
	public enum simpleDataType{
		LIVE_RANK,
		DEATH_LIFE_RANK,
		LIVE_TIME_RANK
	}
	
	public static final int TEAM_PAGE_COUNT = 12;//队伍列表分页个数
	
	public static final String HD_NAME = "问道新世界挑战者大赛";
	
	public static final String MEM_KEY_PREFIX = "WDChallenger_";
	
	public static final int ACTIVITY_CODE = 375;
	
	public static final int LIVE_RADIO_TOP_COUNT = 5;

}
