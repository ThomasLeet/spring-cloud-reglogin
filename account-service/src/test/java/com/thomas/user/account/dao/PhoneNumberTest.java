package com.thomas.user.account.dao;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;

public class PhoneNumberTest {

    PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
    @Test
    public void test(){
        int ccode = NumberUtils.toInt("0086");
        long phone = NumberUtils.toLong("13861395951", 0L);

        Phonenumber.PhoneNumber pn = new Phonenumber.PhoneNumber();
        pn.setCountryCode(ccode);
        pn.setNationalNumber(phone);

        System.out.println(phoneUtil.isValidNumber(pn));
    }
}
