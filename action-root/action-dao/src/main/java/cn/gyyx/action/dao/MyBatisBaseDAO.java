package cn.gyyx.action.dao;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.TransactionIsolationLevel;
import org.slf4j.Logger;

import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
  * <p>
  *   MyBatis操作数据基类
  * </p>
  *  
  * @author GYYX-DEV
  * @since 0.0.1
  */
public class MyBatisBaseDAO {

    protected static Logger logger = GYYXLoggerFactory
            .getLogger(MyBatisBaseDAO.class);

    /**
      * <p>
      *    获取操作数据库的sqlSession。
      *    注意： 默认是事务不自动提交的
      * </p>
      *
      * @action
      *    bozhencheng 2017年3月4日 下午2:40:48 添加描述
      *
      * @return SqlSession
      */
    public SqlSession getSession() {
        SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
                .getSqlActionDBV2SessionFactory();
        logger.debug("Open a mybatis sqlsession with no auto-commit.");
        return sqlSessionFactory.openSession();
    }
    
    /**
      * <p>
      *    获取操作数据库的sqlSession。
      *    注意： false时为事务不自动提交，true时是事务自动提交
      * </p>
      *
      * @action
      *    bozhencheng 2017年3月4日 下午2:41:33 添加新的操作方法
      *
      * @param autoCommit 默认为false, 设置为true时事务自动提交；
      * @return SqlSession
      */
    public SqlSession getSession(boolean autoCommit){
        SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
                .getSqlActionDBV2SessionFactory();
        logger.debug("Open a mybatis sqlsession with auto-commit configurable.");
        return sqlSessionFactory.openSession(autoCommit);
    }
    
    
    /**
      * <p>
      *    获取操作数据库的sqlSession，默认为非自动提交
      *    可以定义level的级别：NONE, READ_UNCOMMITTED, READ_COMMITTED, REPEATABLE_READ, SERIALIZABLE
      * </p>
      *
      * @action
      *    bozhencheng 2017年3月4日 下午2:49:11 添加
      *
      * @param level
      * @return SqlSession
      */
    public SqlSession getSession(TransactionIsolationLevel level) {
        SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
                .getSqlActionDBV2SessionFactory();
        logger.debug("Open a mybatis sqlsession with no auto-commit and TransactionIsolationLevel configurable.");
        return sqlSessionFactory.openSession(level);
    }
    
    /**
     * <p>
     *    获取操作数据库的sqlSession，默认为事务非自动提交
     *    execType有三种类型：
     *       ExecutorType.SIMPLE:  每次都创建新的PreparedStatements
     *       ExecutorType.REUSE:   重用PreparedStatements
     *       ExecutorType.BATCH:   可以用来执行批量的操作
     *       
     *    level为事务的隔离界别，具体见如下链接：
     *       NONE, READ_UNCOMMITTED, READ_COMMITTED, REPEATABLE_READ, SERIALIZABLE   
     * </p>
     *
     * @action
     *    bozhencheng 2017年3月4日 下午2:49:11 添加
     * 
     * @see http://git.gyyx.cn/event/cn-gyyx-action/wikis/KnowledgeDb/lock
     * @param execType 指定操作的类型，有三种类型
     * @param level 级别如上
     * @return SqlSession
     */
    public SqlSession getSession(ExecutorType execType,TransactionIsolationLevel level){
        SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
                .getSqlActionDBV2SessionFactory();
        logger.debug("Open a mybatis sqlsession with no auto-commit and TransactionIsolationLevel/ExecurtorType configurable .");
        return sqlSessionFactory.openSession(execType, level);
    }
    
    /**
     * <p>
     *    获取操作数据库的sqlSession，默认为事务非自动提交
     *    execType有三种类型：
     *       ExecutorType.SIMPLE:  每次都创建新的PreparedStatements
     *       ExecutorType.REUSE:   重用PreparedStatements
     *       ExecutorType.BATCH:   可以用来执行批量的操作
     *       
     * </p>
     *
     * @action
     *    bozhencheng 2017年3月4日 下午2:49:11 添加
     * 
     * @see http://git.gyyx.cn/event/cn-gyyx-action/wikis/KnowledgeDb/lock
     * @param execType 指定操作的类型，有三种类型
     * @return SqlSession
     */
    public SqlSession getSession(ExecutorType execType){
        SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
                .getSqlActionDBV2SessionFactory();
        logger.debug("Open a mybatis sqlsession with no auto-commit and ExecurtorType configurable .");
        return sqlSessionFactory.openSession(execType);
    }
    
    /**
      * <p>
     *    获取操作数据库的sqlSession。
     *    execType有三种类型：
     *       ExecutorType.SIMPLE:  每次都创建新的PreparedStatements
     *       ExecutorType.REUSE:   重用PreparedStatements
     *       ExecutorType.BATCH:   可以用来执行批量的操作
      * </p>
      *
      * @action
      *    bozhencheg 2017年3月4日 下午3:14:13 添加
      *
      * @param execType
      * @param autoCommit 是否自动提交事务，false为不自动提交事务, true为自动提交事务
      * @return SqlSession
      */
    public SqlSession getSession(ExecutorType execType, boolean autoCommit){
        SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
                .getSqlActionDBV2SessionFactory();
        logger.debug("Open a mybatis sqlsession with auto-commit/ExecurtorType configurable .");
        return sqlSessionFactory.openSession(execType, autoCommit);
    }

}
