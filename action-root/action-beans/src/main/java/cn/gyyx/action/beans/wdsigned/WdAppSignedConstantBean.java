package cn.gyyx.action.beans.wdsigned;

public class WdAppSignedConstantBean {
	
	public static final String serialThreeGift="松树铭牌";
	public static final String serialFiveGift="五雷令#赠品";
	public static final String totalEightGift="如意战斗令#赠品*3";
	public static final String totalTwelveGift="修为丹#赠品*5";
	public static final String  MEM_KEY_PREFIX="wd_app_sign_";
	
	public static final String  MATRIX="用户已绑定矩阵密保卡";
	public static final String  NOTPHONE="用户已绑定实体乾坤锁";
	public static final String  MATRIXANDKEY="用户已绑定矩阵密保卡和实体乾坤锁";
	
	public static final String  HD_NAME="问道app签到活动";
	// qd 签到
	public static final String getQrUrl="http://interface.security.gyyx.cn/api/qr/new?type=QD";
	
	public static final String validQrUrl="http://interface.security.gyyx.cn/api/qr/validate";
	
	public static final int ACTIVITY_CODE=654;
	
	public static String getGiftCodeBySignCount(int count) {
		String giftPackage = null;
		if(count == 3){
			giftPackage = "光宇游戏APP每日签到活动奖励连续三天(161027)";
		}else if(count == 5){
			giftPackage = "光宇游戏APP每日签到活动奖励连续五天(161027)";
		}else if(count == 8){
			giftPackage = "光宇游戏APP每日签到活动奖励累计八天(161027)";
		}else if(count == 12){
			giftPackage = "光宇游戏APP每日签到活动奖励累计十二天(161027)";
		}
		return giftPackage;
	}

}
