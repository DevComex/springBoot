/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 *
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：
 * @作者：tanjunkai        
 * @联系方式：tanjunkai@gyyx.cn
 * @创建时间：2017/4/5 10:16
 * @版本号：0.0.1 -------------------------------------------------------------------------
 */
package cn.gyyx.action.bll.wish11th;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import cn.gyyx.action.beans.wish11th.Wish11thWishBean;
import cn.gyyx.action.beans.wish11th.Wish11thWishInfoBean;
import cn.gyyx.action.dao.wish11th.Wish11thWishDao;

/**
 * <p>
 * 许愿相关业务逻辑层
 * </p>
 * 
 * @author tanjunkai
 * @since 0.0.1
 */
public class Wish11thWishBll {
    // 许愿相关数据访问层
    Wish11thWishDao wishDao = new Wish11thWishDao();

    /**
     * <p>
     * 获取用户所有愿望
     * </p>
     *
     * @action tanjunkai 2017年4月5日 上午10:20:56 描述
     *
     * @param userId
     * @return List<Wish11thWishBean>
     */
    public List<Wish11thWishBean> getMyWishAll(int userId) {
        return wishDao.getMyWishAll(userId);
    }

    /**
     * <p>
     * 获取指定层的许愿列表
     * </p>
     *
     * @action tanjunkai 2017年4月5日 上午11:44:26 描述
     *
     * @param level
     *            层级
     * @return List<Wish11thWishBean> 许愿列表
     */
    public List<Wish11thWishBean> getWishsBylevel(int level, int status) {
        return wishDao.getWishsBylevel(level, status);
    }

    /**
     * <p>
     * 获取指定层的前N条许愿列表
     * </p>
     *
     * @action tanjunkai 2017年4月5日 上午11:44:26 描述
     *
     * @param level
     *            层级
     * @return List<Wish11thWishBean> 许愿列表
     */
    public List<Wish11thWishBean> getTopWishsBylevel(int topCount, int level,
            int status) {
        return wishDao.getTopWishsBylevel(topCount, level, status);
    }

    /**
     * <p>
     * 获取当前层数所有许愿通过的人数
     * </p>
     *
     * @action tanjunkai 2017年4月5日 上午11:48:55 描述
     *
     * @param level
     *            层级
     * @return int 获取当前层数所有许愿通过的人数
     */
    public int getWishUserCountByLevel(int level, int status) {
        return wishDao.getWishUserCountByLevel(level, status);
    }
    
    
    /**
      * <p>
      *   获取当前层指定状态的的许愿或抽奖次数(未过滤user)
      * </p>
      *
      * @action
      *    tanjunkai 2017年4月8日 下午3:35:53 描述
      *
      * @param level  层
      * @param status 状态
      * @return int   
      */
    public int getWishCountByLevel(int level,int status)
    {
        return wishDao.getWishCountByLevel(level,status);
    }

    /**
     * <p>
     * 获取用户在当前层的许愿次数
     * </p>
     *
     * @action tanjunkai 2017年4月5日 下午2:29:36 描述
     *
     * @param userId
     * @param level
     * @return int
     */
    public int getUserWishNumByLevel(int userId, int level) {
        return wishDao.getUserWishNumByLevel(userId, level);
    }

    /**
     * <p>
     * 添加许愿
     * </p>
     *
     * @action tanjunkai 2017年4月5日 下午3:46:53 描述
     *
     * @param bean
     *            void
     */
    public int addWish(Wish11thWishBean bean) {
       return wishDao.addWish(bean);
    }

    /**
     * <p>
     * 获取所有许愿并中大奖的许愿信息
     * </p>
     *
     * @action tanjunkai 2017年4月5日 下午5:59:47 描述
     *
     * @param num
     *            前num条
     * @return List<Wish11thWishBean>
     */
    public List<Wish11thWishInfoBean> findWishAll(int num) {
        return wishDao.findWishAll(num);
    }

    /**
     * <p>
     * 获取所有许愿获奖的数量
     * </p>
     *
     * @action tanjunkai 2017年4月5日 下午7:02:04 描述
     *
     * @return int 数量
     */
    public int getAllWishCount() {
        return wishDao.getAllWishCount();
    }

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
    public int wishAudit(String codes, int status) {
        int result = 0;
        for (String code : codes.split(",")) {
            result = wishDao.wishAudit(code, status);
        }
        return result;
    }

    /**
     * <p>
     * 通过条件获取许愿列表
     * </p>
     *
     * @action tanjunkai 2017年4月7日 下午9:11:41 描述
     *
     * @param beginTime
     *            许愿时间
     * @param endTime
     *            许愿时间
     * @param roleName
     *            角色名
     * @param checkStatus
     *            审核窗台
     * @param pageSize
     *            每页显示的条数
     * @param pageNo
     *            页码
     * @return List<Wish11thWishBean>
     */
    public List<Wish11thWishBean> getWishList(String beginTime, String endTime,
            String roleName, String checkStatus, int pageSize, int pageNo) {
        return wishDao.getWishList(beginTime, endTime, roleName, checkStatus,
            pageSize, pageNo);
    }

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
    public int getWishListCount(String beginTime, String endTime,
            String roleName, String checkStatus) {
        return wishDao.getWishListCount(beginTime, endTime, roleName,
            checkStatus);
    }
    
    /**
      * <p>
      *    判断用户是否中过实物奖
      * </p>
      *
      * @action
      *    tanjunkai 2017年4月12日 下午5:03:05 描述
      *
      * @param userId 用户ID
      * @return Boolean
      */
    public Boolean isLotteryRealPrize(int userId)
    {
        Wish11thWishBean bean= wishDao.getLotteryRealPrize(userId);
        if(null!=bean)
            return true;
        else
            return false;
    }
    
    /**
      * <p>
      *    获取我的实物中奖纪录
      * </p>
      *
      * @action
      *    tanjunkai 2017年4月12日 下午5:17:57 描述
      *
      * @param userId 用户ID
      * @return Wish11thWishBean
      */
    public Wish11thWishBean getMyRealPrize(int userId)
    {
        return wishDao.getLotteryRealPrize(userId);
    }
}
