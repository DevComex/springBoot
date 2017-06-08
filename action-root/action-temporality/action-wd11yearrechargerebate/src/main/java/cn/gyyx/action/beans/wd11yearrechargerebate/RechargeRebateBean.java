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
package cn.gyyx.action.beans.wd11yearrechargerebate;


/**
 * 
  * <p>
  *   充值返现bean
  * </p>
  *  
  * @author chenglong
  * @since 0.0.1
 */
public class RechargeRebateBean {
    //累计充值金额
    private int rechargeSum;
    //累计充值金额字符串
    private String rechargeSumStr;
    //返现上限金额
    private int rebateSum;
    //返现上限金额字符串
    private String rebateSumStr;
    
    //充值返现上限差额 小于300 为 300-x
    private int rechargeRebateBalance;
    
    //最大返利
    private int maxRebate;
    //领取工资天数
    private int salaryDays;
    //每天可领取份数
    private int dailyNumber;
    //可领取福袋数
    private int luckyNumber;

    public int getRechargeSum() {
        return rechargeSum;
    }

    public void setRechargeSum(int rechargeSum) {
        this.rechargeSum = rechargeSum;
    }

    public String getRechargeSumStr() {
        return rechargeSumStr;
    }

    public void setRechargeSumStr(String rechargeSumStr) {
        this.rechargeSumStr = rechargeSumStr;
    }

    public int getRebateSum() {
        return rebateSum;
    }

    public void setRebateSum(int rebateSum) {
        this.rebateSum = rebateSum;
    }

    public String getRebateSumStr() {
        return rebateSumStr;
    }

    public void setRebateSumStr(String rebateSumStr) {
        this.rebateSumStr = rebateSumStr;
    }

    public int getRechargeRebateBalance() {
        return rechargeRebateBalance;
    }

    public void setRechargeRebateBalance(int rechargeRebateBalance) {
        this.rechargeRebateBalance = rechargeRebateBalance;
    }

    public int getMaxRebate() {
        return maxRebate;
    }

    public void setMaxRebate(int maxRebate) {
        this.maxRebate = maxRebate;
    }

    public int getSalaryDays() {
        return salaryDays;
    }

    public void setSalaryDays(int salaryDays) {
        this.salaryDays = salaryDays;
    }

    public int getDailyNumber() {
        return dailyNumber;
    }

    public void setDailyNumber(int dailyNumber) {
        this.dailyNumber = dailyNumber;
    }

    public int getLuckyNumber() {
        return luckyNumber;
    }

    public void setLuckyNumber(int luckyNumber) {
        this.luckyNumber = luckyNumber;
    }

}
