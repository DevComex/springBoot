package cn.gyyx.action.dao.wdnationaldayshare;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.wdnationalday.WdPrizesLogBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class WdPrizesLogDao {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(WdPrizesLogDao.class);
	
	/**
	 * 
	 * @日期：20160924
	 * 
	 * @Title: getSession
	 * @Description: TODO 获取session
	 * @return SqlSession
	 */
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	//查询奖品剩余数量
	public int selectPrizesNum(WdPrizesLogBean wdPrizesLogBean){
		SqlSession session = getSession();	
		int Num=0;
		try {
			IWdPrizesLogMapper Mapper = session.getMapper(IWdPrizesLogMapper.class);
			Num=Mapper.selectPrizesNum(wdPrizesLogBean);
			return Num;
		} catch (Exception e) {
			logger.warn(e.toString());

		} finally {
			session.close();
		}
		return Num;
		
	}
	//减少奖品数量
	public int reducePrizesNum(WdPrizesLogBean wdPrizesLogBean){
		SqlSession session = getSession();	
		int reduceNum=0;
		try {
			IWdPrizesLogMapper Mapper = session.getMapper(IWdPrizesLogMapper.class);
			reduceNum=Mapper.reducePrizesNum(wdPrizesLogBean);
			session.commit();
			return reduceNum;
		} catch (Exception e) {
			logger.warn(e.toString());

		} finally {
			session.close();
		}
		return reduceNum;
	
	
}
}
