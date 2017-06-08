package cn.gyyx.action.dao.wechat;

import java.io.IOException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.wechat.LotteryChanceSupplyBean;
import cn.gyyx.action.beans.wechat.WechatPresentLogBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class WechatSendPresentLogDAO{
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(WechatSendPresentLogDAO.class);
	/**
	 * 获取session，
	 * 
	 * @throws IOException
	 */
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	/**
	 * 获取已抽奖品的数量
	 * @param wechatPresentLogBean
	 * @return
	 */
	public int getLotteryPrizeCount(WechatPresentLogBean wechatPresentLogBean){
		SqlSession session = getSession();
		int count = 0;
		try {
			IWechatSendPresentLogMapper mapper = session
					.getMapper(IWechatSendPresentLogMapper.class);
			count = mapper.getLotteryPrizeCount(wechatPresentLogBean);
		} catch (Exception e) {
			logger.warn("getLotteryPrizeCount" + e.toString());
		} finally {
			session.close();
		}
		return count;
	}

}
