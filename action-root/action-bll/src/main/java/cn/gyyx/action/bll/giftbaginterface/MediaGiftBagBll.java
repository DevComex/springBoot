/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年5月17日下午5:03:20
 * 版本号：v1.0
 * 本类主要用途叙述：媒体礼包的Bll
 */

package cn.gyyx.action.bll.giftbaginterface;

import java.util.Date;
import java.util.List;

import cn.gyyx.action.beans.giftinterface.MediaGiftBagBean;
import cn.gyyx.action.beans.giftinterface.OfficialGiftBagBean;
import cn.gyyx.action.dao.giftbaginterface.MediaGiftDao;
import cn.gyyx.action.dao.giftbaginterface.OfficialGiftBagDao;

public class MediaGiftBagBll {
	private MediaGiftDao mediaGiftDao = new MediaGiftDao();
	private OfficialGiftBagDao officialGiftBagDao = new OfficialGiftBagDao();

	public List<MediaGiftBagBean> getMediaGiftBagBeans(Date begin, Date end,
			int serverid) {
		return mediaGiftDao.getMediaGiftBagBeans(begin, end, serverid);
	}

	public List<OfficialGiftBagBean> getOfficialGiftBagBeans(Date begin,
			Date end, String serverName) {
		return officialGiftBagDao.getOfficialGiftBagBeans(begin, end,
				serverName);
	}
}
