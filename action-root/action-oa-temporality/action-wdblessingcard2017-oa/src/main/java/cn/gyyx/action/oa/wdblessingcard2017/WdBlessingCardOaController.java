/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action-wdblessingcard2017-oa
 * @作者：laixiancai
 * @联系方式：laixiancai@gyyx.cn
 * @创建时间：2017年3月9日 下午9:24:05
 * @版本号：0.0.1
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.oa.wdblessingcard2017;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Throwables;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.predicable.ResultBeanWithPage;
import cn.gyyx.action.beans.wdblessingcard2017.BlessingCardBean;
import cn.gyyx.action.service.wdblessingcard2017.BlessingCardBeanService;
import cn.gyyx.action.service.wdblessingcard2017.impl.BlessingCardBeanServiceImpl;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.oa.stage.httpRequest.OAUserRequest;
import cn.gyyx.oa.stage.model.OAUserInfoModel;

/**
 * <p>
 * 2017问道祝福卡片活动(wdblessingcard2017)OA后台Controller
 * </p>
 * 
 * @author laixiancai
 * @since 0.0.1
 */
@Controller
@RequestMapping(value = "/wdblessingcard2017")
public class WdBlessingCardOaController {

    private static final Logger LOGGER = GYYXLoggerFactory
            .getLogger(WdBlessingCardOaController.class);
    private BlessingCardBeanService blessingCardBeanService = new BlessingCardBeanServiceImpl();

    /**
     * 
     * <p>
     * OA祝福卡活动审核页面
     * </p>
     *
     * @action laixiancai 2017年3月11日 下午3:50:46 新增
     *
     * @param model
     * @param request
     * @return String
     */
    @RequestMapping(value = "/blessingcardaudit")
    public String blessingCardAudit(Model model, HttpServletRequest request) {
        return "wdblessingcard2017/blessingCardAudit";
    }

    /**
     * 
     * <p>
     * OA后台-获取系统发出的总抽奖次数
     * </p>
     *
     * @action laixiancai 2017年3月17日 下午8:33:30 新增
     *
     * @return Integer
     */
    @RequestMapping(value = "/getsystemlotterytimes",
        method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Integer> getSystemLotteryTimes(HttpServletRequest request) {
        try {
            LOGGER.info("进入action[getSystemLotteryTimes]");
            LOGGER.info("调用service[getSystemLotteryTimes]开始");

            Integer systemLotteryTimes = blessingCardBeanService
                    .getSystemLotteryTimes();

            LOGGER.info(
                "调用service[getSystemLotteryTimes]结束，返回结果systemLotteryTimes：{}",
                systemLotteryTimes);

            return new ResultBean<Integer>(true, "获取系统发出的总抽奖次数成功",
                    systemLotteryTimes);
        } catch (Exception e) {
            LOGGER.error("OA获取系统发出的总抽奖次数异常：{}",
                Throwables.getStackTraceAsString(e));
            return new ResultBean<Integer>(false, "获取系统发出的总抽奖次数异常", 0);
        }
    }

    /**
     * 
     * <p>
     * OA后台-获取玩家的总抽奖次数
     * </p>
     *
     * @action laixiancai 2017年3月17日 下午8:33:30 新增
     *
     * @return Integer
     */
    @RequestMapping(value = "/getuserslotterytimes", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Integer> getUsersLotteryTimes(HttpServletRequest request) {
        try {
            LOGGER.info("进入action[getUsersLotteryTimes]");
            LOGGER.info("调用service[getUsersLotteryTimes]开始");

            Integer usersLotteryTimes = blessingCardBeanService
                    .getUsersLotteryTimes();

            LOGGER.info(
                "调用service[getUsersLotteryTimes]结束，返回结果usersLotteryTimes：{}",
                usersLotteryTimes);

            return new ResultBean<Integer>(true, "获取玩家的总抽奖次数成功",
                    usersLotteryTimes);
        } catch (Exception e) {
            LOGGER.error("OA获取玩家的总抽奖次数异常：{}",
                Throwables.getStackTraceAsString(e));
            return new ResultBean<Integer>(false, "获取玩家的总抽奖次数异常", 0);
        }
    }

    /**
     * 
     * <p>
     * ajax获取祝福卡列表
     * </p>
     *
     * @action laixiancai 2017年3月11日 下午7:18:15 新增
     *
     * @param beginTime
     * @param endTime
     * @param account
     * @param verifyStatus
     * @param pageSize
     * @param pageIndex
     * @param request
     * @return ResultBeanWithPage<List<BlessingCardBean>>
     */
    @RequestMapping(value = "/getblessingcardlist", method = RequestMethod.POST)
    @ResponseBody
    public ResultBeanWithPage<List<BlessingCardBean>> getBlessingCardList(
            @RequestParam(value = "beginTime") String beginTime, @RequestParam(
                value = "endTime") String endTime, @RequestParam(
                value = "account") String account, @RequestParam(
                value = "verifyStatus") String verifyStatus, @RequestParam(
                value = "pageSize") int pageSize, @RequestParam(
                value = "pageIndex") int pageIndex, HttpServletRequest request) {
        LOGGER.info(
            "进入action[getBlessingCardList]，请求参数beginTime：{}, endTime：{}, account：{}, verifyStatus：{}, pageSize：{}, pageIndex：{}",
            new Object[] { beginTime, endTime, account, verifyStatus, pageSize,
                    pageIndex });

        ResultBeanWithPage<List<BlessingCardBean>> result = new ResultBeanWithPage<>();
        try {
            if (!StringUtils.isEmpty(beginTime)) {
                beginTime = beginTime + " 00:00:00";
            }
            if (!StringUtils.isEmpty(endTime)) {
                endTime = endTime + " 23:59:59";
            }

            LOGGER.info(
                "调用service[getBlessingCardList]开始，参数beginTime：{}, endTime：{}, account：{}, verifyStatus：{}, pageSize：{}, pageIndex：{}",
                new Object[] { beginTime, endTime, account, verifyStatus,
                        pageSize, pageIndex });

            result = blessingCardBeanService.getBlessingCardList(beginTime,
                endTime, account, verifyStatus, pageSize, pageIndex);

            LOGGER.info("调用service[getBlessingCardList]结束，列表总数为：{}",
                new Object[] { result.getTotalPage() });

            return result;
        } catch (Exception e) {
            LOGGER.error("获取数据列表异常{}", Throwables.getStackTraceAsString(e));
            List<BlessingCardBean> list = new ArrayList<BlessingCardBean>();
            result.setProperties(false, "获取数据列表异常", list, 0);
            return result;
        }
    }

    /**
     * 
     * <p>
     * 审核祝福卡
     * </p>
     *
     * @action laixiancai 2017年3月11日 下午3:50:03 新增
     *
     * @param codesStr
     * @param verifyStatus
     * @param request
     * @return ResultBean<String>
     */
    @RequestMapping(value = "/verifyblessingcard", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> batchVerifyBlessingCard(@RequestParam(
        value = "codesStr") String codesStr, @RequestParam(
        value = "verifyStatus") int verifyStatus, HttpServletRequest request) {
        try {
            LOGGER.info(
                "进入action[batchVerifyBlessingCard]，请求参数codesStr：{}, verifyStatus：{}",
                new Object[] { codesStr, verifyStatus });
            if (StringUtils.isEmpty(codesStr)) {
                return new ResultBean<>(false, "祝福卡编号不能为空", null);
            }
            OAUserInfoModel userInfoModel = OAUserRequest
                    .getUserInfoByRequest(request);
            if (userInfoModel == null) {
                return new ResultBean<>(false, "您没登录,请先登录", "");
            }
            String verifyAdmin = userInfoModel.getAccount();
            LOGGER.info(
                "调用service[batchVerifyBlessingCard]开始，参数codesStr：{}, verifyStatus：{}, verifyAdmin：{}",
                new Object[] { codesStr, verifyStatus, verifyAdmin });

            ResultBean<String> result = blessingCardBeanService
                    .batchVerifyBlessingCard(codesStr, verifyStatus,
                        verifyAdmin);

            LOGGER.info("调用service[batchVerifyBlessingCard]结束，返回结果：{}",
                new Object[] { JSONObject.fromObject(result) });

            return result;
        } catch (Exception e) {
            LOGGER.error("审核-祝福卡异常{}", Throwables.getStackTraceAsString(e));
            return new ResultBean<>(false, "审核失败", "");
        }
    }
}
