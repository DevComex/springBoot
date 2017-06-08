/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action
 * @作者：yangyusheng
 * @联系方式：yangyusheng@gyyx.cn
 * @创建时间： 2014年12月8日 上午16:52:21
 * @版本号：
 * @本类主要用途描述：
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.novicecard;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;

import cn.gyyx.action.beans.novicecard.ServerBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class ServerDAO {

	private static final Logger logger = GYYXLoggerFactory
			.getLogger(ServerDAO.class);

	/**
	 * 
	 * @日期：2014年12月8日
	 * @Title: selectServerByGameIdAndState
	 * @Description: TODO
	 * @return List<ServerBean>
	 */

	public List<ServerBean> selectServerByGameIdAndState(ServerBean server) {
		SqlSession session = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory().openSession();
		List<ServerBean> serverList = null;
		try {
			IServerMapper serverMapper = session.getMapper(IServerMapper.class);
			serverList = serverMapper.selectServerByGameIdAndState(server);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return serverList;
	}

}
