package cn.gyyx.action.bll.wdqingchengshan;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.gyyx.action.beans.jswswxsign.UserScore;
import cn.gyyx.action.beans.wdnationalday.WdNationaldayEnrollBean;
import cn.gyyx.action.beans.wdnationalday.WdNationaldaySignLogBean;
import cn.gyyx.action.beans.wdqingchengshan.SignLogBean;
import cn.gyyx.action.bll.lottery.MemcacheUtil;
import cn.gyyx.action.bll.wdnationalday.WdNationaldayEnrollBll;
import cn.gyyx.action.dao.wdnationaldayshare.WdNationaldayEnrollDao;
import cn.gyyx.action.dao.wdnationaldayshare.WdNationaldaySignLogDao;
import cn.gyyx.action.dao.wdqingchengshan.SignLogDAO;
import cn.gyyx.action.dao.wdqingchengshan.UserScoreDAO;
import cn.gyyx.core.memcache.XMemcachedClientAdapter;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class EnrollAndSignBLL {
	  
private static final Logger logger = GYYXLoggerFactory.getLogger(EnrollAndSignBLL.class);
    private WdNationaldayEnrollDao wdNationaldayEnrollDao = new WdNationaldayEnrollDao();
	private UserScoreDAO userScoreDAO = new UserScoreDAO();
	private SignLogDAO signLogDao = new SignLogDAO();
	
	private MemcacheUtil memcacheUtil = new MemcacheUtil();
	private XMemcachedClientAdapter memcachedClientAdapter = memcacheUtil
			.getMemcache();
	/**
	 * 获取用户报名信息
	 */
	public WdNationaldayEnrollBean getEnrollInfoByAccount(String account) {
		return wdNationaldayEnrollDao.getEnrollInfoByAccount(account);
	}
	/**
	 * 插入报名信息
	 */
	public int insert(WdNationaldayEnrollBean bean) {
		return wdNationaldayEnrollDao.insert(bean);
	}

	/**
	 * 获取当天的签到日志
	 */
	public SignLogBean getTodaySignLog(String account,String today) {
		return signLogDao.getTodaySignLog(account,today);
	}
	
	/**
	 * 插入签到日志
	 * @param session 
	 */
	public int insert(SignLogBean bean, SqlSession session) {
		return signLogDao.insertSignLog(bean,session);
	}
	
	public List<SignLogBean> select(SignLogBean params) {
		return signLogDao.select(params);
	}
	
	/**
	 * 为指定的账号，增加相应积分
	 */
	public int increaseScore(String account,int increaseScore, SqlSession session) {
		return wdNationaldayEnrollDao.increaseScore(account,increaseScore,session);
	}
	
	//通过account获取用户信息
	public WdNationaldayEnrollBean getUserInfoByAccount(String account) {
		if(account == null || account.trim().equals("")){
			return null;
		}
		WdNationaldayEnrollBean res = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			String value = memcachedClientAdapter.get("wd_tenyear_nationalday_" + account, String.class);
			if(value == null || value.trim().equals("")){
				//数据库获取
				res = wdNationaldayEnrollDao.getEnrollInfoFromDatabaseByOpenId(account);
				if(res == null){
					return null;
				}else {
					//加入缓存
					mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
					value = mapper.writeValueAsString(res);
					memcachedClientAdapter.set("wd_tenyear_nationalday_" + account, this.getUntilNDayEndSeconds(15),value);
				}
			}else{
				res = mapper.readValue(value, WdNationaldayEnrollBean.class);
			}
		} catch (Exception e) {
			logger.error("通过account获取用户信息错误---"+e);
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
	

	/***
	 * 新增
	 */
	public int insert(UserScore userScore){
		return userScoreDAO.insertUserScore(userScore);
	}
	
	/***
	 * 新增  带session
	 */
	public int insert(UserScore userScore,SqlSession session){
		return userScoreDAO.insertUserScore(userScore,session);
	}
	
	/***
	 * 更新
	 */
	public int update(UserScore userScore){
		return userScoreDAO.updateUserScore(userScore);
	}
	
	/***
	 * 更新  带session
	 */
	public int update(UserScore userScore,SqlSession session){
		return userScoreDAO.updateUserScore(userScore,session);
	}

	/***
	 * 根据account获取用户当前分数
	 */
	public UserScore getUserScore(String account, SqlSession session){
		UserScore param = new UserScore();
		UserScore result = userScoreDAO.getUserScore(param,session);
		return result;
	}

}
