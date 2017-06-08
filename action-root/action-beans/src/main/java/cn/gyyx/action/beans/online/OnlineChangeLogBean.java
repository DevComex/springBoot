/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：online-change
 * @作者：fanyongliang
 * @联系方式：fanyongliang@gyyx.cn
 * @创建时间： 2015年9月28日
 * @版本号：
 * @本类主要用途描述：玩家兑换光宇币日志记录实体类
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.online;

import java.util.Date;

public class OnlineChangeLogBean {
	// 主键
	private Integer code;
	// 玩家社区账号
	private String account;
	// 玩家兑换前元宝数量
	private Integer yuanbao;
	// 玩家兑换后光宇币数量
	private Integer gyyxCurrency;
	// 兑换时间
	private Date changeTime;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Integer getYuanbao() {
		return yuanbao;
	}

	public void setYuanbao(Integer yuanbao) {
		this.yuanbao = yuanbao;
	}

	public Integer getGyyxCurrency() {
		return gyyxCurrency;
	}

	public void setGyyxCurrency(Integer gyyxCurrency) {
		this.gyyxCurrency = gyyxCurrency;
	}

	public Date getChangeTime() {
		return changeTime;
	}

	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}

	public OnlineChangeLogBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OnlineChangeLogBean(String account, Integer yuanbao,
			Integer gyyxCurrency, Date changeTime) {
		super();
		this.account = account;
		this.yuanbao = yuanbao;
		this.gyyxCurrency = gyyxCurrency;
		this.changeTime = changeTime;
	}

}
