/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：wdninestory
 * @作者：yangyusheng
 * @联系方式：yangyusheng@gyyx.cn
 * @创建时间： 2015年3月11日 上午10:27:06
 * @版本号：
 * @本类主要用途描述：作品信息实体类
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.wdninestory;

public class WritingBean {

	private int code;
	private String roleName;
	private String server;
	private String storyName;
	private String storyLink;
	private String author;
	private String imageLink;
	private String type;
	private int voteNumber;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getStoryName() {
		return storyName;
	}

	public void setStoryName(String storyName) {
		this.storyName = storyName;
	}

	public String getStoryLink() {
		return storyLink;
	}

	public void setStoryLink(String storyLink) {
		this.storyLink = storyLink;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getImageLink() {
		return imageLink;
	}

	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getVoteNumber() {
		return voteNumber;
	}

	public void setVoteNumber(int voteNumber) {
		this.voteNumber = voteNumber;
	}

}
