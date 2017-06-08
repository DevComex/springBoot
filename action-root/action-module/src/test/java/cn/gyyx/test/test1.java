/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年5月18日上午11:26:50
 * 版本号：v1.0
 * 本类主要用途叙述：处理用户相关请求，包括登陆和注册
 */

package cn.gyyx.test;

import java.util.List;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.client.RestClient;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.giftinterface.GiftBagBean;
import cn.gyyx.action.service.giftbaginterface.GiftBagInterfaceService;

public class test1 {
	public static void main(String[] args) {
		//GiftBagInterfaceService service =new GiftBagInterfaceService();
		//ResultBean<List<GiftBagBean>> resultBean=service.getGiftBagBeans("2016-4-07");
		//System.out.println(resultBean.getData());
		
	/*	String url="http://actionv2.module.gyyx.cn:8080/action-module/giftbag/getinfo?day=2016-5-19&serverName=愚公移山";
		RestClient client = new RestClient();
		org.apache.wink.client.Resource resource = client.resource(url);
		ClientResponse response = (resource).get();
		// 接收返回响应体
		String result = response.getEntity(String.class);
		System.out.println(result);*/
	}

}
