/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：xuanwuba
 * @作者：yangyusheng
 * @联系方式：yangyusheng@gyyx.cn
 * @创建时间： 2015年7月7日 下午3:34:13
 * @版本号：
 * @本类主要用途描述：操作记录实体类
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.xwbcreditsluckydraw;

import java.util.Date;

public class OprateRecordsBean {

	// 主键编号
	private int code;
	// 操作人员
	private String oprator;
	// 操作时间
	private Date oprateTime;
	// 对应玩家账号
	private String account;
	// 追加的积分
	private int creditGived;
	// 分页页数
	private int page;
	// 获取时间字符串
	private String oprateTimeStr;
	// 查询时间所用字符串
	private String time;
	// 审核的作品号
	private Integer materialCode;
	
	//撤销标识
	private int resetFlag;
	

	public OprateRecordsBean() {

	}

	public OprateRecordsBean(String oprator, Date oprateTime, String account,
			int creditGived, Integer materialCode,int resetFlag) {
		this.oprator = oprator;
		this.oprateTime = oprateTime;
		this.account = account;
		this.creditGived = creditGived;
		this.materialCode = materialCode;
		this.resetFlag = resetFlag;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getOprator() {
		return oprator;
	}

	public void setOprator(String oprator) {
		this.oprator = oprator;
	}

	public Date getOprateTime() {
		return oprateTime;
	}

	public void setOprateTime(Date oprateTime) {
		this.oprateTime = oprateTime;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public int getCreditGived() {
		return creditGived;
	}

	public void setCreditGived(int creditGived) {
		this.creditGived = creditGived;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getOprateTimeStr() {
		return oprateTimeStr;
	}

	public void setOprateTimeStr(String oprateTimeStr) {
		this.oprateTimeStr = oprateTimeStr;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Integer getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(Integer materialCode) {
		this.materialCode = materialCode;
	}
	
	
	
	public int getResetFlag() {
		return resetFlag;
	}

	public void setResetFlag(int resetFlag) {
		this.resetFlag = resetFlag;
	}

	@Override
	public String toString() {
		return "OprateRecordsBean [oprator=" + oprator + ", oprateTime="
				+ oprateTime + ", account=" + account + ", creditGived="
				+ creditGived + ", page=" + page + ", oprateTimeStr="
				+ oprateTimeStr + ", time=" + time + ", materialCode="
				+ materialCode + ", resetFlag=" + resetFlag + "]";
	}
	
	
	
}
