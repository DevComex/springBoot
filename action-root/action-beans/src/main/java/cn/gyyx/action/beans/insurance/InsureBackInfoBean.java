package cn.gyyx.action.beans.insurance;

public class InsureBackInfoBean {
	private String rInt;    //PICC返回投保状态
	private String rMsg;	//PICC返回未处理信息或错误信息
	private String Data;	//PICC保单处理流水
	public String getrInt() {
		return rInt;
	}
	public void setrInt(String rInt) {
		this.rInt = rInt;
	}
	public String getrMsg() {
		return rMsg;
	}
	public void setrMsg(String rMsg) {
		this.rMsg = rMsg;
	}
	public String getData() {
		return Data;
	}
	public void setData(String data) {
		Data = data;
	}
	
	
}
