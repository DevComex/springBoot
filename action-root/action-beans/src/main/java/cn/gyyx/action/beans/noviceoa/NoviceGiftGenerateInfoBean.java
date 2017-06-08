package cn.gyyx.action.beans.noviceoa;

import java.util.Date;

public class NoviceGiftGenerateInfoBean extends NoviceGiftGenerateBean {

	public NoviceGiftGenerateInfoBean(Integer code, Integer batchId, Integer giftId, Integer count, Date beginDate,
			Date endDate, String channel, Boolean isDelete, String description, String giftName) {
		super(code, batchId, giftId, count, beginDate, endDate, channel, isDelete, description);
		this.giftName = giftName;
	}

	private String giftName;

	public String getGiftName() {
		return giftName;
	}

	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}
}
