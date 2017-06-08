package cn.gyyx.action.ui;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;

@Controller
@RequestMapping(value = "/wdschoolopen")
public class WDSchoolOpenController {

    @RequestMapping(value = "/index")
    public String index(Model model, HttpServletRequest request) {

        return "wdschoolopen/index";
    }

    @RequestMapping(value = "/listHot")
    public String listHot(HttpServletRequest request) {

        return "wdschoolopen/listHot";
    }

    @RequestMapping(value = "/listNew")
    public String listNew(HttpServletRequest request) {

        return "wdschoolopen/listNew";
    }

    @RequestMapping(value = {"/ajaxAddFace", "/ajaxGetTopFive", "/ajaxLightFace", 
            "/getTopEightFaceBeanByTime", "/ajaxGetFaceByPageAndAccount"})
    public @ResponseBody ResultBean<String> ajaxAddFace(HttpServletRequest request) {
        
        ResultBean<String> res = new ResultBean<String>(false, "谢谢参与，活动已结束", null);
        
        return res;
    }

   
    @RequestMapping(value = {"/ajaxGetFaceBeanByPageAndAccountByNum", "/ajaxAddSignCollegeBean", 
            "/ajaxGetSignCollegeBean"})
    public @ResponseBody ResultBean ajaxGetFaceBeanByPageAndAccountByNum(HttpServletRequest request) {
        
        ResultBean<String> res = new ResultBean<String>(false, "谢谢参与，活动已结束", null);
        
        return res;
    }


    @ResponseBody
    public ResultBean<Integer> exception() {
        ResultBean<Integer> bandResultBean = new ResultBean<Integer>(false, "谢谢参与，活动已结束", null);
        return bandResultBean;
    }

    // TODO
    /**
     * 
     * @日期：2015年3月20日
     * @Title: getAddress
     * @Description: TODO 传递用户地址信息
     * @param request
     * @return ResultBean<AddressBean>
     */
    @RequestMapping(value = {"/getAddress", "/resetAddress", "/setAddress", "/getPrize", "/getLotteryTime", 
            "/getLotteryInfo"})
    @ResponseBody
    public ResultBean getAddress(HttpServletRequest request) {
       
        ResultBean result = new ResultBean(false, "谢谢参与，活动已结束", null);
      
        return result;
    }

    
    /**
     * 得到当前活动所有中奖的信息
     * 
     * @return
     */
    @RequestMapping(value = {"/getLotteryInfo", "/getLotteryInfoByUser"})
    @ResponseBody
    public ResultBean getAllLotteryInfo() {
        ResultBean resultBean = new ResultBean(false, "谢谢参与，活动已结束", null);
       
        return resultBean;
    }

}