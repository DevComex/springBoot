/*------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：姜晗
 * 联系方式：jianghan@gyyx.cn 
 * 创建时间：2014年12月9日 下午5:44:29 
 * 版本号：v1.0 
 * 本类主要用途描述： 
 * 
-------------------------------------------------------------------------*/

package cn.gyyx.action.dao.novicecard;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;

import cn.gyyx.action.beans.novicecard.VirtualItemBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * @ClassName: VirtualItemDAO
 * @Description: TODO 对虚拟物品进行操作
 * @author jh
 * @date 2014年12月9日 下午6:01:51
 * 
 */
public class VirtualItemDAO {

	private static final Logger logger = GYYXLoggerFactory
			.getLogger(VirtualItemDAO.class);

	/**
	 * @Title: selectVirtualItemByCode
	 * @Author: jianghan
	 * @Description: TODO 通过物品编号取出物品信息
	 * @param code
	 * @return
	 * 
	 */
	public VirtualItemBean selectVirtualItemByCode(int code) {
		SqlSession sqlsession = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory().openSession();
		VirtualItemBean virtualItem = null;
		try {
			IVirtualItemMapper ivim = sqlsession
					.getMapper(IVirtualItemMapper.class);
			virtualItem = ivim.selectVirtualItemByCode(code);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			sqlsession.close();
		}
		return virtualItem;
	}
}
