/*------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：姜晗
 * 联系方式：jianghan@gyyx.cn 
 * 创建时间：2014年12月9日 下午6:05:23 
 * 版本号：v1.0 
 * 本类主要用途描述： 对虚拟物品进行操作
 * 
-------------------------------------------------------------------------*/

package cn.gyyx.action.bll.novicecard;

import org.slf4j.Logger;

import cn.gyyx.action.beans.novicecard.ResultBean;
import cn.gyyx.action.beans.novicecard.VirtualItemBean;
import cn.gyyx.action.dao.novicecard.VirtualItemDAO;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * @ClassName: VirtualItemBll
 * @Description: TODO 对虚拟物品进行操作
 * @author jh
 * @date 2014年12月9日 下午6:05:30
 * 
 */
public class VirtualItemBll {

	private VirtualItemDAO vid = new VirtualItemDAO();
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(VirtualItemBll.class);

	/**
	 * 
	 * @日期：2014年12月12日
	 * @Title: getVirtualItemByCode
	 * @Description: TODO 读取物品
	 * @param itemId
	 * @return ResultBean<VirtualItemBean>
	 */

	public ResultBean<VirtualItemBean> getVirtualItemByCode(int itemId) {
		logger.debug("itemId", itemId);
		ResultBean<VirtualItemBean> result = new ResultBean<VirtualItemBean>();
		VirtualItemBean virturalItem = new VirtualItemBean();
		try {
			virturalItem = vid.selectVirtualItemByCode(itemId);
			if (virturalItem == null) {
				result.setProperties(false, "卡号对应物品不存在", null);
				logger.debug("result", result);
				return result;
			}
			result.setProperties(true, "", virturalItem);
		} catch (Exception e) {
			logger.warn(e.toString());
			result.setProperties(false, "读取奖励物品出错", null);
		}
		logger.debug("result", result);
		return result;
	}
}
