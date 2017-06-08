/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 *
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：
 * @作者：tanjunkai        
 * @联系方式：tanjunkai@gyyx.cn
 * @创建时间：2017/3/13 10:48
 * @版本号：0.0.1 -------------------------------------------------------------------------
 */
package cn.gyyx.action.bll.wechatvideo;

import cn.gyyx.action.beans.wechatvideo.WeChatVideoRoleBindBean;
import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.dao.wechatvideo.WeChatVideoRoleBindDao;

/**
 * <p>
 * 角色绑定业务逻辑层
 * </p>
 * 
 * @author tanjunkai
 * @since 0.0.1
 */
public class WeChatVideoRoleBindBll {

    private WeChatVideoRoleBindDao weChatVideoRoleBind = new WeChatVideoRoleBindDao();

    /**
     * <p>
     * 角色绑定
     * </p>
     *
     * @author tanjunkai
     *
     * @param info
     *            角色绑定实体
     * @return ResultBean
     */
    public ResultBean roleBind(WeChatVideoRoleBindBean info) {
        ResultBean resultBean = new ResultBean<>();

        WeChatVideoRoleBindBean resInfo = weChatVideoRoleBind
                .selectById(info.getUserId(), info.getRoleId());
        if (resInfo != null) {
            resultBean.setIsSuccess(false);
            resultBean.setMessage("账号或角色已被绑定,请选择其他账号或角色");
            return resultBean;
        }
        int result = weChatVideoRoleBind.addRoleBind(info);
        if (result > 0) {
            resultBean.setIsSuccess(true);
            resultBean.setMessage("绑定成功");
        } else {
            resultBean.setIsSuccess(false);
            resultBean.setMessage("绑定失败");
        }
        return resultBean;
    }

    /**
     * <p>
     * 获取账号绑定信息
     * </p>
     *
     * @action Administrator 2017年3月17日 上午11:12:31 描述
     *
     * @param userId
     *            用户ID
     * @return WeChatVideoRoleBindBean 用户角色绑定实体
     */
    public WeChatVideoRoleBindBean getUserBindRoleByOpenId(String openId) {
        return weChatVideoRoleBind.selectByOpenId(openId);
    }

    /**
     * <p>
     * 更新角色绑定信息
     * </p>
     *
     * @action tanjunkai 2017年3月17日 下午5:58:22 描述
     *
     * @param record
     *            角色绑定信息
     * @return int 影响的行数
     */
    public int updateByPrimaryKey(WeChatVideoRoleBindBean record) {
        return weChatVideoRoleBind.updateByPrimaryKey(record);
    }

    /**
     * <p>
     * 增加抽奖机会
     * </p>
     *
     * @action tanjunkai 2017年3月29日 下午6:32:27 描述
     *
     * @param userId
     * @return int
     */
    public int addLotteryTimes(int userId) {
        return weChatVideoRoleBind.addLotteryTimes(userId);
    }

    /**
     * <p>
     * 减少用户抽奖机会
     * </p>
     *
     * @action tanjunkai 2017年3月29日 下午6:32:27 描述
     *
     * @param userId
     * @return int
     */
    public int reduceLotteryTimes(int userId) {
        return weChatVideoRoleBind.reduceLotteryTimes(userId);
    }
}
