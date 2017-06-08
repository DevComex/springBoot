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

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;

import cn.gyyx.action.beans.wd9yearnovicebag.BagActivityConfigBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * @ClassName: ActivityConfigDAO
 * @Description: TODO 通过code获取活动配置
 * @author jh
 * @date 2014年12月9日 下午12:27:49
 * 
 */
public class BagActivityConfigDAO {

	private static final Logger logger = GYYXLoggerFactory
			.getLogger(BagActivityConfigDAO.class);

	/**
	 * 
	 * @日期：2014年12月11日
	 * @Title: selectActivityConfigByhdName
	 * @Description: TODO
	 * @param hdName
	 * @return ActivityConfigBean
	 */

	public BagActivityConfigBean selectActivityConfigByhdName(String hdName) {
		SqlSession sqlsession = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory().openSession();
		BagActivityConfigBean bagActivityConfigBean = null;
		try {
			BagActivityConfigMapper acm = sqlsession
					.getMapper(BagActivityConfigMapper.class);
			bagActivityConfigBean = acm.selectActivityConfigByhdName(hdName);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			sqlsession.close();
		}
		return bagActivityConfigBean;
	}
}
