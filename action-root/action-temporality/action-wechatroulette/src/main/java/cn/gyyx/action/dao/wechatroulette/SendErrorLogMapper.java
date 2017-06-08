/**
  * -------------------------------------------------------------------------
  * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
  * @版权所有：北京光宇在线科技有限责任公司
  * @项目名称：action-wechatroulette
  * @作者：caishuai
  * @联系方式：caishuai@gyyx.cn
  * @创建时间：2017年3月21日 下午5:52:50
  * @版本号：0.0.1
  *-------------------------------------------------------------------------
  */
package cn.gyyx.action.dao.wechatroulette;

import cn.gyyx.action.beans.lottery.UserInfoBean;;

/**
 * <p>
 * 发送奖品失败写入日志数据库操作接口
 * </p>
 * 
 * @author caishuai
 * @since 0.0.1
 */
public interface SendErrorLogMapper {
    /**
     * 
     * <p>
     * 将中奖记录写入错误日志
     * </p>
     *
     * @action caishuai 2017年3月21日 下午5:54:40 描述
     *
     * @param bean
     * @return int
     */
    int addInfo(UserInfoBean bean);
}
