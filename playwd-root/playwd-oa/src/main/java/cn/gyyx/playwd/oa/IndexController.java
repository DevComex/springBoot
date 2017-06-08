package cn.gyyx.playwd.oa;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.core.memcache.RemoteMemcacheInfoManager;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.playwd.agent.UploadAgent;
import cn.gyyx.playwd.beans.common.ResultBean;
import cn.gyyx.playwd.beans.playwd.CategoryBean;
import cn.gyyx.playwd.bll.CategoryBll;
import cn.gyyx.playwd.oa.common.web.BaseController;

/**
 * 版 权：光宇游戏 作 者：lihu 创建时间：2016年9月21日 下午1:36:12 描 述：
 */
@Controller
@RequestMapping(value = "")
public class IndexController extends BaseController {
    private Logger logger = GYYXLoggerFactory.getLogger(getClass());
    @Autowired
    CategoryBll categoryBll;
    @Autowired
    UploadAgent uploadAgent;

    /**
     * 首页
     */
    @RequestMapping({ "/index", "", "/" })
    public String toIndex(Model model, HttpServletRequest request) {
        List<CategoryBean> list = categoryBll.getParentCategory(CategoryBean.CategoryType.article.Value());
        model.addAttribute("list", list);
        return "index";
    }
/**
 * 
  * <p>
  *   图片上传
  * </p>
  *
  * @action
  *    lihu 2017年3月16日 下午3:41:41 描述
  *
  * @param model
  * @param request
  * @param response void
 */
    @RequestMapping("/imageUp")
    public void imageUp( HttpServletRequest request,
            HttpServletResponse response) {
        try {
                String oriName = request.getParameter("fileName");
                String title = request.getParameter("pictitle");
                logger.info("富文本上传图片 文件名称{}", oriName);
                Map<String, Object> map = uploadAgent.imageUp(request, response);
                PrintWriter writer = response.getWriter();
                writer.write("{'url':'" + map.get("Url") + "','title':'" + title
                        + "','original':'" + oriName + "','state':'SUCCESS'}");
                writer.flush();
                writer.close();
        } catch (IOException e) {
            logger.error("富文本上传图片接口错误", e);
        }
       

    }
    
    /**
     * 
      * <p>
      *    post上传裁剪的图片
      * </p>
      *
      * @action
      *    chenglong 2017年4月28日 下午2:58:35 描述
      *
      * @param request
      * @param response void
     */
    @RequestMapping(value = "/imageUpPost", method = { RequestMethod.POST })
    @ResponseBody
    public ResultBean<String> imageUpPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> map = uploadAgent.imageUp(request, response);
            if(map == null || !map.containsKey("Status")){
                return new ResultBean<>(false, "上传图片失败", null);
            }
            return new ResultBean<>((Integer)map.get("Status")==1?true:false, 
                    (String)map.get("Message"),
                    (String)map.get("Url"));
        } catch (Exception e) {
            logger.error("post上传裁剪的图片接口错误", e);
            return new ResultBean<>(false, "上传图片失败", null);
        }

    }
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String Test(Locale locale, Model model, HttpServletRequest request) {
        logger.info("HTTP_X_FORWARDED_FOR:"+request.getHeader("HTTP_X_FORWARDED_FOR")+"|REMOTE_ADDR:"+request.getHeader("REMOTE_ADDR"));

        model.addAttribute("IP", getIp()+":"+request.getLocalPort());
        //File curFile = new File("./"+this.getClass().getName());
        File curFile = new File(this.getClass().getClassLoader().getResource("/").getPath());
        //System.out.println(this.getClass().getClassLoader().getResource("/").getPath());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        model.addAttribute("LastModifyTime",df.format(new Date(curFile.lastModified())));
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
