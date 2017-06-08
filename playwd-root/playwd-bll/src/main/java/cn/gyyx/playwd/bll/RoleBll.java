package cn.gyyx.playwd.bll;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.gyyx.playwd.beans.playwd.RoleBean;
import cn.gyyx.playwd.dao.playwd.RoleBeanMapper;

@Component
public class RoleBll {
    @Autowired
    RoleBeanMapper roleMapper;

    public RoleBean getDefaultRole(Integer userId) {
        RoleBean bean = roleMapper.getDefaultRole(userId);
        if (bean != null && bean.getCode() != null) {
            return bean;
        }
        return null;
    }

    public boolean instertRole(RoleBean roleBean) {
        return roleMapper.insertSelective(roleBean) > 0;
    }

    public void deleteRole(Integer userId) {
        roleMapper.deleteByUserId(userId);
    }

    public List<RoleBean> selectRole(Integer userId) {
        return roleMapper.selectRole(userId);
    }

    public void cancleRoleDefault(RoleBean role) {
        roleMapper.updateByPrimaryKeySelective(role);
    }

    public void editRole(RoleBean newRole) {
        roleMapper.updateByPrimaryKeySelective(newRole);
    }

}