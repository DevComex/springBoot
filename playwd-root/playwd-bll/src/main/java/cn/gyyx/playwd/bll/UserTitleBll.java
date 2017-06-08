 /**
    * -------------------------------------------------------------------------
    * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
    * @版权所有：北京光宇在线科技有限责任公司
    * @项目名称：玩家天地
    * @作者：李杜迪
    * @联系方式：lidudi@gyyx.cn
    * @创建时间：2017年3月9日下午2:23:23
    * @版本号：1.0.0
    *-------------------------------------------------------------------------
    */
package cn.gyyx.playwd.bll;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import cn.gyyx.playwd.beans.playwd.UserTitleBean;
import cn.gyyx.playwd.dao.playwd.UserTitleDao;

/**
 * 用户称号信息
 * @author lidudi
 *
 */
@Component
public class UserTitleBll {

	@Autowired
	public  UserTitleDao userTitleDao;
	
	/**
	 * 增加用户称号信息
	 * @param contentType
	 * @param contentId
	 * @param userId
	 * @param prizeId
	 * @param title
	 * @return
	 */
	public int addUserTitle(String contentType,int contentId,int userId,int prizeId,String title) {
		UserTitleBean bean=new UserTitleBean();
		if(!StringUtils.isEmpty(contentType)){
			bean.setContentType(contentType);
			bean.setContentId(contentId);
			bean.setPrizeId(prizeId);
		}
		bean.setUserId(userId);		
		bean.setTitle(title);
		return userTitleDao.insertUserTitle(bean);
	}

	/**
	 * 获取用户当前称号（有效期内且称号级别最高）
	 * @param code
	 */
	public String getUserCurrentTitle(int userId) {
		List<String> allTitle = userTitleDao.getValidTitleListByUserId(userId);
		String[] titleArray = {"新闻记者称号","点石成金称号","妙语连珠称号","丹青妙笔称号"};
		if(allTitle.size() > 0){
			for(String s :titleArray){
				if(allTitle.contains(s)){
					return s.replace("称号", "");
				}
			}
		}
		
		return "";
	}
}
