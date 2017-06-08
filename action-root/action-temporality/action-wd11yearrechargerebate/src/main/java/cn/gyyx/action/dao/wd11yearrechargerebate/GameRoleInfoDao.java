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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;

import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * <p>
 * 游戏角色信息Dao
 * </p>
 * 
 * @author chenglong
 * @since 0.0.1
 */
public class GameRoleInfoDao {
    private static final Logger logger = GYYXLoggerFactory
            .getLogger(GameRoleInfoDao.class);
    
    private static BasicDataSource dataSource; 
    
    /**
     * 
      * <p>
      *    数据库链接
      * </p>
      *
      * @action
      *    chenglong 2017年3月30日 下午3:37:21 数据库链接
      *
      * @param jdbcClassDriverName
      * @param jdbcUrl
      * @param jdbcUsName
      * @param jdbcPassword
      * @return DataSource
     */
    public synchronized void initDataSource(String jdbcClassDriverName,
            String jdbcUrl, String jdbcUsName, String jdbcPassword) {
        if(dataSource == null || !dataSource.getUrl().equals(jdbcUrl)){
            dataSource = new BasicDataSource();
            dataSource.setDriverClassName(jdbcClassDriverName);
            dataSource.setUrl(jdbcUrl);
            dataSource.setUsername(jdbcUsName);
            dataSource.setPassword(jdbcPassword);
            
            dataSource.setMaxActive(300);
            dataSource.setMinIdle(100);
            dataSource.setMaxWait(2000);
        }
    }

    /**
      * <p>
      *    获取游戏等级
      * </p>
      *
      * @action
      *    chenglong 2017年3月30日 下午3:17:46 描述
      *
      * @param account
      * @return int
      */
    public int getMaxLevel(String account) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "select max(level) as level from char_info where account = ? ";
        int level = 0;
        try{
            conn = dataSource.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, account);
            rs = pst.executeQuery();  
            while(rs.next()){
                level = rs.getInt("level");
            }
            rs.close();
            pst.close();
            conn.close();
        }catch(Exception e){
            logger.error("连接获取游戏角色信息库获取jdbc角色等级失败",e);
            level = -1;
        }finally{
            this.close(conn,pst,rs);
        }
        return level;
    }
    
    /**
     * <p>
     *    close
     * </p>
     *
     * @action
     *    chenglong 2017年3月30日 下午3:17:46 描述
     *
     * @param conn
     * @param pst
     * @param rs
     */
    private void close(Connection conn,PreparedStatement pst,ResultSet rs){
        try {
            if(rs != null){
                rs.close();
            }
            if(pst != null){
                pst.close();
            }
            if(conn != null){
                conn.close();
            }
        } catch (SQLException e) {
            logger.error("连接获取游戏角色信息库关闭连接失败",e);
        }
    }

}
