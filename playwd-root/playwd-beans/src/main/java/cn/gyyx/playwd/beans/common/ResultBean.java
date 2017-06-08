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
package cn.gyyx.playwd.beans.common;


public class ResultBean<T> {

	private boolean isSuccess;
	private String message;
	private T data;
	private String status;

	public ResultBean() {
		
	}
	public ResultBean(boolean isSuccess,String message) {
	    this.setSuccess(isSuccess);
            this.setMessage(message);
        }
	public ResultBean(boolean isSuccess,String message,T date) {
		this.setSuccess(isSuccess);
		this.setMessage(message);
		this.setData(date);
	}
	
	public ResultBean(String status,String message,T date) {
		this.setMessage(message);
		this.setData(date);
		this.setStatus(status);
		if(this.getStatus().equals("success")){
			this.setSuccess(true);
		}else {
			this.setSuccess(false);
		}
	}
	
	public boolean getIsSuccess() {
		return isSuccess;
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

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	@Override
	public String toString() {
		return "ResultBean [isSuccess=" + isSuccess + ", message=" + message
				+ ", data=" + data + "]";
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
}