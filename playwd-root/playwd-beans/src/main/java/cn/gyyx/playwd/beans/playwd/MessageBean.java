package cn.gyyx.playwd.beans.playwd;

import java.io.Serializable;
import java.util.Date;

public class MessageBean implements Serializable {
    private Integer code;

    private Integer userId;//用户ID

    private String contentType;//消息提示中涉及到的资源类型

    private Integer contentId;//消息提示中涉及到的资源ID

    private String messageType;//消息类型

    private String message;//消息

    private Boolean isDelete;

    private Date createTime;
    
    private String contentTitle;
    
    private String contentUrl;

    public String getContentUrl() {
		return contentUrl;
	}
    
	private static final long serialVersionUID = 1L;

	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
	}

	public String getContentTitle() {
		return contentTitle;
	}

	public void setContentTitle(String contentTitle) {
		this.contentTitle = contentTitle;
	}

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

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType == null ? null : contentType.trim();
    }

    public Integer getContentId() {
        return contentId;
    }

    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType == null ? null : messageType.trim();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
	
	/**
	 * 消息类型
	 */
	public enum MessageType{
		pass("pass"),//审核通过
		fail("fail"),//审核不通过
		recommend("recommend"),//推荐
	    editor("editor");//编辑回复
		
		private String value;

		private MessageType(String value) {
			this.value = value;
		}

		public String Value() {
			return value;
		}
	}
	
	public static final String MessageType_Pass_Template = "<b>恭喜，您的{0}《{1}》已通过审核在平台发布！</b>您可以在{2}查看您的作品！感谢您对问道的支持，还请继续加油哦！";
	public static final String MessageType_Fail_Template = "<b>非常遗憾您的{0}《{1}》经审核未能通过，请务必要加油哦！</b>";
	public static final String MessageType_Recommend_Template = "<strong>恭喜，您的{0}《{1}》非常优秀，已获得<font color=\"#FF0000\"><strong>{2}</strong></font>星推荐在平台发布！</strong>您可以在{3}查看您的作品！您将会获得{4}奖励，{5}";
	public static final String MessageType_RecommendLowPrize_Template="还请继续加油哦！";
	public static final String MessageType_RecommendMediumPrize_Template="继续加油！道姐看好你哦！";
	public static final String MessageType_RecommendHighPrize_Template="道姐已经快成为你的粉丝啦！！！";
}