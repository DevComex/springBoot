package cn.gyyx.wd.wanjia.cartoon.dao;

import java.util.List;

import cn.gyyx.wd.wanjia.cartoon.beans.WanwdUser;

public interface WanwdUserMapper {
	int deleteByPrimaryKey(Integer code);

	int insert(WanwdUser record);

	int insertSelective(WanwdUser record);

	WanwdUser selectByPrimaryKey(Integer code);

	int updateByPrimaryKeySelective(WanwdUser record);

	int updateByPrimaryKey(WanwdUser record);

	/**
	 * 通过UserId查询表中用户信息
	 * 
	 * @param userId
	 * @return
	 */
	List<WanwdUser> selectByUserId(Integer userId);
}