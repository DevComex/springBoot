package cn.gyyx.action.beans.wdoldplayers;

import java.util.Date;

public class WdOldWantedInfoBean {
	/** 
	* code
	*/
	private int code;
	/** 
	* my_account
	*/
	private String myAccount;
	/** 
	* my_userId
	*/
	private int myUserId;
	/** 
	* my_area
	*/
	private int myArea;
	/** 
	* my_serverId
	*/
	private int myServerId;
	/** 
	* my_server_name
	*/
	private String myServerName;
	/** 
	* my_role
	*/
	private String myRole;
	/** 
	* my_phone
	*/
	private String myPhone;
	/** 
	* he_account
	*/
	private String heAccount;
	/** 
	* he_userId
	*/
	private int heUserId;
	/** 
	* he_area
	*/
	private int heArea;
	private String heAreaName;
	private String dateS;
	
	public String getDateS() {
		return dateS;
	}
	public void setDateS(String dateS) {
		this.dateS = dateS;
	}
	public String getHeAreaName() {
		return heAreaName;
	}
	public void setHeAreaName(String heAreaName) {
		this.heAreaName = heAreaName;
	}
	/** 
	* he_serverId
	*/
	private int heServerId;
	/** 
	* he_server_name
	*/
	private String heServerName;
	/** 
	* he_professional
	*/
	private String heProfessional;
	/** 
	* he_img
	*/
	private String heImg;
	/** 
	* he_role
	*/
	private String heRole;
	/** 
	* content
	*/
	private String content;
	/** 
	* check_flag
	*/
	private int checkFlag;
	/** 
	* meet_flag
	*/
	private int meetFlag;
	/** 
	* date
	*/
	private Date date;
	/** 
	* relevant_flag
	*/
	private int relevantFlag;
	//是否被当前用户认证
	private int userStatus=0;
	private Date insertTime;
	private Date updateTime;
	
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public int getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(int userStatus) {
		this.userStatus = userStatus;
	}
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
	* 设置 my_account
	*/
	public void setMyAccount(String myAccount){
 		this.myAccount = myAccount;
	}
	/** 
	* 获得 my_account
	*/
	public String getMyAccount(){
		return this.myAccount;
	}
	/** 
	* 设置 my_userId
	*/
	public void setMyUserId(int myUserId){
 		this.myUserId = myUserId;
	}
	/** 
	* 获得 my_userId
	*/
	public int getMyUserId(){
		return this.myUserId;
	}
	/** 
	* 设置 my_area
	*/
	public void setMyArea(int myArea){
 		this.myArea = myArea;
	}
	/** 
	* 获得 my_area
	*/
	public int getMyArea(){
		return this.myArea;
	}
	/** 
	* 设置 my_serverId
	*/
	public void setMyServerId(int myServerId){
 		this.myServerId = myServerId;
	}
	/** 
	* 获得 my_serverId
	*/
	public int getMyServerId(){
		return this.myServerId;
	}
	/** 
	* 设置 my_server_name
	*/
	public void setMyServerName(String myServerName){
 		this.myServerName = myServerName;
	}
	/** 
	* 获得 my_server_name
	*/
	public String getMyServerName(){
		return this.myServerName;
	}
	/** 
	* 设置 my_role
	*/
	public void setMyRole(String myRole){
 		this.myRole = myRole;
	}
	/** 
	* 获得 my_role
	*/
	public String getMyRole(){
		return this.myRole;
	}
	/** 
	* 设置 my_phone
	*/

	/** 
	* 设置 he_account
	*/
	public void setHeAccount(String heAccount){
 		this.heAccount = heAccount;
	}
	public String getMyPhone() {
		return myPhone;
	}
	public void setMyPhone(String myPhone) {
		this.myPhone = myPhone;
	}
	/** 
	* 获得 he_account
	*/
	public String getHeAccount(){
		return this.heAccount;
	}
	/** 
	* 设置 he_userId
	*/
	public void setHeUserId(int heUserId){
 		this.heUserId = heUserId;
	}
	/** 
	* 获得 he_userId
	*/
	public int getHeUserId(){
		return this.heUserId;
	}
	/** 
	* 设置 he_area
	*/
	public void setHeArea(int heArea){
 		this.heArea = heArea;
	}
	/** 
	* 获得 he_area
	*/
	public int getHeArea(){
		return this.heArea;
	}
	/** 
	* 设置 he_serverId
	*/
	public void setHeServerId(int heServerId){
 		this.heServerId = heServerId;
	}
	/** 
	* 获得 he_serverId
	*/
	public int getHeServerId(){
		return this.heServerId;
	}
	/** 
	* 设置 he_server_name
	*/
	public void setHeServerName(String heServerName){
 		this.heServerName = heServerName;
	}
	/** 
	* 获得 he_server_name
	*/
	public String getHeServerName(){
		return this.heServerName;
	}
	/** 
	* 设置 he_professional
	*/
	public void setHeProfessional(String heProfessional){
 		this.heProfessional = heProfessional;
	}
	/** 
	* 获得 he_professional
	*/
	public String getHeProfessional(){
		return this.heProfessional;
	}
	/** 
	* 设置 he_img
	*/
	public void setHeImg(String heImg){
 		this.heImg = heImg;
	}
	/** 
	* 获得 he_img
	*/
	public String getHeImg(){
		return this.heImg;
	}
	/** 
	* 设置 he_role
	*/
	public void setHeRole(String heRole){
 		this.heRole = heRole;
	}
	/** 
	* 获得 he_role
	*/
	public String getHeRole(){
		return this.heRole;
	}
	/** 
	* 设置 content
	*/
	public void setContent(String content){
 		this.content = content;
	}
	/** 
	* 获得 content
	*/
	public String getContent(){
		return this.content;
	}
	/** 
	* 设置 check_flag
	*/
	public void setCheckFlag(int checkFlag){
 		this.checkFlag = checkFlag;
	}
	/** 
	* 获得 check_flag
	*/
	public int getCheckFlag(){
		return this.checkFlag;
	}
	/** 
	* 设置 meet_flag
	*/
	public void setMeetFlag(int meetFlag){
 		this.meetFlag = meetFlag;
	}
	/** 
	* 获得 meet_flag
	*/
	public int getMeetFlag(){
		return this.meetFlag;
	}
	/** 
	* 设置 date
	*/
	public void setDate(Date date){
 		this.date = date;
	}
	/** 
	* 获得 date
	*/
	public Date getDate(){
		return this.date;
	}
	/** 
	* 设置 relevant_flag
	*/
	public void setRelevantFlag(int relevantFlag){
 		this.relevantFlag = relevantFlag;
	}
	/** 
	* 获得 relevant_flag
	*/
	public int getRelevantFlag(){
		return this.relevantFlag;
	}
}