package cn.gyyx.playwd.dao.playwd;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.playwd.beans.playwd.MessageBean;

public interface MessageDao {

    int insert(MessageBean record);

    MessageBean selectByCode(Integer code);
    
    /**
     * 根据用户获取编辑回复列表
     * @param userId
     * @param startCode
     * @param pageSize
     * @return
     */
    List<MessageBean>selectMessageList(@Param("userId")int userId,@Param("startCode")int startCode,@Param("pageSize")int pageSize);
    
    /**
     * 根据用户获取编辑回复数量
     * @param userId
     * @return
     */
    int selectMessageCount(@Param("userId")int userId);
    
    /**
     * 根据分类和消息分类获取消息
     * @param contentType
     * @param contentId
     * @param messageType
     * @return
     */
    MessageBean selectMessage(@Param("contentType") String contentType,@Param("contentId") int contentId,@Param("messageType") String messageType);
    
    /**
     * 我的消息列表
     * @param userId
     * @return
     */
    List<MessageBean>selectMyMessageList(@Param("userId")int userId,@Param("startCode")int startCode,@Param("pageSize")int pageSize);
    
    /**
     * 我的消息数量
     * @param userId
     * @return
     */
    int selectMyMessageCount(@Param("userId")int userId);
}