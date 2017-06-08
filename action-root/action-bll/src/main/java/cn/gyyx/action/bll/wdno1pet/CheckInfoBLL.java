package cn.gyyx.action.bll.wdno1pet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.gyyx.action.beans.wdno1pet.CommentBean;
import cn.gyyx.action.beans.wdno1pet.ImageBean;
import cn.gyyx.action.beans.wdno1pet.WDNo1PetInfoBean;
import cn.gyyx.action.dao.wdno1pet.CheckInfoDAO;



public class CheckInfoBLL {
	private Map<String,Object> map = new HashMap<>();    //用来传递数据库的输入参数
	private CheckInfoDAO checkInfo = new CheckInfoDAO();
	private List<ImageBean> listImg = new ArrayList<>();    //接收查找出来的图片信息
	private List<CommentBean> listDis= new ArrayList<>();    //接收查找出来的评论信息
	/**
	 * 带参数的构造函数，构造要传入的参数map
	 * @param petInfoBean
	 * @param imgStatus
	 * @param nowPage
	 */
	public CheckInfoBLL(WDNo1PetInfoBean petInfoBean,String imgStatus,int nowPage) {
		
		map.put("petInfoBean", petInfoBean);
		map.put("imgStatusOld", imgStatus);
		map.put("nowPage", nowPage);
	}
	public CheckInfoBLL(String commentStatus,int nowPage){	
		map.put("commentStatus", commentStatus);
		map.put("nowPage",nowPage);
	}
	public CheckInfoBLL() {
		
	}
	/**
	 *  1. 
	 * 通过服务器号 ，大区号，是否有审核来查询，图片信息
	 * @author MT
	 * @return
	 */
	public List<ImageBean> checkImgInfo( ) {
		listImg = checkInfo.checkImgInfo(map);
		return listImg;
	}
	/**
	 * 3.
	 * 查询总共有多少个图片信息
	 * @author MT
	 * @return
	 */
	public int checkImgPageNum() {
		int pageNum = checkInfo.checkImgPageNum(map);
		return pageNum;
	}
	/**
	 * 2.
	 * 通过评论状态分页查询评论信息
	 * @author MT
	 * @param commentStatus
	 * @return
	 */
	public List<CommentBean> checkDisInfo( ) {
		listDis = checkInfo.checkDiscussInfo(map);
		return listDis;
	}
	/**
	 * 4.
	 * 通过图片审核
	 * @author MT
	 * @param imgCode
	 * @param realUrl
	 */
	public void passImgInfo (String imgCode,String realUrl) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("imgCode", imgCode);
		map.put("realUrl", realUrl);
		
		checkInfo.passImgInfo(map);
	}
	/**
	 * 5.
	 * 通过imgCode查询相应图片的信息
	 * @author MT
	 */
	public ImageBean selectByImgCode ( String imgCode ) {
		ImageBean imgInfo = checkInfo.selectImgByImgCode(Integer.parseInt(imgCode));
		return imgInfo;
	}
	/**
	 * 6.
	 *  取消通过的图片审核，更改为未审核
	 *  @author MT
	 */
	public void cancelPassedImg(String imgCode) {
		checkInfo.cancelPassedImg(Integer.parseInt(imgCode));
	}
	/**
	 * 7.
	 * 查询出按要求总共有多少评论
	 * @author MT
	 */
	public int selectDisPageNum( String commentStatus ) {
		int disNum = checkInfo.selectDisPageNum(commentStatus);
		return disNum;
	}
	/**
	 * 8.
	 * 通过评论审核
	 * @author MT
	 */
	public void passDisInfo ( String commentCode){
		checkInfo.passDisInfo(Integer.parseInt(commentCode));
	}
	/**
	 * 9.
	 * 取消通过的审核，更改为审核未通过
	 * @author MT
	 */
	public void cancelPassedDis( String commentCode ) {
		checkInfo.cancelPassedDis(Integer.parseInt(commentCode));
	}
	
	/**********************************12-23**********************************************/
	
	public List<ImageBean> selectImgInfoSta (String  imgStatus,String nowPage ) {
		Map<String,Object> map = new HashMap<>();
		map.put("imgStatus", imgStatus);
		map.put("nowPage", Integer.parseInt(nowPage));
		listImg = checkInfo.selectImgInfoSta(map);
		return listImg;
	}
	
	public int checkImgPageNum2 ( String imgStatus ){
		int maxPage = checkInfo.checkImgPageNum2(imgStatus);
		return maxPage;
	}
	/******************************待增加功能************************/
	public void passAllImgInfo ( String boxValue ) {
		List listStr = new ArrayList();
		String []str = boxValue.split(",");
		for(int i=0;i<str.length;i++){
			listStr.add(Integer.parseInt(str[i]));
		}
	}
}
