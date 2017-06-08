/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：问道十周年粉丝榜
 * @作者：chenpeilan
 * @联系方式：chenpeilan@gyyx.cn
 * @创建时间： 2016年3月29日
 * @版本号：
 * @本类主要用途描述：问道十周年粉丝榜活动提名实体bean
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.wdtenyearfans;

import java.util.Date;

public class WdNominationInfoBean {
	//黑粉时间
	private Date voteBlackDate;
	//白粉时间
	private Date voteWhiteDate;
	/** 
	* 主键
	*/
	private int code;
	/** 
	* 提名者账号
	*/
	private String accountName;
	/** 
	* 被提名者大区
	*/
	private int nominatedArea;
	/** 
	* 被提名者区服id
	*/
	private int nominatedServerId;
	/** 
	* 被提名者区服名称
	*/
	private String nominatedServerName;
	/** 
	* 被提名者角色
	*/
	private String nominatedRole;
	/** 
	* 提名类型（0表示为自己提名，1表示为他人提名）
	*/
	private int nominatedType;
	/** 
	* 介绍
	*/
	private String nominatedContent;
	/** 
	* 提名日期
	*/
	private Date nominatedDate;
	/** 
	* 提名者ip
	*/
	private String nominatedIp;
	/** 
	* 审核状态（0为审核中，1为通过，2为未通过）
	*/
	private int auditStatus;
	
	/** 
	* 黑粉数
	*/
	private int voteBlack;
	/** 
	* 粉丝数
	*/
	private int voteWhite;
	/** 
	* 提名角色职业
	*/
	private String nominatedProfessional;
	/** 
	* 提名角色性别
	*/
	private String  nominated_sex;
	/** 
	* 大尺寸头像值
	*/
	private String imageNum;
	/**
	 * 账号ID
	 */
	private int userID;
	private String date;

	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	/**
	 * 扁平尺寸头像值
	 */
	private String imageShow;
	/** 
	* 设置 code
	*/
	public void setCode(int code){
 		this.code = code;
	}
	/** 
	* 获得 code
	*/
	public int getCode(){
		return this.code;
	}
	/** 
	* 设置 account_name
	*/
	public void setAccountName(String accountName){
 		this.accountName = accountName;
	}
	/** 
	* 获得 account_name
	*/
	public String getAccountName(){
		return this.accountName;
	}
	/** 
	* 设置 nominated_area
	*/
	public void setNominatedArea(int nominatedArea){
 		this.nominatedArea = nominatedArea;
	}
	/** 
	* 获得 nominated_area
	*/
	public int getNominatedArea(){
		return this.nominatedArea;
	}
	/** 
	* 设置 nominated_server_id
	*/
	public void setNominatedServerId(int nominatedServerId){
 		this.nominatedServerId = nominatedServerId;
	}
	/** 
	* 获得 nominated_server_id
	*/
	public int getNominatedServerId(){
		return this.nominatedServerId;
	}
	/** 
	* 设置 nominated_server_name
	*/
	public void setNominatedServerName(String nominatedServerName){
 		this.nominatedServerName = nominatedServerName;
	}
	/** 
	* 获得 nominated_server_name
	*/
	public String getNominatedServerName(){
		return this.nominatedServerName;
	}
	/** 
	* 设置 nominated_role
	*/
	public void setNominatedRole(String nominatedRole){
 		this.nominatedRole = nominatedRole;
	}
	/** 
	* 获得 nominated_role
	*/
	public String getNominatedRole(){
		return this.nominatedRole;
	}
	/** 
	* 设置 nominated_type
	*/
	public void setNominatedType(int nominatedType){
 		this.nominatedType = nominatedType;
	}
	/** 
	* 获得 nominated_type
	*/
	public int getNominatedType(){
		return this.nominatedType;
	}
	/** 
	* 设置 nominated_content
	*/
	public void setNominatedContent(String nominatedContent){
 		this.nominatedContent = nominatedContent;
	}
	/** 
	* 获得 nominated_content
	*/
	public String getNominatedContent(){
		return this.nominatedContent;
	}
	/** 
	* 设置 nominated_date
	*/
	public void setNominatedDate(Date nominatedDate){
 		this.nominatedDate = nominatedDate;
	}
	/** 
	* 获得 nominated_date
	*/
	public Date getNominatedDate(){
		return this.nominatedDate;
	}
	/** 
	* 设置 nominated_ip
	*/
	public void setNominatedIp(String nominatedIp){
 		this.nominatedIp = nominatedIp;
	}
	/** 
	* 获得 nominated_ip
	*/
	public String getNominatedIp(){
		return this.nominatedIp;
	}
	/** 
	* 设置 audit_status
	*/
	public void setAuditStatus(int auditStatus){
 		this.auditStatus = auditStatus;
	}
	/** 
	* 获得 audit_status
	*/
	public int getAuditStatus(){
		return this.auditStatus;
	}
	/** 
	* 获得 vote_black
	*/
	public int getVoteBlack() {
		return voteBlack;
	}
	/** 
	* 设置 vote_black
	*/
	public void setVoteBlack(int voteBlack) {
		this.voteBlack = voteBlack;
	}
	/** 
	* 获得 vote_white
	*/
	public int getVoteWhite() {
		return voteWhite;
	}
	/** 
	* 设置 vote_white
	*/
	public void setVoteWhite(int voteWhite) {
		this.voteWhite = voteWhite;
	}
	/** 
	* 获得 nominated_professional
	*/
	public String getNominatedProfessional() {
		return nominatedProfessional;
	}
	/** 
	* 设置 nominated_professional
	*/
	public void setNominatedProfessional(String nominatedProfessional) {
		this.nominatedProfessional = nominatedProfessional;
	}
	/** 
	* 获得nominated_sex
	*/
	public String getNominated_sex() {
		return nominated_sex;
	}
	
	/** 
	* 设置nominated_sex
	*/
	public void setNominated_sex(String nominated_sex) {
		this.nominated_sex = nominated_sex;
	}
	
	/** 
	* 获得 image
	*/
	public String getImageNum() {
		return imageNum;
	}
	/** 
	* 设置 image
	*/
	public void setImageNum(String imageNum) {
		this.imageNum = imageNum;
	}
	/** 
	* 获得 user_id
	*/
	public int getUserID() {
		return userID;
	}
	/** 
	* 设置 user_id
	*/
	public void setUserID(int userID) {
		this.userID = userID;
	}
	/** 
	* 获得 image_show
	*/
	public String getImageShow() {
		return imageShow;
	}
	/** 
	* 设置 image_show
	*/
	public void setImageShow(String imageShow) {
		this.imageShow = imageShow;
	}
	public Date getVoteBlackDate() {
		return voteBlackDate;
	}
	public void setVoteBlackDate(Date voteBlackDate) {
		this.voteBlackDate = voteBlackDate;
	}
	public Date getVoteWhiteDate() {
		return voteWhiteDate;
	}
	public void setVoteWhiteDate(Date voteWhiteDate) {
		this.voteWhiteDate = voteWhiteDate;
	}
	
	
	
	
}