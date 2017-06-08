package cn.gyyx.playwd.dao.playwd;

import java.util.List;

import cn.gyyx.playwd.beans.playwd.RoleBean;

public interface RoleBeanMapper {
    int deleteByPrimaryKey(Integer code);

    int insert(RoleBean record);

    int insertSelective(RoleBean record);

    RoleBean selectByPrimaryKey(Integer code);

    int updateByPrimaryKeySelective(RoleBean record);

    int updateByPrimaryKey(RoleBean record);

    RoleBean getDefaultRole(Integer userId);

    void deleteByUserId(Integer userId);

    List<RoleBean> selectRole(Integer userId);
}