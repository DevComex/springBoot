/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 *
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：
 * @作者：tanjunkai        
 * @联系方式：tanjunkai@gyyx.cn
 * @创建时间：2017/4/7 16:02
 * @版本号：0.0.1 -------------------------------------------------------------------------
 */
package cn.gyyx.action.oa.wish11th;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Throwables;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.predicable.ResultBeanWithPage;
import cn.gyyx.action.beans.wish11th.Constants;
import cn.gyyx.action.beans.wish11th.Wish11thLightBean;
import cn.gyyx.action.beans.wish11th.Wish11thLightInfoBean;
import cn.gyyx.action.beans.wish11th.Wish11thWishBean;
import cn.gyyx.action.bll.wish11th.Wish11thLightBll;
import cn.gyyx.action.bll.wish11th.Wish11thWishBll;
import cn.gyyx.action.service.wish11th.Wish11thLightService;
import cn.gyyx.log.sdk.GYYXLogger;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import net.sf.json.JSONObject;

/**
 * <p>
 * 问道许愿活动OA后台
 * </p>
 * 
 * @author tanjunkai
 * @since 0.0.1
 */
@Controller
@RequestMapping("/wish11th")
public class Wish11thOAController {

    // 日志访问类
    private static final GYYXLogger logger = GYYXLoggerFactory
            .getLogger(Wish11thOAController.class);

    // 蛋糕层数相关业务逻辑类
    Wish11thLightBll lightBll = new Wish11thLightBll();
    // 许愿业务相关逻辑
    Wish11thWishBll wishBll = new Wish11thWishBll();
    // 蛋糕层相关服务
    Wish11thLightService wishService = new Wish11thLightService();

    /**
     * <p>
     * 许愿审核页面跳转
     * </p>
     *
     * @action tanjunkai 2017年4月8日 上午10:27:03 描述
     *
     * @return String
     */
    @RequestMapping(value = "/userwish")
    public String wish() {
        return "wish11th/wish";
    }

    /**
     * <p>
     * 层点亮人数修改页跳转
     * </p>
     *
     * @action tanjunkai 2017年4月8日 上午10:27:29 描述
     *
     * @return String
     */
    @RequestMapping(value = "/light")
    public String light() {
        return "wish11th/light";
    }

    /**
     * <p>
     * 获取全部蛋糕层
     * </p>
     *
     * @action tanjunkai 2017年4月7日 下午4:57:01 描述
     *
     * @param request
     * @param response
     * @return ResultBean<Object>
     */
    @RequestMapping(value = "/levelList", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> getLevelList(HttpServletRequest request,
            HttpServletResponse response) {
        logger.info("获取许愿层信息");
        ResultBean<Object> resultBean = new ResultBean<>();
        List<Wish11thLightBean> allLights = lightBll.getAllLights();

        List<Wish11thLightInfoBean> allLightsWithCount = new ArrayList<Wish11thLightInfoBean>();
        for (Wish11thLightBean light : allLights) {  
            Wish11thLightInfoBean info = new Wish11thLightInfoBean();
            info.setActionCode(light.getActionCode());
            info.setCode(light.getCode());
            info.setLevel(light.getLevel());
            info.setLightType(light.getLightType());
            info.setLimitNum(light.getLimitNum());
            info.setUpdateTime(light.getUpdateTime());
            // 设置该层许愿人数
            info.setWishNum(wishBll.getWishUserCountByLevel(info.getLevel(),
                Constants.WISH11TH_ALLSTATUS));
            // 设置该层抽奖次数
            info.setLotteryNum(wishBll.getWishCountByLevel(info.getLevel(),
                Constants.WISH11TH_ALLSTATUS));
            allLightsWithCount.add(info);
        }
        resultBean.setProperties(true, "获取成功", allLightsWithCount);
        logger.info("获取许愿层信息结束");
        return resultBean;
    }

    /**
     * <p>
     * 修改某层的点亮人数
     * </p>
     *
     * @action tanjunkai 2017年4月7日 下午4:57:32 描述
     *
     * @param level
     * @param limitNum
     * @param request
     * @param response
     * @return ResultBean<Object>
     */
    @RequestMapping(value = "/modifyPepNum", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<Object> modifyLevelPeopleNum(
            @RequestParam(value = "level") int level,
            @RequestParam(value = "limitNum") int limitNum,
            HttpServletRequest request, HttpServletResponse response) {
        logger.info("修改许愿层点亮人数:level:{},limitNum{}",
            new Object[] { level, limitNum });

        logger.info("修改许愿层点亮人数结束");
        return wishService.modifyLightNum(level, limitNum);
    }

    /**
     * <p>
     * 获取所有许愿信息(用于审核)
     * </p>
     *
     * @action tanjunkai 2017年4月8日 上午10:30:01 描述
     *
     * @param beginTime
     *            许愿时间
     * @param endTime
     *            许愿时间
     * @param rolename
     *            角色名称
     * @param verifyStatus
     *            状态
     * @param pageSize
     *            每页显示的条数
     * @param pageIndex
     *            当前页码
     * @param request
     * @return ResultBeanWithPage<List<Wish11thWishBean>>
     */
    @RequestMapping(value = "/getwishlist", method = RequestMethod.POST)
    @ResponseBody
    public ResultBeanWithPage<List<Wish11thWishBean>> getWishList(
            @RequestParam(value = "beginTime") String beginTime,
            @RequestParam(value = "endTime") String endTime,
            @RequestParam(value = "rolename") String rolename,
            @RequestParam(value = "verifyStatus") String verifyStatus,
            @RequestParam(value = "pageSize") int pageSize,
            @RequestParam(value = "pageIndex") int pageIndex,
            HttpServletRequest request) {
        logger.info(
            "进入action[getWishList]，请求参数beginTime：{}, endTime：{}, roleName：{}, verifyStatus：{}, pageSize：{}, pageIndex：{}",
            new Object[] { beginTime, endTime, rolename, verifyStatus, pageSize,
                    pageIndex });

        ResultBeanWithPage<List<Wish11thWishBean>> result = new ResultBeanWithPage<>();
        try {
            if (!StringUtils.isEmpty(beginTime)) {
                beginTime = beginTime + " 00:00:00";
            }
            if (!StringUtils.isEmpty(endTime)) {
                endTime = endTime + " 23:59:59";
            }
            logger.info(
                "调用service[getwishlist]开始，参数beginTime：{}, endTime：{}, roleName：{}, verifyStatus：{}, pageSize：{}, pageIndex：{}",
                new Object[] { beginTime, endTime, rolename, verifyStatus,
                        pageSize, pageIndex });
            // 获取搜索条件内的许愿列表
            List<Wish11thWishBean> wishList = wishBll.getWishList(beginTime,
                endTime, rolename, verifyStatus, pageSize, pageIndex);
            // 获取搜索条件内的许愿数
            int count = wishBll.getWishListCount(beginTime, endTime, rolename,
                verifyStatus);
            result.setProperties(true, "获取成功", wishList, count);
            logger.info("调用service[getwishlist]结束，列表总数为：{}",
                new Object[] { result.getTotalPage() });
            return result;
        } catch (Exception e) {
            logger.error("获取许愿列表异常{}", Throwables.getStackTraceAsString(e));
            List<Wish11thWishBean> list = new ArrayList<Wish11thWishBean>();
            result.setProperties(false, "获取数据列表异常", list, 0);
            return result;
        }
    }

    /**
     * <p>
     * 审核许愿内容(单审核和批量审核)
     * </p>
     *
     * @action tanjunkai 2017年4月8日 上午10:38:18 描述
     *
     * @param codesStr
     *            许愿ID集合,逗号分隔
     * @param verifyStatus
     *            审核状态
     * @param request
     * @return ResultBean<String>
     */
    @RequestMapping(value = "/audit", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> wishAudit(
            @RequestParam(value = "codesStr") String codesStr,
            @RequestParam(value = "verifyStatus") int verifyStatus,
            HttpServletRequest request) {
        try {
            logger.info("进入action[wishAudit]，请求参数codesStr：{}, verifyStatus：{}",
                new Object[] { codesStr, verifyStatus });
            if (StringUtils.isEmpty(codesStr)) {
                return new ResultBean<>(false, "主键不能为空", null);
            }
            logger.info("调用wishBll[wishAudit]开始，参数codesStr：{}, verifyStatus：{}",
                new Object[] { codesStr, verifyStatus });
            ResultBean<String> result = new ResultBean<String>();
            if (wishBll.wishAudit(codesStr, verifyStatus) > 0) {
                result.setProperties(true, "更新成功", null);
            } else {
                result.setProperties(false, "更新失败", null);
            }
            logger.info("调用service[wishAudit]结束，返回结果：{}",
                new Object[] { JSONObject.fromObject(result) });
            return result;
        } catch (Exception e) {
            logger.error("问道许愿审核产生异常{}", Throwables.getStackTraceAsString(e));
            return new ResultBean<>(false, "审核失败", "");
        }
    }
}
