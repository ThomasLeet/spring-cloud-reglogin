package com.thomas.user.account.service;

import com.thomas.common.utils.PwdHelper;
import org.springframework.stereotype.Component;

@Component
public class PasswordService {

    public boolean checkPasswd(String plaintext,String encodePwd){
        return PwdHelper.checkPasswd(plaintext, encodePwd);
    }

    public String encryptPasswd(String plaintext) {
        return PwdHelper.calcPasswd(plaintext, PwdHelper.genPasswdSalt());
    }
}
