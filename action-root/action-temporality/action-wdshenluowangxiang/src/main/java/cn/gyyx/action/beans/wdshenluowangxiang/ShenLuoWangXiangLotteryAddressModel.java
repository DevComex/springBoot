/**
  * -------------------------------------------------------------------------
  * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
  * @版权所有：北京光宇在线科技有限责任公司
  * @项目名称：action-shenluowangxiang
  * @作者：chenglong
  * @联系方式：chenglong@gyyx.cn
  * @创建时间：2017年3月29日 上午10:59:56
  * @版本号：0.0.1
  *-------------------------------------------------------------------------
  */
package cn.gyyx.action.beans.wdshenluowangxiang;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 
  * <p>
  *   ShenLuoWangXiangLotteryAddressVO 抽奖地址VO
  * </p>
  *  
  * @author chenglong
  * @since 0.0.1
 */
public class ShenLuoWangXiangLotteryAddressModel {
    private Integer code;

    @NotBlank(message="请填写“姓名”！")
    private String name;
    @NotBlank(message="请填写“电话”！")
    private String phone;
    @NotBlank(message="请填写“QQ”！")
    private String qq;
    @NotBlank(message="请填写“邮箱”！")
    private String email;
    @NotBlank(message="请填写“地址”！")
    private String address;

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
}