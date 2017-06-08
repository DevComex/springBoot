/**------------------------------------------------------------------------- 
* 版权所有：北京光宇在线科技有限责任公司 
-------------------------------------------------------------------------*/ 

package cn.gyyx.action.dao.xlsgwxsign;

import cn.gyyx.action.beans.xlsgwxsign.XlsgSign;

/** 
 * 作        者：成龙 
 * 联系方式：chenglong@gyyx.cn 
 * 创建时间： 2016年5月13日 下午8:08:14
 * 描        述： 驯龙三国微信签到功能-签到Mapper
 */
public interface IXlsgSignMapper {
	public XlsgSign getSign(XlsgSign sign);
	
	public int insertSign(XlsgSign sign);
	
	public int updateSign(XlsgSign sign);
	
	public XlsgSign getRecentSign(XlsgSign sign);
	
	public void updateSignZero();
}
