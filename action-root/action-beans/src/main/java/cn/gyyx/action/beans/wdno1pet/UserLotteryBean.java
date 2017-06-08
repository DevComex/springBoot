/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2014年12月17日下午1:45:04
 * 版本号：v1.0
 * 本类主要用途叙述：用户中奖信息的实体
 */



package cn.gyyx.action.beans.wdno1pet;

public class UserLotteryBean {
	/**
	 * 用户资格表的主键
	 */
	private int userLotteryCode;
	/**
	 * 用户主键
	 */
	private int userCode;
	/**
	 * 用户中奖状态
	 */
	private String userLotteryStatus;
	/**
	 * 用户中奖的类型
	 */
	private String userLotteryType;
	/**
	 * 收件地址
	 */
	private String userAdress;
	/**
	 * 中奖类型的整数代表
	 */
	private int userLotteryTypeValues;

	/**
	 * 中奖信息的中文
	 */
	 private String userLotteryTypeChinese;
	 /**
	  * 得到中奖信息中文
	  * @return 中文
	  */
	 //错误的种类
	 private int errorCode;
	 
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public String getUserLotteryTypeChinese() {
		return userLotteryTypeChinese;
	}
	/**
	 * 中奖信息中文的设定
	 * @param userLotteryTypeChinese
	 */
	public void setUserLotteryTypeChinese(String userLotteryTypeChinese) {
		this.userLotteryTypeChinese = userLotteryTypeChinese;
	}
	/**
	 * 得到用户中奖类型的代表数字
	 * @return int 代表数字
	 */
	public int getUserLotteryTypeValues() {
		return userLotteryTypeValues;
	}
    /**
     * 设定用户的中奖代表数字
     * @param userLotteryTypeValues
     */
	public void setUserLotteryTypeValues(int userLotteryTypeValues) {
		this.userLotteryTypeValues = userLotteryTypeValues;
	}

	public void setUserCode(int userCode) {
		this.userCode = userCode;
	}

	/**
	 * 得到资格表主键
	 * 
	 * @return userLotteryCode 资格主键
	 */
	public int getUserLotteryCode() {
		return userLotteryCode;
	}

	/**
	 * 设定资格主键
	 * 
	 * @param userLotteryCode
	 */
	public void setUserLotteryCode(int userLotteryCode) {
		this.userLotteryCode = userLotteryCode;
	}

	/**
	 * 得到用户的主键
	 * 
	 * @return userCode 用户主键
	 */
	public Integer getUserCode() {
		return userCode;
	}

	/**
	 * 设定用户主键
	 * 
	 * @param userCode
	 */
	public void setUserCode(Integer userCode) {
		this.userCode = userCode;
	}

	/**
	 * 得到用户状态
	 * 
	 * @return userLotteryStatus 用户状态
	 */
	public String getUserLotteryStatus() {
		return userLotteryStatus;
	}

	/**
	 * 设定用户状态
	 * 
	 * @param userLotteryStatus
	 */
	public void setUserLotteryStatus(String userLotteryStatus) {
		this.userLotteryStatus = userLotteryStatus;
	}

	/**
	 * 得到用户中奖类型
	 * 
	 * @return userLotteryType 用户中奖类型
	 */
	public String getUserLotteryType() {
		return userLotteryType;
	}

	/**
	 * 设定用户中奖类型
	 * 
	 * @param userLotteryType
	 */
	public void setUserLotteryType(String userLotteryType) {
		this.userLotteryType = userLotteryType;
	}

	/**
	 * 得到用户收件地址
	 * 
	 * @return userAdress 用户中奖地址
	 */
	public String getUserAdress() {
		return userAdress;
	}

	/**
	 * 设定用户中奖地址
	 * 
	 * @param userAdress
	 */

	public void setUserAdress(String userAdress) {
		this.userAdress = userAdress;
	}

}
