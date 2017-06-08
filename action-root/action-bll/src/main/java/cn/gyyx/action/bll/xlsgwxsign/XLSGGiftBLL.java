/**------------------------------------------------------------------------- 
* 版权所有：北京光宇在线科技有限责任公司 
-------------------------------------------------------------------------*/ 

package cn.gyyx.action.bll.xlsgwxsign;

import java.util.List;

import org.slf4j.Logger;

import cn.gyyx.action.beans.xlsgwxsign.XlsgGift;
import cn.gyyx.action.dao.xlsgwxsign.XlsgGiftDAO;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/** 
 * 作        者：成龙 
 * 联系方式：chenglong@gyyx.cn 
 * 创建时间： 2016年5月13日 下午6:32:00
 * 描        述： 驯龙三国微信签到功能签到礼包逻辑类
 */
public class XLSGGiftBLL {
	private XlsgGiftDAO giftDAO = new XlsgGiftDAO();
	@SuppressWarnings("unused")
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(XLSGGiftBLL.class);

	/***
	 * 根据openId获取用户当前分数
	 */
	@SuppressWarnings("unused")
	public List<XlsgGift> getGiftList(){
		XlsgGift param = new XlsgGift();
		List<XlsgGift> result = giftDAO.getGiftList(null);
		return result;
	}

	/**
	 * 获取积分商品兑换礼包列表
	 */
	public XlsgGift getGift(XlsgGift gift) {
		return giftDAO.getGift(gift);
	}

}
