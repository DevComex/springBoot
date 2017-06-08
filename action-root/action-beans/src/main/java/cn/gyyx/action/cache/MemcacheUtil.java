/**
  * -------------------------------------------------------------------------
  * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
  * @版权所有：北京光宇在线科技有限责任公司
  * @项目名称：action-beans
  * @作者：caishuai
  * @联系方式：caishuai@gyyx.cn
  * @创建时间：2017年3月10日 下午2:25:06
  * @版本号：0.0.1
  *-------------------------------------------------------------------------
  */
package cn.gyyx.action.cache;

import cn.gyyx.core.memcache.XMemcachedClientAdapter;

/**
 * <p>
 * memcache获取工具
 * </p>
 * 
 * @author caishuai
 * @since 0.0.1
 */
public class MemcacheUtil {
    /**
     * 
     * <p>
     * 创建一个活动的memcache
     * </p>
     *
     * @action caishuai 2017年3月10日 下午2:29:17 描述
     *
     * @return XMemcachedClientAdapter
     */
    public static XMemcachedClientAdapter getActionMemcache() {
        return new XMemcachedClientAdapter("Action-ListData");

    }

    /**
     * 
     * <p>
     * 创建一个getOldWebSiteToolBarKey的memcache
     * </p>
     *
     * @action caishuai 2017年3月10日 下午2:29:17 描述
     *
     * @return XMemcachedClientAdapter
     */
    public static XMemcachedClientAdapter getOldWebSiteToolBarKey() {
        return new XMemcachedClientAdapter("OldWebSiteToolBarKey");

    }

    /**
     * 
     * <p>
     * 创建一个AccountAuthJavaUserInfoCache的memcache
     * </p>
     *
     * @action caishuai 2017年3月10日 下午2:29:17 描述
     *
     * @return XMemcachedClientAdapter
     */
    public static XMemcachedClientAdapter getAccountAuthJavaUserInfoCache() {
        return new XMemcachedClientAdapter("AccountAuthJavaUserInfoCache");
    }

    /**
     * 
     * <p>
     * 创建一个OldCaptchaCache的memcache
     * </p>
     *
     * @action caishuai 2017年3月10日 下午2:29:17 描述
     *
     * @return XMemcachedClientAdapter
     */
    public static XMemcachedClientAdapter getOldCaptchaCache() {
        return new XMemcachedClientAdapter("OldCaptchaCache");
    }

    /**
     * 
     * <p>
     * 创建一个getOldValidateCodeCache2的memcache
     * </p>
     *
     * @action caishuai 2017年3月10日 下午2:29:17 描述
     *
     * @return XMemcachedClientAdapter
     */
    public static XMemcachedClientAdapter getOldValidateCodeCache2() {
        return new XMemcachedClientAdapter("OldValidateCodeCache2");

    }
}
