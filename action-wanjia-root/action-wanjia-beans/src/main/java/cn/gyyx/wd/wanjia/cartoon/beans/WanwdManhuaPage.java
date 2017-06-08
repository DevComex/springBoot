package cn.gyyx.wd.wanjia.cartoon.beans;

import java.util.Date;

public class WanwdManhuaPage {
    private Integer code;

    private Integer bookCode;

    private String pageName;

    private String pagePictureUrl;

    private Integer pagePictureNum;

    private Date createTime;
    
    private Integer isDelete;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getBookCode() {
        return bookCode;
    }

    public void setBookCode(Integer bookCode) {
        this.bookCode = bookCode;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName == null ? null : pageName.trim();
    }

    public String getPagePictureUrl() {
        return pagePictureUrl;
    }

    public void setPagePictureUrl(String pagePictureUrl) {
        this.pagePictureUrl = pagePictureUrl == null ? null : pagePictureUrl.trim();
    }

    public Integer getPagePictureNum() {
        return pagePictureNum;
    }

    public void setPagePictureNum(Integer pagePictureNum) {
        this.pagePictureNum = pagePictureNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
}