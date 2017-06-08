package cn.gyyx.action.dao.wdno1pet;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.wdno1pet.ImageBean;

public interface IImageDAO {
	public ImageBean getImageByImgCode(@Param("imgCode")int imgCode);
	public int uploadImage(@Param("img")ImageBean img);
	/**
	 * 查出所有具有抽奖资格的用户主键
	 * @return  具有资格的用户主键
	 */
	public List<Integer> selectQualification();
}
