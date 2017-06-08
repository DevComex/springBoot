package cn.gyyx.action.beans.wdpetdating;

import java.util.List;

import cn.gyyx.action.beans.common.ActionCommonCommentBean;

public class SingleResult {
	/**
	 * 上一个作品主键
	 */
	int previousCode;
	/**
	 * 下一个作品主键
	 */
	int nextCode;
	WdDatingPet data;
	List<ActionCommonCommentBean> comments;
	public int getPreviousCode() {
		return previousCode;
	}
	public void setPreviousCode(int previousCode) {
		this.previousCode = previousCode;
	}
	public int getNextCode() {
		return nextCode;
	}
	public void setNextCode(int nextCode) {
		this.nextCode = nextCode;
	}
	public WdDatingPet getData() {
		return data;
	}
	public void setData(WdDatingPet data) {
		this.data = data;
	}
	public List<ActionCommonCommentBean> getComments() {
		return comments;
	}
	public void setComments(List<ActionCommonCommentBean> comments) {
		this.comments = comments;
	}
	
}
