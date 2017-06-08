package cn.gyyx.action.beans.lhzs.activityCode;

public class LHZSConfig {
	public static final String GAMEID="16";
	public static enum RESULTTYPE{
		noLogin,noInternet,noChance,success,alreadyHas,error,updateError,noActivityCode;
	}
	public static enum PRESENTTYPE{
		noPrize,noRealPrize;
	}
	public static enum PRESENT{
		activityCodeType("action_recharge_tb:type","LHZSActivityCode")
	   ,prizeEnglish("action_prize_tb:english","thanks");
		private String explain;
		private String value;
		private PRESENT(String explain,String value){
			this.explain = explain;
			this.value = value;
		}
		public String getExplain() {
			return explain;
		}
		public String getValue() {
			return value;
		}
		
	}
}
