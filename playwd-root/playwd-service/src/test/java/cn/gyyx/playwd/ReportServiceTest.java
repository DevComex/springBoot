 /**
    * -------------------------------------------------------------------------
    * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
    * @版权所有：北京光宇在线科技有限责任公司
    * @项目名称：玩家天地
    * @作者：李杜迪
    * @联系方式：lidudi@gyyx.cn
    * @创建时间：2017年4月19日下午2:31:27
    * @版本号：1.0.0
    *-------------------------------------------------------------------------
    */
package cn.gyyx.playwd;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import cn.gyyx.playwd.beans.common.PageBean;
import cn.gyyx.playwd.beans.common.ResultBean;
import cn.gyyx.playwd.beans.playwd.CommentBean;
import cn.gyyx.playwd.beans.playwd.ReportBean;
import cn.gyyx.playwd.bll.CommentBll;
import cn.gyyx.playwd.bll.ReportBll;
import cn.gyyx.playwd.service.ReportService;

/**
 * 举报管理
 * @author lidudi
 *
 */
public class ReportServiceTest {

	private ReportBll reportBll;
	
	private CommentBll commentBll;
	
	private ReportService reportService;  
	
	@Before
	public void setMockup() {
		reportService=new ReportService();
		reportBll=mock(ReportBll.class);
		commentBll =mock(CommentBll.class);
		reportService.setCommentBll(commentBll);
		reportService.setReportBll(reportBll);
	}
	
	@Test
	public void getManagementList_whenCountIs0_thenFailed() throws Exception {
		
		Date startDate=new Date();
		Date endDate=new Date();
		String contentType="article ";
		String status="processing";
		String commentAccount="";
		String reportAccount="";
		int pageSize=10;
		int currentPage=1;
		
		PageBean<Map<String, Object>>resultExpect=PageBean.createPage("success", 0, currentPage, pageSize, null, "成功");

		when(reportBll.getReportManagementCount(startDate, endDate, contentType, status, commentAccount, reportAccount))
		.thenReturn(0);
		
		PageBean<Map<String, Object>> resultActual=reportService.
				getManagementList(startDate, endDate, contentType, status, commentAccount, reportAccount, pageSize, currentPage);
		
		assertEquals(resultActual.getIsSuccess(),resultExpect.getIsSuccess());
		assertEquals(resultActual.getDataSet(),resultExpect.getDataSet());
		assertEquals(resultActual.getMessage(),resultExpect.getMessage());
		assertEquals(resultActual.getStatus(),resultExpect.getStatus());
	}
	
	@Test
	public void getManagementList_whenHaveListCommentIsShow_thenSuccess() throws Exception {
		
		Date startDate=new Date();
		Date endDate=new Date();
		String contentType="article ";
		String status="processing";
		String commentAccount="";
		String reportAccount="";
		int pageSize=10;
		int currentPage=1;
		
		CommentBean commentBean=new CommentBean();
		commentBean.setContent("12131");
		commentBean.setShow(true);
		
		ReportBean bean =new ReportBean();
		bean.setCode(1);
		bean.setCommentId(1);
		bean.setCommentBean(commentBean);
		bean.setReason("asdfsadf");
		bean.setCreateTime(new Date());
		bean.setCommentUserAccount("dedee");
		bean.setReportUserAccount("asdasd");
		bean.setStatus("processed");
		
		List<ReportBean> listActual=new ArrayList<>();
		listActual.add(bean);
		
	    List<Map<String, Object>> listExpect=new ArrayList<>();
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	    String strDate = format.format(bean.getCreateTime());
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("code", bean.getCode());
		map.put("commentId", bean.getCommentId());
		map.put("content", bean.getCommentBean().getContent());
		map.put("reason", bean.getReason());
		map.put("reportTime", strDate);
		map.put("commentAccount", bean.getCommentUserAccount());
		map.put("commentAccountStatus", "正常");
		map.put("reportAccount", bean.getReportUserAccount());
		map.put("reportAccountStatus", "正常");	
		map.put("status","已处理");
		map.put("result","展示");
		
		listExpect.add(map);
		
		PageBean<Map<String, Object>>resultExpect=PageBean.createPage("success", 1, currentPage, pageSize, listExpect, "成功");

		when(reportBll.getReportManagementCount(startDate, endDate, contentType, status, commentAccount, reportAccount))
		.thenReturn(1);
		
		when(reportBll.getReportManagementList(startDate, endDate, contentType, status, commentAccount, reportAccount, pageSize, currentPage))
		.thenReturn(listActual);
		
		PageBean<Map<String, Object>> resultActual=reportService.
				getManagementList(startDate, endDate, contentType, status, commentAccount, reportAccount, pageSize, currentPage);
		
		assertEquals(resultActual.getIsSuccess(),resultExpect.getIsSuccess());
		assertEquals(resultActual.getDataSet(),resultExpect.getDataSet());
		assertEquals(resultActual.getMessage(),resultExpect.getMessage());
		assertEquals(resultActual.getStatus(),resultExpect.getStatus());
	}
	
	@Test
	public void getManagementList_whenHaveListCommentIsHidden_thenSuccess() throws Exception {
		
		Date startDate=new Date();
		Date endDate=new Date();
		String contentType="article ";
		String status="processing";
		String commentAccount="";
		String reportAccount="";
		int pageSize=10;
		int currentPage=1;
		
		CommentBean commentBean=new CommentBean();
		commentBean.setContent("12131");
		commentBean.setShow(false);
		
		ReportBean bean =new ReportBean();
		bean.setCode(1);
		bean.setCommentId(1);
		bean.setCommentBean(commentBean);
		bean.setReason("asdfsadf");
		bean.setCreateTime(new Date());
		bean.setCommentUserAccount("dedee");
		bean.setReportUserAccount("asdasd");
		bean.setStatus("processed");
		
		List<ReportBean> listActual=new ArrayList<>();
		listActual.add(bean);
		
	    List<Map<String, Object>> listExpect=new ArrayList<>();
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	    String strDate = format.format(bean.getCreateTime());
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("code", bean.getCode());
		map.put("commentId", bean.getCommentId());
		map.put("content", bean.getCommentBean().getContent());
		map.put("reason", bean.getReason());
		map.put("reportTime", strDate);
		map.put("commentAccount", bean.getCommentUserAccount());
		map.put("commentAccountStatus", "正常");
		map.put("reportAccount", bean.getReportUserAccount());
		map.put("reportAccountStatus", "正常");	
		map.put("status","已处理");
		map.put("result","隐藏");
		
		listExpect.add(map);
		
		PageBean<Map<String, Object>>resultExpect=PageBean.createPage("success", 1, currentPage, pageSize, listExpect, "成功");

		when(reportBll.getReportManagementCount(startDate, endDate, contentType, status, commentAccount, reportAccount))
		.thenReturn(1);
		
		when(reportBll.getReportManagementList(startDate, endDate, contentType, status, commentAccount, reportAccount, pageSize, currentPage))
		.thenReturn(listActual);
		
		PageBean<Map<String, Object>> resultActual=reportService.
				getManagementList(startDate, endDate, contentType, status, commentAccount, reportAccount, pageSize, currentPage);
		
		assertEquals(resultActual.getIsSuccess(),resultExpect.getIsSuccess());
		assertEquals(resultActual.getDataSet(),resultExpect.getDataSet());
		assertEquals(resultActual.getMessage(),resultExpect.getMessage());
		assertEquals(resultActual.getStatus(),resultExpect.getStatus());
	}
	
	@Test
	public void getManagementList_whenHaveListStatusIsProcessing_thenSuccess() throws Exception {
		
		Date startDate=new Date();
		Date endDate=new Date();
		String contentType="article ";
		String status="processing";
		String commentAccount="";
		String reportAccount="";
		int pageSize=10;
		int currentPage=1;
		
		CommentBean commentBean=new CommentBean();
		commentBean.setContent("12131");
		commentBean.setShow(true);
		
		ReportBean bean =new ReportBean();
		bean.setCode(1);
		bean.setCommentId(1);
		bean.setCommentBean(commentBean);
		bean.setReason("asdfsadf");
		bean.setCreateTime(new Date());
		bean.setCommentUserAccount("dedee");
		bean.setReportUserAccount("asdasd");
		bean.setStatus("processing");
		
		List<ReportBean> listActual=new ArrayList<>();
		listActual.add(bean);
		
	    List<Map<String, Object>> listExpect=new ArrayList<>();
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	    String strDate = format.format(bean.getCreateTime());
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("code", bean.getCode());
		map.put("commentId", bean.getCommentId());
		map.put("content", bean.getCommentBean().getContent());
		map.put("reason", bean.getReason());
		map.put("reportTime", strDate);
		map.put("commentAccount", bean.getCommentUserAccount());
		map.put("commentAccountStatus", "正常");
		map.put("reportAccount", bean.getReportUserAccount());
		map.put("reportAccountStatus", "正常");	
		map.put("status","未处理");
		map.put("result","/");
		
		listExpect.add(map);
		
		PageBean<Map<String, Object>>resultExpect=PageBean.createPage("success", 1, currentPage, pageSize, listExpect, "成功");

		when(reportBll.getReportManagementCount(startDate, endDate, contentType, status, commentAccount, reportAccount))
		.thenReturn(1);
		
		when(reportBll.getReportManagementList(startDate, endDate, contentType, status, commentAccount, reportAccount, pageSize, currentPage))
		.thenReturn(listActual);
		
		PageBean<Map<String, Object>> resultActual=reportService.
				getManagementList(startDate, endDate, contentType, status, commentAccount, reportAccount, pageSize, currentPage);
		
		assertEquals(resultActual.getIsSuccess(),resultExpect.getIsSuccess());
		assertEquals(resultActual.getDataSet(),resultExpect.getDataSet());
		assertEquals(resultActual.getMessage(),resultExpect.getMessage());
		assertEquals(resultActual.getStatus(),resultExpect.getStatus());
	}
	
	@Test
	public void changeProcess_whenReportIsNull_thenSuccess() throws Exception {

		Integer reportId=1;
		String commentStatus="hidden";
		String operatorAccount="测试";
		
		ResultBean<Object>resultExpect=new ResultBean<>("incorrect-status", "举报已经处理过", null);

		when(reportBll.getReportInfo(reportId)).thenReturn(null);
		
		ResultBean<Object> resultActual=reportService.changeProcess(reportId, commentStatus, operatorAccount);
		
		assertEquals(resultActual.getIsSuccess(),resultExpect.getIsSuccess());
		assertEquals(resultActual.getData(),resultExpect.getData());
		assertEquals(resultActual.getMessage(),resultExpect.getMessage());
		assertEquals(resultActual.getStatus(),resultExpect.getStatus());
	}
	
	@Test
	public void changeProcess_whenReportProcessed_thenSuccess() throws Exception {

		Integer reportId=1;
		String commentStatus="hidden";
		String operatorAccount="测试";
		
		ResultBean<Object>resultExpect=new ResultBean<>("incorrect-status", "举报已经处理过", null);
		
		ReportBean bean =new ReportBean();
		bean.setCode(1);
		bean.setCommentId(1);
		bean.setReason("asdfsadf");
		bean.setCreateTime(new Date());
		bean.setCommentUserAccount("dedee");
		bean.setReportUserAccount("asdasd");
		bean.setStatus("processed");

		when(reportBll.getReportInfo(reportId)).thenReturn(bean);
		
		ResultBean<Object> resultActual=reportService.changeProcess(reportId, commentStatus, operatorAccount);
		
		assertEquals(resultActual.getIsSuccess(),resultExpect.getIsSuccess());
		assertEquals(resultActual.getData(),resultExpect.getData());
		assertEquals(resultActual.getMessage(),resultExpect.getMessage());
		assertEquals(resultActual.getStatus(),resultExpect.getStatus());
	}
	
	@Test
	public void changeProcess_whenReportProcessingIsShow_thenSuccess() throws Exception {

		Integer reportId=1;
		String commentStatus="show";
		String operatorAccount="测试";
		
		ResultBean<Object>resultExpect=new ResultBean<>("success", "成功", null);
		
		ReportBean bean =new ReportBean();
		bean.setCode(1);
		bean.setCommentId(1);
		bean.setReason("asdfsadf");
		bean.setCreateTime(new Date());
		bean.setCommentUserAccount("dedee");
		bean.setReportUserAccount("asdasd");
		bean.setStatus("Processing");

		when(reportBll.getReportInfo(reportId)).thenReturn(bean);		
		when(reportBll.changeReport(reportId, operatorAccount)).thenReturn(true);
		
		ResultBean<Object> resultActual=reportService.changeProcess(reportId, commentStatus, operatorAccount);
		
		assertEquals(resultActual.getIsSuccess(),resultExpect.getIsSuccess());
		assertEquals(resultActual.getData(),resultExpect.getData());
		assertEquals(resultActual.getMessage(),resultExpect.getMessage());
		assertEquals(resultActual.getStatus(),resultExpect.getStatus());
	}
	
	@Test
	public void changeProcess_whenReportProcessingIsHidden_thenSuccess() throws Exception {

		Integer reportId=1;
		String commentStatus="hidden";
		String operatorAccount="测试";
		
		ResultBean<Object>resultExpect=new ResultBean<>("success", "成功", null);
		
		ReportBean bean =new ReportBean();
		bean.setCode(1);
		bean.setCommentId(1);
		bean.setReason("asdfsadf");
		bean.setCreateTime(new Date());
		bean.setCommentUserAccount("dedee");
		bean.setReportUserAccount("asdasd");
		bean.setStatus("Processing");

		when(reportBll.getReportInfo(reportId)).thenReturn(bean);		
		when(reportBll.changeReport(reportId, operatorAccount)).thenReturn(true);
		when(commentBll.changeCommentIsShow(bean.getCommentId(), false)).thenReturn(true);
		
		ResultBean<Object> resultActual=reportService.changeProcess(reportId, commentStatus, operatorAccount);
		
		assertEquals(resultActual.getIsSuccess(),resultExpect.getIsSuccess());
		assertEquals(resultActual.getData(),resultExpect.getData());
		assertEquals(resultActual.getMessage(),resultExpect.getMessage());
		assertEquals(resultActual.getStatus(),resultExpect.getStatus());
	}
	
	@Test
	public void insert_whenCommentInfoIsNull_thenFailed() throws Exception {

		ResultBean<Object>resultExpect=new ResultBean<>(false, "举报对象不存在", "");
		
		ReportBean bean =new ReportBean();
		bean.setCode(1);
		bean.setCommentId(1);
		bean.setReason("asdfsadf");
		bean.setCreateTime(new Date());
		bean.setCommentUserAccount("dedee");
		bean.setReportUserAccount("asdasd");
		bean.setStatus("Processing");

		when(commentBll.get(bean.getCommentId())).thenReturn(null);
		
		ResultBean<String>resultActual=reportService.insert(bean);
		
		assertEquals(resultActual.getIsSuccess(),resultExpect.getIsSuccess());
		assertEquals(resultActual.getData(),resultExpect.getData());
		assertEquals(resultActual.getMessage(),resultExpect.getMessage());
		assertEquals(resultActual.getStatus(),resultExpect.getStatus());
	}
	
	@Test
	public void insert_whenReportOneself_thenFailed() throws Exception {

		ResultBean<Object>resultExpect=new ResultBean<>(false, "请勿自残", "");
		
		ReportBean bean =new ReportBean();
		bean.setCode(1);
		bean.setCommentId(1);
		bean.setReason("asdfsadf");
		bean.setCreateTime(new Date());
		bean.setCommentUserAccount("dedee");
		bean.setReportUserAccount("asdasd");
		bean.setStatus("Processing");
		bean.setReportUserId(1111);
		
		CommentBean comment=new CommentBean();
		comment.setFromUserId(1111);

		when(commentBll.get(bean.getCommentId())).thenReturn(comment);
		
		ResultBean<String>resultActual=reportService.insert(bean);
		
		assertEquals(resultActual.getIsSuccess(),resultExpect.getIsSuccess());
		assertEquals(resultActual.getData(),resultExpect.getData());
		assertEquals(resultActual.getMessage(),resultExpect.getMessage());
		assertEquals(resultActual.getStatus(),resultExpect.getStatus());
	}
	
	@Test
	public void insert_whenReportOnce_thenFailed() throws Exception {

		ResultBean<Object>resultExpect=new ResultBean<>(false, "每人只能举报一次", "");
		
		ReportBean bean =new ReportBean();
		bean.setCode(1);
		bean.setCommentId(1);
		bean.setReason("asdfsadf");
		bean.setCreateTime(new Date());
		bean.setCommentUserAccount("dedee");
		bean.setReportUserAccount("asdasd");
		bean.setStatus("Processing");
		bean.setReportUserId(1111);
		
		CommentBean comment=new CommentBean();
		comment.setFromUserId(111111);

		when(commentBll.get(bean.getCommentId())).thenReturn(comment);
		when(reportBll.selectUserReport(bean.getReportUserId(),bean.getCommentId())).thenReturn(true);
		
		ResultBean<String>resultActual=reportService.insert(bean);
		
		assertEquals(resultActual.getIsSuccess(),resultExpect.getIsSuccess());
		assertEquals(resultActual.getData(),resultExpect.getData());
		assertEquals(resultActual.getMessage(),resultExpect.getMessage());
		assertEquals(resultActual.getStatus(),resultExpect.getStatus());
	}
	
	@Test
	public void insert_whenAddReport_thenSuccess() throws Exception {

		ResultBean<Object>resultExpect=new ResultBean<>(true, "操作成功", "");
		
		ReportBean bean =new ReportBean();
		bean.setCode(1);
		bean.setCommentId(1);
		bean.setReason("asdfsadf");
		bean.setCreateTime(new Date());
		bean.setCommentUserAccount("dedee");
		bean.setReportUserAccount("asdasd");
		bean.setStatus("Processing");
		bean.setReportUserId(1111);

		CommentBean comment=new CommentBean();
		comment.setFromUserId(111111);
		comment.setFromUserAccount("decvtree");

		when(commentBll.get(bean.getCommentId())).thenReturn(comment);
		when(reportBll.selectUserReport(bean.getReportUserId(),bean.getCommentId())).thenReturn(false);
		when(reportBll.insert(bean.getCommentId(), comment.getFromUserId(),comment.getFromUserAccount(), bean.getReportUserId(),
	            bean.getReportUserAccount(), bean.getReason()))
	            .thenReturn(1);
		
		ResultBean<String>resultActual=reportService.insert(bean);
		
		assertEquals(resultActual.getIsSuccess(),resultExpect.getIsSuccess());
		assertEquals(resultActual.getData(),resultExpect.getData());
		assertEquals(resultActual.getMessage(),resultExpect.getMessage());
		assertEquals(resultActual.getStatus(),resultExpect.getStatus());
	}
}
