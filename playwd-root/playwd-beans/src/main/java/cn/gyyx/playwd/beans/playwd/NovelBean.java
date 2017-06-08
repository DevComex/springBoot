package cn.gyyx.playwd.beans.playwd;

import java.io.Serializable;
import java.util.Date;

/**
 * 
  * <p>
  *   小说主表
  * </p>
  *  
  * @author chenglong
  * @since 0.0.1
 */
public class NovelBean implements Serializable {
    private Integer code;
    //用户id
    private Integer userId;
    //账号名称
    private String account;
    //小说名称
    private String name;
    //分类id
    private Integer categoryId;
    //小说简介
    private String description;
    //创建时间
    private Date createTime;
    //最后发布时间
    private Date latestPublishTime;
    //封面
    private String cover;
    //总字数
    private Integer wordCount;
    //状态
    private String status;
    //章节数
    private Integer chapterCount;
    //浏览量
    private Integer viewCount;
    //收藏量
    private Integer collectCount;
    //点赞量
    private Integer likeCount;
    //是否展示
    private Boolean isShow;

	private static final long serialVersionUID = 1L;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLatestPublishTime() {
        return latestPublishTime;
    }

    public void setLatestPublishTime(Date latestPublishTime) {
        this.latestPublishTime = latestPublishTime;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover == null ? null : cover.trim();
    }

    public Integer getWordCount() {
        return wordCount;
    }

    public void setWordCount(Integer wordCount) {
        this.wordCount = wordCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Integer getChapterCount() {
        return chapterCount;
    }

    public void setChapterCount(Integer chapterCount) {
        this.chapterCount = chapterCount;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(Integer collectCount) {
        this.collectCount = collectCount;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }
    
    public Boolean getIsShow() {
		return isShow;
	}

	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}
    
    /**
     * 是否完结 
     */
    public enum Status {
        /**
         * 完结
         */
        finished("finished"), // 完结
        /**
         * 审核通过（展示）
         */
        unfinished("unfinished"); // 非完结
        
        private String value;

        private Status(String value) {
            this.value = value;
        }

        public String Value() {
            return value;
        }
    }
}