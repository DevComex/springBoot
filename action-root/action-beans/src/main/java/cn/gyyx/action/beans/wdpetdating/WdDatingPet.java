package cn.gyyx.action.beans.wdpetdating;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.gyyx.action.beans.common.ActionCommonFormBean.Codeable;
import cn.gyyx.action.beans.common.ActionCommonImageBean;


/**
 * 该类为json对应实体，以json串格式装入数据库
 *
 */
public class WdDatingPet implements Codeable{
	int code;
	/**
	 * 所属账号Id
	 */
	int accountCode;
	/**
	 * 账号名
	 */
	String account;
	/**
	 * 大区名称
	 */
	String area;
	/**
	 * 大区代码
	 */
	int areaCode;
	/**
	 * 服务器名
	 */
	String server;
	/**
	 * 服务器代码
	 */
	int serverCode;
	/**
	 * 角色名
	 */
	String character;
	/**
	 * 作品名
	 */
	String petName;
	/**
	 * 作品叙述
	 */
	String describe;
	/**
	 * 得票数
	 */
	int admired;
	Date uploadDate;
	/**
	 * 图片实体主键
	 */
	int imgCode;
	/**
	 * 图片实体 
	 */
	ActionCommonImageBean imageBean;
	private String sDate;
	
	public String getsDate() {
		return sDate;
	}
	public void setsDate(String sDate) {
		this.sDate = sDate;
	}
	@Override
	public int getCode() {
		return code;
	}
	@Override
	public void setCode(int code) {
		this.code = code;
	}
	
	public int getAccountCode() {
		return accountCode;
	}
	public void setAccountCode(int accountCode) {
		this.accountCode = accountCode;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public void setAreaCode(int areaCode) {
		this.areaCode = areaCode;
	}
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	public int getServerCode() {
		return serverCode;
	}
	public void setServerCode(int serverCode) {
		this.serverCode = serverCode;
	}
	public String getCharacter() {
		return character;
	}
	public void setCharacter(String character) {
		this.character = character;
	}
	public String getPetName() {
		return petName;
	}
	public void setPetName(String petName) {
		this.petName = petName;
	}
	public String getDescribe() {
		return sizeLimitConvert(describe);
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public int getAreaCode() {
		return areaCode;
	}
	
	public ActionCommonImageBean getImageBean() {
		return imageBean;
	}
	public void setImageBean(ActionCommonImageBean imageBean) {
		this.imageBean = imageBean;
	}
	public int getAdmired() {
		return admired;
	}
	public void setAdmired(int admired) {
		this.admired = admired;
	}
	public int getImgCode() {
		return imgCode;
	}
	public void setImgCode(int imgCode) {
		this.imgCode = imgCode;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Date getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
	private String sizeLimitConvert(String src) {
		char[] cs = src.toCharArray();
		int allLength = 0;
		int crti = 0;
		for(crti = 0;crti < cs.length;crti++) {
			if(isChinese(cs[crti])) {
				allLength += 2;
			} else {
				allLength += 1;
			}
			if(allLength > 24) {
				return src.substring(0, crti) + "...";
			}
		}
		return src;
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
	public void convertParamCodeType() {
		try {
			character = convert(character);
			server = convert(server);
			area = convert(area);
			petName = convert(petName);
			describe = convert(describe);
		} catch (Exception e) {
		}
	}
	private String convert(String src) {
		String res = null;
		try {
			res = new String(src.getBytes("ISO-8859-1"),"UTF-8");
			res = res.replace('<','《')
					 .replace('>', '》')
					 .replace('\\', '＼')
					 .replace('\'', '‘')
					 .replace('&', '＆')
					 .replace(';', '；')
					 .replace('\"', '“');
		}catch(Exception e) {}
		return res;
	}
	@Override
	public String toString() {
		return code + "(" + admired + ")";
	}
}
