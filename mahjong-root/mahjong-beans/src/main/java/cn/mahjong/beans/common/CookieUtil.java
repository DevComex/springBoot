package cn.mahjong.beans.common;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;

/**
  * <p>
  *   CookieUtil描述
  * </p>
  *  
  * @author 
  * @since 0.0.1
  */
public class CookieUtil {
    /**
     * 设置cookie
     * @param response
     * @param name  cookie名字
     * @param value cookie值
     * @param maxAge cookie生命周期  以秒为单位
     */
    public static void addCookie(HttpServletResponse response,String name,String value,int maxAge){
        Cookie cookie = new Cookie(URLEncoder.encode(name),URLEncoder.encode(value));
        cookie.setPath("/");
        if(maxAge>0){
            cookie.setMaxAge(maxAge);
        }  
        response.addCookie(cookie);
    }
    
    public static void addCookie(HttpServletResponse response,String name,String value,int maxAge,String domian){
        Cookie cookie = new Cookie(URLEncoder.encode(name),URLEncoder.encode(value));
        cookie.setPath("/");
        if(maxAge>0){
            cookie.setMaxAge(maxAge);
        }
        if(!StringUtils.isEmpty(domian)){
            cookie.setDomain(domian);
        }
        response.addCookie(cookie);
    }
    
    /**
      * <p>
      *    移除cookie
      * </p>
      *
      * @action
      *    chenwen 2017年3月31日 下午2:41:33 描述
      *
      * @param response
      * @param name void
     */
    public static void removeCookie(HttpServletResponse response,String name){
        Cookie cookie = new Cookie(URLEncoder.encode(name),null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
    
    /**
     * 设置cookie
     * @param response
     * @param name  cookie名字
     * @param value cookie值
     * @param maxAge cookie生命周期  以秒为单位
     */
    public static void addCookie(HttpServletResponse response,String name,String value){
        Cookie cookie = new Cookie(URLEncoder.encode(name),URLEncoder.encode(value));
        cookie.setPath("/");
        response.addCookie(cookie);
    }
    
    /**
     * 根据名字获取cookie
     * @param request
     * @param name cookie名字
     * @return
     */
    public static Cookie getCookie(HttpServletRequest request,String name){
        Map<String,Cookie> cookieMap = ReadCookieMap(request);
        if(cookieMap.containsKey(name)){
            Cookie cookie = (Cookie)cookieMap.get(name);
            return cookie;
        }else{
            return null;
        }   
    }
    
    /**
     * 根据名字获取cookievalue
     * @param request
     * @param name cookie名字
     * @return
     */
    public static String getCookieValue(HttpServletRequest request,String name){
        Map<String,Cookie> cookieMap = ReadCookieMap(request);
        if(!cookieMap.containsKey(name)){
            return "";  
        }
        Cookie cookie = (Cookie)cookieMap.get(name);
        return URLDecoder.decode(cookie.getValue());
    }
    
    /**
     * 将cookie封装到Map里面
     * @param request
     * @return
     */
    private static Map<String,Cookie> ReadCookieMap(HttpServletRequest request){  
        Map<String,Cookie> cookieMap = new HashMap<String,Cookie>();
        Cookie[] cookies = request.getCookies();
        if(null!=cookies){
            for(Cookie cookie : cookies){
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }

}
