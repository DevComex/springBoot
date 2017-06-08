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
package cn.gyyx.action.bll.lottery;

import org.slf4j.Logger;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.AddressBean;
import cn.gyyx.action.dao.lottery.AddressDAO;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class AddressBLL {

	private AddressDAO addressDao = new AddressDAO();
	private static final Logger logger = GYYXLoggerFactory.getLogger(AddressBLL.class);

	/**
	 * 
	 * @日期：2015年3月19日 @Title: getAddress
	 * @Description: TODO 根据用户code查询用户地址信息
	 * @param userCode
	 * @return ResultBean<AddressBean>
	 */
	public ResultBean<AddressBean> getAddress(int userCode, int actionCode) {
		logger.debug("userCode", userCode);
		AddressBean address = addressDao.getAddress(userCode, actionCode);
		ResultBean<AddressBean> result = new ResultBean<>(false, "查询失败", null);
		if (address != null) {
			result.setProperties(true, "查询成功", address);
		}
		logger.debug("result", result);
		return result;
	}

	public AddressBean selectAddressByUser(String userAccount, int actionCode) {
		AddressBean address = addressDao.selectAddressByUser(userAccount, actionCode);
		return address;
	}

	/**
	 * 
	 * @日期：2015年3月19日 @Title: addAddress
	 * @Description: TODO 新增用户地址信息
	 * @param address
	 * @return ResultBean<Integer>
	 */
	public ResultBean<Integer> addAddress(AddressBean address) {
		logger.debug("address", address);
		ResultBean<Integer> result = new ResultBean<Integer>(true, "添加失败", 0);
		if (addressDao.addAddress(address) == 1) {
			result.setProperties(true, "添加成功", 1);
		}
		logger.debug("result", result);
		return result;
	}

	/**
	 * 
	 * @日期：2015年3月19日 @Title: setAddress
	 * @Description: TODO 修改用户地址信息
	 * @param address
	 * @return ResultBean<Integer>
	 */
	public ResultBean<Integer> setAddress(AddressBean address) {
		logger.debug("address", address);
		ResultBean<Integer> result = new ResultBean<Integer>(true, "修改失败", 0);
		addressDao.setAddress(address);
		result.setProperties(true, "修改成功", 1);
		logger.debug("result", result);
		return result;
	}
	
	public ResultBean<String> editAddress(int userId, int actionCode, AddressBean addressBean) {
		ResultBean<String> result = new ResultBean<String>();
		// 先获取地址
		AddressBean address = addressDao.getAddress(userId, actionCode);
		if (address != null) {
			addressBean.setCode(address.getCode());
			addressDao.setAddress(addressBean);
			result.setMessage("更新地址成功");
			result.setData("更新地址成功");
			result.setIsSuccess(true);
		} else {
			if (addressDao.addAddress(addressBean) == 1) {
				result.setMessage("新建地址成功");
				result.setData("新建地址成功");
				result.setIsSuccess(true);
			} else {
				result.setMessage("新建地址失败");
				result.setData("新建地址失败");
				result.setIsSuccess(false);
			}
		}
		return result;
	}
}
