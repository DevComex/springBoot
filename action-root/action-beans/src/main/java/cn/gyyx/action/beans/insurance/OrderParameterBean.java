/*------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：王雷
 * 联系方式：wanglei@gyyx.cn 
 * 创建时间：2015年07月14日 
 * 版本号：v1.0 
 * 本类主要用途描述： 虚拟保险项目——保单参数表实体类
 * 
-------------------------------------------------------------------------*/

package cn.gyyx.action.beans.insurance;

public class OrderParameterBean {
	//主键
	int code;
	//保险类别
	String type;
	//周期
	Integer circle;
	//赔率
	Float odds;
	//保额上限
	Float upper;
	//保额下限
	Float lower;
	//默认保额
	Float defaultAmount;
	//周期名
	String circleName;
	//是否删除
	Boolean isDelete;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getCircle() {
		return circle;
	}

	public void setCircle(Integer circle) {
		this.circle = circle;
	}

	public Float getOdds() {
		return odds;
	}

	public void setOdds(Float odds) {
		this.odds = odds;
	}

	public Float getUpper() {
		return upper;
	}

	public void setUpper(Float upper) {
		this.upper = upper;
	}

	public Float getLower() {
		return lower;
	}

	public void setLower(Float lower) {
		this.lower = lower;
	}

	public Float getDefaultAmount() {
		return defaultAmount;
	}

	public void setDefaultAmount(Float defaultAmount) {
		this.defaultAmount = defaultAmount;
	}

	public String getCircleName() {
		return circleName;
	}

	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public OrderParameterBean(String type, int circle, float odds,
			float upper, float lower, float defaultAmount, String circleName) {
		super();
		this.type = type;
		this.circle = circle;
		this.odds = odds;
		this.upper = upper;
		this.lower = lower;
		this.defaultAmount = defaultAmount;
		this.circleName = circleName;
	}
	
	public OrderParameterBean(int code, String type, int circle, float odds,
			float upper, float lower, float defaultAmount, String circleName) {
		super();
		this.code = code;
		this.type = type;
		this.circle = circle;
		this.odds = odds;
		this.upper = upper;
		this.lower = lower;
		this.defaultAmount = defaultAmount;
		this.circleName = circleName;
	}
	public OrderParameterBean() {
		super();
	}

	@Override
	public String toString() {
		return "OrderParameterBean [code=" + code + ", type=" + type
				+ ", circle=" + circle + ", odds=" + odds + ", upper=" + upper
				+ ", lower=" + lower + ", defaultAmount=" + defaultAmount
				+ ", circleName=" + circleName + ", isDelete=" + isDelete + "]";
	}
	
}
