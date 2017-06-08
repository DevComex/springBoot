package cn.gyyx.action.beans.xwbcreditsluckydraw;

public class CollectTopShowBean {
	private Integer topNum;
	private String account;
	private String materialType;

	public CollectTopShowBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CollectTopShowBean(Integer topNum, String account,
			String materialType) {
		super();
		this.topNum = topNum;
		this.account = account;
		this.materialType = materialType;
	}

	public Integer getTopNum() {
		return topNum;
	}

	public void setTopNum(Integer topNum) {
		this.topNum = topNum;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getMaterialType() {
		return materialType;
	}

	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}

	@Override
	public String toString() {
		return "CollectTopShowBean [topNum=" + topNum + ", account=" + account
				+ ", materialType=" + materialType + "]";
	}

}
