 /**
    * -------------------------------------------------------------------------
    * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
    * @版权所有：北京光宇在线科技有限责任公司
    * @项目名称：玩家天地
    * @作者：李杜迪
    * @联系方式：lidudi@gyyx.cn
    * @创建时间：2017年3月9日上午11:07:27
    * @版本号：1.0.0
    *-------------------------------------------------------------------------
    */
package cn.gyyx.playwd.utils;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.springframework.util.StringUtils;

/**
 * 生成订单号
 * @author lidudi
 *
 */
public class CreateOrderId {

	/**
	 * 生成订单号
	 * @param account
	 * @return
	 */
	public static String getOrderId(String account) {
		//获取账号的hash值
		int getHashByAccount;
		if(StringUtils.isEmpty(account)){
			getHashByAccount=100000000;
		}
		else {
			getHashByAccount=account.hashCode() % 99999989 + 100000000;
		}
		
		//时间
		SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmmssSSS");		
		StringBuilder builder = new StringBuilder();
		String datePart = format.format(new Date());
	    char[] zeros = new char[17 - datePart.length()];
	    Arrays.fill(zeros, '0');
	    builder.append(datePart);
	    builder.append(zeros);
	    builder.append(getHashByAccount);

	    //4位随机数
	    String itemStr = String.valueOf(1000);
	    char[] zerosItemCode = new char[4 - itemStr.length()];
	    Arrays.fill(zerosItemCode, '0');
	    builder.append(new StringBuilder().append(new String(zerosItemCode)).append(itemStr).toString());
	    return builder.toString();
	}
}
