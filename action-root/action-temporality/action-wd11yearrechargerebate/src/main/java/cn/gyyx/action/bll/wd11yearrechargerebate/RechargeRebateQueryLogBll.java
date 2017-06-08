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

import java.util.Date;

import cn.gyyx.action.beans.wd11yearrechargerebate.RechargeRebateQueryLogBean;
import cn.gyyx.action.dao.wd11yearrechargerebate.RechargeRebateQueryLogDao;


/**
 * <p>
 * 查询Bll
 * </p>
 * 
 * @author chenglong
 * @since 0.0.1
 */
public class RechargeRebateQueryLogBll {
    private RechargeRebateQueryLogDao rechargeRebateQueryLogDao = new RechargeRebateQueryLogDao();
    
    /**
     * 
      * <p>
      *    插入
      * </p>
      *
      * @action
      *    chenglong 2017年3月30日 上午11:09:44 插入
      *
      * @param account 账号
      * @param userId 用户ID
      * @param historyRechargeTotal 累计充值金额
      * @return int
     */
    public int insert(String account, int userId,
            int historyRechargeTotal) {
        RechargeRebateQueryLogBean bean = new RechargeRebateQueryLogBean();
        bean.setCreateTime(new Date());
        bean.setAccount(account);
        bean.setUserId(userId);
        bean.setHistoryRechargeTotal(historyRechargeTotal);
        return rechargeRebateQueryLogDao.insert(bean);
    }

}
