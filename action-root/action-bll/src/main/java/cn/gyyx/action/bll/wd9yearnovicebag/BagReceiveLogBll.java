/*------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：周忠凯 王雷
 * 联系方式：zhouzhongkai@gyyx.cn wanglei@gyyx.cn 
 * 创建时间：2015年03月25日 下午4:57:58 
 * 版本号：v1.0 
 * 本类主要用途描述： 
 * 
-------------------------------------------------------------------------*/

package cn.gyyx.action.bll.wd9yearnovicebag;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;

import cn.gyyx.action.beans.novicecard.NoviceParameter;
import cn.gyyx.action.beans.novicecard.ResultBean;
import cn.gyyx.action.beans.wd9yearnovicebag.HdSendPresentLogBean;
import cn.gyyx.action.dao.wd9yearnovicebag.HdSendPresentLogDAO;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class BagReceiveLogBll {

	private HdSendPresentLogDAO hdSendPresentLogDAO = new HdSendPresentLogDAO();
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(BagActivityConfigBll.class);
	/**
	 * 插入日志信息
	 * @param hdSendPresentLogBean
	 */
	public boolean setHdSendPresentLog(HdSendPresentLogBean hdSendPresentLogBean){
		logger.debug("noviceParameter:"+ hdSendPresentLogBean);
		return hdSendPresentLogDAO.insertHdSendPresentLog(hdSendPresentLogBean);
	}
	/**
	 * 通过userId、gameId、activityId查询数据
	 * @param selectPresentLog
	 * @return
	 */
	public List<HdSendPresentLogBean> selectPresentLog(HdSendPresentLogBean hdSendPresentLogBean){
		return hdSendPresentLogDAO.selectPresentLog(hdSendPresentLogBean);
	}
	public List<HdSendPresentLogBean> selectPresentLogByavailable(HdSendPresentLogBean hdSendPresentLogBean){
		return hdSendPresentLogDAO.selectPresentLogByavailable(hdSendPresentLogBean);
	}
	/**
	 * @Title: setReceiveLog
	 * @Author: jianghan
	 * @Description: TODO 插入新手卡日誌表，存储过程
	 * @param noviceParameter
	 * @param virtualItem
	 * @param typeId
	 * @return
	 * 
	 */
	public ResultBean<String> setReceiveLog(NoviceParameter noviceParameter) throws Exception {
		logger.debug("noviceParameter", noviceParameter);
		ResultBean<String> result = new ResultBean<String>(false, "领取礼包失败", null);
		Integer errorCode = 0;
		HdSendPresentLogBean hdSendPresentLogBean = new HdSendPresentLogBean(
				noviceParameter.getActivityId(), noviceParameter.getHdName(),
				0, noviceParameter.getAccount(), noviceParameter.getGameId(),
				noviceParameter.getServerId(), noviceParameter.getServerInfo()
						.getValue().getServerName(),
				noviceParameter.getVirtualItemType(),
				noviceParameter.getVirtualItemCode(), new Date(),
				noviceParameter.getUserInfo().getLoginIP());
		try {
			errorCode = hdSendPresentLogDAO.setReceiveLog(hdSendPresentLogBean);
			// 返回参数，新手卡取出时错误
			if (errorCode == 1) {
				result.setMessage("您在当前服务器领取礼包失败，请重试");
				return result;
			}
			if (errorCode == 2) {
				result.setMessage("您在当前服务器已领取礼包。");
			} else {
				result.setData(null);
				result.setMessage("领取成功");
				result.setIsSuccess(true);
			}
		} catch (Exception ex) {
			result.setData("插入日志错误");
			result.setMessage("错误编号 ");
			throw new Exception("发送新手礼包数据库异常",ex);
		}
		logger.info("发送新手礼包数据库,调用存储过程返回结果:{}", result);
		return result;
	}
	/**
	 * 向hd_send_present_log 表中插入日志
	 * @param noviceParameter
	 * @return
	 */
	public ResultBean<String> setHdSendReceiveLog(NoviceParameter noviceParameter) {
		logger.debug("noviceParameter", noviceParameter);
		ResultBean<String> result = new ResultBean<String>(false, "记录成功", null);
		Integer errorCode = 0;
		HdSendPresentLogBean hdSendPresentLogBean = new HdSendPresentLogBean(
				noviceParameter.getActivityId(), noviceParameter.getHdName(),
				0, noviceParameter.getAccount(), noviceParameter.getGameId(),
				noviceParameter.getServerId(), noviceParameter.getServerInfo()
						.getValue().getServerName(),
				noviceParameter.getVirtualItemType(),
				noviceParameter.getVirtualItemCode(), new Date(),
				noviceParameter.getUserInfo().getLoginIP());
		try {
			errorCode = hdSendPresentLogDAO.setReceiveLog(hdSendPresentLogBean);
			// 返回参数，新手卡取出时错误
			if (errorCode == 1) {
				result.setMessage("您在当前服务器领取礼包失败，请重试");
			}
			if (errorCode == 2) {
				result.setMessage("您在当前服务器已领取礼包。");
			} else {
				result.setData(null);
				result.setIsSuccess(true);
			}
		} catch (Exception ex) {
			result.setData("插入日志错误");
			result.setMessage("错误编号 ");
			logger.warn(ex.toString());
		}
		logger.debug("result", result);
		return result;
	}

}
