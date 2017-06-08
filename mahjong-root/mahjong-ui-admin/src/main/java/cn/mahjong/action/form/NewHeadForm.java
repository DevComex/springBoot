package cn.mahjong.action.form;

import javax.validation.GroupSequence;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import cn.mahjong.beans.validator.FirstGroup;
import cn.mahjong.beans.validator.SecondGroup;

@GroupSequence({FirstGroup.class, SecondGroup.class, NewHeadForm.class})
public class NewHeadForm {

    private Integer gameUserId;
    
    private String wechatId;

    private String name;

    @NotBlank(message = "手机号不能为空", groups = {FirstGroup.class})
    private String phone;

    @NotBlank(message = "验证码不能为空", groups = {FirstGroup.class})
    private String verifyCode;

    @NotBlank(message = "省市不能为空", groups = {FirstGroup.class})
    private String province;

    @NotBlank(message = "省市不能为空", groups = {FirstGroup.class})
    private String city;

    @NotBlank(message = "地址不能为空", groups = {FirstGroup.class})
    private String address;

    public Integer getGameUserId() {
        return gameUserId;
    }

    public void setGameUserId(Integer gameUserId) {
        this.gameUserId = gameUserId;
    }

    public String getWechatId() {
        return wechatId;
    }

    public void setWechatId(String wechatId) {
        this.wechatId = wechatId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "NewHeadForm [gameUserId=" + gameUserId + ", wechatId=" + wechatId + ", name=" + name
                + ", phone=" + phone + ", verifyCode=" + verifyCode + ", province=" + province
                + ", city=" + city + ", address=" + address + "]";
    }
}
