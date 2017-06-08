/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年3月20日下午4:38:42
 * 版本号：v1.0
 * 本类主要用途叙述：处理用户相关请求，包括登陆和注册
 */

package cn.gyyx.action.bll.newLottery;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.ChancePrizeBean;
import cn.gyyx.action.beans.lottery.ContrParmBean;
import cn.gyyx.action.beans.lottery.OrderAndPrizeBean;
import cn.gyyx.action.beans.lottery.PrizeBean;
import cn.gyyx.action.beans.lottery.RechargeBean;
import cn.gyyx.action.beans.lottery.UserInfoBean;
import cn.gyyx.action.dao.newlottery.NewChancePrizeDao;
import cn.gyyx.action.dao.newlottery.NewContrParmDao;
import cn.gyyx.action.dao.newlottery.NewPrizeAndOrderDao;
import cn.gyyx.action.dao.newlottery.NewPrizeDao;
import cn.gyyx.action.dao.newlottery.NewRechargeDao;
import cn.gyyx.action.dao.newlottery.NewUserInfoDao;
import cn.gyyx.action.dao.newlottery.NewUserLotteryDao;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class NewUserLotteryBll {
	private static final Logger logger = GYYXLoggerFactory.getLogger(NewUserLotteryBll.class);
	private NewContrParmDao newContrParmDao = new NewContrParmDao();
	private NewPrizeDao newPrizeDao = new NewPrizeDao();
	private NewChancePrizeDao newChancePrizeDao = new NewChancePrizeDao();
	private NewUserInfoDao newUserInfoDao = new NewUserInfoDao();
	private NewRechargeDao newRechargeDao = new NewRechargeDao();
	private NewPrizeAndOrderDao newPrizeAndOrderDao = new NewPrizeAndOrderDao();
	private NewUserLotteryDao newUserLotteryDao = new NewUserLotteryDao();

	/**
	 * 得到活动的信息
	 * 
	 * @param actionCode
	 * @return
	 */
	public ContrParmBean getContrParm(int actionCode, SqlSession sqlSession) {
		return newContrParmDao.getConPram(actionCode, sqlSession);

	}

	/**
	 * 获取IP在当前活动获取的所有奖品名称
	 * @param actionCode
	 * @param ip
	 * @param session
	 * @return
	 */
	public List<String> getPresentNames(int actionCode, String ip, SqlSession session) {
		return newUserInfoDao.getPresentNamesByUserIP(actionCode, ip, session);
	}

	/**
	 * 得到奖品的信息
	 * 
	 * @param actionCode
	 * @return List<PrizeBean>
	 */
	public List<PrizeBean> getPrize(int actionCode, SqlSession sqlSession) {
		return newPrizeDao.getPrize(actionCode, sqlSession);
	}

	public PrizeBean getPrizeByCode(int code, SqlSession sqlSession) {
		return newPrizeDao.getPrizeByCode(code, sqlSession);
	}

	/**
	 * 得到奖品的概率信息
	 * 
	 * @return
	 */
	public List<ChancePrizeBean> getChancePrize(int actionCode, SqlSession sqlSession) {
		return newChancePrizeDao.getChancePrize(actionCode, sqlSession);
	}

	/**
	 * 增加一条中奖纪录
	 * 
	 * @param userInfoBean
	 */
	public int addUserLotteryInfo(UserInfoBean userInfoBean, SqlSession sqlSession) {
		return newUserInfoDao.addInfo(userInfoBean, sqlSession);
	}

        /***
         * 减少抽奖次数
         * 
         * @param actionCode
         * @param prizePoolCode
         * @param prizeCode
         * @param sqlSession
         */
        public void reduceTimeChancePrize(int actionCode, int prizePoolCode, int prizeCode, SqlSession sqlSession) {
                newChancePrizeDao.reduceTimeChancePrize(actionCode, prizePoolCode, prizeCode, sqlSession);
        }

	/**
	 * 得到充值卡卡号
	 * 
	 * @param actionCode
	 * @param type
	 * @param code
	 * @return
	 */
	public String getCardCode(int actionCode, String type, int code, SqlSession sqlSession) {
		newRechargeDao.updateRecharge(actionCode, type, code, sqlSession);
		return newRechargeDao.getCardCode(code, actionCode, type, sqlSession);
	}

	public String getCardCodeNotSet(int actionCode, String type, int code, SqlSession sqlSession) {
		return newRechargeDao.getCardCode(code, actionCode, type, sqlSession);
	}

	/***
	 * 获取顺序与奖品的对应信息
	 * 
	 * @param actionCode
	 * @param sqlSession
	 * @return
	 */
	public List<OrderAndPrizeBean> getPrizeAndOrderInfo(int actionCode, SqlSession sqlSession) {
		return newPrizeAndOrderDao.getPrizeInfo(actionCode, sqlSession);
	}

	/**
	 * 获得用户抽奖的名次
	 * 
	 * @param userCode
	 * @param actionCode
	 * @return
	 */
	public ResultBean<Integer> getOrderByUserCode(int userCode, int actionCode, SqlSession sqlSession) {
		logger.debug("userCode", userCode);
		logger.debug("actionCode", actionCode);
		ResultBean<Integer> resultBean = new ResultBean<>();
		// 设定参数
		HashMap<String, Integer> map = new HashMap<>();
		map.put("userCode", userCode);
		map.put("errorCode", 0);
		map.put("actionCode", actionCode);
		map.put("lotteryOrder", 0);
		logger.debug("map", map);
		// 这里就是执行了一个存储过程
		newUserLotteryDao.getOrderByUserCode(map, sqlSession);
		logger.debug("map", map);
		// 存储过程没有出现错误
		if (map.get("errorCode") == 0) {
			resultBean.setData(map.get("lotteryOrder"));
			resultBean.setIsSuccess(true);
		} else {
			resultBean.setIsSuccess(false);
			resultBean.setMessage("抽奖错误");
		}
		logger.debug("getOrderByUserCode", resultBean);
		return resultBean;
	}

	/**
	 * 查询卡号By 用户id和活动id
	 */

	public String selectCardCode(int actionCode, int userCode) {

		return newRechargeDao.selectCardCode(userCode, actionCode);
	}

	public List<String> selectAllCardCode(int actionCode, int userCode) {
		List<String> list=null;
		 list=newRechargeDao.selectAllCardCode(userCode, actionCode);
		 return list;
	}

	/**
	 * 某个人在某个活动获得某个奖
	 */
	public List<UserInfoBean> getOneInActionOnePrizeInfoSqlsession(int actionCode, String userAccount,
			String prizeChinese, SqlSession sqlSession) {
		return newUserInfoDao.getOneInActionOnePrizeInfoSqlsession(actionCode, userAccount, prizeChinese, sqlSession);
	}

	/**
	 * 查询中间卡的卡号
	 */
	public RechargeBean getCardCodeByCardCode(int actionCode, String type, String cardCode, SqlSession sqlSession) {
		return newRechargeDao.getCardCodeByCardCode(actionCode, type, cardCode, sqlSession);
	}
	//查询用户是否中过有效奖品
	public List<UserInfoBean> getAvailableByUserSqlsession(int actionCode, String userAccount,int available,SqlSession sqlSession){
		
		return newUserInfoDao.getAvailableByUserSqlsession(actionCode,userAccount,available,sqlSession);
	}
	
	/**
	 * 更新发送物品日志 yangteng
	 * @param userInfoBean
	 * @param sqlSession
	 * @return
	 */
	public Integer updateSendPresentLog(UserInfoBean userInfoBean,SqlSession sqlSession){
		return newUserInfoDao.updateSendPresentLog(userInfoBean, sqlSession);
	}
}
