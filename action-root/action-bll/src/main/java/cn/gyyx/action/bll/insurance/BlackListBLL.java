/*------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：王雷
 * 联系方式：wanglei@gyyx.cn 
 * 创建时间：2015年07月14日 
 * 版本号：v1.0 
 * 本类主要用途描述： 虚拟保险项目——黑名单业务处理类
 * 
-------------------------------------------------------------------------*/

package cn.gyyx.action.bll.insurance;

import java.util.List;

import org.slf4j.Logger;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.insurance.BlackListBean;
import cn.gyyx.action.dao.insurance.BlackListDAO;
import cn.gyyx.action.dao.insurance.IBlackListMapper;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class BlackListBLL {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(BlackListBLL.class);
	private IBlackListMapper iBlackListMapper = new BlackListDAO();
	private BlackListDAO blackListDAO = new BlackListDAO();
	int num = 10;
	/**
	 * 根据总条数得出分页的页数
	 * @param account
	 * @return
	 */
	public Integer getPage(){
		Integer count = blackListDAO.selectPageCount();
		return count%num==0 ? count/num : count/num+1;
	}
	/**
	 * 根据获取黑名单信息——分页
	 * @param account
	 * @return
	 */
	public List<BlackListBean> getBlackLisForPage( Integer pageNum){
		return iBlackListMapper.getBlackLisForPage(num, pageNum);
	}

	/**
	 * 根据认证手机获取黑名单信息
	 * 
	 * @param account
	 * @return
	 */
	public List<BlackListBean>  getBlackListBeanByAccountOrPhone(String phone,String account) {

		return iBlackListMapper.getBlackListBeanByAccountOrPhone(phone,account);
	}
	/**
	 * 向黑名单中插入数据
	 * @param blackBean
	 */
	public void addBlackBean(BlackListBean blackBean){
		ResultBean<String> result = new ResultBean<String>(false, "插入黑名单未知错误", "");
		try{
			blackListDAO.insertBlackBean(blackBean);
			result.setProperties(true, "插入黑名单获取成功", "");
		}catch(Exception e){
			logger.debug("插入黑名单获取失败");
			result.setProperties(false, "插入黑名单获取失败", "");
		}
	}

}
