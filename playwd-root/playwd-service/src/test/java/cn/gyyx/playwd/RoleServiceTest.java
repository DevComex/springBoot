/**
   * -------------------------------------------------------------------------
   * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
   * @版权所有：北京光宇在线科技有限责任公司
   * @项目名称：玩家天地
   * @作者：李鹄
   * @联系方式：lihu@gyyx.cn
   * @创建时间：2017年4月19日下午2:31:27
   * @版本号：1.0.0
   *-------------------------------------------------------------------------
   */
package cn.gyyx.playwd;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.AssertJUnit.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import cn.gyyx.playwd.agent.InterfaceQiBaoAgent;
import cn.gyyx.playwd.beans.playwd.ArticleBean;
import cn.gyyx.playwd.beans.playwd.Collect;
import cn.gyyx.playwd.beans.playwd.RoleBean;
import cn.gyyx.playwd.beans.playwd.UserBean;
import cn.gyyx.playwd.bll.ArticleBll;
import cn.gyyx.playwd.bll.RoleBll;
import cn.gyyx.playwd.bll.TimeLineBll;
import cn.gyyx.playwd.bll.UserBll;
import cn.gyyx.playwd.bll.UserTitleBll;
import cn.gyyx.playwd.dao.playwd.CollectDao;
import cn.gyyx.playwd.service.CollectService;
import cn.gyyx.playwd.service.RoleService;
import cn.gyyx.playwd.service.UserService;

/**
 * 用户
 * 
 * @author lihu
 *
 */
public class RoleServiceTest {

    private RoleBll roleBll;
    private InterfaceQiBaoAgent interfaceQiBaoAgent;
    private RoleService roleService;

    @BeforeMethod
    public void setMockup() {
        roleService = new RoleService();
        roleBll = mock(RoleBll.class);
        interfaceQiBaoAgent = mock(InterfaceQiBaoAgent.class);

        roleService.setRoleBll(roleBll);
        roleService.setInterfaceQiBaoAgent(interfaceQiBaoAgent);
        
    }

    @Test(description = "添加角色,同步角色")
    public void instertRole_whenParm_thendata()
            throws Exception {
        Integer userId = 1;
        String account="account";
        int serverId=5;
        RoleBean roleBean =getRole(1);
       
        when(roleBll.getDefaultRole(userId)).thenReturn(null);
        List<RoleBean> list = new ArrayList<>();
        list.add(getRole(1));
        list.add(getRole(2));
                when(interfaceQiBaoAgent.isActive(account, serverId)).thenReturn(list);
        // 比较返回值是否相等
        //TODO

    }

    private RoleBean getRole(int i) {
        RoleBean roleBean = new RoleBean();
        roleBean.setAccount("account"+i);
        return roleBean;
    }
  
    
    
}
