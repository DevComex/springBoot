/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action
 * @作者：yangyusheng
 * @联系方式：yangyusheng@gyyx.cn
 * @创建时间： 2014年12月8日 上午17:32:55
 * @版本号：
 * @本类主要用途描述：服务器信息Mapper
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.novicecard;

import java.util.List;

import cn.gyyx.action.beans.novicecard.ServerBean;

public interface IServerMapper {

	/**
	 * 
	 * @日期：2014年12月8日
	 * @Title: selectServerByGameIdAndState 
	 * @Description: TODO 
	 * @param ServerBean server
	 * @return List<ServerBean>
	 */
	 
	
	public List<ServerBean> selectServerByGameIdAndState(ServerBean server);
	

}
