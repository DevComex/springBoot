package cn.gyyx.action.service.weixin;

import net.sf.json.JSONObject;

import org.slf4j.Logger;

import cn.gyyx.action.beans.weixin.dictionary.AttentionDictionary;
import cn.gyyx.action.beans.weixin.related.TokenSuccessBean;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * 关注相关
 * @author Administrator
 *
 */
public class WeChatAttention {
    private static final Logger LOG = GYYXLoggerFactory.getLogger(WeChatAttention.class);
    
    public JSONObject getWeXinUserInfo(String type,String openid) {
        WeChatToken wechatToken = new WeChatToken();
        TokenSuccessBean token = wechatToken.wechatMakeToken(type);
        String resultStr = RequestResource.get(getAttentionUrl(token, openid));
        LOG.debug("get wxuser info:" + resultStr);
        if(resultStr.contains(AttentionDictionary.ATTENTION_SUBSCRIBE)){  
            return JSONObject.fromObject(resultStr);
        } else {
            return tryAgainWithObject(wechatToken, type, openid);
        }
    }
    
    public boolean isAttention(String type,String openid){
        LOG.debug("start judge attention!");
        boolean result = false;
        WeChatToken wechatToken = new WeChatToken();
        TokenSuccessBean token = wechatToken.wechatMakeToken(type);
        Long exeTime = System.currentTimeMillis();
        String resultStr = RequestResource.get(getAttentionUrl(token, openid));
        LOG.debug("is attention result:"+result);
        if(resultStr.contains(AttentionDictionary.ATTENTION_SUBSCRIBE)){  
            JSONObject jsonobject = JSONObject.fromObject(resultStr);
            if("1".equals(jsonobject.getString(AttentionDictionary.ATTENTION_SUBSCRIBE))){
                result = true;
            }
            LOG.debug("attention times:"+(System.currentTimeMillis()-exeTime));
        }else{    //try again!
            result = tryAgain(wechatToken,type,openid);
            LOG.warn("get user information eroor! errcode"+result);
            LOG.debug("attention try again times:"+(System.currentTimeMillis()-exeTime));
        }
        LOG.debug("finish judge attention!");
        return result;
    }
    
    public boolean isAttention(String type,String openid,String token){
        LOG.debug("start judge attention(token)!");
        boolean result = false;
        String resultStr = RequestResource.get(getAttentionUrl(new TokenSuccessBean(token,"7200"), openid));
        LOG.debug("is attention result:"+result);
        if(resultStr.contains(AttentionDictionary.ATTENTION_SUBSCRIBE)){
            JSONObject jsonobject = JSONObject.fromObject(resultStr);
            if("1".equals(jsonobject.getString(AttentionDictionary.ATTENTION_SUBSCRIBE))){
                result = true;
            }
        }else{
            LOG.warn("get user information eroor! errcode"+result);
        }
        LOG.debug("finish judge attention(token)!");
        return result;
    }
    
    public boolean tryAgain (WeChatToken wechatToken,String type,String openid){
        boolean result = false;
        TokenSuccessBean resultToken = wechatToken.makeToken(type);
        String resultStr = RequestResource.get(getAttentionUrl(resultToken, openid));
        LOG.debug("try again token :"+resultStr);
        try{
            JSONObject jsonobject = JSONObject.fromObject(resultStr);
            if(resultStr.contains(AttentionDictionary.ATTENTION_SUBSCRIBE)&&"1".equals(jsonobject.getString(AttentionDictionary.ATTENTION_SUBSCRIBE))){  
                result = true;
            }
        }catch(Exception e){
            LOG.warn("try attention again！"+result);
        }
        return result;
    }
    
    public JSONObject tryAgainWithObject(WeChatToken wechatToken,String type,String openid){
        TokenSuccessBean resultToken = wechatToken.makeToken(type);
        String resultStr = RequestResource.get(getAttentionUrl(resultToken, openid));
        LOG.debug("try again token :"+resultStr);
        return JSONObject.fromObject(resultStr);
    }
    
    private String getAttentionUrl(TokenSuccessBean token,String openid){
        StringBuilder url = new StringBuilder(AttentionDictionary.ATTENTION_PATH);
        url.append(AttentionDictionary.ATTENTION_TOKEN).append("=").append(token.getAccessToken())
        .append("&").append(AttentionDictionary.ATTENTION_OPENID).append("=").append(openid)
        .append("&").append(AttentionDictionary.ATTENTION_LANG).append("=").append("zh_CN");
        return url.toString();
    }
}
