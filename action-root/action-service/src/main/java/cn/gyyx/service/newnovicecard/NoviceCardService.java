/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action
 * @作者：yangyusheng
 * @联系方式：yangyusheng@gyyx.cn
 * @创建时间： 2014年12月10日 下午3:03:43
 * @版本号：
 * @本类主要用途描述：新手卡service层
 *-------------------------------------------------------------------------
 */
package cn.gyyx.service.newnovicecard;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import org.slf4j.Logger;

import cn.gyyx.action.beans.novicecard.NoviceParameter;
import cn.gyyx.action.beans.novicecard.ResultBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.SendPrizeResult;
import cn.gyyx.action.service.agent.CallWebApiXWBAgent;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class NoviceCardService {

	private static final Logger logger = GYYXLoggerFactory
			.getLogger(NoviceCardService.class);

	public ResultBean<Integer> SendItemXWB(NoviceParameter parameter) {
		logger.debug("炫舞吧发送物品方法,parameter:"+ parameter);
		ResultBean<Integer> result = new ResultBean<Integer>(false, "发送物品未知错误",
				-2147483648);
		try {
			SendPrizeResult sendPrizeResult = new SendPrizeResult();
			Calendar c = java.util.Calendar.getInstance(TimeZone.getDefault(),
					Locale.CHINA);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			c.add(Calendar.YEAR, 1);
			java.util.Date newdate = c.getTime();
			String expiredTime = formatter.format(newdate);
			sendPrizeResult = CallWebApiXWBAgent.giveXWBPresents(
					parameter.getGameId(), parameter.getAccount(),
					parameter.getVirtualItemCode(), expiredTime, parameter.getMailContent()!=null&&!"".equals(parameter.getMailContent().trim())?parameter.getMailContent():parameter.getVirtualItemCode(),
					parameter.getServerInfo());
			if (sendPrizeResult != null) {
				result.setProperties(sendPrizeResult.isSuccess(), sendPrizeResult.getMessage(),
						0);
			}
		} catch (Exception e) {
			logger.warn(e.toString());
			result.setProperties(false, "网络异常，领取失败", -1);
		}
		logger.debug("result", result);
		return result;
	}
	
}
