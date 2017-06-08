/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：xuanwuba-collect
 * @作者：范永良
 * @联系方式：fanyongliang@gyyx.cn
 * @创建时间： 2015年10月14日
 * @版本号：v1.228
 * @本类主要用途描述：任务信息表数据访问
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.xwbcreditsluckydraw;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.xwbcreditsluckydraw.MissionBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.NewPageBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class MissionDAO {

	private static final Logger logger = GYYXLoggerFactory
			.getLogger(MissionDAO.class);

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
	 * @日期：2015年7月14日
	 * @Title: getMission
	 * @Description: TODO 获得所有任务
	 * @param page
	 * @return List<MissionBean>
	 */
	public List<MissionBean> getMission(int page) {
		List<MissionBean> missionList = null;
		SqlSession session = getSession();
		try {
			IMissionMapper missionMapper = session
					.getMapper(IMissionMapper.class);
			missionList = missionMapper.getMission(page);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return missionList;
	}

	/**
	 * 
	 * @日期：2015年7月14日
	 * @Title: getMissionTotal
	 * @Description: TODO 查询所有任务数量
	 * @return int
	 */
	public int getMissionTotal() {
		int result = 0;
		SqlSession session = getSession();
		try {
			IMissionMapper missionMapper = session
					.getMapper(IMissionMapper.class);
			result = missionMapper.getMissionTotal();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return result;
	}
	

	/**
	 * 
	 * @日期：2015年7月13日
	 * @Title: addMission
	 * @Description: TODO 添加任务
	 * @param mission
	 * @return int
	 */
	public int addMission(MissionBean mission) {
		int result = 0;
		SqlSession session = getSession();
		try {
			IMissionMapper missionMapper = session
					.getMapper(IMissionMapper.class);
			result = missionMapper.addMission(mission);
			session.commit();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return result;
	}
	public int updateMission(MissionBean mission) {
		int result = 0;
		SqlSession session = getSession();
		try {
			IMissionMapper missionMapper = session
					.getMapper(IMissionMapper.class);
			result = missionMapper.updateMission(mission);
			session.commit();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return result;
	}
	public int closeMission(MissionBean mission) {
		int result = 0;
		SqlSession session = getSession();
		try {
			IMissionMapper missionMapper = session
					.getMapper(IMissionMapper.class);
			result = missionMapper.missionClose(mission);
			session.commit();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return result;
	}
	
	/**
	 * 
	 * @日期：2015年10月14日
	 * @Title: getMissionInfoByCode
	 * @Description: TODO 根据code查任务详细信息
	 * @param code
	 * @return MissionBean
	 */
	public MissionBean getMissionInfoByCode(Integer code){
		MissionBean mission = null;
		SqlSession session = getSession();
		try {
			IMissionMapper missionMapper = session
					.getMapper(IMissionMapper.class);
			mission = missionMapper.getMissionInfoByCode(code);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return mission;
	}
	public List<MissionBean> getMissionBy(NewPageBean newPageBean){
		List<MissionBean> missionList = null;
		SqlSession session = getSession();
		try {
			IMissionMapper missionMapper = session
					.getMapper(IMissionMapper.class);
			missionList = missionMapper.getMissionBy(newPageBean);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return missionList;
	}
	public int getMissionCount(NewPageBean newPageBean){
		
		int result = 0;
		SqlSession session = getSession();
		try {
			IMissionMapper missionMapper = session
					.getMapper(IMissionMapper.class);
			result = missionMapper.getMissionCount(newPageBean);
			session.commit();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return result;
		
		
	}
	public List<MissionBean> getMissionAll(){
		List<MissionBean> missionList = null;
		SqlSession session = getSession();
		try {
			IMissionMapper missionMapper = session
					.getMapper(IMissionMapper.class);
			missionList = missionMapper.getMissionAll();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return missionList;
	}
	public MissionBean getOneRecommend(){
		MissionBean mission = null;
		SqlSession session = getSession();
		try {
			IMissionMapper missionMapper = session
					.getMapper(IMissionMapper.class);
			mission = missionMapper.getOneRecommend();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return mission;
	}
	
	//限制推荐任务
	public MissionBean getPubMissionInfo(){
		MissionBean mission = null;
		SqlSession session = getSession();
		try {
			IMissionMapper missionMapper = session
					.getMapper(IMissionMapper.class);
			mission = missionMapper.getPubMissionInfo();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return mission;
	}
	
	/**
	 * 查询未完成任务数量
	 * @param account
	 * @return
	 */
	public Integer getUnFinishMissionCount(String account){
		Integer count = 0;
		SqlSession session = getSession();
		try {
			IMissionMapper missionMapper = session
					.getMapper(IMissionMapper.class);
			count = missionMapper.getUnFinishMissionCount(account);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return count;
	}
}
