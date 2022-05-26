package Util;
import javax.crypto.*;
import java.security.*;
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

    public static void main(String[] args) throws NoSuchAlgorithmException {
        Encrypt test=new Encrypt();
        test.encrypt("test");
    }
}
