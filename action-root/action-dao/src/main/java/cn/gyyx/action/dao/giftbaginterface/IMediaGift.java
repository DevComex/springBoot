/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年5月17日下午4:33:47
 * 版本号：v1.0
 * 本类主要用途叙述：处理用户相关请求，包括登陆和注册
 */

package cn.gyyx.action.dao.giftbaginterface;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.giftinterface.MediaGiftBagBean;

public interface IMediaGift {
	public List<MediaGiftBagBean> getMediaGiftBagBeans(
			@Param("begin") Date begin, @Param("end") Date end,
			@Param("serverId") int serverId);

}
