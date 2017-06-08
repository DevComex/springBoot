/**------------------------------------------------------------------------- 
* 版权所有：北京光宇在线科技有限责任公司 
-------------------------------------------------------------------------*/ 

package cn.gyyx.action.bll.xlsgwxsign;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.xlsgwxsign.XlsgSignLog;
import cn.gyyx.action.dao.xlsgwxsign.XlsgSignLogDAO;

/** 
 * 作        者：成龙 
 * 联系方式：chenglong@gyyx.cn 
 * 创建时间： 2016年5月13日 下午6:32:00
 * 描        述： 驯龙三国微信签到功能签到日志逻辑类
 */
public class XLSGSignLogBLL {
	private XlsgSignLogDAO signLogDAO = new XlsgSignLogDAO();

	/***
	 * 新增
	 */
	public int insert(XlsgSignLog signLog){
		return signLogDAO.insertSignLog(signLog);
	}
	
	/***
	 * 新增  带session
	 */
	public int insert(XlsgSignLog signLog,SqlSession session){
		return signLogDAO.insertSignLog(signLog,session);
	}
}
