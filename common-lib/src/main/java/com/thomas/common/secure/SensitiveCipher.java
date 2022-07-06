package com.thomas.common.secure;

import com.thomas.common.utils.AESUtils;
import com.thomas.common.utils.CodecHelper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

public class SensitiveCipher {
    private static final Logger logger = LoggerFactory.getLogger(SensitiveCipher.class);
    private static final String CIPHER_KEY = "WDHUCI3hu&#@BNns";

    public SensitiveCipher() {
    }

    public static String encrypt(String plainText) {
        plainText = StringUtils.trim(plainText);
        if (StringUtils.isEmpty(plainText)) {
            return null;
        } else {
            String enc = AESUtils.encryptToString(plainText, CIPHER_KEY);
            return enc == null ? null : CodecHelper.base64ToBase61(enc);
        }
    }

    public static String decrypt(String cipherText) {
        if (StringUtils.isBlank(cipherText)) {
            return null;
        } else {
            try {
                String base64Text = CodecHelper.base61ToBase64(cipherText);
                byte[] values = AESUtils.decryptFromString(base64Text, CIPHER_KEY);
                return values == null ? null : StringUtils.trim(new String(values, StandardCharsets.UTF_8));
            } catch (Exception var3) {
                logger.error("decrypt err:" + cipherText, var3);
                return null;
            }
        }
    }
}
