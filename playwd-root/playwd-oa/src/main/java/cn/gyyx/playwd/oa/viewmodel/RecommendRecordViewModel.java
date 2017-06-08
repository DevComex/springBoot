package cn.gyyx.playwd.oa.viewmodel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 推荐记录
 * @author lidudi
 *
 */
public class RecommendRecordViewModel {

	@NotBlank(message="invalid-contentType|内容分类不能为空")
	@Pattern(regexp="\\b(article|manhua)\\b",message="invalid-contentType|只能选择图文和漫画之一")
	public String contentType;
	
	@NotNull(message="invalid-prizeId|奖励级别选择错误")
	public Integer prizeId;
	
	/**
	 * 开始时间
	 */
	@NotBlank(message="invalid-startDate|开始时间不能为空")
	@Pattern(regexp="((19|20)[0-9]{2})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])",message="invalid-startDate|开始时间格式不正确")
	private String startDate;
	/**
	 * 结束时间
	 */
	@NotBlank(message="invalid-endDate|结束时间不能为空")
	@Pattern(regexp="((19|20)[0-9]{2})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])",message="invalid-endDate|结束时间格式不正确")
	private String endDate;

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

	public Integer getPrizeId() {
		return prizeId;
	}

	public void setPrizeId(Integer prizeId) {
		this.prizeId = prizeId;
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
