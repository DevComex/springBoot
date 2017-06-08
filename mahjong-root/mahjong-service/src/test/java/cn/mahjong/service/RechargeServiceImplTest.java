package cn.mahjong.service;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import cn.mahjong.beans.RechargeLog;
import cn.mahjong.beans.RechargeSummary;
import cn.mahjong.beans.UserBean;
import cn.mahjong.beans.UserInventory;
import cn.mahjong.beans.common.PageResultBean;
import cn.mahjong.beans.common.ResultBean;
import cn.mahjong.bll.RechargeLogBll;
import cn.mahjong.bll.UserBll;
import cn.mahjong.bll.UserInventoryBll;
import cn.mahjong.service.impl.RechargeServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class RechargeServiceImplTest {

  private RechargeServiceImpl service;
  private UserInventoryBll userInventoryBll;
  private UserBll userBll;
  private RechargeLogBll logBll;
  private RechargeLogBll rechargeLogBll;

  @BeforeClass
  public void setUp() {
    service = new RechargeServiceImpl();
    userInventoryBll = mock(UserInventoryBll.class);
    service.setUserInventoryBll(userInventoryBll);
    userBll = mock(UserBll.class);
    service.setUserBll(userBll);
    logBll = mock(RechargeLogBll.class);
    service.setRechargeLogBll(logBll);
    rechargeLogBll = mock(RechargeLogBll.class);
    service.setRechargeLogBll(rechargeLogBll);
  }

  @Test(description = "当Amount与Gift都等于0时返回False")
  public void recharge_whenAmountEquals0_thenReturnFalse() {
    ResultBean<Void> resultBean = service.recharge(0, 0, 0, 0);
    Assert.assertFalse(resultBean.getIsSuccess());
  }

  @Test(description = "当Amount小于0时返回False")
  public void recharge_whenAmountLessThan0_thenReturnFalse() {
    ResultBean<Void> resultBean = service.recharge(-1, 0, 0, 0);
    Assert.assertFalse(resultBean.getIsSuccess());
  }

  @Test(description = "当Gift小于0时返回False")
  public void recharge_whenGiftLessThan0_thenReturnFalse() {
    ResultBean<Void> resultBean = service.recharge(1, -1, 0, 0);
    Assert.assertFalse(resultBean.getIsSuccess());
  }

  @Test(description = "当UserId小于0时返回False")
  public void recharge_whenUserIdLessThan0_thenReturnFalse() {
    ResultBean<Void> resultBean = service.recharge(1, 0, -1, 0);
    Assert.assertFalse(resultBean.getIsSuccess());
  }

  @Test(description = "当UserId等于0时返回False")
  public void recharge_whenUserIdEquals0_thenReturnFalse() {
    ResultBean<Void> resultBean = service.recharge(1, 0, 0, 0);
    Assert.assertFalse(resultBean.getIsSuccess());
  }

  @Test(description = "当UserId小于0时返回False")
  public void recharge_whenOperatorIdLessThan0_thenReturnFalse() {
    ResultBean<Void> resultBean = service.recharge(1, 0, 1, -1);
    Assert.assertFalse(resultBean.getIsSuccess());
  }

  @Test(description = "当UserId等于0时返回False")
  public void recharge_whenOperatorIdEquals0_thenReturnFalse() {
    ResultBean<Void> resultBean = service.recharge(1, 0, 1, 0);
    Assert.assertFalse(resultBean.getIsSuccess());
  }

  @Test(description = "当用户不存在时返回False")
  public void recharge_whenUserNotExist_thenReturnFalse() {
    when(userBll.get(anyInt())).thenReturn(null);
    ResultBean<Void> resultBean = service.recharge(1, 0, 1, 1);
    Assert.assertFalse(resultBean.getIsSuccess());
  }

  @Test(description = "当Operator不是User上级时返回False")
  public void recharge_whenOperatorIsNotInUsersParentUsers_thenReturnFalse() {
    UserBean user = new UserBean();
    user.setCode(1);
    when(userBll.get(anyInt())).thenReturn(user);
    when(userBll.getParentUsers(anyInt())).thenReturn(new ArrayList<>());
    ResultBean<Void> resultBean = service.recharge(1, 0, 1, 1);
    Assert.assertFalse(resultBean.getIsSuccess());
  }

  @Test(description = "当库存不足时返回False")
  public void recharge_whenInventoryNotEnoughReturnFalse() {
    UserBean user = new UserBean();
    user.setCode(1);
    when(userBll.get(anyInt())).thenReturn(user);
    ArrayList<UserBean> parentUsers = new ArrayList<>();
    UserBean parent = new UserBean();
    parent.setCode(1);
    parentUsers.add(parent);
    when(userBll.getParentUsers(anyInt())).thenReturn(parentUsers);
    UserInventory inventory = new UserInventory();
    inventory.setInventory(0);
    inventory.setGiftInventory(0);
    when(userInventoryBll.get(anyInt())).thenReturn(inventory);
    ResultBean<Void> resultBean = service.recharge(1, 0, 1, 1);
    Assert.assertFalse(resultBean.getIsSuccess());
  }

  @Test(description = "当库存充足时返回True")
  public void recharge_whenInventoryIsEnoughReturnTrue() {
    UserBean user = new UserBean();
    user.setCode(1);
    when(userBll.get(anyInt())).thenReturn(user);
    ArrayList<UserBean> parentUsers = new ArrayList<>();
    UserBean parent = new UserBean();
    parent.setCode(1);
    parentUsers.add(parent);
    when(userBll.getParentUsers(anyInt())).thenReturn(parentUsers);
    UserInventory inventory = new UserInventory();
    inventory.setInventory(10);
    inventory.setGiftInventory(10);
    when(userInventoryBll.get(anyInt())).thenReturn(inventory);
    doNothing().when(userInventoryBll).increase(anyInt(), anyInt(), anyInt());
    doNothing().when(rechargeLogBll).insert(anyObject());
    doNothing().when(userInventoryBll).decrease(anyInt(), anyInt());
    ResultBean<Void> resultBean = service.recharge(1, 0, 1, 1);
    Assert.assertTrue(resultBean.getIsSuccess());
  }

  @Test(description = "当数据库查询成功时返回True")
  public void getSummary_whenDBQuerySuccess_thenReturnTrue() {
    when(logBll.getSummary(anyInt(), anyObject(), anyObject())).thenReturn(null);
    ResultBean<RechargeSummary> resultBean = service.getSummary(1, 1, 1);
    Assert.assertTrue(resultBean.getIsSuccess());
  }

  @Test(description = "当数据库查询成功时返回True")
  public void get_whenDBQuerySuccess_thenReturnTrue() {
    when(logBll.get(anyInt(), anyObject(), anyObject(), anyInt())).thenReturn(null);
    PageResultBean<List<RechargeLog>> resultBean = service.get(1, 1, 1, 1);
    Assert.assertTrue(resultBean.getIsSuccess());
  }
}
