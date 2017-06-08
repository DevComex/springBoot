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

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.wish11th.Wish11thLightBean;

/**
 * <p>
 * 许愿配置接口
 * </p>
 * 
 * @author tanjunkai
 * @since 0.0.1
 */
public interface Wish11thLightBeanMapper {

    int insert(Wish11thLightBean record);

    int insertSelective(Wish11thLightBean record);

    Wish11thLightBean selectByPrimaryKey(Integer code);

    int updateByPrimaryKeySelective(Wish11thLightBean record);

    int updateByPrimaryKey(Wish11thLightBean record);

    /**
     * <p>
     * 获取所有的蛋糕层数
     * </p>
     *
     * @action tanjunkai 2017年4月1日 下午4:14:19 描述
     *
     * @return List<Wish11thLightBean>
     */
    List<Wish11thLightBean> getAllLights();

    /**
     * <p>
     * 通过层数获取蛋糕层
     * </p>
     *
     * @action tanjunkai 2017年4月6日 下午5:04:07 描述
     *
     * @param level
     * @return Wish11thLightBean
     */
    Wish11thLightBean getLightByLevel(@Param("level") Integer level);

    /**
     * <p>
     * 更新当前蛋糕层的点亮状态
     * </p>
     *
     * @action tanjunkai 2017年4月5日 下午4:27:37 描述
     *
     * @param lightBean
     *            void
     */
    int updateLightType(@Param("code") Integer code,@Param("actioncode") Integer actioncode,
            @Param("lighttype") Integer lighttype);

    /**
     * <p>
     * 更新当前层的点亮人数
     * </p>
     *
     * @action tanjunkai 2017年4月7日 下午5:06:41 描述
     *
     * @param level 层
     * @param limitNum 点亮人数
     * @return int
     */
    int updateLightLimit(@Param("actioncode") Integer actioncode,@Param("level") Integer level,
            @Param("limitNum") Integer limitNum);
}