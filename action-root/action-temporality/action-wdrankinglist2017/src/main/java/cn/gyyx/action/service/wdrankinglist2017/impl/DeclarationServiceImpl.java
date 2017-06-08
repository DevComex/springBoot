/**
  * -------------------------------------------------------------------------
  * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
  * @版权所有：北京光宇在线科技有限责任公司
  * @项目名称：action-wdrankinglist2017
  * @作者：niushuai
  * @联系方式：niushuai@gyyx.cn
  * @创建时间：2017年4月5日 下午2:49:35
  * @版本号：0.0.1
  *-------------------------------------------------------------------------
  */
package cn.gyyx.action.service.wdrankinglist2017.impl;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import com.google.common.base.Throwables;
import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.predicable.ResultBeanWithPage;
import cn.gyyx.action.beans.wdrankinglist2017.Constants;
import cn.gyyx.action.beans.wdrankinglist2017.DeclarationBean;
import cn.gyyx.action.beans.wdrankinglist2017.DeclarationQueryBean;
import cn.gyyx.action.beans.wdrankinglist2017.RoleBindBean;
import cn.gyyx.action.dao.MyBatisBaseDAO;
import cn.gyyx.action.dao.wdrankinglist2017.DeclarationDao;
import cn.gyyx.action.dao.wdrankinglist2017.RoleBindDao;
import cn.gyyx.action.service.insurance.MemcacheUtil;
import cn.gyyx.action.service.wdrankinglist2017.DeclarationService;
import cn.gyyx.core.memcache.XMemcachedClientAdapter;
import cn.gyyx.distribute.lock.DLockException;
import cn.gyyx.distribute.lock.DistributedLock;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * <p>
 * 宣言service
 * </p>
 * 
 * @author niushuai
 * @since 0.0.1
 */
public class DeclarationServiceImpl implements DeclarationService {

    private static final Logger LOG = GYYXLoggerFactory
            .getLogger(DeclarationServiceImpl.class);
    MyBatisBaseDAO myBatisBaseDAO = new MyBatisBaseDAO();
    private DeclarationDao declarationDao = new DeclarationDao();
    private RoleBindDao roleBindDao = new RoleBindDao();
    private MemcacheUtil memcacheUtil = new MemcacheUtil();
    private XMemcachedClientAdapter memcache = memcacheUtil.getMemcache();

    /**
     * <p>
     * 添加宣言
     * </p>
     *
     * @action niushuai 2017年4月7日 下午2:16:01 描述
     *
     * @param declaration
     * @return ResultBean<String>
     */
    public ResultBean<String> addDeclaration(DeclarationBean declaration) {
        ResultBean<String> result = new ResultBean<String>();

        try {
            RoleBindBean rolebind = roleBindDao
                    .getRoleBindBeanByOpenId(declaration.getOpenId());
            if (rolebind == null) {
                result.setProperties(false, "您还没有绑定账号！", null);
                return result;
            }
            if (rolebind.getRefusedCount() >= 5) {
                result.setProperties(false,
                    "非常遗憾您的报名信息已经未通过审核五次了，在本次活动当中不能再提交了哦~(┬＿┬)", null);
                return result;
            }
            try (DistributedLock lock = new DistributedLock(
                Constants.HD_CODE + declaration.getOpenId() + "_addDeclaration")) {
                // 分布式锁闭
                lock.weakLock(30, 0);
                DeclarationBean decla = declarationDao
                        .getDeclarationByOpenId(declaration.getOpenId());
                if (decla == null) {
                    declaration.setAccount(rolebind.getAccount());
                    declaration.setUserId(rolebind.getUserId());
                    declaration.setWxNick(rolebind.getWxNick());
                    declaration.setStatus(Constants.DECLARATION_STATUS_PENDING);
                    declaration.setCreateTime(new Date());
                    declarationDao.addDeclaration(declaration);
                    result.setIsSuccess(true);
                    result.setMessage("添加成功！");
                } else if (Constants.DECLARATION_STATUS_REFUSED != decla
                        .getStatus()) {
                    result.setProperties(false, "您现在不能修改宣言！", null);
                } else {
                    declarationDao.updateDeclaration(rolebind.getAccount(),
                        declaration.getDeclaration(),
                        Constants.DECLARATION_STATUS_PENDING);
                    // 更新宣言之后，清理缓存
                    memcache.delete(Constants.CACHE_DECLARATION_PREFIX
                            + declaration.getOpenId());
                    result.setProperties(true, "提交成功！", "您的宣言更新成功了，请等待审核！");
                }
            } catch (DLockException e) {
                result.setIsSuccess(false);
                result.setMessage("请不要操作过快!");
                LOG.error("玩家绑定账号异常！{}", Throwables.getStackTraceAsString(e));
            }
        } catch (Exception e) {
            LOG.error(Constants.HD_NAME + "查询账号绑定信息异常！{}",
                Throwables.getStackTraceAsString(e));
            result.setIsSuccess(false);
            result.setMessage("添加失败！请重试！");
        }
        return result;
    }

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
    public DeclarationBean getDeclarationByOpenId(String openId) {
        String memKey = Constants.CACHE_DECLARATION_PREFIX + openId;
        DeclarationBean declaration = memcache.get(memKey,
            DeclarationBean.class);
        if (declaration == null || declaration.getCode() <= 0) {
            declaration = declarationDao.getDeclarationByOpenId(openId);
            if (declaration != null)
                memcache.set(memKey, 60, declaration);
        }
        return declaration;
    }

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
    public ResultBeanWithPage<List<DeclarationBean>> getDeclarationList(
            String beginTime, String endTime, String key, String status,
            int pageNo) {
        ResultBeanWithPage<List<DeclarationBean>> result = new ResultBeanWithPage<>();
        List<DeclarationBean> list = new ArrayList<DeclarationBean>();
        int count = 0;

        // 总数
        count = declarationDao.getDeclarationCount(beginTime, endTime, key,
            status);

        // 数据列表
        list = declarationDao.getDeclarationList(beginTime, endTime, key,
            status, pageNo);
        result.setProperties(true, "查询成功", list, count);
        return result;
    }

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
    public ResultBean<String> batchVerifyDeclaration(String codesStr,
            int verifyStatus, String verifyAdmin) {
        ResultBean<String> result = new ResultBean<String>();
        String resultMsgs = "";
        for (String id : codesStr.split(",")) {
            resultMsgs += verifyDeclarationSingle(Integer.parseInt(id),
                verifyStatus, verifyAdmin) + "\n";
        }

        result.setIsSuccess(true);
        result.setMessage(resultMsgs);
        return result;
    }

    /**
     * 
     * <p>
     * 宣言审核结果提示语模板
     * </p>
     *
     * @action laixiancai 2017年4月9日 下午2:31:28 描述
     *
     * @param code
     * @param account
     * @param verifyType
     * @param verifyResult
     * @return String
     */
    private String resultMsgTemplate(int code, String account, int verifyType,
            String verifyResult) {
        return MessageFormat.format("宣言编号【{0}】，玩家账号【{1}】，操作类型【{2}】，操作结果【{3}】",
            code, account, verifyType == 1 ? "审核通过" : "审核拒绝", verifyResult);
    }

    /**
     * 
     * <p>
     * 宣言审核结果提示语模板
     * </p>
     *
     * @action laixiancai 2017年4月9日 下午2:32:03 描述
     *
     * @param code
     * @param verifyDesc
     * @param verifyResult
     * @return String
     */
    private String resultMsgTemplate(int code, String verifyDesc,
            String verifyResult) {
        return MessageFormat.format("宣言编号【{0}】，操作类型【{1}】，操作结果【{2}】", code,
            verifyDesc, verifyResult);
    }

    /**
     * 
     * <p>
     * OA后台-审核单个宣言
     * </p>
     *
     * @action laixiancai 2017年4月9日 下午2:45:23 描述
     *
     * @param code
     * @param verifyStatus
     * @param verifyAdmin
     * @return String
     */
    private String verifyDeclarationSingle(int code, int verifyStatus,
            String verifyAdmin) {
        LOG.info(
            "进入Service[verifyDeclarationSingle]，请求参数code：{}, verifyStatus：{}, verifyAdmin：{}",
            new Object[] { code, verifyStatus, verifyAdmin });
        String resultMsg = "";
        SqlSession sqlSession = myBatisBaseDAO.getSession();
        try {
            // 验证宣言和玩家绑定数据是否存在
            DeclarationBean declarationBean = declarationDao
                    .getDeclarationByCode(code, sqlSession);

            if (declarationBean == null) {
                resultMsg = resultMsgTemplate(code, "审核宣言", "审核失败，宣言信息不存在");
                return resultMsg;
            }

            String account = declarationBean.getAccount();
            RoleBindBean roleBindBean = roleBindDao
                    .getUserBindByAccount(account, sqlSession);

            if (roleBindBean == null) {
                resultMsg = resultMsgTemplate(code, "审核宣言", "审核失败，用户绑定信息不存在");
                return resultMsg;
            }

            int currentStatus = declarationBean.getStatus();// 当前宣言的审核状态
            // 如果当前宣言的审核状态与审核操作类型一样，则不继续后面的审核流程
            if (currentStatus == verifyStatus) {
                resultMsg = resultMsgTemplate(code, account, verifyStatus,
                    "该记录已经审核");
                return resultMsg;
            }

            // 宣言审核拒绝，需要更新玩家信息绑定表wdrankinglist_role_bind_tb的refused_count字段，加1
            if (verifyStatus == Constants.DECLARATION_STATUS_REFUSED) {
                int updateRefusedCount = roleBindBean.getRefusedCount() + 1;
                roleBindDao.updateUserRefusedCount(account, updateRefusedCount,
                    sqlSession);
            }

            // 更新wdrankinglist_declaration_tb表的相应字段
            SimpleDateFormat dfs = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss.SSS");// 精确到毫秒
            declarationDao.verifyDeclaration(code, verifyStatus,
                dfs.format(new Date()), verifyAdmin, sqlSession);
            // 提交事务
            sqlSession.commit(true);

            if (verifyStatus == Constants.DECLARATION_STATUS_PASS) {
                resultMsg = resultMsgTemplate(code, account, 1, "宣言已通过");
            } else {
                resultMsg = resultMsgTemplate(code, account, -1, "宣言已拒绝");
            }

            LOG.info("End Service[verifyDeclarationSingle]");
            return resultMsg;
        } catch (Exception e) {
            sqlSession.rollback();
            LOG.error(
                "invoke Service[verifyDeclarationSingle] catch a exception{}",
                Throwables.getStackTraceAsString(e));
            resultMsg = resultMsgTemplate(code, "审核宣言", "审核失败，系统异常");
            return resultMsg;
        } finally {
            sqlSession.close();
        }
    }

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
    public void queryDeclarations(DeclarationQueryBean queryBean,
            ResultBean<Object> result) {
        String key = queryBean.getKey();
        int pageNumber = queryBean.getPageIndex();
        int pageSize = queryBean.getPageSize();
        // 缓存key
        String memKey = Constants.HD_CODE + "_" + "queryDeclarations" + "_"
                + queryBean.getType() + "_" + key + "_" + pageNumber;
        String memKeyCount = Constants.HD_CODE + "_" + "queryDeclarations" + "_"
                + key;
        String orderBy = null;
        switch (queryBean.getType()) {
            case "grade":
                orderBy = "grade desc,create_time desc";
                break;
            case "daohang":
                orderBy = "daohang desc,create_time desc";
                break;
            case "latest":
                orderBy = "create_time desc";
                break;
            default:
                orderBy = "create_time desc";
                break;
        }
        List<DeclarationBean> resultData = memcache.get(memKey, List.class);
        if (resultData == null) {
            resultData = declarationDao.getDeclarations(key, pageNumber,
                pageSize, orderBy);
            if (resultData != null && resultData.size() > 0) {
                memcache.set(memKey, 60, resultData);
            }
        }

        String memCount = memcache.get(memKeyCount, String.class);
        int declarationsCount = 0;
        if (memCount == null) {
            declarationsCount = declarationDao.getDeclarationsPageCount(key);
            memcache.set(memKeyCount, 60, String.valueOf(declarationsCount));
        } else {
            declarationsCount = Integer.valueOf(memCount);
        }

        int page = computePage(declarationsCount, pageSize);
        Map<Object, Object> dataMap = new HashMap<>();
        dataMap.put("pageNumber", page);
        dataMap.put("pageData", resultData);
        result.setIsSuccess(true);
        result.setMessage("查询完成");
        result.setData(dataMap);
    }

    /**
     * 
     * <p>
     * 计算总页码
     * </p>
     *
     * @action laixiancai 2017年4月9日 下午7:04:13 描述
     *
     * @param allNumber
     * @param pageSize
     * @return int
     */
    private int computePage(int allNumber, int pageSize) {
        int more = allNumber % pageSize != 0 ? 1 : 0;
        return allNumber / pageSize + more;
    }

}
