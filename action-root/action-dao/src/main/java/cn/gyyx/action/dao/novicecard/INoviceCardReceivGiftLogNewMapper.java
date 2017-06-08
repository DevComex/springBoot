/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action
 * @作者：王雷
 * @联系方式：wanglei@gyyx.cn
 * @创建时间：2014年12月10日 
 * @版本号：v1.0
 * @本类主要用途描述：对novice_card_receive_log表操作的接口类
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.novicecard;

import cn.gyyx.action.beans.novicecard.NoviceCardReceivGiftLog;

public interface INoviceCardReceivGiftLogNewMapper {

	public int insert(NoviceCardReceivGiftLog log);
}
