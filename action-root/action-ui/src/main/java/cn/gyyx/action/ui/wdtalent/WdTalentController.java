package cn.gyyx.action.ui.wdtalent;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

@Controller
@RequestMapping(value = "/wdTalent")
public class WdTalentController {
    private static final Logger LOG = GYYXLoggerFactory
            .getLogger(WdTalentController.class);
    
   
    @RequestMapping(value = "/getTalentStatus", method = RequestMethod.POST)
    public @ResponseBody ResultBean<String> getTalentStatus() {
        return new ResultBean<>(false, "谢谢参与，活动已结束", null);
    }

    @RequestMapping(value = "/bindTalentInfo", method = RequestMethod.POST)
    public @ResponseBody ResultBean<String> bindTalentInfo() {
        return new ResultBean<>(false, "谢谢参与，活动已结束", null);
    }

    @RequestMapping(value = "/getSelfWorks", method = RequestMethod.POST)
    public @ResponseBody ResultBean<String> getSelfWorks() {
        return new ResultBean<>(false, "谢谢参与，活动已结束", null);
    }

    @RequestMapping(value = "/getWorksPageData", method = RequestMethod.POST)
    public @ResponseBody ResultBean<Map<String, Object>> getNewest() {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("total", 0);
        dataMap.put("pageData", null);
        return new ResultBean<Map<String,Object>>(true, "活动已结束", dataMap);

    }

    @RequestMapping(value = "/uploadWorks", method = RequestMethod.POST)
    public @ResponseBody ResultBean<String> uploadWorks() {
        return new ResultBean<>(false, "谢谢参与，活动已结束", null);
    }

    @RequestMapping(value = "/vote", method = RequestMethod.POST)
    public @ResponseBody ResultBean<String> vote() {
        return new ResultBean<>(false, "谢谢参与，活动已结束", null);
    }

    @RequestMapping(value = "/uploadComment", method = RequestMethod.POST)
    public @ResponseBody ResultBean<String> uploadComment() {
        return new ResultBean<>(false, "谢谢参与，活动已结束", null);
    }

    @RequestMapping(value = "/getComment", method = RequestMethod.POST)
    public @ResponseBody ResultBean<String> getComment() {
        return new ResultBean<>(false, "谢谢参与，活动已结束", null);
    }

    /***
     * 获取角色信息
     * 
     * @param request
     * @param response
     * @param serverId
     * @param captcha
     * @return
     */
    @RequestMapping(value = "/getRoleInfo", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> getRoleInfo() {
        return new ResultBean<>(false, "谢谢参与，活动已结束", null);
    }

    @ExceptionHandler({ BindException.class })
    @ResponseBody
    public ResultBean<Integer> exception(BindException e) {
        ResultBean<Integer> bandResultBean = new ResultBean<Integer>();
        bandResultBean.setSuccess(false);
        BindingResult bindingRes = e.getBindingResult();
        FieldError fr = bindingRes.getFieldError();
        bandResultBean.setMessage("数据验证错误:" + fr.getDefaultMessage());
        LOG.info("BindException:" + e);
        return bandResultBean;
    }

    @RequestMapping(value = "/index")
    public String index() {
        return "wdTalent/index";
    }

    @RequestMapping(value = "/info")
    public String info() {
        return "wdTalent/info";
    }

    @RequestMapping(value = "/list")
    public String list() {
        return "wdTalent/list";
    }
}