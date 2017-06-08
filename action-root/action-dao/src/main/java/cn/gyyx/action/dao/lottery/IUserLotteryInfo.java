/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年3月20日下午3:32:49
 * 版本号：v1.0
 * 本类主要用途叙述：处理用户相关请求，包括登陆和注册
 */

package cn.gyyx.action.dao.lottery;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.lottery.UserInfoBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.NewPageBean;

public interface IUserLotteryInfo {
	/**
	 * 得到活动的所有中奖信息
	 * 
	 * @param actionCode
	 * @return
	 */
	public List<UserInfoBean> getAllUserLotteryInfo(
			@Param("actionCode") int actionCode);
	public void updateAvailable(
			@Param("code") int code);
	/**
	 * 增加一条中奖信息
	 * 
	 * @param userInfoBean
	 */
	public int addInfo(UserInfoBean userInfoBean);

	/**
	 * 获得用户的中奖信息
	 * 
	 * @param actionCode
	 * @param userCode
	 * @return List<UserInfoBean>
	 */
	public List<UserInfoBean> getUserLotteryInfoByUserCode(
			@Param("actionCode") int actionCode,
			@Param("userAccount") String userAccount);

	/**
	 * 获得用户的中奖信息(显示虚拟物品)
	 * 
	 * @param actionCode
	 * @param userCode
	 * @return List<UserInfoBean>
	 */
	public List<UserInfoBean> wishGetUserLotteryInfoByUserCode(
			@Param("actionCode") int actionCode,
			@Param("userAccount") String userAccount);
	/**
	 * 获得用户的中奖信息(刮刮乐)
	 * 
	 * @param actionCode
	 * @param userCode
	 * @return List<UserInfoBean>
	 */
	public List<UserInfoBean> wishGetUserLotteryInfoByAvailable(
			@Param("actionCode") int actionCode,
			@Param("userAccount") String userAccount);
	
	/**
	 * 根据活动Code、用户账号和奖品名称查询用户是否中过此奖品
	 * 
	 * @param @param userAccount
	 * @param @param presentName
	 * @return UserInfoBean    
	 */
	public UserInfoBean getUserLotteryInfoByActionCodeAndUserCodeAndPresentName(
			@Param("actionCode") int actionCode,
			@Param("userAccount") String userAccount,
			@Param("presentName") String presentName);

	/**
	 * 根据活动Code、用户账号和中奖日期查询用户当日是否还有抽奖资格
	 * 
	 * @param @param userAccount
	 * @param @param presentName
	 * @return UserInfoBean    
	 */
	public UserInfoBean getUserLotteryInfoByActionCodeAndUserCodeAndDrawTime(
			@Param("actionCode") int actionCode,
			@Param("userAccount") String userAccount,
			@Param("drawTime") String drawTime);

	public List<UserInfoBean> getSendPresentLogs(int actionCode);
	
	/**
	 * 得到该奖品的个数
	 * 
	 * @param actionCode
	 * @param prizeChinese
	 * @return
	 */
	public int getNumOf(@Param("actionCode") int actionCode,
			@Param("prizeChinese") String prizeChinese);
	public  Integer selectLogByDay(String date);
	public  List<UserInfoBean> getLogByInfo(NewPageBean newPageBean);
	public  Integer getCountByInfo(NewPageBean newPageBean);

	public List<UserInfoBean> getLotteryList4Wd2017SeekTeam(@Param("actionCode") int actionCode);

	public List<UserInfoBean> selectOtherPrizes(@Param("actionCode") int actionCode,@Param("userAccount") String userAccount,@Param("presentType") String presentType);
	public List<UserInfoBean> selectOneTypePrizes(@Param("actionCode") int actionCode,@Param("userAccount") String userAccount,@Param("presentType") String presentType);
	public List<UserInfoBean> selectTop50AvaPrizes(@Param("actionCode")int actionCode, @Param("available")int available);
	public List<UserInfoBean> getLotteryList4Wd161(@Param("actionCode") int actionCode);


}
