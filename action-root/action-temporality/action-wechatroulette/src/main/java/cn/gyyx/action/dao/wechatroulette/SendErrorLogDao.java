/**
  * -------------------------------------------------------------------------
  * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
  * @版权所有：北京光宇在线科技有限责任公司
  * @项目名称：action-wechatroulette
  * @作者：caishuai
  * @联系方式：caishuai@gyyx.cn
  * @创建时间：2017年3月21日 下午5:55:56
  * @版本号：0.0.1
  *-------------------------------------------------------------------------
  */
package cn.gyyx.action.dao.wechatroulette;

import org.apache.ibatis.session.SqlSession;

import com.google.common.base.Throwables;

import cn.gyyx.action.beans.lottery.UserInfoBean;
import cn.gyyx.action.dao.MyBatisBaseDAO;

/**
 * <p>
 * SendErrorLogDao描述
 * </p>
 * 
 * @author caishuai
 * @since 0.0.1
 */
public class SendErrorLogDao extends MyBatisBaseDAO {
    /**
     * 
     * <p>
     * 将中奖记录写入错误日志
     * </p>
     *
     * @action caishuai 2017年3月21日 下午5:54:40 描述
     *
     * @param userInfoBean
     * @return boolean
     */
    public boolean addInfo(UserInfoBean userInfoBean) {
        SqlSession session = getSession(false);
        try {
            SendErrorLogMapper mapper = session
                    .getMapper(SendErrorLogMapper.class);
            boolean isSuccess = mapper.addInfo(userInfoBean) > 0;
            if (!isSuccess) {
                session.rollback();
                return isSuccess;
            }
            session.commit();
            return isSuccess;
        } catch (Exception e) {
            logger.warn("InsertSendErrorLogError:{}",
                Throwables.getStackTraceAsString(e));
            session.rollback();
            throw new RuntimeException(e);
        } finally {
            session.close();
        }

    }
}
