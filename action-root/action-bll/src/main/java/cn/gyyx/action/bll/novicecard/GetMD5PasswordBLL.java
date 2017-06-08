/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action
 * @作者：王雷
 * @联系方式：wanglei@gyyx.cn
 * @创建时间：2014年12月10日 
 * @版本号：v1.0
 * @本类主要用途描述：从缓存中获取用户的md5的密码的类
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.bll.novicecard;

import java.text.MessageFormat;
import java.util.Map;

import org.slf4j.Logger;

import cn.gyyx.action.beans.novicecard.ResultBean;
import cn.gyyx.core.DataFormat;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.core.memcache.GyyxStringTranscoder;
import cn.gyyx.core.memcache.XMemcachedClientAdapter;

public class GetMD5PasswordBLL {

	private static XMemcachedClientAdapter xMemcachedClientAdapter = new XMemcachedClientAdapter(
			"OldWebSiteToolBarKey");
	public String getMD5PasswordErrorMessage = null;
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(GetMD5PasswordBLL.class);

	/**
	 * 日期：2014年12月12日 作者：王雷 方法名：getPassword 参 数：String guid
	 * 返回值：ResultBean<String> isSuccess true: 获取密码成功 isSuccess false: 获取密码失败 描述：
	 */
	public ResultBean<String> getPassword(String loginId) {
		logger.debug("loginId", loginId);
		ResultBean<String> result = new ResultBean<String>();
		String password = null;
		String key = MessageFormat.format("login-{0}", loginId);
		String get;
		try {
			get = xMemcachedClientAdapter.get(key, new GyyxStringTranscoder());
			// 没从缓存中读取到值
			if (get == null) {
				result.setProperties(false, "领取失败，请到社区激活服务器", "");
				logger.debug("result", result);
				return result;
			}
			Map<String, Object> obj = DataFormat.jsonToObj(get, Map.class);
			// 接收返回的用户密码
			password = (String) obj.get("Md5Password");
			// 密码为空时
			if (password == null || "".equals(password)) {
				result.setProperties(false, "获取密码失败，请重新登录", "");
				logger.debug("result", result);
				return result;
			}
			// 存在密码时
			result.setProperties(true, "", password);
			logger.debug("result", result);
			return result;

		} catch (Exception e) {
			logger.warn("获取MD5密码时，未知错误" + e.toString());
			result.setProperties(false, "领取失败，请到社区激活服务器", "");
			logger.debug("result", result);
			return result;
		}
	}
}
