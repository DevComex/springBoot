package cn.gyyx.wd.wanjia.cartoon.beans;

import java.util.Date;

public class WanwdManhua {
    private Integer code;

    private String title;

    private String context;

    private String authorName;

    private String authorAccount;

    private Integer authorId;

    private Integer categoryCode;

    private Date createTime;

    private Date updateTime;

    private Integer isClosed;

    private Integer orderNum;
    private Integer readCount;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context == null ? null : context.trim();
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName == null ? null : authorName.trim();
    }

    public String getAuthorAccount() {
        return authorAccount;
    }

    public void setAuthorAccount(String authorAccount) {
        this.authorAccount = authorAccount == null ? null : authorAccount.trim();
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public Integer getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(Integer categoryCode) {
        this.categoryCode = categoryCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(Integer isClosed) {
        this.isClosed = isClosed;
    }

 

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public Integer getReadCount() {
		return readCount;
	}

	public void setReadCount(Integer readCount) {
		this.readCount = readCount;
	}

	public WanwdManhua(Integer code, String title, String context, String authorName, String authorAccount,
			Integer authorId, Integer categoryCode, Date createTime, Date updateTime, Integer isClosed,
			Integer orderNum, Integer readCount) {
		super();
		this.code = code;
		this.title = title;
		this.context = context;
		this.authorName = authorName;
		this.authorAccount = authorAccount;
		this.authorId = authorId;
		this.categoryCode = categoryCode;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.isClosed = isClosed;
		this.orderNum = orderNum;
		this.readCount = readCount;
	}

	public WanwdManhua() {
		super();
	}
    
}