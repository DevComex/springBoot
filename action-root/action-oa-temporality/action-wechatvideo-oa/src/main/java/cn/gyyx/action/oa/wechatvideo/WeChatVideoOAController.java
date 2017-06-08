/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 *
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：
 * @作者：tanjunkai        
 * @联系方式：tanjunkai@gyyx.cn
 * @创建时间：2017/3/16 9:48
 * @版本号：0.0.1 -------------------------------------------------------------------------
 */
package cn.gyyx.action.oa.wechatvideo;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Throwables;

import cn.gyyx.action.beans.predicable.ResultBeanWithPage;
import cn.gyyx.action.beans.wechatvideo.WeChatVideoUpLoadLogBean;
import cn.gyyx.action.bll.wechatvideo.WeChatVideoUpLoadLogBll;
import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.oa.stage.httpRequest.OAUserRequest;
import cn.gyyx.oa.stage.model.OAUserInfoModel;

/**
 * <p>
 * 问道周年视频祝福活动OA后台ui层
 * </p>
 * 
 * @author tanjunkai
 * @since 0.0.1
 */
@Controller
@RequestMapping("/wechatvideo")
public class WeChatVideoOAController {

    private WeChatVideoUpLoadLogBll weChatVideoUpLoadLogBll = new WeChatVideoUpLoadLogBll();
    private static final Logger LOGGER = GYYXLoggerFactory
            .getLogger(WeChatVideoOAController.class);

    @RequestMapping("/manage/index")
    public String index() {
        return "wechatvideo/videoaudit";
    }

    /**
     * <p>
     * 视频审核接口
     * </p>
     *
     * @action tanjunkai 2017年3月16日 上午10:45:08 描述
     *
     * @param request
     * @param videoId
     *            视频ID
     * @param status
     *            视频状态(-1:被拒绝，0:未审核，1:审核通过)
     * @return ResultBean
     */
    @RequestMapping(value = "/audit", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean auditVideo(HttpServletRequest request,
            @RequestParam("videoId") int videoId,
            @RequestParam("status") int status) {
        ResultBean resultBean = new ResultBean<>();
        try {
            OAUserInfoModel userInfoModel = OAUserRequest
                    .getUserInfoByRequest(request);
            if (userInfoModel == null) {
                resultBean.setIsSuccess(false);
                resultBean.setMessage("您没登录,请先登录！");
                return resultBean;
            }
            resultBean = weChatVideoUpLoadLogBll.updateByPrimaryKeySelective(
                videoId, status,userInfoModel.getAccount());

        } catch (Exception e) {
            LOGGER.error("视频审核接口异常{}", Throwables.getStackTraceAsString(e));
            resultBean.setIsSuccess(false);
            resultBean.setMessage("sorry,服务器出点小问题,请稍后重试！");
        }
        return resultBean;
    }

    /**
     * <p>
     * 视频批量审核接口
     * </p>
     *
     * @action tanjunkai 2017年3月16日 上午10:45:08 描述
     *
     * @param request
     * @param videoId
     *            视频ID字符数组("例：1;2;3;4")
     * @param status
     *            视频状态(-1:被拒绝，0:未审核，1:审核通过)
     * @return ResultBean
     */
    @RequestMapping(value = "/batchaudit", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean batchAuditVideo(HttpServletRequest request,
            @RequestParam("videoIds") String videoIds,
            @RequestParam("status") int status) {
        ResultBean resultBean = new ResultBean<>();
        try {
            if (videoIds == null || videoIds.trim().equals("")) {
                resultBean.setIsSuccess(false);
                resultBean.setMessage("请选择需要审核的视频！");
                return resultBean;
            }

            OAUserInfoModel userInfoModel = OAUserRequest
                    .getUserInfoByRequest(request);
            if (userInfoModel == null) {
                resultBean.setIsSuccess(false);
                resultBean.setMessage("您没登录,请先登录！");
                return resultBean;
            }
            resultBean = weChatVideoUpLoadLogBll.updateByBatch(
                videoIds.split(";"), status,userInfoModel.getAccount());

        } catch (Exception e) {
            LOGGER.error("视频批量审核接口异常{}", Throwables.getStackTraceAsString(e));
            resultBean.setIsSuccess(false);
            resultBean.setMessage("sorry,服务器出点小问题,请稍后重试！");
        }
        return resultBean;
    }

    /**
     * <p>
     * 更新视频地址(手动格式转换后重新上传)
     * </p>
     *
     * @action tanjunkai 2017年3月23日 上午8:28:55 描述
     *
     * @param videoId
     * @param address
     * @return ResultBean
     */
    @RequestMapping(value = "/upload/manual", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean updateVideoAddress(@RequestParam("videoid") int videoId,
            @RequestParam("videoaddress") String videoAddress) {
        ResultBean resultBean = new ResultBean<>();
        try {
            if (videoAddress == null || videoAddress.equals("")) {
                resultBean.setIsSuccess(false);
                resultBean.setMessage("视频地址不能为空");
                return resultBean;
            }
            resultBean = weChatVideoUpLoadLogBll.updateVideoAddress(videoId,
                videoAddress);
        } catch (Exception e) {
            LOGGER.error("更新视频地址(手动格式转换后重新上传)异常{}", Throwables.getStackTraceAsString(e));
            resultBean.setIsSuccess(false);
            resultBean.setMessage("sorry,服务器出点小问题,请稍后重试！");
        }
        return resultBean;
    }

    /**
     * <p>
     * OA后台问道周年视频祝福活动审核列表
     * </p>
     *
     * @action tanjunkai 2017年3月23日 上午7:35:28 描述
     *
     * @param beginTime
     * @param endTime
     * @param keyword
     * @param verifyStatus
     * @param pageSize
     * @param pageIndex
     * @param request
     * @return ResultBeanWithPage<List<WeChatVideoUpLoadLogBean>>
     */
    @RequestMapping(value = "/getvideolist", method = RequestMethod.POST)
    @ResponseBody
    public ResultBeanWithPage<List<WeChatVideoUpLoadLogBean>> getVideoList(
            @RequestParam(value = "beginTime") String beginTime,
            @RequestParam(value = "endTime") String endTime,
            @RequestParam(value = "keyword") String keyword,
            @RequestParam(value = "verifyStatus") String verifyStatus,
            @RequestParam(value = "pageSize") int pageSize,
            @RequestParam(value = "pageIndex") int pageIndex,
            HttpServletRequest request) {
        LOGGER.info(
            "进入action[getvideolist]，请求参数beginTime：{}, endTime：{}, keyword：{}, verifyStatus：{}, pageSize：{}, pageIndex：{}",
            new Object[] { beginTime, endTime, keyword, verifyStatus, pageSize,
                    pageIndex });

        ResultBeanWithPage<List<WeChatVideoUpLoadLogBean>> result = new ResultBeanWithPage<>();
        try {
            if (!StringUtils.isEmpty(beginTime)) {
                beginTime = beginTime + " 00:00:00";
            }
            if (!StringUtils.isEmpty(endTime)) {
                endTime = endTime + " 23:59:59";
            }
            LOGGER.info(
                "调用service[getvideolist]开始，参数beginTime：{}, endTime：{}, keyword：{}, verifyStatus：{}, pageSize：{}, pageIndex：{}",
                new Object[] { beginTime, endTime, keyword, verifyStatus,
                        pageSize, pageIndex });

            result = weChatVideoUpLoadLogBll.getVideoList(beginTime, endTime,
                keyword, verifyStatus, pageSize, pageIndex);

            LOGGER.info("调用service[getvideolist]结束，列表总数为：{}",
                new Object[] { result.getTotalPage() });

            return result;
        } catch (Exception e) {
            LOGGER.error("获取数据列表异常{}", Throwables.getStackTraceAsString(e));
            List<WeChatVideoUpLoadLogBean> list = new ArrayList<WeChatVideoUpLoadLogBean>();
            result.setProperties(false, "获取数据列表异常", list, 0);
            return result;
        }
    }
}
