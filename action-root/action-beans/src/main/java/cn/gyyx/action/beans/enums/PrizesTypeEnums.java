package cn.gyyx.action.beans.enums;

public enum PrizesTypeEnums {
	// 谢谢参与
	ThankYou {
		public String toString() {
			return "ThankYou";
		}
	},
	// 实物奖
	RealPrizes {
		public String toString() {
			return "RealPrizes";
		}
	},
	// 邀请函
	Invitation {
		public String toString() {
			return "Invitation";
		}
	},
	// 虚拟奖品
	VirtualPrizes {
		public String toString() {
			return "VirtualPrizes";
		}
	},
	// 需要发放到游戏中的奖品
	GamePrizes {
		public String toString() {
			return "GamePrizes";
		}
	};
}
