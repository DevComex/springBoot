/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：
 * @作者：GYYX-DEV
 * @联系方式：GYYX-DEV@gyyx.cn
 * @创建时间：2016年11月3日
 * @版本号：1.0.0
 * @本类主要用途描述：  光宇统一SDK
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.beans;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: ExtranetIPUtils
 * @description 获取本机外网IP 
 * 				ServerHost.getInstance().getExtranetIPAddress();
 *              ServerHost.getInstance().getExtranetIPv4Address();
 *              ServerHost.getInstance().getExtranetIPv6Address();
 *              <a href="http://bbs.chinaunix.net/thread-4180497-1-1.html">refer code page</a>
 * @author bozhencheng
 * @date 2016年11月3日
 */
public class ExtranetIPUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExtranetIPUtils.class);
	
	/**
	 * Singleton instance
	 */
	private static final ExtranetIPUtils instance = new ExtranetIPUtils();

	/**
	 * Access Control
	 */
	private ExtranetIPUtils() {
	};

	/**
	 * @return instance
	 */
	public static ExtranetIPUtils getInstance() {
		return instance;
	}

	public String getExtranetIPv4Address() {
		return searchNetworkInterfaces(IPAcceptFilterFactory.getIPv4AcceptFilter());
	}

	public String getExtranetIPv6Address() {
		return searchNetworkInterfaces(IPAcceptFilterFactory.getIPv6AcceptFilter());
	}

	public String getExtranetIPAddress() {
		return searchNetworkInterfaces(IPAcceptFilterFactory.getIPAllAcceptFilter());
	}

	private String searchNetworkInterfaces(IPAcceptFilter ipFilter) {
		try {
			Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
			while (enumeration.hasMoreElements()) {
				NetworkInterface networkInterface = enumeration.nextElement();
				// Ignore Loop/virtual/Non-started network interface
				if (networkInterface.isLoopback() || networkInterface.isVirtual() || !networkInterface.isUp()) {
					continue;
				}
				Enumeration<InetAddress> addressEnumeration = networkInterface.getInetAddresses();
				while (addressEnumeration.hasMoreElements()) {
					InetAddress inetAddress = addressEnumeration.nextElement();
					String address = inetAddress.getHostAddress();
					if (ipFilter.accept(address)) {
						return address;
					}
				}
			}
		} catch (SocketException e) {
			LOGGER.warn("Exception:",e);
			LOGGER.warn("Get Local extranet ip error:{},{},{}",e.getCause(),e.getMessage(),e.getStackTrace());
		}
		return "";
	}

}

class IPAcceptFilterFactory {
	
	public static IPAcceptFilter getIPAllAcceptFilter() {
		return IPAllAcceptFilter.getInstance();
	}

	public static IPAcceptFilter getIPv4AcceptFilter() {
		return IPv4AcceptFilter.getInstance();
	}

	public static IPAcceptFilter getIPv6AcceptFilter() {
		return IPv6AcceptFilter.getInstance();
	}

}

interface IPAcceptFilter {
	public String IPv6KeyWord = ":";

	public boolean accept(String ipAddress);
}

class IPAllAcceptFilter implements IPAcceptFilter {
	private static IPAcceptFilter instance = null;

	/**
	 * Access Control
	 */
	private IPAllAcceptFilter() {
	};

	/**
	 * Ignore multiple thread sync problem in extreme case
	 */
	public static IPAcceptFilter getInstance() {
		if (instance == null) {
			instance = new IPAllAcceptFilter();
		}
		return instance;
	}

	@Override
	public boolean accept(String ipAddress) {
		return true;
	}

}

class IPv4AcceptFilter implements IPAcceptFilter {
	private static IPAcceptFilter instance = null;

	/**
	 * Access Control
	 */
	private IPv4AcceptFilter() {
	};

	/**
	 * Ignore multiple thread sync problem in extreme case
	 */
	public static IPAcceptFilter getInstance() {
		if (instance == null) {
			instance = new IPv4AcceptFilter();
		}
		return instance;
	}

	@Override
	public boolean accept(String ipAddress) {
		return ipAddress != null && ipAddress.indexOf(IPv6KeyWord) == -1;
	}

}

class IPv6AcceptFilter implements IPAcceptFilter {
	private static IPAcceptFilter instance = null;

	/**
	 * Access Control
	 */
	private IPv6AcceptFilter() {
	};

	/**
	 * Ignore multiple thread sync problem in extreme case
	 */
	public static IPAcceptFilter getInstance() {
		if (instance == null) {
			instance = new IPv6AcceptFilter();
		}
		return instance;
	}

	@Override
	public boolean accept(String ipAddress) {
		return ipAddress != null && ipAddress.indexOf(IPv6KeyWord) > -1;
	}

}