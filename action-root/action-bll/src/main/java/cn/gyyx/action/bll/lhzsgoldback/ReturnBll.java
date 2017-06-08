/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：LhzsGoldBack
 * @作者：范佳琪
 * @联系方式：fanjiaqig@gyyx.cn
 * @创建时间： 2016年04月13日
 * @版本号：
 * @本类主要用途描述：玩家金币返还记录业务逻辑层
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.bll.lhzsgoldback;

import org.slf4j.Logger;

import cn.gyyx.action.beans.lhzsgoldback.ReturnBean;
import cn.gyyx.action.dao.lhzsgoldback.ReturnDAO;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class ReturnBll {

	private static final Logger logger =GYYXLoggerFactory
			.getLogger(ReturnBll.class);
	
	private ReturnDAO returnDAO = new ReturnDAO();
	
	/**
	 * @Title addReturnBean
	 * @Description 添加用户返还记录
	 * @author fanjiaqi
	 * @param returnBean
	 */
	public void addReturnBean(ReturnBean returnBean){
		logger.warn("灵魂战神  金币返还活动-ReturnBll-addReturnBean,参数returnBean:" + returnBean);
		returnDAO.addReturnBean(returnBean);
	}
	
	/**
	 * @Title findByAccount
	 * @Description 根据用户账号查询用户返还记录
	 * @author fanjiaqi
	 * @param account
	 * @return
	 */
	public Integer findByAccount(String account){
		logger.warn("灵魂战神  金币返还活动-ReturnBll-addReturnBean,参数account:" + account);
		return returnDAO.findByAccount(account);
	}
}
