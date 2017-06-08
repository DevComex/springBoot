/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：才帅
 * 联系方式：caishuai@gyyx.cn 
 * 创建时间：2016-12-19
 * 版本号：v1.0
 * 本类主要用途叙述：玩家天地，漫画专区，后台漫画管理审核的service实现类
 */
package cn.gyyx.wd.wanjia.cartoon.service.impl;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.wd.wanjia.ResultBean;
import cn.gyyx.wd.wanjia.cartoon.beans.Constans;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdManhuaBook;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdManhuaPage;
import cn.gyyx.wd.wanjia.cartoon.beans.tools.MessageEnum;
import cn.gyyx.wd.wanjia.cartoon.bll.ManHuaBrowseBLL;
import cn.gyyx.wd.wanjia.cartoon.bll.ManHuaManageReviewBLL;
import cn.gyyx.wd.wanjia.cartoon.bll.upload.ManHuaInfoBLL;
import cn.gyyx.wd.wanjia.cartoon.service.ManHuaManageReviewService;
import cn.gyyx.wd.wanjia.cartoon.service.MessageService;

@Service
@Transactional
public class ManHuaManageReviewServiceImpl implements ManHuaManageReviewService {
	private static final Logger logger = GYYXLoggerFactory.getLogger(ManHuaManageReviewService.class);
	@Autowired
	private ManHuaManageReviewBLL manageReviewBLL;
	@Autowired
	private ManHuaBrowseBLL manHuaBrowseBLL;
	@Autowired
	private ManHuaInfoBLL manHuaInfoBLL;
	@Autowired
	private MessageService messageService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.gyyx.wd.wanjia.cartoon.service.ManHuaManageReviewService#getViewList(
	 * java.util.Map)
	 */
	@Override
	public List<Map<String, Object>> getViewList(Map<String, Object> map) {
		List<Map<String, Object>> viewList = manageReviewBLL.getViewList(map);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (Map<String, Object> map2 : viewList) {
			map2.put("create_time", formatter.format(map2.get("create_time")));
			map2.put("is_delete", ((map2.get("is_delete").equals(0))?"已展示":"未展示"));
			map2.put("is_closed", ((map2.get("is_closed").equals(1))?"已完结":"未完结"));
		}
		logger.debug("获取后台展示的经筛选的漫画章节列表，转化时间格式，展示格式，完结格式。manageReviewBLL.getViewList=" + viewList);
		return viewList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.gyyx.wd.wanjia.cartoon.service.ManHuaManageReviewService#showAll(java.
	 * util.List)
	 */
	@Override
	@Transactional
	public ResultBean<String> showAll(List<Integer> list) {
		ResultBean<String> result = new ResultBean<>();
		result.setSuccess(false);
		result.setMessage("操作失败");
		List<WanwdManhuaBook> bookList = manageReviewBLL.selectBookListByPrimaryKey(list);
		logger.debug("获取章节列表。manageReviewBLL.selectBookListByPrimaryKey=" + bookList);
		if (bookList == null || bookList.size() != list.size()) {
			result.setMessage("选择的数据不存在");
			logger.debug("要修改的数据不存在。bookList：" + bookList + "bookList.size:" + bookList.size() + "askList.size:"
					+ list.size());
			return result;
		}
		for (WanwdManhuaBook book : bookList) {
			if (!book.getReviewStatus().equals(Constans.REVIEW_YES)) {
				result.setMessage("选择的数据中必须是已审核的稿件");
				logger.debug("章节code:" + book.getCode() + "不是已审核状态，反馈:'选择的数据中必须是已审核的稿件'");
				return result;
			}
			if (book.getIsDelete() == 0) {
				list.remove(book.getCode());
				logger.debug(book.getCode() + "是已展示漫画，从修改状态list里移除");
			}
		}
		if (list.size() == 0) {
			result.setMessage("选中记录已全部已展示，请重新选择！");
			logger.debug("list.size:" + list.size() + "选中记录已全部已展示，请重新选择！");
			return result;
		}
		int i = manageReviewBLL.updateBookListToShow(list);
		logger.debug("更新记录状态。条数：" + i);
		if (i == list.size()) {
			logger.debug("更新记录条数manageReviewBLL.updateBookListToShow：" + i + "和请求更新条数" + list.size() + "一致");
			result.setSuccess(true);
			result.setMessage("操作成功！点击确定后更新列表状态");
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.gyyx.wd.wanjia.cartoon.service.ManHuaManageReviewService#hiddenAll(
	 * java.util.List)
	 */
	@Override
	@Transactional
	public ResultBean<String> hiddenAll(List<Integer> list) {
		ResultBean<String> result = new ResultBean<>();
		result.setSuccess(false);
		result.setMessage("操作失败");
		List<WanwdManhuaBook> bookList = manageReviewBLL.selectBookListByPrimaryKey(list);
		logger.debug("获取章节列表。manageReviewBLL.selectBookListByPrimaryKey=" + bookList);
		if (bookList == null || bookList.size() != list.size()) {
			result.setMessage("选择的数据不存在");
			logger.debug("要修改的数据不存在。bookList：" + bookList + "bookList.size:" + bookList.size() + "askList.size:"
					+ list.size());
			return result;
		}
		for (WanwdManhuaBook book : bookList) {
			if (!book.getReviewStatus().equals(Constans.REVIEW_YES)) {
				result.setMessage("选择的数据中必须是已审核的稿件");
				logger.debug("章节code:" + book.getCode() + "不是已审核状态，反馈:'选择的数据中必须是已审核的稿件'");
				return result;
			}
			if (book.getIsDelete() == 1) {
				list.remove(book.getCode());
				logger.debug(book.getCode() + "是已隐藏漫画，从修改状态list里移除");
			}
		}

		if (list.size() == 0) {
			result.setMessage("选中记录已全部隐藏，请重新选择");
			logger.debug("list.size:" + list.size() + "选中记录已全部已隐藏，请重新选择！");
			return result;
		}
		int i = manageReviewBLL.updateBookListToHidden(list);
		if (i == list.size()) {
			logger.debug("更新记录条数manageReviewBLL.updateBookListToHidden：" + i + "和请求更新条数" + list.size() + "一致");
			result.setSuccess(true);
			result.setMessage("操作成功！点击确定后更新列表状态");
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.gyyx.wd.wanjia.cartoon.service.ManHuaManageReviewService#statusFailAll
	 * (java.util.List)
	 */
	@Override
	@Transactional
	public ResultBean<String> statusFailAll(List<Integer> list) {
		ResultBean<String> result = new ResultBean<>();
		result.setSuccess(false);
		result.setMessage("操作失败");
		List<WanwdManhuaBook> bookList = manageReviewBLL.selectBookListByPrimaryKey(list);
		logger.debug("获取章节列表。manageReviewBLL.selectBookListByPrimaryKey=" + bookList);
		if (bookList == null || bookList.size() != list.size()) {
			result.setMessage("选择的数据不存在");
			logger.debug("要修改的数据不存在。bookList：" + bookList + "bookList.size:" + bookList.size() + "askList.size:"
					+ list.size());
			return result;
		}
		for (WanwdManhuaBook book : bookList) {
			if (!book.getReviewStatus().equals(Constans.REVIEW_ING)) {
				result.setMessage("只能删除未审核的记录");
				logger.debug("章节code:" + book.getCode() + "不是未审核状态，反馈:'选择的数据中必须是未审核的稿件'");
				return result;
			}
		}
		int i = manageReviewBLL.updateBookListToStatusFail(list);
		if (i == list.size()) {
			// 为每个审核fail的发送消息
			logger.debug("更新记录条数manageReviewBLL.updateBookListToStatusFail：" + i + "和请求更新条数" + list.size() + "一致");
			for (WanwdManhuaBook book : bookList) {
				boolean saveMessage = saveMessage(book, MessageEnum.REVIEW_FAIL_MESSAGE);
				logger.debug("为book.code:" + book.getCode() + "审核fail发送消息，Success？"+saveMessage);
			}
			result.setSuccess(true);
			result.setMessage("删除成功！该章节审核失败，更新审核状态。");

		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.gyyx.wd.wanjia.cartoon.service.ManHuaManageReviewService#
	 * showAllBookForOneManhua(java.lang.Integer)
	 */
	@Override
	public List<Map<String, Object>> showAllBookForOneManhua(Integer bookCode) {
		List<Map<String, Object>> books = manageReviewBLL.selectBelongSameManhuaWithNoFailByBookCode(bookCode);
		for (Map<String, Object> book : books) {
			book.put("book_num", MessageFormat.format("第{0}章", book.get("book_num")));
		}
		return books;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.gyyx.wd.wanjia.cartoon.service.ManHuaManageReviewService#
	 * getBookInfoAndPage(java.lang.Integer)
	 */
	@Override
	public Map<String, Object> getBookInfoAndPage(Integer bookCode) {
		Map<String, Object> map = new HashMap<>();
		WanwdManhuaBook manhuaBook = manHuaBrowseBLL.selectBookByPrimaryKey(bookCode);
		logger.debug("获取漫画章节信息manHuaBrowseBLL.selectBookByPrimaryKey：" + manhuaBook);
		manhuaBook.setBookNum(MessageFormat.format("第{0}章", manhuaBook.getBookNum()));
		List<Map<String, Object>> pageList = manageReviewBLL.selectAllPageWithBookCode(bookCode);
		logger.debug("获取漫画章节下每页图片信息manageReviewBLL.selectAllPageWithBookCode：" + manhuaBook);
		map.put("manhuaBook", manhuaBook);
		map.put("pageList", pageList);
		return map;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.gyyx.wd.wanjia.cartoon.service.ManHuaManageReviewService#reviewFinish(
	 * java.lang.Integer, java.util.List, java.util.List)
	 */
	@Override
	@Transactional
	public ResultBean<String> reviewFinish(Integer bookCode, List<String> name, List<String> urls, String reply) {
		ResultBean<String> result = new ResultBean<>();
		result.setSuccess(false);
		List<Integer> list = new ArrayList<>();
		list.add(bookCode);
		List<WanwdManhuaBook> books = manageReviewBLL.selectBookListByPrimaryKey(list);
		if (books == null || books.size() != 1) {
			logger.debug(
					"要修改的数据不存在。bookList：" + books + "bookList.size:" + books.size() + "askList.size:" + list.size());
			result.setMessage("请求数据不存在或请求数据不正确");
			return result;
		}
		WanwdManhuaBook book=books.get(0);
		if (!(book.getReviewStatus().equals(Constans.REVIEW_ING))) {
			result.setMessage("请求数据必须是未审核状态");
			logger.debug("章节code:" + book.getCode() + "不是未审核状态，反馈:'选择的数据中必须是未审核的稿件'");
			return result;
		}
		Date date = new Date();
		book.setIsDelete(0);
		book.setReviewStatus(Constans.REVIEW_YES);
		book.setReviewTime(date);
		Integer updateByPrimaryKey = manageReviewBLL.updateByPrimaryKey(book);
		if (updateByPrimaryKey <= 0) {
			result.setMessage("更改审核状态失败");
			logger.debug("章节code:" + book.getCode() + "更改审核状态失败");
			return result;
		}

		// 存储章节中图片
		manageReviewBLL.changeIsDelete(bookCode);
		logger.debug("章节code:" + book.getCode() + "删除原有图片");
		WanwdManhuaPage page = new WanwdManhuaPage();
		for (int i = 0; i < urls.size(); i++) {
			if (name == null || name.get(i) == null) {
				page.setPageName("");
			} else {
				page.setPageName(name.get(i));
			}
			page.setBookCode(bookCode);
			page.setCreateTime(date);
			page.setPagePictureUrl(urls.get(i));
			page.setPagePictureNum(i);
			manHuaInfoBLL.insertPages(page);
		}
		logger.debug("章节code:" + book.getCode() + "插入新图片");
		// reply编辑回复处理
		if (reply != null&&!reply.equals("")&&reply!="") {
			logger.debug("章节code:" + book.getCode() + "处理编辑回复");
			boolean saveReply = saveEditMessage(book, MessageEnum.EDIT_MESSAGE, reply);
			if (!saveReply) {
				result.setSuccess(false);
				result.setMessage("编辑回复失败");
				return result;
			}
		}
		// 为审核success的发送消息
		boolean saveMessage = saveMessage(book, MessageEnum.REVIEW_YES_MESSAGE);
		logger.debug("为book.code:" + book.getCode() + "审核SUCCESS发送消息，Success？"+saveMessage);
		result.setSuccess(true);
		result.setMessage("该章节审核通过");
		return result;

	}

	/**
	 * 发送推送消息
	 * 
	 * @param book
	 * @param messageEnum
	 * @return
	 */
	private boolean saveMessage(WanwdManhuaBook book, MessageEnum messageEnum) {
		String context = MessageFormat.format(messageEnum.getName(), book.getBookName());
		logger.debug("发送推送消息：" + context + " to userid=" + book.getUserId());
		return messageService.saveMessage(book.getUserId(), book.getBookName(), book.getCode(), messageEnum.getIndex(),
				context);
	}
	 
	private boolean saveEditMessage(WanwdManhuaBook book, MessageEnum messageEnum,String context) {
		 context = MessageFormat.format(messageEnum.getName(), book.getBookName(), context);
		logger.debug("发送推送消息：" + context + " to userid=" + book.getUserId());
		return messageService.saveMessage(book.getUserId(), book.getBookName(), book.getCode(), messageEnum.getIndex(),
				context);
	}
	
}
