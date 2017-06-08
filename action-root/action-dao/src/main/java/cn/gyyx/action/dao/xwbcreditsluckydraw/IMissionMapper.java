/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：xuanwuba-collect
 * @作者：范永良
 * @联系方式：fanyongliang@gyyx.cn
 * @创建时间： 2015年10月14日
 * @版本号：v1.228
 * @本类主要用途描述：任务信息表接口
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.xwbcreditsluckydraw;

import java.util.List;

import cn.gyyx.action.beans.xwbcreditsluckydraw.MissionBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.NewPageBean;

public interface IMissionMapper {

	/**
	 * 
	 * @日期：2015年7月14日
	 * @Title: getMission
	 * @Description: TODO 获得所有任务
	 * @param page
	 * @return List<MissionBean>
	 */
	public List<MissionBean> getMission(int page);

	/**
	 * 
	 * @日期：2015年7月14日
	 * @Title: getMissionTotal
	 * @Description: TODO 查询所有任务数量
	 * @return int
	 */
	public int getMissionTotal();

	/**
	 * 
	 * @日期：2015年7月13日
	 * @Title: addMission
	 * @Description: TODO 添加任务
	 * @param mission
	 * @return int
	 */
	public int addMission(MissionBean mission);
	/**
	 * 
	 * @日期：2015年7月13日
	 * @Title: addMission
	 * @Description: TODO 编辑任务
	 * @param mission
	 * @return int
	 */
	public int updateMission(MissionBean mission);
	/**
	 * 
	 * @日期：2015年7月13日
	 * @Title: addMission
	 * @Description: TODO 关闭任务
	 * @param mission
	 * @return int
	 */
	public int missionClose(MissionBean mission);
	
	
	public MissionBean getMissionInfoByCode(Integer code);
	public List<MissionBean> getMissionBy(NewPageBean newPageBean);
	public int getMissionCount(NewPageBean newPageBean);
	public List<MissionBean> getMissionAll();
	public MissionBean getOneRecommend();
	
	//限制推荐任务
	public MissionBean getPubMissionInfo();
	/**
	 * 查询未完成任务数量
	 * @param account
	 * @return
	 */
	public Integer getUnFinishMissionCount(String account);
}
