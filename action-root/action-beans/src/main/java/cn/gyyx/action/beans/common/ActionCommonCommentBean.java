package cn.gyyx.action.beans.common;

import java.util.Date;


public class ActionCommonCommentBean {
	/** 
	* 主键
	*/
	private int code;
	/** 
	* 活动代码
	*/
	private int actionCode;
	/** 
	* 公共表单主键
	*/
	private int formCode;
	/** 
	* 昵称
	*/
	private String nickName;
	/** 
	* 评论
	*/
	private String comment;
	/** 
	* 状态
	*/
	private String status;
	private Date uploadDate;
	public void setCode(int code){
 		this.code = code;
	}
	public int getCode(){
		return this.code;
	}
	public void setActionCode(int actionCode){
 		this.actionCode = actionCode;
	}
	public int getActionCode(){
		return this.actionCode;
	}
	public void setFormCode(int formCode){
 		this.formCode = formCode;
	}
	public int getFormCode(){
		return this.formCode;
	}
	public void setNickName(String nickName){
 		this.nickName = nickName;
	}
	public String getNickName(){
		return this.nickName;
	}
	public void setComment(String comment){
 		this.comment = comment;
	}
	public String getComment(){
		return this.comment;
	}
	public void setStatus(String status){
 		this.status = status;
	}
	public String getStatus(){
		return this.status;
	}
	public Date getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
	public void convertParam() {
		try {
			nickName = convert(nickName);
			comment = convert(comment);
		} catch (Exception e) {
		}
	}
	private String convert(String src) {
		String res = null;
		try {
			res = new String(src.getBytes("ISO-8859-1"),"UTF-8");
			res = res.replace('<','《')
					 .replace('>', '》')
					 .replace('\\', '＼')
					 .replace('\'', '‘')
					 .replace('&', '＆')
					 .replace(';', '；')
					 .replace('\"', '“');
		}catch(Exception e) {}
		return res;
	}
}