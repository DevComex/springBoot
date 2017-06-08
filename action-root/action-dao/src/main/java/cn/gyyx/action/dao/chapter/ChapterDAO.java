/**
 * --------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：范佳琪 
 * 联系方式：fanjiaqi@gyyx.cn 
 * 创建时间：2015年12月17日
 * 版本号：v1.0
 * 本类主要用途叙述：章节的数据库DAO
 */

package cn.gyyx.action.dao.chapter;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.chapter.ChapterBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class ChapterDAO implements IChapterMapper{

	private static final Logger logger = GYYXLoggerFactory
			.getLogger(ChapterDAO.class);
	
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	
	/**
	 * 添加章节信息
	 */
	@Override
	public int insertChapter(ChapterBean chapterBean) {
		int result = 0;
		SqlSession session = getSession();
		try {
			IChapterMapper chapterMapper = session.getMapper(IChapterMapper.class);
			result = chapterMapper.insertChapter(chapterBean);
			session.commit();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return result;
	}

	/**
	 * 通过主键查找章节信息
	 */
	@Override
	public ChapterBean getChapterByCode(int chapterCode) {
		ChapterBean chapterBean = null;
		SqlSession session = getSession();
		try {
			IChapterMapper chapterMapper = session.getMapper(IChapterMapper.class);
			chapterBean = chapterMapper.getChapterByCode(chapterCode);
		} catch (Exception e) {
			logger.warn(e.toString());
		}finally{
			session.close();
		}
		return chapterBean;
	}

	/**
	 * 分页获取章节目录
	 */
	@Override
	public List<ChapterBean> getAllChapterForPage(Integer pageSize,Integer currentPage) {
		List<ChapterBean> chapterBeans = null;
		SqlSession session = getSession();
		try {
			IChapterMapper chapterMapper = session.getMapper(IChapterMapper.class);
			chapterBeans = chapterMapper.getAllChapterForPage(pageSize, currentPage);
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn(e.toString());
		}finally{
			session.close();
		}            
		return chapterBeans;
	}

	/**
	 * 获取表中记录总数
	 */
	@Override
	public int getCount() {
		int count = 0;
		SqlSession session = getSession();
		try {
			IChapterMapper chapterMapper = session.getMapper(IChapterMapper.class);
			count = chapterMapper.getCount();
		} catch (Exception e) {
			logger.warn(e.toString());
		}finally{
			session.close();
		}   
		return count;
	}

	/**
	 * 根据章节的orderNum获取章节Code
	 */
	@Override
	public int getChapterCode(int orderNum) {
		int chapterCode = 0;
		SqlSession session = getSession();
		try {
			IChapterMapper chapterMapper = session.getMapper(IChapterMapper.class);
			chapterCode = chapterMapper.getChapterCode(orderNum);
		} catch (Exception e) {
			logger.warn(e.toString());
		}finally{
			session.close();
		}   
		return chapterCode;
	}
}
