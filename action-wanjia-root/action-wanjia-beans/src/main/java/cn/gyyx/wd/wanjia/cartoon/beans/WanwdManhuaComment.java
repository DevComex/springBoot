package cn.gyyx.wd.wanjia.cartoon.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class WanwdManhuaComment {
    private Integer code;

    private Integer manhuaCode;

    private Integer parentCode;

    private Integer sourcesId;

    private String sourcesAccount;

    private Integer targetId;

    private String targetAccount;

    private String context;

    private String type;

    private Date createTime;
    private List<WanwdManhuaComment> list;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getManhuaCode() {
        return manhuaCode;
    }

    public void setManhuaCode(Integer manhuaCode) {
        this.manhuaCode = manhuaCode;
    }

    public Integer getParentCode() {
        return parentCode;
    }

    public void setParentCode(Integer parentCode) {
        this.parentCode = parentCode;
    }

    public Integer getSourcesId() {
        return sourcesId;
    }

    public void setSourcesId(Integer sourcesId) {
        this.sourcesId = sourcesId;
    }

    public String getSourcesAccount() {
        return sourcesAccount;
    }

    public void setSourcesAccount(String sourcesAccount) {
        this.sourcesAccount = sourcesAccount == null ? null : sourcesAccount.trim();
    }

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    public String getTargetAccount() {
        return targetAccount;
    }

    public void setTargetAccount(String targetAccount) {
        this.targetAccount = targetAccount == null ? null : targetAccount.trim();
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context == null ? null : context.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8") 
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public List<WanwdManhuaComment> getList() {
		return list;
	}

	public void setList(List<WanwdManhuaComment> list) {
		this.list = list;
	}

	 

 
}