/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：wd weChat scratchCard
 * @作者：chenpeilan
 * @联系方式：chenpeilan@gyyx.cn
 * @创建时间： 2015年12月25日
 * @版本号：
 * @本类主要用途描述：获取当前用户当天获奖记录
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.wechat;

import java.util.List;

import cn.gyyx.action.beans.lottery.UserInfoBean;

/**
 * @ClassName: IWeiXinSendpresent
 * @Description: TODO 获取当前用户当天获奖记录
 * @author chenpeilan chenpeilan@gyyx.cn
 * @date 2015年12月25日 下午4:04:51
 *
 */
public interface IWeiXinSendpresent {
	
	//获取当前用户当天获奖记录
	public List<UserInfoBean> getUserInfoByOpenIdAndDate(String account, String time);
	//获取当前用户当天获奖记录
	public Integer getUserCount(Integer actionCode);
	
}
