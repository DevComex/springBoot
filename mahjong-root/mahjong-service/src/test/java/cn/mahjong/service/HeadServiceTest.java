package cn.mahjong.service;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.beust.jcommander.internal.Lists;

import cn.mahjong.beans.CookieUserInfo;
import cn.mahjong.beans.UserBean;
import cn.mahjong.beans.UserInventory;
import cn.mahjong.beans.UserRole;
import cn.mahjong.beans.common.ResultBean;
import cn.mahjong.bll.UserBll;
import cn.mahjong.bll.UserInventoryBll;
import cn.mahjong.redis.bll.SMSBll;
import cn.mahjong.service.impl.HeadServiceImpl;

public class HeadServiceTest {

  private HeadServiceImpl headService;
  private UserBll userBll;
  private UserInventoryBll cardBll;
  private SMSBll smsBll;

  @BeforeClass
  public void setUp() {

    headService = new HeadServiceImpl();
    userBll = mock(UserBll.class);
    headService.setUserBll(userBll);
    cardBll = mock(UserInventoryBll.class);
    headService.setUserInventoryBll(cardBll);
    smsBll = mock(SMSBll.class);
    headService.setSMSBll(smsBll);
  }

  @Test(description = "获取下级局头列表-为局头填充余额")
  public void getHeads_whenGetList_thenReturnList() {
    UserBean hasInventory = new UserBean();
    hasInventory.setCode(1);
    UserBean noInventory = new UserBean();
    noInventory.setCode(2);
    when(userBll.getSubList(anyInt(), anyString(), anyInt(), anyInt()))
        .thenReturn(Lists.newArrayList(hasInventory, noInventory));
    when(cardBll.get(1)).thenReturn(new UserInventory());

    List<UserBean> result = headService.getHeads(0, 1, 20);

    Assert.assertEquals(result.size(), 2);
    Assert.assertEquals(result.get(0).getCardInventory(), 0);
    Assert.assertEquals(result.get(1).getCardInventory(), 0);
  }

  @Test(description = "获取指定下级局头-当用户不存在时返回 null")
  public void getHead_whenUserNotExist_thenReturnNull() {
    when(userBll.get(anyString())).thenReturn(null);

    Assert.assertNull(headService.getHead(0, "notexist"));
  }

  @Test(description = "获取指定下级局头-当用户并非下级时返回 null")
  public void getHead_whenUserNotParent_thenReturnNull() {
    UserBean user = new UserBean();
    user.setParentId(1);
    when(userBll.get(anyString())).thenReturn(user);

    Assert.assertNull(headService.getHead(0, "notchild"));
  }

  @Test(description = "获取指定下级局头-当用户状态与要求不符时返回 null")
  public void getHead_whenUserStatusNotMatch_thenReturnNull() {

    UserBean normal = new UserBean();
    normal.setCode(1);
    normal.setStatus("normal");
    when(userBll.get("normal")).thenReturn(normal);
    UserBean blocked = new UserBean();
    normal.setCode(1);
    blocked.setStatus("blocked");
    when(userBll.get("blocked")).thenReturn(blocked);

    Assert.assertNotNull(headService.getHead(0, "normal"));
    Assert.assertNull(headService.getHead(0, "blocked"));
  }

  @Test(description = "创建局头-当手机号已被注册为帐号时返回失败")
  public void createHead_whenPhoneDuplicate_thenReturnFalse() {

    CookieUserInfo parent = new CookieUserInfo();
    parent.setRole(UserRole.SALESMAN.getValue());
      
    UserBean user = new UserBean();
    when(userBll.get(anyString())).thenReturn(user);

    UserBean newHead = new UserBean();
    newHead.setPhone("18618161929");
    ResultBean<String> result = headService.createHead(newHead, parent, null);

    Assert.assertFalse(result.getIsSuccess());
  }

  @Test(description = "创建局头-当手机验证码验证失败时返回失败")
  public void createHead_whenVerifyCodeWrong_thenReturnFalse() {

    CookieUserInfo parent = new CookieUserInfo();
    parent.setRole(UserRole.SALESMAN.getValue());
    
    when(userBll.get(anyString())).thenReturn(null);
    when(smsBll.validate(anyString(), anyString())).thenReturn(false);

    UserBean newHead = new UserBean();
    newHead.setPhone("18618161929");
    ResultBean<String> result = headService.createHead(newHead, parent, "wrong");

    Assert.assertSame(result.getIsSuccess(), false);
  }

  @Test(description = "创建局头-创建成功")
  public void createHead_whenInsertSuccess_thenReturnTrue() {
    
    CookieUserInfo parent = new CookieUserInfo();
    parent.setRole(UserRole.SALESMAN.getValue());
    
    UserBean newHead = new UserBean();
    newHead.setPhone("18618161929");

    when(smsBll.validate(anyString(), anyString())).thenReturn(true);
    when(userBll.insert(newHead)).then(new Answer<Boolean>() {

      @Override
      public Boolean answer(InvocationOnMock invocation) throws Throwable {
        invocation.getArgumentAt(0, UserBean.class).setCode(1);
        return true;
      }
    });

    ResultBean<String> result = headService.createHead(newHead, parent, "rightCode");

    Assert.assertTrue(result.getIsSuccess());
    Assert.assertNotEquals(newHead.getCode(), 0);
  }

  @Test(description = "当更新成功时返回True")
  public void update_whenDBUpdateSuccess_thenReturnTrue() {
    doNothing().when(userBll).updateInfo(anyObject());
    doNothing().when(userBll).updateHeadInfo(anyObject());
    ResultBean<Void> resultBean = headService.update(1, 1, "111111", "asd", "asd", "asd", "asd");
    Assert.assertTrue(resultBean.getIsSuccess());
  }
}
