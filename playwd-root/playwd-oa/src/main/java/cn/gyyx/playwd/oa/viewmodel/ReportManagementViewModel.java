 /**
    * -------------------------------------------------------------------------
    * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
    * @版权所有：北京光宇在线科技有限责任公司
    * @项目名称：玩家天地
    * @作者：李杜迪
    * @联系方式：lidudi@gyyx.cn
    * @创建时间：2017年3月27日下午4:02:47
    * @版本号：1.0.0
    *-------------------------------------------------------------------------
    */
package cn.gyyx.playwd.oa.viewmodel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 推荐管理
 * @author lidudi
 *
 */
public class ReportManagementViewModel {

	@NotBlank(message="invalid-contentType|内容分类不能为空")
	@Pattern(regexp="\\b(all|article|manhua)\\b",message="invalid-contentType|所属栏目选择错误")
	public String contentType;

	@NotBlank(message="invalid-startDate|开始时间不能为空")
	@Pattern(regexp="((19|20)[0-9]{2})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])",message="invalid-startDate|开始时间格式不正确")
	private String startDate;

	@NotBlank(message="invalid-endDate|结束时间不能为空")
	@Pattern(regexp="((19|20)[0-9]{2})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])",message="invalid-endDate|结束时间格式不正确")
	private String endDate;
	
	@NotBlank(message="invalid-status|处理状态不能为空")
	@Pattern(regexp="\\b(all|processed|processing)\\b",message="invalid-status|处理状态选择错误")
	public String status;
	
	//被举报账号
	public String commentAccount;
	
	//举报账号
	public String reportAccount;
	
	@NotNull(message="invalid-pageIndex|页码不能为空")
	public Integer pageIndex;
	
	@NotNull(message="invalid-pageSize|每页显示条数为空")
	public Integer pageSize;
	
	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCommentAccount() {
		return commentAccount;
	}

	public void setCommentAccount(String commentAccount) {
		this.commentAccount = commentAccount;
	}

	public String getReportAccount() {
		return reportAccount;
	}

	public void setReportAccount(String reportAccount) {
		this.reportAccount = reportAccount;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}
