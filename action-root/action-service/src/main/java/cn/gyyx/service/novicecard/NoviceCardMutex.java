package cn.gyyx.service.novicecard;

import java.util.List;

import cn.gyyx.action.beans.novicecard.NovicCardConfig.MutexErrorType;
import cn.gyyx.action.beans.novicecard.NovicCardConfig.NoviceActionType;
import cn.gyyx.action.beans.novicecard.NoviceCardMutexBean;
import cn.gyyx.action.beans.novicecard.NoviceParameter;
import cn.gyyx.action.beans.novicecard.ResultBean;
import cn.gyyx.action.bll.novicecard.CheckAlreadyGetBLL;
import cn.gyyx.action.bll.novicecard.NoviceCardMutexBLL;
import cn.gyyx.action.bll.wd9yearnovicebag.CheckAlreadyBagGetBLL;
/**
 * 判断互斥静态方法
 * @author Administrator
 *
 */
public class NoviceCardMutex {
	/**
	 * 判断互斥
	 * @param noviceParameter
	 * @param noviceActionType 请求类型
	 * @return true标识通过互斥检查(没有获奖记录)  <br>
	 *         false没有通过互斥检查(有...)<br>
	 *         message为错误类型
	 */
	public static ResultBean<String> judgeMutex(NoviceParameter noviceParameter,NoviceActionType noviceActionType){
		NoviceCardMutexBLL noviceCardMutexBLL = new NoviceCardMutexBLL();
		ResultBean<String> result = new ResultBean<String>();
		switch(noviceActionType){
		case card:{
			result = checkMutex(noviceParameter,noviceCardMutexBLL,noviceParameter.getBatchNo());
			break;
		}
		case bag:{
			result = checkMutex(noviceParameter,noviceCardMutexBLL,noviceParameter.getActivityId());
			break;
		}
		}
		return result;
	}
	
	private static ResultBean<String> checkMutex(NoviceParameter noviceParameter,NoviceCardMutexBLL noviceCardMutexBLL,int actionSymbol){
		CheckAlreadyGetBLL checkCardAlreadyGetBLL = new CheckAlreadyGetBLL();
		CheckAlreadyBagGetBLL checkAlreadyBagGetBLL = new CheckAlreadyBagGetBLL();
		ResultBean<String> resultBean = new ResultBean<String>(true,"success","");
		try {
			List<NoviceCardMutexBean> result = noviceCardMutexBLL.getMutexMes(actionSymbol);
			if(result==null){
				resultBean.setProperties(false, "您可能已经在其他活动中领取了哟！！",MutexErrorType.paraError.name() );
			}else{
				for(int i=0;i<result.size();i++){
					NoviceCardMutexBean para = result.get(i);
					NoviceParameter newPara = noviceParameter.clone();
					ResultBean<String> resultCardGet = new ResultBean<String>(false,"","");
					if(para.getMutexCodeSymbol().equals(NoviceActionType.card.name())){
						newPara.setBatchNo(para.getMutexCode());
						resultCardGet = checkCardAlreadyGetBLL.checkGet(newPara);
					}else{
						newPara.setActivityId(para.getMutexCode());
						resultCardGet = checkAlreadyBagGetBLL.checkBagGet(newPara);
					}
					if (resultCardGet.getIsSuccess()) {
						resultBean.setProperties(false,resultCardGet.getMessage() ,  MutexErrorType.repeatReceive.name());
						return resultBean;
					}
				}
			}
		} catch (Exception e) {
			resultBean.setProperties(false, "您可能已经在其他活动中领取了哟！", MutexErrorType.cloneError.name());
		} 
		return resultBean;
	}
}
