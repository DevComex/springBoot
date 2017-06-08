/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：xuanwuba
 * @作者：yangyusheng
 * @联系方式：yangyusheng@gyyx.cn
 * @创建时间： 2015年7月7日 下午3:36:08
 * @版本号：
 * @本类主要用途描述：素材提交审核实体类
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.xwbcreditsluckydraw;

import java.util.Date;

/**
 * 
 * */
public class MaterialAuditBean {

	// 主键编号
	private int code;
	// 用户账号
	private String account;
	// 所在大区
	private Integer netType;
	// 所在区服
	private String serverName;
	// 素材名称
	private String materialName;
	// 素材类型
	private String materialType;
	// 素材内容
	private Integer materialCode;
	// 是否通过
	private String isChecked;
	// 所获积分
	private Integer credits;
	// 所获评论数
	private Integer commentGot;
	// 所获评论数
	private Integer praiseGot;
	// 是否显示
	private Integer isShow;
	//上传时间
	private Date uploadTime;
	
	
	
	//选择首页显示
	private Integer showStatus;
	//日期字符串
	private String uploadTimeStr;
	
	
	private Integer withBest;
	
	// 图片 begin
	private String tempUrl;
	private String imgFeature;
	private Integer imgWidth;
	private Integer imgHeight;
	// 图片 end
	
	
	/**
	 * new
	 */
	private String content;
	private String materialProfile;
	private String materialPicture;
	
	private String commonNum;
	private String praiseNum;
	
	private String materialLink;
	private String materialCate;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Integer getNetType() {
		return netType;
	}

	public void setNetType(Integer netType) {
		this.netType = netType;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getMaterialType() {
		return materialType;
	}

	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}

	public Integer getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(Integer materialCode) {
		this.materialCode = materialCode;
	}

	public String getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(String isChecked) {
		this.isChecked = isChecked;
	}

	public Integer getCredits() {
		return credits;
	}

	public void setCredits(Integer credits) {
		this.credits = credits;
	}

	public Integer getCommentGot() {
		return commentGot;
	}

	public void setCommentGot(Integer commentGot) {
		this.commentGot = commentGot;
	}

	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}


	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	
	
	public Integer getPraiseGot() {
		return praiseGot;
	}

	public void setPraiseGot(Integer praiseGot) {
		this.praiseGot = praiseGot;
	}
	
	
	public Integer getWithBest() {
		return withBest;
	}

	public void setWithBest(Integer withBest) {
		this.withBest = withBest;
	}

	/**
	 * new
	 */
	

	public MaterialAuditBean(String materialType, Integer isShow) {
		super();
		this.materialType = materialType;
		this.isShow = isShow;
	}

	public String getUploadTimeStr() {
		return uploadTimeStr;
	}

	public void setUploadTimeStr(String uploadTimeStr) {
		this.uploadTimeStr = uploadTimeStr;
	}

	public Integer getShowStatus() {
		return showStatus;
	}

	public void setShowStatus(Integer showStatus) {
		this.showStatus = showStatus;
	}

	public String getMaterialLink() {
		return materialLink;
	}

	public void setMaterialLink(String materialLink) {
		this.materialLink = materialLink;
	}

	public String getMaterialCate() {
		return materialCate;
	}

	public void setMaterialCate(String materialCate) {
		this.materialCate = materialCate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMaterialProfile() {
		return materialProfile;
	}

	public void setMaterialProfile(String materialProfile) {
		this.materialProfile = materialProfile;
	}

	public String getMaterialPicture() {
		return materialPicture;
	}

	public void setMaterialPicture(String materialPicture) {
		this.materialPicture = materialPicture;
	}

	public MaterialAuditBean() {
		super();
	}
	
	
	
	public String getCommonNum() {
		return commonNum;
	}

	public void setCommonNum(String commonNum) {
		this.commonNum = commonNum;
	}

	public String getPraiseNum() {
		return praiseNum;
	}

	public void setPraiseNum(String praiseNum) {
		this.praiseNum = praiseNum;
	}
	
	

	public String getTempUrl() {
		return tempUrl;
	}

	public void setTempUrl(String tempUrl) {
		this.tempUrl = tempUrl;
	}

	public String getImgFeature() {
		return imgFeature;
	}

	public void setImgFeature(String imgFeature) {
		this.imgFeature = imgFeature;
	}

	public Integer getImgWidth() {
		return imgWidth;
	}

	public void setImgWidth(Integer imgWidth) {
		this.imgWidth = imgWidth;
	}

	public Integer getImgHeight() {
		return imgHeight;
	}

	public void setImgHeight(Integer imgHeight) {
		this.imgHeight = imgHeight;
	}

	//上传用
	public MaterialAuditBean(String account, Integer netType,
			String serverName, String materialName, String materialType,
			Integer materialCode, String isChecked, Integer credits,
			Integer commentGot,Integer praiseGot, Integer isShow,Date uploadTime) {
		super();
		this.account = account;
		this.netType = netType;
		this.serverName = serverName;
		this.materialName = materialName;
		this.materialType = materialType;
		this.materialCode = materialCode;
		this.isChecked = isChecked;
		this.credits = credits;
		this.commentGot = commentGot;
		this.praiseGot = praiseGot;
		this.isShow = isShow;
		this.uploadTime = uploadTime;
	}
	
	
}
