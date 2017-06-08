/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：xuanwuba-collect
 * @作者：范永良
 * @联系方式：fanyongliang@gyyx.cn
 * @创建时间： 2015年10月14日
 * @版本号：v1.228
 * @本类主要用途描述：任务领取记录表数据访问
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.xwbcreditsluckydraw;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.xwbcreditsluckydraw.MissionReceiveBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class MissionReceiveDAO {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(MissionReceiveDAO.class);

	/**
	 * 获取session
	 */
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}

	/**
	 * 
	 * @日期：2015年10月14日
	 * @Title: getMissionReceiveByAccountAndType
	 * @Description: TODO 根据code查任务详细信息
	 * @param account
	 *            , missionType
	 * @return MissionReceiveBean
	 */
	public List<MissionReceiveBean> getMissionReceiveByAccountAndType(String account,
			String missionType) {
		List<MissionReceiveBean> missionReceiveBean = null;
		SqlSession session = getSession();
		try {
			IMissionReceiveMapper iMissionReceiveMapper = session
					.getMapper(IMissionReceiveMapper.class);
			missionReceiveBean = iMissionReceiveMapper
					.getMissionReceiveByAccountAndType(account, missionType);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return missionReceiveBean;
	}

	/**
	 * 
	 * @日期：2015年10月14日
	 * @Title: "updateCompleteStatus"
	 * @Description: TODO 根据编号修改完成状态
	 * @param code
	 *            , completeStatus
	 * @return
	 */
	public void updateCompleteStatus(Integer code, String completeStatus,Date finishTime) {
		SqlSession session = getSession();
		try {
			IMissionReceiveMapper iMissionReceiveMapper = session
					.getMapper(IMissionReceiveMapper.class);
			iMissionReceiveMapper.updateCompleteStatus(code, completeStatus,finishTime);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.commit();
			session.close();
		}
	}
	/**
	 * 
	 * @日期：2015年10月14日
	 * @Title: "getMissionReceiveLog"
	 * @Description: TODO 查询完成记录 
	 * @param 
	 * @return 
	 */
	public List<MissionReceiveBean> getMissionReceiveLog(MissionReceiveBean missionReceiveBean){
		List<MissionReceiveBean> missionReceive = null;
		SqlSession session = getSession();
		try {
			IMissionReceiveMapper iMissionReceiveMapper = session
					.getMapper(IMissionReceiveMapper.class);
			missionReceive = iMissionReceiveMapper
					.getMissionReceiveLog(missionReceiveBean);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return missionReceive;
	}
	/**
	 * 
	 * @日期：2015年10月14日
	 * @Title: getMissionReceiveBycode
	 * @Description: TODO 根据code查任务详细信息
	 * @param missionCode , acount
	 * @return MissionReceiveBean
	 */
	public MissionReceiveBean getMissionReceiveBycode(int missionCode,String acount){
		MissionReceiveBean missionReceiveBean = null;
		SqlSession session = getSession();
		try {
			IMissionReceiveMapper iMissionReceiveMapper = session
					.getMapper(IMissionReceiveMapper.class);
			missionReceiveBean = iMissionReceiveMapper
					.getMissionReceiveBycode(missionCode, acount);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return missionReceiveBean;
	}
	public void updateCount(Integer code,Integer count){
		SqlSession session = getSession();
		try {
			IMissionReceiveMapper iMissionReceiveMapper = session
					.getMapper(IMissionReceiveMapper.class);
			iMissionReceiveMapper.updateCount(code, count);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.commit();
			session.close();
		}
	}
	public void addMissionReceive(MissionReceiveBean missionReceiveBean){
		SqlSession session = getSession();
		try {
			IMissionReceiveMapper iMissionReceiveMapper = session
					.getMapper(IMissionReceiveMapper.class);
			iMissionReceiveMapper.addMissionReceive(missionReceiveBean);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.commit();
			session.close();
		}
	}
	
	/**
	 * 根据编号查询是否已经被领取任务
	 * @param code
	 * @return
	 */
	public Integer getCountReceiveByCode(Integer code){
		Integer count = 0;
		SqlSession session = getSession();
		try {
			IMissionReceiveMapper iMissionReceiveMapper = session
					.getMapper(IMissionReceiveMapper.class);
			count = iMissionReceiveMapper
					.getCountReceiveByCode(code);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return count;
	}
}
