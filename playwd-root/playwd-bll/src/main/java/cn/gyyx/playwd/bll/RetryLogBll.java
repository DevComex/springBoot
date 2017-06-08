package cn.gyyx.playwd.bll;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.gyyx.playwd.beans.playwd.RetryLogBean;
import cn.gyyx.playwd.dao.playwd.RetryLogDao;

/**
 * 重试日志
 * @author chenglong
 */
@Component
public class RetryLogBll {
	@Autowired
	RetryLogDao retryLogDao;

	/**
	 * 插入
	 */
    public int insert(String orderId,int serverId,String serverName,
    		String serverIp,int serverPort,String status,String description) {
    	RetryLogBean bean = new RetryLogBean();
    	bean.setOrderId(orderId);
    	bean.setServerId(serverId);
    	bean.setServerName(serverName);
    	bean.setServerIp(serverIp);
    	bean.setServerPort(serverPort);
    	bean.setCreateTime(new Date());
    	bean.setDescription(description);
    	bean.setStatus(status);
    	return retryLogDao.insert(bean);
	}
}