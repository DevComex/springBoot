package cn.mahjong.service;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import cn.mahjong.beans.ExchangeLog;
import cn.mahjong.beans.ExchangeSummary;
import cn.mahjong.beans.GameUserInfo;
import cn.mahjong.beans.UserBean;
import cn.mahjong.beans.UserInventory;
import cn.mahjong.beans.common.PageResultBean;
import cn.mahjong.beans.common.ResultBean;
import cn.mahjong.bll.ExchangeLogBll;
import cn.mahjong.bll.UserBll;
import cn.mahjong.bll.UserInventoryBll;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import cn.mahjong.service.impl.ExchangeServiceImpl;

public class ExchangeServiceTest {

  private ExchangeService exchangeService;
  private GameService gameService;
  private UserInventoryBll userInventoryBll;
  private ExchangeLogBll exchangeLogBll;
  private UserBll userBll;

  @BeforeClass
  public void setUp() {
    exchangeService = new ExchangeServiceImpl();
    gameService = mock(GameService.class);
    exchangeService.setGameService(gameService);
    exchangeLogBll = mock(ExchangeLogBll.class);
    exchangeService.setExchangeLogBll(exchangeLogBll);
    userInventoryBll = mock(UserInventoryBll.class);
    exchangeService.setUserInventoryBll(userInventoryBll);
    userBll = mock(UserBll.class);
    exchangeService.setUserBll(userBll);
  }

  @Test(description = "兑换-当用户ID小于等于0时返回False")
  public void exchange_whenGameUserIdLessThan0OrEquals0_thenReturnFalse() {
    when(userBll.get(anyInt())).thenReturn(new UserBean());
    when(userInventoryBll.get(anyInt())).thenReturn(null);
    ResultBean<Object> resultBean = exchangeService.exchange(-1, 1, 0);
    Assert.assertFalse(resultBean.getIsSuccess());
  }

  @Test(description = "兑换-当用户游戏ID小于等于0时返回False")
  public void exchange_whenUserIdLessThan0OrEquals0_thenReturnFalse() {
    when(userBll.get(anyInt())).thenReturn(new UserBean());
    when(userInventoryBll.get(anyInt())).thenReturn(null);
    ResultBean<Object> resultBean = exchangeService.exchange(1, -1, 0);
    Assert.assertFalse(resultBean.getIsSuccess());
  }

  @Test(description = "兑换-当充值金额小于等于0时返回False")
  public void exchange_whenAmountLessThan0OrEquals0_thenReturnFalse() {
    when(userBll.get(anyInt())).thenReturn(new UserBean());
    when(userInventoryBll.get(anyInt())).thenReturn(null);
    ResultBean<Object> resultBean = exchangeService.exchange(1, 1, 0);
    Assert.assertFalse(resultBean.getIsSuccess());
  }

  @Test(description = "兑换-当库存不足时返回False")
  public void exchange_whenInventoryNotEnough_thenReturnFalse() {
    when(userBll.get(anyInt())).thenReturn(new UserBean());
    when(userInventoryBll.get(anyInt())).thenReturn(new UserInventory());
    ResultBean<Object> resultBean = exchangeService.exchange(1, 1, 1);
    Assert.assertFalse(resultBean.getIsSuccess());
  }

  @Test(description = "兑换-当库存不足时返回False")
  public void exchange_whenInventoryNotEnough2_thenReturnFalse() {
    when(userBll.get(anyInt())).thenReturn(new UserBean());
    when(userInventoryBll.get(anyInt())).thenReturn(new UserInventory(1, 0, 0));
    ResultBean<Object> resultBean = exchangeService.exchange(1, 1, 1);
    Assert.assertFalse(resultBean.getIsSuccess());
  }

  @Test(description = "兑换-当用户不存在时返回False")
  public void exchange_whenUserNotExist_thenReturnFalse() {
    when(userBll.get(anyInt())).thenReturn(null);
    when(userInventoryBll.get(anyInt())).thenReturn(new UserInventory(1, 0, 0));
    when(gameService.getUserInfo(anyInt())).thenReturn(new ResultBean<>(false, null, null));
    ResultBean<Object> resultBean = exchangeService.exchange(1, 1, 1);
    Assert.assertFalse(resultBean.getIsSuccess());
  }

  @Test(description = "兑换-当游戏用户不存在时返回False")
  public void exchange_whenGameUserNotExist_thenReturnFalse() {
    when(userBll.get(anyInt())).thenReturn(new UserBean());
    when(userInventoryBll.get(anyInt())).thenReturn(new UserInventory(1, 10, 0));
    when(gameService.getUserInfo(anyInt())).thenReturn(new ResultBean<>(false, null, null));
    ResultBean<Object> resultBean = exchangeService.exchange(1, 1, 1);
    Assert.assertFalse(resultBean.getIsSuccess());
  }

  @Test(description = "兑换-当游戏用户不存在时返回False2")
  public void exchange_whenGameUserNotExist_thenReturnFalse2() {
    when(userBll.get(anyInt())).thenReturn(new UserBean());
    when(userInventoryBll.get(anyInt())).thenReturn(new UserInventory(1, 10, 0));
    when(gameService.getUserInfo(anyInt())).thenReturn(new ResultBean<>(true, null, null));
    ResultBean<Object> resultBean = exchangeService.exchange(1, 1, 1);
    Assert.assertFalse(resultBean.getIsSuccess());
  }

  @Test(description = "兑换-操作失败时返回False")
  public void exchange_whenExchangeIsNotSuccess_thenReturnFalse() {
    when(userBll.get(anyInt())).thenReturn(new UserBean());
    when(userInventoryBll.get(anyInt())).thenReturn(new UserInventory(1, 100, 0));
    when(gameService.getUserInfo(anyInt()))
        .thenReturn(new ResultBean<>(true, null, new GameUserInfo()));
    doNothing().when(userInventoryBll).increase(anyInt(), anyInt(), anyInt());
    when(gameService.exchange(anyInt(), anyInt())).thenReturn(new ResultBean<>(false, null,
        null));
    ResultBean<Object> resultBean = exchangeService.exchange(1, 1, 1);
    Assert.assertFalse(resultBean.getIsSuccess());
  }

  @Test(description = "兑换-操作成功时返回True")
  public void exchange_whenExchangeSuccess_thenReturnTrue() {
    when(userBll.get(anyInt())).thenReturn(new UserBean());
    when(userInventoryBll.get(anyInt())).thenReturn(new UserInventory(1, 1, 0));
    when(gameService.getUserInfo(anyInt()))
        .thenReturn(new ResultBean<>(true, null, new GameUserInfo()));
    doNothing().when(userInventoryBll).increase(anyInt(), anyInt(), anyInt());
    when(gameService.exchange(anyInt(), anyInt())).thenReturn(new ResultBean<>(true, null,
        null));
    ResultBean<Object> resultBean = exchangeService.exchange(1, 1, 1);
    Assert.assertTrue(resultBean.getIsSuccess());
  }

  @Test(description = "当数据库查询成功时返回True")
  public void getSummary_whenDBQuerySuccess_thenReturnTrue() {
    when(exchangeLogBll.getSummary(anyInt(), anyInt(), anyObject(), anyObject())).thenReturn(null);
    ResultBean<ExchangeSummary> resultBean = exchangeService.getSummary(1, 1, 1, 1);
    Assert.assertTrue(resultBean.getIsSuccess());
  }

  @Test(description = "当数据库查询成功时返回True")
  public void get_whenDBQuerySuccess_thenReturnTrue() {
    when(exchangeLogBll.get(anyInt(), anyInt(), anyObject(), anyObject(), anyInt())).thenReturn(null);
    PageResultBean<List<ExchangeLog>> resultBean = exchangeService.get(1, 1, 1, 1, 1);
    Assert.assertTrue(resultBean.getIsSuccess());
  }
}
