package cn.gyyx.playwd.agent;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.gyyx.core.security.MD5;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.playwd.beans.playwd.RoleBean;
import cn.gyyx.playwd.utils.HttpTools;

/**
 * 版        权：光宇游戏
 * 作        者：lihu
 * 创建时间：2016年9月14日 下午1:26:27
 * 描        述：调用interface.account.gyyx.cn的接口
 */
@Component
public class InterfaceQiBaoAgent {
        Logger logger = GYYXLoggerFactory.getLogger(InterfaceQiBaoAgent.class);
	
	private static final String DOMAIN = "interface.qibao.gyyx.cn";
	private static final String SERVER_URL = "http" + "://" + DOMAIN;
	//private static final String KEY = "123456";
	@Value("${key}")
	String key  ;
	//是否激活服务器
	private static final String requestUrl_01 = "/Role/GetRoleList?account={0}&serverid={1}";
	
	/**
	 * 查询角色列表
	 */
	public   List<RoleBean> isActive(String account,int serverId){
	    List<RoleBean> result = new   ArrayList<>();
		try {
			long timestamp = System.currentTimeMillis() / 1000L;
			String url = MessageFormat.format(requestUrl_01, account, serverId)+"&timestamp="+timestamp;
			String md5 = MD5.encode(url + key);
			String signUrl = SERVER_URL+ url + "&sign=" + md5+"&sign_type=MD5";
			logger.info("调用接口发送请求开始["+DOMAIN+"]:isActive,请求链接:"+signUrl,"请求方式:get");
			String response = HttpTools.get(signUrl);
			logger.info("调用接口发送请求结束["+DOMAIN+"]:isActive,返回值:"+response);
			JSONObject jsonObject = JSONObject.parseObject(response);
			Boolean IsSuccess = jsonObject.getBoolean("IsSuccess");
			if(IsSuccess){
			    JSONArray array = jsonObject.getJSONArray ("List");
			     for (int i = 0; i < array.size(); i++) {
			         RoleBean bean=new RoleBean(); 
			         JSONObject object = array.getJSONObject(i);
			         bean.setFaction(object.getString("Religion"));
			         bean.setGender("");
			         bean.setlevel(object.getInteger("Level"));
			         bean.setNickName(object.getString("RoleName"));
			         bean.setPicture("/static/images/role/"+object.getString("ImageNumber")+".jpg");
			         bean.setIsDefault(false);
			         result.add(bean);
                            }
			    return result;
			}
		} catch (Exception e) {
			logger.error("请求接口：["+DOMAIN+"]isActive接口异常",e);
		}
		return null;
	}
        
}
