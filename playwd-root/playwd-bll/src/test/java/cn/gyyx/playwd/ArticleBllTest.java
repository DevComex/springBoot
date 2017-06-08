 /**
    * -------------------------------------------------------------------------
    * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
    * @版权所有：北京光宇在线科技有限责任公司
    * @项目名称：玩家天地
    * @作者：李杜迪
    * @联系方式：lidudi@gyyx.cn
    * @创建时间：2017年4月13日下午3:25:19
    * @版本号：1.0.0
    *-------------------------------------------------------------------------
    */
package cn.gyyx.playwd;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import cn.gyyx.playwd.beans.playwd.ArticleBean;
import cn.gyyx.playwd.beans.playwd.ArticleBean.AuthorType;
import cn.gyyx.playwd.beans.playwd.ArticleBean.State;
import cn.gyyx.playwd.bll.ArticleBll;
import cn.gyyx.playwd.dao.playwd.ArticleDao;

public class ArticleBllTest {
	
	private ArticleDao articleDao;
	
	private ArticleBll articleBll;
	
	@Before
	public void setMockup() {
		articleBll=new ArticleBll();
		articleDao=mock(ArticleDao.class);
		articleBll.setArticleDao(articleDao);
	}
	
	
	//@Test(description="playwd-046_playwd-049_playwd-054_playwd-096_playwd-097_筛选出未审核的作品点击“预览”_页面跳转到文章发表页面")
	@Test
	public void getArticle_whenClickPreview_thenSuccess_playwd_046() throws Exception {		
		//初始化返回值
		ArticleBean bean=getArticleBean();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		when(articleBll.getArticle(1)).thenReturn(bean);
		
		ArticleBean resultBean=articleBll.getArticle(1);
		assertEquals(resultBean.getAccount(), bean.getAccount());
		assertEquals(resultBean.getPublishTimeStr(), df.format(bean.getPublishTime()));
	}

	//@Test(description="playwd-110_已被推荐的玩家上传文章点击“预览”_页面跳转到文章详情页面文章标题增加标红“荐”字、时间图标后显示审核通过时间")
	@Test
	public void getArticle_whenClickPreview_thenSuccess_playwd_110() throws Exception {		
		//初始化返回值
		ArticleBean bean=getArticleBean();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		bean.setStatus(State.recommended.Value());
		bean.setAuthorType(AuthorType.PLAYER.getIndex());
		when(articleBll.getArticle(1)).thenReturn(bean);
		
		ArticleBean resultBean=articleBll.getArticle(1);
		assertEquals(resultBean.getStatus(), bean.getStatus());
		assertEquals(resultBean.getPublishTimeStr(), df.format(bean.getPublishTime()));
		assertEquals(resultBean.getAuthorType(), bean.getAuthorType());
	}

	//@Test(description="playwd-111_已被推荐的官方上传文章点击“预览”_页面跳转到文章详情页面文章标题增加标红“荐”字、不显示审核通过时间")
	@Test
	public void getArticle_whenClickPreview_thenSuccess_playwd_111() throws Exception {		
		//初始化返回值
		ArticleBean bean=getArticleBean();
		bean.setStatus(State.recommended.Value());
		bean.setAuthorType(AuthorType.OFFICIAL.getIndex());
		when(articleBll.getArticle(1)).thenReturn(bean);
		
		ArticleBean resultBean=articleBll.getArticle(1);
		assertEquals(resultBean.getStatus(), bean.getStatus());
		assertEquals(resultBean.getAuthorType(), bean.getAuthorType());
	}
	
	//@Test(description="playwd-112_审核不通过的玩家上传文章点击“预览”_文章标题 时间图标后显示审核通过时间")
	@Test
	public void getArticle_whenClickPreview_thenSuccess_playwd_112() throws Exception {		
		//初始化返回值
		ArticleBean bean=getArticleBean();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		bean.setStatus(State.failed.Value());
		bean.setAuthorType(AuthorType.PLAYER.getIndex());
		when(articleBll.getArticle(1)).thenReturn(bean);
		
		ArticleBean resultBean=articleBll.getArticle(1);
		assertEquals(resultBean.getStatus(), bean.getStatus());
		assertEquals(resultBean.getPublishTimeStr(), df.format(bean.getPublishTime()));
		assertEquals(resultBean.getAuthorType(), bean.getAuthorType());
	}

	@Test
	public void getArticle_whenArticleInfoIsNull_thenSuccess() throws Exception {		

		when(articleBll.getArticle(1)).thenReturn(null);
		
		ArticleBean resultBean=articleBll.getArticle(1);
		assertEquals(resultBean,null);
	}

	@Test
	public void getArticle_whenPublishTimeIsNull_thenSuccess() throws Exception {		
		//初始化返回值
		ArticleBean bean=getArticleBean();
		bean.setPublishTime(null);
		when(articleBll.getArticle(1)).thenReturn(bean);
		
		ArticleBean resultBean=articleBll.getArticle(1);
		assertEquals(resultBean.getPublishTime(), null);
	}
	
	/**
	 * 模拟ArticleBean
	 * @return
	 */
	private ArticleBean getArticleBean() {
		
		Date date=new Date();
		
		//初始化返回值
		ArticleBean bean=new ArticleBean();
		bean.setCode(1);
		bean.setAccount("aaaa");
		bean.setPublishTime(date);
		bean.setStatus(ArticleBean.State.unreviewd.Value());
		bean.setCategoryId(1);
		bean.setTitle("article");
		bean.setUserId(1212);
		bean.setAuthorType(AuthorType.PLAYER.getIndex());
		return bean;
	}
	
}
