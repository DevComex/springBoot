package cn.gyyx.wd.wanjia.cartoon.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.wd.wanjia.ResultBean;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdManhuaPage;
import cn.gyyx.wd.wanjia.cartoon.beans.tools.UploadUtil;
import cn.gyyx.wd.wanjia.cartoon.dao.WanwdManhuaPageMapper;

@Controller
@RequestMapping("message")
public class ManHuaManageMessageController {
	@Autowired
	private WanwdManhuaPageMapper pageMapper;

	@RequestMapping("down")
	@ResponseBody
	public void down(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Integer bookCode) {
		ResultBean<Object> resultBean = new ResultBean<>(false, "fail");
		try {
			// Integer bookCode = 1032;
			String millis = String.valueOf(System.currentTimeMillis());
			List<WanwdManhuaPage> list = pageMapper.selectByParentCode(bookCode);
			String rootPath = request.getSession().getServletContext().getRealPath("/");
			String sourcePath = rootPath + "/assets/photo/" + bookCode;
			String zipPath = rootPath + "/assets/zip";
			if(list==null||list.size()==0){
				return ;
			}
			for (WanwdManhuaPage wanwdManhuaPage : list) {
				String url = wanwdManhuaPage.getPagePictureUrl();
				System.out.println(url);
				url= url.replaceAll("-small", "");
				UploadUtil.download(url,
						wanwdManhuaPage.getCode() + "-" + url.substring(url.length() - 34, url.length()), sourcePath);
			}

			String strBackUrl = request.getScheme()+"://" + request.getServerName() // 服务器地址
					+ ":" + request.getServerPort() // 端口号
					//+ request.getContextPath() // 项目名称
					;
			System.out.println("strBackUrl" + strBackUrl);
			System.out.println("sourcePath :" + sourcePath);
			System.out.println("zipPath :" + zipPath);

			UploadUtil.fileToZip(sourcePath, zipPath, bookCode.toString());
			String str = strBackUrl + "/assets/zip/" + bookCode + ".zip";
			
			  File fileZip = new File(zipPath+"/"+ bookCode + ".zip");
		  
		        this.downloadFile(fileZip, response, true);
			
			//response.sendRedirect(str);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void downloadFile(File file,HttpServletResponse response,boolean isDelete) {
        try {
            // 以流的形式下载文件。
            BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file.getPath()));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(file.getName().getBytes("UTF-8"),"ISO-8859-1"));
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
            if(isDelete)
            {
                file.delete();        //是否将生成的服务器端文件删除
            }
         } 
         catch (IOException ex) {
            ex.printStackTrace();
        }
    } 
	@RequestMapping("test")
	@ResponseBody
	public void down1(HttpServletRequest request, HttpServletResponse response) {
		System.out.println(request.getRequestURI());
		System.out.println(request.getRequestURL());
		
		String url = request.getScheme()+"://"+ request.getServerName()+request.getRequestURI()+"?"+request.getQueryString();
		System.out.println("获取全路径（协议类型：//域名/项目名/命名空间/action名称?其他参数）url="+url);
		String url2=request.getScheme()+"://"+ request.getServerName();//+request.getRequestURI();
		System.out.println("协议名：//域名="+url2);


		System.out.println("获取项目名="+request.getContextPath());
		System.out.println("获取参数="+request.getQueryString());
		System.out.println("获取全路径="+request.getRequestURL());
	}

	public static void main(String[] args) {
		String url = "541654615132156-samll.jpg";
		url= url.replaceAll("-samll", "");
		//url.replace("-small", "");
		System.out.println(url);
	}
}
