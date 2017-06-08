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

import cn.gyyx.action.dao.wd11yearrechargerebate.GameRoleInfoDao;

/**
 * <p>
 * 游戏角色信息Bll
 * </p>
 * 
 * @author chenglong
 * @since 0.0.1
 */
public class GameRoleInfoBll {
    private GameRoleInfoDao gameRoleInfoDao = new GameRoleInfoDao();

    /**
      * <p>
      *    获取游戏等级
      * </p>
      *
      * @action
      *    chenglong 2017年3月30日 下午3:02:54 获取游戏等级
      *
      * @param jdbcClassDriverName
      * @param jdbcUrl
      * @param jdbcUsName
      * @param jdbcPassword 
      * @param account 账号
      * @return int
      */
    public int getLevel(String jdbcClassDriverName,String jdbcUrl,String jdbcUsName,String jdbcPassword
            ,String account) {
        gameRoleInfoDao.initDataSource(jdbcClassDriverName,jdbcUrl,jdbcUsName,jdbcPassword);
        return gameRoleInfoDao.getMaxLevel(account);
    }
    
    

}
