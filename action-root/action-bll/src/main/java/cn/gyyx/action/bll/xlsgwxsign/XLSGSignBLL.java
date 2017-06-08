/**------------------------------------------------------------------------- 
* 版权所有：北京光宇在线科技有限责任公司 
-------------------------------------------------------------------------*/ 

package cn.gyyx.action.bll.xlsgwxsign;

import java.util.Date;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.xlsgwxsign.XlsgSign;
import cn.gyyx.action.bll.jswswxsign.DateTools;
import cn.gyyx.action.dao.xlsgwxsign.XlsgSignDAO;

/** 
 * 作        者：成龙 
 * 联系方式：chenglong@gyyx.cn 
 * 创建时间： 2016年5月13日 下午6:32:00
 * 描        述：驯龙三国微信签到功能签到逻辑类
 */
public class XLSGSignBLL {
	private XlsgSignDAO signDAO = new XlsgSignDAO();

	/***
	 * 获取签到主表信息
	 */
	public XlsgSign getSignOne(Date date,String openId){
		XlsgSign param = new XlsgSign();
		param.setOpenId(openId);
		//param.setLastSignDate(date);
		if(date != null){
			param.setSignDateStr(DateTools.formatDate(date));
		}
		XlsgSign result = signDAO.getSign(param);
		return result;
	}
	
	/***
	 * 更新
	 */
	public int update(XlsgSign sign){
		return signDAO.updateSign(sign);
	}
	
	/***
	 * 更新  传入session
	 */
	public int update(XlsgSign sign,SqlSession session){
		return signDAO.updateSign(sign,session);
	}
	
	/***
	 * 新增
	 */
	public int insert(XlsgSign sign){
		return signDAO.insertSign(sign);
	}
	
	/***
	 * 新增  传入session
	 */
	public int insert(XlsgSign sign,SqlSession session){
		return signDAO.insertSign(sign,session);
	}

	/***
	 * 获得用户最近签到日期
	 */
	public XlsgSign getRecentSign(String openId, SqlSession session) {
		return signDAO.getRecentSign(openId,session);
	}

	/***
	 * 连续签到次数清零
	 */
	public void updateSignZero(SqlSession session) {
		signDAO.updateSignZero(session);
	}


}
