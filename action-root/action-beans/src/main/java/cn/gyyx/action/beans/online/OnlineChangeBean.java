/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：online-change
 * @作者：fanyongliang
 * @联系方式：fanyongliang@gyyx.cn
 * @创建时间： 2015年9月28日
 * @版本号：
 * @本类主要用途描述：玩家元宝信息实体类
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.online;

public class OnlineChangeBean {
	// 主键
	private Integer code;
	// 玩家社区账号
	private String account;
	// 玩家账号元宝数量
	private Integer yuanbao;

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

	public OnlineChangeBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OnlineChangeBean(String account, Integer yuanbao) {
		super();
		this.account = account;
		this.yuanbao = yuanbao;
	}

}
