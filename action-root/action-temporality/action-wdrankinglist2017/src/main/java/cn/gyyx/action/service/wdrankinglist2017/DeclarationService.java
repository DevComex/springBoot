/**
  * -------------------------------------------------------------------------
  * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
  * @版权所有：北京光宇在线科技有限责任公司
  * @项目名称：action-wdrankinglist2017
  * @作者：niushuai
  * @联系方式：niushuai@gyyx.cn
  * @创建时间：2017年4月5日 下午2:49:17
  * @版本号：0.0.1
  *-------------------------------------------------------------------------
  */
package cn.gyyx.action.service.wdrankinglist2017;

import java.util.List;
import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.predicable.ResultBeanWithPage;
import cn.gyyx.action.beans.wdrankinglist2017.DeclarationBean;
import cn.gyyx.action.beans.wdrankinglist2017.DeclarationQueryBean;

/**
 * <p>
 * 宣言service
 * </p>
 * 
 * @author niushuai
 * @since 0.0.1
 */
public interface DeclarationService {

    /**
     * <p>
     * 添加宣言
     * </p>
     *
     * @action niushuai 2017年4月7日 下午2:16:01 描述
     *
     * @param declaration
     *            void
     */
    ResultBean<String> addDeclaration(DeclarationBean declaration);

    /**
     * <p>
     * 根据openId获取宣言
     * </p>
     *
     * @action niushuai 2017年4月7日 下午3:39:52 描述
     *
     * @param openId
     * @return DeclarationBean
     */
    DeclarationBean getDeclarationByOpenId(String openId);

    /**
     * 
     * <p>
     * OA后台获取宣言列表
     * </p>
     *
     * @action laixiancai 2017年4月8日 下午7:18:33 描述
     *
     * @param beginTime
     * @param endTime
     * @param key
     * @param status
     * @param pageNo
     * @return ResultBeanWithPage<List<DeclarationBean>>
     */
    ResultBeanWithPage<List<DeclarationBean>> getDeclarationList(
            String beginTime, String endTime, String key, String status,
            int pageNo);

    /**
     * 
     * <p>
     * OA后台-宣言审核
     * </p>
     *
     * @action laixiancai 2017年4月9日 下午1:49:19 描述
     *
     * @param codesStr
     * @param verifyStatus
     * @param verifyAdmin
     * @return ResultBean<String>
     */
    ResultBean<String> batchVerifyDeclaration(String codesStr, int verifyStatus,
            String verifyAdmin);

    /**
     * 
     * <p>
     * 前台宣言排行页面查询宣言数据
     * </p>
     *
     * @action laixiancai 2017年4月9日 下午7:05:24 描述
     *
     * @param queryBean
     * @param result
     *            void
     */
    void queryDeclarations(DeclarationQueryBean queryBean,
            ResultBean<Object> result);

}
