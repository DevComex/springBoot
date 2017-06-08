package cn.gyyx.playwd.beans.playwd;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * 推荐区域信息实体类 yangteng
 */
public class RecommendContentBean implements Serializable {
	
	//编号
    private Integer code;

    //推荐位置ID
    private Integer recommendSlotId;

    //标题
    private String title;

    //文章地址
    private String url;

    //图片地址
    private String cover;

    //显示顺序
    private Integer displayOrder;

    //内容ID
    private Integer contentId;

    //是否删除
    private Boolean isDelete;

    //是否显示
    private Boolean isDisplay;

    //创建时间
    private Date createTime;
    
    //更新时间
    private Date updateTime;
    
    //文章信息
    private ArticleBean articleBean;
 
	private static final long serialVersionUID = 1L;

    public RecommendContentBean(){}    

	public RecommendContentBean(Integer recommendSlotId, String title,
			String url, String cover, Integer contentId) {
		super();
		this.recommendSlotId = recommendSlotId;
		this.title = title;
		this.url = url;
		this.cover = cover;
		this.contentId = contentId;
		this.isDisplay=true;
		Date date=new Date();
		this.createTime=date;
		this.updateTime=date;
	}

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
    
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    
    public String getUpdateTimeStr() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(updateTime);
    }
    
    public ArticleBean getArticleBean() {
		return articleBean;
	}

	public void setArticleBean(ArticleBean articleBean) {
		this.articleBean = articleBean;
	}
}