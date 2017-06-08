/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年4月10日下午1:08:19
 * 版本号：v1.0
 * 本类主要用途叙述：处理用户相关请求，包括登陆和注册
 */

package cn.gyyx.action.ui;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.beans.exchangescore.PrizeConfigBean;
import cn.gyyx.action.beans.lottery.QualificationBean;
import cn.gyyx.action.beans.tenyearpicture.ScoreBean;
import cn.gyyx.action.bll.wdninestory.QualificationBLL;
import cn.gyyx.action.bll.wdtenyearspicture.ScoreBll;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.action.service.insurance.MemcacheUtil;
import cn.gyyx.core.memcache.XMemcachedClientAdapter;
import cn.gyyx.distribute.lock.DistributedLock;

public class WdTenYearExchange {
/*	private static MemcacheUtil memcacheUtil = new MemcacheUtil();
	private static XMemcachedClientAdapter memcachedClientAdapter = memcacheUtil
			.getMemcache();

	private static SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}

	public static void main(String[] args) {
		try (SqlSession sqlSession = getSession()) {
			// 线程锁
			try (DistributedLock lock = new DistributedLock(
					"getExchangePrize310")) {
				lock.weakLock(200, 60);
				ScoreBll scoreBll = new ScoreBll();
				QualificationBLL qualificationBLL = new QualificationBLL();
				PrizeConfigBll prizeConfigBll = new PrizeConfigBll();

				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());
				int year = calendar.get(Calendar.YEAR);
				int month = calendar.get(Calendar.MONTH) + 1;
				int day = calendar.get(Calendar.DATE);
				// 在这一天 2016年4月22日 2016年5月3日要更新资格表
				if ((year == 2016 && month == 4 && day == 22)
						|| (year == 2016 && month == 5 && day == 3)) {
					// 前一千明的积分信息
					List<ScoreBean> list = scoreBll.getTopScoreByPage(1, 1000,
							sqlSession);
					// 清除资格数据
					qualificationBLL.removeByActionCode(310);
					for (int i = 0; i < list.size(); i++) {
						ScoreBean scoreBean = list.get(i);
						// 资格信息
						QualificationBean qualification = new QualificationBean();
						qualification.setUserCode(scoreBean.getUserCode());
						qualification.setLotteryTime(scoreBean.getScore());
						qualification.setActionCode(310);
						// 增加资格
						qualificationBLL.addQualification(qualification);

					}
				}
				// 要去更新每日的中奖信息
				// 获取配置信息
				List<PrizeConfigBean> listPrizeConfigBeans = prizeConfigBll
						.getAllExchangePrizes(sqlSession);

				if (listPrizeConfigBeans != null
						&& !listPrizeConfigBeans.isEmpty()) {
					for (int i = 0; i < listPrizeConfigBeans.size(); i++) {
						PrizeConfigBean temPrizeConfigBean = listPrizeConfigBeans
								.get(i);
						Calendar cal1 = Calendar.getInstance();
						cal1.setTime(new Date());

						Calendar cal2 = Calendar.getInstance();
						cal2.setTime(listPrizeConfigBeans.get(i)
								.getUpdateTime());
						if (cal1.get(Calendar.DATE) != cal2.get(Calendar.DATE)
								&& ((temPrizeConfigBean.getAddNum() > 0 && temPrizeConfigBean
										.getAllNum() < temPrizeConfigBean
										.getTotalBum()) || temPrizeConfigBean
										.getTotalBum() == -1)) {
							int tempnum = temPrizeConfigBean.getAddNum()
									- temPrizeConfigBean.getDayBum();
							if (temPrizeConfigBean.getDayBum() >= 999998) {
								tempnum = 0;
							}
							prizeConfigBll.setTimeByCode(
									temPrizeConfigBean.getCode(), tempnum,
									sqlSession);
							String keyPrizeConfig = 310 + ".prize."
									+ temPrizeConfigBean.getPrizeCode();
							memcachedClientAdapter
									.set(keyPrizeConfig,
											3600 * 24,
											prizeConfigBll
													.getPrizeConfigBeanByPrizeCode(temPrizeConfigBean
															.getPrizeCode()));
						}

					}
				}
				sqlSession.commit(true);
			} catch (Exception e) {
				sqlSession.rollback();

			}
		}
		System.exit(0);
	}*/
}
