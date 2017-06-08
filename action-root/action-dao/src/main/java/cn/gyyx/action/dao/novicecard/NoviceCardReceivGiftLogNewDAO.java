/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action
 * @作者：yangyusheng
 * @联系方式：yangyusheng@gyyx.cn
 * @创建时间： 2014年12月9日 下午4:49:56
 * @版本号：
 * @本类主要用途描述：
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.novicecard;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;

import cn.gyyx.action.beans.novicecard.NoviceCardReceivGiftLog;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class NoviceCardReceivGiftLogNewDAO {

	private static final Logger logger = GYYXLoggerFactory
			.getLogger(NoviceCardReceivGiftLogNewDAO.class);

	public int insert(NoviceCardReceivGiftLog log) {
		SqlSession session = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory().openSession();
		try {
			INoviceCardReceivGiftLogNewMapper mapper = session
					.getMapper(INoviceCardReceivGiftLogNewMapper.class);
			return mapper.insert(log);
		} catch (Exception e) {
			logger.warn(e.toString(),e);
		} finally {
			session.commit();
			session.close();
		}
		return -1;
	}

}
