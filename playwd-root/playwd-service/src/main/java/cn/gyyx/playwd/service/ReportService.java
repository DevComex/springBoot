/**
   * -------------------------------------------------------------------------
   * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
   * @版权所有：北京光宇在线科技有限责任公司
   * @项目名称：玩家天地
   * @作者：李杜迪
   * @联系方式：lidudi@gyyx.cn
   * @创建时间：2017年3月27日下午4:01:04
   * @版本号：1.0.0
   *-------------------------------------------------------------------------
   */
package cn.gyyx.playwd.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.gyyx.playwd.beans.common.PageBean;
import cn.gyyx.playwd.beans.common.ResultBean;
import cn.gyyx.playwd.beans.playwd.CommentBean;
import cn.gyyx.playwd.beans.playwd.ReportBean;
import cn.gyyx.playwd.beans.playwd.ReportBean.ReportStatus;
import cn.gyyx.playwd.bll.CommentBll;
import cn.gyyx.playwd.bll.ReportBll;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;

/**
 * 推荐管理
 * 
 * @author lidudi
 *
 */
@Service
public class ReportService {

    private ReportBll reportBll;

    private CommentBll commentBll;

    public ResultBean<String> insert(ReportBean bean) {
        // 查询评论
        CommentBean comment = commentBll.get(bean.getCommentId());
        if (comment == null) {
            return new ResultBean<>(false, "举报对象不存在", "");
        }
        //自己不能举报自己
        if (comment.getFromUserId().equals(bean.getReportUserId())) {
            return new ResultBean<>(false, "请勿自残", "");
        }
        //查询此人是否有对此评论有过举报
        if(reportBll.selectUserReport(bean.getReportUserId(),bean.getCommentId())){
            return new ResultBean<>(false, "每人只能举报一次", "");
        }
        // 插入举报
        reportBll.insert(bean.getCommentId(), comment.getFromUserId(),
            comment.getFromUserAccount(), bean.getReportUserId(),
            bean.getReportUserAccount(), bean.getReason());
        return new ResultBean<>(true, "操作成功", "");
    }

    /**
     * 获取举报管理列表
     * 
     * @return
     */
    public PageBean<Map<String, Object>> getManagementList(Date startDate,
            Date endDate, String contentType, String status,
            String commentAccount, String reportAccount, int pageSize,
            int currentPage) {
        // 获取数量
        int count = reportBll.getReportManagementCount(startDate, endDate,
            contentType, status, commentAccount, reportAccount);
        if (count <= 0) {
            return PageBean.createPage("success", 0, currentPage, pageSize,
                null, "成功");
        }

        // 获取列表
        List<ReportBean> list = reportBll.getReportManagementList(startDate,
            endDate, contentType, status, commentAccount, reportAccount,
            pageSize, currentPage);

        List<Map<String, Object>> ResultBeanList = FluentIterable.from(list)
                .transform(new Function<ReportBean, Map<String, Object>>() {

                    @Override
                    public Map<String, Object> apply(ReportBean info) {

                        SimpleDateFormat format = new SimpleDateFormat(
                                "yyyy-MM-dd HH:mm:ss");
                        String strDate = format.format(info.getCreateTime());

                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("code", info.getCode());
                        map.put("commentId", info.getCommentId());
                        map.put("content", info.getCommentBean().getContent());
                        map.put("reason", info.getReason());
                        map.put("reportTime", strDate);
                        map.put("commentAccount", info.getCommentUserAccount());
                        map.put("commentAccountStatus", "正常");
                        map.put("reportAccount", info.getReportUserAccount());
                        map.put("reportAccountStatus", "正常");
                        if (info.getStatus()
                                .equals(ReportStatus.processed.Value())) {
                            map.put("status", "已处理");
                            map.put("result",
                                info.getCommentBean().isShow() ? "展示" : "隐藏");
                        } else {
                            map.put("status", "未处理");
                            map.put("result", "/");
                        }
                        return map;
                    }
                }).toList();

        return PageBean.createPage("success", count, currentPage, pageSize,
            ResultBeanList, "成功");
    }

    /**
     * 举报处理
     * 
     * @param commentId
     * @param reportId
     * @param commentStatus
     * @return
     */
    @Transactional
    public ResultBean<Object> changeProcess(Integer reportId,String commentStatus, String operatorAccount) {
    	
        ReportBean reportBean = reportBll.getReportInfo(reportId);
        if (reportBean == null || reportBean.getStatus().equals(ReportStatus.processed.Value())) {
            return new ResultBean<Object>("incorrect-status", "举报已经处理过", null);
        }
        
        // 修改状态为processed 已处理
        reportBll.changeReport(reportId, operatorAccount);

        // 隐藏评论或回复操作
        if (commentStatus.equals("hidden")) {
            commentBll.changeCommentIsShow(reportBean.getCommentId(), false);
        }

        return new ResultBean<Object>("success", "成功", null);
    }

    public ReportBll getReportBll() {
        return reportBll;
    }

    @Autowired
    public void setReportBll(ReportBll reportBll) {
        this.reportBll = reportBll;
    }

    public CommentBll getCommentBll() {
        return commentBll;
    }

    @Autowired
    public void setCommentBll(CommentBll commentBll) {
        this.commentBll = commentBll;
    }
}
