/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：xuanwuba
 * @作者：yangyusheng
 * @联系方式：yangyusheng@gyyx.cn
 * @创建时间： 2015年7月14日 下午12:04:13
 * @版本号：
 * @本类主要用途描述：操作记录连接数据库
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.xwbcreditsluckydraw;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.xwbcreditsluckydraw.OprateRecordsBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class OprateRecordsDAO {

	private static final Logger logger = GYYXLoggerFactory
			.getLogger(OprateRecordsDAO.class);

	/**
	 * 
	 * @日期：2015年7月13日
	 * @Title: getSession
	 * @Description: TODO 获取session
	 * @return SqlSession
	 */
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}

	/**
	 * 
	 * @日期：2015年7月14日
	 * @Title: getRecords
	 * @Description: TODO 根据条件获得操作记录
	 * @param oprate
	 * @return List<OprateRecordsBean>
	 */
	public List<OprateRecordsBean> getRecords(OprateRecordsBean oprate) {
		List<OprateRecordsBean> recordList = null;
		SqlSession session = getSession();
		try {
			IOprateRecordsMapper recordMapper = session
					.getMapper(IOprateRecordsMapper.class);
			recordList = recordMapper.getRecords(oprate);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return recordList;
	}

	/**
	 * 
	 * @日期：2015年7月14日
	 * @Title: getRecordsTotal
	 * @Description: TODO 获得操作记录条数
	 * @param record
	 * @return int
	 */
	public int getRecordsTotal(OprateRecordsBean record) {
		int result = 0;
		SqlSession session = getSession();
		try {
			IOprateRecordsMapper recordMapper = session
					.getMapper(IOprateRecordsMapper.class);
			result = recordMapper.getRecordsTotal(record);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return result;
	}

	/**
	 * 
	 * @日期：2015年7月14日
	 * @Title: addRecord
	 * @Description: TODO 添加任务
	 * @param oprate
	 * @return int
	 */
	public int addRecord(OprateRecordsBean oprate) {
		int result = 0;
		SqlSession session = getSession();
		try {
			IOprateRecordsMapper recordMapper = session
					.getMapper(IOprateRecordsMapper.class);
			result = recordMapper.addRecord(oprate);
			session.commit();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return result;
	}
	
	/**
	 * 添加重置标识
	 * @param materialCode
	 */
	public void setResetFlag(Integer materialCode){
		SqlSession session = getSession();
		try {
			IOprateRecordsMapper recordMapper = session
					.getMapper(IOprateRecordsMapper.class);
			recordMapper.setResetFlag(materialCode);
			session.commit();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
	}
}
