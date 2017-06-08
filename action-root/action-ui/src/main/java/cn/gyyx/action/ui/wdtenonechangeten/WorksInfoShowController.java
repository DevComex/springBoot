/*************************************************	
   Copyright ©, 2016, GY Game
   Author: 王雷
   Created: 2016年-4月-18日
   Note:问道十周年 —— 一生换十年活动 作品展示控制器
 ************************************************/
package cn.gyyx.action.ui.wdtenonechangeten;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.wd10yearonechangten.ResultBean;
import cn.gyyx.core.DataFormat;

@Controller
@RequestMapping("/WDOneChangeTen")
public class WorksInfoShowController {

    /*----------------------------首页------------------------------*/
    /**
     * 
     * @Title: getAddress
     * @Description: TODO 传递用户地址信息
     * @param request
     * @return ResultBean<AddressBean>
     */
    @RequestMapping(value = "/getAddress")
    @ResponseBody
    public ResultBean getAddress(HttpServletRequest request) {
        ResultBean result = new ResultBean(false, "谢谢参与，活动已结束", null);

        return result;
    }

    /**
     * 
     * @Title: resetAddress
     * @Description: TODO 重置或者插入地址信息
     * @param address
     * @param request
     * @return ResultBean<Integer>
     */
    @RequestMapping(value = "/resetAddress", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Integer> resetAddress(HttpServletRequest request) {
        ResultBean<Integer> result = new ResultBean<Integer>(false,
                "谢谢参与，活动已结束", 0);

        return result;
    }

    /**
     * 
     * @Title: resetAddress
     * @Description: TODO 查询用户是否中奖
     * @param address
     * @param request
     * @return ResultBean<UserLotteryBean>
     */
    @RequestMapping(value = { "/getPrize", "/getCardCode", "/getLotteryTime",
            "/getAllLotteryInfoByUser", "/getLikeTop30" },
        produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getPrize(HttpServletRequest request) {

        ResultBean result = new ResultBean(false, "谢谢参与，活动已结束", null);

        return DataFormat.objToJson(result);
    }

    /** ----------------------列表页------------------------------------------- */
    /**
     * 详细列表初始页
     * 
     * @param code
     * @param model
     * @return
     */
    @RequestMapping(value = "/workInfoListIndex")
    public String workInfoListIndex() {

        return "wdTenOneChangeTen/list";
    }

    /**
     * 获取最新作品（分页）获取点赞数排名作品（分页）
     * 
     * @param bean
     *            type:1(按点赞) 0（按最新）
     * @return
     */
    @RequestMapping(value = "/getWorks", method = RequestMethod.GET,
        produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getWorksByDate() {

        ResultBean result = new ResultBean(false, "谢谢参与，活动已结束", null);

        return DataFormat.objToJson(result);
    }

    /*--------------------详细页---------------------------------------*/
    /**
     * 详细信息初始页
     * 
     * @param code
     * @param model
     * @return
     */
    @RequestMapping(value = "/workInfoIndex")
    public String index(Integer code, Model model) {

        return "wdTenOneChangeTen/final";
    }

    /**
     * 获取作品详细数据
     * 
     * @param code
     * @return
     */
    @RequestMapping(value = "/getWorkInfo", method = RequestMethod.GET,
        produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String showInfo(Integer code) {
        ResultBean result = new ResultBean(false, "谢谢参与，活动已结束", null);

        return DataFormat.objToJson(result);

    }

    /*--------------------公共（分享、点赞）-------------------------------*/
    /**
     * 分享作品
     * 
     * @param wdTenShareLogBean
     * @return
     */
    @RequestMapping(value = { "/share", "/likeWork" },
        method = RequestMethod.POST,
        produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String shareWork(HttpServletResponse response) {

        ResultBean<Boolean> result = new ResultBean<Boolean>(false,
                "谢谢参与，活动已结束", false);

        return DataFormat.objToJson(result);
    }

}