/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action
 * @作者：yangyusheng
 * @联系方式：yangyusheng@gyyx.cn
 * @创建时间： 2014年12月12日 下午3:57:01
 * @版本号：
 * @本类主要用途描述：游戏WebService中方法返回值的基类
 *-------------------------------------------------------------------------
 */

package cn.gyyx.action.beans.novicecard;

public class ProcessResult {

	// 0 为成功，无错误；任何大于0的值均为错误代码
	private int errorCode;
	// 详细说明
	public String description;

	// 默认错误码为int最小值
	public ProcessResult() {
		errorCode = -2147483648;
		description = "";
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "ProcessResult [errorCode=" + errorCode + ", description="
				+ description + "]";
	}
	
}
