/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：才帅
 * 联系方式：caishuai@gyyx.cn 
 * 创建时间：2016-12-8
 * 版本号：v1.0
 * 本类主要用途叙述：玩家天地，漫画专区，漫画上传测试类
 */
package cn.gyyx.wd.wanjia.cartoon.service.upload;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
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
import org.testng.annotations.Test;

import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.wd.wanjia.ResultBean;
import cn.gyyx.wd.wanjia.ServerBean;
import cn.gyyx.wd.wanjia.ServerMessage;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdManhua;
import cn.gyyx.wd.wanjia.cartoon.bll.upload.ManHuaCategoryInfoBLL;
import cn.gyyx.wd.wanjia.cartoon.bll.upload.ManHuaInfoBLL;
import cn.gyyx.wd.wanjia.cartoon.service.upload.impl.ManHuaUploadServiceImpl;

@ContextConfiguration(classes = { ManHuaUploadServiceTest.Config.class })
public class ManHuaUploadServiceTest extends AbstractTestNGSpringContextTests {
	@Autowired
	private ManHuaUploadServiceImpl manHuaUploadService;

	@Autowired
	private ManHuaInfoBLL manHuaInfoBLL;
	@Autowired
	private ManHuaCategoryInfoBLL manHuaCategoryInfoBLL;
	private WDGameAgent gameAgent;

	@BeforeClass
	public void init() {
		MockitoAnnotations.initMocks(this);
		gameAgent = Mockito.mock(WDGameAgent.class);
		manHuaUploadService.setGameAgent(gameAgent);
	}

	@Test(description = "选择区服第一个下拉框里的网通,点击区组第二个下拉框,结果：查看到对应在网通中的区服名称")

	public void WD_WJTD_019_getServerThenReturnServerList() {
		ServerBean bean = new ServerBean();
		ServerMessage[] data = { new ServerMessage("1", "ceshi1", "true"), new ServerMessage("2", "ceshi2", "true") };
		bean.setData(data);
		try {
			when(gameAgent.getServers(2, 1)).thenReturn(bean);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ResultBean<ServerBean> serverlist = manHuaUploadService.serverlist(1);
		Assert.assertEquals(serverlist.getData(), bean);
	}

	@Test(description = "玩家输入的漫画名称是自己已经上传的漫画，结果：玩家填写的漫画名称为之前曾上传过的漫画名称")
	public void WD_WJTD_022_025_026_036_whenInputManhuaTitleHasBeenUploadAndBelongHimselfThenReturnCheckResult() {
		WanwdManhua manhua = new WanwdManhua();
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(111);
		manhua.setAuthorId(userInfo.getUserId());
		manhua.setTitle("aaa");
		manhua.setIsClosed(0);
		when(manHuaInfoBLL.findManhuaByTitle("aaa")).thenReturn(manhua);
		ResultBean<Map<String, Object>> result = manHuaUploadService.checkManHuaTitle(userInfo, "aaa");
		Assert.assertEquals(result.getMessage(), "漫画可以续传");
	}

	@Test(description = "玩家输入的漫画未曾上传过的漫画名称，结果：玩家填写的漫画名称为新的漫画名称")
	public void WD_WJTD_027_028_029_whenInputManhuaTitleIsNewManhuaNoOneUploadThenReturnCheckResult() {
		UserInfo userInfo = new UserInfo();
		when(manHuaInfoBLL.findManhuaByTitle("aaa")).thenReturn(null);
		ResultBean<Map<String, Object>> result = manHuaUploadService.checkManHuaTitle(userInfo, "aaa");
		Assert.assertEquals(result.getMessage(), "漫画名称可用");
	}

	@Test(description = "玩家手动输入系统中已经存在的漫画名称且当前存在的漫画名称不是当前玩家账号上传的，结果：提示玩家： 您的帐号与该作品记录的作者帐号不一致，无法续传该系列漫画的后续章节，如有疑问请联系编辑")
	public void WD_WJTD_030_whenInputManhuaTitleIsBelongOtherAuthorThenReturnCheckResult() {
		WanwdManhua manhua = new WanwdManhua();
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(111);
		manhua.setAuthorId(1111);
		manhua.setTitle("aaa");
		when(manHuaInfoBLL.findManhuaByTitle("aaa")).thenReturn(manhua);
		ResultBean<Map<String, Object>> result = manHuaUploadService.checkManHuaTitle(userInfo, "aaa");
		Assert.assertEquals(result.getMessage(), "您的帐号与该作品记录的作者帐号不一致，无法续传该系列漫画的后续章节，如有疑问请联系编辑~");
	}

	@Test(description = "漫画名称已填写新漫画名称。结果章节数默认显示第一章")
	public void WD_WJTD_040_whenInputManhuaTitleIsNewManhuaNoOneUploadThenReturnBookNum() {
		UserInfo userInfo = new UserInfo();
		when(manHuaInfoBLL.findManhuaByTitle("aaa")).thenReturn(null);
		ResultBean<Map<String, Object>> result = manHuaUploadService.checkManHuaTitle(userInfo, "aaa");
		Assert.assertEquals(result.getData().get("book_num"), 1);
	}

	@Test(description = "漫画名称已填写新漫画名称。漫画类型可选择")
	public void WD_WJTD_051_whenInputManhuaTitleIsNewManhuaNoOneUploadThenReturnManhuaCategoryCodeIsNull() {
		UserInfo userInfo = new UserInfo();
		when(manHuaInfoBLL.findManhuaByTitle("aaa")).thenReturn(null);
		ResultBean<Map<String, Object>> result = manHuaUploadService.checkManHuaTitle(userInfo, "aaa");
		Assert.assertEquals(result.getData().get("manhua"), null);
	}

	@Test(description = "漫画名称选择已上传但未完结的漫画。章节名称默认为该系列漫画的后续章节")
	public void WD_WJTD_042_whenInputManhuaTitleHasBeenUploadAndBelongHimselfThenReturnBookNum() {
		WanwdManhua manhua = new WanwdManhua();
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(111);
		manhua.setAuthorId(userInfo.getUserId());
		manhua.setTitle("aaa");
		manhua.setIsClosed(0);
		when(manHuaInfoBLL.findManhuaByTitle("aaa")).thenReturn(manhua);
		when(manHuaInfoBLL.findManhuaNextBookNum(manhua)).thenReturn(100);
		ResultBean<Map<String, Object>> result = manHuaUploadService.checkManHuaTitle(userInfo, "aaa");
		Assert.assertEquals(result.getData().get("book_num"), 100);
	}

	@Test(description = "漫画名称选择已上传但未完结的漫画。带出原有漫画简介内容")
	public void WD_WJTD_044_whenInputManhuaTitleHasBeenUploadAndBelongHimselfThenReturnContext() {
		String context = "漫画简介";
		WanwdManhua manhua = new WanwdManhua();
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(111);
		manhua.setAuthorId(userInfo.getUserId());
		manhua.setTitle("aaa");
		manhua.setIsClosed(0);
		manhua.setContext(context);
		when(manHuaInfoBLL.findManhuaByTitle("aaa")).thenReturn(manhua);
		ResultBean<Map<String, Object>> result = manHuaUploadService.checkManHuaTitle(userInfo, "aaa");
		Assert.assertEquals(((WanwdManhua) result.getData().get("manhuaInfo")).getContext(), context);
	}

	@Test(description = "漫画名称选择已上传但未完结的漫画。结果：漫画类型不可选择，默认为之前选择的类型")
	public void WD_WJTD_042_050_whenInputManhuaTitleHasBeenUploadAndBelongHimselfThenReturnType() {
		Integer categoryCode = 111;
		WanwdManhua manhua = new WanwdManhua();
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(111);
		manhua.setAuthorId(userInfo.getUserId());
		manhua.setTitle("aaa");
		manhua.setIsClosed(0);
		manhua.setCategoryCode(categoryCode);
		when(manHuaInfoBLL.findManhuaByTitle("aaa")).thenReturn(manhua);
		ResultBean<Map<String, Object>> result = manHuaUploadService.checkManHuaTitle(userInfo, "aaa");
		Assert.assertEquals(categoryCode, ((WanwdManhua) result.getData().get("manhuaInfo")).getCategoryCode());
	}

	@Test(description = "再次上传漫画时，漫画名称还是选择填写之前已完结的漫画名称。结果：该漫画已完结，请重新选择！")
	public void whenInputManhuaTitleHasBeenUploadAndBelongHimselfThenReturnType() {
		Integer categoryCode = 111;
		WanwdManhua manhua = new WanwdManhua();
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(111);
		manhua.setAuthorId(userInfo.getUserId());
		manhua.setTitle("aaa");
		manhua.setIsClosed(1);
		manhua.setCategoryCode(categoryCode);
		when(manHuaInfoBLL.findManhuaByTitle("aaa")).thenReturn(manhua);
		ResultBean<Map<String, Object>> result = manHuaUploadService.checkManHuaTitle(userInfo, "aaa");
		Assert.assertEquals(result.getMessage(), "该漫画已完结，请重新选择！");
	}

	@Configuration
	public static class Config {
		@Bean
		public ManHuaUploadServiceImpl manHuaUploadServiceImpl() {
			return new ManHuaUploadServiceImpl();
		}

		@Bean
		public ManHuaInfoBLL manHuaInfoBLL() {
			return mock(ManHuaInfoBLL.class);
		}

		@Bean
		public ManHuaCategoryInfoBLL manHuaCategoryInfoBLL() {
			return mock(ManHuaCategoryInfoBLL.class);
		}

	}
}
