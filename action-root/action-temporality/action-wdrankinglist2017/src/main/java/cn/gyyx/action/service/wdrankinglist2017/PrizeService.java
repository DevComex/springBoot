/**
  * -------------------------------------------------------------------------
  * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
  * @版权所有：北京光宇在线科技有限责任公司
  * @项目名称：action-wdrankinglist2017
  * @作者：laixiancai
  * @联系方式：laixiancai@gyyx.cn
  * @创建时间：2017年4月10日 下午5:53:37
  * @版本号：0.0.1
  *-------------------------------------------------------------------------
  */
package cn.gyyx.action.service.wdrankinglist2017;

import java.util.List;
import cn.gyyx.action.beans.lottery.PrizeBean;

/**
 * <p>
 * 活动奖品Service
 * </p>
 * 
 * @author laixiancai
 * @since 0.0.1
 */
public interface PrizeService {

    /**
     * 
     * <p>
     * 查询活动奖品列表
     * </p>
     *
     * @action laixiancai 2017年4月10日 下午5:54:35 描述
     *
     * @param actionCode
     * @return List<PrizeBean>
     */
    public List<PrizeBean> getPrizes(int actionCode);
}
