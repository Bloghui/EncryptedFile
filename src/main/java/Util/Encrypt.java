package Util;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.Base64;
import java.util.UUID;

/**
 * @author lihui
 * @description TODO
 * @date 2022/5/25 17:31
 */

public class Encrypt {
    public  void encrypt(String context) throws NoSuchAlgorithmException {
        KeyGenerator kgen = null;
        kgen = KeyGenerator.getInstance("AES");
        //设置密钥长度
        kgen.init(128);
        //生成密钥
        SecretKey key = kgen.generateKey();
        //返回密钥的二进制编码
       // return skey.getEncoded();
       byte[]chars= key.getEncoded();
        for (byte aChar : chars) {
            System.out.println(String.valueOf(aChar));
        }
    }
//    生成秘钥，返回BASE64 处理之后的密钥字符串
    public static String generateAESKey(){
        UUID uuid = UUID.randomUUID();
        String aesKey = Base64.getEncoder().encodeToString(uuid.toString().getBytes()).substring(2,34);
        return aesKey;
    }
    public static String generateAESIv(){
        UUID uuid = UUID.randomUUID();
        String iv = Base64.getEncoder().encodeToString(uuid.toString().getBytes()).substring(2,18);
        return iv;
    }
    public static String encryptAES(byte[] content, String secretKeyStr, String iv) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
        // 获得一个加密规则 SecretKeySpec
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyStr.getBytes(), "AES");
        // 获得加密算法实例对象 Cipher
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); //"算法/模式/补码方式"
        // 获得一个 IvParameterSpec
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());  // 使用 CBC 模式，需要一个向量 iv, 可增加加密算法的强度
        // 根据参数初始化算法
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        // 执行加密并返回经 BASE64 处助理之后的密文
        return Base64.getEncoder().encodeToString(cipher.doFinal(content));
    }
    public static String decryptAES(byte[] content, String secretKeyStr, String iv) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException, UnsupportedEncodingException, UnsupportedEncodingException {
        // 密文进行 BASE64 解密处理
        byte[] contentDecByBase64 = Base64.getDecoder().decode(content);
        // 获得一个 SecretKeySpec
        // SecretKeySpec secretKeySpec = new SecretKeySpec(Base64.getDecoder().decode(secretKeyStr), "AES");
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyStr.getBytes(), "AES");
        // 获得加密算法实例对象 Cipher
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); //"算法/模式/补码方式"
        // 获得一个初始化 IvParameterSpec
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
        // 根据参数初始化算法
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        // 解密
        return new String(cipher.doFinal(contentDecByBase64), "utf8");
    }
}
