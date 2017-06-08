package cn.gyyx.action.beans.weixin.related;

public class PictureSourceBean {
	private String type;
	private String group;
	private String width;
	private String height;
	
	
	public PictureSourceBean() {

	}
	/**
	 * @param type  在数据库中唯一表示 appid 与 secret （weixin_sysconfig_tb）
	 * @param group  表示资源所在服务器文件夹（不可随意填写，需先在服务器端配置）
	 * @param width
	 * @param height
	 */
	public PictureSourceBean(String type, String group, String width,String height) {
		this.type = type;
		this.group = group;
		this.width = width;
		this.height = height;
	}
	/**
	 * 在数据库中唯一表示 appid 与 secret （weixin_sysconfig_tb）
	 * @return
	 */
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 表示资源所在服务器文件夹（不可随意填写，需先在服务器端配置）
	 * @return
	 */
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public void fillAll(String type, String group, String width,String height){
		this.type = type;
		this.group = group;
		this.width = width;
		this.height = height;
	}
	
}
