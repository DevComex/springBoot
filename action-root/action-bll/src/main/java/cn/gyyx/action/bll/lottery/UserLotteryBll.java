/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年3月20日下午4:38:42
 * 版本号：v1.0
 * 本类主要用途叙述：处理用户相关请求，包括登陆和注册
 */

package cn.gyyx.action.bll.lottery;

import java.util.List;
import java.util.Random;

import cn.gyyx.action.beans.lottery.ActionChangeLog;
import cn.gyyx.action.beans.lottery.ChancePrizeBean;
import cn.gyyx.action.beans.lottery.ContrParmBean;
import cn.gyyx.action.beans.lottery.OrderAndPrizeBean;
import cn.gyyx.action.beans.lottery.PrizeBean;
import cn.gyyx.action.beans.lottery.UserInfoBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.NewPageBean;
import cn.gyyx.action.dao.lottery.ActionChangeDAO;
import cn.gyyx.action.dao.lottery.ChancePrizeDao;
import cn.gyyx.action.dao.lottery.ContrParmDao;
import cn.gyyx.action.dao.lottery.IActionChangeMapper;
import cn.gyyx.action.dao.lottery.IChancePrize;
import cn.gyyx.action.dao.lottery.IConPram;
import cn.gyyx.action.dao.lottery.IPrize;
import cn.gyyx.action.dao.lottery.IPrizeAndOrder;
import cn.gyyx.action.dao.lottery.IUserLotteryInfo;
import cn.gyyx.action.dao.lottery.PrizeAndOrderDao;
import cn.gyyx.action.dao.lottery.PrizeDao;
import cn.gyyx.action.dao.lottery.UserInfoDao;

public class UserLotteryBll {
	/**
	 * 得到名次与奖品的对应信息
	 * 
	 * @param actionCode
	 * @return List<OrderPrizeBean>
	 */
	public List<OrderAndPrizeBean> getPrizeAndOrderInfo(int actionCode) {
		IPrizeAndOrder iPrizeAndOrder = new PrizeAndOrderDao();
		return iPrizeAndOrder.getPrizeInfo(actionCode);
	}

	/**
	 * 得到奖品的信息
	 * 
	 * @param actionCode
	 * @return List<PrizeBean>
	 */
	public List<PrizeBean> getPrize(int actionCode) {
		IPrize iPrize = new PrizeDao();
		return iPrize.getPrize(actionCode);
	}

	public List<PrizeBean> getPrizeAll(int actionCode) {
		IPrize iPrize = new PrizeDao();
		return iPrize.getPrizeAll(actionCode);
	}

	public PrizeBean getPrizeByCode(int code) {
		IPrize iPrize = new PrizeDao();
		return iPrize.getPrizeByCode(code);
	}

	public Integer insertPrize(PrizeBean prizeBean) {
		IPrize iPrize = new PrizeDao();
		return iPrize.insertPrize(prizeBean);
	}

	public Integer updatePrizeBean(PrizeBean prizeBean) {
		PrizeDao iPrize = new PrizeDao();
		return iPrize.updatePrizeBean(prizeBean);
	}

	public Integer updatePrizeBeanAll(PrizeBean prizeBean) {
		PrizeDao iPrize = new PrizeDao();
		return iPrize.updatePrizeBeanAll(prizeBean);
	}

	public PrizeBean getPrizeByNum(PrizeBean prizeBean) {
		PrizeDao iPrize = new PrizeDao();
		return iPrize.getPrizeByNum(prizeBean);
	}

	/**
	 * 得到活动的信息
	 * 
	 * @param actionCode
	 * @return
	 */
	public ContrParmBean getContrParm(int actionCode) {
		IConPram iConPram = new ContrParmDao();
		return iConPram.getConPram(actionCode);

	}

	/**
	 * 增加一条中奖纪录
	 * 
	 * @param userInfoBean
	 */
	public int addUserLotteryInfo(UserInfoBean userInfoBean) {
		IUserLotteryInfo info = new UserInfoDao();
		return info.addInfo(userInfoBean);
	}

	public void updateAvailable(int code) {
		IUserLotteryInfo info = new UserInfoDao();
		info.updateAvailable(code);
	}

	/**
	 * 得到所有用户的中奖信息
	 * 
	 * @param actionCode
	 * @return
	 */
	public List<UserInfoBean> getUserLottery(int actionCode) {
		IUserLotteryInfo info = new UserInfoDao();
		return info.getAllUserLotteryInfo(actionCode);
	}

	/**
	 * 得到用户的中奖信息
	 * 
	 * @param actionCode
	 * @param account
	 * @return List<UserInfoBean>
	 */
	public List<UserInfoBean> getUserLotteryByUserCode(int actionCode, String account) {
		IUserLotteryInfo info = new UserInfoDao();
		return info.getUserLotteryInfoByUserCode(actionCode, account);
	}

	/**
	 * 获得用户的中奖信息(刮刮乐)
	 * 
	 * @param actionCode
	 * @param userCode
	 * @return List<UserInfoBean>
	 */
	public List<UserInfoBean> wishGetUserLotteryInfoByAvailable(int actionCode, String account) {
		IUserLotteryInfo info = new UserInfoDao();
		return info.wishGetUserLotteryInfoByAvailable(actionCode, account);
	}

	public List<UserInfoBean> getLogByInfo(NewPageBean newPageBean) {
		IUserLotteryInfo info = new UserInfoDao();
		return info.getLogByInfo(newPageBean);
	}

	public List<UserInfoBean> getSendPresentLogs(int actionCode) {
		IUserLotteryInfo info = new UserInfoDao();
		return info.getSendPresentLogs(actionCode);
	}

	public Integer getCountByInfo(NewPageBean newPageBean) {
		IUserLotteryInfo info = new UserInfoDao();
		return info.getCountByInfo(newPageBean);
	}

	/**
	 * 得到用户的中奖信息(包括虚拟物品)
	 * 
	 * @param actionCode
	 * @param account
	 * @return List<UserInfoBean>
	 */
	public List<UserInfoBean> wishGetUserLotteryByUserCode(int actionCode, String account) {
		IUserLotteryInfo info = new UserInfoDao();
		return info.wishGetUserLotteryInfoByUserCode(actionCode, account);
	}

	/**
	 * 根据活动Code、用户账号和奖品名称查询用户是否中过此奖品
	 * 
	 * @param @param
	 *            actionCode
	 * @param @param
	 *            userAccount
	 * @param @param
	 *            presentName
	 * @return UserInfoBean
	 */
	public UserInfoBean getUserLotteryInfoByActionCodeAndUserCodeAndPresentName(int actionCode, String userAccount,
			String presentName) {
		IUserLotteryInfo info = new UserInfoDao();
		return info.getUserLotteryInfoByActionCodeAndUserCodeAndPresentName(actionCode, userAccount, presentName);
	}

	/**
	 * 根据活动Code、用户账号和中奖日期查询用户当日是否还有抽奖资格
	 * 
	 * @param @param
	 *            userAccount
	 * @param @param
	 *            presentName
	 * @return UserInfoBean
	 */
	public UserInfoBean getUserLotteryInfoByActionCodeAndUserCodeAndDrawTime(int actionCode, String userAccount,
			String drawTime) {
		IUserLotteryInfo info = new UserInfoDao();
		return info.getUserLotteryInfoByActionCodeAndUserCodeAndDrawTime(actionCode, userAccount, drawTime);
	}

	/**
	 * 得到按概率中奖的配置信息
	 * 
	 * @return
	 */
	public List<ChancePrizeBean> getChancePrize(int actionCode) {
		IChancePrize iChancePrize = new ChancePrizeDao();
		return iChancePrize.getChancePrize(actionCode);
	}

	public Integer insertPrizeChange(ChancePrizeBean chancePrizeBean) {
		IChancePrize iChancePrize = new ChancePrizeDao();
		return iChancePrize.insertPrizeChange(chancePrizeBean);
	}

	public Integer updateChancePrizeBean(ChancePrizeBean chancePrizeBean) {
		IChancePrize iChancePrize = new ChancePrizeDao();
		return iChancePrize.updateChancePrizeBean(chancePrizeBean);
	}

	public ChancePrizeBean getChancePrizeByprizeCode(Integer prizeCode) {
		IChancePrize iChancePrize = new ChancePrizeDao();
		return iChancePrize.getChancePrizeByprizeCode(prizeCode);
	}
	
        public ChancePrizeBean getPrizeProbabilityAndNumberByPrizeCode(
                Integer prizeCode) {
            IChancePrize iChancePrize = new ChancePrizeDao();
            return iChancePrize.getPrizeProbabilityAndNumberByPrizeCode(prizeCode);
        }
    
        /**
         * 根据奖品编号获取该奖品对应的所有奖池
         */
        public List<ChancePrizeBean> getChancePrizesByPrizeCode(Integer prizeCode) {
            IChancePrize iChancePrize = new ChancePrizeDao();
            return iChancePrize.getChancePrizesByPrizeCode(prizeCode);
        }

	public int insertActionChangeLog(ActionChangeLog actionChangeLog) {
		IActionChangeMapper iActionChangeMapper = new ActionChangeDAO();
		return iActionChangeMapper.insertActionChangeLog(actionChangeLog);
	}

	/**
	 * 查询该奖品所中个数
	 * 
	 * @param actionCode
	 * @param prizeChinese
	 * @return
	 */
	public int gettNumOfPrize(int actionCode, String prizeChinese) {
		IUserLotteryInfo info = new UserInfoDao();
		return info.getNumOf(actionCode, prizeChinese);
	}

	/**
	 * 随机选择数字
	 * 
	 * @param a
	 * @return
	 */
	public int randomNum(int a[]) {
		int i = 0;
		if (a != null) {
			Random random = new Random();
			i = random.nextInt(a.length);
		}
		return a[i];
	}

	public Integer selectLogByDay(String date) {
		IUserLotteryInfo info = new UserInfoDao();
		return info.selectLogByDay(date);
	}


	/**
	 * 得到所有用户的中奖信息 
	 * 问道2017找基友活动使用 用于中奖信息轮播
	 */
	public List<UserInfoBean> getLotteryList4Wd2017SeekTeam(int actionCode) {
		IUserLotteryInfo info = new UserInfoDao();
		return info.getLotteryList4Wd2017SeekTeam(actionCode);
	}

    //查询某人某次活动中某类奖品
	public List<UserInfoBean> selectOneTypePrizes(int actionCode, String userAccount, String presentType){
		UserInfoDao userInfoDao=new UserInfoDao();
		return userInfoDao.selectOneTypePrizes(actionCode, userAccount, presentType);
	}
	// 查询不是谢谢参与的奖品
	public List<UserInfoBean> selectOtherPrizes(int actionCode, String userAccount, String presentType) {
		UserInfoDao userInfoDao=new UserInfoDao();
		return userInfoDao.selectOtherPrizes(actionCode, userAccount, presentType);
	}

	/**
	 * 得到所有用户的中奖信息 
	 * 问道1.61活动使用 用于中奖信息轮播
	 */
	public List<UserInfoBean> getLotteryList4Wd161(int actionCode) {
		IUserLotteryInfo info = new UserInfoDao();
		return info.getLotteryList4Wd161(actionCode);
	}


}
