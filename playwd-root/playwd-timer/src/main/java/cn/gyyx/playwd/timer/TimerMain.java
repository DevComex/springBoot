package cn.gyyx.playwd.timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import task.SendPrizeToGameTask;



public class TimerMain {
	private static Logger logger = LoggerFactory.getLogger(TimerMain.class);
	
	private static String paths[] = { "classpath:spring/applicationContext.xml" };
	public static ApplicationContext ctx = null;
	
	public static void main(String[] args) {
		ctx = new ClassPathXmlApplicationContext(paths);
		SendPrizeToGameTask task1 = (SendPrizeToGameTask)ctx.getBean("sendPrizeToGameTask");
		task1.setTaskName("玩家天地发送虚拟物品订单到游戏任务");
		task1.setMinProduceInterval(1000);
		task1.start();
		logger.info("...定时任务开启...");
	}
}
