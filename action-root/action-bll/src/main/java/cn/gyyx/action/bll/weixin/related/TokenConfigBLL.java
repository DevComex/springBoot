package cn.gyyx.action.bll.weixin.related;

import cn.gyyx.action.beans.weixin.related.TokenParaBean;
import cn.gyyx.action.dao.weixin.related.TokenConfigDAO;

public class TokenConfigBLL {
	private static final String TOKEN_PARA_APPID_DB = "Appid";
	private static final String TOKEN_PARA_SECRET_DB = "Secret";
	private TokenConfigDAO tokenConfigDAO = new TokenConfigDAO();
	
	public TokenParaBean getTokenPara(String type){
		TokenParaBean tokenPara = new TokenParaBean();
		tokenPara.setAppid(tokenConfigDAO.getTokenPara(TOKEN_PARA_APPID_DB,type));
		tokenPara.setSecret(tokenConfigDAO.getTokenPara(TOKEN_PARA_SECRET_DB,type));
		return tokenPara;
	}
}
