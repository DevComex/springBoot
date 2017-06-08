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
package cn.gyyx.action.bll.wish11th;

import java.util.List;
import cn.gyyx.action.beans.wish11th.Wish11thLightBean;
import cn.gyyx.action.dao.wish11th.Wish11thLightDao;

/**
 * <p>
 * 蛋糕层数相关业务逻辑
 * </p>
 * 
 * @author tanjunkai
 * @since 0.0.1
 */
public class Wish11thLightBll {

    // 蛋糕层数相关数据访问层
    Wish11thLightDao lightDao = new Wish11thLightDao();

    /**
     * <p>
     * 获取蛋糕的所有层数信息
     * </p>
     *
     * @action tanjunkai 2017年4月1日 下午4:15:16 描述
     *
     * @return List<Wish11thLightBean>
     */
    public List<Wish11thLightBean> getAllLights() {
        return lightDao.getAllLights();
    }


    /**
      * <p>
      *   通过层数获取蛋糕层
      * </p>
      *
      * @action
      *    tanjunkai 2017年4月8日 上午10:03:46 描述
      *
      * @param actionCode 活动ID
      * @param level      许愿层
      * @return Wish11thLightBean
      */
    public Wish11thLightBean getLightByLevel(int level) {
        return lightDao.getLightByLevel(level);
    }

    /**
     * <p>
     * 获取当前蛋糕层应该的状态
     * </p>
     *
     * @action tanjunkai 2017年4月5日 下午4:10:59 描述
     *
     * @param limitNum
     *            点亮人数
     * @param wishNum
     *            实际许愿人数
     * @return int
     */
    public int getCurrentLightType(int limitNum, int wishNum) {
        if (wishNum >= limitNum) { // X>=600 全亮
            return 3;
        } else if (wishNum >= limitNum / 3 * 2) { // 400<=x<=599 三分之二亮
            return 2;
        } else if (wishNum >= limitNum / 3 * 1) { // 200<=x<=399 三分之一亮
            return 1;
        } else {
            return 0; // 0<x<=199 全暗
        }
    }

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
    public int updateLightType(int code,int actionCode,int lightType) {
        return lightDao.updateLightType(code,actionCode ,lightType);
    }

    /**
     * <p>
     * 更新层点亮人数
     * </p>
     *
     * @action tanjunkai 2017年4月7日 下午5:11:52 描述
     *
     * @param level
     *            层级
     * @param limitNum
     *            点亮人数
     * @return int
     */
    public int updateLightLimitNum(int actionCode,int level, int limitNum) {
        return lightDao.updateLightLimitNum(actionCode,level, limitNum);
    }
}
