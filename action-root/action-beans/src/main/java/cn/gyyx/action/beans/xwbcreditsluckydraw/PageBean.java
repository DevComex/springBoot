/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：xuanwuba-creditsLuckyDraw-backstage
 * @作者：mawenbin
 * @联系方式：mawenbin@gyyx.cn
 * @创建时间： 2015年7月8日 上午11:16:53
 * @版本号：v1.204
 * @本类主要用途描述：分页实体bean
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.xwbcreditsluckydraw;

/**
 * 分页实体bean
 * */
public class PageBean {
	
	//
	public final int DEFAULT_PAGE_SIZE = 10;
	// 总页数
	private int totalRecords;
	// 保存分页的数据
	// private List list;
	// 当前页
	private int pageNo;
	// 页大小
	private int pageSize;
	//总页数
	private int totalPages;
	//首页
	private int topPage;
	// 保存用户查询的字符串，查询分页用
	// private String query;
	// 操作分页的servlet或Action（struts）
	//private String pageAction;

	public PageBean(int pageNo, int totalRecords){
		this.pageNo = pageNo;
		this.pageSize = DEFAULT_PAGE_SIZE;
		this.totalRecords = totalRecords;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	/**
	 * 取得总页数的方法 return
	 * 
	 * @return
	 */
	public int getTotalPages() {
		return (totalRecords % pageSize) == 0 ? (totalRecords / pageSize)
				: (totalRecords / pageSize + 1);
	}

	/**
	 * 得到首页
	 * 
	 * @return
	 */
	public int getTopPage() {
		return 1;
	}

	/**
	 * 得到上一页
	 * 
	 * @return
	 */
	public int getPreviousPageNo() {
		if (pageNo <= 1)
			return 1;
		else
			return (pageNo - 1);
	}

	/**
	 * 得到下一页
	 * 
	 * @return
	 */
	public int getNextPageNo() {
		if (pageNo >= getTotalPages()) {
			return getTotalPages() == 0 ? 1 : getTotalPages();
		} else {
			return pageNo + 1;
		}
	}

	/**
	 * 得到尾页
	 * 
	 * @return
	 */
	public int getBottomPageNo() {
		return getTotalRecords() == 0 ? 1 : getTotalPages();
	}
	public int[] getPageNumArray(){
		int[] resultArray;
		int k  = 0;
		if(getTotalPages() <= 11){
			resultArray = new int[getTotalPages()];
			for(int i = 1;i<= getTotalPages();i++){
				resultArray[k] = i;
				k ++;
			}
		}else{
			resultArray = new int[11];
			if(getPageNo() - 5 <= 1){
				for(int i = 1;i <= 11; i++){
					resultArray[k] = i;
					k++;
				}
			}else if(getPageNo() - 5 > 1){
				if(getPageNo() + 5 < getTotalPages()){
					for(int i = getPageNo() - 5;i <= getPageNo()+5; i++){
						resultArray[k] = i;
						k++;
					}
				}else{
					for(int i = getTotalPages() - 10;i <= getTotalPages(); i++){
						resultArray[k] = i;
						k++;
					}
				}
			}
		}
		return resultArray;
	}
	/*
	 * // 页面分页导航的链接 方式一
	 * 
	 * public String getPageToolBar1() { String str = ""; str += "<a href='" +
	 * pageAction + "?method=" + method + "&userQuery=" + query + "&pageNo=" +
	 * getTopPage() + "&pageSize=" + pageSize + "'>首页</a>&nbsp;"; str +=
	 * "<a href='" + pageAction + "?method=" + method + "&userQuery=" + query +
	 * "&pageNo=" + getPreviousPageNo() + "&pageSize=" + pageSize +
	 * "'>上一页</a>&nbsp;"; str += "<a href='" + pageAction + "?method=" + method
	 * + "&userQuery=" + query + "&pageNo=" + getNextPageNo() + "&pageSize=" +
	 * pageSize + "'>下一页</a>&nbsp;"; str += "<a href='" + pageAction +
	 * "?method=" + method + "&userQuery=" + query + "&pageNo=" +
	 * getBottomPageNo() + "&pageSize=" + pageSize + "'>尾页</a>&nbsp;";
	 * 
	 * return str; }
	 * 
	 * // 页面分页导航的链接 方式二
	 * 
	 * public String getPageToolBar2() { String str = ""; int pageSplit =
	 * (pageNo / 5) * 5;
	 * 
	 * for (int i = pageSplit - 1; i < (pageSplit + 6); i++) { if (i <= 0) {
	 * 
	 * } else if (pageNo == i) { str += i + "&nbsp;"; } else if (i >
	 * getTotalPages()) {
	 * 
	 * } else { str += "<a href='" + pageAction + "?method=" + method +
	 * "&userQuery=" + query + "&pageNo=" + i + "&pageSize=" + pageSize + "'>" +
	 * i + "</a>" + "&nbsp;"; } } return str; }
	 */

}
