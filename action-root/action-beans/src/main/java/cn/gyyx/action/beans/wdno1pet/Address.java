/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2014年12月29日下午2:07:34
 * 版本号：v1.0
 * 本类主要用途叙述：存放用户地址信息
 */



package cn.gyyx.action.beans.wdno1pet;

public class Address {
	/**
	 * 用户的名字
	 */
	private String txtRealName;
    /**
     * 用户电话
     */
	private String txtPhone;
	/**
	 * 用户地址
	 */
	private String txtAddress;
	/**
	 * 用户服务器
	 */
	private String channel;
	private String server;
	/**
	 * 得到用户服务器大区
	 * @return chanel 大区名称
	 * 
	 */
	public String getChannel() {
		if(channel != null){
			if(channel.equals("1")){
				return "网通";
			}
			else if(channel.equals("2")){
				return "电信";
			}
			else if(channel.equals("3")){
				return "双线";
			}
			else {
				return "不存在";
			}
		}
		else{
			return "不存在";
		}
		
	}
	/**
	 * 设定服务器大区名称
	 * @param channel
	 */
	public void setChannel(String channel) {
		this.channel = channel;
	}
	/**
	 * 得到具体服务器
	 * @return server 服务器编号
	 */
	public String getServer() {
		return server;
	}
	/**
	 * 设定服务器编号
	 * @param server
	 */
	public void setServer(String server) {
		this.server = server;
	}
	/**
	 * 得到用户姓名
	 * @return username 用户姓名
	 */
	public String getTxtRealName() {
		return txtRealName;
	}
	/**
	 * 设定用户姓名
	 * @param txtRealName
	 */
	public void setTxtRealName(String txtRealName) {
		this.txtRealName = txtRealName;
	}
	/**
	 * 得到用户电话
	 * @return 用户电话
	 */
	public String getTxtPhone() {
		return txtPhone;
	}
	/**
	 * 设定用户电话
	 * @param txtPhone
	 */
	public void setTxtPhone(String txtPhone) {
		this.txtPhone = txtPhone;
	}
	/**
	 * 得到用户地址
	 * @return 用户地址
	 */
	public String getTxtAddress() {
		return txtAddress;
	}
	/**
	 * 设定用户地址
	 * @param txtAddress
	 */
	public void setTxtAddress(String txtAddress) {
		this.txtAddress = txtAddress;
	}

}
