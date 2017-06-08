/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2014年12月17日下午2:14:54
 * 版本号：v1.0
 * 本类主要用途叙述：有关用户资格表的业务层
 */

package cn.gyyx.action.bll.wdno1pet;

import java.util.HashMap;
import java.util.Random;

import org.slf4j.Logger;

import cn.gyyx.action.beans.wdno1pet.LotteryConfigBean;
import cn.gyyx.action.beans.wdno1pet.UserLotteryBean;
import cn.gyyx.action.dao.wdno1pet.IUserLottery;
import cn.gyyx.action.dao.wdno1pet.UserLotteryDAO;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class UserLotteryBLL {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(UserLotteryBLL.class);

	/**
	 * 统计具有中奖资格的人
	 * 
	 * @return int 中奖人数
	 * 
	 */
	public int getNumberAlllucky() {
		IUserLottery iLottery = new UserLotteryDAO();
		int num = iLottery.selectNumLucky();
		return num;

	}

	/**
	 * 向用户中奖信息中插入中奖信息
	 * 
	 * @param lBean
	 */
	public void setUserLottery(LotteryConfigBean lBean,
			UserLotteryBean userLotteryBean) {
		logger.debug("LotteryConfigBean", lBean);
		logger.debug("UserLotteryBean", userLotteryBean);
		userLotteryBean.setUserLotteryType(lBean.getLotterType());
		if (lBean.getLotterType() == "thank") {
			userLotteryBean.setUserLotteryStatus("no");
		} else {
			userLotteryBean.setUserLotteryStatus("yes");
		}
		IUserLottery uLottery = new UserLotteryDAO();
		uLottery.insertUserLottery(userLotteryBean);
	}

	/**
	 * 插入用户的地址信息
	 * 
	 * @param adress
	 */
	public void setUserAdress(String address, int userCode) {
		UserLotteryDAO uLotteryDAO = new UserLotteryDAO();
		uLotteryDAO.updateUserAdress(address, userCode);
	}

	/**
	 * 确认这个用户是否有抽奖资格
	 * 
	 * @param userCode
	 * @return boolean
	 * 
	 */
	public boolean isQualification(int userCode) {
		boolean is = false;
		IUserLottery uLottery = new UserLotteryDAO();
		UserLotteryBean uBean = uLottery.selectUserLotteryByUserCode(userCode);
		logger.debug("UserLotteryBean", uBean);
		if (uBean != null) {
			is = true;
		}

		return is;

	}

	/**
	 * 判断用户是否抽过奖
	 * 
	 * @param userCode
	 * @return boolean
	 */
	public boolean isLottery(int userCode) {
		boolean is = false;
		IUserLottery uLottery = new UserLotteryDAO();
		UserLotteryBean uBean = uLottery.selectUserLotteryByUserCode(userCode);
		logger.debug("UserLotteryBean", uBean);
		// None是未抽奖的状态
		if (uBean.getUserLotteryStatus().toString().trim().equals("none")) {
			is = true;
		}
		return is;
	}

	/**
	 * 设定用户的中奖信息
	 * 
	 * @param userCode
	 */
	public void setUserLotteryStatusAndType(int userCode) {
		IUserLottery iLottery = new UserLotteryDAO();
		iLottery.updateUserLotteryStatusAndType(userCode);
	}

	/**
	 * 通过用户主键查询中奖信息
	 * 
	 * @return userLotteryBean 用户中奖信息
	 */
	public UserLotteryBean getUserLotteryByUserCode(int userCode) {
		IUserLottery iLottery = new UserLotteryDAO();
		UserLotteryBean uBean = iLottery.selectUserLotteryByUserCode(userCode);
		return uBean;

	}

	/**
	 * 更新用户中奖状态
	 * 
	 * @param lotteryStatus
	 * @param userCode
	 */
	public void setUserLotteryStatus(String lotteryStatus, int userCode) {
		IUserLottery iLottery = new UserLotteryDAO();
		iLottery.updateUserLotteryStatus(lotteryStatus, userCode);
	}

	/**
	 * 设定中奖的状态
	 * 
	 * @param uLotteryBean
	 * @return uLotteryBean 新的状态
	 */
	public UserLotteryBean ConfigLotteryStatus(UserLotteryBean uLotteryBean) {
		// 如果是谢谢惠顾就是未中奖
		logger.debug("UserLotteryBean", uLotteryBean);
		if (uLotteryBean.getUserLotteryType().toString().trim().equals("thank")) {
			uLotteryBean.setUserLotteryStatus("no");
		}
		// 如果是五百金元宝就中虚拟奖
		else if (uLotteryBean.getUserLotteryType().toString().trim()
				.equals("500gold")) {

			uLotteryBean.setUserLotteryStatus("yesGold");
		} else {
			uLotteryBean.setUserLotteryStatus("yes");
		}
		logger.debug("UserLotteryBean", uLotteryBean);
		return uLotteryBean;
	}

	/**
	 * 将中奖类型转换成对应的代表数字
	 * 
	 * @param type
	 * @return values int
	 */
	public int setLotteryValues(UserLotteryBean uBean) {
		String valueString = uBean.getUserLotteryType();
		int valueInt = 0;
		switch (valueString) {
		case "giftPackage":
			valueInt = 3;
			break;
		case "pillow":
			valueInt = 2;
			break;
		case "Mi":
			valueInt = 1;
			break;
		case "Haier":
			valueInt = 11;
			break;
		case "purse":
			valueInt = 7;
			break;
		case "bracelet":
			valueInt = 9;
			break;
		case "mouse":
			valueInt = 5;
			break;
		case "10000gold":
			valueInt = 8;
			break;
		case "Tshirt":
			valueInt = 10;
			break;
		case "keyboard":
			valueInt = 4;
			break;
		case "thank":
			int a[] = { 0, 0, 6 };
			Random random = new Random();
			int i = random.nextInt(3);
			valueInt = a[i];
			break;
		default:
			break;
		}
		logger.debug("valueInt", valueInt);
		return valueInt;
	}

	public String setLotteryChinese(UserLotteryBean uBean) {
		logger.debug("UserLotteryBean", uBean);
		String valueString = uBean.getUserLotteryType();
		String chinese = "";
		switch (valueString) {
		case "pillow":
			chinese = "威威虎抱枕";
			break;
		case "giftPackage":
			chinese = "典藏大礼盒";
			break;
		case "Mi":
			chinese = "红米手机";
			break;
		case "Haier":
			chinese = "海尔音响";
			break;
		case "purse":
			chinese = "问道钱包";
			break;
		case "Tshirt":
			chinese = "问道t恤";
			break;
		case "keyboard":
			chinese = "多彩T15键盘";
			break;
		case "bracelet":
			chinese = "千足金手链";
			;
			break;
		case "mouse":
			chinese = "鼠标";
			break;
		case "10000gold":
			chinese = "10000银元宝";
			break;
		case "thank":
			chinese = "谢谢参与";
			break;
		default:
			break;
		}
		logger.debug("chinese", chinese);
		return chinese;
	}

	/**
	 * 更改用户中奖的信息
	 * 
	 * @return
	 */
	public int setUserLotteryType(int userCode) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("userCode", userCode);
		map.put("errorCode", 0);

		logger.debug("map", map);
		IUserLottery iLottery = new UserLotteryDAO();
		iLottery.updateLotteryType(map);
		logger.debug("map", map);
		return map.get("errorCode");
	}
}
