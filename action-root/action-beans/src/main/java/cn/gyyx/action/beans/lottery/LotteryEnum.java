package cn.gyyx.action.beans.lottery;

public class LotteryEnum {

	public enum PrizesTypeEnum {
		// 实物
		RealPrizes { public String getName() { return "RealPrizes"; } },
		// 虚拟物品
		VirtualPrizes { public String getName() { return "VirtualPrizes"; } },
		// 游戏物品
		GamePrizes { public String getName() { return "GamePrizes"; } },
		// 卡
		CardPrizes { public String getName() { return "CardPrizes"; } },
		// 谢谢参与
		ThankYou { public String getName() { return "ThankYou"; } };
	}
}
