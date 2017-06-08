/**
 * --------------------------------------------------- 
- *  版权所有：北京光宇在线科技有限责任公司
 * 作者：才帅
 * 联系方式：caishuai@gyyx.cn 
 * 创建时间：2016-12-21
 * 版本号：v1.0
 * 本类主要用途叙述：玩家天地，漫画专区，漫画浏览测试类
 */
package cn.gyyx.wd.wanjia.cartoon.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.MessageFormat;
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
import org.testng.annotations.Test;

import cn.gyyx.wd.wanjia.ResultBean;
import cn.gyyx.wd.wanjia.cartoon.beans.Constans;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdManhuaBook;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdManhuaPage;
import cn.gyyx.wd.wanjia.cartoon.beans.tools.MessageEnum;
import cn.gyyx.wd.wanjia.cartoon.bll.ManHuaBrowseBLL;
import cn.gyyx.wd.wanjia.cartoon.bll.ManHuaManageReviewBLL;
import cn.gyyx.wd.wanjia.cartoon.bll.upload.ManHuaInfoBLL;
import cn.gyyx.wd.wanjia.cartoon.dao.WanwdMessageMapper;
import cn.gyyx.wd.wanjia.cartoon.service.impl.ManHuaManageReviewServiceImpl;
import cn.gyyx.wd.wanjia.cartoon.service.upload.ManHuaUploadService;

@ContextConfiguration(classes = { ManHuaManageReviewServiceTest.Config.class })
public class ManHuaManageReviewServiceTest extends AbstractTestNGSpringContextTests {
	@Autowired
	ManHuaManageReviewServiceImpl manHuaManageReviewServiceImpl;
	@Autowired
	private ManHuaManageReviewBLL manageReviewBLL;
	@Autowired
	private ManHuaBrowseBLL manHuaBrowseBLL;
	@Autowired
	private ManHuaInfoBLL manHuaInfoBLL;
	@Autowired
	private MessageService messageService;
	

	@BeforeClass
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	/**
	 * WD_WJTD_001_003_004_005_006_007_008_009_010_011_012_013_014_015_016_017_018为直接return
	 * 
	 * @see ManHuaManageReviewServiceImpl.getViewList
	 */
	/**
	 * WD_WJTD_002为直接return
	 * 
	 * @see ManHuaUploadService.getManHuaAllType
	 */
	/*
	 * 展示
	 */
	@Test(description = "勾选内容全部为已展示，点击展示全部，返回‘选中记录已全部已展示，请重新选择！’提示消息")
	public void WD_WJTD_262_264_whenAllDataIsShowDoShowAllThenReturnMessageAllDataHasBeenShow() {
		List<Integer> bookCode = new ArrayList<>();
		bookCode.add(1);
		bookCode.add(2);
		List<WanwdManhuaBook> books = new ArrayList<>();
		WanwdManhuaBook book = new WanwdManhuaBook();
		book.setCode(1);
		book.setIsDelete(0);
		book.setReviewStatus(Constans.REVIEW_YES);
		books.add(book);
		book = new WanwdManhuaBook();
		book.setCode(2);
		book.setIsDelete(0);
		book.setReviewStatus(Constans.REVIEW_YES);
		books.add(book);
		when(manageReviewBLL.selectBookListByPrimaryKey(bookCode)).thenReturn(books);
		ResultBean<String> showAll = manHuaManageReviewServiceImpl.showAll(bookCode);
		Assert.assertEquals(showAll.getMessage(), "选中记录已全部已展示，请重新选择！");
	}

	@Test(description = "勾选内容存在不是审核通过的，点击展示全部，返回‘选择的数据中必须是已审核的稿件’提示消息")
	public void WD_WJTD_263_272_whenHaveDataIsNotReviewSuccessDoShowAllThenReturnMessageDataMustReviewSuccess() {
		List<Integer> bookCode = new ArrayList<>();
		bookCode.add(1);
		bookCode.add(2);
		List<WanwdManhuaBook> books = new ArrayList<>();
		WanwdManhuaBook book = new WanwdManhuaBook();
		book.setCode(1);
		book.setIsDelete(0);
		book.setReviewStatus(Constans.REVIEW_YES);
		books.add(book);
		book = new WanwdManhuaBook();
		book.setCode(2);
		book.setIsDelete(1);
		book.setReviewStatus(Constans.REVIEW_ING);
		books.add(book);
		when(manageReviewBLL.selectBookListByPrimaryKey(bookCode)).thenReturn(books);
		ResultBean<String> showAll = manHuaManageReviewServiceImpl.showAll(bookCode);
		Assert.assertEquals(showAll.getMessage(), "选择的数据中必须是已审核的稿件");
	}

	@Test(description = "勾选内容存在未展示切不包括不是审核通过的，点击展示全部，返回‘操作成功！点击确定后更新列表状态’提示消息")
	public void WD_WJTD_271_273_whenHaveDataIsNotShowAndAllIsSuccessDoShowAllThenReturnMessageOperationSuccess() {
		List<Integer> bookCode = new ArrayList<>();
		bookCode.add(1);
		bookCode.add(2);
		List<WanwdManhuaBook> books = new ArrayList<>();
		WanwdManhuaBook book = new WanwdManhuaBook();
		book.setCode(1);
		book.setIsDelete(0);
		book.setReviewStatus(Constans.REVIEW_YES);
		books.add(book);
		book = new WanwdManhuaBook();
		book.setCode(2);
		book.setIsDelete(1);
		book.setReviewStatus(Constans.REVIEW_YES);
		books.add(book);
		when(manageReviewBLL.selectBookListByPrimaryKey(bookCode)).thenReturn(books);
		when(manageReviewBLL.updateBookListToShow(bookCode)).thenReturn(1);
		ResultBean<String> showAll = manHuaManageReviewServiceImpl.showAll(bookCode);
		Assert.assertEquals(showAll.getMessage(), "操作成功！点击确定后更新列表状态");
	}

	/*
	 * 隐藏
	 */
	@Test(description = "勾选内容全部为未展示且全部为审核通过的，点击隐藏全部，返回‘选中记录已全部隐藏，请重新选择’提示消息")
	public void WD_WJTD_265_267_whenAllDataIsHideDoHideAllThenReturnMessageAllDataHasBeenHide() {
		List<Integer> bookCode = new ArrayList<>();
		bookCode.add(1);
		bookCode.add(2);
		List<WanwdManhuaBook> books = new ArrayList<>();
		WanwdManhuaBook book = new WanwdManhuaBook();
		book.setCode(1);
		book.setIsDelete(1);
		book.setReviewStatus(Constans.REVIEW_YES);
		books.add(book);
		book = new WanwdManhuaBook();
		book.setCode(2);
		book.setIsDelete(1);
		book.setReviewStatus(Constans.REVIEW_YES);
		books.add(book);
		when(manageReviewBLL.selectBookListByPrimaryKey(bookCode)).thenReturn(books);
		ResultBean<String> hiddenAll = manHuaManageReviewServiceImpl.hiddenAll(bookCode);
		Assert.assertEquals(hiddenAll.getMessage(), "选中记录已全部隐藏，请重新选择");
	}

	@Test(description = "勾选内容存在不是审核通过的，点击隐藏全部，返回‘选择的数据中必须是已审核的稿件’提示消息")
	public void WD_WJTD_266_269_whenHaveDataIsNotReviewSuccessDoHideAllThenReturnMessageDataMustReviewSuccess() {
		List<Integer> bookCode = new ArrayList<>();
		bookCode.add(1);
		bookCode.add(2);
		List<WanwdManhuaBook> books = new ArrayList<>();
		WanwdManhuaBook book = new WanwdManhuaBook();
		book.setCode(1);
		book.setIsDelete(1);
		book.setReviewStatus(Constans.REVIEW_YES);
		books.add(book);
		book = new WanwdManhuaBook();
		book.setCode(2);
		book.setIsDelete(1);
		book.setReviewStatus(Constans.REVIEW_ING);
		books.add(book);
		when(manageReviewBLL.selectBookListByPrimaryKey(bookCode)).thenReturn(books);
		ResultBean<String> hiddenAll = manHuaManageReviewServiceImpl.hiddenAll(bookCode);
		Assert.assertEquals(hiddenAll.getMessage(), "选择的数据中必须是已审核的稿件");
	}

	@Test(description = "勾选内容存在已展示的，点击隐藏全部，返回‘操作成功！点击确定后更新列表状态’提示消息")
	public void WD_WJTD_268_270_whenHaveDataIsNotHideAndAllIsSuccessDoHideAllThenReturnMessageOperationSuccess() {
		List<Integer> bookCode = new ArrayList<>();
		bookCode.add(1);
		bookCode.add(2);
		List<WanwdManhuaBook> books = new ArrayList<>();
		WanwdManhuaBook book = new WanwdManhuaBook();
		book.setCode(1);
		book.setIsDelete(0);
		book.setReviewStatus(Constans.REVIEW_YES);
		books.add(book);
		book = new WanwdManhuaBook();
		book.setCode(2);
		book.setIsDelete(1);
		book.setReviewStatus(Constans.REVIEW_YES);
		books.add(book);
		when(manageReviewBLL.selectBookListByPrimaryKey(bookCode)).thenReturn(books);
		when(manageReviewBLL.updateBookListToHidden(bookCode)).thenReturn(1);
		ResultBean<String> hiddenAll = manHuaManageReviewServiceImpl.hiddenAll(bookCode);
		Assert.assertEquals(hiddenAll.getMessage(), "操作成功！点击确定后更新列表状态");
	}

	/*
	 * 审核未通过（删除）
	 */
	@Test(description = "勾选内容存在不是未审核的，点击删除全部（状态置为：审核未通过），返回‘只能删除未审核的记录")
	public void WD_WJTD_261_whenHaveDataIsNotReview_IngDoStatusFailAllThenReturnMessageOnlyDoStatusFailOnReview_Ing() {
		List<Integer> bookCode = new ArrayList<>();
		bookCode.add(1);
		bookCode.add(2);
		List<WanwdManhuaBook> books = new ArrayList<>();
		WanwdManhuaBook book = new WanwdManhuaBook();
		book.setCode(1);
		book.setReviewStatus(Constans.REVIEW_ING);
		books.add(book);
		book = new WanwdManhuaBook();
		book.setCode(2);
		book.setReviewStatus(Constans.REVIEW_YES);
		books.add(book);
		when(manageReviewBLL.selectBookListByPrimaryKey(bookCode)).thenReturn(books);
		ResultBean<String> statusFailAll = manHuaManageReviewServiceImpl.statusFailAll(bookCode);
		Assert.assertEquals(statusFailAll.getMessage(), "只能删除未审核的记录");
	}

	@Test(description = "勾选内容全部为审核中的或在审核时点击删除，点击删除(全部)（状态置为：审核未通过），返回‘删除成功！该章节审核失败，更新审核状态。’提示消息")
	public void WD_WJTD_257_258_259_260_289_290_whenAllDataIsReview_IngDoStatusFailAllHideAllThenReturnMessageOperationSuccess() {
		List<Integer> bookCode = new ArrayList<>();
		bookCode.add(1);
		bookCode.add(2);
		List<WanwdManhuaBook> books = new ArrayList<>();
		WanwdManhuaBook book = new WanwdManhuaBook();
		book.setCode(1);
		book.setReviewStatus(Constans.REVIEW_ING);
		books.add(book);
		book = new WanwdManhuaBook();
		book.setCode(2);
		book.setReviewStatus(Constans.REVIEW_ING);
		books.add(book);
		when(manageReviewBLL.selectBookListByPrimaryKey(bookCode)).thenReturn(books);
		when(manageReviewBLL.updateBookListToStatusFail(bookCode)).thenReturn(bookCode.size());
		String context = MessageFormat.format(MessageEnum.REVIEW_YES_MESSAGE.getName(), book.getBookName());
		when(messageService.saveMessage(book.getUserId(), book.getBookName(), book.getCode(), MessageEnum.REVIEW_YES_MESSAGE.getIndex(),
				context)).thenReturn(true);
		ResultBean<String> statusFailAll = manHuaManageReviewServiceImpl.statusFailAll(bookCode);
		Assert.assertEquals(statusFailAll.getMessage(), "删除成功！该章节审核失败，更新审核状态。");
	}

	@Test(description = "点击某一章节的编辑图标，返回该章对应漫画所有审核通过和未审核的漫画章节列表")
	public void WD_WJTD_280_whenAskEditReturnThisChapterMappingManhuaAllChapter() {
		int bookCode = 70;
		List<Map<String, Object>> resultList = new ArrayList<>();
		List<Map<String, Object>> assertList = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		map.put("book_num", 1);
		map.put("code", bookCode);
		map.put("review_status", Constans.REVIEW_YES);
		resultList.add(map);

		Map<String, Object> map2 = map;
		map2.put("book_num", "第1章");
		assertList.add(map2);
		
		map = new HashMap<>();
		map.put("book_num", 2);
		map.put("code", bookCode + 1);
		map.put("review_status", Constans.REVIEW_ING);
		resultList.add(map);

		map2 = map;
		map2.put("book_num", "第2章");
		assertList.add(map2);
		
		when(manageReviewBLL.selectBelongSameManhuaWithNoFailByBookCode(bookCode)).thenReturn(resultList);
		List<Map<String, Object>> books = manHuaManageReviewServiceImpl.showAllBookForOneManhua(bookCode);
		Assert.assertEquals(books, assertList);
	}

	@Test(description = "点击漫画目录的某一章节，返回漫画这一章的图片和章节信息")
	public void WD_WJTD_274_282_284_whenAskchapterDetailsThenrReturnThisChapterPicAndInfo() {
		int bookCode = 70;
		Map<String, Object> map = new HashMap<>();
		WanwdManhuaBook book=new WanwdManhuaBook();
		book.setBookNum("1");
		WanwdManhuaBook outbook=book;
		outbook.setBookNum("第1章");
		List<Map<String, Object>> list=new ArrayList<>();
		map.put("manhuaBook", outbook);
		map.put("pageList", list);
		when(manHuaBrowseBLL.selectBookByPrimaryKey(bookCode)).thenReturn(book);
		when( manageReviewBLL.selectAllPageWithBookCode(bookCode)).thenReturn(list);
		Map<String, Object> bookInfoAndPage = manHuaManageReviewServiceImpl.getBookInfoAndPage(bookCode);
		Assert.assertEquals(bookInfoAndPage, map);
	}
	@Test(description = "审核页点击完成，更改该章节的对应状态为已审核，同时保存编辑修改的内容")
	public void WD_WJTD_286_287_whenClickFinishThenrReturnReviewSuccess() {
		int bookCode = 70;
		List<Integer> list = new ArrayList<>();
		list.add(bookCode);
		List<WanwdManhuaBook> books=new ArrayList<>();//返回值
		WanwdManhuaBook book=new WanwdManhuaBook();
		book.setReviewStatus(Constans.REVIEW_ING);
		book.setCode(bookCode);
		books.add(book);
		when(manageReviewBLL.selectBookListByPrimaryKey(list)).thenReturn(books);
		when(manageReviewBLL.updateByPrimaryKey(any(WanwdManhuaBook.class))).thenReturn(1);
		when(manageReviewBLL.changeIsDelete(bookCode)).thenReturn(6);
		doNothing().when(manHuaInfoBLL).insertPages(any(WanwdManhuaPage.class));
		String context = MessageFormat.format(MessageEnum.REVIEW_YES_MESSAGE.getName(), book.getBookName());
		when(messageService.saveMessage(book.getUserId(), book.getBookName(), book.getCode(), MessageEnum.REVIEW_YES_MESSAGE.getIndex(),
				context)).thenReturn(true);
		ResultBean<String> result = new ResultBean<>();
		result.setSuccess(true);
		result.setMessage("该章节审核通过");
		List<String> urls=new ArrayList<>();
		urls.add("aaa");
		urls.add("bbb");
		urls.add("ccc");
		
		Assert.assertEquals(manHuaManageReviewServiceImpl.reviewFinish(bookCode, null, urls, null).toString(), result.toString());
	}
	
	@Configuration
	public static class Config {
		@Bean
		public ManHuaManageReviewServiceImpl ManHuaManageReviewServiceImpl() {
			return new ManHuaManageReviewServiceImpl();
		}

		@Bean
		public ManHuaManageReviewBLL ManHuaManageReviewBLL() {
			return mock(ManHuaManageReviewBLL.class);
		}

		@Bean
		public ManHuaBrowseBLL ManHuaBrowseBLL() {
			return mock(ManHuaBrowseBLL.class);
		}

		@Bean
		public ManHuaInfoBLL ManHuaInfoBLL() {
			return mock(ManHuaInfoBLL.class);
		}

		@Bean
		public MessageService MessageService() {
			return mock(MessageService.class);
		}
		@Bean
		public WanwdMessageMapper WanwdMessageMapper() {
			return mock(WanwdMessageMapper.class);
		}

	}
}
