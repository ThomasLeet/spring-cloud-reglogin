package com.thomas.user.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@ApiModel(description = "登录")
@Getter
@Setter
public class AdminLoginVO {
    @ApiModelProperty(value = "用户名")
    @NotNull(message = "The name cannot be empty")
    private String name;

    @ApiModelProperty(value = "密码")
    @NotNull(message = "The password cannot be empty")
    private String pwd;

}
