package com.thomas.common.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.math.JVMRandom;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.InvalidParameterException;
import java.security.SecureRandom;

public class PwdHelper {
    public static Logger logger = LoggerFactory.getLogger(PwdHelper.class);
    private static final String aesKeyPasswdV4 = "TWDz+v$znlRTv%vv";

    public PwdHelper() {
    }

    public static boolean validatePassword(String passwd) {
        return StringUtils.isNotEmpty(passwd) && passwd.length() >= 8 && passwd.length() <= 20 && !RegisterUtil.isSimplePwd(passwd) && passwd.matches("^[A-Za-z0-9_\\!@#\\$%\\^&\\*\\(\\)_\\+=|<>,\\.{}:;\\]\\[\\~\\/\\?\"'\\\\-]+$") && passwd.matches("(?!^(\\d+|[a-zA-Z]+|[\\!@#\\$%\\^&\\*\\(\\)_\\+=|<>,\\.{}:;\\]\\[\\~\\/\\?\"'\\\\-]+)$)^[\\w\\!@#\\$%\\^&\\*\\(\\)_\\+=|<>,\\.{}:;\\]\\[\\~\\/\\?\"'\\\\-]+$");
    }



    public static final long genPasswdSalt() {
        return JVMRandom.nextLong(68719476735L);
    }


    public static final String genRandomSalt(int len) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[len * 3 / 4];
        random.nextBytes(bytes);
        String base64 = Base64.encodeBase64String(bytes).replaceAll("[\\s*\t\n\r]", "");
        return base64.length() > len ? base64.substring(0, len) : StringUtils.rightPad(base64, len, '$');
    }


    public static final String calcPasswd(String passwd, long salt) {
        StringBuilder sb = new StringBuilder(40);
        String md5 = CodecHelper.md5(sb.append(salt).append(passwd).append("iEUSEp9A").toString());
        String salthex = StringUtils.rightPad(Long.toHexString(salt), 10, 'z');
        salthex = salthex.length() > 10 ? salthex.substring(0, 10) : salthex;
        return (new StringBuilder(40)).append(md5.substring(2, 30)).append(salthex).toString();
    }

    public static long extractSalt(String encodePasswd) {
        if (encodePasswd != null && encodePasswd.length() == 38 && encodePasswd.endsWith("z")) {
            String suffix = encodePasswd.substring(28);
            String saltHex = suffix.substring(0, suffix.indexOf("z"));
            return Long.valueOf(saltHex, 16);
        } else {
            throw new InvalidParameterException();
        }
    }


    public static final boolean isPasswd(String passwd) {
        return StringUtils.length(passwd) == 38;
    }

    public static boolean checkPasswd(String plaintext, String encodePasswd) {
        if (!StringUtils.isBlank(plaintext) && !StringUtils.isBlank(encodePasswd)) {
            if (!isPasswd(encodePasswd)) {
                return false;
            } else {
                byte[] tryV3Bytes;
                byte[] v3Bytes;
                try {
                    tryV3Bytes = calcPasswd(plaintext, extractSalt(encodePasswd)).getBytes("UTF-8");
                    v3Bytes = encodePasswd.getBytes("UTF-8");
                } catch (UnsupportedEncodingException var6) {
                    return false;
                }
                if (tryV3Bytes.length != v3Bytes.length) {
                    return false;
                } else {
                    byte ret = 0;
                    for(int i = 0; i < tryV3Bytes.length; ++i) {
                        ret = (byte)(ret | tryV3Bytes[i] ^ v3Bytes[i]);
                    }
                    return ret == 0;
                }
            }
        } else {
            return false;
        }
    }


    public static void main(String[] args) {
        System.out.println(validatePassword("中"));
        System.out.println(validatePassword("yiwyrqequroew198042830483200932184921"));
        System.out.println(validatePassword("1234243a#%aaaa"));
        System.out.println(validatePassword("1234243abc"));
        System.out.println(validatePassword("*1234243abc"));
        String passwd = "!@#$";
        System.out.println(passwd.matches("(?!^(\\d+|[a-zA-Z]+|[\\!@#\\$%\\^&\\*\\(\\)_\\+=|<>,\\.{}:;\\]\\[\\~\\/\\?\"'\\\\-]+)$)^[\\w\\!@#\\$%\\^&\\*\\(\\)_\\+=|<>,\\.{}:;\\]\\[\\~\\/\\?\"'\\\\-]+$"));
        String regStr = "(?!^(\\d+|[a-zA-Z]+|[\\!@#\\$%\\^&\\*\\(\\)_\\+=|<>,\\.{}:;\\]\\[\\~\\/\\?\"'\\\\-]+)$)^[\\w\\!@#\\$%\\^&\\*\\(\\)_\\+=|<>,\\.{}:;\\]\\[\\~\\/\\?\"'\\\\-]+$";
        System.out.println(regStr);
        System.out.println(passwd.matches(regStr));
        System.out.println(extractSalt("f54b5ba5630fc430d1e47cd9ba235cac2d2b6z"));
        System.out.println(Long.valueOf("ffffffffff", 16));
    }
}
