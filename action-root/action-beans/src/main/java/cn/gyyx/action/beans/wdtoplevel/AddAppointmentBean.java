package cn.gyyx.action.beans.wdtoplevel;

public class AddAppointmentBean {
	public static final String CHECK_SUCCESS = "SUCCESS";
	
	String area;
	String serverName;
	String characterName;
	int type = -1;
	
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getCharacterName() {
		return characterName;
	}
	public void setCharacterName(String characterName) {
		this.characterName = characterName;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getTypeInString(){
		return type == 0?"28DAYS":"45DAYS";
	}
	
	public String check(){
		if(area == null||area.equals("0")){
			return "请选择预约冲级赛服务器区组";
		} else if(serverName == null||serverName.equals("0")){
			return "请选择预约冲级赛服务器";
		} else if(characterName == null||characterName.length() == 0){
			return "请输入您想预约冲级赛的角色名";
		} else if(characterName.length() < 1||characterName.length() > 20){
			return "角色名应在1-20位字符";
		} else if(characterName.startsWith(" ")||characterName.endsWith(" ")){
			return "角色名不能以空格开头或结尾";
		}else if(type != 0&&type != 1){
			return "请选择正确的预约类型";
		} else {
			return CHECK_SUCCESS;
		}
	}
}
