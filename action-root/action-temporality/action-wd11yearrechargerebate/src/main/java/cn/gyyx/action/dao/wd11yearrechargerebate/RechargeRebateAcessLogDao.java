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
package cn.gyyx.action.dao.wd11yearrechargerebate;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.wd11yearrechargerebate.RechargerebateAcessLogWithBLOBs;
import cn.gyyx.action.dao.MyBatisBaseDAO;

/**
 * <p>
 * 查询日志Dao
 * </p>
 * 
 * @author chenglong
 * @since 0.0.1
 */
public class RechargeRebateAcessLogDao extends MyBatisBaseDAO {

    /**
     * 
      * <p>
      *    插入
      * </p>
      *
      * @action
      *    chenglong 2017年3月30日 上午11:02:30 插入
      *
      * @param bean
      * @return int
     */
    public int insert(RechargerebateAcessLogWithBLOBs bean) {
        int count = 0;
        try (SqlSession session = this.getSession(true)) {
            RechargerebateAcessLogMapper mapper = session
                    .getMapper(RechargerebateAcessLogMapper.class);
            count = mapper.insertSelective(bean) ;
        }
        return count;
    }

    public int update(RechargerebateAcessLogWithBLOBs accessLogBean) {
        int result = 0;
        try (SqlSession session = this.getSession(true)) {
            RechargerebateAcessLogMapper mapper = session
                    .getMapper(RechargerebateAcessLogMapper.class);
            result = mapper.updateByPrimaryKeySelective(accessLogBean) ;
            return result;
        }
    }

    

}
