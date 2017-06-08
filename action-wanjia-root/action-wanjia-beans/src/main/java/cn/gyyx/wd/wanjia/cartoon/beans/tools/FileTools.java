/**------------------------------------------------------------------------- 
* 版权所有：北京光宇在线科技有限责任公司 
-------------------------------------------------------------------------*/ 

package cn.gyyx.wd.wanjia.cartoon.beans.tools;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 作        者：成龙 
 * 联系方式：chenglong@gyyx.cn 
 * 创建时间： 2016年6月7日 上午9:28:50
 * 描        述： 文件工具类
 */
public class FileTools {
	private static final Logger logger = LoggerFactory.getLogger(FileTools.class); 
	public static void downFile(HttpServletRequest request,HttpServletResponse response,String filename,Workbook workbook) throws IOException{
		OutputStream outs = null;
		try {
			setResponse(request,response,filename);
			
			outs = response.getOutputStream();
			workbook.write(outs);
			outs.flush();
		} catch (Exception e) {
			logger.error("下载文件异常！",e);
		} finally {
			if(outs != null){
				outs.close();
			}
		}
	}
	
	public static void downFile(HttpServletRequest request,HttpServletResponse response,String filePath) throws IOException{
		File f = new File(filePath);
        if (!f.exists()) {
        
            response.sendError(404, "文件没找到!");
            return;
        }
        BufferedInputStream br = new BufferedInputStream(new FileInputStream(f));
        byte[] buf = new byte[1024];
        int len = 0;
        response.reset(); // 非常重要
        OutputStream out = null;
		try {
			setResponse(request,response,f.getName());
	
	        out = response.getOutputStream();
	        out.flush();
	        while ((len = br.read(buf)) > 0){
	        	out.write(buf, 0, len);
	        }
	        br.close();
	        out.close();
		} catch (Exception e) {
			logger.error("下载文件异常！",e);
		} finally {
			br.close();
			if(out != null){
				out.close();
			}
		}
	}
	
	public static void setResponse(HttpServletRequest request,HttpServletResponse response,String filename) throws UnsupportedEncodingException {
		boolean isIE = true;
		// 火狐
		if (request.getHeader("User-Agent").toLowerCase()
				.indexOf("firefox") > 0) {
			isIE = false;
		}
		if (isIE) {
			response.setHeader(
					"Content-Disposition",
					"attachment; filename="
							+ java.net.URLEncoder.encode(filename, "UTF-8"));
		} else {
			response.setHeader(
					"Content-Disposition",
					"attachment; filename="
							+ new String(filename.getBytes("UTF-8"),
									"ISO8859-1"));
		}
		response.setContentType("application/x-msdownload");
        response.setCharacterEncoding("UTF-8");
	}

}
