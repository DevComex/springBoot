package cn.gyyx.action.dao.wdtombthreeteam;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.beans.wdtombthreeteam.TombLoginInfoBean;
import cn.gyyx.action.beans.wdtombthreeteam.TombVoteInfoBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

/**
 * 投票数据访问层
 * @author 
 *
 */

public class TombVoteInfoDAO {

	SqlSessionFactory factory = MyBatisConnectionFactory.getSqlActionDBV2SessionFactory();

	/**
	 * 按手机号、活动编号获取个人投票记录信息
	 * @param phoneNum
	 * @param actionCode
	 * @return
	 */
	public List<TombVoteInfoBean> selectByVotePhoneAndActionCode(String votterPhone, int actionCode) {
		try(SqlSession session = factory.openSession()){
            TombIVoteInfoBean mapper = session.getMapper(TombIVoteInfoBean.class);
            return mapper.selectByVotePhoneAndActionCode(votterPhone,actionCode);
        }
	}

	/**
	 * 添加投票信息
	 * @param info
	 * @return
	 */
	public int addVoteInfoBean(TombVoteInfoBean voteInfoBean) {
		try (SqlSession session = factory.openSession()) {
            TombIVoteInfoBean mapper = session.getMapper(TombIVoteInfoBean.class);
            int count = mapper.addVoteInfoBean(voteInfoBean);
            session.commit();
            return count;
        }
	}
	/**
	 * 按手机号、活动编号、日期 获取投票信息
	 * @param votterPhone
	 * @param actionCode
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public TombVoteInfoBean selectVoteInfoByDate(String votterPhone, int actionCode, String date) {
		try(SqlSession session = factory.openSession()){
            TombIVoteInfoBean mapper = session.getMapper(TombIVoteInfoBean.class);
            return mapper.selectVoteInfoByDate(votterPhone,actionCode,date);
        }
	}

	/**
	 * 按活动编号获取投票记录信息
	 * @param actioncode
	 * @return
	 */
	public List<Map<String,Integer>> selectByActionCode(int actioncode) {
		try(SqlSession session = factory.openSession()){
            TombIVoteInfoBean mapper = session.getMapper(TombIVoteInfoBean.class);
            return mapper.selectByActionCode(actioncode);
        }
	}

	
   //查询手机号登录记录
	public TombLoginInfoBean selectLoginLog(String votterPhone) {
		try(SqlSession session = factory.openSession()){
			TombIVoteInfoBean mapper = session.getMapper(TombIVoteInfoBean.class);
            return mapper.selectLoginLog(votterPhone);
        }
	}
	//添加登录手机号记录
	public int addLoginInfoBean(TombLoginInfoBean loginInfoBean) {
		try (SqlSession session = factory.openSession()) {
            TombIVoteInfoBean mapper = session.getMapper(TombIVoteInfoBean.class);
            int count = mapper.addLoginInfoBean(loginInfoBean);
            session.commit();
            return count;
        }
	}



	
}
