package com.thomas.common.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class CodecHelper {
    public static final String md5(String input) {
        return DigestUtils.md5Hex(input);
    }

    public static String base64ToBase61(String input) {
        input = input.replaceAll("m", "A1");
        input = input.replaceAll("\\+", "A2");
        input = input.replaceAll("/", "A3");
        input = input.replaceAll("=", "A4");
        return input;
    }

    public static String base61ToBase64(String input) {
        input = input.replaceAll("A4", "=");
        input = input.replaceAll("A3", "/");
        input = input.replaceAll("A2", "+");
        input = input.replaceAll("A1", "m");
        return input;
    }
}
