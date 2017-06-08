/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action-wdblessingcard2017
 * @作者：laixiancai
 * @联系方式：laixiancai@gyyx.cn
 * @创建时间：2017年3月10日 下午12:10:04
 * @版本号：0.0.1
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.service.wdblessingcard2017.impl;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.vo.LotteryPrizesVO;
import cn.gyyx.action.beans.wdblessingcard2017.Constants;
import cn.gyyx.action.beans.wdblessingcard2017.RoleBindBean;
import cn.gyyx.action.bll.lottery.IActionPrizesAddressBLL;
import cn.gyyx.action.bll.lottery.impl.ActionPrizesAddressBLLImpl;
import cn.gyyx.action.bll.wdblessingcard2017.RoleBindBeanBll;
import cn.gyyx.action.bll.wdblessingcard2017.impl.RoleBindBeanBllImpl;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.action.service.agent.CallWebServiceInsuranceAgent;
import cn.gyyx.action.service.wdblessingcard2017.RoleBindBeanService;
import cn.gyyx.service.WDGameAgent;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * <p>
 * 用户角色信息Service
 * </p>
 * 
 * @author laixiancai
 * @since 0.0.1
 */
public class RoleBindBeanServiceImpl implements RoleBindBeanService {

    RoleBindBeanBll roleBindBeanBll = new RoleBindBeanBllImpl();
    IActionPrizesAddressBLL actionPrizesAddressBll = new ActionPrizesAddressBLLImpl();
    private WDGameAgent gameAgent = new WDGameAgent();
    
    /*
     * (non-Javadoc)
     * 
     * @see cn.gyyx.action.service.wdblessingcard2017.RoleBindBeanService#
     * getRoleBindBeanByAccount(java.lang.String)
     */
    @Override
    public RoleBindBean getRoleBindBeanByAccount(String account) {
        return roleBindBeanBll.getRoleBindBeanByAccount(account);
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.gyyx.action.service.wdblessingcard2017.RoleBindBeanService#
     * insertRoleBindBean(cn.gyyx.action.beans.wdblessingcard2017.RoleBindBean)
     */
    @Override
    public int insertRoleBindBean(RoleBindBean roleBindBean) {
        return roleBindBeanBll.insertRoleBindBean(roleBindBean);
    }

    /**
     * 
     * <p>
     * 玩家前台-玩家玩了拼图游戏后更新玩家的抽奖次数
     * </p>
     *
     * @action laixiancai 2017年3月15日 下午8:57:26 新增
     *
     * @param account
     * @param lotteryTimes
     * @param remainingTimes
     *            void
     */
    public void updateUserLotteryTimesAfterPlayGame(String account,
            int lotteryTimes, int remainingTimes) {
        roleBindBeanBll.updateUserLotteryTimesAfterPlayGame(account,
            lotteryTimes, remainingTimes);
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.gyyx.action.service.wdblessingcard2017.RoleBindBeanService#
     * updateRemainingTimes(cn.gyyx.action.beans.wdblessingcard2017.
     * RoleBindBean)
     */
    @Override
    public int updateRemainingTimes(RoleBindBean roleBind, SqlSession session) {
        return roleBindBeanBll.updateRemainingTimes(roleBind, session);
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.gyyx.action.service.wdblessingcard2017.RoleBindBeanService#
     * getAddressByAccount(java.lang.String)
     */
    @Override
    public LotteryPrizesVO getAddressByAccount(String account, int actionCode,
            int userId) {
        return actionPrizesAddressBll.getAddress(actionCode, userId, account);
    }

    /**
     * <p>
     * 获取操作数据库的sqlSession。 注意： false时为事务不自动提交，true时是事务自动提交
     * </p>
     *
     * @action bozhencheng 2017年3月4日 下午2:41:33 添加新的操作方法
     *
     * @param autoCommit
     *            默认为false, 设置为true时事务自动提交；
     * @return SqlSession
     */
    public SqlSession getSession(boolean autoCommit) {
        SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
                .getSqlActionDBV2SessionFactory();
        return sqlSessionFactory.openSession(autoCommit);
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.gyyx.action.service.wdblessingcard2017.RoleBindBeanService#
     * getRoleBindBeanCount(java.lang.String)
     */
    @Override
    public int getRoleBindBeanCount(String roleId) {
        return roleBindBeanBll.getRoleBindBeanCount(roleId);
    }

    /* (non-Javadoc)
     * @see cn.gyyx.action.service.wdblessingcard2017.RoleBindBeanService#validateRole()
     */
    @Override
    public ResultBean<String> validateRole(String account, int serverCode, String roleId) {
        ResultBean<String> result = new ResultBean<String>();
        // 根据选择的服务器判断是否激活
        cn.gyyx.action.common.beans.ResultBean<Boolean> serverStatus = gameAgent
                .accountIsActivated(Constants.GAMEID,
                    account, serverCode);
        if (serverStatus == null || !serverStatus.getIsSuccess()) {
            result.setIsSuccess(false);
            result.setMessage("该服务器尚未激活!");
            return result;
        }
        // 查询对应服务器的信息
        cn.gyyx.action.common.beans.ResultBean<JSONObject> server = gameAgent
                .getServerByCode(Constants.GAMEID, serverCode);
        if (server == null) {
            result.setProperties(false, "该服务器信息不存在!", null);
            return result;
        }
        // 验证该角色是否在选择的区服中
        String roleInfo = CallWebServiceInsuranceAgent.getRoleInfo(
            account, serverCode);
        JSONObject jsonObj = JSONObject.fromObject(roleInfo);
        if (jsonObj == null || !jsonObj.getBoolean("IsSuccess")) {
            result.setProperties(false, "您在该区没有角色信息！", null);
            return result;
        }
        JSONArray jsonData = jsonObj.getJSONArray("List");
        boolean finded = false;
        for (int i = 0 ;i<jsonData.size();i++){
            if(roleId.equals(jsonData.getJSONObject(i).getString("RoleId"))){
                // 找到角色
                finded = true;
                result.setProperties(true, "匹配到角色信息！", null);
                break;
            }
        }
        if(!finded) {
            result.setProperties(false, "该区服中没有找到您的角色！请重新选择区服！", null);
            return result;
        }
        return result;
    }
}
