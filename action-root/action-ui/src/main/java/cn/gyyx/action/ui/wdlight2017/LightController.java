package cn.gyyx.action.ui.wdlight2017;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import cn.gyyx.log.sdk.GYYXLoggerFactory;

@Controller
@RequestMapping("/wdlight2017light")
public class LightController {

    // 日志
    private static final Logger logger = GYYXLoggerFactory
            .getLogger(LightController.class);


    /***
     * 页面跳转
     * 
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/index ")
    public String toIndex() {
        logger.info("access 点亮2017活动首页");
        return "wdlight2017/index";
    }

    /****
     * 得到每层的亮灯状态
     * 
     * @param parm
     * @param request
     * @return
     */
    @RequestMapping("/getlighttype")
    @ResponseBody
    public JSONObject getLightType() {
        logger.info("access 得到每层的亮灯状态");
        return JSONObject.parseObject(
            "{\"isSuccess\":true,\"message\":\"获取所有灯的状态成功\","
            + "\"data\":[{\"lightType\":3,\"level\":1},{\"lightType\":"
            + "3,\"level\":2},{\"lightType\":3,\"level\":3},{\"lightType"
            + "\":3,\"level\":4}],\"rows\":null,\"total\":0,\"stateCode\":0}");
    }

    /****
     * 得到可以选择的层次
     * 
     * @param parm
     * @param request
     * @return
     */
    @RequestMapping("/getlightfloor")
    @ResponseBody
    public JSONObject getLightFloor() {
        logger.info("access 得到可以选择的层次");
        return JSONObject.parseObject(
            "{\"isSuccess\":true,\"message\":\"获取所有灯的状态成功\",\""
            + "data\":[1,2,3,4],\"rows\":null,\"total\":0,\"stateCode\":0}");
    }

}
