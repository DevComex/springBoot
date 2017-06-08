package cn.gyyx.action.beans.wdlight2017;

/**
 * 后台审核许愿bean   userInfo  wish 关联
 * @ClassName: ValidWishBean
 * @description ValidWishBean
 * @author luozhenyu
 * @date 2016年12月22日
 */
public class ValidWishBean {
	
	private int  code;
	
    private String account;
    
    private String serverName;
    
    private String roleName;
    
    private String content;
    
    //许愿层数
    private int level;
    //许愿状态
    private int status;
	/**
	 * @return the code
	 */
    private int score;
    
    private int consumeScore;

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
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
	 * @return the serverName
	 */
	public String getServerName() {
		return serverName;
	}

	/**
	 * @param serverName the serverName to set
	 */
	public void setServerName(String serverName) {
		this.serverName = serverName;
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
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * @return the consumeScore
	 */
	public int getConsumeScore() {
		return consumeScore;
	}

	/**
	 * @param consumeScore the consumeScore to set
	 */
	public void setConsumeScore(int consumeScore) {
		this.consumeScore = consumeScore;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((account == null) ? 0 : account.hashCode());
		result = prime * result + code;
		result = prime * result + consumeScore;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + level;
		result = prime * result + ((roleName == null) ? 0 : roleName.hashCode());
		result = prime * result + score;
		result = prime * result + ((serverName == null) ? 0 : serverName.hashCode());
		result = prime * result + status;
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
		ValidWishBean other = (ValidWishBean) obj;
		if (account == null) {
			if (other.account != null)
				return false;
		} else if (!account.equals(other.account))
			return false;
		if (code != other.code)
			return false;
		if (consumeScore != other.consumeScore)
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (level != other.level)
			return false;
		if (roleName == null) {
			if (other.roleName != null)
				return false;
		} else if (!roleName.equals(other.roleName))
			return false;
		if (score != other.score)
			return false;
		if (serverName == null) {
			if (other.serverName != null)
				return false;
		} else if (!serverName.equals(other.serverName))
			return false;
		if (status != other.status)
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ValidWishBean [code=" + code + ", account=" + account + ", serverName=" + serverName + ", roleName="
				+ roleName + ", content=" + content + ", level=" + level + ", status=" + status + ", score=" + score
				+ ", consumeScore=" + consumeScore + "]";
	}
	
}
