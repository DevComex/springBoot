package cn.gyyx.action.ui;

import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.gyyx.action.bll.jswswxsign.DateTools;
import cn.gyyx.core.memcache.RemoteMemcacheInfoManager;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = GYYXLoggerFactory
			.getLogger(HomeController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model, HttpServletRequest request) {
		logger.info("Welcome ActionV2! The client locale is "+locale+".");

		return "home";
	}

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String Test(Locale locale, Model model, HttpServletRequest request) {
		logger.info("HTTP_X_FORWARDED_FOR:"+request.getHeader("HTTP_X_FORWARDED_FOR")+"|REMOTE_ADDR:"+request.getHeader("REMOTE_ADDR"));

		model.addAttribute("IP", getIp()+":"+request.getLocalPort());
		//File curFile = new File("./"+this.getClass().getName());
		File curFile = new File(this.getClass().getClassLoader().getResource("/").getPath());
		//System.out.println(this.getClass().getClassLoader().getResource("/").getPath());
		model.addAttribute("LastModifyTime",
				DateTools.formatDate(new Date(curFile.lastModified()),"yyyy-MM-dd HH:mm:ss"));
		model.addAttribute("CurrentVersion",
				RemoteMemcacheInfoManager.getCurrentVersion());
		//model.addAttribute("ClientIp", Ip.getCurrent(request).asNumber());
		return "home";
	}

	/**
	 * 多IP处理，可以得到最终ip
	 * 
	 * @return
	 */
	public static String getIp() {
		String localip = null;// 本地IP，如果没有配置外网IP则返回它
		String netip = null;// 外网IP
		try {
			Enumeration<NetworkInterface> netInterfaces = NetworkInterface
					.getNetworkInterfaces();
			InetAddress ip = null;
			boolean finded = false;// 是否找到外网IP
			while (netInterfaces.hasMoreElements() && !finded) {
				NetworkInterface ni = netInterfaces.nextElement();
				Enumeration<InetAddress> address = ni.getInetAddresses();
				while (address.hasMoreElements()) {
					ip = address.nextElement();
					// System.out.println(ni.getName() + ";" +
					// ip.getHostAddress()
					// + ";ip.isSiteLocalAddress()="
					// + ip.isSiteLocalAddress()
					// + ";ip.isLoopbackAddress()="
					// + ip.isLoopbackAddress());
					logger.debug("HomeController getIp {}",ip.getHostAddress());
					if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress()
							&& ip.getHostAddress().indexOf(":") == -1) {// 外网IP
						netip = ip.getHostAddress();
						finded = true;
						break;
					} else if (ip.isSiteLocalAddress()
							&& !ip.isLoopbackAddress()
							&& ip.getHostAddress().indexOf(":") == -1) {// 内网IP
						localip = ip.getHostAddress();
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		if (netip != null && !"".equals(netip)) {
			return netip;
		} else {
			return localip;
		}
	}

}
