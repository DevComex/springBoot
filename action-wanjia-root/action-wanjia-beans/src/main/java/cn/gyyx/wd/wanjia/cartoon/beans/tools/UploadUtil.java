package cn.gyyx.wd.wanjia.cartoon.beans.tools;

import java.io.BufferedInputStream;  
import java.io.BufferedOutputStream;  
import java.io.File;  
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;  
import java.io.IOException;  
import java.io.InputStream;  
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;  
  
import javax.servlet.http.HttpServletResponse;  
  
import org.apache.log4j.Logger;  
import org.apache.tools.zip.ZipEntry;  
import org.apache.tools.zip.ZipOutputStream;  
public class UploadUtil {
	/**
	 * 
	 * @param response
	 * @param tmpFileName zip名称
	 * @param filePath 下载文件地址
	 * @param file1  数据数组
	 * @return
	 */
    public static String execute(HttpServletResponse response,String tmpFileName ,String filePath,File[] file1) {  
        //生成的ZIP文件名为Demo.zip  
        //String tmpFileName = "Demo.zip";  
        byte[] buffer = new byte[1024];  
        String strZipPath = filePath + tmpFileName;  
        try {  
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(  
                    strZipPath));  
             
            for (int i = 0; i < file1.length; i++) {  
                FileInputStream fis = new FileInputStream(file1[i]);  
                out.putNextEntry(new ZipEntry(file1[i].getName()));  
                //设置压缩文件内的字符编码，不然会变成乱码  
                out.setEncoding("GBK");  
                int len;  
                // 读入需要下载的文件的内容，打包到zip文件  
                while ((len = fis.read(buffer)) > 0) {  
                    out.write(buffer, 0, len);  
                }  
                out.closeEntry();  
                fis.close();  
            }  
            out.close();  
            downFile(response, tmpFileName,filePath);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
  
 
  
    /** 
     * 文件下载 
     * @param response 
     * @param str 
     */  
    private static void downFile(HttpServletResponse response, String str,String filePath) {  
        try {  
            String path = filePath + str;  
            File file = new File(path);  
            if (file.exists()) {  
                InputStream ins = new FileInputStream(path);  
                BufferedInputStream bins = new BufferedInputStream(ins);// 放到缓冲流里面  
                OutputStream outs = response.getOutputStream();// 获取文件输出IO流  
                BufferedOutputStream bouts = new BufferedOutputStream(outs);  
                response.setContentType("application/x-download");// 设置response内容的类型  
                response.setHeader(  
                        "Content-disposition",  
                        "attachment;filename="  
                                + URLEncoder.encode(str, "UTF-8"));// 设置头部信息  
                int bytesRead = 0;  
                byte[] buffer = new byte[8192];  
                // 开始向网络传输文件流  
                while ((bytesRead = bins.read(buffer, 0, 8192)) != -1) {  
                    bouts.write(buffer, 0, bytesRead);  
                }  
                bouts.flush();// 这里一定要调用flush()方法  
                ins.close();  
                bins.close();  
                outs.close();  
                bouts.close();  
            } else {  
                response.sendRedirect("../error.jsp");  
            }  
        } catch (IOException e) {  
            e.printStackTrace();;  
        }  
    } 
    
    /** 
     * 将存放在sourceFilePath目录下的源文件，打包成fileName名称的zip文件，并存放到zipFilePath路径下 
     * @param sourceFilePath :待压缩的文件路径 
     * @param zipFilePath :压缩后存放路径 
     * @param fileName :压缩后文件的名称 
     * @return 
     */  
    public static boolean fileToZip(String sourceFilePath,String zipFilePath,String fileName){  
        boolean flag = false;  
        File sourceFile = new File(sourceFilePath);  
        FileInputStream fis = null;  
        BufferedInputStream bis = null;  
        FileOutputStream fos = null;  
        ZipOutputStream zos = null;  
       
        if(sourceFile.exists() == false){  
            System.out.println("待压缩的文件目录："+sourceFilePath+"不存在.");  
        }else{  
            try {  
                File zipFile = new File(zipFilePath + "/" + fileName +".zip");  
                if(zipFile.exists()){  
                    System.out.println(zipFilePath + "目录下存在名字为:" + fileName +".zip" +"打包文件.");  
                }else{  
                	File dirFile = new File(zipFilePath);
                	 if(!dirFile.exists()){ 
             	        //文件路径不存在时，自动创建目录
                		 dirFile.mkdir();
             	      }
                	 File zipFilePathFile = new File(zipFilePath );
                	 if(! zipFilePathFile.exists()){ 
                		 //文件路径不存在时，自动创建目录
                		 zipFilePathFile.mkdir();
                	 }
                    File[] sourceFiles = sourceFile.listFiles();  
                    if(null == sourceFiles || sourceFiles.length<1){  
                        System.out.println("待压缩的文件目录：" + sourceFilePath + "里面不存在文件，无需压缩.");  
                    }else{  
                        fos = new FileOutputStream(zipFile);  
                        zos = new ZipOutputStream(new BufferedOutputStream(fos));  
                        byte[] bufs = new byte[1024*10];  
                        for(int i=0;i<sourceFiles.length;i++){  
                            //创建ZIP实体，并添加进压缩包  
                            ZipEntry zipEntry = new ZipEntry(sourceFiles[i].getName());  
                            zos.putNextEntry(zipEntry);  
                            //读取待压缩的文件并写进压缩包里  
                            fis = new FileInputStream(sourceFiles[i]);  
                            bis = new BufferedInputStream(fis, 1024*10);  
                            int read = 0;  
                            while((read=bis.read(bufs, 0, 1024*10)) != -1){  
                                zos.write(bufs,0,read);  
                            }  
                        }  
                        flag = true;  
                    }  
                   
                }  
                
            } catch (FileNotFoundException e) {  
                e.printStackTrace();  
                throw new RuntimeException(e);  
            } catch (IOException e) {  
                e.printStackTrace();  
                throw new RuntimeException(e);  
            } finally{  
                //关闭流  
                try {  
                    if(null != bis) bis.close();  
                    if(null != zos) zos.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                    throw new RuntimeException(e);  
                }  
            }  
        }  
        return flag;  
    }  
    /**
     * 下载文件
     * @param urlString
     * @param filename
     * @param savePath
     * @throws Exception
     */
    public static void download(String urlString, String filename, String savePath) throws Exception {
		// 构造URL
		URL url = new URL(urlString);
		// 打开连接
		URLConnection con = url.openConnection();
		// 设置请求超时为5s
		con.setConnectTimeout(5 * 1000);
		// 输入流
		InputStream is = con.getInputStream();

		// 1K的数据缓冲
		byte[] bs = new byte[1024];
		// 读取到的数据长度
		int len;
		// 输出的文件流
		File sf = new File(savePath);
		if (!sf.exists()) {
			sf.mkdirs();
		}
		
		OutputStream os = new FileOutputStream(sf.getPath() + File.separator + filename);
		// 开始读取
		while ((len = is.read(bs)) != -1) {
			os.write(bs, 0, len);
		}
		// 完毕，关闭所有链接
		os.close();
		is.close();
	}
    
    
  //删除文件和目录
    private static void clearFiles(String workspaceRootPath){
         File file = new File(workspaceRootPath);
         if(file.exists()){
              deleteFile(file);
         }
    }
    private static void deleteFile(File file){
         if(file.isDirectory()){
              File[] files = file.listFiles();
              for(int i=0; i<files.length; i++){
                   deleteFile(files[i]);
              }
         }
        // file.delete();
    }
    
}
