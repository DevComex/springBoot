package cn.gyyx.action.beans.wdlogdata;

import java.util.Date;

/**
 * 
 * @author liqiang
 * @作用:封装放入缓存的计数器
 */
public class CountBean {
	private Date firstCacheDate;
	private Integer data;

	public CountBean() {
	}

	@Override
	public String toString() {
		return "CountBean [firstCacheDate=" + firstCacheDate + ", data=" + data
				+ "]";
	}

	public Date getFirstCacheDate() {
		return firstCacheDate;
	}

	public void setFirstCacheDate(Date firstCacheDate) {
		this.firstCacheDate = firstCacheDate;
	}

	public Integer getData() {
		return data;
	}
	public void setData(Integer data) {
		this.data = data;
	}


}
