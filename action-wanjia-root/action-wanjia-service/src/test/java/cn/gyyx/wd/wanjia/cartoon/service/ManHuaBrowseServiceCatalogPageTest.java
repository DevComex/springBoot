/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：才帅
 * 联系方式：caishuai@gyyx.cn 
 * 创建时间：2016-12-15
 * 版本号：v1.0
 * 本类主要用途叙述：玩家天地，漫画专区，漫画浏览测试类
 */
package cn.gyyx.wd.wanjia.cartoon.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdCategory;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdCollect;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdManhua;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdManhuaGood;
import cn.gyyx.wd.wanjia.cartoon.bll.ManHuaBrowseBLL;
import cn.gyyx.wd.wanjia.cartoon.service.impl.ManHuaBrowseServiceImpl;

@ContextConfiguration(classes = { ManHuaBrowseServiceCatalogPageTest.Config.class })
public class ManHuaBrowseServiceCatalogPageTest extends AbstractTestNGSpringContextTests {
	@Autowired
	private ManHuaBrowseServiceImpl manHuaBrowseServiceImpl;
	@Autowired
	private ManHuaBrowseBLL manHuaBrowseBLL;

	@BeforeClass
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	/*
	 * 目录页开始阅读和继续阅读状态
	 */
	@Test(dataProvider = "dataprovider", description = "当用户登录时，用户已收藏过该漫画并且有浏览记录，用户在该漫画目录页的阅读状态为‘继续阅读’")
	public void WD_WJTD_123_whenUserCollectThisManhuaAndhaveReadHistoryThenWhenUserLookCatalogShowContinueRead(
			WanwdManhua manhua, UserInfo user) {
		when(manHuaBrowseBLL.getManHuaByCode(manhua.getCode())).thenReturn(manhua);
		when(manHuaBrowseBLL.getManHuaGoodCount(manhua.getCode())).thenReturn(1);
		when(manHuaBrowseBLL.getManHuaMaxBookNum(manhua.getCode())).thenReturn(1);
		Map<String, Object> map2 = new HashMap<>();
		map2.put("manhuaCode", manhua.getCode());
		map2.put("bookNum", 1);
		map2.put("pictureNum", 0);
		Map<String, Object> manHuaBrowseInfo = new HashMap<>();
		manHuaBrowseInfo.put("page_picture_url", "http://aaa.jpg");
		when(manHuaBrowseBLL.getManHuaBrowseInfo(map2)).thenReturn(manHuaBrowseInfo);
		WanwdCategory category = new WanwdCategory();
		when(manHuaBrowseBLL.getCategoryByPirmaryKey(manhua.getCategoryCode())).thenReturn(category);
		when(manHuaBrowseBLL.selectManhuaInfo(manhua.getCode())).thenReturn(map2);
		when(manHuaBrowseBLL.getGoodStatus(Mockito.any(WanwdManhuaGood.class))).thenReturn(1);
		WanwdCollect collectionStatus = new WanwdCollect();
		collectionStatus.setCode(111);
		collectionStatus.setReadLogCode(1);
		when(manHuaBrowseBLL.selectCollectionStatus(Mockito.any(WanwdCollect.class))).thenReturn(collectionStatus);

		Map<String, Object> manHuaInfo = manHuaBrowseServiceImpl.getManHuaInfo(manhua.getCode(), user);
		Assert.assertEquals(manHuaInfo.get("readStatus"), true);
	}

	@Test(dataProvider = "dataprovider", description = "当用户登录时，用户已收藏过该漫画但没有有浏览记录，用户在该漫画目录页的阅读状态为‘开始阅读’")
	public void WD_WJTD_120_whenUserCollectThisManhuaButNoReadHistoryThenWhenUserLookCatalogShowStartRead(WanwdManhua manhua,
			UserInfo user) {
		when(manHuaBrowseBLL.getManHuaByCode(manhua.getCode())).thenReturn(manhua);
		when(manHuaBrowseBLL.getManHuaGoodCount(manhua.getCode())).thenReturn(1);
		when(manHuaBrowseBLL.getManHuaMaxBookNum(manhua.getCode())).thenReturn(1);
		Map<String, Object> map2 = new HashMap<>();
		map2.put("manhuaCode", manhua.getCode());
		map2.put("bookNum", 1);
		map2.put("pictureNum", 0);
		Map<String, Object> manHuaBrowseInfo = new HashMap<>();
		manHuaBrowseInfo.put("page_picture_url", "http://aaa.jpg");
		when(manHuaBrowseBLL.getManHuaBrowseInfo(map2)).thenReturn(manHuaBrowseInfo);
		WanwdCategory category = new WanwdCategory();
		when(manHuaBrowseBLL.getCategoryByPirmaryKey(manhua.getCategoryCode())).thenReturn(category);
		when(manHuaBrowseBLL.selectManhuaInfo(manhua.getCode())).thenReturn(map2);
		when(manHuaBrowseBLL.getGoodStatus(Mockito.any(WanwdManhuaGood.class))).thenReturn(1);
		WanwdCollect collectionStatus = new WanwdCollect();
		collectionStatus.setCode(111);
		when(manHuaBrowseBLL.selectCollectionStatus(Mockito.any(WanwdCollect.class))).thenReturn(collectionStatus);

		Map<String, Object> manHuaInfo = manHuaBrowseServiceImpl.getManHuaInfo(manhua.getCode(), user);
		Assert.assertEquals(manHuaInfo.get("readStatus"), false);
	}

	@Test(dataProvider = "dataprovider", description = "当用户登录时，用户未收藏过该漫画，用户在该漫画目录页的阅读状态为‘开始阅读’")
	public void WD_WJTD_120_whenUserNoCollectThisManhuaThenWhenUserLookCatalogShowStartRead(WanwdManhua manhua, UserInfo user) {
		when(manHuaBrowseBLL.getManHuaByCode(manhua.getCode())).thenReturn(manhua);
		when(manHuaBrowseBLL.getManHuaGoodCount(manhua.getCode())).thenReturn(1);
		when(manHuaBrowseBLL.getManHuaMaxBookNum(manhua.getCode())).thenReturn(1);
		Map<String, Object> map2 = new HashMap<>();
		map2.put("manhuaCode", manhua.getCode());
		map2.put("bookNum", 1);
		map2.put("pictureNum", 0);
		Map<String, Object> manHuaBrowseInfo = new HashMap<>();
		manHuaBrowseInfo.put("page_picture_url", "http://aaa.jpg");
		when(manHuaBrowseBLL.getManHuaBrowseInfo(map2)).thenReturn(manHuaBrowseInfo);
		WanwdCategory category = new WanwdCategory();
		when(manHuaBrowseBLL.getCategoryByPirmaryKey(manhua.getCategoryCode())).thenReturn(category);
		when(manHuaBrowseBLL.selectManhuaInfo(manhua.getCode())).thenReturn(map2);
		when(manHuaBrowseBLL.getGoodStatus(Mockito.any(WanwdManhuaGood.class))).thenReturn(1);
		WanwdCollect collectionStatus = null;
		when(manHuaBrowseBLL.selectCollectionStatus(Mockito.any(WanwdCollect.class))).thenReturn(collectionStatus);

		Map<String, Object> manHuaInfo = manHuaBrowseServiceImpl.getManHuaInfo(manhua.getCode(), user);
		Assert.assertEquals(manHuaInfo.get("readStatus"), false);
	}

	@Test(dataProvider = "dataprovider", description = "当用户未登录时，用户在该漫画目录页的阅读状态为‘开始阅读’")
	public void whenUserNoLoginThenWhenUserLookCatalogShowStartRead(WanwdManhua manhua, UserInfo user) {
		user = null;
		when(manHuaBrowseBLL.getManHuaByCode(manhua.getCode())).thenReturn(manhua);
		when(manHuaBrowseBLL.getManHuaGoodCount(manhua.getCode())).thenReturn(1);
		when(manHuaBrowseBLL.getManHuaMaxBookNum(manhua.getCode())).thenReturn(1);
		Map<String, Object> map2 = new HashMap<>();
		map2.put("manhuaCode", manhua.getCode());
		map2.put("bookNum", 1);
		map2.put("pictureNum", 0);
		Map<String, Object> manHuaBrowseInfo = new HashMap<>();
		manHuaBrowseInfo.put("page_picture_url", "http://aaa.jpg");
		when(manHuaBrowseBLL.getManHuaBrowseInfo(map2)).thenReturn(manHuaBrowseInfo);
		WanwdCategory category = new WanwdCategory();
		when(manHuaBrowseBLL.getCategoryByPirmaryKey(manhua.getCategoryCode())).thenReturn(category);

		Map<String, Object> manHuaInfo = manHuaBrowseServiceImpl.getManHuaInfo(manhua.getCode(), user);
		Assert.assertEquals(manHuaInfo.get("readStatus"), false);
	}

	/*
	 * 点赞状态
	 */

	@Test(dataProvider = "dataprovider", description = "当用户登录时，用户未点赞该漫画，用户在该漫画目录页的“点赞”按钮可点击")
	public void WD_WJTD_143_whenUserNoAddGoodsThisManhuaThenWhenUserLookCatalogShowCanAddGoods(WanwdManhua manhua,
			UserInfo user) {
		when(manHuaBrowseBLL.getManHuaByCode(manhua.getCode())).thenReturn(manhua);
		when(manHuaBrowseBLL.getManHuaGoodCount(manhua.getCode())).thenReturn(1);
		when(manHuaBrowseBLL.getManHuaMaxBookNum(manhua.getCode())).thenReturn(1);
		Map<String, Object> map2 = new HashMap<>();
		map2.put("manhuaCode", manhua.getCode());
		map2.put("bookNum", 1);
		map2.put("pictureNum", 0);
		Map<String, Object> manHuaBrowseInfo = new HashMap<>();
		manHuaBrowseInfo.put("page_picture_url", "http://aaa.jpg");
		when(manHuaBrowseBLL.getManHuaBrowseInfo(map2)).thenReturn(manHuaBrowseInfo);
		WanwdCategory category = new WanwdCategory();
		when(manHuaBrowseBLL.getCategoryByPirmaryKey(manhua.getCategoryCode())).thenReturn(category);

		when(manHuaBrowseBLL.getGoodStatus(Mockito.any(WanwdManhuaGood.class))).thenReturn(0);
		WanwdCollect collectionStatus = null;
		when(manHuaBrowseBLL.selectCollectionStatus(Mockito.any(WanwdCollect.class))).thenReturn(collectionStatus);

		Map<String, Object> manHuaInfo = manHuaBrowseServiceImpl.getManHuaInfo(manhua.getCode(), user);
		Assert.assertEquals(manHuaInfo.get("goodStatus"), false);
	}

	@Test(dataProvider = "dataprovider", description = "当用户未登录时，用户未点赞该漫画，用户在该漫画目录页的“点赞”按钮可点击")
	public void WD_WJTD_146_whenUserNotLoginWhenUserLookCatalogShowCanAddGoods(WanwdManhua manhua, UserInfo user) {
		user = null;
		when(manHuaBrowseBLL.getManHuaByCode(manhua.getCode())).thenReturn(manhua);
		when(manHuaBrowseBLL.getManHuaGoodCount(manhua.getCode())).thenReturn(1);
		when(manHuaBrowseBLL.getManHuaMaxBookNum(manhua.getCode())).thenReturn(1);
		Map<String, Object> map2 = new HashMap<>();
		map2.put("manhuaCode", manhua.getCode());
		map2.put("bookNum", 1);
		map2.put("pictureNum", 0);
		Map<String, Object> manHuaBrowseInfo = new HashMap<>();
		manHuaBrowseInfo.put("page_picture_url", "http://aaa.jpg");
		when(manHuaBrowseBLL.getManHuaBrowseInfo(map2)).thenReturn(manHuaBrowseInfo);
		WanwdCategory category = new WanwdCategory();
		when(manHuaBrowseBLL.getCategoryByPirmaryKey(manhua.getCategoryCode())).thenReturn(category);

		Map<String, Object> manHuaInfo = manHuaBrowseServiceImpl.getManHuaInfo(manhua.getCode(), user);
		Assert.assertEquals(manHuaInfo.get("goodStatus"), false);
	}

	@Test(dataProvider = "dataprovider", description = "当用户登录时，用户已点赞该漫画，用户在该漫画目录页的“点赞”按钮不可点击")
	public void WD_WJTD_145_whenUserHasBeenAddGoodsThisManhuaThenWhenUserLookCatalogShowCanNotAddGoods(
			WanwdManhua manhua, UserInfo user) {
		when(manHuaBrowseBLL.getManHuaByCode(manhua.getCode())).thenReturn(manhua);
		when(manHuaBrowseBLL.getManHuaGoodCount(manhua.getCode())).thenReturn(1);
		when(manHuaBrowseBLL.getManHuaMaxBookNum(manhua.getCode())).thenReturn(1);
		Map<String, Object> map2 = new HashMap<>();
		map2.put("manhuaCode", manhua.getCode());
		map2.put("bookNum", 1);
		map2.put("pictureNum", 0);
		Map<String, Object> manHuaBrowseInfo = new HashMap<>();
		manHuaBrowseInfo.put("page_picture_url", "http://aaa.jpg");
		when(manHuaBrowseBLL.getManHuaBrowseInfo(map2)).thenReturn(manHuaBrowseInfo);
		WanwdCategory category = new WanwdCategory();
		when(manHuaBrowseBLL.getCategoryByPirmaryKey(manhua.getCategoryCode())).thenReturn(category);

		when(manHuaBrowseBLL.getGoodStatus(Mockito.any(WanwdManhuaGood.class))).thenReturn(1);
		WanwdCollect collectionStatus = null;
		when(manHuaBrowseBLL.selectCollectionStatus(Mockito.any(WanwdCollect.class))).thenReturn(collectionStatus);

		Map<String, Object> manHuaInfo = manHuaBrowseServiceImpl.getManHuaInfo(manhua.getCode(), user);
		Assert.assertEquals(manHuaInfo.get("goodStatus"), true);
	}

	@Test(dataProvider = "dataprovider", description = "当用户登录时，用户未点赞该漫画，点击“点赞”按钮，点赞数+1")
	public void WD_WJTD_144_whenUserNotAddGoodsThisManhuaThenWhenUserLookCatalogCanAddGoodsSuccess(WanwdManhua manhua,
			UserInfo user) {
		when(manHuaBrowseBLL.getGoodStatus(Mockito.any(WanwdManhuaGood.class))).thenReturn(0);
		boolean addGoodStatus = manHuaBrowseServiceImpl.addGoodStatus(user, manhua.getCode());
		Assert.assertEquals(addGoodStatus, true);
	}

	@Test(dataProvider = "dataprovider", description = "当用户登录时，用户已点赞该漫画，发送点赞请求，点赞失败")
	public void whenUserHasBeenAddGoodsThisManhuaThenWhenUserLookCatalogCanNotAddGoodsSuccess(WanwdManhua manhua,
			UserInfo user) {
		when(manHuaBrowseBLL.getGoodStatus(Mockito.any(WanwdManhuaGood.class))).thenReturn(1);
		boolean addGoodStatus = manHuaBrowseServiceImpl.addGoodStatus(user, manhua.getCode());
		Assert.assertEquals(addGoodStatus, false);
	}

	/*
	 * 目录页收藏显示和不显示
	 */
	@Test(dataProvider = "dataprovider", description = "当用户登录时，用户收藏了该漫画，用户在该漫画目录页的收藏状态为‘已收藏’")
	public void WD_WJTD_131_133_135_137_193_whenUserCollectThisManhuaThenWhenUserLookCatalogShowCollectionHasBeenCollected(
			WanwdManhua manhua, UserInfo user) {
		when(manHuaBrowseBLL.getManHuaByCode(manhua.getCode())).thenReturn(manhua);
		when(manHuaBrowseBLL.getManHuaGoodCount(manhua.getCode())).thenReturn(1);
		when(manHuaBrowseBLL.getManHuaMaxBookNum(manhua.getCode())).thenReturn(1);
		Map<String, Object> map2 = new HashMap<>();
		map2.put("manhuaCode", manhua.getCode());
		map2.put("bookNum", 1);
		map2.put("pictureNum", 0);
		Map<String, Object> manHuaBrowseInfo = new HashMap<>();
		manHuaBrowseInfo.put("page_picture_url", "http://aaa.jpg");
		when(manHuaBrowseBLL.getManHuaBrowseInfo(map2)).thenReturn(manHuaBrowseInfo);
		WanwdCategory category = new WanwdCategory();
		when(manHuaBrowseBLL.getCategoryByPirmaryKey(manhua.getCategoryCode())).thenReturn(category);
		when(manHuaBrowseBLL.getGoodStatus(Mockito.any(WanwdManhuaGood.class))).thenReturn(1);
		WanwdCollect collectionStatus = new WanwdCollect();
		collectionStatus.setCode(111);
		when(manHuaBrowseBLL.selectCollectionStatus(Mockito.any(WanwdCollect.class))).thenReturn(collectionStatus);

		Map<String, Object> map = manHuaBrowseServiceImpl.getManHuaInfo(manhua.getCode(), user);
		Assert.assertEquals(map.get("collectStatus"), true);
	}

	@Test(dataProvider = "dataprovider", description = "当用户登录时，用户没有收藏了该漫画，用户在该漫画目录页的收藏状态为‘收藏’（还没有收藏的状态）")
	public void WD_WJTD_139_141_190_193_whenUserNotCollectThisManhuaThenWhenUserLookCatalogShowCollectionIsNotCollected(
			WanwdManhua manhua, UserInfo user) {
		when(manHuaBrowseBLL.getManHuaByCode(manhua.getCode())).thenReturn(manhua);
		when(manHuaBrowseBLL.getManHuaGoodCount(manhua.getCode())).thenReturn(1);
		when(manHuaBrowseBLL.getManHuaMaxBookNum(manhua.getCode())).thenReturn(1);
		Map<String, Object> map2 = new HashMap<>();
		map2.put("manhuaCode", manhua.getCode());
		map2.put("bookNum", 1);
		map2.put("pictureNum", 0);
		Map<String, Object> manHuaBrowseInfo = new HashMap<>();
		manHuaBrowseInfo.put("page_picture_url", "http://aaa.jpg");
		when(manHuaBrowseBLL.getManHuaBrowseInfo(map2)).thenReturn(manHuaBrowseInfo);
		WanwdCategory category = new WanwdCategory();
		when(manHuaBrowseBLL.getCategoryByPirmaryKey(manhua.getCategoryCode())).thenReturn(category);
		when(manHuaBrowseBLL.getGoodStatus(Mockito.any(WanwdManhuaGood.class))).thenReturn(1);
		WanwdCollect collectionStatus = null;
		when(manHuaBrowseBLL.selectCollectionStatus(Mockito.any(WanwdCollect.class))).thenReturn(collectionStatus);

		Map<String, Object> map = manHuaBrowseServiceImpl.getManHuaInfo(manhua.getCode(), user);
		Assert.assertEquals(map.get("collectStatus"), false);
	}
	@Test(dataProvider = "dataprovider", description = "当用户没有登录时，用户在该漫画目录页的收藏状态为‘收藏’（还没有收藏的状态）")
	public void WD_WJTD_129_whenUserNotCollectThisManhuaThenWhenUserLookCatalogShowCollectionIsNotCollected(
			WanwdManhua manhua, UserInfo user) {
		user=null;
		when(manHuaBrowseBLL.getManHuaByCode(manhua.getCode())).thenReturn(manhua);
		when(manHuaBrowseBLL.getManHuaGoodCount(manhua.getCode())).thenReturn(1);
		when(manHuaBrowseBLL.getManHuaMaxBookNum(manhua.getCode())).thenReturn(1);
		Map<String, Object> map2 = new HashMap<>();
		map2.put("manhuaCode", manhua.getCode());
		map2.put("bookNum", 1);
		map2.put("pictureNum", 0);
		Map<String, Object> manHuaBrowseInfo = new HashMap<>();
		manHuaBrowseInfo.put("page_picture_url", "http://aaa.jpg");
		when(manHuaBrowseBLL.getManHuaBrowseInfo(map2)).thenReturn(manHuaBrowseInfo);
		WanwdCategory category = new WanwdCategory();
		when(manHuaBrowseBLL.getCategoryByPirmaryKey(manhua.getCategoryCode())).thenReturn(category);

		Map<String, Object> map = manHuaBrowseServiceImpl.getManHuaInfo(manhua.getCode(), user);
		Assert.assertEquals(map.get("collectStatus"), false);
	}

	@DataProvider(name = "dataprovider")
	public Object[][] providerMethod() {
		Object[][] result = null;
		UserInfo user = new UserInfo();
		user.setAccount("testAccount");
		user.setUserId(1);
		WanwdManhua manhua = new WanwdManhua(1, "1title", "1context", "1authorName", user.getAccount(),
				user.getUserId(), 130, new Date(111), new Date(222), 0, 111, 120);
		result = new Object[][] { { manhua, user } };
		return result;
	}

	@Configuration
	public static class Config {
		@Bean
		public ManHuaBrowseServiceImpl manHuaBrowseServiceImpl() {
			return new ManHuaBrowseServiceImpl();
		}

		@Bean
		public ManHuaBrowseBLL manHuaBrowseBLL() {
			return mock(ManHuaBrowseBLL.class);
		}

	}
}
