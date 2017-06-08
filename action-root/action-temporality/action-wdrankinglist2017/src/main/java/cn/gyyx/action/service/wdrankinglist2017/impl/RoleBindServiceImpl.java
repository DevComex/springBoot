/**
  * -------------------------------------------------------------------------
  * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
  * @版权所有：北京光宇在线科技有限责任公司
  * @项目名称：action-wdrankinglist2017
  * @作者：niushuai
  * @联系方式：niushuai@gyyx.cn
  * @创建时间：2017年4月5日 下午2:50:23
  * @版本号：0.0.1
  *-------------------------------------------------------------------------
  */
package cn.gyyx.action.service.wdrankinglist2017.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import org.apache.commons.lang.time.DateUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import com.google.common.base.Throwables;
import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.po.ActionPrizesAddressPO;
import cn.gyyx.action.beans.novicecard.ProcessResult;
import cn.gyyx.action.beans.novicecard.ServerInfoBean;
import cn.gyyx.action.beans.wdrankinglist2017.Constants;
import cn.gyyx.action.beans.wdrankinglist2017.DeclarationBean;
import cn.gyyx.action.beans.wdrankinglist2017.RoleBindBean;
import cn.gyyx.action.beans.weixin.dictionary.AttentionDictionary;
import cn.gyyx.action.bll.lottery.IActionPrizesAddressBLL;
import cn.gyyx.action.bll.lottery.impl.ActionPrizesAddressBLLImpl;
import cn.gyyx.action.bll.novicecard.GetMD5PasswordBLL;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.action.dao.wdrankinglist2017.DeclarationDao;
import cn.gyyx.action.dao.wdrankinglist2017.RoleBindDao;
import cn.gyyx.action.service.agent.CallWebApiActivateAccount;
import cn.gyyx.action.service.agent.CallWebApiAgent;
import cn.gyyx.action.service.agent.CallWebServiceAgent;
import cn.gyyx.action.service.insurance.MemcacheUtil;
import cn.gyyx.action.service.wdrankinglist2017.CommonService;
import cn.gyyx.action.service.wdrankinglist2017.RoleBindService;
import cn.gyyx.action.service.weixin.WeChatAttention;
import cn.gyyx.action.ui.wdrankinglist2017.Wdrankinglist2017Controller;
import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.core.memcache.XMemcachedClientAdapter;
import cn.gyyx.distribute.lock.DLockException;
import cn.gyyx.distribute.lock.DistributedLock;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import net.sf.json.JSONObject;

/**
 * <p>
 * 账号绑定service
 * </p>
 * 
 * @author niushuai
 * @since 0.0.1
 */
public class RoleBindServiceImpl implements RoleBindService {

    private RoleBindDao roleBindDao = new RoleBindDao();
    private static final Logger LOG = GYYXLoggerFactory
            .getLogger(Wdrankinglist2017Controller.class);
    private MemcacheUtil memcacheUtil = new MemcacheUtil();
    private XMemcachedClientAdapter memcache = memcacheUtil.getMemcache();
    private IActionPrizesAddressBLL actionPrizesAddressBll = new ActionPrizesAddressBLLImpl();
    private DeclarationDao declarationDao = new DeclarationDao();
    private CallWebApiAgent callWebApiAgent = new CallWebApiAgent();
    private GetMD5PasswordBLL getMD5PassBll = new GetMD5PasswordBLL();
    private CommonService commonService = new CommonServiceImpl();

    /**
     * <p>
     * 查询账号绑定信息
     * </p>
     *
     * @action niushuai 2017年4月5日 下午6:45:02 描述
     *
     * @param account
     * @return RoleBindBean
     */
    public RoleBindBean getRoleBindBeanByOpenId(String openId) {
        String memKey = Constants.CACHE_ROLEBIND_PREFIX + openId;
        RoleBindBean roleBindBean = memcache.get(memKey, RoleBindBean.class);
        if (roleBindBean == null || roleBindBean.getCode() <= 0) {
            roleBindBean = roleBindDao.getRoleBindBeanByOpenId(openId);
            if (roleBindBean != null)
                memcache.set(memKey, 60, roleBindBean);
        }
        return roleBindBean;
    }

    /**
     * <p>
     * 添加账号绑定
     * </p>
     *
     * @action niushuai 2017年4月6日 上午10:15:06 描述
     *
     * @param roleBindBean
     * @return int
     */
    public int insertRoleBind(RoleBindBean roleBindBean) {
        return roleBindDao.insertRoleBind(roleBindBean);
    }

    /**
     * <p>
     * 绑定账号信息
     * </p>
     *
     * @action niushuai 2017年4月6日 下午4:54:00 描述
     *
     * @param roleBind
     *            void
     * @param userInfo
     * @param openId
     */
    public ResultBean<String> bindRoleInfo(RoleBindBean roleBind,
            UserInfo userInfo) {
        ResultBean<String> result = new ResultBean<String>();

        // result.setIsSuccess(false);
        // result.setMessage("绑定异常，请重试！");
        // 验证微信关注状态
        JSONObject jsonWXUserInfo = null;
        if (!commonService.isDebug()) {
            WeChatAttention att = new WeChatAttention();
            // 调用微信接口
            jsonWXUserInfo = att.getWeXinUserInfo("Wd", roleBind.getOpenId());
            if (!jsonWXUserInfo
                    .containsKey(AttentionDictionary.ATTENTION_SUBSCRIBE)) {
                result.setIsSuccess(false);
                result.setMessage("微信接口调用失败,info:" + roleBind.getOpenId() + ","
                        + att.isAttention("Wd", roleBind.getOpenId()));
                return result;
            } else if (!"1".equals(jsonWXUserInfo
                    .getString(AttentionDictionary.ATTENTION_SUBSCRIBE))) {
                result.setIsSuccess(false);
                result.setMessage("请先关注问道微信！");
                return result;
            } else if (!jsonWXUserInfo
                    .containsKey(AttentionDictionary.ATTENTION_NICK_NAME)) {
                result.setIsSuccess(false);
                result.setMessage("错误，微信已关注，但未能获取昵称");
                return result;
            }
        }

        try (DistributedLock lock = new DistributedLock(
                Constants.HD_CODE + userInfo.getAccount() + "bindRoleInfo")) {
            // 分布式锁闭
            lock.weakLock(30, 0);
            // 检测此人信息是否存在
            RoleBindBean data = roleBindDao
                    .getRoleBindBeanByOpenId(roleBind.getOpenId());
            if (data != null) {
                result.setIsSuccess(false);
                result.setMessage("您已经绑定过角色！");
                return result;
            }
            RoleBindBean data2 = roleBindDao
                    .getRoleBindBeanByAccount(userInfo.getAccount());
            if (data2 != null) {
                result.setIsSuccess(false);
                result.setMessage("您的社区账号已经参与过活动了！");
                return result;
            }

            roleBind.setUserId(userInfo.getUserId());
            roleBind.setAccount(userInfo.getAccount());
            if (commonService.isDebug()) {
                roleBind.setWxNick("昵称");
            } else {
                roleBind.setWxNick(jsonWXUserInfo
                        .getString(AttentionDictionary.ATTENTION_NICK_NAME));
            }

            roleBind.setCreateTime(new Date());
            int count = roleBindDao.insertRoleBind(roleBind);
            if (count == 1) {
                result.setIsSuccess(true);
                result.setMessage("操作成功！");
                result.setData("绑定账号信息完成！");
            } else {
                result.setIsSuccess(false);
                result.setMessage("操作失败！");
                result.setData("不能重复绑定！");
            }

            LOG.info(Constants.HD_NAME + "bindRoleInfo "
                    + "玩家账号信息绑定完毕，准备发放冲榜资格码，玩家账号是：" + userInfo.getAccount());

            result = activiteAndSendItem(userInfo);
            if (!result.getIsSuccess()) {
                return result;
            }
            result.setIsSuccess(true);
            result.setMessage("操作成功！");

        } catch (DLockException e) {
            result.setIsSuccess(false);
            result.setMessage("网络异常");
            LOG.error("玩家绑定账号异常！{}", Throwables.getStackTraceAsString(e));
        } catch (Exception e) {
            result.setIsSuccess(false);
            result.setMessage("网络异常");
            LOG.error("玩家绑定账号异常！{}", Throwables.getStackTraceAsString(e));
        }
        // 账号绑定到微信，附加流程，失败也不会影响到活动的进行
        if (result.getIsSuccess()) {
            bindAccountToWechat(roleBind.getOpenId(), userInfo.getAccount(),
                userInfo.getUserId());
        }
        return result;
    }

    /**
     * <p>
     * 社区账号绑定到微信
     * </p>
     * 无论成功或者失败，都不会返回异常
     * 
     * @action niushuai 2017年4月12日 下午9:09:43 描述
     *
     * @param openId
     * @param account
     *            void
     */
    private void bindAccountToWechat(String openId, String account,
            int userId) {
        try {
            ResultBean<String> result = callWebApiAgent
                    .getAccountByOpenId(openId);
            if (result.getIsSuccess() == true && result.getStateCode() == 1) {
                callWebApiAgent.roleBindWechatAcount(openId, account, userId);
            }
        } catch (Exception e) {
            LOG.warn(Constants.ERROR_LOG + "社区账号绑定到微信异常！异常信息：{}",
                Throwables.getStackTraceAsString(e));
        }
    }

    /**
     * 
     * <p>
     * 游戏激活并发放新服冲榜资格码
     * </p>
     *
     * @action laixiancai 2017年4月11日 下午5:30:14 描述
     *
     * @param userInfo
     * @return ResultBean<String>
     */
    private ResultBean<String> activiteAndSendItem(UserInfo userInfo) {
        LOG.info("start activiteAndSendItem");
        ResultBean<String> result = new ResultBean<>();
        int serverCode = commonService.getServerCode();

        ResultBean<String> rb = callWebApiAgent.accountIsActivated(
            userInfo.getAccount(), String.valueOf(serverCode));
        if (rb.getIsSuccess()) {
            LOG.info("激活查询成功");
            if ("not".equals(rb.getMessage())) {
                LOG.info("用户未激活，尝试获得MD5密码");
                // 获得MD5密码
                cn.gyyx.action.beans.novicecard.ResultBean<String> passResult = getMD5PassBll
                        .getPassword(userInfo.getLoginID());
                // 激活账户
                if (passResult.getIsSuccess()) {
                    LOG.info("MD5密码已获得，尝试激活服务器");
                    CallWebApiActivateAccount accountActivater = new CallWebApiActivateAccount(
                            "2", String.valueOf(serverCode),
                            userInfo.getAccount(), passResult.getData(),
                            userInfo.getLoginIP());
                    cn.gyyx.action.beans.novicecard.ResultBean<String> activateResult = accountActivater
                            .executeActivate();
                    LOG.info("activateResult: {}",
                        JSONObject.fromObject(activateResult));
                    if (!activateResult.getIsSuccess()) {
                        LOG.info("未能激活服务器：" + activateResult.getMessage());
                        result.setIsSuccess(false);
                        result.setMessage(
                            "服务器激活失败: " + activateResult.getMessage());
                        return result;
                    }
                } else {
                    LOG.info("获得MD5密码失败：" + passResult.getMessage());
                    result.setIsSuccess(false);
                    result.setMessage("获得MD5密码失败:" + passResult.getMessage());
                    return result;
                }
            }
        } else {
            LOG.info("激活查询失败");
            result.setIsSuccess(false);
            result.setMessage("激活查询失败");
            return result;

        }
        LOG.info("准备发送冲榜码，获得服务器");
        try {
            // 获取服务器信息
            ServerInfoBean serverInfo = callWebApiAgent
                    .getServerStatusFromWebApi(serverCode);

            LOG.info("服务器已获得，准备发送物品");
            // 向服务器发物品
            ProcessResult sendRes = CallWebServiceAgent.givePresents(2,
                userInfo.getAccount(),
                Constants.SPECIFIED_PRIZE_NAME_CHONGBANGMA, genExpTime(), "",
                serverInfo);
            if (sendRes != null) {
                LOG.info("物品发送接口调用成功");
                if (sendRes.getErrorCode() != 0) {
                    LOG.info("发送冲榜码失败，account: " + userInfo.getAccount()
                            + " ;ITEM_NAME: "
                            + Constants.SPECIFIED_PRIZE_NAME_CHONGBANGMA
                            + " ;serverId: " + serverCode);
                    result.setIsSuccess(false);
                    result.setMessage(sendRes.getDescription());
                    return result;
                } else {
                    LOG.info("发送冲榜码成功，account: " + userInfo.getAccount()
                            + " ;ITEM_NAME: "
                            + Constants.SPECIFIED_PRIZE_NAME_CHONGBANGMA
                            + " ;serverId: " + serverCode);
                    result.setIsSuccess(true);
                    result.setMessage(sendRes.getDescription());
                    return result;
                }
            } else {
                LOG.info("物品发送接口调用失败");
                result.setIsSuccess(false);
                result.setMessage("发放冲榜码物品发送接口调用失败");
                return result;
            }
        } catch (Exception e) {
            LOG.error(Constants.HD_NAME + "发放新服冲榜码异常：{}",
                Throwables.getStackTraceAsString(e));
            result.setIsSuccess(false);
            result.setMessage("发放新服冲榜码异常");
            return result;
        }
    }

    /**
     * 
     * <p>
     * 获取礼包的过期时间
     * </p>
     *
     * @action laixiancai 2017年4月11日 下午5:07:59 描述
     *
     * @return String
     */
    private String genExpTime() {
        Calendar c = java.util.Calendar.getInstance(TimeZone.getDefault(),
            Locale.CHINA);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        c.add(Calendar.YEAR, 1);
        java.util.Date newdate = c.getTime();
        return formatter.format(newdate);
    }

    /**
     * <p>
     * 获取用户的邮寄地址
     * </p>
     *
     * @action niushuai 2017年4月8日 下午1:16:10 描述
     *
     * @param openId
     * @return ResultBean<LotteryPrizesVO>
     */
    public ResultBean<ActionPrizesAddressPO> getUserAddress(String openId) {
        ResultBean<ActionPrizesAddressPO> result = new ResultBean<ActionPrizesAddressPO>();
        result.setIsSuccess(false);
        result.setMessage("查询邮寄地址异常，请重试！");
        RoleBindBean rolebind = roleBindDao.getRoleBindBeanByOpenId(openId);
        if (rolebind == null) {
            result.setIsSuccess(false);
            result.setMessage(Constants.MSG_NOT_YET_ROLEBIND);
            return result;
        }
        ActionPrizesAddressPO address = actionPrizesAddressBll.get(
            Constants.HD_CODE, rolebind.getAccount(), Constants.HD_TYPE);
        if (address != null) {
            result.setData(address);
            result.setIsSuccess(true);
            result.setMessage("查询成功！");
        } else {
            result.setIsSuccess(false);
            result.setMessage("您还没有添加地址！");
        }
        return result;
    }

    /**
     * <p>
     * 添加用户的邮寄地址
     * </p>
     *
     * @action niushuai 2017年4月8日 下午1:51:14 描述
     *
     * @param address
     * @param openId
     * @return ResultBean<String>
     */
    public ResultBean<String> addAddress(ActionPrizesAddressPO address,
            String openId) {
        ResultBean<String> result = new ResultBean<String>();
        try {
            RoleBindBean rolebind = roleBindDao.getRoleBindBeanByOpenId(openId);
            if (rolebind == null) {
                result.setIsSuccess(false);
                result.setMessage(Constants.MSG_NOT_YET_ROLEBIND);
                return result;
            }
            try (SqlSession session = MyBatisConnectionFactory
                    .getSqlActionDBV2SessionFactory().openSession(false)) {
                address.setActivityId(Constants.HD_CODE);
                address.setActivityType(Constants.HD_TYPE);
                address.setUserCode(rolebind.getUserId());
                address.setUserId(rolebind.getAccount());
                actionPrizesAddressBll.post(address, session);
                session.commit();
            }
            LOG.info(Constants.HD_NAME + "，用户更新地址信息" + address);
            result.setIsSuccess(true);
            result.setMessage("更新成功！");
        } catch (Exception e) {
            LOG.error(Constants.ERROR_LOG + "更新用户地址时失败!{}",
                Throwables.getStackTraceAsString(e));
            result.setIsSuccess(false);
            result.setMessage("更新失败！");
        }
        return result;
    }

    /**
     * 
     * <p>
     * 根据openId查询玩家的报名情况
     * </p>
     *
     * @action laixiancai 2017年4月11日 上午9:13:11 描述
     *
     * @param openId
     * @return ResultBean<Boolean>
     */
    public ResultBean<Boolean> checkApply(String openId) {
        ResultBean<Boolean> result = new ResultBean<Boolean>();
        result.setProperties(false, "查询异常！", false);
        RoleBindBean rolebind = roleBindDao.getRoleBindBeanByOpenId(openId);
        if (rolebind == null) {
            result.setProperties(false, "您还没有绑定账号！", false);
            return result;
        }
        DeclarationBean declaration = declarationDao
                .getDeclarationByOpenId(openId);
        if (declaration == null) {
            result.setIsSuccess(false);
            result.setProperties(false, "请先填写宣言！", false);
            return result;
        }
        if (Constants.DECLARATION_STATUS_PASS != declaration.getStatus()) {
            result.setIsSuccess(false);
            result.setProperties(false, "您的宣言还没有审核通过！", false);
            return result;
        }

        result.setProperties(true, "查询成功！", true);
        return result;
    }

    /**
     * <p>
     * 获取抽奖次数
     * </p>
     *
     * @action niushuai 2017年4月10日 下午1:44:05 描述
     *
     * @param openId
     * @return ResultBean<Integer>
     */
    public ResultBean<Integer> getLotteryTimes(String openId) {
        ResultBean<Integer> result = new ResultBean<Integer>();
        result.setProperties(false, "查询异常！", 0);
        RoleBindBean rolebind = roleBindDao.getRoleBindBeanByOpenId(openId);
        if (rolebind == null) {
            result.setProperties(false, "您还没有绑定账号！", 0);
            return result;
        }
        DeclarationBean declaration = declarationDao
                .getDeclarationByOpenId(openId);
        if (declaration == null) {
            result.setIsSuccess(false);
            result.setProperties(false, "请先填写宣言！", 0);
            return result;
        }
        if (Constants.DECLARATION_STATUS_PASS != declaration.getStatus()) {
            result.setIsSuccess(false);
            result.setProperties(false, "您的宣言还没有审核通过！", 0);
            return result;
        }
        // 抽奖时间为空，则说明没抽过奖，如果抽奖时间是今天，则说明当天已抽过
        if (rolebind.getLotteryTime() != null
                && DateUtils.isSameDay(new Date(), rolebind.getLotteryTime())) {
            result.setIsSuccess(false);
            result.setProperties(false, "您今天已经抽过奖了！", 0);
            return result;
        }

        // 玩家等级未达到80级且道行未达到400年
        if (declaration.getGrade() < 80
                && (declaration.getDaohang() / 360) < 400) {
            result.setIsSuccess(false);
            result.setProperties(false, "您的角色等级或道行未达到要求！", 0);
            return result;
        }
        result.setProperties(true, "查询成功！", 1);
        return result;
    }

    /**
     * 
     * <p>
     * 获取抽奖次数（重载）
     * </p>
     *
     * @action laixiancai 2017年4月11日 上午9:13:14 描述
     *
     * @param openId
     * @param sqlSession
     * @return ResultBean<Integer>
     */
    public ResultBean<Integer> getLotteryTimes(String openId,
            SqlSession sqlSession) {
        ResultBean<Integer> result = new ResultBean<Integer>();
        result.setProperties(false, "查询异常！", 0);
        RoleBindBean rolebind = roleBindDao.getRoleBindBeanByOpenId(openId,
            sqlSession);
        if (rolebind == null) {
            result.setProperties(false, "您还没有绑定账号！", 0);
            return result;
        }
        DeclarationBean declaration = declarationDao
                .getDeclarationByOpenId(openId, sqlSession);
        if (declaration == null) {
            result.setIsSuccess(false);
            result.setProperties(false, "请先填写宣言！", 0);
            return result;
        }
        if (Constants.DECLARATION_STATUS_PASS != declaration.getStatus()) {
            result.setIsSuccess(false);
            result.setProperties(false, "您的宣言还没有审核通过！", 0);
            return result;
        }
        // 抽奖时间为空，则说明没抽过奖，如果抽奖时间是今天，则说明当天已抽过
        if (rolebind.getLotteryTime() != null
                && DateUtils.isSameDay(new Date(), rolebind.getLotteryTime())) {
            result.setIsSuccess(false);
            result.setProperties(false, "您今天已经抽过奖了！", 0);
            return result;
        }

        // 玩家等级未达到80级且道行未达到400年
        if (declaration.getGrade() < 80
                && (declaration.getDaohang() / 360) < 400) {
            result.setIsSuccess(false);
            result.setProperties(false, "您的角色等级或道行未达到要求！", 0);
            return result;
        }
        result.setProperties(true, "查询成功！", 1);
        return result;
    }
}
