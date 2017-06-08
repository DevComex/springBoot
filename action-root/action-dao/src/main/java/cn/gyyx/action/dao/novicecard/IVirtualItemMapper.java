/*------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：姜晗
 * 联系方式：jianghan@gyyx.cn 
 * 创建时间：2014年12月9日 下午5:45:09 
 * 版本号：v1.0 
 * 本类主要用途描述： 
 * 
-------------------------------------------------------------------------*/

package cn.gyyx.action.dao.novicecard;

import cn.gyyx.action.beans.novicecard.VirtualItemBean;

/**
 * @ClassName: IVirtualItemMapper
 * @Description: TODO 操作虚拟物品接口
 * @author jh
 * @date 2014年12月10日 下午2:53:47
 * 
 */
public interface IVirtualItemMapper {
	/**
	 * @Title: selectVirtualItemByCode
	 * @Author: jianghan 根据code返回虚拟物品
	 * @Description: TODO
	 * @param code
	 * @return
	 * 
	 */
	public VirtualItemBean selectVirtualItemByCode(int code);
}
