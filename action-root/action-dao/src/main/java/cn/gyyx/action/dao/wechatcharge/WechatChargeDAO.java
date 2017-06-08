/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action-dao
 * @作者：guoyonggang
 * @联系方式：guoyonggang@gyyx.cn
 * @创建时间：2017年2月27日
 * @版本号：1.0
 * @本类主要用途描述：  
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.wechatcharge;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Throwables;

import cn.gyyx.action.beans.wechatcharge.WechatChargeBean;
import cn.gyyx.action.beans.wechatcharge.WechatChargePaginationBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
/**
 * 
  * <p>
  *   兑换信息数据库操作
  * </p>
  *  
  * @author guoyonggang
  * @since 0.0.1
 */
public class WechatChargeDAO {

    private static final Logger logger = LoggerFactory
            .getLogger(WechatChargeDAO.class);

    private SqlSession getSession(Boolean authoCommit) {
        SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
                .getSqlActionDBV2SessionFactory();
        return sqlSessionFactory.openSession(authoCommit);
    }

    /**
     * 
     * <p>
     * 添加兑换信息
     * </p>
     *
     * @action guoyonggang 后台添加兑换奖品信息
     *
     * @param record
     * @return boolean
     */
    public boolean addWechatChargeInfo(WechatChargeBean record) {
        SqlSession session = getSession(false);
        try {
            IWechatChargeMapper mapper = session
                    .getMapper(IWechatChargeMapper.class);
            boolean isSuccess = mapper.insertSelective(record) > 0;
            if (!isSuccess) {
                session.rollback();
                return isSuccess;
            }
            session.commit();
            return isSuccess;
        } catch (Exception e) {
            logger.warn("addWechatChargeInfoError:{}" ,Throwables.getStackTraceAsString(e));
            session.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    /**
     * 
     * <p>
     * 删除兑换信息
     * </p>
     *
     * @action guoyonggang 删除兑换信息
     *
     * @param record
     * @return boolean
     */
    public boolean delete(Integer code) {
        SqlSession session = getSession(false);
        try {
            IWechatChargeMapper mapper = session
                    .getMapper(IWechatChargeMapper.class);
            boolean isSuccess = mapper.deleteByPrimaryKey(code) > 0;
            if (!isSuccess) {
                session.rollback();
                return isSuccess;
            }
            session.commit();
            return isSuccess;
        } catch (Exception e) {
            logger.warn("deleteError:{}" ,Throwables.getStackTraceAsString(e));
            session.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    /**
     * 
     * <p>
     * 获取兑换信息
     * </p>
     *
     * @action guoyonggang 后台获取兑换信息
     *
     * @param record
     * @return WechatChargeBean
     */
    public WechatChargeBean getWechatChargeInfo(Integer code) {
        SqlSession session = getSession(true);
        try {
            IWechatChargeMapper mapper = session
                    .getMapper(IWechatChargeMapper.class);
            WechatChargeBean chargeInfo = mapper.selectByPrimaryKey(code);
            return chargeInfo;
        } catch (Exception e) {
            logger.warn("getWechatChargeInfoError:{}", Throwables.getStackTraceAsString(e));
            return null;
        } finally {
            session.close();
        }
    }

    /**
      * <p>
      *    修改兑换信息
      * </p>
      *
      * @action
      *    guoyonggang 2017年3月1日 上午9:37:49 描述
      *
      * @param record
      * @return boolean
     */
    public boolean update(WechatChargeBean record) {
        SqlSession session = getSession(false);
        try {
            IWechatChargeMapper mapper = session
                    .getMapper(IWechatChargeMapper.class);
            boolean isSuccess = mapper.updateByPrimaryKeySelective(record) > 0;
            if (!isSuccess) {
                session.rollback();
                return isSuccess;
            }
            session.commit();
            return isSuccess;
        } catch (Exception e) {
            logger.warn("getWechatChargeInfoError:{}", Throwables.getStackTraceAsString(e));
            session.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    /**
     * <p>
     * 分页查询结果
     * </p>
     *
     * @action guoyonggang 2017年2月28日 下午1:13:16 描述
     *
     * @param queryCondition
     * @return List<WechatChargeBean>
     */
    public List<WechatChargeBean> queryWechatChargeList(
            WechatChargePaginationBean queryCondition) {
        SqlSession session = getSession(true);
        try {
            IWechatChargeMapper mapper = session
                    .getMapper(IWechatChargeMapper.class);
            List<WechatChargeBean> list = mapper
                    .queryWechatChargeList(queryCondition);
            return list;
        } catch (Exception e) {
            logger.warn("queryWechatChargeError:{}" ,Throwables.getStackTraceAsString(e));
            return null;
        } finally {
            session.close();
        }
    }

    /**
     * <p>
     * 分页查询总数
     * </p>
     *
     * @action guoyonggang 2017年2月28日 下午1:13:16 描述
     *
     * @param queryCondition
     * @return int
     */
    public int queryWechatChargeCount(
            WechatChargePaginationBean queryCondition) {
        SqlSession session = getSession(true);
        try {
            IWechatChargeMapper mapper = session
                    .getMapper(IWechatChargeMapper.class);
            int count = mapper.queryWechatChargeCount(queryCondition);
            return count;
        } catch (Exception e) {
            logger.warn("queryWechatChargeError:{}" ,Throwables.getStackTraceAsString(e));
            return 0;
        } finally {
            session.close();
        }
    }
    /**
     * <p>
     * 查询要导出数据
     * </p>
     *
     * @action guoyonggang 2017年2月28日 下午1:13:16 描述
     *
     * @param strWhere
     * @return List<WechatChargeBean>
     */
    public List<WechatChargeBean> queryWechatChargeListForExport(String strWhere) {
        SqlSession session = getSession(true);
        try {
            IWechatChargeMapper mapper = session
                    .getMapper(IWechatChargeMapper.class);
            List<WechatChargeBean> list = mapper
                    .queryWechatChargeListForExport(strWhere);
            return list;
        } catch (Exception e) {
            logger.warn("queryWechatChargeListForExportError:{}" ,Throwables.getStackTraceAsString(e));
            return null;
        } finally {
            session.close();
        }
    }
    /**
     * <p>
     * 查询兑换记录
     * </p>
     *
     * @action guoyonggang 2017年2月28日 下午1:13:16 描述
     *
     * @param openId
     * @return List<ChargeResultBean>
     */
    public List<WechatChargeBean> queryWechatChargeListByOpenId(String openId) {
        SqlSession session = getSession(true);
        try {
            IWechatChargeMapper mapper = session
                    .getMapper(IWechatChargeMapper.class);
            List<WechatChargeBean> list = mapper
                    .queryWechatChargeListByOpenId(openId);
            return list;
        } catch (Exception e) {
            logger.warn("queryWechatChargeListByOpenIdError:{}" ,Throwables.getStackTraceAsString(e));
            return null;
        } finally {
            session.close();
        }
    }
    /**
     * <p>
     * 根据兑换码查询兑换信息
     * </p>
     *
     * @action guoyonggang 2017年2月28日 下午1:13:16 描述
     *
     * @param chargeCode
     * @return ChargeResultBean
     */
    public WechatChargeBean queryWechatChargeByChargeCode(String chargeCode) {
        SqlSession session = getSession(true);
        try {
            IWechatChargeMapper mapper = session
                    .getMapper(IWechatChargeMapper.class);
            return mapper.queryWechatChargeByChargeCode(chargeCode);
        } catch (Exception e) {
            logger.warn("queryWechatChargeListByChargeCodeError:{}" ,Throwables.getStackTraceAsString(e));
            return null;
        } finally {
            session.close();
        }
    }
}
