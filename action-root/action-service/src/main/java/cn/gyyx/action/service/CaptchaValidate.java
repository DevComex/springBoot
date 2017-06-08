/**
  * -------------------------------------------------------------------------
  * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
  * @版权所有：北京光宇在线科技有限责任公司
  * @项目名称：action-service
  * @作者：guoyonggang
  * @联系方式：guoyonggang@gyyx.cn
  * @创建时间：2017年3月23日 上午9:05:30
  * @版本号：0.0.1
  *-------------------------------------------------------------------------
  */
package cn.gyyx.action.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;

import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.core.memcache.GyyxStringTranscoder;
import cn.gyyx.core.memcache.XMemcachedClientAdapter;

/**
 * 
 * <p>
 * 验证码校验
 * </p>
 * 
 * @author guoyonggang
 * @since 0.0.1
 */
public class CaptchaValidate {
    private XMemcachedClientAdapter adapter = new XMemcachedClientAdapter(
            "OldCaptchaCache");
    private static final Logger LOG = GYYXLoggerFactory
            .getLogger(CaptchaValidate.class);
    private static final String GYYX_STAGE_CHECKCODE_COOKIE = "GYYX_CHECKCODE_STAGE_V1";
    private static final String GYYX_SIMPLE_CHECKCODE_COOKIE = "GYYX_CHECKCODE_VJ";

    /**
     * <p>
     * 验证验证码
     * </p>
     *
     * @action guoyonggang 2017年3月29日 上午9:20:37 描述
     *
     * @param captcha
     *            验证码
     * @param cookieKeyMessage
     *            验证码key
     * @return int 验证结果
     */
    public int checkCaptcha(String captcha, String cookieKeyMessage) {
        String realCap = (String) adapter.get(cookieKeyMessage,
            new GyyxStringTranscoder());
        LOG.error("checkCaptcha:from memecache:" + realCap + ",from browser:"
                + captcha + "*");
        if (realCap != null && realCap.equalsIgnoreCase(captcha)) {
            return adapter.delete(cookieKeyMessage) ? 0 : -1;
        } else {
            return -1;
        }
    }

    /**
     * <p>
     * 验证简单验证码
     * </p>
     *
     * @action guoyonggang 2017年3月29日 上午9:20:03 描述
     *
     * @param captcha
     *            验证码
     * @param request
     * @return int 验证结果
     */
    public int checkCaptcha(String captcha, HttpServletRequest request) {
        Cookie[] cook = request.getCookies();
        if (cook != null) {
            for (Cookie c : cook) {
                if (c.getName().equals(GYYX_SIMPLE_CHECKCODE_COOKIE)) {
                    return checkCaptcha(captcha, c.getValue());
                }
            }
        }
        return -1;
    }

    /**
     * 
     * <p>
     * 中文验证码校验
     * </p>
     *
     * @action guoyonggang 2017年3月27日 上午9:35:26 描述
     *
     * @param captcha
     * @param bid
     * @param request
     * @return int
     */
    public int checkCaptcha(String captcha, String bid,
            HttpServletRequest request) {
        Cookie[] cook = request.getCookies();
        if (cook != null) {
            for (Cookie c : cook) {
                if (c.getName().equals(GYYX_STAGE_CHECKCODE_COOKIE)) {
                    return checkCaptcha(captcha,
                        bid.concat(":").concat(c.getValue()));
                }
            }
        }
        return -1;
    }
}
