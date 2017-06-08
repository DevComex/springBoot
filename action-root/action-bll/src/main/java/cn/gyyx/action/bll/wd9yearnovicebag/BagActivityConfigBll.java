/*------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：周忠凯 王雷
 * 联系方式：zhouzhongkai@gyyx.cn wanglei@gyyx.cn 
 * 创建时间：2015年03月25日 下午4:57:58 
 * 版本号：v1.0 
 * 本类主要用途描述： 
 * 
-------------------------------------------------------------------------*/

package cn.gyyx.action.bll.wd9yearnovicebag;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;

import cn.gyyx.action.beans.novicecard.ResultBean;
import cn.gyyx.action.beans.wd9yearnovicebag.BagActivityConfigBean;
import cn.gyyx.action.dao.wd9yearnovicebag.BagActivityConfigDAO;
import cn.gyyx.core.DataFormat;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.core.Text;

/**
 * @ClassName: ActivityConfigBll
 * @Description: TODO 对活动配置进行操作
 * @author jh
 * @date 2014年12月10日 下午3:04:38
 * 
 */
public class BagActivityConfigBll {

	private BagActivityConfigDAO acd = new BagActivityConfigDAO();
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(BagActivityConfigBll.class);

	/**
	 * 
	 * @日期：2014年12月11日
	 * @Title: getConfigMessage
	 * @Description: TODO
	 * @param hdName
	 * @param isLimit
	 * @return ResultInfo<ActivityConfigBean>
	 */

	public ResultBean<BagActivityConfigBean> getConfigMessage(String hdName) {
		logger.debug("hdName", hdName);
		ResultBean<BagActivityConfigBean> result = new ResultBean<BagActivityConfigBean>(
				false, "未知错误", null);
		try {
			BagActivityConfigBean activityConfig = acd
					.selectActivityConfigByhdName(hdName);
			// 检查活动是否开启
			if (activityConfig == null) {
				result.setMessage("没有读取到配置信息！");
				logger.debug("result", result);
				return result;
			}
			// 获取奖品信息和检查类
			if (!Text.isNullOrEmpty(activityConfig.getParas())) {
				@SuppressWarnings("unchecked")
				Map<String, Object> map = DataFormat.jsonToObj(
						activityConfig.getParas(), Map.class);
				activityConfig.setCheckType(map.get("CheckType").toString());
				activityConfig.setPrize(map.get("Prize").toString());
			}
			// 检查活动时间
			if (activityConfig.getActivityStart().compareTo(new Date()) > 0) {
				result.setMessage("活动尚未开启，敬请期待！");
				logger.debug("result", result);
				return result;
			}
			if (activityConfig.getActivityEnd().compareTo(new Date()) < 0) {
				result.setMessage("活动已结束，感谢参与！");
				logger.debug("result", result);
				return result;
			}
			result.setProperties(true, "根据活动描述获得活动配置信息成功", activityConfig);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage("根据活动描述获得活动配置信息失败");
		}
		logger.debug("result", result);
		return result;
	}

}
