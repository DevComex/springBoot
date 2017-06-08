/**------------------------------------------------------------------------- 
* 版权所有：北京光宇在线科技有限责任公司 
-------------------------------------------------------------------------*/ 

package cn.gyyx.action.bll.xlsgwxsign;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;

import cn.gyyx.action.beans.xlsgwxsign.XlsgGiftExchangeCode;
import cn.gyyx.action.dao.xlsgwxsign.XlgsGiftExchangeCodeDAO;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/** 
 * 作        者：成龙 
 * 联系方式：chenglong@gyyx.cn 
 * 创建时间： 2016年5月13日 下午6:32:00
 * 描        述： 驯龙三国微信签到功能礼包兑换码逻辑类
 */
public class XLSGGiftExchangeCodeBLL {
	private XlgsGiftExchangeCodeDAO giftExchangeCodeDAO = new XlgsGiftExchangeCodeDAO();
	@SuppressWarnings("unused")
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(XLSGGiftExchangeCodeBLL.class);

	/***
	 * 获取一个兑换码
	 */
	public XlsgGiftExchangeCode getGiftExchangeCodeOne(Integer gid, String os, SqlSession session){
		XlsgGiftExchangeCode param = new XlsgGiftExchangeCode();
		param.setGid(gid);
		param.setOs(os);
		XlsgGiftExchangeCode result = giftExchangeCodeDAO.getGiftExchangeCodeOne(param);
		return result;
	}

	/**
	 * 更新
	 */
	public int update(XlsgGiftExchangeCode giftExchangeCode, SqlSession session) {
		int result = 0;
		result = giftExchangeCodeDAO.updateGiftExchangeCode(giftExchangeCode,session);
		return result;
	}

}
