/**------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：liqiang
 * 联系方式：liqiang@gyyx.cn 
 * 创建时间： 2015/11/11
 * 版本号：v1.0 
 * 本类主要用途描述： 
-------------------------------------------------------------------------*/

package cn.gyyx.action.beans.wdlogdata;

import java.util.Date;

/**
 * 安装与卸载问道LOG数据的bean
 */
public class WdLogDataBean {
	/*
	 * 主键
	 */
	private Integer code;
	/*
	 * 客户端程序安装完成时刻
	 */
	private Date time;
	/*
	 * 玩家的本机ip
	 */
	private String ip;
	/*
	 * 玩家的本机mac
	 */
	private String mac;
	/*
	 * 用户账号
	 */
	private String acc;
	/*
	 * 游戏ID
	 */
	private String gameid;
	/*
	 * 正在安装的版本号
	 */
	private String ver;
	/*
	 * 区分玩家操作是安装还是卸载
	 */
	
	private int type;

	public WdLogDataBean() {
		
	}

	public WdLogDataBean(Date time, String ip, String mac, String ver, String acc,String gameid, int type) {
		super();
		this.time = time;
		this.ip = ip;
		this.mac = mac;
		this.ver = ver;
		this.acc = acc;
		this.gameid = gameid;
		this.type = type;
	}

	public WdLogDataBean(Integer code, Date time, String ip, String mac,String acc,String gameid,
			String ver, int type) {
		super();
		this.code = code;
		this.time = time;
		this.ip = ip;
		this.mac = mac;
		this.ver = ver;
		this.acc = acc;
		this.gameid = gameid;
		this.type = type;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}
    
	public String getAcc() {
		return acc;
	}

	public void setAcc(String acc) {
		this.acc = acc;
	}

	public String getGameid() {
		return gameid;
	}

	public void setGameid(String gameid) {
		this.gameid = gameid;
	}

	public String getVer() {
		return ver;
	}

	public void setVer(String ver) {
		this.ver = ver;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "WdLogDataBean [code=" + code + ", time=" + time + ", ip=" + ip
				+ ", mac=" + mac + ", ver=" + ver + ", acc=" + acc + ", gameid=" + gameid + ",type=" + type + "]";
	}



}
