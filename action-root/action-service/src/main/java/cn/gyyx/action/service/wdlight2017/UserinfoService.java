package cn.gyyx.action.service.wdlight2017;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.AddressBean;
import cn.gyyx.action.beans.wdlight2017.UserinfoBean;
import cn.gyyx.action.beans.wdlight2017.WishBean;
import cn.gyyx.action.beans.wdlight2017.base.Constants;
import cn.gyyx.action.bll.lottery.AddressBLL;
import cn.gyyx.action.bll.lottery.MemcacheUtil;
import cn.gyyx.action.bll.wdlight2017.UserinfoBll;
import cn.gyyx.action.bll.wdlight2017.WishBll;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.action.service.agent.CallWebServiceInsuranceAgent;
import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.core.memcache.XMemcachedClientAdapter;
import cn.gyyx.distribute.lock.DistributedLock;
import cn.gyyx.log.sdk.GYYXLoggerFactory;


public class UserinfoService {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(UserinfoService.class);
	
	private AddressBLL addressBll = new AddressBLL();
	private UserinfoBll userinfoBll = new UserinfoBll();
	private WishBll wishBll = new WishBll();
	
	private MemcacheUtil memcacheUtil = new MemcacheUtil();
	private XMemcachedClientAdapter memcachedClientAdapter = memcacheUtil
			.getMemcache();
	
	private static SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	
	/**
	 * 验证角色
	 *
	 */
	public ResultBean<String> changeRoleInfo(UserInfo userInfo,int serverId) {
		ResultBean<String> resultBean = new ResultBean<>();
		int roleCount = 0;
		String nameTempString = "wdlight2017";
		// 获取缓存
		if (memcachedClientAdapter.get(userInfo.getUserId()
				+ nameTempString, int.class) != null) {
			roleCount = memcachedClientAdapter.get(userInfo.getUserId()
					+ nameTempString, int.class);
			memcachedClientAdapter.set(userInfo.getUserId()
					+ nameTempString, 300,
					Integer.toString(roleCount + 1));
		} else {
			// 增加缓存
			memcachedClientAdapter.set(userInfo.getUserId()
					+ nameTempString, 300, Integer.toString(0));
		}

		// 次数记录大于20次
		if (roleCount >= 4) {
			resultBean.setProperties(false, "禁止多次刷新", "");
		} else {
			resultBean.setProperties(true, "获取成功", "");
			resultBean.setData(CallWebServiceInsuranceAgent
					.getRoleInfo(userInfo.getAccount(), serverId));
		}
		return resultBean;
	}
	
	/**
	 * 查找用户信息
	 *
	 */
	public ResultBean<UserinfoBean> getUserinfoBeanByUserId(int userId) {
		ResultBean<UserinfoBean> resultBean = new ResultBean<>();
		try {
			UserinfoBean userinfoBean = userinfoBll.getUserinfoBeanByUserId(userId);
			if (null != userinfoBean) {
				resultBean.setData(userinfoBean);
				resultBean.setIsSuccess(true);
				
				// 判断是否有地址
				int flag_addr = 0;
				List<WishBean> wishList = wishBll.getMyWishAll(userId);
				if (wishList != null && wishList.size() > 0) {
					for (WishBean wishBean : wishList) {
						if (wishBean.getPresenttype().equals("realPrize")) {
							flag_addr = 1;
							break;
						}
					}
				}
				resultBean.setStateCode(flag_addr);
				resultBean.setMessage("获取用户信息成功");
			} else {
				resultBean.setIsSuccess(false);
				resultBean.setMessage("没有差查找到用户信息");
			}
		} catch (Exception e) {
			logger.warn("getUserinfoBeanByUserId:" , e);
			resultBean.setIsSuccess(false);
			resultBean.setMessage("获取用户信息失败");
		}
		return resultBean;
		
	}
	
	/**
	 * 添加用户信息
	 *
	 */
	public ResultBean<String> addAccountRole(UserInfo userInfo,String roleName,String roleCode,String serverName,int serverId) {
		int userCode = userInfo.getUserId();
		try (DistributedLock lock = new DistributedLock("wdlight2017_lottery_adduserinfo_"
				+ userCode + "." + Constants.actionCode)) {
			lock.weakLock(30, 11);
			UserinfoBean oldUserinfo = userinfoBll.getUserinfoBeanByRoleCode(roleCode.trim());
			if (oldUserinfo != null) {
				return new ResultBean<String>(false, "该角色已经被绑定，请更换其他角色", null);
			}
			UserinfoBean userinfoBean = new UserinfoBean();
			userinfoBean.setRoleCode(roleCode);
			userinfoBean.setRoleName(roleName);
			userinfoBean.setServerId(serverId);
			userinfoBean.setServerName(serverName);
			userinfoBean.setUserId(userCode);
			userinfoBean.setAccount(userInfo.getAccount());
			userinfoBean.setCreateTime(new Date());
			ResultBean<String> resultBean = new ResultBean<>();
			try {
				// 判断是否已经绑定
				UserinfoBean bean = userinfoBll.getUserinfoBeanByUserId(userinfoBean.getUserId());
				if (bean != null) {
					resultBean.setIsSuccess(false);
					resultBean.setMessage("已绑定用户信息");
				} else {
					userinfoBean.setScore(0);
					userinfoBean.setConsumeScore(0);
					userinfoBll.addUserinfoBean(userinfoBean);
					resultBean.setIsSuccess(true);
					resultBean.setMessage("绑定用户信息成功");
				}
			} catch (Exception e) {
				logger.warn("addAccountRole:" + e);
				resultBean.setIsSuccess(false);
				resultBean.setMessage("绑定用户信息失败");
			}
			return resultBean;
		} catch (Exception e) {
			logger.warn(e.getMessage());
			logger.warn("addAccountRole warn:" , e);
			return new ResultBean<>(false, "人气太过火爆，正在排队中...", null);
		}
	}
	
	/**
	 * 获取用户地址
	 *
	 */
	public ResultBean<AddressBean> getAddress(int userId) {
		ResultBean<AddressBean> result = new ResultBean<>();
		try {
			// 获取信息
			result = addressBll.getAddress(userId, Constants.actionCode);
		} catch (Exception e) {
			logger.warn("getAddress:" , e);
			result.setIsSuccess(false);
			result.setMessage("获取地址信息失败");
		}
		logger.info("getAddress result ", result);
		return result;
	}
	
	/**
	 * 操作用户地址
	 *
	 */
	public ResultBean<String> editAddress(int userId, AddressBean addressBean) {
		ResultBean<String> result = new ResultBean<>();
		try {
			// 获取信息
			result = addressBll.editAddress(userId, Constants.actionCode ,addressBean);
		} catch (Exception e) {
			logger.warn("editAddress:" , e);
			result.setIsSuccess(false);
			result.setMessage("地址更改信息失败");
		}
		logger.info("getAddress result ", result);
		return result;
	}
	
	
}
