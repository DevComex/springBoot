package cn.gyyx.action.bll.wdlogdata;

public class WdDecrypt {

	public static String encode(String str) {
		 int code = 2; // 密钥
		 char[] charArray = str.toCharArray();
		 for(int i = 0; i < charArray.length; i++){
		  charArray[i] = (char) (charArray[i] ^ code);
		 }
		 return new String(charArray);
		}
	}