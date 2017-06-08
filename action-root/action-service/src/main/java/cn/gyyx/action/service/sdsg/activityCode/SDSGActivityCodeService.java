package cn.gyyx.action.service.sdsg.activityCode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.wink.client.ClientResponse;
import org.apache.wink.client.RestClient;
import org.slf4j.Logger;
import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.ContrParmBean;
import cn.gyyx.action.beans.lottery.PrizeBean;
import cn.gyyx.action.beans.lottery.UserInfoBean;
import cn.gyyx.action.beans.lottery.UserLotteryBean;
import cn.gyyx.action.beans.wd9yeardatavideo.PresentLogBean;
import cn.gyyx.action.bll.lottery.UserLotteryBll;
import cn.gyyx.action.bll.newLottery.NewUserLotteryBll;
import cn.gyyx.action.bll.novicecard.GetMD5PasswordBLL;
import cn.gyyx.action.bll.sdsg.activityCode.SDSGPresentLogBLL;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.action.service.newLottery.LotteryException;
import cn.gyyx.action.service.newLottery.NewlotteryService;
import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.core.security.MD5;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import net.sf.json.JSONObject;

/**
 * @Description: 神道三国抢激活码活动-业务层
 * @author lizhihai
 * @date 2016年11月07日
 */
public class SDSGActivityCodeService {
	private static final String SERVLET_POST = "POST";
	private SDSGPresentLogBLL presentLogBLL = new SDSGPresentLogBLL();
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final int ACTIONCODE = 405;
	private static final Logger LOG = GYYXLoggerFactory.getLogger(SDSGActivityCodeService.class);
	private NewlotteryService lotteryService = new NewlotteryService();
	private GetMD5PasswordBLL getMD5PasswordBll = new GetMD5PasswordBLL();
	public static final int default_day_lotteryTime=3;
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}

	/**
	 * 抽奖
	 */
	public ResultBean<UserLotteryBean> lottery(int actionCode, String lotteryType, UserInfo userInfo) {
		ResultBean<UserLotteryBean> lotteryResult = new ResultBean<>();
		String account = userInfo.getAccount(); // 用户账号
		int userId = userInfo.getUserId();// 用户账号id
		String loginIp = userInfo.getLoginIP();// 用户ip
		ContrParmBean controParm = new UserLotteryBll().getContrParm(actionCode); // 传入活动编号活动编号
		Date nowDate = new Date();
		NewUserLotteryBll userLotteryBll = new NewUserLotteryBll();
		if (nowDate.getTime() < controParm.getActivityStart().getTime()) {
			return new ResultBean<>(false, "活动还未开始", null);
		} 
		if (nowDate.getTime() > controParm.getActivityEnd().getTime()) {
			return new ResultBean<>(false, "活动已结束", null);
		}
		int lotteryTime = lotteryNum(actionCode, userInfo);
		PresentLogBean prizeLog = presentLogBLL.hasPrize(actionCode, account, "card");
		if (prizeLog != null) { // 已中奖，
			return new ResultBean<>(false, "您已经抽中激活码了", null);
		} 
		if (lotteryTime < 1) {
			return new ResultBean<>(false, "每天只能抽奖3次", null);
		} 
		// 开始抽奖	
		try (SqlSession session = getSession()) {
				PrizeBean prizeBean=new PrizeBean();
				prizeBean.setActionCode(actionCode);
				prizeBean.setChinese("谢谢参与");
				prizeBean.setEnglish("thanks");
				prizeBean.setIsReal("thanks");
				prizeBean.setNum(2);
				lotteryResult = lotteryService.lotteryByDataBase(actionCode, userId, "byChance", account, "-",
						0, loginIp,prizeBean, session);
				if ("谢谢参与".equals(lotteryResult.getMessage())) {
					// 保存谢谢参与玩家记录到日志表 hd_send_present_log
					UserInfoBean bean = new UserInfoBean();
					bean.setAccount(account);
					bean.setActionCode(actionCode);
					bean.setGameCode(2);
					bean.setIp(loginIp);
					bean.setPresentName("谢谢参与");
					bean.setPresentType("noRealPrize");
					bean.setServerCode(1);
					bean.setServerName("serverName");
					bean.setSource("神道三国激活码大放送");
					bean.setSourceCode(actionCode);
					bean.setTime(new Date());
					userLotteryBll.addUserLotteryInfo(bean, session);
					int temp=prizePosition(3);
					lotteryResult.getData().setConfigCode(temp);
				} else if ("未中奖".equals(lotteryResult.getMessage())) { // 激活码用完了
					lotteryResult.setProperties(false, "激活码已抽完", null);
				} else { // 抽取到激活码的话，更新game_activation_code status为1
					int temp=prizePosition(5);
					lotteryResult.getData().setConfigCode(temp);
					presentLogBLL.updateGameActivityCodeStatus(lotteryResult.getData().getCardCode());
				}
				session.commit(true);
				return lotteryResult;
			} catch (LotteryException e) {
				LOG.error("神道三国抢激活码[抽奖]接口异常", e);
				return new ResultBean<>(false, "网络异常", null);
			}
	}
	//与前台交互随机分配到到一个奖品
	public int prizePosition(int prizeSum){
		int temp=0;
		int a=0;
		Random random = new Random();
		if(prizeSum==5){
			a = random.nextInt(5);
			if (a == 0) {
				temp = 1;
			} else if (a == 1) {
				temp = 3;
			} else if (a == 2) {
				temp = 5;
			} else if (a == 3) {
				temp = 6;
			} else {
				temp = 8;
			}
		}
		if(prizeSum==3){
			a = random.nextInt(3);
			if (a == 1) {
				temp = 2;
			} else if (a == 2) {
				temp = 4;
			} else {
				temp = 7;
			}
		}
		return temp;
	}

	/**
	 * 获取剩余抽奖次数
	 */
	public int lotteryNum(int actionCode, UserInfo userInfo) { 
		Date nowDate = new Date();
		int isQualification = presentLogBLL.hasQualification(actionCode, userInfo.getAccount(),
				format.format(new Date(getDayEnd(nowDate))), format.format(new Date(getDayBegin(nowDate))));
		if (isQualification < 0) {
			return 0;
		} 
		//
		int result = default_day_lotteryTime - isQualification;
		return result;
	}

	/**
	 * 获取神道三国区服
	 */
	public String getRegion() {
		StringBuffer md5Para = new StringBuffer("/v2/game/28/server?timestamp=");
		StringBuffer urlStr = new StringBuffer("http://game.module.gyyx.cn/v2/game/28/server");
		StringBuffer paraStr = new StringBuffer("timestamp=");
		long nowDate = System.currentTimeMillis() / 1000;
		String nowDateStr = String.valueOf(nowDate);
		md5Para.append(nowDateStr).append("123456");
		String sign = MD5.encode(md5Para.toString());
		paraStr.append(nowDateStr).append("&sign_type=MD5").append("&sign=").append(sign);
		urlStr.append("?").append(paraStr.toString()).toString();
		String result = requestGet(urlStr.toString(), "UTF-8");
		return result;
	}

	/**
	 * 激活操作 0未领取 1已领取 2已激活
	 */
	public ResultBean<String> stepTwo(UserInfo userInfo, int actionCode, String serverID, String serverName,String activeCode) {
		if(!presentLogBLL.isRightActiveCode(activeCode)){
			return new ResultBean<>(false, "该激活码输入有误", "");
		}
		if (presentLogBLL.isExistActiveCode(activeCode)) {
			return new ResultBean<>(false, "该激活码已经被使用", "");
		}
		// 获取MD5密码
		String GamePwd = getMD5PasswordBll.getPassword(userInfo.getLoginID()).getData();
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("Account", userInfo.getAccount());
		paraMap.put("Ip", userInfo.getLoginIP());
		paraMap.put("GameId", "28");
		paraMap.put("ServerId", serverID);
		paraMap.put("GamePwd", GamePwd);
		paraMap.put("IsNeedActiveCode", "0");
		paraMap.put("ActiveCode", activeCode);
		paraMap.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
		LOG.info("Ip:" + userInfo.getLoginIP() + ",GameId:" + 28 + ",ServerId:" + serverID + ",GamePwd:" + GamePwd
				+ ",IsNeedActiveCode:" + "0" + "ActiveCode:" + activeCode + "timestamp:"
				+ String.valueOf(System.currentTimeMillis() / 1000));
		String resultStr = post("http://interface.account.gyyx.cn/game/client/register", paraMap, "123456");
		String isSuccess = "", jsonMsg = "";
		try {
			JSONObject jsonObj = JSONObject.fromObject(resultStr);
			isSuccess = jsonObj.getString("IsSuccess");
			jsonMsg = jsonObj.getString("Message");
			// 如果激活成功，更新数据
			if (isSuccess.equals("true")) {
				boolean flag = presentLogBLL.isExist(actionCode, userInfo.getAccount(), "card");
				if (flag) { // 存在
					presentLogBLL.updateGameActivityCode(28, activeCode, serverID, userInfo.getAccount(), new Date(),
							userInfo.getUserId());
				} else { // 不存在
					presentLogBLL.insertPrizeLog(userInfo.getAccount(), Integer.parseInt(serverID), serverName,
							activeCode, format.format(new Date()), userInfo.getUserId().toString());
				}
				return new ResultBean<>(true, "激活成功", "");
			} else {
				return new ResultBean<>(false, jsonMsg, null);
			}
		} catch (Exception e) {
			LOG.error("神道三国抢激活码Service[激活操作]接口异常,错误堆栈：{}", e);
			return new ResultBean<>(false, "激活失败", null);
		}

	}

	/**
	 * 查看激活情况
	 */
	public ResultBean<String> stepThree(int gameId, int userId) {
		Integer resultInt = presentLogBLL.isActivation(gameId, userId);
		if (resultInt == null) {
			return new ResultBean<String>(true, "您还没有激活", "");
		}
		if(resultInt<=0){
			return new ResultBean<String>(true, "查询失败", "");
		}
		return new ResultBean<String>(true, "激活成功", "");
	}

	/**
	 * 获取一天开始时间
	 */
	public long getDayBegin(Date date) {
		long ts = date.getTime();
		ts = ts - ts % (24 * 3600 * 1000) - 8 * 60 * 60 * 1000;
		return ts;
	}

	/**
	 * 获取一天结束时间
	 */
	public long getDayEnd(Date date) {
		return getDayBegin(date) + (24 * 3600 * 1000);
	}

	/**
	 * post请求
	 */
	public String requestPost(String urlStr, Map<String, String> paramMap, String key) {
		URL url = null;
		HttpURLConnection conn = null;
		String result = "";
		String paramStr = prepareParam(paramMap, key);
		try {
			url = new URL(urlStr + "?" + paramStr);
			LOG.debug("begin connect " + urlStr);
			LOG.debug("connection type :" + SERVLET_POST);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(SERVLET_POST);
			System.out.println("paraStr" + paramStr);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			OutputStream os = conn.getOutputStream();
			os.write(paramStr.toString().getBytes("utf-8"));
			os.close();
			InputStream a = conn.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			LOG.debug("over connection ... begin response message ...");
			String line = "";
			while ((line = br.readLine()) != null) {
				result += line;
			}
			br.close();
			LOG.debug("get reponse message :" + result);
		} catch (Exception e) {
			LOG.error("神道三国抢激活码[post请求 ]接口异常",e);
		}
		return result;
	}

	public String post(String url, Map<String, String> paramMap, String key) {
		StringBuffer response = new StringBuffer();
		String paramStr = prepareParam(paramMap, key);
		String paraUrl = url + "?" + paramStr;
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(url);
		method.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		NameValuePair[] param = { new NameValuePair("Account", paramMap.get("Account")),
				// paraMap.put("Account", "dceshiyonghu99");
				new NameValuePair("Ip", paramMap.get("Ip")), new NameValuePair("GameId", paramMap.get("GameId")),
				new NameValuePair("ServerId", paramMap.get("ServerId")),
				new NameValuePair("GamePwd", paramMap.get("GamePwd")), // 暂时不知道应该怎么输入
				new NameValuePair("IsNeedActiveCode", "1"), // 需要再问一下 TODO
				new NameValuePair("ActiveCode", paramMap.get("ActiveCode")),
				new NameValuePair("timestamp", String.valueOf(System.currentTimeMillis() / 1000)) };
		method.setRequestBody(param);
		method.releaseConnection();
		try {
			client.executeMethod(method);
			if (method.getStatusCode() == HttpStatus.SC_OK || method.getStatusCode() == HttpStatus.SC_BAD_REQUEST) {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(method.getResponseBodyAsStream(), "utf-8"));
				String line;
				while ((line = reader.readLine()) != null) {
					response.append(line);
				}
				reader.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			method.releaseConnection();
		}
		return response.toString();
	}

	public String requestGet(String urlStr, String encode) {
		RestClient client = new RestClient();
		String result = "";
		try {
			org.apache.wink.client.Resource resource = client.resource(urlStr).accept("application/json");
			ClientResponse response = (resource).get();
			// 接收返回响应体
			result = response.getEntity(String.class);
		} catch (Exception e) {
			LOG.warn("获取机构列表失败" ,e);
		}
		return result;
	}

	/**
	 * 生成签名参数
	 * 
	 * @param paramMap
	 * @return
	 */
	private String prepareParam(Map<String, String> paramMap, String key) {
		StringBuffer paraBuffer = new StringBuffer();
		StringBuffer urlBuffer = new StringBuffer("/game/client/register");
		Object[] keyObj = paramMap.keySet().toArray();
		String[] keyArrayStr = new String[keyObj.length];
		for (int i = 0; i < paramMap.keySet().toArray().length; i++) {
			keyArrayStr[i] = (String) keyObj[i];
		}
		quickSort(keyArrayStr, 0, paramMap.size() - 1);
		for (int i = 0; i < keyArrayStr.length; i++) {
			if (i == 0) {
				paraBuffer.append(keyArrayStr[i]).append("=").append(paramMap.get(keyArrayStr[i]));
				urlBuffer.append("?").append(keyArrayStr[i]).append("=").append(paramMap.get(keyArrayStr[i]));
			} else {
				paraBuffer.append("&").append(keyArrayStr[i]).append("=").append(paramMap.get(keyArrayStr[i]));
				urlBuffer.append("&").append(keyArrayStr[i]).append("=").append(paramMap.get(keyArrayStr[i]));
			}
		}
		String sign = MD5.encode(urlBuffer.toString() + key);
		paraBuffer.append("&sign_type=MD5&sign=").append(sign);
		return paraBuffer.toString();
	}

	/**
	 * 快排
	 */
	public int executeCom(String[] array, int first, int last) {
		int i = first, j = last;
		String standard = array[i];
		while (i < j) {
			if (!(array[j] == null)) {
				while (i < j && array[j].compareTo(standard) >= 0) {
					j--;
				}
				if (i < j) {
					array[i] = array[j];
				}
				while (i < j && array[i].compareTo(standard) <= 0) {
					i++;
				}
				if (i < j) {
					array[j] = array[i];
				}
			} else {
				j--;
			}
		}
		array[i] = standard;
		return i;
	}

	public void quickSort(String[] array, int first, int last) {
		if (first < last) {
			int i = executeCom(array, first, last);
			quickSort(array, first, i - 1);
			quickSort(array, i + 1, last);
		}
	}

	/**
	 * 根据用户信息获取激活码
	 * 
	 * @param userInfo
	 * @return
	 */
	public String searchActivityCode(UserInfo userInfo) {
		String code = presentLogBLL.getCardCode(userInfo.getUserId(), ACTIONCODE, "SDSGActivityCode");
		return code;
	}

}
