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

import org.slf4j.Logger;

import cn.gyyx.action.beans.novicecard.NoviceCardReceivGiftLog;
import cn.gyyx.action.dao.novicecard.NoviceCardReceivGiftLogNewDAO;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class NoviceCardReceivGiftLogNewBLL {

	private NoviceCardReceivGiftLogNewDAO dao = new NoviceCardReceivGiftLogNewDAO();
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(NoviceCardReceivGiftLogNewBLL.class);


	public int insert(NoviceCardReceivGiftLog log) {
		return dao.insert(log);
	}

}
