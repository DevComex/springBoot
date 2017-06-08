/**
  * -------------------------------------------------------------------------
  * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
  * @版权所有：北京光宇在线科技有限责任公司
  * @项目名称：action-service
  * @作者：guoyonggang
  * @联系方式：guoyonggang@gyyx.cn
  * @创建时间：2017年3月1日 上午10:00:53
  * @版本号：0.0.1
  *-------------------------------------------------------------------------
  */
package cn.gyyx.action.service.wechatcharge;

import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Throwables;

import cn.gyyx.action.beans.wechatcharge.ChannelType;
import cn.gyyx.action.beans.wechatcharge.PrizeType;
import cn.gyyx.action.beans.wechatcharge.QueryConditionBean;
import cn.gyyx.action.beans.wechatcharge.WechatChargeBean;
import cn.gyyx.action.bll.config.IHdConfigBLL;
import cn.gyyx.action.bll.config.impl.DefaultHdConfigBLL;
import cn.gyyx.action.bll.lottery.MemcacheUtil;
import cn.gyyx.action.bll.wechatcharge.WechatChargeBLL;
import cn.gyyx.action.common.beans.ResultBean;
import cn.gyyx.core.memcache.XMemcachedClientAdapter;

/**
 * <p>
 * 微信兑换业务操作service
 * </p>
 * 
 * @author guoyonggang
 * @since 0.0.1
 */
public class WechatChargeService {
    private static final Logger logger = LoggerFactory
            .getLogger(WechatChargeService.class);
    private WechatChargeBLL bll = new WechatChargeBLL();
    private IHdConfigBLL hdConfig=new DefaultHdConfigBLL();
    private MemcacheUtil memcacheUtil;
    private XMemcachedClientAdapter memcachedClientAdapter;
    private static final Integer activityId=442;
    // 是否开启昵称判断
    private Boolean isValidateNickName ;
    // 是否开启渠道判断
    private Boolean isValidateChannelName ;
    // 每天兑换次数限制
    private Integer wechatChargeEveryDayLimitTimes ;
    
    //构造函数
    public WechatChargeService(){
        memcacheUtil = new MemcacheUtil();
        memcachedClientAdapter = memcacheUtil.getMemcache();        
    }

    /**
     * 兑换信息导出
     * 
     * @action guoyonggang 2017年3月1日 上午10:02:28 描述
     *
     * @param response
     *            void
     * @throws IOException
     */
    public void exortExcel(HttpServletResponse response,
            QueryConditionBean queryCondition) throws IOException {
        logger.info("WechatChargeService exortExcel begin:params:{}",
            queryCondition);
        // 第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet("中奖信息列表");

        sheet.setColumnWidth(0, 10 * 256);
        sheet.setColumnWidth(1, 10 * 256);
        sheet.setColumnWidth(2, 20 * 256);
        sheet.setColumnWidth(3, 20 * 256);
        sheet.setColumnWidth(4, 20 * 256);
        sheet.setColumnWidth(5, 20 * 256);
        sheet.setColumnWidth(6, 20 * 256);
        sheet.setColumnWidth(7, 10 * 256);
        sheet.setColumnWidth(8, 20 * 256);
        sheet.setColumnWidth(9, 20 * 256);
        sheet.setColumnWidth(10, 10 * 256);
        sheet.setColumnWidth(11, 20 * 256);
        sheet.setColumnWidth(12, 10 * 256);
        sheet.setColumnWidth(13, 20 * 256);
        sheet.setColumnWidth(14, 10 * 256);
        sheet.setColumnWidth(15, 20 * 256);
        sheet.setColumnWidth(16, 15 * 256);
        sheet.setColumnWidth(17, 15 * 256);
        sheet.setColumnWidth(18, 20 * 256);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        HSSFRow row = sheet.createRow((int) 0);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        ArrayList<String> cellList = new ArrayList<String>();
        cellList.add("编号");
        cellList.add("渠道");
        cellList.add("OpenID");
        cellList.add("昵称");
        cellList.add("活动名");
        cellList.add("兑换码");
        cellList.add("奖品名");
        cellList.add("奖品类型");
        cellList.add("卡号");
        cellList.add("密码");
        cellList.add("是否已兑换");
        cellList.add("是否为问道玩家");
        cellList.add("收件人");
        cellList.add("收件人电话");
        cellList.add("收件人地址");
        cellList.add("快递单号");
        cellList.add("中奖时间");
        cellList.add("兑换截止时间");
        cellList.add("兑换时间");

        HSSFCell cell = row.createCell(0);
        for (int i = 0; i < cellList.size(); i++) {
            cell = row.createCell(i);
            cell.setCellValue(cellList.get(i));
            cell.setCellStyle(style);
        }
        // 查询要导出的数据
        List<WechatChargeBean> list = bll
                .queryWechatChargeListForExport(queryCondition);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow((int) i + 1);
            WechatChargeBean chargeInfo = list.get(i);
            logger.info("export excel{} row data：{}", i, chargeInfo);
            // 第四步，创建单元格，并设置值
            row.createCell(0).setCellValue(chargeInfo.getCode());
            row.createCell(1).setCellValue(
                ChannelType.valueOf(chargeInfo.getChannelName()).getName());
            row.createCell(2).setCellValue(
                chargeInfo.getOpenId() == null ? "" : chargeInfo.getOpenId());
            row.createCell(3).setCellValue(chargeInfo.getNickName() == null ? ""
                    : chargeInfo.getNickName());
            row.createCell(4).setCellValue(chargeInfo.getActionName());
            row.createCell(5).setCellValue(chargeInfo.getChargeCode());
            row.createCell(6).setCellValue(chargeInfo.getPrizeName());
            row.createCell(7).setCellValue(
                PrizeType.valueOf(chargeInfo.getPrizeType()).getName());
            row.createCell(8).setCellValue(
                chargeInfo.getCardNo() == null ? "" : chargeInfo.getCardNo());
            row.createCell(9).setCellValue(
                chargeInfo.getCardPwd() == null ? "" : chargeInfo.getCardPwd());
            row.createCell(10).setCellValue(chargeInfo.getIsCharge() == null
                    ? "" : chargeInfo.getIsCharge() ? "是" : "否");
            row.createCell(11).setCellValue(chargeInfo.getIsWendao() == null
                    ? "" : chargeInfo.getIsWendao() ? "是" : "否");
            row.createCell(12)
                    .setCellValue(chargeInfo.getRecipientName() == null ? ""
                            : chargeInfo.getRecipientName());
            row.createCell(13)
                    .setCellValue(chargeInfo.getRecipientPhone() == null ? ""
                            : chargeInfo.getRecipientPhone());
            row.createCell(14)
                    .setCellValue(chargeInfo.getRecipientAddress() == null ? ""
                            : chargeInfo.getRecipientAddress());
            row.createCell(15)
                    .setCellValue(chargeInfo.getExpressNumber() == null ? ""
                            : chargeInfo.getExpressNumber());
            row.createCell(16)
                    .setCellValue(format.format(chargeInfo.getAwardTime()));
            row.createCell(17)
                    .setCellValue(format.format(chargeInfo.getChargeEndTime()));
            row.createCell(18).setCellValue(chargeInfo.getChargeTime() == null
                    ? "" : format2.format(chargeInfo.getChargeTime()));
        }
        // 第六步，将文件存到指定位置
        String fileName = "WechatChargeInfo.xls";
        // 取得输出流
        OutputStream os = response.getOutputStream();
        // 清空输出流
        response.reset();
        response.setHeader("Content-disposition", "attachment; filename="
                + new String(fileName.getBytes("GB2312"), "ISO8859-1"));
        response.setContentType("application/msexcel");
        wb.write(os);
        os.close();
    }

    /**
     * <p>
     * 兑换验证
     * </p>
     *
     * @action guoyonggang 2017年3月2日 下午2:07:21 描述
     *
     * @param chargeBean
     * @return ResultBean<ChargeResultBean>
     */
    public ResultBean<Map<String,Object>> charge(WechatChargeBean chargeBean) {
        ResultBean<Map<String,Object>> result = new ResultBean<>();
        logger.info("WechatChargeService charge begin,params:{}", chargeBean);
        // 参数校验
        result = checkChargeParams(chargeBean);
        logger.debug("WechatChargeService charge checkChargeParams params:{};result:{}",chargeBean,result);
        if (!result.getIsSuccess()) {
            return result;
        }
        result.setIsSuccess(false);
        // 校验缓存兑换次数
        String key = "wechat_charge_" + chargeBean.getOpenId();
        // 获取当前已兑换次数
        String times = memcachedClientAdapter.get(key, String.class);
        logger.info(
            "WechatChargeService get openid:{} charge times success,value:{}",
            chargeBean.getOpenId(), times);

        wechatChargeEveryDayLimitTimes = Integer
                .parseInt(getConfigKey("WechatChargeEveryDayLimitTimes","5"));
        // 判断是否超过了当前设置的限制次数
        if (times != null && Integer.parseInt(times) >= wechatChargeEveryDayLimitTimes) {
            result.setMessage("当天兑换机会已用完");
            return result;
        } 
        if (times == null) {
            int newTimes = 1;
            memcachedClientAdapter.set(key, getUntilDayEndSeconds(),
                String.valueOf(newTimes));
        } else {
            int newTimes = Integer.parseInt(times) + 1;
            memcachedClientAdapter.set(key, getUntilDayEndSeconds(),
                String.valueOf(newTimes));
        }
        // 根据chargeCode获取兑换信息
        String chargeCode = chargeBean.getChargeCode();
        WechatChargeBean chargeInfo = bll
                .queryWechatChargeByChargeCode(chargeCode);
        logger.info(
            "WechatChargeService queryWechatChargeByChargeCode chargeCode:{} success,data:{}",
            chargeCode, chargeInfo);
        // 验证兑换数据
        result = validateData(chargeInfo, chargeBean);
        logger.debug(String.format("WechatChargeService charge validateData params1:%s;params2:%s;result:%s",chargeInfo,chargeBean,result));
        if (!result.getIsSuccess()) {
            return result;
        }
        result.setIsSuccess(false);
        String openId=chargeBean.getOpenId();
        // 判断是否为虚拟奖品 是的话直接兑换
        if (chargeInfo.getPrizeType()
                .equals(PrizeType.VirtualPrize.toString())) {
            // 更新表中数据为已兑换状态
            logger.info("begin vertualPrize charge openId:{}",openId);
            chargeInfo.setIsCharge(true);
            chargeInfo.setIsWendao(chargeBean.getIsWendao());
            chargeInfo.setChargeTime(new Date());
            chargeInfo.setOpenId(openId);
            if (!bll.update(chargeInfo)) {
                logger.info("end vertualPrize charge update error openId:{}",openId);
                result.setMessage("兑换失败，请稍后重试！");
                return result;
            }
        }else{
            // 更新库中的openId
            logger.info("begin realPrize charge openId:{}",openId);
            chargeInfo.setOpenId(openId);
            bll.update(chargeInfo);
        }
        // 返回兑换信息
        result.setIsSuccess(true);
        result.setMessage("兑换成功，请继续！");
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("prizeType", chargeInfo.getPrizeType());
        resultMap.put("prizeName", chargeInfo.getPrizeName());
        resultMap.put("chargeEndTime", chargeInfo.getChargeEndTime());
        resultMap.put("cardNo", chargeInfo.getCardNo());
        resultMap.put("cardPwd", chargeInfo.getCardPwd());
        result.setData(resultMap);
        return result;
    }

    /**
     * <p>
     * 实物需要确认兑换信息，填写收件人信息
     * </p>
     *
     * @action guoyonggang 2017年3月2日 下午5:57:01 描述
     *
     * @param chargeBean
     * @return ResultBean<WechatChargeBean>
     */
    public ResultBean<Map<String,Object>> confirm(WechatChargeBean chargeBean) {
        logger.info("WechatChargeService confirm begin ,data:{}", chargeBean);
        ResultBean<Map<String,Object>> result = new ResultBean<>();
        // 校验基本参数
        result = checkChargeParams(chargeBean);
        logger.debug("WechatChargeService confirm checkChargeParams params:{};result:{}",chargeBean,result);
        if (!result.getIsSuccess()) {
            return result;
        }
        // 校验收件人信息
        result = checkConfirmParams(chargeBean);
        logger.debug("WechatChargeService confirm checkConfirmParams params:{};result:{}",chargeBean,result);
        if (!result.getIsSuccess()) {
            return result;
        }
        result.setIsSuccess(false);
        // 根据兑换码获取兑换信息
        String chargeCode = chargeBean.getChargeCode();
        WechatChargeBean chargeInfo = bll
                .queryWechatChargeByChargeCode(chargeCode);
        logger.info(
            "WechatChargeService queryWechatChargeByChargeCode chargeCode:{} success,data:{}",
            chargeCode, chargeInfo);
        if (chargeInfo == null) {
            result.setMessage("兑换码无效");
            return result;
        }
        // 验证兑换数据
        result = validateData(chargeInfo, chargeBean);
        logger.debug(String.format("WechatChargeService confirm validateData params1:%s;params2:%s;result:%s",chargeInfo,chargeBean,result));
        if (!result.getIsSuccess()) {
            return result;
        }
        result.setIsSuccess(false);
        String openId=chargeBean.getOpenId();
        //如果为实物需要判断一下OpenId是否与库中存储的一致
        if(chargeInfo.getPrizeType().equals(PrizeType.RealPrize.toString())){
            logger.info("RealPrize confirm checkout openId input_openId:{};db_openId:{}",openId,chargeInfo.getOpenId());
            if(!chargeInfo.getOpenId().equals(openId)){
                result.setMessage("请用同一微信号领取奖品！");
                return result;
            }
        }
        // 更新兑换信息
        chargeInfo.setIsCharge(true);
        chargeInfo.setIsWendao(chargeBean.getIsWendao());
        chargeInfo.setRecipientName(chargeBean.getRecipientName());
        chargeInfo.setRecipientPhone(chargeBean.getRecipientPhone());
        chargeInfo.setRecipientAddress(chargeBean.getRecipientAddress());
        chargeInfo.setChargeTime(new Date());
        if (!bll.update(chargeInfo)) {
            logger.info("confirm charge fail openId:{}",openId);
            result.setMessage("兑换失败，请稍后重试！");
            return result;
        }
        result.setIsSuccess(true);
        result.setMessage("兑换成功");
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("prizeName", chargeInfo.getPrizeName());
        result.setData(resultMap);
        return result;
    }

    /**
     * <p>
     * 校验兑换数据
     * </p>
     *
     * @action guoyonggang 2017年3月3日 上午9:43:09 描述
     *
     * @param chargeInfo
     * @param parms
     * @return ResultBean<WechatChargeBean>
     */
    private ResultBean<Map<String,Object>> validateData(
            WechatChargeBean dbChargeInfo, WechatChargeBean inputChargeInfo) {
        ResultBean<Map<String,Object>> result = new ResultBean<>();
        wechatChargeEveryDayLimitTimes = Integer
                .parseInt(getConfigKey("WechatChargeEveryDayLimitTimes","5"));
        isValidateNickName = Boolean
                .parseBoolean(getConfigKey("IsValidateNickName","true"));
        isValidateChannelName = Boolean
                .parseBoolean(getConfigKey("IsValidateChannelName","true"));
        logger.info(String.format("start validate chargeInfo dbChargeInfo:%s,inputChargeInfo:%s,limitTime:%s,isValNickName:%s,isValChannleName:%s", 
            dbChargeInfo,
            inputChargeInfo,
            wechatChargeEveryDayLimitTimes,
            isValidateNickName,
            isValidateChannelName));
        if (dbChargeInfo == null) {
            result.setMessage("兑换码无效");
            return result;
        }
        // 判断截止时间
        Date currentDate = new Date();
        if (currentDate.compareTo(dbChargeInfo.getChargeEndTime()) > 0) {
            result.setMessage("兑换码已过期");
            return result;
        }
        // 判断是否已兑换
        if (dbChargeInfo.getIsCharge()) {
            result.setMessage("兑换码已被兑换");
            return result;
        }

        // 是否开启判断昵称
        if (isValidateNickName) {
            // 判断昵称是否相同
            if (!dbChargeInfo.getNickName().equals(inputChargeInfo.getNickName())) {
                result.setMessage("昵称不正确");
                return result;
            }
        }
        // 是否开启判断渠道
        if (isValidateChannelName) {
            // 判断渠道是否相同
            if (!dbChargeInfo.getChannelName().equals(inputChargeInfo.getChannelName())) {
                result.setMessage("渠道选择不正确");
                return result;
            }
        }
        result.setIsSuccess(true);
        result.setMessage("兑换码有效");
        return result;
    }

    /**
     * <p>
     * 校验收件人信息填写
     * </p>
     *
     * @action guoyonggang 2017年3月2日 下午5:57:29 描述
     *
     * @param chargeBean
     * @return ResultBean<WechatChargeBean>
     */
    private ResultBean<Map<String,Object>> checkConfirmParams(
            WechatChargeBean chargeBean) {
        ResultBean<Map<String,Object>> result = new ResultBean<>();
        result.setIsSuccess(false);
        String recipientName = chargeBean.getRecipientName();
        if (StringUtils.isEmpty(recipientName)) {
            result.setMessage("收件人姓名不能为空");
            return result;
        }
        String recipientPhone = chargeBean.getRecipientPhone();
        if (StringUtils.isEmpty(recipientPhone)) {
            result.setMessage("收件人电话不能为空");
            return result;
        }
        String recipientAddress = chargeBean.getRecipientPhone();
        if (StringUtils.isEmpty(recipientAddress)) {
            result.setMessage("收件人地址不能为空");
            return result;
        }
        result.setIsSuccess(true);
        return result;
    }

    /**
     * <p>
     * 兑换信息是否完整
     * </p>
     *
     * @action guoyonggang 2017年3月3日 上午9:43:57 描述
     *
     * @param chargeBean
     * @return ResultBean<WechatChargeBean>
     */
    private ResultBean<Map<String,Object>> checkChargeParams(
            WechatChargeBean chargeBean) {
        ResultBean<Map<String,Object>> result = new ResultBean<>();
        result.setIsSuccess(false);
        String channelName = chargeBean.getChannelName();
        if (StringUtils.isEmpty(channelName)) {
            result.setMessage("请选择渠道");
            return result;
        }
        if (StringUtils.isEmpty(chargeBean.getChargeCode())) {
            result.setMessage("兑换码不能为空");
            return result;
        }
        if (StringUtils.isEmpty(chargeBean.getNickName())) {
            result.setMessage("昵称不能为空");
            return result;
        }
        if (chargeBean.getIsWendao() == null) {
            result.setMessage("请选择是否为问道玩家");
            return result;
        }
        if (StringUtils.isEmpty(chargeBean.getOpenId())) {
            result.setMessage("请关注光宇游戏每日推送服务号参与活动！");
            return result;
        }
        result.setIsSuccess(true);
        return result;
    }

    /**
     * <p>
     * 距离一天结束还有多少秒
     * </p>
     *
     * @action guoyonggang 2017年3月3日 上午9:49:22 描述
     *
     * @return Integer
     */
    private Integer getUntilDayEndSeconds() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date nowDate = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(nowDate);
            calendar.add(Calendar.DATE, 1);
            Date tempDate;
            tempDate = sdf.parse(sdf.format(calendar.getTime()));
            return (int) ((tempDate.getTime() - nowDate.getTime()) / 1000);
        } catch (ParseException e) {
            logger.warn("getUntilDayEndSeconds:{}"
                    + Throwables.getStackTraceAsString(e));
            return 60 * 60 * 24;
        }
    }
    
    private String getConfigKey(String key,String defaultValue){
        Object configValue=hdConfig.getConfigByKey(activityId, key);
        if(configValue==null || StringUtils.isEmpty(configValue.toString())){
            return defaultValue;
        }
        return configValue.toString();
    }
}
