package cn.gyyx.action.dao.wdno1pet;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.wdno1pet.ImageBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

public class ImageDAO implements IImageDAO{

	@Override
	public ImageBean getImageByImgCode(int imgCode) {
		try(SqlSession sqlsession = MyBatisConnectionFactory.getSqlActionDBV2SessionFactory().openSession()){
			IImageDAO imgDao = sqlsession.getMapper(IImageDAO.class);
			return imgDao.getImageByImgCode(imgCode);
		}
	}

	@Override
	public int uploadImage(ImageBean img) {
		try(SqlSession sqlsession = MyBatisConnectionFactory.getSqlActionDBV2SessionFactory().openSession()){
			IImageDAO imgDao = sqlsession.getMapper(IImageDAO.class);
			imgDao.uploadImage(img);
			//上传图片实体
			sqlsession.commit();
			
			return img.getImgCode();
		}
	}

	/**
	 * 查询所有资格
	 */
	@Override
	public List<Integer> selectQualification() {
		// TODO Auto-generated method stub
		try(SqlSession sqlsession = MyBatisConnectionFactory.getSqlActionDBV2SessionFactory().openSession()){
			List<Integer> list=new ArrayList<Integer>();
			IImageDAO imgDao = sqlsession.getMapper(IImageDAO.class);
	           list=imgDao.selectQualification();
			//上传图片实体
			sqlsession.commit();
			
			return list;
		}
		
		
	}
	
}
