/*-------------------------------------------------------------------------
* 版权所有：北京光宇在线科技有限责任公司
* 作者：chenpeilan
* 联系方式：chenpeilan@gyyx.cn
* 创建时间： 2016年3月30日
* 版本号：
* 本类主要用途描述：问道十周年粉丝榜提名相关BLL
-------------------------------------------------------------------------*/
package cn.gyyx.action.bll.wdtenyearfans;

import java.util.List;

import org.slf4j.Logger;

import cn.gyyx.action.beans.wdtenyearfans.WdAccountInfoBean;
import cn.gyyx.action.beans.wdtenyearfans.WdMajorImageBean;
import cn.gyyx.action.beans.wdtenyearfans.WdNominationInfoBean;
import cn.gyyx.action.dao.wdtenyearfans.WdAccountInfoDAO;
import cn.gyyx.action.dao.wdtenyearfans.WdMajorImageDAO;
import cn.gyyx.action.dao.wdtenyearfans.WdNominationInfoDAO;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * @ClassName: nominatedBLL
 * @Description: TODO 问道十周年粉丝榜提名相关BLL
 * @author chenpeilan chenpeilan@gyyx.cn
 * @date 2016年3月30日 下午2:05:01
 *
 */
public class NominatedBLL {
	
	private WdAccountInfoDAO accountDAO = new WdAccountInfoDAO();
	private WdNominationInfoDAO nominationInfoDAO = new WdNominationInfoDAO();
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(NominatedBLL.class);
	private WdMajorImageDAO wdMajorImageDAO = new WdMajorImageDAO();
	
	/**
	 * 
	* @Title: addRole
	* @Description: TODO 绑定角色
	* @param @param wdAccountInfoBean    
	* @return void    
	* @throws
	 */
	public void addRole(WdAccountInfoBean wdAccountInfoBean){
		logger.debug("wdAccountInfoBean:"+wdAccountInfoBean);
		accountDAO.insertWdAccountInfoBean(wdAccountInfoBean);
	}
	
	/**
	 * 
	* @Title: getWdAccountInfoBeanByAccountName
	* @Description: TODO 查询是否绑定过角色
	* @param @param accountName
	* @param @return    
	* @return WdAccountInfoBean    
	* @throws
	 */
	public WdAccountInfoBean getWdAccountInfoBeanByAccountName(String accountName){
		logger.debug("accountName:"+accountName);
		return accountDAO.selectWdAccountInfoBeanByAccountName(accountName);
		
	}
	
	/**
	 * 
	* @Title: getWdNominationInfoBeanByAccountName
	* @Description: TODO 判断是否提名
	* @param @param accountName
	* @param @return    
	* @return WdNominationInfoBean    
	* @throws
	 */
	public WdNominationInfoBean getWdNominationInfoBeanByAccountName(String accountName){
		logger.debug("accountName:"+accountName);
		return nominationInfoDAO.selectWdNominationInfoBeanByAccountName(accountName);
	}
	
	/**
	 * 
	* @Title: insertWdNominationInfoBean
	* @Description: TODO 提名操作
	* @param @param wdNominationInfoBean    
	* @return void    
	* @throws
	 */
	public void addWdNominationInfoBean(WdNominationInfoBean wdNominationInfoBean){
		logger.debug("wdNominationInfoBean:"+wdNominationInfoBean);
		nominationInfoDAO.insertWdNominationInfoBean(wdNominationInfoBean);
	}
	
	/**
	 * 
	* @Title: getImageByMajor
	* @Description: TODO 通过提名类型，职业，性别获取图片信息
	* @param @param type
	* @param @param major
	* @param @param sex
	* @param @return    
	* @return WdMajorImageBean    
	* @throws
	 */
	public WdMajorImageBean getImageByMajor(int type,int major,int sex) {
		logger.info("type:"+type);
		logger.info("major:"+major);
		logger.info("sex:"+sex);
		return wdMajorImageDAO.selectImageByMajor(type, major, sex);
	}
	
	/**
	 * 
	* @Title: getPageNum
	* @Description: TODO 获取通过作品总条数
	* @param @return    
	* @return Integer    
	* @throws
	 */
	public Integer getPageNum() {
		return nominationInfoDAO.getPageNum();
	}
	
	/**
	 * 查询通过提名账号数量
	 * @param accountName
	 * @return
	 */
	public Integer getNominationAccountNum(String accountName) {
		return nominationInfoDAO.selectNominationAccountNum(accountName);
	}
	
	/**
	 * 查询提名账号数量
	 * @return
	 */
	public Integer getAllNominationAccountNum() {
		return nominationInfoDAO.selectAllNominationAccountNum();
	}
	
	/**
	 * 查询提名IP数量
	 * @return
	 */
	public Integer getNominationIpNum() {
		return nominationInfoDAO.selectNominationIpNum();
	}
	
	/**
	 * 根据账号查询提名相关信息
	 * @param accountName
	 * @return
	 */
	public WdNominationInfoBean getNominationInfo(String accountName){
		logger.debug("accountName:"+accountName);
		return nominationInfoDAO.selectNominationInfo(accountName);
	}
	
	/**
	 * 查询所有提名账号--分页
	 * @param pageSize
	 * @param pageNo
	 * @param accountName
	 * @return
	 */
	public List<String> getNominationAccount(int pageSize,int pageNo,String accountName) {
		return nominationInfoDAO.selectNominationAccount(pageSize, pageNo, accountName);
	}
	
	/**
	 * 查询所有提名账号
	 * @return
	 */
	public List<String> getAllNominationAccount() {
		return nominationInfoDAO.selectAllNominationAccount();
	}
	
	/**
	 * 根据账号查询区服和绑定角色
	 * @param accountName
	 * @return
	 */
	public WdAccountInfoBean getAccountInfoByAccountName(String accountName){
		logger.debug("accountName:"+accountName);
		return accountDAO.selectAccountInfoByAccountName(accountName);
		
	}

}
