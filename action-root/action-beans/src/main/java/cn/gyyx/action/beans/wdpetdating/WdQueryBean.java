package cn.gyyx.action.beans.wdpetdating;

import java.io.UnsupportedEncodingException;

public class WdQueryBean {
	int areaCode;
	int serverCode;
	String queryStr;
	
	/**
	 * 排序策略：最热(hotest) 最新(newest)
	 */
	String strategy;
	
	public int getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(int areaCode) {
		this.areaCode = areaCode;
	}

	public int getServerCode() {
		return serverCode;
	}

	public void setServerCode(int serverCode) {
		this.serverCode = serverCode;
	}

	public String getQueryStr() {
		return queryStr;
	}

	public void setQueryStr(String queryStr) {
		this.queryStr = queryStr;
		try {
			this.queryStr = new String(queryStr.getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
	}

	public String getStrategy() {
		return strategy;
	}

	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}
	
}
