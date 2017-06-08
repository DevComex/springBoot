package cn.gyyx.action.beans.wdtombthreeteam;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 问卷调查信息实体
 * Created by DerCg on 2016/8/30.
 */
public class TombServeyInfoBean {
    private int code;
    private int reserveCode;

    @NotBlank(message = "性别不可为空")
    private String sex;

    @NotBlank(message = "年龄不可为空")
    private String age;

    @NotBlank(message = "城市不可为空")
    private String city;

    @NotBlank(message = "职业不可为空")
    private String job;

    @NotBlank(message = "手机型号不可为空")
    private String phoneModel;

    @NotBlank(message = "awaitContent不可为空")
    private String awaitContent;

    @NotBlank(message = "代言人不可为空")
    private String spokesman;

    @NotBlank(message = "messageChannel不可为空")
    private String messageChannel;

    @NotBlank(message = "exchangeChannel不可为空")
    private String exchangeChannel;

    @NotBlank(message = "gameInformation不可为空")
    private String gameInformation;

    @NotBlank(message = "favoriteExperience不可为空")
    private String favoriteExperience;

    @NotBlank(message = "email不可为空")
    @Pattern(regexp = "^\\w+((-\\w+)|(\\.\\w+))*@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$",message = "邮箱格式错误")
    private String email;

    @NotBlank(message = "QQ不可为空")
    @Pattern(regexp = "^[1-9]\\d{4,15}$",message = "QQ格式错误")
    private String qqNum;
    private String createTime;

   /* @NotBlank(message = "手机号不可为空")
    @Pattern(regexp = "^1[0-9]{10}$",message = "手机号格式错误")*/
    private String phoneNum;

//    @NotBlank(message = "渠道不可为空")
    private String channelType;

    @NotBlank(message = "性格不可为空")
    private String disposition;
    
    // 活动编号
    private int actionCode;


    public int getActionCode() {
		return actionCode;
	}

	public void setActionCode(int actionCode) {
		this.actionCode = actionCode;
	}

	/**
     * 获取用户性格
     * @return
     */
    public String getDisposition() {
        return disposition;
    }

    /**
     * 设置用户性格
     * @param disposition
     */
    public void setDisposition(String disposition) {
        this.disposition = disposition;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    //来源渠道
    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    /**
     * 预约编号
     *
     * @return
     */
    public int getReserveCode() {
        return reserveCode;
    }

    /**
     * 预约编号
     *
     * @return
     */
    public void setReserveCode(int reserveCode) {
        this.reserveCode = reserveCode;
    }

    /**
     * 性别
     *
     * @return
     */
    public String getSex() {
        return sex;
    }

    /**
     * 性别
     *
     * @return
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * 年龄
     *
     * @return
     */
    public String getAge() {
        return age;
    }

    /**
     * 年龄
     *
     * @return
     */
    public void setAge(String age) {
        this.age = age;
    }

    /**
     * 城市
     *
     * @return
     */
    public String getCity() {
        return city;
    }

    /**
     * 城市
     *
     * @return
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 职业
     *
     * @return
     */
    public String getJob() {
        return job;
    }

    /**
     * 职业
     *
     * @return
     */
    public void setJob(String job) {
        this.job = job;
    }

    /**
     * 手机型号
     *
     * @return
     */
    public String getPhoneModel() {
        return phoneModel;
    }

    /**
     * 手机型号
     *
     * @return
     */
    public void setPhoneModel(String phoneModel) {
        this.phoneModel = phoneModel;
    }

    /**
     * 最期待玩怎样的盗墓手游
     *
     * @return
     */
    public String getAwaitContent() {
        return awaitContent;
    }

    /**
     * 最期待玩怎样的盗墓手游
     *
     * @return
     */
    public void setAwaitContent(String awaitContent) {
        this.awaitContent = awaitContent;
    }

    /**
     * 手游代言人
     *
     * @return
     */
    public String getSpokesman() {
        return spokesman;
    }

    /**
     * 手游代言人
     *
     * @return
     */
    public void setSpokesman(String spokesman) {
        this.spokesman = spokesman;
    }

    /**
     * 从哪里获取的游戏相关信息
     *
     * @return
     */
    public String getMessageChannel() {
        return messageChannel;
    }

    /**
     * 从哪里获取的游戏相关信息
     *
     * @return
     */
    public void setMessageChannel(String messageChannel) {
        this.messageChannel = messageChannel;
    }

    /**
     * 交流心得的渠道
     *
     * @return
     */
    public String getExchangeChannel() {
        return exchangeChannel;
    }

    /**
     * 交流心得的渠道
     *
     * @return
     */
    public void setExchangeChannel(String exchangeChannel) {
        this.exchangeChannel = exchangeChannel;
    }

    /**
     * 获取游戏中哪方面的资讯
     *
     * @return
     */
    public String getGameInformation() {
        return gameInformation;
    }

    /**
     * 获取游戏中哪方面的资讯
     *
     * @return
     */
    public void setGameInformation(String gameInformation) {
        this.gameInformation = gameInformation;
    }

    /**
     * 之前的游戏体验，最有趣的是
     *
     * @return
     */
    public String getFavoriteExperience() {
        return favoriteExperience;
    }

    /**
     * 之前的游戏体验，最有趣的是
     *
     * @return
     */
    public void setFavoriteExperience(String favoriteExperience) {
        this.favoriteExperience = favoriteExperience;
    }

    /**
     * 邮箱
     *
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * 邮箱
     *
     * @return
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * qq号码
     *
     * @return
     */
    public String getQqNum() {
        return qqNum;
    }

    /**
     * qq号码
     *
     * @return
     */
    public void setQqNum(String qqNum) {
        this.qqNum = qqNum;
    }

    /**
     * @return
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * @return
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

}
