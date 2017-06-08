/**------------------------------------------------------------------------- 
* 版权所有：北京光宇在线科技有限责任公司 
-------------------------------------------------------------------------*/ 

package cn.gyyx.action.bll.jswswxsign;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;

import cn.gyyx.action.beans.jswswxsign.SignLog;
import cn.gyyx.action.dao.jswswxsign.SignLogDAO;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/** 
 * 作        者：成龙 
 * 联系方式：chenglong@gyyx.cn 
 * 创建时间： 2016年5月13日 下午6:32:00
 * 描        述： 绝世武神微信签到功能签到日志逻辑类
 */
public class SignLogBLL {
	private SignLogDAO signLogDAO = new SignLogDAO();
	@SuppressWarnings("unused")
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(SignBLL.class);

	/***
	 * 新增
	 */
	public int insert(SignLog signLog){
		return signLogDAO.insertSignLog(signLog);
	}
	
	/***
	 * 新增  带session
	 */
	public int insert(SignLog signLog,SqlSession session){
		return signLogDAO.insertSignLog(signLog,session);
	}


}
