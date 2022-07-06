package com.thomas.common;

import com.thomas.common.utils.IdHelper;

public class EmailIdTest {

    public static void main(String[] args) {
        String emailId = IdHelper.encodeEmailId(11111111111111L);
        IdHelper.decodeEmailId(emailId);
    }
}
