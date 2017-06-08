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

import cn.gyyx.action.beans.wd11yearrechargerebate.InterfaceTongAgentBean;
import cn.gyyx.action.beans.wd11yearrechargerebate.RechargerebateAcessLogWithBLOBs;
import cn.gyyx.action.bll.wd11yearrechargerebate.RechargeRebateAcessLogBll;
import cn.gyyx.core.security.MD5;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import net.sf.json.JSONObject;
/**
 * 
  * <p>
  *   APP外部接口
  * </p>
  *  
  * @author lihu
  * @since 0.0.1
 */
public class InterfaceTongModuleAgent {
    private static final Logger logger = GYYXLoggerFactory.getLogger(InterfaceTongModuleAgent.class);
    private static String key = "123456";
    private InterfaceTongModuleAgent(){};
    /**
      * <p>
      *    通过用户 获取登录绑定信息
      * </p>
      *
      * @action
      *    lihu 2017年3月30日 上午10:08:41 描述
      *
      * @param userId
     * @param logCode 
      * @return InterfaceTongAgentBean
     */
    public static InterfaceTongAgentBean getResultFromUserId(Integer userId,
            int logCode) {
        RechargeRebateAcessLogBll rechargeRebateAcessLogBll = new RechargeRebateAcessLogBll();

        long timestamp = System.currentTimeMillis() / 1000;
        logger.info("问道11周年充值返现活动调用光宇APP是否绑定接口:userId={}",
            new Object[] { userId });
        String signType = "MD5";
        String sign = getMD5(userId, timestamp);
        String url = "http://interface.tong.gyyx.cn/v1/account/GetAccountBindInfoByUserId?timestamp="
                + timestamp + "&userId=" + userId + "&sign=" + sign
                + "&sign_type=" + signType;
        logger.info("问道11周年充值返现活动___调取interface.tong.gyyx.cn域名的接口，URL为：",
            new Object[] { url });
        RestClient client = new RestClient();
        org.apache.wink.client.Resource resource = client.resource(url);
        InterfaceTongAgentBean agentResultBean = new InterfaceTongAgentBean();
        RechargerebateAcessLogWithBLOBs accessLogBean = new RechargerebateAcessLogWithBLOBs();
        accessLogBean.setCode(logCode);

        try {
            ClientResponse response = (resource).get();
            int statusCode = response.getStatusCode();
            String result = response.getEntity(String.class);
            logger.info(
                "问道11周年充值返现活动___调取interface.tong.gyyx.cn域名的接口，response为：{},statusCode:{}",
                new Object[] { response, statusCode });
            accessLogBean.setBindappResult("获取登录绑定信息接口userId{" + userId + "} :"
                    + result);
            rechargeRebateAcessLogBll.update(accessLogBean);
            // 接收返回响应体
            if (statusCode == 200) {
                logger.info(
                    "问道11周年充值返现活动___调取interface.tong.gyyx.cn域名的接口，result:{}",
                    new Object[] { result });
                if (result != null) {
                    agentResultBean = new InterfaceTongAgentBean();
                    JSONObject jsonobject = JSONObject.fromObject(result);
                    agentResultBean
                            .setMsg(jsonobject.getString("error_message"));
                    agentResultBean.setStatus(
                        jsonobject.getJSONObject("data").getString("status"));
                    return agentResultBean;
                }

            }
            logger.info(
                "问道11周年充值返现活动___调取interface.tong.gyyx.cn域名的接口，InterfaceAgentBean为：{}",
                new Object[] { agentResultBean });
            return new InterfaceTongAgentBean("接口失败", "fail");
        } catch (Exception e) {
            accessLogBean.setBindappResult(
                "获取登录绑定信息接口异常:" + Throwables.getStackTraceAsString(e));
            rechargeRebateAcessLogBll.update(accessLogBean);
            logger.error("问道11周年充值返现活动___调取interface.tong.gyyx.cn域名的接口异常"
                    + Throwables.getStackTraceAsString(e));
            return new InterfaceTongAgentBean("接口异常", "error");
        }

    }

    private static String getMD5(Integer userId, long timestamp) {
        logger.info("问道11周年充值返现活动___计算sign的值。参数userId:{} &timestamp: {}",
            new Object[] { userId, timestamp });
        String interfaceURL = "/v1/account/GetAccountBindInfoByUserId?timestamp="
                + timestamp + "&userId=" + userId + key;
        String result = MD5.encode(interfaceURL);
        logger.info("问道11周年充值返现活动___计算sign的值。计算结果为：{}",
            new Object[] { interfaceURL });
        return result;
    }
    
    
}
