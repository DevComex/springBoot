/**------------------------------------------------------------------------- 
* 版权所有：北京光宇在线科技有限责任公司 
-------------------------------------------------------------------------*/ 

package cn.gyyx.action.dao.jswswxsign;


import cn.gyyx.action.beans.jswswxsign.Sign;

/** 
 * 作        者：成龙 
 * 联系方式：chenglong@gyyx.cn 
 * 创建时间： 2016年5月13日 下午8:08:14
 * 描        述： 绝世武神微信签到功能-签到Mapper
 */
public interface ISignMapper {
	public Sign getSign(Sign sign);
	
	public int insertSign(Sign sign);
	
	public int updateSign(Sign sign);
	
	public Sign getRecentSign(Sign sign);
	
	public void updateSignZero();
}
