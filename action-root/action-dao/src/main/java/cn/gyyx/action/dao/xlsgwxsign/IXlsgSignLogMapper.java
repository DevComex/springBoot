/**------------------------------------------------------------------------- 
* 版权所有：北京光宇在线科技有限责任公司 
-------------------------------------------------------------------------*/ 

package cn.gyyx.action.dao.xlsgwxsign;

import cn.gyyx.action.beans.xlsgwxsign.XlsgSignLog;

/** 
 * 作        者：成龙 
 * 联系方式：chenglong@gyyx.cn 
 * 创建时间： 2016年5月13日 下午8:08:14
 * 描        述：驯龙三国微信签到功能-签到明细Mapper
 */
public interface IXlsgSignLogMapper {
	public int insertSignLog(XlsgSignLog signLog);
}
