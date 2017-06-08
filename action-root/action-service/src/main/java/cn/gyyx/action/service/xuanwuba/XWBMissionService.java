/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：xuanwuba-collect
 * @作者：范永良
 * @联系方式：fanyongliang@gyyx.cn
 * @创建时间： 2015年10月14日
 * @版本号：v1.228
 * @本类主要用途描述：任务业务拼装
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.service.xuanwuba;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.CreditsBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.MissionBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.MissionReceiveBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.SumCreditBean;
import cn.gyyx.action.bll.xwbcreditsluckydraw.CreditBLL;
import cn.gyyx.action.bll.xwbcreditsluckydraw.MissionBLL;
import cn.gyyx.action.bll.xwbcreditsluckydraw.MissionReceiveBLL;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class XWBMissionService {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(XWBMissionService.class);
	 
	private MissionBLL missionBLL = new MissionBLL();
	private MissionReceiveBLL missionReceiveBLL = new MissionReceiveBLL();
	private CreditBLL creditBLL = new CreditBLL();
	
	/**
	 * 
	 * @日期：2015年10月14日
	 * @Title: checkMissionReceiveAndUpdateMissionStatus
	 * @Description: TODO 征集提交时检查有没有对应上传素材的任务，如果有上传后修改任务状态
	 * @param account , missionType
	 * @return ResultBean<String>
	 */
	public ResultBean<String> checkMissionReceiveInfoAndUpdateMissionStatus(String account,String missionType){
		logger.info("XWBMissionService-checkMissionReceiveAndUpdateMissionStatus-参数:account{"+account+"}missionType{"+missionType+"}");
		ResultBean<String> result = new ResultBean<String>();
		List<MissionReceiveBean> receiveBeanList = missionReceiveBLL.getMissionReceiveByAccountAndType(account, missionType);
		if(receiveBeanList!=null){
			for(MissionReceiveBean missionReceiveBean : receiveBeanList){
				MissionBean missionBean = missionBLL.getMissionInfoByCode(missionReceiveBean.getMissionCode());
			
					if(missionReceiveBean.getCount()+1>=missionBean.getUploadNum()){
						missionReceiveBLL.updateCompleteStatus(missionReceiveBean.getCode(), "已完成",new Date());
						missionReceiveBLL.updateCount(missionReceiveBean.getCode(), missionReceiveBean.getCount()+1);
						CreditsBean credit = new CreditsBean();
						credit.setAccount(account);
						credit.setCredits(missionBean.getMissionCredits());
						credit.setEnterTime(new Date());
						credit.setType(missionType);
						creditBLL.addCredits(credit);
						SumCreditBean sumCreditBean = creditBLL.getCreditByAccount(account);
						if(sumCreditBean!=null){
							int count = sumCreditBean.getSumCredit() + missionBean.getMissionCredits();
							sumCreditBean.setSumCredit(count);
							creditBLL.setSumCredit(sumCreditBean);
							logger.info("炫舞吧XWBMissionService-上传素材任务增加积分-:account{}"+account+"，增加积分{}"+missionBean.getMissionCredits());
						}else{
							SumCreditBean sumCredit = new SumCreditBean();
							sumCredit.setAccount(account);
							sumCredit.setSumCredit(missionBean.getMissionCredits());
							creditBLL.addSumCredit(sumCredit);
							logger.info("炫舞吧XWBMissionService-上传素材任务增加积分-:account{}"+account+"，增加积分{}"+missionBean.getMissionCredits());
						}
					}else{
						missionReceiveBLL.updateCount(missionReceiveBean.getCode(), missionReceiveBean.getCount()+1);
					}
				
				
			}
		}
		
//		if(receiveBean!=null){
//			missionReceiveBLL.updateCompleteStatus(receiveBean.getCode(), "审核中");
//			result.setProperties(true, "", "未完成-->审核中");
//		}else{
//			result.setProperties(false, "没有领取对应类型的任务!", "已领取任务完成状态修改失败!");
//		}
		logger.info("XWBMissionService-checkMissionReceiveAndUpdateMissionStatus-result"+result.toString());
		return result;
	}
}
