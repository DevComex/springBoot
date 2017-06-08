package cn.gyyx.action.dao.wdrankinglist2017;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import cn.gyyx.action.beans.wdrankinglist2017.DeclarationBean;

/**
 * 
 * <p>
 * 宣言Mapper
 * </p>
 * 
 * @author laixiancai
 * @since 0.0.1
 */
public interface DeclarationBeanMapper {

    /**
     * <p>
     * 添加宣言
     * </p>
     *
     * @action niushuai 2017年4月7日 下午2:21:59 描述
     *
     * @param declaration
     *            void
     */
    int addDeclaration(DeclarationBean declaration);

    /**
     * <p>
     * 添加宣言
     * </p>
     *
     * @action niushuai 2017年4月7日 下午3:43:09 描述
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
     * @action laixiancai 2017年4月8日 下午7:00:04 描述
     *
     * @param beginTime
     * @param endTime
     * @param key
     * @param status
     * @param pageNo
     * @return List<DeclarationBean>
     */
    List<DeclarationBean> getDeclarationList(
            @Param("beginTime") String beginTime,
            @Param("endTime") String endTime, @Param("key") String key,
            @Param("status") String status, @Param("pageNo") int pageNo);

    /**
     * 
     * <p>
     * OA后台获取宣言数量
     * </p>
     *
     * @action laixiancai 2017年4月8日 下午7:05:04 描述
     *
     * @param beginTime
     * @param endTime
     * @param key
     * @param status
     * @return int
     */
    int getDeclarationCount(@Param("beginTime") String beginTime,
            @Param("endTime") String endTime, @Param("key") String account,
            @Param("status") String status);

    /**
     * 
     * <p>
     * OA后台-宣言审核
     * </p>
     *
     * @action laixiancai 2017年4月9日 下午1:40:27 描述
     *
     * @param code
     * @param status
     * @param verifyTime
     * @param verifyAdmin
     *            void
     */
    void verifyDeclaration(@Param("code") int code, @Param("status") int status,
            @Param("verifyTime") String verifyTime,
            @Param("verifyAdmin") String verifyAdmin);

    /**
     * 
     * <p>
     * OA后台-根据code查询宣言
     * </p>
     *
     * @action laixiancai 2017年4月9日 下午2:18:39 描述
     *
     * @param code
     * @return DeclarationBean
     */
    DeclarationBean getDeclarationByCode(@Param("code") int code);

    /**
     * 
     * <p>
     * 根据最新排行获取宣言列表
     * </p>
     *
     * @action laixiancai 2017年4月9日 下午5:38:07 描述
     *
     * @param key
     * @param pageNumber
     * @param pageSize
     * @return List<DeclarationBean>
     */
    List<DeclarationBean> getDeclarations(@Param("key") String key,
            @Param("pageNumber") int pageNumber,
            @Param("pageSize") int pageSize, @Param("orderBy") String orderBy);

    /**
     * 
     * <p>
     * 前台排行页获取宣言数量
     * </p>
     *
     * @action laixiancai 2017年4月9日 下午5:50:49 描述
     *
     * @param key
     * @return int
     */
    int getDeclarationsPageCount(@Param("key") String key);

    /**
     * <p>
     * 更新宣言
     * </p>
     *
     * @action niushuai 2017年4月10日 下午10:23:45 描述
     *
     * @param openid
     * @param declaration
     * @param status
     * @return int
     */
    int updateDeclaration(@Param("account") String account,
            @Param("declaration") String declaration,
            @Param("status") int status);

}