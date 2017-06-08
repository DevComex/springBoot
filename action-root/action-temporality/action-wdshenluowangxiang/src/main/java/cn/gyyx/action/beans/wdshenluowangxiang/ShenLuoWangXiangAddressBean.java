/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action-wdshenluowangxiang
 * @作者：lihu
 * @联系方式：lihu@gyyx.cn
 * @创建时间：2017年4月8日 下午4:23:05
 * @版本号：0.0.1
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.wdshenluowangxiang;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 
 * <p>
 * ShenLuoWangXiangAddressBean 邀请函地址
 * </p>
 * 
 * @author chenglong
 * @since 0.0.1
 */
public class ShenLuoWangXiangAddressBean {
    private Integer code;
    private Integer userId;

    @NotBlank(message = "地址不能为空")
    private String address;
    private String account;
    @NotBlank(message = "姓名不能为空")
    private String name;
    @NotBlank(message = "手机不能为空")
    private String phone;
    @NotBlank(message = "QQ不能为空")
    private String qq;
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式错误")
    private String email;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }
    

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq == null ? null : qq.trim();
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
        this.account = account;
    }

}