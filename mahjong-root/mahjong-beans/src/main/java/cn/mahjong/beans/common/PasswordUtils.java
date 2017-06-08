package cn.mahjong.beans.common;

import java.security.MessageDigest;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: PasswordUtils
 * @description 密码工具类
 * @author 
 * @date 2016年8月10日
 */
public class PasswordUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordUtils.class);

    /**
     * @Title: md5 @Description: 密码MD5加密 @param s @return String @throws
     */
    public static String md5(String s) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9','a', 'b', 'c', 'd', 'e', 'f' };
        try {
            byte[] strTemp = s.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            LOGGER.warn("" + e);
            return "";
        }
    }

    /**
     * @Title: encode @Description: 对password进行加密 @param password @param
     * salt @return String @throws
     */
    public static String encode(String password, String salt) {
        return md5("Au4W*KZf" + salt + md5(password));
    }

    /**
     * @Title: validate @Description: 验证密码 @param currentPwd @param salt @param
     * existPwd @return boolean @throws
     */
    public static boolean validate(String currentPwd, String salt,
            String existPwd) {
        if (encode(currentPwd, salt).equals(existPwd)) {
            return true;
        }
        return false;
    }

    /**
     * @Title: generatePasswordalt @Description: 生成密碼盐值 @param @return
     * String @throws
     */
    public static String generatePasswordalt() {
        return RandomStringUtils.randomAscii(8).trim();
    }
}
