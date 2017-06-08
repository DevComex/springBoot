 /**
    * -------------------------------------------------------------------------
    * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
    * @版权所有：北京光宇在线科技有限责任公司
    * @项目名称：玩家天地
    * @作者：李杜迪
    * @联系方式：lidudi@gyyx.cn
    * @创建时间：2017年3月29日下午2:15:29
    * @版本号：1.0.0
    *-------------------------------------------------------------------------
    */
package cn.gyyx.playwd;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import cn.gyyx.oa.stage.model.OAUserInfoModel;
import cn.gyyx.playwd.beans.common.PageBean;
import cn.gyyx.playwd.beans.common.ResultBean;
import cn.gyyx.playwd.beans.playwd.ArticleBean;
import cn.gyyx.playwd.beans.playwd.ArticleBean.AuthorType;
import cn.gyyx.playwd.beans.playwd.ArticleBean.State;
import cn.gyyx.playwd.beans.playwd.CategoryBean;
import cn.gyyx.playwd.beans.playwd.MessageBean;
import cn.gyyx.playwd.bll.ArticleBll;
import cn.gyyx.playwd.bll.CategoryBll;
import cn.gyyx.playwd.bll.MessageBll;
import cn.gyyx.playwd.bll.ReviewLogBll;
import cn.gyyx.playwd.service.ArticleService;

public class ArticleServiceTest {

	private ArticleBll articleBll;
	
	private ArticleService articleService;
	
	private CategoryBll categoryBll;
	
	private MessageBll messageBll;
	
	private ReviewLogBll reviewLogBll;
	
	@Before
	public void setMockup() {
		articleService=new ArticleService();
		articleBll=mock(ArticleBll.class);
		articleService.setArticleBll(articleBll);
		categoryBll=mock(CategoryBll.class);
		articleService.setCategoryBll(categoryBll);
		messageBll=mock(MessageBll.class);
		articleService.setMessageBll(messageBll);
		reviewLogBll=mock(ReviewLogBll.class);
		articleService.setReviewLogBll(reviewLogBll);
	}

	//@Test(description="playwd-048_筛选出未审核的作品点击“通过”_文章状态变更为审核通过展示")
	@Test
	public void reviewArticle_whenClickReviewPass_thenSuccess_playwd_048() throws Exception {		
		
		ResultBean<String>resultExpect=new ResultBean<>(true, "操作成功", "");
		
		State status = ArticleBean.State.passed;
		
		//初始化返回值
		ArticleBean bean=getArticleBean();
		when(articleBll.getArticleById(1)).thenReturn(bean);
		
		CategoryBean categoryBean=new CategoryBean();
		categoryBean.setTitle("ceshi");
		when(categoryBll.getParentCategoryBySubCode(bean.getCategoryId())).thenReturn(categoryBean);
		
		when(articleBll.editArticleStatusById(1,status.Value())).thenReturn(1);
		
		when(messageBll.add("", MessageBean.MessageType.pass.Value(), bean.getCode(), 
				CategoryBean.CategoryType.article.Value(), bean.getUserId(),bean.getTitle())).thenReturn(true);
		
		OAUserInfoModel userInfoModel=getOAUserInfoModel();
		when(reviewLogBll.insert(bean.getCode(),CategoryBean.CategoryType.article,
				ArticleBean.State.unreviewd,status,userInfoModel.getRealName())).thenReturn(true);
		
		ResultBean<String> resultActual=articleService.reviewArticle("success", 1, userInfoModel);
		
		assertEquals(resultActual.getIsSuccess(),resultExpect.getIsSuccess());
		assertEquals(resultActual.getData(),resultExpect.getData());
		assertEquals(resultActual.getMessage(),resultExpect.getMessage());
	}
	
	//@Test(description="playwd-050_playwd-055_playwd-056_playwd-057_选择筛选出来的已审核的作品点击“编辑”_进行编辑")
	@Test
	public void saveArticle_whenClickSave_thenSuccess_playwd_050_055_056_057() throws Exception {		
		
		ResultBean<Object>resultExpect=new ResultBean<>(true, "修改文章成功", null);
		
		//初始化返回值
		ArticleBean bean=getArticleBean();
		when(articleBll.editArticle(bean)).thenReturn(true);
		
		OAUserInfoModel userInfoModel=getOAUserInfoModel();
		
		ResultBean<Object> resultActual=articleService.saveArticle(bean, userInfoModel.getAccount(),userInfoModel.getCode());
		
		assertEquals(resultActual.getIsSuccess(),resultExpect.getIsSuccess());
		assertEquals(resultActual.getData(),resultExpect.getData());
		assertEquals(resultActual.getMessage(),resultExpect.getMessage());
	}
	
	//@Test(description="playwd-073_筛选出未审核的作品点击“不通过”_文章状态变更为不通过未展示")
	@Test
	public void reviewArticle_whenClickReviewFailed_thenSuccess_playwd_073() throws Exception {		
		
		ResultBean<String>resultExpect=new ResultBean<>(true, "操作成功", "");
		
		State status = ArticleBean.State.failed;
		
		//初始化返回值
		ArticleBean bean=getArticleBean();
		when(articleBll.getArticleById(1)).thenReturn(bean);
		
		CategoryBean categoryBean=new CategoryBean();
		categoryBean.setTitle("ceshi");
		when(categoryBll.getParentCategoryBySubCode(bean.getCategoryId())).thenReturn(categoryBean);
		
		when(articleBll.editArticleStatusById(1,status.Value())).thenReturn(1);
		
		when(messageBll.add("", MessageBean.MessageType.fail.Value(), bean.getCode(), 
				CategoryBean.CategoryType.article.Value(), bean.getUserId(),bean.getTitle())).thenReturn(true);
		
		OAUserInfoModel userInfoModel=getOAUserInfoModel();
		when(reviewLogBll.insert(bean.getCode(),CategoryBean.CategoryType.article,
				ArticleBean.State.unreviewd,status,userInfoModel.getRealName())).thenReturn(true);
		
		ResultBean<String> resultActual=articleService.reviewArticle("fail", 1, userInfoModel);
		
		assertEquals(resultActual.getIsSuccess(),resultExpect.getIsSuccess());
		assertEquals(resultActual.getData(),resultExpect.getData());
		assertEquals(resultActual.getMessage(),resultExpect.getMessage());
	}
	
	//@Test(description="playwd-074_筛选出审核不通过的作品进行“批量展示”的操作_该作品审核未通过")
	@Test
	public void batchShowOrHide_whenReviewFailedChangeShow_thenFail_playwd_074() throws Exception {		
		
		ResultBean<String>resultExpect=new ResultBean<>(true, "操作成功", "");
		
		//初始化返回值
		OAUserInfoModel userInfoModel=getOAUserInfoModel();
		ArticleBean bean=getArticleBean();
		bean.setStatus(ArticleBean.State.failed.Value());
		when(articleBll.getArticleById(1)).thenReturn(bean);

		int[] ids=new int[]{1};
		ResultBean<String> resultActual=articleService.batchShowOrHide("show",ids,userInfoModel);
		
		assertEquals(resultActual.getIsSuccess(),resultExpect.getIsSuccess());
		assertEquals(resultActual.getData(),resultExpect.getData());
		assertEquals(resultActual.getMessage(),resultExpect.getMessage());
	}
	
	//@Test(description="playwd-075_勾选单个未展示作品进行点击“批量展示”按键_该作品状态更新为已展示")
	@Test
	public void batchShowOrHide_whenReviewHiddenChangeShow_thensSuccess_playwd_075() throws Exception {		
		
		ResultBean<String>resultExpect=new ResultBean<>(true, "操作成功", "");
		
		State status = ArticleBean.State.hidden;
		
		//初始化返回值
		OAUserInfoModel userInfoModel=getOAUserInfoModel();
		ArticleBean bean=getArticleBean();
		bean.setStatus(status.Value());
		when(articleBll.getArticleById(1)).thenReturn(bean);
		when(articleBll.editArticleStatusById(1,status.Value())).thenReturn(1);
		when(reviewLogBll.insert(bean.getCode(),CategoryBean.CategoryType.article,
				ArticleBean.State.passed,status,userInfoModel.getRealName())).thenReturn(true);
		
		int[] ids=new int[]{1};
		ResultBean<String> resultActual=articleService.batchShowOrHide("show",ids,userInfoModel);
		
		assertEquals(resultActual.getIsSuccess(),resultExpect.getIsSuccess());
		assertEquals(resultActual.getData(),resultExpect.getData());
		assertEquals(resultActual.getMessage(),resultExpect.getMessage());
	}
	
	//@Test(description="playwd-077_勾选单个未展示作品进行点击“批量隐藏”按键_该作品状态更新为已隐藏")
	@Test
	public void batchShowOrHide_whenReviewHiddenChangeHidden_thensSuccess_playwd_077() throws Exception {		
		
		ResultBean<String>resultExpect=new ResultBean<>(true, "操作成功", "");
		
		State status = ArticleBean.State.hidden;
		
		//初始化返回值
		OAUserInfoModel userInfoModel=getOAUserInfoModel();
		ArticleBean bean=getArticleBean();
		bean.setStatus(status.Value());
		when(articleBll.getArticleById(1)).thenReturn(bean);

		int[] ids=new int[]{1};
		ResultBean<String> resultActual=articleService.batchShowOrHide("hidden",ids,userInfoModel);
		
		assertEquals(resultActual.getIsSuccess(),resultExpect.getIsSuccess());
		assertEquals(resultActual.getData(),resultExpect.getData());
		assertEquals(resultActual.getMessage(),resultExpect.getMessage());
	}

	//@Test(description="playwd-078_勾选单个已展示作品进行点击“批量隐藏”按键_该作品状态更新为已隐藏")
	@Test
	public void batchShowOrHide_whenReviewShowChangeHidden_thensSuccess_playwd_078() throws Exception {		
		
		ResultBean<String>resultExpect=new ResultBean<>(true, "操作成功", "");
		
		State status = ArticleBean.State.passed;
		
		//初始化返回值
		OAUserInfoModel userInfoModel=getOAUserInfoModel();
		ArticleBean bean=getArticleBean();
		bean.setStatus(status.Value());
		when(articleBll.getArticleById(1)).thenReturn(bean);
		when(articleBll.editArticleStatusById(1,ArticleBean.State.hidden.Value())).thenReturn(1);
		when(reviewLogBll.insert(bean.getCode(),CategoryBean.CategoryType.article,
				status,ArticleBean.State.hidden,userInfoModel.getRealName())).thenReturn(true);
		
		int[] ids=new int[]{1};
		ResultBean<String> resultActual=articleService.batchShowOrHide("hidden",ids,userInfoModel);
		
		assertEquals(resultActual.getIsSuccess(),resultExpect.getIsSuccess());
		assertEquals(resultActual.getData(),resultExpect.getData());
		assertEquals(resultActual.getMessage(),resultExpect.getMessage());
	}
	
	//@Test(description="playwd-080_勾选单个已展示作品进行点击“批量展示”按键_该作品状态更新为已展示")
	@Test
	public void batchShowOrHide_whenReviewShowChangeShow_thensSuccess_playwd_080() throws Exception {		
		
		ResultBean<String>resultExpect=new ResultBean<>(true, "操作成功", "");
		
		State status = ArticleBean.State.passed;
		
		//初始化返回值
		OAUserInfoModel userInfoModel=getOAUserInfoModel();
		ArticleBean bean=getArticleBean();
		bean.setStatus(status.Value());
		when(articleBll.getArticleById(1)).thenReturn(bean);
		
		int[] ids=new int[]{1};
		ResultBean<String> resultActual=articleService.batchShowOrHide("show",ids,userInfoModel);
		
		assertEquals(resultActual.getIsSuccess(),resultExpect.getIsSuccess());
		assertEquals(resultActual.getData(),resultExpect.getData());
		assertEquals(resultActual.getMessage(),resultExpect.getMessage());
	}
	
	//@Test(description="playwd-081_勾选多个未展示作品进行点击“批量展示”按键_该作品状态更新为已展示")
	@Test
	public void batchShowOrHide_whenReviewBatchHiddenChangeShow_thensSuccess_playwd_081() throws Exception {		
		
		ResultBean<String>resultExpect=new ResultBean<>(true, "操作成功", "");
		
		State status = ArticleBean.State.hidden;
		
		//初始化返回值
		OAUserInfoModel userInfoModel=getOAUserInfoModel();
		ArticleBean bean=getArticleBean();
		bean.setStatus(status.Value());
		when(articleBll.getArticleById(1)).thenReturn(bean);
		when(articleBll.editArticleStatusById(1,status.Value())).thenReturn(1);
		when(reviewLogBll.insert(bean.getCode(),CategoryBean.CategoryType.article,
				ArticleBean.State.passed,status,userInfoModel.getRealName())).thenReturn(true);
		
		int[] ids=new int[]{1,2,3};
		ResultBean<String> resultActual=articleService.batchShowOrHide("show",ids,userInfoModel);
		
		assertEquals(resultActual.getIsSuccess(),resultExpect.getIsSuccess());
		assertEquals(resultActual.getData(),resultExpect.getData());
		assertEquals(resultActual.getMessage(),resultExpect.getMessage());
	}

	//@Test(description="playwd-083_勾选多个未展示作品进行点击“批量隐藏”按键_该作品状态更新为已隐藏")
	@Test
	public void batchShowOrHide_whenReviewBatchHiddenChangeHidden_thensSuccess_playwd_083() throws Exception {		
		
		ResultBean<String>resultExpect=new ResultBean<>(true, "操作成功", "");
		
		State status = ArticleBean.State.hidden;
		
		//初始化返回值
		OAUserInfoModel userInfoModel=getOAUserInfoModel();
		ArticleBean bean=getArticleBean();
		bean.setStatus(status.Value());
		when(articleBll.getArticleById(1)).thenReturn(bean);

		int[] ids=new int[]{1,2,3};
		ResultBean<String> resultActual=articleService.batchShowOrHide("hidden",ids,userInfoModel);
		
		assertEquals(resultActual.getIsSuccess(),resultExpect.getIsSuccess());
		assertEquals(resultActual.getData(),resultExpect.getData());
		assertEquals(resultActual.getMessage(),resultExpect.getMessage());
	}

	//@Test(description="playwd-084_勾选多个已展示作品进行点击“批量隐藏”按键_该作品状态更新为已隐藏")
	@Test
	public void batchShowOrHide_whenReviewBatchShowChangeHidden_thensSuccess_playwd_084() throws Exception {		
		
		ResultBean<String>resultExpect=new ResultBean<>(true, "操作成功", "");
		
		State status = ArticleBean.State.passed;
		
		//初始化返回值
		OAUserInfoModel userInfoModel=getOAUserInfoModel();
		ArticleBean bean=getArticleBean();
		bean.setStatus(status.Value());
		when(articleBll.getArticleById(1)).thenReturn(bean);
		when(articleBll.editArticleStatusById(1,ArticleBean.State.hidden.Value())).thenReturn(1);
		when(reviewLogBll.insert(bean.getCode(),CategoryBean.CategoryType.article,
				status,ArticleBean.State.hidden,userInfoModel.getRealName())).thenReturn(true);
		
		int[] ids=new int[]{1,2,3};
		ResultBean<String> resultActual=articleService.batchShowOrHide("hidden",ids,userInfoModel);
		
		assertEquals(resultActual.getIsSuccess(),resultExpect.getIsSuccess());
		assertEquals(resultActual.getData(),resultExpect.getData());
		assertEquals(resultActual.getMessage(),resultExpect.getMessage());
	}

	//@Test(description="playwd-086_勾选多个已展示作品进行点击“批量展示”按键_该作品状态更新为已展示")
	@Test
	public void batchShowOrHide_whenReviewBatchShowChangeShow_thensSuccess_playwd_086() throws Exception {		
		
		ResultBean<String>resultExpect=new ResultBean<>(true, "操作成功", "");
		
		State status = ArticleBean.State.passed;
		
		//初始化返回值
		OAUserInfoModel userInfoModel=getOAUserInfoModel();
		ArticleBean bean=getArticleBean();
		bean.setStatus(status.Value());
		when(articleBll.getArticleById(1)).thenReturn(bean);
		
		int[] ids=new int[]{1,2,3};
		ResultBean<String> resultActual=articleService.batchShowOrHide("show",ids,userInfoModel);
		
		assertEquals(resultActual.getIsSuccess(),resultExpect.getIsSuccess());
		assertEquals(resultActual.getData(),resultExpect.getData());
		assertEquals(resultActual.getMessage(),resultExpect.getMessage());
	}

	//@Test(description="playwd-087_正确输入默认为官方")
	@Test
	public void saveArticle_whenClickSave_thenSuccess_playwd_087() throws Exception {		
		
		ResultBean<Object>resultExpect=new ResultBean<>(true, "添加文章成功", null);
		
		//初始化返回值
		ArticleBean bean=getArticleBean();
		bean.setCode(null);
		bean.setAuthorType(AuthorType.OFFICIAL.getIndex());
		when(articleBll.insertArticleOA(bean)).thenReturn(true);
		
		OAUserInfoModel userInfoModel=getOAUserInfoModel();
		
		ResultBean<Object> resultActual=articleService.saveArticle(bean, userInfoModel.getAccount(),userInfoModel.getCode());
		
		assertEquals(bean.getAuthor(),"官方");
		assertEquals(resultActual.getIsSuccess(),resultExpect.getIsSuccess());
		assertEquals(resultActual.getData(),resultExpect.getData());
		assertEquals(resultActual.getMessage(),resultExpect.getMessage());
	}

	//@Test(description="playwd-095_添加文章正确输入")
	@Test
	public void saveArticle_whenClickSave_thenSuccess_playwd_095() throws Exception {		
		
		ResultBean<Object>resultExpect=new ResultBean<>(true, "添加文章成功", null);
		
		//初始化返回值
		ArticleBean bean=getArticleBean();
		bean.setCode(null);
		when(articleBll.insertArticleOA(bean)).thenReturn(true);
		
		OAUserInfoModel userInfoModel=getOAUserInfoModel();
		
		ResultBean<Object> resultActual=articleService.saveArticle(bean, userInfoModel.getAccount(),userInfoModel.getCode());
		
		assertEquals(resultActual.getIsSuccess(),resultExpect.getIsSuccess());
		assertEquals(resultActual.getData(),resultExpect.getData());
		assertEquals(resultActual.getMessage(),resultExpect.getMessage());
	}

	//@Test(description="playwd-099_编辑文章对该文章进行文章分类和子分类的更改_修改文章成功")
	@Test
	public void saveArticle_whenClickSave_thenSuccess_playwd_099() throws Exception {		
		
		ResultBean<Object>resultExpect=new ResultBean<>(true, "修改文章成功", null);
		
		//初始化返回值
		ArticleBean bean=getArticleBean();
		bean.setCategoryId(2);

		when(articleBll.editArticle(bean)).thenReturn(true);
		
		OAUserInfoModel userInfoModel=getOAUserInfoModel();
		
		ResultBean<Object> resultActual=articleService.saveArticle(bean, userInfoModel.getAccount(),userInfoModel.getCode());
		
		assertEquals(resultActual.getIsSuccess(),resultExpect.getIsSuccess());
		assertEquals(resultActual.getData(),resultExpect.getData());
		assertEquals(resultActual.getMessage(),resultExpect.getMessage());
	}

	//@Test(description="playwd-101_编辑文章对该文章进行文章的标题的更改_修改文章成功")
	@Test
	public void saveArticle_whenClickSave_thenSuccess_playwd_101() throws Exception {		
		
		ResultBean<Object>resultExpect=new ResultBean<>(true, "修改文章成功", null);
		
		//初始化返回值
		ArticleBean bean=getArticleBean();
		bean.setTitle("测试");

		when(articleBll.editArticle(bean)).thenReturn(true);
		
		OAUserInfoModel userInfoModel=getOAUserInfoModel();
		
		ResultBean<Object> resultActual=articleService.saveArticle(bean, userInfoModel.getAccount(),userInfoModel.getCode());
		
		assertEquals(resultActual.getIsSuccess(),resultExpect.getIsSuccess());
		assertEquals(resultActual.getData(),resultExpect.getData());
		assertEquals(resultActual.getMessage(),resultExpect.getMessage());
	}
	
	//@Test(description="playwd-102_playwd-104_playwd-105_编辑文章对该文章进行选择一张图片点击上传_修改文章成功")
	@Test
	public void saveArticle_whenClickSave_thenSuccess_playwd_102() throws Exception {		
		
		ResultBean<Object>resultExpect=new ResultBean<>(true, "修改文章成功", null);
		
		//初始化返回值
		ArticleBean bean=getArticleBean();
		bean.setCover("http://ceshi/图片.png");

		when(articleBll.editArticle(bean)).thenReturn(true);
		
		OAUserInfoModel userInfoModel=getOAUserInfoModel();
		
		ResultBean<Object> resultActual=articleService.saveArticle(bean, userInfoModel.getAccount(),userInfoModel.getCode());
		
		assertEquals(resultActual.getIsSuccess(),resultExpect.getIsSuccess());
		assertEquals(resultActual.getData(),resultExpect.getData());
		assertEquals(resultActual.getMessage(),resultExpect.getMessage());
	}
	
	//@Test(description="playwd-107_编辑文章修改文章的“描述”描述字数不超过规定字数_修改文章成功")
	@Test
	public void saveArticle_whenClickSave_thenSuccess_playwd_107() throws Exception {		
		
		ResultBean<Object>resultExpect=new ResultBean<>(true, "修改文章成功", null);
		
		//初始化返回值
		ArticleBean bean=getArticleBean();
		bean.setSummary("不看你会....");

		when(articleBll.editArticle(bean)).thenReturn(true);
		
		OAUserInfoModel userInfoModel=getOAUserInfoModel();
		
		ResultBean<Object> resultActual=articleService.saveArticle(bean, userInfoModel.getAccount(),userInfoModel.getCode());
		
		assertEquals(resultActual.getIsSuccess(),resultExpect.getIsSuccess());
		assertEquals(resultActual.getData(),resultExpect.getData());
		assertEquals(resultActual.getMessage(),resultExpect.getMessage());
	}
	

	//@Test(description="playwd-109_编辑文章修改文章的“关键字”关键字字数不超过规定字数_修改文章成功")
	@Test
	public void saveArticle_whenClickSave_thenSuccess_playwd_109() throws Exception {		
		
		ResultBean<Object>resultExpect=new ResultBean<>(true, "修改文章成功", null);
		
		//初始化返回值
		ArticleBean bean=getArticleBean();
		bean.setKeywords("2017,11周年");

		when(articleBll.editArticle(bean)).thenReturn(true);
		
		OAUserInfoModel userInfoModel=getOAUserInfoModel();
		
		ResultBean<Object> resultActual=articleService.saveArticle(bean, userInfoModel.getAccount(),userInfoModel.getCode());
		
		assertEquals(resultActual.getIsSuccess(),resultExpect.getIsSuccess());
		assertEquals(resultActual.getData(),resultExpect.getData());
		assertEquals(resultActual.getMessage(),resultExpect.getMessage());
	}
	
	@Test
	public void reviewArticle_whenArticleNoExist_thenFalse() throws Exception {		
		
		ResultBean<String>resultExpect=new ResultBean<>(false, "文章不存在", "");
		
		when(articleBll.getArticleById(1)).thenReturn(null);
		
		OAUserInfoModel userInfoModel=getOAUserInfoModel();

		ResultBean<String> resultActual=articleService.reviewArticle("success", 1, userInfoModel);
		
		assertEquals(resultActual.getIsSuccess(),resultExpect.getIsSuccess());
		assertEquals(resultActual.getData(),resultExpect.getData());
		assertEquals(resultActual.getMessage(),resultExpect.getMessage());
	}
	
	@Test
	public void reviewArticle_whenArticleStatusNotUnreviewd_thenFalse() throws Exception {		
		
		ResultBean<String>resultExpect=new ResultBean<>(false, "请选择待审核的文章进行操作", "");
		
		ArticleBean bean=getArticleBean();
		bean.setStatus(State.passed.Value());
		when(articleBll.getArticleById(1)).thenReturn(bean);
		
		OAUserInfoModel userInfoModel=getOAUserInfoModel();

		ResultBean<String> resultActual=articleService.reviewArticle("success", 1, userInfoModel);
		
		assertEquals(resultActual.getIsSuccess(),resultExpect.getIsSuccess());
		assertEquals(resultActual.getData(),resultExpect.getData());
		assertEquals(resultActual.getMessage(),resultExpect.getMessage());
	}
	
	@Test
	public void saveArticle_whenArticleTitleExist_thenFalse() throws Exception {		
		
		ResultBean<Object>resultExpect=new ResultBean<>(false, "文章标题已存在", null);
		
		//初始化返回值
		ArticleBean bean=getArticleBean();
		bean.setCode(null);
		when(articleBll.findCountByTitle(bean.getTitle())).thenReturn(true);
		
		OAUserInfoModel userInfoModel=getOAUserInfoModel();
		
		ResultBean<Object> resultActual=articleService.saveArticle(bean, userInfoModel.getAccount(),userInfoModel.getCode());
		
		assertEquals(resultActual.getIsSuccess(),resultExpect.getIsSuccess());
		assertEquals(resultActual.getData(),resultExpect.getData());
		assertEquals(resultActual.getMessage(),resultExpect.getMessage());
	}
	
	@Test
	public void saveArticle_whenArticleNetIdIsNull_thenSuccess() throws Exception {		
		
		ResultBean<Object>resultExpect=new ResultBean<>(true, "添加文章成功", null);
		
		//初始化返回值
		ArticleBean bean=getArticleBean();
		bean.setCode(null);
		bean.setNetId(null);
		when(articleBll.findCountByTitle(bean.getTitle())).thenReturn(false);
		when(articleBll.insertArticleOA(bean)).thenReturn(true);
		OAUserInfoModel userInfoModel=getOAUserInfoModel();
		
		ResultBean<Object> resultActual=articleService.saveArticle(bean, userInfoModel.getAccount(),userInfoModel.getCode());
		
		assertEquals(resultActual.getIsSuccess(),resultExpect.getIsSuccess());
		assertEquals(resultActual.getData(),resultExpect.getData());
		assertEquals(resultActual.getMessage(),resultExpect.getMessage());
	}
	
	@Test
	public void saveArticle_whenArticleNetIdIs1_thenSuccess() throws Exception {		
		
		ResultBean<Object>resultExpect=new ResultBean<>(true, "添加文章成功", null);
		
		//初始化返回值
		ArticleBean bean=getArticleBean();
		bean.setCode(null);
		bean.setNetId(1);
		when(articleBll.findCountByTitle(bean.getTitle())).thenReturn(false);
		when(articleBll.insertArticleOA(bean)).thenReturn(true);
		OAUserInfoModel userInfoModel=getOAUserInfoModel();
		
		ResultBean<Object> resultActual=articleService.saveArticle(bean, userInfoModel.getAccount(),userInfoModel.getCode());
		
		assertEquals(resultActual.getIsSuccess(),resultExpect.getIsSuccess());
		assertEquals(resultActual.getData(),resultExpect.getData());
		assertEquals(resultActual.getMessage(),resultExpect.getMessage());
	}
	
	@Test
	public void getList_whenArticleListIsNull_thenFalse() throws Exception {		
		
		PageBean<Map<String, String>>resultExpect=PageBean.createPage("", 0, 1, 10, null, "暂无数据");
		
		//初始化返回值
		//文章标题 title,上传账号 account,昵称 author
		String type="title" ;
		//搜索内容
		String key="aaaa";
		//展示状态 全部 -1,隐藏 0,展示 1
		int displayStatus=1;
		//一级分类
		int firstCategoryId=1;
		//二级分类
		int secondCategoryId=2;
		//发布人 玩家player,官方official,全部all
		String authorType="player";
		//开始时间
		String startTime="2017-04-12 10:14:00";
		//结束时间
		String endTime="2017-04-13 10:14:00";
		//审核状态 待审核unreviewd,审核通过(展示)passed,审核不通过failed,通过但不显示hidden, 通过且推荐recommended
		String status="unreviewd";
		int pageIndex=1;
		int pageSize=10;
		
		when(articleBll.getList(type, key, displayStatus, firstCategoryId,
				secondCategoryId, authorType, startTime, endTime, status,
				pageIndex, pageSize)).thenReturn(null);

		PageBean<Map<String, String>> resultActual=articleService.getList(type, key, displayStatus, firstCategoryId,
				secondCategoryId, authorType, startTime, endTime, status,
				pageIndex, pageSize);
		
		assertEquals(resultActual.getIsSuccess(),resultExpect.getIsSuccess());
		assertEquals(resultActual.getDataSet(),resultExpect.getDataSet());
		assertEquals(resultActual.getMessage(),resultExpect.getMessage());
	}
	
	@Test
	public void getList_whenArticleListSizeIs0_thenFalse() throws Exception {		
		
		PageBean<Map<String, String>>resultExpect=PageBean.createPage("", 0, 1, 10, null, "暂无数据");
		
		//初始化返回值
		//文章标题 title,上传账号 account,昵称 author
		String type="title" ;
		//搜索内容
		String key="aaaa";
		//展示状态 全部 -1,隐藏 0,展示 1
		int displayStatus=1;
		//一级分类
		int firstCategoryId=1;
		//二级分类
		int secondCategoryId=2;
		//发布人 玩家player,官方official,全部all
		String authorType="player";
		//开始时间
		String startTime="2017-04-12 10:14:00";
		//结束时间
		String endTime="2017-04-13 10:14:00";
		//审核状态 待审核unreviewd,审核通过(展示)passed,审核不通过failed,通过但不显示hidden, 通过且推荐recommended
		String status="unreviewd";
		int pageIndex=1;
		int pageSize=10;
		
		when(articleBll.getList(type, key, displayStatus, firstCategoryId,
				secondCategoryId, authorType, startTime, endTime, status,
				pageIndex, pageSize)).thenReturn(new ArrayList<ArticleBean>());

		PageBean<Map<String, String>> resultActual=articleService.getList(type, key, displayStatus, firstCategoryId,
				secondCategoryId, authorType, startTime, endTime, status,
				pageIndex, pageSize);
		
		assertEquals(resultActual.getIsSuccess(),resultExpect.getIsSuccess());
		assertEquals(resultActual.getDataSet(),resultExpect.getDataSet());
		assertEquals(resultActual.getMessage(),resultExpect.getMessage());
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
	
	/**
	 * 模拟ArticleBean
	 * @return
	 */
	private OAUserInfoModel getOAUserInfoModel() {
		OAUserInfoModel userInfoModel=new OAUserInfoModel();
		userInfoModel.setRealName("test");
		userInfoModel.setAccount("testAccount");
		userInfoModel.setCode(1);
		
		return userInfoModel;
	}
}
