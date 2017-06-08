/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：炫舞吧积分活动
 * @作者：王雷
 * @联系方式：wanglei@gyyx.cn
 * @创建时间： 2015年9月6日
 * @版本号：V1.214
 * @本类主要用途描述：素材首页展示数据类
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.xwbcreditsluckydraw;

public class MaterialShowBean {
	//素材信息Code
	int materialCode;
	//素材图片（建议可以为空；视屏为封面；图片为图片）
	String materialPicture;
	//账号
	String account;
	//服务器名
	String serverName;
	//评价数
	Integer commentsCount;
	//点赞数
	Integer praiseCount;
	
	Integer praiseOwn;
	
	Integer commentsOwn;
	
	public Integer getPraiseOwn() {
		return praiseOwn;
	}
	public void setPraiseOwn(Integer praiseOwn) {
		this.praiseOwn = praiseOwn;
	}
	public Integer getCommentsOwn() {
		return commentsOwn;
	}
	public void setCommentsOwn(Integer commentsOwn) {
		this.commentsOwn = commentsOwn;
	}
	public int getMaterialCode() {
		return materialCode;
	}
	public void setMaterialCode(int materialCode) {
		this.materialCode = materialCode;
	}
	public String getMaterialPicture() {
		return materialPicture;
	}
	public void setMaterialPicture(String materialPicture) {
		this.materialPicture = materialPicture;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public Integer getCommentsCount() {
		return commentsCount;
	}
	public void setCommentsCount(Integer commentsCount) {
		this.commentsCount = commentsCount;
	}
	public Integer getPraiseCount() {
		return praiseCount;
	}
	public void setPraiseCount(Integer praiseCount) {
		this.praiseCount = praiseCount;
	}
		
	public MaterialShowBean(int materialCode, String materialPicture,
			String account, String serverName, Integer commentsCount,
			Integer praiseCount,Integer praiseOwn,Integer commentsOwn) {
		super();
		this.materialCode = materialCode;
		this.materialPicture = materialPicture;
		this.account = account;
		this.serverName = serverName;
		this.commentsCount = commentsCount;
		this.praiseCount = praiseCount;
		this.praiseOwn = praiseOwn;
		this.commentsOwn = commentsOwn;
	}
	public MaterialShowBean() {
		super();
	}
	@Override
	public String toString() {
		return "MaterialShowBean [materialCode=" + materialCode
				+ ", materialPicture=" + materialPicture + ", account="
				+ account + ", serverName=" + serverName + ", commentsCount="
				+ commentsCount + ", praiseCount=" + praiseCount + "]";
	}
	
}
