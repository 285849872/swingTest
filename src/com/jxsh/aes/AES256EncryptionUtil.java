package com.jxsh.aes;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Created by zyb on 2018/5/23.
 */
public class AES256EncryptionUtil {
    private static String MD5(String text) throws Exception {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(text.getBytes("UTF-8"), 0, text.length());
            String secretKey = new BigInteger(1, md.digest()).toString(16);
            while (secretKey.length() < 32) {
                secretKey = "0".concat(secretKey);
            }
            return secretKey.toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    public static String encrypt(String raw, String cdd) {
        String encrypted = "";
        try {
            Cipher cipher = generateCipher(cdd);
            byte enc[] = cipher.doFinal(raw.getBytes());
            encrypted = DatatypeConverter.printBase64Binary(enc);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return encrypted;
    }
    private static Cipher generateCipher(String cdd) {
        Cipher cipher = null;
        try {
            cdd = MD5(cdd);
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKeySpec secretkeySpec = new SecretKeySpec(
                    cdd.getBytes(), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretkeySpec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return cipher;
    }
    /**
     * 进行 BASE64 编码
     * @param result
     * @return
     */
    @SuppressWarnings("restriction")
    public static String getBASE64(String result) {

        if (result == null) {
            return null;
        }else{
            try {
                return (new sun.misc.BASE64Encoder()).encode( result.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
