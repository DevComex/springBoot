/**
  * -------------------------------------------------------------------------
  * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
  * @版权所有：北京光宇在线科技有限责任公司
  * @项目名称：action-wd11yearrechargerebate
  * @作者：lihu
  * @联系方式：lihu@gyyx.cn
  * @创建时间：2017年3月30日 上午10:59:56
  * @版本号：0.0.1
  *-------------------------------------------------------------------------
  */
package cn.gyyx.action.service.wd11yearrechargerebate;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.client.RestClient;
import org.slf4j.Logger;

import com.google.common.base.Throwables;

import cn.gyyx.action.beans.wd11yearrechargerebate.InterfaceChangeAgentBean;
import cn.gyyx.action.beans.wd11yearrechargerebate.RechargerebateAcessLogWithBLOBs;
import cn.gyyx.action.bll.wd11yearrechargerebate.RechargeRebateAcessLogBll;
import cn.gyyx.core.security.MD5;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import net.sf.json.JSONObject;

/**
 * 
 * <p>
 * APP外部接口
 * </p>
 * 
 * @author lihu
 * @since 0.0.1
 */
public class InterfaceChangeModuleAgent {
    private static final Logger logger = GYYXLoggerFactory
            .getLogger(InterfaceChangeModuleAgent.class);
    private static String key = "123456";
    private static final String TIMESTAMP = "&timestamp=";
    private InterfaceChangeModuleAgent(){};
    /**
     * 
     * <p>
     * 用户在某个区服充值金额
     * </p>
     * @param logCode 
     *
     * @action lihu 2017年3月30日 上午10:08:41 描述
     *         http://change.module.gyyx.cn/v1/game/coin/QueryChangeCoin?endTime
     *         =2016-11-04
     *         &gameId=10067&serverId=8935&startTime=2016-11-03&timestamp=
     *         1479898816&userId=292386
     *         &sign=2f5ecab79feb28572f347e21c29805f1&sign_type=MD5
     * @param account
     * @return InterfaceTongAgentBean
     */
    public static InterfaceChangeAgentBean getRechargeResult(String startTime,
            String endTime, int gameId, int serverId, int userId, int logCode) {
        RechargeRebateAcessLogBll rechargeRebateAcessLogBll = new RechargeRebateAcessLogBll();
        RechargerebateAcessLogWithBLOBs accessLogBean = new RechargerebateAcessLogWithBLOBs();
        accessLogBean.setCode(logCode);

        long timestamp = System.currentTimeMillis() / 1000;
        logger.info("问道11周年充值返现活动调用用户在某个区服充值金额接口:startTime={},endTime={},gameId={},serverId={},userId={} ",
            new Object[] { startTime, endTime, gameId, serverId, userId });
        String signType = "MD5";
        String sign = getMD5(startTime, endTime, gameId, serverId, userId,
            timestamp);
        String url = "http://change.module.gyyx.cn/v1/game/coin/QueryChangeCoin?endTime="
                + endTime + "&startTime=" + startTime + "&gameId=" + gameId
                + "&serverId=" + serverId + "&userId=" + userId + "&sign_type="
                + signType + TIMESTAMP + timestamp + "&sign=" + sign;
        logger.info(
            "问道11周年充值返现活动___调取change.module.gyyx.cn域名的接口，URL为：".concat(url));
        RestClient client = new RestClient();
        org.apache.wink.client.Resource resource = client.resource(url);
        InterfaceChangeAgentBean agentResultBean = new InterfaceChangeAgentBean();
        try {
            ClientResponse response = (resource).get();
            logger.info(
                "问道11周年充值返现活动___调取change.module.gyyx.cn域名的接口，response为：",
                response);
            // 接收返回响应体
            String result = response.getEntity(String.class);
            logger.info("问道11周年充值返现活动___调取change.module.gyyx.cn域名的接口，result:",
                result);
            accessLogBean.setRecharge10Result(
                "用户在某个区服充值金额接口userId{" + userId + "} :" + result);
            rechargeRebateAcessLogBll.update(accessLogBean);
            JSONObject jsonobject1 = JSONObject.fromObject(result);
            if (jsonobject1 != null) {
                String status = jsonobject1.getString("status");
                String data = jsonobject1.getString("data");
                String message = jsonobject1.getString("message");
                agentResultBean.setMessage(message);
                agentResultBean.setStatus(status);
                if ("success".equals(status)) {
                    agentResultBean.setData(data);
                } else {
                    agentResultBean.setData("0");
                }
                return agentResultBean;
            }
            logger.info(
                "问道11周年充值返现活动___调取change.module.gyyx.cn域名的接口，InterfaceAgentBean为："
                        + agentResultBean);
            return new InterfaceChangeAgentBean("调用接口失败", "fail", "0");
        } catch (Exception e) {
            accessLogBean.setRecharge10Result(
                "用户在某个区服充值金额接口异常 :" + Throwables.getStackTraceAsString(e));
            rechargeRebateAcessLogBll.update(accessLogBean);
            logger.error("问道11周年充值返现活动___调取change.module.gyyx.cn域名的接口异常"
                    + Throwables.getStackTraceAsString(e));
            return new InterfaceChangeAgentBean("调用接口异常", "error", "0");
        }
    }

    private static String getMD5(String startTime, String endTime, int gameId,
            int serverId, int userId, long timestamp) {
        logger.info(
            "问道11周年充值返现活动___计算sign的值:startTime={},endTime={},gameId={},serverId={},userId={} ",
            new Object[] { startTime, endTime, gameId, serverId, userId });

        String interfaceURL = "/v1/game/coin/QueryChangeCoin?endTime=" + endTime
                + "&gameId=" + gameId + "&serverId=" + serverId + "&startTime="
                + startTime + TIMESTAMP + timestamp + "&userId=" + userId
                + key;
        String result = MD5.encode(interfaceURL);
        logger.info("问道11周年充值返现活动___计算sign的值。计算结果为：" + result);
        return result;
    }

    /**
     * 
      * <p>
      *    获取用户历史所有兑换金额
      * </p>
      *{"status":"success","data":5352.00,"message":"获取兑换金额成功"}
      * @action
      *    lihu 2017年3月31日 下午1:24:54 描述
      *
      * @param account
      * @param logCode 
      * @return InterfaceChangeAgentBean
     */
    public static InterfaceChangeAgentBean getAllRechargeResult(String account,
            int logCode) {
        RechargeRebateAcessLogBll rechargeRebateAcessLogBll = new RechargeRebateAcessLogBll();
        RechargerebateAcessLogWithBLOBs accessLogBean = new RechargerebateAcessLogWithBLOBs();
        accessLogBean.setCode(logCode);
        long timestamp = System.currentTimeMillis() / 1000;
        logger.info("问道11周年充值返现活动调用用户所有充值金额接口:account={}",
            new Object[] { account });
        String signType = "MD5";
        String error = "error";
        String sign = getMD5(account, timestamp);
        String url = "http://change.module.gyyx.cn/v1/game/coin/QueryChangeGyb?account="
                + account + "&sign_type=" + signType + TIMESTAMP
                + timestamp + "&sign=" + sign;
        logger.info("问道11周年充值返现活动___调取change.module.gyyx.cn域名的接口，URL为：" + url);
        RestClient client = new RestClient();
        org.apache.wink.client.Resource resource = client.resource(url);
        InterfaceChangeAgentBean agentResultBean = new InterfaceChangeAgentBean();
        try {
            ClientResponse response = (resource).get();
            int statusCode = response.getStatusCode();// 获取状态码
            logger.info("问道11周年充值返现活动___调取change.module.gyyx.cn域名的接口，response为："
                    + response + " statusCode:" + statusCode);
            // 接收返回响应体
            String result = response.getEntity(String.class);
            logger.info("问道11周年充值返现活动___调取change.module.gyyx.cn域名的接口，result:",
                result);
            accessLogBean.setHistoryRechargeResult("获取用户历史所有兑换金额接口account{"
                    + account + "} :" + result);
            rechargeRebateAcessLogBll.update(accessLogBean);
            if (result != null && statusCode == 200) {
                JSONObject jsonobject = JSONObject.fromObject(result);
                String status = jsonobject.getString("status");
                String message = jsonobject.getString("message");
                String data = jsonobject.getString("data");
                agentResultBean.setMessage(message);
                agentResultBean.setStatus(status);
                if ("success".equals(status)) {
                    agentResultBean.setData(data);
                } else {
                    agentResultBean.setData("0");
                }
                return agentResultBean;
            }

            logger.info(
                "问道11周年充值返现活动___调取change.module.gyyx.cn域名的接口，InterfaceAgentBean为："
                        + agentResultBean);
            return new InterfaceChangeAgentBean("调用接口失败", error, "0");
        } catch (Exception e) {
            accessLogBean.setHistoryRechargeResult(
                "获取用户历史所有兑换金额接口异常 :" + Throwables.getStackTraceAsString(e));
            rechargeRebateAcessLogBll.update(accessLogBean);
            logger.error("问道11周年充值返现活动___调取change.module.gyyx.cn域名的接口异常"
                    + Throwables.getStackTraceAsString(e));
            return new InterfaceChangeAgentBean("调用接口异常", error, "0");
        }
    }

    private static String getMD5(String account, long timestamp) {
        logger.info("问道11周年充值返现活动___计算sign的值:account={" + account);

        String interfaceURL = "/v1/game/coin/QueryChangeGyb?account=" + account
                + TIMESTAMP + timestamp + key;
        String result = MD5.encode(interfaceURL);
        logger.info("问道11周年充值返现活动___计算sign的值。计算结果为：" + result);
        return result;
    }
}
