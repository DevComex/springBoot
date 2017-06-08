/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action
 * @作者：王雷
 * @联系方式：wanglei@gyyx.cn
 * @创建时间：2014年12月10日 
 * @版本号：v1.0
 * @本类主要用途描述：对novice_card_receive_log表操作的类
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.novicecard;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;

import cn.gyyx.action.beans.novicecard.NoviceCardReceiveLogBean;
import cn.gyyx.action.beans.novicecard.NoviceOaBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class ReceiveLogDAO {

	private static final Logger logger = GYYXLoggerFactory
			.getLogger(ReceiveLogDAO.class);

	/**
	 * 日期：2014年12月9日 作者：王雷 方法名：selectLogAccount 参 数：@param userId 参 数：@paramserverId
	 * 参 数：@param gameId 参 数：@param batchNo 批次号
	 * 描述：通过userId、serverId、gameId查询novice_card_receive_log中的数据
	 */
	public int selectLogAccount(Integer userId,
			Integer serverId, Integer gameId, Integer batchNo) {
		SqlSession session = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory().openSession();
		int result = -1;
		try {
			IReceiveLogMapper receiveLog = session
					.getMapper(IReceiveLogMapper.class);
			NoviceCardReceiveLogBean receiveLogPara = new NoviceCardReceiveLogBean();
			receiveLogPara.setUserId(userId);
			receiveLogPara.setServerId(serverId);
			receiveLogPara.setGameId(gameId);
			receiveLogPara.setBatchNo(batchNo);
			result = receiveLog.selectLogAccount(receiveLogPara);
		} finally {
			session.close();
		}
		return result;
	}

	/**
	 * @Title: setNoviceCardReceiveLog
	 * @Author: jianghan
	 * @date 2014年12月13日 下午7:12:31
	 * @Description: TODO 向新手卡日志插入信息
	 * @param noviceCardReceiveLogBean
	 * @param checkType
	 * @return
	 * 
	 */
	public boolean setNoviceCardReceiveLog(
			NoviceCardReceiveLogBean noviceCardReceiveLogBean) {

		boolean isRerurn = false;
		SqlSession session = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory().openSession();
		try {
			IReceiveLogMapper receiveLog = session
					.getMapper(IReceiveLogMapper.class);
			isRerurn = receiveLog
					.setNoviceCardReceiveLog(noviceCardReceiveLogBean);
			session.commit();
		} catch (Exception e) {
			logger.error(e.toString());
		} finally {
			session.close();
		}
		return isRerurn;
	}

	/**
	 * @Title: setReceiveLog
	 * @Author: jianghan
	 * @date 2014年12月13日 下午7:12:45
	 * @Description: TODO 插入新手卡日誌表，存储过程
	 * @param param
	 * 
	 */
	public int setReceiveLog(NoviceCardReceiveLogBean param) {
		SqlSession session = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory().openSession();
		try {

			IReceiveLogMapper receiveLog = session
					.getMapper(IReceiveLogMapper.class);
			receiveLog.spNoviceCardReceiveLog(param);
			session.commit();

		} catch (Exception e) {
			logger.error(e.toString());
		} finally {
			session.close();
		}
		return param.getErrorCode();
	}

	/**
	 * 
	 * @日期：2014年12月25日
	 * @Title: setReceiveLogV3
	 * @Description: TODO 插入二维码新手卡日志 存储过程
	 * @param noviceCardReceiveLogBean
	 * @return int
	 */

	public int setReceiveLogV3(NoviceCardReceiveLogBean noviceCardReceiveLogBean) {
		SqlSession session = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory().openSession();
		try {

			IReceiveLogMapper receiveLog = session
					.getMapper(IReceiveLogMapper.class);
			receiveLog.spNoviceCardReceiveLogV3(noviceCardReceiveLogBean);
			session.commit();
		} catch (Exception e) {
			logger.error(e.toString());
		} finally {
			session.close();
		}
		return noviceCardReceiveLogBean.getErrorCode();
	}
	
	public List<NoviceOaBean> getReceiveLogByAccountAndTimeAndLikeGiftPageName(
			String account, String timeStr, String hdType) {
		try(SqlSession session = MyBatisConnectionFactory.getSqlActionDBV2SessionFactory().openSession();){
			IReceiveLogMapper receiveLog = session
					.getMapper(IReceiveLogMapper.class);
			return receiveLog.getReceiveLogByAccountAndTimeAndLikeGiftPageName(account,timeStr,hdType);
		}
	}

	public List<NoviceOaBean> getSendToGameLogByAccountAndTimeAndLikeGiftPageName(
			String account, String timeStr, String hdType) {
		try(SqlSession session = MyBatisConnectionFactory.getSqlActionDBV2SessionFactory().openSession();){
			IReceiveLogMapper receiveLog = session
					.getMapper(IReceiveLogMapper.class);
			return receiveLog.getSendToGameLogByAccountAndTimeAndLikeGiftPageName(account,timeStr,hdType);
		}
	}

	public List<NoviceOaBean> getNoviceServerList(String batchNo,
			String activityId) {
		try(SqlSession session = MyBatisConnectionFactory.getSqlActionDBV2SessionFactory().openSession();){
			IReceiveLogMapper receiveLog = session
					.getMapper(IReceiveLogMapper.class);
			return receiveLog.getNoviceServerList(batchNo,activityId);
		}
	}

	public List<Map<String, String>> getNoviceReceiveCountByMonthAndLikeGiftPackage(String month,String hdType,int serverId) {
		try(SqlSession session = MyBatisConnectionFactory.getSqlActionDBV2SessionFactory().openSession();){
			IReceiveLogMapper receiveLog = session
					.getMapper(IReceiveLogMapper.class);
			return receiveLog.getNoviceReceiveCountByMonthAndLikeGiftPackage(month,hdType,serverId);
		}
	}

	public void deleteActionServerConfig(int code) {
		try(SqlSession session = MyBatisConnectionFactory.getSqlActionDBV2SessionFactory().openSession();){
			IReceiveLogMapper receiveLog = session
					.getMapper(IReceiveLogMapper.class);
			receiveLog.deleteActionServerConfig(code);
			session.commit();
		}
	}

	public void deleteNoviceCardServer(int code) {
		try(SqlSession session = MyBatisConnectionFactory.getSqlActionDBV2SessionFactory().openSession();){
			IReceiveLogMapper receiveLog = session
					.getMapper(IReceiveLogMapper.class);
			receiveLog.deleteNoviceCardServer(code);
			session.commit();
		}
	}

	public int getNoviceCardServerCountByCodeAndBatchNo(int serverId,int batchNo) {
		try(SqlSession session = MyBatisConnectionFactory.getSqlActionDBV2SessionFactory().openSession();){
			IReceiveLogMapper receiveLog = session
					.getMapper(IReceiveLogMapper.class);
			return receiveLog.getNoviceCardServerCountByCodeAndBatchNo(serverId,batchNo);
		}
	}

	public void insertNoviceCardServer(NoviceOaBean bean) {
		try(SqlSession session = MyBatisConnectionFactory.getSqlActionDBV2SessionFactory().openSession();){
			IReceiveLogMapper receiveLog = session
					.getMapper(IReceiveLogMapper.class);
			receiveLog.insertNoviceCardServer(bean);
			session.commit();
		}
	}

}
