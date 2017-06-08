/**
  * -------------------------------------------------------------------------
  * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
  * @版权所有：北京光宇在线科技有限责任公司
  * @项目名称：action-wdrankinglist2017
  * @作者：laixiancai
  * @联系方式：laixiancai@gyyx.cn
  * @创建时间：2017年4月12日 下午1:46:46
  * @版本号：0.0.1
  *-------------------------------------------------------------------------
  */
package cn.gyyx.action.service.wdrankinglist2017.impl;

import cn.gyyx.action.beans.wdrankinglist2017.Constants;
import cn.gyyx.action.bll.config.impl.DefaultHdConfigBLL;
import cn.gyyx.action.service.insurance.MemcacheUtil;
import cn.gyyx.action.service.wdrankinglist2017.CommonService;
import cn.gyyx.core.memcache.XMemcachedClientAdapter;

/**
 * <p>
 * 公用帮助类
 * </p>
 * 
 * @author laixiancai
 * @since 0.0.1
 */
public class CommonServiceImpl implements CommonService {
    private DefaultHdConfigBLL defaultHdConfigBLL = new DefaultHdConfigBLL();
    private MemcacheUtil memcacheUtil = new MemcacheUtil();
    private XMemcachedClientAdapter memcache = memcacheUtil.getMemcache();
    
    /**
     * 
     * <p>
     * 从hd_config_tb表中获取是否是debug模式
     * </p>
     *
     * @action laixiancai 2017年4月12日 下午1:46:08 描述
     *
     * @return boolean
     */
    public boolean isDebug() {
        String isDebug = defaultHdConfigBLL
                .getConfigByKey(Constants.HD_CODE, "debug").toString();
        return Boolean.parseBoolean(isDebug);
    }
    
    /**
     * 
     * <p>
     * 从hd_config_tb表中获取ServerCode
     * </p>
     *
     * @action laixiancai 2017年4月11日 下午6:56:53 描述
     *
     * @return int
     */
    public int getServerCode() {
        String memKey = Constants.HD_CODE
                + "_LotteryPrizesBeanServiceImpl_getServerCode";
        String serverCode = memcache.get(memKey, String.class);
        if (serverCode == null) {
            serverCode = defaultHdConfigBLL
                    .getConfigByKey(Constants.HD_CODE, "serverId").toString();
            if (serverCode != null && !serverCode.equals(""))
                memcache.set(memKey, 60, serverCode);
        }

        return Integer.valueOf(serverCode);
    }

    /**
     * 
     * <p>
     * 从hd_config_tb表中获取ServerName
     * </p>
     *
     * @action laixiancai 2017年4月11日 下午6:56:57 描述
     *
     * @return String
     */
    public String getServerName() {
        String memKey = Constants.HD_CODE
                + "_LotteryPrizesBeanServiceImpl_getServerName";
        String serverName = memcache.get(memKey, String.class);
        if (serverName == null) {
            serverName = defaultHdConfigBLL
                    .getConfigByKey(Constants.HD_CODE, "serverName").toString();
            if (serverName != null && !serverName.equals(""))
                memcache.set(memKey, 60, serverName);
        }

        return serverName;
    }
}
