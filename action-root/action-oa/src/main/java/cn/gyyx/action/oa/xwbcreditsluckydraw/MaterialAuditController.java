package cn.gyyx.action.oa.xwbcreditsluckydraw;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.common.ActionCommonImageBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.CreditsBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.MaterialAuditBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.MaterialInfoBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.OprateRecordsBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.PageBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.ShowTopBean;
import cn.gyyx.action.bll.xwbcreditsluckydraw.CreditBLL;
import cn.gyyx.action.bll.xwbcreditsluckydraw.MaterialAuditBll;
import cn.gyyx.action.bll.xwbcreditsluckydraw.MaterialInfoBLL;
import cn.gyyx.action.bll.xwbcreditsluckydraw.OprateRecordsBLL;
import cn.gyyx.action.bll.xwbcreditsluckydraw.ShowTopBll;
import cn.gyyx.action.service.common.OaCommonService;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.core.Text;
import cn.gyyx.oa.stage.httpRequest.OAUserRequest;
import cn.gyyx.oa.stage.model.OAUserInfoModel;

@Controller
@RequestMapping("/xwbJiFen")
public class MaterialAuditController {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(GoodsGetController.class);
	private MaterialAuditBll materialAuditBll = new MaterialAuditBll();
	private MaterialInfoBLL materialInfoBLL = new MaterialInfoBLL();
	private CreditBLL creditBll = new CreditBLL();
	private ShowTopBll showTopBLL = new ShowTopBll();
	private OprateRecordsBLL oprateRecordsBll = new OprateRecordsBLL();
	private OaCommonService oaCommonService = new OaCommonService();
	
	@RequestMapping("/")
	public String index(Model model) {
		return "redirect:searchMaterial";
	}

	/**
	 * 分页查询素材
	 * */
	@RequestMapping("/searchMaterial")
	public String searchMaterialAudit(Model model, HttpServletRequest request,
			MaterialAuditBean materialAuditBean, Integer pageNum) {
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 初始化页码为1
		if (pageNum == null) {
			pageNum = 1;
		}
		String strUrl = "searchMaterial?";
		@SuppressWarnings("unchecked")
		Enumeration<String> paraNames = request.getParameterNames();
		// 判断是否为非条件查询
		if (materialAuditBean == null) {
			materialAuditBean = new MaterialAuditBean();
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
			if (!Text.isNullOrEmpty(materialAuditBean.getMaterialType())) {
				materialAuditBean.setMaterialType(encodeStr(materialAuditBean
						.getMaterialType()));
			}
			if (!Text.isNullOrEmpty(materialAuditBean.getIsChecked())) {
				materialAuditBean.setIsChecked(encodeStr(materialAuditBean
						.getIsChecked()));
			}
			strUrl = strUrl.substring(0, strUrl.length());
			strUrl = encodeStr(strUrl);
		}
		if(materialAuditBean.getMaterialName()!=null){
			materialAuditBean.setMaterialName(encodeStr(materialAuditBean.getMaterialName()));
		}
		int totalRecords = materialAuditBll
				.getMaterialAuditCount(materialAuditBean);
		PageBean page = new PageBean(pageNum, totalRecords);
		List<MaterialAuditBean> signInList = materialAuditBll
				.getMaterialAuditByPage(materialAuditBean, page);
		
		for (MaterialAuditBean materialAuditBean2 : signInList) {
			materialAuditBean2.setShowStatus(showTopBLL.getCountByAuditCode(materialAuditBean2.getCode()));
			materialAuditBean2.setUploadTimeStr(format.format(materialAuditBean2.getUploadTime()));
		}
		
		model.addAttribute("materialList", signInList);
		model.addAttribute("page", page);
		model.addAttribute("url", strUrl);
		model.addAttribute("mType", materialAuditBean.getMaterialType());
		model.addAttribute("isChecked", materialAuditBean.getIsChecked());
		model.addAttribute("isShow", materialAuditBean.getIsShow());
		model.addAttribute("timeStr", materialAuditBean.getUploadTimeStr());
		model.addAttribute("account", materialAuditBean.getAccount());
		model.addAttribute("materialName", materialAuditBean.getMaterialName());
		// 已选择服装个数
		model.addAttribute("clothNum", showTopBLL.getCountByMaterialType("服装"));
		// 已选择视频个数
		model.addAttribute("mediaNum", showTopBLL.getCountByMaterialType("视频"));
		// 修改提示
		//重做：对比两边显示素材差异  tipFlag = 0 无差异  1 有差异
		int tipFlag = 0;
		List<ShowTopBean> showTopList = showTopBLL.getAllInfo();
		for (ShowTopBean showTopBean : showTopList) {
			if(materialAuditBll.getShowStatusByCode(showTopBean.getAuditCode()) == 0){
				tipFlag = 1;
			}
		}
		if(materialAuditBll.getShowMaterialInfo().size() != showTopList.size()){
			tipFlag = 1;
		}
		model.addAttribute("tipFlag", tipFlag);
		
		return "xwbcreditsluckydraw/material_review";
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

	@RequestMapping("/updatePass")
	@ResponseBody
	public String updatePass(Integer code, Integer credits, String account,
			HttpServletRequest request) {
		//审核图片  begin
		MaterialAuditBean materialAuditBean = materialAuditBll.getMaterialInfoByCode(code);
		if(!materialAuditBean.getMaterialType().equals("建议") && materialAuditBean.getTempUrl() != null && materialAuditBean.getImgFeature() != null){
			ActionCommonImageBean actionCommonImageBean = new ActionCommonImageBean();
			actionCommonImageBean.setTempUrl(materialAuditBean.getTempUrl());
			actionCommonImageBean.setImgFeature(materialAuditBean.getImgFeature());
			actionCommonImageBean.setImgWidth(materialAuditBean.getImgWidth());
			actionCommonImageBean.setImgHeight(materialAuditBean.getImgHeight());
			ResultBean<String> imgResult = oaCommonService.passImg(actionCommonImageBean);
			String realUrl = imgResult.getData();
			MaterialInfoBean materialInfoBean = new MaterialInfoBean();
			materialInfoBean.setMaterialPicture(realUrl);
			materialInfoBean.setCode(materialAuditBean.getMaterialCode());
			materialInfoBLL.setRealUrl(materialInfoBean);
		}else if(materialAuditBean.getMaterialType().equals("建议")){
			
		}else{
			return "3";
		}
		//审核图片  end	
		String userName = "管理员";
		materialAuditBll.updateIsChecked("已通过", code);
		
		OprateRecordsBean record = new OprateRecordsBean(userName, new Date(),
				account, credits, code,0);
		oprateRecordsBll.addRecord(record);
		CreditsBean credit = new CreditsBean();
		credit.setType("审核");
		credit.setEnterTime(new Date());
		credit.setAccount(account);
		credit.setCredits(credits);
		creditBll.judge(credit);
		materialAuditBll.updateCreditsNum(credits, code);
		return "1";
	}

	@RequestMapping("/updateFailed")
	@ResponseBody
	public String updateFailed(Integer code,Integer credits, String account) {
		materialAuditBll.updateIsChecked("未通过", code);
		String userName = "管理员";
		OprateRecordsBean record = new OprateRecordsBean(userName, new Date(),
				account, credits, code, 0);
		oprateRecordsBll.addRecord(record);
		CreditsBean credit = new CreditsBean();
		credit.setType("审核");
		credit.setEnterTime(new Date());
		credit.setAccount(account);
		credit.setCredits(credits);
		creditBll.judge(credit);
		materialAuditBll.updateCreditsNum(credits, code);
		return "1";
	}

	/**
	 * 显示到首页
	 * 
	 * @author fanyongliang
	 * @param auditCode
	 * @param materialType
	 */
	@RequestMapping("/showpass")
	@ResponseBody
	public void showpass(Integer auditCode, String materialType) {
		logger.info("炫舞吧积分活动___MaterialAuditController显示到首页,接受的参数auditCode:"
				+ auditCode + ",materialType:" + materialType);
		try {
			materialType = new String(materialType.getBytes("ISO-8859-1"),
					"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		logger.info("炫舞吧积分活动___MaterialAuditController显示到首页,编码后materialType:" + materialType);
		ShowTopBean showTopBean = new ShowTopBean();
		showTopBean.setAuditCode(auditCode);
		showTopBean.setMaterialType(materialType);
		showTopBLL.addOne(showTopBean);
	}

	/**
	 * 取消显示
	 * 
	 * @author fanyongliang
	 * @param auditCode
	 */
	@RequestMapping("/shownopass")
	@ResponseBody
	public void shownopass(Integer auditCode) {
		logger.info("炫舞吧积分活动___MaterialAuditController取消显示,参数auditCode:" + auditCode);
		showTopBLL.deleteByAuditCode(auditCode);
	}

	/**
	 * 返回上次修改
	 * 
	 * @author fanyongliang
	 */
	@RequestMapping("/resetshow")
	@ResponseBody
	public void resetshow() {
		showTopBLL.deleteAll();
		List<MaterialAuditBean> list = materialAuditBll.getShowMaterialInfo();
		for (MaterialAuditBean materialAuditBean : list) {
			ShowTopBean showTopBean = new ShowTopBean();
			showTopBean.setAuditCode(materialAuditBean.getCode());
			showTopBean.setMaterialType(materialAuditBean.getMaterialType());
			showTopBLL.addOne(showTopBean);
		}
	}

	/**
	 * 确定修改
	 * 
	 * @author fanyongliang
	 */
	@RequestMapping("/updateshow")
	@ResponseBody
	public void updateshow() {
		materialAuditBll.resetIsShow();
		List<ShowTopBean> list = showTopBLL.getAllInfo();
		for (ShowTopBean showTopBean : list) {
			materialAuditBll.updateIsShow(showTopBean.getAuditCode());
		}
	}
	
	
	@RequestMapping("/getMaterialInfoByCode")
	@ResponseBody
	public MaterialAuditBean getMaterialInfoByCode(Integer code) {
		MaterialAuditBean bean = materialAuditBll.getMaterialInfoByCode(code);
		return bean;
	}
}
