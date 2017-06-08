package cn.gyyx.action.bll.wdno1pet;

import cn.gyyx.action.beans.wdno1pet.ImageBean;
import cn.gyyx.action.dao.wdno1pet.ImageDAO;

public class ImageBLL {
	private static ImageDAO imgDao;
	static{
		imgDao = new ImageDAO();
	}
	public int uploadImage(ImageBean img){
		return imgDao.uploadImage(img);
	}
	
	public ImageBean getImageByCode(int imgCode){
		return imgDao.getImageByImgCode(imgCode);
	}
}
