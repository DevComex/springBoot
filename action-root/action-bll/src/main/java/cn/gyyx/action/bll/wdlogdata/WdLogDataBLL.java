/**------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：liqiang
 * 联系方式：liqiang@gyyx.cn 
 * 创建时间： 2015/11/11 
 * 版本号：v1.0 
 * 本类主要用途描述： 
-------------------------------------------------------------------------*/
package cn.gyyx.action.bll.wdlogdata;

import java.util.Date;

import org.slf4j.Logger;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.wdlogdata.CountBean;
import cn.gyyx.action.beans.wdlogdata.WdLogDataBean;
import cn.gyyx.action.dao.wdlogdata.WdLogDataDAO;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.core.memcache.XMemcachedClientAdapter;
import cn.gyyx.core.prop.SimpleProperties;
import cn.gyyx.action.bll.wdlogdata.WdDecrypt;


/**
 * 安装与卸载问道LOG数据的BLL
 */
public class WdLogDataBLL {
	private WdLogDataDAO wdLogDAO = new WdLogDataDAO();
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(WdLogDataBLL.class);
	private static final String KEY = SimpleProperties.getInstance().getStringProperty("wd_log.key");

	/**
	 * 保存正确的安装和卸载的LOG数据
	 * */
	public ResultBean<WdLogDataBean> saveLogData(WdLogDataBean log,
			String timestamp, String sign,String sign_type,String requestIP,String mapping,String params) {
		/* 1.验证部分，验证数据是否有意义 */
		try {
            /* 正则验证Acc是否正确 */
			
			if (log.getAcc()!=null&&log.getAcc().length()>1700) {
				return new ResultBean<WdLogDataBean>(false, "Acc长度超出，保存数据失败",
						log);
			}
            /* 正则验证gameid是int类型 */
			String gameipTemplate ="[0-9]+";
			if (!log.getGameid().matches(gameipTemplate)) {
				return new ResultBean<WdLogDataBean>(false, "gameid不是数字，保存数据失败",
						log);
			}
				
			/* 正则验证IP是否正确 */
			String ipTemplate = "^((\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]|[*])\\.){3}(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]|[*])$";

			if (!log.getIp().matches(ipTemplate)) {
				return new ResultBean<WdLogDataBean>(false, "IP地址有问题，保存数据失败",
						log);
			}
			/* 正则验证MAC是否正确 */
			String macTemplate = "^[a-fA-F0-9]{2}([a-fA-F0-9]{2}){5}$";

			if (!log.getMac().matches(macTemplate)) {
				return new ResultBean<WdLogDataBean>(false, "MAC地址有问题，保存数据失败",
						log);
			}
			/* 正则验证版本号是否正确 */
			String verTemplate = "^\\d+(\\.\\d+)*$";

			if (!log.getVer().matches(verTemplate)) {
				return new ResultBean<WdLogDataBean>(false, "版本号有问题，保存数据失败",
						log);
			}
			/* 验证时间戳 */
			String timestampTemplate = "^\\d{10}$";
			if ("on".equals(SimpleProperties.getInstance().getStringProperty(
					"wd_log.signSwitch"))) {
				if (!timestamp.matches(timestampTemplate)) {
					return new ResultBean<WdLogDataBean>(false, "时间戳有问题，保存数据失败",log);
				}
				/* 验证加密标签 */
				String signTemplate = "^[a-fA-F0-9]{32}$";
				/* 问道验证加密标签 */
				if(Integer.parseInt(log.getGameid())==2)
				{
					
					String wdmd5_sign = WDMD5.encode(mapping, params,KEY,sign_type);
					logger.info("URL传的:" + sign);
					logger.info("加密之后:" + wdmd5_sign);
					if (!sign.matches(signTemplate) || !wdmd5_sign.equals(sign)) {
						return new ResultBean<WdLogDataBean>(false,
								"加密标签有问题，保存数据失败", log);
				}
					}
					else
				{
				String md5_sign = MD5.encode(mapping, params,KEY,sign_type);
				
				logger.info("URL传的:" + sign);
				logger.info("加密之后:" + md5_sign);
				if (!sign.matches(signTemplate) || !md5_sign.equals(sign)) {
					return new ResultBean<WdLogDataBean>(false,
							"加密标签有问题，保存数据失败", log);
				}
				}
			}
				
			}
		 catch (Exception e) {
			logger.error("业务逻辑异常" + e.toString());
			return null;
		}
		/* 
		 * 如果gameid=2，且版本号不小于1.60 那么是问道加密账户
		 * 执行解密操作，保存明文Acc。
		 * */
		boolean ver=vercompare.isNewVersion(log.getVer(), "1.60");
		if(Integer.parseInt(log.getGameid())==2&&ver==true&&log.getAcc()!=null)
		{
			
		 String s1=WdDecrypt.encode(log.getAcc());
		 log.setAcc(s1);
		 logger.info("解密后："+log.getAcc());		 
			
		 }	
		
		/* 2.保存数据部分 */
		boolean result = false;
		/*查询缓存，24小时之内相同数据提交10次以上，从第11次开始将不再插入到数据库 */
		XMemcachedClientAdapter client = new XMemcachedClientAdapter("Action-ListData");
		String key = requestIP;
		CountBean rb = new CountBean();
		CountBean bean =  client.get(key,CountBean.class);
		String cachetime =SimpleProperties.getInstance().getStringProperty("wd_log.cacheSeconds") ;	
		logger.info("wd_log.cacheSeconds:"+cachetime);  
		  int cacheSeconds = 1;
		  String[] strs = cachetime.split("\\*");
		  
		  for(int i=0;i<strs.length;i++){
			  cacheSeconds*=Integer.parseInt(strs[i]);
		  }
		logger.info("cacheSeconds:"+cacheSeconds);
		int maxCount=10;
		try {
			maxCount = Integer.parseInt(SimpleProperties.getInstance().getStringProperty("wd_log.count"));
		} catch (NumberFormatException e) {
			logger.error("wd_log.count的数据转换异常"+e.toString());
		}
		
		if(bean!=null){
			Integer count = bean.getData();
			if(count >=maxCount){
				result = false;
			}else{
				Date now = new Date();
				if(bean.getFirstCacheDate().getTime() - now.getTime() < cacheSeconds){
					//在缓存有效期内 
					bean.setData(count + 1);
					client.set(key,cacheSeconds,bean);
					result = wdLogDAO.saveLog(log);
				}
			}
		}else{
			rb.setData(1);
			rb.setFirstCacheDate(new Date());
			client.add(key, cacheSeconds, rb);
			result = wdLogDAO.saveLog(log);
			logger.info("wdLogDataBll:else:"+result);
		}


		/* 3.处理结果部分，分情况返回ResultBean */
		if (result && log.getType() == 1) {
			return new ResultBean<WdLogDataBean>(true, "保存问道安装LOG数据成功", log);
		} else if (result && log.getType() == 2) {
			return new ResultBean<WdLogDataBean>(true, "保存问道卸载LOG数据成功", log);
		} else {
			return new ResultBean<WdLogDataBean>(false, "存储数据异常,保存数据失败", log);
		}
	}
}
