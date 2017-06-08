/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action
 * @作者：yangyusheng
 * @联系方式：yangyusheng@gyyx.cn
 * @创建时间： 2014年12月9日 上午9:59:52
 * @版本号：
 * @本类主要用途描述：新手卡bll层
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.bll.novicecard;

import java.util.Date;

import org.slf4j.Logger;

import cn.gyyx.action.beans.novicecard.NoviceCardBean;
import cn.gyyx.action.beans.novicecard.ResultBean;
import cn.gyyx.action.dao.novicecard.NoviceCardDAO;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.core.Text;

public class NoviceCardBLL {

	private NoviceCardDAO dao = new NoviceCardDAO();
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(NoviceCardBLL.class);

	/**
	 * 
	 * @日期：2014年12月15日
	 * @Title: CheckCardNoInfoIsValid
	 * @Description: TODO 检查新手卡有效性
	 * @param cardNo
	 * @param gameId
	 * @param batchNo
	 * @return ResultBean<String>
	 */

	public ResultBean<NoviceCardBean> CheckCardNoInfoIsValid(String cardNo,
			Integer gameId, Integer batchNo) {
		logger.debug("batchNo", batchNo);
		logger.debug("gameId:", gameId);
		logger.debug("cardNo", cardNo);
		ResultBean<NoviceCardBean> result = new ResultBean<NoviceCardBean>(
				false, "检查新手卡未知错误", null);
		try {
			if (Text.isNullOrEmpty(cardNo)) {
				result.setMessage("卡号为空");
				logger.debug("result", result);
				return result;
			}
			if (batchNo == null || batchNo <= 0) {
				result.setMessage("无效活动");
				logger.debug("result", result);
				return result;
			}
			if (gameId == null || gameId <= 0) {
				result.setMessage("该游戏不参加此次活动");
				logger.debug("result", result);
				return result;
			}
			NoviceCardBean noviceCard = dao.selectNoviceCardByCardNo(cardNo);
			if (noviceCard == null) {
				result.setMessage("新手卡不存在或在本批次内无效");
				logger.debug("result", result);
				return result;
			}
			if (gameId != noviceCard.getGameId()) {
				result.setMessage("新手卡不为此游戏的");
				logger.debug("result", result);
				return result;
			}
			if (batchNo != noviceCard.getBatchNo()) {
				result.setMessage("新手卡不为此批次的");
				logger.debug("result", result);
				return result;
			}

			if (noviceCard.isLimit()) {
				Date date = new Date();
				if (date.compareTo(noviceCard.getEndTime()) > 0
						|| date.compareTo(noviceCard.getStartTime()) < 0) {
					result.setMessage("新手卡过期");
					logger.debug("result", result);
					return result;
				}
			}
			if (noviceCard.getIsUse() == 1) {
				result.setMessage("该新手卡已经使用过，请检查您的卡号！");
				logger.debug("result", result);
				return result;
			}
			if (noviceCard.getItemId() == 0 || noviceCard.getItemId() == null) {
				result.setMessage("物品编号不存在");
				logger.debug("result", result);
				return result;
			}
			result.setProperties(true, "", noviceCard);
		} catch (Exception e) {
			logger.warn(e.toString());
			result.setProperties(false, "错误信息", null);
		}
		logger.debug("result", result);
		return result;
	}

}
