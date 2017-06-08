package cn.gyyx.playwd.beans.playwd;

import java.util.Date;

public class PrizeLogBean {
    private Integer code;

    private String operator;

    private String contentType;

    private Integer contentId;

    private Integer userId;

    private Integer prizeId;

    private String alias;

    private Integer yyb;

    private Integer rmb;

    private String contentUrl;

    private Date createTime;
    
    

    public PrizeLogBean() {
      super();
    }

    
    public PrizeLogBean(Integer prizeId,String operator, String contentType, Integer contentId, Integer userId,
        String contentUrl, Date createTime) {
      super();
      this.operator = operator;
      this.contentType = contentType;
      this.contentId = contentId;
      this.userId = userId;
      this.prizeId = prizeId;
      this.contentUrl = contentUrl;
      this.createTime = createTime;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType == null ? null : contentType.trim();
    }

    public Integer getContentId() {
        return contentId;
    }

    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPrizeId() {
        return prizeId;
    }

    public void setPrizeId(Integer prizeId) {
        this.prizeId = prizeId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias == null ? null : alias.trim();
    }

    public Integer getYyb() {
        return yyb;
    }

    public void setYyb(Integer yyb) {
        this.yyb = yyb;
    }

    public Integer getRmb() {
        return rmb;
    }

    public void setRmb(Integer rmb) {
        this.rmb = rmb;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl == null ? null : contentUrl.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}