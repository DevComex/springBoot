/**
  * -------------------------------------------------------------------------
  * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
  * @版权所有：北京光宇在线科技有限责任公司
  * @项目名称：action-wechatroulette
  * @作者：caishuai
  * @联系方式：caishuai@gyyx.cn
  * @创建时间：2017年3月21日 下午3:06:03
  * @版本号：0.0.1
  *-------------------------------------------------------------------------
  */
package cn.gyyx.action.bll.wechatroulette;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.dao.wechatroulette.WeChatScoreDao;

/**
 * <p>
 * 微信公众号积分操作bll实现类
 * </p>
 * 
 * @author caishuai
 * @since 0.0.1
 */
public class WeChatScoreBllImpl implements WeChatScoreBll {
    private WeChatScoreDao weChatScoreDao = new WeChatScoreDao();

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
    @Override
    public int selectScoreByOpenIdAndType(String openId, String type) {
        return weChatScoreDao.selectScoreByOpenIdAndType(openId, type);
    }

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
    @Override
    public boolean reduceScoreByOpenIdAndType(String openId, String type,
            int score) {
        return weChatScoreDao.reduceScoreByOpenIdAndType(openId, type, score);
    }

    /**
     * 
     * <p>
     * 查询用户积分bysession
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
    @Override
    public int selectScoreByOpenIdAndTypeBySession(String openId, String type,
            SqlSession session) {
        return weChatScoreDao.selectScoreByOpenIdAndTypeBySession(openId, type,
            session);
    }

    /**
     * 
     * <p>
     * 减少用户积分by session
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
    @Override
    public boolean reduceScoreByOpenIdAndTypeBySession(String openId,
            String type, int score, SqlSession session) {
        return weChatScoreDao.reduceScoreByOpenIdAndTypeBySession(openId, type,
            score, session);
    }

}
