package cn.gyyx.playwd.beans.playwd;

public class AuthorInfoBean {

    private Integer userId;

    private String author;

    private String account;
    
    private Integer netId;
    
    private String netName;
    private Integer serverId;
    
    private String serverName;

    public AuthorInfoBean() {
        super();
    }

    public AuthorInfoBean(Integer userId, String author, String account,
            Integer netId, Integer serverId,
            String serverName) {
        super();
        this.userId = userId;
        this.author = author;
        this.account = account;
        this.netId = netId;
        this.serverId = serverId;
        this.serverName = serverName;
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


     
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
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