/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：xuanwuba-collect
 * @作者：范永良
 * @联系方式：fanyongliang@gyyx.cn
 * @创建时间： 2015年10月14日
 * @版本号：v1.228
 * @本类主要用途描述：任务领取记录表业务
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.bll.xwbcreditsluckydraw;

import java.util.Date;
import java.util.List;

import cn.gyyx.action.beans.xwbcreditsluckydraw.MissionReceiveBean;
import cn.gyyx.action.dao.xwbcreditsluckydraw.MissionReceiveDAO;

public class MissionReceiveBLL {

	private MissionReceiveDAO missionReceiveDAO = new MissionReceiveDAO();

	/**
	 * 
	 * @日期：2015年10月14日
	 * @Title: getMissionReceiveByAccountAndType
	 * @Description: TODO 根据code查任务详细信息
	 * @param account , missionType
	 * @return MissionReceiveBean
	 */
	public List<MissionReceiveBean> getMissionReceiveByAccountAndType(String account,String missionType){
		return missionReceiveDAO.getMissionReceiveByAccountAndType(account,missionType);
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
		missionReceiveDAO.updateCompleteStatus(code,completeStatus,finishTime);
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
		return missionReceiveDAO.getMissionReceiveLog(missionReceiveBean);
		
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
		return missionReceiveDAO.getMissionReceiveBycode(missionCode, acount);
		
	}
	public void updateCount(Integer code,Integer count){
		missionReceiveDAO.updateCount(code, count);
	}
	public void addMissionReceive(MissionReceiveBean missionReceiveBean){
		missionReceiveDAO.addMissionReceive(missionReceiveBean);
	}
	
	/**
	 * 根据编号查询是否已经被领取任务
	 * @param code
	 * @return
	 */
	public Integer getCountReceiveByCode(Integer code){
		return missionReceiveDAO.getCountReceiveByCode(code);
	}
}
