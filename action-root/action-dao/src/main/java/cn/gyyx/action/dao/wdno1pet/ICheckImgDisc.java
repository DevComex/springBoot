package cn.gyyx.action.dao.wdno1pet;

import java.util.List;
import java.util.Map;

import cn.gyyx.action.beans.wdno1pet.CommentBean;
import cn.gyyx.action.beans.wdno1pet.ImageBean;
/**
 * 
 * @author MT
 *
 */
public interface ICheckImgDisc {
	//1.查找按要求的图片信息	 
	public List<ImageBean> checkImgInfo( Map map );    
	// 2.查找按要求的评论信息 
	public List<CommentBean> checkDiscussInfo( Map map );  
	//3.查找所有按要求的图片信息个数	
	public int checkImgPageNum( Map map );  
	//4.通过图片审核
	public void passImgInfo( Map map );   
	//5.通过imgCode查询相应图片的信息	
	public ImageBean selectImgByImgCode( int imgCode ); 
	//6.取消通过的图片审核，更改为审核审核未通过  
	public void cancelPassedImg ( int imgCode );
	//7.查询出按要求的评论的信息个数
	public int selectDisPageNum( String commentStatus ); 
	//8.通过评论审核
	public void passDisInfo( int commentCode );  
	//9.取消通过的评论审核，更改为审核未通过
	public void cancelPassedDis( int commentCode);    
	public void passAllImgInfo(List list);
	
	
	/*************************12-23修改  ********************************/
	public List<ImageBean> selectImgInfoSta( Map<String,Object> map );
	public int checkImgPageNum2 ( String imgStatus );
	/*************************12-23修改  结束***********************************/
	
	/******************************暂无用******************************************/
	public List<ImageBean> checkImgAll(  );   
	public void passImgI( Map map ) ;
	/******************************暂无用******************************************/
}
