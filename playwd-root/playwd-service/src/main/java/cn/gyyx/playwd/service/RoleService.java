package cn.gyyx.playwd.service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gyyx.playwd.agent.InterfaceQiBaoAgent;
import cn.gyyx.playwd.beans.common.ResultBean;
import cn.gyyx.playwd.beans.playwd.RoleBean;
import cn.gyyx.playwd.bll.RoleBll;

/**
 * 
 * <p>
 * 角色信息Service
 * </p>
 * 
 * @author lihu
 * @since 0.0.1
 */
@Service
public class RoleService {
    @Autowired
    private RoleBll roleBll;

    @Autowired
    private InterfaceQiBaoAgent interfaceQiBaoAgent;

    /**
     * 
     * <p>
     * 角色信息同步
     * </p>
     *
     * @action lihu 2017年4月24日 下午8:02:09 描述
     *
     * @param user
     * @param netId
     * @param serverId
     * @param serverName
     * @param qq
     *            void
     */
    public ResultBean instertRole(Integer userId, String account, Integer netId,
            Integer serverId, String serverName, String qq) {
        RoleBean role = roleBll.getDefaultRole(userId);
        List<RoleBean> list = interfaceQiBaoAgent.isActive(account, serverId);
        if (list == null) {
            return new ResultBean(false, "没有角色");
        }
        if (role != null) { // 之前同步数据存在,清除
            roleBll.deleteRole(role.getUserId());
        }
        // 比较器
        ComparatorUser comparator = new ComparatorUser();
        Collections.sort(list, comparator);
        // 赋予最后一个对象 默认
        list.get(list.size() - 1).setIsDefault(true);
        for (RoleBean roleBean : list) {
            roleBean.setUserId(userId);
            roleBean.setAccount(account);
            roleBean.setNetId(netId);
            roleBean.setServerId(serverId);
            roleBean.setServerName(serverName);
            roleBean.setQq(qq);
            roleBean.setUpdateTime(new Date());
            roleBll.instertRole(roleBean);
        }
        return new ResultBean(true, "同步角色成功");
    }

    /**
     * 
     * <p>
     * 获取我的角色列表
     * </p>
     *
     * @action lihu 2017年4月25日 下午1:14:54 描述
     *
     * @param userId
     * @return ResultBean<List<RoleBean>>
     */
    public ResultBean<List<RoleBean>> myRole(Integer userId) {
        List<RoleBean> list = roleBll.selectRole(userId);
        if (list == null || list.isEmpty()) {
            return new ResultBean<>(false, "获取我的角色列表为空", null);
        }
        return new ResultBean<>(true, "获取我的角色列表成功", list);
    }

    /**
     * 
     * <p>
     * 修改默认角色
     * </p>
     *
     * @action lihu 2017年4月25日 下午1:22:17 描述
     *
     * @param code
     *            void
     * @param userId
     */
    public void editRole(Integer code, Integer userId) {
        RoleBean role = roleBll.getDefaultRole(userId);
        if (role != null) {
            // 旧友默认角色取消
            role.setIsDefault(false);
            roleBll.cancleRoleDefault(role);
            // 修改code角色为默认
            RoleBean newRole = new RoleBean();
            newRole.setCode(code);
            newRole.setIsDefault(true);
            roleBll.editRole(newRole);
        }
    }

    public RoleBean defaultRole(Integer userId) {
        return roleBll.getDefaultRole(userId);
    }

    public void setRoleBll(RoleBll roleBll) {
        this.roleBll = roleBll;
    }

    public void setInterfaceQiBaoAgent(InterfaceQiBaoAgent interfaceQiBaoAgent) {
        this.interfaceQiBaoAgent = interfaceQiBaoAgent;
    }

}
