/**
  * -------------------------------------------------------------------------
  * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
  * @版权所有：北京光宇在线科技有限责任公司
  * @项目名称：action-wd11thyearscommentwall-oa
  * @作者：guoyonggang
  * @联系方式：guoyonggang@gyyx.cn
  * @创建时间：2017年3月13日 下午4:14:09
  * @版本号：0.0.1
  *-------------------------------------------------------------------------
  */
package cn.gyyx.action.oa.wd11thyearscommentwall.bll;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.gyyx.action.oa.wd11thyearscommentwall.beans.WdElevenCommentWallBean;
import cn.gyyx.action.oa.wd11thyearscommentwall.dao.WdElevenCommentWallDAO;

/**
 * <p>
 * 问道留言墙后台BLL层操作
 * </p>
 * 
 * @author guoyonggang
 * @since 0.0.1
 */
public class WdElevenCommentWallBLL {
    private WdElevenCommentWallDAO dao = new WdElevenCommentWallDAO();

    /**
     * <p>
     * 更新问道留言墙审核信息
     * </p>
     *
     * @action guoyonggang 2017年3月13日 下午5:17:34 描述
     *
     * @param record
     * @return boolean
     */
    public boolean update(WdElevenCommentWallBean record) {
        return dao.update(record);
    }

    /**
     * <p>
     * 分页查询评论数据
     * </p>
     *
     * @action guoyonggang 2017年3月13日 下午6:14:27 描述
     *
     * @param pageIndex
     *            页码
     * @param pageSize
     *            页面尺寸
     * @param forwardPage
     *            当前页前面的总条数
     * @param strWhere
     *            查询条件
     * @return List<WdElevenCommentWallBean> 查询结果
     * @throws ParseException
     */
    public List<Object> queryElevenYearsCommentList(Integer pageIndex,
            Integer pageSize, Integer forwardPage, String auditStatus,
            String beginTime, String endTime, String keyWord)
            throws ParseException {
        List<Object> result = new ArrayList<>();
        List<WdElevenCommentWallBean> list = dao.queryElevenYearsCommentList(
            pageIndex, pageSize, forwardPage, auditStatus, beginTime, endTime,
            keyWord);
        if (list == null) {
            return result;
        }
        for (WdElevenCommentWallBean item : list) {
            result.add(item);
        }
        return result;
    }

    /**
     * 
     * <p>
     * 查询评论总数
     * </p>
     *
     * @action guoyonggang 2017年3月13日 下午4:37:12 描述
     *
     * @param strWhere
     *            查询条件
     * @return int 总条数
     * @throws ParseException
     */
    public int queryElevenYearsCommentCount(String auditStatus,
            String beginTime, String endTime, String keyWord)
            throws ParseException {
        return dao.queryElevenYearsCommentCount(auditStatus, beginTime, endTime,
            keyWord);
    }

    /**
     * <p>
     * 获取评论墙统计数据
     * </p>
     *
     * @action guoyonggang 2017年3月13日 下午5:08:54 描述
     *
     * @param strWhere
     *            查询条件
     * @return List<Map<String,Object>> 查询结果
     * @throws ParseException
     */
    public List<Map<String, Object>> queryElevenYearsCommentStatistics(
            String auditStatus, String beginTime, String endTime,
            String keyWord) throws ParseException {
        return dao.queryElevenYearsCommentStatistics(auditStatus, beginTime,
            endTime, keyWord);
    }
}
