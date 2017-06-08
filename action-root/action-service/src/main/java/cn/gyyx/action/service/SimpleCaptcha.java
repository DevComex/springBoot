package cn.gyyx.action.service;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.core.memcache.XMemcachedClientAdapter;

/** 
* 类:SimpleCaptcha<br />
* 叙述:产生一个简单验证码，仅用于Java程序接口防刷<br />
* 注释日期:2015年11月16日<br />
* @author lizepu
*/
public class SimpleCaptcha {
	static final String COOKIE_CHECK_NAME = "GYYX_CAPTCHA4J";
	static Logger log = GYYXLoggerFactory.getLogger(SimpleCaptcha.class);
	static final XMemcachedClientAdapter captcha = new XMemcachedClientAdapter("OldCaptchaCache");
	static Random random;
	static {
		try {
			random = SecureRandom.getInstance("SHA1PRNG");
		} catch (NoSuchAlgorithmException e) {
			log.warn("SHA1P RNG algorithm not supported.");
			log.warn("Default RNG algorithm can be predict linearly.");
			random = new Random();
		}
	}
	
	public static boolean validate(HttpServletRequest request,String cap) {
		Cookie[] cookie = request.getCookies();
		if(cookie != null&&cap != null) {
			for(Cookie c:cookie) {
				if(c.getName().equals(COOKIE_CHECK_NAME)) {
					if(cap.equalsIgnoreCase(captcha.get(c.getValue(), String.class))) {
						return captcha.delete(c.getValue());
					}
				}
			}
		} 
		return false;
	}
	
	public static void write(HttpServletResponse response) throws IOException {
		String str = getRandomString(4);
		String key = getRandomString(32);
		Cookie cookie = new Cookie(COOKIE_CHECK_NAME,key);
		cookie.setMaxAge(60 * 5);
		cookie.setDomain(".gyyx.cn");
		cookie.setPath("/");
		response.addCookie(cookie);
		
		int width = 100,height = 45;
		BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) image.getGraphics();
		g.setColor(getRandomColdColor());
		g.fillRect(0, 0, width, height);
		for(int i = 0;i < 15;i++) {
			g.setColor(getRandomWarmColor());
			int x1 = getRandomNum(0,width);
			int x2 = getRandomNum(0,width);
			int y1 = getRandomNum(0,height);
			int y2 = getRandomNum(0,height);
			g.drawLine(x1, y1, x2, y2);
		}
		
		char[] strChar = str.toCharArray();
		for(int i = 0;i < strChar.length;i++) {
			g.setColor(getRandomWarmColor());
			g.setFont(new Font("Calibri", Font.PLAIN, getRandomNum(30,40)));
			g.drawString(strChar[i] + "",100 * i / strChar.length,20 + getRandomNum(0,15));
		}
		g.dispose();
		ImageIO.write(image, "png", response.getOutputStream());
		captcha.set(key, 60 * 5, str);
	}
	
	private static String getRandomString(int length) {
		char[] src = new char[length];
		for(int i = 0;i < src.length;i++) {
			if(getRandomNum(0,1) == 0) {
				src[i] = (char)getRandomNum(65,90);
			} else {
				src[i] = (char)getRandomNum(97,122);
			}
		}
		return new String(src);
	}
	
	private static int getRandomNum(int smallistNum, int BiggestNum) {
		return (Math.abs(random.nextInt()) % (BiggestNum - smallistNum + 1))
				+ smallistNum;
	}
	
	private static Color getRandomColdColor() {
		int r = getRandomNum(0,255);
		int g = getRandomNum(r,255);
		int b = getRandomNum(g,255);
		return new Color(r,g,b);
	}
	private static Color getRandomWarmColor() {
		int r = getRandomNum(0,255);
		int g = getRandomNum(0,r);
		int b = getRandomNum(0,g);
		return new Color(r,g,b);
	}
}
