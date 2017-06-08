package cn.gyyx.wd.wanjia.cartoon.beans;

import java.io.Serializable;
import java.util.Date;
/**
 * 
  * <p>
  *   新版message
  * </p>
  *  
  * @author lihu
  * @since 0.0.1
 */
public class MessageBean implements Serializable {

    private Integer userId;//用户ID

    private String contentType;//消息提示中涉及到的资源类型

    private Integer contentId;//消息提示中涉及到的资源ID

    private String messageType;//消息类型

    private String message;//消息
    
    private String contentTitle;
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
        this.contentType = contentType;
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
        this.messageType = messageType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getContentTitle() {
        return contentTitle;
    }

    public void setContentTitle(String contentTitle) {
        this.contentTitle = contentTitle;
    }

    
 
}