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

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import cn.gyyx.playwd.agent.WDGameAgent;
import cn.gyyx.playwd.beans.common.ResultBean;
import cn.gyyx.playwd.beans.playwd.ArticleBean;
import cn.gyyx.playwd.beans.playwd.RoleBean;
import cn.gyyx.playwd.beans.playwd.UserBean;
import cn.gyyx.playwd.bll.ArticleBll;
import cn.gyyx.playwd.bll.RoleBll;
import cn.gyyx.playwd.bll.UserBll;
import cn.gyyx.playwd.bll.UserTitleBll;
import cn.gyyx.playwd.service.ArticleUploadService;
import cn.gyyx.playwd.service.UserService;

/**
 * 用户
 * @author lihu
 *
 */
public class ArticleUploadServiceTest {

    ArticleBll articleBll;
    ArticleUploadService articleUploadService;
	@BeforeMethod
    public void setMockup() {
        articleUploadService = new ArticleUploadService();
        articleBll = mock(ArticleBll.class);

        articleUploadService.setArticleBll(articleBll);
    }
	@Test(description=" 添加图文,标题已存在,返回数据")
	public void instertArticle_whenCommentId_thendata() throws Exception {
	        int userId=1;
	        ArticleBean articleBean = new ArticleBean();
	        articleBean.setTitle("title");
		when(articleBll.findCountByTitle(articleBean.getTitle())).thenReturn(true);
		
		//比较返回值是否相等
		ResultBean<Object> resultBean = articleUploadService.instertArticle(articleBean, "account", userId);
                assertEquals(resultBean.getIsSuccess(), false);
		 
	}
	 
	 
	@Test(description=" 添加图文,标题已存在,返回数据")
	public void instertArticle_whenfalse_thendata() throws Exception {
	    int userId=1;
	    ArticleBean articleBean = new ArticleBean();
	    articleBean.setTitle("title");
	    when(articleBll.findCountByTitle(articleBean.getTitle())).thenReturn(false);
	    
	    //比较返回值是否相等
	    ResultBean<Object> resultBean = articleUploadService.instertArticle(articleBean, "account", userId);
	    assertEquals(resultBean.getIsSuccess(), true);
	    
	}
	
	@Test(description=" 检测标题是否,返回数据")
	public void checktitle_whenfalse_thendata() throws Exception {
	    String title="title";
	    when(articleBll.findCountByTitle(title)).thenReturn(false);
	    
	    //比较返回值是否相等
	    boolean checktitle = articleUploadService.checktitle(title);
	    assertEquals(checktitle, false);
	    
	}
	
	
	@Test(description=" 检测标题是否,返回数据")
	public void checktitle_whentrue_thendata() throws Exception {
	    String title="title";
	    when(articleBll.findCountByTitle(title)).thenReturn(true);
	    
	    //比较返回值是否相等
	    boolean checktitle = articleUploadService.checktitle(title);
	    assertEquals(checktitle, true);
	    
	}
	
	
}
