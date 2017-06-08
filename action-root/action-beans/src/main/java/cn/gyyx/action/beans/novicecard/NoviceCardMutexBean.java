package cn.gyyx.action.beans.novicecard;

/**
 * 新手卡（礼包）互斥信息Bean
 * @author Administrator
 *
 */
public class NoviceCardMutexBean {
	private int code;    //非功能标识
	private int actionSymbol;    //当前活动编号 [新手卡（批次）, 新手礼包（活动编号）]
	private int mutexCode;    //与之互斥的活动  [新手卡（批次）,新手礼包（活动编号）]
	private String mutexCodeSymbol;    //标注与之互斥活动类型
	private boolean isDelete;    //是否删除
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public int getActionSymbol() {
		return actionSymbol;
	}
	public void setActionSymbol(int actionSymbol) {
		this.actionSymbol = actionSymbol;
	}
	public int getMutexCode() {
		return mutexCode;
	}
	public void setMutexCode(int mutexCode) {
		this.mutexCode = mutexCode;
	}
	public String getMutexCodeSymbol() {
		return mutexCodeSymbol;
	}
	public void setMutexCodeSymbol(String mutexCodeSymbol) {
		this.mutexCodeSymbol = mutexCodeSymbol;
	}
	public boolean getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
	
}
