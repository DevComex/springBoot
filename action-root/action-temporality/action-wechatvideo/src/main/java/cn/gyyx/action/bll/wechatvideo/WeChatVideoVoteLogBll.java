/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 *
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：
 * @作者：tanjunkai        
 * @联系方式：tanjunkai@gyyx.cn
 * @创建时间：2017/3/13 10:48
 * @版本号：0.0.1 -------------------------------------------------------------------------
 */
package cn.gyyx.action.bll.wechatvideo;

import cn.gyyx.action.beans.wechatvideo.Constants;
import cn.gyyx.action.beans.wechatvideo.WeChatVideoRoleBindBean;
import cn.gyyx.action.beans.wechatvideo.WeChatVideoVoteLogBean;

import com.google.common.base.Throwables;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.dao.wechatvideo.WeChatVideoRoleBindDao;
import cn.gyyx.action.dao.wechatvideo.WeChatVideoUpLoadLogDao;
import cn.gyyx.action.dao.wechatvideo.WeChatVideoVoteLogDao;
import cn.gyyx.action.service.wechatvideo.WechatVideoLotteryPrizesService;
import cn.gyyx.distribute.lock.DLockException;
import cn.gyyx.distribute.lock.DistributedLock;
import cn.gyyx.log.sdk.GYYXLogger;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * <p>
 * 点赞记录业务逻辑层
 * </p>
 * 
 * @author tanjunkai
 * @since 0.0.1
 */
public class WeChatVideoVoteLogBll {
    WeChatVideoRoleBindDao weChatVideoRoleBindDao = new WeChatVideoRoleBindDao();
    WeChatVideoVoteLogDao weChatVideoVoteLogDao = new WeChatVideoVoteLogDao();
    WeChatVideoUpLoadLogDao weChatVideoUpLoadLogDao = new WeChatVideoUpLoadLogDao();

    GYYXLogger logger = GYYXLoggerFactory
            .getLogger(WeChatVideoVoteLogBll.class);

    /**
     * <p>
     * 视频点赞
     * </p>
     *
     * @author tanjunkai
     *
     * @param info
     *            视频点赞日志实体
     * @return ResultBean
     */
    public ResultBean videoVote(WeChatVideoVoteLogBean info) {
        ResultBean resultBean = new ResultBean<>();
        // 判断是否已绑定角色
        WeChatVideoRoleBindBean bindingInfo = weChatVideoRoleBindDao
                .selectByUserId(info.getUserId());
        if (bindingInfo == null) {
            resultBean.setIsSuccess(false);
            resultBean.setMessage("请先绑定角色信息");
            return resultBean;
        }
        DistributedLock lock = new DistributedLock(
                Constants.VOTE_PRIFIX + info.getUserId());
        try {
            lock.weakLock(10, 5);
            // 判断该视频该用户是否已经点过赞了
            WeChatVideoVoteLogBean voteInfo = weChatVideoVoteLogDao
                    .selectTodayVoteLog(info.getUserId(), info.getVideoId());
            if (voteInfo != null) {
                resultBean.setIsSuccess(false);
                resultBean.setMessage("一个视频一天只能点赞一次");
                return resultBean;
            }
            // 插入点赞日志,同时更新视频被赞总次数
            int result = weChatVideoVoteLogDao.insertSelective(info);

            if (result > 0) {
                resultBean.setIsSuccess(true);
                resultBean.setMessage("成功点赞");
            } else {
                resultBean.setIsSuccess(false);
                resultBean.setMessage("点赞失败");
            }
        } catch (DLockException e) {
            logger.error(Constants.ERROR_LOG + "【点赞】关闭分布式锁失败！异常：{}",
                Throwables.getStackTraceAsString(e));
            resultBean.setIsSuccess(false);
            resultBean.setMessage("点赞失败！");
        } catch (Exception e) {
            logger.error(Constants.ERROR_LOG + "【点赞】失败！{}",
                Throwables.getStackTraceAsString(e));
            resultBean.setIsSuccess(false);
            resultBean.setMessage("点赞失败！");
        } finally {
            try {
                if (lock != null)
                    lock.close();
            } catch (DLockException e) {
                logger.error(Constants.ERROR_LOG + "【点赞】关闭分布式锁失败！异常：{}",
                    Throwables.getStackTraceAsString(e));
            }
        }
        return resultBean;
    }
}
