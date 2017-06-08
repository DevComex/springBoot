package cn.gyyx.action.beans.fightbrickgame;

import java.util.Date;

/**
 * h5小游戏
 * @author yangteng
 *
 */
public class FightBrickGameHistoryBean {
	
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
	 * 游戏分数
	 */
	private int score;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 排名
	 */
	private Integer number;
	
	public FightBrickGameHistoryBean(){}
	
	public FightBrickGameHistoryBean(String openid, String nickName, int score) {
		super();
		this.openid = openid;
		this.nickName = nickName;
		this.score = score;
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
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
	
	
}
