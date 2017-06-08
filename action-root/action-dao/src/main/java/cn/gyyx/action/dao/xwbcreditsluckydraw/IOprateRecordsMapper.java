/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：xuanwuba
 * @作者：yangyusheng
 * @联系方式：yangyusheng@gyyx.cn
 * @创建时间： 2015年7月14日 下午12:03:52
 * @版本号：
 * @本类主要用途描述：操作记录接口
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.xwbcreditsluckydraw;

import java.util.List;

import cn.gyyx.action.beans.xwbcreditsluckydraw.OprateRecordsBean;

public interface IOprateRecordsMapper {

	/**
	 * 
	 * @日期：2015年7月14日
	 * @Title: getRecords
	 * @Description: TODO 根据条件获得部分操作记录
	 * @param oprate
	 * @return List<OprateRecordsBean>
	 */
	public List<OprateRecordsBean> getRecords(OprateRecordsBean oprate);

	/**
	 * 
	 * @日期：2015年7月14日
	 * @Title: addRecord
	 * @Description: TODO 添加操作记录
	 * @param oprate
	 * @return int
	 */
	public int addRecord(OprateRecordsBean oprate);

	/**
	 * 
	 * @日期：2015年7月14日
	 * @Title: getRecordsTotal 
	 * @Description: TODO 获得操作记录条数
	 * @param record
	 * @return int
	 */
	public int getRecordsTotal(OprateRecordsBean record);
	
	/**
	 * 添加重置标识
	 * @param materialCode
	 */
	public void setResetFlag(Integer materialCode);
}
