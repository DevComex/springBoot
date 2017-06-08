package cn.gyyx.action.bll.common;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import java.util.List;
import cn.gyyx.action.beans.common.ActionCommonSignLogBean;
import cn.gyyx.action.dao.common.SignLogDao;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class ActionCommonSignLogBll {
	private SignLogDao signLogDAO = new SignLogDao();
	@SuppressWarnings("unused")
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(ActionCommonSignLogBll.class);

	/***
	 * 新增
	 */
	public int insert(ActionCommonSignLogBean signLog){
		return signLogDAO.insertSignLog(signLog);
	}
	
	/***
	 * 新增  带session
	 */
	public int insert(ActionCommonSignLogBean signLog,SqlSession session){
		return signLogDAO.insertSignLog(signLog,session);
	}
	
	public List<ActionCommonSignLogBean> selectSignLog(ActionCommonSignLogBean signLog){
		return signLogDAO.selectSignLog(signLog);
	}
}
