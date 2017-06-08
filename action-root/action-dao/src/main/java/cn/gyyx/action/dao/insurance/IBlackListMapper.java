/*------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：王雷
 * 联系方式：wanglei@gyyx.cn 
 * 创建时间：2015年07月14日 
 * 版本号：v1.0 
 * 本类主要用途描述： 虚拟保险项目——黑名单数据处理接口
 * 
-------------------------------------------------------------------------*/

package cn.gyyx.action.dao.insurance;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.insurance.BlackListBean;

public interface IBlackListMapper {
	
	/**
	 * 根据认证手机获取黑名单信息
	 * @param account
	 * @return
	 */
	public List<BlackListBean> getBlackListBeanByAccountOrPhone( @Param("phone") String phone,@Param("account") String account);
	/**
	 * 向黑名单中插入数据
	 * @param blackBean
	 */
	public void insertBlackBean(BlackListBean blackBean);
	/**
	 * 根据获取黑名单信息——分页
	 * @param account
	 * @return
	 */
	public List<BlackListBean> getBlackLisForPage( Integer num, Integer pageNum);
	/**
	 * 根据获取总条数
	 * @param account
	 * @return
	 */
	public Integer selectPageCount( );

}
