/*-------------------------------------------------------------------------
* 版权所有：北京光宇在线科技有限责任公司
* 作者：guohai
* 联系方式：guohai@gyyx.cn
* 创建时间： 2014年12月1日
* 版本号：v1.0
* 本类主要用途描述：
* MyBatis连接产生器
-------------------------------------------------------------------------*/
package cn.gyyx.action.dao;

import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import cn.gyyx.core.prop.PropertiesParser;
import cn.gyyx.core.prop.SimpleProperties;

/**
  * <p>
  *   MyBatisConnectionFactory读取配置文件并初始化SqlSessionFactory。
  * </p>
  *  
  * @author bozhencheng
  * @since 0.0.1
  */
public class MyBatisConnectionFactory {
    private static SqlSessionFactory sqlActionDBV2SessionFactory;

    /**
     * 修改原来的实现为单例模式
     */
    private MyBatisConnectionFactory() {
        try {
            String resource = "config/mybatis-config.xml";
            String dataSql = SimpleProperties.getInstance()
                    .getStringProperty("database.path");
            Properties dataSqlProperties = new PropertiesParser(dataSql)
                    .getProperties();
            Reader reader = Resources.getResourceAsReader(resource);
            if (sqlActionDBV2SessionFactory == null) {
                sqlActionDBV2SessionFactory = new SqlSessionFactoryBuilder()
                        .build(reader, "ActionDBV2", dataSqlProperties);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
      * <p>
      *    静态方法返回SQL实例
      * </p>
      *
      * @action
      *    bozhencheng update 2017年3月4日 下午3:37:01 修改为单例
      *
      * @return SqlSessionFactory
      */
    public static SqlSessionFactory getSqlActionDBV2SessionFactory() {
        if(sqlActionDBV2SessionFactory == null) {
            new MyBatisConnectionFactory();
        }
        return sqlActionDBV2SessionFactory;
    }
}
