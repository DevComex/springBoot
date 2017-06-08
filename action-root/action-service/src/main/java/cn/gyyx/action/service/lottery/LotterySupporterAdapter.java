package cn.gyyx.action.service.lottery;

import cn.gyyx.action.beans.lottery.PrizeBean;
import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.UserLotteryBean;
import cn.gyyx.action.common.beans.InnerTransmissionData;
import cn.gyyx.action.common.util.JsonUtil;

public class LotterySupporterAdapter {
	public static final String NO_PRIZE_CHINESE = "NO_PRIZE_CHINESE";
	public static final String NO_PRIZE_ENGLISH = "NO_PRIZE_ENGLISH";
	public static final String NO_PRIZE_REAL = "NO_PRIZE_REAL";
	public static final String NO_PRIZE_NUMBER = "NO_PRIZE_NUMBER";
	LotteryService service = new LotteryService();
	public Object lotteryByDataBase(InnerTransmissionData data) {
		PrizeBean noprizeBean = getPrizeFromData(data);
		ResultBean<UserLotteryBean> res = service.lotteryByDataBase(data.getActionCode(), data.getAccountCode(), "byChance", data.getAccount(), "", 0,data.getIpAddress(), noprizeBean);
		UserLotteryBean result = res.getData();
		String str = JsonUtil.parseObject2JSON(result);
		return JsonUtil.parseJSON2Map(str);
	}
	
	public String lotteryByDataBaseWithJson(InnerTransmissionData data) {
		PrizeBean noprizeBean = getPrizeFromData(data);
		ResultBean<UserLotteryBean> res = service.lotteryByDataBase(data.getActionCode(), data.getAccountCode(), "byChance", data.getAccount(), "", 0,data.getIpAddress(), noprizeBean);
		UserLotteryBean result = res.getData();
		return JsonUtil.parseObject2JSON(result);
	}
	
	private PrizeBean getPrizeFromData(InnerTransmissionData data) {
		String chinese = "谢谢参与";
		String english = "nameplate";
		String real = "noRealPrize";
		int number = 4;
		Object oValue = data.getExtra(NO_PRIZE_CHINESE);
		if(oValue != null&&oValue instanceof String) {
			chinese = (String)oValue;
		}
		oValue = data.getExtra(NO_PRIZE_ENGLISH);
		if(oValue != null&&oValue instanceof String) {
			english = (String)oValue;
		}
		oValue = data.getExtra(NO_PRIZE_REAL);
		if(oValue != null&&oValue instanceof String) {
			real = (String)oValue;
		}
		oValue = data.getExtra(NO_PRIZE_NUMBER);
		if(oValue != null&&oValue instanceof Integer) {
			number = (Integer)oValue;
		}
		PrizeBean noprizeBean = new PrizeBean(0, chinese, english,
				data.getActionCode(), real, number);
		return noprizeBean;
	}
}
