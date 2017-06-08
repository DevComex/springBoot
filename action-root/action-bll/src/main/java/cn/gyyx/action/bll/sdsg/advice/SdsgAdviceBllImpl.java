package cn.gyyx.action.bll.sdsg.advice;

import cn.gyyx.action.beans.sdsg.advice.SdsgAdviceBean;
import cn.gyyx.action.bll.sdsg.advice.ISdsgAdviceBll;
import cn.gyyx.action.dao.sdsg.advice.SdsgAdviceMapperImpl;

public class SdsgAdviceBllImpl implements ISdsgAdviceBll {

	SdsgAdviceMapperImpl sdsgAdviceMapper = new SdsgAdviceMapperImpl();

	@Override
	public int insertSdsgAdvice(SdsgAdviceBean sdsgAdviceBean) {
		return sdsgAdviceMapper.insertAdviceLog(sdsgAdviceBean);
	}

}
