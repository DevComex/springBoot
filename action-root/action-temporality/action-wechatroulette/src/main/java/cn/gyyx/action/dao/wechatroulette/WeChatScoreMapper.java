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

import org.apache.ibatis.annotations.Param;

public interface WeChatScoreMapper {
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
    int selectScoreByOpenIdAndType(@Param("openId") String openId,
            @Param("type") String type);

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
    int reduceScoreByOpenIdAndType(@Param("openId") String openId,
            @Param("type") String type, @Param("score") int score);

}