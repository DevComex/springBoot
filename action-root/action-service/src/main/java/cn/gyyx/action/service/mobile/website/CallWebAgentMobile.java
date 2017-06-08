package cn.gyyx.action.service.mobile.website;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.mobile.website.InterfaceReturnBean;
import cn.gyyx.core.security.MD5;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class CallWebAgentMobile {
    private static final Logger LOG = GYYXLoggerFactory.getLogger(CallWebAgentMobile.class);
    
    public ResultBean<InterfaceReturnBean> post(String url){
        LOG.debug("post url*:"+url);
        ResultBean<InterfaceReturnBean> result = new ResultBean<>(false,"连接失败了！",new InterfaceReturnBean());
        InterfaceReturnBean resultBean = new InterfaceReturnBean();
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(url);
        method.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
        method.releaseConnection();
        try {
            client.executeMethod(method);
            int status = method.getStatusCode();
            String message = getResponseMsg(method);
            resultBean.fillAll(status,message);
            if(status==200){
                result.setProperties(true, "成功", resultBean);
            }else{
                result.setProperties(false, "失败", resultBean);
            }
            LOG.debug("post debug:status:"+status+"--"+message);
        } catch (HttpException e) {
            LOG.warn("connect error！ url:"+url,e);
            resultBean.fillAll(HttpStatus.SC_INTERNAL_SERVER_ERROR, "连接失败!");
        } catch (IOException e) {
            LOG.warn("connect IO error url:"+url,e);
            resultBean.fillAll(HttpStatus.SC_INTERNAL_SERVER_ERROR, "连接失败.");
        }finally{
            method.releaseConnection();
        }
        LOG.debug("finished post "+resultBean.getHttpStatus());
        return result;
    }
    /**
     * TreeMap中要添加timestamp
     * @param url
     * @param para
     * @return
     */
    public ResultBean<InterfaceReturnBean> post(String url,TreeMap<String,String> para){
        ResultBean<InterfaceReturnBean> result = new ResultBean<>(false,"连接失败了！",new InterfaceReturnBean(500,"{}"));
        InterfaceReturnBean resultBean = new InterfaceReturnBean();
        String content = "";
        for(Entry<String,String> p : para.entrySet()){
            content += "&"+p.getKey() +"=" + p.getValue();
        }
        try {
            Response response = Request.Post(url)
                    .bodyString(content, ContentType.APPLICATION_FORM_URLENCODED).execute();
            HttpResponse httpResponse = response.returnResponse();
            resultBean.fillAll(httpResponse.getStatusLine().getStatusCode(), EntityUtils.toString(httpResponse.getEntity()));
            if(httpResponse.getStatusLine().getStatusCode()==200){
                result.setProperties(true, "成功!", resultBean);
            }else{
                result.setProperties(false, "失败!", resultBean);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * TreeMap中要有timestamp
     * @param url
     * @param para
     * @return
     */
    public String getSign(String domain,String projectName,String key,TreeMap<String,String> para){
        StringBuilder realUrl = new StringBuilder(domain);
        StringBuilder md5Url = new StringBuilder(projectName);
        StringBuilder encodUrl = new StringBuilder(projectName);
        Set<Entry<String, String>> entrySet = para.entrySet();
        boolean first = true;
        for(Entry<String,String> p :entrySet){
            if(p.getKey().contains("adreguserinfotemp")){
                md5Url.append(first?"?":"&").append(p.getKey()).append("=").append(p.getValue());
                encodUrl.append(first?"?":"&").append(p.getKey()).append("=").append(URLEncoder.encode(p.getValue()));
            }else if(p.getKey().contains("adreguserinfoforever")){
                md5Url.append(first?"?":"&").append(p.getKey()).append("=").append(p.getValue());
                encodUrl.append(first?"?":"&").append(p.getKey()).append("=").append(URLEncoder.encode(p.getValue()));
            }else if(p.getKey().contains("pagevisitguid")){
                md5Url.append(first?"?":"&").append(p.getKey()).append("=").append(p.getValue());
                encodUrl.append(first?"?":"&").append(p.getKey()).append("=").append(URLEncoder.encode(p.getValue()));
            }else{
                md5Url.append(first?"?":"&").append(p.getKey()).append("=").append(p.getValue());
                encodUrl.append(first?"?":"&").append(p.getKey()).append("=").append(p.getValue());
            }
            first = false;
        }
        realUrl.append(encodUrl.toString());
        String sign = MD5.encode(md5Url.append(key).toString());
        realUrl.append("&sign_type=").append("MD5").append("&sign=").append(sign);
        return realUrl.toString();
    }
    /**
     * TreeMap中要有timestamp
     * @param url
     * @param para
     * @return
     */
    public String getSign(String domain,String projectName,String key){
        StringBuilder realUrl = new StringBuilder(domain);
        StringBuilder md5Url = new StringBuilder(projectName);
        md5Url.append("?").append("timestamp=").append(System.currentTimeMillis()/1000);
        realUrl.append(md5Url.toString());
        String sign = MD5.encode(md5Url.append(key).toString());
        realUrl.append("&sign_type=").append("MD5").append("&sign=").append(sign);
        return realUrl.toString();
    }
    
    /**
     * 获取接口返回信息
     * @param method
     * @return
     */
    private String getResponseMsg(PostMethod method){
        StringBuilder response = new StringBuilder("");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(),"utf-8"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            LOG.warn("IOException in getResponseMsg:" + e);
        }
        return response.toString();
    }
}
