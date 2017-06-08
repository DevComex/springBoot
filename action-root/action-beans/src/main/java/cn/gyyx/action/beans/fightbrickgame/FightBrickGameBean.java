package cn.gyyx.action.beans.fightbrickgame;

import java.util.Date;

/**
 * h5小游戏
 * @author yangteng
 *
 */
public class FightBrickGameBean {
	
	/**
	 * 编号
	 */
	private int code;
	
	/**
	 * 微信用户标识
	 */
	private String openid;
	
	/**
	 * 微信用户昵称
	 */
	private String nickName;
	
	/**
	 * 游戏最新得分
	 */
	private int latestScore;
	
	/**
	 * 游戏当天最高得分
	 */
	private int highScore;
	
	/**
	 * 创建时间
	 */
	private String createTime;
	
	/**
	 * 更新时间
	 */
	private Date updateTime;
	
	/**
	 * 历史排名
	 */
	private Integer historyRank;
	
	//扩展属性	
	/**
	 * 是否需要更新
	 */
	private boolean isUpdate;
	
	/**
	 * 奖励名称
	 */
	private String prizeName;
	
	/**
	 * 排名
	 */
	private Integer rank;
	
	/**
	 * 历史最高分数
	 */
	private Integer historyScore;
	
	public FightBrickGameBean(){}		
	
	public FightBrickGameBean(String openid, String nickName, int latestScore,
			int highScore) {
		super();
		this.openid = openid;
		this.nickName = nickName;
		this.latestScore = latestScore;
		this.highScore = highScore;
	}

	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public int getLatestScore() {
		return latestScore;
	}

	public void setLatestScore(int latestScore) {
		this.latestScore = latestScore;
	}

	public int getHighScore() {
		return highScore;
	}

	public void setHighScore(int highScore) {
		this.highScore = highScore;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public boolean isUpdate() {
		return isUpdate;
	}

	public void setUpdate(boolean isUpdate) {
		this.isUpdate = isUpdate;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public String getPrizeName() {
		return prizeName;
	}

	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}

	public Integer getHistoryScore() {
		return historyScore;
	}

	public void setHistoryScore(Integer historyScore) {
		this.historyScore = historyScore;
	}

	public Integer getHistoryRank() {
		return historyRank;
	}

	public void setHistoryRank(Integer historyRank) {
		this.historyRank = historyRank;
	}
		
}
