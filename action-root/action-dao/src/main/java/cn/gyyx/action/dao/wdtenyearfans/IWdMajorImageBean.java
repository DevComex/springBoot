/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：问道十周年粉丝榜
 * @作者：chenpeilan
 * @联系方式：chenpeilan@gyyx.cn
 * @创建时间： 2016年3月31日
 * @版本号：
 * @本类主要用途描述：问道十周年粉丝榜活动职业获取图片相关接口
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.wdtenyearfans;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.wdtenyearfans.WdMajorImageBean;

public interface IWdMajorImageBean {
	public WdMajorImageBean selectImageByMajor(@Param("type")int type, @Param("major")int major, @Param("sex")int sex);
}