package cn.gyyx.action.ui.wdlight2017;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

@Controller
@RequestMapping("/wdlight2017wish")
public class WishController {

    // 日志
    private static final Logger logger = GYYXLoggerFactory
            .getLogger(WishController.class);

    /***
     * 获取指定层的祝愿信息
     * 
     * @param request
     * @param level
     * @return
     */
    @RequestMapping(value = "/getlevelwish")
    @ResponseBody
    public JSONObject getLevelComment() {
        logger.info("access 获取指定层的祝愿信息");
        return JSONObject.parseObject(
            " {\"isSuccess\":true,\"message\":\"获取许愿数据成功\",\"data\":[{\"roleName\":\"°夏沫浅雨つ\",\"content\":\"我要做托\"},"
                    + "{\"roleName\":\"卖萌小荣荣\",\"content\":\"。。\"},{\"roleName\":\"ゝ゛無殇。\",\"content\":\"我要银元宝\"},"
                    + "{\"roleName\":\"ゞ極度ゞ放纵\",\"content\":\"问道越来越好！！！！！！\"},{\"roleName\":\"卖萌小荣荣\",\"content\":\"。。。\"},"
                    + "{\"roleName\":\"╰ゝ゛無殇。\",\"content\":\"44\"},{\"roleName\":\"丫头╮已成年\",\"content\":\"给点元宝吧\"},"
                    + "{\"roleName\":\"°夏沫浅雨つ\",\"content\":\"我要银元宝\"},{\"roleName\":\"丫头╮已成年\",\"content\":\"哎\"},"
                    + "{\"roleName\":\"卖萌小荣荣\",\"content\":\"。。\"}],\"rows\":null,\"total\":7650,\"stateCode\":0}");
    }

    /***
     * 获取用户积分
     * 
     * @param request
     * @param level
     * @return
     */
    @RequestMapping(value = "/getuserconsumescore")
    @ResponseBody
    public ResultBean<Integer> getUserScore() {
        logger.info("access 获取用户消耗积分");
        return new ResultBean<>(false, "谢谢参与，活动已结束！", null);
    }

    /***
     * 用户许愿
     * 
     * @param request
     * @param level
     *            -1 您的积分不足哦！ -5 登录过期！ -6 您还没有绑定角色 -4 系统错误，请重试！ -7 活动尚未开始 -8
     *            活动已经结束
     * @return
     */
    @RequestMapping(value = "/userwish", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> getUserWish() {
        logger.info("access 用户许愿");
        return new ResultBean<>(false, "-8", null);
    }

    /***
     * 获取当前用户所有的许愿信息
     * 
     * @param request
     * @param level
     * @return
     */
    @RequestMapping(value = "/mywishall")
    @ResponseBody
    public ResultBean<Object> getMyWishAll() {
        logger.info("access 获取当前用户所有的许愿信息");
        return new ResultBean<>(false, "谢谢参与，活动已结束！", null);
    }

    /***
     * 展示所有的许愿信息
     * 
     * @param request
     * @param level
     * @return
     */
    @RequestMapping(value = "/getalllottery")
    @ResponseBody
    public JSONObject getlotteryAll() {
        logger.info("access  展示所有的许愿信息");
        return JSONObject.parseObject(
            "{\"isSuccess\":true,\"message\":\"获取所有许愿数据成功\",\"data\":["
            + "{\"roleName\":\"全村人的希望\",\"createTime\":1485407028733,\"prizeName\":\"十周年纪念币\"},"
            + "{\"roleName\":\"心有一丝痛╮\",\"createTime\":1485341134137,\"prizeName\":\"IPHONE 7\"},"
            + "{\"roleName\":\"Alone°初冬\",\"createTime\":1485559947637,\"prizeName\":\"至尊蛋蛋鸡\"},"
            + "{\"roleName\":\"天曦\",\"createTime\":1484404206007,\"prizeName\":\"蛋蛋鸡\"},"
            + "{\"roleName\":\"′﹎玖．ゝ\",\"createTime\":1485614841927,\"prizeName\":\"10000银元宝\"},"
            + "{\"roleName\":\"把十\",,\"createTime\":1485256383137,\"prizeName\":\"10000银元宝\"},"
            + "{\"roleName\":\"诠释淡忘\",\"createTime\":1485926788973,\"prizeName\":\"10000银元宝\"},"
            + "{\"roleName\":\"2017，Wm\",\"createTime\":1485437516833,\"prizeName\":\"10000银元宝\"},"
            + "{\"roleName\":\"3O36小宝\",\"createTime\":1486430385273,\"prizeName\":\"10000银元宝\"},"
            + "{\"roleName\":\"Angel灬芬达\",\"createTime\":1486432775813,\"prizeName\":\"10000银元宝\"},"
            + "{\"roleName\":\"Angel灬脉动\",\"createTime\":1486432608960,\"prizeName\":\"8000银元宝\"},"
            + "{\"roleName\":\"昨夜、烟雨楼\",\"createTime\":1486434586587,\"prizeName\":\"8000银元宝\"},"
            + "{\"roleName\":\"︶ㄣoо老白\",\"createTime\":1486387467487,\"prizeName\":\"8000银元宝\"},"
            + "{\"roleName\":\"China灬体木\",\"createTime\":1486441890727,\"prizeName\":\"8000银元宝\"},"
            + "{\"roleName\":\"限量版、菲菲\",\"createTime\":1486442288127,\"prizeName\":\"8000银元宝\"},"
            + "{\"roleName\":\"魅影そ妖姬\",\"createTime\":1486459014530,\"prizeName\":\"8000银元宝\"},"
            + "{\"roleName\":\"一抹〃黄\",\"createTime\":1486467110543,\"prizeName\":\"8000银元宝\"},"
            + "{\"roleName\":\"曹青衣丶\",\"createTime\":1486471137817,\"prizeName\":\"8000银元宝\"},"
            + "{\"roleName\":\"乄、Misterゝ\",\"createTime\":1486483107397,\"prizeName\":\"8000银元宝\"},"
            + "{\"roleName\":\"嵿尖ㄨ十速熊\",\"createTime\":1486490496237,\"prizeName\":\"8000银元宝\"},"
            + "],\"rows\":null,\"total\":63479,\"stateCode\":0}");
    }

}
