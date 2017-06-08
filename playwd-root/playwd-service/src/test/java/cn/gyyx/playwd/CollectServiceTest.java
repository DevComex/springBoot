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
import cn.gyyx.playwd.service.UserService;

/**
 * 用户
 * 
 * @author lihu
 *
 */
public class CollectServiceTest {

    private CollectDao collectdao;
    private ArticleBll articleBll;
    private TimeLineBll timeLineBll;
    private CollectService collectService;

    @BeforeMethod
    public void setMockup() {
        collectService = new CollectService();
        collectdao = mock(CollectDao.class);
        articleBll = mock(ArticleBll.class);
        timeLineBll = mock(TimeLineBll.class);

        collectService.setArticleBll(articleBll);
        collectService.setCollectdao(collectdao);
        collectService.setTimeLineBll(timeLineBll);
    }

    @Test(description = " 根据用户id 获取用户收藏")
    public void getCollectByUserId_whenUserId_thendata()
            throws Exception {
        Integer userId = 1;
        int startSize=1;
        int endSize=5;
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId",userId);
        map.put("startSize",startSize);
        map.put("endSize",endSize);
        List<Collect> list= new ArrayList<>();
        list.add(getCollect(1));
        list.add(getCollect(2));
        when(collectdao.getCollectByUserId(map)).thenReturn(list);

        // 比较返回值是否相等
        List<Collect> collectByUserId = collectService.getCollectByUserId(  userId,   startSize,   endSize);
        assertThat(collectByUserId);
        ;

    }
    @Test(description = " 根据用户id 获取用户收藏数量")
    public void getCollectCountByUserId_whenUserId_thendata()
            throws Exception {
        Integer userId = 1;
        int startSize=1;
        int endSize=5;
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId",userId);
        map.put("startSize",startSize);
        map.put("endSize",endSize);
        List<Collect> list= new ArrayList<>();
        list.add(getCollect(1));
        list.add(getCollect(2));
        when(collectdao.getCollectCountByUserId(userId)).thenReturn(2);
        
        // 比较返回值是否相等
        int count = collectService.getCollectCountByUserId(userId);
        assertEquals(count, 2);
        ;
        
    }
    @Test(description = " 根据用户id,图文主键, 取消收藏")
    public void cancelCollect_whenUserId_thendata()
            throws Exception {
        Integer userId = 1;
        Integer code= 1; 
        String account= "account";
       
        Collect collect = getCollect(1);
        when(articleBll.getArticle(code)).thenReturn(new ArticleBean());
        
        // 比较返回值是否相等
         
        verify(collectdao).updateStatusByCode(code);
        
    }

    private Collect getCollect(int i) {
        Collect collect = new Collect();
        collect.setCode(i);
        return collect;
    }
    
    
}
