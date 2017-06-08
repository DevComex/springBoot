package cn.gyyx.action.beans.sdsg.advice;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class SdsgAdviceForm {

	@NotBlank(message="标题不能为空")
	@Length(min=0,max=15,message="标题长度不能超过15")
	private String title;
	
	@NotBlank(message="区服不能为空")
	private String server;
	
	@NotBlank(message="账号不能为空")
//	@Pattern(regexp="^[a-zA-Z_][0-9a-zA-Z_]{5,15}$",message="账号格式不正确")
	private String account;
	
	@NotBlank(message="角色名不能为空")
	private String roleName;
	
	@NotBlank(message="内容不能为空")
	private String content;
	
	@NotBlank(message="验证码不能为空")
	private String CAPTCHA;

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the server
	 */
	public String getServer() {
		return server;
	}

	/**
	 * @param server the server to set
	 */
	public void setServer(String server) {
		this.server = server;
	}

	/**
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the cAPTCHA
	 */
	public String getCAPTCHA() {
		return CAPTCHA;
	}

	/**
	 * @param cAPTCHA the cAPTCHA to set
	 */
	public void setCAPTCHA(String cAPTCHA) {
		CAPTCHA = cAPTCHA;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((CAPTCHA == null) ? 0 : CAPTCHA.hashCode());
		result = prime * result + ((account == null) ? 0 : account.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((roleName == null) ? 0 : roleName.hashCode());
		result = prime * result + ((server == null) ? 0 : server.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SdsgAdviceForm other = (SdsgAdviceForm) obj;
		if (CAPTCHA == null) {
			if (other.CAPTCHA != null)
				return false;
		} else if (!CAPTCHA.equals(other.CAPTCHA))
			return false;
		if (account == null) {
			if (other.account != null)
				return false;
		} else if (!account.equals(other.account))
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (roleName == null) {
			if (other.roleName != null)
				return false;
		} else if (!roleName.equals(other.roleName))
			return false;
		if (server == null) {
			if (other.server != null)
				return false;
		} else if (!server.equals(other.server))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SdsgAdviceForm [title=" + title + ", server=" + server + ", account=" + account + ", roleName="
				+ roleName + ", content=" + content + ", CAPTCHA=" + CAPTCHA + "]";
	}

	
}