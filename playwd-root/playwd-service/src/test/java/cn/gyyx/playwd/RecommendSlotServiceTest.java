 /**
    * -------------------------------------------------------------------------
    * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
    * @版权所有：北京光宇在线科技有限责任公司
    * @项目名称：玩家天地
    * @作者：李杜迪
    * @联系方式：lidudi@gyyx.cn
    * @创建时间：2017年4月27日下午3:11:49
    * @版本号：1.0.0
    *-------------------------------------------------------------------------
    */
package cn.gyyx.playwd;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;

import cn.gyyx.playwd.beans.common.PageBean;
import cn.gyyx.playwd.beans.common.ResultBean;
import cn.gyyx.playwd.beans.playwd.ArticleBean;
import cn.gyyx.playwd.beans.playwd.ArticleBean.State;
import cn.gyyx.playwd.beans.playwd.CategoryBean;
import cn.gyyx.playwd.beans.playwd.CategoryBean.CategoryType;
import cn.gyyx.playwd.beans.playwd.ItemBean;
import cn.gyyx.playwd.beans.playwd.MessageBean;
import cn.gyyx.playwd.beans.playwd.PrizeBean;
import cn.gyyx.playwd.beans.playwd.RecommendContentBean;
import cn.gyyx.playwd.beans.playwd.RecommendSlotBean;
import cn.gyyx.playwd.beans.playwd.UserBean;
import cn.gyyx.playwd.bll.ArticleBll;
import cn.gyyx.playwd.bll.CategoryBll;
import cn.gyyx.playwd.bll.ItemBll;
import cn.gyyx.playwd.bll.MessageBll;
import cn.gyyx.playwd.bll.PrizeBll;
import cn.gyyx.playwd.bll.RecommendBll;
import cn.gyyx.playwd.bll.RecommendContentBll;
import cn.gyyx.playwd.bll.RecommendSlotBll;
import cn.gyyx.playwd.bll.ReviewLogBll;
import cn.gyyx.playwd.bll.RmbOrderBll;
import cn.gyyx.playwd.bll.UserBll;
import cn.gyyx.playwd.bll.UserTitleBll;
import cn.gyyx.playwd.bll.WdgiftOrderBll;
import cn.gyyx.playwd.service.RecommendService;
import cn.gyyx.playwd.service.RecommendSlotService;

/**
 * 推荐管理
 * @author lihu
 *
 */
public class RecommendSlotServiceTest {

    /**
     *推荐位管理 
     */
    private RecommendSlotService recommendSlotService;
    private RecommendSlotBll recommendSlotBll;
	@Before
	public void setMockup() {
	    recommendSlotService = new RecommendSlotService();
		recommendSlotBll=mock(RecommendSlotBll.class);
		recommendSlotService.setRecommendSlotBll(recommendSlotBll);
	}
	
	@Test
	public void getRecommendSlotList_whenTypeIsNovel_thenSuccesss() throws Exception {

		String contentType="novel";
		
		Integer resultExpect=1;
		
		List<RecommendSlotBean> list = new  ArrayList<>();
		RecommendSlotBean recommendSlotBean = new RecommendSlotBean();
		recommendSlotBean.setCode(1);
		list.add(recommendSlotBean);
		when(recommendSlotBll.getRecommendSlotList(contentType)).thenReturn(list);

		list=recommendSlotService.getRecommendSlotList(contentType);
		assertEquals( list.get(0).getCode(),resultExpect);
	}
	@Test
        public void getRecommendSlotList_whenTypeIsArticle_thenSuccesss() throws Exception {

                String contentType="article";
                
                Integer resultExpect=1;
                
                List<RecommendSlotBean> list = new  ArrayList<>();
                RecommendSlotBean recommendSlotBean = new RecommendSlotBean();
                recommendSlotBean.setCode(1);
                list.add(recommendSlotBean);
                when(recommendSlotBll.getRecommendSlotList(contentType)).thenReturn(list);

                list=recommendSlotService.getRecommendSlotList(contentType);
                assertEquals( list.get(0).getCode(),resultExpect);
        }
	@Test
	public void getRecommendSlotList_whenTypeIsAll_thenSuccesss() throws Exception {
	    
	    String contentType="all";
	    
	    Integer resultExpect=1;
	    
	    List<RecommendSlotBean> list = new  ArrayList<>();
	    RecommendSlotBean recommendSlotBean = new RecommendSlotBean();
	    recommendSlotBean.setCode(1);
	    list.add(recommendSlotBean);
	    when(recommendSlotBll.getRecommendSlotGroupList()).thenReturn(list);
	    
	    list=recommendSlotService.getRecommendSlotList(contentType);
	    assertEquals( list.get(0).getCode(),resultExpect);
	}
}
