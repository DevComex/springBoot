/**
 * ---------------------------------------------------
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞
 * 联系方式：wangyufei@gyyx.cn
 * 创建时间：2015年3月20日下午3:32:49
 * 版本号：v1.0
 * 本类主要用途叙述：处理用户相关请求，包括登陆和注册
 */

package cn.gyyx.action.dao.newlottery;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.lottery.UserInfoBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.NewPageBean;

public interface IUserLotteryInfoNew {
    /**
     * 得到活动的所有中奖信息
     *
     * @param actionCode
     * @return
     */
    public List<UserInfoBean> getAllUserLotteryInfo(
            @Param("actionCode") int actionCode);

    /**
     * 增加一条中奖信息
     *
     * @param userInfoBean
     */
    public int addInfo(UserInfoBean userInfoBean);

    /**
     * 获得用户的中奖信息
     *
     * @param actionCode
     * @param userCode
     * @return List<UserInfoBean>
     */
    public List<UserInfoBean> getUserLotteryInfoByUserCode(
            @Param("actionCode") int actionCode,
            @Param("userAccount") String userAccount);

    /**
     * 获得用户的中奖信息(显示虚拟物品)
     *
     * @param actionCode
     * @param userCode
     * @return List<UserInfoBean>
     */
    public List<UserInfoBean> wishGetUserLotteryInfoByUserCode(
            @Param("actionCode") int actionCode,
            @Param("userAccount") String userAccount);

    /**
     * 得到该奖品的个数
     *
     * @param actionCode
     * @param prizeChinese
     * @return
     */
    public int getNumOf(@Param("actionCode") int actionCode,
                        @Param("prizeChinese") String prizeChinese);

    public Integer selectLogByDay(String date);

    public List<UserInfoBean> getLogByInfo(NewPageBean newPageBean);

    public Integer getCountByInfo(NewPageBean newPageBean);

    /**
     * 某个人在某个活动获得某个奖
     */
    public List<UserInfoBean> getOneInActionOnePrizeInfo(
            @Param("actionCode") int actionCode,
            @Param("userAccount") String userAccount,
            @Param("prizeChinese") String prizeChinese);

    /**
     * 某个人在某个活动获得有效奖或无效奖或其他
     */
    public List<UserInfoBean> getAvailableByUserSqlsession(@Param("actionCode") int actionCode, @Param("userAccount") String userAccount, @Param("available") int available);

    public List<String> getPresentNamesByUserIP(@Param("actionCode") int actionCode, @Param("ip") String ip);
    
    /**
     * 更新发送日志信息 yangteng
     * @param userInfoBean
     * @return
     */
    public Integer updateSendPresentLog(UserInfoBean userInfoBean);
}
