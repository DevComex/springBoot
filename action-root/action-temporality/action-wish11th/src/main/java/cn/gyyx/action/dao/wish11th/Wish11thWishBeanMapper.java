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

import cn.gyyx.action.beans.wish11th.Wish11thWishBean;
import cn.gyyx.action.beans.wish11th.Wish11thWishInfoBean;

/**
 * <p>
 * 许愿接口
 * </p>
 * 
 * @author tanjunkai
 * @since 0.0.1
 */
public interface Wish11thWishBeanMapper {

    int insert(Wish11thWishBean record);

    /**
     * <p>
     * 添加许愿
     * </p>
     *
     * @action tanjunkai 2017年4月5日 下午3:49:12 描述
     *
     * @param record
     * @return int
     */
    int insertSelective(Wish11thWishBean record);

    Wish11thWishBean selectByPrimaryKey(Integer code);

    int updateByPrimaryKeySelective(Wish11thWishBean record);

    int updateByPrimaryKey(Wish11thWishBean record);

    /**
     * <p>
     * 根据用户ID获取用户所有许愿内容
     * </p>
     *
     * @action tanjunkai 2017年4月5日 上午10:29:08 描述
     *
     * @param userId
     *            用户ID
     * @return List<Wish11thWishBean> 许愿列表
     */
    List<Wish11thWishBean> getMyWishAll(@Param("userId") Integer userId);

    /**
     * <p>
     * 获取指定层的许愿状态列表
     * </p>
     *
     * @action tanjunkai 2017年4月5日 上午11:42:56 描述
     *
     * @param level
     * @return List<Wish11thWishBean>
     */
    List<Wish11thWishBean> getWishsBylevel(@Param("level") Integer level,
            @Param("status") Integer status);

    /**
     * <p>
     * 获取指定层的前N条许愿状态列表
     * </p>
     *
     * @action tanjunkai 2017年4月5日 上午11:42:56 描述
     *
     * @param level
     * @return List<Wish11thWishBean>
     */
    List<Wish11thWishBean> getTopWishsBylevel(@Param("topnum") Integer topnum,
            @Param("level") Integer level, @Param("status") Integer status);

    /**
     * <p>
     * 获取当前层数所有许愿通过的人数
     * </p>
     *
     * @action tanjunkai 2017年4月5日 上午11:47:38 描述
     *
     * @param level
     *            层级
     * @return int 许愿通过的人数
     */
    public int getWishUserCountByLevel(@Param("level") Integer level,
            @Param("status") Integer status);

    /**
     * <p>
     * 获取当前层指定状态的的许愿或抽奖次数(未过滤user)
     * </p>
     *
     * @action tanjunkai 2017年4月8日 下午3:35:53 描述
     *
     * @param level
     *            层
     * @param status
     *            状态
     * @return int
     */
    public int getWishCountByLevel(@Param("level") Integer level,
            @Param("status") Integer status);
    
    /**
     * <p>
     * 获取用户在当前层的许愿次数
     * </p>
     *
     * @action tanjunkai 2017年4月5日 下午2:28:24 描述
     *
     * @param userId
     * @param level
     * @return int
     */
    int getUserWishNumByLevel(@Param("userId") Integer userId,
            @Param("level") Integer level);

    /**
     * <p>
     * 获取所有许愿并中大奖的许愿信息
     * </p>
     *
     * @action tanjunkai 2017年4月5日 下午5:56:30 描述
     *
     * @param num
     * @return List<Wish11thWishBean>
     */
    List<Wish11thWishInfoBean> findWishAll(@Param("num") Integer num);

    /**
     * <p>
     * 获取所有许愿获奖的数量
     * </p>
     *
     * @action tanjunkai 2017年4月5日 下午7:02:04 描述
     *
     * @return int
     */
    int getAllWishCount();

    /**
     * <p>
     * 审核许愿内容
     * </p>
     *
     * @action tanjunkai 2017年4月7日 下午8:11:41 描述
     *
     * @param code
     *            主键
     * @param status
     *            状态
     * @return int
     */
    int wishAudit(@Param("code") String code, @Param("status") int status);

    /**
     * <p>
     * 获取许愿列表(oa 搜索)
     * </p>
     *
     * @action tanjunkai 2017年4月7日 下午8:45:02 描述
     *
     * @param beginTime
     *            许愿时间
     * @param endTime
     *            许愿时间
     * @param rolename
     *            角色名
     * @param verifyStatus
     *            审核状态
     * @param pageSize
     * @param pageNo
     * @return List<Wish11thWishBean>
     */
    List<Wish11thWishBean> getWishList(@Param("beginTime") String beginTime,
            @Param("endTime") String endTime,
            @Param("rolename") String rolename, @Param("status") String status,
            @Param("pageSize") int pageSize, @Param("pageNo") int pageNo);

    /**
     * <p>
     * 通过条件获取许愿列表数量
     * </p>
     *
     * @action tanjunkai 2017年4月7日 下午9:13:16 描述
     *
     * @param beginTime
     *            许愿时间
     * @param endTime
     *            许愿时间
     * @param roleName
     *            角色名
     * @param checkStatus
     *            审核状态
     * @return int 列表条数
     */
    int getWishListCount(@Param("beginTime") String beginTime,
            @Param("endTime") String endTime,
            @Param("rolename") String rolename, @Param("status") String status);
    
    
    /**
      * <p>
      *    获取用户中的实物奖列表
      * </p>
      *
      * @action
      *    tanjunkai 2017年4月12日 下午4:57:14 描述
      *
      * @param userId
      * @return List<Wish11thWishBean>
      */
      Wish11thWishBean getLotteryRealPrize(@Param("userId") Integer userId);
}