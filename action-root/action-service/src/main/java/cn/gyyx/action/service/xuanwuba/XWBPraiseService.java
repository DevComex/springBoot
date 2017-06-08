/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：xuanwuba-collect
 * @作者：范永良
 * @联系方式：fanyongliang@gyyx.cn
 * @创建时间： 2015年10月14日
 * @版本号：v1.228
 * @本类主要用途描述：业务拼装
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.service.xuanwuba;

import java.util.Date;

import org.slf4j.Logger;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.PraiseBean;
import cn.gyyx.action.bll.xwbcreditsluckydraw.MaterialAuditBll;
import cn.gyyx.action.bll.xwbcreditsluckydraw.PraiseBLL;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class XWBPraiseService {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(XWBPraiseService.class);

	private PraiseBLL praiseBLL = new PraiseBLL();
	private MaterialAuditBll materialAuditBll = new MaterialAuditBll();
	/**
	 * 点赞
	 * @param account
	 * @param missionType
	 * @return
	 */
	public ResultBean<String> givePraise(String account,Integer materialCode){
		logger.info("XWBMissionService-givePraise-参数:account{"+account+"}materialCode{"+materialCode+"}");
		ResultBean<String> result = new ResultBean<String>();
		//检查xuanxuba_praise_tb本人本素材是否已经点赞
		PraiseBean checkBean = new PraiseBean(account,materialCode);
		PraiseBean praise = praiseBLL.getPraise(checkBean);
		if(praise == null){
			PraiseBean addBean = new PraiseBean(account, materialCode, new Date(), false);
			logger.info(materialCode+"点赞：1.1如果点赞记录为空，加一天新的点赞记录");
			praiseBLL.addPraise(addBean);
			//点赞
			logger.info(materialCode+"点赞：1，2如果点赞记录为空，素材审核表，点赞数加一");
			materialAuditBll.updatePraiseInfo(1, materialCode);
			result.setProperties(true, "点赞成功", "新增点赞记录！");
		}else{
			if(praise.getIsDelete()){
				PraiseBean updateBean = new PraiseBean(account,materialCode);
				updateBean.setIsDelete(false);
				logger.info(materialCode+"点赞：2.1.1如果点赞记录不为空，而且已删除，将删除标志恢复");
				praiseBLL.updatePraiseStatus(updateBean);
				//点赞
				logger.info(materialCode+"点赞：2.1.2如果点赞记录不为空素材审核表，而且已删除，点赞数+1");
				materialAuditBll.updatePraiseInfo(1, materialCode);
				result.setProperties(true, "点赞成功", "已有点赞记录，修改状态，点赞数加1！");
			}else{
				PraiseBean updateBean = new PraiseBean(account,materialCode);
				updateBean.setIsDelete(true);
				logger.info(materialCode+"点赞：2.2.1如果点赞记录不为空，而且没有删除，将删除标志恢复");
				praiseBLL.updatePraiseStatus(updateBean);
				//点赞
				logger.info(materialCode+"点赞：2.2.1如果点赞记录不为空，而且没有删除，修改状态，点赞数-1");
				materialAuditBll.updatePraiseInfo(-1, materialCode);
				result.setProperties(true, "取消赞成功", "已有点赞记录，修改状态，点赞数减1！");
			}
		}
		logger.info("XWBMissionService-givePraise-result"+result.toString());
		return result;
	}
}
