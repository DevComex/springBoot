/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action-wdshenluowanxiang
 * @作者：chenglong
 * @联系方式：chenglong@gyyx.cn
 * @创建时间：2017年3月12日 下午4:23:05
 * @版本号：0.0.1
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.ui.wdshenluowangxiang;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.enums.ActivityStatus;
import cn.gyyx.action.beans.lottery.UserLotteryBean;
import cn.gyyx.action.beans.lottery.po.ActionLotteryLogPO;
import cn.gyyx.action.beans.lottery.po.ActionPrizesAddressPO;
import cn.gyyx.action.beans.wdshenluowangxiang.Constants;
import cn.gyyx.action.beans.wdshenluowangxiang.ShenLuoWangXiangLotteryAddressModel;
import cn.gyyx.action.bll.config.impl.DefaultHdConfigBLL;
import cn.gyyx.action.service.wdshenluowangxiang.ShenLuoWangXiangLotteryService;
import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

import com.google.common.base.Throwables;

/**
 * 
  * <p>
  *   ShenLuoLotteryController 抽奖控制层
  * </p>
  *  
  * @author chenglong
  * @since 0.0.1
 */
@Controller
@RequestMapping(value = "/wdshenluowangxiang")
public class ShenLuoWangXiangLotteryController extends cn.gyyx.action.service.BaseController{
    private static final Logger LOG = GYYXLoggerFactory
            .getLogger(ShenLuoWangXiangLotteryController.class);
    //抽奖service
    private ShenLuoWangXiangLotteryService lotteryService = new ShenLuoWangXiangLotteryService();
    
    
    /**
     * 
      * <p>
      *    抽奖实现
      * </p>
      *
      * @action
      *    chenglong 2017年4月9日 下午2:42:11 抽奖实现
      *
      * @param request
      * @param openId
      * @return ResultBean<UserLotteryBean>
     */
    @RequestMapping(value = "/lottery", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<UserLotteryBean> doLottery(HttpServletRequest request) {
        ResultBean<UserLotteryBean> result = null;
        try {
            // 判断用户是否登录
            UserInfo userInfo = cn.gyyx.core.auth.SignedUser
                    .getUserInfo(request);
            if (userInfo == null) {
                return new ResultBean<>(false,Constants.NOLOGIN_PROMPT,null);
            }
            // 判断活动是否开始或结束
            ActivityStatus status = new DefaultHdConfigBLL()
                        .getStatus(Constants.HD_CODE);
            if (status.getCode() != ActivityStatus.IS_NORMAL.getCode()) {
                return new ResultBean<>(false,status.getMsg(),null);
            }
            // 进行抽奖
            result = lotteryService.doLottery(userInfo,
                this.getIp(request));
            return result;
        } catch (Exception e) {
            LOG.error(Constants.HD_NAME + "_用户抽奖时失败,错误堆栈：{}",
                Throwables.getStackTraceAsString(e));
            return new ResultBean<>(false,Constants.FRIENDLY_PROMPT,null);
        }
    }
    
    /**
    * <p>
    *     获取我的中奖纪录
    * </p>
    *
    * @action chenglong 2017年3月22日 下午2:11:23 描述
    *
    * @return ResultBean<String>
    */
   @RequestMapping(value = "/myprize", method = RequestMethod.GET)
   @ResponseBody
   public ResultBean<List<ActionLotteryLogPO>> getMyPrize(HttpServletRequest request) {
       try {
           // 判断用户是否登录
           UserInfo userInfo = cn.gyyx.core.auth.SignedUser
                   .getUserInfo(request);
           if (userInfo == null) {
               return new ResultBean<>(false,Constants.NOLOGIN_PROMPT,null);
           }
           // 判断活动是否开始或结束
           ActivityStatus status = new DefaultHdConfigBLL()
                       .getStatus(Constants.HD_CODE);
           if (status.getCode() != ActivityStatus.IS_NORMAL.getCode()) {
               return new ResultBean<>(false,status.getMsg(),null);
           }
           List<ActionLotteryLogPO> myPrizeList = lotteryService
                   .getMyPrize(userInfo);
           return new ResultBean<>(true,"查询成功",myPrizeList);
       } catch (Exception e) {
           LOG.error(Constants.HD_NAME + "_获取我的中奖纪录时失败,错误堆栈：{}",
               Throwables.getStackTraceAsString(e));
           return new ResultBean<>(false,Constants.FRIENDLY_PROMPT,null);
       }
   }
   
   /**
    * <p>
    *    中奖后添加地址信息
    * </p>
    *
    * @action chenglong 2017年3月22日 下午12:56:59 描述
    *
    * @param request
    * @param params
    * @return ResultBean<String>
    */
   @RequestMapping(value = "/lottery/addr/update", method = RequestMethod.POST)
   @ResponseBody
   public ResultBean<String> updateAddress(HttpServletRequest request,
           @Valid ShenLuoWangXiangLotteryAddressModel params, BindingResult br) {
       try {
           if (br.hasErrors()) {
               return new ResultBean<>(false,br.getFieldError().getDefaultMessage(),null);
           }
           // 判断用户是否登录
           UserInfo userInfo = cn.gyyx.core.auth.SignedUser
                   .getUserInfo(request);
           if (userInfo == null) {
               return new ResultBean<>(false,Constants.NOLOGIN_PROMPT,null);
           }
           // 判断活动是否开始或结束
           ActivityStatus status = new DefaultHdConfigBLL()
                       .getStatus(Constants.HD_CODE);
           if (status.getCode() != ActivityStatus.IS_NORMAL.getCode()) {
               return new ResultBean<>(false,status.getMsg(),null);
           }
           ActionPrizesAddressPO po = new ActionPrizesAddressPO();
           po.setUserName(params.getName());
           po.setUserPhone(params.getPhone());
           po.setUserEmail(params.getEmail());
           po.setUserAddress(params.getAddress());
           po.setQq(params.getQq());
           lotteryService.putAddress(po,userInfo);
           return new ResultBean<>(true,"操作成功",null);
       } catch (Exception e) {
           LOG.error(Constants.HD_NAME + "_添加用户中奖地址信息时失败,错误堆栈：{}",
               Throwables.getStackTraceAsString(e));
           return new ResultBean<>(false,Constants.FRIENDLY_PROMPT,null);
       }
   }
   
   /**
    * <p>
    *    查询用户的地址信息
    * </p>
    *
    * @action chenglong 2017年3月16日 下午5:09:19 描述
    *
    * @param request
    * @return ResultBean<AddressBean>
    */
   @RequestMapping(value = "/lottery/addr/view", method = RequestMethod.GET)
   @ResponseBody
   public ResultBean<ShenLuoWangXiangLotteryAddressModel> getAddress(HttpServletRequest request) {
       try {
           // 判断用户是否登录
           UserInfo userInfo = cn.gyyx.core.auth.SignedUser
                   .getUserInfo(request);
           if (userInfo == null) {
               return new ResultBean<>(false,Constants.NOLOGIN_PROMPT,null);
           }
           // 判断活动是否开始或结束
           ActivityStatus status = new DefaultHdConfigBLL()
                       .getStatus(Constants.HD_CODE);
           if (status.getCode() != ActivityStatus.IS_NORMAL.getCode()) {
               return new ResultBean<>(false,status.getMsg(),null);
           }

           ActionPrizesAddressPO addressPo = lotteryService.getAddress(userInfo.getAccount());
           ShenLuoWangXiangLotteryAddressModel result = new ShenLuoWangXiangLotteryAddressModel();
           if (addressPo == null) {
               result.setAddress("");
               result.setPhone("");
               result.setName("");
               result.setQq("");
               result.setEmail("");
               return new ResultBean<>(true,"您还没有填写地址！",result);
           } 
           result.setAddress(addressPo.getUserAddress());
           result.setPhone(addressPo.getUserPhone());
           result.setName(addressPo.getUserName());
           result.setQq(addressPo.getQq());
           result.setEmail(addressPo.getUserEmail());
           return new ResultBean<>(true,"查询成功！",result);
       } catch (Exception e) {
           LOG.error(Constants.HD_NAME + "_查询用户中奖地址信息时失败,错误堆栈：{}",
               Throwables.getStackTraceAsString(e));
           return new ResultBean<>(false,Constants.FRIENDLY_PROMPT,null);
       }
   }
}
