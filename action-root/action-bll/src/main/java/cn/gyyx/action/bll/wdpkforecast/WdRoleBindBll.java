package cn.gyyx.action.bll.wdpkforecast;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.po.ActionQualificationPO;
import cn.gyyx.action.beans.wdpkforecast.WdPkRoleBindBean;
import cn.gyyx.action.dao.lottery.impl.ActionQualificationDAOImpl;
import cn.gyyx.action.dao.wdpkforecast.WdPkForeCastDao;

public class WdRoleBindBll {
    private WdPkForeCastDao wdPkForeCastDao = new WdPkForeCastDao();
    private ActionQualificationDAOImpl ad = new ActionQualificationDAOImpl();

    public void insertWdPkRoleBindBean(WdPkRoleBindBean wdPkRoleBindBean) {
        wdPkForeCastDao.insertWdPkRoleBindBean(wdPkRoleBindBean);
    }

    public WdPkRoleBindBean getWdPkRoleBindBeanByAccount(String accountName) {
        return wdPkForeCastDao.selectWdPkRoleBindBeanByAccount(accountName);

    }

    public WdPkRoleBindBean getRoleBindInfoByAccount(String accountName,
            int actionCode) {
        return wdPkForeCastDao.selectRoleBindInfoByAccount(accountName,
            actionCode);

    }

    // 查询抽奖机会
    public ResultBean<ActionQualificationPO> userisexisted(String userId,
            int activityId, String mark) {
        ResultBean<ActionQualificationPO> result = new ResultBean<ActionQualificationPO>();
        ActionQualificationPO po;
        po = ad.getData(userId, activityId);

        if (po == null || po.getRemark().equals(mark)) {
            result.setIsSuccess(true);
            result.setMessage("该用户可以获得抽奖机会");
            return result;
        } else {
            result.setIsSuccess(false);
            result.setMessage("已兑换抽奖机会");
            result.setData(po);
            return result;
        }

    }

    // 计算用户应有的抽奖机会
    public int countlotterytimes(int userId, int actionCode, int win) {

        int Vote = wdPkForeCastDao.selectVoteWinTimesByTypeAndUserId(userId,
            actionCode, win);
        int LOttery = Vote % 5;
        return LOttery;
    }

    public WdPkRoleBindBean getWdPkRoleBindBeanByRoleName(String roleName) {
        return wdPkForeCastDao.getWdPkRoleBindBeanByRoleName(roleName);

    }

    public void updateBind(WdPkRoleBindBean wdPkRoleBindBean) {
        wdPkForeCastDao.updateBind(wdPkRoleBindBean);

    }

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
    public WdPkRoleBindBean getBindByAccountOrRoleName(int actionCode,
            String account, String roleName) {
        return wdPkForeCastDao.selectByAccountOrRoleName(actionCode, account,
            roleName);
    }
}
