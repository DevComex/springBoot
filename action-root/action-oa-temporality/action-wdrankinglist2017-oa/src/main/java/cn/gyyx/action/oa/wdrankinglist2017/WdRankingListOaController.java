/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action-wdrankinglist2017-oa
 * @作者：laixiancai
 * @联系方式：laixiancai@gyyx.cn
 * @创建时间：2017年4月8日 下午9:24:05
 * @版本号：0.0.1
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.oa.wdrankinglist2017;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.common.base.Throwables;
import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.predicable.ResultBeanWithPage;
import cn.gyyx.action.beans.wdrankinglist2017.DeclarationBean;
import cn.gyyx.action.service.wdrankinglist2017.DeclarationService;
import cn.gyyx.action.service.wdrankinglist2017.impl.DeclarationServiceImpl;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.oa.stage.httpRequest.OAUserRequest;
import cn.gyyx.oa.stage.model.OAUserInfoModel;

/**
 * <p>
 * 2017问道微信新服冲榜活动(wdrankinglist2017)OA后台Controller
 * </p>
 * 
 * @author laixiancai
 * @since 0.0.1
 */
@Controller
@RequestMapping(value = "/wdrankinglist2017")
public class WdRankingListOaController {

    private static final Logger LOGGER = GYYXLoggerFactory
            .getLogger(WdRankingListOaController.class);
    private DeclarationService declarationService = new DeclarationServiceImpl();

    /**
     * 
     * <p>
     * OA微信新服冲榜活动宣言审核页面
     * </p>
     *
     * @action laixiancai 2017年3月11日 下午3:50:46 新增
     *
     * @param model
     * @param request
     * @return String
     */
    @RequestMapping(value = "/delcarationaudit")
    public String declarationAudit(Model model, HttpServletRequest request) {
        return "wdrankinglist2017/declarationAudit";
    }

    /**
     * 
     * <p>
     * ajax获取宣言列表
     * </p>
     *
     * @action laixiancai 2017年4月8日 下午7:28:00 描述
     *
     * @param beginTime
     * @param endTime
     * @param account
     * @param status
     * @param pageIndex
     * @param request
     * @return ResultBeanWithPage<List<DeclarationBean>>
     */
    @RequestMapping(value = "/getdeclarationlist", method = RequestMethod.POST)
    @ResponseBody
    public ResultBeanWithPage<List<DeclarationBean>> getDeclarationList(
            @RequestParam(value = "beginTime") String beginTime,
            @RequestParam(value = "endTime") String endTime,
            @RequestParam(value = "key") String key,
            @RequestParam(value = "status") String status,
            @RequestParam(value = "pageIndex") int pageIndex,
            HttpServletRequest request) {
        LOGGER.info(
            "进入action[getBlessingCardList]，请求参数beginTime：{}, endTime：{}, key：{}, status：{}, pageIndex：{}",
            new Object[] { beginTime, endTime, key, status, pageIndex });

        ResultBeanWithPage<List<DeclarationBean>> result = new ResultBeanWithPage<>();
        try {
            if (!StringUtils.isEmpty(beginTime)) {
                beginTime = beginTime + " 00:00:00";
            }
            if (!StringUtils.isEmpty(endTime)) {
                endTime = endTime + " 23:59:59";
            }
            if (key == null || key.equals("")) {
                key = "";
            }

            LOGGER.info(
                "调用declarationService[getDeclarationList]开始，参数beginTime：{}, endTime：{}, key：{}, status：{}, pageIndex：{}",
                new Object[] { beginTime, endTime, key, status, pageIndex });

            result = declarationService.getDeclarationList(beginTime, endTime,
                key, status, pageIndex);

            LOGGER.info("调用declarationService[getDeclarationList]结束，列表总数为：{}",
                new Object[] { result.getTotalPage() });

            return result;
        } catch (Exception e) {
            LOGGER.error("获取数据列表异常{}", Throwables.getStackTraceAsString(e));
            List<DeclarationBean> list = new ArrayList<DeclarationBean>();
            result.setProperties(false, "获取数据列表异常", list, 0);
            return result;
        }
    }

    /**
     * 
     * <p>
     * 审核宣言
     * </p>
     *
     * @action laixiancai 2017年4月9日 下午2:48:52 描述
     *
     * @param codesStr
     * @param verifyStatus
     * @param request
     * @return ResultBean<String>
     */
    @RequestMapping(value = "/verifydeclaration", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> batchVerifyDeclaration(
            @RequestParam(value = "codesStr") String codesStr,
            @RequestParam(value = "verifyStatus") int verifyStatus,
            HttpServletRequest request) {
        try {
            LOGGER.info(
                "进入action[batchVerifyBlessingCard]，请求参数codesStr：{}, verifyStatus：{}",
                new Object[] { codesStr, verifyStatus });
            if (StringUtils.isEmpty(codesStr)) {
                return new ResultBean<>(false, "宣言编号不能为空", null);
            }
            OAUserInfoModel userInfoModel = OAUserRequest
                    .getUserInfoByRequest(request);
            if (userInfoModel == null) {
                return new ResultBean<>(false, "您没登录，请先登录", "");
            }
            String verifyAdmin = userInfoModel.getAccount();

            LOGGER.info(
                "调用declarationService[batchVerifyDeclaration]开始，参数codesStr：{}, verifyStatus：{}, verifyAdmin：{}",
                new Object[] { codesStr, verifyStatus, verifyAdmin });

            ResultBean<String> result = declarationService
                    .batchVerifyDeclaration(codesStr, verifyStatus,
                        verifyAdmin);

            LOGGER.info(
                "调用declarationService[batchVerifyDeclaration]结束，返回结果：{}",
                new Object[] { JSONObject.fromObject(result) });

            return result;
        } catch (Exception e) {
            LOGGER.error("审核-宣言异常{}", Throwables.getStackTraceAsString(e));
            return new ResultBean<>(false, "审核失败", "");
        }
    }
}
