package com.thomas.common.secure;


import com.thomas.common.utils.AESUtils;
import com.thomas.common.utils.IPHelper;
import com.thomas.common.utils.TimeHelper;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class SecureAuthCookie {

    static final Logger logger = LoggerFactory.getLogger(SecureAuthCookie.class);

    private static int AUTHCOOKIE_LENGTH = 43;
    private static String md5Key = "auth_test%^#$";
    private static String aesAuthKey = "aes_test*@%!";
    private static String md5Symbol = "md5s1";
    private static String aesSymbol = "adss1";

    public static String genSAuthcookie(long uid, int ctime, String pwd, String ip, String deviceId) {
        if (ctime > TimeHelper.currentTimeSeconds()) {
            ctime = TimeHelper.currentTimeSeconds();
        }

        long pwdCrc32 = 0L;
        if (StringUtils.isNotBlank(pwd)) {
            pwdCrc32 = crc32(pwd);
        }

        long deviceIdCrc32 = 0L;
        if (StringUtils.isNotBlank(deviceId)) {
            deviceIdCrc32 = genSessionId(deviceId);
        }

        return genSAuthcookie(uid, ctime, pwdCrc32, deviceIdCrc32, ip);
    }

    public static long genSessionId(String deviceid) {
        return crc32(deviceid);
    }

    public static long crc32(String content) {
        if (content == null) {
            return 0L;
        } else {
            Checksum checksum = new CRC32();
            checksum.update(content.getBytes(), 0, content.length());
            return checksum.getValue();
        }
    }

    private static String genSAuthcookie(long uid, int ctime, long pwd, long session_id, String ip) {
        ByteBuffer content = getContentByte(uid, ctime, pwd, session_id, ip);
        return encryAuthcookie(content, uid, ctime);
    }

    private static String encryAuthcookie(ByteBuffer content, long uid, int ctime) {
        String uidMd5 = StringUtils.lowerCase(DigestUtils.md5Hex(uid + md5Key + ctime));
        String suffix = StringUtils.substring(uidMd5, -4);
        String aesKey = aesAuthKey + suffix;
        String encryptedAuth = AESUtils.encryptToBase61(content.array(), aesKey);
        return md5Symbol + aesSymbol + encryptedAuth + suffix;
    }

    public static SecureAuthCookie decodeAuthCookie(String sAuthcookie) {
        try {
            int keySymbolLen = md5Symbol.length() + aesSymbol.length();
            String suffix = StringUtils.substring(sAuthcookie, -4);
            String realAesKey = aesAuthKey + suffix;
            String authcookie = StringUtils.substring(sAuthcookie, keySymbolLen , -4);
            byte[] decrypted = AESUtils.decryptFromBase61(authcookie, realAesKey, true);
            if (decrypted == null) {
                return null;
            } else {
                int len = decrypted.length;
                if (len > 4) {
                    for(int i = len - 4; i < len; ++i) {
                        if (decrypted[i] != 0) {
                            return null;
                        }
                    }
                }
                SecureAuthCookie secureAuthCookie = valueOf(decrypted);
                if (secureAuthCookie != null && (md5Symbol.length() > 0 || secureAuthCookie.ctime > 2147483647)) {
                    String uidMd5 = StringUtils.lowerCase(DigestUtils.md5Hex(secureAuthCookie.uid + md5Key + secureAuthCookie.ctime));
                    String rightSuffix = StringUtils.substring(uidMd5, -4);
                    if (!StringUtils.equals(suffix, rightSuffix)) {
                        return null;
                    }
                }
                return secureAuthCookie;
            }
        } catch (Exception var16) {
            logger.error("decode_authcookie_error {}", sAuthcookie, var16);
            return null;
        }
    }

    private static ByteBuffer getContentByte(long uid, int ctime, long pwd, long session_id, String ip) {
        ByteBuffer content = ByteBuffer.allocate(AUTHCOOKIE_LENGTH);
        content.putLong(uid);
        content.putInt(ctime);
        content.putLong(pwd);
        content.putLong(session_id);
        content.putInt(IPHelper.ip2int(ip));
        return content;
    }

    public long uid;
    public int ctime;
    public long pwd;
    public long session_id;
    public String ip;
    public int expire;

    public static SecureAuthCookie valueOf(byte[] data) {
        ByteBuffer authByteBuffer = ByteBuffer.wrap(data);
        authByteBuffer.rewind();
        SecureAuthCookie secureAuthCookie = new SecureAuthCookie();
        secureAuthCookie.uid = authByteBuffer.getLong();
        secureAuthCookie.ctime = authByteBuffer.getInt();
        secureAuthCookie.expire = secureAuthCookie.ctime + 7776000;
        secureAuthCookie.pwd = authByteBuffer.getLong();
        secureAuthCookie.session_id = authByteBuffer.getLong();
        secureAuthCookie.ip = IPHelper.int2ip(authByteBuffer.getInt());
        return secureAuthCookie;
    }
}
