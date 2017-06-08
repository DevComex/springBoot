/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：光宇9周年祝福活动
 * @作者：liuyongzhi
 * @联系方式：liuyongzhi@gyyx.cn
 * @创建时间： 2015年3月13日 下午1:14:52
 * @版本号：
 * @本类主要用途描述：分页实体类
 *-------------------------------------------------------------------------
 */
package cn.gyyx.service.wd9year;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * 分页实体类
 */
public class PageModel {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(WishService.class);
	//每页的大小
	private int pageSize = 20;
	//数据总数
	private int dataTotal;
	//页面数
	private int pageTotal;
	//当前页
	private int currentPage = 1;
	//下一页
	private int nextPage = 1 ;
	//上一页
	private int prePage = 1;
	//分页列表中显示页数
	private int showPageSize = 6;
	//分页列表
	private List<Integer> pageList;
	
	/**
	 * 分页构造函数
	 * @param datatotal 数据中数据总数
	 * @param pageSize 每页显示数据数
	 */
	public PageModel(int datatotal,int pageSize){
		logger.debug("datatotal",datatotal);
		logger.debug("pageSize",pageSize);
		this.dataTotal = datatotal;
		this.pageSize = pageSize;
		this.pageTotal = datatotal/pageSize;
		if(datatotal % pageSize > 0){
			this.pageTotal++;
		}
		if(this.pageTotal == 0){
			this.pageTotal = 1;
		}
	}
	
	/**
	 * 设置当前页
	 * @日期：2015年3月13日
	 * @Title: setCurrentPage 
	 * @param currentPage 当前页
	 * void
	 */
	public void setCurrentPage(int currentPage){
		logger.debug("currentPage",currentPage);
		this.currentPage = currentPage;
		//设置上一页
		if(currentPage > 1){
			this.prePage = currentPage-1;
		}else{
			this.prePage = 1;
		}
		//设置下一页
		if(currentPage < pageTotal){
			this.nextPage = currentPage++;
		}else{
			this.nextPage = pageTotal;
		}
	}
	
	/**
	 * 得到当前页
	 * @日期：2015年3月13日
	 * @Title: getCurrentPage 
	 * @return 当前页
	 * int
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * 得到页面大小
	 * @日期：2015年3月13日
	 * @Title: getPageSize 
	 * @return 
	 * int
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 得到数据总数
	 * @日期：2015年3月13日
	 * @Title: getDataTotal 
	 * @return  数据总数
	 * int
	 */
	public int getDataTotal() {
		return dataTotal;
	}

	/**
	 * 得到页面总数
	 * @日期：2015年3月13日
	 * @Title: getPageTotal 
	 * @return  页面总数
	 * int
	 */
	public int getPageTotal() {
		return pageTotal;
	}


	/**
	 * 下一页
	 * @日期：2015年3月13日
	 * @Title: getNextPage 
	 * @return 下一页
	 * int
	 */
	public int getNextPage() {
		return nextPage;
	}

	/**
	 * 前一页
	 * @日期：2015年3月13日
	 * @Title: getPrePage 
	 * @return 前一页
	 * int
	 */
	public int getPrePage() {
		return prePage;
	}

	/**
	 * 得到分页列表
	 * @日期：2015年3月13日
	 * @Title: getPageList 
	 * @return 分页列表
	 * List<Integer>
	 */
	public List<Integer> getPageList() {
		pageList = new ArrayList<Integer>();
		//总页数小于分页数
		if(pageTotal <= showPageSize){
			for(int i= 0; i < pageTotal; i++){
				pageList.add(i+1);
			}
		}else{
			if(currentPage < showPageSize/2){
				for(int i= 0; i < showPageSize; i++){
					pageList.add(i+1);
				}
			}else if(currentPage > (pageTotal - showPageSize/2)){
				for(int i= pageTotal - showPageSize; i < pageTotal; i++){
					pageList.add(i+1);
				}
			}else{
				int index = showPageSize/2;
				for(int i= currentPage - index; i < currentPage + index; i++){
					pageList.add(i+1);
				}
			}
		}
		logger.debug("pageList",pageList);
		return pageList;
	}


	
	
}
