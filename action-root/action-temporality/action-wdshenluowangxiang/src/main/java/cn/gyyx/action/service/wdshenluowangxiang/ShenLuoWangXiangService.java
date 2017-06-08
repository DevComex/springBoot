/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action-wdshenluowangxiang
 * @作者：lihu
 * @联系方式：lihu@gyyx.cn
 * @创建时间：2017年4月8日 下午4:23:05
 * @版本号：0.0.1
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.service.wdshenluowangxiang;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.po.ActionLotteryLogPO;
import cn.gyyx.action.beans.userregistlog.UserRegistLogBean;
import cn.gyyx.action.beans.wdshenluowangxiang.Constants;
import cn.gyyx.action.beans.wdshenluowangxiang.ShenLuoWangXiangAddressBean;
import cn.gyyx.action.beans.wdshenluowangxiang.ShenLuoWangXiangBean;
import cn.gyyx.action.beans.wdshenluowangxiang.ShenLuoWangXiangReturnBean;
import cn.gyyx.action.bll.lottery.IActionLotteryLogBLL;
import cn.gyyx.action.bll.lottery.impl.ActionLotteryLogBLLImpl;
import cn.gyyx.action.bll.userregistlog.UserRegistLogBLL;
import cn.gyyx.action.bll.wdshenluowangxiang.ShenLuoWangXiangBll;
import cn.gyyx.action.service.mobile.website.CallApi;
import cn.gyyx.action.ui.wdshenluowangxiang.ShenLuoWangXiangController;
import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.log.sdk.GYYXLogger;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * <p>
 * 森罗万象活动页面service
 * </p>
 * 
 * @author lihu
 * @since 0.0.1
 */
public class ShenLuoWangXiangService {

        private static final GYYXLogger LOG = GYYXLoggerFactory.getLogger(ShenLuoWangXiangController.class);
	private ShenLuoWangXiangBll bll = new ShenLuoWangXiangBll();
	// 抽奖日志bll
	private IActionLotteryLogBLL actionLotteryLogBLL = new ActionLotteryLogBLLImpl();
	private UserRegistLogBLL userRegistLogBLL = new UserRegistLogBLL();
	
	/**
	 * 绑定服务器
	 * 
	 * @param serverId
	 * @param serverName
	 * @param userInfo
	 * @param source
	 * @return
	 * @throws Exception 
	 */
	public ResultBean<String> bindServer(int serverId, String serverName, UserInfo userInfo,String source) throws Exception {
		ShenLuoWangXiangBean bean = bll.getUserInfoByUserId(userInfo.getUserId());
		if (bean != null) {
			return new ResultBean<>(false, "用户已有报名数据", null);
		}
		Date date = new Date();
		ShenLuoWangXiangBean bindBean = new ShenLuoWangXiangBean();
		bindBean.setAccount(userInfo.getAccount());
		bindBean.setServerId(serverId);
		bindBean.setServerName(serverName);
		bindBean.setCreateTime(date);
		bindBean.setThinkTime(date);
		bindBean.setUserId(userInfo.getUserId());
		bindBean.setSource(source);
		bll.instert(bindBean);

		return new ResultBean<>(true, "用户报名成功", null);
	}

	/**
	 * 检测用户信息
	 * 
	 * @param userInfo
	 * @return
	 * @throws Exception 
	 */
	public ShenLuoWangXiangReturnBean checkUser(UserInfo userInfo) throws Exception {
		ShenLuoWangXiangBean bean = bll.getUserInfoByUserId(userInfo.getUserId());
		ShenLuoWangXiangReturnBean returnBean = new ShenLuoWangXiangReturnBean();
		returnBean.setBind(false);
		if (bean != null) {
			returnBean.setBind(true);
			returnBean.setUserId(bean.getUserId());
			returnBean.setAccount(this.accountEncrypt(bean.getAccount()));
			returnBean.setCode(bean.getCode());
			returnBean.setServerId(bean.getServerId());
			returnBean.setServerName(bean.getServerName());
			returnBean.setLastNum(bean.getLastNum());
			
			ActionLotteryLogPO po = new ActionLotteryLogPO();
			po.setActivityId(Constants.HD_CODE);
		        po.setUserId(userInfo.getAccount());
			//查询是否中过实物奖品
		        po.setPrizeType("realPrize");
		        returnBean.setRealPrizeFlag(actionLotteryLogBLL.isExsits(po) > 0);
			//查询是否中过邀请函
		        po.setPrizeType("invite");
			returnBean.setInviteFlag(actionLotteryLogBLL.isExsits(po) > 0);
			return returnBean;
		}
		return returnBean;
	}

	/**
	 * 
	 * <p>
	 * 加密显示账号
	 * </p>
	 *
	 * @action lihu 2017年3月30日 上午10:42:26 加密显示账号
	 *
	 * @param account
	 * @return String
	 */
	public String accountEncrypt(String account) {
		if (account == null) {
			return "";
		}
		if (account.length() > 4) {
			return account.substring(0, 2) + "****" + account.substring(account.length() - 2, account.length());
		}
		return account;
	}
	/**
	 * 
	 * <p>
	 * 编辑地址
	 * </p>
	 *
	 * @action lihu 2017年3月30日 上午10:42:26 
	 *
	 * @param account
	 * @return String
	 */
	public ResultBean<String> editInviteAddress(ShenLuoWangXiangAddressBean addressBean, UserInfo userInfo) {
		ShenLuoWangXiangAddressBean selectBean = bll.selectAddressByUserId(userInfo.getUserId());
		
		if(selectBean==null){
			addressBean.setAccount(userInfo.getAccount());
			addressBean.setUserId(userInfo.getUserId());
			bll.insertAddress(addressBean);
			return new ResultBean<>(true ,"添加地址成功",null);
		}else{
			addressBean.setCode(selectBean.getCode());
			bll.updateAddress(addressBean);
			return new ResultBean<>(true ,"修改地址成功",null);
		}
		
	}
	/**
	 * 获取邀请函地址
	 * @param userId
	 * @return
	 */
	public ResultBean<ShenLuoWangXiangAddressBean> getInviteAddress(Integer userId) {
		ShenLuoWangXiangAddressBean bean= bll.getInviteAddress(userId);
		if(bean == null){
		    bean = new ShenLuoWangXiangAddressBean();
		    bean.setAddress("");
		    bean.setPhone("");
		    bean.setName("");
		    bean.setQq("");
		    bean.setEmail("");
		}
		return new ResultBean<>(true ,"获取邀请函地址成功",bean);
	}

    /**
     * 
      * <p>
      *    注册
      * </p>
      *
      * @action
      *    chenglong 2017年4月14日 下午7:35:12 描述
      *
      * @param account
      * @param pwd
      * @param request
      * @return ResultBean<String>
     */
    public ResultBean<String> regist(String account, String pwd, HttpServletRequest request) {
        ResultBean<String> resistResult = new CallApi().register(new String[] { "default",
                "default", "default" }, account, pwd, request, "custom");
        try {
            if (resistResult.getIsSuccess()) {
                UserRegistLogBean ul = new UserRegistLogBean();
                ul.setAccountName(account);
                ul.setAccountType("custom");// 账号注册
                ul.setCreateDate(new Date());
                ul.setRegistSource(Constants.HD_CODE + "");// 活动ID
                userRegistLogBLL.insertRegiste(ul);
            }
        } catch (Exception e) {
            LOG.error("神罗万象活动新增注册用户【" + account + "】插入活动注册信息失败！", e);
        }
        return resistResult;
    }

}
