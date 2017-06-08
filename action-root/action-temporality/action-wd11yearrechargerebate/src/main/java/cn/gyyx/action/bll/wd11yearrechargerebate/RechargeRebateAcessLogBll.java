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
package cn.gyyx.action.bll.wd11yearrechargerebate;

import cn.gyyx.action.beans.wd11yearrechargerebate.RechargerebateAcessLogWithBLOBs;
import cn.gyyx.action.dao.wd11yearrechargerebate.RechargeRebateAcessLogDao;


/**
 * <p>
 * 查询Bll
 * </p>
 * 
 * @author lihu
 * @since 0.0.1
 */
public class RechargeRebateAcessLogBll {
    private RechargeRebateAcessLogDao  acessLog = new RechargeRebateAcessLogDao();

    public int instert(RechargerebateAcessLogWithBLOBs accessLogBean) {
        return acessLog.insert(accessLogBean);
        
    }

    public int update(RechargerebateAcessLogWithBLOBs accessLogBean) {
        if(accessLogBean.getResult() != null && accessLogBean.getResult().length() >400){
            accessLogBean.setResult(accessLogBean.getResult().substring(0,400));
        }
        if(accessLogBean.getBindappResult() != null && accessLogBean.getBindappResult().length() >400){
            accessLogBean.setBindappResult(accessLogBean.getBindappResult().substring(0,400));
        }
        if(accessLogBean.getHistoryRechargeResult() != null && accessLogBean.getHistoryRechargeResult().length() >400){
            accessLogBean.setHistoryRechargeResult(accessLogBean.getHistoryRechargeResult().substring(0,400));
        }
        if(accessLogBean.getLevel60Result() != null && accessLogBean.getLevel60Result().length() >400){
            accessLogBean.setLevel60Result(accessLogBean.getLevel60Result().substring(0,400));
        }
        if(accessLogBean.getRecharge10Result() != null && accessLogBean.getRecharge10Result().length() >400){
            accessLogBean.setRecharge10Result(accessLogBean.getRecharge10Result().substring(0,400));
        }
        return acessLog.update(accessLogBean);
    }
    
    

}
