package cn.gyyx.action.beans.wdallpk;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AllPKInfo {
	private Pattern phonePattern = Pattern.compile("^1[3|4|5|8|7][0-9]\\d{8}$");
	private Pattern idCardPattern = Pattern.compile("(^\\d{15}$)|(^\\d{17}([0-9]|X)$)");
	private Pattern qqPattern = Pattern.compile("^\\d{5,16}$");
	/**
	 * 主键
	 */
	int code;
	/**
	 * 玩家账号
	 */
	String account;
	/**
	 * 大区
	 */
	String area;
	/**
	 * 服务器
	 */
	String server;
	/**
	 * 角色名
	 */
	String characterName;
	/**
	 * 姓名
	 */
	String name;
	/**
	 * 身份证
	 */
	String idCard;
	/**
	 * 电话号码
	 */
	String phoneNumber;
	/**
	 * 短信验证码
	 */
	String messageCaptcha;
	/**
	 * QQ号
	 */
	String qqNumber;
	/**
	 * 验证码
	 */
	String captcha;
	/**
	 * 图片地址
	 */
	String imgUrl;
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	public String getCharacterName() {
		return characterName;
	}
	public void setCharacterName(String characterName) {
		this.characterName = characterName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getMessageCaptcha() {
		return messageCaptcha;
	}
	public void setMessageCaptcha(String messageCaptcha) {
		this.messageCaptcha = messageCaptcha;
	}
	public String getQqNumber() {
		return qqNumber;
	}
	public void setQqNumber(String qqNumber) {
		this.qqNumber = qqNumber;
	}
	public String getCaptcha() {
		return captcha;
	}
	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
	public String validate() {
		if(account == null) {
			return "账号获取失败";
		}
		/*if(area == null||area.length() == 0) {
			return "大区错误";
		}
		if(server == null||server.length() == 0) {
			return "服务器错误";
		}
		if(characterName != null) {
			int count = countWord(characterName);
			if(count < 2 || count > 16) {
				return "角色名错误";
			}
		} else {
			return "角色名错误";
		}*/
		if(name != null) {
			int count = countName(name);
			if(count < 2 || count > 16) {
				return "姓名错误";
			}
		} else {
			return "姓名错误";
		}
		if(idCard == null||!idCardPattern.matcher(idCard).matches()) {
			return "身份证错误";
		}
		if(phoneNumber == null||!phonePattern.matcher(phoneNumber).matches()){
			return "手机号格式错误";
		}
		if(messageCaptcha == null||messageCaptcha.length() == 0) {
			return "手机验证码格式错误";
		}
		if(qqNumber == null||!qqPattern.matcher(qqNumber).matches()) {
			return "QQ号码格式错误";
		}
		if(captcha == null||captcha.length() == 0) {
			return "验证码格式错误";
		}
		if(imgUrl == null||imgUrl.length() == 0) {
			return "身份证照片不能为空";
		}
		return "SUCCESS";
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	
	private int countName(String name) {
		int res = 0;
		char[] cs = name.toCharArray();
		if(isChinese(cs[0])) {
			for(int i = 0;i < cs.length;i++) {
				if(isChinese(cs[i])) {
					res +=2;
				} else {
					return -1;
				}
			}
		} else {
			for(int i = 0;i < cs.length;i++) {
				if(!isChinese(cs[i])) {
					res +=1;
				} else {
					return -1;
				}
			}
		}
		return res;
	}
	
	private int countWord(String src) {
		int res = 0;
		char[] cs = src.toCharArray();
		for(char c:cs) {
			if(isChinese(c)) {
				res += 2;
			} else {
				res ++;
			}
		}
		return res;
	}
	
	final String regEx = "[\u4e00-\u9fa5]";
	final Pattern p = Pattern.compile(regEx);

	public boolean isChinese(char c) {
		Matcher m = p.matcher(c + "");
		if (m.find()) {
			return true;
		}
		return false;
	}
}
