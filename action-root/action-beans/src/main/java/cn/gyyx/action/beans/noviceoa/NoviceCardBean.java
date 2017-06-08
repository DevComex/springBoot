package cn.gyyx.action.beans.noviceoa;

import java.util.Date;

/**
 * 新手卡实体
 * @author tanjunkai
 *
 */
public class NoviceCardBean {
	 private Integer code;

	    private String cardNum;

	    private String description;

	    private Integer giftId;

	    private Integer status;

	    private Integer taskId;
	    
	    private Date updateTime;

	    private Date createTime;

	    public NoviceCardBean(Integer code, String cardNum, String description, Integer giftId, Integer status, Date updateTime, Date createTime) {
	        this.code = code;
	        this.cardNum = cardNum;
	        this.description = description;
	        this.giftId = giftId;
	        this.status = status;
	        this.updateTime = updateTime;
	        this.createTime = createTime;
	    }

	    public NoviceCardBean() {
	        super();
	    }

	    public Integer getCode() {
	        return code;
	    }

	    public void setCode(Integer code) {
	        this.code = code;
	    }

	    public String getCardNum() {
	        return cardNum;
	    }

	    public void setCardNum(String cardNum) {
	        this.cardNum = cardNum == null ? null : cardNum.trim();
	    }

	    public String getDescription() {
	        return description;
	    }

	    public void setDescription(String description) {
	        this.description = description == null ? null : description.trim();
	    }

	    public Integer getGiftId() {
	        return giftId;
	    }

	    public void setGiftCode(Integer giftId) {
	        this.giftId =giftId;
	    }

	    public Integer getStatus() {
	        return status;
	    }

	    public void setStatus(Integer status) {
	        this.status = status;
	    }

	    public Integer getTaskId() {
	        return taskId;
	    }

	    public void setTaskId(Integer taskId) {
	        this.taskId = taskId;
	    }
	    
	    public Date getUpdateTime() {
	        return updateTime;
	    }

	    public void setUpdateTime(Date updateTime) {
	        this.updateTime = updateTime;
	    }

	    public Date getCreateTime() {
	        return createTime;
	    }

	    public void setCreateTime(Date createTime) {
	        this.createTime = createTime;
	    }
}
