package cn.gyyx.action.ui;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.wdninestory.ServerBean;

@Controller
@RequestMapping(value = "/wdoldplayers")
public class WDOldPlayersController {

    @RequestMapping(value = "/index")
    public String index(Model model, HttpServletRequest request) {

        return "wdOldPlayers/index";
    }

    @RequestMapping(value = "/list")
    public String list(Model model, HttpServletRequest request) {

        return "wdOldPlayers/list";
    }

    /**
     * 
     * @Title: getServers
     * @Description: TODO 获取服务器信息
     * @param @param typeCode
     * @param @return
     * @return ResultBean<List<ServerBean>>
     * @throws
     */
    @RequestMapping(value = "/getServers", method = RequestMethod.GET,
        produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResultBean<List<ServerBean>> getServers(@RequestParam(
        value = "netType") String typeCode) {
        ResultBean<List<ServerBean>> result = new ResultBean<List<ServerBean>>(
                false, "谢谢参与，活动已结束", null);

        return result;
    }

    /***
     * 获取角色信息
     * 
     * @param request
     * @param response
     * @param serverId
     * @param veCode
     * @return
     */
    @RequestMapping(value = { "/getRoles", "/addRole", "/checkRole" },
        method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResultBean<String> getRoles() {
        ResultBean<String> resultBean = new ResultBean<>(false, "谢谢参与，活动已结束",
                null);

        return resultBean;
    }

    @RequestMapping(value = "/getPrizeByScore", method = RequestMethod.GET,
        produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResultBean<Boolean> getPrizeByScore(HttpServletRequest request) {
        ResultBean<Boolean> result = new ResultBean<Boolean>(false,
                "谢谢参与，活动已结束", null);

        return result;
    }

    /**
     * 
     * @Title: addWdOldWantedInfo
     * @Description: TODO 寻人操作
     * @param @param wdOldWantedInfoBean
     * @param @return
     * @return ResultBean<WdOldWantedInfoBean>
     * @throws
     */
    @RequestMapping(value = "/addWdOldWantedInfo", method = RequestMethod.POST,
        produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResultBean addWdOldWantedInfo(HttpServletRequest request) {
        ResultBean result = new ResultBean(false, "谢谢参与，活动已结束", null);

        return result;

    }

    /**
     * 
     * @Title: getCertification
     * @Description: TODO 判断是否认证
     * @param @param accountName
     * @param @param request
     * @param @return
     * @return ResultBean<WdOldCertificationBean>
     * @throws
     */
    @RequestMapping(value = { "/getCertification", "/addCertification" },
        method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResultBean getCertification(HttpServletRequest request) {
        ResultBean result = new ResultBean(false, "谢谢参与，活动已结束", null);

        return result;
    }

    /**
     * 
     * @Title: addCertification
     * @Description: TODO 评论
     * @param @param wdOldWantedInfoBean
     * @param @return
     * @return ResultBean<WdOldWantedInfoBean>
     * @throws
     */
    @RequestMapping(value = "/addComments", method = RequestMethod.POST,
        produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResultBean<Boolean> addComments(HttpServletRequest request) {
        ResultBean<Boolean> result = new ResultBean<Boolean>(false,
                "谢谢参与，活动已结束", false);
        return result;

    }

    /**
     * 
     * @Title: getCommentsBeanByRole
     * @Description: TODO 获取评论集合
     * @param @param accountName
     * @param @param request
     * @param @return
     * @return ResultBean<WdOldCertificationBean>
     * @throws
     */
    @RequestMapping(value = { "/getCommentsBeanByRole",
            "/getWdCommentsBeanByPage", "/getWdCommentsIndex",
            "/getWdOldWantedInfoBeanByRole", "/getWdOldWantedInfoBeanByMe",
            "/getWdOldWantedInfoBeanByAccount" }, method = RequestMethod.POST,
        produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResultBean getCommentsBeanByRole(HttpServletRequest request) {
        ResultBean result = new ResultBean(false, "谢谢参与，活动已结束", null);

        return result;
    }

    /**
     * 
     * @Title: updateWdOldWantedInfoBean
     * @Description: TODO 响应
     * @param @param accountName
     * @param @param request
     * @param @return
     * @return ResultBean<PageBean<WdOldWantedInfoBean>>
     * @throws
     */
    @RequestMapping(value = "/updateWdOldWantedInfoBean",
        method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResultBean<Boolean> getWdOldWantedInfoBeanByRole(
            HttpServletRequest request) {
        ResultBean<Boolean> result = new ResultBean<Boolean>(false,
                "谢谢参与，活动已结束", null);

        return result;
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
    @RequestMapping(value = { "/getAddress", "/resetAddress", "/setAddress" })
    @ResponseBody
    public ResultBean getAddress(HttpServletRequest request) {
        ResultBean result = new ResultBean(false, "谢谢参与，活动已结束", null);

        return result;
    }

    /**
     * 
     * @日期：2015年3月20日
     * @Title: resetAddress
     * @Description: TODO 查询用户是否中奖
     * @param address
     * @param request
     * @return ResultBean<UserLotteryBean>
     */
    @RequestMapping(value = { "/getPrize", "/getLotteryTime" })
    @ResponseBody
    public ResultBean getPrize(HttpServletRequest request) {

        ResultBean result = new ResultBean(false, "谢谢参与，活动已结束", null);

        return result;
    }

    /**
     * 得到当前活动所有中奖的信息
     * 
     * @return
     */
    @RequestMapping(value = { "/getLotteryInfo", "/getLotteryInfoByUser" })
    @ResponseBody
    public ResultBean getAllLotteryInfo() {
        ResultBean resultBean = new ResultBean(false, "谢谢参与，活动已结束", null);

        return resultBean;
    }

}
