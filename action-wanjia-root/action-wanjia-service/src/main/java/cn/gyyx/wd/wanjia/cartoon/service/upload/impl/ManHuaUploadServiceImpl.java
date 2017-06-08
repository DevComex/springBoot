/**
 * --------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：才帅
 * 联系方式：caishuai@gyyx.cn 
 * 创建时间：2016-12-6
 * 版本号：v1.0
 * 本类主要用途叙述：玩家天地，漫画上传用户作者信息的Service实现类
 */
package cn.gyyx.wd.wanjia.cartoon.service.upload.impl;

import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;


import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
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
import cn.gyyx.wd.wanjia.cartoon.service.upload.ManHuaUploadService;
import cn.gyyx.wd.wanjia.cartoon.service.upload.WDGameAgent;

@Service
@Transactional
public class ManHuaUploadServiceImpl implements ManHuaUploadService {
	private static final Logger logger = GYYXLoggerFactory.getLogger(ManHuaUploadService.class);
	// 问道游戏id
	private static final int gameId = 2;
	private WDGameAgent gameAgent = new WDGameAgent();

	@Autowired
	private ManHuaInfoBLL manHuaInfoBLL;
	@Autowired
	private ManHuaCategoryInfoBLL manHuaCategoryInfoBLL;

	/**
	 * @see ManHuaUploadService netType 1网通 2电信3双线
	 */
	@Override
	public ResultBean<ServerBean> serverlist(int netType) {
		logger.info("netType ---- {}", netType);
		ResultBean<ServerBean> result = new ResultBean<>();
		result.setSuccess(false);
		try {
			ServerBean sb = gameAgent.getServers(gameId, netType);
			result.setSuccess(true);
			result.setMessage("查询服务器成功");
			result.setData(sb);
			logger.info("result", result);

		} catch (Exception e) {
			logger.error("玩家天地-漫画上传[获取服务器列表异常]", e);
		}
		return result;
	}

	/**
	 * @see ManHuaUploadService
	 */
	@Override
	public List<WanwdManhua> getManHua(UserInfo userInfo) {
		return manHuaInfoBLL.findAllUnfinishedManhuaNameByAuthor(userInfo);
	}

	@Override
	public ResultBean<Map<String, Object>> checkManHuaTitle(UserInfo userInfo, String title) {
		ResultBean<Map<String, Object>> result = new ResultBean<>();
		Map<String, Object> map = new HashMap<>();
		if (title == null || title == "") {
			result.setSuccess(false);
			result.setMessage("漫画名称不能为空");
			return result;
		}else if (!Pattern.compile("^[a-zA-Z0-9\u4E00-\u9FA5]+$").matcher(title).matches()) {
			result.setSuccess(false);
			result.setMessage("漫画名称只能输入汉字英文字母和数字，请重新输入！");
			return result;
		}
		
		/*System.out.println(title);*/
		WanwdManhua manhua = manHuaInfoBLL.findManhuaByTitle(title);
		/*Integer code = null;
		List<WanwdManhua> list = manHuaInfoBLL.findAllUnfinishedManhuaNameByAuthor(userInfo);
		for (WanwdManhua wanwdManhua : list) {
			if(wanwdManhua.getTitle().equals(title)){
				code=wanwdManhua.getCode();
			}
		}*/
		result.setSuccess(false);
		// 判断漫画名是否存在
		if (manhua==null) {
			// 返回title不存在，可以使用
			result.setSuccess(true);
			result.setMessage("漫画名称可用");
			map.put("manhuaInfo", null);
			map.put("book_num", 1);
			result.setData(map);
			return result;
		} else {
			//WanwdManhua manhua = manHuaInfoBLL.selectByPrimaryKey(code);
			// 判定该漫画是不是属于该作者
			if (userInfo.getUserId().equals(manhua.getAuthorId())) {
				// 判定漫画是否已完结
				if (manhua.getIsClosed() != 0) {
					result.setSuccess(false);
					result.setMessage("该漫画已完结，请重新选择！");
					return result;
				} else {
					// 获取漫画章节，返回
					result.setSuccess(true);
					result.setMessage("漫画可以续传");
					map.put("manhuaInfo", manhua);
					map.put("book_num", manHuaInfoBLL.findManhuaNextBookNum(manhua));
					result.setData(map);
					return result;
				}
			} else {
				// 返回，您的帐号与该作品记录的作者帐号不一致，无法续传该系列漫画的后续章节，如有疑问请联系编辑~
				result.setSuccess(false);
				result.setMessage("您的帐号与该作品记录的作者帐号不一致，无法续传该系列漫画的后续章节，如有疑问请联系编辑~");
				return result;
			}

		}
	}

	@Override
	public List<WanwdCategory> getManHuaAllType() {
		List<WanwdCategory> list = manHuaCategoryInfoBLL.findAll();
		return list;
	}

	@Override
	public ResultBean<Map<String, String>> uploadSave(UploadFormBean form, UserInfo info, List<String> pagename,
			List<String> pageurl,  Boolean captchaCheck) {
		ResultBean<Map<String, String>> result = new ResultBean<>();
		// 检验数据
		Map<String, String> uploadCheck = uploadCheck(form, info, pagename, pageurl, captchaCheck);
		String areaName = uploadCheck.get("serverName");
		uploadCheck.remove("serverName");
		for(Entry<String, String> entry : uploadCheck.entrySet())   {  
			if (!entry.getValue().equals("正确")) {
				result.setSuccess(false);
				result.setMessage("信息不正确");
				result.setData(uploadCheck);
				return result;
			}
		} 
		saveData(form, info, pagename, pageurl,areaName);
		result.setSuccess(true);
		result.setMessage("上传成功");
		result.setData(null);
		return result;
		// 存储漫画信息

	}

	/**
	 * 漫画上传，检查信息是否正确，是否可以存储
	 * 
	 * @param form
	 * @param info
	 * @param pagename
	 * @param pageurl
	 * @return
	 */
	private Map<String, String> uploadCheck(UploadFormBean form, UserInfo info, List<String> pagename,
			List<String> pageurl,Boolean captchaCheck) {
		Map<String, String> map = new HashMap<>();
		// 检查验证码正确性
		if (!captchaCheck) {
			map.put("captcha", "验证码不正确");
			logger.debug("玩家天地-漫画上传：验证码不正确");
		}else {
			map.put("captcha", "正确");
		}

		// 检查图片正确性
		if (pageurl.size() <= 0) {
			map.put("pages", "不存在图片");
			logger.debug("玩家天地-漫画上传：不存在图片");
		} else {
			map.put("pages", "正确");
			for (int i = 0; i < pageurl.size(); i++) {
				if (pageurl.get(i) == null || pageurl.get(i) == "") {
					map.put("pages", "图片url不能为空");
					logger.debug("玩家天地-漫画上传：图片url不能为空");
				}
			}
		}
		// 检查服区正确性
		logger.debug("玩家天地-漫画上传：检查服区是否正确");
		if (form.getNetType() == null || (form.getNetType() != 1 && form.getNetType() != 2 && form.getNetType() != 3)) {
			map.put("server", "不存在的服务器大区");
			logger.debug("玩家天地-漫画上传：不存在的服务器大区");
		} else {
			ServerBean sb = null;
			try {
				sb = gameAgent.getServers(gameId, form.getNetType());
			} catch (Exception e) {
				logger.error("玩家天地-漫画上传[获取服务器列表异常]", e);
			}
			if (sb == null) {
				map.put("server", "获取服务器列表异常");
			} else {
				ServerMessage[] data = sb.getData();
				boolean flag = false;
				for (int i = 0; i < data.length; i++) {
					if (form.getServerCode() != null && data[i].getCode().equals(form.getServerCode())) {
						flag = true;
						map.put("server", "正确");
						map.put("serverName", data[i].getServerName());
						logger.debug("玩家天地-漫画上传：服区正确");
						break;
					}
				}
				if (!flag) {
					map.put("server", "该大区不存在对应服务器");
					logger.debug("玩家天地-漫画上传：该大区不存在对应服务器");
				}
			}
		}

		// 检查漫画信息正确性
		// 章节名长度和漫画简介长度验证
		if (form.getBookName() == null || form.getBookName().length() > 16) {
			map.put("bookName", "漫画章节长度超出限制");
			logger.debug("玩家天地-漫画上传：漫画章节长度超出限制");
		} else {
			map.put("bookName", "正确");
		}
		if (form.getContext() == null || form.getContext().length() > 200) {
			map.put("context", "漫画简介超过限制，请减少字数");
			logger.debug("玩家天地-漫画上传：漫画简介长度超出限制");
		} else {
			map.put("context", "正确");
		}
		// 章节数是否合法
		if (form.getBookNum() != null && Pattern.compile("[0-9]*").matcher(form.getBookNum()).matches()
				&& Integer.parseInt(form.getBookNum()) > 0) {
			map.put("bookNum", "正确");
			logger.debug("玩家天地-漫画上传：漫画章节数合法");
		} else {
			map.put("bookNum", "漫画章节数不合法");
			logger.debug("玩家天地-漫画上传：漫画章节数不合法");
		}
		// 完结状态是否合法
		if (form.getIsClosed() != null && (form.getIsClosed() == 0 || form.getIsClosed() == 1)) {
			map.put("isClosed", "正确");
			logger.debug("玩家天地-漫画上传：漫画完结状态合法");
		} else {
			map.put("bookNum", "漫画完结状态不合法");
			logger.debug("玩家天地-漫画上传：漫画完结状态不合法");
		}
		// 漫画类型是否存在
		if (form.getCategoryCode() == null) {
			map.put("categoryCode", "漫画类型不存在");
			logger.debug("玩家天地-漫画上传：漫画类型不存在");
		} else {
			List<WanwdCategory> list = manHuaCategoryInfoBLL.findAll();
			boolean flag2 = false;
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getCode().equals(form.getCategoryCode())) {
					flag2 = true;
					break;
				}
			}
			if (!flag2) {
				map.put("categoryCode", "漫画类型不存在");
				logger.debug("玩家天地-漫画上传：漫画类型不存在");
			} else {
				map.put("categoryCode", "正确");
				logger.debug("玩家天地-漫画上传：漫画类型存在");
			}
		}
		if (form.getAuthorName() == null) {
			map.put("authorName", "角色名不能为空");
			logger.debug("玩家天地-漫画上传：角色名不能为空");
		} else if(!Pattern.compile("^[a-zA-Z0-9\u4E00-\u9FA5]+$").matcher(form.getAuthorName()).matches()){
			map.put("authorName", "角色名只能输入汉字英文字母和数字，请重新输入！");
			logger.debug("玩家天地-漫画上传：角色名只能包括汉字英文字母和数字"+form.getAuthorName());
		}else {
			map.put("authorName", "正确");
			logger.debug("玩家天地-漫画上传：角色名正确");
		}
		// 漫画标题
		ResultBean<Map<String, Object>> checkManHuaTitle = checkManHuaTitle(info, form.getTitle());
		if (!checkManHuaTitle.isSuccess()) {
			// 漫画标题不合法
			map.put("title", checkManHuaTitle.getMessage());
			logger.debug("玩家天地-漫画上传：" + checkManHuaTitle.getMessage());
		} else {
			map.put("title", "正确");
			logger.debug("玩家天地-漫画上传：漫画名称正确");
			WanwdManhua manhua = (WanwdManhua) checkManHuaTitle.getData().get("manhuaInfo");
			if (manhua != null) {
				// 漫画为续传时，验证角色名和类型
				if (!manhua.getAuthorName().equals(form.getAuthorName())) {
					map.put("authorName", "续传作品不能更改角色名");
					logger.debug("玩家天地-漫画上传：续传作品不能更改角色名");
				}
				if (!manhua.getCategoryCode().equals(form.getCategoryCode())) {
					map.put("categoryCode", "续传作品不能更改漫画类型");
					logger.debug("玩家天地-漫画上传：续传作品不能更改漫画类型");
				}
			}
		}
		return map;
	}

	/**
	 * 存储上传漫画的数据
	 * 
	 * @param form
	 * @param info
	 * @param pagename
	 * @param pageurl
	 * @param areaName
	 */
	@Transactional
	private void saveData(UploadFormBean form, UserInfo info, List<String> pagename, List<String> pageurl,String areaName) {
		// 存储漫画信息
		Date date = new Date();
		WanwdManhua manhua = new WanwdManhua();
		manhua.setContext(form.getContext());
		manhua.setIsClosed(form.getIsClosed());

		// 判定是更新还是插入
		int manhuaCode;
		WanwdManhua manhuafinded = manHuaInfoBLL.findManhuaByTitle(form.getTitle());
		if (manhuafinded != null && manhuafinded.getAuthorId().equals(info.getUserId())) {
			manhuaCode = manhuafinded.getCode();
			manhua.setCode(manhuaCode);
			manHuaInfoBLL.updateManhua(manhua);
		} else {
			manhua.setAuthorAccount(info.getAccount());
			manhua.setAuthorId(info.getUserId());
			manhua.setAuthorName(form.getAuthorName());
			manhua.setCategoryCode(form.getCategoryCode());
			manhua.setTitle(form.getTitle());
			manhua.setCreateTime(date);
			manhuaCode = manHuaInfoBLL.addManhua(manhua);
		}

		// 存储章节信息
		WanwdManhuaBook book = new WanwdManhuaBook();
		book.setManhuaCode(manhuaCode);
		book.setBookName(MessageFormat.format("第{0}章 ", form.getBookNum()) + form.getBookName());
		book.setBookNum(form.getBookNum());
		book.setCreateTime(date);
		book.setReviewStatus(Constans.REVIEW_ING);
		book.setIsDelete(1);
		book.setNetTypeCode(form.getNetType());
		book.setAreaCode(Integer.parseInt(form.getServerCode()));
		book.setAreaName(areaName);
		Integer insertBook = manHuaInfoBLL.insertBook(book);
		// 存储章节中图片
		WanwdManhuaPage page = new WanwdManhuaPage();
		for (int i = 0; i < pageurl.size(); i++) {
			if (pagename==null||pagename.get(i) == null) {
				page.setPageName("");
			} else {
				page.setPageName(pagename.get(i));
			}
			page.setBookCode(insertBook);
			page.setCreateTime(date);
			page.setPagePictureUrl(pageurl.get(i));
			page.setPagePictureNum(i);
			manHuaInfoBLL.insertPages(page);
		}

	}

	public WDGameAgent getGameAgent() {
		return gameAgent;
	}

	public void setGameAgent(WDGameAgent gameAgent) {
		this.gameAgent = gameAgent;
	}
}
