package cn.gyyx.playwd.beans.playwd;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)//返回Json时为null 则不返回
public class ArticleBean implements Serializable {

    @NotBlank(message = "invalid-title|标题不能为空")
    private String title;
    private String secondTitle;

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
    
    private Integer netId;
    
    private String netName;
    
    private Integer serverId;

    private String serverName;

    private Date createTime;

    private Integer viewCount;
    private String viewCountString;

    private Integer likeCount;
    private String likeCountString;
    
    private Integer collectCount;
    private String collectCountString;

    private Integer prizeId;

    private String status;

    private Date publishTime;
    private String publishTimeStr;

    private Boolean isDelete;
    private String isRecommend;

    private static final long serialVersionUID = 1L;

    private String firstCategoryName;
    private Integer firstCategoryId;

    private String secondCategoryName;

    private String account;
    
    private Date recommmendTime;

    private String recommendDetail;
    
    private String prizeName;

    //标记
    private String remark;
    
    private String editorMessage;
    private String shareFullUrl;//分享全路径
   
	/**
     * 文章状态
     */
    public enum State {
        /**
         * 待审核
         */
        unreviewd("unreviewd"), // 待审核
        /**
         * 审核通过（展示）
         */
        passed("passed"), // 审核通过（展示）
        /**
         * 审核不通过
         */
        failed("failed"), // 审核不通过
        /**
         * 通过但不展示
         */
        hidden("hidden"), // 通过但不展示
        /**
         * 通过且推荐
         */
        recommended("recommended");// 通过且推荐
        
        private String value;

        private State(String value) {
            this.value = value;
        }

        public String Value() {
            return value;
        }
    }

    public Integer getNetId() {
        return netId;
    }

    public void setNetId(Integer netId) {
        this.netId = netId;
    }

    public String getNetName() {
        return netName;
    }

    public void setNetName(String netName) {
        this.netName = netName;
    }

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

    public enum AuthorType {
        /**
         * 发布人 玩家
         */
        PLAYER("player", "玩家"),
        /**
         * 发布人 官方
         */
        OFFICIAL("official", "官方");
        private String index;
        private String value;

        private AuthorType(String index, String value) {
            this.index = index;
            this.value = value;
        }

        public String getIndex() {
            return index;
        }

        public void setIndex(String index) {
            this.index = index;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        // 普通方法
        public static String getName(String index) {
            for (AuthorType c : AuthorType.values()) {
                if (c.getIndex().equals(index)) {
                    return c.value;
                }
            }
            return null;
        }
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
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8") 
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

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
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
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8") 
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

    
    
    public String getViewCountString() {
        return viewCountString;
    }

    public void setViewCountString(String viewCountString) {
        this.viewCountString = viewCountString;
    }

    public String getLikeCountString() {
        return likeCountString;
    }

    public void setLikeCountString(String likeCountString) {
        this.likeCountString = likeCountString;
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
	
	public String getPrizeName() {
		return prizeName;
	}

	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

    public String getSecondTitle() {
        return secondTitle;
    }

    public void setSecondTitle(String secondTitle) {
        this.secondTitle = secondTitle;
    }

    public String getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(String isRecommend) {
        this.isRecommend = isRecommend;
    }

	public String getPublishTimeStr() {
		return publishTimeStr;
	}

	public void setPublishTimeStr(String publishTimeStr) {
		this.publishTimeStr = publishTimeStr;
	}

	public Integer getCollectCount() {
		return collectCount;
	}

	public void setCollectCount(Integer collectCount) {
		this.collectCount = collectCount;
	}

	public String getCollectCountString() {
		return collectCountString;
	}

	public void setCollectCountString(String collectCountString) {
		this.collectCountString = collectCountString;
	}

	public String getUrl() {
		return "/article/view/"+this.code;
	}
	 
	public String getEditorMessage() {
		return editorMessage;
	}

	public void setEditorMessage(String editorMessage) {
		this.editorMessage = editorMessage;
	}

    public Integer getFirstCategoryId() {
        return firstCategoryId;
    }

    public void setFirstCategoryId(Integer firstCategoryId) {
        this.firstCategoryId = firstCategoryId;
    }

    public String getShareFullUrl() {
        return shareFullUrl;
    }

    public void setShareFullUrl(String shareFullUrl) {
        this.shareFullUrl = shareFullUrl;
    }
}