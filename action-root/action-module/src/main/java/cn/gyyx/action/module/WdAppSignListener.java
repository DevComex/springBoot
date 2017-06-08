package cn.gyyx.action.module;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import cn.gyyx.action.service.wdsigned.IWdAppSignLogService;
import cn.gyyx.action.service.wdsigned.WdAppSignLogServiceImpl;
//import cn.gyyx.message.MessageClient;

public class WdAppSignListener implements ServletContextListener {

	//private IWdAppSignLogService wdAppSignLogService = new WdAppSignLogServiceImpl();
	
	//private MessageClient client = new MessageClient("redis.queue.qrlogin_signin_wendao");
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		//wdAppSignLogService.gameLoginSign(client);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
		//client.shutdown();
	}

}
