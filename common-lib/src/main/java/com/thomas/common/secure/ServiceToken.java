package com.thomas.common.secure;

import com.thomas.common.utils.CodecHelper;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class ServiceToken {

    private static Map<String, String> AGENT_TYPE_SALT_MAP = new HashMap<String, String>() {{
        put("admin-service", "sdHUasHYhg7goUOI");
        put("account-service", "MdHUasHYhg7goUOI");
        put("profile-service", "BdHUasHYhg7goUOI");
    }};

    public static String encodeToken(String agentType) {
        String salt = AGENT_TYPE_SALT_MAP.get(agentType);
        if(salt == null){
            return null;
        }
        StringBuilder sb = new StringBuilder(40);
        return CodecHelper.md5(sb.append(salt).toString());
    }

    public static boolean checkToken(String agentType, String token) {
        String currentToken = encodeToken(agentType);
        if(StringUtils.isBlank(token) || StringUtils.isBlank(currentToken)){
            return false;
        }
        return currentToken.equals(token);
    }


}
