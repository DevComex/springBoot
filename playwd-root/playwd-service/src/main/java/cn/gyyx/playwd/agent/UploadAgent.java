package cn.gyyx.playwd.agent;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.playwd.utils.StringTools;

/**
 * 
 * <p>
 * 图片上传
 * </p>
 * 
 * @author lihu
 * @since 0.0.1
 */
@Component
public class UploadAgent {
    Logger logger = GYYXLoggerFactory.getLogger(getClass());

    public Map<String, Object> imageUp(HttpServletRequest request,
            HttpServletResponse response) {
        
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, Object> map = new HashMap<>();
        try {
            for (Iterator<String> it = multipartRequest.getFileNames(); it
                    .hasNext();) {
                String fileName = it.next();
                MultipartFile file = multipartRequest.getFile(fileName);
                String res = this.upload(fileName, file);
                if (!StringTools.isBlank(res)) {
                    ObjectMapper mapper = new ObjectMapper();
                    map = mapper.readValue(res, Map.class);// 转成map
                }
            }
            return map;
        } catch (Exception e) {
            logger.error("富文本上传图片接口错误", e);
        }
        return null;

    }

    @SuppressWarnings("deprecation")
    private String upload(String fileName, MultipartFile file)throws ClientProtocolException, IOException {
        CloseableHttpClient httpclient = new DefaultHttpClient();
        // post上传二进制流的时候 filename没有. Content-Disposition: form-data; name="imgFile"; filename="blob"
        File f = new File(
                "./" + StringTools.getUuid() + (file.getOriginalFilename().indexOf(".")>-1 ? file.getOriginalFilename()
                        .substring(file.getOriginalFilename().indexOf(".")):".jpg"));
        if (!f.exists()) {
            f.createNewFile();
        }
        try {
            HttpPost post = new HttpPost(
                    "http://up.gyyx.cn/Image/WebSiteSaveToTemp.ashx?group=official_site&width=200&height=200");
            file.transferTo(f);
            FileBody fileBody = new FileBody(f);
            MultipartEntity entity = new MultipartEntity();
            entity.addPart("file", fileBody);
            post.setEntity(entity);

            HttpResponse response = httpclient.execute(post);
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                HttpEntity entitys = response.getEntity();

                String res = EntityUtils.toString(entitys);
                return res;
            }
            return null;
        } finally {
            if (f.exists()) {
                f.delete();
            }
            httpclient.close();
        }
    }
    
    /**
     * 上传剪切图
     * @param file
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public Map<String, Object> upload(File file)throws ClientProtocolException, IOException {
    	
    	CloseableHttpClient httpclient = new DefaultHttpClient();
    	Map<String, Object> map = new HashMap<>();
    	try {
            HttpPost post = new HttpPost("http://up.gyyx.cn/Image/WebSiteSaveToTemp.ashx?group=official_site&width=200&height=200");
            FileBody fileBody = new FileBody(file);
            MultipartEntity entity = new MultipartEntity();
            entity.addPart("file", fileBody);
            post.setEntity(entity);

            HttpResponse response = httpclient.execute(post);
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                HttpEntity entitys = response.getEntity();
                String res = EntityUtils.toString(entitys);
                if (!StringTools.isBlank(res)) {
                    ObjectMapper mapper = new ObjectMapper();
                    map = mapper.readValue(res, Map.class);// 转成map
                }
                return map;
            }
            return null;
        } finally {
            if (file.exists()) {
            	file.delete();
            }
            httpclient.close();
        }
    }

}
