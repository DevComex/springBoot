/**
 * --------------------------------------------------- 
 * @版权所有：北京光宇在线科技有限责任公司
 * @作者：才帅
 * @联系方式：caishuai@gyyx.cn 
 * @创建时间：2016-1-5
 * @版本号：v1.0
 * @本类主要用途叙述：玩家天地，漫画专区，用户中心漫漶相关内容测试类
 */
package cn.gyyx.wd.wanjia.cartoon.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import cn.gyyx.wd.wanjia.cartoon.beans.Constans;
import cn.gyyx.wd.wanjia.cartoon.bll.UserCenterManHuaBLL;
import cn.gyyx.wd.wanjia.cartoon.service.impl.UserCenterManHuaServiceImpl;

@ContextConfiguration(classes = { UserCenterManHuaServiceTest.Config.class })
public class UserCenterManHuaServiceTest extends AbstractTestNGSpringContextTests {
	@Autowired
	UserCenterManHuaServiceImpl userCenterManHuaServiceImpl;
	@Autowired
	private UserCenterManHuaBLL userCenterManHuaBLL;

	@BeforeClass
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test(description = "查询用户收藏的某一页时，返回这一页的list和收藏总条数Count", dataProvider = "dataprovider")
	public void _whenAskUserCollectedManhuaForOnePageThenReturnThisPageCollectionListAndAllCollectedManhuaSize(
			int pageIndex, int pageSize, int resourceType, int userId, List<Map<String, Object>> list) {
		int start = (pageIndex - 1) * pageSize + 1;
		int end = pageIndex * pageSize;
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> resultMap = new HashMap<>();
		map.put("resourceType", resourceType);
		map.put("userId", userId);
		map.put("start", start);
		map.put("end", end);
		when(userCenterManHuaBLL.selectUserCollection(map)).thenReturn(list);
		when(userCenterManHuaBLL.selectUserCollectionTotalSize(map)).thenReturn(25);
		resultMap.put("DataSet", list);
		resultMap.put("Count", 25);
		Map<String, Object> collection = userCenterManHuaServiceImpl.getCollection(userId, resourceType, pageIndex,
				pageSize);
		Assert.assertEquals(collection, resultMap);
	}

	@Test(description = "查询用户漫画消息的某一页时，返回这一页的list和消息总条数Count", dataProvider = "dataprovider")
	public void WD_WJTD_356_357_whenAskUserManhuaMessageForOnePageThenReturnThisPageListAndAllManhuaMessageSize(int pageIndex,
			int pageSize, int resourceType, int userId, List<Map<String, Object>> list) {
		int start = (pageIndex - 1) * pageSize + 1;
		int end = pageIndex * pageSize;
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> resultMap = new HashMap<>();
		map.put("resourceType", resourceType);
		map.put("userId", userId);
		map.put("start", start);
		map.put("end", end);
		when(userCenterManHuaBLL.selectMessageAboutManHua(map)).thenReturn(list);
		when(userCenterManHuaBLL.selectMessageAboutManHuaTotalSize(map)).thenReturn(25);
		resultMap.put("DataSet", list);
		resultMap.put("Count", 25);
		Map<String, Object> collection = userCenterManHuaServiceImpl.getMessage(userId, resourceType, pageIndex,
				pageSize);
		Assert.assertEquals(collection, resultMap);
	}

	@Test(description = "查询用户漫画编辑回复消息的某一页时，返回这一页的list和编辑回复消息总条数Count", dataProvider = "dataprovider")
	public void WD_WJTD_376_378_whenAskUserManhuaEditorReplyMessageForOnePageThenReturnThisPageListAndAllManhuaEditorReplyMessageSize(
			int pageIndex, int pageSize, int resourceType, int userId, List<Map<String, Object>> list) {
		int start = (pageIndex - 1) * pageSize + 1;
		int end = pageIndex * pageSize;
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> resultMap = new HashMap<>();
		map.put("resourceType", resourceType);
		map.put("userId", userId);
		map.put("start", start);
		map.put("end", end);
		when(userCenterManHuaBLL.selectManagerReply(map)).thenReturn(list);
		when(userCenterManHuaBLL.selectManagerReplyTotalSize(map)).thenReturn(25);
		resultMap.put("DataSet", list);
		resultMap.put("Count", 25);
		Map<String, Object> collection = userCenterManHuaServiceImpl.getManagerReply(userId, resourceType, pageIndex,
				pageSize);
		Assert.assertEquals(collection, resultMap);
	}

	@Test(description = "查询用户上传漫画全部的某一页时，返回这一页的list和上传漫画总条数Count", dataProvider = "dataprovider")
	public void WD_WJTD_351_347_348_whenAskUserManhuaUploadedDefaultForOnePageThenReturnThisPageListAndAllManhuaUploadedSize(int pageIndex,
			int pageSize, int resourceType, int userId, List<Map<String, Object>> list) {
		int start = (pageIndex - 1) * pageSize + 1;
		int end = pageIndex * pageSize;
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> resultMap = new HashMap<>();
		map.put("resourceType", resourceType);
		map.put("status", null);
		map.put("userId", userId);
		map.put("start", start);
		map.put("end", end);
		when(userCenterManHuaBLL.selectUserUpload(map)).thenReturn(list);
		when(userCenterManHuaBLL.selectUserUploadTotalSize(map)).thenReturn(25);
		resultMap.put("DataSet", list);
		resultMap.put("Count", 25);
		Map<String, Object> collection = userCenterManHuaServiceImpl.getUpload("DEFAULT", resourceType, userId,
				pageIndex, pageSize);
		Assert.assertEquals(collection, resultMap);
	}

	@Test(description = "查询用户上传漫画的某一页时，返回这一页的list和上传漫画总条数Count", dataProvider = "dataprovider")
	public void WD_WJTD_347_348_whenAskUserManhuaUploadedRecommendForOnePageThenReturnThisPageListAndAllManhuaUploadedSize(
			int pageIndex, int pageSize, int resourceType, int userId, List<Map<String, Object>> list) {
		int start = (pageIndex - 1) * pageSize + 1;
		int end = pageIndex * pageSize;
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> resultMap = new HashMap<>();
		map.put("resourceType", resourceType);
		map.put("status", "RECOMMEND");
		map.put("userId", userId);
		map.put("start", start);
		map.put("end", end);
		when(userCenterManHuaBLL.selectRecommendManHua(map)).thenReturn(list);
		when(userCenterManHuaBLL.selectRecommendManHuaTotalSize(map)).thenReturn(25);
		resultMap.put("DataSet", list);
		resultMap.put("Count", 25);
		Map<String, Object> collection = userCenterManHuaServiceImpl.getUpload("RECOMMEND", resourceType, userId,
				pageIndex, pageSize);
		Assert.assertEquals(collection, resultMap);
	}

	@Test(description = "查询用户上传漫画通过审核状态的筛选条件的某一页时，返回这一页的list和上传漫画总条数Count", dataProvider = "dataprovider2")
	public void WD_WJTD_352_347_348_whenAskUserManhuaUploadedByReviewStatusForOnePageThenReturnThisPageListAndAllManhuaUploadedSize(
			int pageIndex, int pageSize, int resourceType, int userId, List<Map<String, Object>> list, String status) {
		int start = (pageIndex - 1) * pageSize + 1;
		int end = pageIndex * pageSize;
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> resultMap = new HashMap<>();
		map.put("resourceType", resourceType);
		map.put("status", status);
		map.put("userId", userId);
		map.put("start", start);
		map.put("end", end);
		when(userCenterManHuaBLL.selectUserUpload(map)).thenReturn(list);
		when(userCenterManHuaBLL.selectUserUploadTotalSize(map)).thenReturn(25);
		resultMap.put("DataSet", list);
		resultMap.put("Count", 25);
		Map<String, Object> collection = userCenterManHuaServiceImpl.getUpload(status, resourceType, userId, pageIndex,
				pageSize);
		Assert.assertEquals(collection, resultMap);
	}

	@DataProvider(name = "dataprovider")
	public Object[][] providerMethod() {
		Object[][] result = null;
		int pageSize = 3;
		int pageIndex = 1;
		int resourceType = 4;
		int userId = 294285;
		Map<String, Object> map = new HashMap<>();
		map.put("test", "test");
		Map<String, Object> map2 = map;
		Map<String, Object> map3 = map;
		List<Map<String, Object>> list = new ArrayList<>();
		list.add(map);
		list.add(map2);
		list.add(map3);
		result = new Object[][] { { pageIndex, pageSize, resourceType, userId, list } };
		return result;
	}

	@DataProvider(name = "dataprovider2")
	public Object[][] providerMethodForUploadManHuaReview() {
		Object[][] result = null;
		int pageSize = 3;
		int pageIndex = 1;
		int resourceType = 4;
		int userId = 294285;
		Map<String, Object> map = new HashMap<>();
		map.put("test", "test");
		Map<String, Object> map2 = map;
		Map<String, Object> map3 = map;
		List<Map<String, Object>> list = new ArrayList<>();
		list.add(map);
		list.add(map2);
		list.add(map3);
		result = new Object[][] { { pageIndex, pageSize, resourceType, userId, list, Constans.REVIEW_FAIL },
				{ pageIndex, pageSize, resourceType, userId, list, Constans.REVIEW_ING },
				{ pageIndex, pageSize, resourceType, userId, list, Constans.REVIEW_YES } };
		return result;
	}

	@Configuration
	public static class Config {
		@Bean
		public UserCenterManHuaServiceImpl UserCenterManHuaServiceImpl() {
			return new UserCenterManHuaServiceImpl();
		}

		@Bean
		public UserCenterManHuaBLL UserCenterManHuaBLL() {
			return mock(UserCenterManHuaBLL.class);
		}

	}
}
