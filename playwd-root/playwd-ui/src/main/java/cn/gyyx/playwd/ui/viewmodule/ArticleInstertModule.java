package cn.gyyx.playwd.ui.viewmodule;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;


public class ArticleInstertModule {
    @NotBlank(message = "invalid-title|标题不能为空")
    private String title;

    @NotNull(message = "invalid-categoryId|二级分类不可为空")
    private Integer categoryId;

   @NotBlank(message = "invalid-author|作者名称不可为空")
    private String author;

    @NotBlank(message = "invalid-content|编辑内容不可为空")
    private String content;

    @NotBlank(message = "invalid-cover|图片不可为空")
    private String cover;

   @NotBlank(message = "invalid-summary|描述不可为空")
    private String summary;

   @NotBlank(message = "invalid-keywords|关键词不可为空")
    private String keywords;

    @NotNull(message = "invalid-serverId|服务器ID不可为空")
    private Integer serverId;

    @NotBlank(message = "invalid-serverName|服务器名称不可为空")
    private String serverName;
    
    @NotNull(message = "invalid-netId|网络ID不可为空")
    private Integer netId;
    
    //@NotBlank(message = "invalid-netName|网络名称不可为空")
    private String netName;
    public String getTitle() {
        return title;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
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


    
 

    
}
