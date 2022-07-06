package com.thomas.user.account.dto;

import com.thomas.user.account.validation.LoginProvider;
import com.thomas.user.account.validation.Password;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.group.GroupSequenceProvider;

import javax.validation.GroupSequence;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ApiModel(description = "登录实体")
@GroupSequenceProvider(LoginProvider.class)
public class LoginVO {

    @ApiModelProperty(value = "邮箱")
    @Email(groups = {EmailAnn.class})
    @NotNull(groups = {EmailAnn.class}, message = "Email is empty")
    @Size(max = 50, groups = {EmailAnn.class}, message = "Email address too long")
    private String email;

    @ApiModelProperty(value = "中国86开头")
    @NotNull(groups = {CountryCodeAnn.class}, message = "Country code is empty")
    @Size(max = 50,groups = {CountryCodeAnn.class}, message = "Country code is too long")
    private String countryCode;

    @ApiModelProperty(value = "手机号")
    @NotNull(groups = {PhoneAnn.class}, message = "Phone is empty")
    @Size(max=16,groups = {PhoneAnn.class}, message = "Phone is too long")
    private String phone;

    @ApiModelProperty(value = "密码")
    @NotNull(message = "The password cannot be empty")
    @Size(max=30,message = "The password is too long")
    @Password(groups = {PasspwordAnn.class})
    private String pwd;

    public interface EmailAnn {
    }

    public interface PhoneAnn {
    }

    public interface PasspwordAnn {
    }

    public interface CountryCodeAnn{

    }

    @GroupSequence({EmailAnn.class, PasspwordAnn.class})
    public interface EmailGroup {

    }

    @GroupSequence({CountryCodeAnn.class,PhoneAnn.class, PasspwordAnn.class})
    public interface PhoneGroup {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
