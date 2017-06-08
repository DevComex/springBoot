package cn.gyyx.playwd.oa.viewmodel;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

public class ArticleSaveModule  {

    @NotBlank(message = "invalid-title|标题不能为空")
    private String title;

    @NotNull(message = "invalid-categoryId|二级分类不可为空")
    private Integer categoryId;

    @NotBlank(message = "invalid-author|显示名称不可为空")
    private String author;

    @NotBlank(message = "invalid-authorType|发布人不可为空")
    @Pattern(regexp = "\\b(player|official)\\b",message = "invalid-authorType|发布人只能player和official")
    private String authorType;

    @NotBlank(message = "invalid-content|编辑内容不可为空")
    private String content;

    //@NotBlank(message = "invalid-cover|图片不可为空")
    private String cover;

    @NotBlank(message = "invalid-summary|描述不可为空")
    private String summary;

    @NotBlank(message = "invalid-keywords|关键词不可为空")
    private String keywords;

    private Integer code;

    private Integer userId;

    private Integer parentId;

    private Integer gameId;

    private Integer serverId;

    private String serverName;

    private Date createTime;

    private Integer viewCount;

    private Integer collectCount;

    private Integer prizeId;

    private String status;

    private Date publishTime;

    private Boolean isDelete;


    private String firstCategoryName;

    private String secondCategoryName;

    private String account;
    
    private Date recommmendTime;

    private String recommendDetail;
 
    public String getFirstCategoryName() {
        return firstCategoryName;
    }

    public void setFirstCategoryName(String firstCategoryName) {
        this.firstCategoryName = firstCategoryName;
    }

    public String getSecondCategoryName() {
        return secondCategoryName;
    }

    public void setSecondCategoryName(String secondCategoryName) {
        this.secondCategoryName = secondCategoryName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

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

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorType() {
        return authorType;
    }

    public void setAuthorType(String authorType) {
        this.authorType = authorType;
    }

     

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public Integer getPrizeId() {
        return prizeId;
    }

    public void setPrizeId(Integer prizeId) {
        this.prizeId = prizeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Date getRecommmendTime() {
		return recommmendTime;
	}

	public void setRecommmendTime(Date recommmendTime) {
		this.recommmendTime = recommmendTime;
	}

	public String getRecommendDetail() {
		return recommendDetail;
	}

	public void setRecommendDetail(String recommendDetail) {
		this.recommendDetail = recommendDetail;
	}
}