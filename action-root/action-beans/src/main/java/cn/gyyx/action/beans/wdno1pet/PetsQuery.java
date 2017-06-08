/*------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：Lizepu
 * 联系方式：lizepu@gyyx.cn 
 * 创建时间： 2014年12月17日13:11:09
 * 版本号：v1.0 
 * 本类主要用途描述： 
 * 用于复杂宠物作品查询的参数实体
-------------------------------------------------------------------------*/
package cn.gyyx.action.beans.wdno1pet;


public class PetsQuery {
	private String stg = "newest";
	private int serverId = -1;
	private String characterName;
	private String petName;
	private int pageIndex = 0;
	private String petType;
	
	public String getPetType() {
		return petType;
	}
	public void setPetType(String petType) {
		this.petType = "null".equals(petType)?null:petType;
	}
	public String getStg() {
		return stg;
	}
	public void setStg(String stg) {
		this.stg = stg;
	}
	public int getServerId() {
		return serverId;
	}
	public void setServerId(int serverId) {
		this.serverId = serverId;
	}
	public String getCharacterName() {
		return characterName;
	}
	public void setCharacterName(String characterName) {
		this.characterName = "null".equals(characterName)?null:characterName;
	}
	public String getPetName() {
		return petName;
	}
	public void setPetName(String petName) {
		this.petName = "null".equals(petName)?null:petName;
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	
}
