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

import static org.mockito.Matchers.anyMap;
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
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdCollect;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdManhua;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdManhuaBook;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdManhuaPage;
import cn.gyyx.wd.wanjia.cartoon.bll.ManHuaBrowseBLL;
import cn.gyyx.wd.wanjia.cartoon.service.impl.ManHuaBrowseServiceImpl;

@ContextConfiguration(classes = { ManHuaBrowseServiceBrowsePageTest.Config.class })
public class ManHuaBrowseServiceBrowsePageTest extends AbstractTestNGSpringContextTests {
	@Autowired
	private ManHuaBrowseServiceImpl manHuaBrowseServiceImpl;
	@Autowired
	private ManHuaBrowseBLL manHuaBrowseBLL;

	@BeforeClass
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	/*
	 * 浏览页收藏展示收藏和已收藏
	 */
	@Test(dataProvider = "dataprovider", description = "当用户登录时，用户没有收藏了该漫画，用户在该漫画浏览页的收藏状态为‘收藏’（还没有收藏的状态）")
	public void WD_WJTD_142_187_194_whenUserNotCollectThisManhuaThenWhenUserLookBrowseShowCollectionIsNotCollected(
			WanwdManhua manhua, UserInfo user) {
		WanwdCollect collectionStatus = null;
		when(manHuaBrowseBLL.selectCollectionStatus(Mockito.any(WanwdCollect.class))).thenReturn(collectionStatus);
		Map<String, Object> map = new HashMap<>();
		when(manHuaBrowseBLL.getManHuaBrowseInfo(anyMap())).thenReturn(map);
		Map<String, Object> manhuaBrowse = manHuaBrowseServiceImpl.manhuaBrowse(user, manhua.getCode(), 1, 1);
		Assert.assertEquals(manhuaBrowse.get("collect"), false);
	}

	@Test(dataProvider = "dataprovider", description = "当用户登录时，用户收藏了该漫画，用户在该漫画浏览页的收藏状态为‘已收藏’")
	public void WD_WJTD_134_138_189_192_whenUserCollectThisManhuaThenWhenUserLookBrowseShowCollectionHasBeenCollected(
			WanwdManhua manhua, UserInfo user) {
		WanwdCollect collectionStatus = new WanwdCollect();
		collectionStatus.setCode(111);
		when(manHuaBrowseBLL.selectCollectionStatus(Mockito.any(WanwdCollect.class))).thenReturn(collectionStatus);
		Map<String, Object> map = new HashMap<>();
		when(manHuaBrowseBLL.getManHuaBrowseInfo(anyMap())).thenReturn(map);
		Map<String, Object> manhuaBrowse = manHuaBrowseServiceImpl.manhuaBrowse(user, manhua.getCode(), 1, 1);
		Assert.assertEquals(manhuaBrowse.get("collect"), true);
	}

	/*
	 * 开始阅读记录判定
	 */
	@Test(dataProvider = "dataprovider", description = "当用户未登录时，点击开始阅读，进入漫画浏览页，显示最小的章节和页数的漫画（默认为第一章第一页如果第一章第一页存在且编辑允许用户查看）")
	public void WD_WJTD_185_whenUserNotLoginThenGoToBrowseFirstCharapter(WanwdManhua manhua, UserInfo user) {
		user = null;
		when(manHuaBrowseBLL.getMinBookNum(manhua.getCode())).thenReturn(1);
		Map<String, Object> manHuaBrowseInfo = new HashMap<>();
		manHuaBrowseInfo.put("page_picture_url", "http://aaa.jpg");
		manHuaBrowseInfo.put("book_num", 1);
		Map<String, Object> map2 = new HashMap<>();
		map2.put("manhuaCode", manhua.getCode());
		map2.put("bookNum", 1);
		map2.put("pictureNum", 1);
		when(manHuaBrowseBLL.getManHuaBrowseInfo(map2)).thenReturn(manHuaBrowseInfo);

		Map<String, Object> manhuaBrowse = manHuaBrowseServiceImpl.manhuaBrowse(user, manhua.getCode(), null, null);
		Assert.assertEquals(manhuaBrowse.get("book_num"), 1);
	}

	@Test(dataProvider = "dataprovider", description = "当用户登录未收藏时，点击开始阅读，进入漫画浏览页，显示最小的章节和页数的漫画（默认为第一章第一页如果第一章第一页存在且编辑允许用户查看）")
	public void WD_WJTD_121_184_whenUserLoginNoCollectedThenGoToBrowseFirstCharapter(WanwdManhua manhua,
			UserInfo user) {
		when(manHuaBrowseBLL.getMinBookNum(manhua.getCode())).thenReturn(1);
		WanwdCollect collectionStatus = null;
		when(manHuaBrowseBLL.selectCollectionStatus(Mockito.any(WanwdCollect.class))).thenReturn(collectionStatus);
		Map<String, Object> map2 = new HashMap<>();
		map2.put("manhuaCode", manhua.getCode());
		map2.put("bookNum", 1);
		map2.put("pictureNum", 1);
		Map<String, Object> manHuaBrowseInfo = new HashMap<>();
		manHuaBrowseInfo.put("page_picture_url", "http://aaa.jpg");
		manHuaBrowseInfo.put("book_num", 1);
		when(manHuaBrowseBLL.getManHuaBrowseInfo(map2)).thenReturn(manHuaBrowseInfo);

		Map<String, Object> manhuaBrowse = manHuaBrowseServiceImpl.manhuaBrowse(user, manhua.getCode(), null, null);
		Assert.assertEquals(manhuaBrowse.get("book_num"), 1);
		Assert.assertEquals(manhuaBrowse.get("collect"), false);
	}

	@Test(dataProvider = "dataprovider", description = "当用户登录已收藏无阅读记录时，点击开始阅读，进入漫画浏览页，显示最小的章节和页数的漫画（默认为第一章第一页如果第一章第一页存在且编辑允许用户查看）")
	public void WD_WJTD_122_184_whenUserLoginCollectedNoReadHistoryThenGoToBrowseFirstCharapter(WanwdManhua manhua,
			UserInfo user) {
		when(manHuaBrowseBLL.getMinBookNum(manhua.getCode())).thenReturn(1);
		WanwdCollect collectionStatus = new WanwdCollect();
		collectionStatus.setCode(111);
		when(manHuaBrowseBLL.selectCollectionStatus(Mockito.any(WanwdCollect.class))).thenReturn(collectionStatus);
		Map<String, Object> map2 = new HashMap<>();
		map2.put("manhuaCode", manhua.getCode());
		map2.put("bookNum", 1);
		map2.put("pictureNum", 1);
		Map<String, Object> manHuaBrowseInfo = new HashMap<>();
		manHuaBrowseInfo.put("page_picture_url", "http://aaa.jpg");
		manHuaBrowseInfo.put("book_num", 1);
		when(manHuaBrowseBLL.getManHuaBrowseInfo(map2)).thenReturn(manHuaBrowseInfo);

		Map<String, Object> manhuaBrowse = manHuaBrowseServiceImpl.manhuaBrowse(user, manhua.getCode(), null, null);
		Assert.assertEquals(manhuaBrowse.get("book_num"), 1);
		Assert.assertEquals(manhuaBrowse.get("collect"), true);
	}

	@Test(dataProvider = "dataprovider", description = "当用户登录已收藏有阅读记录时，但章节处于未展示，点击开始阅读，进入漫画浏览页，显示最小的章节和页数的漫画（默认为第一章第一页如果第一章第一页存在且编辑允许用户查看）")
	public void _whenUserLoginCollectedhaveReadHistoryButCharacterNoDisplayThenGoToBrowseFirstCharapter(
			WanwdManhua manhua, UserInfo user) {
		when(manHuaBrowseBLL.getMinBookNum(manhua.getCode())).thenReturn(1);
		WanwdCollect collectionStatus = new WanwdCollect();
		collectionStatus.setCode(111);
		collectionStatus.setReadLogCode(120);
		WanwdManhuaPage page = new WanwdManhuaPage();
		page.setBookCode(123);
		page.setPagePictureNum(3);
		when(manHuaBrowseBLL.selectCollectionStatus(Mockito.any(WanwdCollect.class))).thenReturn(collectionStatus);
		when(manHuaBrowseBLL.selectPageByPrimaryKey(collectionStatus.getReadLogCode())).thenReturn(page);
		WanwdManhuaBook book = new WanwdManhuaBook();
		book.setBookNum("10");
		book.setIsDelete(1);
		when(manHuaBrowseBLL.selectBookByPrimaryKey(page.getBookCode())).thenReturn(book);
		Map<String, Object> map2 = new HashMap<>();
		map2.put("manhuaCode", manhua.getCode());
		map2.put("bookNum", 1);
		map2.put("pictureNum", 1);

		Map<String, Object> manHuaBrowseInfo = new HashMap<>();
		manHuaBrowseInfo.put("page_picture_url", "http://aaa.jpg");
		manHuaBrowseInfo.put("book_num", 1);
		manHuaBrowseInfo.put("page_picture_num", 1);
		when(manHuaBrowseBLL.getManHuaBrowseInfo(map2)).thenReturn(manHuaBrowseInfo);

		Map<String, Object> manhuaBrowse = manHuaBrowseServiceImpl.manhuaBrowse(user, manhua.getCode(), null, null);
		Assert.assertEquals(manhuaBrowse.get("book_num"), 1);
		Assert.assertEquals(manhuaBrowse.get("page_picture_num"), 1);
		Assert.assertEquals(manhuaBrowse.get("collect"), true);
	}

	@Test(dataProvider = "dataprovider", description = "当用户登录已收藏有阅读记录时，且章节处于已展示，点击开始阅读，进入漫画浏览页，显示阅读记录的章节和页数")
	public void WD_WJTD_124_186_whenUserLoginCollectedhaveReadHistoryAndCharacterDisplayThenGoToBrowseHistoryCharapterAndPage(
			WanwdManhua manhua, UserInfo user) {
		when(manHuaBrowseBLL.getMinBookNum(manhua.getCode())).thenReturn(1);
		WanwdCollect collectionStatus = new WanwdCollect();
		collectionStatus.setCode(111);
		collectionStatus.setReadLogCode(120);
		WanwdManhuaPage page = new WanwdManhuaPage();
		page.setBookCode(123);
		page.setPagePictureNum(3);
		when(manHuaBrowseBLL.selectCollectionStatus(Mockito.any(WanwdCollect.class))).thenReturn(collectionStatus);
		when(manHuaBrowseBLL.selectPageByPrimaryKey(collectionStatus.getReadLogCode())).thenReturn(page);
		WanwdManhuaBook book = new WanwdManhuaBook();
		book.setBookNum("10");
		book.setIsDelete(0);
		when(manHuaBrowseBLL.selectBookByPrimaryKey(page.getBookCode())).thenReturn(book);
		Map<String, Object> map2 = new HashMap<>();
		map2.put("manhuaCode", manhua.getCode());
		map2.put("bookNum", "10");
		map2.put("pictureNum", 3);

		Map<String, Object> manHuaBrowseInfo = new HashMap<>();
		manHuaBrowseInfo.put("page_picture_url", "http://aaa.jpg");
		manHuaBrowseInfo.put("book_num", 10);
		manHuaBrowseInfo.put("page_picture_num", 3);
		when(manHuaBrowseBLL.getManHuaBrowseInfo(map2)).thenReturn(manHuaBrowseInfo);

		Map<String, Object> manhuaBrowse = manHuaBrowseServiceImpl.manhuaBrowse(user, manhua.getCode(), null, null);
		Assert.assertEquals(manhuaBrowse.get("book_num"), 10);
		Assert.assertEquals(manhuaBrowse.get("page_picture_num"), 3);
		Assert.assertEquals(manhuaBrowse.get("collect"), true);
	}

	@Test(dataProvider = "dataprovider", description = "当用户请求某一章某一页，若允许显示，返回这一章这一页的信息")
	public void WD_WJTD_198_199_200_201_202_whenUserAskPageAndIsAllowShowThenShowIt(WanwdManhua manhua, UserInfo user) {
		Map<String, Object> map2 = new HashMap<>();
		map2.put("manhuaCode", manhua.getCode());
		map2.put("bookNum", 2);
		map2.put("pictureNum", 3);
		Map<String, Object> manHuaBrowseInfo = new HashMap<>();
		manHuaBrowseInfo.put("page_picture_url", "http://aaa.jpg");
		manHuaBrowseInfo.put("book_num", 2);
		manHuaBrowseInfo.put("page_picture_num", 3);
		when(manHuaBrowseBLL.getManHuaBrowseInfo(map2)).thenReturn(manHuaBrowseInfo);
		Map<String, Object> manhuaBrowse = manHuaBrowseServiceImpl.manhuaBrowse(user, manhua.getCode(), 2, 3);
		Assert.assertEquals(manhuaBrowse.get("book_num"), 2);
		Assert.assertEquals(manhuaBrowse.get("page_picture_num"), 3);
		Assert.assertEquals(manhuaBrowse.get("page_picture_url"), "http://aaa.jpg");
	}

	@Test(dataProvider = "dataprovider", description = "当用户请求最新章节，返回这一章第一页的信息")
	public void WD_WJTD_197_whenUserAskPageAndIsAllowShowThenShowIt(WanwdManhua manhua, UserInfo user) {
		user = null;
		Map<String, Object> map2 = new HashMap<>();
		map2.put("manhuaCode", manhua.getCode());
		map2.put("bookNum", 12);
		map2.put("pictureNum", 1);
		Map<String, Object> manHuaBrowseInfo = new HashMap<>();
		manHuaBrowseInfo.put("page_picture_url", "http://aaa.jpg");
		manHuaBrowseInfo.put("book_num", 12);
		manHuaBrowseInfo.put("page_picture_num", 1);
		when(manHuaBrowseBLL.getManHuaMaxBookNum(manhua.getCode())).thenReturn(12);
		when(manHuaBrowseBLL.getManHuaBrowseInfo(map2)).thenReturn(manHuaBrowseInfo);
		Map<String, Object> manhuaBrowse = manHuaBrowseServiceImpl.getNewestBook(user, manhua.getCode());
		Assert.assertEquals(manhuaBrowse.get("book_num"), 12);
		Assert.assertEquals(manhuaBrowse.get("page_picture_num"), 1);
		Assert.assertEquals(manhuaBrowse.get("page_picture_url"), "http://aaa.jpg");
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
