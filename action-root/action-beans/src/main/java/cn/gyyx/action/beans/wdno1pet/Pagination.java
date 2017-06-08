/*------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：Chen 
 * 联系方式：chenpeng03@gyyx.cn 
 * 创建时间： 2014年12月16日 上午9:42:36
 * 版本号：v1.0 
 * 本类主要用途描述： 
 * 分页模版
-------------------------------------------------------------------------*/
package cn.gyyx.action.beans.wdno1pet;

import java.util.List;

public class Pagination<T> {
	private int size = 4;// 每页显示2条

	private int pageNum = 1; // 当前页码

	private int count;// 结果集总计大小
	private int totalPages;// 总共的页码数量

	private List<T> objects;

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public List<T> getObjects() {
		return objects;
	}

	public void setObjects(List<T> objects) {
		this.objects = objects;
	}

	public Integer getTotalPages() {
		Integer lastPage = this.count / this.size;
		return this.count % this.size == 0 ? lastPage : lastPage + 1;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}
}
