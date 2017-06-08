/*------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：Chen 
 * 联系方式：chenpeng03@gyyx.cn 
 * 创建时间： 2014年12月15日 下午2:02:50
 * 版本号：v1.0 
 * 本类主要用途描述： 
 * 天下第一宠详细信息实体
-------------------------------------------------------------------------*/
package cn.gyyx.action.beans.wdno1pet;

import java.sql.Timestamp;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WDNo1PetInfoBean {
	private int petCode;
	private int imgCode;
	private String petName;
	private String channel;
	private int serverID;
	private String serverName;
	
	private String characterName;
	private String petInfo;
	private int petGrowth;
	private int petBlood;
	private int petSpeed;
	private int petMagic;
	private int petAttack;
	private String petType;

	private Timestamp voteTime;
	private int voteCount;

	/**
	 * 用于前端展示,图片地址,最强属性键值
	 */
	private String imgURL;
	private String strongKey;
	private String strongValue;
	/**存储截断字符
	 * */
	private String smallCharacterName;
	private String smallPetName;
	
	public String getSmallCharacterName(){
		return smallCharacterName;
	}
	
	public String getSmallPetName(){
		return smallPetName;
	}
	
	public String getServerName() {
		return serverName;
	}


	public void setServerName(String serverName) {
		this.serverName = serverName;
	}


	public String getTypeInString() {
		String res;
		switch (petType) {
		case "0":
			res = "普宠";
			break;
		case "1":
			res = "元灵";
			break;
		case "2":
			res = "神兽";
			break;
		case "3":
			res = "变异";
			break;
		case "4":
			res = "坐骑";
			break;
		default:
			res = "ERROR";
			break;
		}
		return res;
	}
	
	
	public String getPetType() {
		return petType;
	}

	public void setPetType(String petType) {
		this.petType = petType;
	}

	public int getPetCode() {
		return petCode;
	}

	public void setPetCode(int petCode) {
		this.petCode = petCode;
	}

	public Integer getImgCode() {
		return imgCode;
	}

	public void setImgCode(Integer imgCode) {
		this.imgCode = imgCode;
	}

	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
		this.smallPetName = getSmallSize(petName, 14);
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Integer getServerID() {
		return serverID;
	}

	public void setServerID(Integer serverID) {
		this.serverID = serverID;
	}

	public String getCharacterName() {
		return characterName;
	}

	public void setCharacterName(String characterName) {
		this.characterName = characterName;
		this.smallCharacterName = getSmallSize(characterName, 12);
	}

	public String getPetInfo() {
		return petInfo;
	}

	public void setPetInfo(String petInfo) {
		this.petInfo = petInfo;
	}

	public Integer getPetGrowth() {
		return petGrowth;
	}

	public void setPetGrowth(Integer petGrowth) {
		this.petGrowth = petGrowth;
	}

	public Integer getPetBlood() {
		return petBlood;
	}

	public void setPetBlood(Integer petBlood) {
		this.petBlood = petBlood;
	}

	public Integer getPetSpeed() {
		return petSpeed;
	}

	public void setPetSpeed(Integer petSpeed) {
		this.petSpeed = petSpeed;
	}

	public Integer getPetMagic() {
		return petMagic;
	}

	public void setPetMagic(Integer petMagic) {
		this.petMagic = petMagic;
	}

	public Integer getPetAttack() {
		return petAttack;
	}

	public void setPetAttack(Integer petAttack) {
		this.petAttack = petAttack;
	}

	public Timestamp getVoteTime() {
		return voteTime;
	}

	public void setVoteTime(Timestamp voteTime) {
		this.voteTime = voteTime;
	}

	public Integer getVoteCount() {
		return voteCount;
	}

	public void setVoteCount(Integer voteCount) {
		this.voteCount = voteCount;
	}

	public String getImgURL() {
		return imgURL;
	}

	public void setImgURL(String imgURL) {
		this.imgURL = imgURL;
	}

	public String getSmallUrl(){
		if(imgURL != null){
			String[] extraNames = imgURL.split("\\.");
			String lastExtra = extraNames[extraNames.length - 1];
			return imgURL.split("." + lastExtra)[0] + "-small" + "." + lastExtra;
		}
		return "realURL is NULL!";
	}
	
	public String getStrongKey() {
		return strongKey;
	}

	public void setStrongKey(String strongKey) {
		this.strongKey = strongKey;
	}

	/**
	 * 获取属性名称:属性值<br>
	 * <strong>K</strong>:pet_growth,pet_blood,pet_speed,pet_magic,pet_attack-[
	 * 输入]<br>
	 * <strong>V</strong>:总成长，气血，速度，法伤，物伤-[输出]
	 * 
	 * @return
	 */
	public String getStrongValue() {
		switch (this.strongKey) {
		case "pet_growth":
			strongValue = "总成长:" + this.petGrowth;
			break;
		case "pet_blood":
			strongValue = "气血:" + this.petBlood;
			break;
		case "pet_speed":
			strongValue = "速度:" + this.petSpeed;
			break;
		case "pet_magic":
			strongValue = "法伤:" + this.petMagic;
			break;
		case "pet_attack":
			strongValue = "物伤:" + this.petAttack;
			break;

		default:
			break;
		}
		return strongValue;
	}

	public void setStrongValue(String strongValue) {
		this.strongValue = strongValue;
	}
	
	private boolean isChinese(char c) {
		final String regEx = "[\u4e00-\u9fa5]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(c + "");
		if (m.find()) {
			return true;
		}
		return false;
	}
	
	/**根据限制计算中英混合字符串的截断长度
	 * @param str 原字符串
	 * @param enLimit 以英文为标准的字符串长度
	 * @return 阶段长度
	 */
	private String getSmallSize(String str,int enLimit){
		int crtLimit = 0,crtIndex = 0;
		char[] chars = str.toCharArray();
		for(char c:chars){
			if(isChinese(c)){
				crtLimit += 2;
			}else{
				crtLimit += 1;
			}
			crtIndex++;
			if(crtLimit >= enLimit){
				return new String(chars,0,crtIndex);
			}
		}
		return str;
	}
}
