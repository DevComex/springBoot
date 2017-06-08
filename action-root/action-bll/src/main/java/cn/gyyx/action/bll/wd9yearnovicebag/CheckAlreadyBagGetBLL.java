/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action
 * @作者：wanglei && zhouzhongkai
 * @联系方式：wanglei@gyyx.cn  zhouzhongkai@gyyx.cn
 * @创建时间： 2014年12月10日 下午3:03:43
 * @版本号：
 * @本类主要用途描述：新手卡service层
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.bll.wd9yearnovicebag;

import java.util.List;

import org.slf4j.Logger;

import cn.gyyx.action.beans.novicecard.NoviceParameter;
import cn.gyyx.action.beans.novicecard.ResultBean;
import cn.gyyx.action.beans.wd9yearnovicebag.HdSendPresentLogBean;
import cn.gyyx.action.dao.wd9yearnovicebag.HdSendPresentLogDAO;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class CheckAlreadyBagGetBLL {
	private HdSendPresentLogDAO hdSendPresentLogDAO = new HdSendPresentLogDAO();
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(CheckAlreadyBagGetBLL.class);

	/**
	 * 日期：2014年12月12日 作者：王雷 方法名：checkGet 参 数：NoviceParameter parameter, Integer
	 * momentType 返回值：ResultBean<String> result :IsSuccess: false :message
	 * 此用户没在此服务器上领取过新手卡 result :IsSuccess: true :message 领取过此类新手卡礼包 result
	 * :IsSuccess: true :message 您不能获取本新手卡！ ：程序数据库读取失败
	 * 描述：判断用户是否已在此服务器上领取过此类型的新手卡
	 */
	public ResultBean<String> checkBagGet(NoviceParameter parameter) {
		logger.debug("parameter", parameter);
		int count;
		ResultBean<String> result = new ResultBean<String>();
		// 取回查询的数据，有错返回
		try {
			count = hdSendPresentLogDAO.selectBagAccount(parameter
					.getUserInfo().getAccount(), parameter.getServerId(),
					parameter.getGameId(), parameter.getActivityId());
		} catch (Exception e) {
			logger.error(e.toString());
			result.setProperties(true, "您获取本新手卡礼包失败，请重试！", null);
			return result;
		}
		// 用户是否已领取过此类型的新手礼包
		if (count != 0) {
			result.setProperties(true, "您已经领取过此类新手卡礼包！", "您已经领取过此类新手卡礼包！");
			return result;
		} else {
			// 不然的话就是没领过新手礼包
			result.setProperties(false, "", "");
			logger.debug("result", result);
			return result;
		}
	}
}
