/**
  * -------------------------------------------------------------------------
  * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
  * @版权所有：北京光宇在线科技有限责任公司
  * @项目名称：action-wechatroulette
  * @作者：caishuai
  * @联系方式：caishuai@gyyx.cn
  * @创建时间：2017年3月17日 上午9:50:56
  * @版本号：0.0.1
  *-------------------------------------------------------------------------
  */
package cn.gyyx.action.bll.wechatroulette;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.common.ActionWXjifenChangeLogBean;
import cn.gyyx.action.beans.lottery.UserInfoBean;
import cn.gyyx.action.beans.lottery.po.ActionQualificationPO;
import cn.gyyx.action.dao.lottery.UserInfoDao;
import cn.gyyx.action.dao.lottery.impl.ActionQualificationDAOImpl;
import cn.gyyx.action.dao.wechatroulette.SendErrorLogDao;
import cn.gyyx.action.dao.wechatroulette.WeChatScoreChangeDao;

/**
 * <p>
 * 微信大转盘的操作Bll
 * </p>
 * 
 * @author caishuai
 * @since 0.0.1
 */
public class RouletteBindBll {
    private ActionQualificationDAOImpl ad = new ActionQualificationDAOImpl();
    private UserInfoDao user = new UserInfoDao();
    private WeChatScoreChangeDao wechatScoreChangeDao = new WeChatScoreChangeDao();
    private SendErrorLogDao errorLogDao = new SendErrorLogDao();

    /**
     * 
     * <p>
     * 查询当前用户当天抽奖资格
     * </p>
     *
     * @action caishuai 2017年3月20日 下午6:03:08 描述
     *
     * @param openId
     * @param activityId
     *            活动code
     * @param createAt
     *            创建时间
     * @return int 每日最大抽奖次数
     */
    public ActionQualificationPO getQualification(String openId, int activityId,
            Date createAt) {
        return ad.getDataByCreateAt(openId, activityId, createAt);
    }

    /**
     * 
     * <p>
     * 初始化每日抽奖次数
     * </p>
     *
     * @action caishuai 2017年3月20日 下午6:00:54 描述
     *
     * @param defaultPo
     *            想要初始化的抽奖实体
     * @param sqlSession
     *            void
     */
    public void InitializeLottery(ActionQualificationPO defaultPo,
            SqlSession sqlSession) {
        ad.InitializeLotterySession(defaultPo, sqlSession);
    }

    /**
     * 
     * <p>
     * 插入微信积分改变日志
     * </p>
     *
     * @action caishuai 2017年3月21日 下午5:34:48 描述
     *
     * @param jfchangeBean
     *            积分变更日志实体
     * @param sqlSession
     *            void
     */
    public void insertReduceJfLog(ActionWXjifenChangeLogBean jfchangeBean,
            SqlSession session) {
        wechatScoreChangeDao.insertReduceJfLog(jfchangeBean, session);

    }

    /**
     * 
     * <p>
     * 获取前50的all有效奖品中奖记录
     * </p>
     *
     * @action caishuai 2017年3月21日 下午1:14:29 描述
     *
     * @param actionCode
     *            活动code
     * @param available
     *            '有效'字段 0 or 1
     * @return List<UserInfoBean>
     */
    public List<UserInfoBean> selectTop50AvaPrizes(int actionCode,
            int available) {
        return user.selectTop50AvaPrizes(actionCode, available);
    }

    /**
     * 
     * <p>
     * 发奖失败，将发奖记录写入失败日志
     * </p>
     *
     * @action caishuai 2017年3月21日 下午6:03:09 描述
     *
     * @param userInfoBean
     *            发奖日志实体
     * @return boolean
     */
    public boolean addErrorInfo(UserInfoBean userInfoBean) {
        return errorLogDao.addInfo(userInfoBean);
    }
}