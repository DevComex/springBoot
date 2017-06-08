package cn.gyyx.action.service.wdcountdown;

import java.text.ParseException;
import java.util.Date;

import org.slf4j.Logger;

import com.ibm.icu.text.SimpleDateFormat;

import cn.gyyx.action.beans.lottery.ResultBean;
import cn.gyyx.action.bll.jswswxsign.DateTools;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class WdCountDownService {

	private static final Logger logger = GYYXLoggerFactory.getLogger(WdCountDownService.class);

	public ResultBean<String> countDownTime() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ResultBean<String> result = new ResultBean<>();
		result.setProperties(false, "获取倒计时失败", null);
		try {
			Date endTime = format.parse("2017-01-06 12:00:00");
			// "2017-01-06 12:00:00" 1483675200000
			long endTimeS = endTime.getTime();
			long nowTime = System.currentTimeMillis();
			if (nowTime > endTimeS) {
				result.setProperties(true, "获取倒计时成功", "00-00-00");
				return result;
			}
			result.setProperties(true, "获取倒计时成功", formatDuring(endTimeS - nowTime));
			return result;
		} catch (ParseException e) {
			logger.info("获取倒计时失败", e.toString());
			e.printStackTrace();
		}
		return result;
	}

	public ResultBean<String> lottery(String time) {
		ResultBean<String> result = new ResultBean<>();
		result.setProperties(false, "不可以砸开金蛋", null);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String today = format.format(new Date());
		if (today.compareTo(time) >= 0) {
			result.setProperties(true, "可以砸开金蛋", null);
		}
		return result;
	}

	/**
	 * 
	 * @param 要转换的毫秒数
	 * @return 该毫秒数转换为 * days * hours * minutes * seconds 后的格式
	 * @author fy.zhang
	 */
	public String formatDuring(long mss) {
		long days = mss / (1000 * 60 * 60 * 24);
		long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
		long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
		long seconds = (mss % (1000 * 60)) / 1000;
		String day = "";
		String hour = "";
		String minute = "";
		if (days < 10) {
			day = "0" + days;
		} else {
			day = days + "";
		}
		if (hours < 10) {
			hour = "0" + hours;
		} else {
			hour = hours + "";
		}
		if (minutes < 10) {
			minute = "0" + minutes;
		} else {
			minute = minutes + "";
		}
		return day + "-" + hour + "-" + minute + "-" + seconds;
	}
}
