/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action
 * @作者：yangyusheng
 * @联系方式：yangyusheng@gyyx.cn
 * @创建时间： 2014年12月9日 上午9:31:47
 * @版本号：
 * @本类主要用途描述：读取新手卡信息Mapper
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.novicecard;

import cn.gyyx.action.beans.novicecard.NoviceCardBean;

public interface INoviceCardMapper {


	

	
	/**
	 * 通过卡号获取卡实体
	 * @param cardNo 卡号
	 * @return 返回卡实体
	 */
	public NoviceCardBean selectNoviceCardByCardNo(String cardNo);
	

}
