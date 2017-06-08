package cn.gyyx.action.beans.wd10yearcoser;

import java.util.Date;
/**
 * 版        权：光宇游戏
 * 作        者：WangAndong
 * 创建时间：2016年9月9日 下午1:08:01
 * 描        述：
 */
public class ResourceBean {
    private Integer code;

    private Integer userCode;

    private String type;

    private String name;

    private String description;

    private Integer favoriteNum;

    private String attrs;

    private String checkType;

    private Boolean isShow;

    private Date showTime;

    private Date createTime;

    private Date updateTime;

    private Integer clickNum;

    private String username;

    private String works;
    
    //判断当前用户获取资源是否点过赞
    private boolean flag = false;
    
    //ext
    private String beginTimeStr;
    
    private String endTimeStr;
    
    private int currentPage;
	
    private int pageSize;
    private String isShowStr;
	
    
    
    public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getBeginTimeStr() {
		return beginTimeStr;
	}

	public void setBeginTimeStr(String beginTimeStr) {
		this.beginTimeStr = beginTimeStr;
	}

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getFavoriteNum() {
        return favoriteNum;
    }

    public void setFavoriteNum(Integer favoriteNum) {
        this.favoriteNum = favoriteNum;
    }

    public String getAttrs() {
        return attrs;
    }

    public void setAttrs(String attrs) {
        this.attrs = attrs == null ? null : attrs.trim();
    }

    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType == null ? null : checkType.trim();
    }

    public Boolean getIsShow() {
        return isShow;
    }

    public void setIsShow(Boolean isShow) {
        this.isShow = isShow;
    }

    public Date getShowTime() {
        return showTime;
    }

    public void setShowTime(Date showTime) {
        this.showTime = showTime;
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

    public Integer getClickNum() {
        return clickNum;
    }

    public void setClickNum(Integer clickNum) {
        this.clickNum = clickNum;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getWorks() {
        return works;
    }

    public void setWorks(String works) {
        this.works = works == null ? null : works.trim();
    }

	public String getIsShowStr() {
		return isShowStr;
	}

	public void setIsShowStr(String isShowStr) {
		this.isShowStr = isShowStr;
	}
}