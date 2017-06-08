/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 *
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action-root
 * @作者：changlu
 * @联系方式：changlu@gyyx.cn
 * @创建时间：2017/2/23 20:09
 * @版本号：0.0.1 -------------------------------------------------------------------------
 */
package cn.gyyx.action.oa.noviceoa;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.activity.ActivityConfigBean;
import cn.gyyx.action.beans.game.GameInfoBean;
import cn.gyyx.action.beans.novicecard.ServerInfoBean;
import cn.gyyx.action.beans.noviceoa.*;
import cn.gyyx.action.bll.activity.ActivityConfigBll;
import cn.gyyx.action.bll.noviceoa.*;
import cn.gyyx.action.oa.noviceoa.ViewModel.NoviceBatchViewBean;
import cn.gyyx.action.oa.noviceoa.ViewModel.NoviceGiftGenerateViewBean;
import cn.gyyx.action.oa.noviceoa.ViewModel.NoviceGiftPutViewBean;
import cn.gyyx.action.oa.noviceoa.ViewModel.NoviceGiftViewBean;
import cn.gyyx.action.service.agent.CallWebApiAgent;
import cn.gyyx.action.service.noviceoa.NoviceService;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import com.google.common.base.Throwables;
import org.apache.commons.lang.ArrayUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 描述:新手卡后台管理ui层
 * </p>
 *
 * @author changlu
 * @since 0.0.1
 */
@Controller
@RequestMapping("/novice-oa")
public class NoviceOAController {
    private Logger log = GYYXLoggerFactory.getLogger(NoviceOAController.class);
    private static final String ACTIVITY_KEY = "novice-";
    private SimpleDateFormat timeParse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    NoviceBatchBll noviceBatchBll = new NoviceBatchBll();
    NoviceGiftBll noviceGiftBll = new NoviceGiftBll();
    NoviceService noviceService = new NoviceService();
    NoviceServerBll noviceServerBll = new NoviceServerBll();
    ActivityConfigBll activityConfigBll = new ActivityConfigBll();
    NoviceCardBll noviceCardBll = new NoviceCardBll();
    NoviceGiftPutBll noviceGiftPutBll = new NoviceGiftPutBll();
    NoviceBatchTypeBll noviceBatchTypeBll = new NoviceBatchTypeBll();
    NoviceCooperateServerBll noviceCooperateServerBll = new NoviceCooperateServerBll();

    /**
     * 新手卡 项目管理页
     *
     * @return
     */
    @RequestMapping("/manage/index")
    public String index() {
        return "noviceoa/index";
    }

    /**
     * 异业合作 批次管理页
     *
     * @return
     */
    @RequestMapping("/manage/yyhz")
    public String yyhzIndex() {
        return "noviceoa/yyhzIndex";
    }

    /**
     * 新手卡项目投放对象管理页
     *
     * @return
     */
    @RequestMapping("/manage/putmanage")
    public String putmanage() {
        return "noviceoa/putmanage";
    }

    // TODO:批次类别管理 ↓↓↓

    /**
     * 新手卡项目 批次类型管理页
     *
     * @return
     */
    @RequestMapping("/manage/batch/type")
    public String batchTypeManage() {
        return "noviceoa/batchTypeManage";
    }

    /**
     * 批次类型添加接口
     *
     * @param typeName   类别名
     * @param abbreviate 类别名首字母缩写
     * @return
     */
    @RequestMapping(value = "/batch/type/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean addBatchType(@RequestParam("typeName") String typeName, @RequestParam("abbreviate") String abbreviate) {
        ResultBean resultBean = new ResultBean();
        if (typeName == null || typeName.trim().isEmpty() || abbreviate == null || abbreviate.trim().isEmpty()) {
            resultBean.setProperties(false, "请求参数错误", null);
            return resultBean;
        }

        if (!abbreviate.matches(Constant.Batch_Type_Regex)) {
            resultBean.setProperties(false, "类别名缩写格式错误，请重新输入", null);
            return resultBean;
        }

        //检查 批次类型是否重复
        NoviceBatchTypeBean batchTypeCheckBean = noviceBatchTypeBll.selectBeanByBatchType(abbreviate.toLowerCase());

        if (batchTypeCheckBean != null) {
            resultBean.setProperties(false, "该类别类型缩写已存在，请更换类别名或更改缩写名称", null);
            return resultBean;
        }

        //添加批次类型信息
        NoviceBatchTypeBean bean = new NoviceBatchTypeBean(null, abbreviate, typeName, false, new Date());
        int code = noviceBatchTypeBll.insert(bean);

        if (code <= 0) {
            resultBean.setProperties(false, "添加失败", null);
        }

        resultBean.setProperties(true, "添加成功", null);
        return resultBean;
    }

    /**
     * 获取批次类型列表
     *
     * @return
     */
    @RequestMapping("/batch/type/list")
    @ResponseBody
    public ResultBean<List<NoviceBatchTypeBean>> getBatchTypeList() {
        ResultBean<List<NoviceBatchTypeBean>> resultBean = new ResultBean();

        List<NoviceBatchTypeBean> list = noviceBatchTypeBll.selectList();

        resultBean.setIsSuccess(true);
        resultBean.setData(list);

        return resultBean;
    }


    // TODO:批次管理接口 ↓↓↓

    /**
     * 新手卡批次添加接口
     *
     * @param bean
     * @return
     */
    @RequestMapping(value = "/batch/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> batchAdd(@Valid NoviceBatchViewBean bean) {
        ResultBean<String> resultBean = new ResultBean<>();
        ActivityConfigBean activityConfigBean = new ActivityConfigBean();
        activityConfigBean.setIsOpen(bean.getIsOpen());

        if (bean.getGameId() != 2) {
            resultBean.setSuccess(false);
            resultBean.setMessage("系统暂只开放问道游戏");
            return resultBean;
        }

        //前端传递时间戳转时间
        try {
            if (bean.getStartTime() != null) {
                Date startTime = timeParse.parse(timeParse.format(bean.getStartTime() * 1000));
                activityConfigBean.setBeginTime(startTime);
            }

            if (bean.getEndTime() != null) {
                Date endTime = timeParse.parse(timeParse.format(bean.getEndTime() * 1000));
                activityConfigBean.setEndTime(endTime);
            }
        } catch (ParseException ex) {
            log.error("时间转换异常：" + Throwables.getStackTraceAsString(ex));

            resultBean.setSuccess(false);
            resultBean.setMessage("格林治时间午夜秒数");
            return resultBean;
        }

        //创建批次实体对象
        NoviceBatchBean batchBean = new NoviceBatchBean(null, bean.getBatchName(), bean.getGameId(), bean.getBatchType(), bean.getIsOpen(),
                new Date());

        //添加批次信息
        resultBean = noviceService.addBatch(batchBean, activityConfigBean,
                bean.getServerId() == null ? null : bean.getServerId().split(","),
                bean.getServerName() == null ? null : bean.getServerName().split(","));

        return resultBean;

    }

    /**
     * 批次更新
     *
     * @param bean
     * @return
     */
    @RequestMapping(value = "/batch/update", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> batchUpdate(@Valid NoviceBatchViewBean bean) {
        ResultBean<String> resultBean = new ResultBean<>();
        ActivityConfigBean activityConfigBean = new ActivityConfigBean();
        activityConfigBean.setIsOpen(bean.getIsOpen());
        activityConfigBean.setNote(bean.getBatchType());

        if (bean.getBatchCode() <= 0) {
            resultBean.setSuccess(false);
            resultBean.setMessage("批次无效");
            return resultBean;
        }

        //前端传递时间戳转时间
        try {
            if (bean.getStartTime() != null) {
                Date startTime = timeParse.parse(timeParse.format(bean.getStartTime() * 1000));
                activityConfigBean.setBeginTime(startTime);
            }

            if (bean.getEndTime() != null) {
                Date endTime = timeParse.parse(timeParse.format(bean.getEndTime() * 1000));
                activityConfigBean.setEndTime(endTime);
            }
        } catch (ParseException ex) {
            log.error("时间转换异常：" + Throwables.getStackTraceAsString(ex));

            resultBean.setSuccess(false);
            resultBean.setMessage("格林治时间午夜秒数");
            return resultBean;
        }

        //创建批次实体对象
        NoviceBatchBean batchBean = new NoviceBatchBean(bean.getBatchCode(), bean.getBatchName(), bean.getGameId(), bean.getBatchType(),
                bean.getIsOpen(), new Date());

        //更新批次信息
        resultBean = noviceService.updateBatch(batchBean, activityConfigBean,
                bean.getServerId() == null ? null : bean.getServerId().split(","),
                bean.getServerName() == null ? null : bean.getServerName().split(","));
        return resultBean;
    }

    /**
     * 获取批次详情信息
     *
     * @param batchCode 业务组ID
     * @return
     */
    @RequestMapping("/batch/info")
    @ResponseBody
    public ResultBean<NoviceBatchViewBean> getBatchInfo(@RequestParam("batchCode") int batchCode) {
        ResultBean<NoviceBatchViewBean> resultBean = new ResultBean<>();

        if (batchCode <= 0) {
            resultBean.setIsSuccess(false);
            resultBean.setMessage("批次号无效");
            return resultBean;
        }

        //根据批次编号获取批次信息
        NoviceBatchBean batchBean = noviceBatchBll.selectBeanByCode(batchCode);

        if (batchBean == null) {
            resultBean.setIsSuccess(false);
            resultBean.setMessage("无此批次信息");
            return resultBean;
        }

        //根据批次编号获取 批次配置信息
        ActivityConfigBean activityConfigBean = activityConfigBll.selectBeanByActivityCode(ACTIVITY_KEY + batchCode);

        if (activityConfigBean == null) {
            resultBean.setIsSuccess(false);
            resultBean.setMessage("无此批次活动配置信息");
            return resultBean;
        }

        List<NoviceServerBean> list;
        if (activityConfigBean.getNote().equals(Constant.NoviceBatchType)) {
            //新手卡类型批次 获取绑定服务器
            list = noviceServerBll.selectByBatchId(batchCode);
        } else {
            //非新手卡类型批次  获取绑定服务器
            list = noviceCooperateServerBll.selectByBatchId(batchCode);
        }

        //创建批次交互对象
        NoviceBatchViewBean viewBean = new NoviceBatchViewBean();
        viewBean.setBatchCode(batchCode);
        viewBean.setBatchName(batchBean.getName());
        viewBean.setGameId(batchBean.getGameId());
        viewBean.setIsOpen(activityConfigBean.getIsOpen());

        //赋值开始结束时间
        if (activityConfigBean.getBeginTime() != null) {
            viewBean.setStartTime(activityConfigBean.getBeginTime().getTime());
        }
        if (activityConfigBean.getEndTime() != null) {
            viewBean.setEndTime(activityConfigBean.getEndTime().getTime());
        }

        //赋值绑定服务器信息
        if (list.size() > 0) {
            String[] serverIds = new String[list.size()];
            String[] serverNames = new String[list.size()];

            int i = 0;
            for (NoviceServerBean bean : list) {
                serverIds[i] = bean.getServerId() + "";
                serverNames[i] = bean.getServerName();
                i++;
            }

            //服务器编号 以逗号分隔
            String resultServerIds = ArrayUtils.toString(serverIds);
            //服务器名 以逗号分隔
            String resultServerNames = ArrayUtils.toString(serverNames);
            viewBean.setServerId(resultServerIds.substring(1, resultServerIds.length() - 1));
            viewBean.setServerName(resultServerNames.substring(1, resultServerNames.length() - 1));
        }
        resultBean.setIsSuccess(true);
        resultBean.setData(viewBean);
        return resultBean;
    }

    /**
     * 获取服务器列表
     *
     * @param gameId 游戏ID
     * @return
     */
    @RequestMapping("/server/list")
    @ResponseBody
    public ResultBean<List<ServerInfoBean.Value>> getNoviceServerList(@RequestParam("gameId") int gameId) {
        ResultBean<List<ServerInfoBean.Value>> resultBean = new ResultBean<>();

        if (gameId != 2) {
            resultBean.setSuccess(false);
            resultBean.setMessage("系统暂只开放问道新手卡管理");
            return resultBean;
        }

        //获取新手卡类型服务器列表
        return noviceService.getServerList(gameId, Constant.NoviceBatchType);
    }

    /**
     * 获取全部服务器列表（根据新手卡类型获取批次列表）
     *
     * @param gameId
     * @param batchType 批次类型
     * @return
     */
    @RequestMapping("/server/all")
    @ResponseBody
    public ResultBean<List<ServerInfoBean.Value>> getServerList(@RequestParam("gameId") int gameId, @RequestParam("batchType") String batchType) {
        ResultBean<List<ServerInfoBean.Value>> resultBean = new ResultBean<>();

        if (gameId != 2) {
            resultBean.setSuccess(false);
            resultBean.setMessage("系统暂只开放问道新手卡管理");
            return resultBean;
        }

        if (batchType == null || batchType.isEmpty()) {
            resultBean.setSuccess(false);
            resultBean.setMessage("请求参数错误");
            return resultBean;
        }

        return noviceService.getServerList(gameId, batchType);
    }

    // TODO:新手卡礼包管理接口 ↓↓↓

    /**
     * 根据批次编号获取 礼包列表
     *
     * @param batchCode
     * @return
     */
    @RequestMapping(value = "/gift/list", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<List<NoviceGiftBean>> getGiftList(@RequestParam("batchCode") int batchCode) {
        ResultBean<List<NoviceGiftBean>> resultBean = new ResultBean<>();
        if (batchCode == 0) {
            resultBean.setIsSuccess(false);
            resultBean.setMessage("批次值无效");
            return resultBean;
        }

        List<NoviceGiftBean> list = noviceGiftBll.selectListByBatchId(batchCode);
        if (list == null || list.size() == 0) {
            resultBean.setIsSuccess(false);
            resultBean.setMessage("该批次下无礼包信息");
        } else {
            resultBean.setIsSuccess(true);
            resultBean.setData(list);
        }

        return resultBean;
    }

    /**
     * 礼包添加接口
     *
     * @param bean 礼包实体
     * @return
     */
    @RequestMapping(value = "/gift/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<Integer> giftAdd(@Valid NoviceGiftViewBean bean) {
        ResultBean<Integer> resultBean = new ResultBean<>();

        NoviceGiftBean giftBean = new NoviceGiftBean();
        giftBean.setBatchId(bean.getBatchId());
        giftBean.setCreateTime(new Date());
        giftBean.setgiftCode(bean.getGiftCode());
        giftBean.setGiftName(bean.getGiftName());
        giftBean.setIsDefault(bean.getIsDefault());

        resultBean = noviceGiftBll.insert(giftBean);

        return resultBean;
    }


    /**
     * 礼包更新接口
     *
     * @param bean 礼包实体
     * @return
     */
    @RequestMapping(value = "/gift/update", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> giftUpdate(@Valid NoviceGiftViewBean bean) {
        ResultBean<String> resultBean = new ResultBean<>();

        if (bean.getGiftId() == null || bean.getGiftId() <= 0) {
            resultBean.setIsSuccess(false);
            resultBean.setMessage("礼包ID无效");
            return resultBean;
        }

        NoviceGiftBean giftBean = new NoviceGiftBean();

        giftBean.setCode(bean.getGiftId());
        giftBean.setBatchId(bean.getBatchId());
        giftBean.setgiftCode(bean.getGiftCode());
        giftBean.setGiftName(bean.getGiftName());
        giftBean.setIsDefault(bean.getIsDefault());
        resultBean = noviceGiftBll.update(giftBean);

        return resultBean;
    }

    /**
     * 判断礼包是否被使用
     *
     * @param code 礼包ID
     * @return
     */
    @RequestMapping(value = "/gift/isused", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<String> giftIsUsed(@RequestParam("code") int code) {
        ResultBean<String> resultBean = new ResultBean<>();
        if (code <= 0) {
            resultBean.setIsSuccess(false);
            resultBean.setMessage("礼包ID无效");
            return resultBean;
        }
        // 判断礼包是否存在被使用的情况
        List<NoviceGiftGenerateBean> list = noviceGiftBll.selectByGiftId(code);
        if (list != null && !list.isEmpty()) {
            resultBean.setIsSuccess(true);
            resultBean.setMessage("该礼包已被使用");
            return resultBean;
        } else {
            resultBean.setIsSuccess(false);
            resultBean.setMessage("该礼包未被使用");
            return resultBean;
        }
    }

    /**
     * 礼包删除接口
     *
     * @param code 礼包ID
     * @return
     */
    @RequestMapping(value = "/gift/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> giftDelete(@RequestParam("code") int code) {
        ResultBean<String> resultBean = new ResultBean<>();

        if (code <= 0) {
            resultBean.setIsSuccess(false);
            resultBean.setMessage("礼包ID无效");
            return resultBean;
        }

        // 判断礼包是否存在被使用的情况
        List<NoviceGiftGenerateBean> list = noviceGiftBll.selectByGiftId(code);
        if (list != null && !list.isEmpty()) {
            resultBean.setIsSuccess(false);
            resultBean.setMessage("该礼包已被使用,不允许删除");
            return resultBean;
        }

        boolean result = noviceGiftBll.deleteGiftInfo(code);
        if (result) {
            resultBean.setIsSuccess(result);
            resultBean.setMessage("删除成功");
            return resultBean;
        } else {
            resultBean.setIsSuccess(result);
            resultBean.setMessage("删除失败");
            return resultBean;
        }
    }

    // TODO:新手卡管理页接口 ↓↓↓

    /**
     * 获取游戏列表
     *
     * @return
     */
    @RequestMapping(value = "/game/list", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<List<GameInfoBean>> getGameList() {
        return CallWebApiAgent.getClientGameList();
    }

    /**
     * 根据批次类型获取批次列表
     *
     * @param gameId
     * @param batchType
     * @return
     */
    @RequestMapping(value = "/batch/list", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<List<NoviceBatchBean>> getBatchList(@RequestParam("gameId") int gameId, @RequestParam("batchType") String batchType) {
        ResultBean<List<NoviceBatchBean>> resultBean = new ResultBean<>();

        if (gameId != 2) {
            resultBean.setSuccess(false);
            resultBean.setMessage("系统暂只开放问道新手卡管理");
            return resultBean;
        }

        if (batchType == null || batchType.isEmpty()) {
            resultBean.setSuccess(false);
            resultBean.setMessage("请求参数错误");
            return resultBean;
        }

        List<NoviceBatchBean> list = noviceBatchBll.selectBatchList(gameId, batchType);

        if (list == null || list.isEmpty()) {
            resultBean.setIsSuccess(false);
            resultBean.setMessage("无批次信息");
        } else {
            resultBean.setIsSuccess(true);
            resultBean.setData(list);
        }

        return resultBean;
    }

    /**
     * 根据批次类型，获取批次信息分页列表
     *
     * @param gameId
     * @param index
     * @param pageCount
     * @param batchType
     * @return
     */
    @RequestMapping(value = "/batch/list/page", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<List<NoviceBatchBean>> getBatchPageList(@RequestParam("gameId") int gameId,
                                                              @RequestParam("index") int index,
                                                              @RequestParam("pageCount") int pageCount,
                                                              @RequestParam("batchType") String batchType) {
        ResultBean<List<NoviceBatchBean>> resultBean = new ResultBean<>();

        if (gameId != 2) {
            resultBean.setSuccess(false);
            resultBean.setMessage("系统暂只开放问道新手卡管理");
            return resultBean;
        }

        if (batchType == null || batchType.isEmpty()) {
            resultBean.setSuccess(false);
            resultBean.setMessage("请求参数错误");
            return resultBean;
        }

        List<NoviceBatchBean> list = noviceBatchBll.selectPageList(gameId, index, pageCount, batchType);
        int totalCount = noviceBatchBll.selectTotalCount(batchType);

        //获取游戏信息
        ResultBean<GameInfoBean> gameInfoResult = CallWebApiAgent.getGameInfo(gameId);

        if (!gameInfoResult.getIsSuccess()) {
            resultBean.setIsSuccess(false);
            resultBean.setMessage("获取游戏信息失败");
            return resultBean;
        }

        //赋值游戏名
        for (NoviceBatchBean bean : list) {
            bean.setGameName(gameInfoResult.getData().getGameName());
        }

        resultBean.setIsSuccess(true);
        resultBean.setData(list);
        resultBean.setTotal(totalCount);


        return resultBean;
    }


    /**
     * 根据批次编号获取批次信息
     *
     * @param batchCode
     * @return
     */
    @RequestMapping(value = "/batch/code", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<NoviceBatchBean> getBatch(@RequestParam("batchCode") int batchCode) {
        ResultBean<NoviceBatchBean> resultBean = new ResultBean<>();

        if (batchCode == 0) {
            resultBean.setIsSuccess(false);
            resultBean.setMessage("批次号无效");
            return resultBean;
        }

        NoviceBatchBean bean = noviceBatchBll.selectBeanByCode(batchCode);

        if (bean == null) {
            resultBean.setIsSuccess(false);
            resultBean.setMessage("无批次信息");
        } else {
            ResultBean<GameInfoBean> gameInfoResult = CallWebApiAgent.getGameInfo(bean.getGameId());

            if (!gameInfoResult.getIsSuccess()) {
                resultBean.setIsSuccess(false);
                resultBean.setMessage("获取游戏信息失败");
                return resultBean;
            }
            bean.setGameName(gameInfoResult.getData().getGameName());
            resultBean.setIsSuccess(true);
            resultBean.setData(bean);
        }

        return resultBean;
    }

    /**
     * 获取渠道批次礼包展示列表
     *
     * @param batchId 业务组ID
     * @return
     */
    @RequestMapping(value = "/channel/gift/generatelist", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<List<NoviceGiftGenerateInfoBean>> getNoviceGiftGenerateList(
            @RequestParam("batchId") int batchId) {
        ResultBean<List<NoviceGiftGenerateInfoBean>> resultBean = new ResultBean<>();
        List<NoviceGiftGenerateInfoBean> list = noviceGiftBll.selectNoviceGiftGenerateList(batchId);
        if (list == null || list.size() == 0) {
            resultBean.setIsSuccess(false);
            resultBean.setMessage("无礼包新手卡生成信息");
        } else {
            resultBean.setIsSuccess(true);
            resultBean.setData(list);
        }
        return resultBean;
    }

    /**
     * 删除渠道新手卡礼包
     *
     * @param bean
     * @return
     */
    @RequestMapping(value = "/channel/gift/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean noviceGiftDelete(NoviceGiftGenerateViewBean bean) {

        NoviceGiftGenerateBean nInfo = new NoviceGiftGenerateBean();
        nInfo.setCode(bean.getCode());
        nInfo.setIsDelete(bean.getIsDelete());

        ResultBean resultBean = noviceGiftBll.noviceGiftUpdate(nInfo);
        return resultBean;
    }

    /**
     * 更新渠道新手卡礼包
     *
     * @param bean 投放对象礼包的新手卡生成实体
     * @return
     */
    @RequestMapping(value = "/channel/gift/update", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> noviceGiftUpdate(NoviceGiftGenerateViewBean bean) {
        ResultBean<String> resultBean = new ResultBean<>();

        if (bean.getBatchId() == null || bean.getBatchId() <= 0) {
            resultBean.setIsSuccess(false);
            resultBean.setMessage("批次ID无效");
            return resultBean;
        }

        NoviceGiftGenerateBean nInfo = new NoviceGiftGenerateBean();
        try {
            nInfo.setBatchId(bean.getBatchId());
            nInfo.setChannel(bean.getChannel());
            nInfo.setCode(bean.getCode());
            nInfo.setCount(bean.getCount());
            nInfo.setDescription(bean.getDescription());
            nInfo.setGiftId(bean.getGiftId());
            nInfo.setIsDelete(bean.getIsDelete());
            if (bean.getBeginDate() != null) {
                Date beginDate = timeParse.parse(timeParse.format(bean.getBeginDate() * 1000));
                nInfo.setBeginDate(beginDate);
            }
            if (bean.getEndDate() != null) {
                Date endDate = timeParse.parse(timeParse.format(bean.getEndDate() * 1000));
                nInfo.setEndDate(endDate);
            }
        } catch (ParseException ex) {
            log.error("时间转换异常：" + Throwables.getStackTraceAsString(ex));
            resultBean.setSuccess(false);
            resultBean.setMessage("时间转换出错");
            return resultBean;
        }
        resultBean = noviceGiftBll.noviceGiftUpdate(nInfo);
        return resultBean;
    }

    /**
     * 添加渠道新手卡礼包并生成新手卡
     *
     * @param bean 投放对象礼包的新手卡生成实体
     * @return
     */
    @RequestMapping(value = "/channel/gift/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> noviceGiftAdd(NoviceGiftGenerateViewBean bean) {
        ResultBean<String> resultBean = new ResultBean<>();

        if (bean.getBatchId() == null || bean.getBatchId() <= 0) {
            resultBean.setIsSuccess(false);
            resultBean.setMessage("批次ID无效");
            return resultBean;
        }

        NoviceGiftGenerateBean nInfo = new NoviceGiftGenerateBean();
        try {
            nInfo.setBatchId(bean.getBatchId());
            nInfo.setChannel(bean.getChannel());
            nInfo.setCount(bean.getCount());
            nInfo.setDescription(bean.getDescription());
            nInfo.setGiftId(bean.getGiftId());
            if (bean.getBeginDate() != null) {
                Date beginDate = timeParse.parse(timeParse.format(bean.getBeginDate() * 1000));
                nInfo.setBeginDate(beginDate);
            }
            if (bean.getEndDate() != null) {
                Date endDate = timeParse.parse(timeParse.format(bean.getEndDate() * 1000));
                nInfo.setEndDate(endDate);
            }
        } catch (ParseException ex) {
            log.error("时间转换异常：" + Throwables.getStackTraceAsString(ex));
            resultBean.setSuccess(false);
            resultBean.setMessage("时间转换出错");
            return resultBean;
        }
        resultBean = noviceGiftBll.noviceGiftAdd(nInfo);
        return resultBean;
    }

    /**
     * 新手卡导出
     *
     * @param batchId 业务组ID
     * @param taskId  业务组明细ID
     */
    @RequestMapping(value = "/channel/gift/export", method = RequestMethod.GET)
    @ResponseBody
    public void exportNoviceCard(HttpServletResponse response, @RequestParam("batchId") int batchId,
                                 @RequestParam("taskId") int taskId) {
        log.info("exportNoviceCard" + System.getProperty("java.io.tmpdir"));

        String channel = noviceGiftBll.selectByPrimaryKey(taskId).getChannel();
        SXSSFWorkbook wb = noviceCardBll.exportNoviceCard(batchId, taskId, channel);

        // 第六步，将文件存到指定位置
        String fileName = "新手卡号记录.xlsx";
        try {
            // 取得输出流
            OutputStream os = response.getOutputStream();
            // 清空输出流
            response.reset();
            response.setHeader("Content-disposition",
                    "attachment; filename=" + new String(fileName.getBytes("GB2312"), "ISO8859-1"));
            response.setContentType("application/msexcel");
            wb.write(os);
            os.close();
        } catch (Exception ex) {

        }
    }

    /**
     * 参数校验结果处理
     *
     * @param e 参数验证异常
     * @return 格式化后的错误信息信息
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public ResultBean bindExceptionHandler(BindException e) {
        ResultBean<String> resultBean = new ResultBean();

        resultBean.setSuccess(false);
        resultBean.setMessage(e.getFieldError().getDefaultMessage());

        return resultBean;
    }

    /**
     * 获取投放对象列表
     *
     * @param putType   投放类型(1:落地页,2:非落地页)
     * @param putName   投放对象名称
     * @param index     当前页
     * @param pageCount 一页显示记录数
     * @return
     */
    @RequestMapping(value = "/channel/list/page", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<List<NoviceGiftPutBean>> getChannelPageList(@RequestParam("putType") int putType,
                                                                  @RequestParam("putName") String putName, @RequestParam("index") int index,
                                                                  @RequestParam("pageCount") int pageCount) {
        ResultBean<List<NoviceGiftPutBean>> resultBean = new ResultBean<>();
        List<NoviceGiftPutBean> list = noviceGiftPutBll.selectPageList(putType, putName, index, pageCount);
        if (list == null || list.size() == 0) {
            resultBean.setIsSuccess(false);
            resultBean.setMessage("无投放对象信息");
        } else {
            int totalCount = noviceGiftPutBll.selectTotalCount(putType, putName);
            resultBean.setIsSuccess(true);
            resultBean.setData(list);
            resultBean.setTotal(totalCount);
        }
        return resultBean;
    }

    /**
     * 根据投放类型获取投放对象列表
     *
     * @param putType 投放类型(1:落地页,2:非落地页)
     * @return
     */
    @RequestMapping(value = "/channel/list", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<List<NoviceGiftPutBean>> getChannelListByPutType(@RequestParam("putType") int putType) {
        ResultBean<List<NoviceGiftPutBean>> resultBean = new ResultBean<>();
        List<NoviceGiftPutBean> list = noviceGiftPutBll.selectByPutType(putType);
        if (list == null || list.size() == 0) {
            resultBean.setIsSuccess(false);
            resultBean.setMessage("无投放对象信息");
        } else {
            resultBean.setIsSuccess(true);
            resultBean.setData(list);
        }
        return resultBean;
    }

    /**
     * 投放对象添加
     *
     * @param bean 投放对象实体
     * @return
     */
    @RequestMapping(value = "/channel/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> novicePutAdd(NoviceGiftPutViewBean bean) {
        ResultBean<String> resultBean = new ResultBean<>();

        if (bean.getPutType() == null || bean.getPutType() < 0) {
            resultBean.setIsSuccess(false);
            resultBean.setMessage("投放类型无效");
            return resultBean;
        }
        if (bean.getPutName() == null || bean.getPutName().equals("")) {
            resultBean.setIsSuccess(false);
            resultBean.setMessage("投放对象名称无效");
            return resultBean;
        }

        List<NoviceGiftPutBean> list = noviceGiftPutBll.selectNoviceGiftPut(bean.getPutName(), bean.getPutType());
        if (list != null && list.size() > 0) {
            resultBean.setIsSuccess(false);
            resultBean.setMessage("同投放类型下的投放名称不能重复添加");
            return resultBean;
        }

        NoviceGiftPutBean info = new NoviceGiftPutBean();
        info.setPutName(bean.getPutName());
        info.setPutType(bean.getPutType());
        info.setPutUrl(bean.getPutUrl());
        info.setDescription(bean.getDescription());

        if (noviceGiftPutBll.insertSelective(info) > 0) {
            resultBean.setIsSuccess(true);
            resultBean.setMessage("添加成功");
            return resultBean;
        } else {
            resultBean.setIsSuccess(false);
            resultBean.setMessage("添加失败");
            return resultBean;
        }
    }

    /**
     * 投放对象更新
     *
     * @param bean 投放对象实体
     * @return
     */
    @RequestMapping(value = "/channel/update", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> novicePutUpdate(NoviceGiftPutViewBean bean) {
        ResultBean<String> resultBean = new ResultBean<>();
        if (bean.getCode() <= 0) {
            resultBean.setIsSuccess(false);
            resultBean.setMessage("投放对象无效");
            return resultBean;
        }

        NoviceGiftPutBean info = noviceGiftPutBll.selectByPrimaryKey(bean.getCode());
        if (info == null) {
            resultBean.setIsSuccess(false);
            resultBean.setMessage("投放对象不存在");
            return resultBean;
        }
        info.setPutUrl(bean.getPutUrl());
        int result = noviceGiftPutBll.updateByPrimaryKey(info);
        if (result > 0) {
            resultBean.setIsSuccess(true);
            resultBean.setMessage("修改成功");
            return resultBean;
        } else {
            resultBean.setIsSuccess(false);
            resultBean.setMessage("修改失败");
            return resultBean;
        }
    }
}
