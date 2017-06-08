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
package cn.gyyx.action.ui.wish11th;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.wish11th.Constants;
import cn.gyyx.action.service.wish11th.Wish11thLightService;
import cn.gyyx.action.ui.BaseController;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * <p>
 * 许愿层相关接口
 * </p>
 * 
 * @author tanjunkai
 * @since 0.0.1
 */
@Controller
@RequestMapping("/wish11th")
public class Wish11thLightController extends BaseController {

    // 日志
    private static final Logger logger = GYYXLoggerFactory
            .getLogger(Wish11thLightController.class);
    // 亮灯层相关服务
    private Wish11thLightService lightService = new Wish11thLightService();

    /**
     * 
     * <p>
     * 获取蛋糕所有层及每层的亮灯状态
     * </p>
     *
     * @action tanjunkai 2017年4月1日 下午4:39:09 描述
     *
     * @return ResultBean<List<Map<String,Integer>>>
     */
    @RequestMapping(value = "/lightstatus", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<List<Map<String, Integer>>> getLightStatus() {
        logger.info(Constants.ACTIVITY_NAME + "access 获取蛋糕所有层及每层的亮灯状态");
        return lightService.getLightStatus();
    }

    /**
     * <p>
     * 获取当前可许愿层数
     * </p>
     *
     * @action tanjunkai 2017年4月1日 下午5:09:13 描述
     *
     * @return ResultBean<List<Integer>>
     */
    @RequestMapping(value = "/lightfloor", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<List<Integer>> getLightFloor() {
        logger.info(Constants.ACTIVITY_NAME + "access  获取当前可许愿层数");
        return lightService.getLightFloor();
    }
}
