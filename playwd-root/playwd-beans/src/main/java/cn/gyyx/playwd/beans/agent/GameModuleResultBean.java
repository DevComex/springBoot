/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @作者：fanyongliang
 * @联系方式：fanyongliang@gyyx.cn
 * @创建时间： 2016年3月15日 
 * @本类主要用途描述：webapi实体
 *-------------------------------------------------------------------------
 */
package cn.gyyx.playwd.beans.agent;

import java.util.List;

public class GameModuleResultBean {

	private List<GameInfoBean> data;
	private String status;
	public List<GameInfoBean> getData() {
		return data;
	}
	public void setData(List<GameInfoBean> data) {
		this.data = data;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
