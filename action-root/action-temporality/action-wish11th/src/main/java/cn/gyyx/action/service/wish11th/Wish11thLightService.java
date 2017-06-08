/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 *
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：
 * @作者：tanjunkai        
 * @联系方式：tanjunkai@gyyx.cn
 * @创建时间：2017/4/1 15:48
 * @版本号：0.0.1 -------------------------------------------------------------------------
 */
package cn.gyyx.action.service.wish11th;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import com.google.common.base.Throwables;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.wish11th.Constants;
import cn.gyyx.action.beans.wish11th.Wish11thLightBean;
import cn.gyyx.action.bll.wish11th.Wish11thLightBll;
import cn.gyyx.action.bll.wish11th.Wish11thWishBll;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * <p>
 * 蛋糕层数相关服务类
 * </p>
 * 
 * @author tanjunkai
 * @since 0.0.1
 */
public class Wish11thLightService {

    // 蛋糕层数相关业务逻辑类
    Wish11thLightBll lightBll = new Wish11thLightBll();
    // 许愿相关业务逻辑
    Wish11thWishBll wishBll = new Wish11thWishBll();
    // 日志访问类
    private static final Logger logger = GYYXLoggerFactory
            .getLogger(Wish11thLightService.class);

    /**
     * <p>
     * 获取蛋糕的所有层数及点亮状态信息
     * </p>
     *
     * @action tanjunkai 2017年4月1日 下午4:26:06 描述
     *
     * @return ResultBean<List<Map<String,Integer>>>
     */
    public ResultBean<List<Map<String, Integer>>> getLightStatus() {
        ResultBean<List<Map<String, Integer>>> resultBean = new ResultBean<>();
        try {
            List<Wish11thLightBean> lights = lightBll.getAllLights();
            List<Map<String, Integer>> list = new ArrayList<>();
            for (Wish11thLightBean light : lights) {
                Map<String, Integer> data = new HashMap<>();
                data.put("level", light.getLevel());
                data.put("lightType", light.getLightType());
                list.add(data);
            }
            resultBean.setData(list);
            resultBean.setIsSuccess(true);
            resultBean.setMessage("获取蛋糕所有层点亮状态成功");
        } catch (Exception e) {
            logger.error(Constants.ERROR_LOG + "获取蛋糕所有层点亮状态失败！异常：{}",
                Throwables.getStackTraceAsString(e));
            resultBean.setIsSuccess(false);
            resultBean.setMessage("获取蛋糕所有层点亮状态失败");
        }
        return resultBean;
    }

    /**
     * <p>
     * 获取当前可许愿层数
     * </p>
     *
     * @action tanjunkai 2017年4月1日 下午5:07:13 描述
     *
     * @return ResultBean<List<Integer>>
     */
    public ResultBean<List<Integer>> getLightFloor() {
        ResultBean<List<Integer>> resultBean = new ResultBean<>();
        try {
            List<Wish11thLightBean> lights = lightBll.getAllLights();
            List<Integer> list = new ArrayList<>();

            Wish11thLightBean light1 = null;
            Wish11thLightBean light2 = null;
            Wish11thLightBean light3 = null;
            Wish11thLightBean light4 = null;
            Wish11thLightBean light5 = null;
            Wish11thLightBean light6 = null;
            for (Wish11thLightBean light : lights) {
                if (light.getLevel() == 1)
                    light1 = light;
                else if (light.getLevel() == 2)
                    light2 = light;
                else if (light.getLevel() == 3)
                    light3 = light;
                else if (light.getLevel() == 4)
                    light4 = light;
                else if (light.getLevel() == 5)
                    light5 = light;
                else if (light.getLevel() == 6)
                    light6 = light;
            }
            if (light1 != null)
                list.add(1);
            if (null != light1 && light1.getLightType() == 3)
                list.add(2);
            if (null != light2 && light2.getLightType() == 3)
                list.add(3);
            if (null != light3 && light3.getLightType() == 3)
                list.add(4);
            if (null != light4 && light4.getLightType() == 3)
                list.add(5);
            if (null != light5 && light5.getLightType() == 3)
                list.add(6);

            resultBean.setData(list);
            resultBean.setIsSuccess(true);
            resultBean.setMessage("获取可许愿层数成功");
        } catch (Exception e) {
            logger.warn("getLightFloor:", e);
            logger.error(Constants.ERROR_LOG + "获取可许愿层数失败！异常：{}",
                Throwables.getStackTraceAsString(e));
            resultBean.setIsSuccess(false);
            resultBean.setMessage("获取可许愿层数失败");
        }
        return resultBean;
    }

    /**
      * <p>
      *    修改许愿层点亮人数
      * </p>
      *
      * @action
      *    tanjunkai 2017年4月8日 下午3:12:01 描述
      *
      * @param level 层
      * @param limitNum 点亮人数
      * @return ResultBean<Object>
      */
    public ResultBean<Object> modifyLightNum(int level, int limitNum) {
        ResultBean<Object> resultBean = new ResultBean<>();
        int actionCode = Constants.Wish11thMapperActionCode
                .getActionCode(level);
        // 获取当前蛋糕层的信息
        Wish11thLightBean light = lightBll.getLightByLevel(level);
        if (null == light) {
            resultBean.setProperties(false, "当前层不存在", null);
            return resultBean;
        }
        // 限制修改点亮人数必须是3的倍数的正整数
        if (limitNum % 3 != 0 || limitNum < 0) {
            logger.info("修改人数必须是3的倍数的正整数:{}", limitNum);
            resultBean.setProperties(false, "修改人数必须是3的倍数的正整数", null);
            return resultBean;
        }
        if (light.getLightType() >= 3) {
            logger.info("该层已经是点亮状态，不能修改人数:{}", light.getLightType());
            resultBean.setProperties(false, "该层已经是点亮状态，不能修改人数", null);
            return resultBean;
        }
        // 获取当前层的许愿人数
        int wishCount = wishBll.getWishUserCountByLevel(level,
            Constants.WISH11TH_ALLSTATUS);
        if (limitNum < wishCount) {
            logger.info("修改人数不能小于当前该层已许愿人数:oldNum{},newNum{}", wishCount,
                limitNum);
            resultBean.setProperties(false, "修改人数小于或等于该层原有人数,不能修改人数", null);
            return resultBean;
        }
        //更新当前层点亮人数
        int result = lightBll.updateLightLimitNum(light.getActionCode(),level, limitNum);
        logger.debug("修改人数影响行数:{}", result);
        if (result > 0) {
            resultBean.setProperties(true, "修改人数成功", null);
            return resultBean;
        } else {
            resultBean.setProperties(false, "修改人数失败", null);
            return resultBean;
        }
    }
}
