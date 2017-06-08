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

import cn.gyyx.action.beans.novicecard.NoviceCardBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class NoviceCardDAO {

	private static final Logger logger = GYYXLoggerFactory
			.getLogger(NoviceCardDAO.class);

	/**
	 * 通过卡号获取卡实体
	 * 
	 * @param cardNo
	 *            卡号
	 * @return 卡实体
	 */
	public NoviceCardBean selectNoviceCardByCardNo(String cardNo) {
		SqlSession session = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory().openSession();
		try {
			INoviceCardMapper novicecard = session
					.getMapper(INoviceCardMapper.class);
			return novicecard.selectNoviceCardByCardNo(cardNo);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return null;
	}

}
