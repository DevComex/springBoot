/**
  * -------------------------------------------------------------------------
  * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
  * @版权所有：北京光宇在线科技有限责任公司
  * @项目名称：action-wdrankinglist2017
  * @作者：niushuai
  * @联系方式：niushuai@gyyx.cn
  * @创建时间：2017年4月5日 下午2:50:05
  * @版本号：0.0.1
  *-------------------------------------------------------------------------
  */
package cn.gyyx.action.service.wdrankinglist2017;

import org.apache.ibatis.session.SqlSession;
import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.po.ActionPrizesAddressPO;
import cn.gyyx.action.beans.wdrankinglist2017.RoleBindBean;
import cn.gyyx.core.auth.UserInfo;

/**
 * <p>
 * 账号绑定
 * </p>
 * 
 * @author niushuai
 * @since 0.0.1
 */
public interface RoleBindService {

    /**
     * <p>
     * 查询账号绑定信息
     * </p>
     *
     * @action niushuai 2017年4月5日 下午6:45:02 描述
     *
     * @param account
     * @return RoleBindBean
     */
    RoleBindBean getRoleBindBeanByOpenId(String openId);

    /**
     * <p>
     * 添加账号绑定
     * </p>
     *
     * @action niushuai 2017年4月6日 上午10:15:06 描述
     *
     * @param roleBindBean
     * @return int
     */
    int insertRoleBind(RoleBindBean roleBindBean);

    /**
     * <p>
     * 绑定账号信息
     * </p>
     *
     * @action niushuai 2017年4月6日 下午4:54:00 描述
     *
     * @param roleBind
     *            void
     * @param userInfo
     * @param openId
     */
    ResultBean<String> bindRoleInfo(RoleBindBean roleBind, UserInfo userInfo);

    /**
     * <p>
     * 获取用户的邮寄地址
     * </p>
     *
     * @action niushuai 2017年4月8日 下午1:16:10 描述
     *
     * @param openId
     * @return ResultBean<LotteryPrizesVO>
     */
    ResultBean<ActionPrizesAddressPO> getUserAddress(String openId);

    /**
     * <p>
     * 添加用户的邮寄地址
     * </p>
     *
     * @action niushuai 2017年4月8日 下午1:51:14 描述
     *
     * @param address
     * @param openId
     * @return ResultBean<String>
     */
    ResultBean<String> addAddress(ActionPrizesAddressPO address, String openId);

    /**
     * <p>
     * 获取抽奖次数
     * </p>
     *
     * @action niushuai 2017年4月10日 下午1:44:05 描述
     *
     * @param openId
     * @return ResultBean<Integer>
     */
    ResultBean<Integer> getLotteryTimes(String openId);

    /**
     * 
     * <p>
     * 获取抽奖次数（重载）
     * </p>
     *
     * @action laixiancai 2017年4月11日 上午9:13:14 描述
     *
     * @param openId
     * @param sqlSession
     * @return ResultBean<Integer>
     */
    ResultBean<Integer> getLotteryTimes(String openId, SqlSession sqlSession);

    /**
     * 
     * <p>
     * 根据openId查询玩家的报名情况
     * </p>
     *
     * @action laixiancai 2017年4月11日 上午9:13:11 描述
     *
     * @param openId
     * @return ResultBean<Boolean>
     */
    ResultBean<Boolean> checkApply(String openId);

}
