/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：xuanwuba
 * @作者：yangyusheng
 * @联系方式：yangyusheng@gyyx.cn
 * @创建时间： 2015年7月14日 下午2:54:53
 * @版本号：
 * @本类主要用途描述：操作记录控制器
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.oa.xwbcreditsluckydraw;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.gyyx.action.beans.xwbcreditsluckydraw.CreditsBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.OprateRecordsBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.PageBean;
import cn.gyyx.action.bll.xwbcreditsluckydraw.CreditBLL;
import cn.gyyx.action.bll.xwbcreditsluckydraw.MaterialAuditBll;
import cn.gyyx.action.bll.xwbcreditsluckydraw.OprateRecordsBLL;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.oa.stage.httpRequest.OAUserRequest;
import cn.gyyx.oa.stage.model.OAUserInfoModel;

@Controller
@RequestMapping("/xwbJiFen")
public class OprateRecordsController {

	private static final Logger logger = GYYXLoggerFactory
			.getLogger(OprateRecordsController.class);
	private OprateRecordsBLL recordBll = new OprateRecordsBLL();
	private CreditBLL creditBll = new CreditBLL();
	private MaterialAuditBll materialAuditBll = new MaterialAuditBll();

	/**
	 * 
	 * @日期：2015年7月14日
	 * @Title: records
	 * @Description: TODO 操作记录首页
	 * @param model
	 * @param pageNum
	 * @param record
	 * @param time
	 * @return
	 * @throws UnsupportedEncodingException
	 *             String
	 */
	@RequestMapping("/records")
	public String records(Model model, Integer pageNum,
			OprateRecordsBean record, String date)
			throws UnsupportedEncodingException {
		logger.debug(record.getAccount()+"-"+record.getTime());
		logger.debug("第几页：" + pageNum);
		logger.debug("时间：" + date);
		List<OprateRecordsBean> recordsList = null;
		int recordsNum = 0;
		// 直接进入默认第一页
		if (pageNum == null) {
			pageNum = 1;
		}
		record.setPage(pageNum);
		OprateRecordsBean bean = new OprateRecordsBean();
		bean.setPage(pageNum); 
		bean.setTime(date);
		bean.setAccount(record.getAccount());
		if(record.getMaterialCode() != null){
			bean.setMaterialCode(record.getMaterialCode());
		}
		recordsList = recordBll.getRecords(bean);
		recordsNum = recordBll.getRecordsTotal(bean);
		model.addAttribute("date", date);
		
		
		PageBean page = new PageBean(pageNum, recordsNum);
		model.addAttribute("page", page);
		model.addAttribute("account", record.getAccount());
		if(record.getMaterialCode() != null){
			model.addAttribute("materialCode", record.getMaterialCode());
		}
		model.addAttribute("recordsList", recordsList);
		return "xwbcreditsluckydraw/records";
	}

	@RequestMapping("/back")
	public String back(String account, Integer credit, int materialCode,
			HttpServletRequest request) {
		// 撤销加过的积分
		CreditsBean creditBean = new CreditsBean();
		creditBean.setAccount(account);
		creditBean.setCredits(-credit);
		creditBean.setType("撤销");
		creditBean.setEnterTime(new Date());
		creditBll.judge(creditBean);
		// 添加操作记录
		String userName = "管理员";
		OprateRecordsBean record = new OprateRecordsBean(userName, new Date(),
				account, -credit, materialCode,0);
		recordBll.addRecord(record);
		//增加撤销标识
		recordBll.setResetFlag(materialCode);
		
		// 修改审核状态
		materialAuditBll.updateIsChecked("未审核", materialCode);

		//修改素材审核积分
		materialAuditBll.updateCreditsNum(-credit, materialCode);
		
		return "redirect:records";
	}

}
