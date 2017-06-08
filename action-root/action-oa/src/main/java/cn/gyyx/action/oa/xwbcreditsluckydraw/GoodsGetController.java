package cn.gyyx.action.oa.xwbcreditsluckydraw;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibm.icu.text.DateFormat;

import cn.gyyx.action.beans.xwbcreditsluckydraw.Data;
import cn.gyyx.action.beans.xwbcreditsluckydraw.GoodsGetBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.PageBean;
import cn.gyyx.action.bll.xwbcreditsluckydraw.GoodsGetBll;
import cn.gyyx.action.service.agent.CallWebApiAgent;
import cn.gyyx.core.DataFormat;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.core.Text;

@Controller
@RequestMapping("/xwbJiFen")
public class GoodsGetController {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(GoodsGetController.class);
	private GoodsGetBll goodsGetBll = new GoodsGetBll();
	private CallWebApiAgent callWeb = new CallWebApiAgent();

	/**
	 * 查询中奖或积分兑换记录
	 * */
	@RequestMapping("/searchGoodsGet")
	public String searchGoodsGet(Model model, HttpServletRequest request,
			GoodsGetBean goodsGetBean, Integer pageNum) {
		// 初始化页码为1
		if (pageNum == null) {
			pageNum = 1;
		}
		String strUrl = "searchGoodsGet?";
		@SuppressWarnings("unchecked")
		Enumeration<String> paraNames = request.getParameterNames();
		// 判断是否为非条件查询
		if (goodsGetBean == null) {
			goodsGetBean = new GoodsGetBean();
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
			if (!Text.isNullOrEmpty(goodsGetBean.getDomain())) {
				goodsGetBean.setDomain(encodeStr(goodsGetBean.getDomain()));
			}
			if (!Text.isNullOrEmpty(goodsGetBean.getServer())) {
				if (goodsGetBean.getServer().equals("-1")) {
					goodsGetBean.setServer("");
				} else {
					goodsGetBean.setServer(encodeStr(goodsGetBean.getServer()));
				}

			}
			if (!Text.isNullOrEmpty(goodsGetBean.getRoleName())) {
				goodsGetBean.setRoleName(encodeStr(goodsGetBean.getRoleName()));
			}
			strUrl = encodeStr(strUrl);
		}

		int totalRecords = goodsGetBll.getGoodsGetCount(goodsGetBean);
		PageBean page = new PageBean(pageNum, totalRecords);
		List<GoodsGetBean> goodsGetList = goodsGetBll.getGoodsGetByPage(
				goodsGetBean, page);
		model.addAttribute("goodsGetList", goodsGetList);
		model.addAttribute("page", page);
		model.addAttribute("url", strUrl);

		model.addAttribute("account", goodsGetBean.getAccount());
		model.addAttribute("qu", goodsGetBean.getDomain());
		model.addAttribute("server", goodsGetBean.getServer());
		model.addAttribute("roleName", goodsGetBean.getRoleName());
		model.addAttribute("exchangeTime", goodsGetBean.getStrExchangeDate());
		return "xwbcreditsluckydraw/gameduihuan_new";
	}

	/**
	 * 根据区号获取服务器列表
	 * */
	@RequestMapping(value = "/getXWBServer", method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getXWBServer(HttpServletRequest request,
			HttpServletResponse response, Integer domainNum) {
		return DataFormat.objToJson(getServerByDomainNum(domainNum));
		
	}

	private List<String> getServerByDomainNum(Integer domainNum) {
		List<Data> list = callWeb.getAllServerXuanWuBa();
		List<String> serverList = new ArrayList<String>();
		for (Data temp : list) {
			if (temp.getNetTypeCode() == domainNum) {
				serverList.add(temp.getServerName());
			}
		}
		return serverList;
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
