package cn.gyyx.action.beans.wdrankinglist2017;

import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * <p>
 * 用户宣言
 * </p>
 * 
 * @author niushuai
 * @since 0.0.1
 */
public class DeclarationBean {

    private Integer code;

    private Integer userId;

    private String account;

    @Null(message = "您不可以指定角色名！")
    private String roleName;
    @NotNull(message = "您需要指定openId！")
    private String openId;
    @NotNull(message = "您还没有上传头像！")
    private String avatar;
    @NotNull(message = "你还没有填写宣言！")
    private String declaration;
    @Null(message = "您不可以指定昵称！")
    private String wxNick;
    @NotNull(message = "您还没有填写爱好！")
    private String hobby;

    private Integer grade;
    @NotNull(message = "请选择您的相性！")
    private String element;
    @Null(message = "您不可以指定道行！")
    private Integer daohang;

    private Integer status;

    private Date createTime;

    private Date verifyTime;

    private String verifyAdmin;

    /**
     * 前台用于排名的字段
     */
    private int rank;

    /**
     * @return the rank
     */
    public int getRank() {
        return rank;
    }

    /**
     * @param rank
     *            the rank to set
     */
    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    public String getDeclaration() {
        return declaration;
    }

    public void setDeclaration(String declaration) {
        this.declaration = declaration == null ? null : declaration.trim();
    }

    public String getWxNick() {
        return wxNick;
    }

    public void setWxNick(String wxNick) {
        this.wxNick = wxNick == null ? null : wxNick.trim();
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby == null ? null : hobby.trim();
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element == null ? null : element.trim();
    }

    public Integer getDaohang() {
        return daohang;
    }

    public void setDaohang(Integer daohang) {
        this.daohang = daohang;
    }

    /**
     * @return the createTime
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     *            the createTime to set
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return the verifyTime
     */
    public Date getVerifyTime() {
        return verifyTime;
    }

    /**
     * @param verifyTime
     *            the verifyTime to set
     */
    public void setVerifyTime(Date verifyTime) {
        this.verifyTime = verifyTime;
    }

    /**
     * @return the verifyAdmin
     */
    public String getVerifyAdmin() {
        return verifyAdmin;
    }

    /**
     * @param verifyAdmin
     *            the verifyAdmin to set
     */
    public void setVerifyAdmin(String verifyAdmin) {
        this.verifyAdmin = verifyAdmin;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "DeclarationBean [code=" + code + ", userId=" + userId
                + ", account=" + account + ", roleName=" + roleName
                + ", openId=" + openId + ", avatar=" + avatar + ", declaration="
                + declaration + ", wxNick=" + wxNick + ", hobby=" + hobby
                + ", grade=" + grade + ", element=" + element + ", daohang="
                + daohang + ", status=" + status + ", createTime=" + createTime
                + ", verifyTime=" + verifyTime + ", verifyAdmin=" + verifyAdmin
                + ", rank=" + rank + "]";
    }
}