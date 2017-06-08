/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：
 * @作者：chenpeilan zhouzhongkai
 * @联系方式：chenpeilan@gyyx.cn zhouzhongkai@gyyx.cn
 * @创建时间： 2016年1月20日
 * @版本号：
 * @本类主要用途描述：问道乾坤锁领取礼包活动
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.QianKunLock.QianKunLockModels;
import cn.gyyx.action.beans.novicecard.NoviceParameter;
import cn.gyyx.action.beans.novicecard.ServerInfoBean.Value;
import cn.gyyx.action.beans.wdninestory.ServerBean;
import cn.gyyx.action.bll.novicecard.ActivityConfigBll;
import cn.gyyx.action.bll.qiankunlock.QianKunLockBLL;
import cn.gyyx.action.service.agent.CallWebApiAgent;
import cn.gyyx.action.service.wdqklock.WDQKLockService;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.core.auth.SignedUser;
import cn.gyyx.core.auth.UserInfo;

/**
 * @ClassName: WDQianKunLockGiftsReceived
 * @Description: TODO
 * @author chenpeilan chenpeilan@gyyx.cn
 * @date 2016年1月20日 下午3:08:02
 *
 */
@Controller
@RequestMapping(value = "/QianKunLock")
public class WDQianKunLockGiftsReceived {

	private static final Logger logger = GYYXLoggerFactory
			.getLogger(WDNineLotteryController.class);
	private ActivityConfigBll activityConfigBll = new ActivityConfigBll();
	private WDQKLockService wDQKLockService = new WDQKLockService();
	private static final CallWebApiAgent callWebApiAgent = new CallWebApiAgent();
	private QianKunLockBLL qianKunLockBLL = new QianKunLockBLL();
	@RequestMapping("/index")
	public String index(Model model) {
		return "qiankunlock/reward";
	}
	/**
	 * 
	 * @Title: getServers
	 * @Description: TODO 获取服务器信息
	 * @param @param typeCode
	 * @param @return
	 * @return ResultBean<List<ServerBean>>
	 * @throws
	 */
	@RequestMapping(value = "/getServers")
	@ResponseBody
	public ResultBean<List<ServerBean>> getServers(
			@RequestParam(value = "netType") String typeCode) {
		logger.info("typeCode", typeCode);
		ResultBean<List<ServerBean>> result = new ResultBean<List<ServerBean>>(
				true, "查询服务器失败", null);
		result.setProperties(activityConfigBll.getConfigMessage("问道乾坤锁领取礼包活动")
				.getIsSuccess(),
				activityConfigBll.getConfigMessage("问道乾坤锁领取礼包活动").getMessage(),
				null);
		if (!activityConfigBll.getConfigMessage("问道乾坤锁领取礼包活动").getIsSuccess()) {
			logger.info("result", result);
			return result;
		}
		result.setProperties(true, "查询服务器失败", null);
		List<Value> serversList = callWebApiAgent
				.getAllServerByNetTypeCode(typeCode);
		List<ServerBean> serverList = new ArrayList<ServerBean>();
		for (Value value : serversList) {
			ServerBean server = new ServerBean(value.getCode(),
					value.getServerName());
			serverList.add(server);
		}
		result.setProperties(true, "查询服务器成功", serverList);
		logger.info("result", result);
		return result;
	}

	@RequestMapping(value = "/ReceivePrize")
	@ResponseBody
	public ResultBean<String> getpizes(HttpServletRequest request,
			@RequestParam(value = "serverName") String serverName,
			@RequestParam(value = "serverId") String serverId,
			@RequestParam(value = "password") String password,
			@RequestParam(value = "sn") String sn) {
		ResultBean<String> result = new ResultBean<String>(false, "", "-1");
		// 获取用户cookie
		UserInfo userInfo = SignedUser.getUserInfo(request);
		if (userInfo == null) {
			result = new ResultBean<String>(false, "请登录光宇社区账号参与活动", null);
			return result;
		}
		result.setProperties(activityConfigBll.getConfigMessage("问道乾坤锁领取礼包活动")
				.getIsSuccess(),
				activityConfigBll.getConfigMessage("问道乾坤锁领取礼包活动").getMessage(),
				null);
		if (!activityConfigBll.getConfigMessage("问道乾坤锁领取礼包活动").getIsSuccess()) {
			logger.info("result", result);
			return result;
		}
		logger.info("result", result);
		if (password.equals("")|| sn.equals("")) {
			result = new ResultBean<String>(false, "乾坤锁动态密码或乾坤锁SN不能为空!", "-1");
			return result;
		}
		int userCode = userInfo.getUserId();
		if (userCode <= 0) {
			result = new ResultBean<String>(false, "该账号不存在!", "-1");
			return result;
		}
		QianKunLockModels qianKunLockModels = wDQKLockService.getQianKunLock(
				userCode, sn);
		if (qianKunLockModels.getAccount()!= null) {
			if (qianKunLockBLL.selectBindAcount(userCode) != null) {
				result = new ResultBean<String>(false, "对不起，您的账号已经领取过礼包！", "-1");
				return result;
			} else {
				if (qianKunLockModels.getEKeyCompany().equals("4")) {
					result = new ResultBean<String>(false, "对不起，手机乾坤锁不支持此次活动！",
							"-1");
					return result;
				}
				if (qianKunLockBLL.SelectByEkeySn(sn) != null) {
					result = new ResultBean<String>(false,
							"对不起，您绑定的乾坤锁已经领取过礼包！", "-1");
					return result;
				}
				result = wDQKLockService.unChain(userInfo, qianKunLockModels.getEKeyCompany(), sn, password);
				if(!result.getIsSuccess()){
					result = new ResultBean<String>(false,
							"乾坤锁动态密码或乾坤锁SN错误！", "-1");
					return result;
				}
			}
		}
		 else
         {
			 result = new ResultBean<String>(false,
						"乾坤锁动态密码或乾坤锁SN错误！！", "-1");
				return result;

         }
		boolean isActivity =  wDQKLockService.getIsActivityFromAction(userInfo.getAccount(),serverId);
		if(!isActivity){
			result = new ResultBean<String>(
					false, "您未激活该游戏服务器!","-1");
			return result;
		}
		HashMap<String,Object> map = qianKunLockBLL.addLog(userInfo.getUserId(), userInfo.getAccount(), Integer.valueOf(serverId), serverName, qianKunLockModels.getEkeyType(), sn);
		if((int) map.get("errcode")==-1){
			result = new ResultBean<String>(
					false, "该光宇移动密保已经参与了领奖!","-1");
			return result;
		}
		if((int) map.get("errcode")==-2){
			result = new ResultBean<String>(
					false, "玩家领取过奖品!","-1");
			return result;
		}
		if((int) map.get("errcode")==-3){
			result = new ResultBean<String>(
					false, "执行语句错误!","-1");
			return result;
		}
		int code = (int) map.get("logCode");
		result = wDQKLockService.sendItem(userInfo.getAccount(),
				Integer.valueOf(serverId), sn,code);
		return result;

	}


}
