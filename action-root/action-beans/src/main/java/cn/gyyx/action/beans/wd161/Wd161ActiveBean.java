package cn.gyyx.action.beans.wd161;

import java.util.Date;

public class Wd161ActiveBean {

		//主键
		private int id;
		//用户标识
		private String account;
		//玩家GID
		private String gid;
		//服务器名字
		private String serverName;
		//积分
		private int score;
		//更新时间
		private Date updateTime;
		//数据放入时间
		private Date insertTime;
		//创建时间
		private int scoreDate;
		//是否已使用
//		private int  hasUsed;
		private int hasVotes;
		
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		
		public String getAccount() {
			return account;
		}
		public void setAccount(String account) {
			this.account = account;
		}
		public String getGid() {
			return gid;
		}
		public void setGid(String gid) {
			this.gid = gid;
		}
		public String getServerName() {
			return serverName;
		}
		public void setServerName(String serverName) {
			this.serverName = serverName;
		}
		public int getScore() {
			return score;
		}
		public void setScore(int score) {
			this.score = score;
		}
		public Date getUpdateTime() {
			return updateTime;
		}
		public void setUpdateTime(Date updateTime) {
			this.updateTime = updateTime;
		}
		public Date getInsertTime() {
			return insertTime;
		}
		public void setInsertTime(Date insertTime) {
			this.insertTime = insertTime;
		}
		public int getScoreDate() {
			return scoreDate;
		}
		public void setScoreDate(int scoreDate) {
			this.scoreDate = scoreDate;
		}
//		public Boolean getHasUsed() {
//			return hasUsed;
//		}
//		public void setHasUsed(Boolean hasUsed) {
//			this.hasUsed = hasUsed;
//		}
		public int getHasVotes() {
			return hasVotes;
		}
		public void setHasVotes(int hasVotes) {
			this.hasVotes = hasVotes;
		}
		
		
		
}
