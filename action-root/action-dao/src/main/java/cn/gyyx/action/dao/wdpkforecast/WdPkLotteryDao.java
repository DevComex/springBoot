package cn.gyyx.action.dao.wdpkforecast;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.beans.lottery.QualificationBean;
import cn.gyyx.action.beans.lottery.po.ActionLotteryLogPO;
import cn.gyyx.action.beans.wdpkforecast.WdPkAllLotteryInfo;
import cn.gyyx.action.beans.wdpkforecast.WdVoteSurplusBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

public class WdPkLotteryDao {

	SqlSessionFactory factory = MyBatisConnectionFactory.getSqlActionDBV2SessionFactory();

	public void updatelotteryTime(int lottery_time, int user_code, int action_code) {
		try (SqlSession session = factory.openSession()) {
			IWdPkLotteryMapper mapper = session.getMapper(IWdPkLotteryMapper.class);
			mapper.updatelotteryTime(lottery_time, user_code, action_code);
			session.commit();
		}

	}

	public List<QualificationBean> selectBiglotteryByTypeAndWin(int actionCdoe, int type, int win) {

		try (SqlSession session = factory.openSession()) {
			IWdPkLotteryMapper mapper = session.getMapper(IWdPkLotteryMapper.class);
			return mapper.selectBiglotteryByTypeAndWin(actionCdoe, type, win);
		}
	}

	public List<QualificationBean> selectSamllLotteryStatusByTypeAndWin(int actionCdoe, int type, int win) {

		try (SqlSession session = factory.openSession()) {
			IWdPkLotteryMapper mapper = session.getMapper(IWdPkLotteryMapper.class);
			return mapper.selectSamllLotteryStatusByTypeAndWin(actionCdoe, type, win);
		}
	}

	public List<ActionLotteryLogPO> selectIsAvailable(ActionLotteryLogPO po, SqlSession sqlSession) {

		IWdPkLotteryMapper mapper = sqlSession.getMapper(IWdPkLotteryMapper.class);
		return mapper.selectIsAvailable(po);

	}

	public void insertLotteryLog(ActionLotteryLogPO po, SqlSession sqlSession) {

		IWdPkLotteryMapper mapper = sqlSession.getMapper(IWdPkLotteryMapper.class);
		mapper.insertLotteryLog(po);

	}

	public List<ActionLotteryLogPO> selectUserLotteryInfo(String userId) {

		try (SqlSession session = factory.openSession()) {
			IWdPkLotteryMapper mapper = session.getMapper(IWdPkLotteryMapper.class);
			return mapper.selectUserLotteryInfo(userId);
		}
	}
	
	public List<WdPkAllLotteryInfo>selectAllUserLotteryInfo(int type){
		
		try (SqlSession session = factory.openSession()) {
			IWdPkLotteryMapper mapper = session.getMapper(IWdPkLotteryMapper.class);
			return mapper.selectAllUserLotteryInfo(type);
		}
		
	}
	
	public List<QualificationBean> selectBig(int actionCdoe, int type, int win) {

		try (SqlSession session = factory.openSession()) {
			IWdPkLotteryMapper mapper = session.getMapper(IWdPkLotteryMapper.class);
			return mapper.selectBig(actionCdoe, type, win);
		}
	}

	public List<QualificationBean> selectSamll(int actionCdoe, int type, int win) {

		try (SqlSession session = factory.openSession()) {
			IWdPkLotteryMapper mapper = session.getMapper(IWdPkLotteryMapper.class);
			return mapper.selectSamll(actionCdoe, win, type);
		}
	}

	public void insertVoteSurplus (WdVoteSurplusBean VSB){
		
		try (SqlSession session = factory.openSession()) {
			IWdPkLotteryMapper mapper = session.getMapper(IWdPkLotteryMapper.class);
		     mapper.insertVoteSurplus(VSB);
		     session.commit();
		}
		
	}
	
	public List<WdVoteSurplusBean>selectSurplus(WdVoteSurplusBean VSB ){
		
		try (SqlSession session = factory.openSession()) {
			IWdPkLotteryMapper mapper = session.getMapper(IWdPkLotteryMapper.class);
			return mapper.selectSurplus(VSB);
		
		}	
	}
	
	public List<ActionLotteryLogPO> selectUserLotteryInfoBYactioncode(ActionLotteryLogPO po){
		
		try (SqlSession session = factory.openSession()) {
			IWdPkLotteryMapper mapper = session.getMapper(IWdPkLotteryMapper.class);
			return mapper.selectUserLotteryInfoBYactioncode(po);
		}
	}
	
	public void insertAllLotteryLog(ActionLotteryLogPO po, SqlSession sqlSession) {

		IWdPkLotteryMapper mapper = sqlSession.getMapper(IWdPkLotteryMapper.class);
		mapper.insertAllLotteryLog(po);

	}

	public List<Map<String, String>> getLotteryCountGroupByDate(
			int activityCode) {
		try (SqlSession session = factory.openSession()) {
			IWdPkLotteryMapper mapper = session.getMapper(IWdPkLotteryMapper.class);
			return mapper.getLotteryCountGroupByDate(activityCode);
		}
	}
}
