/**
 * --------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：范佳琪 
 * 联系方式：fanjiaqi@gyyx.cn 
 * 创建时间：2015年12月17日
 * 版本号：v1.0
 * 本类主要用途叙述：对章节进行操作
 */

package cn.gyyx.action.bll.chapter;

import java.util.List;

import org.slf4j.Logger;

import cn.gyyx.action.beans.chapter.ChapterBean;
import cn.gyyx.action.dao.chapter.ChapterDAO;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class ChapterBLL {

	private ChapterDAO chapterDAO = new ChapterDAO();
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(ChapterBLL.class);
	
	/**
	 * @Title: insertChapter
	 * @Description: TODO 添加章节信息
	 * @param @param chapterBean
	 * @param @return    
	 * @return int    
	 * @throws
	 */
	public int insertChapter(ChapterBean chapterBean){
		logger.debug("chapterBean", chapterBean);
		return chapterDAO.insertChapter(chapterBean);
	}
	
	/**
	 * @Title: getChapterByCode
	 * @Description: TODO 通过主键获取章节信息
	 * @param @param chapterCode
	 * @param @return    
	 * @return ChapterBean    
	 * @throws
	 */
	public ChapterBean getChapterByCode(int chapterCode){
		logger.debug("chapterCode",chapterCode);
		return chapterDAO.getChapterByCode(chapterCode);
	}
	
	/**
	 * @Title: getAllChapterForPage
	 * @Description: TODO 分页获取章节目录
	 * @param @param pageSize
	 * @param @param currentPage
	 * @param @return    
	 * @return List<ChapterBean>    
	 * @throws
	 */
	public List<ChapterBean> getAllChapterForPage(Integer pageSize,Integer currentPage){
		logger.debug("pageSize",pageSize);
		logger.debug("currentPage",currentPage);
		return chapterDAO.getAllChapterForPage(pageSize, currentPage);
	}
	
	/**
	 * @Title: getCount
	 * @Description: TODO 获取表中记录总数
	 * @param @return    
	 * @return int    
	 * @throws
	 */
	public int getCount(){
		return chapterDAO.getCount();
	}
	
	/**
	 * 
	 * @Title: getChapterCode
	 * @Description: TODO 根据orderNum获取章节Code
	 * @param @param orderNum
	 * @param @return    
	 * @return int    
	 * @throws
	 */
	public int getChapterCode(int orderNum){
		logger.debug("orderNum",orderNum);
		return chapterDAO.getChapterCode(orderNum);
	}
}
