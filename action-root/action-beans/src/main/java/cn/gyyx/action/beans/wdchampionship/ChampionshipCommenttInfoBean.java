/*************************************************
       Copyright ©, 2015, GY Game
       Author: 柳佳琦
       Created: 2016年3月24日
       Note：名人争霸赛评论实体
************************************************/
package cn.gyyx.action.beans.wdchampionship;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName: ChampionshipCommenttInfoBean
 * @Description: TODO 名人争霸赛评论实体.
 * @author 柳佳琦 liujiaqi@gyyx.cn
 * @date 2016年3月24日 上午10:38:47
 */
public class ChampionshipCommenttInfoBean {
	// 主键
	private int code;
	
	//昵称
	private String nickName;
	
	// 评论内容
	private String commentContent;
	
	// 评论时间
	private Date commentTime;
	
	// 是否被删除
	private boolean isDel;
	
	// 第几届的评论
	private int typeOfYear;
	
	// 账号
	private String account;
	
	// 服务器
	private String serverName;
	
	
	private String commentTimeStr;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public Date getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(Date commentTime) {
		commentTimeStr = "";
		if(commentTime != null){
			commentTimeStr = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(commentTime);
		}
		this.commentTime = commentTime;
	}

	public boolean isDel() {
		return isDel;
	}

	public void setDel(boolean isDel) {
		this.isDel = isDel;
	}

	public int getTypeOfYear() {
		return typeOfYear;
	}

	public void setTypeOfYear(int typeOfYear) {
		this.typeOfYear = typeOfYear;
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

	public String getCommentTimeStr() {
		return commentTimeStr;
	}

	public void setCommentTimeStr(String commentTimeStr) {
		this.commentTimeStr = commentTimeStr;
	}

	@Override
	public String toString() {
		return "ChampionshipCommenttInfoBean [code=" + code + ", nickName="
				+ nickName + ", commentContent=" + commentContent
				+ ", commentTime=" + commentTime + ", isDel=" + isDel
				+ ", typeOfYear=" + typeOfYear + ", account=" + account
				+ ", serverName=" + serverName + ", commentTimeStr="
				+ commentTimeStr + "]";
	}
	
	
	
	
}
