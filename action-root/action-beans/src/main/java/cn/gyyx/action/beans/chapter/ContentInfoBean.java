/**
 * --------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：范佳琪 
 * 联系方式：fanjiaqi@gyyx.cn 
 * 创建时间：2015年12月17日
 * 版本号：v1.0
 * 本类主要用途叙述：TODO 获取章节信息封装类
 */

package cn.gyyx.action.beans.chapter;

public class ContentInfoBean {

	//小说当前章内容
	private String content;
	private int orderNum;
	//上一章Code
	private int lastChapterCode;
	//下一章Code
	private int nextChapterCode;
	
	public ContentInfoBean() {
		// TODO Auto-generated constructor stub
	}

	public ContentInfoBean(String content, int orderNum, int lastChapterCode,
			int nextChapterCode) {
		this.content = content;
		this.orderNum = orderNum;
		this.lastChapterCode = lastChapterCode;
		this.nextChapterCode = nextChapterCode;
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public int getLastChapterCode() {
		return lastChapterCode;
	}
	public void setLastChapterCode(int lastChapterCode) {
		this.lastChapterCode = lastChapterCode;
	}
	public int getNextChapterCode() {
		return nextChapterCode;
	}
	public void setNextChapterCode(int nextChapterCode) {
		this.nextChapterCode = nextChapterCode;
	}

	@Override
	public String toString() {
		return "ContentInfoBean [content=" + content + ", orderNum=" + orderNum
				+ ", lastChapterCode=" + lastChapterCode + ", nextChapterCode="
				+ nextChapterCode + "]";
	}
}
