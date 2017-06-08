/**
  * -------------------------------------------------------------------------
  * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
  * @版权所有：北京光宇在线科技有限责任公司
  * @项目名称：action-wd11yearrechargerebate
  * @作者：chenglong
  * @联系方式：chenglong@gyyx.cn
  * @创建时间：2017年3月29日 上午10:59:56
  * @版本号：0.0.1
  *-------------------------------------------------------------------------
  */
package cn.gyyx.action.beans.wd11yearrechargerebate;

import java.util.Date;

/**
 * 
  * <p>
  *   查询日志实体bean
  * </p>
  *  
  * @author chenglong
  * @since 0.0.1
 */
public class RechargeRebateQueryLogBean {
    private int code;
    private int userId;
    private String account;
    private int historyRechargeTotal;
    private Date createTime;
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getAccount() {
        return account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public int getHistoryRechargeTotal() {
        return historyRechargeTotal;
    }
    public void setHistoryRechargeTotal(int historyRechargeTotal) {
        this.historyRechargeTotal = historyRechargeTotal;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
}
