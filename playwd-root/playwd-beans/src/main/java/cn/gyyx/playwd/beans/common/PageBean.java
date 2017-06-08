package cn.gyyx.playwd.beans.common;

import java.util.List;

/**
 * 
  * <p>
  *   PageBean 页面分类
  * </p>
  * 
  * @param <T> 
  * @author lihu
  * @since 0.0.1
 */
public class PageBean<T>  extends ResultBean<T>{
  
	// 总页码
	private int totalPage;
		
	// 当前页码
	private int pageIndex;
		
	// 每页数量
	private int pageSize;
		
	// 分页数据
	private List<T> dataSet;
	
	// 总条数
	private int count;

	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getpageIndex() {
		return pageIndex;
	}
	public void setpageIndex(int pageIndex) {
		if(pageIndex < 1){
			pageIndex = 1;
		}
		this.pageIndex = pageIndex;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

    public List<T> getDataSet() {
        return dataSet;
    }
    public void setDataSet(List<T> dataSet) {
        this.dataSet = dataSet;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
 
    /**
     * 
      * <p>
      *    创建分页信息实体
      * </p>
      *
      * @action
      *    lihu 2017年3月14日 上午10:56:02 描述
      *
      * @param isSuccess
      * @param Count
      * @param pageIndex
      * @param pageSize
      * @param DataSet
      * @param message
      * @return PageBean<T>
     */
	public static <T> PageBean<T> createPage(boolean isSuccess,int count, int pageIndex,
			int pageSize, List<T> data,String message) {
		if(pageIndex < 1){
			return null;
		}
		PageBean<T> pageBean = new PageBean<T>();
		pageBean.setpageIndex(pageIndex);
		pageBean.setPageSize(pageSize);
		pageBean.setSuccess(isSuccess);
		if (count % pageSize == 0) {
			pageBean.setTotalPage(count / pageSize);
		} else {
			pageBean.setTotalPage(count / pageSize + 1);
		}
		pageBean.setDataSet(data);
		pageBean.setCount(count);
		pageBean.setMessage(message);
		return pageBean;
	}
	
	 /**
     * 
      * <p>
      *    创建分页信息实体
      * </p>
      *
      * @action
      *    lihu 2017年3月14日 上午10:56:02 描述
      *
      * @param isSuccess
      * @param Count
      * @param pageIndex
      * @param pageSize
      * @param DataSet
      * @param message
      * @return PageBean<T>
     */
	public static <T> PageBean<T> createPage(String status,int count, int pageIndex,
			int pageSize, List<T> data,String message) {
		if(pageIndex < 1){
			return null;
		}
		PageBean<T> pageBean = new PageBean<T>();
		pageBean.setpageIndex(pageIndex);
		pageBean.setPageSize(pageSize);
		
		if (count % pageSize == 0) {
			pageBean.setTotalPage(count / pageSize);
		} else {
			pageBean.setTotalPage(count / pageSize + 1);
		}
		pageBean.setDataSet(data);
		pageBean.setCount(count);
		pageBean.setMessage(message);
		pageBean.setStatus(status);
		if(pageBean.getStatus().equals("success")){
			pageBean.setSuccess(true);
		}else {
			pageBean.setSuccess(false);
		}
		return pageBean;
	}
	
	/**
	 * 
	  * <p>
	  *    
	  * </p>
	  *
	  * @action
	  *    lihu 2017年3月14日 上午10:55:48 描述
	  *
	  * @param isSuccess
	  * @param Count
	  * @param pageIndex
	  * @param DataSet
	  * @param message
	  * @return PageBean<T>
	 */
	public static <T> PageBean<T> createPage(boolean isSuccess,int count, int pageIndex, List<T> data,String message) {
		if(pageIndex < 1 || count == 0){
			return null;
		}
		PageBean<T> pageBean = new PageBean<T>();
		pageBean.setpageIndex(pageIndex);
		pageBean.setSuccess(isSuccess);
		int pageSize = 10;
		pageBean.setPageSize(pageSize);
		if (count % pageSize == 0) {
			pageBean.setTotalPage(count / pageSize);
		} else {
			pageBean.setTotalPage(count / pageSize + 1);
		}
		pageBean.setDataSet(data);
		pageBean.setCount(count);
		pageBean.setMessage(message);
		return pageBean;
	}
	
	public PageBean() {
		super();
	}
	
	public PageBean(boolean isSuccess,String message) {
	        super(isSuccess,message);
    }
}
