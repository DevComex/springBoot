/**
  * -------------------------------------------------------------------------
  * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
  * @版权所有：北京光宇在线科技有限责任公司
  * @项目名称：action-wd11thyearscommentwall-oa
  * @作者：guoyonggang
  * @联系方式：guoyonggang@gyyx.cn
  * @创建时间：2017年3月13日 下午4:42:24
  * @版本号：0.0.1
  *-------------------------------------------------------------------------
  */
package cn.gyyx.action.oa.wd11thyearscommentwall.service;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.oa.wd11thyearscommentwall.bll.WdElevenCommentWallBLL;

/**
 * <p>
 * 问道11周年评论墙业务操作
 * </p>
 * 
 * @author guoyonggang
 * @since 0.0.1
 */
public class WdElevenCommentWallService {

    private static final Logger logger = LoggerFactory
            .getLogger(WdElevenCommentWallService.class);

    private static WdElevenCommentWallBLL wdElevenCommentWallBLL = new WdElevenCommentWallBLL();

    /**
     * <p>
     * 分页查询
     * </p>
     *
     * @action guoyonggang 2017年3月13日 下午8:48:11 描述
     *
     * @param request
     * @return
     * @throws ParseException
     *             ResultBean<Object>
     * @throws UnsupportedEncodingException
     */
    public ResultBean<Object> query(HttpServletRequest request)
            throws ParseException, UnsupportedEncodingException {
        String auditStatus = request.getParameter("auditStatus");
        String beginTime = request.getParameter("beginTime");
        String endTime = request.getParameter("endTime");
        // 页码
        Integer pageIndex = StringUtils.isEmpty(
            request.getParameter("pageIndex"))
                    ? 1
                    : NumberUtils.isNumber(request.getParameter("pageIndex"))
                            ? Integer.parseInt(
                                request.getParameter("pageIndex"))
                            : 1;
        // 页面尺寸
        Integer pageSize = StringUtils.isEmpty(request.getParameter("pageSize"))
                ? 10
                : NumberUtils.isNumber(request.getParameter("pageSize"))
                        ? Integer.parseInt(request.getParameter("pageSize"))
                        : 10;
        String keyWord = request.getParameter("keyWord");
        logger.info("WdElevenCommentWallBeanService query begin ,params:{}",
            request.getParameterMap());
        // 校验参数
        logger.info("checkInputPrams begin");
        ResultBean<Object> result = checkInputPrams(auditStatus, beginTime,
            endTime);
        logger.info("checkInputPrams end ; result:{}", result);
        if (!result.getIsSuccess()) {
            return result;
        }
        result.setIsSuccess(false);

        // 获取统计数据
        logger.info("queryStatistics begin");
        List<Map<String, Object>> statisticsResult = wdElevenCommentWallBLL
                .queryElevenYearsCommentStatistics(auditStatus, beginTime,
                    endTime, keyWord);
        logger.info("queryStatistics end ; result：{}", statisticsResult);

        // 获取分页数据
        logger.info("queryPageRows begin");
        List<Object> pageResult = wdElevenCommentWallBLL
                .queryElevenYearsCommentList(pageIndex, pageSize,
                    (pageIndex - 1) * pageSize, auditStatus, beginTime, endTime,
                    keyWord);
        logger.info("queryPageRows end ; result:{}", pageResult);

        // 获取总条数
        logger.info("queryPageCount begin");
        int pageCount = wdElevenCommentWallBLL.queryElevenYearsCommentCount(
            auditStatus, beginTime, endTime, keyWord);
        logger.info("queryPageCount end ; result:{}", pageCount);

        result.setIsSuccess(true);
        result.setRows(pageResult);
        result.setData(statisticsResult);
        result.setTotal(pageCount);
        result.setMessage("获取成功");
        return result;
    }

    /**
     * <p>
     * 校验查询参数
     * </p>
     *
     * @action guoyonggang 2017年3月13日 下午8:22:30 描述
     *
     * @param auditStatus
     *            审核状态
     * @param beginTime
     *            开始时间
     * @param endTime
     *            结束时间
     * @param pageIndex
     *            页码
     * @param pageSize
     *            页面尺寸
     * @return ResultBean<Object> 校验结果
     */
    private ResultBean<Object> checkInputPrams(String auditStatus,
            String beginTime, String endTime) {
        ResultBean<Object> result = new ResultBean<>();
        if (StringUtils.isEmpty(auditStatus)) {
            result.setMessage("请选择审核状态");
            return result;
        }
        SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
        if (!StringUtils.isEmpty(beginTime)) {
            formate.setLenient(false);
            try {
                formate.parse(beginTime);
            } catch (Exception ex) {
                result.setMessage("开始时间格式不正确");
                return result;
            }
        }
        if (!StringUtils.isEmpty(endTime)) {
            formate.setLenient(false);
            try {
                formate.parse(endTime);
            } catch (Exception ex) {
                result.setMessage("结束时间格式不正确");
                return result;
            }
        }
        result.setIsSuccess(true);
        result.setMessage("参数校验成功");
        return result;
    }
}
