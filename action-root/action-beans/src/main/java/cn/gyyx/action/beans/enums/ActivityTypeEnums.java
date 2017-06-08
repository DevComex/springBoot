package cn.gyyx.action.beans.enums;

public enum ActivityTypeEnums {
	// 普通抽奖
	Lottery {
		public String toString() {
			return "lottery";
		}
	},
	// 普通兑换
	Exchange {
		public String toString() {
			return "exchange";
		}
	},
	// 抽奖抽中了邀请函
	InvitationOfLottery {
		public String toString() {
			return "invitation";
		}
	};
}
