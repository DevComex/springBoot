/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：xuanwuba
 * @作者：fanyongliang
 * @联系方式：fanyongliang@gyyx.cn
 * @创建时间： 2015年9月7日
 * @版本号：1.214
 * @本类主要用途描述：签到奖励
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.xwbcreditsluckydraw;

public class SignPrizeBean {
	//主键
	private Integer code;
	//奖品编号
	private Integer prizeCode;
	//奖品类型
	private String prizeType;
	//奖品图片
	private String prizePhoto;
	//奖品名称
	private String prizeName;
	//奖励性别
	private String prizeSex;
	//是否删除
	private Integer isDelete;
	//奖品邮件内容
	private String mailContent;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Integer getPrizeCode() {
		return prizeCode;
	}

	public void setPrizeCode(Integer prizeCode) {
		this.prizeCode = prizeCode;
	}

	public String getPrizeType() {
		return prizeType;
	}

	public void setPrizeType(String prizeType) {
		this.prizeType = prizeType;
	}

	public String getPrizePhoto() {
		return prizePhoto;
	}

	public void setPrizePhoto(String prizePhoto) {
		this.prizePhoto = prizePhoto;
	}

	public String getPrizeName() {
		return prizeName;
	}

	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}
	
	public String getPrizeSex() {
		return prizeSex;
	}

	public void setPrizeSex(String prizeSex) {
		this.prizeSex = prizeSex;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public String getMailContent() {
		return mailContent;
	}

	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}

}
