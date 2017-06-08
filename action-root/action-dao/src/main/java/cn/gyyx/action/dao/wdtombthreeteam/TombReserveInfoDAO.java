package cn.gyyx.action.dao.wdtombthreeteam;

import cn.gyyx.action.beans.wdtombthreeteam.TombReserveInfoBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

/**
 * 预约数据访问层
 * Created by DerCg on 2016/8/30.
 */
public class TombReserveInfoDAO {
    SqlSessionFactory factory = MyBatisConnectionFactory.getSqlActionDBV2SessionFactory();

    /**
     * 添加预约信息
     *
     * @param reserveInfoBean
     * @return
     */
    public int addReserveInfoBean(TombReserveInfoBean reserveInfoBean) {
        try (SqlSession session = factory.openSession()) {
            TombIReserveInfoBean mapper = session.getMapper(TombIReserveInfoBean.class);
            int count= mapper.addReserveInfoBean(reserveInfoBean);
            session.commit();
            return count;
        }
    }

    /**
     * 获取预约信息
     * @param code
     * @return
     */
    public TombReserveInfoBean selectByCode(int code) {
        try (SqlSession session = factory.openSession()) {
            TombIReserveInfoBean mapper = session.getMapper(TombIReserveInfoBean.class);
            return mapper.selectByCode(code);
        }
    }

    /**
     * 获取预约信息
     * @param phoneNum
     * @param channelType
     * @return
     */
    public TombReserveInfoBean selectByPhoneAndActionCode(String phoneNum, int actionCode) {
        try (SqlSession session = factory.openSession()) {
            TombIReserveInfoBean mapper = session.getMapper(TombIReserveInfoBean.class);
            TombReserveInfoBean abc=mapper.selectByPhoneAndActionCode(phoneNum, actionCode);
            return abc;
        }
    }

    /**
     * 更新抽奖状态
     * @param reserveCode
     * @return
     */
    public void updateIsPrizeStatus(int reserveCode) {
        try (SqlSession session = factory.openSession()) {
            TombIReserveInfoBean mapper = session.getMapper(TombIReserveInfoBean.class);
            mapper.updateIsPrizeStatus(reserveCode);
            session.commit();
        }
    }

}
