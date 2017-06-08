/**
 * --------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：范佳琪 
 * 联系方式：fanjiaqi@gyyx.cn 
 * 创建时间：2015年12月17日
 * 版本号：v1.0
 * 本类主要用途叙述：章节信息的数据库交互接口
 */
package cn.gyyx.action.dao.chapter;

import java.util.List;

import cn.gyyx.action.beans.chapter.ChapterBean;

public interface IChapterMapper {

	/**
	 * @Title: insertChapter
	 * @Description: TODO 添加章节信息
	 * @param @return    
	 * @return int    
	 * @throws
	 */
	public int insertChapter(ChapterBean chapterBean);
	
	/**
	 * @Title: getChapterByCode
	 * @Description: TODO 通过主键查找章节信息
	 * @param @return    
	 * @return ChapterBean    
	 * @throws
	 */
	public ChapterBean getChapterByCode(int chapterCode);
	
	/**
	 * @Title: getAllChapterForPage
	 * @Description: TODO 分页获取章节目录
	 * @param @return    
	 * @return List<ChapterBean>    
	 * @throws
	 */
	public List<ChapterBean> getAllChapterForPage(Integer pageSize,Integer currentPage);

	/**
	 * @Title: getCount
	 * @Description: TODO 获取表中记录总数
	 * @param @return    
	 * @return int    
	 * @throws
	 */
	public int getCount();
	
	/**
	 * @Title: getChapterCode
	 * @Description: TODO 根据章节的orderNum获取章节Code
	 * @param @param orderNum
	 * @param @return    
	 * @return int    
	 * @throws
	 */
	public int getChapterCode(int orderNum);
}
