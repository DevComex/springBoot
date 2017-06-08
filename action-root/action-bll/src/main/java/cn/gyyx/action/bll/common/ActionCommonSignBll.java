package cn.gyyx.action.bll.common;

import java.util.Date;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;

import cn.gyyx.action.beans.common.ActionCommonSignBean;
import cn.gyyx.action.bll.jswswxsign.DateTools;
import cn.gyyx.action.bll.jswswxsign.SignBLL;
import cn.gyyx.action.dao.common.SignDao;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class ActionCommonSignBll {
	private SignDao signDAO=new SignDao();
	/***
	 * 获取签到主表信息
	 */
	public ActionCommonSignBean getSignOne(Date date,String account){
		ActionCommonSignBean param = new ActionCommonSignBean();
		param.setAccount(account);
		param.setLastSignDate(date);
		if(date != null){
			param.setSignDateStr(DateTools.formatDate(date));
		}
		ActionCommonSignBean result = signDAO.getSign(param);
		return result;
	}
	
	/***
	 * 更新
	 */
	public int update(ActionCommonSignBean sign){
		return signDAO.updateSign(sign);
	}
	
	/***
	 * 更新  传入session
	 */
	public int update(ActionCommonSignBean sign,SqlSession session){
		return signDAO.updateSign(sign,session);
	}
	
	/***
	 * 新增
	 */
	public int insert(ActionCommonSignBean sign){
		return signDAO.insertSign(sign);
	}
	
	/***
	 * 新增  传入session
	 */
	public int insert(ActionCommonSignBean sign,SqlSession session){
		return signDAO.insertSign(sign,session);
	}

	/***
	 * 获得用户最近签到日期
	 */
	public ActionCommonSignBean getRecentSign(int actionCode,String account, SqlSession session) {
		return signDAO.getRecentSign(actionCode,account,session);
	}

	/***
	 * 获得用户签到信息
	 */
	public ActionCommonSignBean getRecentSign(int actionCode,String account) {
		return signDAO.getRecentSign(actionCode,account);
	}


}
