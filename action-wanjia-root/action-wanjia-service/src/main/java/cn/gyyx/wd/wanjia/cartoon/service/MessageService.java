package cn.gyyx.wd.wanjia.cartoon.service;
 
import java.util.Date;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gyyx.wd.wanjia.cartoon.beans.Constans;
import cn.gyyx.wd.wanjia.cartoon.beans.MessageBean;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdMessage;
import cn.gyyx.wd.wanjia.cartoon.beans.tools.MessageEnum;
import cn.gyyx.wd.wanjia.cartoon.dao.WanwdMessageMapper;
import cn.gyyx.wd.wanjia.cartoon.service.angent.InterfacePlaywdAnget;
 

@Service
public class MessageService {
	@Autowired
	private WanwdMessageMapper messageMapper;
	/**
	 * 
	 * @param userId  作者ID
	 * @param account  作者用户名
	 * @param sourceCode 审核的章节code
	 * @param messageType  消息类型  MessageEnum
	 * @param context  消息内容  须作处理 MessageEnum
	 * @return
	 */
	public boolean saveMessage(Integer userId,String contentTitle,Integer contentId,Integer messageType,String messageContext){
	        MessageBean  message = new MessageBean();
	        message.setContentTitle(contentTitle);
	        message.setContentId(contentId);
	        message.setContentType("manhua");
	        message.setMessage(messageContext);
	        message.setMessageType(getMessageType(messageType));
	        message.setUserId(userId);
	        return InterfacePlaywdAnget.insterMessage(message);
	}
	private String getMessageType(Integer messageType){
	    if(messageType==1){
	        return "fail";
            }else if(messageType==2){
                return "pass";
            }else if(messageType==3){
                return "editor";
            }else if(messageType==4){
                return "recommend";
            }
	    return "";
	}
}
