package cn.gyyx.action.service.wdnovicecard;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 版 权：光宇游戏 
 * 作 者：ChengLong 
 * 创建时间：2017年3月8日 下午3:18:09 
 * 描 述：订单Id生成
 */
public class OrderIdBuilder {

	/**
	 * 根据账号生成30位订单号,格式：时间[17位]+(100000000+取正(账号hash%99999999)[9位]+随机数[4位])
	 * 
	 * @param account
	 * @return
	 */
	public static String buildOrderId(String account) {
		StringBuilder builder = new StringBuilder();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		builder.append(sdf.format(new Date()));
		builder.append(Math.abs(account.hashCode()) % 99999999 + 100000000);
		builder.append((int)(Math.random()*(9999-1000+1))+1000);
		return builder.toString();
	}
	

}
