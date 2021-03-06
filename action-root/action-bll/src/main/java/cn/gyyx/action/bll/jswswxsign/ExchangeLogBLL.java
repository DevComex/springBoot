/**------------------------------------------------------------------------- 
* 版权所有：北京光宇在线科技有限责任公司 
-------------------------------------------------------------------------*/ 

package cn.gyyx.action.bll.jswswxsign;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;

import cn.gyyx.action.beans.jswswxsign.ExchangeLog;
import cn.gyyx.action.dao.jswswxsign.ExchangeLogDAO;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/** 
 * 作        者：成龙 
 * 联系方式：chenglong@gyyx.cn 
 * 创建时间： 2016年5月13日 下午6:32:00
 * 描        述： 绝世武神微信签到功能兑换记录逻辑类
 */
public class ExchangeLogBLL {
	private ExchangeLogDAO exchangeLogDAO = new ExchangeLogDAO();
	@SuppressWarnings("unused")
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(ExchangeLogBLL.class);

	/***
	 * 获取兑奖记录列表
	 */
	public List<ExchangeLog> getExchangeLogList(String openId){
		ExchangeLog param = new ExchangeLog();
		param.setOpenId(openId);
		List<ExchangeLog> result = exchangeLogDAO.getExchangeLogList(param);
		return result;
	}
	
	/***
	 * 插入一条兑奖记录
	 */
	public int insert(ExchangeLog exchangeLog,SqlSession session){
		int result = exchangeLogDAO.insertExchangeLog(exchangeLog);
		return result;
	}



}
