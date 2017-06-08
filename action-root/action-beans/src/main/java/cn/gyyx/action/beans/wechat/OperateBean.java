/*************************************************
       Copyright ©, 2015, GY Game
       Author: 范永良
       Created: 2015年-12月-23日
       Note:操作Bean
************************************************/
package cn.gyyx.action.beans.wechat;

public class OperateBean {
	// 主键
	private Integer code;
	// 统计编号
	private Integer configCode;
	// 操作类型
	private String operateType;
	// 操作描述
	private String operateDesc;
	// 操作参数
	private String operatePara;
	// 参与统计
	private boolean inCount;
	// 参与详细统计
	private boolean inDetail;
	// 是否删除
	private boolean isDelete;
	// 增加抽奖次数
	private Integer lotteryTime;
	// 增加抽奖次数限制
	private String lotteryLimit;

	/*
	 * 总数
	 */
	private Integer totalCount;

	public OperateBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OperateBean(Integer configCode, String operateType,
			String operateDesc, String operatePara, boolean inCount,
			boolean inDetail, boolean isDelete, Integer lotteryTime,
			String lotteryLimit) {
		super();
		this.configCode = configCode;
		this.operateType = operateType;
		this.operateDesc = operateDesc;
		this.operatePara = operatePara;
		this.inCount = inCount;
		this.inDetail = inDetail;
		this.isDelete = isDelete;
		this.lotteryTime = lotteryTime;
		this.lotteryLimit = lotteryLimit;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Integer getConfigCode() {
		return configCode;
	}

	public void setConfigCode(Integer configCode) {
		this.configCode = configCode;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getOperateDesc() {
		return operateDesc;
	}

	public void setOperateDesc(String operateDesc) {
		this.operateDesc = operateDesc;
	}

	public String getOperatePara() {
		return operatePara;
	}

	public void setOperatePara(String operatePara) {
		this.operatePara = operatePara;
	}

	public boolean isInCount() {
		return inCount;
	}

	public void setInCount(boolean inCount) {
		this.inCount = inCount;
	}

	public boolean isInDetail() {
		return inDetail;
	}

	public void setInDetail(boolean inDetail) {
		this.inDetail = inDetail;
	}

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getLotteryTime() {
		return lotteryTime;
	}

	public void setLotteryTime(Integer lotteryTime) {
		this.lotteryTime = lotteryTime;
	}

	public String getLotteryLimit() {
		return lotteryLimit;
	}

	public void setLotteryLimit(String lotteryLimit) {
		this.lotteryLimit = lotteryLimit;
	}

	@Override
	public String toString() {
		return "OperateBean [configCode=" + configCode + ", operateType="
				+ operateType + ", operateDesc=" + operateDesc
				+ ", operatePara=" + operatePara + ", inCount=" + inCount
				+ ", inDetail=" + inDetail + ", isDelete=" + isDelete
				+ ", lotteryTime=" + lotteryTime + ", lotteryLimit="
				+ lotteryLimit + "]";
	}

}
