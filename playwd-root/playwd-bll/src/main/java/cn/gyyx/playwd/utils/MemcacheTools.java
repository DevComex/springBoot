package cn.gyyx.playwd.utils;

import cn.gyyx.core.memcache.XMemcachedClientAdapter;

/**
 * 版        权：光宇游戏
 * 作        者：ChengLong
 * 创建时间：2016年10月10日 下午1:33:38
 * 描        述：
 */
public class MemcacheTools {
	private static String ADAPTER_ACTION_LIST_NAME = "Action-ListData";
	
	public static synchronized XMemcachedClientAdapter getMemcache(){
		XMemcachedClientAdapter  memcachedClientAdapter=new XMemcachedClientAdapter(ADAPTER_ACTION_LIST_NAME);
		return memcachedClientAdapter;
	}
	
}
