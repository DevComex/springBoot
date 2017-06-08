package cn.gyyx.action.beans.wdpkforecast;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class WdPkRoleBindBean {

    private int code;
    private int actionCode;
    private String account;
    private int userId;
    @NotNull(message = "网络类型不能为空！")
    private Integer area;
    private int ImageNumber;
    @NotNull(message = "服务器ID不能为空！")
    private Integer serverId;
    @NotNull(message = "服务器名不能为空！")
    private String serverName;
    private String roleName;
    private Date createAt;

    public int getImageNumber() {
        return ImageNumber;
    }

    public void setImageNumber(int imageNumber) {
        ImageNumber = imageNumber;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getActionCode() {
        return actionCode;
    }

    public void setActionCode(int actionCode) {
        this.actionCode = actionCode;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "WdPkRoleBindBean [code=" + code + ", actionCode=" + actionCode
                + ", account=" + account + ", userId=" + userId + ", area="
                + area + ", ImageNumber=" + ImageNumber + ", serverId="
                + serverId + ", serverName=" + serverName + ", roleName="
                + roleName + ", createAt=" + createAt + "]";
    }

}
