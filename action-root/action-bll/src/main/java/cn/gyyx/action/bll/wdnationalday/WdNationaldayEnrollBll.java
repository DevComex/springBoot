package cn.gyyx.action.bll.wdnationalday;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;

import cn.gyyx.action.beans.wdnationalday.WdNationaldayEnrollBean;
import cn.gyyx.action.beans.wdnationalday.WdNationaldaySignLogBean;
import cn.gyyx.action.bll.lottery.MemcacheUtil;
import cn.gyyx.action.dao.wdnationaldayshare.WdNationaldayEnrollDao;
import cn.gyyx.action.dao.wdnationaldayshare.WdNationaldaySignLogDao;
import cn.gyyx.core.memcache.XMemcachedClientAdapter;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 版        权：光宇游戏
 * 作        者：ChengLong
 * 创建时间：2016年9月25日 下午3:35:36
 * 描        述：报名信息表
 */
public class WdNationaldayEnrollBll {
	private static final Logger logger = GYYXLoggerFactory.getLogger(WdNationaldayEnrollBll.class);
	
	private WdNationaldayEnrollDao wdNationaldayEnrollDao = new WdNationaldayEnrollDao();
	private WdNationaldaySignLogDao wdNationaldaySignLogDao = new WdNationaldaySignLogDao();
	
	private MemcacheUtil memcacheUtil = new MemcacheUtil();
	private XMemcachedClientAdapter memcachedClientAdapter = memcacheUtil
			.getMemcache();

	public WdNationaldayEnrollBean getEnrollInfoByAccount(String account) {
		return wdNationaldayEnrollDao.getEnrollInfoByAccount(account);
	}
	
	public WdNationaldayEnrollBean getEnrollInfoFromDatabaseByOpenId(String openId) {
		return wdNationaldayEnrollDao.getEnrollInfoFromDatabaseByOpenId(openId);
	}
	
	public int insert(WdNationaldayEnrollBean bean) {
		return wdNationaldayEnrollDao.insert(bean);
	}
	
	/**
	 * 为指定的账号，增加相应积分
	 */
	public int increaseScore(String account,int increaseScore, SqlSession session) {
		return wdNationaldayEnrollDao.increaseScore(account,increaseScore,session);
	}

	/**
	 * 获取当天的签到日志
	 */
	public WdNationaldaySignLogBean getTodaySignLog(String account,String today) {
		return wdNationaldaySignLogDao.getTodaySignLog(account,today);
	}
	
	/**
	 * 获取历史签到参数
	 */
	public List<String> getSignHistoryDate(String account) {
		return wdNationaldaySignLogDao.getSignHistoryDate(account);
	}

	/**
	 * 插入签到日志
	 * @param session 
	 */
	public int insert(WdNationaldaySignLogBean bean, SqlSession session) {
		return wdNationaldaySignLogDao.insert(bean,session);
	}

	/**
	 * 获取累计签到数量-session
	 */
	public int getSignCountByAccount(String account, SqlSession session) {
		return wdNationaldaySignLogDao.getSignCountByAccount(account,session);
	}
	
	/**
	 * 获取累计签到数量
	 */
	public int getSignCountByAccount(String account) {
		return wdNationaldaySignLogDao.getSignCountByAccount(account);
	}
	
	/**
	 * 通过openId获取用户信息
	 */
	public WdNationaldayEnrollBean getUserInfoByOpenId(String openId) {
		if(openId == null || openId.trim().equals("")){
			return null;
		}
		WdNationaldayEnrollBean res = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			String value = memcachedClientAdapter.get("wd_tenyear_nationalday_" + openId, String.class);
			if(value == null || value.trim().equals("")){
				//数据库获取
				res = wdNationaldayEnrollDao.getEnrollInfoFromDatabaseByOpenId(openId);
				if(res == null){
					return null;
				}else {
					//加入缓存
					mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
					value = mapper.writeValueAsString(res);
					memcachedClientAdapter.set("wd_tenyear_nationalday_" + openId, this.getUntilNDayEndSeconds(15),value);
				}
			}else{
				res = mapper.readValue(value, WdNationaldayEnrollBean.class);
			}
		} catch (Exception e) {
			logger.error("通过openId获取用户信息错误---"+e);
		}
		return res;
	}
	
	/**
	 * 得到距离N天结束秒数
	 */
	public Integer getUntilNDayEndSeconds(int n) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			Date date = new Date();
			calendar.setTime(date);
			calendar.add(Calendar.DATE, n);
			Date tempDate;
			tempDate = sdf.parse(sdf.format(calendar.getTime()));
			return (int) ((tempDate.getTime() - date.getTime()) / 1000);
		} catch (ParseException e) {
			logger.error("getUntilDayEndSeconds error" , e);
			return 60 * 60 * 24;
		}
	}
}
