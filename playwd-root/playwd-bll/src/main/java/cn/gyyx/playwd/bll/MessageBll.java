package cn.gyyx.playwd.bll;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.gyyx.playwd.beans.playwd.MessageBean;
import cn.gyyx.playwd.beans.playwd.CategoryBean.CategoryType;
import cn.gyyx.playwd.dao.playwd.MessageDao;

@Component
public class MessageBll {
	@Autowired
	MessageDao messageDao;

	/**
	 * 插入
	 * @param bean
	 */
	public boolean add(String message,String messageType,int contentId,String contentType,int userId,String contentTitle){
		MessageBean messageBean = new MessageBean();
		messageBean.setCreateTime(new Date());
		messageBean.setIsDelete(false);
		messageBean.setContentTitle(contentTitle);
		messageBean.setMessage(message);
		messageBean.setMessageType(messageType);
		messageBean.setContentId(contentId);
		messageBean.setContentType(contentType);
		messageBean.setUserId(userId);
		return messageDao.insert(messageBean)>0;
	}

	/**
	 * 根据ID查询消息
	 * @param id
	 */
	public MessageBean getMessageById(int code) {
		return messageDao.selectByCode(code);
	}

    /**
     * 根据用户获取编辑回复列表
     * @param userId
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List<MessageBean> getMessageList(Integer userId, int pageIndex,int pageSize) {
        return messageDao.selectMessageList(userId, (pageIndex-1)*pageSize,pageSize);
    }
    
    /**
     * 根据用户获取编辑回复数量
     * @param userId
     * @return
     */
    public int getMessageCount(Integer userId) {
        return messageDao.selectMessageCount(userId);
    }
    
    /**
     * 根据分类和消息分类获取消息
     * @param contentType
     * @param contentId
     * @param messageType
     * @return
     */
    public MessageBean getMessage(String contentType,int contentId,String messageType) {
		return messageDao.selectMessage(contentType, contentId, messageType);
	}
    
    /**
     * 我的消息列表
     * @param userId
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List<MessageBean> getMyMessageList(Integer userId ,int pageIndex,int pageSize) {
		return messageDao.selectMyMessageList(userId, (pageIndex-1)*pageSize, pageSize);
	}
    
    /**
     * 我的消息数量
     * @param userId
     * @return
     */
    public int getMyMessageCount(Integer userId) {
        return messageDao.selectMyMessageCount(userId);
    }
    
    
 	/**
 	 * 获取分类描述
 	 * @param value
 	 * @return
 	 */
 	public MessageBean getMessageContentInfo(MessageBean bean) {

 		if(bean.getContentType().equals(CategoryType.article.Value())){
 			bean.setContentType("查看图文");
 			bean.setContentUrl("../article/view/"+bean.getContentId());
 			return bean;
 		}		
 		if(bean.getContentType().equals(CategoryType.manhua.Value())){
 			bean.setContentType("查看漫画");
 			bean.setContentUrl("");
 			return bean;
 		}
 		if(bean.getContentType().equals(CategoryType.picture.Value())){
 			bean.setContentType("查看图片");
 			bean.setContentUrl("http://wanwd.gyyx.cn/Picture/"+bean.getContentId());
 			return bean;
 		}
 		if(bean.getContentType().equals(CategoryType.video.Value())){
 			bean.setContentType("查看视频");
 			bean.setContentUrl("http://wanwd.gyyx.cn/Video/"+bean.getContentId());
 			return bean;
 		}
 		return bean;
 	}
}