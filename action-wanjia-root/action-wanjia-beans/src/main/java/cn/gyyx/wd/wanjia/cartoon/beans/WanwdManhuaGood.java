package cn.gyyx.wd.wanjia.cartoon.beans;

import java.util.Date;

public class WanwdManhuaGood {
    private Integer code;

    private Integer manhuaCode;

    private Integer userId;

    private String userAccount;

    private Date createTime;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getManhuaCode() {
        return manhuaCode;
    }

    public void setManhuaCode(Integer manhuaCode) {
        this.manhuaCode = manhuaCode;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount == null ? null : userAccount.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}