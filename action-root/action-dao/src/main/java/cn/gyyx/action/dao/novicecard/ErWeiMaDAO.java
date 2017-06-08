/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action
 * @作者：yangyusheng
 * @联系方式：yangyusheng@gyyx.cn
 * @创建时间： 2014年12月25日 上午11:23:21
 * @版本号：
 * @本类主要用途描述：二维码连接数据库DAO
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.novicecard;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;

import cn.gyyx.action.beans.novicecard.ErWeiMaBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class ErWeiMaDAO {

	private static final Logger logger = GYYXLoggerFactory
			.getLogger(ErWeiMaDAO.class);

	/**
	 * 
	 * @日期：2014年12月25日
	 * @Title: selectErWeiMaValid
	 * @Description: TODO 根据卡号、批次号、游戏ID查询二维码新手卡信息
	 * @param erWeiMa
	 * @return ErWeiMaBean
	 */

	public ErWeiMaBean selectErWeiMaValid(ErWeiMaBean erWeiMa) {

		SqlSession session = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory().openSession();
		ErWeiMaBean erWeiMaResult = null;
		try {
			IErWeiMaMapper erWeiMaMapper = session
					.getMapper(IErWeiMaMapper.class);
			erWeiMaResult = erWeiMaMapper.selectErWeiMaValid(erWeiMa);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return erWeiMaResult;
	}

}
