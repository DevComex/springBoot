package cn.mahjong.beans.common;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
  * <p>
  *   AESUtil描述
  * </p>
  *  
  * @author 
  * @since 0.0.1
  */
public class AES {
    private static final Logger LOGGER = LoggerFactory.getLogger(AES.class);
    /** 
     * 加密 
     *  
     * @param content 需要加密的内容 
     * @param password  加密密码 
     * @return 
     */  
    public static String encrypt(String content, String password) {  
            try {  
                  //1.构造密钥生成器，指定为AES算法,不区分大小写
                    KeyGenerator keygen=KeyGenerator.getInstance("AES");
                    //2.根据ecnodeRules规则初始化密钥生成器
                    //生成一个128位的随机源,根据传入的字节数组
                    SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" ); 
                    secureRandom.setSeed(password.getBytes()); 
                    keygen.init(128, secureRandom);
                      //3.产生原始对称密钥
                    SecretKey originalKey=keygen.generateKey();
                      //4.获得原始对称密钥的字节数组
                    byte [] raw=originalKey.getEncoded();
                    //5.根据字节数组生成AES密钥
                    SecretKey key=new SecretKeySpec(raw, "AES");
                      //6.根据指定算法AES自成密码器
                    Cipher cipher=Cipher.getInstance("AES");
                      //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
                    cipher.init(Cipher.ENCRYPT_MODE, key);
                    //8.获取加密内容的字节数组(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
                    byte [] byteEncode=content.getBytes("utf-8");
                    //9.根据密码器的初始化方式--加密：将数据加密
                    byte [] byteAES=cipher.doFinal(byteEncode);
                    String aesEncode = Base64.getEncoder().encodeToString(byteAES);
                    return aesEncode;
            } catch (NoSuchAlgorithmException e) {
                LOGGER.warn("" + e);
            } catch (NoSuchPaddingException e) {  
                LOGGER.warn("" + e);
            } catch (InvalidKeyException e) {  
                LOGGER.warn("" + e); 
            } catch (UnsupportedEncodingException e) {  
                LOGGER.warn("" + e); 
            } catch (IllegalBlockSizeException e) {  
                LOGGER.warn("" + e);
            } catch (BadPaddingException e) {  
                LOGGER.warn("" + e);
            }  
            return null;  
    } 
    
    /**解密 
     * @param content  待解密内容 
     * @param password 解密密钥 
     * @return 
     * @throws UnsupportedEncodingException 
     */  
    public static String decrypt(String content, String password) throws UnsupportedEncodingException {  
            try {  
                //1.构造密钥生成器，指定为AES算法,不区分大小写
                KeyGenerator keygen=KeyGenerator.getInstance("AES");
                //2.根据ecnodeRules规则初始化密钥生成器
                //生成一个128位的随机源,根据传入的字节数组
                SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" ); 
                secureRandom.setSeed(password.getBytes()); 
                keygen.init(128, secureRandom);
                  //3.产生原始对称密钥
                SecretKey originalKey=keygen.generateKey();
                  //4.获得原始对称密钥的字节数组
                byte [] raw=originalKey.getEncoded();
                //5.根据字节数组生成AES密钥
                SecretKey key=new SecretKeySpec(raw, "AES");
                  //6.根据指定算法AES自成密码器
                Cipher cipher=Cipher.getInstance("AES");
                  //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
                cipher.init(Cipher.DECRYPT_MODE, key);
                //8.将加密并编码后的内容解码成字节数组
                byte [] byteContent= Base64.getDecoder().decode(content);
                /*
                 * 解密
                 */
                byte [] byteDecode=cipher.doFinal(byteContent);
                String aesDecode= new String(byteDecode,"utf-8");
                return aesDecode;
            } catch (NoSuchAlgorithmException e) {  
                LOGGER.warn("" + e); 
            } catch (NoSuchPaddingException e) {  
                LOGGER.warn("" + e);
            } catch (InvalidKeyException e) {  
                LOGGER.warn("" + e); 
            } catch (IllegalBlockSizeException e) {  
                LOGGER.warn("" + e); 
            } catch (BadPaddingException e) {  
                LOGGER.warn("" + e); 
            }  
            return null;  
    }  

}
