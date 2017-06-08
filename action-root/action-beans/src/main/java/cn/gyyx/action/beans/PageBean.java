/*************************************************
       Copyright ©, 2015, GY Game
       Author:  fanjiaqi
       Created: 2016年3月17日
       Note：分页信息实体,不持久化到数据库
************************************************/
package cn.gyyx.action.beans;
import java.util.List;

/**
 * @ClassName: PageBean
 * @Description: TODO 分页信息实体,不持久化到数据库
 * @author fanjiaqi fanjiaqi@gyyx.cn
 * @date 2016年3月17日 下午7:54:55
 *
 */
public class PageBean<T> {
	// 总页码
	private int totalPage;
		
	// 当前页码
	private int currentPage;
		
	// 每页数量
	private int pageSize;
		
	// 是否有下一页
	private boolean hasNextPage = false;
		
	// 是否有上一页
	private boolean hasPrevPage = false;
		
	// 分页数据
	private List<T> list;
	
	// 总条数
	private int totalCount;

	public static final int PAGE_SIZE_20 = 20;
	public static final int PAGE_SIZE_50 = 50;
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		if(currentPage < 1){
			currentPage = 1;
		}
		this.currentPage = currentPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public boolean isHasNextPage() {
		return hasNextPage;
	}
	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}
	public boolean isHasPrevPage() {
		return hasPrevPage;
	}
	public void setHasPrevPage(boolean hasPrevPage) {
		this.hasPrevPage = hasPrevPage;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
	/**
	* @Title: createPage
	* @Description: TODO 创建分页信息实体
	* @param @param totalCount
	* @param @param currentPage
	* @param @param everyPageNum
	* @param @param list
	* @param @return    
	* @return PageBean<T>    
	* @throws
	*/
	public static <T> PageBean<T> createPage(int totalCount, int currentPage,
			int everyPageNum, List<T> list) {
		if(currentPage < 1){
			return null;
		}
		PageBean<T> pageBean = new PageBean<T>();
		pageBean.setCurrentPage(currentPage);
		pageBean.setPageSize(everyPageNum);
		if (totalCount % everyPageNum == 0) {
			pageBean.setTotalPage(totalCount / everyPageNum);
		} else {
			pageBean.setTotalPage(totalCount / everyPageNum + 1);
		}
		if (currentPage > 1) {
			pageBean.setHasPrevPage(true);
		}
		if (currentPage != pageBean.getTotalPage()) {
			pageBean.setHasNextPage(true);
		}
		pageBean.setList(list);
		pageBean.setTotalCount(totalCount);
		return pageBean;
	}
	
	
	/**
	 * @Title: createPage
	 * @Description: 创建分页信息实体
	 * @param @param totalCount
	 * @param @param currentPage
	 * @param @param everyPageNum
	 * @param @param list
	 * @param @return    
	 * @return PageBean<T>    
	 * @throws
	 */
	public static <T> PageBean<T> createPage(int totalCount, int currentPage, List<T> list) {
		if(currentPage < 1 || totalCount == 0){
			return null;
		}
		PageBean<T> pageBean = new PageBean<T>();
		pageBean.setCurrentPage(currentPage);
		int pageSize = 10;
		pageBean.setPageSize(pageSize);
		if (totalCount % pageSize == 0) {
			pageBean.setTotalPage(totalCount / pageSize);
		} else {
			pageBean.setTotalPage(totalCount / pageSize + 1);
		}
		if (currentPage > 1) {
			pageBean.setHasPrevPage(true);
		}
		if (currentPage != pageBean.getTotalPage()) {
			pageBean.setHasNextPage(true);
		}
		pageBean.setList(list);
		pageBean.setTotalCount(totalCount);
		return pageBean;
	}
	
	public PageBean() {
		super();
	}
		
}
