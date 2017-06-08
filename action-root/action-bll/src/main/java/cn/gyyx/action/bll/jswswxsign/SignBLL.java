/**------------------------------------------------------------------------- 
* 版权所有：北京光宇在线科技有限责任公司 
-------------------------------------------------------------------------*/ 

package cn.gyyx.action.bll.jswswxsign;

import java.util.Date;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;

import cn.gyyx.action.beans.jswswxsign.Sign;
import cn.gyyx.action.dao.jswswxsign.SignDAO;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/** 
 * 作        者：成龙 
 * 联系方式：chenglong@gyyx.cn 
 * 创建时间： 2016年5月13日 下午6:32:00
 * 描        述： 绝世武神微信签到功能签到逻辑类
 */
public class SignBLL {
	private SignDAO signDAO = new SignDAO();
	@SuppressWarnings("unused")
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(SignBLL.class);

	/***
	 * 获取签到主表信息
	 */
	public Sign getSignOne(Date date,String openId){
		Sign param = new Sign();
		param.setOpenId(openId);
		//param.setLastSignDate(date);
		if(date != null){
			param.setSignDateStr(DateTools.formatDate(date));
		}
		Sign result = signDAO.getSign(param);
		return result;
	}
	
	/***
	 * 更新
	 */
	public int update(Sign sign){
		return signDAO.updateSign(sign);
	}
	
	/***
	 * 更新  传入session
	 */
	public int update(Sign sign,SqlSession session){
		return signDAO.updateSign(sign,session);
	}
	
	/***
	 * 新增
	 */
	public int insert(Sign sign){
		return signDAO.insertSign(sign);
	}
	
	/***
	 * 新增  传入session
	 */
	public int insert(Sign sign,SqlSession session){
		return signDAO.insertSign(sign,session);
	}

	/***
	 * 获得用户最近签到日期
	 */
	public Sign getRecentSign(String openId, SqlSession session) {
		return signDAO.getRecentSign(openId,session);
	}

	/***
	 * 连续签到次数清零
	 */
	public void updateSignZero(SqlSession session) {
		signDAO.updateSignZero(session);
	}


}
