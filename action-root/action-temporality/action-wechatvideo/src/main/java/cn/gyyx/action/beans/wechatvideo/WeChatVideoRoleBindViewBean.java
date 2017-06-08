/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 *
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：
 * @作者：tanjunkai        
 * @联系方式：tanjunkai@gyyx.cn
 * @创建时间：2017/3/12 9:48
 * @版本号：0.0.1 -------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.wechatvideo;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;

/**
 * <p>
 * 角色绑定视图
 * </p>
 * 
 * @author tanjunkai
 * @since 0.0.1
 */
public class WeChatVideoRoleBindViewBean {
    @NotBlank(message = "openId不可为空")
    private String openId;
    @NotBlank(message = "微信名称不可为空")
    private String wechatAccount;
    @Min(value = 1, message = "游戏编号无效")
    private Integer serverCode;
    @NotBlank(message = "服务器名称不可为空")
    private String serverName;
    @NotBlank(message = "角色编号无效")
    private String roleId;
    @NotBlank(message = "角色名称不可为空")
    private String roleName;

    @Override
    public String toString() {
        return "WeChatVideoRoleBindViewBean [openId=" + openId
                + ", wechatAccount=" + wechatAccount + ", serverCode="
                + serverCode + ", serverName=" + serverName + ", roleId="
                + roleId + ", roleName=" + roleName + "]";
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    public String getWechatAccount() {
        return wechatAccount;
    }

    public void setWechatAccount(String wechatAccount) {
        this.wechatAccount = wechatAccount == null ? null
                : wechatAccount.trim();
    }

    public Integer getServerCode() {
        return serverCode;
    }

    public void setServerCode(Integer serverCode) {
        this.serverCode = serverCode;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName == null ? null : serverName.trim();
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }
}
