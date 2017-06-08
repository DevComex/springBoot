package cn.gyyx.action.beans.activityCode;

public class ConfigBean {
	/**
	 * Existed 该账户已经领取奖品
	 * Expired 活动已经过期
	 * phoneWrong 输入的手机号有误
	 * emailWrong 输入的邮箱有误
	 * noPrizes 奖品已经发完
	 * @author Administrator
	 */
	public static enum RESULTTYPE{
		existed,expired,phoneWrong,emailWrong,noPrizes,overIP,getPrizes;
	}
	/**
	 * 返回true与false字符串
	 * @author Administrator
	 *
	 */
	public static enum JUDGE{
		trueStr("true"),falseStr("false");
		private String variable;
		private JUDGE(String variable){
			this.variable = variable;
		}
		public String getVariable() {
			return variable;
		}
		
	}
	/**
	 * 输入类型
	 * @author Administrator
	 *
	 */
	public static enum IMPORTTYPE{
		defaultType,phone,email;
	}
	/**
	 * 逻辑关系
	 * @author Administrator
	 *
	 */
	public static enum INEQUALITY {
		over,less;
	}
	/**
	 * 活动配置
	 * @author Administrator
	 *
	 */
	public static enum ACTION{
		LHCJ("272","烈火裁决官方专属礼包",0,"activityCode")
	   ,JSWS("274","官方福利客栈",0,"activityCode")
	   ,CJKP("280","超级酷跑礼包领取",0,"activityCode");
		private String actionCode;    //活动号码
		private String source;    //活动名
		private int sourceCode;    //
		private String presentType;    //奖品类型
		private ACTION(String activityCode,String source,int sourceCode,String presentType){
			this.actionCode = activityCode;
			this.source = source;
			this.sourceCode = sourceCode;
			this.presentType = presentType;
		}
		public String getSource() {
			return source;
		}
		public int getSourceCode() {
			return sourceCode;
		}
		public String getPresentType() {
			return presentType;
		}
		public String getActionCode() {
			return actionCode;
		}
	}
}
