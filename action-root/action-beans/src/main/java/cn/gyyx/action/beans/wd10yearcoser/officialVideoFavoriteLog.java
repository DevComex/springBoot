package cn.gyyx.action.beans.wd10yearcoser;

import java.util.Date;
/**
 * 版        权：光宇游戏
 * 作        者：WangAndong
 * 创建时间：2016年9月9日 下午1:08:01
 * 描        述：
 */
public class officialVideoFavoriteLog {
    private Integer code;

    private Integer userCode;

    private Integer videoCode;
    
    private Integer sourceCode;
    
    private Date createTime;
    // 瞬时字段 不存数据库 传值
    private String username;

    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(Integer sourceCode) {
		this.sourceCode = sourceCode;
	}

	public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getUserCode() {
        return userCode;
    }

    public void setUserCode(Integer userCode) {
        this.userCode = userCode;
    }

    public Integer getVideoCode() {
		return videoCode;
	}

	public void setVideoCode(Integer videoCode) {
		this.videoCode = videoCode;
	}

	public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}