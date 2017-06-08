/**
  * -------------------------------------------------------------------------
  * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
  * @版权所有：北京光宇在线科技有限责任公司
  * @项目名称：action-wechatroulette
  * @作者：caishuai
  * @联系方式：caishuai@gyyx.cn
  * @创建时间：2017年3月16日 下午4:44:37
  * @版本号：0.0.1
  *-------------------------------------------------------------------------
  */
package cn.gyyx.action.dao.wechatroulette;

import org.apache.ibatis.session.SqlSession;

import com.google.common.base.Throwables;

import cn.gyyx.action.dao.MyBatisBaseDAO;

/**
 * <p>
 * 微信积分操作DAO
 * </p>
 * 
 * @author caishuai
 * @since 0.0.1
 */
public class WeChatScoreDao extends MyBatisBaseDAO {

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
    public int selectScoreByOpenIdAndType(String openId, String type) {

        SqlSession session = getSession(true);
        try {
            WeChatScoreMapper mapper = session
                    .getMapper(WeChatScoreMapper.class);
            return mapper.selectScoreByOpenIdAndType(openId, type);
        } catch (Exception e) {
            logger.warn("getWechatScoreError:{}",
                Throwables.getStackTraceAsString(e));
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    /**
     * 
     * <p>
     * 查询用户积分by session
     * </p>
     *
     * @action caishuai 2017年3月16日 下午4:16:11 描述
     *
     * @param openId
     * @param type
     *            公众号类型
     * @return int 积分
     */
    public int selectScoreByOpenIdAndTypeBySession(String openId, String type,
            SqlSession session) {
        WeChatScoreMapper mapper = session.getMapper(WeChatScoreMapper.class);
        return mapper.selectScoreByOpenIdAndType(openId, type);

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
     * @return int 积分
     */
    public boolean reduceScoreByOpenIdAndType(String openId, String type,
            int score) {
        SqlSession session = getSession(true);
        try {
            WeChatScoreMapper mapper = session
                    .getMapper(WeChatScoreMapper.class);
            return mapper.reduceScoreByOpenIdAndType(openId, type, score) > 0;
        } catch (Exception e) {
            logger.warn("reduceWechatScoreError:{}",
                Throwables.getStackTraceAsString(e));
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    /**
     * 
     * <p>
     * 减少用户积分by Session
     * </p>
     *
     * @action caishuai 2017年3月16日 下午4:16:11 描述
     *
     * @param openId
     * @param type
     *            公众号类型
     * @param score
     *            减少积分数
     * @return int 积分
     */
    public boolean reduceScoreByOpenIdAndTypeBySession(String openId,
            String type, int score, SqlSession session) {
        WeChatScoreMapper mapper = session.getMapper(WeChatScoreMapper.class);
        return mapper.reduceScoreByOpenIdAndType(openId, type, score) > 0;
    }

}
