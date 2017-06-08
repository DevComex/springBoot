/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：
 * @作者：bozhencheng
 * @联系方式：bozhencheng@gyyx.cn
 * @创建时间：2016年5月24日 
 * @版本号：
 * @本类主要用途描述： 问道十年缤纷活动
 *-------------------------------------------------------------------------
 */
package cn.gyyx.service;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import cn.gyyx.action.common.beans.ResultBean;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import net.sf.json.JSONObject;

/**
 * @ClassName: UploadPicAgent
 * @description 上传图片工具类
 * @author bozhencheng
 * @date 2016年5月25日
 */
public class UploadPicAgent {


	private static final Logger logger = GYYXLoggerFactory.getLogger(UploadPicAgent.class);
	static final String BOUNDARY = "----MyFormBoundarySMFEtUYQG6r5B920"; 
	
	/**
	  * @Title: updatePicToStaticServer
	  * @Description:  上传文件到静态资源服务器
	  * @param file
	  * @return ResultBean<Boolean> 
	  * @throws
	  */
	public static ResultBean<JSONObject> updatePicToStaticServer(File file){
		ResultBean<JSONObject> result = new ResultBean<>();
		List<String[]> strParams = new ArrayList<>(); // 文字post项集 strParams 1:key 2:value
		strParams.add(new String[]{"group","wd"});
		List<String[]> fileParams = new ArrayList<>(); // 文件的post项集 fileParams 1:fileField, 2.fileName, 3.fileType, 4.filePath
		// image/jpeg
		fileParams.add(new String[]{"file",file.getAbsolutePath(),"image/png",file.getAbsolutePath()});
		
		HttpURLConnection hc = null;  //http连接器
        ByteArrayOutputStream bos = null;//byte输出流，用来读取服务器返回的信息   
        InputStream is = null;//输入流，用来读取服务器返回的信息  
        byte[] res = null;//保存服务器返回的信息的byte数组
        
        try { 
            URL url = new URL("http://up.gyyx.cn/Image/WebSiteSaveToTemp.ashx"); 
            hc = (HttpURLConnection) url.openConnection(); 
 
            hc.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY); 
            hc.setRequestProperty("Charsert", "UTF-8"); 
            // 发送POST请求必须设置如下两行 
            hc.setDoOutput(true); 
            hc.setDoInput(true); 
            hc.setUseCaches(false); 
            hc.setRequestMethod("POST"); 
 
            OutputStream dout = hc.getOutputStream(); 
            ////1.先写文字形式的post流 
            //头 
            String boundary = BOUNDARY; 
            //中 
            StringBuilder resSB = new StringBuilder("\r\n"); 
            //尾 
            String endBoundary = "\r\n--" + boundary + "--\r\n"; 
            //strParams 1:key 2:value 
			for(String[] parsm : strParams){ 
                String key = parsm[0]; 
                String value = parsm[1]; 
                resSB.append("Content-Disposition: form-data; name=\"").append(key).append("\"\r\n").append("\r\n").append(value).append("\r\n").append("--").append(boundary).append("\r\n"); 
            } 
            String boundaryMessage = resSB.toString(); 
             
            //写出流 
            dout.write( ("--"+boundary+boundaryMessage).getBytes("utf-8") ); 
             
            //2.再写文件开式的post流 
            //fileParams 1:fileField, 2.fileName, 3.fileType, 4.filePath 
            resSB = new StringBuilder(); 
            for(int i=0, num=fileParams.size(); i<num; i++){ 
                String[] parsm = fileParams.get(i); 
                String fileField = parsm[0]; 
                String fileName = parsm[1]; 
                String fileType = parsm[2]; 
                String filePath = parsm[3]; 
                 
                resSB.append("Content-Disposition: form-data; name=\"").append(fileField).append("\"; filename=\"").append( 
                        fileName).append("\"\r\n").append("Content-Type: ").append(fileType).append("\r\n\r\n"); 
                 
                dout.write(resSB.toString().getBytes("utf-8") ); 
                 
                //开始写文件 
                DataInputStream in = new DataInputStream(new FileInputStream(file)); 
                int bytes = 0; 
                byte[] bufferOut = new byte[1024 * 5]; 
                while ((bytes = in.read(bufferOut)) != -1) { 
                    dout.write(bufferOut, 0, bytes); 
                } 
                 
                if(i<num-1){ 
                    dout.write( endBoundary.getBytes("utf-8") ); 
                } 
                 
                in.close(); 
            } 
                 
             
            //3.最后写结尾 
            dout.write( endBoundary.getBytes("utf-8") );    
            dout.close();  
            int ch;  
            is = hc.getInputStream();    
            bos = new ByteArrayOutputStream(); 
            while ((ch = is.read()) != -1) { 
                bos.write(ch); 
            } 
            res = bos.toByteArray(); 
            JSONObject jsonObj = JSONObject.fromObject(new String(res));
         	result.setIsSuccess(true);
         	result.setData(jsonObj);
         	
        } catch (IOException e) { 
        	result.setIsSuccess(false);
        	logger.warn("文件"+file.getAbsolutePath()+"上传失败！");
        } finally { 
            try { 
                if (bos != null) 
                    bos.close();  
                if (is != null) 
                    is.close();  
            } catch (Exception e2) { 
                e2.printStackTrace(); 
            } 
        } 
		return result;
	}
	
	
	/**
	  * @Title: updatePicToStaticServer
	  * @Description:  上传文件到静态资源服务器
	  * @param file
	  * @return ResultBean<Boolean> 
	  * @throws
	  */
	public static ResultBean<JSONObject> updatePicToStaticServer(String file){
		return updatePicToStaticServer(new File(file));
	}
}
