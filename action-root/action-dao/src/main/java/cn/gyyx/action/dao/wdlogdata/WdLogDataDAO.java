/**------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：liqiang
 * 联系方式：liqiang@gyyx.cn 
 * 创建时间： 2015/11/11 
 * 版本号：v1.0 
 * 本类主要用途描述： 
-------------------------------------------------------------------------*/
package cn.gyyx.action.dao.wdlogdata;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.wdlogdata.WdLogDataBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * 安装与卸载问道LOG数据的DAO
 */
public class WdLogDataDAO implements WdLogDataMapper {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(WdLogDataDAO.class);

	/* 获取Session */
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}

	/**
	 * 保存正确的安装和卸载的LOG数据
	 * */
	public boolean saveLog(WdLogDataBean log) {
		SqlSession session = getSession();
		boolean result = false;
		logger.info(toString());
		try {
			WdLogDataMapper entity = session.getMapper(WdLogDataMapper.class);
			result = entity.saveLog(log);
		} catch (Exception e) {
			logger.error("数据访问异常" + e.toString());
		} finally {
			session.commit();
			session.close();
		}
		return result;
	}
}