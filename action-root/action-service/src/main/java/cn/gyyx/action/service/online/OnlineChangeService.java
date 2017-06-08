/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：online-change
 * @作者：fanyongliang
 * @联系方式：fanyongliang@gyyx.cn
 * @创建时间： 2015年9月28日
 * @版本号：
 * @本类主要用途描述：创世online光宇币兑换业务拼装层
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.service.online;

import org.slf4j.Logger;

import cn.gyyx.action.beans.online.OnlineChangeBean;
import cn.gyyx.action.beans.online.OnlineChangeLogBean;
import cn.gyyx.action.bll.online.OnlineChangeBll;
import cn.gyyx.action.bll.online.OnlineChangeLogBll;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class OnlineChangeService {

	private OnlineChangeBll onlineChangeBll = new OnlineChangeBll();	
	private OnlineChangeLogBll onlineChangeLogBll = new OnlineChangeLogBll();

	private static final Logger logger = GYYXLoggerFactory
			.getLogger(OnlineChangeService.class);
	
}
