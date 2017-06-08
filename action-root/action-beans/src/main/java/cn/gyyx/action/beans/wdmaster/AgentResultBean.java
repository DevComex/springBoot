/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action
 * @作者：wanglei
 * @联系方式：wanglei@gyyx.cn
 * @创建时间： 2015年8月11日 
 * @版本号：V1.211
 * @本类主要用途描述：问道康师傅推广活动调api.activityresult.gyyx.cn域名的返回结果实体类
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.wdmaster;

public class AgentResultBean {
	//结果
	boolean success;
	//返回消息
	String msg;
	

	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public AgentResultBean() {
	}
	@Override
	public String toString() {
		return "ActivityResultAgentResultBean [ success=" + success+ ", msg=" + msg + "]";
	}
	
}
