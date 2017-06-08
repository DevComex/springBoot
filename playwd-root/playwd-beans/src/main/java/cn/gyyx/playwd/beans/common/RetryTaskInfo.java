package cn.gyyx.playwd.beans.common;

import java.util.Date;

/**
 * 版        权：光宇游戏
 * 作        者：ChengLong
 * 创建时间：2017年3月3日 下午12:19:38
 * 描        述：
 */
public abstract class RetryTaskInfo {
	  /**
     * 重试次数
     */
    private Integer retryCount;

    /**
     * 下次重试时间
     */
    private Date retryTime;

	public Integer getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(Integer retryCount) {
		this.retryCount = retryCount;
	}

	public Date getRetryTime() {
		return retryTime;
	}

	public void setRetryTime(Date retryTime) {
		this.retryTime = retryTime;
	}
	
}
