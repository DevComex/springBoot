package cn.gyyx.action.beans.callwebservice;

/**
 * 返回激活接口提示信息与状态码
 * @author matao
 *
 */
public class CallWebServiceResultMsg {
	public static final String BAD_REQUEST="badRequest";
	public static final String ERROR="error";
	public static final String FALSE="false";
	public static final String TRUE="true";
	public static final String DEFAULT="default";
	private String interfaceMsg;
	private String status;
	
	public CallWebServiceResultMsg(){
		
	}
	public CallWebServiceResultMsg(String interfaceMsg,String status){
		this.interfaceMsg = interfaceMsg;
		this.status = status;
	}
	public String getInterfaceMsg() {
		return interfaceMsg;
	}
	public void setInterfaceMsg(String interfaceMsg) {
		this.interfaceMsg = interfaceMsg;
	}
	/**
	 * status状态：</br>
	 * badRequest  错误的返回值（未知的接口返回值）</br>
	 * error  错误的请求（请求链接失败）</br>
	 * false  激活失败</br>
	 * true  激活成功</br>
	 * default  默认状态（执行激活出现问题）</br>
	 * @return
	 */
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public void addAll(String interfaceMsg ,String status){
		this.interfaceMsg = interfaceMsg;
		this.status = status;
	}
}
