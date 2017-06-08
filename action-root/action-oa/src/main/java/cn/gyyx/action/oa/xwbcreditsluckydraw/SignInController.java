package cn.gyyx.action.oa.xwbcreditsluckydraw;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.CalendarInfoBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.PageBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.ShowTopBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.SignInBean;
import cn.gyyx.action.bll.xwbcreditsluckydraw.SignInBll;
import cn.gyyx.action.service.xuanwuba.XWBService;
import cn.gyyx.core.DataFormat;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

@Controller
@RequestMapping("/xwbJiFen")
public class SignInController {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(GoodsGetController.class);
	private SignInBll signInBll = new SignInBll();
	private XWBService xWBService = new XWBService();
	/**
	 * 查询签到记录
	 * */
	@RequestMapping("/searchSignIn")
	public String searchSignIn(Model model, HttpServletRequest request,
			SignInBean signInBean, Integer pageNum) {
		// 初始化页码为1
		if (pageNum == null) {
			pageNum = 1;
		}
		String strUrl = "searchSignIn?";
		@SuppressWarnings("unchecked")
		Enumeration<String> paraNames = request.getParameterNames();
		// 判断是否为非条件查询
		if (signInBean == null) {
			signInBean = new SignInBean();
		} else {
			for (Enumeration<String> e = paraNames; e.hasMoreElements();) {
				String thisName = e.nextElement().toString();
				String thisValue = request.getParameter(thisName);
				if (!thisName.equals("pageNum")) {
					strUrl = strUrl + thisName + "=" + thisValue + "&";
				} else {
					strUrl = strUrl.substring(0, strUrl.length());
				}
			}
			strUrl = strUrl.substring(0, strUrl.length());
			strUrl = encodeStr(strUrl);
		}
		int totalRecords = signInBll.getSignInCount(signInBean);
		PageBean page = new PageBean(pageNum, totalRecords);
		List<SignInBean> signInList = signInBll.getSignInByPage(signInBean,
				page);
		model.addAttribute("signInList", signInList);
		model.addAttribute("page", page);
		model.addAttribute("url", strUrl);
		model.addAttribute("acc", signInBean.getAccount());
		model.addAttribute("time", signInBean.getStrSignInDate());
		return "xwbcreditsluckydraw/gameqiandao_new";
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
}
