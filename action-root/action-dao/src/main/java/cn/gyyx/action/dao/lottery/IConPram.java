/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年3月20日下午1:17:58
 * 版本号：v1.0
 * 本类主要用途叙述：得到活动的参数
 */



package cn.gyyx.action.dao.lottery;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.lottery.ContrParmBean;

public interface IConPram {
	/**
	 * 得到活动的信息
	 * @param actionCode
	 * @return List<ContrParm> 
	 */
	public ContrParmBean getConPram (@Param("actionCode")int actionCode);

}
