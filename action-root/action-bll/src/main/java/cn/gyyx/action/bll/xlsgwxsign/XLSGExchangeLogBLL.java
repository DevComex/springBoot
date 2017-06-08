/**------------------------------------------------------------------------- 
* 版权所有：北京光宇在线科技有限责任公司 
-------------------------------------------------------------------------*/ 

package cn.gyyx.action.bll.xlsgwxsign;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;

import cn.gyyx.action.beans.xlsgwxsign.XlsgExchangeLog;
import cn.gyyx.action.dao.xlsgwxsign.XlsgExchangeLogDAO;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/** 
 * 作        者：成龙 
 * 联系方式：chenglong@gyyx.cn 
 * 创建时间： 2016年5月13日 下午6:32:00
 * 描        述： 驯龙三国微信签到功能兑换记录逻辑类
 */
public class XLSGExchangeLogBLL {
	private XlsgExchangeLogDAO exchangeLogDAO = new XlsgExchangeLogDAO();
	@SuppressWarnings("unused")
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(XLSGExchangeLogBLL.class);

	/***
	 * 获取兑奖记录列表
	 */
	public List<XlsgExchangeLog> getExchangeLogList(String openId){
		XlsgExchangeLog param = new XlsgExchangeLog();
		param.setOpenId(openId);
		List<XlsgExchangeLog> result = exchangeLogDAO.getExchangeLogList(param);
		return result;
	}
	
	/***
	 * 插入一条兑奖记录
	 */
	public int insert(XlsgExchangeLog exchangeLog,SqlSession session){
		int result = exchangeLogDAO.insertExchangeLog(exchangeLog);
		return result;
	}
}
