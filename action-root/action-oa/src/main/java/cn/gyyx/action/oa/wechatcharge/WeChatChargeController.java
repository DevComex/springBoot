/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action-oa
 * @作者：guoyonggang
 * @联系方式：guoyonggang@gyyx.cn
 * @创建时间：2017年2月27日
 * @版本号：1.0
 * @本类主要用途描述：  
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.oa.wechatcharge;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Throwables;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.wechatcharge.PaginationResultBean;
import cn.gyyx.action.beans.wechatcharge.PrizeType;
import cn.gyyx.action.beans.wechatcharge.QueryConditionBean;
import cn.gyyx.action.beans.wechatcharge.WechatChargeBean;
import cn.gyyx.action.bll.wechatcharge.WechatChargeBLL;
import cn.gyyx.action.service.wechatcharge.WechatChargeService;

/**
 * 
 * <p>
 * 微信OA后台兑换管理
 * </p>
 * 
 * @author guoyonggang
 * @since 0.0.1
 */
@Controller
@RequestMapping(value = "wechatcharge")
public class WeChatChargeController {

    private static final Logger logger = LoggerFactory
            .getLogger(WeChatChargeController.class);
    private static String[] eightBitsCharSources = new String[] { "a", "b", "c",
            "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p",
            "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2",
            "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F",
            "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S",
            "T", "U", "V", "W", "X", "Y", "Z" };
    private WechatChargeBLL bll = new WechatChargeBLL();
    private WechatChargeService service = new WechatChargeService();

    @RequestMapping(value = "index")
    public String index() {
        return "wechatcharge/index";
    }

    /**
     * <p>
     * 添加兑换奖品信息
     * </p>
     *
     * @action guoyonggang 2017年2月27日 下午8:02:44 描述
     *
     * @param record
     * @return ResultBean<String>
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> addChargeInfo(WechatChargeBean wechatChargeBean) {
        logger.info("add wechatchargeinfo begin:{}", wechatChargeBean);
        ResultBean<String> result = new ResultBean<>();
        try {
            if (wechatChargeBean.getPrizeType().equals(PrizeType.RealPrize.toString())) {
                wechatChargeBean.setCardNo("");
                wechatChargeBean.setCardPwd("");
            }

            if (!bll.addWechatChargeInfo(wechatChargeBean)) {
                result.setIsSuccess(false);
                result.setMessage("添加失败");
                return result;
            }
            result.setIsSuccess(true);
            result.setMessage("添加成功");
        } catch (Exception ex) {
            logger.error("add wechatchargeinfo error:{}",
                Throwables.getStackTraceAsString(ex));
            result.setIsSuccess(false);
            result.setMessage("添加失败");
            return result;
        }
        return result;
    }

    /**
     * <p>
     * 删除兑换信息
     * </p>
     *
     * @action guoyonggang 2017年2月28日 下午3:42:23 描述
     *
     * @param code
     * @return ResultBean<String>
     */
    @RequestMapping(value = "delete", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<String> delete(Integer code) {
        logger.info("delete wechatchargeinfo begin:code={}", code);
        ResultBean<String> result = new ResultBean<>();
        try {
            if (!bll.delete(code)) {
                result.setIsSuccess(false);
                result.setMessage("删除失败");
                return result;
            }
            result.setIsSuccess(true);
            result.setMessage("删除成功");
        } catch (Exception ex) {
            logger.error("delete wechatchargeinfo error:{}",
                Throwables.getStackTraceAsString(ex));
            result.setIsSuccess(false);
            result.setMessage("删除失败");
        }
        return result;
    }

    /**
     * <p>
     * 根据兑换信息编号获取兑换信息
     * </p>
     *
     * @action guoyonggang 2017年2月28日 下午8:05:52 描述
     *
     * @param code
     * @return ResultBean<WechatChargeBean>
     */
    @RequestMapping(value = "get", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<WechatChargeBean> get(Integer code) {
        logger.info("get wechatchargeinfo begin:code={}", code);
        ResultBean<WechatChargeBean> result = new ResultBean<WechatChargeBean>();
        try {
            WechatChargeBean info = bll.getWechatChargeInfo(code);
            result.setIsSuccess(true);
            result.setMessage("获取成功");
            result.setData(info);
        } catch (Exception ex) {
            logger.error("get wechatchargeinfo error:{}",
                Throwables.getStackTraceAsString(ex));
            result.setIsSuccess(false);
            result.setMessage("获取失败");
        }
        return result;
    }

    /**
     * <p>
     * 根据兑换信息编号更新兑换信息
     * </p>
     *
     * @action guoyonggang 2017年2月28日 下午8:06:40 描述
     *
     * @param record
     * @return ResultBean<String>
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> update(WechatChargeBean record) {
        logger.info("update wechatchargeinfo begin:{}", record);
        ResultBean<String> result = new ResultBean<String>();
        try {
            if (record.getPrizeType().equals(PrizeType.RealPrize.toString())) {
                record.setCardNo("");
                record.setCardPwd("");
            }
            boolean isSuccess = bll.update(record);
            result.setIsSuccess(isSuccess);
            result.setMessage(isSuccess ? "修改成功" : "修改失败");
        } catch (Exception ex) {
            logger.error("update wechatchargeinfo error:{}",
                Throwables.getStackTraceAsString(ex));
            result.setIsSuccess(false);
            result.setMessage("修改失败");
        }
        return result;
    }

    /**
     * <p>
     * 生成兑换码
     * </p>
     *
     * @action guoyonggang 2017年2月27日 下午8:02:23 描述
     *
     * @return ResultBean<String>
     */
    @RequestMapping(value = "getChargeCode", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<String> getChargeCode() {
        ResultBean<String> result = new ResultBean<String>();
        try {
            StringBuffer shortBuffer = new StringBuffer();

            String uuid = UUID.randomUUID().toString().replace("-", "");
            for (int i = 0; i < 8; i++) {
                String str = uuid.substring(i * 4, i * 4 + 4);
                int x = Integer.parseInt(str, 16);
                shortBuffer.append(eightBitsCharSources[x % 0x3E]);
            }
            result.setSuccess(true);
            result.setMessage("生成成功");
            result.setData("GY" + shortBuffer.toString());
            return result;
        } catch (Exception ex) {
            logger.error("create wechatchargeCode error:{}",
                Throwables.getStackTraceAsString(ex));
            result.setSuccess(false);
            result.setMessage("生成失败" + ex.getMessage());
            return result;
        }
    }

    /**
     * <p>
     * 分页查询兑换信息
     * </p>
     *
     * @action guoyonggang 2017年2月28日 下午8:07:25 描述
     *
     * @param queryCondition
     * @return PaginationResultBean<WechatChargeBean>
     */
    @RequestMapping(value = "query",
        method = { RequestMethod.POST, RequestMethod.GET })
    @ResponseBody
    public PaginationResultBean<WechatChargeBean> queryChargeInfoList(
            QueryConditionBean queryCondition) {
        logger.info("query wechatchargeinfo begin:{}", queryCondition);
        PaginationResultBean<WechatChargeBean> result = new PaginationResultBean<>();
        try {
            List<WechatChargeBean> list = bll
                    .queryWechatChargeList(queryCondition);
            int count = bll.queryWechatChargeCount(queryCondition);
            result.setSuccess(true);
            result.setCount(count);
            result.setData(list);
            result.setMessage("查询成功");
            result.setPageIndex(queryCondition.getPageIndex());
            result.setPageSize(queryCondition.getPageSize());
        } catch (Exception ex) {
            logger.error("query wechatchargeinfo error:{}",
                Throwables.getStackTraceAsString(ex));
            result.setSuccess(false);
            result.setMessage("查询失败+" + ex.getMessage());
        }
        return result;
    }

    /**
     * 兑换信息导出
     *
     * @action guoyonggang 2017年3月1日 上午10:25:27 描述
     *
     * @param queryCondition
     *            void
     */
    @RequestMapping(value = "export",
        method = { RequestMethod.POST, RequestMethod.GET })
    public void export(HttpServletResponse response,
            QueryConditionBean queryCondition) {
        logger.info("export WechatChargeInfo begin:{}", queryCondition);
        try {
            service.exortExcel(response, queryCondition);
        } catch (Exception ex) {
            logger.error("export WechatChargeInfo error:{}",
                Throwables.getStackTraceAsString(ex));
        }
    }
}
