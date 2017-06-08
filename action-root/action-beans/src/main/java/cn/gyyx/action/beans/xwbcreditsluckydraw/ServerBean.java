/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：xuanwuba
 * @作者：yangyusheng
 * @联系方式：yangyusheng@gyyx.cn
 * @创建时间： 2015年7月8日 下午1:55:31
 * @版本号：
 * @本类主要用途描述：炫舞吧服务器实体类
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.xwbcreditsluckydraw;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ServerBean {
	@JsonProperty(value="status")
	private String status;
	@JsonProperty(value="data")
	private Data[] data;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Data[] getData() {
		return data;
	}

	public void setData(Data[] data) {
		this.data = data;
	}
}
