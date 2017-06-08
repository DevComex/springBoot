/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 *
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：
 * @作者：tanjunkai        
 * @联系方式：tanjunkai@gyyx.cn
 * @创建时间：2017/4/1 13:48
 * @版本号：0.0.1 -------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.wish11th;

import org.apache.ibatis.annotations.Param;
import cn.gyyx.action.beans.wish11th.Wish11thRoleBindBean;

/**
 * <p>
 * 角色绑定接口
 * </p>
 * 
 * @author tanjunkai
 * @since 0.0.1
 */
public interface Wish11thRoleBindBeanMapper {

    int insert(Wish11thRoleBindBean record);

    /**
     * <p>
     * 添加角色绑定信息
     * </p>
     *
     * @action tanjunkai 2017年4月5日 上午9:37:12 描述
     *
     * @param record
     *            角色绑定实体
     * @return int 影响的行数
     */
    int insertSelective(Wish11thRoleBindBean record);

    Wish11thRoleBindBean selectByPrimaryKey(Integer code);

    int updateByPrimaryKeySelective(Wish11thRoleBindBean record);

    int updateByPrimaryKey(Wish11thRoleBindBean record);

    /**
     * <p>
     * 通过角色获取用户绑定信息
     * </p>
     *
     * @action tanjunkai 2017年4月5日 上午9:15:11 描述
     *
     * @param roleId
     * @return Wish11thLightBean
     */
    Wish11thRoleBindBean getUserByRoleID(@Param("roleId") String roleId);

    /**
     * <p>
     * 通过用户ID获取用户绑定信息
     * </p>
     *
     * @action tanjunkai 2017年4月5日 上午9:15:11 描述
     *
     * @param userId
     * @return Wish11thLightBean
     */
    Wish11thRoleBindBean getUserByUserID(@Param("userId") Integer userId);

    /**
     * <p>
     * 更新用户消费积分
     * </p>
     *
     * @action tanjunkai 2017年4月5日 下午3:34:58 描述
     *
     * @param code
     * @param consumeScore
     * @param sqlSession
     */
    int updateUserConsumeScore(@Param("code") Integer code,
            @Param("consumescore") Integer consumescore);
}