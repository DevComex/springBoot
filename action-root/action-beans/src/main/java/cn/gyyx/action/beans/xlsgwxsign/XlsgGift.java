/**------------------------------------------------------------------------- 
* 版权所有：北京光宇在线科技有限责任公司 
-------------------------------------------------------------------------*/ 

package cn.gyyx.action.beans.xlsgwxsign;

/**
 * 作        者：成龙 
 * 联系方式：chenglong@gyyx.cn 
 * 创建时间： 2016年5月13日 下午6:32:00
 * 描        述： 绝世武神微信签到功能-礼包表
 */
public class XlsgGift {
	private Integer code;//主键
	private String gitfName;//礼包名称
	private Integer giftIntergral;//兑换所需积分值
	private Integer isDisplay = 1;//是否显示(1是，0否默认1)
	private String giftPicture;//礼包照片
	private String giftDesc;//礼包描述
	private Integer soft;//排序
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getGitfName() {
		return gitfName;
	}
	public void setGitfName(String gitfName) {
		this.gitfName = gitfName;
	}
	public Integer getGiftIntergral() {
		return giftIntergral;
	}
	public void setGiftIntergral(Integer giftIntergral) {
		this.giftIntergral = giftIntergral;
	}
	public Integer getIsDisplay() {
		return isDisplay;
	}
	public void setIsDisplay(Integer isDisplay) {
		this.isDisplay = isDisplay;
	}
	public String getGiftPicture() {
		return giftPicture;
	}
	public void setGiftPicture(String giftPicture) {
		this.giftPicture = giftPicture;
	}
	public String getGiftDesc() {
		return giftDesc;
	}
	public void setGiftDesc(String giftDesc) {
		this.giftDesc = giftDesc;
	}
	public Integer getSoft() {
		return soft;
	}
	public void setSoft(Integer soft) {
		this.soft = soft;
	}
	
}
