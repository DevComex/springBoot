package cn.gyyx.playwd.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.render.RenderAPI;

import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.playwd.beans.playwd.NovelBean;

/**
 * 
  * <p>
  *   文件工具类
  * </p>
  *  
  * @author lihu
  * @since 0.0.1
 */
public class FileUtil {
    private static Logger logger = GYYXLoggerFactory.getLogger(FileUtil.class);

    /** 
     * 将存放在sourceFilePath目录下的源文件，打包成fileName名称的zip文件，并存放到zipFilePath路径下 
     * @param sourceFilePath :待压缩的文件路径 
     * @param zipFilePath :压缩后存放路径 
     * @param fileName :压缩后文件的名称 
     * @return 
     */  
    public static boolean fileToZip(String sourceFilePath, String zipFilePath,
            String fileName) {
        boolean flag = false;
        File sourceFile = new File(sourceFilePath);
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        ZipOutputStream zos = null;

        if (sourceFile.exists() == false) {
            logger.info("待压缩的文件目录：" + sourceFilePath + "不存在.");
            return flag;
        }
        try {
            File zipFile = new File(zipFilePath + "/" + fileName + ".zip");
            if (zipFile.exists()) {
                logger.info(
                    zipFilePath + "目录下存在名字为:" + fileName + ".zip" + "打包文件.");
                return flag;
            }
            File zipFilePathFile = new File(zipFilePath);
            if (!zipFilePathFile.exists()) {
                // 文件路径不存在时，自动创建目录
                zipFilePathFile.mkdir();
            }
            File[] sourceFiles = sourceFile.listFiles();
            if (null == sourceFiles || sourceFiles.length < 1) {
                logger.info("待压缩的文件目录：" + sourceFilePath + "里面不存在文件，无需压缩.");
                return flag;
            }
            fos = new FileOutputStream(zipFile);
            zos = new ZipOutputStream(new BufferedOutputStream(fos));
            byte[] bufs = new byte[1024 * 10];
            for (int i = 0; i < sourceFiles.length; i++) {
                // 创建ZIP实体，并添加进压缩包
                ZipEntry zipEntry = new ZipEntry(sourceFiles[i].getName());
                zos.putNextEntry(zipEntry);
                // 读取待压缩的文件并写进压缩包里
                fis = new FileInputStream(sourceFiles[i]);
                bis = new BufferedInputStream(fis, 1024 * 10);
                int read = 0;
                while ((read = bis.read(bufs, 0, 1024 * 10)) != -1) {
                    zos.write(bufs, 0, read);
                }
                zos.setEncoding("gbk");
            }
            flag = true;

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            // 关闭流
            try {
                if (null != bis)
                    bis.close();
                if (null != zos)
                    zos.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return flag;
    }
    
    
    /**
     * 返回文件 response
     */
    public static void downloadFile(File file,HttpServletResponse response,boolean isDelete) {
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
             logger.error("获取小说目录接口异常", ex);
        }
}
   
    public static String download(NovelBean novelBean,List<Map<String, Object>> list, String savePath,
            XWPFTemplate doc) {
        if (list.isEmpty() || novelBean == null) {
            return null;
        }
        String filePath = savePath + "/" + novelBean.getName() + "/";
        File filePathFile = new File(filePath);
        if (!filePathFile.exists()) {
            // 文件路径不存在时，自动创建目录
            filePathFile.mkdir();
        }
        if (doc != null) {
            try {
                // 数据转成word文件
                for (Map<String, Object> map : list) {
                    RenderAPI.render(doc, new HashMap<String, Object>() {
                        {

                            put("chaptertitle", map.get("chaptertitle"));
                            put("chapternum", map.get("chapternum"));
                            put("date", map.get("date"));
                            put("author", novelBean.getAccount());
                            put("content", map.get("content"));
                        }
                    });
                    // 将文件放入filePath 文件夹下
                    String fileName = "" + map.get("chapternum")
                            + map.get("chaptertitle");
                    FileOutputStream out = new FileOutputStream(
                            filePath + fileName + ".docx");
                    doc.write(out);
                    out.close();
                }
                // 将文件夹下的所有word 打成zip包
                FileUtil.fileToZip(filePath, savePath, novelBean.getName());
                return savePath + novelBean.getName() + ".zip";
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
