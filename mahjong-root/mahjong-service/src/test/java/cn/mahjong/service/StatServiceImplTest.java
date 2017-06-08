/**
  * -------------------------------------------------------------------------
  * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
  * @版权所有：北京光宇在线科技有限责任公司
  * @项目名称：mahjong-service
  * @作者：chenwen
  * @联系方式：chenwen@gyyx.cn
  * @创建时间：2017年5月26日 下午2:16:16
  * @版本号：0.0.1
  *-------------------------------------------------------------------------
  */
package cn.mahjong.service;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import cn.mahjong.beans.UserBean;
import cn.mahjong.beans.UserRole;
import cn.mahjong.beans.common.ResultBean;
import cn.mahjong.bll.RechargeLogBll;
import cn.mahjong.bll.UserBll;
import cn.mahjong.service.impl.StatServiceImpl;
import cn.mahjong.service.impl.UserServiceImpl;

/**
  * <p>
  *   UserServiceImplTest描述
  * </p>
  *  
  * @since 0.0.1
  */
public class StatServiceImplTest {
    private RechargeLogBll rechargeLogBll;
    private StatServiceImpl statServiceImpl;
    
    @BeforeClass
    public void setUp(){
      rechargeLogBll = mock(RechargeLogBll.class);
      statServiceImpl = new StatServiceImpl();
      statServiceImpl.setRechargeLogBll(rechargeLogBll);
    }
    
    @Test(description = "当查询个人进货记录时，返回数据")
    public void personPurchaseSum_whenUserId_thenRerurn(){
        String start = "2017-05-23"; 
        String end = "2017-05-28"; 
        int userId = 1;
        int pageIndex = 1;
        int pageSize = 1;
        String result = "查询个人进货记录成功";
        
        when(rechargeLogBll.selectCountByOperatorId(start, end, userId)).thenReturn(null);
        
        String message = statServiceImpl.personPurchaseSum(start, end, pageIndex, pageSize, userId).getMessage();
        Assert.assertEquals(message, result);
        
    }
   
    
    @Test(description = "当查询个人销售记录时，返回数据")
    public void personSaleSum_whenUserId_thenRerurn(){
      String start = "2017-05-23"; 
      String end = "2017-05-28"; 
      int userId = 1;
      int pageIndex = 1;
      int pageSize = 1;
      String result = "查询个人销售记录成功";
      
      when(rechargeLogBll.selectCountByOperatorId(start, end, userId)).thenReturn(null);
      
      String message = statServiceImpl.personSaleSum(start, end, pageIndex, pageSize, userId).getMessage();
      Assert.assertEquals(message, result);
      
    }
    
    
    
 
   /* 
    @Test(description = "当发送成功时，返回true")
    public void sendFindPwdSms_whenSendSuccess_thenRerurnFalse(){
        UserBean userBean = new UserBean();
        userBean.setPhone("111111111");
        when(userBll.get(anyString())).thenReturn(userBean);
        when(smsService.send(anyString(), anyString())).thenReturn(new ResultBean<>(true,"发送成功",null));
        
        ResultBean<Integer> resultBean = userServiceImpl.sendFindPwdSms("");
        Assert.assertEquals(true, resultBean.getIsSuccess());
        Assert.assertEquals("发送成功", resultBean.getMessage());
    }*/
    
}
