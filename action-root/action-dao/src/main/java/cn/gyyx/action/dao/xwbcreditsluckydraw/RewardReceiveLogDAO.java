/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：炫舞吧积分活动
 * @作者：王雷
 * @联系方式：wanglei@gyyx.cn
 * @创建时间： 2015年9月2日
 * @版本号：V1.214
 * @本类主要用途描述：签到奖励领取记录数据处理接口
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.xwbcreditsluckydraw;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.xwbcreditsluckydraw.RewardReceiveLogBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class RewardReceiveLogDAO {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(GoodsinfoDAO.class);
	IRewardReceiveLogMapper iRewardReceiveLogMapper;

	/**
	 * 
	 * @日期：2015年9月2日
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
	 * 增加签到奖励领取记录
	 * @param rewardReceiveLogBean
	 */
	public boolean insertRewardReceiveLog(RewardReceiveLogBean rewardReceiveLogBean){
		SqlSession session = getSession();
		boolean flag = false;
		try {
			iRewardReceiveLogMapper = session
					.getMapper(IRewardReceiveLogMapper.class);
			iRewardReceiveLogMapper.insertRewardReceiveLog(rewardReceiveLogBean);
			session.commit();
			flag = true;
		} catch (Exception e) {
			logger.warn(e.toString());
			flag= false;
		} finally {
			session.close();
		}
		return flag;
	}
	/**
	 * 按条件查询奖励领取记录
	 * @param rewardReceiveLogBean
	 * @return
	 */
	public List<RewardReceiveLogBean> selectRewardReceiveLog(RewardReceiveLogBean rewardReceiveLogBean){
		List<RewardReceiveLogBean> rewardReceiveLog = null;
		SqlSession session = getSession();
		try {
			iRewardReceiveLogMapper = session
					.getMapper(IRewardReceiveLogMapper.class);
			rewardReceiveLog = iRewardReceiveLogMapper.selectRewardReceiveLog(rewardReceiveLogBean);
			session.commit();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return rewardReceiveLog;
	}
}
