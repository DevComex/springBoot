package cn.gyyx.action.oa.xwbcreditsluckydraw;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.xwbcreditsluckydraw.CommentsBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.PageBean;
import cn.gyyx.action.bll.xwbcreditsluckydraw.CommentsBll;
import cn.gyyx.action.bll.xwbcreditsluckydraw.MaterialAuditBll;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.core.Text;

@Controller
@RequestMapping("/xwbJiFen")
public class CommentsController {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(PlayerinfoController.class);
	private CommentsBll commentsBll = new CommentsBll();
	private MaterialAuditBll materialAuditBll = new MaterialAuditBll();

	/**
	 * 查询评论
	 * @throws UnsupportedEncodingException 
	 * */
	@RequestMapping("/searchComments")
	public String searchComments(Model model, HttpServletRequest request,
			CommentsBean commentsBean, Integer pageNum) throws UnsupportedEncodingException {
		// 初始化页码为1
		if (pageNum == null) {
			pageNum = 1;
		}
		String strUrl = "searchComments?";
		@SuppressWarnings("unchecked")
		Enumeration<String> paraNames = request.getParameterNames();
		// 判断是否为非条件查询
		if (commentsBean == null) {
			commentsBean = new CommentsBean();
			logger.info("------------------",commentsBean.toString()+"---------------------"+pageNum);
		} else {
			logger.info("------------------",commentsBean.toString()+"---------------------"+pageNum);
			String type = commentsBean.getMaterialType();
			String name = commentsBean.getMaterialName();
			if(type!=null){
				type = new String(type.getBytes("iso-8859-1"),"utf-8");
				commentsBean.setMaterialType(type);
			}
			if(name!=null){
				name = new String(name.getBytes("iso-8859-1"),"utf-8");
				commentsBean.setMaterialName(name);
			}
			
			
			for (Enumeration<String> e = paraNames; e.hasMoreElements();) {
				String thisName = e.nextElement().toString();
				String thisValue = request.getParameter(thisName);
				if (!thisName.equals("pageNum")) {
					strUrl = strUrl + thisName + "=" + thisValue + "&";
				} else {
					strUrl = strUrl.substring(0, strUrl.length());
				}
			}
			if (!Text.isNullOrEmpty(commentsBean.getCommentAccount())) {
				commentsBean.setCommentAccount(encodeStr(commentsBean
						.getCommentAccount()));
			}
			strUrl = strUrl.substring(0, strUrl.length());
			strUrl = encodeStr(strUrl);
		}
		int totalRecords = commentsBll.getCommentsCount(commentsBean);
		PageBean page = new PageBean(pageNum, totalRecords);
		List<CommentsBean> signInList = commentsBll.getCommentsByPage(
				commentsBean, page);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (CommentsBean commentsBean2 : signInList) {
			commentsBean2.setCommentDateStr(sdf.format(commentsBean2.getCommentDate()));
			if(commentsBean2.getContent().length()>30){
				commentsBean2.setContentSub(commentsBean2.getContent().substring(0, 30)+". . .");
			}
			commentsBean2.setContent(replaceImg(commentsBean2.getContent()));
			if(commentsBean2.getContentSub()!=null){
				commentsBean2.setContentSub(replaceImg(commentsBean2.getContentSub()));
			}
		}
		model.addAttribute("commentDateStr", commentsBean.getCommentDateStr());
		model.addAttribute("commentsList", signInList);
		model.addAttribute("page", page);
		model.addAttribute("acc", commentsBean.getCommentAccount());
		model.addAttribute("materialCode", commentsBean.getMaterialCode());
		model.addAttribute("materialName", commentsBean.getMaterialName());
		model.addAttribute("materialType", commentsBean.getMaterialType());
		model.addAttribute("isBest", commentsBean.getIsBest());
		model.addAttribute("url", strUrl);
		return "xwbcreditsluckydraw/pinglunnews";
	}

	@RequestMapping("/deleteComments")
	@ResponseBody
	public String deleteComments(Integer code) {		
		CommentsBean comm = commentsBll.getCommonByCode(code);
		commentsBll.deleteByCode(code);
		materialAuditBll.updateCommentNum(-1,comm.getMaterialCode());
		materialAuditBll.resetBestFlag(comm.getMaterialCode());
		commentsBll.resetBestCommon(code);
		return "1";
	}
	
	@RequestMapping("/setBestCommon")
	@ResponseBody
	public String setBestCommon(Integer materialCode,Integer code) {
		commentsBll.setBestCommon(materialCode,code);
		materialAuditBll.setBestFlag(materialCode);
		return "1";
	}

	private String encodeStr(String str) {
		String ecodestr = null;
		try {
			if (str != null) {
				ecodestr = new String(str.getBytes("ISO-8859-1"), "UTF-8");
			}
			return ecodestr;
		} catch (UnsupportedEncodingException e) {
			logger.debug(e.toString());
			return null;
		}
	}
	
	private String replaceImg(String str) {
		String[] s = new String[98];
		for(int i = 0 ;i<s.length;i++){
			s[i] = "[em="+(i+1)+"]";
		}
		for(int i = 0 ;i<s.length;i++){
				str = str.replace(s[i],  "<img src='http://res.gyyx.cn/mgp2res/images/faces/"+(i+1)+".gif'/>");
		}
		return str;
	}
}
