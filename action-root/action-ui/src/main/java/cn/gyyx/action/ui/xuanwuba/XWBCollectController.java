/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：xuanwuba-collect
 * @作者：范永良
 * @联系方式：fanyongliang@gyyx.cn
 * @创建时间： 2015年10月14日
 * @版本号：v1.228
 * @本类主要用途描述：征集 控制器
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.ui.xuanwuba;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.CollectTopShowBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.CommentsBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.ListPageBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.MaterialAuditBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.MaterialInfoBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.PlayerInfoBean;
import cn.gyyx.action.bll.xwbcreditsluckydraw.CommentsBll;
import cn.gyyx.action.bll.xwbcreditsluckydraw.MaterialAuditBll;
import cn.gyyx.action.bll.xwbcreditsluckydraw.MaterialInfoBLL;
import cn.gyyx.action.bll.xwbcreditsluckydraw.PlayerinfoBLL;
import cn.gyyx.action.service.xuanwuba.XWBMissionService;
import cn.gyyx.action.service.xuanwuba.XWBPraiseService;
import cn.gyyx.core.DataFormat;
import cn.gyyx.core.Ip;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.core.auth.SignedUser;
import cn.gyyx.core.auth.UserInfo;

@Controller
@RequestMapping(value = "/xuanwuba")
public class XWBCollectController {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(XWBCollectController.class);
	private XWBMissionService xWBMissionService = new XWBMissionService();
	private XWBPraiseService xWBPraiseService = new XWBPraiseService();
	private MaterialInfoBLL materialInfoBLL = new MaterialInfoBLL();
	private MaterialAuditBll materialAuditBll = new MaterialAuditBll();
	private PlayerinfoBLL playerinfoBLL = new PlayerinfoBLL();
	private CommentsBll commentsBll = new CommentsBll();
	private int actionCode = 288;

	/**
	 * 征集首页
	 * 
	 * @return
	 * 
	 */
	@RequestMapping(value = "/collectIndex")
	public String collectIndex(Model model, HttpServletRequest request) {
		return "xuanwuba/collectIndex";
	}

	/**
	 * 征集首页TOP显示
	 * 
	 * @param materialType
	 * @param topNum
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/collectIndexTopShow", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String collectIndexVideo(String materialType, Integer topNum,
			HttpServletRequest request) {
		logger.info("炫舞吧-舞吧征集XWBCollectController-征集首页TOP显示-参数:materialType{}"
				+ materialType);
		logger.info("炫舞吧-舞吧征集XWBCollectController-征集首页TOP显示-参数:topNum{}"
				+ topNum);
//		if (materialType != null) {
//			try {
//				materialType = new String(materialType.getBytes("iso-8859-1"),
//						"utf-8");
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			}
//		}
		logger.info("炫舞吧-舞吧征集XWBCollectController-征集首页TOP显示-编码后的参数:materialType{}"
				+ materialType);
		// 接收Cookie中的信息
		UserInfo userInfo = null;
		if (request.getCookies() != null) {
			userInfo = SignedUser.getUserInfo(request);
		}
		List<MaterialAuditBean> showList = new ArrayList<MaterialAuditBean>();
		// 获取失败，返回为空
		if (userInfo == null) {
			showList = materialAuditBll
					.getCollectTopShow(new CollectTopShowBean(topNum, "",
							materialType));
			logger.info("炫舞吧-舞吧征集XWBCollectController-征集首页TOP显示-未登录显示列表长度{}"
					+ showList.size());
		} else {
			showList = materialAuditBll
					.getCollectTopShow(new CollectTopShowBean(topNum, userInfo
							.getAccount(), materialType));
			logger.info("炫舞吧-舞吧征集XWBCollectController-征集首页TOP显示-登录后显示列表长度{}"
					+ showList.size());
		}
		for (MaterialAuditBean materialAuditBean : showList) {
			if (materialAuditBean.getContent() != null) {
				materialAuditBean.setContent(replaceImg(materialAuditBean
						.getContent()));
			}
		}
		return DataFormat.objToJson(showList);
	}

	/**
	 * 评论列表
	 * 
	 * @param MaterialInfoBean
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/commentList", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String commonList(Integer materialCode, Integer pageNum,
			Model model, HttpServletRequest request) {
		logger.info("炫舞吧-舞吧征集XWBCollectController-评论列表-参数:materialCode{}"
				+ materialCode);
		logger.info("炫舞吧-舞吧征集XWBCollectController-评论列表-参数:pageNum{}"
				+ pageNum);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<CommentsBean> list = commentsBll.getCommentListByPage(
				materialCode, pageNum);
		for (CommentsBean commentsBean : list) {
			commentsBean.setCommentDateStr(sdf.format(commentsBean
					.getCommentDate()));
			if (!commentsBean.getCommentAccount().equals("匿名玩家")) {
				String str = commentsBean.getCommentAccount();
				if (0 < str.length() && str.length() <= 3) {
					str = String.valueOf(str.charAt(0))
							+ matchStr(str.length() - 1);
				} else if (str.length() == 4) {
					str = String.valueOf(str.charAt(0))
							+ matchStr(str.length() - 2)
							+ String.valueOf(str.charAt(str.length() - 1));
				} else if (str.length() >= 5) {
					str = str.substring(0, 2) + matchStr(str.length() - 4)
							+ str.substring(str.length() - 2, str.length());
				} else {

				}
				commentsBean.setCommentAccount(str);
			}
			commentsBean.setContent(replaceImg(commentsBean.getContent()));
		}
		return DataFormat.objToJson(list);
	}

	/**
	 * 隐藏账号
	 * 
	 * @param num
	 * @return
	 */
	public static String matchStr(int num) {
		String str = "";
		for (int i = 0; i < num; i++) {
			str += "*";
		}
		return str;
	}

	/**
	 * 视频上传页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/videoCollect")
	public String videoCollect(Model model) {
		return "xuanwuba/videocollect";
	}

	/**
	 * 视频列表页
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/videoList")
	public String videoList(Model model, HttpServletRequest request) {
		// 接收Cookie中的信息
		UserInfo userInfo = null;
		if (request.getCookies() != null) {
			userInfo = SignedUser.getUserInfo(request);
		}
		List<MaterialAuditBean> videoList = new ArrayList<MaterialAuditBean>();
		// 获取失败，返回为空
		if (userInfo == null) {
			videoList = materialAuditBll.getVideoListWithName("");
		} else {
			videoList = materialAuditBll.getVideoListWithName(userInfo
					.getAccount());
		}
		for (MaterialAuditBean materialAuditBean : videoList) {
			if (materialAuditBean.getContent() != null) {
				materialAuditBean.setContent(replaceImg(materialAuditBean
						.getContent()));
			}
		}
		model.addAttribute("videoListJSON", DataFormat.objToJson(videoList));
		model.addAttribute("videoList", videoList);
		model.addAttribute(
				"listCount",
				videoList.size() % 12 == 0 ? videoList.size() / 12 : videoList
						.size() / 12 + 1);
		return "xuanwuba/videoList";
	}

	/**
	 * 视频列表分页JSON
	 * 
	 * @param pageIndex
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/videoListPageJSON", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String videoListPageJSON(Integer pageIndex, Model model,
			HttpServletRequest request) {
		// 接收Cookie中的信息
		UserInfo userInfo = null;
		if (request.getCookies() != null) {
			userInfo = SignedUser.getUserInfo(request);
		}
		ListPageBean<MaterialAuditBean> bean = new ListPageBean<MaterialAuditBean>();
		List<MaterialAuditBean> videoList = new ArrayList<MaterialAuditBean>();
		List<MaterialAuditBean> videoListOld = new ArrayList<MaterialAuditBean>();
		int pageCount = 12;
		int start = (pageIndex - 1) * pageCount;
		int end = pageIndex * pageCount - 1;
		// 获取失败，返回为空
		if (userInfo == null) {
			videoListOld = materialAuditBll.getVideoListWithName("");
		} else {
			videoListOld = materialAuditBll.getVideoListWithName(userInfo
					.getAccount());
		}
		if (videoListOld != null && videoListOld.size() > 0) {
			for (int i = 0; i < videoListOld.size(); i++) {
				if (i >= start && i <= end) {
					videoList.add(videoListOld.get(i));
				}
			}
			for (MaterialAuditBean materialAuditBean : videoList) {
				if (materialAuditBean.getContent() != null) {
					materialAuditBean.setContent(replaceImg(materialAuditBean
							.getContent()));
				}
			}
			bean.setTotalCount(videoListOld.size());
			bean.setPageArray(pageInfo(
					videoListOld.size() % 12 == 0 ? videoListOld.size() / 12
							: videoListOld.size() / 12 + 1, pageIndex));
		} else {
			videoList = null;
			bean.setTotalCount(0);
			bean.setPageArray(pageInfo(1, pageIndex));
		}
		bean.setList(videoList);
		bean.setCurrentPage(pageIndex);
		return DataFormat.objToJson(bean);
	}

	/**
	 * 视频列表JSON
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/videoListJSON", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String videoListJSON(Model model, HttpServletRequest request) {
		// 接收Cookie中的信息
		UserInfo userInfo = null;
		if (request.getCookies() != null) {
			userInfo = SignedUser.getUserInfo(request);
		}
		List<MaterialAuditBean> videoList = new ArrayList<MaterialAuditBean>();
		// 获取失败，返回为空
		if (userInfo == null) {
			videoList = materialAuditBll.getVideoListWithName("");
		} else {
			videoList = materialAuditBll.getVideoListWithName(userInfo
					.getAccount());
		}
		for (MaterialAuditBean materialAuditBean : videoList) {
			if (materialAuditBean.getContent() != null) {
				materialAuditBean.setContent(replaceImg(materialAuditBean
						.getContent()));
			}
		}
		return DataFormat.objToJson(videoList);
	}

	/**
	 * 视频终极页
	 * 
	 * @param materialCode
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/video")
	public String video(Integer materialCode, Model model) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		MaterialInfoBean video = materialInfoBLL
				.getMaterialInfoByCode(materialCode);
		video.setUploadTimeStr(sdf.format(video.getUploadTime()));

		if (!video.getAccount().equals("匿名")) {
			String str = video.getAccount();
			if (0 < str.length() && str.length() <= 3) {
				str = String.valueOf(str.charAt(0))
						+ matchStr(str.length() - 1);
			} else if (str.length() == 4) {
				str = String.valueOf(str.charAt(0))
						+ matchStr(str.length() - 2)
						+ String.valueOf(str.charAt(str.length() - 1));
			} else if (str.length() >= 5) {
				str = str.substring(0, 2) + matchStr(str.length() - 4)
						+ str.substring(str.length() - 2, str.length());
			} else {

			}
			video.setAccount(str);
		}
		model.addAttribute("video", video);
		return "xuanwuba/video";
	}

	/**
	 * 服装上传页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/photoCollect")
	public String photoCollect(Model model) {
		return "xuanwuba/photocollect";
	}

	/**
	 * 服装列表页
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/photoList")
	public String photoList(Model model, HttpServletRequest request) {
		// 接收Cookie中的信息
		UserInfo userInfo = null;
		if (request.getCookies() != null) {
			userInfo = SignedUser.getUserInfo(request);
		}
		List<MaterialAuditBean> photoList = new ArrayList<MaterialAuditBean>();
		// 获取失败，返回为空
		if (userInfo == null) {
			photoList = materialAuditBll.getPhotoListWithName("");
		} else {
			photoList = materialAuditBll.getPhotoListWithName(userInfo
					.getAccount());
		}
		for (MaterialAuditBean materialAuditBean : photoList) {
			if (materialAuditBean.getContent() != null) {
				materialAuditBean.setContent(replaceImg(materialAuditBean
						.getContent()));
			}
		}
		model.addAttribute("photoListJSON", DataFormat.objToJson(photoList));
		model.addAttribute("photoList", photoList);
		model.addAttribute(
				"listCount",
				photoList.size() % 15 == 0 ? photoList.size() / 15 : photoList
						.size() / 15 + 1);
		return "xuanwuba/photoList";
	}

	/**
	 * 服装列表分页JSON
	 * 
	 * @param pageIndex
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/photoListPageJSON", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String photoListPageJSON(Integer pageIndex, Model model,
			HttpServletRequest request) {
		// 接收Cookie中的信息
		UserInfo userInfo = null;
		if (request.getCookies() != null) {
			userInfo = SignedUser.getUserInfo(request);
		}
		ListPageBean<MaterialAuditBean> bean = new ListPageBean<MaterialAuditBean>();
		List<MaterialAuditBean> photoList = new ArrayList<MaterialAuditBean>();
		List<MaterialAuditBean> photoListOld = new ArrayList<MaterialAuditBean>();
		int pageCount = 15;
		int start = (pageIndex - 1) * pageCount;
		int end = pageIndex * pageCount - 1;
		// 获取失败，返回为空
		if (userInfo == null) {
			photoListOld = materialAuditBll.getPhotoListWithName("");
		} else {
			photoListOld = materialAuditBll.getPhotoListWithName(userInfo
					.getAccount());
		}
		if (photoListOld != null && photoListOld.size() > 0) {
			for (int i = 0; i < photoListOld.size(); i++) {
				if (i >= start && i <= end) {
					photoList.add(photoListOld.get(i));
				}
			}
			for (MaterialAuditBean materialAuditBean : photoList) {
				if (materialAuditBean.getContent() != null) {
					materialAuditBean.setContent(replaceImg(materialAuditBean
							.getContent()));
				}
			}
			bean.setTotalCount(photoListOld.size());
			bean.setPageArray(pageInfo(
					photoListOld.size() % 15 == 0 ? photoListOld.size() / 15
							: photoListOld.size() / 15 + 1, pageIndex));
		} else {
			photoList = null;
			bean.setTotalCount(0);
			bean.setPageArray(pageInfo(1, pageIndex));
		}
		bean.setList(photoList);
		bean.setCurrentPage(pageIndex);
		return DataFormat.objToJson(bean);
	}

	/**
	 * 服装列表JSON
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/photoListJSON", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String photoListJSON(Model model, HttpServletRequest request) {
		// 接收Cookie中的信息
		UserInfo userInfo = null;
		if (request.getCookies() != null) {
			userInfo = SignedUser.getUserInfo(request);
		}
		List<MaterialAuditBean> photoList = new ArrayList<MaterialAuditBean>();
		// 获取失败，返回为空
		if (userInfo == null) {
			photoList = materialAuditBll.getPhotoListWithName("");
		} else {
			photoList = materialAuditBll.getPhotoListWithName(userInfo
					.getAccount());
		}
		for (MaterialAuditBean materialAuditBean : photoList) {
			if (materialAuditBean.getContent() != null) {
				materialAuditBean.setContent(replaceImg(materialAuditBean
						.getContent()));
			}
		}
		return DataFormat.objToJson(photoList);
	}

	/**
	 * 服装终极页
	 * 
	 * @param materialCode
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/photo")
	public String photo(Integer materialCode, Model model) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		MaterialInfoBean photo = materialInfoBLL
				.getMaterialInfoByCode(materialCode);
		photo.setUploadTimeStr(sdf.format(photo.getUploadTime()));
		if (!photo.getAccount().equals("匿名")) {
			String str = photo.getAccount();
			if (0 < str.length() && str.length() <= 3) {
				str = String.valueOf(str.charAt(0))
						+ matchStr(str.length() - 1);
			} else if (str.length() == 4) {
				str = String.valueOf(str.charAt(0))
						+ matchStr(str.length() - 2)
						+ String.valueOf(str.charAt(str.length() - 1));
			} else if (str.length() >= 5) {
				str = str.substring(0, 2) + matchStr(str.length() - 4)
						+ str.substring(str.length() - 2, str.length());
			} else {

			}
			photo.setAccount(str);
		}
		model.addAttribute("photo", photo);
		return "xuanwuba/photo";
	}

	/**
	 * 建议上传页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/suggestCollect")
	public String suggestCollect(Model model) {
		return "xuanwuba/suggestcollect";
	}

	/**
	 * 建议列表页
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/suggestList")
	public String suggestList(Model model, HttpServletRequest request) {
		// 接收Cookie中的信息
		UserInfo userInfo = null;
		if (request.getCookies() != null) {
			userInfo = SignedUser.getUserInfo(request);
		}
		List<MaterialAuditBean> suggestList = new ArrayList<MaterialAuditBean>();
		// 获取失败，返回为空
		if (userInfo == null) {
			suggestList = materialAuditBll.getSuggestListWithName("");
		} else {
			suggestList = materialAuditBll.getSuggestListWithName(userInfo
					.getAccount());
		}
		for (MaterialAuditBean materialAuditBean : suggestList) {
			if (materialAuditBean.getContent() != null) {
				materialAuditBean.setContent(replaceImg(materialAuditBean
						.getContent()));
			}
		}
		for (MaterialAuditBean materialAuditBean : suggestList) {
			if (materialAuditBean.getMaterialType().equals("建议")
					&& materialAuditBean.getMaterialProfile().length() > 15) {
				materialAuditBean.setMaterialProfile(materialAuditBean
						.getMaterialProfile().substring(0, 15) + ". . .");
			}
		}
		model.addAttribute("suggestListJSON", DataFormat.objToJson(suggestList));
		model.addAttribute("suggestList", suggestList);
		model.addAttribute("listCount",
				suggestList.size() % 9 == 0 ? suggestList.size() / 9
						: suggestList.size() / 9 + 1);
		return "xuanwuba/suggestList";
	}

	/**
	 * 建议列表分页JSON
	 * 
	 * @param pageIndex
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/suggestListPageJSON", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String suggestListPageJSON(Integer pageIndex, Model model,
			HttpServletRequest request) {
		// 接收Cookie中的信息
		UserInfo userInfo = null;
		if (request.getCookies() != null) {
			userInfo = SignedUser.getUserInfo(request);
		}
		ListPageBean<MaterialAuditBean> bean = new ListPageBean<MaterialAuditBean>();
		List<MaterialAuditBean> suggestList = new ArrayList<MaterialAuditBean>();
		List<MaterialAuditBean> suggestListOld = new ArrayList<MaterialAuditBean>();
		int pageCount = 9;
		int start = (pageIndex - 1) * pageCount;
		int end = pageIndex * pageCount - 1;
		// 获取失败，返回为空
		if (userInfo == null) {
			suggestListOld = materialAuditBll.getSuggestListWithName("");
		} else {
			suggestListOld = materialAuditBll.getSuggestListWithName(userInfo
					.getAccount());
		}
		if (suggestListOld != null && suggestListOld.size() > 0) {
			for (int i = 0; i < suggestListOld.size(); i++) {
				if (i >= start && i <= end) {
					suggestList.add(suggestListOld.get(i));
				}
			}
			for (MaterialAuditBean materialAuditBean : suggestList) {
				if (materialAuditBean.getContent() != null) {
					materialAuditBean.setContent(replaceImg(materialAuditBean
							.getContent()));
				}
			}
			bean.setTotalCount(suggestListOld.size());
			bean.setPageArray(pageInfo(
					suggestListOld.size() % 9 == 0 ? suggestListOld.size() / 9
							: suggestListOld.size() / 9 + 1, pageIndex));
		} else {
			suggestList = null;
			bean.setTotalCount(0);
			bean.setPageArray(pageInfo(1, pageIndex));
		}
		bean.setList(suggestList);
		bean.setCurrentPage(pageIndex);
		return DataFormat.objToJson(bean);
	}

	/**
	 * 建议列表JSON
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/suggestListJSON", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String suggestListJSON(Model model, HttpServletRequest request) {
		// 接收Cookie中的信息
		UserInfo userInfo = null;
		if (request.getCookies() != null) {
			userInfo = SignedUser.getUserInfo(request);
		}
		List<MaterialAuditBean> suggestList = new ArrayList<MaterialAuditBean>();
		// 获取失败，返回为空
		if (userInfo == null) {
			suggestList = materialAuditBll.getSuggestListWithName("");
		} else {
			suggestList = materialAuditBll.getSuggestListWithName(userInfo
					.getAccount());
		}
		for (MaterialAuditBean materialAuditBean : suggestList) {
			if (materialAuditBean.getContent() != null) {
				materialAuditBean.setContent(replaceImg(materialAuditBean
						.getContent()));
			}
		}
		for (MaterialAuditBean materialAuditBean : suggestList) {
			if (materialAuditBean.getMaterialType().equals("建议")
					&& materialAuditBean.getMaterialProfile().length() > 15) {
				materialAuditBean.setMaterialProfile(materialAuditBean
						.getMaterialProfile().substring(0, 15) + ". . .");
			}
		}
		return DataFormat.objToJson(suggestList);
	}

	/**
	 * 建议终极页
	 * 
	 * @param materialCode
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/suggest")
	public String suggest(Integer materialCode, Model model) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		MaterialInfoBean suggest = materialInfoBLL
				.getMaterialInfoByCode(materialCode);
		suggest.setUploadTimeStr(sdf.format(suggest.getUploadTime()));
		if (!suggest.getAccount().equals("匿名")) {
			String str = suggest.getAccount();
			if (0 < str.length() && str.length() <= 3) {
				str = String.valueOf(str.charAt(0))
						+ matchStr(str.length() - 1);
			} else if (str.length() == 4) {
				str = String.valueOf(str.charAt(0))
						+ matchStr(str.length() - 2)
						+ String.valueOf(str.charAt(str.length() - 1));
			} else if (str.length() >= 5) {
				str = str.substring(0, 2) + matchStr(str.length() - 4)
						+ str.substring(str.length() - 2, str.length());
			} else {

			}
			suggest.setAccount(str);
		}
		model.addAttribute("suggest", suggest);
		return "xuanwuba/suggest";
	}

	/**
	 * 征集上传
	 * 
	 * @param MaterialInfoBean
	 * @param model
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/collectUpload", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String collectUpload(MaterialInfoBean materialInfoBean, Model model,
			HttpServletRequest request) throws UnsupportedEncodingException {
		logger.info("XWBCollectController-collectUpload-参数:"
				+ materialInfoBean.toString());
		ResultBean<String> result = new ResultBean<String>(false, "征集上传失败", "");
		// 接收Cookie中的信息
		UserInfo userInfo = null;
		if (request.getCookies() != null) {
			userInfo = SignedUser.getUserInfo(request);
		}
		// 获取失败，返回为空
		if (userInfo == null) {
			result.setProperties(false, "很抱歉，您的登陆超时", null);
			logger.info("message" + result.getMessage());
			return DataFormat.objToJson(result);
		}
		// 查询账号信息
		PlayerInfoBean playerInfoBean = playerinfoBLL.getInfoByAccount(userInfo
				.getAccount());
		if (playerInfoBean == null) {
			result.setProperties(false, "请先到个人中心完善个人信息！", null);
			logger.info("message" + result.getMessage());
			return DataFormat.objToJson(result);
		}
		if (materialInfoBean.getMaterialName().getBytes("gbk").length > 20) {
			result.setProperties(false,
					"素材名称长度为10位以内的中文字符或长度为20位以内的字母，数字或半角字符！", null);
			logger.info("message" + result.getMessage());
			return DataFormat.objToJson(result);
		}
		// 记录素材详细
		Integer infoNum = materialInfoBLL.addMaterialInfo(materialInfoBean);
		if (infoNum == 0) {
			result.setProperties(false, "征集信息上传失败，请稍后重试！", null);
			logger.info("message" + result.getMessage());
			return DataFormat.objToJson(result);
		}
		logger.info("XWBCollectController-collectUpload-记录主键{}:"
				+ materialInfoBean.getCode());
		// 插入审核信息
		MaterialAuditBean materialAuditBean = new MaterialAuditBean(
				userInfo.getAccount(), playerInfoBean.getNetType(),
				playerInfoBean.getServerName(),
				materialInfoBean.getMaterialName(),
				materialInfoBean.getMaterialType(), materialInfoBean.getCode(),
				"未审核", 0, 0, 0, 0, new Date());
		Integer auditNum = materialAuditBll.addMaterialAudit(materialAuditBean);
		if (auditNum == 0) {
			result.setProperties(false, "审核信息提交失败，请稍后重试！", null);
			logger.info("message" + result.getMessage());
			return DataFormat.objToJson(result);
		}
		// 任务
		ResultBean<String> missionResult = xWBMissionService
				.checkMissionReceiveInfoAndUpdateMissionStatus(
						userInfo.getAccount(),
						materialInfoBean.getMaterialType());
		if (missionResult.getIsSuccess()) {
			result.setProperties(true, "提交成功,请等待审核！", "存在任务，已经修改任务状态为审核中！");
			logger.info("炫舞吧XWBCollectController-上传-message" + result.getMessage());
			return DataFormat.objToJson(result);
		}
		result.setProperties(true, "提交成功,请等待审核！", null);
		logger.info("炫舞吧XWBCollectController-上传-message" + result.getMessage());
		return DataFormat.objToJson(result);
	}

	/**
	 * 点赞
	 * 
	 * @param MaterialInfoBean
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/praise", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String praise(Integer materialCode, Model model,
			HttpServletRequest request) {
		logger.info("XWBCollectController-praise-参数:" + materialCode);
		ResultBean<String> result = new ResultBean<String>(false, "点赞失败", "");
		// 接收Cookie中的信息
		UserInfo userInfo = null;
		if (request.getCookies() != null) {
			userInfo = SignedUser.getUserInfo(request);
		}
		// 获取失败，返回为空
		if (userInfo == null) {
			result.setProperties(false, "很抱歉，您的登陆超时", null);
			logger.info("message" + result.getMessage());
			return DataFormat.objToJson(result);
		}
		result = xWBPraiseService.givePraise(userInfo.getAccount(),
				materialCode);
		logger.info("炫舞吧XWBCollectController-点赞-message" + result.getMessage());
		return DataFormat.objToJson(result);
	}

	/**
	 * 评论
	 * 
	 * @param materialCode
	 * @param content
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/comment", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String comment(Integer materialCode, String content, Model model,
			HttpServletRequest request) throws UnsupportedEncodingException {
		//content = new String(content.getBytes("iso-8859-1"), "utf-8");
		logger.info("XWBCollectController-comment-参数{}:" + materialCode + ","
				+ content);
		ResultBean<String> result = new ResultBean<String>(false, "评论失败", "");
		// 接收Cookie中的信息
		UserInfo userInfo = null;
		if (request.getCookies() != null) {
			userInfo = SignedUser.getUserInfo(request);
		}
		String noLoginAddr = Ip.getCurrent(request).asString();
		// 获取失败，返回为空
		if (userInfo == null) {
			if (content.trim().length() < 15) {
				result.setProperties(false, "请输入15~200字的评论内容！", null);
				logger.info("炫舞吧XWBCollectController-评论-message" + result.getMessage());
				return DataFormat.objToJson(result);
			}
			if (content.trim().length() > 200) {
				result.setProperties(false, "请输入15~200字的评论内容！", null);
				logger.info("炫舞吧XWBCollectController-评论-message" + result.getMessage());
				return DataFormat.objToJson(result);
			}
			CommentsBean commBean = commentsBll.getNoNameCommentWithAddr(
					materialCode, noLoginAddr);
			SimpleDateFormat commFormat = new SimpleDateFormat("yyyy-MM-dd");
			if (commBean != null) {
				int noFlag = Integer.parseInt(commFormat.format(new Date())
						.replaceAll("-", ""))
						- Integer.parseInt(commFormat.format(
								commBean.getCommentDate()).replaceAll("-", ""));
				if (noFlag == 0) {
					result.setProperties(false, "亲，您今天的匿名评论次数已达上限，请登陆后评论！",
							null);
					logger.info("炫舞吧XWBCollectController-评论-message" + result.getMessage());
					return DataFormat.objToJson(result);
				}
			}
			CommentsBean commentsBean = new CommentsBean("匿名玩家", materialCode,
					content, new Date(), 0, noLoginAddr, 0);
			commentsBll.addComment(commentsBean);
			materialAuditBll
					.updateCommentNum(1, commentsBean.getMaterialCode());
			result.setProperties(true, "评论成功", content);
			logger.info("炫舞吧XWBCollectController-评论-message" + result.getMessage());
			return DataFormat.objToJson(result);

		}
		if (content.trim().length() < 15) {
			result.setProperties(false, "请输入15~200字的评论内容！", null);
			logger.info("炫舞吧XWBCollectController-评论-message" + result.getMessage());
			return DataFormat.objToJson(result);
		}
		if (content.trim().length() > 200) {
			result.setProperties(false, "请输入15~200字的评论内容！", null);
			logger.info("炫舞吧XWBCollectController-评论-message" + result.getMessage());
			return DataFormat.objToJson(result);
		}
		CommentsBean commentsBean = commentsBll.getComment(
				userInfo.getAccount(), materialCode, noLoginAddr);
		int betTime = 0;
		if (commentsBean != null) {
			betTime = (int) (new Date().getTime() - commentsBean
					.getCommentDate().getTime());
			if (betTime / 1000 < 30) {
				result.setProperties(false, "您评论的太快了,请30s后重试", null);
				return DataFormat.objToJson(result);
			} else {
				CommentsBean bean = new CommentsBean(userInfo.getAccount(),
						materialCode, content, new Date(), 0, noLoginAddr, 0);
				commentsBll.addComment(bean);
				materialAuditBll.updateCommentNum(1, bean.getMaterialCode());
				result.setProperties(true, "评论成功", content);
				logger.info("炫舞吧XWBCollectController-评论成功内容" + content);
				return DataFormat.objToJson(result);
			}
		}
		CommentsBean bean = new CommentsBean(userInfo.getAccount(),
				materialCode, content, new Date(), 0, noLoginAddr, 0);
		commentsBll.addComment(bean);
		materialAuditBll.updateCommentNum(1, bean.getMaterialCode());
		result.setProperties(true, "评论成功", content);
		logger.info("炫舞吧XWBCollectController-新的第一次评论成功内容" + content);
		return DataFormat.objToJson(result);
	}

	/**
	 * 表情素材接口
	 * 
	 * @return
	 */
	@RequestMapping(value = "/face")
	@ResponseBody
	public String face() {
		String opt = "";
		for (int i = 1; i <= 98; i++) {
			opt += "<li>"
					+ "<a>"
					+ "<img eid=\""
					+ i
					+ "\" src=\"http://res.gyyx.cn/mgp2res/images/faces/"
					+ i
					+ ".gif\" />"
					+ "</a>"
					+ "<div id=\"leftface\" class=\"on\" style=\"display:none;\">"
					+ "<p><a><img src=\"\"/></a></p>"
					+ "<p class=\"a_bg\"></p>" + "</div>" + "</li>";
		}

		String htmlOpt = "<div class=\"a_expup\"></div>"
				+ "<div class=\"a_expinon\">" + "<ul class=\"a_axplist\">"
				+ opt + "</ul>" + "</div>";
		return htmlOpt;
	}

	/**
	 * 替换图片链接
	 * 
	 * @param str
	 * @return
	 */
	private String replaceImg(String str) {
		String[] s = new String[98];
		for (int i = 0; i < s.length; i++) {
			s[i] = "[em=" + (i + 1) + "]";
		}
		for (int i = 0; i < s.length; i++) {
			str = str.replace(s[i],
					"<img src='http://res.gyyx.cn/mgp2res/images/faces/"
							+ (i + 1) + ".gif'/>");
		}
		return str;
	}

	/**
	 * 分页信息
	 * 
	 * @param total
	 * @param index
	 * @return
	 */
	private int[] pageInfo(Integer total, Integer index) {
		int[] resultArray;
		int k = 0;
		if (total <= 7) {
			resultArray = new int[total];
			for (int i = 1; i <= total; i++) {
				resultArray[k] = i;
				k++;
			}
		} else {
			resultArray = new int[7];
			if (index - 3 <= 1) {
				for (int i = 1; i <= 7; i++) {
					resultArray[k] = i;
					k++;
				}
			} else if (index - 3 > 1) {
				if (index + 3 < total) {
					for (int i = index - 3; i <= index + 3; i++) {
						resultArray[k] = i;
						k++;
					}
				} else {
					for (int i = total - 6; i <= total; i++) {
						resultArray[k] = i;
						k++;
					}
				}
			}
		}
		return resultArray;
	}

}
