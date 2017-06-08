/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：xuanwuba-collect
 * @作者：范永良
 * @联系方式：fanyongliang@gyyx.cn
 * @创建时间： 2015年10月14日
 * @版本号：v1.228
 * @本类主要用途描述：任务信息表业务
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.bll.xwbcreditsluckydraw;

import java.util.List;

import cn.gyyx.action.beans.xwbcreditsluckydraw.MissionBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.NewPageBean;
import cn.gyyx.action.dao.xwbcreditsluckydraw.MissionDAO;

public class MissionBLL {

	private MissionDAO missionDao = new MissionDAO();

	/**
	 * 
	 * @日期：2015年7月14日
	 * @Title: getMission
	 * @Description: TODO 获得所有任务信息
	 * @param page
	 * @return List<MissionBean>
	 */
	public List<MissionBean> getMission(int page) {
		return missionDao.getMission(page);
	}

	/**
	 * 
	 * @日期：2015年7月14日
	 * @Title: getMissionTotal
	 * @Description: TODO 获得所有任务数量
	 * @return int
	 */
	public int getMissionTotal() {
		return missionDao.getMissionTotal();
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
		return missionDao.addMission(mission);
	}

	/**
	 * 
	 * @日期：2015年10月14日
	 * @Title: getMissionInfoByCode
	 * @Description: TODO 根据code查任务详细信息
	 * @param code
	 * @return MissionBean
	 */
	public MissionBean getMissionInfoByCode(Integer code) {
		return missionDao.getMissionInfoByCode(code);
	}

	public int getMissionCount(NewPageBean newPageBean) {
		return missionDao.getMissionCount(newPageBean);

	}

	public List<MissionBean> getMissionBy(NewPageBean newPageBean) {
		return missionDao.getMissionBy(newPageBean);

	}

	public int updateMission(MissionBean mission) {
		return missionDao.updateMission(mission);

	}

	public int closeMission(MissionBean mission) {
		return missionDao.closeMission(mission);

	}

	public List<MissionBean> getMissionAll() {
		return missionDao.getMissionAll();

	}

	public MissionBean getOneRecommend() {
		return missionDao.getOneRecommend();

	}

	// 限制推荐任务
	public MissionBean getPubMissionInfo() {
		return missionDao.getPubMissionInfo();
	}
	/**
	 * 查询未完成任务数量
	 * @param account
	 * @return
	 */
	public Integer getUnFinishMissionCount(String account){
		return missionDao.getUnFinishMissionCount(account);
	}
}
