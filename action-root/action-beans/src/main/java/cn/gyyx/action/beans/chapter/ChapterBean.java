/**
 * --------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：范佳琪 
 * 联系方式：fanjiaqi@gyyx.cn 
 * 创建时间：2015年12月17日
 * 版本号：v1.0
 * 本类主要用途叙述：存放章节信息
 */

package cn.gyyx.action.beans.chapter;

import java.util.Date;

public class ChapterBean {

	// 章节Code
	private int chapterCode;
	// 章节名称
	private String chapterName;
	// 章节内容
	private String content;
	// 字数
	private int wordNum;
	// 创建时间
	private Date createTimeStr;
	// 更新时间
	private Date updateTimeStr;
	// 0 正常； 99 屏蔽；100 删除
	private String status;
	private int orderNum;

	public int getChapterCode() {
		return chapterCode;
	}

	public void setChapterCode(int chapterCode) {
		this.chapterCode = chapterCode;
	}

	public String getChapterName() {
		return chapterName;
	}

	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getWordNum() {
		return wordNum;
	}

	public void setWordNum(int wordNum) {
		this.wordNum = wordNum;
	}

	public Date getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(Date createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public Date getUpdateTimeStr() {
		return updateTimeStr;
	}

	public void setUpdateTimeStr(Date updateTimeStr) {
		this.updateTimeStr = updateTimeStr;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	@Override
	public String toString() {
		return "ChapterBean [chapterCode=" + chapterCode + ", chapterName="
				+ chapterName + ", content=" + content + ", wordNum=" + wordNum
				+ ", createTimeStr=" + createTimeStr + ", updateTimeStr="
				+ updateTimeStr + ", status=" + status + ", orderNum="
				+ orderNum + "]";
	}
}
