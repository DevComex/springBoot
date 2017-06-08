/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：wdfire
 * @作者：yangyusheng
 * @联系方式：yangyusheng@gyyx.cn
 * @创建时间： 2015年4月9日 下午2:04:06
 * @版本号：
 * @本类主要用途描述：操作结果集
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.wd10yearcoser;

public class ResultBeanWithPage<T> {

	private boolean isSuccess;
	private String message;
	private T data;
	private int  totalPage;
	public ResultBeanWithPage() {

	}
   
    

	public ResultBeanWithPage(boolean isSuccess, String message, T data,
			int totalPage) {
		this.isSuccess = isSuccess;
		this.message = message;
		this.data = data;
		this.totalPage = totalPage;
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
	 * @param data
	 *            void
	 */

	public void setProperties(boolean isSuccess, String message, T data,int totalPage) {
		this.isSuccess = isSuccess;
		this.message = message;
		this.setData(data);
		this.totalPage = totalPage;
	}
	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

}
