package com.thomas.common.utils;

import com.sun.jersey.core.util.Base64;
import com.thomas.common.secure.SecureAuthCookie;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class AESUtils {
    static String IV = "AAAAAAAAAAAAAAAA";
    static String plaintext = "test text 123\u0000\u0000\u0000";
    static String encryptionKey = "7890123456abcdef";
    public static String PASSWD_LOG_KEY = "7809123456!$cdef";
    private static final Logger logger = LoggerFactory.getLogger(AESUtils.class);

    public AESUtils() {
    }

    public static void main(String[] args) {
        try {
            System.out.println("==Java==");
            plaintext = "1231219aaaa232@2123121923812\u0000\u0000\u0000\u0000";
            System.out.println(plaintext.length());
            System.out.println("plain:   " + plaintext);
            byte[] cipher = encrypt(plaintext, encryptionKey);
            System.out.print("cipher:  ");

            for (int i = 0; i < cipher.length; ++i) {
                System.out.print(new Integer(cipher[i]) + " ");
            }

            System.out.println("");
            String decrypted = decrypt(cipher, encryptionKey);
            System.out.println("decrypt: " + decrypted);
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    public static String encryptPwd(String pwd) {
        return StringUtils.isBlank(pwd) ? null : encryptToString(pwd, PASSWD_LOG_KEY);
    }

    public static String decryptPwd(String pwd) {
        if (StringUtils.isBlank(pwd)) {
            return null;
        } else {
            try {
                String ret = new String(decryptFromString(pwd, PASSWD_LOG_KEY), "UTF-8");
                return StringUtils.trim(ret);
            } catch (UnsupportedEncodingException var3) {
                var3.printStackTrace();
                return null;
            }
        }
    }

    public static byte[] encrypt(String plainText, String encryptionKey) {
        try {
            int length = plainText.length();
            int padNums = 16 - (length & 15);
            if (padNums > 0) {
                plainText = StringUtils.rightPad(plainText, length + padNums, "\u0000");
            }

            return encrypt(plainText.getBytes("UTF-8"), encryptionKey);
        } catch (Exception var4) {
            logger.error("encrypt {}", plainText, var4);
            return null;
        }
    }

    public static byte[] encrypt(byte[] plainText, String encryptionKey) throws Exception {
        if (plainText == null) {
            return null;
        } else {
            int module = plainText.length % 16;
            if (module != 0) {
                ByteBuffer byteBuffer = ByteBuffer.allocate(plainText.length + 16 - module);
                byteBuffer.put(plainText);
                plainText = byteBuffer.array();
            }

            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding", "SunJCE");
            SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
            cipher.init(1, key, new IvParameterSpec(IV.getBytes("UTF-8")));
            return cipher.doFinal(plainText);
        }
    }

    public static String encryptToString(byte[] plainText, String encryptionKey) {
        try {
            byte[] encrypt = encrypt(plainText, encryptionKey);
            return new String(Base64.encode(encrypt), "UTF8");
        } catch (Exception var4) {
            logger.error("encryptToString_error {}", plainText, var4);
            return null;
        }
    }


    public static String encryptToString(String plainText, String encryptionKey) {
        try {
            byte[] encrypt = encrypt(plainText, encryptionKey);
            return new String(Base64.encode(encrypt), "UTF8");
        } catch (UnsupportedEncodingException var3) {
            logger.error("encryptToString_error {}", plainText, var3);
            return null;
        }
    }

    public static String decryptToString(String cipherText, String encryptionKey){
        try {
            return StringUtils.trim(new String(decryptFromString(cipherText,encryptionKey)));
        } catch (Exception e) {
            logger.error("aesDecypt_error {}", e);
        }
        return null;
    }

    public static byte[] decryptFromString(String cipherText, String encryptionKey) {
        try {
            return decryptToByte(Base64.decode(cipherText.getBytes("UTF8")), encryptionKey);
        } catch (Exception var3) {
            logger.error("aesDecypt_error {}", cipherText, var3);
            return null;
        }
    }

    public static byte[] decryptToByte(byte[] cipherText, String encryptionKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding", "SunJCE");
        SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
        cipher.init(2, key, new IvParameterSpec(IV.getBytes("UTF-8")));
        return cipher.doFinal(cipherText);
    }

    public static String decrypt(byte[] cipherText, String encryptionKey) throws Exception {
        String ret = new String(decryptToByte(cipherText, encryptionKey), "UTF-8");
        return StringUtils.trim(ret);
    }

    public static String encryptToBase61(byte[] plainText, String encryptionKey) {
        return CodecHelper.base64ToBase61(encryptToString(plainText, encryptionKey));
    }

    public static byte[] decryptFromBase61(String cipherText, String encryptionKey, boolean isAuth) {
        String auth = CodecHelper.base61ToBase64(cipherText);
        return decryptFromString(auth, encryptionKey);
    }
}
