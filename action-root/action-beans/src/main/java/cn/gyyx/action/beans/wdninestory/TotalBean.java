/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：wdninestory
 * @作者：yangyusheng
 * @联系方式：yangyusheng@gyyx.cn
 * @创建时间： 2015年3月11日 下午1:38:01
 * @版本号：
 * @本类主要用途描述：每个类型投票总数实体类
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.wdninestory;

public class TotalBean {

	private String type;
	private int totalNumber;

	public TotalBean() {

	}

	public TotalBean(String type, int totalNumber) {
		this.type = type;
		this.totalNumber = totalNumber;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}

}
