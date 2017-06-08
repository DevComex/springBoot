/**------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：liqiang
 * 联系方式：liqiang@gyyx.cn 
 * 创建时间： 2015/11/11 
 * 版本号：v1.0 
 * 本类主要用途描述： 
-------------------------------------------------------------------------*/
package cn.gyyx.action.dao.wdlogdata;

import cn.gyyx.action.beans.wdlogdata.WdLogDataBean;

/**
 * 安装与卸载问道LOG数据的Mapper
 */
public interface WdLogDataMapper {
	/**
	 * 保存正确的安装和卸载的LOG数据
	 * */
	public boolean saveLog(WdLogDataBean log);
}
