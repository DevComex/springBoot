/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：LhzsGoldBack
 * @作者：范佳琪
 * @联系方式：fanjiaqig@gyyx.cn
 * @创建时间： 2016年04月13日
 * @版本号：
 * @本类主要用途描述：金币返还业务逻辑拼装层
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.service.lhzsgoldback;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import com.google.common.base.Throwables;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lhzsgoldback.LhzsGoldReturnApplyBean;
import cn.gyyx.action.beans.lhzsgoldback.LhzsGoldReturnUserBean;
import cn.gyyx.action.beans.lhzsgoldback.RechangeBean;
import cn.gyyx.action.beans.lhzsgoldback.ReturnBean;
import cn.gyyx.action.bll.lhzsgoldback.LhzsGoldReturnBll;
import cn.gyyx.action.bll.lhzsgoldback.RechangeBll;
import cn.gyyx.action.bll.lhzsgoldback.ReturnBll;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.action.service.agent.CallWebAPIUserInfoAgent;
import cn.gyyx.action.service.insurance.MemcacheUtil;
import cn.gyyx.core.memcache.XMemcachedClientAdapter;
import cn.gyyx.distribute.lock.DLockException;
import cn.gyyx.distribute.lock.DistributedLock;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class GoldBackService {

	private static final Logger logger = GYYXLoggerFactory
			.getLogger(GoldBackService.class);
	
	private RechangeBll rechangeBll = new RechangeBll();
	private ReturnBll returnBll = new ReturnBll();
	private LhzsGoldReturnBll lhzsGoldReturnBll = new LhzsGoldReturnBll();
	
	private MemcacheUtil memcacheUtil = new MemcacheUtil();
	private XMemcachedClientAdapter memcachedClientAdapter = memcacheUtil.getMemcache();
	private static final String  mem_account_pwd = "lhzs_425_%s";
	private static final String  mem_account_get = "lhzs_425_get_";
	private static final String  mem_account_insert = "lhzs_425_insert_";
	private static final String  mem_account_applycount = "lhzs_425_applycount_";
	
	/**
	 * @Title findByAccount
	 * @Description 根据用户账号查询用户返还记录
	 * @author fanjiaqi
	 * @param account
	 * @return
	 */
	public Integer findUserReturnRecord(String account){
		logger.debug("灵魂战神  金币返还活动-GoldbackService-findUserReturnRecord,参数account:" + account);
		Integer count = returnBll.findByAccount(account);
		return count;		
	}
	
	/**
	 * @Title findByAccount
	 * @Description 根据用户账号查询用户是否满足领取条件
	 * @author fanjiaqi
	 * @param account
	 * @return
	 */
	public ResultBean<RechangeBean> findByAccount(String account){
		logger.debug("灵魂战神  金币返还活动-GoldbackService-findByAccount,参数account:" + account);
		RechangeBean rechangeBean = new RechangeBean();
		rechangeBean.setAccount(account);
		ResultBean<RechangeBean> resultBean = new ResultBean<RechangeBean>(false, "该账号不在返还范围内", rechangeBean);
		Integer count = returnBll.findByAccount(account);
		rechangeBean = rechangeBll.findByAccount(account);
		if(null != rechangeBean){
			if(count == 0){
				rechangeBean.setCoinNum(rechangeBean.getCashAmount() + rechangeBean.getSpentCash());
				resultBean.setProperties(true, "该账号在返还范围内", rechangeBean);
			} else {
				rechangeBean.setCoinNum(0);
				resultBean.setProperties(true, "该账号可返还的金币数为0", rechangeBean);
			}
		}
		return resultBean;
	}
	
	/**
	 * @Title addReturnBean
	 * @Description 申请返还金币
	 * @author fanjiaqi
	 * @param account
	 * @return
	 */
	public ResultBean<String> addReturnBean(String account,int userCode){
		logger.debug("灵魂战神  金币返还活动-GoldbackService-findByAccount,参数account:" + account
				+ ",参数userCode：" + userCode);
		ResultBean<String> resultBean = new ResultBean<String>(false,"该账号不在返还范围内",null);
		RechangeBean rechangeBean = new RechangeBean();
		ReturnBean returnBean = new ReturnBean();
		returnBean.setAccount(account);
		returnBean.setUserCode(userCode);
		returnBean.setReturnDate(new Date());
		int count = findUserReturnRecord(account);
		rechangeBean = rechangeBll.findByAccount(account);
		if(rechangeBean != null){
			if(count == 0){
				returnBean.setReturnAmount(rechangeBean.getCashAmount() + rechangeBean.getSpentCash());
				returnBll.addReturnBean(returnBean);
				resultBean.setProperties(true, "恭喜您，申请返还成功！", null);
			} else {
				resultBean.setProperties(true, "该账号可返还的金币数为0", null);
			}
		}
		return resultBean;
	}

	//--------------------------------------灵魂战神 2016-12-15下线返还金币-------------------------------------
	
	/**
	 * 验证账号密码,并查询金币数量
	 */
	public static final String HD_NAME = "灵魂战神201612金币返回";
	public static final int HD_CODE = 425;
	public cn.gyyx.action.beans.novicecard.ResultBean<String> getgold(String account, String pwd) {
		cn.gyyx.action.beans.novicecard.ResultBean<String> result = new cn.gyyx.action.beans.novicecard.ResultBean<>(false,"查询剩余金币数失败","");
		LhzsGoldReturnUserBean user = null;
		String cache = memcachedClientAdapter.get(mem_account_get+account, String.class);
		if(cache != null && !cache.trim().equals("")){
			logger.info(String.format("灵魂战神金币返还活动[查询金币:最终结果返回:缓存],账号:%s,密码:%s,返回结果:%s",account, pwd, cache));
			result.setProperties(true, "查询数据成功", cache);
			return result;
		}
		int applyCount = 0;
		// 判断剩余金币数量
		try(SqlSession session = getSession()) {
			user = lhzsGoldReturnBll.getUserByAccount(account,session);
			logger.info(String.format("灵魂战神金币返还活动[查询金币:数据库查询用户信息]：账号%s,用户信息:%s",account,user));
			if(user == null){
				logger.info(String.format("灵魂战神金币返还活动[查询金币:判断账号是否存在]：账号%s,该账号不在返还范围内",account));
				return new cn.gyyx.action.beans.novicecard.ResultBean<>(false,"该账号不在返还范围内","no_data");
			}
			applyCount = lhzsGoldReturnBll.getUserApplyCountByAccount(account,session);
			logger.info(String.format("灵魂战神金币返还活动[查询金币:数据库查询申请数量]：账号%s,金币数量:%s",account,applyCount));
		}
		// 后判断用户名密码是否正确-防止刷账号接口
		cn.gyyx.action.beans.novicecard.ResultBean<String> validResult = validAccountAndPwd(account, pwd);
		if(!validResult.getIsSuccess()){
			logger.info(String.format("灵魂战神金币返还活动[查询金币:验证账号密码],账号:%s,密码:%s,返回结果:%s",account, pwd, validResult));
			return new cn.gyyx.action.beans.novicecard.ResultBean<>(false,validResult.getMessage(),"");
		}
		result.setProperties(true, "查询数据成功", user.getRemainGoldCount()+":"+applyCount);
		logger.info(String.format("灵魂战神金币返还活动[查询金币:最终结果返回],账号:%s,密码:%s,返回结果:%s",account, pwd, result));
		memcachedClientAdapter.set(mem_account_get+account, 60 * 5 , user.getRemainGoldCount()+":"+applyCount);
		return result;
	}

	/**
	 * 插入记录
	 */
	public cn.gyyx.action.beans.novicecard.ResultBean<String> insertapply(String account, String pwd, String ip) {
		cn.gyyx.action.beans.novicecard.ResultBean<String> result = new cn.gyyx.action.beans.novicecard.ResultBean<>(false,"提交申请失败","");
		
		String cache = memcachedClientAdapter.get(mem_account_applycount+account, String.class);
		if(cache != null && !cache.trim().equals("")){
			logger.info(String.format("灵魂战神金币返还活动[插入记录:最终结果返回:缓存],账号:%s,返回结果:%s",account, cache));
			result.setProperties(false, "很抱歉,您已提交过申请", "");
			return result;
		}
		
		LhzsGoldReturnUserBean user = null;
		try(DistributedLock lock = new DistributedLock(mem_account_insert+account)) {
			lock.weakLock(30, 11);
			try(SqlSession session = getSession()) {
				// 判断剩余金币数量
				user = lhzsGoldReturnBll.getUserByAccount(account,session);
				logger.info(String.format("灵魂战神金币返还活动[插入记录:数据库查询用户信息]：账号%s,用户信息:%s",account,user));
				if(user == null){
					logger.info(String.format("灵魂战神金币返还活动[插入记录:判断账号是否存在]：账号%s,该账号不在返还范围内",account));
					return new cn.gyyx.action.beans.novicecard.ResultBean<>(false,"该账号不在返还范围内","");
				}
				// 判断是否提交过申请
				int applyCount = lhzsGoldReturnBll.getUserApplyCountByAccount(account,session);
				logger.info(String.format("灵魂战神金币返还活动[插入记录:数据库查询申请数量]：账号%s,金币数量:%s",account,applyCount));
				if(applyCount > 0){
					logger.info(String.format("灵魂战神金币返还活动[插入记录:判断是否提交过申请]：账号%s,该账号已经提交过申请",account));
					memcachedClientAdapter.set(mem_account_applycount+account, 60 * 60 * 1 , "1");
					return new cn.gyyx.action.beans.novicecard.ResultBean<>(false,"很抱歉,您已提交过申请","");
				}
			
				// 后判断用户名密码是否正确-防止刷账号接口
				cn.gyyx.action.beans.novicecard.ResultBean<String> validResult = validAccountAndPwd(account, pwd);
				if(!validResult.getIsSuccess()){
					logger.info(String.format("灵魂战神金币返还活动[插入记录:验证账号密码],账号:%s,密码:%s,返回结果:%s",account, pwd, validResult));
					return new cn.gyyx.action.beans.novicecard.ResultBean<>(false,validResult.getMessage(),"");
				}
			
				// 插入申请记录
				lhzsGoldReturnBll.insertUserApply(new LhzsGoldReturnApplyBean(account,ip,new Date(),user.getRemainGoldCount()),session);
				session.commit(true);
				logger.info(String.format("灵魂战神金币返还活动[插入记录:数据库插入申请记录]：账号:%s",account));
				memcachedClientAdapter.delete(mem_account_get+account);
			}
		}catch(DLockException e){
			logger.error("灵魂战神金币返还活动[插入申请记录],请求超时,错误堆栈:{}", Throwables.getStackTraceAsString(e));
			result.setProperties(false, "接口请求超时", "");
		}
		
		result.setProperties(true, "恭喜您,提交返还申请成功", "");
		logger.info(String.format("灵魂战神金币返还活动[插入记录:最后结果返回],账号:%s,密码:%s,返回结果:%s",account, pwd, result));
		return result;
	}
	
	/**
	 * 验证
	 * @param account
	 * @param pwd
	 */
	public cn.gyyx.action.beans.novicecard.ResultBean<String> validAccountAndPwd(String account, String pwd) {
		String md5Pwd = memcachedClientAdapter.get(
				String.format(mem_account_pwd, account), String.class);
		if(StringUtils.isNotBlank(md5Pwd)){
			if(!pwd.equals(md5Pwd)){
				return new cn.gyyx.action.beans.novicecard.ResultBean<>(false,"密码错误,请重新输入",""); 
			}
			return new cn.gyyx.action.beans.novicecard.ResultBean<>(true,"valid","");
		}else{
			String validAccountAndPwdResult = CallWebAPIUserInfoAgent.validUserAccountAndpwd(account,pwd);
			if(!validAccountAndPwdResult.equals("valid")){
				return new cn.gyyx.action.beans.novicecard.ResultBean<>(false,validAccountAndPwdResult,"");
			}
			memcachedClientAdapter.set(String.format(mem_account_pwd, account), 3600 * 1 * 24, pwd);
			return new cn.gyyx.action.beans.novicecard.ResultBean<>(true,"valid","");
		}
	}
	
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
}
