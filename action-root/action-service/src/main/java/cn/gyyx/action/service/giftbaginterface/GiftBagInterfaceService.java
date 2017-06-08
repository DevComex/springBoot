/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年5月17日上午11:17:08
 * 版本号：v1.0
 * 本类主要用途叙述：
 */

package cn.gyyx.action.service.giftbaginterface;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.giftinterface.GiftBagBean;
import cn.gyyx.action.beans.giftinterface.MediaGiftBagBean;
import cn.gyyx.action.beans.giftinterface.OfficialGiftBagBean;
import cn.gyyx.action.beans.novicecard.ServerInfoBean.Value;
import cn.gyyx.action.bll.giftbaginterface.MediaGiftBagBll;
import cn.gyyx.action.service.agent.CallWebApiAgent;
import cn.gyyx.action.service.insurance.MemcacheUtil;
import cn.gyyx.core.memcache.XMemcachedClientAdapter;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class GiftBagInterfaceService {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(GiftBagInterfaceService.class);
	MediaGiftBagBll mediaGiftBagBll = new MediaGiftBagBll();
	private static final CallWebApiAgent callWebApiAgent = new CallWebApiAgent();
	private MemcacheUtil memcacheUtil = new MemcacheUtil();
	private XMemcachedClientAdapter memcachedClientAdapter = memcacheUtil
			.getMemcache();

	/***
	 * 获取各个礼包的数量
	 * 
	 * @return
	 */
	public ResultBean<List<GiftBagBean>> getGiftBagBeans(String day,
			String serverName) {
		try {
			serverName = new String(serverName.getBytes("iso8859-1"), "UTF-8");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String nowDay = sdf.format(new Date());
			// 获取现在的小时数
			int hours = calendar.get(Calendar.HOUR_OF_DAY);
			Date beginDate = sdf.parse(day);
			String dayTemp = sdf.format(beginDate);
			if (!nowDay.equals(dayTemp)) {
				hours = 24;
			}
			List<GiftBagBean> list = getGiftBagBeans(day, hours, beginDate,
					serverName);
			return new ResultBean<>(true, "获取结果成功", list);
		} catch (Exception e) {
			logger.warn("getGiftBagBeans" + e);
			return new ResultBean<>(false, "获取失败", null);
		}

	}

	/***
	 * 具体获取礼包的统计数据
	 * 
	 * @param day
	 * @param hours
	 * @return
	 */
	private List<GiftBagBean> getGiftBagBeans(String day, int hours,
			Date beginDate, String serverName) {
		Map<String, Integer> mapServerMap = getServerMap();
		Date timeTemp = beginDate;
		List<GiftBagBean> list = new ArrayList<>();
		for (int i = 0; i < hours; i++) {
			// 获取区间的开始时间
			Date beginTemp = timeTemp;
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(timeTemp);
			calendar.add(Calendar.HOUR_OF_DAY, 1);
			timeTemp = calendar.getTime();
			// 获取区间的结束时间
			Date beginEnd = timeTemp;
			// 获取媒体的新手卡激活
			List<GiftBagBean> listMedia = getMediaGiftBag(day, serverName,
					beginTemp, beginEnd, i + 1, mapServerMap);
			// 官网新手卡礼包
			List<GiftBagBean> listOfficial = getOfficialGiftBag(day,
					serverName, beginTemp, beginEnd, i + 1);
			if (listMedia != null && !listMedia.isEmpty()) {
				list.addAll(listMedia);
			}
			if (listOfficial != null && !listOfficial.isEmpty()) {
				list.addAll(listOfficial);
			}

		}
		return list;
	}

	/****
	 * 官网新手卡礼包统计
	 * 
	 * @param day
	 * @param beginTemp
	 * @param beginEnd
	 * @param hours
	 * @return
	 */
	private List<GiftBagBean> getOfficialGiftBag(String day, String serverName,
			Date beginTemp, Date beginEnd, int hours) {
		List<GiftBagBean> list = new ArrayList<>();
		// 官方新手卡
		List<OfficialGiftBagBean> listOfficialGiftBagBeans = mediaGiftBagBll
				.getOfficialGiftBagBeans(beginTemp, beginEnd, serverName);
		if (listOfficialGiftBagBeans != null
				&& !listOfficialGiftBagBeans.isEmpty()) {
			for (int i = 0; i < listOfficialGiftBagBeans.size(); i++) {
				OfficialGiftBagBean officialGiftBagBean = listOfficialGiftBagBeans
						.get(i);
				GiftBagBean giftBagBean = new GiftBagBean();
				giftBagBean.setServerName(serverName);
				giftBagBean.setNumber(officialGiftBagBean.getNumber());
				giftBagBean.setHours(hours);
				giftBagBean.setGiftType("官网新手卡激活人数");
				giftBagBean.setGiftName(officialGiftBagBean.getPageName());
				giftBagBean.setDateString(day + " " + hours);
				list.add(giftBagBean);

			}

		}
		return list;
	}

	/***
	 * 媒体礼包
	 * 
	 * @param day
	 * @param begin
	 * @param end
	 * @param hours
	 * @param mapServerMap
	 * @return
	 */
	private List<GiftBagBean> getMediaGiftBag(String day, String serverName,
			Date begin, Date end, int hours, Map<String, Integer> mapServerMap) {
		Integer serverid = mapServerMap.get(serverName);
		if (serverid == null) {
			serverid = 0;
		}
		List<MediaGiftBagBean> listMediaGiftBagBeans = mediaGiftBagBll
				.getMediaGiftBagBeans(begin, end, serverid);
		List<GiftBagBean> list = new ArrayList<>();
		if (listMediaGiftBagBeans != null && !listMediaGiftBagBeans.isEmpty()) {
			for (int i = 0; i < listMediaGiftBagBeans.size(); i++) {
				MediaGiftBagBean mediaGiftBagBeanTemp = listMediaGiftBagBeans
						.get(i);
				GiftBagBean giftBagBean = new GiftBagBean();
				giftBagBean.setNumber(mediaGiftBagBeanTemp.getNumber());
				giftBagBean.setHours(hours);
				giftBagBean.setGiftType("媒体新手卡激活人数");
				giftBagBean.setGiftName(mediaGiftBagBeanTemp.getPageName());
				giftBagBean.setServerName(serverName);
				giftBagBean.setDateString(day + " " + hours);
				list.add(giftBagBean);

			}

		}
		return list;
	}

	/**
	 * 获取服务器map
	 * 
	 * @return
	 */
	private Map<String, Integer> getServerMap() {
		List<Value> list = getServerMem();
		Map<String, Integer> map = new HashMap<>();
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				map.put(list.get(i).getServerName(), list.get(i).getCode());
			}
		}
		return map;
	}

	/***
	 * 获取服务器信息
	 * 
	 * @return
	 */
	private List<Value> getServerMem() {
		List<Value> list = new ArrayList<>();
		String keyString = 010 + "getServerMem";
		try {
			list = Arrays.asList(memcachedClientAdapter.get(keyString,
					Value[].class));
			if (list == null || list.isEmpty()) {
				List<Value> serversList1 = callWebApiAgent
						.getAllServerByNetTypeCode(String.valueOf(1));
				List<Value> serversList2 = callWebApiAgent
						.getAllServerByNetTypeCode(String.valueOf(2));
				List<Value> serversList3 = callWebApiAgent
						.getAllServerByNetTypeCode(String.valueOf(3));
				list = serversList1;
				list.addAll(serversList2);
				list.addAll(serversList3);
				Value[] array = new Value[0];
				array = list.toArray(array);
				memcachedClientAdapter.set(keyString, 3600 * 6, array);
			}
			return list;

		} catch (Exception e) {
			logger.warn(e + "");
			List<Value> serversList1 = callWebApiAgent
					.getAllServerByNetTypeCode(String.valueOf(1));
			List<Value> serversList2 = callWebApiAgent
					.getAllServerByNetTypeCode(String.valueOf(2));
			List<Value> serversList3 = callWebApiAgent
					.getAllServerByNetTypeCode(String.valueOf(3));
			list.addAll(serversList1);
			list.addAll(serversList2);
			list.addAll(serversList3);
			Value[] array = new Value[0];
			array = list.toArray(array);
			memcachedClientAdapter.set(keyString, 3600 * 6, array);
			return list;

		}

	}
}
