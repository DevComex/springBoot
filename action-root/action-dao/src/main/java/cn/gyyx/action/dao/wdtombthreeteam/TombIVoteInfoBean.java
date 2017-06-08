package cn.gyyx.action.dao.wdtombthreeteam;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.wdtombthreeteam.TombLoginInfoBean;
import cn.gyyx.action.beans.wdtombthreeteam.TombVoteInfoBean;

public interface TombIVoteInfoBean {

	List<TombVoteInfoBean> selectByVotePhoneAndActionCode(@Param("votterPhone")String votterPhone, @Param("actionCode")int  actionCode);

	int addVoteInfoBean(TombVoteInfoBean voteInfoBean);

	TombVoteInfoBean selectVoteInfoByDate(@Param("votterPhone")String votterPhone, @Param("actionCode")int  actionCode, @Param("createTime")String  createTime );

	List<Map<String,Integer>> selectByActionCode(@Param("actionCode")int  actionCode);

	TombLoginInfoBean selectLoginLog(@Param("votterPhone")String votterPhone);

	int addLoginInfoBean(TombLoginInfoBean loginInfoBean);

//	List<TombVoteInfoBean> selectPesonalVoteLog(String votterPhone, int actioncode);

	
	
	
}
