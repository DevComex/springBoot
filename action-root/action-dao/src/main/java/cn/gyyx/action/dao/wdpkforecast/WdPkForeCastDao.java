package cn.gyyx.action.dao.wdpkforecast;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.lottery.QualificationBean;
import cn.gyyx.action.beans.wdpkforecast.WdPkRoleBindBean;
import cn.gyyx.action.beans.wdpkforecast.WdPkVoteLogBean;
import cn.gyyx.action.beans.wdpkforecast.WdPkVoteTeamsBean;
import cn.gyyx.action.beans.wdpkforecast.WdVoteOpportunityBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class WdPkForeCastDao {
    private static final Logger logger = GYYXLoggerFactory
            .getLogger(WdPkForeCastDao.class);
    SqlSessionFactory factory = MyBatisConnectionFactory
            .getSqlActionDBV2SessionFactory();

    public void insertWdPkRoleBindBean(WdPkRoleBindBean wdPkRoleBindBean) {
        try (SqlSession session = factory.openSession(true)) {
            IWdPkForeCastMapper mapper = session
                    .getMapper(IWdPkForeCastMapper.class);
            mapper.insertWdPkRoleBindBean(wdPkRoleBindBean);
        }
    }

    public WdPkRoleBindBean selectWdPkRoleBindBeanByCode(int code) {
        try (SqlSession session = factory.openSession()) {
            IWdPkForeCastMapper mapper = session
                    .getMapper(IWdPkForeCastMapper.class);
            return mapper.selectWdPkRoleBindBeanByCode(code);
        }
    }

    public WdPkRoleBindBean selectWdPkRoleBindBeanByAccount(String account) {
        try (SqlSession session = factory.openSession()) {
            IWdPkForeCastMapper mapper = session
                    .getMapper(IWdPkForeCastMapper.class);
            return mapper.selectWdPkRoleBindBeanByAccount(account);
        }
    }

    public WdPkRoleBindBean selectRoleBindInfoByAccount(String account,
            int actionCode) {
        try (SqlSession session = factory.openSession(true)) {
            IWdPkForeCastMapper mapper = session
                    .getMapper(IWdPkForeCastMapper.class);
            return mapper.selectRoleBindInfoByAccount(account, actionCode);
        }
    }

    public void updateWdPkRoleBindBean(WdPkRoleBindBean wdPkRoleBindBean) {
        try (SqlSession session = factory.openSession()) {
            IWdPkForeCastMapper mapper = session
                    .getMapper(IWdPkForeCastMapper.class);
            mapper.updateWdPkRoleBindBean(wdPkRoleBindBean);
            session.commit();
        }
    }

    // 预测功能

    public void insertVoteLog(WdPkVoteLogBean wdPkVoteLogBean,
            SqlSession sqlSession) {
        IWdPkForeCastMapper mapper = sqlSession
                .getMapper(IWdPkForeCastMapper.class);
        mapper.insertVoteLog(wdPkVoteLogBean);
        sqlSession.commit();
    }

    public WdPkVoteLogBean selectAllVoteLogByuserId(int userId,
            int actionCode) {
        try (SqlSession session = factory.openSession()) {
            IWdPkForeCastMapper mapper = session
                    .getMapper(IWdPkForeCastMapper.class);
            return mapper.selectAllVoteLogByuserId(userId, actionCode);

        }
    }

    public WdPkVoteLogBean selectVoteLogByuserIdAndType(int userId,
            int actionCode, int type) {
        try (SqlSession session = factory.openSession()) {
            IWdPkForeCastMapper mapper = session
                    .getMapper(IWdPkForeCastMapper.class);
            return mapper.selectVoteLogByuserIdAndType(userId, actionCode,
                type);

        }
    }

    public int selectVoteWinTimesByTypeAndUserId(int userId, int actionCode,
            int win) {
        try (SqlSession session = factory.openSession()) {
            IWdPkForeCastMapper mapper = session
                    .getMapper(IWdPkForeCastMapper.class);
            return mapper.selectVoteWinTimesByTypeAndUserId(userId, actionCode,
                win);

        }
    }

    // 操作投票机会

    public void insertVoteOpportunity(
            WdVoteOpportunityBean wdVoteOpportunityBean) {
        try (SqlSession session = factory.openSession()) {
            IWdPkForeCastMapper mapper = session
                    .getMapper(IWdPkForeCastMapper.class);
            mapper.insertVoteOpportunity(wdVoteOpportunityBean);
            session.commit();
        }
    }

    public Integer selectVoteTimesByUserIdAndType(int userId, int actionCode,
            int type) {

        try (SqlSession session = factory.openSession()) {
            IWdPkForeCastMapper mapper = session
                    .getMapper(IWdPkForeCastMapper.class);
            return mapper.selectVoteTimesByUserIdAndType(userId, actionCode,
                type);
        }

    }

    public WdVoteOpportunityBean selectVoteOpportunityBeanByUserIdAndType(
            int userId, int actionCode, int type) {

        try (SqlSession session = factory.openSession()) {
            IWdPkForeCastMapper mapper = session
                    .getMapper(IWdPkForeCastMapper.class);
            return mapper.selectVoteOpportunityBeanByUserIdAndType(userId,
                actionCode, type);
        }

    }

    public void reduceVoteTimes(WdVoteOpportunityBean wdVoteOpportunityBean,
            SqlSession sqlSession) {

        IWdPkForeCastMapper mapper = sqlSession
                .getMapper(IWdPkForeCastMapper.class);
        mapper.deduceVoteTimes(wdVoteOpportunityBean);
        sqlSession.commit();

    }

    public List<WdPkVoteLogBean> selectVoteStatus(String account) {

        try (SqlSession session = factory.openSession()) {
            IWdPkForeCastMapper mapper = session
                    .getMapper(IWdPkForeCastMapper.class);
            return mapper.selectVoteStatus(account);
        }
    }

    public Integer selectVoteExistedTimes(int userId, int type) {

        try (SqlSession session = factory.openSession()) {
            IWdPkForeCastMapper mapper = session
                    .getMapper(IWdPkForeCastMapper.class);
            return mapper.selectVoteExistedTimes(userId, type);
        }
    }

    public List<WdPkVoteLogBean> selectVoteExisted(
            WdPkVoteLogBean wdPkVoteLogBean) {

        try (SqlSession session = factory.openSession()) {
            IWdPkForeCastMapper mapper = session
                    .getMapper(IWdPkForeCastMapper.class);
            return mapper.selectVoteExisted(wdPkVoteLogBean);
        }

    }

    public List<WdPkVoteTeamsBean> selectVoteTeamByType(int type) {

        try (SqlSession session = factory.openSession()) {
            IWdPkForeCastMapper mapper = session
                    .getMapper(IWdPkForeCastMapper.class);
            return mapper.selectVoteTeamByType(type);
        }

    }

    public List<QualificationBean> selectBiglotteryByTypeAndWin(
            WdPkVoteLogBean wdPkVoteLogBean) {

        try (SqlSession session = factory.openSession()) {
            IWdPkForeCastMapper mapper = session
                    .getMapper(IWdPkForeCastMapper.class);
            return mapper.selectBiglotteryByTypeAndWin(wdPkVoteLogBean);
        }
    }

    public List<QualificationBean> selectSamllLotteryStatusByTypeAndWin(
            int actionCode, int type, int win) {

        try (SqlSession session = factory.openSession()) {
            IWdPkForeCastMapper mapper = session
                    .getMapper(IWdPkForeCastMapper.class);
            return mapper.selectSamllLotteryStatusByTypeAndWin(actionCode, type,
                win);
        }
    }

    public void insertLottery(int lottery_time, int user_code,
            int action_code) {
        try (SqlSession session = factory.openSession()) {
            IWdPkForeCastMapper mapper = session
                    .getMapper(IWdPkForeCastMapper.class);
            mapper.insertLottery(lottery_time, user_code, action_code);
            session.commit();
        }
    }

    public QualificationBean selectLottery(int user_code, int action_code) {

        try (SqlSession session = factory.openSession()) {
            IWdPkForeCastMapper mapper = session
                    .getMapper(IWdPkForeCastMapper.class);
            return mapper.selectLottery(user_code, action_code);
        }

    }

    public void updatelotteryTime(int lottery_time, int user_code,
            int action_code) {
        try (SqlSession session = factory.openSession(true)) {
            IWdPkForeCastMapper mapper = session
                    .getMapper(IWdPkForeCastMapper.class);
            mapper.updatelotteryTime(lottery_time, user_code, action_code);
        }

    }

    public WdPkRoleBindBean getWdPkRoleBindBeanByRoleName(String roleName) {
        try (SqlSession session = factory.openSession()) {
            IWdPkForeCastMapper mapper = session
                    .getMapper(IWdPkForeCastMapper.class);
            return mapper.selectWdPkRoleBindBeanByRoleName(roleName);
        }
    }

    public void updateBind(WdPkRoleBindBean wdPkRoleBindBean) {
        try (SqlSession session = factory.openSession(true)) {
            IWdPkForeCastMapper mapper = session
                    .getMapper(IWdPkForeCastMapper.class);
            mapper.updateBind(wdPkRoleBindBean);
        }

    }

    /**
     * 
     * <p>
     * 获取绑定信息by account or roleName，微信大转盘增加
     * </p>
     *
     * @action caishuai 2017年3月20日 下午12:43:33 描述
     *
     * @param actionCode
     *            活动code
     * @param account
     *            账户（在微信大转盘中特指openid）
     * @param roleName
     *            角色名（在微信大转盘中特指account）
     * @return WdPkRoleBindBean 绑定角色信息
     */
    public WdPkRoleBindBean selectByAccountOrRoleName(int actionCode,
            String account, String roleName) {
        try (SqlSession session = factory.openSession()) {
            IWdPkForeCastMapper mapper = session
                    .getMapper(IWdPkForeCastMapper.class);
            return mapper.selectByAccountOrRoleName(actionCode, account,
                roleName);
        }

    }

}
