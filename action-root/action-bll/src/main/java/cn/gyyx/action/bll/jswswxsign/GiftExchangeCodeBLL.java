/**------------------------------------------------------------------------- 
* 版权所有：北京光宇在线科技有限责任公司 
-------------------------------------------------------------------------*/ 

package cn.gyyx.action.bll.jswswxsign;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;

import cn.gyyx.action.beans.jswswxsign.GiftExchangeCode;
import cn.gyyx.action.dao.jswswxsign.GiftExchangeCodeDAO;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/** 
 * 作        者：成龙 
 * 联系方式：chenglong@gyyx.cn 
 * 创建时间： 2016年5月13日 下午6:32:00
 * 描        述： 绝世武神微信签到功能礼包兑换码逻辑类
 */
public class GiftExchangeCodeBLL {
	private GiftExchangeCodeDAO giftExchangeCodeDAO = new GiftExchangeCodeDAO();
	@SuppressWarnings("unused")
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(GiftExchangeCodeBLL.class);

	/***
	 * 获取一个兑换码
	 */
	public GiftExchangeCode getGiftExchangeCodeOne(Integer gid, String os, SqlSession session){
		GiftExchangeCode param = new GiftExchangeCode();
		param.setGid(gid);
		param.setOs(os);
		GiftExchangeCode result = giftExchangeCodeDAO.getGiftExchangeCodeOne(param);
		return result;
	}

	/**
	 * 更新
	 */
	public int update(GiftExchangeCode giftExchangeCode, SqlSession session) {
		int result = 0;
		result = giftExchangeCodeDAO.updateGiftExchangeCode(giftExchangeCode,session);
		return result;
	}


}
