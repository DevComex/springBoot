/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：lottery
 * @作者：yangyusheng
 * @联系方式：yangyusheng@gyyx.cn
 * @创建时间： 2015年3月19日 下午3:54:07
 * @版本号：
 * @本类主要用途描述：地址信息表连接数据库
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.wdninestory;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.wdninestory.AddressBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class AddressDAO {

	private static final Logger logger = GYYXLoggerFactory
			.getLogger(AddressDAO.class);

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
	 * 
	 * @日期：2015年3月19日
	 * @Title: getAddress
	 * @Description: TODO 根据用户code查询用户地址信息
	 * @param userCode
	 * @return AddressBean
	 */
	public AddressBean getAddress(int userCode,int actionCode) {
		AddressBean address = null;
		SqlSession session = getSession();
		try {
			IAddressMapper addressMapper = session
					.getMapper(IAddressMapper.class);
			address = addressMapper.selectAddress( userCode,actionCode);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return address;
	}

	/**
	 * 
	 * @日期：2015年3月19日
	 * @Title: addAddress
	 * @Description: TODO 新增用户地址信息
	 * @param address
	 * @return int
	 */
	public int addAddress(AddressBean address) {
		int result = 0;
		SqlSession session = getSession();
		try {
			IAddressMapper addressMapper = session
					.getMapper(IAddressMapper.class);
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
	 * 
	 * @日期：2015年3月19日
	 * @Title: setAddress
	 * @Description: TODO 修改用户地址信息
	 * @param address
	 * @return int
	 */
	public int setAddress(AddressBean address) {
		int result = 0;
		SqlSession session = getSession();
		try {
			IAddressMapper addressMapper = session
					.getMapper(IAddressMapper.class);
			result = addressMapper.updateAddress(address);
			session.commit();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return result;
	}
}
