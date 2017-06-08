/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2014年12月15日下午1:53:54
 * 版本号：v1.0
 * 本类主要用途叙述：举报的实体。
 */



package cn.gyyx.action.beans.wdno1pet;

public class CrimeReporterBean {
	//举报主键
	private int  crimeCode;
	//用户主键
	private int userCode;
	//宠物作品主键
	private int petCode;
	//被举报作品链接
	private  String petUrl;
	/**
	 * 得到举报的主键
	 * @return crimeCode
	 */
	public int getCrimeCode() {
		return crimeCode;
	}
	/**
	 * 设定举报的主键	
	 * @param crimeCode
	 */
	public void setCrimeCode(int crimeCode) {
		this.crimeCode = crimeCode;
	}
	/**
	 * 得到举报用户的主键
	 * @return userCode
	 */
	public Integer getUserCode() {
		return userCode;
	}
	/**
	 * 设定举报用户的主键
	 * @param userCode
	 */
	public void setUserCode(Integer userCode) {
		this.userCode = userCode;
	}
	/**
	 * 得到被举报作品的主键
	 * @return petCode
	 */
	public Integer getPetCode() {
		return petCode;
	}
	/**
	 * 设定被举报用户的主键
	 * @param petCode
	 */
	public void setPetCode(Integer petCode) {
		this.petCode = petCode;
	}
	/**
	 * 得到作品的链接
	 * @return petUrl 
	 */
	public String getPetUrl() {
		return petUrl;
	}
	/**
	 * 设定作品的链接
	 * @param petUrl
	 */
	public void setPetUrl(String petUrl) {
		this.petUrl = petUrl;
	}
	
	

}
