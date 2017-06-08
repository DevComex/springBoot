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

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import com.google.common.base.Throwables;

import cn.gyyx.action.beans.predicable.ResultBeanWithPage;
import cn.gyyx.action.beans.wechatvideo.Constants;
import cn.gyyx.action.beans.wechatvideo.WeChatVideoRoleBindBean;
import cn.gyyx.action.beans.wechatvideo.WeChatVideoUpLoadLogBean;
import cn.gyyx.action.bll.config.impl.DefaultHdConfigBLL;
import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.PrizeBean;
import cn.gyyx.action.beans.lottery.po.ActionLotteryLogPO;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.action.dao.lottery.impl.ActionLotteryLogDAOImpl;
import cn.gyyx.action.dao.wechatvideo.WeChatVideoRoleBindDao;
import cn.gyyx.action.dao.wechatvideo.WeChatVideoUpLoadLogBeanMapper;
import cn.gyyx.action.dao.wechatvideo.WeChatVideoUpLoadLogDao;
import cn.gyyx.action.service.wechatvideo.WeChatVideoPrizeService;
import cn.gyyx.action.service.wechatvideo.WeChatVideoService;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * <p>
 * 视频相关业务逻辑层
 * </p>
 * 
 * @author tanjunkai
 * @since 0.0.1
 */
public class WeChatVideoUpLoadLogBll {

    WeChatVideoUpLoadLogDao weChatVideoUpLoadLog = new WeChatVideoUpLoadLogDao();
    WeChatVideoRoleBindDao weChatVideoRoleBindDao = new WeChatVideoRoleBindDao();
    DefaultHdConfigBLL hdConfig = new DefaultHdConfigBLL();
    private static final Logger LOGGER = GYYXLoggerFactory
            .getLogger(WeChatVideoUpLoadLogBll.class);
    private static final SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
            .getSqlActionDBV2SessionFactory();

    /**
     * <p>
     * 审核通过的视频搜索
     * </p>
     *
     * @author tanjunkai
     *
     * @param searchPar
     *            游戏账号、角色名、微信名
     * @return List<WeChatVideoUpLoadLogBean>
     */
    public List<WeChatVideoUpLoadLogBean> videoSearch(String searchPar) {
        return weChatVideoUpLoadLog.videoSearch(searchPar);
    }

    /**
     * <p>
     * 首页根据页码获取视频列表
     * </p>
     *
     * @action tanjunkai 2017年3月15日 下午4:44:31 描述
     *
     * @param index
     *            当前页码
     * @param pageCount
     *            每页显示条数
     * @return List<WeChatVideoUpLoadLogBean>
     */
    public List<WeChatVideoUpLoadLogBean> getVideoByIndex(int index,
            int pageCount) {
        return weChatVideoUpLoadLog.getVideoByIndex(index, pageCount);
    }

    /**
     * <p>
     * 通过主键获取视频信息
     * </p>
     *
     * @author tanjunkai
     *
     * @param code
     *            视频主键
     * @return WeChatVideoUpLoadLogBean
     */
    public WeChatVideoUpLoadLogBean selectByPrimaryKey(Integer code) {
        return weChatVideoUpLoadLog.selectByPrimaryKey(code);
    }

    /**
     * <p>
     * 视频上传
     * </p>
     *
     * @action tanjunkai 2017年3月15日 下午2:48:46 描述
     *
     * @param info
     *            视频上传实体
     * @return ResultBean
     */
    public ResultBean videoUpload(WeChatVideoUpLoadLogBean info) {
        ResultBean resultBean = new ResultBean<>();
        // 判断是否已绑定角色
        WeChatVideoRoleBindBean bindingInfo = weChatVideoRoleBindDao
                .selectByUserId(info.getUserId());
        if (bindingInfo == null) {
            resultBean.setIsSuccess(false);
            resultBean.setMessage("请先绑定角色信息");
            return resultBean;
        }
        resultBean = this.videoUploadJudge(bindingInfo.getUserId());
        if (!resultBean.getIsSuccess()) {
            return resultBean;
        }

        info.setRoleName(bindingInfo.getRoleName());
        info.setWechatAccount(bindingInfo.getWechatAccount());
        info.setServerName(bindingInfo.getServerName());
        info.setUploadTime(new Date());

        int count = weChatVideoUpLoadLog.insertSelective(info);
        if (count > 0) {
            resultBean.setIsSuccess(true);
            resultBean.setMessage("视频上传成功,待小编审核通过后即可参与抽奖");
        } else {
            resultBean.setIsSuccess(false);
            resultBean.setMessage("视频上传失败");
        }
        // 检查上传次数
        return resultBean;
    }

    /**
     * <p>
     * 视频上传的判断
     * </p>
     *
     * @action tanjunkai 2017年3月21日 下午4:24:16 描述
     *
     * @param userId
     * @return ResultBean
     */
    public ResultBean videoUploadJudge(int userId) {
        ResultBean resultBean = new ResultBean<>();
        resultBean.setSuccess(true);
        // 1.首先判断审核通过视频数量是否超过3次
        // 2.获取该用户所有上传视频列表,是否存在正在审核视频(只要存在未审核的视频就不能上传了)
        // 3.如果1,2都通过,则判断该用户今天是否已经上传过视频了(1天只能上传1次视频)

        // 获取该用户所有上传视频列表
        List<WeChatVideoUpLoadLogBean> allUploadList = weChatVideoUpLoadLog
                .getUploadTimesByUserId(userId,
                    Constants.WECHATVIDEO_ALLSTATUS);
        // 已审核列表
        List<WeChatVideoUpLoadLogBean> verifyList = new ArrayList<WeChatVideoUpLoadLogBean>();
        // 未审核列表
        List<WeChatVideoUpLoadLogBean> pendingList = new ArrayList<WeChatVideoUpLoadLogBean>();
        // 今日上传视频
        List<WeChatVideoUpLoadLogBean> todayList = new ArrayList<WeChatVideoUpLoadLogBean>();

        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        for (WeChatVideoUpLoadLogBean bean : allUploadList) {
            if (bean.getVideoStatus() == Constants.WECHATVIDEO_VERIFYED) {
                verifyList.add(bean);
            }
            if (bean.getVideoStatus() == Constants.WECHATVIDEO_VERIFYPENDING) {
                pendingList.add(bean);
            }
            if (fmt.format(bean.getUploadTime()).toString()
                    .equals(fmt.format(new Date()).toString())) {
                todayList.add(bean);
            }
        }
        // 审核通过次数超过3次判断
        if (verifyList.size() >= 3) {
            resultBean.setIsSuccess(false);
            resultBean.setMessage("您已达到上传次数的限制.");
            return resultBean;
        }
        // 存在未审核的视频
        if (pendingList.size() > 0) {
            resultBean.setIsSuccess(false);
            resultBean.setMessage("您有视频正在审核中..请等待审核结果");
            return resultBean;
        }
        // 今日上传视频
        if (todayList.size() > 0) {
            for (WeChatVideoUpLoadLogBean today : todayList) {
                if (today.getVideoStatus() != Constants.WECHATVIDEO_REFUSED) {
                    resultBean.setIsSuccess(false);
                    resultBean.setMessage("您的视频已审核通过,一天只能上传一次视频哦!");
                    break;
                }
            }
        }
        return resultBean;
    }

    /**
     * <p>
     * 视频审核
     * </p>
     *
     * @action tanjunkai 2017年3月16日 上午10:59:24 描述
     *
     * @param videoId
     *            更新的视频ID
     * @param status
     *            视频状态
     * @param auditName
     *            审核人
     * @return ResultBean
     */
    public ResultBean updateByPrimaryKeySelective(int videoId, int status,
            String auditName) {
        ResultBean resultBean = new ResultBean<>();
        try {
            WeChatVideoUpLoadLogBean info = this.selectByPrimaryKey(videoId);
            if (info == null) {
                resultBean.setIsSuccess(false);
                resultBean.setMessage("该视频不存在！");
                return resultBean;
            }
            // 如果是进行【审核通过】处理时,视频格式是【非mp4】的话需要提示
            if (status == Constants.WECHATVIDEO_VERIFYED
                    && !info.getVideoAddress().toLowerCase().endsWith(".mp4")) {
                resultBean.setIsSuccess(false);
                resultBean.setMessage("该视频还未手动进行格式转换！");
                return resultBean;
            }
            info.setAuditor(auditName);
            info.setAuditTime(new Date());
            info.setVideoStatus(status);

            // 需要更换成CDN域名
            Map<String, String> config = new HashMap<>();
            config = hdConfig.getConfigParams(Constants.ACTIVITY_ID, Map.class);
            if (null != config) {
                String cdnAddress = config.get(Constants.CDN_ADDRESS);
                if (null != cdnAddress && !"".equals(cdnAddress)) {
                    URL url = new URL(info.getVideoAddress());
                    String old = url.getHost();
                    String newAddress = info.getVideoAddress().replaceAll(old,
                        cdnAddress);
                    info.setVideoAddress(newAddress);
                }
            }

            int result = weChatVideoUpLoadLog.updateByPrimaryKeySelective(info);
            if (result > 0) {
                //查询前11000名审核通过的用户的视频数量(按照userid过滤)
                boolean canAdd = weChatVideoUpLoadLog.getDistinctUserIdCount(
                    Constants.WECHATVIDEO_VERIFYED + "") <= 11000;
                // 如果是审核通过,上传视频的前11000人(这里用自增的视频作为判断,不再去查库)新增2000银元宝日志(已增加过的不在增加)
                if (status == Constants.WECHATVIDEO_VERIFYED && canAdd) {
                    new WeChatVideoService()
                            .auditAddSilerJudge(info.getUserId());
                }
                resultBean.setIsSuccess(true);
                resultBean.setMessage("更新成功");
            } else {
                resultBean.setIsSuccess(false);
                resultBean.setMessage("更新失败");
            }
        } catch (Exception e) {
            LOGGER.error("OA后台更新视频状态异常{}", Throwables.getStackTraceAsString(e));
            resultBean.setIsSuccess(false);
            resultBean.setMessage("sorry,服务器出点小问题,请稍后重试！");
        }
        return resultBean;
    }

    /**
     * <p>
     * 更新视频地址(oa后台手动转换格式后重新上传后更新)
     * </p>
     *
     * @action tanjunkai 2017年3月23日 上午8:47:13 描述
     *
     * @param videoId
     *            视频 ID
     * @param videoAddress
     *            视频地址
     * @return ResultBean
     */
    public ResultBean updateVideoAddress(int videoId, String videoAddress) {
        ResultBean resultBean = new ResultBean<>();
        WeChatVideoUpLoadLogBean info = this.selectByPrimaryKey(videoId);
        if (info == null) {
            resultBean.setIsSuccess(false);
            resultBean.setMessage("该视频不存在！");
            return resultBean;
        }
        info.setVideoAddress(videoAddress);
        int result = weChatVideoUpLoadLog.updateByPrimaryKeySelective(info);
        if (result > 0) {
            resultBean.setIsSuccess(true);
            resultBean.setMessage("更新成功");
        } else {
            resultBean.setIsSuccess(false);
            resultBean.setMessage("更新失败");
        }
        return resultBean;
    }

    /**
     * <p>
     * 批量更新视频状态
     * </p>
     *
     * @action tanjunkai 2017年3月16日 上午10:57:33 描述
     *
     * @param videoIds
     *            更新的视频ID数组
     * @param status
     *            更新的视频状态
     * @param auditName
     *            审核人
     * @return ResultBean
     */
    public ResultBean updateByBatch(String[] videoIds, int status,
            String auditName) {
        ResultBean resultBean = new ResultBean<>();
        String err = "";
        for (String a : videoIds) {
            resultBean = this.updateByPrimaryKeySelective(Integer.parseInt(a),
                status, auditName);
            if (!resultBean.getIsSuccess()) {
                err += "视频Id:" + a + resultBean.getMessage() + ";";
                continue;
            }
        }
        if (!err.equals(""))
            resultBean.setMessage(err);
        return resultBean;
    }

    /**
     * <p>
     * OA后台审核获取所有上传视频列表
     * </p>
     *
     * @action tanjunkai 2017年3月16日 下午3:35:48 描述
     *
     * @param beginTime
     *            开始时间
     * @param endTime
     *            结束时间
     * @param keyWord
     *            关键字
     * @param verifyStatus
     *            视频状态
     * @param pageSize
     *            每页条数
     * @param pageIndex
     *            当前页码
     * @return ResultBeanWithPage<List<WeChatVideoUpLoadLogBean>>
     */
    public ResultBeanWithPage<List<WeChatVideoUpLoadLogBean>> getVideoList(
            String beginTime, String endTime, String keyWord,
            String verifyStatus, int pageSize, int pageIndex) {
        ResultBeanWithPage<List<WeChatVideoUpLoadLogBean>> listBean = new ResultBeanWithPage<>();

        List<WeChatVideoUpLoadLogBean> list = weChatVideoUpLoadLog.getVideoList(
            beginTime, endTime, keyWord, verifyStatus, pageSize, pageIndex);
        int totalPage = weChatVideoUpLoadLog.getVideoCount(beginTime, endTime,
            keyWord, verifyStatus);
        listBean.setProperties(true, "查询成功", list, totalPage);
        return listBean;
    }

    /**
     * <p>
     * 根据视频状态获取上传过滤用户后视频总数
     * </p>
     *
     * @action tanjunkai 2017年3月25日 下午3:03:38 描述
     *
     * @param verifyStatus
     * @return int
     */
    public int getDistinctUserIdCount(String verifyStatus) {
        return weChatVideoUpLoadLog.getDistinctUserIdCount(verifyStatus);
    }
}
