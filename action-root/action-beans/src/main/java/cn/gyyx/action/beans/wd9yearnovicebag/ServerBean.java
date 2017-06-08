/*------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：周忠凯 王雷
 * 联系方式：zhouzhongkai@gyyx.cn wanglei@gyyx.cn 
 * 创建时间：2015年03月25日 下午4:57:58 
 * 版本号：v1.0 
 * 本类主要用途描述： 
 * 
-------------------------------------------------------------------------*/
package cn.gyyx.action.beans.wd9yearnovicebag;

import java.util.List;

public class ServerBean {
	//主键
	private Integer code;
	//区组
	private String areaName;
	//服务器信息集合
	private List<ActionServerConfigBean> serverList;
	
	public List<ActionServerConfigBean> getServerList() {
		return serverList;
	}
	public void setServerList(List<ActionServerConfigBean> serverList) {
		this.serverList = serverList;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}

	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public ServerBean(){
		
	}
	public ServerBean(Integer code, String areaName,
			List<ActionServerConfigBean> serverList) {
		super();
		this.code = code;
		this.areaName = areaName;
		this.serverList = serverList;
	}

	
}
