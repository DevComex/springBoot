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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.AssertJUnit.assertEquals;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import cn.gyyx.playwd.beans.playwd.RoleBean;
import cn.gyyx.playwd.beans.playwd.UserBean;
import cn.gyyx.playwd.bll.ArticleBll;
import cn.gyyx.playwd.bll.RoleBll;
import cn.gyyx.playwd.bll.UserBll;
import cn.gyyx.playwd.bll.UserTitleBll;
import cn.gyyx.playwd.service.UserService;

/**
 * 用户
 * @author lihu
 *
 */
public class UserServiceTest {

    private  UserBll userBll  ;
    private  UserService userService  ;
    
    private  UserTitleBll userTitleBll;
    
    private ArticleBll articleBll;
    private RoleBll roleBll;
	
	@BeforeMethod
    public void setMockup() {
	        userService=new UserService();
	        userBll=mock(UserBll.class);
	        userTitleBll=mock(UserTitleBll.class);
	        articleBll = mock(ArticleBll.class);
	        roleBll = mock(RoleBll.class);
	        
	        userService.setArticleBll(articleBll);
	        userService.setRoleBll(roleBll);
	        userService.setUserBll(userBll);
	        userService.setUserTitleBll(userTitleBll);
	}
	@Test(description=" 根据评论id,获取回复列表,返回数据")
	public void getBrowsePageReplyList_whenCommentId_thendata() throws Exception {
	        int userId=1;
	        UserBean userBean = new UserBean();
	        userBean.setUserId(userId);
	        RoleBean role = new RoleBean();
	        role.setPicture("icon");
	       
		when(roleBll.getDefaultRole(userId)).thenReturn(role);
		when(userBll.getByUserId(userId)).thenReturn(userBean);
		
		//比较返回值是否相等
		UserBean userInfoAll = userService.getUserInfoAll(userId);
                assertEquals(userInfoAll.getUserId().intValue(), userId);;
		 
	}
	 
	 
}
