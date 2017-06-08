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

import org.apache.ibatis.session.SqlSession;

/**
 * <p>
 * 微信签到积分的操作Bll
 * </p>
 * 
 * @author caishuai
 * @since 0.0.1
 */
public interface WeChatScoreBll {
    /**
     * 
     * <p>
     * 查询用户积分
     * </p>
     *
     * @action caishuai 2017年3月16日 下午4:16:11 描述
     *
     * @param openId
     * @param type
     *            公众号类型
     * @return int 积分
     */
    int selectScoreByOpenIdAndType(String openId, String type);

    /**
     * 
     * <p>
     * 减少用户积分
     * </p>
     *
     * @action caishuai 2017年3月16日 下午4:16:11 描述
     *
     * @param openId
     * @param type
     *            公众号类型
     * @param score
     *            减少积分数
     * @return boolean 
     */
    boolean reduceScoreByOpenIdAndType(String openId, String type, int score);

    /**
     * 
     * <p>
     * 查询用户积分BySession
     * </p>
     *
     * @action caishuai 2017年3月16日 下午4:16:11 描述
     *
     * @param openId
     * @param type
     *            公众号类型
     * @param session
     * @return int 积分
     */
    int selectScoreByOpenIdAndTypeBySession(String openId, String type,
            SqlSession session);

    /**
     * 
     * <p>
     * 减少用户积分BySession
     * </p>
     *
     * @action caishuai 2017年3月16日 下午4:16:11 描述
     *
     * @param openId
     * @param type
     *            公众号类型
     * @param score
     *            减少积分数
     * @param session
     * @return boolean
     */
    boolean reduceScoreByOpenIdAndTypeBySession(String openId, String type,
            int score, SqlSession session);
}
