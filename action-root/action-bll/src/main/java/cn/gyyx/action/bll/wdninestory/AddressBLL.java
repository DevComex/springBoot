/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：lottery
 * @作者：yangyusheng
 * @联系方式：yangyusheng@gyyx.cn
 * @创建时间： 2015年3月19日 下午5:01:19
 * @版本号：
 * @本类主要用途描述：地址信息逻辑类
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.bll.wdninestory;

import org.slf4j.Logger;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.wdninestory.AddressBean;
import cn.gyyx.action.dao.wdninestory.AddressDAO;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class AddressBLL {

	private AddressDAO addressDao = new AddressDAO();
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(AddressBLL.class);

	/**
	 * 
	 * @日期：2015年3月19日
	 * @Title: getAddress
	 * @Description: TODO 根据用户code查询用户地址信息
	 * @param userCode
	 * @return ResultBean<AddressBean>
	 */
	public ResultBean<AddressBean> getAddress(int userCode,int actionCode) {
		logger.debug("userCode", userCode);
		AddressBean address = addressDao.getAddress(userCode,actionCode);
		ResultBean<AddressBean> result = new ResultBean<AddressBean>(false,
				"查询失败", address);
		if (address != null) {
			result.setProperties(true, "查询成功", address);
		}
		logger.debug("result", result);
		return result;
	}

	/**
	 * 
	 * @日期：2015年3月19日
	 * @Title: addAddress
	 * @Description: TODO 新增用户地址信息
	 * @param address
	 * @return ResultBean<Integer>
	 */
	public ResultBean<Integer> addAddress(AddressBean address) {
		logger.debug("address", address);
		ResultBean<Integer> result = new ResultBean<Integer>(false, "添加失败", 0);
		if (addressDao.addAddress(address) == 1) {
			result.setProperties(true, "添加成功", 1);
		}
		logger.debug("result", result);
		return result;
	}

	/**
	 * 
	 * @日期：2015年3月19日
	 * @Title: setAddress
	 * @Description: TODO 修改用户地址信息
	 * @param address
	 * @return ResultBean<Integer>
	 */
	public ResultBean<Integer> setAddress(AddressBean address) {
		logger.debug("address", address);
		ResultBean<Integer> result = new ResultBean<Integer>(false, "修改失败", 0);
		if (addressDao.setAddress(address) >= 1) {
			result.setProperties(true, "修改成功", 1);
		}
		logger.debug("result", result);
		return result;
	}

}
