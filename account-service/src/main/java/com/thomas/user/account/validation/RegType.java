package com.thomas.user.account.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RegTypeValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface RegType {
    String message() default "Invalid register type";
    Class[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
