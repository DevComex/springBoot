/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年3月11日下午4:18:03
 * 版本号：v1.0
 * 本类主要用途叙述：链接memcache的工具类
 */



package cn.gyyx.action.service.insurance;

import cn.gyyx.core.memcache.XMemcachedClientAdapter;



public class MemcacheUtil {
	/**
	 * 创建一个memcache
	 * @return
	 */
	public XMemcachedClientAdapter getMemcache(){
		
		XMemcachedClientAdapter  memcachedClientAdapter=new XMemcachedClientAdapter("Action-ListData");
		return memcachedClientAdapter;
		
	}

}
