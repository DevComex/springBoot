/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：炫舞吧积分活动
 * @作者：王雷
 * @联系方式：wanglei@gyyx.cn
 * @创建时间： 2015年9月14日
 * @版本号：V1.214
 * @本类主要用途描述:调取发送物品接口的返回类
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.xwbcreditsluckydraw;


public class SendPrizeResult {
	//是否发送成功
	boolean isSuccess;
	//发送状态
	String status;
	//发送返回信息
	String message;
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public SendPrizeResult() {
		super();
		this.isSuccess = false;
		this.status = "未知错误";
		this.message = "初始化完成";
	}
	@Override
	public String toString() {
		return "SendPrizeResult [isSuccess=" + isSuccess + ", status=" + status
				+ ", message=" + message + "]";
	}
	
}
