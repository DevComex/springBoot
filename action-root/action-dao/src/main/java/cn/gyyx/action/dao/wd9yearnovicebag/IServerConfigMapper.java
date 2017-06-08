/*------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：周忠凯 王雷
 * 联系方式：zhouzhongkai@gyyx.cn wanglei@gyyx.cn 
 * 创建时间：2015年03月25日 下午4:57:58 
 * 版本号：v1.0 
 * 本类主要用途描述： 
 * 
-------------------------------------------------------------------------*/

package cn.gyyx.action.dao.wd9yearnovicebag;

import java.util.List;

import cn.gyyx.action.beans.wd9yearnovicebag.ActionServerConfigBean;

public interface IServerConfigMapper {
	/**
	 * @Title: setReceiveLog
	 * @Author: zhouzhongkai wanglei
	 * @date 2015年03月27日 下午7:12:45
	 * @Description: TODO 根据活动id查询区组
	 * @param param
	 * 
	 */
   public List<String> selectServerConfigInfo(Integer activityId);
	/**
	 * @Title: setReceiveLog
	 * @Author: zhouzhongkai wanglei
	 * @date 2015年03月27日 下午7:12:45
	 * @Description: TODO 根据区组查询服务期信息
	 * @param param
	 * 
	 */
   public List<ActionServerConfigBean> selectServerConfigInfoBy(ActionServerConfigBean actionServerConfigBean);
}
