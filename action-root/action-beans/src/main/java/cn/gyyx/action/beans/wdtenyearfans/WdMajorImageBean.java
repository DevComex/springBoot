/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：问道十周年粉丝榜
 * @作者：chenpeilan
 * @联系方式：chenpeilan@gyyx.cn
 * @创建时间： 2016年3月31日
 * @版本号：
 * @本类主要用途描述：问道十周年粉丝榜活动职业对应图片bean
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.wdtenyearfans;

public class WdMajorImageBean {
	//主键code
	private int code;
	// 职业
	private int major;
	// 职业性别
	private int sex;
	// 图片名称
	private String path;
	// 提名类型(0为自己，1为他人)
	private int type;
	
	public int getMajor() {
		return major;
	}
	public void setMajor(int major) {
		this.major = major;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

	

}
