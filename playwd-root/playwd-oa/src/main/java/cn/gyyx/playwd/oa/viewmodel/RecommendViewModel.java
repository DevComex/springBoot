 /**
    * -------------------------------------------------------------------------
    * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
    * @版权所有：北京光宇在线科技有限责任公司
    * @项目名称：玩家天地
    * @作者：李杜迪
    * @联系方式：lidudi@gyyx.cn
    * @创建时间：2017年3月2日下午5:56:54
    * @版本号：1.0.0
    *-------------------------------------------------------------------------
    */
package cn.gyyx.playwd.oa.viewmodel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 推荐验证类
 * @author lidudi
 *
 */
public class RecommendViewModel {
	
	@NotBlank(message="invalid-contentType|内容分类不能为空")
	@Pattern(regexp="\\b(article|manhua)\\b",message="invalid-contentType|只能选择图文和漫画之一")
	public String contentType;

	@NotNull(message="invalid-contentId|推荐内容id不能为空")
	public Integer contentId;
	
	@NotBlank(message="invalid-recommendSlotId|推荐位为必选项")
	@Pattern(regexp="^\\+?[1-9][0-9,]*$",message="invalid-recommendSlotId|推荐位为必选项")
	public String recommendSlotId;
	
	@NotNull(message="invalid-prizeId|奖励类型不能为空")
	public Integer prizeId;

	@Pattern(regexp="\\b(rmb|silverCoins)\\b",message="invalid-prizeType|只能选择人民币和银元宝之一")
	public String prizeType;

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Integer getContentId() {
		return contentId;
	}

	public void setContentId(Integer contentId) {
		this.contentId = contentId;
	}

	public String getRecommendSlotId() {
		return recommendSlotId;
	}

	public void setRecommendSlotId(String recommendSlotId) {
		this.recommendSlotId = recommendSlotId;
	}

	public Integer getPrizeId() {
		return prizeId;
	}

	public void setPrizeId(Integer prizeId) {
		this.prizeId = prizeId;
	}

	public String getPrizeType() {
		return prizeType;
	}

	public void setPrizeType(String prizeType) {
		this.prizeType = prizeType;
	}
}
