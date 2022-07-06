package com.thomas.user.account.validation;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.apache.commons.lang3.math.NumberUtils;

public class PhoneNumberHelper {
    public static final PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

    public static boolean isValidNumber(String cCodeStr, String phoneStr) {
        int cCode = NumberUtils.toInt(cCodeStr);
        long phone = NumberUtils.toLong(phoneStr, 0L);
        Phonenumber.PhoneNumber pn = new Phonenumber.PhoneNumber();
        pn.setCountryCode(cCode);
        pn.setNationalNumber(phone);
        return phoneUtil.isValidNumber(pn);
    }
}
