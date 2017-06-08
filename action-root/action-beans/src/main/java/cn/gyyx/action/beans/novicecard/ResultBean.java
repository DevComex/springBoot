/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action
 * @作者：yangyusheng
 * @联系方式：yangyusheng@gyyx.cn
 * @创建时间： 2014年12月9日 下午3:15:17
 * @版本号：
 * @本类主要用途描述：操作结果集
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.novicecard;


public class ResultBean<T> {

	private boolean isSuccess;
	private String message;
	private T data;
	
	public ResultBean() {
		
	}
	
	public ResultBean(boolean isSuccess,String message,T date) {
		this.setIsSuccess(isSuccess);
		this.setMessage(message);
		this.setData(date);
	}
	
	public boolean getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
	/**
	 * 
	 * @日期：2014年12月18日
	 * @Title: setProperties 
	 * @Description: TODO 构造一个复制所有属性的方法
	 * @param isSuccess
	 * @param message
	 * @param data void
	 */
	
	public void setProperties(boolean isSuccess, String message, T data) {
		this.isSuccess = isSuccess;
		this.message = message;
		this.setData(data);
	}

	@Override
	public String toString() {
		return "ResultBean [isSuccess=" + isSuccess + ", message=" + message
				+ ", data=" + data + "]";
	}
	
}
