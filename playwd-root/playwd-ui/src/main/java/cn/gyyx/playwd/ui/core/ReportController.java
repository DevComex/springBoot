/**
   * -------------------------------------------------------------------------
   * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
   * @版权所有：北京光宇在线科技有限责任公司
   * @项目名称：玩家天地
   * @作者：李杜迪
   * @联系方式：lidudi@gyyx.cn
   * @创建时间：2017年3月27日下午3:47:26
   * @版本号：1.0.0
   *-------------------------------------------------------------------------
   */
package cn.gyyx.playwd.ui.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.core.auth.SignedUser;
import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.playwd.beans.common.ResultBean;
import cn.gyyx.playwd.beans.playwd.ReportBean;
import cn.gyyx.playwd.bll.CommentBll;
import cn.gyyx.playwd.service.ReportService;

/**
 * 举报管理
 * 
 * @author lidudi
 *
 */
@Controller
@RequestMapping("/report")
public class ReportController {

    private static final Logger logger = GYYXLoggerFactory
            .getLogger(ReportController.class);

    /**
     * 回复
     */
    @Autowired
    private ReportService reportService;

    /**
     * 评论
     */
    @Autowired
    CommentBll commentBll;

    /**
     * 添加举报信息
     */
    @RequestMapping(value = "", method = { RequestMethod.POST })
    @ResponseBody
    public ResultBean<String> report(ReportBean bean,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            UserInfo user = SignedUser.getUserInfo(request);
            if (user == null) {
                return new ResultBean<>(false, "您还未登陆", "no-login");
            }
            if (StringUtils.isEmpty(bean.getReason())) {
                return new ResultBean<>(false, "举报原因不能为空", "");
            }
            if (bean.getCommentId() == null) {
                return new ResultBean<>(false, "举报对象不能为空", "");
            }
            bean.setReportUserAccount(user.getAccount());
            bean.setReportUserId(user.getUserId());
            return reportService.insert(bean);
        } catch (Exception e) {
            logger.error("添加举报接口异常:", e);
            return new ResultBean<>(false, "添加举报接口异常");
        }
    }
}
