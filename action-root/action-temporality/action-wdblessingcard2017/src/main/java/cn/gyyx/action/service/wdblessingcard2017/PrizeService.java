/**
  * -------------------------------------------------------------------------
  * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
  * @版权所有：北京光宇在线科技有限责任公司
  * @项目名称：action-wdblessingcard2017
  * @作者：niushuai
  * @联系方式：niushuai@gyyx.cn
  * @创建时间：2017年3月14日 下午5:13:58
  * @版本号：0.0.1
  *-------------------------------------------------------------------------
  */
package cn.gyyx.action.service.wdblessingcard2017;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.lottery.PrizeBean;

/**
 * <p>
 * 奖品相关service
 * </p>
 * 
 * @author niushuai
 * @since 0.0.1
 */
public interface PrizeService {

    /**
     * 
     * <p>
     * 查询活动的奖品信息
     * </p>
     *
     * @action niushuai 2017年3月14日 下午5:15:00 描述
     *
     * @param actionCode
     * @param sqlSession
     * @return List<PrizeBean>
     */
    public List<PrizeBean> getPrizes(int actionCode);

    /**
      * <p>
      * 统计用户的奖品数量
      * </p>
      *
      * @action
      *    niushuai 2017年3月17日 下午2:03:09 描述
      *
      * @param account
      * @param prizeType
      * @return int
      */
    public int prizeCount(String account, String prizeType, int actionCode);

}
