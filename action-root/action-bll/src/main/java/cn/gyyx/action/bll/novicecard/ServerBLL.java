/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action
 * @作者：yangyusheng
 * @联系方式：yangyusheng@gyyx.cn
 * @创建时间： 2014年12月8日 上午16:59:34
 * @版本号：
 * @本类主要用途描述：服务器bll层
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.bll.novicecard;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.slf4j.Logger;

import cn.gyyx.action.beans.novicecard.ActiveLog;
import cn.gyyx.action.beans.novicecard.ResultBean;
import cn.gyyx.action.beans.novicecard.ServerBean;
import cn.gyyx.action.dao.novicecard.ServerDAO;
import cn.gyyx.core.DataFormat;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class ServerBLL {

	private static final Logger logger = GYYXLoggerFactory
			.getLogger(ServerBLL.class);

	private ServerDAO dao = new ServerDAO();

	/**
	 * 
	 * @日期：2014年12月8日
	 * @Title: getServerByGameIdAndState
	 * @Description: TODO
	 * @param gameId
	 * @param state
	 * @param areaId
	 * @param batchNo
	 * @return ResultBean<List<ServerBean>>
	 */

	public ResultBean<List<ServerBean>> getServerByGameIdAndState(
			Integer gameId, boolean state, Integer areaId, Integer batchNo) {
		logger.debug("gameId", gameId);
		logger.debug("state", state);
		logger.debug("areaId", areaId);
		logger.debug("batchNo", batchNo);
		ServerBean server = new ServerBean(gameId, state, areaId, batchNo);
		List<ServerBean> listServers = dao.selectServerByGameIdAndState(server);
		ResultBean<List<ServerBean>> result = new ResultBean<List<ServerBean>>(
				false, null, null);
		if (listServers != null && listServers.size() > 0) {
			result.setProperties(true, "", listServers);
		}
		logger.debug("result", result);
		return result;
	}

	/**
	 * @Title: getServerStatus
	 * @Author: jianghan
	 * @Description: TODO 向已有接口发送参数， 返回激活服务器状态 -2代表服务器不存在， -1代表用户不存在， 0代表未激活,
	 *               1代表激活, 2代表激活，并激活天数大于30天,
	 * @param account
	 * @param response
	 * @return int
	 * 
	 */
	public int getServerStatus(String StatusType, String result,
			String account, int game, int serverCode) {
		logger.debug("StatusType", StatusType);
		logger.debug("result", result);
		logger.debug("account", account);
		logger.debug("game", game);
		logger.debug("serverCode", serverCode);
		int returnMessage = 0;
		// 接收返回响应体
		if (StatusType == "OK") {

			logger.debug("ServerBLL中getServerStatus方法接收参数result结果（调CallWebApiAgent"
					+ "下的CallWebApiAgent返回的服务器信息实体）：        " + result);

			if (!result.equals("[]")) {
				ActiveLog[] activeLogList = DataFormat.jsonToObj(result,
						ActiveLog[].class);
				for (ActiveLog activeLog : activeLogList) {
					logger.debug("activeLog:" + activeLog.getActiveTime());
					if (serverCode == activeLog.getServerCode()) {
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd");
						try {
							Date date = (Date) sdf.parse(activeLog
									.getActiveTime());
							Date date2 = new Date();
							int betweenday = daysBetween(date, date2);
							if (betweenday <= 30) {
								returnMessage = 1;
							} else {
								returnMessage = 2;
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							logger.warn("返回结果异常");
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							logger.warn("返回结果异常");
						}
						break;
					}
				}

			} else {
				returnMessage = -2;
				logger.debug("returnMessage", returnMessage);
				return returnMessage;
			}
		} else if (StatusType == "Bad Request") {
			org.json.JSONObject obj = new org.json.JSONObject(result);
			obj.getString("Message");
			if (obj.getString("Message").toString().substring(0, 5)
					.equals("用户不存在")) {
				returnMessage = -1;
			} else {
				returnMessage = 0;
			}
		}
		logger.debug("returnMessage", returnMessage);
		return returnMessage;
	}

	/**
	 * @Title: daysBetween
	 * @Author: jianghan
	 * @Description: TODO 计算两个日期之间相差的天数 私有
	 * @param smdate
	 *            比较小的时间
	 * @param bdate
	 *            比较大的时间
	 * @return int
	 * @throws ParseException
	 * 
	 */
	private static int daysBetween(Date smdate, Date bdate)
			throws ParseException {
		logger.debug("smdate", smdate);
		logger.debug("bdate", bdate);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		smdate = sdf.parse(sdf.format(smdate));
		bdate = sdf.parse(sdf.format(bdate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(between_days));
	}
}
