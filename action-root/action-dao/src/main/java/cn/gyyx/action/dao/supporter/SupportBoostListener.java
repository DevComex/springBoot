package cn.gyyx.action.dao.supporter;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class SupportBoostListener implements ServletContextListener {
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		PreJPADemo.fireJuge();
		PreJPADemo.lhzs();
		PreJPADemo.lhzsInvestigate();
		//PreJPADemo.wdAlone();
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}
}
