package cn.gyyx.action.ui.wdlight2017;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

@Controller
@RequestMapping("/wdlight2017userinfo")
public class UserinfoController {

    // 日志
    private static final Logger logger = GYYXLoggerFactory
            .getLogger(UserinfoController.class);
    private static final ResultBean<String> ACTIVITY_END = new ResultBean<>(
            false, "谢谢参与，活动已结束！", null);

    /***
     * 获取角色信息
     * 
     * @param request
     * @param response
     * @param serverId
     * @param veCode
     * @return
     */
    @RequestMapping(value = "/getroleinfo", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> changeRoleInfo() {
        return ACTIVITY_END;
    }

    /****
     * 增加账户角色绑定信息
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/binduserinfo", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> addAccountRole() {
        return ACTIVITY_END;
    }

    /***
     * 得到账户的角色信息
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/getuserinfo")
    @ResponseBody
    public ResultBean<String> getAccountRoleBeanByUserCode() {
        logger.info("access 得到账户的角色信息");
        return ACTIVITY_END;
    }

    /**
     * 
     * @Title: getAddress 传递用户地址信息
     * @param request
     * @return ResultBean<AddressBean>
     */
    @RequestMapping(value = "/getAddress")
    @ResponseBody
    public ResultBean<String> getAddress() {
        logger.info("access 传递用户地址信息");
        return ACTIVITY_END;
    }

    /**
     * 
     * @Title: editAddress 修改用户地址信息
     * @param request
     * @return ResultBean<AddressBean>
     */
    @RequestMapping(value = "/editAddress", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> addAddress() {
        logger.info("access 修改用户地址信息");
        return ACTIVITY_END;
    }

}
