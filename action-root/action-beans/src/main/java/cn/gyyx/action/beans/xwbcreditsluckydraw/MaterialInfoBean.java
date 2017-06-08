/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：炫舞吧积分活动
 * @作者：王雷
 * @联系方式：wanglei@gyyx.cn
 * @创建时间： 2015年9月2日
 * @版本号：V1.214
 * @本类主要用途描述：素材信息实体类（包括视频、服装、建议）
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.xwbcreditsluckydraw;

import java.util.Date;

public class MaterialInfoBean {
	// 主键
	private int code;
	// 素材名
	private String materialName;
	// 类型
	private String materialType;
	// 素材内容
	private String materialProfile;
	// 素材图片（建议可以为空；视屏为封面；图片为图片）
	private String materialPicture;
	// 素材链接（图片和建议可以为空）
	private String materialLink;
	// 是否显示发布人账号
	private Boolean isShowIssuer;
	// 素材类别（服装可以为空）
	private String materialCate;

	// 图片 begin
	private String tempUrl;
	private String imgFeature;
	private Integer imgWidth;
	private Integer imgHeight;
	// 图片 end

	private String account;
	private Date uploadTime;
	private String uploadTimeStr;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
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

	public String getMaterialLink() {
		return materialLink;
	}

	public void setMaterialLink(String materialLink) {
		this.materialLink = materialLink;
	}

	public Boolean getIsShowIssuer() {
		return isShowIssuer;
	}

	public void setIsShowIssuer(Boolean isShowIssuer) {
		this.isShowIssuer = isShowIssuer;
	}

	public String getMaterialCate() {
		return materialCate;
	}

	public void setMaterialCate(String materialCate) {
		this.materialCate = materialCate;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getUploadTimeStr() {
		return uploadTimeStr;
	}

	public void setUploadTimeStr(String uploadTimeStr) {
		this.uploadTimeStr = uploadTimeStr;
	}

	public MaterialInfoBean() {
		super();
	}

	public MaterialInfoBean(int code) {
		super();
		this.code = code;
	}

	@Override
	public String toString() {
		return "MaterialInfoBean [materialName=" + materialName
				+ ", materialType=" + materialType + ", materialProfile="
				+ materialProfile + ", materialPicture=" + materialPicture
				+ ", materialLink=" + materialLink + ", isShowIssuer="
				+ isShowIssuer + ", materialCate=" + materialCate + "]";
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

}
