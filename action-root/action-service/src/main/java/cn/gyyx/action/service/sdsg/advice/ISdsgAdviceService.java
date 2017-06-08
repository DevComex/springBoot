package cn.gyyx.action.service.sdsg.advice;

import cn.gyyx.action.beans.sdsg.advice.SdsgAdviceBean;

public interface ISdsgAdviceService {

	
	boolean insertSdsgAdvice(SdsgAdviceBean sdsgAdviceBean);
	
	String getRegion();
}
