/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年7月21日下午2:24:46
 * 版本号：v1.0
 * 本类主要用途叙述：临时上次登录信息
 */



package cn.gyyx.action.beans.insurance;

public class LastLoginBean {
	public LastLoginBean() {
	}
	private String ip;
	private String time;
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public LastLoginBean(String ip, String time) {
		this.ip = ip;
		this.time = time;
	}
	

}
