package cn.gyyx.action.dao.wdno1pet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.wdno1pet.CommentBean;
import cn.gyyx.action.beans.wdno1pet.ImageBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class CheckInfoDAO implements ICheckImgDisc {
	private static final Logger logger = GYYXLoggerFactory.getLogger(CheckInfoDAO.class);
	private int nowPage=1;
	
	//获取session对象
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	
	/**
	 *  1. 
	 * 通过服务器号 ，大区号，是否有审核来查询，图片信息
	 * @author MT
	 */
	@Override
	public List<ImageBean> checkImgInfo(Map map) {
		List<ImageBean> list =null;
		try (SqlSession sqlSession = getSession()) {
			ICheckImgDisc checkImg = sqlSession.getMapper(ICheckImgDisc.class);
			list = checkImg.checkImgInfo(map);
			sqlSession.close();
			
		}catch( Exception e ) {
			logger.info(e.toString());
		}
		return list;
	}
	/**
	 *  2.
	 * 通过评论状态分页查询评论信息
	 * @author MT
	 */
	@Override
	public List<CommentBean> checkDiscussInfo(Map map) {
		List<CommentBean> list=null;
		try {
			SqlSession session = getSession();
			ICheckImgDisc checkImg = session.getMapper(ICheckImgDisc.class);
			list = checkImg.checkDiscussInfo(map);
			session.close();
			
		}catch ( Exception e ) {
			logger.info(e.toString());
		}
		return list;
	}
	/**
	 *  3.
	 * 查询总共有多少个图片信息
	 * @author MT
	 */
	@Override
	public int checkImgPageNum(Map map) {
		int impPageNum = 0;
		try{
			SqlSession session =  getSession();
			ICheckImgDisc checkImg=session.getMapper(ICheckImgDisc.class);
			impPageNum = checkImg.checkImgPageNum(map);
			session.close();
			
		}catch( Exception e  ) {
			logger.info(e.toString());
		}
		return impPageNum;
	}
	/**
	 *  4.
	 * 通过图片审核
	 * @author MT
	 */
	@Override
	public void passImgInfo(Map map) {
		try{
			SqlSession session =  getSession();
			ICheckImgDisc checkImg=session.getMapper(ICheckImgDisc.class);
			checkImg.passImgInfo(map);
			session.commit();
			session.close();
		}catch( Exception e  ) {
			logger.info(e.toString());
		}
	}
	
	/**
	 *  5.
	 * 通过imgCode查询相应图片的信息
	 * @author MT
	 */
	@Override
	public ImageBean selectImgByImgCode(int imgCode) {
		ImageBean imgInfo = null;
		try{
			SqlSession session =  getSession();
			ICheckImgDisc checkImg=session.getMapper(ICheckImgDisc.class);
			imgInfo = checkImg.selectImgByImgCode(imgCode);
			session.close();
			
		}catch( Exception e  ) {
			logger.info(e.toString());
		}
		return imgInfo;
	}
	/**
	 *  6.
	 *  取消通过的审核，更改为未审核
	 *  @author MT
	 */
	@Override
	public void cancelPassedImg(int imgCode) {
		try{
			SqlSession session =  getSession();
			ICheckImgDisc checkImg=session.getMapper(ICheckImgDisc.class);
			checkImg.cancelPassedImg(imgCode);
			session.commit();
			session.close();
		}catch( Exception e  ) {
			logger.info(e.toString());
		}
	}
	/**
	 * 7.
	 * 查询总共有多少评论信息
	 * @author MT
	 */
	@Override
	public int selectDisPageNum(String commentStatus) {
		int disNum = 0;
		try{
			SqlSession session =  getSession();
			ICheckImgDisc checkImg=session.getMapper(ICheckImgDisc.class);
			disNum = checkImg.selectDisPageNum(commentStatus);
			session.close();
			
		}catch( Exception e  ) {
			logger.info(e.toString());
		}
		return disNum;
	}
	/**
	 * 8.
	 * 通过评论审核
	 * @author MT
	 */
	@Override
	public void passDisInfo(int commentCode) {
		try{
			SqlSession session =  getSession();
			ICheckImgDisc checkImg=session.getMapper(ICheckImgDisc.class);
			checkImg.passDisInfo(commentCode);
			session.commit();
			session.close();
		}catch( Exception e  ) {
			logger.info(e.toString());
		}
		
	}
	/**
	 * 9.
	 * 取消通过的审核，更改为审核未通过
	 * @author MT
	 */
	@Override
	public void cancelPassedDis(int commentCode) {
		try{
			SqlSession session =  getSession();
			ICheckImgDisc checkImg=session.getMapper(ICheckImgDisc.class);
			checkImg.cancelPassedDis(commentCode);
			session.commit();
			session.close();
		}catch( Exception e  ) {
			logger.info(e.toString());
		}
	}
	/**
	 * 通过全部选择
	 */
	@Override
	public void passAllImgInfo(List list) {
		try{
			SqlSession session =  getSession();
			ICheckImgDisc checkImg=session.getMapper(ICheckImgDisc.class);
			for( int i=0;i<list.size();i+=2 ) {
				List listT = new ArrayList();
				listT.add(list.get(i));
				listT.add(list.get(i+1));
				checkImg.passAllImgInfo(listT);
			}
			session.commit();
			session.close();
		}catch( Exception e ){
			logger.info(e.toString());
		}
		
	}
	
	/*************************12-23修改  增加只依靠imgStatus查询********************************/
	public List<ImageBean> selectImgInfoSta( Map<String,Object> map ){
		List<ImageBean> list = null;
		try{
			SqlSession session =  getSession();
			ICheckImgDisc checkImg=session.getMapper(ICheckImgDisc.class);
			list = checkImg.selectImgInfoSta(map);
		}catch( Exception e ) {
			logger.info(e.toString());
		}
		return list;
	}
	
	public int checkImgPageNum2 ( String imgStatus ){
		int maxPageNum=0;
		try{
			SqlSession session =  getSession();
			ICheckImgDisc checkImg=session.getMapper(ICheckImgDisc.class);
			maxPageNum = checkImg.checkImgPageNum2(imgStatus);
		}catch( Exception e ) {
			logger.info(e.toString());
		}
		return maxPageNum;
	}
	/*************************12-23修改  结束***********************************/
	
	
	
	@Override
	public List<ImageBean> checkImgAll() {
		// TODO Auto-generated method stub
		try(SqlSession sqlSession = getSession()) {
			ICheckImgDisc checkImg = sqlSession.getMapper(ICheckImgDisc.class);
			List<ImageBean> list = checkImg.checkImgAll();
			sqlSession.close();
			return list;
		}catch( Exception e ) {
			throw e;
		}

	}
	
	public void passImgI( Map map ) {
		try(SqlSession sqlSession = getSession()) {
			ICheckImgDisc checkImg = sqlSession.getMapper(ICheckImgDisc.class);
			checkImg.passImgI(map);
			sqlSession.commit();
			sqlSession.close();
		}catch( Exception e ) {
			throw e;
		}
	}

	
}
