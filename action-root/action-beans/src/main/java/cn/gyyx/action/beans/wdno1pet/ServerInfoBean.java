/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action
 * @作者：lizepu
 * @联系方式：lizepu@gyyx.cn
 * @创建时间：2014年12月21日15:43:01
 * @版本号：v1.0
 * @本类主要用途描述：获取服务器信息集合的实体类
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.wdno1pet;

import cn.gyyx.action.beans.novicecard.ServerInfoBean.Value;

public class ServerInfoBean {
	
	private long TimeStamp;
	private Value[] Value;
	private String ErrorMessage;
	private String SignType;
	private String Sign;
	private String Error;

	public long getTimeStamp() {
		return TimeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		TimeStamp = timeStamp;
	}

	public Value[] getValue() {
		return Value;
	}

	public void setValue(Value[] value) {
		Value = value;
	}

	public String getErrorMessage() {
		return ErrorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		ErrorMessage = errorMessage;
	}

	public String getSignType() {
		return SignType;
	}

	public void setSignType(String signType) {
		SignType = signType;
	}

	public String getSign() {
		return Sign;
	}

	public void setSign(String sign) {
		Sign = sign;
	}

	public String getError() {
		return Error;
	}

	public void setError(String error) {
		Error = error;
	}
}
