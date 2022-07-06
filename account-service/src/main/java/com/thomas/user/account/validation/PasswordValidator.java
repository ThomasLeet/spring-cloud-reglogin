package com.thomas.user.account.validation;

import com.thomas.common.utils.PwdHelper;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {

    @Override
    public boolean isValid(String passwordField, ConstraintValidatorContext context) {
        if (passwordField == null) return false;
        return PwdHelper.validatePassword(passwordField);
    }
}
