package com.thomas.user.account.validation;

import org.apache.commons.lang.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RegTypeValidator implements ConstraintValidator<RegType, String> {

    @Override
    public boolean isValid(String regTypeStr, ConstraintValidatorContext context) {
      return RegTypeEnum.isInEnum(regTypeStr);
    }
}
