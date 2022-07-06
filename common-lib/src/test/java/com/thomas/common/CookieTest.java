package com.thomas.common;

import com.alibaba.fastjson.JSON;
import com.thomas.common.secure.SecureAuthCookie;
import com.thomas.common.utils.TimeHelper;

public class CookieTest {

    public static void main(String[] args) {
        long uid = 1357924680L;
        int ctime = TimeHelper.currentTimeSeconds();
        String pwd = "thisispassword";
        System.out.println(ctime+"|"+SecureAuthCookie.crc32(pwd));
        String ip = "127.0.0.1";
        String authcookie = SecureAuthCookie.genSAuthcookie(uid, ctime, pwd, ip, null);
        System.out.println(authcookie);
        SecureAuthCookie secureAuthCookie = SecureAuthCookie.decodeAuthCookie(authcookie);
        System.out.println(JSON.toJSON(secureAuthCookie));
    }
}
