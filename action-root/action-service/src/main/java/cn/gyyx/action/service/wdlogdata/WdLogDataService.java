/**------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：liqiang
 * 联系方式：liqiang@gyyx.cn 
 * 创建时间： 2015/11/11 
 * 版本号：v1.0 
 * 本类主要用途描述： 
-------------------------------------------------------------------------*/
package cn.gyyx.action.service.wdlogdata;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.wdlogdata.WdLogDataBean;
import cn.gyyx.action.bll.wdlogdata.WdLogDataBLL;
import cn.gyyx.core.Ip;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.distribute.lock.DLockException;
import cn.gyyx.distribute.lock.DistributedLock;

/**
 * 安装与卸载问道LOG数据的Service
 */
public class WdLogDataService {
	private WdLogDataBLL wdLogDataBLL = new WdLogDataBLL();
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(WdLogDataService.class);

	/**
	 * 保存正确的安装和卸载的LOG数据,operation(1为安装，2为卸载)
	 * */
	public ResultBean<WdLogDataBean> saveLogData(WdLogDataBean log,
			String timestamp, String sign,String sign_type,HttpServletRequest request) {
		ResultBean<WdLogDataBean> result = new ResultBean<WdLogDataBean>();
		String requestIP = Ip.getCurrent(request).asString();
		logger.info("requestIP:"+requestIP);
		logger.info(toString());
      	String mapping = request.getRequestURI().toString();
////		本地测试改成下句
//		String mapping = request
//				.getRequestURI().toString().replace(request.getContextPath(), "");

		String params = request
				.getQueryString().replace("&sign="+sign, "")
				.replace("&sign_type="+sign_type, "");
		logger.info("mapping:"+mapping);
		logger.info("params:"+params);
		/* 分布式锁 */
		try (DistributedLock lock = new DistributedLock("wdLogData001")) {
			lock.weakLock(30, 10);/* 1秒最大鎖持有時間，2秒等待 */
			result = wdLogDataBLL.saveLogData(log,timestamp,sign,sign_type,requestIP,mapping,params);
		} catch (DLockException e) {
			logger.error("分布式锁异常:",e);
		}
		return result;
	}
}
