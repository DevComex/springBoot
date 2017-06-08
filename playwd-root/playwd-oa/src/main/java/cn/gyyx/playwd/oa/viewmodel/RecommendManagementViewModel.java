 /**
    * -------------------------------------------------------------------------
    * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
    * @版权所有：北京光宇在线科技有限责任公司
    * @项目名称：玩家天地
    * @作者：李杜迪
    * @联系方式：lidudi@gyyx.cn
    * @创建时间：2017年3月13日下午2:32:08
    * @版本号：1.0.0
    *-------------------------------------------------------------------------
    */
package cn.gyyx.playwd.oa.viewmodel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 推荐管理列表
 * @author lidudi
 *
 */
public class RecommendManagementViewModel {
	
	@NotBlank(message="invalid-contentType|内容分类不能为空")
	@Pattern(regexp="\\b(article|manhua|novel)\\b",message="invalid-contentType|只能选择图文和漫画之一")
	public String contentType;
	
	@NotNull(message="invalid-recommendSlotId|推荐位选择错误")
	public Integer recommendSlotId;
	
	
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

	public Integer getRecommendSlotId() {
		return recommendSlotId;
	}

	public void setRecommendSlotId(Integer recommendSlotId) {
		this.recommendSlotId = recommendSlotId;
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
