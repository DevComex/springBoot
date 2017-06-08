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
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;
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
import cn.gyyx.wd.wanjia.ResultBean;
import cn.gyyx.wd.wanjia.ServerBean;
import cn.gyyx.wd.wanjia.ServerMessage;
import cn.gyyx.wd.wanjia.cartoon.beans.Constans;
import cn.gyyx.wd.wanjia.cartoon.beans.UploadFormBean;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdCategory;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdManhua;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdManhuaBook;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdManhuaPage;
import cn.gyyx.wd.wanjia.cartoon.bll.upload.ManHuaCategoryInfoBLL;
import cn.gyyx.wd.wanjia.cartoon.bll.upload.ManHuaInfoBLL;
import cn.gyyx.wd.wanjia.cartoon.service.upload.impl.ManHuaUploadServiceImpl;

@ContextConfiguration(classes = { ManHuaUploadServiceUploadTest.Config.class })
public class ManHuaUploadServiceUploadTest extends AbstractTestNGSpringContextTests {
	@Autowired
	private ManHuaUploadServiceImpl manHuaUploadService;

	@Autowired
	private ManHuaInfoBLL manHuaInfoBLL;
	@Autowired
	private ManHuaCategoryInfoBLL manHuaCategoryInfoBLL;
	private WDGameAgent gameAgent;

	@BeforeClass
	public void init() {
		gameAgent = Mockito.mock(WDGameAgent.class);
		manHuaUploadService.setGameAgent(gameAgent);
	}

	@Test(dataProvider = "dataprovider", description = "漫画的信息全部填写正确，点击上传，结果：上传成功")
	public void WD_WJTD_038_047_048_049_whenInfoCorrectThenReturnUploadSuccess(UploadFormBean form, UserInfo info,
			List<String> pagename, List<String> pageurl, WanwdManhua oldmanhua, WanwdManhua newmanhua,
			WanwdManhuaBook book, WanwdManhuaPage page) {
		// mock server
		ServerBean bean = new ServerBean();
		ServerMessage[] data = { new ServerMessage(form.getServerCode(), "ceshi1", "true"),
				new ServerMessage("2", "ceshi2", "true") };
		bean.setData(data);
		try {
			when(gameAgent.getServers(2, form.getNetType())).thenReturn(bean);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// mock category
		List<WanwdCategory> list = new ArrayList<>();
		WanwdCategory wanwdCategory = new WanwdCategory();
		wanwdCategory.setCode(form.getCategoryCode());
		list.add(0, wanwdCategory);
		wanwdCategory = new WanwdCategory();
		wanwdCategory.setCode(0);
		list.add(1, wanwdCategory);
		wanwdCategory = new WanwdCategory();
		wanwdCategory.setCode(10);
		list.add(2, wanwdCategory);
		when(manHuaCategoryInfoBLL.findAll()).thenReturn(list);
		// mock manhua
		when(manHuaInfoBLL.findManhuaByTitle(form.getTitle())).thenReturn(oldmanhua);
		when(manHuaInfoBLL.addManhua(oldmanhua)).thenReturn(oldmanhua.getCode());
		when(manHuaInfoBLL.insertBook(book)).thenReturn(1);
		Mockito.doNothing().when(manHuaInfoBLL).insertPages(Mockito.argThat(new IsPage()));

		ResultBean<Map<String, String>> uploadSave = manHuaUploadService.uploadSave(form, info, pagename, pageurl,
				true);
		Assert.assertEquals(uploadSave.isSuccess(), true);
	}

	class IsPage extends ArgumentMatcher<WanwdManhuaPage> {
		@Override
		public boolean matches(Object argument) {
			if (argument.getClass() == WanwdManhuaPage.class) {
				return true;
			}
			return false;
		}
	}

	/***/

	@DataProvider(name = "dataprovider")
	public Object[][] providerMethod() {
		Object[][] result = null;
		UploadFormBean form = new UploadFormBean(1, "123", "作者名", "漫画名称", "章节名称", "2", "漫画简介", 0, 130, "1234");
		UserInfo info = new UserInfo();
		info.setUserId(111);
		List<String> pagename = new ArrayList<>();
		pagename.add(0, "a");
		;
		pagename.add(1, "b");
		pagename.add(2, "c");
		List<String> pageurl = new ArrayList<>();
		pageurl.add(0, "aurl");
		pageurl.add(1, "burl");
		pageurl.add(2, "curl");

		WanwdManhua oldmanhua = new WanwdManhua(1, form.getTitle(), "lalala", form.getAuthorName(), "",
				info.getUserId(), form.getCategoryCode(), new Date(), new Date(), 0, 0, 0);
		WanwdManhua newmanhua = new WanwdManhua();
		newmanhua.setContext(form.getContext());
		newmanhua.setIsClosed(form.getIsClosed());
		newmanhua.setCode(oldmanhua.getCode());
		WanwdManhuaBook book = new WanwdManhuaBook();
		book.setManhuaCode(oldmanhua.getCode());
		book.setBookName(MessageFormat.format("第{0}章 ", form.getBookNum()) + form.getBookName());
		book.setBookNum(form.getBookNum());
		book.setCreateTime(new Date());
		book.setReviewStatus(Constans.REVIEW_ING);
		WanwdManhuaPage page = new WanwdManhuaPage();
		if (pagename.get(0) == null) {
			page.setPageName("");
		} else {
			page.setPageName(pagename.get(0));
		}
		page.setBookCode(1);
		page.setCreateTime(new Date());
		page.setPagePictureUrl(pageurl.get(0));
		page.setPagePictureNum(0);
		result = new Object[][] { { form, info, pagename, pageurl, oldmanhua, newmanhua, book, page } };
		return result;
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
