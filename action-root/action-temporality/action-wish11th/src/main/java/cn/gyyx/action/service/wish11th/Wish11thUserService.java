/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 *
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：
 * @作者：tanjunkai        
 * @联系方式：tanjunkai@gyyx.cn
 * @创建时间：2017/4/1 15:48
 * @版本号：0.0.1 -------------------------------------------------------------------------
 */
package cn.gyyx.action.service.wish11th;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;

import com.google.common.base.Throwables;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.AddressBean;
import cn.gyyx.action.beans.wish11th.Constants;
import cn.gyyx.action.beans.wish11th.Wish11thRoleBindBean;
import cn.gyyx.action.beans.wish11th.Wish11thWishBean;
import cn.gyyx.action.bll.lottery.AddressBLL;
import cn.gyyx.action.bll.lottery.MemcacheUtil;
import cn.gyyx.action.bll.wish11th.Wish11thUserBll;
import cn.gyyx.action.bll.wish11th.Wish11thWishBll;
import cn.gyyx.action.service.agent.CallWebServiceInsuranceAgent;
import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.core.memcache.XMemcachedClientAdapter;
import cn.gyyx.distribute.lock.DistributedLock;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * <p>
 * 用户相关服务类
 * </p>
 * 
 * @author tanjunkai
 * @since 0.0.1
 */
public class Wish11thUserService {

    // 日志访问类
    private static final Logger logger = GYYXLoggerFactory
            .getLogger(Wish11thUserService.class);

    // 缓存操作类
    private MemcacheUtil memcacheUtil = new MemcacheUtil();
    private XMemcachedClientAdapter memcachedClientAdapter = memcacheUtil
            .getMemcache();

    // 许愿用户相关业务逻辑层
    private Wish11thUserBll userBll = new Wish11thUserBll();
    // 许愿内容相关业务逻辑层
    private Wish11thWishBll wishBll = new Wish11thWishBll();
    // 填写地址相关业务逻辑层
    private AddressBLL addressBll = new AddressBLL();

    /**
     * <p>
     * 验证角色
     * </p>
     *
     * @action tanjunkai 2017年4月1日 下午5:37:45 描述
     *
     * @param userInfo
     *            用户信息
     * @param serverId
     *            服务器ID
     * @return ResultBean<String>
     */
    public ResultBean<String> validRoleInfo(UserInfo userInfo, int serverId) {
        ResultBean<String> resultBean = new ResultBean<>();
        int roleCount = 0;
        String roleKey = userInfo.getUserId() + Constants.ACTIVITY_ENGLISHNAME;
        // 获取缓存
        if (memcachedClientAdapter.get(roleKey, int.class) != null) {
            roleCount = memcachedClientAdapter.get(roleKey, int.class);
            memcachedClientAdapter.set(roleKey, 300,
                Integer.toString(roleCount + 1));
        } else {
            // 增加缓存
            memcachedClientAdapter.set(roleKey, 300, Integer.toString(0));
        }

        // 次数记录大于20次
        if (roleCount >= 20) {
            resultBean.setProperties(false, "禁止多次刷新", "");
        } else {
            resultBean.setProperties(true, "获取成功", "");
            resultBean.setData(CallWebServiceInsuranceAgent
                    .getRoleInfo(userInfo.getAccount(), serverId));
        }
        return resultBean;
    }

    /**
     * <p>
     * 添加角色绑定
     * </p>
     *
     * @action tanjunkai 2017年4月1日 下午5:47:13 描述
     *
     * @param userInfo
     * @param roleName
     * @param roleCode
     * @param serverName
     * @param serverId
     * @return ResultBean<String>
     */
    public ResultBean<String> addAccountRole(UserInfo userInfo, String roleName,
            String roleCode, String serverName, int serverId) {
        int userCode = userInfo.getUserId();
        try (DistributedLock lock = new DistributedLock(
                Constants.ROLEBIND_PRIFIX + "_" + userCode + "."
                        + Constants.ACTIVITY_ID)) {
            lock.weakLock(30, 11);
            // 判断角色是否已被绑定
            Wish11thRoleBindBean oldUserinfo = userBll
                    .getUserByRoleID(roleCode);
            if (oldUserinfo != null) {
                return new ResultBean<String>(false, "该角色已经被绑定，请更换其他角色", null);
            }
            ResultBean<String> resultBean = new ResultBean<>();
            try {
                // 判断用户是否已经绑定角色
                Wish11thRoleBindBean bean = userBll
                        .getUserByUserID(userInfo.getUserId());
                if (bean != null) {
                    resultBean.setIsSuccess(false);
                    resultBean.setMessage("该用户已绑定角色信息");
                    return resultBean;
                }
                Wish11thRoleBindBean userRoleBindBean = new Wish11thRoleBindBean();
                userRoleBindBean.setRoleCode(roleCode);
                userRoleBindBean.setRoleName(roleName);
                userRoleBindBean.setServerId(serverId);
                userRoleBindBean.setServerName(serverName);
                userRoleBindBean.setUserId(userCode);
                userRoleBindBean.setAccount(userInfo.getAccount());
                userRoleBindBean.setCreateTime(new Date());
                userRoleBindBean.setScore(0);
                userRoleBindBean.setConsumeScore(0);
                if (userBll.addRoleBind(userRoleBindBean) > 0) {
                    resultBean.setIsSuccess(true);
                    resultBean.setMessage("用户绑定角色信息成功");
                } else {
                    resultBean.setIsSuccess(false);
                    resultBean.setMessage("用户绑定角色信息失败");
                }

            } catch (Exception e) {
                logger.error(Constants.ERROR_LOG + "添加角色绑定发生异常：{}",
                    Throwables.getStackTraceAsString(e));
                resultBean.setIsSuccess(false);
                resultBean.setMessage("绑定用户信息失败");
            }
            return resultBean;
        } catch (Exception e) {
            logger.error(Constants.ERROR_LOG + "添加角色绑定发生异常：{}",
                Throwables.getStackTraceAsString(e));
            return new ResultBean<>(false, "人气太过火爆，正在排队中...", null);
        }
    }

    /**
     * <p>
     * 获取用户绑定信息
     * </p>
     *
     * @action tanjunkai 2017年4月5日 上午10:31:50 描述
     *
     * @param userId
     *            用户ID
     * @return ResultBean<Wish11thRoleBindBean> 用户许愿列表
     */
    public ResultBean<Wish11thRoleBindBean> getUserinfoBeanByUserId(
            int userId) {
        ResultBean<Wish11thRoleBindBean> resultBean = new ResultBean<>();
        try {
            Wish11thRoleBindBean userinfoBean = userBll.getUserByUserID(userId);
            if (null != userinfoBean) {
                resultBean.setData(userinfoBean);
                resultBean.setIsSuccess(true);
                // 判断是否有地址
                int flag_addr = 0;
                // 判断是否中过实物奖
                if (wishBll.isLotteryRealPrize(userId))
                    flag_addr = 1;
                resultBean.setStateCode(flag_addr);
                resultBean.setMessage("获取用户信息成功");
            } else {
                resultBean.setIsSuccess(false);
                resultBean.setMessage("没有差查找到用户角色绑定信息");
            }
        } catch (Exception e) {
            logger.error(Constants.ERROR_LOG + "获取用户绑定信息发生异常：{}",
                Throwables.getStackTraceAsString(e));
            resultBean.setIsSuccess(false);
            resultBean.setMessage("获取用户信息失败");
        }
        return resultBean;
    }

    /**
     * <p>
     * 用户添加地址
     * </p>
     *
     * @action tanjunkai 2017年4月5日 上午10:43:46 描述
     *
     * @param userId
     * @param addressBean
     * @return ResultBean<String>
     */
    public ResultBean<String> addAddress(int userId, AddressBean addressBean) {
        ResultBean<String> result = new ResultBean<>();
        try {
            // 获取信息
            result = addressBll.editAddress(userId, addressBean.getActionCode(),
                addressBean);
        } catch (Exception e) {
            logger.error(Constants.ERROR_LOG + "用户添加地址发生异常：{}",
                Throwables.getStackTraceAsString(e));
            result.setIsSuccess(false);
            result.setMessage("地址更改信息失败");
        }
        logger.info(Constants.ACTIVITY_NAME + "用户添加地址结果：", result);
        return result;
    }

    /**
     * <p>
     * 获取用户的中实物奖后填写的地址信息
     * </p>
     *
     * @action tanjunkai 2017年4月11日 下午12:41:05 描述
     *
     * @param userId
     *            用户ID
     * @return ResultBean<AddressBean>
     */
    public ResultBean<AddressBean> getAddress(int userId) {
        ResultBean<AddressBean> result = new ResultBean<>();
        try {
            Wish11thWishBean wishBean = wishBll.getMyRealPrize(userId);
            if (null == wishBean)
                return result;
            int actionCode = Constants.Wish11thMapperActionCode
                    .getActionCode(wishBean.getLevel());
            result = addressBll.getAddress(userId, actionCode);
        } catch (Exception e) {
            logger.error(Constants.ERROR_LOG + "获取用户的中实物奖后填写的地址信息发生异常：{}",
                Throwables.getStackTraceAsString(e));
            result.setIsSuccess(false);
            result.setMessage("获取用户的中实物奖后填写的地址信息失败");
        }
        result.setIsSuccess(true);
        result.setMessage("成功获取用户的中实物奖后填写的地址信息");
        return result;
    }

}
