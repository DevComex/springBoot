package cn.gyyx.action.bll.mobile.website;

import org.slf4j.Logger;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.mobile.website.MobileRegisteBean;
import cn.gyyx.action.beans.mobile.website.MobileWebsiteRegisteLog;
import cn.gyyx.action.dao.mobile.website.RegisteDAO;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class RegisteBLL {
	private static final Logger LOG = GYYXLoggerFactory
			.getLogger(RegisteBLL.class);
	private RegisteDAO registeDAO = new RegisteDAO();
	public ResultBean<String> insertRegiste(MobileRegisteBean para){
		ResultBean<String> result = new ResultBean<String>();
		if(registeDAO.insertRegiste(para)<0){
			result.setProperties(false, "注册失败,请稍后重试!", "");
		}else{
			result.setProperties(true, "注册成功!", "");
		}
		return result;
	}
	public ResultBean<String> isExist(String phone){
		ResultBean<String> result = new ResultBean<String>();
		int temp = registeDAO.isExist(phone);
		if(temp<0){
			result.setProperties(true, "查询是否存在失败!", "");
		}else if(temp==0){
			result.setProperties(false, "不存在", "");
		}else{
			result.setProperties(true, "已经存在!", "");
		}
		return result;
	}
	public ResultBean<String> insertRegisteLog(MobileWebsiteRegisteLog para){
		ResultBean<String> result = new ResultBean<String>();
		if(registeDAO.insertRegisteLog(para)<0){
			result.setProperties(false, "插入日志失败!", "");
		}else{
			result.setProperties(true, "插入日志成功!", "");
		}
		return result;
		
	}
}
