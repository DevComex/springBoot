/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年3月9日上午10:49:00
 * 版本号：v1.0
 * 本类主要用途叙述：按概率中奖的奖品实体
 */



package cn.gyyx.action.beans.lottery;

public class ChancePrizeBean {
	//code
	    private int code;
	
		private int prizeCode;
	    private double probability;
		//概率
	    private int actionCode;
	    /**
	     * 限定个数
	     */
	    private  int number;
	    
	    //////////////////奖品补充专用////////////////////////
		//奖品名
	    private String prizeName;
	    public String getPrizeName() {
			return prizeName;
		}
		public void setPrizeName(String prizeName) {
			this.prizeName = prizeName;
		}
		//奖品名
	    private String prizeEnglish;
	    public String getPrizeEnglish() {
			return prizeEnglish;
		}
		public void setPrizeEnglish(String prizeEnglish) {
			this.prizeEnglish = prizeEnglish;
		}
		//累计补充
		private int supplyNum;
		public int getSupplyNum() {
			return supplyNum;
		}
		public void setSupplyNum(int supplyNum) {
			this.supplyNum = supplyNum;
		}
		//补充前总数
		private int oldNumber;
		public int getOldNumber() {
			return oldNumber;
		}
		public void setOldNumber(int oldNumber) {
			this.oldNumber = oldNumber;
		}
		//发放量
		private int provideNum;
		public int getProvideNum() {
			return provideNum;
		}
		public void setProvideNum(int provideNum) {
			this.provideNum = provideNum;
		}
		//剩余量
		private int overNum;
		public int getOverNum() {
			return overNum;
		}
		public void setOverNum(int overNum) {
			this.overNum = overNum;
		}
		//补充时间
		private String supplyTime;
		public String getSupplyTime() {
			return supplyTime;
		}
		public void setSupplyTime(String supplyTime) {
			this.supplyTime = supplyTime;
		}
		//卡总量
		private int cardTotal;
		public int getCardTotal() {
			return cardTotal;
		}
		public void setCardTotal(int cardTotal) {
			this.cardTotal = cardTotal;
		}
		//卡剩余
		private int cardNoUse;
		public int getCardNoUse() {
			return cardNoUse;
		}
		public void setCardNoUse(int cardNoUse) {
			this.cardNoUse = cardNoUse;
		}
		////////////////////////////////////////////////
		public int getCode() {
			return code;
		}
		public void setCode(int code) {
			this.code = code;
		}
		public double getProbability() {
			return probability;
		}
		public void setProbability(double probability) {
			this.probability = probability;
		}
		public int getActionCode() {
			return actionCode;
		}
		public void setActionCode(int actionCode) {
			this.actionCode = actionCode;
		}
		public int getPrizeCode() {
			return prizeCode;
		}
		public void setPrizeCode(int prizeCode) {
			this.prizeCode = prizeCode;
		}
		public int getNumber() {
			return number;
		}
		public void setNumber(int number) {
			this.number = number;
		}
		public ChancePrizeBean(int code, int prizeCode, double probability,
				int actionCode, int number) {
			this.code = code;
			this.prizeCode = prizeCode;
			this.probability = probability;
			this.actionCode = actionCode;
			this.number = number;
		}
		public ChancePrizeBean() {
		}
		@Override
		public String toString() {
			return "ChancePrizeBean [code=" + code + ", prizeCode=" + prizeCode
					+ ", probability=" + probability + ", actionCode="
					+ actionCode + ", number=" + number + "]";
		}
		
		
}
