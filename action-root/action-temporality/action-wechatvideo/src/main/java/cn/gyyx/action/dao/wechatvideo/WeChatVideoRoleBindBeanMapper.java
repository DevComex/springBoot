/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 *
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：
 * @作者：tanjunkai        
 * @联系方式：tanjunkai@gyyx.cn
 * @创建时间：2017/3/14 14:48
 * @版本号：0.0.1 -------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.wechatvideo;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.wechatvideo.WeChatVideoRoleBindBean;

/**
 * <p>
 * 角色绑定接口
 * </p>
 * 
 * @author tanjunkai
 * @since 0.0.1
 */
public interface WeChatVideoRoleBindBeanMapper {

    /**
     * <p>
     * 新增角色绑定信息
     * </p>
     *
     * @action tanjunkai 2017年3月15日 下午5:48:15 描述
     *
     * @param record
     *            角色绑定实体
     * @return int 影响的行数
     */
    int insert(WeChatVideoRoleBindBean record);

    /**
     * <p>
     * 添加角色绑定信息
     * </p>
     *
     * @action tanjunkai 2017年3月15日 下午5:46:38 描述
     *
     * @param record
     *            角色绑定实体
     * @return int 影响行数
     */
    int insertSelective(WeChatVideoRoleBindBean record);

    /**
     * <p>
     * 通过主键获取角色绑定信息
     * </p>
     *
     * @action tanjunkai 2017年3月15日 下午5:48:51 描述
     *
     * @param code
     *            角色绑定表主键
     * @return WeChatVideoRoleBindBean 角色绑定实体
     */
    WeChatVideoRoleBindBean selectByPrimaryKey(Integer code);

    /**
     * <p>
     * 更新角色绑定信息
     * </p>
     *
     * @action tanjunkai 2017年3月15日 下午5:49:27 描述
     *
     * @param record
     *            角色绑定信息
     * @return int 影响的行数
     */
    int updateByPrimaryKeySelective(WeChatVideoRoleBindBean record);

    /**
     * <p>
     * 更新角色绑定信息
     * </p>
     *
     * @action tanjunkai 2017年3月15日 下午5:49:27 描述
     *
     * @param record
     *            角色绑定信息
     * @return int 影响的行数
     */
    int updateByPrimaryKey(WeChatVideoRoleBindBean record);

    /**
     * <p>
     * 通过UserId获取角色绑定信息
     * </p>
     *
     * @action tanjunkai 2017年3月15日 下午5:45:44 描述
     *
     * @param userId
     * @return WeChatVideoRoleBindBean
     */
    WeChatVideoRoleBindBean selectByUserId(@Param("userId") Integer userId);

    /**
     * <p>
     * 根据UserId或roleId获取角色绑定信息
     * </p>
     *
     * @action tanjunkai 2017年3月15日 下午5:46:04 描述
     *
     * @param userId
     *            用户ID
     * @param roleId
     *            角色ID
     * @return WeChatVideoRoleBindBean 角色绑定信息
     */
    WeChatVideoRoleBindBean selectById(@Param("userId") Integer userId,
            @Param("roleId") String roleId);
    
    
    /**
      * <p>
      *    根据openId获取用户角色绑定信息
      * </p>
      *
      * @action
      *    Administrator 2017年3月17日 上午11:38:39 描述
      *
      * @param openId
      * @return WeChatVideoRoleBindBean
      */
    WeChatVideoRoleBindBean selectByOpenId(@Param("openId")String openId);
    
    
    /**
      * <p>
      *    给用户增加抽奖机会
      * </p>
      *
      * @action
      *    tanjunkai 2017年3月29日 下午6:32:59 描述
      *
      * @param userId
      * @return int
      */
    int addLotteryTimes(@Param("userId")int userId);
    
    /**
     * <p>
     *    减少用户抽奖机会
     * </p>
     *
     * @action
     *    tanjunkai 2017年3月29日 下午6:32:59 描述
     *
     * @param userId
     * @return int
     */
   int reduceLotteryTimes(@Param("userId")int userId);
}