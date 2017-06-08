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

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;

import cn.gyyx.action.beans.wd9yearnovicebag.HdSendPresentLogBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class HdSendPresentLogDAO {

	private static final Logger logger = GYYXLoggerFactory
			.getLogger(HdSendPresentLogDAO.class);
	
	/**
	 * 插入日志信息
	 * @param hdSendPresentLogBean
	 */
	public boolean insertHdSendPresentLog(HdSendPresentLogBean hdSendPresentLogBean){
		boolean flag = false;
		SqlSession session = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory().openSession();
		try {
			IHdSendPresentLogMapper receiveLog = session
					.getMapper(IHdSendPresentLogMapper.class);
			receiveLog.insertHdSendPresentLog(hdSendPresentLogBean);
			session.commit();
			flag = true;
		} catch (Exception e) {
			logger.warn(e.toString());
			flag = false;
		} finally {
			session.close();
		}
		return flag;
	}

	/**
	 * 日期：2014年12月9日 作者：王雷 方法名：selectLogAccount 参 数：@param userId 参 数：@paramserverId
	 * 参 数：@param gameId 参 数：@param batchNo 批次号
	 * 返回值：List<NoviceCardReceiveLogBean>
	 * 描述：通过userId、serverId、gameId查询novice_card_receive_log中的数据
	 */
	public int selectBagAccount(String account,
			Integer serverId, Integer gameId, Integer activityId) {
		SqlSession session = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory().openSession();
		int result = -1;
		try {
			IHdSendPresentLogMapper receiveLog = session
					.getMapper(IHdSendPresentLogMapper.class);
			HdSendPresentLogBean hdSendPresentLogBean = new HdSendPresentLogBean();
			hdSendPresentLogBean.setAccount(account);
			hdSendPresentLogBean.setServerId(serverId);
			hdSendPresentLogBean.setGameId(gameId);
			hdSendPresentLogBean.setActivityId(activityId);
			result = receiveLog.selectBagLogAccount(hdSendPresentLogBean);
		} finally {
			session.close();
		}
		return result;
	}
	/**
	 * 通过userId、gameId、activityId查询数据
	 * @param selectPresentLog
	 * @return
	 */
	public List<HdSendPresentLogBean> selectPresentLog(HdSendPresentLogBean hdSendPresentLogBean){
		SqlSession session = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory().openSession();
		List<HdSendPresentLogBean> resultList = null;
		try {
			IHdSendPresentLogMapper receiveLog = session
					.getMapper(IHdSendPresentLogMapper.class);
			resultList = receiveLog.selectPresentLog(hdSendPresentLogBean);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return resultList;
	}
	public List<HdSendPresentLogBean> selectPresentLogByavailable(HdSendPresentLogBean hdSendPresentLogBean){
		SqlSession session = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory().openSession();
		List<HdSendPresentLogBean> resultList = null;
		try {
			IHdSendPresentLogMapper receiveLog = session
					.getMapper(IHdSendPresentLogMapper.class);
			resultList = receiveLog.selectPresentLogByavailable(hdSendPresentLogBean);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return resultList;
	}
	/**
	 * @Title: setReceiveLog
	 * @Author: jianghan
	 * @date 2014年12月13日 下午7:12:45
	 * @Description: TODO 插入新手卡日誌表，存储过程
	 * @param param
	 * 
	 */
	public int setReceiveLog(HdSendPresentLogBean hdSendPresentLogBean) {
		Integer result = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("activityId", hdSendPresentLogBean.getActivityId());
		map.put("source", hdSendPresentLogBean.getSource());
		map.put("sourceCode", hdSendPresentLogBean.getSourceCode());
		map.put("account", hdSendPresentLogBean.getAccount());
		map.put("gameId", hdSendPresentLogBean.getGameId());
		map.put("serverId", hdSendPresentLogBean.getServerId());
		map.put("serverName", hdSendPresentLogBean.getServerName());
		map.put("presentType", hdSendPresentLogBean.getPresentType());
		map.put("presentName", hdSendPresentLogBean.getPresentName());
		map.put("drawTime", new Date());
		map.put("drawIp", hdSendPresentLogBean.getDrawIp());
		map.put("errorCode", 0);

		SqlSession session = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory().openSession();
		try {

			IHdSendPresentLogMapper receiveLog = session
					.getMapper(IHdSendPresentLogMapper.class);
			receiveLog.spNoviceCardReceiveLog(map);
			result = (int) map.get("errorCode");
			session.commit();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return result;

	}

}
