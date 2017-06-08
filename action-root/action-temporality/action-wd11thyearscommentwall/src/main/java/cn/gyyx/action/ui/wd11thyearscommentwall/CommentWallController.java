/**
  * -------------------------------------------------------------------------
  * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
  * @版权所有：北京光宇在线科技有限责任公司
  * @项目名称：action-wd11thyearscommentwall
  * @作者：caishuai
  * @联系方式：caishuai@gyyx.cn
  * @创建时间：2017年3月9日 上午10:59:56
  * @版本号：0.0.1
  *-------------------------------------------------------------------------
  */
package cn.gyyx.action.ui.wd11thyearscommentwall;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Throwables;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.wd11thyearscommentwall.CommentWallBean;
import cn.gyyx.action.beans.wd11thyearscommentwall.Constant;
import cn.gyyx.action.bll.config.IHdConfigBLL;
import cn.gyyx.action.bll.config.impl.DefaultHdConfigBLL;
import cn.gyyx.action.bll.wd11thyearscommentwall.CommentWallBll;
import cn.gyyx.action.bll.wd11thyearscommentwall.impl.CommentWallBllImpl;
import cn.gyyx.action.cache.MemcacheUtil;
import cn.gyyx.action.service.BaseController;
import cn.gyyx.action.service.wd11thyearscommentwall.CommentWallService;
import cn.gyyx.action.service.wd11thyearscommentwall.impl.CommentWallServiceImpl;
import cn.gyyx.core.memcache.XMemcachedClientAdapter;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * <p>
 * CommentWallController描述
 * </p>
 * 
 * @author caishuai
 * @since 0.0.1
 */
@Controller
@RequestMapping("/wd11thyearscommentwall")
public class CommentWallController extends BaseController {
    public static Logger logger = GYYXLoggerFactory
            .getLogger(CommentWallController.class);
    private IHdConfigBLL hdConfigBLL = new DefaultHdConfigBLL();
    private CommentWallBll commentWallBll = new CommentWallBllImpl();
    private CommentWallService commentWallService = new CommentWallServiceImpl();
    private XMemcachedClientAdapter memcached = MemcacheUtil
            .getActionMemcache();

    /**
     * 
     * <p>
     * 获取前150留言弹幕
     * </p>
     *
     * @action caishuai 2017年3月9日 下午1:18:02 描述
     *
     * @param request
     * @return ResultBean<List<CommentBean>>
     */
    @RequestMapping("/getBarrage")
    @ResponseBody
    public ResultBean<List<CommentWallBean>> getBarrage(
            HttpServletRequest request) {

        ResultBean<List<CommentWallBean>> result = new ResultBean<>();
        String memKey = Constant.ACTION_CODE
                + "wd11thyearscommentwall_getBarrage";
        // 获取配置信息
        int barrageSize = Integer.parseInt(String.valueOf(getConfigParamByKey(
            Constant.ACTION_CODE, Constant.BARRAGE, Constant.BARRAGE_DEFAULT)));
        int cacheTime = Integer.parseInt(
            String.valueOf(getConfigParamByKey(Constant.ACTION_CODE,
                Constant.CACHE_TIME, Constant.CACHE_TIME_DEFAULT)));
        logger.info("当前读取的配置弹幕大小{}条，缓存时间{}秒", barrageSize, cacheTime);
        try {
            List<CommentWallBean> commentsList = memcached.get(memKey,
                List.class);
            if (commentsList == null) {
                commentsList = commentWallBll.getTopCommentsList(barrageSize);
                memcached.set(memKey, cacheTime, commentsList);
            }
            result.setProperties(true, "获取数据成功", commentsList);
            logger.info("获取弹幕数据返回结果:{}", result);
        } catch (Exception e) {
            result.setProperties(false, "加载失败,请稍后再试", null);
            logger.error(
                "获取弹幕数据异常，错误堆栈：" + Throwables.getStackTraceAsString(e));
        }
        return result;

    }

    /**
     * 
     * <p>
     * 获取某个时间之前的x条留言
     * </p>
     *
     * @action caishuai 2017年3月10日 下午2:36:54 描述
     * @action caishuai 2017年3月15日 11:42:24 添加actionCode字段,增加获取某个活动单独的留言功能
     *
     * @param request
     * @param pageIndex
     *            页数
     * @param size
     *            数据大小 必填
     * @param endTime
     *            上一条记录的create时间
     * @param actionCode
     *            活动code，为null时默认获取全部
     * 
     * @return ResultBean<List<CommentWallBean>>
     */
    @RequestMapping("/getCommentsByPage")
    @ResponseBody
    public ResultBean<Map<String, Object>> getCommentsByPage(
            HttpServletRequest request, @RequestParam("pageSize") int pageSize,
            @RequestParam("pageIndex") int pageIndex, Date endTime,
            Integer actionCode) {
        logger.info("getCommentsByPage 参数pageSize:" + pageSize + ",endTime{"
                + endTime + "}" + ",pageIndex{" + pageIndex + "}"
                + ",actionCode:" + actionCode);
        ResultBean<Map<String, Object>> result = new ResultBean<>();
        if (!((pageIndex == 1 && endTime == null && pageSize > 0)
                || (pageIndex > 1 && endTime != null && pageSize > 0))) {
            result.setProperties(false, "加载失败,请稍后再试", null);
            logger.warn("参数不正确pageIndex{}endTime{}", pageIndex, endTime);
            return result;
        }
        try {
            Map<String, Object> commentsList = commentWallService
                    .getCommentsByPage(pageSize, pageIndex, endTime,
                        Integer.parseInt(String.valueOf(getConfigParamByKey(
                            Constant.ACTION_CODE, Constant.CACHE_TIME,
                            Constant.CACHE_TIME_DEFAULT))),
                        actionCode);
            result.setProperties(true, "获取数据成功", commentsList);
            logger.info("获取分页数据数据返回结果:{}", result);
        } catch (Exception e) {
            result.setProperties(false, "加载失败,请稍后再试", null);
            logger.error(
                "获取微信评论数据异常，错误堆栈：" + Throwables.getStackTraceAsString(e));
        }
        return result;

    }

    /**
     * 
     * <p>
     * 新增留言
     * </p>
     *
     * @action caishuai 2017年3月10日 下午2:37:57 描述
     *
     * @param request
     * @param response
     * @param nickname
     *            昵称 必须
     * @param context
     *            留言 必须
     * @param openId
     *            微信openid
     * @param captcha
     *            验证码 必须
     * @param actionCode
     *            活动code 必须
     * @return ResultBean<Object>
     */
    @RequestMapping("/addComment")
    @ResponseBody
    public ResultBean<Object> addComment(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("nickname") String nickname,
            @RequestParam("context") String context, String openId,
            @RequestParam("captcha") String captcha,
            @RequestParam("actionCode") int actionCode) {
        logger.info("提交评论传入参数nickname:{" + nickname + "},context:{" + context
                + "},captcha:{" + captcha + "},actionCode:{" + actionCode
                + "},openId:{" + openId + "}");
        ResultBean<Object> result = new ResultBean<>();

        // 判定参数正确性
        if (getRegExpLength(nickname) > 14) {
            result.setProperties(false, "昵称长度超出限制", null);
            logger.warn("参数nickname超出14字符{}", nickname);
            return result;
        }
        if (getRegExpLength(context) > 40) {
            result.setProperties(false, "留言长度超出限制", null);
            logger.warn("参数留言内容context超出40字符{}", context);
            return result;
        }
        if (!validateCapatcha(captcha, request, response)) {
            result.setProperties(false, "验证码不正确", null);
            logger.warn("参数验证码captcha不正确{}", captcha);
            return result;
        }
        CommentWallBean comment = new CommentWallBean();
        if (checkIsWeiXin(request)) {
            if (StringUtils.isEmpty(openId)) {
                result.setProperties(false, "请关注问道公众号，参与留言活动！", null);
                logger.warn("参数openId不存在{}", openId);
                return result;
            }
            comment.setSourceTag(openId);
        } else {
            comment.setSourceTag(getIp(request));
        }

        String allowAction = getConfigParamByKey(Constant.ACTION_CODE,
            Constant.ALLOW_ACTION, Constant.ALLOW_ACTION_DEFAULT);
        if (!allowAction.contains(String.valueOf(actionCode))) {
            result.setProperties(false, "留言提交失败，请重新提交留言！", null);
            logger.warn("参数actionCode不合法{}", actionCode);
            return result;
        }
        comment.setNickName(nickname);
        comment.setActionCode(actionCode);
        comment.setMessage(context);
        // 活动访问时间验证
        ResultBean<Object> activityStatus = getActivityStatus(actionCode);
        if (!activityStatus.getIsSuccess()) {
            logger.warn("活动访问时间不正确{}", activityStatus);
            return activityStatus;
        }

        try {
            result = commentWallService.addComment(comment, getConfigParamByKey(
                Constant.ACTION_CODE, Constant.LIMIT, Constant.LIMIT_DEFAULT));
            logger.info("提交评论返回数据resultBean{}", result);
        } catch (Exception e) {
            result.setProperties(false, "留言提交失败，请重新提交留言！", null);
            logger.error(
                "提交留言数据异常，错误堆栈：" + Throwables.getStackTraceAsString(e));
        }
        return result;

    }

    /**
     * 
     * <p>
     * 检查是否为微信浏览器
     * </p>
     *
     * @action caishuai 2017年3月9日 下午1:32:40 描述
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     *             boolean
     */
    private boolean checkIsWeiXin(HttpServletRequest request) {
        try {
            String ua = request.getHeader("user-agent").toLowerCase();

            if (ua.indexOf("micromessenger") == -1
                    || ua.indexOf("windows") > -1) {
                // 不是微信浏览器
                return false;
            }
        } catch (Exception e) {
            logger.error("检查是否是微信浏览器失败,错误堆栈:{}",
                Throwables.getStackTraceAsString(e));
        }
        return true;
    }

    /**
     * 
     * <p>
     * 利用正则表达式将每个中文字符转换为"**" 匹配中文字符的正则表达式：
     *  [\u4e00-\u9fa5] 匹配双字节字符(包括汉字在内)：[^\x00-\xff] 
     * </p>
     *
     * @action caishuai 2017年3月9日 下午6:41:05 描述
     *
     * @param validateStr
     * @return int
     */
    private int getRegExpLength(String validateStr) {
        String temp = validateStr.replaceAll("[^\\x00-\\xff]", "**");
        return temp.length();
    }

    /**
     * 
     * <p>
     * 获取活动配置参数
     * </p>
     *
     * @action caishuai 2017年3月9日 下午9:16:32 描述
     * @param actionCode活动code
     * @param key获取项
     * @param defaultValue默认值
     * @return int
     */
    private <T> T getConfigParamByKey(int actionCode, String key,
            T defaultValue) {
        String memKey = actionCode + "getConfigParamByKey";
        Map<String, T> config = new HashMap<>();
        try {
            if (memcached.get(memKey, Map.class) != null) {
                config = memcached.get(memKey, Map.class);
            } else {
                config = hdConfigBLL.getConfigParams(actionCode, Map.class);
                memcached.set(memKey, 300, config);
            }
        } catch (Exception e) {
            logger.error("获取活动配置信息失败,错误堆栈:{}",
                Throwables.getStackTraceAsString(e));
        }
        // 判定值存在
        if (config != null && config.get(key) != null
                && StringUtils.isNotEmpty(config.get(key).toString())) {
            return config.get(key);
        }
        return defaultValue;
    }
}
