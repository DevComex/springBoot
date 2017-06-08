/*------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：姜晗
 * 联系方式：jianghan@gyyx.cn 
 * 创建时间：2014年12月13日 下午7:24:54 
 * 版本号：v1.0 
 * 本类主要用途描述： 
 * 
-------------------------------------------------------------------------*/

package cn.gyyx.action.bll.novicecard;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import cn.gyyx.action.beans.novicecard.NoviceCardReceiveLogBean;
import cn.gyyx.action.beans.novicecard.NoviceOaBean;
import cn.gyyx.action.beans.novicecard.NoviceParameter;
import cn.gyyx.action.beans.novicecard.ResultBean;
import cn.gyyx.action.dao.novicecard.ReceiveLogDAO;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class ReceiveLogBll {

	private ReceiveLogDAO receiveLogDAO = new ReceiveLogDAO();
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(ReceiveLogBll.class);

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
	public ResultBean<String> setReceiveLog(NoviceParameter noviceParameter,
			String virtualItem, int typeId) {
		logger.debug("noviceParameter", noviceParameter);
		logger.debug("virtualItem", virtualItem);
		logger.debug("typeId", typeId);
		ResultBean<String> result = new ResultBean<String>(false, "活动领取新手卡失败", null);
		int errorCode = 0;
		NoviceCardReceiveLogBean noviceCardReceiveLogBean = new NoviceCardReceiveLogBean(
				noviceParameter.getUserInfo().getUserId(),
				noviceParameter.getAccount(), noviceParameter.getCard(),
				noviceParameter.getGameId(), noviceParameter.getServerId(),
				virtualItem, typeId, noviceParameter.getCellPhone(),
				noviceParameter.getCheckType());
		if (noviceParameter.getMark().value() == 2) {
			errorCode = receiveLogDAO.setReceiveLogV3(noviceCardReceiveLogBean);
		} else {
			errorCode = receiveLogDAO.setReceiveLog(noviceCardReceiveLogBean);
		}
		try {
			// 返回参数，新手卡取出时错误
			if (errorCode < 0) {
				switch (errorCode) {
				case -1:
					result.setMessage("新手卡号不存在");
					break;

				case -2:
					result.setMessage("新手卡号已被使用或使用次数已达上限");
					break;

				case -3:
					result.setMessage("您已领取过礼包，无法再次领取");
					break;

				case -5:
					result.setMessage("您在当前服务器已经领取过此类新手卡礼包, 无法再次领取");
					break;

				default:
					result.setMessage("添加物品发送日志失败！");
					break;
				}
			} else {
				result.setData(Integer.toString(errorCode));
				result.setMessage("活动领取新手卡成功");
				result.setIsSuccess(true);
			}
		} catch (Exception ex) {
			result.setData(null);
			result.setMessage("错误编号 ");
			logger.warn(ex.toString());
		}
		return result;
	}
	
	public List<NoviceOaBean> getReceiveLogByAccountAndTimeAndLikeGiftPageName(
			String account, String timeStr, String hdType) {
		return receiveLogDAO.getReceiveLogByAccountAndTimeAndLikeGiftPageName(account,timeStr,hdType);
	}

	public List<NoviceOaBean> getSendToGameLogByAccountAndTimeAndLikeGiftPageName(
			String account, String timeStr, String hdType) {
		return receiveLogDAO.getSendToGameLogByAccountAndTimeAndLikeGiftPageName(account,timeStr,hdType);
	}

	public List<NoviceOaBean> getNoviceServerList(String batchNo, String activityId) {
		return receiveLogDAO.getNoviceServerList(batchNo,activityId);
	}

	public List<Map<String, String>> getNoviceReceiveCountByMonthAndLikeGiftPackage(String month,String hdType,int serverId) {
		return receiveLogDAO.getNoviceReceiveCountByMonthAndLikeGiftPackage(month,hdType,serverId);
	}

	public void deleteActionServerConfig(int code) {
		receiveLogDAO.deleteActionServerConfig(code);
	}

	public void deleteNoviceCardServer(int code) {
		receiveLogDAO.deleteNoviceCardServer(code);
	}

	public int getNoviceCardServerCountByCodeAndBatchNo(int serverId,int batchNo) {
		return receiveLogDAO.getNoviceCardServerCountByCodeAndBatchNo(serverId,batchNo);
	}

	public void insertNoviceCardServer(NoviceOaBean bean) {
		receiveLogDAO.insertNoviceCardServer(bean);
	}

}
