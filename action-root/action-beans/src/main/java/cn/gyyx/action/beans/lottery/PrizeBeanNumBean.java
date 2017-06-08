package cn.gyyx.action.beans.lottery;

public class PrizeBeanNumBean {
	
	private int prizeCode;
	private int num;
	public int getPrizeCode() {
		return prizeCode;
	}
	public void setPrizeCode(int prizeCode) {
		this.prizeCode = prizeCode;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public PrizeBeanNumBean(int prizeCode, int num) {
		super();
		this.prizeCode = prizeCode;
		this.num = num;
	}

}
