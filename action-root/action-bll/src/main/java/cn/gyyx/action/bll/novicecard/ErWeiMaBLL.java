/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action
 * @作者：yangyusheng
 * @联系方式：yangyusheng@gyyx.cn
 * @创建时间： 2014年12月25日 下午1:40:52
 * @版本号：
 * @本类主要用途描述：二维码BLL层
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.bll.novicecard;

import java.util.Date;

import org.slf4j.Logger;

import cn.gyyx.action.beans.novicecard.ErWeiMaBean;
import cn.gyyx.action.beans.novicecard.ResultBean;
import cn.gyyx.action.dao.novicecard.ErWeiMaDAO;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.core.Text;

public class ErWeiMaBLL {

	private ErWeiMaDAO erWeiMaDao = new ErWeiMaDAO();
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(ErWeiMaBLL.class);

	/**
	 * 
	 * @日期：2014年12月25日
	 * @Title: CheckErWeiCardNoInfoIsValid
	 * @Description: TODO 检查二维新手卡在此批次是否存在及有效性
	 * @param erWeiMa
	 * @return ResultBean<ErWeiMaBean>
	 */

	public ResultBean<ErWeiMaBean> CheckErWeiCardNoInfoIsValid(
			ErWeiMaBean erWeiMaBean) {
		logger.debug("erWeiMaBean", erWeiMaBean);
		ResultBean<ErWeiMaBean> result = new ResultBean<ErWeiMaBean>(false,
				"检查二维码未知错误", null);
		ErWeiMaBean erWeiMa = new ErWeiMaBean();
		// 判断卡号是否为空
		if (Text.isNullOrEmpty(erWeiMaBean.getCardNo())) {
			result.setMessage("卡号为空");
			logger.debug("result", result);
			return result;
		}
		// 判断批次号是否有效
		if (erWeiMaBean.getBatchNo() < 0) {
			result.setMessage("无效活动");
			logger.debug("result", result);
			return result;
		}
		try {
			erWeiMa = erWeiMaDao.selectErWeiMaValid(erWeiMaBean);
			// 判断数据库中得到的二维码新手卡是否为空
			if (erWeiMa == null) {
				result.setMessage("二维码不存在或者在本批次内无效");
				logger.debug("result", result);
				return result;
			}
			// 判断二维码新年卡是否有时间限制
			if (erWeiMa.isLimit()) {
				Date date = new Date();
				if (date.compareTo(erWeiMa.getEndTime()) > 0
						|| date.compareTo(erWeiMa.getStartTime()) < 0) {
					result.setMessage("二维码过期");
					logger.debug("result", result);
					return result;
				}
			}
			// 判断得到的物品编号是否有效
			if (erWeiMa.getItemId() == 0 || erWeiMa.getItemId() == null) {
				result.setMessage("物品编号不存在");
				logger.debug("result", result);
				return result;
			}
			result.setProperties(true, "", erWeiMa);
		} catch (Exception e) {
			result.setProperties(false, "", null);
			logger.warn(e.toString());
		}
		logger.debug("result", result);
		return result;
	}
}
