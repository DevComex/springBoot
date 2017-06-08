package cn.gyyx.wd.wanjia.cartoon.beans;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class WanwdRecommend {
    private Integer code;

    private String title;

    private String picture;

    private String thumbnail;

    private String url;

    private Integer locationId;

    private Boolean isDisplay;

    private Integer displayOrder;

    private Date createTime;

    private Date updateTime;

    private Boolean isDelete;

    private Integer contentId;

    private Integer contentType;

    private String remark;
    private ManhuaInfoBean infoBean;
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture == null ? null : picture.trim();
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail == null ? null : thumbnail.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public Boolean getIsDisplay() {
        return isDisplay;
    }

    public void setIsDisplay(Boolean isDisplay) {
        this.isDisplay = isDisplay;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getContentId() {
        return contentId;
    }

    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }

    public Integer getContentType() {
        return contentType;
    }

    public void setContentType(Integer contentType) {
        this.contentType = contentType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

	public ManhuaInfoBean getInfoBean() {
		return infoBean;
	}

	public void setInfoBean(ManhuaInfoBean infoBean) {
		this.infoBean = infoBean;
	}

	@Override
	public String toString() {
		return "WanwdRecommend [code=" + code + ", title=" + title + ", picture=" + picture + ", thumbnail=" + thumbnail
				+ ", url=" + url + ", locationId=" + locationId + ", isDisplay=" + isDisplay + ", displayOrder="
				+ displayOrder + ", createTime=" + createTime + ", updateTime=" + updateTime + ", isDelete=" + isDelete
				+ ", contentId=" + contentId + ", contentType=" + contentType + ", remark=" + remark + ", infoBean="
				+ infoBean + "]";
	}
    
}