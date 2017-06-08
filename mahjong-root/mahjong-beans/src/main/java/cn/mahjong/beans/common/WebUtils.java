package cn.mahjong.beans.common;

import java.math.BigInteger;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: WebUtils
 * @description 
 * @author bozhencheng
 * @date 2016年10月10日
 */
public class WebUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebUtils.class);
	
	/**
	  * @Title: getIp
	  * @Description:  获取StringIP
	  * @param request 请求
	  * @return String 
	  * @throws
	  */
	public static String getIp(HttpServletRequest request) {
		
	   String ip = request.getHeader("x-forwarded-for");
           if (!ifIpIsBlank(ip)){
               LOGGER.info("Request Params Header x-forwarded-for: {} !", ip);
           	return ip;
           }
           
           ip = request.getHeader("Proxy-Client-IP");  
           if (!ifIpIsBlank(ip)){
           	LOGGER.info("Request Params Header Proxy-Client-IP: {} !", ip);
           	return ip;
           }
           
           ip = request.getHeader("WL-Proxy-Client-IP");  
           if (!ifIpIsBlank(ip)){
           	LOGGER.info("Request Params Header WL-Proxy-Client-IP: {}!", ip);
           	return ip;
           }
           
           ip = request.getHeader("HTTP_CLIENT_IP");  
           if (!ifIpIsBlank(ip)){
           	LOGGER.info("Request Params Header HTTP_CLIENT_IP: {}!", ip);
           	return ip;
           }
           
           ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
           if (!ifIpIsBlank(ip)){
           	LOGGER.info("Request Params Header HTTP_X_FORWARDED_FOR: {}!", ip);
           	return ip;
           }  
           ip = request.getRemoteAddr();   
           if (!ifIpIsBlank(ip)){
    	        LOGGER.info("Request Params RemoteAddr: {}!", ip);
    	        return ip; 
           }
           return "";
	}
	
	/**
	  * @Title: ifIpIsBlank
	  * @Description: 判断IP是否为空
	  * @param ip
	  * @return 
	  * boolean 
	  * @throws
	  */
	private static boolean ifIpIsBlank(String ip){
		return ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip);
	}
	
	/**
	  * @Title: getIntIp
	  * @Description: 获取Ip对应的Int值
	  * @param request
	  * @return 
	  * int 
	  * @throws
	  */
	public static int getIntIp(HttpServletRequest request){
		return (int) ipToLong(getIp(request));
	}
	
	/**
	  * @Title: getLongIp
	  * @Description: 获取Ip对应的Long值
	  * @param request
	  * @return 
	  * long 
	  * @throws
	  */
	public static long getLongIp(HttpServletRequest request){
		return ipToLong(getIp(request));
	}
	
	/**
	  * @Title: ipToLong
	  * @Description: long   
	  * @param ipAddress IP地址
	  * @return long 
	  * @throws
	  */
	public static long ipToLong(String ipAddress) {  
		byte[] res;  
		String ipStr = ipAddress.replace(" ", "");
		if(ipStr.contains(":")){// ipv6
			res = ipv6ToBytes(ipStr);
		}else{ // IPv4
			res = ipv4ToBytes(ipStr);
		}
        return new BigInteger(res).longValue();  
   }
	
	/**
	  * @Title: ipv6ToBytes
	  * @Description: 将IPv6装换成字节码
	  * @return 
	  * byte[] 
	  * @throws
	  */
	private static byte[] ipv6ToBytes(String ipStr){
		byte[] ret = new byte[17];// to sign Data
        ret[0] = 0;
        int ib = 16;
        boolean comFlag = false;// ipv4混合模式标记
        // 去掉开头的冒号
        String ip = ipStr;
        if (ipStr.startsWith(":")){
            ip = ipStr.substring(1);
        }
        String groups[] = ip.split(":");
        for (int ig = groups.length - 1; ig > -1; ig--) {// 反向扫描
            if (groups[ig].contains(".")) {
                // 出现ipv4混合模式
                byte[] temp = ipv4ToBytes(groups[ig]);
                ret[ib--] = temp[4];
                ret[ib--] = temp[3];
                ret[ib--] = temp[2];
                ret[ib--] = temp[1];
                comFlag = true;
            } else if ("".equals(groups[ig])) {
                // 出现零长度压缩,计算缺少的组数
                int zlg = 9 - (groups.length + (comFlag ? 1 : 0));
                while (zlg-- > 0) {// 将这些组置0
                    ret[ib--] = 0;
                    ret[ib--] = 0;
                }
            } else {
                int temp = Integer.parseInt(groups[ig], 16);
                ret[ib--] = (byte) temp;
                ret[ib--] = (byte) (temp >> 8);
            }
        }
        return ret;
	} 
	
	/**
	  * @Title: ipv4ToBytes
	  * @Description: 将ipv4装换成字节码
	  * <pre class="code">
	  *  此方式可以转换ipv4字节码
	  *  int power = 3 - i;  
	  *  result += ip * Math.pow(256, power);  
	  * </pre>
	  * @param ip
	  * @return 
	  * byte[] 
	  * @throws
	  */
	private static byte[] ipv4ToBytes(String ipStr){
		byte[] res = new byte[5];
		res[0] = 0;
		String[] ipAddressInArray = ipStr.split("\\.");  
		for (int i = 0; i < ipAddressInArray.length; i++) {  
			int ip = Integer.parseInt(ipAddressInArray[i]);
			res[i+1] = (byte)ip;
		}
		return res;
	}
  
   /**
     * @Title: longToIp
     * @Description:  IP转换
     * @param i ip long
     * @return String 
     * @throws
     */
   public static String longToIp(long i) {  
       return ((i >> 24) & 0xFF) +   
                  "." + ((i >> 16) & 0xFF) +   
                  "." + ((i >> 8) & 0xFF) +   
                  "." + (i & 0xFF);  
  
   }  
}
