package cn.gyyx.action.dao.wdpkforecast;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.lottery.QualificationBean;
import cn.gyyx.action.beans.wdpkforecast.WdPkRoleBindBean;
import cn.gyyx.action.beans.wdpkforecast.WdPkVoteLogBean;
import cn.gyyx.action.beans.wdpkforecast.WdPkVoteTeamsBean;
import cn.gyyx.action.beans.wdpkforecast.WdVoteOpportunityBean;

public interface IWdPkForeCastMapper {
    // 插入角色绑定信息
    public void insertWdPkRoleBindBean(WdPkRoleBindBean wdPkRoleBindBean);

    public WdPkRoleBindBean selectWdPkRoleBindBeanByCode(
            @Param("code") int code);

    // 查询绑定信息BY用户名
    public WdPkRoleBindBean selectWdPkRoleBindBeanByAccount(
            @Param("account") String account);

    // 查询绑定信息BY用户名and 活动编号
    public WdPkRoleBindBean selectRoleBindInfoByAccount(
            @Param("account") String account,
            @Param("actionCode") int actionCode);

    public void updateWdPkRoleBindBean(WdPkRoleBindBean wdPkRoleBindBean);

    // 插入投票日志
    public void insertVoteLog(WdPkVoteLogBean wdPkVoteLogBean);

    // 查询投票日志BY用户id
    public WdPkVoteLogBean selectAllVoteLogByuserId(@Param("userId") int userId,
            @Param("actionCode") int actionCode);

    // 查询当前阶段投票日志 By用户id 和 阶段

    public WdPkVoteLogBean selectVoteLogByuserIdAndType(
            @Param("userId") int userId, @Param("actionCode") int actionCode,
            @Param("type") int type);

    // 查询可投票目标 BY 阶段

    // public WdPkVoteTeamsBean selectVoteTeamsByType(@Param("actionCode")int
    // actionCode,@Param("type")int type);

    // 查询用户 投中/预测中 的次数 或 未投中次数;
    public int selectVoteWinTimesByTypeAndUserId(@Param("userId") int userId,
            @Param("actionCode") int actionCode, @Param("win") int win);

    // 插入用户投票机会
    public void insertVoteOpportunity(
            WdVoteOpportunityBean wdVoteOpportunityBean);

    // 查询用户现有票数

    public Integer selectVoteTimesByUserIdAndType(@Param("userId") int userId,
            @Param("actionCode") int actionCode, @Param("type") int type);

    // 查询用户BEAN VoteOpportunityBean

    public WdVoteOpportunityBean selectVoteOpportunityBeanByUserIdAndType(
            @Param("userId") int userId, @Param("actionCode") int actionCode,
            @Param("type") int type);

    // 减少用户投票次数

    public void deduceVoteTimes(WdVoteOpportunityBean wdVoteOpportunityBean);

    // 查询用户投票情况

    public List<WdPkVoteLogBean> selectVoteStatus(
            @Param("account") String account);

    // 查询用户投了几个区组

    public Integer selectVoteExistedTimes(@Param("userId") int userId,
            @Param("type") int type);

    // 查询预测区组队伍信息
    public List<WdPkVoteTeamsBean> selectVoteTeamByType(
            @Param("type") int type);

    // 查询用户大奖池抽奖机会
    public List<QualificationBean> selectBiglotteryByTypeAndWin(
            WdPkVoteLogBean wdPkVoteLogBean);

    // 查询用户小奖池抽奖机会
    public List<QualificationBean> selectSamllLotteryStatusByTypeAndWin(
            @Param("win") int win, @Param("actionCode") int actionCode,
            @Param("type") int type);

    public List<WdPkVoteLogBean> selectVoteExisted(
            WdPkVoteLogBean wdPkVoteLogBean);

    // 更新用户抽奖机会

    public void updatelotteryTime(@Param("lottery_time") int lottery_time,
            @Param("user_code") int user_code,
            @Param("action_code") int action_code);

    // 初始化用户抽奖机会

    public void insertLottery(@Param("lottery_time") int lottery_time,
            @Param("user_code") int user_code,
            @Param("action_code") int action_code);

    // 查询抽奖机会

    public QualificationBean selectLottery(@Param("user_code") int user_code,
            @Param("action_code") int action_code);

    public WdPkRoleBindBean selectWdPkRoleBindBeanByRoleName(
            @Param("roleName") String roleName);

    // 更新绑定关系
    public void updateBind(WdPkRoleBindBean wdPkRoleBindBean);

    /**
     * 
     * <p>
     * 获取绑定信息by account or roleName，微信大转盘增加(其他想调用请先查看sql语句)
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
    public WdPkRoleBindBean selectByAccountOrRoleName(
            @Param("actionCode") int actionCode,
            @Param("account") String account,
            @Param("roleName") String roleName);
}
