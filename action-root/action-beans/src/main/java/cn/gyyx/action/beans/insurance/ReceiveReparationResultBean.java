/*------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：王雷
 * 联系方式：wanglei@gyyx.cn 
 * 创建时间：2015年07月14日 
 * 版本号：v1.0 
 * 本类主要用途描述： 虚拟保险项目——接收保险公司的理赔信息返回类
 * 
-------------------------------------------------------------------------*/

package cn.gyyx.action.beans.insurance;

public class ReceiveReparationResultBean {

	//获取或验证结果
	boolean IsSuccess;
	//验证结果
	boolean Error;
	//验证失败或错误描述
	String ErrorDescription;
	public boolean isIsSuccess() {
		return IsSuccess;
	}
	public void setIsSuccess(boolean isSuccess) {
		IsSuccess = isSuccess;
	}
	public boolean isError() {
		return Error;
	}
	public void setError(boolean error) {
		Error = error;
	}
	public String getErrorDescription() {
		return ErrorDescription;
	}
	public void setErrorDescription(String errorDescription) {
		ErrorDescription = errorDescription;
	}
	public void setAll (boolean isSuccess, boolean error,
			String errorDescription){
		IsSuccess = isSuccess;
		Error = error;
		ErrorDescription = errorDescription;
	}
	public ReceiveReparationResultBean(boolean isSuccess, boolean error,
			String errorDescription) {
		super();
		IsSuccess = isSuccess;
		Error = error;
		ErrorDescription = errorDescription;
	}
	public ReceiveReparationResultBean() {
		super();
	}
	@Override
	public String toString() {
		return "ReceiveReparationResultBean [IsSuccess=" + IsSuccess
				+ ", Error=" + Error + ", ErrorDescription=" + ErrorDescription
				+ "]";
	}
	
	
	
	
}
