package cn.gyyx.playwd.beans.playwd;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 用户信息
 * @author chenglong
 */
public class UserBean implements Serializable {
    private Integer code;

    private Integer userId;

    private String icon;//头像

    private BigDecimal rmb;//累计获得奖励-人民币

    private Integer silverCoin;//累计获得奖励-银元宝

    private Boolean isSuspend;//是否封停
    
    private int rankNum;//累计财富排名
    private double rewardSum;//累计财富
    private int articleCount;//审核通过的图文数据
    private String title;//称号

    private static final long serialVersionUID = 1L;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public BigDecimal getRmb() {
        return rmb;
    }

    public void setRmb(BigDecimal rmb) {
        this.rmb = rmb;
    }

    public Integer getSilverCoin() {
        return silverCoin;
    }

    public void setSilverCoin(Integer silverCoin) {
        this.silverCoin = silverCoin;
    }

    public Boolean getIsSuspend() {
        return isSuspend;
    }

    public void setIsSuspend(Boolean isSuspend) {
        this.isSuspend = isSuspend;
    }

	public int getRankNum() {
		return rankNum;
	}

	public void setRankNum(int rankNum) {
		this.rankNum = rankNum;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getArticleCount() {
		return articleCount;
	}

	public void setArticleCount(int articleCount) {
		this.articleCount = articleCount;
	}

	public double getRewardSum() {
		return rewardSum;
	}

	public void setRewardSum(double rewardSum) {
		this.rewardSum = rewardSum;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}