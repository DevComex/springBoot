package cn.gyyx.playwd.ui;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.core.memcache.RemoteMemcacheInfoManager;
import cn.gyyx.playwd.agent.UploadAgent;
import cn.gyyx.playwd.beans.common.ResultBean;
import cn.gyyx.playwd.service.ImgEditorService;
import cn.gyyx.playwd.ui.common.web.BaseController;

/**
 * 版        权：光宇游戏
 * 作        者：ChengLong
 * 创建时间：2016年9月21日 下午1:36:12
 * 描        述：
 */
@Controller
@RequestMapping()
public class IndexController extends BaseController{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
    @Autowired
    public UploadAgent uploadAgent;
    
    @Autowired
    public ImgEditorService imgEditorService;
    
	/**
	 * 首页
	 */
	@RequestMapping({"/index","","/"})
	public String toIndex(Model model, HttpServletRequest request) {
		return "playwd/article";
	}
	
	/**
	 * 图文首页
	 */
	@GetMapping("/article")
	public String home(Model model, HttpServletRequest request) {
		return "playwd/article";
	}

	/**
	 * 上传页
	 */
	@GetMapping("/article/upload")
	public String upload(Model model, HttpServletRequest request) {
		return "playwd/upload";
	}
	
	/**
	 * 图文列表
	 */
	@GetMapping("/article/lists")
	public String list(Model model, HttpServletRequest request) {
		return "playwd/list";
	}
	
	/**
	 * 用户中心 ——我的上传
	 */
	@GetMapping("/userCenter/myupload")
	public String myupload(Model model, HttpServletRequest request) {
		model.addAttribute("title", "我的上传");
		return "userCenter/myupload";
	}
	
	/**
	 * 用户中心 ——我的收藏
	 */
	@GetMapping("/userCenter/mycollect")
	public String mycollect(Model model, HttpServletRequest request) {
		model.addAttribute("title", "我的收藏");
		return "userCenter/myCollectArticle";
	}
	
	/**
	 * 用户中心 ——编辑回复
	 */
	@GetMapping("/userCenter/editorMessage")
	public String editorMessage(Model model, HttpServletRequest request) {
		model.addAttribute("title", "编辑回复");
		return "userCenter/editorMessage";
	}
	
	/**
	 * 用户中心 ——我的消息
	 */
	@GetMapping("/userCenter/myMessage")
	public String myMessage(Model model, HttpServletRequest request) {
		model.addAttribute("title", "我的消息");
		return "userCenter/myMessage";
	}
	
	/**
	 * 用户中心 ——我的角色
	 */
	@GetMapping("/userCenter/myRoles")
	public String myRole(Model model, HttpServletRequest request) {
		model.addAttribute("title", "我的角色");
		return "userCenter/myRole";
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
	@GetMapping("/imageUp")
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
     * 裁剪图片上传
     * @param imageFileUrl 原图地址
     * @param xaxis X轴
     * @param yaxis Y轴
     * @param width 裁剪区域宽度
     * @param height 裁剪区域高度
	 * @param cutWidth 裁剪后宽度
	 * @param cutHeight裁剪后高度
	 * @return
	 */
	@PostMapping("/imageUpPost")
	@ResponseBody
    public ResultBean<String> imageUpPost(String imageFileUrl,Integer xaxis,Integer yaxis,
    		Integer width,Integer height,Integer cutWidth,Integer cutHeight) {
        try {
        	if(xaxis==null||yaxis==null||width==null||height==null||cutWidth==null||cutHeight==null){
        		return new ResultBean<>(false, "裁剪图片上传失败", null);
        	}
        	URL url = new URL(imageFileUrl); 
            return imgEditorService.cutImg(url, xaxis, yaxis, width, height,cutWidth,cutHeight);
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
