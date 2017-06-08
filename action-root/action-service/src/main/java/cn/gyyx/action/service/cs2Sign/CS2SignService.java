package cn.gyyx.action.service.cs2Sign;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.slf4j.Logger;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.cs2sign.Cs2BindInfoBean;
import cn.gyyx.action.beans.cs2sign.Cs2SignBean;
import cn.gyyx.action.beans.cs2sign.Cs2SignInfoBean;
import cn.gyyx.action.bll.cs2sign.Cs2signBLL;
import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.distribute.lock.DLockException;
import cn.gyyx.distribute.lock.DistributedLock;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class CS2SignService {
	private static final Logger LOG = GYYXLoggerFactory.getLogger(CS2SignService.class);
	private static final int ACTION_CODE = 364;
	private Cs2signBLL signInfoBll = new Cs2signBLL();
	private CS2GameAgent agent = new CS2GameAgent();
	private static Timing timing = new Timing("2016-05-20 00:00:00","2016-05-26 23:59:59");
	private static Map<String,String> prizeMap = new HashMap<>();
	static {
		prizeMap.put("20160520", "60102622:1");
		prizeMap.put("20160521", "60102623:1");
		prizeMap.put("20160522", "60102624:1");
		prizeMap.put("20160523", "60102625:1");
		prizeMap.put("20160524", "60102626:1");
		prizeMap.put("20160525", "60102627:1");
		prizeMap.put("20160526", "60102628:1");
	}
	/** 
	* 叙述:获得用户签到及绑定信息 当没有时创建<br />
	* @param user 用户登陆信息
	* @param result 结果储存
	*/
	public void getSignInfo(UserInfo user,ResultBean<Cs2SignInfoBean> result) {
		final String account = user.getAccount();
		int count = signInfoBll.selectCs2SignInfoCountByAccount(account);
		if(count == 0) {
			Cs2SignInfoBean info = new Cs2SignInfoBean();
			info.setAccount(account);
			info.setSignContinuity(0);
			info.setUserCode(user.getUserId());
			if(signInfoBll.insertCs2SignInfoBean(info)) {
				result.setIsSuccess(true);
				result.setData(info);
				result.setMessage("新的信息已增加");
			} else {
				result.setIsSuccess(false);
				result.setMessage("网络超时");
			}
		} else {
			Cs2SignInfoBean info = signInfoBll.selectCs2SignInfoBeanByAccount(account);
			info.setSignToday(signInfoBll.isSignToday(account));
			result.setIsSuccess(true);
			result.setData(info);
			result.setMessage("查询成功");
		}
	}
	
	public void bindInfo(UserInfo user,String server,String serverIp,String serverPort,String character,ResultBean<Object> result) {
		final String account = user.getAccount();
		if(signInfoBll.selectCs2SignInfoCountByAccount(account) == 0) {
			result.setIsSuccess(false);
			result.setMessage("未找到您的注册信息,请按照正常流程参与活动");
		} else {
			try(DistributedLock lock = new DistributedLock("bindInfo" + ACTION_CODE)) {
				lock.weakLock(30, 2);
				List<Cs2BindInfoBean> roles = signInfoBll.selectCs2BindInfoBeanByAccount(account);
				if(roles.size() != 0) {//TODO 将在下次迭代中允许多个绑定
					result.setIsSuccess(false);
					result.setMessage("您当前已有绑定角色");
				} else if(signInfoBll.selectCs2BindInfoCountByServerAndChar(server, character) != 0) {
					result.setIsSuccess(false);
					result.setMessage("此服务器角色已被绑定");
				} else {
					Cs2BindInfoBean bindBean = new Cs2BindInfoBean();
					bindBean.setAccount(account);
					bindBean.setCharacter(character);
					bindBean.setServer(server);
					bindBean.setServerIp(serverIp);
					bindBean.setServerPort(serverPort);
					if(signInfoBll.insertCs2BindInfoBean(bindBean)) {
						result.setIsSuccess(true);
						result.setMessage("绑定成功");
					} else {
						result.setIsSuccess(false);
						result.setMessage("网络超时");
					}
				}
			} catch (DLockException e) {
				LOG.warn("DLockException in bind role:" + e);
				result.setIsSuccess(false);
				result.setMessage("网络超时");
			}
		}
	}
	
	public void sign(UserInfo user,ResultBean<Object> result) {
		final String account = user.getAccount();
		if(timing.getCurrentFlag() == Timing.FLAG_BEFORE) {
			result.setIsSuccess(false);
			result.setMessage("活动未开始");
		} else if(timing.getCurrentFlag() == Timing.FLAG_AFTER) { 
			result.setIsSuccess(false);
			result.setMessage("活动已结束");
		} else if(signInfoBll.selectCs2SignInfoCountByAccount(account) == 0) {
			result.setIsSuccess(false);
			result.setMessage("未找到您的注册信息,请按照正常流程参与活动");
		} else {
			try(DistributedLock lock = new DistributedLock("sign" + ACTION_CODE)) {
				lock.weakLock(30, 2);
				if(signInfoBll.isSignToday(account)) {
					result.setIsSuccess(false);
					result.setMessage("您今日已经签到过了");
				} else {
					//↓插入、更新、发送物品 - 非事务实现
					Date current = new Date();
					Cs2SignBean cs2SignBean = new Cs2SignBean();
					cs2SignBean.setAccount(account);
					cs2SignBean.setSignDate(current);
					LOG.info("insert sign bean...");
					signInfoBll.insert(cs2SignBean);
					LOG.info("query user info...");
					Cs2SignInfoBean cs2Info = signInfoBll.selectCs2SignInfoBeanByAccount(account);
					if(cs2Info.getSignLast() == null || !isContinuity(cs2Info.getSignLast(),current)) {
						LOG.info("update user info for sign broken...");
						signInfoBll.updateCs2SignInfo(current, 1, account);
					} else {
						LOG.info("update user info for sign continuity...");
						signInfoBll.updateCs2SignInfo(current,cs2Info.getSignContinuity() + 1, account);
					}
					String prizeCode = getRealTodayPrize();
					if(prizeCode != null) {
						for(Cs2BindInfoBean binded:signInfoBll.selectCs2BindInfoBeanByAccount(account)){
							LOG.info("send prize:" + prizeCode + " to info:" + binded);
							ResultBean<Boolean> sendResult = agent.givePrize(binded.getCharacter(),prizeCode, "action" + ACTION_CODE + "sign", binded.getServerIp(),  binded.getServerPort());
							LOG.info("send result:" + sendResult.getIsSuccess());
						}
						LOG.info("send completed.");
					} else {
						LOG.info("no prize code found now,send failed");
					}
					result.setIsSuccess(true);
					result.setMessage("签到完成");
				}
			} catch (DLockException e) {
				LOG.warn("DLockException in sign:" + e);
				result.setIsSuccess(false);
				result.setMessage("网络超时");
			}
		}
	}

	private boolean isContinuity(Date signLast,Date signCurrent) {
		long offset = TimeZone.getDefault().getRawOffset();
		long dayLength = 3600 * 24 * 1000;
		long s1 = (signLast.getTime() + offset) / dayLength;
		long s2 = (signCurrent.getTime() + offset) / dayLength;
		return s2 - s1 == 1;
	}
	
	private String getRealTodayPrize() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String key = format.format(new Date());
		return prizeMap.get(key);
	}
}
