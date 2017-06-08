/**
  * -------------------------------------------------------------------------
  * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
  * @版权所有：北京光宇在线科技有限责任公司
  * @项目名称：action-wdrankinglist2017
  * @作者：niushuai
  * @联系方式：niushuai@gyyx.cn
  * @创建时间：2017年4月7日 下午2:16:59
  * @版本号：0.0.1
  *-------------------------------------------------------------------------
  */
package cn.gyyx.action.dao.wdrankinglist2017;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import cn.gyyx.action.beans.wdrankinglist2017.DeclarationBean;
import cn.gyyx.action.dao.MyBatisBaseDAO;

/**
 * <p>
 * 宣言Dao
 * </p>
 * 
 * @author niushuai
 * @since 0.0.1
 */
public class DeclarationDao extends MyBatisBaseDAO {

    /**
     * <p>
     * 添加宣言
     * </p>
     *
     * @action niushuai 2017年4月7日 下午2:19:41 描述
     *
     * @param declaration
     *            void
     */
    public int addDeclaration(DeclarationBean declaration) {
        try (SqlSession sqlSession = this.getSession(false)) {
            DeclarationBeanMapper mapper = sqlSession
                    .getMapper(DeclarationBeanMapper.class);
            int count = mapper.addDeclaration(declaration);
            sqlSession.commit();
            return count;
        }
    }

    /**
     * <p>
     * 根据openId查询宣言
     * </p>
     *
     * @action niushuai 2017年4月7日 下午3:42:37 描述
     *
     * @param openId
     * @return DeclarationBean
     */
    public DeclarationBean getDeclarationByOpenId(String openId) {
        try (SqlSession sqlSession = this.getSession(true)) {
            DeclarationBeanMapper mapper = sqlSession
                    .getMapper(DeclarationBeanMapper.class);
            DeclarationBean declarationBean = mapper
                    .getDeclarationByOpenId(openId);
            return declarationBean;
        }
    }

    /**
     * 
     * <p>
     * 根据openId查询宣言（重载）
     * </p>
     *
     * @action laixiancai 2017年4月11日 下午6:53:44 描述
     *
     * @param openId
     * @param sqlSession
     * @return DeclarationBean
     */
    public DeclarationBean getDeclarationByOpenId(String openId,
            SqlSession sqlSession) {
        DeclarationBeanMapper mapper = sqlSession
                .getMapper(DeclarationBeanMapper.class);
        DeclarationBean declarationBean = mapper.getDeclarationByOpenId(openId);
        return declarationBean;
    }

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
     * @param account
     * @param wxNick
     * @param status
     * @param pageNo
     * @return List<DeclarationBean>
     */
    public List<DeclarationBean> getDeclarationList(String beginTime,
            String endTime, String key, String status, int pageNo) {
        try (SqlSession session = this.getSession(true)) {
            DeclarationBeanMapper mapper = session
                    .getMapper(DeclarationBeanMapper.class);
            return mapper.getDeclarationList(beginTime, endTime, key, status,
                pageNo);
        }
    }

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
     * @param account
     * @param wxNick
     * @param status
     * @return int
     */
    public int getDeclarationCount(String beginTime, String endTime, String key,
            String status) {
        try (SqlSession session = this.getSession(true)) {
            DeclarationBeanMapper mapper = session
                    .getMapper(DeclarationBeanMapper.class);
            return mapper.getDeclarationCount(beginTime, endTime, key, status);
        }
    }

    /**
     * 
     * <p>
     * OA后台-宣言审核
     * </p>
     *
     * @action laixiancai 2017年4月9日 下午1:43:54 描述
     *
     * @param code
     * @param status
     * @param verifyTime
     * @param verifyAdmin
     * @param sqlSession
     *            void
     */
    public void verifyDeclaration(int code, int status, String verifyTime,
            String verifyAdmin, SqlSession sqlSession) {
        DeclarationBeanMapper mapper = sqlSession
                .getMapper(DeclarationBeanMapper.class);
        mapper.verifyDeclaration(code, status, verifyTime, verifyAdmin);
    }

    /**
     * 
     * <p>
     * OA后台-根据code查询宣言
     * </p>
     *
     * @action laixiancai 2017年4月9日 下午2:20:21 描述
     *
     * @param code
     * @param sqlSession
     * @return DeclarationBean
     */
    public DeclarationBean getDeclarationByCode(int code,
            SqlSession sqlSession) {
        DeclarationBeanMapper mapper = sqlSession
                .getMapper(DeclarationBeanMapper.class);
        return mapper.getDeclarationByCode(code);
    }

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
    public List<DeclarationBean> getDeclarations(@Param("key") String key,
            @Param("pageNumber") int pageNumber,
            @Param("pageSize") int pageSize, @Param("orderBy") String orderBy) {
        try (SqlSession session = this.getSession(true)) {
            DeclarationBeanMapper mapper = session
                    .getMapper(DeclarationBeanMapper.class);
            return mapper.getDeclarations(key, pageNumber, pageSize, orderBy);
        }
    }

    /**
     * 
     * <p>
     * 前台排行页获取宣言数量
     * </p>
     *
     * @action laixiancai 2017年4月9日 下午5:51:56 描述
     *
     * @param key
     * @return int
     */
    public int getDeclarationsPageCount(String key) {
        try (SqlSession session = this.getSession(true)) {
            DeclarationBeanMapper mapper = session
                    .getMapper(DeclarationBeanMapper.class);
            return mapper.getDeclarationsPageCount(key);
        }
    }

    /**
     * <p>
     * 更新宣言
     * </p>
     *
     * @action niushuai 2017年4月10日 下午10:23:35 描述
     *
     * @param account
     * @param declaration
     * @param status
     * @return int
     */
    public int updateDeclaration(String account, String declaration,
            int status) {
        try (SqlSession session = this.getSession(true)) {
            DeclarationBeanMapper mapper = session
                    .getMapper(DeclarationBeanMapper.class);
            return mapper.updateDeclaration(account, declaration, status);
        }
    }

}
