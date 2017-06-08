package cn.gyyx.action.bll.wdtombthreeteam;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.gyyx.action.beans.wdtombthreeteam.TombLoginInfoBean;
import cn.gyyx.action.beans.wdtombthreeteam.TombReserveInfoBean;
import cn.gyyx.action.beans.wdtombthreeteam.TombServeyInfoBean;
import cn.gyyx.action.beans.wdtombthreeteam.TombVoteInfoBean;
import cn.gyyx.action.dao.wdtombthreeteam.TombReserveInfoDAO;
import cn.gyyx.action.dao.wdtombthreeteam.TombServeyInfoDAO;
import cn.gyyx.action.dao.wdtombthreeteam.TombVoteInfoDAO;

/**
 * Created by DerCg on 2016/8/30.
 */
public class WdTombThreeTeamBLL {
    private TombReserveInfoDAO reserveInfoDAO = new TombReserveInfoDAO();
    private TombServeyInfoDAO serveyInfoDAO = new TombServeyInfoDAO();
    private TombVoteInfoDAO voteInfoDAO = new TombVoteInfoDAO();

    /**
     * 添加预约信息
     *
     * @param info
     * @return
     */
    public Boolean addReserveInfoBean(TombReserveInfoBean info) {
        return reserveInfoDAO.addReserveInfoBean(info) > 0;
    }

    /**
     * 获取预约信息
     *
     * @param code
     * @return
     */
    public TombReserveInfoBean selectByCode(int code) {
        return reserveInfoDAO.selectByCode(code);
    }

    /**
     * 获取预约信息
     *
     * @param phoneNum
     * @param channelType
     * @return
     */
    public TombReserveInfoBean selectByPhoneAndActionCode(String phoneNum, int actionCode) {
        return reserveInfoDAO.selectByPhoneAndActionCode(phoneNum, actionCode);
    }

    /**
     * 添加调查问卷信息
     *
     * @param info
     * @return
     */
    public Boolean addServeyInfoBean(TombServeyInfoBean info) {
        return serveyInfoDAO.addServeyInfoBean(info) > 0;
    }

    /**
     * 更新抽奖状态
     * @param reserveCode
     * @return
     */
    public void updateIsPrizeStatus(int reserveCode) {
        reserveInfoDAO.updateIsPrizeStatus(reserveCode);
    }

    /**
     * 获取调查问卷信息
     *
     * @param reserveCode
     * @return
     */
    public TombServeyInfoBean selectByReserveCode(int reserveCode) {
        return serveyInfoDAO.selectByReserveCode(reserveCode);
    }
    
    /**
     * 添加投票信息
     */
    public Boolean addVoteInfoBean(TombVoteInfoBean info){
    
      return   voteInfoDAO.addVoteInfoBean(info) > 0;
   }
    
    /**
     * 
     * 按手机号获取投票信息
     * 
     */
    public List<TombVoteInfoBean> selectByVotePhoneAndActionCode(String votterPhone, int actionCode) {
        return voteInfoDAO.selectByVotePhoneAndActionCode(votterPhone, actionCode);
        
       
    }
    
   /**
    * 按日期获取投票信息 
    */
    public TombVoteInfoBean  selectVoteInfoByDate(String votterPhone, int actionCode,String date) {
        return voteInfoDAO.selectVoteInfoByDate(votterPhone, actionCode,date);
        
       
    }

    /**
     * 获取个人投票记录信息
     * @param votterPhone
     * @param actioncode
     * @return
     */
    public List<TombVoteInfoBean> selectPesonalVoteLog(String votterPhone, int actioncode) {
	   return voteInfoDAO.selectByVotePhoneAndActionCode(votterPhone,actioncode);
    }

    /**
     * 获取所有投票记录信息
     * @param actioncode
     * @return
     */
	public List<Map<String,Integer>> selectAllVoteLog(int actioncode) {
		 
		
		return voteInfoDAO.selectByActionCode(actioncode);
	}
	/**
	 * 查询登录手机号信息
	 * @param votterPhone
	 * @return
	 */
	public TombLoginInfoBean selectLoginInfo(String votterPhone) {
		// TODO Auto-generated method stub
		return voteInfoDAO.selectLoginLog(votterPhone);
	}
/**
 * 添加手机号信息
 * @param loginInfoBean
 */
	public Boolean addLoginInfoBean(TombLoginInfoBean loginInfoBean) {
		return voteInfoDAO.addLoginInfoBean(loginInfoBean) > 0;
		
	}

	
    
}
