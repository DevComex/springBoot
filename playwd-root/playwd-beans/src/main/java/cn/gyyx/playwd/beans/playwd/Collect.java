package cn.gyyx.playwd.beans.playwd;

import java.util.Date;

public class Collect {
    private Integer code;

    private Integer sourcesCode;
    
    private String sourcesType;

    private Date createrTime;

    private Boolean isDelete;

    private Integer userId;

    private String userName;
    private Integer readLogCode;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getSourcesCode() {
        return sourcesCode;
    }

    public void setSourcesCode(Integer sourcesCode) {
        this.sourcesCode = sourcesCode;
    }

    public Date getCreaterTime() {
        return createrTime;
    }

    public void setCreaterTime(Date createrTime) {
        this.createrTime = createrTime;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

	public Integer getReadLogCode() {
		return readLogCode;
	}

	public void setReadLogCode(Integer readLogCode) {
		this.readLogCode = readLogCode;
	}

	public String getSourcesType() {
		return sourcesType;
	}

	public void setSourcesType(String sourcesType) {
		this.sourcesType = sourcesType;
	}
	
}