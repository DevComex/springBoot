/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 *
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action-root
 * @作者：changlu
 * @联系方式：changlu@gyyx.cn
 * @创建时间：2017/3/20 14:12
 * @版本号：0.0.1 -------------------------------------------------------------------------
 */
package cn.gyyx.action.dao;

import cn.gyyx.core.prop.PropertiesParser;
import cn.gyyx.core.prop.SimpleProperties;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

/**
 * <p>
 * 描述:MyBatisMySQLConnectionFactory读取配置文件并初始化MySql SqlSessionFactory。
 * </p>
 *
 * @author changlu
 * @since 0.0.1
 */
public class MyBatisMySQLConnectionFactory {
    private static SqlSessionFactory sqlActivityDBMySqlSessionFactory;

    private MyBatisMySQLConnectionFactory() {
        try {
            String resource = "config/mybatis-config.xml";
            String dataSql = SimpleProperties.getInstance()
                    .getStringProperty("database.path");
            Properties dataSqlProperties = new PropertiesParser(dataSql)
                    .getProperties();
            Reader reader = Resources.getResourceAsReader(resource);
            if (sqlActivityDBMySqlSessionFactory == null) {
                sqlActivityDBMySqlSessionFactory = new SqlSessionFactoryBuilder()
                        .build(reader, "activity_db", dataSqlProperties);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SqlSessionFactory getSqlActivityDBSessionFactory() {
        if (sqlActivityDBMySqlSessionFactory == null) {
            new MyBatisMySQLConnectionFactory();
        }
        return sqlActivityDBMySqlSessionFactory;
    }
}
