/**
  * -------------------------------------------------------------------------
  * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
  * @版权所有：北京光宇在线科技有限责任公司
  * @项目名称：action-wdrankinglist2017
  * @作者：laixiancai
  * @联系方式：laixiancai@gyyx.cn
  * @创建时间：2017年4月12日 下午1:45:12
  * @版本号：0.0.1
  *-------------------------------------------------------------------------
  */
package cn.gyyx.action.service.wdrankinglist2017;

/**
 * <p>
 * 公用帮助类接口
 * </p>
 * 
 * @author laixiancai
 * @since 0.0.1
 */
public interface CommonService {

    /**
     * 
     * <p>
     * 从hd_config_tb表中获取是否是debug模式
     * </p>
     *
     * @action laixiancai 2017年4月12日 下午1:46:08 描述
     *
     * @return boolean
     */
    boolean isDebug();

    /**
     * 
     * <p>
     * 从hd_config_tb表中获取ServerCode
     * </p>
     *
     * @action laixiancai 2017年4月11日 下午6:56:53 描述
     *
     * @return int
     */
    int getServerCode();

    /**
     * 
     * <p>
     * 从hd_config_tb表中获取ServerName
     * </p>
     *
     * @action laixiancai 2017年4月11日 下午6:56:57 描述
     *
     * @return String
     */
    String getServerName();
}
