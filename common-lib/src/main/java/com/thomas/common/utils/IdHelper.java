package com.thomas.common.utils;

import com.thomas.common.api.ResultCode;
import com.thomas.common.exception.ExceptionHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

public class IdHelper {

    public static Logger logger = LoggerFactory.getLogger(IdHelper.class);

    private static final String key = "oZ5ziTng0dXe0CUj";

    public static  String encodeEmailId(long uid){
        long timeMillis = System.currentTimeMillis();
        String plainText = uid+"|"+TimeHelper.get10MinutesExpireTime();
//        if(plainText.length() < 16){
//            plainText = StringUtils.rightPad(plainText,16-plainText.length(),'z');
//        }
        try {
            return AESUtils.encryptToString(plainText.getBytes(StandardCharsets.UTF_8), key);
        } catch (Exception e) {
            logger.error("email id encode error",e);
        }
        return plainText;
    }

    public static long decodeEmailId(String code){
        String decodeStr = null;
        try {
            String decrypt = AESUtils.decryptToString(code, key);
            decodeStr = new String(decrypt);
            //StringUtils.removeEnd(decodeStr,"z");
        } catch (Exception e) {
            logger.error("email id decode error",e);
        }
        if(decodeStr == null){
            return 0L;
        }
        if(StringUtils.indexOf(decodeStr,'|') > 0){
            String[] idAndTime = StringUtils.split(decodeStr, '|');
            if(idAndTime != null &&idAndTime.length ==2){
                long uid = NumberUtils.toLong(idAndTime[0],0L);
                long timestamp = NumberUtils.toLong(idAndTime[1],0L);
                if(timestamp < System.currentTimeMillis()){
                    throw ExceptionHelper.createUserBizException(ResultCode.EMAIL_LINK_EXPIRED);
                };
                return uid;
            }
        }
        return 0L;
    }
}
