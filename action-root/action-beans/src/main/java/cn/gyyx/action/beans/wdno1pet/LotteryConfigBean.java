/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2014年12月17日上午11:15:05
 * 版本号：v1.0
 * 本类主要用途叙述：中奖配置的实体
 */

package cn.gyyx.action.beans.wdno1pet;

public class LotteryConfigBean {
	/**
	 * 中奖配置信息的主键
	 */
	private int LotterCode;
	/**
	 * 中奖的名次
	 */
	private int LotterOrder;
	/**
	 * 中奖类别
	 */
	private String LotterType;

	/**
	 * 得到中奖信息的主键
	 * 
	 * @return 中奖信息的主键lotterCode
	 */
	public int getLotterCode() {
		return LotterCode;
	}

	/**
	 * 设定中奖信息的主键
	 * 
	 * @param lotterCode
	 */
	public void setLotterCode(int lotterCode) {
		LotterCode = lotterCode;
	}

	/**
	 * 得到中奖信息的名次
	 * 
	 * @return 中奖信息的名次 lotterOrder
	 */
	public Integer getLotterOrder() {
		return LotterOrder;
	}

	/**
	 * 设定中奖信息的名次
	 * 
	 * @param lotterOrder
	 */
	public void setLotterOrder(Integer lotterOrder) {
		LotterOrder = lotterOrder;
	}

	/**
	 * 得到中奖信息的类别
	 * 
	 * @return 中奖信息的类别 lotterType
	 */
	public String getLotterType() {
		return LotterType;
	}

	/**
	 * 设定中奖信息的类别
	 * 
	 * @param lotterType
	 */
	public void setLotterType(String lotterType) {
		LotterType = lotterType;
	}

}
