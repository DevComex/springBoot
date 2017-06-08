/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：lottery
 * @作者：yangyusheng
 * @联系方式：yangyusheng@gyyx.cn
 * @创建时间： 2015年3月19日 下午3:28:43
 * @版本号：
 * @本类主要用途描述：地址信息类接口
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.wechat;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.wechat.WeiXinAddressBean;


public interface IWeiXinAddressMapper {

	/**
	 * 
	 * @日期：2015年3月19日
	 * @Title: selectAddress
	 * @Description: TODO 通过用户code查询用户地址信息
	 * @param userCode
	 * @return AddressBean
	 */
	public WeiXinAddressBean selectAddress(@Param("openId") String openId,@Param("actionCode") int actionCode);
	public WeiXinAddressBean selectAddressByUser(@Param("userAccount") String userAccount,@Param("actionCode") int actionCode);

	/**
	 * 
	 * @日期：2015年3月19日
	 * @Title: insertAddress
	 * @Description: TODO 新增用户地址信息
	 * @param address
	 * @return int
	 */
	public int insertAddress(WeiXinAddressBean address);

	/**
	 * 
	 * @日期：2015年3月19日
	 * @Title: updateAddress
	 * @Description: TODO 修改用户地址信息
	 * @param address
	 * @return int
	 */
	public void updateAddress(WeiXinAddressBean address);
	/**
	 * 获取actionCode的所有地址信息
	 * @param actionCode
	 * @return
	 */
	public List<WeiXinAddressBean> getAllAddressInfoByActionCode(WeiXinAddressBean weiXinAddressBean);
}
