/**
  * -------------------------------------------------------------------------
  * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
  * @版权所有：北京光宇在线科技有限责任公司
  * @项目名称：action-wd11thyearscommentwall-oa
  * @作者：guoyonggang
  * @联系方式：guoyonggang@gyyx.cn
  * @创建时间：2017年3月13日 下午4:16:49
  * @版本号：0.0.1
  *-------------------------------------------------------------------------
  */
package cn.gyyx.action.oa.wd11thyearscommentwall.ui;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Throwables;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.oa.wd11thyearscommentwall.beans.WdElevenCommentWallBean;
import cn.gyyx.action.oa.wd11thyearscommentwall.bll.WdElevenCommentWallBLL;
import cn.gyyx.action.oa.wd11thyearscommentwall.service.WdElevenCommentWallService;
import cn.gyyx.oa.stage.httpRequest.OAUserRequest;
import cn.gyyx.oa.stage.model.OAUserInfoModel;

/**
 * <p>
 * 问道11周年留言墙活动后台
 * </p>
 * 
 * @author guoyonggang
 * @since 0.0.1
 */
@Controller
@RequestMapping(value = "wd11thyearscommentwall")
public class WdElevenCommentWallController {

    private WdElevenCommentWallService service = new WdElevenCommentWallService();
    private WdElevenCommentWallBLL bll = new WdElevenCommentWallBLL();

    private static final Logger logger = LoggerFactory
            .getLogger(WdElevenCommentWallController.class);

    /**
     * 
     * <p>
     * 问道11周年留言墙活动后台首页
     * </p>
     *
     * @action guoyonggang 2017年3月13日 下午4:19:16 描述
     *
     * @return String
     */
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index() {
        return "wd11thyearscommentwall/index";
    }

    /**
      * <p>
      *    留言墙后台查询
      * </p>
      *
      * @action
      *    guoyonggang 2017年3月14日 上午11:01:53 描述
      *
      * @param request
      * @return ResultBean<Object>
      *         查询结果
     */
    @RequestMapping(value = "query", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<Object> query(HttpServletRequest request) {
        ResultBean<Object> result = new ResultBean<>();
        logger.info("WdElevenCommentWallController query begin,params:{}",
            request.getParameterMap());
        try {
            result = service.query(request);
            logger.info("WdElevenCommentWallController query end,result:{}",
                result);
        } catch (Exception ex) {
            logger.error(
                "WdElevenCommentWallController query error ; Exception:{}",
                Throwables.getStackTraceAsString(ex));
            result.setIsSuccess(false);
            result.setMessage("查询失败");
        }
        return result;
    }

    /**
      * <p>
      *    留言墙后台审核
      * </p>
      *
      * @action
      *    guoyonggang 2017年3月14日 上午11:01:27 描述
      *
      * @param request
      * @return ResultBean<Object>
      *         审核结果
     */
    @RequestMapping(value = "audit", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<Object> audit(HttpServletRequest request) {
        ResultBean<Object> result = new ResultBean<>();
        try {
            //获取OA登陆用户
            logger.info(
                "WdElevenCommentWallController audit begin get OAUserInfo");
            OAUserInfoModel userInfoModel = OAUserRequest
                    .getUserInfoByRequest(request);
            logger.info(
                "WdElevenCommentWallController audit begin get OAUserInfo;result:{}",
                userInfoModel);
            if (userInfoModel == null) {
                result.setMessage("您没登录,请登录后进行审核！");
                return result;
            }

            logger.info("WdElevenCommentWallController audit begin,params:{}",
                request.getParameterMap());

            String auditValue = request.getParameter("auditValue");
            String codes = request.getParameter("codes");
            if (StringUtils.isEmpty(auditValue)) {
                result.setMessage("审核状态错误");
                return result;
            }
            if (StringUtils.isEmpty(codes)) {
                result.setMessage("至少选择一条留言");
                return result;
            }
            WdElevenCommentWallBean record = new WdElevenCommentWallBean();
            record.setCodes(codes);
            record.setIsAudit(Integer.parseInt(auditValue));
            // 设置审核人的姓名
            record.setAuditor(userInfoModel.getRealName());
            record.setAuditTime(new Date());
            boolean isSuccess = bll.update(record);
            result.setIsSuccess(isSuccess);
            result.setMessage(!isSuccess ? "审核失败，请稍后重试！" : auditValue.equals("1") ? "留言已通过" : "留言已拒绝");
            logger.info("WdElevenCommentWallController audit end,result:{}",
                result);
        } catch (Exception ex) {
            logger.error(
                "WdElevenCommentWallController audit error ; Exception:{}",
                Throwables.getStackTraceAsString(ex));
            result.setIsSuccess(false);
            result.setMessage("审核失败，稍后重试！");
        }
        return result;
    }
}
