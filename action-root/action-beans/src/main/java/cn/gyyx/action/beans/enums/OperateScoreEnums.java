package cn.gyyx.action.beans.enums;

public enum OperateScoreEnums {
	// 兑换
	Exchange("exchange"),
	// 抽奖
	Lottery("lottery"),
	// 签到
	SignUp("signUp"),
	// 分享新浪微博
	SinaBlog("sinaBlog"),
	// 根据游戏数据计算
	GameConvert("gameConvert");
	
	private String name;
	
	private OperateScoreEnums(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}
