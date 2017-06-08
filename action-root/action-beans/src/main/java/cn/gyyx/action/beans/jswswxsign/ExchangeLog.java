/**------------------------------------------------------------------------- 
* 版权所有：北京光宇在线科技有限责任公司 
-------------------------------------------------------------------------*/ 

package cn.gyyx.action.beans.jswswxsign;

import java.util.Date;

/** 
 * 作        者：成龙 
 * 联系方式：chenglong@gyyx.cn 
 * 创建时间： 2016年5月13日 下午6:32:00
 * 描        述： 绝世武神微信签到功能-礼包兑换记录表
 */
public class ExchangeLog {
	private Integer code;//主键
	private Integer gid;//礼包ID
	private String openId;//用户ID
	private Date exangeTime;//兑换时间
	private Integer exangeIntegral;//兑换所花积分
	private String exangeCode;//兑换码
	
	private String giftName;//冗余查询字段
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public Integer getGid() {
		return gid;
	}
	public void setGid(Integer gid) {
		this.gid = gid;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public Date getExangeTime() {
		return exangeTime;
	}
	public void setExangeTime(Date exangeTime) {
		this.exangeTime = exangeTime;
	}
	public Integer getExangeIntegral() {
		return exangeIntegral;
	}
	public void setExangeIntegral(Integer exangeIntegral) {
		this.exangeIntegral = exangeIntegral;
	}
	public String getExangeCode() {
		return exangeCode;
	}
	public void setExangeCode(String exangeCode) {
		this.exangeCode = exangeCode;
	}
	public String getGiftName() {
		return giftName;
	}
	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}
	
}
