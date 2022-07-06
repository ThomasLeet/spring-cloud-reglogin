package com.thomas.user.account.dto;

import com.thomas.common.constants.ServiceConstants;
import com.thomas.user.account.validation.Password;
import com.thomas.user.account.validation.RegProvider;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.group.GroupSequenceProvider;

import javax.validation.GroupSequence;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ApiModel(description = "注册实体")
@GroupSequenceProvider(RegProvider.class)
@Getter
@Setter
public class RegVO {

    @ApiModelProperty(value = "注册类型，e-邮箱注册,p-手机注册")
    @NotNull(groups = {EmailAnn.class, PhoneAnn.class})
    private String regType;

    @ApiModelProperty(value = "邮箱")
    @Email(groups = {EmailAnn.class}, message = "Invalid mailbox")
    @NotNull(groups = {EmailAnn.class}, message = "Email is empty")
    @Size(max = 50, groups = {EmailAnn.class}, message = "Email is too long")
    private String email;

    @ApiModelProperty(value = "中国86开头")
    @NotNull(groups = {CountryCodeAnn.class}, message = "Country code is empty")
    @Size(max = 4, groups = {CountryCodeAnn.class}, message = "Country code is too long")
    private String countryCode;

    @ApiModelProperty(value = "手机号")
    @NotNull(groups = {PhoneAnn.class}, message = "Phone is empty")
    @Size(max = 16, groups = {PhoneAnn.class}, message = "Phone is too long ")
    private String phone;

    @ApiModelProperty(value = "密码")
    @NotNull(groups = {PasspwordAnn.class}, message = "The password cannot be empty")
    @Size(max = 30, groups = {PasspwordAnn.class}, message = "The password is too long")
    @Password(groups = {PasspwordAnn.class})
    private String pwd;

    @ApiModelProperty(hidden = true)
    public boolean isEmailRegister() {
        return regType != null && ServiceConstants.ACCOUNT_EMAIL_REGISTER.equals(regType);
    }

    @ApiModelProperty(hidden = true)
    public boolean isPhoneRegister() {
        return regType != null && ServiceConstants.ACCOUNT_PHONE_REGISTER.equals(regType);
    }

    public interface EmailAnn {
    }

    public interface PhoneAnn {
    }

    public interface PasspwordAnn {
    }

    public interface CountryCodeAnn {

    }

    @GroupSequence({EmailAnn.class, PasspwordAnn.class})
    public interface EmailGroup {

    }

    @GroupSequence({CountryCodeAnn.class, PhoneAnn.class, PasspwordAnn.class})
    public interface PhoneGroup {

    }


}
