package cn.gyyx.action.dao.wdtombthreeteam;

import cn.gyyx.action.beans.wdtombthreeteam.TombServeyInfoBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

/**
 * 调查问卷数据访问层
 * Created by DerCg on 2016/8/30.
 */
public class TombServeyInfoDAO {
    SqlSessionFactory factory = MyBatisConnectionFactory.getSqlActionDBV2SessionFactory();

    /**
     * 添加问卷调查信息
     * @param serveyInfoBean
     * @return
     */
    public int addServeyInfoBean(TombServeyInfoBean serveyInfoBean) {
        try (SqlSession session = factory.openSession()) {
            TombIServeyInfoBean mapper = session.getMapper(TombIServeyInfoBean.class);
            int count = mapper.addServeyInfoBean(serveyInfoBean);
            session.commit();
            return count;
        }
    }

    /**
     * 获取调查问卷信息
     * @param reserveCode
     * @return
     */
    public TombServeyInfoBean selectByReserveCode(int reserveCode){
        try(SqlSession session = factory.openSession()){
            TombIServeyInfoBean mapper = session.getMapper(TombIServeyInfoBean.class);
            return mapper.selectByReserveCode(reserveCode);
        }
    }
}
