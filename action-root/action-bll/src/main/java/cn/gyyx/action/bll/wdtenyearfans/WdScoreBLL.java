/*-------------------------------------------------------------------------
* 版权所有：北京光宇在线科技有限责任公司
* 作者：chenpeilan
* 联系方式：chenpeilan@gyyx.cn
* 创建时间： 2016年3月31日
* 版本号：
* 本类主要用途描述：问道十周年粉丝榜积分相关bll
-------------------------------------------------------------------------*/
package cn.gyyx.action.bll.wdtenyearfans;

import org.slf4j.Logger;

import cn.gyyx.action.beans.wdtenyearfans.WdAccountScoreBean;
import cn.gyyx.action.dao.wdtenyearfans.WdAccountScoreDAO;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * @ClassName: nominatedBLL
 * @Description: TODO 问道十周年粉丝榜积分相关BLL
 * @author chenpeilan chenpeilan@gyyx.cn
 * @date 2016年3月31日 下午4:35:01
 *
 */
public class WdScoreBLL {
	
	private WdAccountScoreDAO wdAccountScoreDAO = new WdAccountScoreDAO();
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(WdScoreBLL.class);
	
	/**
	 * 
	* @Title: addWdAccountScoreBean
	* @Description: TODO 增加积分表数据
	* @param @param wdAccountScoreBean    
	* @return void    
	* @throws
	 */
	public void addWdAccountScoreBean(WdAccountScoreBean wdAccountScoreBean){
		logger.debug("wdAccountScoreBean:"+wdAccountScoreBean);
		wdAccountScoreDAO.insertWdAccountScoreBean(wdAccountScoreBean);
	}
	
	/**
	 * 
	* @Title: selectWdAccountScoreBeanByAccountName
	* @Description: TODO 根据账号查询积分表数据
	* @param @param accountName
	* @param @return    
	* @return WdAccountScoreBean    
	* @throws
	 */
	public WdAccountScoreBean getWdAccountScoreBeanByAccountName(String accountName){
		logger.debug("accountName:"+accountName);
		return wdAccountScoreDAO.selectWdAccountScoreBeanByAccountName(accountName);
		
	}
	
}
