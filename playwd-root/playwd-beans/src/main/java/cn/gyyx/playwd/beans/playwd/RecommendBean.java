 /**
    * -------------------------------------------------------------------------
    * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
    * @版权所有：北京光宇在线科技有限责任公司
    * @项目名称：玩家天地
    * @作者：李杜迪
    * @联系方式：lidudi@gyyx.cn
    * @创建时间：2017年3月3日上午10:57:08
    * @版本号：1.0.0
    *-------------------------------------------------------------------------
    */
package cn.gyyx.playwd.beans.playwd;

import java.util.Date;

/**
 * 推荐表
 * @author lidudi
 *
 */
public class RecommendBean {

	/**
	 * 默认构造函数
	 */
	public RecommendBean() {
		super();
	}

	private Integer code;

	//内容类型
    private String contentType;

    //内容Id
    private Integer contentId;

    //推荐位置id
    private Integer slotId;

    //标题
    private String title;

    //封面
    private String cover;

    private Date createTime;

    //是否删除
    private Boolean isDelete;
    
    //奖励id
    private Integer prizeId;

	public Integer getPrizeId() {
		return prizeId;
	}

	public void setPrizeId(Integer prizeId) {
		this.prizeId = prizeId;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

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

	public Integer getSlotId() {
		return slotId;
	}

	public void setSlotId(Integer slotId) {
		this.slotId = slotId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}
}
