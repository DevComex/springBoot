package beans;

import java.io.Serializable;
import java.util.Date;

public class RecommendContentBean implements Serializable {
    private Integer code;

    private Integer recommendSlotId;

    private String title;

    private String url;

    private String cover;

    private Integer displayOrder;

    private Integer contentId;

    private Boolean isDelete;

    private Boolean isDisplay;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getRecommendSlotId() {
        return recommendSlotId;
    }

    public void setRecommendSlotId(Integer recommendSlotId) {
        this.recommendSlotId = recommendSlotId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover == null ? null : cover.trim();
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Integer getContentId() {
        return contentId;
    }

    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Boolean getIsDisplay() {
        return isDisplay;
    }

    public void setIsDisplay(Boolean isDisplay) {
        this.isDisplay = isDisplay;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}