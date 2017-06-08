/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action
 * @作者：yangyusheng
 * @联系方式：yangyusheng@gyyx.cn
 * @创建时间： 2014年12月25日 上午11:02:13
 * @版本号：
 * @本类主要用途描述：连接二维码xml的接口mapper
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.novicecard;

import cn.gyyx.action.beans.novicecard.ErWeiMaBean;

public interface IErWeiMaMapper {

	/**
	 * 
	 * @日期：2014年12月25日
	 * @Title: selectErWeiMaValid 
	 * @Description: TODO 根据卡号、批次号、游戏ID查询二维码新手卡信息
	 * @param erWeiMa
	 * @return ErWeiMaBean
	 */
	
	public ErWeiMaBean selectErWeiMaValid(ErWeiMaBean erWeiMa);
}
