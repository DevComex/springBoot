/*************************************************
       Copyright ©, 2015, GY Game
       Author: 王雷
       Created: 2015年-12月-21日
       Note: 微信端地址信息表连接数据库
************************************************/

package cn.gyyx.action.dao.wechat;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.wechat.WeiXinAddressBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class WeiXinAddressDAO  {

	private static final Logger logger = GYYXLoggerFactory
			.getLogger(WeiXinAddressDAO.class);

	/**
	 * 
	 * @日期：2015年3月19日
	 * @Title: getSession
	 * @Description: TODO 获取session
	 * @return SqlSession
	 */
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}

	/**
	 * 根据用户openId查询用户地址信息
	 * @param openId
	 * @param actionCode
	 * @return
	 */
	public WeiXinAddressBean getAddress(String openId,int actionCode) {
		WeiXinAddressBean address = null;
		SqlSession session = getSession();
		try {
			IWeiXinAddressMapper addressMapper = session
					.getMapper(IWeiXinAddressMapper.class);
			address = addressMapper.selectAddress(openId,actionCode);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return address;
	}
	public WeiXinAddressBean selectAddressByUser(String userAccount,int actionCode){
		WeiXinAddressBean address = null;
		SqlSession session = getSession();
		try {
			IWeiXinAddressMapper addressMapper = session
					.getMapper(IWeiXinAddressMapper.class);
			address = addressMapper.selectAddressByUser(userAccount, actionCode);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return address;
	}

	/**
	 * 新增用户地址信息
	 * @param address
	 * @return
	 */
	public int addAddress(WeiXinAddressBean address) {
		int result = 0;
		SqlSession session = getSession();
		try {
			IWeiXinAddressMapper addressMapper = session
					.getMapper(IWeiXinAddressMapper.class);
			result = addressMapper.insertAddress(address);
			session.commit();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return result;
	}

	/**
	 * 修改用户地址信息
	 * @param address
	 */
	public void setAddress(WeiXinAddressBean address) {

		SqlSession session = getSession();
		try {
			IWeiXinAddressMapper addressMapper = session
					.getMapper(IWeiXinAddressMapper.class);
			addressMapper.updateAddress(address);
			session.commit();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
	}
	
	/**
	 * 获取actionCode的所有地址信息
	 * @param actionCode
	 * @return
	 */
	public List<WeiXinAddressBean> getAllAddressInfoByActionCode(WeiXinAddressBean weiXinAddressBean){
		List<WeiXinAddressBean> addressList = null;
		SqlSession session = getSession();
		try {
			IWeiXinAddressMapper addressMapper = session
					.getMapper(IWeiXinAddressMapper.class);
			addressList = addressMapper.getAllAddressInfoByActionCode(weiXinAddressBean);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return addressList;
	}
}
