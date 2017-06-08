package cn.gyyx.action.beans.wdtombthreeteam;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * 预约信息实体
 * Created by DerCg on 2016/8/30.
 */
public class TombReserveInfoBean {
    private int code;

//    @NotNull(message = "手机号不可为空")
//    @Pattern(regexp = "^1[0-9]{10}$",message = "手机号格式错误")
    private String phoneNum;

    @NotNull(message = "客户端类型不可为空")
    @Pattern(regexp = "PC|Mobile",message = "客户端类型参数错误")
    private String clientType;

    @NotNull(message = "手机系统类型不可为空")
    @Pattern(regexp = "IOS|Android",message = "手机系统类型参数错误")
    private String mobileSystemType;

    @NotBlank(message = "来源渠道不可为空")
    private String channelType;
    private Date createTime;

    private Boolean isPrize;
    //活动编号
    private int actionCode;
    

    public int getActionCode() {
		return actionCode;
	}

	public void setActionCode(int actionCode) {
		this.actionCode = actionCode;
	}

	/**
     * 获取用户预约号
     * @return
     */
    public int getCode() {
        return code;
    }

    /**
     * 设置用户预约编号
     * @param code
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * 获取用户手机号
     * @return
     */
    public String getPhoneNum() {
        return phoneNum;
    }

    /**
     * 设置手机号
     * @param phoneNum
     */
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    /**
     * 获取用户客户端类型
     * @return mobile 手机端/PC PC端
     */
    public String getClientType() {
        return clientType;
    }

    /**
     * 设置用户客户端类型
     * @param clientType mobile 手机端/PC PC端
     */
    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    /**
     * 获取用户来源渠道类型
     * @return
     */
    public String getChannelType() {
        return channelType;
    }

    /**
     * 设置用户来源渠道类型
     * @param channelType
     */
    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    /**
     * 获取预约时间
     * @return
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置预约时间
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取手机系统类型
     * @return
     */
    public String getMobileSystemType() {
        return mobileSystemType;
    }

    /**
     * 设置手机系统类型
     * @param mobileSystemType
     */
    public void setMobileSystemType(String mobileSystemType) {
        this.mobileSystemType = mobileSystemType;
    }

    /**
     * 获取抽奖状态
     * @return
     */
    public Boolean getIsPrize() {
        return isPrize;
    }

    /**
     * 设置抽奖状态
     * @param prize
     */
    public void setIsPrize(Boolean prize) {
        isPrize = prize;
    }
}
