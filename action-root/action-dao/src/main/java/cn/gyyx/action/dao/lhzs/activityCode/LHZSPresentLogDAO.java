package cn.gyyx.action.dao.lhzs.activityCode;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.lhzs.activityCode.ActionConfigBean;
import cn.gyyx.action.beans.wd9yeardatavideo.PresentLogBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class LHZSPresentLogDAO implements ILHZSPresentLogMapper {
	private static final Logger LOG = GYYXLoggerFactory
			.getLogger(LHZSPresentLogDAO.class);
	/**
	 * 
	 * @Title: getSession
	 * @Description: TODO 获取session
	 * @return SqlSession
	 */
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	/**
	 * 查找该用户每天抽奖次数
	 */
	@Override
	public int presentCountDay(int actioinCode, String account, String endTime,
			String startTime) {
		SqlSession session = getSession();
		int result = -1;
		try{
			ILHZSPresentLogMapper iPresentLogMapper = session.getMapper(ILHZSPresentLogMapper.class);
			result = iPresentLogMapper.presentCountDay(actioinCode, account, endTime, startTime);
		}catch(Exception e){
			LOG.error("查找该用户每天抽奖次数出错！！",e);
		}finally{
			session.close();
		}
		return result;
	}
	/**
	 * 判断是否获奖
	 */
	@Override
	public PresentLogBean hasPrize(int actioinCode, String account,String presentType) {
		SqlSession session = getSession();
		PresentLogBean result = new PresentLogBean();
		try{
			ILHZSPresentLogMapper iPresentLogMapper = session.getMapper(ILHZSPresentLogMapper.class);
			result = iPresentLogMapper.hasPrize(actioinCode, account,presentType);
		}catch(Exception e){
			LOG.error("判断是否获奖出错！！",e);
		}finally{
			session.close();
		}
		return result;
	}
	@Override
	public Integer isActivation(int gameId, int userId) {
		SqlSession session = getSession();
		Integer result = -1;
		try{
			ILHZSPresentLogMapper iPresentLogMapper = session.getMapper(ILHZSPresentLogMapper.class);
			result = iPresentLogMapper.isActivation(gameId, userId);
		}catch(Exception e){
			LOG.error("判断是否激活出错！！",e);
		}finally{
			session.close();
		}
		return result;
	}
	@Override
	public void updateActivation(int actionCode,String account,String presentType,String serverID,String serverName,String activeCode) {
		SqlSession session = getSession();
		try{
			ILHZSPresentLogMapper iPresentLogMapper = session.getMapper(ILHZSPresentLogMapper.class);
			iPresentLogMapper.updateActivation(actionCode, account, presentType,serverID, serverName,activeCode);
		}catch(Exception e){
			LOG.error("更新激活状态失败！！",e);
		}finally{
			session.commit();
			session.close();
		}
	}
	
	@Override
	public int isExist(int actionCode, String account, String presentType) {
		SqlSession session = getSession();
		int result = -1;
		try{
			ILHZSPresentLogMapper iPresentLogMapper = session.getMapper(ILHZSPresentLogMapper.class);
			result = iPresentLogMapper.isExist(actionCode, account, presentType);
		}catch(Exception e){
			LOG.error("查询是否存在失败！！",e);
		}finally{
			session.close();
		}
		return result;
	}
	@Override
	public void updateGameActivityCode(int gameId, String activityCode) {
		SqlSession session = getSession();
		try{
			ILHZSPresentLogMapper iPresentLogMapper = session.getMapper(ILHZSPresentLogMapper.class);
			iPresentLogMapper.updateGameActivityCode(gameId, activityCode);
		}catch(Exception e){
			LOG.error("更新game_activity_code表失败！！",e);
		}finally{
			session.commit();
			session.close();
		}
		
	}
	@Override
	public void insertPrizeLog(String account, int serverId, String serverName,
			String presentName, String drawTime, String drawIp) {
		SqlSession session = getSession();
		try{
			ILHZSPresentLogMapper iPresentLogMapper = session.getMapper(ILHZSPresentLogMapper.class);
			iPresentLogMapper.insertPrizeLog(account, serverId, serverName, presentName, drawTime, drawIp);
		}catch(Exception e){
			LOG.error("添加状态失败！！",e);
		}finally{
			session.commit();
			session.close();
		}
	}
	@Override
	public int isExistActiveCode(String activeCode) {
		SqlSession session = getSession();
		int result = 0;
		try{
			ILHZSPresentLogMapper iPresentLogMapper = session.getMapper(ILHZSPresentLogMapper.class);
			result = iPresentLogMapper.isExistActiveCode(activeCode);
		}catch(Exception e){
			LOG.error("判断激活码是否存在失败！！",e);
			return 2;
		}
		return result;
	}
	@Override
	public ActionConfigBean getActionMsg(int code) {
		SqlSession session = getSession();
		ActionConfigBean result = new ActionConfigBean();
		try{
			ILHZSPresentLogMapper iPresentLogMapper = session.getMapper(ILHZSPresentLogMapper.class);
			result = iPresentLogMapper.getActionMsg(code);
		}catch(Exception e){
			LOG.error("查找活动信息失败！",e);
		}
		return result;
	}
	
	
}
