package task;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.playwd.agent.ActionModuleAgent;
import cn.gyyx.playwd.agent.GameModuleAgent;
import cn.gyyx.playwd.agent.InterfaceAccountAgent;
import cn.gyyx.playwd.beans.agent.ActionModuleSendGiftToGameResultBean;
import cn.gyyx.playwd.beans.agent.GameModuleServerInfoResultBean;
import cn.gyyx.playwd.beans.playwd.WdgiftOrderBean;
import cn.gyyx.playwd.beans.playwd.WdgiftOrderBean.GiftOrderState;
import cn.gyyx.playwd.bll.RetryLogBll;
import cn.gyyx.playwd.bll.WdgiftOrderBll;

/**
 * 版        权：光宇游戏
 * 作        者：ChengLong
 * 创建时间：2017年3月3日 下午12:46:00
 * 描        述：银元宝订单发送到游戏服务
 */
@Component
public class SendPrizeToGameTask extends OneProducerMultiConsumer<WdgiftOrderBean>{
	private Logger logger = GYYXLoggerFactory.getLogger(getClass());
	
	private int maxTryNum = 5;//最大重试次数
	private int itemNum = 50;//每次获取的数据条数
	@Autowired
	private WdgiftOrderBll wdgiftOrderBll;
	
	@Autowired
	private ActionModuleAgent actionModuleAgent;
	
	@Autowired
	private InterfaceAccountAgent interfaceAccountAgent;
	
	@Autowired
	private GameModuleAgent gameModuleAgent;
	
	@Autowired
	private RetryLogBll retryLogBll;
	
	/**
	 * 获取要发送到游戏的订单列表 
	 * 条件：状态为非成功且未达到最大重试次数且到达下次重试时间
	 * @return 订单列表
	 */
	@Override
	public List<WdgiftOrderBean> produce() {
		return wdgiftOrderBll.getOrderListToSend(itemNum,maxTryNum);
		
	}

	/**
	 * 调用接口发送到游戏
	 * @return 是否发送成功
	 */
	@Override
	public boolean consume(WdgiftOrderBean item) {
		//检查订单状态
		WdgiftOrderBean dbItem = wdgiftOrderBll.getOrderByCode(item.getCode());
		if(dbItem.getStatus().equals(GiftOrderState.success.Value())){
			logger.info("订单状态已经为成功状态,不再处理,订单ID：{}",item.getCode());
			return false;
		}
		
		//获取服务器信息
		GameModuleServerInfoResultBean serverInfo =  gameModuleAgent.getWdServerInfo(item.getServerId());
		if(serverInfo == null || StringUtils.isNotBlank(serverInfo.getError())){
			logger.info("获取服务器信息失败,订单ID：{}", item.getOrderId());
			this.updateRetryInfo("false", "获取服务器信息返回结果："+serverInfo.getErrorMessage(), dbItem,"0",0);
			return false;
		}
		//检查是否激活服务器
		Map<String,String> isActiveServer = interfaceAccountAgent.isActive(dbItem.getAccount(), dbItem.getServerId(), 
				serverInfo.getValue().getServerIp(), serverInfo.getValue().getServerPort()+"");
		if(!isActiveServer.get("success").equals("true")){
			logger.info("未成功检测到用户激活服务器,订单ID：{}", item.getOrderId());
			wdgiftOrderBll.updateOrderStatus(item.getCode(), GiftOrderState.unactivated.Value());
			this.updateRetryInfo("false", "检查是否激活服务器返回结果："+isActiveServer.get("response"), dbItem,serverInfo.getValue().getServerIp(),
					serverInfo.getValue().getServerPort());
			return false;
		}
		//调用接口发送
		logger.info("调用接口发送订单,订单ID：{}", item.getOrderId());
		ActionModuleSendGiftToGameResultBean sendResult = actionModuleAgent.sendGiftToGame(item.getAccount(), 
				item.getGift(), "wjtd",2, 
				serverInfo.getValue().getServerIp(), serverInfo.getValue().getServerPort(), 
				"defaultSource", item.getOrderId());
		if(sendResult != null && sendResult.isSuccess()){
			//更新订单状态为发送成功
			wdgiftOrderBll.updateOrderStatus(item.getCode(), GiftOrderState.success.Value());
			this.updateRetryInfo("true", "发送成功", dbItem,serverInfo.getValue().getServerIp(),
					serverInfo.getValue().getServerPort());
			return true;
		}else {
			//更新订单状态为发送失败
			this.updateRetryInfo("false","发放到游戏返回结果："+sendResult.getMessage(), dbItem,serverInfo.getValue().getServerIp(),
					serverInfo.getValue().getServerPort());
			return true;
		}
	}
	
	/**
	 * 更新重试信息
	 */
	private void updateRetryInfo(String status,String description,WdgiftOrderBean item,String serverIp,int serverPort){
		//记录重试日志 
		retryLogBll.insert(item.getOrderId(), item.getServerId(), item.getServerName(), 
				serverIp, serverPort, status, description);
		//更新订单重试时间
		this.updateRetryTaskInfo(item);
		wdgiftOrderBll.updateOrderRetryInfo(item.getCode(), item.getRetryCount(),item.getRetryTime());
	}
	

}
