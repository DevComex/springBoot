/**
 * --------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：范佳琪 
 * 联系方式：fanjiaqi@gyyx.cn 
 * 创建时间：2015年12月17日
 * 版本号：v1.0
 * 本类主要用途叙述：TODO 分页获取章节目录信息封装类
 */

package cn.gyyx.action.beans.chapter;

import java.util.List;

public class CatalogBean {
	
	//章节信息
	private List<ChapterBean> chapterBeans;
	//章节总数
	private int count;
	
	public CatalogBean() {
		
	}
	
	public CatalogBean(List<ChapterBean> chapterBeans, int count) {
		this.chapterBeans = chapterBeans;
		this.count = count;
	}



	public List<ChapterBean> getChapterBeans() {
		return chapterBeans;
	}
	public void setChapterBeans(List<ChapterBean> chapterBeans) {
		this.chapterBeans = chapterBeans;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	@Override
	public String toString() {
		return "CatalogBean [chapterBeans=" + chapterBeans + ", count=" + count
				+ "]";
	}
}
