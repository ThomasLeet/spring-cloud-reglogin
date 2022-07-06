package com.thomas.common.utils;

import org.apache.commons.lang3.StringUtils;

public class RegisterUtil {
    private static String LETTERS = "abcdefghijklmnopqrstuvwxyz/ABCDEFGHIJKLMNOPQRSTUVWXYZ/01234567890";

    public RegisterUtil() {
    }

    public static boolean isSimplePwd(String pwd) {
        return isRepeatedString(pwd) || isAscending(pwd) || isDescending(pwd);
    }

    public static boolean isRepeatedString(String pwd) {
        return StringUtils.equals(pwd, StringUtils.repeat(StringUtils.substring(pwd, 0, 1), pwd.length()));
    }

    public static boolean isAscending(String pwd) {
        return StringUtils.contains(LETTERS, pwd);
    }

    public static boolean isDescending(String pwd) {
        return StringUtils.contains(StringUtils.reverse(LETTERS), pwd);
    }
}
