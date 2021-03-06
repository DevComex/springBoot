/**------------------------------------------------------------------------- 
* 版权所有：北京光宇在线科技有限责任公司 
-------------------------------------------------------------------------*/ 

package cn.gyyx.action.dao.jswswxsign;

import java.util.List;

import cn.gyyx.action.beans.jswswxsign.Gift;

/** 
 * 作        者：成龙 
 * 联系方式：chenglong@gyyx.cn 
 * 创建时间： 2016年5月13日 下午8:08:14
 * 描        述： 绝世武神微信签到功能-礼包Mapper
 */
public interface IGiftMapper {
	public List<Gift> getGiftList(Gift gift);

	public Gift getGift(Gift gift);
}
