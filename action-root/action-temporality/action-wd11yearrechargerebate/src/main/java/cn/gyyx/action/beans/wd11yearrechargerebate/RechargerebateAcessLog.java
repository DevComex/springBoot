package cn.gyyx.action.beans.wd11yearrechargerebate;

import java.util.Date;
/**
 * 
  * <p>
  *   操作日志实体bean
  * </p>
  *  
  * @author lihu
  * @since 0.0.1
 */
public class RechargerebateAcessLog {

    private String account;

    private Integer userId;

    private Date createTime;

    private String result;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result == null ? null : result.trim();
    }
}