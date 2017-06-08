package cn.gyyx.action.service.wdlight2017;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.PrizeBean;
import cn.gyyx.action.beans.wdlight2017.LightBean;
import cn.gyyx.action.bll.lottery.MemcacheUtil;
import cn.gyyx.action.bll.newLottery.NewUserLotteryBll;
import cn.gyyx.action.bll.wdlight2017.LightBll;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.core.memcache.XMemcachedClientAdapter;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class LightService {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(LightService.class);
	
	private LightBll lightBll = new LightBll();
	private NewUserLotteryBll newUserLotteryBll = new NewUserLotteryBll();
	
	private static SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	
	public ResultBean<List<PrizeBean>> getPrizes (int actionCode) {
		ResultBean<List<PrizeBean>> result = new ResultBean<>();
		SqlSession session = getSession();
		List<PrizeBean> prizeList = newUserLotteryBll.getPrize(actionCode, session);
		result.setData(prizeList);
		result.setIsSuccess(true);
		result.setMessage("奖品获取成功");
		return new ResultBean<>();
	}
	
	public ResultBean<List<Map<String, Integer>>> getLightType () {
		ResultBean<List<Map<String, Integer>>> resultBean = new ResultBean<>();
		try {
			List<LightBean> lights = lightBll.getLightAll();
			List<Map<String, Integer>> list = new ArrayList<>();
			for (LightBean light : lights) {
				Map<String, Integer> data = new HashMap<>();
				data.put("level", light.getLevel());
				data.put("lightType", light.getLightType());
				list.add(data);
			}
			resultBean.setData(list);
			resultBean.setIsSuccess(true);
			resultBean.setMessage("获取所有灯的状态成功");
		} catch (Exception e) {
			logger.warn("getLightType:" , e);
			resultBean.setIsSuccess(false);
			resultBean.setMessage("获取所有灯的状态失败");
		}
		return resultBean;
	}
	
	public ResultBean<List<Integer>> getLightFloor () {
		ResultBean<List<Integer>> resultBean = new ResultBean<>();
		try {
			List<LightBean> lights = lightBll.getLightAll();
			List<Integer> list = new ArrayList<>();
			for (LightBean light : lights) {
				if (light.getLightType() != 0) {
					list.add(light.getLevel());
				} 
			}
			resultBean.setData(list);
			resultBean.setIsSuccess(true);
			resultBean.setMessage("获取所有灯的状态成功");
		} catch (Exception e) {
			logger.warn("getLightFloor:" , e);
			resultBean.setIsSuccess(false);
			resultBean.setMessage("获取所有灯的状态失败");
		}
		return resultBean;
	}
	
}
