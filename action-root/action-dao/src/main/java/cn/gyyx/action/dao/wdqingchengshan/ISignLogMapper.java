/**------------------------------------------------------------------------- 
* 版权所有：北京光宇在线科技有限责任公司 
-------------------------------------------------------------------------*/ 

package cn.gyyx.action.dao.wdqingchengshan;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.jswswxsign.Sign;
import cn.gyyx.action.beans.jswswxsign.SignLog;
import cn.gyyx.action.beans.wdnationalday.WdNationaldaySignLogBean;
import cn.gyyx.action.beans.wdqingchengshan.SignLogBean;

/** 
            问道青城山签到功能-签到明细Mapper
 */
public interface ISignLogMapper {

	/**
	 * 根据account获取当天的签到日志
	 */
	public SignLogBean getTodaySignLog(@Param("account") String account,@Param("today") String today);
	
	/**
	 * 新增签到日志
	 */
	public int insertSignLog(SignLogBean bean);
	

	List<SignLogBean> select(SignLogBean params);
	
	
} 
