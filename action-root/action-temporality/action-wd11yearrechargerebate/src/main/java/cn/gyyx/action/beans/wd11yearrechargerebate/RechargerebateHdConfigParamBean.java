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

/**
 * 
  * <p>
  *   活动配置参数bean
  * </p>
  *  
  * @author chenglong
  * @since 0.0.1
 */
public class RechargerebateHdConfigParamBean {
    private int serverCode;
    private String serverName;
    private String jdbcUrl;
    private String jdbcUserName;
    private String jdbcPassword;
    private String jdbcDriverClassName;
    private String startTime;
    private boolean isLimit;
    private boolean rebateOpen;
    public int getServerCode() {
        return serverCode;
    }
    public void setServerCode(int serverCode) {
        this.serverCode = serverCode;
    }
    public String getServerName() {
        return serverName;
    }
    public void setServerName(String serverName) {
        this.serverName = serverName;
    }
    public String getJdbcUrl() {
        return jdbcUrl;
    }
    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }
    public String getJdbcUserName() {
        return jdbcUserName;
    }
    public void setJdbcUserName(String jdbcUserName) {
        this.jdbcUserName = jdbcUserName;
    }
    public String getJdbcPassword() {
        return jdbcPassword;
    }
    public void setJdbcPassword(String jdbcPassword) {
        this.jdbcPassword = jdbcPassword;
    }
    public String getJdbcDriverClassName() {
        return jdbcDriverClassName;
    }
    public void setJdbcDriverClassName(String jdbcDriverClassName) {
        this.jdbcDriverClassName = jdbcDriverClassName;
    }
    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    public boolean isLimit() {
        return isLimit;
    }
    public void setLimit(boolean isLimit) {
        this.isLimit = isLimit;
    }
    public boolean isRebateOpen() {
        return rebateOpen;
    }
    public void setRebateOpen(boolean rebateOpen) {
        this.rebateOpen = rebateOpen;
    }
    
}
