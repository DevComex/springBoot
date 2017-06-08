/**
  * -------------------------------------------------------------------------
  * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
  * @版权所有：北京光宇在线科技有限责任公司
  * @项目名称：action-wd11yearrechargerebate
  * @作者：chenglong
  * @联系方式：chenglong@gyyx.cn
  * @创建时间：2017年3月29日 上午10:59:56
  * @版本号：0.0.1
  *-------------------------------------------------------------------------
  */
package cn.gyyx.action.service.wd11yearrechargerebate;

import java.util.Calendar;

import com.google.common.base.Throwables;

import cn.gyyx.action.beans.wd11yearrechargerebate.Constants;
import cn.gyyx.action.beans.wd11yearrechargerebate.InterfaceChangeAgentBean;
import cn.gyyx.action.beans.wd11yearrechargerebate.InterfaceTongAgentBean;
import cn.gyyx.action.beans.wd11yearrechargerebate.RechargeRebateBean;
import cn.gyyx.action.beans.wd11yearrechargerebate.RechargerebateAcessLogWithBLOBs;
import cn.gyyx.action.bll.wd11yearrechargerebate.GameRoleInfoBll;
import cn.gyyx.action.bll.wd11yearrechargerebate.RechargeRebateAcessLogBll;
import cn.gyyx.action.cache.MemcacheUtil;
import cn.gyyx.core.memcache.XMemcachedClientAdapter;


/**
 * <p>
 * 问道11周年充值返现Dao
 * </p>
 * 
 * @author chenglong
 * @since 0.0.1
 */
public class Wd11YearRechargeRebateService {
    
   /**
    * 缓存
    */
   private XMemcachedClientAdapter memcachedClientAdapter = MemcacheUtil.getActionMemcache();
   private GameRoleInfoBll gameLevelBll = new GameRoleInfoBll();
   private RechargeRebateAcessLogBll rechargeRebateAcessLogBll = new RechargeRebateAcessLogBll();

    /**
     * 
      * <p>
      *    是否绑定app
      * </p>
      *
      * @action
      *    chenglong 2017年3月29日 上午10:52:52 是否绑定app
      *
      * @param userId 用户ID
      * @param logCode 
      * @return boolean 绑定app结果
     */
    public boolean isBindApp(Integer userId, int logCode) {
        //调用接口查询，不放缓存防止解绑
        InterfaceTongAgentBean bean = InterfaceTongModuleAgent.getResultFromUserId(userId,logCode);
        if("bind".equals(bean.getStatus())){//为bind 绑定app
            return true;
        }
        return false;
    }

    /**
     * 
      * <p>
      *    新服充值金额是否大于等于10
      * </p>
      *
      * @action
      *    chenglong 2017年3月29日 上午11:04:53 新服充值金额是否大于等于10
      *
      * @param startTime
      * @param userId 用户ID
      * @param account 用户账号
      * @param serverCode 服务器ID
     * @param logCode 
      * @return boolean
     */
    public boolean rechargeIsGt10(boolean rebateOpen,String startTime,Integer userId, String account, int serverCode, int logCode) {
        String key = Constants.RECHARGEISGT10_PRIFIX + account;
        if(!rebateOpen){
            return false;
        }
        //查询缓存是否存在
        if(memcachedClientAdapter.get(key, Integer.class) != null){
            RechargerebateAcessLogWithBLOBs accessLogBean = new RechargerebateAcessLogWithBLOBs();
            accessLogBean.setRecharge10Result("用户在某个区服充值金额缓存获取 :"+memcachedClientAdapter.get(key, Integer.class));
            accessLogBean.setCode(logCode);
            rechargeRebateAcessLogBll.update(accessLogBean);
            return true;
        }
        //缓存没有调用接口查询
        String endTime = getEndTime();
        InterfaceChangeAgentBean bean = InterfaceChangeModuleAgent.getRechargeResult(startTime, endTime, Constants.GAMEID, serverCode, userId,logCode);
        if("success".equals(bean.getStatus())){
            int data=Integer.parseInt(bean.getData())/100;//金元宝换算成RMB
            if (data >= 10) {
                //如果符合条件缓存设置并返回查询结果
                memcachedClientAdapter.set(key,24 * 3600, "1");
                return true;
            }
        }
        return false;
    }

    /**
     * 
      * <p>
      *    新服等级是否大于等于60
      * </p>
      *
      * @action
      *    chenglong 2017年3月29日 上午11:06:53 新服等级是否大于等于60
      *
      * @param jdbcClassDriverName
      * @param jdbcUrl
      * @param jdbcUsName 
      * @param jdbcPassword 
      * @param userId 用户ID
      * @param account 用户账号
      * @param serverCode 服务器ID
      * @param logCode 
      * @return boolean
     */
    public boolean levelIsGt60(boolean rebateOpen,String jdbcClassDriverName,String jdbcUrl,String jdbcUsName,String jdbcPassword,
            String account,int logCode) {
        if(!rebateOpen){
            return false;
        }
        RechargerebateAcessLogWithBLOBs accessLogBean = new RechargerebateAcessLogWithBLOBs();
        accessLogBean.setCode(logCode);
        String key = Constants.LEVELISGT60_PRIFIX + account;
        //查询缓存是否存在
        if(memcachedClientAdapter.get(key, String.class) != null){
            accessLogBean.setLevel60Result("新服等级是否大于等于60缓存获取 :"+memcachedClientAdapter.get(key, Integer.class));
            rechargeRebateAcessLogBll.update(accessLogBean);
            return true;
        }
        //缓存没有数据库查询等级 
        int level = gameLevelBll.getLevel(jdbcClassDriverName,jdbcUrl,jdbcUsName,jdbcPassword,account);
        accessLogBean.setLevel60Result("新服等级是否大于等于60数据库获取 :"+level);
        rechargeRebateAcessLogBll.update(accessLogBean);
        if(level >= 60){
            //如果符合条件缓存设置并返回查询结果
            memcachedClientAdapter.set(key,24 * 3600, "1");
            return true;
        }
        return false;
    }

    /**
     * 
      * <p>
      *    获取充值返现金额
      * </p>
      *
      * @action
      *    chenglong 2017年3月29日 下午2:13:02 获取充值返现金额
      *
      * @param account 用户账号
      * @param logCode 日志code
      * @return RechargeRebateBean
     */
    public RechargeRebateBean getRechargeRebateData(
            String account,int logCode) {
        RechargeRebateBean data = new RechargeRebateBean();
        //查询历史充值总额 
        InterfaceChangeAgentBean allRechargeResult = InterfaceChangeModuleAgent.getAllRechargeResult(account,logCode);
        int rechargeSumMoney = (int)Double.parseDouble(allRechargeResult.getData()); 
        data.setRechargeSum(rechargeSumMoney);
        //获取返利上限金额
        int rebateSum = this.getRebateMoeny(rechargeSumMoney);
        data.setRebateSum(rebateSum);
        if(rebateSum == 0){
            data.setRechargeRebateBalance(300 - rechargeSumMoney);
        }else{
            // 获取返利明细,返利大于0才计算 最小是60
            // 最大返利 规则：返利上限*2再向下取100的整倍数
            data.setMaxRebate((data.getRebateSum() * 2 / 100) * 100);
            // 领取工资天数
            data.setSalaryDays(this.getSalaryDays(data.getRebateSum()));
            // 每天可领取工资份数 规则： 返利上限向下取50的整倍数/50/可领取工资天数 然后结果向上取整
            double dayN = 1.0 * ((data.getRebateSum() / 50) * 50
            / 50 )/ data.getSalaryDays() ;
            data.setDailyNumber((int)Math.ceil(dayN));
            // 可领取福袋数 规则：返利上限*2再除100的商
            data.setLuckyNumber(data.getRebateSum() * 2 / 100);
        }
        return data;
    }

    /**
     * 
      * <p>
      *    获取可领取工资天数 eg:
      *    60~1040      1
      *    1060~3040    2
      *    3060~6540    3
      *    6560~10040   4
      *    10060~20040  5
      *    20060~30000  6
      * </p>
      *
      * @action
      *    chenglong 2017年3月30日 上午10:19:13 描述
      *
      * @param rebateSum 返利上限
      * @return int
     */
    private int getSalaryDays(int rebateSum) {
        int result = 6;
        if(rebateSum < 1060){
            result = 1;
        }else if(rebateSum < 3060){
            result = 2;
        }else if(rebateSum < 6560){
            result = 3;
        }else if(rebateSum < 10060){
            result = 4;
        }else if(rebateSum < 20060){
            result = 5;
        }
        return result;
    }

    /**
     * 
      * <p>
      *    获取返现上限
      *    规则如下：
      *    充值金额<300
      *         返利金额：0元
      *    充值金额>=300且<15万
      *         返利金额：充值金额除以100的商*20
      *         返利金额举例：充值321元，返还上限为 60
      *    充值金额>=15万
      *         返利金额：30000元   
      * </p>
      *
      * @action
      *    chenglong 2017年3月29日 下午2:27:22 获取返现金额
      *
      * @param rechargeSumMoney
      * @return int
     */
    private int getRebateMoeny(int rechargeSumMoney) {
        if(rechargeSumMoney < 300){
            return 0;
        }else if(rechargeSumMoney <150000){
            return (rechargeSumMoney/100)*20/50*50;
        }else{
            return 30000;
        }
    }
    /**
     * 
      * <p>
      *    获取当前时间年月日
      * </p>
      *
      * @action
      *    lihu 2017年3月30日 下午1:00:19 描述
      *
      * @return String
     */
    private  String getEndTime(){
        Calendar now = Calendar.getInstance();  
        now.add(Calendar.DAY_OF_MONTH, 1);
       return now.get(Calendar.YEAR)+"-"+(now.get(Calendar.MONTH) + 1) +"-"+now.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 
      * <p>
      *    获取请求次数
      *    规则：同一账号，1分钟内最多请求10次，30分钟最多请求100次
      *        同一ip，1分钟最多请求30次，30分钟最多请求500次
      * </p>
     * @param instertCode 
      *
      * @action
      *    lihu 2017年3月30日 下午1:00:19 描述
      *
      * @return String
     */
	public boolean requestIsLimit(String account,String ip, int instertCode) {
		
	    RechargerebateAcessLogWithBLOBs accessLogBean = new RechargerebateAcessLogWithBLOBs();
		try {
			//账号1分钟请求次数
			Long memAccount1Min = memcachedClientAdapter.getFirstClient().incr(
			    String.format(Constants.ACCOUNT_REQUEST_PRIFIX, 1,account), 1L, 1L,5000,60);
			//账号30分钟请求次数
			Long memAccount30Min = memcachedClientAdapter.getFirstClient().incr(
					String.format(Constants.ACCOUNT_REQUEST_PRIFIX, 30,account), 1L, 1L,5000,60*30);
			//IP 1分钟请求次数
			Long memIp1Min = memcachedClientAdapter.getFirstClient().incr(
					String.format(Constants.IP_REQUEST_PRIFIX, 1,ip)
					, 1L, 1L,5000,60);
			//IP 30分钟请求次数
			Long memIp30Min = memcachedClientAdapter.getFirstClient().incr(
					String.format(Constants.IP_REQUEST_PRIFIX, 30,ip), 1L, 1L,5000,60*30);
			if(memAccount1Min >= 10 || memAccount30Min>=300 
					|| memIp1Min >=30 || memIp30Min >=500){
			    //记录操作日志
			    accessLogBean.setResult("查询次数被限制account{"+account+"},ip{"+ip+"} : 账号1分钟请求次数="+memAccount1Min+",账号30分钟请求次数="+memAccount30Min+
			        ",IP 1分钟请求次数="+memIp1Min+",IP 30分钟请求次数"+memIp30Min);
			    accessLogBean.setCode(instertCode);
			    rechargeRebateAcessLogBll.update(accessLogBean);
				return true;
			}
		} catch (Exception e) {
		    //记录操作日志
		    accessLogBean.setResult("查询次数是否被限制异常  account{"+account+"},ip{"+ip+"} :"+Throwables.getStackTraceAsString(e) );
                    accessLogBean.setCode(instertCode);
                    rechargeRebateAcessLogBll.update(accessLogBean);
			return false;
		}
		
		return false;
	}
	
}
