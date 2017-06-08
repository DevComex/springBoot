/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：xuanwuba
 * @作者：yangyusheng
 * @联系方式：yangyusheng@gyyx.cn
 * @创建时间： 2015年7月14日 下午2:32:56
 * @版本号：
 * @本类主要用途描述：操作记录接口类
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.bll.xwbcreditsluckydraw;

import java.text.SimpleDateFormat;
import java.util.List;

import cn.gyyx.action.beans.xwbcreditsluckydraw.OprateRecordsBean;
import cn.gyyx.action.dao.xwbcreditsluckydraw.OprateRecordsDAO;

public class OprateRecordsBLL {

	private OprateRecordsDAO recordDao = new OprateRecordsDAO();

	/**
	 * 
	 * @日期：2015年7月14日
	 * @Title: getRecords
	 * @Description: TODO 根据条件获得操作记录
	 * @param record
	 * @return List<OprateRecordsBean>
	 */
	public List<OprateRecordsBean> getRecords(OprateRecordsBean record) {
		List<OprateRecordsBean> recordsList = recordDao.getRecords(record);
		if (recordsList != null) {
			for (OprateRecordsBean creditBean : recordsList) {
				SimpleDateFormat format = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				creditBean.setOprateTimeStr(format.format(creditBean
						.getOprateTime()));
			}
		}
		return recordsList;
	}

	/**
	 * 
	 * @日期：2015年7月14日
	 * @Title: addRecord
	 * @Description: TODO 添加操作记录
	 * @param record
	 * @return int
	 */
	public int addRecord(OprateRecordsBean record) {
		return recordDao.addRecord(record);
	}

	/**
	 * 
	 * @日期：2015年7月14日
	 * @Title: getRecordsTotal
	 * @Description: TODO 获得操作记录条数
	 * @param record
	 * @return int
	 */
	public int getRecordsTotal(OprateRecordsBean record) {
		return recordDao.getRecordsTotal(record);
	}
	
	/**
	 * 添加重置标识
	 * @param materialCode
	 */
	public void setResetFlag(Integer materialCode){
		recordDao.setResetFlag(materialCode);
	}
}
