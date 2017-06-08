package cn.gyyx.action.beans.wdtravelentry;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * 
 * This class corresponds to the database table wd_travel_entry_tb
 *
 * @gyyxDate do_not_delete_during_merge Tue Jul 05 10:52:11 CST 2016
 */
public class WdTravelEntry {
    /**
     *
     * 
     * This field corresponds to the database column wd_travel_entry_tb.code
     *
     * @gyyxDate Tue Jul 05 10:52:11 CST 2016
     */
    private Integer code;

    /**
     *
     * 
     * This field corresponds to the database column wd_travel_entry_tb.user_id
     *
     * @gyyxDate Tue Jul 05 10:52:11 CST 2016
     */
    private Integer userId;

    /**
     *
     * 
     * This field corresponds to the database column wd_travel_entry_tb.real_name
     *
     * @gyyxDate Tue Jul 05 10:52:11 CST 2016
     */
    @NotBlank(message="真实姓名不能为空")
    @Pattern(regexp="[\\s\\.a-zA-Z\\s\u4e00-\u9fa5]{2,30}",message="真实姓名格式不符合要求")
    private String realName;

    /**
     *
     * 
     * This field corresponds to the database column wd_travel_entry_tb.age
     *
     * @gyyxDate Tue Jul 05 10:52:11 CST 2016
     */
    @NotNull(message="年龄不能为空")
    @Min(value=10,message="年龄不符合要求")@Max(value=100,message="年龄不符合要求")
    private Integer age;

    /**
     *
     * 
     * This field corresponds to the database column wd_travel_entry_tb.gender
     *
     * @gyyxDate Tue Jul 05 10:52:11 CST 2016
     */
    @NotNull(message="性别不能为空")
    private Boolean gender;

    /**
     *
     * 
     * This field corresponds to the database column wd_travel_entry_tb.occupation
     *
     * @gyyxDate Tue Jul 05 10:52:11 CST 2016
     */
    @NotNull(message="现实职业不能为空")
    @NotBlank(message="现实职业不能为空")
    @Length(min=2,max=10,message="现实职业长度不符合要求")
    private String occupation;

    /**
     *
     * 
     * This field corresponds to the database column wd_travel_entry_tb.address
     *
     * @gyyxDate Tue Jul 05 10:52:11 CST 2016
     */
    @NotNull(message="所在省市不能为空")
    @NotBlank(message="所在省市不能为空")
    @Length(min=4,max=20,message="所属省市不符合要求")
    private String address;

    /**
     *
     * 
     * This field corresponds to the database column wd_travel_entry_tb.free_flag
     *
     * @gyyxDate Tue Jul 05 10:52:11 CST 2016
     */
    @NotNull(message="是否有时间不能为空")
    private Boolean freeFlag;

    /**
     *
     * 
     * This field corresponds to the database column wd_travel_entry_tb.phone
     *
     * @gyyxDate Tue Jul 05 10:52:11 CST 2016
     */
    @NotNull(message="手机号不能为空")
    @NotBlank(message="手机号不能为空")
    @Pattern(regexp="^[1][0-9]{10}$",message="手机号格式不正确")
    private String phone;

    /**
     *
     * 
     * This field corresponds to the database column wd_travel_entry_tb.qq
     *
     * @gyyxDate Tue Jul 05 10:52:11 CST 2016
     */
    @NotBlank(message="QQ不能为空")
    @Pattern(regexp="^\\d{1,20}$",message="QQ号格式不正确")
    private String qq;

    /**
     *
     * 
     * This field corresponds to the database column wd_travel_entry_tb.specialty
     *
     * @gyyxDate Tue Jul 05 10:52:11 CST 2016
     */
    private String specialty;

    /**
     *
     * 
     * This field corresponds to the database column wd_travel_entry_tb.event_liked
     *
     * @gyyxDate Tue Jul 05 10:52:11 CST 2016
     */
    private String eventLiked;

    /**
     *
     * 
     * This field corresponds to the database column wd_travel_entry_tb.server_liked
     *
     * @gyyxDate Tue Jul 05 10:52:11 CST 2016
     */
    private String serverLiked;

    /**
     *
     * 
     * This field corresponds to the database column wd_travel_entry_tb. interest
     *
     * @gyyxDate Tue Jul 05 10:52:11 CST 2016
     */
    private String interest;

    /**
     *
     * 
     * This field corresponds to the database column wd_travel_entry_tb.reason
     *
     * @gyyxDate Tue Jul 05 10:52:11 CST 2016
     */
    private String reason;

    /**
     *
     * 
     * This field corresponds to the database column wd_travel_entry_tb.other_server
     *
     * @gyyxDate Tue Jul 05 10:52:11 CST 2016
     */
    private String otherServer;

    /**
     *
     * 
     * This field corresponds to the database column wd_travel_entry_tb.other_role
     *
     * @gyyxDate Tue Jul 05 10:52:11 CST 2016
     */
    private String otherRole;

    /**
     *
     * 
     * This field corresponds to the database column wd_travel_entry_tb.other_level
     *
     * @gyyxDate Tue Jul 05 10:52:11 CST 2016
     */
    private String otherLevel;

    /**
     *
     * 
     * This field corresponds to the database column wd_travel_entry_tb.image_url
     *
     * @gyyxDate Tue Jul 05 10:52:11 CST 2016
     */
    private String imageUrl;

    
    /**
     * 构造
     */
    public WdTravelEntry() {
		super();
	}

	/**
     * 
     * This method returns the value of the database column wd_travel_entry_tb.code
     *
     * @return the value of wd_travel_entry_tb.code
     *
     * @gyyxDate Tue Jul 05 10:52:11 CST 2016
     */
    public Integer getCode() {
        return code;
    }

    /**
     * 
     * This method sets the value of the database column wd_travel_entry_tb.code
     *
     * @param code the value for wd_travel_entry_tb.code
     *
     * @gyyxDate Tue Jul 05 10:52:11 CST 2016
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * 
     * This method returns the value of the database column wd_travel_entry_tb.user_id
     *
     * @return the value of wd_travel_entry_tb.user_id
     *
     * @gyyxDate Tue Jul 05 10:52:11 CST 2016
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 
     * This method sets the value of the database column wd_travel_entry_tb.user_id
     *
     * @param userId the value for wd_travel_entry_tb.user_id
     *
     * @gyyxDate Tue Jul 05 10:52:11 CST 2016
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 
     * This method returns the value of the database column wd_travel_entry_tb.real_name
     *
     * @return the value of wd_travel_entry_tb.real_name
     *
     * @gyyxDate Tue Jul 05 10:52:11 CST 2016
     */
    public String getRealName() {
        return realName;
    }

    /**
     * 
     * This method sets the value of the database column wd_travel_entry_tb.real_name
     *
     * @param realName the value for wd_travel_entry_tb.real_name
     *
     * @gyyxDate Tue Jul 05 10:52:11 CST 2016
     */
    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    /**
     * 
     * This method returns the value of the database column wd_travel_entry_tb.age
     *
     * @return the value of wd_travel_entry_tb.age
     *
     * @gyyxDate Tue Jul 05 10:52:11 CST 2016
     */
    public Integer getAge() {
        return age;
    }

    /**
     * 
     * This method sets the value of the database column wd_travel_entry_tb.age
     *
     * @param age the value for wd_travel_entry_tb.age
     *
     * @gyyxDate Tue Jul 05 10:52:11 CST 2016
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * 
     * This method returns the value of the database column wd_travel_entry_tb.gender
     *
     * @return the value of wd_travel_entry_tb.gender
     *
     * @gyyxDate Tue Jul 05 10:52:11 CST 2016
     */
    public Boolean getGender() {
        return gender;
    }

    /**
     * 
     * This method sets the value of the database column wd_travel_entry_tb.gender
     *
     * @param gender the value for wd_travel_entry_tb.gender
     *
     * @gyyxDate Tue Jul 05 10:52:11 CST 2016
     */
    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    /**
     * 
     * This method returns the value of the database column wd_travel_entry_tb.occupation
     *
     * @return the value of wd_travel_entry_tb.occupation
     *
     * @gyyxDate Tue Jul 05 10:52:11 CST 2016
     */
    public String getOccupation() {
        return occupation;
    }

    /**
     * 
     * This method sets the value of the database column wd_travel_entry_tb.occupation
     *
     * @param occupation the value for wd_travel_entry_tb.occupation
     *
     * @gyyxDate Tue Jul 05 10:52:11 CST 2016
     */
    public void setOccupation(String occupation) {
        this.occupation = occupation == null ? null : occupation.trim();
    }

    /**
     * 
     * This method returns the value of the database column wd_travel_entry_tb.address
     *
     * @return the value of wd_travel_entry_tb.address
     *
     * @gyyxDate Tue Jul 05 10:52:11 CST 2016
     */
    public String getAddress() {
        return address;
    }

    /**
     * 
     * This method sets the value of the database column wd_travel_entry_tb.address
     *
     * @param address the value for wd_travel_entry_tb.address
     *
     * @gyyxDate Tue Jul 05 10:52:11 CST 2016
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * 
     * This method returns the value of the database column wd_travel_entry_tb.free_flag
     *
     * @return the value of wd_travel_entry_tb.free_flag
     *
     * @gyyxDate Tue Jul 05 10:52:11 CST 2016
     */
    public Boolean getFreeFlag() {
        return freeFlag;
    }

    /**
     * 
     * This method sets the value of the database column wd_travel_entry_tb.free_flag
     *
     * @param freeFlag the value for wd_travel_entry_tb.free_flag
     *
     * @gyyxDate Tue Jul 05 10:52:11 CST 2016
     */
    public void setFreeFlag(Boolean freeFlag) {
        this.freeFlag = freeFlag;
    }

    /**
     * 
     * This method returns the value of the database column wd_travel_entry_tb.phone
     *
     * @return the value of wd_travel_entry_tb.phone
     *
     * @gyyxDate Tue Jul 05 10:52:11 CST 2016
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 
     * This method sets the value of the database column wd_travel_entry_tb.phone
     *
     * @param phone the value for wd_travel_entry_tb.phone
     *
     * @gyyxDate Tue Jul 05 10:52:11 CST 2016
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * 
     * This method returns the value of the database column wd_travel_entry_tb.qq
     *
     * @return the value of wd_travel_entry_tb.qq
     *
     * @gyyxDate Tue Jul 05 10:52:11 CST 2016
     */
    public String getQq() {
        return qq;
    }

    /**
     * 
     * This method sets the value of the database column wd_travel_entry_tb.qq
     *
     * @param qq the value for wd_travel_entry_tb.qq
     *
     * @gyyxDate Tue Jul 05 10:52:11 CST 2016
     */
    public void setQq(String qq) {
        this.qq = qq == null ? null : qq.trim();
    }

    /**
     * 
     * This method returns the value of the database column wd_travel_entry_tb.specialty
     *
     * @return the value of wd_travel_entry_tb.specialty
     *
     * @gyyxDate Tue Jul 05 10:52:11 CST 2016
     */
    public String getSpecialty() {
        return specialty;
    }

    /**
     * 
     * This method sets the value of the database column wd_travel_entry_tb.specialty
     *
     * @param specialty the value for wd_travel_entry_tb.specialty
     *
     * @gyyxDate Tue Jul 05 10:52:11 CST 2016
     */
    public void setSpecialty(String specialty) {
        this.specialty = specialty == null ? null : specialty.trim();
    }

    /**
     * 
     * This method returns the value of the database column wd_travel_entry_tb.event_liked
     *
     * @return the value of wd_travel_entry_tb.event_liked
     *
     * @gyyxDate Tue Jul 05 10:52:11 CST 2016
     */
    public String getEventLiked() {
        return eventLiked;
    }

    /**
     * 
     * This method sets the value of the database column wd_travel_entry_tb.event_liked
     *
     * @param eventLiked the value for wd_travel_entry_tb.event_liked
     *
     * @gyyxDate Tue Jul 05 10:52:11 CST 2016
     */
    public void setEventLiked(String eventLiked) {
        this.eventLiked = eventLiked == null ? null : eventLiked.trim();
    }

    /**
     * 
     * This method returns the value of the database column wd_travel_entry_tb.server_liked
     *
     * @return the value of wd_travel_entry_tb.server_liked
     *
     * @gyyxDate Tue Jul 05 10:52:11 CST 2016
     */
    public String getServerLiked() {
        return serverLiked;
    }

    /**
     * 
     * This method sets the value of the database column wd_travel_entry_tb.server_liked
     *
     * @param serverLiked the value for wd_travel_entry_tb.server_liked
     *
     * @gyyxDate Tue Jul 05 10:52:11 CST 2016
     */
    public void setServerLiked(String serverLiked) {
        this.serverLiked = serverLiked == null ? null : serverLiked.trim();
    }

    /**
     * 
     * This method returns the value of the database column wd_travel_entry_tb. interest
     *
     * @return the value of wd_travel_entry_tb. interest
     *
     * @gyyxDate Tue Jul 05 10:52:11 CST 2016
     */
    public String getInterest() {
        return interest;
    }

    /**
     * 
     * This method sets the value of the database column wd_travel_entry_tb. interest
     *
     * @param interest the value for wd_travel_entry_tb. interest
     *
     * @gyyxDate Tue Jul 05 10:52:11 CST 2016
     */
    public void setInterest(String interest) {
        this.interest = interest == null ? null : interest.trim();
    }

    /**
     * 
     * This method returns the value of the database column wd_travel_entry_tb.reason
     *
     * @return the value of wd_travel_entry_tb.reason
     *
     * @gyyxDate Tue Jul 05 10:52:11 CST 2016
     */
    public String getReason() {
        return reason;
    }

    /**
     * 
     * This method sets the value of the database column wd_travel_entry_tb.reason
     *
     * @param reason the value for wd_travel_entry_tb.reason
     *
     * @gyyxDate Tue Jul 05 10:52:11 CST 2016
     */
    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    /**
     * 
     * This method returns the value of the database column wd_travel_entry_tb.other_server
     *
     * @return the value of wd_travel_entry_tb.other_server
     *
     * @gyyxDate Tue Jul 05 10:52:11 CST 2016
     */
    public String getOtherServer() {
        return otherServer;
    }

    /**
     * 
     * This method sets the value of the database column wd_travel_entry_tb.other_server
     *
     * @param otherServer the value for wd_travel_entry_tb.other_server
     *
     * @gyyxDate Tue Jul 05 10:52:11 CST 2016
     */
    public void setOtherServer(String otherServer) {
        this.otherServer = otherServer == null ? null : otherServer.trim();
    }

    /**
     * 
     * This method returns the value of the database column wd_travel_entry_tb.other_role
     *
     * @return the value of wd_travel_entry_tb.other_role
     *
     * @gyyxDate Tue Jul 05 10:52:11 CST 2016
     */
    public String getOtherRole() {
        return otherRole;
    }

    /**
     * 
     * This method sets the value of the database column wd_travel_entry_tb.other_role
     *
     * @param otherRole the value for wd_travel_entry_tb.other_role
     *
     * @gyyxDate Tue Jul 05 10:52:11 CST 2016
     */
    public void setOtherRole(String otherRole) {
        this.otherRole = otherRole == null ? null : otherRole.trim();
    }

    /**
     * 
     * This method returns the value of the database column wd_travel_entry_tb.other_level
     *
     * @return the value of wd_travel_entry_tb.other_level
     *
     * @gyyxDate Tue Jul 05 10:52:11 CST 2016
     */
    public String getOtherLevel() {
        return otherLevel;
    }

    /**
     * 
     * This method sets the value of the database column wd_travel_entry_tb.other_level
     *
     * @param otherLevel the value for wd_travel_entry_tb.other_level
     *
     * @gyyxDate Tue Jul 05 10:52:11 CST 2016
     */
    public void setOtherLevel(String otherLevel) {
        this.otherLevel = otherLevel == null ? null : otherLevel.trim();
    }

    /**
     * 
     * This method returns the value of the database column wd_travel_entry_tb.image_url
     *
     * @return the value of wd_travel_entry_tb.image_url
     *
     * @gyyxDate Tue Jul 05 10:52:11 CST 2016
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * 
     * This method sets the value of the database column wd_travel_entry_tb.image_url
     *
     * @param imageUrl the value for wd_travel_entry_tb.image_url
     *
     * @gyyxDate Tue Jul 05 10:52:11 CST 2016
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
    }

	@Override
	public String toString() {
		return "WdTravelEntry [code=" + code + ", userId=" + userId + ", realName=" + realName + ", age=" + age
				+ ", gender=" + gender + ", occupation=" + occupation + ", address=" + address + ", freeFlag="
				+ freeFlag + ", phone=" + phone + ", qq=" + qq + ", specialty=" + specialty + ", eventLiked="
				+ eventLiked + ", serverLiked=" + serverLiked + ", interest=" + interest + ", reason=" + reason
				+ ", otherServer=" + otherServer + ", otherRole=" + otherRole + ", otherLevel=" + otherLevel
				+ ", imageUrl=" + imageUrl + "]";
	}
    
}