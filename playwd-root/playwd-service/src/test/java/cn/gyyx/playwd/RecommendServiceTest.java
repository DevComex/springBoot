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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import cn.gyyx.playwd.beans.playwd.NovelBean;
import cn.gyyx.playwd.beans.playwd.NovelChapterBean;
import cn.gyyx.playwd.beans.playwd.PrizeBean;
import cn.gyyx.playwd.beans.playwd.RecommendContentBean;
import cn.gyyx.playwd.beans.playwd.RecommendSlotBean;
import cn.gyyx.playwd.beans.playwd.UserBean;
import cn.gyyx.playwd.bll.ArticleBll;
import cn.gyyx.playwd.bll.CategoryBll;
import cn.gyyx.playwd.bll.ItemBll;
import cn.gyyx.playwd.bll.MessageBll;
import cn.gyyx.playwd.bll.NovelBll;
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

/**
 * 推荐管理
 * @author lidudi
 *
 */
public class RecommendServiceTest {

	private RecommendService recommendService;
	
	private RecommendSlotBll recommendSlotBll;
	
	private RecommendBll recommendBll;
	
	private PrizeBll prizeBll;

	private ArticleBll articleBll;

	private ReviewLogBll reviewLogBll;

	private WdgiftOrderBll wdgiftOrderBll;

	private RecommendContentBll recommendContentBll;

	private MessageBll messageBll;

	private CategoryBll categoryBll;

	private RmbOrderBll rmbOrderBll;

	private UserTitleBll userTitleBll;

	private ItemBll itemBll;

	private UserBll userBll;
	
	private NovelBll novelBll;
	@Before
	public void setMockup() {
		recommendService=new RecommendService();
		recommendSlotBll=mock(RecommendSlotBll.class);
		recommendService.setRecommendSlotBll(recommendSlotBll);
		recommendBll=mock(RecommendBll.class);
		recommendService.setRecommendBll(recommendBll);
		prizeBll=mock(PrizeBll.class);
		recommendService.setPrizeBll(prizeBll);
		articleBll=mock(ArticleBll.class);
		recommendService.setArticleBll(articleBll);
		reviewLogBll=mock(ReviewLogBll.class);
		recommendService.setReviewLogBll(reviewLogBll);
		wdgiftOrderBll=mock(WdgiftOrderBll.class);
		recommendService.setWdgiftOrderBll(wdgiftOrderBll);
		recommendContentBll=mock(RecommendContentBll.class);
		recommendService.setRecommendContentBll(recommendContentBll);
		messageBll=mock(MessageBll.class);
		recommendService.setMessageBll(messageBll);
		categoryBll=mock(CategoryBll.class);
		recommendService.setCategoryBll(categoryBll);
		rmbOrderBll=mock(RmbOrderBll.class);
		recommendService.setRmbOrderBll(rmbOrderBll);
		userTitleBll=mock(UserTitleBll.class);
		recommendService.setUserTitleBll(userTitleBll);
		itemBll=mock(ItemBll.class);
		recommendService.setItemBll(itemBll);
		userBll=mock(UserBll.class);
		recommendService.setUserBll(userBll);
		novelBll=mock(NovelBll.class);
		recommendService.setNovelBll(novelBll);
	}
	
	@Test
	public void addOrChangeMemberInfo_whenUserIsNull_thenSuccesss() throws Exception {

		int userId=122112;
		BigDecimal rmb=new BigDecimal(0);
		int silverCoin=2000;
		
		boolean resultExpect=true;

		when(userBll.getByUserId(userId)).thenReturn(null);
		when(userBll.addRecord(userId, rmb, silverCoin)).thenReturn(1);

		boolean resultActual=recommendService.addOrChangeMemberInfo(userId, rmb, silverCoin);
		
		assertEquals(resultActual,resultExpect);
	}
	
	@Test
	public void addOrChangeMemberInfo_whenUserIsNull_thenFailed() throws Exception {

		int userId=122112;
		BigDecimal rmb=new BigDecimal(0);
		int silverCoin=2000;
		
		boolean resultExpect=false;

		when(userBll.getByUserId(userId)).thenReturn(null);
		when(userBll.addRecord(userId, rmb, silverCoin)).thenReturn(0);

		boolean resultActual=recommendService.addOrChangeMemberInfo(userId, rmb, silverCoin);
		
		assertEquals(resultActual,resultExpect);
	}
	
	@Test
	public void addOrChangeMemberInfo_whenUserNotNull_thenSuccesss() throws Exception {

		int userId=122112;
		BigDecimal rmb=new BigDecimal(0);
		int silverCoin=2000;
		
		UserBean userBean=new UserBean();
		
		boolean resultExpect=true;

		when(userBll.getByUserId(userId)).thenReturn(userBean);
		when(userBll.changeRmbAndSilverCoin(userId, rmb, silverCoin)).thenReturn(1);

		boolean resultActual=recommendService.addOrChangeMemberInfo(userId, rmb, silverCoin);
		
		assertEquals(resultActual,resultExpect);
	}
	
	@Test
	public void addOrChangeMemberInfo_whenUserNotNull_thenFailed() throws Exception {

		int userId=122112;
		BigDecimal rmb=new BigDecimal(0);
		int silverCoin=2000;
		
		UserBean userBean=new UserBean();
		
		boolean resultExpect=false;

		when(userBll.getByUserId(userId)).thenReturn(userBean);
		when(userBll.changeRmbAndSilverCoin(userId, rmb, silverCoin)).thenReturn(0);

		boolean resultActual=recommendService.addOrChangeMemberInfo(userId, rmb, silverCoin);
		
		assertEquals(resultActual,resultExpect);
	}
	
	@Test
	public void getArticleCountByPrize_whenPrizeIdNotNull_then1() throws Exception {

		ArticleBean articleBean=new ArticleBean();
		articleBean.setCode(1);
		articleBean.setParentId(11);
		articleBean.setTitle("文章");
		articleBean.setUserId(44545);
		articleBean.setPrizeId(1);
		articleBean.setAccount("dessss");
		articleBean.setGameId(2);
		articleBean.setServerId(311);
		articleBean.setServerName("问道测试");
		
		List<ArticleBean>articleBeans=new ArrayList<ArticleBean>();
		articleBeans.add(articleBean);

		int resultExpect=1;

		int resultActual=recommendService.getArticleCountByPrize(articleBeans, 1);
		
		assertEquals(resultActual,resultExpect);
	}
	
	@Test
	public void getArticleCountByPrize_whenPrizeIdIsNull_then0() throws Exception {

		ArticleBean articleBean=new ArticleBean();
		articleBean.setCode(1);
		articleBean.setParentId(11);
		articleBean.setTitle("文章");
		articleBean.setUserId(44545);
		articleBean.setPrizeId(null);
		articleBean.setAccount("dessss");
		articleBean.setGameId(2);
		articleBean.setServerId(311);
		articleBean.setServerName("问道测试");
		
		List<ArticleBean>articleBeans=new ArrayList<ArticleBean>();
		articleBeans.add(articleBean);

		int resultExpect=0;

		int resultActual=recommendService.getArticleCountByPrize(articleBeans, 1);
		
		assertEquals(resultActual,resultExpect);
	}
	
	@Test
	public void articleRecommend_whenArticleInfoIsNull_thenFailed() throws Exception {
		
		Integer contentId=1; 
		String recommendSlotId="1";
		Integer prizeId=1;
		String prizeType="";
		String operator="lidudi";
		
		ResultBean<String>resultExpect=new ResultBean<String>("incorrect-articleInfo", "图文不存在", null);

		when(articleBll.getArticleById(contentId)).thenReturn(null);
		
		ResultBean<String> resultActual=recommendService.articleRecommend(contentId, recommendSlotId, prizeId, prizeType, operator);
		
		assertEquals(resultActual.getIsSuccess(),resultExpect.getIsSuccess());
		assertEquals(resultActual.getData(),resultExpect.getData());
		assertEquals(resultActual.getMessage(),resultExpect.getMessage());
		assertEquals(resultActual.getStatus(),resultExpect.getStatus());
	}
	
	@Test
	public void articleRecommend_whenPrizeInfoIsNull_thenFailed() throws Exception {
		
		Integer contentId=1; 
		String recommendSlotId="1";
		Integer prizeId=1;
		String prizeType="";
		String operator="lidudi";
		
		ArticleBean articleBean=new ArticleBean();
		articleBean.setCode(1);
		
		ResultBean<String>resultExpect=new ResultBean<String>("incorrect-prize", "奖品信息不存在", null);

		when(articleBll.getArticleById(contentId)).thenReturn(articleBean);
		when(prizeBll.getPrizeByCode(prizeId)).thenReturn(null);
		
		ResultBean<String> resultActual=recommendService.articleRecommend(contentId, recommendSlotId, prizeId, prizeType, operator);
		
		assertEquals(resultActual.getIsSuccess(),resultExpect.getIsSuccess());
		assertEquals(resultActual.getData(),resultExpect.getData());
		assertEquals(resultActual.getMessage(),resultExpect.getMessage());
		assertEquals(resultActual.getStatus(),resultExpect.getStatus());
	}
	
	@Test
	public void articleRecommend_whenPrizeIsFourStarAndPrizeTypeIsEmpty_thenFailed() throws Exception {
		
		Integer contentId=1; 
		String recommendSlotId="1";
		Integer prizeId=1;
		String prizeType="";
		String operator="lidudi";
		
		ArticleBean articleBean=new ArticleBean();
		articleBean.setCode(1);
		
		PrizeBean prizeBean=new PrizeBean();
		prizeBean.setCode(1);
		prizeBean.setName("4星级");
		
		ResultBean<String>resultExpect=new ResultBean<String>("incorrect-prizeType", "4星级和5星级需选择人民币或者银元宝", null);

		when(articleBll.getArticleById(contentId)).thenReturn(articleBean);
		when(prizeBll.getPrizeByCode(prizeId)).thenReturn(prizeBean);
		
		ResultBean<String> resultActual=recommendService.articleRecommend(contentId, recommendSlotId, prizeId, prizeType, operator);
		
		assertEquals(resultActual.getIsSuccess(),resultExpect.getIsSuccess());
		assertEquals(resultActual.getData(),resultExpect.getData());
		assertEquals(resultActual.getMessage(),resultExpect.getMessage());
		assertEquals(resultActual.getStatus(),resultExpect.getStatus());
	}
	
	@Test
	public void articleRecommend_whenPrizeIsFiveStarAndPrizeTypeIsEmpty_thenFailed() throws Exception {
		
		Integer contentId=1; 
		String recommendSlotId="1";
		Integer prizeId=1;
		String prizeType="";
		String operator="lidudi";
		
		ArticleBean articleBean=new ArticleBean();
		articleBean.setCode(1);
		
		PrizeBean prizeBean=new PrizeBean();
		prizeBean.setCode(1);
		prizeBean.setName("5星级");
		
		ResultBean<String>resultExpect=new ResultBean<String>("incorrect-prizeType", "4星级和5星级需选择人民币或者银元宝", null);

		when(articleBll.getArticleById(contentId)).thenReturn(articleBean);
		when(prizeBll.getPrizeByCode(prizeId)).thenReturn(prizeBean);
		
		ResultBean<String> resultActual=recommendService.articleRecommend(contentId, recommendSlotId, prizeId, prizeType, operator);
		
		assertEquals(resultActual.getIsSuccess(),resultExpect.getIsSuccess());
		assertEquals(resultActual.getData(),resultExpect.getData());
		assertEquals(resultActual.getMessage(),resultExpect.getMessage());
		assertEquals(resultActual.getStatus(),resultExpect.getStatus());
	}
	
	//获得一星奖励并且已经获得小于4篇一星奖励
	@Test
	public void articlePrize_whenPrizeIsOneStarAndPrizeTypeIsEmpty_thenSuccesss() throws Exception {

		String prizeType="";
		String recommendSlotNameString="猜你喜欢";
		
		ArticleBean articleBean=new ArticleBean();
		articleBean.setCode(1);
		articleBean.setParentId(11);
		articleBean.setTitle("文章");
		articleBean.setUserId(44545);
		articleBean.setPrizeId(1);
		articleBean.setAccount("dessss");
		articleBean.setGameId(2);
		articleBean.setServerId(311);
		articleBean.setServerName("问道测试");
		
		List<ArticleBean>articleBeans=new ArrayList<ArticleBean>();
		articleBeans.add(articleBean);
		
		List<ItemBean> itemBeans=new ArrayList<ItemBean>();
		ItemBean itemBean =new ItemBean();
		itemBean.setCode(1);
		itemBean.setName("2000银元宝");
		itemBean.setItemCode("微信摇一摇活动奖励500银元宝礼包(161128)");
		itemBean.setValue("2000");
		itemBeans.add(itemBean);
		
		PrizeBean prizeBean=new PrizeBean();
		prizeBean.setCode(1);
		prizeBean.setName("1星级");
		prizeBean.setItemList(itemBeans);
		
		CategoryBean categoryBean =new CategoryBean();
		categoryBean.setTitle("十一周年");

		String message=MessageFormat.format(MessageBean.MessageType_Recommend_Template, 
				"十一周年", 
				articleBean.getTitle(),
				prizeBean.getName().subSequence(0, 1),recommendSlotNameString,prizeBean.getItemList().get(0).getName(),
				MessageBean.MessageType_RecommendLowPrize_Template);
		
		ResultBean<String>resultExpect=new ResultBean<String>("success", "成功", null);

		when(categoryBll.getCategroyByCode(articleBean.getParentId())).thenReturn(categoryBean);
		when(articleBll.getListByMonthUserId(articleBean.getUserId())).thenReturn(articleBeans);
		when(wdgiftOrderBll.addWdgiftOrder(articleBean.getAccount(), articleBean.getCode(), prizeBean.getCode(), articleBean.getGameId(), 
				articleBean.getServerId(), articleBean.getServerName(), prizeBean.getItemList().get(0).getItemCode(),
				CategoryType.article.Value(),articleBean.getUserId()))
		.thenReturn(1);
		when(userBll.getByUserId(articleBean.getUserId())).thenReturn(null);
		when(userBll.addRecord(articleBean.getUserId(),new BigDecimal(0), Integer.parseInt(prizeBean.getItemList().get(0).getValue())))
		.thenReturn(1);
		when(messageBll.add(message, MessageBean.MessageType.recommend.Value(), articleBean.getCode(),
				CategoryBean.CategoryType.article.Value(), articleBean.getUserId(),articleBean.getTitle()))
		.thenReturn(true);
		
		ResultBean<String> resultActual=recommendService.articlePrize(articleBean, prizeBean, recommendSlotNameString, prizeType,"hi");
		
		assertEquals(resultActual.getIsSuccess(),resultExpect.getIsSuccess());
		assertEquals(resultActual.getData(),resultExpect.getData());
		assertEquals(resultActual.getMessage(),resultExpect.getMessage());
		assertEquals(resultActual.getStatus(),resultExpect.getStatus());
	}
	
	//获得一星奖励并且已经获得过了4篇一星奖励
	@Test
	public void articlePrize_whenPrizeIsOneStarAndPrizeTypeIsEmptyAndArticleCountIs4_thenSuccesss() throws Exception {

		String prizeType="";
		String recommendSlotNameString="猜你喜欢";
		
		ArticleBean articleBean=new ArticleBean();
		articleBean.setCode(1);
		articleBean.setParentId(11);
		articleBean.setTitle("文章");
		articleBean.setUserId(44545);
		articleBean.setPrizeId(1);
		articleBean.setAccount("dessss");
		articleBean.setGameId(2);
		articleBean.setServerId(311);
		articleBean.setServerName("问道测试");
		
		List<ArticleBean>articleBeans=new ArrayList<ArticleBean>();
		articleBeans.add(articleBean);
		articleBeans.add(articleBean);
		articleBeans.add(articleBean);
		articleBeans.add(articleBean);
		
		List<ItemBean> itemBeans=new ArrayList<ItemBean>();
		ItemBean itemBean =new ItemBean();
		itemBean.setCode(1);
		itemBean.setName("2000银元宝");
		itemBean.setItemCode("微信摇一摇活动奖励500银元宝礼包(161128)");
		itemBean.setValue("2000");
		itemBeans.add(itemBean);
		
		ItemBean itemBeanTitle =new ItemBean();
		itemBeanTitle.setCode(2);
		itemBeanTitle.setName("丹青妙笔称号");
		itemBeanTitle.setItemCode("丹青妙笔称号");
		itemBeanTitle.setValue("丹青妙笔称号");
		
		PrizeBean prizeBean=new PrizeBean();
		prizeBean.setCode(1);
		prizeBean.setName("1星级");
		prizeBean.setItemList(itemBeans);
		
		CategoryBean categoryBean =new CategoryBean();
		categoryBean.setTitle("十一周年");

		String message=MessageFormat.format(MessageBean.MessageType_Recommend_Template, 
				"十一周年", 
				articleBean.getTitle(),
				prizeBean.getName().subSequence(0, 1),recommendSlotNameString,prizeBean.getItemList().get(0).getName(),
				MessageBean.MessageType_RecommendLowPrize_Template);
		
		ResultBean<String>resultExpect=new ResultBean<String>("success", "成功", null);

		when(categoryBll.getCategroyByCode(articleBean.getParentId())).thenReturn(categoryBean);
		when(articleBll.getListByMonthUserId(articleBean.getUserId())).thenReturn(articleBeans);
		when(wdgiftOrderBll.addWdgiftOrder(articleBean.getAccount(), articleBean.getCode(), prizeBean.getCode(), articleBean.getGameId(), 
				articleBean.getServerId(), articleBean.getServerName(), prizeBean.getItemList().get(0).getItemCode(),
				CategoryType.article.Value(),articleBean.getUserId()))
		.thenReturn(1);
		when(userBll.getByUserId(articleBean.getUserId())).thenReturn(null);
		when(userBll.addRecord(articleBean.getUserId(),new BigDecimal(0), Integer.parseInt(prizeBean.getItemList().get(0).getValue())))
		.thenReturn(1);
		when(itemBll.getItemByName("丹青妙笔称号")).thenReturn(itemBeanTitle);
		when(wdgiftOrderBll.addWdgiftOrder(articleBean.getAccount(), articleBean.getCode(), prizeBean.getCode(), articleBean.getGameId(),
				articleBean.getServerId(), articleBean.getServerName(), itemBean.getItemCode(),"",articleBean.getUserId()))
		.thenReturn(1);
		when(userTitleBll.addUserTitle("", articleBean.getCode(), articleBean.getUserId(),prizeBean.getCode(), "丹青妙笔称号"))
		.thenReturn(1);
		when(messageBll.add(message, MessageBean.MessageType.recommend.Value(), articleBean.getCode(),
				CategoryBean.CategoryType.article.Value(), articleBean.getUserId(),articleBean.getTitle()))
		.thenReturn(true);
		
		ResultBean<String> resultActual=recommendService.articlePrize(articleBean, prizeBean, recommendSlotNameString, prizeType,"hi");
		
		assertEquals(resultActual.getIsSuccess(),resultExpect.getIsSuccess());
		assertEquals(resultActual.getData(),resultExpect.getData());
		assertEquals(resultActual.getMessage(),resultExpect.getMessage());
		assertEquals(resultActual.getStatus(),resultExpect.getStatus());
	}
	
	//获得一星奖励并且已经获得过了6篇一星奖励
	@Test
	public void articlePrize_whenPrizeIsOneStarAndPrizeTypeIsEmptyAndArticleCountIs6_thenSuccesss() throws Exception {

		String prizeType="";
		String recommendSlotNameString="猜你喜欢";
		
		ArticleBean articleBean=new ArticleBean();
		articleBean.setCode(1);
		articleBean.setParentId(11);
		articleBean.setTitle("文章");
		articleBean.setUserId(44545);
		articleBean.setPrizeId(1);
		articleBean.setAccount("dessss");
		articleBean.setGameId(2);
		articleBean.setServerId(311);
		articleBean.setServerName("问道测试");
		
		List<ArticleBean>articleBeans=new ArrayList<ArticleBean>();
		articleBeans.add(articleBean);
		articleBeans.add(articleBean);
		articleBeans.add(articleBean);
		articleBeans.add(articleBean);
		articleBeans.add(articleBean);
		articleBeans.add(articleBean);
		
		List<ItemBean> itemBeans=new ArrayList<ItemBean>();
		ItemBean itemBean =new ItemBean();
		itemBean.setCode(1);
		itemBean.setName("2000银元宝");
		itemBean.setItemCode("微信摇一摇活动奖励500银元宝礼包(161128)");
		itemBean.setValue("2000");
		itemBeans.add(itemBean);
		
		ItemBean itemBeanTitle =new ItemBean();
		itemBeanTitle.setCode(2);
		itemBeanTitle.setName("妙语连珠称号");
		itemBeanTitle.setItemCode("妙语连珠称号");
		itemBeanTitle.setValue("妙语连珠称号");
		
		PrizeBean prizeBean=new PrizeBean();
		prizeBean.setCode(1);
		prizeBean.setName("1星级");
		prizeBean.setItemList(itemBeans);
		
		CategoryBean categoryBean =new CategoryBean();
		categoryBean.setTitle("十一周年");

		String message=MessageFormat.format(MessageBean.MessageType_Recommend_Template, 
				"十一周年", 
				articleBean.getTitle(),
				prizeBean.getName().subSequence(0, 1),recommendSlotNameString,prizeBean.getItemList().get(0).getName(),
				MessageBean.MessageType_RecommendLowPrize_Template);
		
		ResultBean<String>resultExpect=new ResultBean<String>("success", "成功", null);

		when(categoryBll.getCategroyByCode(articleBean.getParentId())).thenReturn(categoryBean);
		when(articleBll.getListByMonthUserId(articleBean.getUserId())).thenReturn(articleBeans);
		when(wdgiftOrderBll.addWdgiftOrder(articleBean.getAccount(), articleBean.getCode(), prizeBean.getCode(), articleBean.getGameId(), 
				articleBean.getServerId(), articleBean.getServerName(), prizeBean.getItemList().get(0).getItemCode(),
				CategoryType.article.Value(),articleBean.getUserId()))
		.thenReturn(1);
		when(userBll.getByUserId(articleBean.getUserId())).thenReturn(null);
		when(userBll.addRecord(articleBean.getUserId(),new BigDecimal(0), Integer.parseInt(prizeBean.getItemList().get(0).getValue())))
		.thenReturn(1);
		when(itemBll.getItemByName("妙语连珠称号")).thenReturn(itemBeanTitle);
		when(wdgiftOrderBll.addWdgiftOrder(articleBean.getAccount(), articleBean.getCode(), prizeBean.getCode(), articleBean.getGameId(),
				articleBean.getServerId(), articleBean.getServerName(), itemBean.getItemCode(),"",articleBean.getUserId()))
		.thenReturn(1);
		when(userTitleBll.addUserTitle("", articleBean.getCode(), articleBean.getUserId(),prizeBean.getCode(), "妙语连珠称号"))
		.thenReturn(1);
		when(messageBll.add(message, MessageBean.MessageType.recommend.Value(), articleBean.getCode(),
				CategoryBean.CategoryType.article.Value(), articleBean.getUserId(),articleBean.getTitle()))
		.thenReturn(true);
		
		ResultBean<String> resultActual=recommendService.articlePrize(articleBean, prizeBean, recommendSlotNameString, prizeType,"hi");
		
		assertEquals(resultActual.getIsSuccess(),resultExpect.getIsSuccess());
		assertEquals(resultActual.getData(),resultExpect.getData());
		assertEquals(resultActual.getMessage(),resultExpect.getMessage());
		assertEquals(resultActual.getStatus(),resultExpect.getStatus());
	}
	
	//获得二星奖励并且已经获得小于4篇二星奖励
	@Test
	public void articlePrize_whenPrizeIsTwoStarAndPrizeTypeIsEmpty_thenSuccesss() throws Exception {

		String prizeType="";
		String recommendSlotNameString="猜你喜欢";
		
		ArticleBean articleBean=new ArticleBean();
		articleBean.setCode(1);
		articleBean.setParentId(11);
		articleBean.setTitle("文章");
		articleBean.setUserId(44545);
		articleBean.setPrizeId(1);
		articleBean.setAccount("dessss");
		articleBean.setGameId(2);
		articleBean.setServerId(311);
		articleBean.setServerName("问道测试");
		
		List<ArticleBean>articleBeans=new ArrayList<ArticleBean>();
		articleBeans.add(articleBean);
		
		List<ItemBean> itemBeans=new ArrayList<ItemBean>();
		ItemBean itemBean =new ItemBean();
		itemBean.setCode(1);
		itemBean.setName("5000银元宝");
		itemBean.setItemCode("微信摇一摇活动奖励5000银元宝礼包(161128)");
		itemBean.setValue("5000");
		ItemBean itemBeanTitle =new ItemBean();
		itemBeanTitle.setCode(1);
		itemBeanTitle.setName("丹青妙笔称号");
		itemBeanTitle.setItemCode("丹青妙笔称号");
		itemBeanTitle.setValue("丹青妙笔称号");
		itemBeans.add(itemBean);
		itemBeans.add(itemBeanTitle);
		
		PrizeBean prizeBean=new PrizeBean();
		prizeBean.setCode(1);
		prizeBean.setName("2星级");
		prizeBean.setItemList(itemBeans);
		
		CategoryBean categoryBean =new CategoryBean();
		categoryBean.setTitle("十一周年");

		String message=MessageFormat.format(MessageBean.MessageType_Recommend_Template, 
				"十一周年", 
				articleBean.getTitle(),
				prizeBean.getName().subSequence(0, 1),recommendSlotNameString,prizeBean.getItemList().get(0).getName(),
				MessageBean.MessageType_RecommendLowPrize_Template);
		
		ResultBean<String>resultExpect=new ResultBean<String>("success", "成功", null);

		when(categoryBll.getCategroyByCode(articleBean.getParentId())).thenReturn(categoryBean);
		when(articleBll.getListByMonthUserId(articleBean.getUserId())).thenReturn(articleBeans);
		when(wdgiftOrderBll.addWdgiftOrder(articleBean.getAccount(), articleBean.getCode(), prizeBean.getCode(), articleBean.getGameId(), 
				articleBean.getServerId(), articleBean.getServerName(), prizeBean.getItemList().get(0).getItemCode(),
				CategoryType.article.Value(),articleBean.getUserId()))
		.thenReturn(1);
		when(userBll.getByUserId(articleBean.getUserId())).thenReturn(null);
		when(userBll.addRecord(articleBean.getUserId(),new BigDecimal(0), Integer.parseInt(prizeBean.getItemList().get(0).getValue())))
		.thenReturn(1);
		when(messageBll.add(message, MessageBean.MessageType.recommend.Value(), articleBean.getCode(),
				CategoryBean.CategoryType.article.Value(), articleBean.getUserId(),articleBean.getTitle()))
		.thenReturn(true);
		
		ResultBean<String> resultActual=recommendService.articlePrize(articleBean, prizeBean, recommendSlotNameString, prizeType,"hi");
		
		assertEquals(resultActual.getIsSuccess(),resultExpect.getIsSuccess());
		assertEquals(resultActual.getData(),resultExpect.getData());
		assertEquals(resultActual.getMessage(),resultExpect.getMessage());
		assertEquals(resultActual.getStatus(),resultExpect.getStatus());
	}
		
	//获得二星奖励并且已经获得过了4篇二星奖励
	@Test
	public void articlePrize_whenPrizeIsTwoStarAndPrizeTypeIsEmptyAndArticleCountIs4_thenSuccesss() throws Exception {

		String prizeType="";
		String recommendSlotNameString="猜你喜欢";
		
		ArticleBean articleBean=new ArticleBean();
		articleBean.setCode(1);
		articleBean.setParentId(11);
		articleBean.setTitle("文章");
		articleBean.setUserId(44545);
		articleBean.setPrizeId(2);
		articleBean.setGameId(2);
		articleBean.setServerId(311);
		articleBean.setServerName("问道测试");
		
		List<ArticleBean>articleBeans=new ArrayList<ArticleBean>();
		articleBeans.add(articleBean);
		articleBeans.add(articleBean);
		articleBeans.add(articleBean);
		articleBeans.add(articleBean);
		
		List<ItemBean> itemBeans=new ArrayList<ItemBean>();
		ItemBean itemBean =new ItemBean();
		itemBean.setCode(1);
		itemBean.setName("5000银元宝");
		itemBean.setItemCode("微信摇一摇活动奖励5000银元宝礼包(161128)");
		itemBean.setValue("5000");
		ItemBean itemBeanTitle =new ItemBean();
		itemBeanTitle.setCode(3);
		itemBeanTitle.setName("丹青妙笔称号");
		itemBeanTitle.setItemCode("丹青妙笔称号");
		itemBeanTitle.setValue("丹青妙笔称号");
		itemBeans.add(itemBean);
		itemBeans.add(itemBeanTitle);
		
		ItemBean itemBeanTitleExtra =new ItemBean();
		itemBeanTitleExtra.setCode(2);
		itemBeanTitleExtra.setName("妙语连珠称号");
		itemBeanTitleExtra.setItemCode("妙语连珠称号");
		itemBeanTitleExtra.setValue("妙语连珠称号");
		
		PrizeBean prizeBean=new PrizeBean();
		prizeBean.setCode(2);
		prizeBean.setName("2星级");
		prizeBean.setItemList(itemBeans);
		
		CategoryBean categoryBean =new CategoryBean();
		categoryBean.setTitle("十一周年");

		String message=MessageFormat.format(MessageBean.MessageType_Recommend_Template, 
				"十一周年", 
				articleBean.getTitle(),
				prizeBean.getName().subSequence(0, 1),recommendSlotNameString,prizeBean.getItemList().get(0).getName(),
				MessageBean.MessageType_RecommendLowPrize_Template);
		
		ResultBean<String>resultExpect=new ResultBean<String>("success", "成功", null);

		when(categoryBll.getCategroyByCode(articleBean.getParentId())).thenReturn(categoryBean);
		when(articleBll.getListByMonthUserId(articleBean.getUserId())).thenReturn(articleBeans);
		when(wdgiftOrderBll.addWdgiftOrder(articleBean.getAccount(), articleBean.getCode(), prizeBean.getCode(), articleBean.getGameId(), 
				articleBean.getServerId(), articleBean.getServerName(), prizeBean.getItemList().get(0).getItemCode(),
				CategoryType.article.Value(),articleBean.getUserId()))
		.thenReturn(1);
		when(userBll.getByUserId(articleBean.getUserId())).thenReturn(null);
		when(userBll.addRecord(articleBean.getUserId(),new BigDecimal(0), Integer.parseInt(prizeBean.getItemList().get(0).getValue())))
		.thenReturn(1);
		when(itemBll.getItemByName("妙语连珠称号")).thenReturn(itemBeanTitle);
		when(wdgiftOrderBll.addWdgiftOrder(articleBean.getAccount(), articleBean.getCode(), prizeBean.getCode(), articleBean.getGameId(),
				articleBean.getServerId(), articleBean.getServerName(), itemBean.getItemCode(),"",articleBean.getUserId()))
		.thenReturn(1);
		when(userTitleBll.addUserTitle("", articleBean.getCode(), articleBean.getUserId(),prizeBean.getCode(), "妙语连珠称号"))
		.thenReturn(1);
		when(messageBll.add(message, MessageBean.MessageType.recommend.Value(), articleBean.getCode(),
				CategoryBean.CategoryType.article.Value(), articleBean.getUserId(),articleBean.getTitle()))
		.thenReturn(true);
		
		ResultBean<String> resultActual=recommendService.articlePrize(articleBean, prizeBean, recommendSlotNameString, prizeType,"hi");
		
		assertEquals(resultActual.getIsSuccess(),resultExpect.getIsSuccess());
		assertEquals(resultActual.getData(),resultExpect.getData());
		assertEquals(resultActual.getMessage(),resultExpect.getMessage());
		assertEquals(resultActual.getStatus(),resultExpect.getStatus());
	}
	
	//获得二星奖励并且已经获得过了6篇二星奖励
	@Test
	public void articlePrize_whenPrizeIsTwoStarAndPrizeTypeIsEmptyAndArticleCountIs6_thenSuccesss() throws Exception {

		String prizeType="";
		String recommendSlotNameString="猜你喜欢";
		
		ArticleBean articleBean=new ArticleBean();
		articleBean.setCode(1);
		articleBean.setParentId(11);
		articleBean.setTitle("文章");
		articleBean.setUserId(44545);
		articleBean.setPrizeId(2);
		articleBean.setGameId(2);
		articleBean.setServerId(311);
		articleBean.setServerName("问道测试");
		
		List<ArticleBean>articleBeans=new ArrayList<ArticleBean>();
		articleBeans.add(articleBean);
		articleBeans.add(articleBean);
		articleBeans.add(articleBean);
		articleBeans.add(articleBean);
		articleBeans.add(articleBean);
		articleBeans.add(articleBean);
		
		List<ItemBean> itemBeans=new ArrayList<ItemBean>();
		ItemBean itemBean =new ItemBean();
		itemBean.setCode(1);
		itemBean.setName("5000银元宝");
		itemBean.setItemCode("微信摇一摇活动奖励5000银元宝礼包(161128)");
		itemBean.setValue("5000");
		ItemBean itemBeanTitle =new ItemBean();
		itemBeanTitle.setCode(3);
		itemBeanTitle.setName("丹青妙笔称号");
		itemBeanTitle.setItemCode("丹青妙笔称号");
		itemBeanTitle.setValue("丹青妙笔称号");
		itemBeans.add(itemBean);
		itemBeans.add(itemBeanTitle);
		
		ItemBean itemBeanTitleExtra =new ItemBean();
		itemBeanTitleExtra.setCode(2);
		itemBeanTitleExtra.setName("点石成金称号");
		itemBeanTitleExtra.setItemCode("点石成金称号");
		itemBeanTitleExtra.setValue("点石成金称号");
		
		PrizeBean prizeBean=new PrizeBean();
		prizeBean.setCode(2);
		prizeBean.setName("2星级");
		prizeBean.setItemList(itemBeans);
		
		CategoryBean categoryBean =new CategoryBean();
		categoryBean.setTitle("十一周年");

		String message=MessageFormat.format(MessageBean.MessageType_Recommend_Template, 
				"十一周年", 
				articleBean.getTitle(),
				prizeBean.getName().subSequence(0, 1),recommendSlotNameString,prizeBean.getItemList().get(0).getName(),
				MessageBean.MessageType_RecommendLowPrize_Template);
		
		ResultBean<String>resultExpect=new ResultBean<String>("success", "成功", null);

		when(categoryBll.getCategroyByCode(articleBean.getParentId())).thenReturn(categoryBean);
		when(articleBll.getListByMonthUserId(articleBean.getUserId())).thenReturn(articleBeans);
		when(wdgiftOrderBll.addWdgiftOrder(articleBean.getAccount(), articleBean.getCode(), prizeBean.getCode(), articleBean.getGameId(), 
				articleBean.getServerId(), articleBean.getServerName(), prizeBean.getItemList().get(0).getItemCode(),
				CategoryType.article.Value(),articleBean.getUserId()))
		.thenReturn(1);
		when(userBll.getByUserId(articleBean.getUserId())).thenReturn(null);
		when(userBll.addRecord(articleBean.getUserId(),new BigDecimal(0), Integer.parseInt(prizeBean.getItemList().get(0).getValue())))
		.thenReturn(1);
		when(itemBll.getItemByName("点石成金称号")).thenReturn(itemBeanTitle);
		when(wdgiftOrderBll.addWdgiftOrder(articleBean.getAccount(), articleBean.getCode(), prizeBean.getCode(), articleBean.getGameId(),
				articleBean.getServerId(), articleBean.getServerName(), itemBean.getItemCode(),"",articleBean.getUserId()))
		.thenReturn(1);
		when(userTitleBll.addUserTitle("", articleBean.getCode(), articleBean.getUserId(),prizeBean.getCode(), "点石成金称号"))
		.thenReturn(1);
		when(messageBll.add(message, MessageBean.MessageType.recommend.Value(), articleBean.getCode(),
				CategoryBean.CategoryType.article.Value(), articleBean.getUserId(),articleBean.getTitle()))
		.thenReturn(true);
		
		ResultBean<String> resultActual=recommendService.articlePrize(articleBean, prizeBean, recommendSlotNameString, prizeType,"hi");
		
		assertEquals(resultActual.getIsSuccess(),resultExpect.getIsSuccess());
		assertEquals(resultActual.getData(),resultExpect.getData());
		assertEquals(resultActual.getMessage(),resultExpect.getMessage());
		assertEquals(resultActual.getStatus(),resultExpect.getStatus());
	}

	//获得三星奖励并且已经获得小于4篇三星奖励
	@Test
	public void articlePrize_whenPrizeIsThreeStarAndPrizeTypeIsEmpty_thenSuccesss() throws Exception {

		String prizeType="";
		String recommendSlotNameString="猜你喜欢";
		
		ArticleBean articleBean=new ArticleBean();
		articleBean.setCode(1);
		articleBean.setParentId(11);
		articleBean.setTitle("文章");
		articleBean.setUserId(44545);
		articleBean.setPrizeId(3);
		articleBean.setAccount("dessss");
		articleBean.setGameId(2);
		articleBean.setServerId(311);
		articleBean.setServerName("问道测试");
		
		List<ArticleBean>articleBeans=new ArrayList<ArticleBean>();
		articleBeans.add(articleBean);
		
		List<ItemBean> itemBeans=new ArrayList<ItemBean>();
		ItemBean itemBean =new ItemBean();
		itemBean.setCode(1);
		itemBean.setName("8000银元宝");
		itemBean.setItemCode("微信摇一摇活动奖励8000银元宝礼包(161128)");
		itemBean.setValue("8000");
		ItemBean itemBeanTitle =new ItemBean();
		itemBeanTitle.setCode(1);
		itemBeanTitle.setName("点石成金称号");
		itemBeanTitle.setItemCode("点石成金称号");
		itemBeanTitle.setValue("点石成金称号");
		itemBeans.add(itemBean);
		itemBeans.add(itemBeanTitle);
		
		PrizeBean prizeBean=new PrizeBean();
		prizeBean.setCode(3);
		prizeBean.setName("3星级");
		prizeBean.setItemList(itemBeans);
		
		CategoryBean categoryBean =new CategoryBean();
		categoryBean.setTitle("十一周年");

		String message=MessageFormat.format(MessageBean.MessageType_Recommend_Template, 
				"十一周年", 
				articleBean.getTitle(),
				prizeBean.getName().subSequence(0, 1),recommendSlotNameString,prizeBean.getItemList().get(0).getName(),
				MessageBean.MessageType_RecommendLowPrize_Template);
		
		ResultBean<String>resultExpect=new ResultBean<String>("success", "成功", null);

		when(categoryBll.getCategroyByCode(articleBean.getParentId())).thenReturn(categoryBean);
		when(articleBll.getListByMonthUserId(articleBean.getUserId())).thenReturn(articleBeans);
		when(wdgiftOrderBll.addWdgiftOrder(articleBean.getAccount(), articleBean.getCode(), prizeBean.getCode(), articleBean.getGameId(), 
				articleBean.getServerId(), articleBean.getServerName(), prizeBean.getItemList().get(0).getItemCode(),
				CategoryType.article.Value(),articleBean.getUserId()))
		.thenReturn(1);
		when(userBll.getByUserId(articleBean.getUserId())).thenReturn(null);
		when(userBll.addRecord(articleBean.getUserId(),new BigDecimal(0), Integer.parseInt(prizeBean.getItemList().get(0).getValue())))
		.thenReturn(1);
		when(messageBll.add(message, MessageBean.MessageType.recommend.Value(), articleBean.getCode(),
				CategoryBean.CategoryType.article.Value(), articleBean.getUserId(),articleBean.getTitle()))
		.thenReturn(true);
		
		ResultBean<String> resultActual=recommendService.articlePrize(articleBean, prizeBean, recommendSlotNameString, prizeType,"hi");
		
		assertEquals(resultActual.getIsSuccess(),resultExpect.getIsSuccess());
		assertEquals(resultActual.getData(),resultExpect.getData());
		assertEquals(resultActual.getMessage(),resultExpect.getMessage());
		assertEquals(resultActual.getStatus(),resultExpect.getStatus());
	}
	
	//获得三星奖励并且已经获得过了4篇三星奖励
	@Test
	public void articlePrize_whenPrizeIsThreeStarAndPrizeTypeIsEmptyAndArticleCountIs4_thenSuccesss() throws Exception {

		String prizeType="";
		String recommendSlotNameString="猜你喜欢";
		
		ArticleBean articleBean=new ArticleBean();
		articleBean.setCode(1);
		articleBean.setParentId(11);
		articleBean.setTitle("文章");
		articleBean.setUserId(44545);
		articleBean.setPrizeId(3);
		articleBean.setAccount("dessss");
		articleBean.setGameId(2);
		articleBean.setServerId(311);
		articleBean.setServerName("问道测试");
		
		List<ArticleBean>articleBeans=new ArrayList<ArticleBean>();
		articleBeans.add(articleBean);
		articleBeans.add(articleBean);
		articleBeans.add(articleBean);
		articleBeans.add(articleBean);
		
		List<ItemBean> itemBeans=new ArrayList<ItemBean>();
		ItemBean itemBean =new ItemBean();
		itemBean.setCode(1);
		itemBean.setName("8000银元宝");
		itemBean.setItemCode("微信摇一摇活动奖励8000银元宝礼包(161128)");
		itemBean.setValue("8000");
		ItemBean itemBeanTitle =new ItemBean();
		itemBeanTitle.setCode(3);
		itemBeanTitle.setName("点石成金称号");
		itemBeanTitle.setItemCode("点石成金称号");
		itemBeanTitle.setValue("点石成金称号");
		itemBeans.add(itemBean);
		itemBeans.add(itemBeanTitle);
		
		ItemBean itemBeanTitleExtra =new ItemBean();
		itemBeanTitleExtra.setCode(2);
		itemBeanTitleExtra.setName("新闻记者称号");
		itemBeanTitleExtra.setItemCode("新闻记者称号");
		itemBeanTitleExtra.setValue("新闻记者称号");
		
		PrizeBean prizeBean=new PrizeBean();
		prizeBean.setCode(3);
		prizeBean.setName("3星级");
		prizeBean.setItemList(itemBeans);
		
		CategoryBean categoryBean =new CategoryBean();
		categoryBean.setTitle("十一周年");

		String message=MessageFormat.format(MessageBean.MessageType_Recommend_Template, 
				"十一周年", 
				articleBean.getTitle(),
				prizeBean.getName().subSequence(0, 1),recommendSlotNameString,prizeBean.getItemList().get(0).getName(),
				MessageBean.MessageType_RecommendLowPrize_Template);
		
		ResultBean<String>resultExpect=new ResultBean<String>("success", "成功", null);

		when(categoryBll.getCategroyByCode(articleBean.getParentId())).thenReturn(categoryBean);
		when(articleBll.getListByMonthUserId(articleBean.getUserId())).thenReturn(articleBeans);
		when(wdgiftOrderBll.addWdgiftOrder(articleBean.getAccount(), articleBean.getCode(), prizeBean.getCode(), articleBean.getGameId(), 
				articleBean.getServerId(), articleBean.getServerName(), prizeBean.getItemList().get(0).getItemCode(),
				CategoryType.article.Value(),articleBean.getUserId()))
		.thenReturn(1);
		when(userBll.getByUserId(articleBean.getUserId())).thenReturn(null);
		when(userBll.addRecord(articleBean.getUserId(),new BigDecimal(0), Integer.parseInt(prizeBean.getItemList().get(0).getValue())))
		.thenReturn(1);
		when(itemBll.getItemByName("新闻记者称号")).thenReturn(itemBeanTitle);
		when(wdgiftOrderBll.addWdgiftOrder(articleBean.getAccount(), articleBean.getCode(), prizeBean.getCode(), articleBean.getGameId(),
				articleBean.getServerId(), articleBean.getServerName(), itemBean.getItemCode(),"",articleBean.getUserId()))
		.thenReturn(1);
		when(userTitleBll.addUserTitle("", articleBean.getCode(), articleBean.getUserId(),prizeBean.getCode(), "新闻记者称号"))
		.thenReturn(1);
		when(messageBll.add(message, MessageBean.MessageType.recommend.Value(), articleBean.getCode(),
				CategoryBean.CategoryType.article.Value(), articleBean.getUserId(),articleBean.getTitle()))
		.thenReturn(true);
		
		ResultBean<String> resultActual=recommendService.articlePrize(articleBean, prizeBean, recommendSlotNameString, prizeType,"hi");
		
		assertEquals(resultActual.getIsSuccess(),resultExpect.getIsSuccess());
		assertEquals(resultActual.getData(),resultExpect.getData());
		assertEquals(resultActual.getMessage(),resultExpect.getMessage());
		assertEquals(resultActual.getStatus(),resultExpect.getStatus());
	}
	
	//获得四星奖励
	@Test
	public void articlePrize_whenPrizeIsFourStarAndPrizeTypeIsCoin_thenSuccesss() throws Exception {

		String prizeType="silverCoins";
		String recommendSlotNameString="猜你喜欢";
		
		ArticleBean articleBean=new ArticleBean();
		articleBean.setCode(1);
		articleBean.setParentId(11);
		articleBean.setTitle("文章");
		articleBean.setUserId(44545);
		articleBean.setPrizeId(3);
		articleBean.setAccount("dessss");
		articleBean.setGameId(2);
		articleBean.setServerId(311);
		articleBean.setServerName("问道测试");
		
		List<ArticleBean>articleBeans=new ArrayList<ArticleBean>();
		articleBeans.add(articleBean);
		
		List<ItemBean> itemBeans=new ArrayList<ItemBean>();
		ItemBean itemBean =new ItemBean();
		itemBean.setCode(1);
		itemBean.setName("10000银元宝");
		itemBean.setItemCode("微信摇一摇活动奖励10000银元宝礼包(161128)");
		itemBean.setValue("10000");
		ItemBean itemBeanTitle =new ItemBean();
		itemBeanTitle.setCode(1);
		itemBeanTitle.setName("新闻记者称号");
		itemBeanTitle.setItemCode("新闻记者称号");
		itemBeanTitle.setValue("新闻记者称号");
		ItemBean itemBeanRmb =new ItemBean();
		itemBeanRmb.setCode(1);
		itemBeanRmb.setName("100现金");
		itemBeanRmb.setValue("100");
		itemBeans.add(itemBean);
		itemBeans.add(itemBeanTitle);
		itemBeans.add(itemBeanRmb);
		
		PrizeBean prizeBean=new PrizeBean();
		prizeBean.setCode(4);
		prizeBean.setName("4星级");
		prizeBean.setItemList(itemBeans);
		
		CategoryBean categoryBean =new CategoryBean();
		categoryBean.setTitle("十一周年");

		String message=MessageFormat.format(MessageBean.MessageType_Recommend_Template, 
				"十一周年", 
				articleBean.getTitle(),
				prizeBean.getName().subSequence(0, 1),recommendSlotNameString,prizeBean.getItemList().get(0).getName(),
				MessageBean.MessageType_RecommendLowPrize_Template);
		
		ResultBean<String>resultExpect=new ResultBean<String>("success", "成功", null);

		when(categoryBll.getCategroyByCode(articleBean.getParentId())).thenReturn(categoryBean);
		when(articleBll.getListByMonthUserId(articleBean.getUserId())).thenReturn(articleBeans);
		when(wdgiftOrderBll.addWdgiftOrder(articleBean.getAccount(), articleBean.getCode(), prizeBean.getCode(), articleBean.getGameId(), 
				articleBean.getServerId(), articleBean.getServerName(), prizeBean.getItemList().get(0).getItemCode(),
				CategoryType.article.Value(),articleBean.getUserId()))
		.thenReturn(1);
		when(userBll.getByUserId(articleBean.getUserId())).thenReturn(null);
		when(userBll.addRecord(articleBean.getUserId(),new BigDecimal(0), Integer.parseInt(prizeBean.getItemList().get(0).getValue())))
		.thenReturn(1);
		when(messageBll.add(message, MessageBean.MessageType.recommend.Value(), articleBean.getCode(),
				CategoryBean.CategoryType.article.Value(), articleBean.getUserId(),articleBean.getTitle()))
		.thenReturn(true);
		
		ResultBean<String> resultActual=recommendService.articlePrize(articleBean, prizeBean, recommendSlotNameString, prizeType,"hi");
		
		assertEquals(resultActual.getIsSuccess(),resultExpect.getIsSuccess());
		assertEquals(resultActual.getData(),resultExpect.getData());
		assertEquals(resultActual.getMessage(),resultExpect.getMessage());
		assertEquals(resultActual.getStatus(),resultExpect.getStatus());
	}
	
	//获得四星奖励
	@Test
	public void articlePrize_whenPrizeIsFourStarAndPrizeTypeIsRmb_thenSuccesss() throws Exception {

		String prizeType="rmb";
		String recommendSlotNameString="猜你喜欢";
		
		ArticleBean articleBean=new ArticleBean();
		articleBean.setCode(1);
		articleBean.setParentId(11);
		articleBean.setTitle("文章");
		articleBean.setUserId(44545);
		articleBean.setPrizeId(3);
		articleBean.setAccount("dessss");
		articleBean.setGameId(2);
		articleBean.setServerId(311);
		articleBean.setServerName("问道测试");
		
		List<ArticleBean>articleBeans=new ArrayList<ArticleBean>();
		articleBeans.add(articleBean);
		
		List<ItemBean> itemBeans=new ArrayList<ItemBean>();
		ItemBean itemBean =new ItemBean();
		itemBean.setCode(1);
		itemBean.setName("10000银元宝");
		itemBean.setItemCode("微信摇一摇活动奖励10000银元宝礼包(161128)");
		itemBean.setValue("10000");
		ItemBean itemBeanTitle =new ItemBean();
		itemBeanTitle.setCode(1);
		itemBeanTitle.setName("新闻记者称号");
		itemBeanTitle.setItemCode("新闻记者称号");
		itemBeanTitle.setValue("新闻记者称号");
		ItemBean itemBeanRmb =new ItemBean();
		itemBeanRmb.setCode(1);
		itemBeanRmb.setName("100现金");
		itemBeanRmb.setValue("100");
		itemBeans.add(itemBean);
		itemBeans.add(itemBeanTitle);
		itemBeans.add(itemBeanRmb);
		
		PrizeBean prizeBean=new PrizeBean();
		prizeBean.setCode(4);
		prizeBean.setName("4星级");
		prizeBean.setItemList(itemBeans);
		
		CategoryBean categoryBean =new CategoryBean();
		categoryBean.setTitle("十一周年");

		String message=MessageFormat.format(MessageBean.MessageType_Recommend_Template, 
				"十一周年", 
				articleBean.getTitle(),
				prizeBean.getName().subSequence(0, 1),recommendSlotNameString,prizeBean.getItemList().get(0).getName(),
				MessageBean.MessageType_RecommendLowPrize_Template);
		
		ResultBean<String>resultExpect=new ResultBean<String>("success", "成功", null);

		when(categoryBll.getCategroyByCode(articleBean.getParentId())).thenReturn(categoryBean);
		when(articleBll.getListByMonthUserId(articleBean.getUserId())).thenReturn(articleBeans);
		when(wdgiftOrderBll.addWdgiftOrder(articleBean.getAccount(), articleBean.getCode(), prizeBean.getCode(), articleBean.getGameId(), 
				articleBean.getServerId(), articleBean.getServerName(), prizeBean.getItemList().get(0).getItemCode(),
				CategoryType.article.Value(),articleBean.getUserId()))
		.thenReturn(1);
		when(userBll.getByUserId(articleBean.getUserId())).thenReturn(null);
		when(userBll.addRecord(articleBean.getUserId(),new BigDecimal(0), Integer.parseInt(prizeBean.getItemList().get(0).getValue())))
		.thenReturn(1);
		when(messageBll.add(message, MessageBean.MessageType.recommend.Value(), articleBean.getCode(),
				CategoryBean.CategoryType.article.Value(), articleBean.getUserId(),articleBean.getTitle()))
		.thenReturn(true);
		
		ResultBean<String> resultActual=recommendService.articlePrize(articleBean, prizeBean, recommendSlotNameString, prizeType,"hi");
		
		assertEquals(resultActual.getIsSuccess(),resultExpect.getIsSuccess());
		assertEquals(resultActual.getData(),resultExpect.getData());
		assertEquals(resultActual.getMessage(),resultExpect.getMessage());
		assertEquals(resultActual.getStatus(),resultExpect.getStatus());
	}
	
	//获得五星奖励
	@Test
	public void articlePrize_whenPrizeIsFiveStarAndPrizeTypeIsRmb_thenSuccesss() throws Exception {

		String prizeType="rmb";
		String recommendSlotNameString="猜你喜欢";
		
		ArticleBean articleBean=new ArticleBean();
		articleBean.setCode(1);
		articleBean.setParentId(11);
		articleBean.setTitle("文章");
		articleBean.setUserId(44545);
		articleBean.setPrizeId(3);
		articleBean.setAccount("dessss");
		articleBean.setGameId(2);
		articleBean.setServerId(311);
		articleBean.setServerName("问道测试");
		
		List<ArticleBean>articleBeans=new ArrayList<ArticleBean>();
		articleBeans.add(articleBean);
		
		List<ItemBean> itemBeans=new ArrayList<ItemBean>();
		ItemBean itemBean =new ItemBean();
		itemBean.setCode(1);
		itemBean.setName("10000银元宝");
		itemBean.setItemCode("微信摇一摇活动奖励10000银元宝礼包(161128)");
		itemBean.setValue("10000");
		ItemBean itemBeanTitle =new ItemBean();
		itemBeanTitle.setCode(1);
		itemBeanTitle.setName("新闻记者称号");
		itemBeanTitle.setItemCode("新闻记者称号");
		itemBeanTitle.setValue("新闻记者称号");
		ItemBean itemBeanRmb =new ItemBean();
		itemBeanRmb.setCode(1);
		itemBeanRmb.setName("100现金");
		itemBeanRmb.setValue("100");
		itemBeans.add(itemBean);
		itemBeans.add(itemBeanTitle);
		itemBeans.add(itemBeanRmb);
		
		PrizeBean prizeBean=new PrizeBean();
		prizeBean.setCode(5);
		prizeBean.setName("5星级");
		prizeBean.setItemList(itemBeans);
		
		CategoryBean categoryBean =new CategoryBean();
		categoryBean.setTitle("十一周年");

		String message=MessageFormat.format(MessageBean.MessageType_Recommend_Template, 
				"十一周年", 
				articleBean.getTitle(),
				prizeBean.getName().subSequence(0, 1),recommendSlotNameString,prizeBean.getItemList().get(0).getName(),
				MessageBean.MessageType_RecommendLowPrize_Template);
		
		ResultBean<String>resultExpect=new ResultBean<String>("success", "成功", null);

		when(categoryBll.getCategroyByCode(articleBean.getParentId())).thenReturn(categoryBean);
		when(articleBll.getListByMonthUserId(articleBean.getUserId())).thenReturn(articleBeans);
		when(wdgiftOrderBll.addWdgiftOrder(articleBean.getAccount(), articleBean.getCode(), prizeBean.getCode(), articleBean.getGameId(), 
				articleBean.getServerId(), articleBean.getServerName(), prizeBean.getItemList().get(0).getItemCode(),
				CategoryType.article.Value(),articleBean.getUserId()))
		.thenReturn(1);
		when(userBll.getByUserId(articleBean.getUserId())).thenReturn(null);
		when(userBll.addRecord(articleBean.getUserId(),new BigDecimal(0), Integer.parseInt(prizeBean.getItemList().get(0).getValue())))
		.thenReturn(1);
		when(messageBll.add(message, MessageBean.MessageType.recommend.Value(), articleBean.getCode(),
				CategoryBean.CategoryType.article.Value(), articleBean.getUserId(),articleBean.getTitle()))
		.thenReturn(true);
		
		ResultBean<String> resultActual=recommendService.articlePrize(articleBean, prizeBean, recommendSlotNameString, prizeType,"hi");
		
		assertEquals(resultActual.getIsSuccess(),resultExpect.getIsSuccess());
		assertEquals(resultActual.getData(),resultExpect.getData());
		assertEquals(resultActual.getMessage(),resultExpect.getMessage());
		assertEquals(resultActual.getStatus(),resultExpect.getStatus());
	}
	
	//无对应奖励信息
	@Test
	public void articlePrize_whenPrizeIsNull_thenSuccesss() throws Exception {

		String prizeType="rmb";
		String recommendSlotNameString="猜你喜欢";
		
		ArticleBean articleBean=new ArticleBean();
		articleBean.setCode(1);
		articleBean.setParentId(11);
		
		articleBean.setTitle("文章");
		articleBean.setUserId(44545);
		articleBean.setPrizeId(6);
		articleBean.setAccount("dessss");
		articleBean.setGameId(2);
		articleBean.setServerId(311);
		articleBean.setServerName("问道测试");
		
		List<ArticleBean>articleBeans=new ArrayList<ArticleBean>();
		articleBeans.add(articleBean);
		
		List<ItemBean> itemBeans=new ArrayList<ItemBean>();
		ItemBean itemBean =new ItemBean();
		itemBean.setCode(1);
		itemBean.setName("10000银元宝");
		itemBean.setItemCode("微信摇一摇活动奖励10000银元宝礼包(161128)");
		itemBean.setValue("10000");
		ItemBean itemBeanTitle =new ItemBean();
		itemBeanTitle.setCode(1);
		itemBeanTitle.setName("新闻记者称号");
		itemBeanTitle.setItemCode("新闻记者称号");
		itemBeanTitle.setValue("新闻记者称号");
		ItemBean itemBeanRmb =new ItemBean();
		itemBeanRmb.setCode(1);
		itemBeanRmb.setName("100现金");
		itemBeanRmb.setValue("100");
		itemBeans.add(itemBean);
		itemBeans.add(itemBeanTitle);
		itemBeans.add(itemBeanRmb);
		
		PrizeBean prizeBean=new PrizeBean();
		prizeBean.setCode(6);
		prizeBean.setName("6星级");
		prizeBean.setItemList(itemBeans);
		
		CategoryBean categoryBean =new CategoryBean();
		categoryBean.setTitle("十一周年");

		
		ResultBean<String>resultExpect=new ResultBean<String>("success", "成功", null);

		when(categoryBll.getCategroyByCode(articleBean.getParentId())).thenReturn(categoryBean);
		when(articleBll.getListByMonthUserId(articleBean.getUserId())).thenReturn(articleBeans);
		
		ResultBean<String> resultActual=recommendService.articlePrize(articleBean, prizeBean, recommendSlotNameString, prizeType,"hi");
		
		assertEquals(resultActual.getIsSuccess(),resultExpect.getIsSuccess());
		assertEquals(resultActual.getData(),resultExpect.getData());
		assertEquals(resultActual.getMessage(),resultExpect.getMessage());
		assertEquals(resultActual.getStatus(),resultExpect.getStatus());
	}
	
	//文章状态未审核
	@Test
	public void addArticleRecommend_whenArticleStateIsUnreviewd_thenFailed() throws Exception {

		String prizeType="rmb";
		String recommendSlotId="1,2";
		String operator="devtest01";
		
		ArticleBean articleBean=new ArticleBean();
		articleBean.setCode(1);
		articleBean.setParentId(11);
		
		articleBean.setTitle("文章");
		articleBean.setUserId(44545);
		articleBean.setPrizeId(6);
		articleBean.setAccount("dessss");
		articleBean.setGameId(2);
		articleBean.setServerId(311);
		articleBean.setServerName("问道测试");
		articleBean.setStatus(State.unreviewd.Value());

		PrizeBean prizeBean=new PrizeBean();
		prizeBean.setCode(1);

		ResultBean<String>resultExpect=new ResultBean<String>("incorrect-articleStatus", "图文状态不是审核通过并且展示", null);
		
		ResultBean<String> resultActual=recommendService.addArticleRecommend(recommendSlotId, articleBean, prizeBean, operator, prizeType);
		
		assertEquals(resultActual.getIsSuccess(),resultExpect.getIsSuccess());
		assertEquals(resultActual.getData(),resultExpect.getData());
		assertEquals(resultActual.getMessage(),resultExpect.getMessage());
		assertEquals(resultActual.getStatus(),resultExpect.getStatus());
	}
	
	//推荐位不存在
	@Test
	public void addArticleRecommend_whenRecommendSlotIsNull_thenFailed() throws Exception {

		String prizeType="rmb";
		String recommendSlotId="1,2";
		String operator="devtest01";
		
		ArticleBean articleBean=new ArticleBean();
		articleBean.setCode(1);
		articleBean.setParentId(11);
		
		articleBean.setTitle("文章");
		articleBean.setUserId(44545);
		articleBean.setPrizeId(6);
		articleBean.setAccount("dessss");
		articleBean.setGameId(2);
		articleBean.setServerId(311);
		articleBean.setServerName("问道测试");
		articleBean.setStatus(State.passed.Value());

		PrizeBean prizeBean=new PrizeBean();
		prizeBean.setCode(1);

		ResultBean<String>resultExpect=new ResultBean<String>("incorrect-slot", "推荐位不存在", null);

		when(recommendSlotBll.getRecommendSlotByCode(1)).thenReturn(null);
		
		ResultBean<String> resultActual=recommendService.addArticleRecommend(recommendSlotId, articleBean, prizeBean, operator, prizeType);
		
		assertEquals(resultActual.getIsSuccess(),resultExpect.getIsSuccess());
		assertEquals(resultActual.getData(),resultExpect.getData());
		assertEquals(resultActual.getMessage(),resultExpect.getMessage());
		assertEquals(resultActual.getStatus(),resultExpect.getStatus());
	}
	
	//增加推荐奖励成功
	@Test
	public void addArticleRecommend_whenAddRecommend_thenSuccess() throws Exception {

		String prizeType="rmb";
		String recommendSlotId="1";
		String operator="devtest01";
		
		List<String>recommendSlotNameString= new ArrayList<String>();
		
		ArticleBean articleBean=new ArticleBean();
		articleBean.setCode(1);
		articleBean.setParentId(11);
		
		articleBean.setTitle("文章");
		articleBean.setUserId(44545);
		articleBean.setPrizeId(6);
		articleBean.setAccount("dessss");
		articleBean.setGameId(2);
		articleBean.setServerId(311);
		articleBean.setServerName("问道测试");
		articleBean.setStatus(State.passed.Value());
		articleBean.setCover("img");

		List<ArticleBean>articleBeans=new ArrayList<ArticleBean>();
		articleBeans.add(articleBean);
		
		List<ItemBean> itemBeans=new ArrayList<ItemBean>();
		ItemBean itemBean =new ItemBean();
		itemBean.setCode(1);
		itemBean.setName("2000银元宝");
		itemBean.setItemCode("微信摇一摇活动奖励500银元宝礼包(161128)");
		itemBean.setValue("2000");
		itemBeans.add(itemBean);
		
		PrizeBean prizeBean=new PrizeBean();
		prizeBean.setCode(1);
		prizeBean.setName("1星级");
		prizeBean.setItemList(itemBeans);
		
		CategoryBean categoryBean =new CategoryBean();
		categoryBean.setTitle("十一周年");
		
		String message=MessageFormat.format(MessageBean.MessageType_Recommend_Template, 
				"十一周年", 
				articleBean.getTitle(),
				prizeBean.getName().subSequence(0, 1),recommendSlotNameString,prizeBean.getItemList().get(0).getName(),
				MessageBean.MessageType_RecommendLowPrize_Template);
		
		RecommendSlotBean recommendSlotBean=new RecommendSlotBean();
		recommendSlotBean.setSlotGroup("图文驿站");

		ResultBean<String>resultExpect=new ResultBean<String>("success", "成功", null);

		when(recommendSlotBll.getRecommendSlotByCode(1)).thenReturn(recommendSlotBean);
		when(recommendBll.addRecommend(CategoryBean.CategoryType.article.Value(), articleBean.getCode(),1,
				articleBean.getTitle(), articleBean.getCover(), prizeBean.getCode()))
		.thenReturn(1);
		when(recommendContentBll.add(new RecommendContentBean(1, articleBean.getTitle(), 
				"", articleBean.getCover(), articleBean.getCode())))
		.thenReturn(1);
		
		when(prizeBll.getPrizeInfo(prizeBean,prizeType)).thenReturn("一星级2000银元宝");
		
		when(articleBll.editArticleStatusById(articleBean.getCode(), ArticleBean.State.recommended.Value(),
				"一星级2000银元宝", prizeBean.getCode()))
		.thenReturn(1);
		when(reviewLogBll.insert(articleBean.getCode(),CategoryBean.CategoryType.article,
				State.valueOf(articleBean.getStatus()),ArticleBean.State.recommended,operator))
		.thenReturn(true);
		
		recommendSlotNameString.add(recommendSlotBean.getSlotGroup());
		
		when(categoryBll.getCategroyByCode(articleBean.getParentId())).thenReturn(categoryBean);
		when(articleBll.getListByMonthUserId(articleBean.getUserId())).thenReturn(articleBeans);
		when(wdgiftOrderBll.addWdgiftOrder(articleBean.getAccount(), articleBean.getCode(), prizeBean.getCode(), articleBean.getGameId(), 
				articleBean.getServerId(), articleBean.getServerName(), prizeBean.getItemList().get(0).getItemCode(),
				CategoryType.article.Value(),articleBean.getUserId()))
		.thenReturn(1);
		when(userBll.getByUserId(articleBean.getUserId())).thenReturn(null);
		when(userBll.addRecord(articleBean.getUserId(),new BigDecimal(0), Integer.parseInt(prizeBean.getItemList().get(0).getValue())))
		.thenReturn(1);
		when(messageBll.add(message, MessageBean.MessageType.recommend.Value(), articleBean.getCode(),
				CategoryBean.CategoryType.article.Value(), articleBean.getUserId(),articleBean.getTitle()))
		.thenReturn(true);
		
		ResultBean<String> resultActual=recommendService.addArticleRecommend(recommendSlotId, articleBean, prizeBean, operator, prizeType);
		
		assertEquals(resultActual.getMessage(),resultExpect.getMessage());
		assertEquals(resultActual.getIsSuccess(),resultExpect.getIsSuccess());
		assertEquals(resultActual.getData(),resultExpect.getData());		
		assertEquals(resultActual.getStatus(),resultExpect.getStatus());
	}
	
	//获取推荐管理列表不存在数据
	@Test
	public void getArticleManagementList_whenArticleIsCount_thenSuccess() throws Exception {

		int slotId=1;
		int prizeId=1;
		String authorType="player";
		int pageSize=10;
		int currentPage=1;

		PageBean<Map<String, Object>>resultExpect=PageBean.createPage("success", 0, currentPage, pageSize, null, "成功");

		when(articleBll.getRecommendManagementCount(slotId,prizeId, authorType)).thenReturn(0);

		PageBean<Map<String, Object>> resultActual=recommendService.getArticleManagementList(slotId, prizeId, authorType, pageSize, currentPage);
		
		assertEquals(resultActual.getMessage(),resultExpect.getMessage());
		assertEquals(resultActual.getIsSuccess(),resultExpect.getIsSuccess());
		assertEquals(resultActual.getDataSet(),resultExpect.getDataSet());		
		assertEquals(resultActual.getStatus(),resultExpect.getStatus());
	}
	
	//获取推荐管理列表存在数据（玩家上传）
	@Test
	public void getArticleManagementList_whenPlayerArticleListExist_thenSuccess() throws Exception {

		int slotId=1;
		int prizeId=1;
		String authorType="player";
		int pageSize=10;
		int currentPage=1;
		
		ArticleBean articleBean=new ArticleBean();
		articleBean.setCode(1);
		articleBean.setRecommmendTime(null);
		articleBean.setTitle("测试标题");
		articleBean.setAuthorType(ArticleBean.AuthorType.PLAYER.getIndex());
		articleBean.setCover("http://img.gyyx.cn/temp1/M00/00/4A/wKgGplkJrPSASAyPAAAIR_VRAgs214.jpg");
		articleBean.setPrizeName(null);
		
		List<ArticleBean>list=new ArrayList<ArticleBean>();
		list.add(articleBean);
		
		List<Map<String, Object>> resultExpectList=FluentIterable.from(list)
				.transform(new Function<ArticleBean, Map<String, Object>>() {

					@Override
					public Map<String, Object> apply(ArticleBean info) {
						
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
						String strDate="无";
						if(info.getRecommmendTime()!=null){
							strDate = format.format(info.getRecommmendTime());
						}
						
						Map<String, Object> map=new HashMap<String, Object>();
						map.put("code", info.getCode());
						map.put("title", info.getTitle());
						map.put("authorType", info.getAuthorType().equals("player")?"玩家":"官方");
						map.put("cover", info.getCover());
						map.put("name", info.getPrizeName()==null?"无":info.getPrizeName());
						map.put("recommmendTime", strDate);
						return map;
					}
				}).toList();

		PageBean<Map<String, Object>>resultExpect=PageBean.createPage("success", 0, currentPage, pageSize, resultExpectList, "成功");

		when(articleBll.getRecommendManagementCount(slotId,prizeId, authorType)).thenReturn(1);
		when(articleBll.getRecommendManagementList(slotId, prizeId, authorType, pageSize, currentPage)).thenReturn(list);

		PageBean<Map<String, Object>> resultActual=recommendService.getArticleManagementList(slotId, prizeId, authorType, pageSize, currentPage);
		
		assertEquals(resultActual.getMessage(),resultExpect.getMessage());
		assertEquals(resultActual.getIsSuccess(),resultExpect.getIsSuccess());
		assertEquals(resultActual.getDataSet(),resultExpect.getDataSet());		
		assertEquals(resultActual.getStatus(),resultExpect.getStatus());
	}
	
	//获取推荐管理列表存在数据（官方上传）
	@Test
	public void getArticleManagementList_whenOFFICIALArticleListExist_thenSuccess() throws Exception {

		int slotId=1;
		int prizeId=1;
		String authorType="player";
		int pageSize=10;
		int currentPage=1;
		
		ArticleBean articleBean=new ArticleBean();
		articleBean.setCode(1);
		articleBean.setRecommmendTime(new Date());
		articleBean.setTitle("测试标题");
		articleBean.setAuthorType(ArticleBean.AuthorType.OFFICIAL.getIndex());
		articleBean.setCover("http://img.gyyx.cn/temp1/M00/00/4A/wKgGplkJrPSASAyPAAAIR_VRAgs214.jpg");
		articleBean.setPrizeName("1星级");
		
		List<ArticleBean>list=new ArrayList<ArticleBean>();
		list.add(articleBean);
		
		List<Map<String, Object>> resultExpectList=FluentIterable.from(list)
				.transform(new Function<ArticleBean, Map<String, Object>>() {

					@Override
					public Map<String, Object> apply(ArticleBean info) {
						
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
						String strDate="无";
						if(info.getRecommmendTime()!=null){
							strDate = format.format(info.getRecommmendTime());
						}
						
						Map<String, Object> map=new HashMap<String, Object>();
						map.put("code", info.getCode());
						map.put("title", info.getTitle());
						map.put("authorType", info.getAuthorType().equals("player")?"玩家":"官方");
						map.put("cover", info.getCover());
						map.put("name", info.getPrizeName()==null?"无":info.getPrizeName());
						map.put("recommmendTime", strDate);
						return map;
					}
				}).toList();

		PageBean<Map<String, Object>>resultExpect=PageBean.createPage("success", 0, currentPage, pageSize, resultExpectList, "成功");

		when(articleBll.getRecommendManagementCount(slotId,prizeId, authorType)).thenReturn(1);
		when(articleBll.getRecommendManagementList(slotId, prizeId, authorType, pageSize, currentPage)).thenReturn(list);

		PageBean<Map<String, Object>> resultActual=recommendService.getArticleManagementList(slotId, prizeId, authorType, pageSize, currentPage);
		
		assertEquals(resultActual.getMessage(),resultExpect.getMessage());
		assertEquals(resultActual.getIsSuccess(),resultExpect.getIsSuccess());
		assertEquals(resultActual.getDataSet(),resultExpect.getDataSet());		
		assertEquals(resultActual.getStatus(),resultExpect.getStatus());
	}
	//获取文章对应的推荐位  成功获取
	@Test
	    public void getSlotsByContentId_whenSuccess_thenReturnResultList() throws Exception {
	        int contentId =1;
	        // 期望结果
	        String resultExpect = "存在同名的章节名称,请更换";
	        // mock过程
	        RecommendSlotBean recommendSlotBean=new RecommendSlotBean();
	        recommendSlotBean.setCode(1);
	        recommendSlotBean.setSlotGroup("1");
	        recommendSlotBean.setSlot("1");
                List<RecommendSlotBean>list=new ArrayList<RecommendSlotBean>();
                list.add(recommendSlotBean);
                
                Map<String, Object> map = new HashMap<>();
                map.put("code", 1);
                map.put("slotGroup", "1");
                map.put("slot", "1");
                map.put("code", 1);
                List<Map<String, Object>> resultList = new ArrayList<>();
                resultList.add(map);
	        
	        when(recommendSlotBll.getRecommendSlotList(CategoryType.article.Value())).thenReturn(list);
	        when(recommendSlotBll.getRecommendSlotList("")).thenReturn(new ArrayList<>());
	        when(recommendSlotBll.getRecommendSlotList("website")).thenReturn(new ArrayList<>());
	        when(recommendBll.getSlotsByContentId(contentId,CategoryType.article.Value())).thenReturn(new ArrayList<>());
	        
	        List<Map<String,Object>> byContentId = recommendService.getSlotsByContentId(contentId);
	        // 调用函数
	       // List<Map<String, Object>> slotsByContentId = recommendService.getSlotsByContentId(contentId);
	        // 断言结果
	       assertEquals(byContentId.get(0).get("code"), resultList.get(0).get("code"));
	        
	    }
	@Test
        public void changeSlotsByContentId_whenArticleBeanisNull_thenReturnArticleNull() throws Exception {
            int contentId =1;
            String recommendSlotId ="2";
            // 期望结果
            String resultExpect = "图文不存在";
            // mock过程
           
            
            when(articleBll.getArticleById(contentId)).thenReturn(null);
            
            String resultActual = recommendService.changeSlotsByContentId(contentId, recommendSlotId).getMessage();
            // 调用函数
           // List<Map<String, Object>> slotsByContentId = recommendService.getSlotsByContentId(contentId);
            // 断言结果
           assertEquals(resultActual,resultExpect);
            
        }
	@Test
	public void changeSlotsByContentId2_whenRecommendisNull_thenReturnArticleIsNotRecommend() throws Exception {
	    int contentId =1;
	    String recommendSlotId ="2";
	    // 期望结果
	    String resultExpect = "图文状态不是推荐状态";
	    // mock过程
	    ArticleBean articleBean=new ArticleBean();
            articleBean.setCode(1);
//            articleBean.setStatus("recommended");
            articleBean.setStatus("recommended11");
	    when(articleBll.getArticleById(contentId)).thenReturn(articleBean);
	    
	    String resultActual = recommendService.changeSlotsByContentId(contentId, recommendSlotId).getMessage();
	    // 调用函数
	    // List<Map<String, Object>> slotsByContentId = recommendService.getSlotsByContentId(contentId);
	    // 断言结果
	    assertEquals(resultActual,resultExpect);
	    
	}
	@Test
	public void changeSlotsByContentId3_whenRecommendBeanisNull_thenReturnRecommendNull() throws Exception {
	    int contentId =1;
	    String recommendSlotId ="2";
	    // 期望结果
	    String resultExpect = "推荐位不存在";
	    // mock过程
	    ArticleBean articleBean=new ArticleBean();
	    articleBean.setCode(1);
            articleBean.setStatus("recommended");
	    when(articleBll.getArticleById(contentId)).thenReturn(articleBean);
	    
	    String resultActual = recommendService.changeSlotsByContentId(contentId, recommendSlotId).getMessage();
	    // 调用函数
	    // List<Map<String, Object>> slotsByContentId = recommendService.getSlotsByContentId(contentId);
	    // 断言结果
	    assertEquals(resultActual,resultExpect);
	    
	}
	@Test
	public void changeSlotsByContentId4_whenSlotsOldIsNotNull_thenReturnSuccess() throws Exception {
	    int contentId =1;
	    String recommendSlotId ="2";
	    // 期望结果
	    String resultExpect = "成功";
	    // mock过程
	    ArticleBean articleBean=new ArticleBean();
	    articleBean.setCode(1);
	    articleBean.setStatus("recommended");
	    List<Integer>slotsOld= new ArrayList<>();
	    slotsOld.add(1);
	    
	    when(articleBll.getArticleById(contentId)).thenReturn(articleBean);
	    when(recommendBll.getSlotsByContentId(contentId,CategoryType.article.Value())).thenReturn(slotsOld);
	    when(recommendSlotBll.getRecommendSlotByCode(anyInt())).thenReturn(new RecommendSlotBean());
	    
	    String resultActual = recommendService.changeSlotsByContentId(contentId, recommendSlotId).getMessage();
	    // 调用函数
	    // List<Map<String, Object>> slotsByContentId = recommendService.getSlotsByContentId(contentId);
	    // 断言结果
	    assertEquals(resultActual,resultExpect);
	    
	}
	@Test
	public void getArticleRecordList_whenRecommendSlotisNotNull_thenReturnSuccess() throws Exception {
	    int prizeId =1;
	    int pageSize =1;
	    int currentPage =1;
	    Date date = new Date();
	    String recommendSlotId ="2";
	    // 期望结果
	    String resultExpect = "成功";
	    // mock过程
	    ArticleBean articleBean=new ArticleBean();
	    articleBean.setCode(1);
	    articleBean.setStatus("recommended");
	    List<Integer>slotsOld= new ArrayList<>();
	    slotsOld.add(1);
	    
	    when(articleBll.getRecommendRecordCount(anyInt(), any(), any())).thenReturn(-1);
	    when(recommendSlotBll.getRecommendSlotByCode(anyInt())).thenReturn(new RecommendSlotBean());
	    
	    String resultActual = recommendService.getArticleRecordList(prizeId, date, date, pageSize, currentPage).getMessage();
	    // 调用函数
	    // List<Map<String, Object>> slotsByContentId = recommendService.getSlotsByContentId(contentId);
	    // 断言结果
	    assertEquals(resultActual,resultExpect);
	    
	}
	@Test
	public void getArticleRecordList2_whenAnyIsNotNull_thenReturnSuccess() throws Exception {
	    int prizeId =1;
	    int pageSize =1;
	    int currentPage =1;
	    Date date = new Date();
	    String recommendSlotId ="2";
	    // 期望结果
	    String resultExpect = "成功";
	    // mock过程
	    ArticleBean articleBean=new ArticleBean();
	    articleBean.setCode(1);
	    articleBean.setStatus("recommended");
	    List<ArticleBean>list= new ArrayList<>();
	    list.add(articleBean);
	    
	    when(articleBll.getRecommendRecordCount(anyInt(), any(), any())).thenReturn(1);
	    when(articleBll.getRecommendRecordList(prizeId,date, date, pageSize, currentPage)).thenReturn(list);
	    
	    String resultActual = recommendService.getArticleRecordList(prizeId, date, date, pageSize, currentPage).getMessage();
	    // 调用函数
	    // List<Map<String, Object>> slotsByContentId = recommendService.getSlotsByContentId(contentId);
	    // 断言结果
	    assertEquals(resultActual,resultExpect);
	    
	}
	@Test
	public void getArticleRecordList3_whenParmIsTrue_thenReturnSuccess() throws Exception {
	    int prizeId =1;
	    int pageSize =1;
	    int currentPage =1;
	    Date date = new Date();
	    String recommendSlotId ="2";
	    // 期望结果
	    String resultExpect = "成功";
	    // mock过程
	    ArticleBean articleBean=new ArticleBean();
	    articleBean.setCode(1);
	    articleBean.setStatus("recommended");
	    articleBean.setRecommmendTime(date);
	    List<ArticleBean>list= new ArrayList<>();
	    list.add(articleBean);
	    
	    when(articleBll.getRecommendRecordCount(anyInt(), any(), any())).thenReturn(1);
	    when(articleBll.getRecommendRecordList(prizeId,date, date, pageSize, currentPage)).thenReturn(list);
	    
	    String resultActual = recommendService.getArticleRecordList(prizeId, date, date, pageSize, currentPage).getMessage();
	    // 调用函数
	    // List<Map<String, Object>> slotsByContentId = recommendService.getSlotsByContentId(contentId);
	    // 断言结果
	    assertEquals(resultActual,resultExpect);
	    
	}
	@Test
	public void novelRecommend_whenNovelIdisNull_thenReturnNovelNull() throws Exception {
	    int novelId =1;
	    int recommendSlotId =1;
	    int currentPage =1;
	    Date date = new Date();
	    // 期望结果
	    String resultExpect = "小说不存在";
	    // mock过程
	    ArticleBean articleBean=new ArticleBean();
	    articleBean.setCode(1);
	    articleBean.setStatus("recommended");
	    articleBean.setRecommmendTime(date);
	    List<ArticleBean>list= new ArrayList<>();
	    list.add(articleBean);
	    
	    when(novelBll.get(novelId)).thenReturn(null);
	    
	    String resultActual = recommendService.novelRecommend(recommendSlotId, novelId).getMessage();
	    // 调用函数
	    // List<Map<String, Object>> slotsByContentId = recommendService.getSlotsByContentId(contentId);
	    // 断言结果
	    assertEquals(resultActual,resultExpect);
	    
	}
	@Test
	public void novelRecommend2_whenRecommendisNull_thenReturnRecommendNull() throws Exception {
	    int novelId =1;
	    int recommendSlotId =1;
	    int currentPage =1;
	    Date date = new Date();
	    // 期望结果
	    String resultExpect = "推荐位不存在";
	    // mock过程
	    NovelBean novelBean=new NovelBean();
	    novelBean.setCode(1);
	    novelBean.setStatus("recommended");
	     
	    
	    when(novelBll.get(novelId)).thenReturn(new NovelBean());
	    when(recommendSlotBll.getRecommendSlotByCode(recommendSlotId)).thenReturn(null);
	    
	    String resultActual = recommendService.novelRecommend(recommendSlotId, novelId).getMessage();
	    // 调用函数
	    // List<Map<String, Object>> slotsByContentId = recommendService.getSlotsByContentId(contentId);
	    // 断言结果
	    assertEquals(resultActual,resultExpect);
	    
	}
	@Test
	public void novelRecommend3_whenNovelBeanisNotNull_thenReturnNovelIsRecommend() throws Exception {
	    int novelId =1;
	    int recommendSlotId =1;
	    int currentPage =1;
	    Date date = new Date();
	    // 期望结果
	    String resultExpect = "该小说已推荐";
	    // mock过程
	    NovelBean novelBean=new NovelBean();
	    novelBean.setCode(1);
	    novelBean.setStatus("recommended");
	    
	    List<Integer>  list= new ArrayList<>();
	    list.add(1);
	    
	    when(novelBll.get(novelId)).thenReturn(new NovelBean());
	    when(recommendSlotBll.getRecommendSlotByCode(recommendSlotId)).thenReturn(new RecommendSlotBean());
	    when(recommendBll.getSlotsByContentId(novelId,CategoryType.novel.Value())).thenReturn(list);
	    
	    String resultActual = recommendService.novelRecommend(recommendSlotId, novelId).getMessage();
	    // 调用函数
	    // List<Map<String, Object>> slotsByContentId = recommendService.getSlotsByContentId(contentId);
	    // 断言结果
	    assertEquals(resultActual,resultExpect);
	    
	}
	@Test
	public void novelRecommend4_whenNovelIdisYes_thenReturnAddSuccess() throws Exception {
	    int novelId =1;
	    int recommendSlotId =1;
	    int currentPage =1;
	    Date date = new Date();
	    // 期望结果
	    String resultExpect = "推荐位添加成功";
	    // mock过程
	    NovelBean novelBean=new NovelBean();
	    novelBean.setCode(1);
	    novelBean.setStatus("recommended");
	    
	    List<Integer>  list= new ArrayList<>();
	    
	    when(novelBll.get(novelId)).thenReturn(new NovelBean());
	    when(recommendSlotBll.getRecommendSlotByCode(recommendSlotId)).thenReturn(new RecommendSlotBean());
	    when(recommendBll.getSlotsByContentId(novelId,CategoryType.novel.Value())).thenReturn(list);
	    
	    String resultActual = recommendService.novelRecommend(recommendSlotId, novelId).getMessage();
	    // 调用函数
	    // List<Map<String, Object>> slotsByContentId = recommendService.getSlotsByContentId(contentId);
	    // 断言结果
	    assertEquals(resultActual,resultExpect);
	    
	}
	@Test
	public void getNovelManagementList_whenSuccess_thenReturnQuerySuccess() throws Exception {
	    int novelId =1;
	    int recommendSlotId =1;
	    int currentPage =1;
	    Date date = new Date();
	    // 期望结果
	    String resultExpect = "查询小说推荐成功";
	    // mock过程
	    NovelBean novelBean=new NovelBean();
	    novelBean.setCode(1);
	    novelBean.setStatus("recommended");
	    
	    List<Integer>  list= new ArrayList<>();
	    
	    when(novelBll.get(novelId)).thenReturn(new NovelBean());
	    when(recommendSlotBll.getRecommendSlotByCode(recommendSlotId)).thenReturn(new RecommendSlotBean());
	    when(recommendBll.getSlotsByContentId(novelId,CategoryType.novel.Value())).thenReturn(list);
	    
	    String resultActual = recommendService.getNovelManagementList(recommendSlotId, 1, 5).getMessage();
	    // 调用函数
	    // List<Map<String, Object>> slotsByContentId = recommendService.getSlotsByContentId(contentId);
	    // 断言结果
	    assertEquals(resultActual,resultExpect);
	    
	}
	@Test
	public void getNovelSlotsByContentId_whenSuccess_thenReturnArticleNull() throws Exception {
	    int novelId =1;
	    int recommendSlotId =1;
	    int currentPage =1;
	    Date date = new Date();
	    // 期望结果
	   // String resultExpect = "";
	    // mock过程
	    NovelBean novelBean=new NovelBean();
	    novelBean.setCode(1);
	    novelBean.setStatus("recommended");
	    RecommendSlotBean bean = new RecommendSlotBean();
	    bean.setCode(1);
	    List<RecommendSlotBean> recommendSlotList = new ArrayList<>();
	    recommendSlotList.add(bean);
	    List<Map<String, Object>> resultExpect= new ArrayList<>();
	    
	    when(novelBll.get(novelId)).thenReturn(new NovelBean());
	    when(recommendSlotBll.getRecommendSlotByCode(recommendSlotId)).thenReturn(new RecommendSlotBean());
	    when(recommendSlotBll.getRecommendSlotList("novel")).thenReturn(recommendSlotList);
	    
	    Object resultList = recommendService.getNovelSlotsByContentId(recommendSlotId);
	    // 调用函数
	    // List<Map<String, Object>> slotsByContentId = recommendService.getSlotsByContentId(contentId);
	    // 断言结果
	    assertTrue(resultList!=null);
	    
	}
	
	@Test
        public void changeNovelSlotsByContentId_whenArticleBeanisNull_thenReturnArticleNull() throws Exception {
            int contentId =1;
            String recommendSlotId ="1";
            int currentPage =1;
            Date date = new Date();
            // 期望结果
            String resultExpect = "小说不存在";
            // mock过程
            NovelBean novelBean=new NovelBean();
            novelBean.setCode(1);
            novelBean.setStatus("recommended");
            
            List<Integer>  list= new ArrayList<>();
            list.add(1);
            
//            when(novelBll.get(novelId)).thenReturn(new NovelBean());
//            when(recommendSlotBll.getRecommendSlotByCode(recommendSlotId)).thenReturn(new RecommendSlotBean());
//            when(recommendBll.getSlotsByContentId(novelId,CategoryType.novel.Value())).thenReturn(list);
//            
            String resultActual = recommendService.changeNovelSlotsByContentId(contentId, recommendSlotId).getMessage();
            // 调用函数
            // List<Map<String, Object>> slotsByContentId = recommendService.getSlotsByContentId(contentId);
            // 断言结果
            assertEquals(resultActual,resultExpect);
            
        }
	@Test
	public void changeNovelSlotsByContentId_whenslotsOldisNull_thenReturnStatusIsNO() throws Exception {
	    int contentId =1;
	    String recommendSlotId ="1";
	    int currentPage =1;
	    Date date = new Date();
	    // 期望结果
	    String resultExpect = "推荐位不存在";
	    // mock过程
	    NovelBean novelBean=new NovelBean();
	    novelBean.setCode(1);
	    novelBean.setStatus("recommended");
	    
	    List<Integer>  list= new ArrayList<>();
	    list.add(1);
	    ArticleBean articleBean=new ArticleBean();
            articleBean.setCode(1);
            articleBean.setStatus("recommended");
            List<Integer>slotsOld= new ArrayList<>();
            slotsOld.add(2);
            
            when(novelBll.get(contentId)).thenReturn(new NovelBean());
           when(recommendBll.getSlotsByContentId(contentId,CategoryType.novel.Value())).thenReturn(slotsOld);
//            when(recommendBll.getSlotsByContentId(novelId,CategoryType.novel.Value())).thenReturn(list);
//            
	    String resultActual = recommendService.changeNovelSlotsByContentId(contentId, recommendSlotId).getMessage();
	    // 调用函数
	    // List<Map<String, Object>> slotsByContentId = recommendService.getSlotsByContentId(contentId);
	    // 断言结果
	    assertEquals(resultActual,resultExpect);
	    
	}
	@Test
	public void changeNovelSlotsByContentId2_whenslotsOldisSuccess_thenReturnSuccess() throws Exception {
	    int contentId =1;
	    String recommendSlotId ="1";
	    int currentPage =1;
	    Date date = new Date();
	    // 期望结果
	    String resultExpect = "成功";
	    // mock过程
	    NovelBean novelBean=new NovelBean();
	    novelBean.setCode(1);
	    novelBean.setStatus("recommended");
	    
	    List<Integer>  list= new ArrayList<>();
	    list.add(1);
	    ArticleBean articleBean=new ArticleBean();
	    articleBean.setCode(1);
	    articleBean.setStatus("recommended");
	    List<Integer>slotsOld= new ArrayList<>();
	    slotsOld.add(1);
	    
	    when(novelBll.get(contentId)).thenReturn(new NovelBean());
	    when(recommendBll.getSlotsByContentId(contentId,CategoryType.novel.Value())).thenReturn(slotsOld);
            when(recommendSlotBll.getRecommendSlotByCode(anyInt())).thenReturn(new RecommendSlotBean());
//            
	    String resultActual = recommendService.changeNovelSlotsByContentId(contentId, recommendSlotId).getMessage();
	    // 调用函数
	    // List<Map<String, Object>> slotsByContentId = recommendService.getSlotsByContentId(contentId);
	    // 断言结果
	    assertEquals(resultActual,resultExpect);
	    
	}
	@Test
	public void changeNovelSlotsByContentId3_whenslotsOldisSuccess_thenReturnStatusIsNORecommend() throws Exception {
	    int contentId =1;
	    String recommendSlotId ="1";
	    int currentPage =1;
	    Date date = new Date();
	    // 期望结果
	    String resultExpect = "小说状态不是推荐状态";
	    // mock过程
	    NovelBean novelBean=new NovelBean();
	    novelBean.setCode(1);
	    novelBean.setStatus("recommended");
	    
	    List<Integer>  list= new ArrayList<>();
	    list.add(1);
	    ArticleBean articleBean=new ArticleBean();
	    articleBean.setCode(1);
	    articleBean.setStatus("recommended");
	    List<Integer>slotsOld= new ArrayList<>();
	    
	    when(novelBll.get(contentId)).thenReturn(new NovelBean());
	    when(recommendBll.getSlotsByContentId(contentId,CategoryType.novel.Value())).thenReturn(slotsOld);
	    when(recommendSlotBll.getRecommendSlotByCode(anyInt())).thenReturn(new RecommendSlotBean());
//            
	    String resultActual = recommendService.changeNovelSlotsByContentId(contentId, recommendSlotId).getMessage();
	    // 调用函数
	    // List<Map<String, Object>> slotsByContentId = recommendService.getSlotsByContentId(contentId);
	    // 断言结果
	    assertEquals(resultActual,resultExpect);
	    
	}
}
