/**
  * -------------------------------------------------------------------------
  * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
  * @版权所有：北京光宇在线科技有限责任公司
  * @项目名称：action-wd11thyearscommentwall
  * @作者：caishuai
  * @联系方式：caishuai@gyyx.cn
  * @创建时间：2017年3月9日 上午10:58:09
  * @版本号：0.0.1
  *-------------------------------------------------------------------------
  */
package cn.gyyx.action.service.wd11thyearscommentwall.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;

import com.google.common.base.Throwables;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.wd11thyearscommentwall.CommentWallBean;
import cn.gyyx.action.beans.wd11thyearscommentwall.Constant;
import cn.gyyx.action.bll.wd11thyearscommentwall.CommentWallBll;
import cn.gyyx.action.bll.wd11thyearscommentwall.impl.CommentWallBllImpl;
import cn.gyyx.action.cache.MemcacheUtil;
import cn.gyyx.action.service.wd11thyearscommentwall.CommentWallService;
import cn.gyyx.core.memcache.XMemcachedClientAdapter;
import cn.gyyx.distribute.lock.DistributedLock;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import net.rubyeye.xmemcached.XMemcachedClient;

/**
 * <p>
 * CommentWallServiceImpl描述
 * </p>
 * 
 * @author caishuai
 * @since 0.0.1
 */
public class CommentWallServiceImpl implements CommentWallService {
    public static Logger logger = GYYXLoggerFactory
            .getLogger(CommentWallServiceImpl.class);
    private XMemcachedClientAdapter memcached = MemcacheUtil
            .getActionMemcache();
    private CommentWallBll commentWallBll = new CommentWallBllImpl();

    /**
     * 
     * <p>
     * 添加留言实体
     * </p>
     *
     * @action caishuai 2017年3月9日 下午2:09:22 描述
     *
     * @param comment
     *            留言实体
     * @param limit
     *            每日留言限制
     * @return ResultBean<Object>
     */
    @Override
    public ResultBean<Object> addComment(CommentWallBean comment, int limit) {
        ResultBean<Object> result = new ResultBean<>(false, "留言提交失败，请重新提交留言！",
                null);
        DateFormat format = new SimpleDateFormat("yyyy_MM_dd");
        // key:关键字_YYYY-MM-DD用户标志
        String memKey = "wdelevenyearscommentwall_addcomment_"
                + format.format(new Date())
                + comment.getSourceTag().replace(".", "").toLowerCase()
                + comment.getActionCode();
        // 每天最大limit次
        Integer limitCount = 0;
        // lock
        try (DistributedLock lock = new DistributedLock(
                "action" + memKey + "lock")) {
            lock.weakLock(10, 5);
            // 获取缓存
            XMemcachedClient firstClient = memcached.getFirstClient();
            Object object = firstClient.get(memKey);
            logger.info("get memKey{} success result{}", memKey, object);
            if (object != null) {
                limitCount = Integer.parseInt(object.toString());
            } else {
                firstClient.set(memKey, 24 * 60 * 60, "0");
            }
            logger.info("用户memkey:{}当前次数:{}limit:{}",
                new Object[] { memKey, limitCount, limit });
            // 次数记录大于limit次
            if (limitCount >= limit) {
                logger.info("用户memkey:{}评论次数{}超出限制，limit={}",
                    new Object[] { memKey, limitCount, limit });
                result.setProperties(false, "今天已经收到道友的留言啦！！希望道友明天还来哟~", null);
                return result;
            }
            // 未超出次数，插入数据，判定插入是否成功
            if (commentWallBll.addComments(comment)) {
                long incr = firstClient.incr(memKey, 1);
                logger.info("用户memkey:{}评论后次数{}", memKey, incr);
                result.setProperties(true, "谢谢道友的留言！留言通过审核后就会在留言墙上展示出来啦！",
                    null);
            }
        } catch (Exception e) {
            logger.warn("分布式锁异常{}", Throwables.getStackTraceAsString(e));
        }
        return result;
    }

    /**
     * 
     * <p>
     * 获取留言的一页数据，请求时间之前
     * </p>
     *
     * @action caishuai 2017年3月9日 下午5:13:56 描述
     * @action caishuai 2017年3月15日 11:42:24 添加actionCode字段,增加获取某个活动单独的留言功能
     *
     * @param pageSize页大小
     * @param pageIndex页码
     * @param endTime数据截止时间
     * @param cacheTime缓存时间
     * @param actionCode
     *            活动code，为null时默认获取全部
     * @return Map<String, Object>
     */
    @Override
    public Map<String, Object> getCommentsByPage(int pageSize, int pageIndex,
            Date endTime, int cacheTime, Integer actionCode) {
        int start = pageSize * (pageIndex - 1) + 1;
        int end = pageSize * (pageIndex);
        Map<String, Object> result = new HashMap<>();
        // 判定是否为第一次拉取，是则缓存读取，不存在则数据库读取
        if (pageIndex == 1) {
            String memKey = Constant.ACTION_CODE
                    + "wd11thyearscommentwall_getCommentsByPage_" + pageSize
                    + actionCode;
            Map<String, Object> tempResult = memcached.get(memKey, Map.class);
            if (tempResult == null) {
                result.put("list", commentWallBll.getCommentsByPage(start, end,
                    new Date(), actionCode));
                result.put("Total",
                    commentWallBll.getCommentsCount(new Date(), actionCode));
                memcached.set(memKey, cacheTime, result);
            } else {
                result.putAll(tempResult);
            }
        } else {
            result.put("list", commentWallBll.getCommentsByPage(start, end,
                endTime, actionCode));
            result.put("Total",
                commentWallBll.getCommentsCount(endTime, actionCode));
        }
        return result;
    }

}
